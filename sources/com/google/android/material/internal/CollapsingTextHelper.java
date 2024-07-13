package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.StaticLayoutBuilderCompat;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.TextAppearance;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    @NonNull
    private static final Paint DEBUG_DRAW_PAINT;
    private static final String ELLIPSIS_NORMAL = "…";
    private static final float FADE_MODE_THRESHOLD_FRACTION_RELATIVE = 0.5f;
    private static final String TAG = "CollapsingTextHelper";
    private static final boolean USE_SCALING_TEXTURE;
    private boolean boundsChanged;
    private float collapsedDrawX;
    private float collapsedDrawY;
    private CancelableFontCallback collapsedFontCallback;
    private float collapsedLetterSpacing;
    private ColorStateList collapsedShadowColor;
    private float collapsedShadowDx;
    private float collapsedShadowDy;
    private float collapsedShadowRadius;
    private float collapsedTextBlend;
    private ColorStateList collapsedTextColor;
    private Typeface collapsedTypeface;
    private float currentDrawX;
    private float currentDrawY;
    private int currentOffsetY;
    private float currentTextSize;
    private Typeface currentTypeface;
    private boolean drawTitle;
    private float expandedDrawX;
    private float expandedDrawY;
    private float expandedFirstLineDrawX;
    private CancelableFontCallback expandedFontCallback;
    private float expandedFraction;
    private float expandedLetterSpacing;
    private ColorStateList expandedShadowColor;
    private float expandedShadowDx;
    private float expandedShadowDy;
    private float expandedShadowRadius;
    private float expandedTextBlend;
    private ColorStateList expandedTextColor;
    @Nullable
    private Bitmap expandedTitleTexture;
    private Typeface expandedTypeface;
    private boolean fadeModeEnabled;
    private float fadeModeStartFraction;
    private boolean isRtl;
    private TimeInterpolator positionInterpolator;
    private float scale;
    private int[] state;
    @Nullable
    private CharSequence text;
    private StaticLayout textLayout;
    private TimeInterpolator textSizeInterpolator;
    @Nullable
    private CharSequence textToDraw;
    private CharSequence textToDrawCollapsed;
    private Paint texturePaint;
    private boolean useTexture;
    private final View view;
    private int expandedTextGravity = 16;
    private int collapsedTextGravity = 16;
    private float expandedTextSize = 15.0f;
    private float collapsedTextSize = 15.0f;
    private boolean isRtlTextDirectionHeuristicsEnabled = true;
    private int maxLines = 1;
    private float lineSpacingAdd = 0.0f;
    private float lineSpacingMultiplier = 1.0f;
    private int hyphenationFrequency = StaticLayoutBuilderCompat.DEFAULT_HYPHENATION_FREQUENCY;
    @NonNull
    private final TextPaint textPaint = new TextPaint(129);
    @NonNull
    private final TextPaint tmpPaint = new TextPaint(this.textPaint);
    @NonNull
    private final Rect collapsedBounds = new Rect();
    @NonNull
    private final Rect expandedBounds = new Rect();
    @NonNull
    private final RectF currentBounds = new RectF();
    private float fadeModeThresholdFraction = calculateFadeModeThresholdFraction();

    static {
        USE_SCALING_TEXTURE = Build.VERSION.SDK_INT < 18;
        DEBUG_DRAW_PAINT = null;
        if (DEBUG_DRAW_PAINT != null) {
            DEBUG_DRAW_PAINT.setAntiAlias(true);
            DEBUG_DRAW_PAINT.setColor(-65281);
        }
    }

    public CollapsingTextHelper(View view) {
        this.view = view;
    }

    public void setTextSizeInterpolator(TimeInterpolator interpolator) {
        this.textSizeInterpolator = interpolator;
        recalculate();
    }

    public void setPositionInterpolator(TimeInterpolator interpolator) {
        this.positionInterpolator = interpolator;
        recalculate();
    }

    public void setExpandedTextSize(float textSize) {
        if (this.expandedTextSize != textSize) {
            this.expandedTextSize = textSize;
            recalculate();
        }
    }

    public void setCollapsedTextSize(float textSize) {
        if (this.collapsedTextSize != textSize) {
            this.collapsedTextSize = textSize;
            recalculate();
        }
    }

    public void setCollapsedTextColor(ColorStateList textColor) {
        if (this.collapsedTextColor != textColor) {
            this.collapsedTextColor = textColor;
            recalculate();
        }
    }

    public void setExpandedTextColor(ColorStateList textColor) {
        if (this.expandedTextColor != textColor) {
            this.expandedTextColor = textColor;
            recalculate();
        }
    }

    public void setExpandedBounds(int left, int top, int right, int bottom) {
        if (!rectEquals(this.expandedBounds, left, top, right, bottom)) {
            this.expandedBounds.set(left, top, right, bottom);
            this.boundsChanged = true;
            onBoundsChanged();
        }
    }

    public void setExpandedBounds(@NonNull Rect bounds) {
        setExpandedBounds(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    public void setCollapsedBounds(int left, int top, int right, int bottom) {
        if (!rectEquals(this.collapsedBounds, left, top, right, bottom)) {
            this.collapsedBounds.set(left, top, right, bottom);
            this.boundsChanged = true;
            onBoundsChanged();
        }
    }

    public void setCollapsedBounds(@NonNull Rect bounds) {
        setCollapsedBounds(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    public void getCollapsedTextActualBounds(@NonNull RectF bounds, int labelWidth, int textGravity) {
        this.isRtl = calculateIsRtl(this.text);
        bounds.left = getCollapsedTextLeftBound(labelWidth, textGravity);
        bounds.top = this.collapsedBounds.top;
        bounds.right = getCollapsedTextRightBound(bounds, labelWidth, textGravity);
        bounds.bottom = this.collapsedBounds.top + getCollapsedTextHeight();
    }

    private float getCollapsedTextLeftBound(int width, int gravity) {
        if (gravity == 17 || (gravity & 7) == 1) {
            return (width / 2.0f) - (calculateCollapsedTextWidth() / 2.0f);
        }
        return ((gravity & GravityCompat.END) == 8388613 || (gravity & 5) == 5) ? this.isRtl ? this.collapsedBounds.left : this.collapsedBounds.right - calculateCollapsedTextWidth() : this.isRtl ? this.collapsedBounds.right - calculateCollapsedTextWidth() : this.collapsedBounds.left;
    }

    private float getCollapsedTextRightBound(@NonNull RectF bounds, int width, int gravity) {
        if (gravity == 17 || (gravity & 7) == 1) {
            return (width / 2.0f) + (calculateCollapsedTextWidth() / 2.0f);
        }
        return ((gravity & GravityCompat.END) == 8388613 || (gravity & 5) == 5) ? this.isRtl ? bounds.left + calculateCollapsedTextWidth() : this.collapsedBounds.right : this.isRtl ? this.collapsedBounds.right : bounds.left + calculateCollapsedTextWidth();
    }

    public float calculateCollapsedTextWidth() {
        if (this.text == null) {
            return 0.0f;
        }
        getTextPaintCollapsed(this.tmpPaint);
        return this.tmpPaint.measureText(this.text, 0, this.text.length());
    }

    public float getExpandedTextHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getExpandedTextFullHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return (-this.tmpPaint.ascent()) + this.tmpPaint.descent();
    }

    public float getCollapsedTextHeight() {
        getTextPaintCollapsed(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public void setCurrentOffsetY(int currentOffsetY) {
        this.currentOffsetY = currentOffsetY;
    }

    public void setFadeModeStartFraction(float fadeModeStartFraction) {
        this.fadeModeStartFraction = fadeModeStartFraction;
        this.fadeModeThresholdFraction = calculateFadeModeThresholdFraction();
    }

    private float calculateFadeModeThresholdFraction() {
        return this.fadeModeStartFraction + ((1.0f - this.fadeModeStartFraction) * FADE_MODE_THRESHOLD_FRACTION_RELATIVE);
    }

    public void setFadeModeEnabled(boolean fadeModeEnabled) {
        this.fadeModeEnabled = fadeModeEnabled;
    }

    private void getTextPaintExpanded(@NonNull TextPaint textPaint) {
        textPaint.setTextSize(this.expandedTextSize);
        textPaint.setTypeface(this.expandedTypeface);
        if (Build.VERSION.SDK_INT >= 21) {
            textPaint.setLetterSpacing(this.expandedLetterSpacing);
        }
    }

    private void getTextPaintCollapsed(@NonNull TextPaint textPaint) {
        textPaint.setTextSize(this.collapsedTextSize);
        textPaint.setTypeface(this.collapsedTypeface);
        if (Build.VERSION.SDK_INT >= 21) {
            textPaint.setLetterSpacing(this.collapsedLetterSpacing);
        }
    }

    void onBoundsChanged() {
        this.drawTitle = this.collapsedBounds.width() > 0 && this.collapsedBounds.height() > 0 && this.expandedBounds.width() > 0 && this.expandedBounds.height() > 0;
    }

    public void setExpandedTextGravity(int gravity) {
        if (this.expandedTextGravity != gravity) {
            this.expandedTextGravity = gravity;
            recalculate();
        }
    }

    public int getExpandedTextGravity() {
        return this.expandedTextGravity;
    }

    public void setCollapsedTextGravity(int gravity) {
        if (this.collapsedTextGravity != gravity) {
            this.collapsedTextGravity = gravity;
            recalculate();
        }
    }

    public int getCollapsedTextGravity() {
        return this.collapsedTextGravity;
    }

    public void setCollapsedTextAppearance(int resId) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), resId);
        if (textAppearance.textColor != null) {
            this.collapsedTextColor = textAppearance.textColor;
        }
        if (textAppearance.textSize != 0.0f) {
            this.collapsedTextSize = textAppearance.textSize;
        }
        if (textAppearance.shadowColor != null) {
            this.collapsedShadowColor = textAppearance.shadowColor;
        }
        this.collapsedShadowDx = textAppearance.shadowDx;
        this.collapsedShadowDy = textAppearance.shadowDy;
        this.collapsedShadowRadius = textAppearance.shadowRadius;
        this.collapsedLetterSpacing = textAppearance.letterSpacing;
        if (this.collapsedFontCallback != null) {
            this.collapsedFontCallback.cancel();
        }
        this.collapsedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() { // from class: com.google.android.material.internal.CollapsingTextHelper.1
            @Override // com.google.android.material.resources.CancelableFontCallback.ApplyFont
            public void apply(Typeface font) {
                CollapsingTextHelper.this.setCollapsedTypeface(font);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.collapsedFontCallback);
        recalculate();
    }

    public void setExpandedTextAppearance(int resId) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), resId);
        if (textAppearance.textColor != null) {
            this.expandedTextColor = textAppearance.textColor;
        }
        if (textAppearance.textSize != 0.0f) {
            this.expandedTextSize = textAppearance.textSize;
        }
        if (textAppearance.shadowColor != null) {
            this.expandedShadowColor = textAppearance.shadowColor;
        }
        this.expandedShadowDx = textAppearance.shadowDx;
        this.expandedShadowDy = textAppearance.shadowDy;
        this.expandedShadowRadius = textAppearance.shadowRadius;
        this.expandedLetterSpacing = textAppearance.letterSpacing;
        if (this.expandedFontCallback != null) {
            this.expandedFontCallback.cancel();
        }
        this.expandedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() { // from class: com.google.android.material.internal.CollapsingTextHelper.2
            @Override // com.google.android.material.resources.CancelableFontCallback.ApplyFont
            public void apply(Typeface font) {
                CollapsingTextHelper.this.setExpandedTypeface(font);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.expandedFontCallback);
        recalculate();
    }

    public void setCollapsedTypeface(Typeface typeface) {
        if (setCollapsedTypefaceInternal(typeface)) {
            recalculate();
        }
    }

    public void setExpandedTypeface(Typeface typeface) {
        if (setExpandedTypefaceInternal(typeface)) {
            recalculate();
        }
    }

    public void setTypefaces(Typeface typeface) {
        boolean collapsedFontChanged = setCollapsedTypefaceInternal(typeface);
        boolean expandedFontChanged = setExpandedTypefaceInternal(typeface);
        if (collapsedFontChanged || expandedFontChanged) {
            recalculate();
        }
    }

    private boolean setCollapsedTypefaceInternal(Typeface typeface) {
        if (this.collapsedFontCallback != null) {
            this.collapsedFontCallback.cancel();
        }
        if (this.collapsedTypeface != typeface) {
            this.collapsedTypeface = typeface;
            return true;
        }
        return false;
    }

    private boolean setExpandedTypefaceInternal(Typeface typeface) {
        if (this.expandedFontCallback != null) {
            this.expandedFontCallback.cancel();
        }
        if (this.expandedTypeface != typeface) {
            this.expandedTypeface = typeface;
            return true;
        }
        return false;
    }

    public Typeface getCollapsedTypeface() {
        return this.collapsedTypeface != null ? this.collapsedTypeface : Typeface.DEFAULT;
    }

    public Typeface getExpandedTypeface() {
        return this.expandedTypeface != null ? this.expandedTypeface : Typeface.DEFAULT;
    }

    public void setExpansionFraction(float fraction) {
        float fraction2 = MathUtils.clamp(fraction, 0.0f, 1.0f);
        if (fraction2 != this.expandedFraction) {
            this.expandedFraction = fraction2;
            calculateCurrentOffsets();
        }
    }

    public final boolean setState(int[] state) {
        this.state = state;
        if (isStateful()) {
            recalculate();
            return true;
        }
        return false;
    }

    public final boolean isStateful() {
        return (this.collapsedTextColor != null && this.collapsedTextColor.isStateful()) || (this.expandedTextColor != null && this.expandedTextColor.isStateful());
    }

    public float getFadeModeThresholdFraction() {
        return this.fadeModeThresholdFraction;
    }

    public float getExpansionFraction() {
        return this.expandedFraction;
    }

    public float getCollapsedTextSize() {
        return this.collapsedTextSize;
    }

    public float getExpandedTextSize() {
        return this.expandedTextSize;
    }

    public void setRtlTextDirectionHeuristicsEnabled(boolean rtlTextDirectionHeuristicsEnabled) {
        this.isRtlTextDirectionHeuristicsEnabled = rtlTextDirectionHeuristicsEnabled;
    }

    public boolean isRtlTextDirectionHeuristicsEnabled() {
        return this.isRtlTextDirectionHeuristicsEnabled;
    }

    private void calculateCurrentOffsets() {
        calculateOffsets(this.expandedFraction);
    }

    private void calculateOffsets(float fraction) {
        float textBlendFraction;
        interpolateBounds(fraction);
        if (this.fadeModeEnabled) {
            if (fraction < this.fadeModeThresholdFraction) {
                textBlendFraction = 0.0f;
                this.currentDrawX = this.expandedDrawX;
                this.currentDrawY = this.expandedDrawY;
                setInterpolatedTextSize(this.expandedTextSize);
            } else {
                textBlendFraction = 1.0f;
                this.currentDrawX = this.collapsedDrawX;
                this.currentDrawY = this.collapsedDrawY - Math.max(0, this.currentOffsetY);
                setInterpolatedTextSize(this.collapsedTextSize);
            }
        } else {
            textBlendFraction = fraction;
            this.currentDrawX = lerp(this.expandedDrawX, this.collapsedDrawX, fraction, this.positionInterpolator);
            this.currentDrawY = lerp(this.expandedDrawY, this.collapsedDrawY, fraction, this.positionInterpolator);
            setInterpolatedTextSize(lerp(this.expandedTextSize, this.collapsedTextSize, fraction, this.textSizeInterpolator));
        }
        setCollapsedTextBlend(1.0f - lerp(0.0f, 1.0f, 1.0f - fraction, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        setExpandedTextBlend(lerp(1.0f, 0.0f, fraction, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        if (this.collapsedTextColor != this.expandedTextColor) {
            this.textPaint.setColor(blendColors(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), textBlendFraction));
        } else {
            this.textPaint.setColor(getCurrentCollapsedTextColor());
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (this.collapsedLetterSpacing != this.expandedLetterSpacing) {
                this.textPaint.setLetterSpacing(lerp(this.expandedLetterSpacing, this.collapsedLetterSpacing, fraction, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
            } else {
                this.textPaint.setLetterSpacing(this.collapsedLetterSpacing);
            }
        }
        this.textPaint.setShadowLayer(lerp(this.expandedShadowRadius, this.collapsedShadowRadius, fraction, null), lerp(this.expandedShadowDx, this.collapsedShadowDx, fraction, null), lerp(this.expandedShadowDy, this.collapsedShadowDy, fraction, null), blendColors(getCurrentColor(this.expandedShadowColor), getCurrentColor(this.collapsedShadowColor), fraction));
        if (this.fadeModeEnabled) {
            int textAlpha = (int) (calculateFadeModeTextAlpha(fraction) * 255.0f);
            this.textPaint.setAlpha(textAlpha);
        }
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private float calculateFadeModeTextAlpha(@FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        return fraction <= this.fadeModeThresholdFraction ? AnimationUtils.lerp(1.0f, 0.0f, this.fadeModeStartFraction, this.fadeModeThresholdFraction, fraction) : AnimationUtils.lerp(0.0f, 1.0f, this.fadeModeThresholdFraction, 1.0f, fraction);
    }

    @ColorInt
    private int getCurrentExpandedTextColor() {
        return getCurrentColor(this.expandedTextColor);
    }

    @ColorInt
    public int getCurrentCollapsedTextColor() {
        return getCurrentColor(this.collapsedTextColor);
    }

    @ColorInt
    private int getCurrentColor(@Nullable ColorStateList colorStateList) {
        if (colorStateList == null) {
            return 0;
        }
        if (this.state != null) {
            return colorStateList.getColorForState(this.state, 0);
        }
        return colorStateList.getDefaultColor();
    }

    private void calculateBaseOffsets(boolean forceRecalculate) {
        float f;
        float currentTextSize = this.currentTextSize;
        calculateUsingTextSize(this.collapsedTextSize, forceRecalculate);
        if (this.textToDraw != null && this.textLayout != null) {
            this.textToDrawCollapsed = TextUtils.ellipsize(this.textToDraw, this.textPaint, this.textLayout.getWidth(), TextUtils.TruncateAt.END);
        }
        float width = this.textToDrawCollapsed != null ? this.textPaint.measureText(this.textToDrawCollapsed, 0, this.textToDrawCollapsed.length()) : 0.0f;
        int collapsedAbsGravity = GravityCompat.getAbsoluteGravity(this.collapsedTextGravity, this.isRtl ? 1 : 0);
        switch (collapsedAbsGravity & 112) {
            case 48:
                this.collapsedDrawY = this.collapsedBounds.top;
                break;
            case 80:
                this.collapsedDrawY = this.collapsedBounds.bottom + this.textPaint.ascent();
                break;
            default:
                float textOffset = (this.textPaint.descent() - this.textPaint.ascent()) / 2.0f;
                this.collapsedDrawY = this.collapsedBounds.centerY() - textOffset;
                break;
        }
        switch (8388615 & collapsedAbsGravity) {
            case 1:
                this.collapsedDrawX = this.collapsedBounds.centerX() - (width / 2.0f);
                break;
            case 5:
                this.collapsedDrawX = this.collapsedBounds.right - width;
                break;
            default:
                this.collapsedDrawX = this.collapsedBounds.left;
                break;
        }
        calculateUsingTextSize(this.expandedTextSize, forceRecalculate);
        float expandedTextHeight = this.textLayout != null ? this.textLayout.getHeight() : 0.0f;
        float measuredWidth = this.textToDraw != null ? this.textPaint.measureText(this.textToDraw, 0, this.textToDraw.length()) : 0.0f;
        float width2 = (this.textLayout == null || this.maxLines <= 1) ? measuredWidth : this.textLayout.getWidth();
        if (this.textLayout != null) {
            f = this.maxLines > 1 ? this.textLayout.getLineStart(0) : this.textLayout.getLineLeft(0);
        } else {
            f = 0.0f;
        }
        this.expandedFirstLineDrawX = f;
        int expandedAbsGravity = GravityCompat.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0);
        switch (expandedAbsGravity & 112) {
            case 48:
                this.expandedDrawY = this.expandedBounds.top;
                break;
            case 80:
                this.expandedDrawY = (this.expandedBounds.bottom - expandedTextHeight) + this.textPaint.descent();
                break;
            default:
                float textOffset2 = expandedTextHeight / 2.0f;
                this.expandedDrawY = this.expandedBounds.centerY() - textOffset2;
                break;
        }
        switch (8388615 & expandedAbsGravity) {
            case 1:
                this.expandedDrawX = this.expandedBounds.centerX() - (width2 / 2.0f);
                break;
            case 5:
                this.expandedDrawX = this.expandedBounds.right - width2;
                break;
            default:
                this.expandedDrawX = this.expandedBounds.left;
                break;
        }
        clearTexture();
        setInterpolatedTextSize(currentTextSize);
    }

    private void interpolateBounds(float fraction) {
        if (this.fadeModeEnabled) {
            this.currentBounds.set(fraction < this.fadeModeThresholdFraction ? this.expandedBounds : this.collapsedBounds);
            return;
        }
        this.currentBounds.left = lerp(this.expandedBounds.left, this.collapsedBounds.left, fraction, this.positionInterpolator);
        this.currentBounds.top = lerp(this.expandedDrawY, this.collapsedDrawY, fraction, this.positionInterpolator);
        this.currentBounds.right = lerp(this.expandedBounds.right, this.collapsedBounds.right, fraction, this.positionInterpolator);
        this.currentBounds.bottom = lerp(this.expandedBounds.bottom, this.collapsedBounds.bottom, fraction, this.positionInterpolator);
    }

    private void setCollapsedTextBlend(float blend) {
        this.collapsedTextBlend = blend;
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private void setExpandedTextBlend(float blend) {
        this.expandedTextBlend = blend;
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    public void draw(@NonNull Canvas canvas) {
        boolean drawTexture = true;
        int saveCount = canvas.save();
        if (this.textToDraw != null && this.drawTitle) {
            float firstLineX = this.maxLines > 1 ? this.textLayout.getLineStart(0) : this.textLayout.getLineLeft(0);
            float currentExpandedX = (this.currentDrawX + firstLineX) - (this.expandedFirstLineDrawX * 2.0f);
            this.textPaint.setTextSize(this.currentTextSize);
            float x = this.currentDrawX;
            float y = this.currentDrawY;
            if (!this.useTexture || this.expandedTitleTexture == null) {
                drawTexture = false;
            }
            if (this.scale != 1.0f && !this.fadeModeEnabled) {
                canvas.scale(this.scale, this.scale, x, y);
            }
            if (drawTexture) {
                canvas.drawBitmap(this.expandedTitleTexture, x, y, this.texturePaint);
                canvas.restoreToCount(saveCount);
                return;
            }
            if (shouldDrawMultiline() && (!this.fadeModeEnabled || this.expandedFraction > this.fadeModeThresholdFraction)) {
                drawMultilineTransition(canvas, currentExpandedX, y);
            } else {
                canvas.translate(x, y);
                this.textLayout.draw(canvas);
            }
            canvas.restoreToCount(saveCount);
        }
    }

    private boolean shouldDrawMultiline() {
        return this.maxLines > 1 && (!this.isRtl || this.fadeModeEnabled) && !this.useTexture;
    }

    private void drawMultilineTransition(@NonNull Canvas canvas, float currentExpandedX, float y) {
        int originalAlpha = this.textPaint.getAlpha();
        canvas.translate(currentExpandedX, y);
        this.textPaint.setAlpha((int) (this.expandedTextBlend * originalAlpha));
        this.textLayout.draw(canvas);
        this.textPaint.setAlpha((int) (this.collapsedTextBlend * originalAlpha));
        int lineBaseline = this.textLayout.getLineBaseline(0);
        canvas.drawText(this.textToDrawCollapsed, 0, this.textToDrawCollapsed.length(), 0.0f, lineBaseline, this.textPaint);
        if (!this.fadeModeEnabled) {
            String tmp = this.textToDrawCollapsed.toString().trim();
            if (tmp.endsWith(ELLIPSIS_NORMAL)) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            this.textPaint.setAlpha(originalAlpha);
            canvas.drawText(tmp, 0, Math.min(this.textLayout.getLineEnd(0), tmp.length()), 0.0f, lineBaseline, (Paint) this.textPaint);
        }
    }

    private boolean calculateIsRtl(@NonNull CharSequence text) {
        boolean defaultIsRtl = isDefaultIsRtl();
        if (!this.isRtlTextDirectionHeuristicsEnabled) {
            return defaultIsRtl;
        }
        return isTextDirectionHeuristicsIsRtl(text, defaultIsRtl);
    }

    private boolean isDefaultIsRtl() {
        return ViewCompat.getLayoutDirection(this.view) == 1;
    }

    private boolean isTextDirectionHeuristicsIsRtl(@NonNull CharSequence text, boolean defaultIsRtl) {
        return (defaultIsRtl ? TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL : TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR).isRtl(text, 0, text.length());
    }

    private void setInterpolatedTextSize(float textSize) {
        calculateUsingTextSize(textSize);
        this.useTexture = USE_SCALING_TEXTURE && this.scale != 1.0f;
        if (this.useTexture) {
            ensureExpandedTexture();
        }
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private void calculateUsingTextSize(float textSize) {
        calculateUsingTextSize(textSize, false);
    }

    private void calculateUsingTextSize(float textSize, boolean forceRecalculate) {
        float newTextSize;
        float availableWidth;
        if (this.text != null) {
            float collapsedWidth = this.collapsedBounds.width();
            float expandedWidth = this.expandedBounds.width();
            boolean updateDrawText = false;
            if (isClose(textSize, this.collapsedTextSize)) {
                newTextSize = this.collapsedTextSize;
                this.scale = 1.0f;
                if (this.currentTypeface != this.collapsedTypeface) {
                    this.currentTypeface = this.collapsedTypeface;
                    updateDrawText = true;
                }
                availableWidth = collapsedWidth;
            } else {
                newTextSize = this.expandedTextSize;
                if (this.currentTypeface != this.expandedTypeface) {
                    this.currentTypeface = this.expandedTypeface;
                    updateDrawText = true;
                }
                if (isClose(textSize, this.expandedTextSize)) {
                    this.scale = 1.0f;
                } else {
                    this.scale = textSize / this.expandedTextSize;
                }
                float textSizeRatio = this.collapsedTextSize / this.expandedTextSize;
                float scaledDownWidth = expandedWidth * textSizeRatio;
                if (forceRecalculate) {
                    availableWidth = expandedWidth;
                } else {
                    availableWidth = scaledDownWidth > collapsedWidth ? Math.min(collapsedWidth / textSizeRatio, expandedWidth) : expandedWidth;
                }
            }
            if (availableWidth > 0.0f) {
                updateDrawText = this.currentTextSize != newTextSize || this.boundsChanged || updateDrawText;
                this.currentTextSize = newTextSize;
                this.boundsChanged = false;
            }
            if (this.textToDraw == null || updateDrawText) {
                this.textPaint.setTextSize(this.currentTextSize);
                this.textPaint.setTypeface(this.currentTypeface);
                this.textPaint.setLinearText(this.scale != 1.0f);
                this.isRtl = calculateIsRtl(this.text);
                this.textLayout = createStaticLayout(shouldDrawMultiline() ? this.maxLines : 1, availableWidth, this.isRtl);
                this.textToDraw = this.textLayout.getText();
            }
        }
    }

    private StaticLayout createStaticLayout(int maxLines, float availableWidth, boolean isRtl) {
        StaticLayout textLayout = null;
        try {
            textLayout = StaticLayoutBuilderCompat.obtain(this.text, this.textPaint, (int) availableWidth).setEllipsize(TextUtils.TruncateAt.END).setIsRtl(isRtl).setAlignment(Layout.Alignment.ALIGN_NORMAL).setIncludePad(false).setMaxLines(maxLines).setLineSpacing(this.lineSpacingAdd, this.lineSpacingMultiplier).setHyphenationFrequency(this.hyphenationFrequency).build();
        } catch (StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException e) {
            Log.e(TAG, e.getCause().getMessage(), e);
        }
        return (StaticLayout) Preconditions.checkNotNull(textLayout);
    }

    private void ensureExpandedTexture() {
        if (this.expandedTitleTexture == null && !this.expandedBounds.isEmpty() && !TextUtils.isEmpty(this.textToDraw)) {
            calculateOffsets(0.0f);
            int width = this.textLayout.getWidth();
            int height = this.textLayout.getHeight();
            if (width > 0 && height > 0) {
                this.expandedTitleTexture = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(this.expandedTitleTexture);
                this.textLayout.draw(c);
                if (this.texturePaint == null) {
                    this.texturePaint = new Paint(3);
                }
            }
        }
    }

    public void recalculate() {
        recalculate(false);
    }

    public void recalculate(boolean forceRecalculate) {
        if ((this.view.getHeight() > 0 && this.view.getWidth() > 0) || forceRecalculate) {
            calculateBaseOffsets(forceRecalculate);
            calculateCurrentOffsets();
        }
    }

    public void setText(@Nullable CharSequence text) {
        if (text == null || !TextUtils.equals(this.text, text)) {
            this.text = text;
            this.textToDraw = null;
            clearTexture();
            recalculate();
        }
    }

    @Nullable
    public CharSequence getText() {
        return this.text;
    }

    private void clearTexture() {
        if (this.expandedTitleTexture != null) {
            this.expandedTitleTexture.recycle();
            this.expandedTitleTexture = null;
        }
    }

    public void setMaxLines(int maxLines) {
        if (maxLines != this.maxLines) {
            this.maxLines = maxLines;
            clearTexture();
            recalculate();
        }
    }

    public int getMaxLines() {
        return this.maxLines;
    }

    public int getLineCount() {
        if (this.textLayout != null) {
            return this.textLayout.getLineCount();
        }
        return 0;
    }

    @RequiresApi(23)
    public void setLineSpacingAdd(float spacingAdd) {
        this.lineSpacingAdd = spacingAdd;
    }

    @RequiresApi(23)
    public float getLineSpacingAdd() {
        return this.textLayout.getSpacingAdd();
    }

    @RequiresApi(23)
    public void setLineSpacingMultiplier(@FloatRange(from = 0.0d) float spacingMultiplier) {
        this.lineSpacingMultiplier = spacingMultiplier;
    }

    @RequiresApi(23)
    public float getLineSpacingMultiplier() {
        return this.textLayout.getSpacingMultiplier();
    }

    @RequiresApi(23)
    public void setHyphenationFrequency(int hyphenationFrequency) {
        this.hyphenationFrequency = hyphenationFrequency;
    }

    @RequiresApi(23)
    public int getHyphenationFrequency() {
        return this.hyphenationFrequency;
    }

    private static boolean isClose(float value, float targetValue) {
        return Math.abs(value - targetValue) < 0.001f;
    }

    public ColorStateList getExpandedTextColor() {
        return this.expandedTextColor;
    }

    public ColorStateList getCollapsedTextColor() {
        return this.collapsedTextColor;
    }

    private static int blendColors(int color1, int color2, float ratio) {
        float inverseRatio = 1.0f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

    private static float lerp(float startValue, float endValue, float fraction, @Nullable TimeInterpolator interpolator) {
        if (interpolator != null) {
            fraction = interpolator.getInterpolation(fraction);
        }
        return AnimationUtils.lerp(startValue, endValue, fraction);
    }

    private static boolean rectEquals(@NonNull Rect r, int left, int top, int right, int bottom) {
        return r.left == left && r.top == top && r.right == right && r.bottom == bottom;
    }
}
