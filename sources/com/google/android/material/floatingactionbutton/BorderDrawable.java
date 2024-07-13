package com.google.android.material.floatingactionbutton;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class BorderDrawable extends Drawable {
    private static final float DRAW_STROKE_WIDTH_MULTIPLE = 1.3333f;
    @Nullable
    private ColorStateList borderTint;
    @Dimension
    float borderWidth;
    @ColorInt
    private int bottomInnerStrokeColor;
    @ColorInt
    private int bottomOuterStrokeColor;
    @ColorInt
    private int currentBorderTintColor;
    private ShapeAppearanceModel shapeAppearanceModel;
    @ColorInt
    private int topInnerStrokeColor;
    @ColorInt
    private int topOuterStrokeColor;
    private final ShapeAppearancePathProvider pathProvider = ShapeAppearancePathProvider.getInstance();
    private final Path shapePath = new Path();
    private final Rect rect = new Rect();
    private final RectF rectF = new RectF();
    private final RectF boundsRectF = new RectF();
    private final BorderState state = new BorderState();
    private boolean invalidateShader = true;
    @NonNull
    private final Paint paint = new Paint(1);

    public BorderDrawable(ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        this.paint.setStyle(Paint.Style.STROKE);
    }

    public void setBorderWidth(@Dimension float width) {
        if (this.borderWidth != width) {
            this.borderWidth = width;
            this.paint.setStrokeWidth(DRAW_STROKE_WIDTH_MULTIPLE * width);
            this.invalidateShader = true;
            invalidateSelf();
        }
    }

    public void setBorderTint(@Nullable ColorStateList tint) {
        if (tint != null) {
            this.currentBorderTintColor = tint.getColorForState(getState(), this.currentBorderTintColor);
        }
        this.borderTint = tint;
        this.invalidateShader = true;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setGradientColors(@ColorInt int topOuterStrokeColor, @ColorInt int topInnerStrokeColor, @ColorInt int bottomOuterStrokeColor, @ColorInt int bottomInnerStrokeColor) {
        this.topOuterStrokeColor = topOuterStrokeColor;
        this.topInnerStrokeColor = topInnerStrokeColor;
        this.bottomOuterStrokeColor = bottomOuterStrokeColor;
        this.bottomInnerStrokeColor = bottomInnerStrokeColor;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        if (this.invalidateShader) {
            this.paint.setShader(createGradientShader());
            this.invalidateShader = false;
        }
        float halfBorderWidth = this.paint.getStrokeWidth() / 2.0f;
        copyBounds(this.rect);
        this.rectF.set(this.rect);
        float cornerSize = this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(getBoundsAsRectF());
        float radius = Math.min(cornerSize, this.rectF.width() / 2.0f);
        if (this.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            this.rectF.inset(halfBorderWidth, halfBorderWidth);
            canvas.drawRoundRect(this.rectF, radius, radius, this.paint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    @TargetApi(21)
    public void getOutline(@NonNull Outline outline) {
        if (this.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            float radius = this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(getBoundsAsRectF());
            outline.setRoundRect(getBounds(), radius);
            return;
        }
        copyBounds(this.rect);
        this.rectF.set(this.rect);
        this.pathProvider.calculatePath(this.shapeAppearanceModel, 1.0f, this.rectF, this.shapePath);
        if (this.shapePath.isConvex()) {
            outline.setConvexPath(this.shapePath);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(@NonNull Rect padding) {
        if (this.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            int borderWidth = Math.round(this.borderWidth);
            padding.set(borderWidth, borderWidth, borderWidth, borderWidth);
            return true;
        }
        return true;
    }

    @NonNull
    protected RectF getBoundsAsRectF() {
        this.boundsRectF.set(getBounds());
        return this.boundsRectF;
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        this.paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.borderWidth > 0.0f ? -3 : -2;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        this.invalidateShader = true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return (this.borderTint != null && this.borderTint.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] state) {
        int newColor;
        if (this.borderTint != null && (newColor = this.borderTint.getColorForState(state, this.currentBorderTintColor)) != this.currentBorderTintColor) {
            this.invalidateShader = true;
            this.currentBorderTintColor = newColor;
        }
        if (this.invalidateShader) {
            invalidateSelf();
        }
        return this.invalidateShader;
    }

    @NonNull
    private Shader createGradientShader() {
        Rect rect = this.rect;
        copyBounds(rect);
        float borderRatio = this.borderWidth / rect.height();
        int[] colors = {ColorUtils.compositeColors(this.topOuterStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(this.topInnerStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.topInnerStrokeColor, 0), this.currentBorderTintColor), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.bottomInnerStrokeColor, 0), this.currentBorderTintColor), ColorUtils.compositeColors(this.bottomInnerStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(this.bottomOuterStrokeColor, this.currentBorderTintColor)};
        float[] positions = {0.0f, borderRatio, 0.5f, 0.5f, 1.0f - borderRatio, 1.0f};
        return new LinearGradient(0.0f, rect.top, 0.0f, rect.bottom, colors, positions, Shader.TileMode.CLAMP);
    }

    @Override // android.graphics.drawable.Drawable
    @Nullable
    public Drawable.ConstantState getConstantState() {
        return this.state;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class BorderState extends Drawable.ConstantState {
        private BorderState() {
            BorderDrawable.this = r1;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return BorderDrawable.this;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }
    }
}
