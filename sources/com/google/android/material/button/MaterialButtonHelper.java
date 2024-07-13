package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleDrawableCompat;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;

/* JADX INFO: Access modifiers changed from: package-private */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class MaterialButtonHelper {
    private static final boolean IS_LOLLIPOP;
    @Nullable
    private ColorStateList backgroundTint;
    @Nullable
    private PorterDuff.Mode backgroundTintMode;
    private boolean checkable;
    private int cornerRadius;
    private int elevation;
    private int insetBottom;
    private int insetLeft;
    private int insetRight;
    private int insetTop;
    @Nullable
    private Drawable maskDrawable;
    private final MaterialButton materialButton;
    @Nullable
    private ColorStateList rippleColor;
    private LayerDrawable rippleDrawable;
    @NonNull
    private ShapeAppearanceModel shapeAppearanceModel;
    @Nullable
    private ColorStateList strokeColor;
    private int strokeWidth;
    private boolean shouldDrawSurfaceColorStroke = false;
    private boolean backgroundOverwritten = false;
    private boolean cornerRadiusSet = false;

    static {
        IS_LOLLIPOP = Build.VERSION.SDK_INT >= 21;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MaterialButtonHelper(MaterialButton button, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.materialButton = button;
        this.shapeAppearanceModel = shapeAppearanceModel;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void loadFromAttributes(@NonNull TypedArray attributes) {
        this.insetLeft = attributes.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetLeft, 0);
        this.insetRight = attributes.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetRight, 0);
        this.insetTop = attributes.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetTop, 0);
        this.insetBottom = attributes.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetBottom, 0);
        if (attributes.hasValue(R.styleable.MaterialButton_cornerRadius)) {
            this.cornerRadius = attributes.getDimensionPixelSize(R.styleable.MaterialButton_cornerRadius, -1);
            setShapeAppearanceModel(this.shapeAppearanceModel.withCornerSize(this.cornerRadius));
            this.cornerRadiusSet = true;
        }
        this.strokeWidth = attributes.getDimensionPixelSize(R.styleable.MaterialButton_strokeWidth, 0);
        this.backgroundTintMode = ViewUtils.parseTintMode(attributes.getInt(R.styleable.MaterialButton_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.backgroundTint = MaterialResources.getColorStateList(this.materialButton.getContext(), attributes, R.styleable.MaterialButton_backgroundTint);
        this.strokeColor = MaterialResources.getColorStateList(this.materialButton.getContext(), attributes, R.styleable.MaterialButton_strokeColor);
        this.rippleColor = MaterialResources.getColorStateList(this.materialButton.getContext(), attributes, R.styleable.MaterialButton_rippleColor);
        this.checkable = attributes.getBoolean(R.styleable.MaterialButton_android_checkable, false);
        this.elevation = attributes.getDimensionPixelSize(R.styleable.MaterialButton_elevation, 0);
        int paddingStart = ViewCompat.getPaddingStart(this.materialButton);
        int paddingTop = this.materialButton.getPaddingTop();
        int paddingEnd = ViewCompat.getPaddingEnd(this.materialButton);
        int paddingBottom = this.materialButton.getPaddingBottom();
        if (attributes.hasValue(R.styleable.MaterialButton_android_background)) {
            setBackgroundOverwritten();
        } else {
            updateBackground();
        }
        ViewCompat.setPaddingRelative(this.materialButton, this.insetLeft + paddingStart, this.insetTop + paddingTop, this.insetRight + paddingEnd, this.insetBottom + paddingBottom);
    }

    private void updateBackground() {
        this.materialButton.setInternalBackground(createBackground());
        MaterialShapeDrawable materialShapeDrawable = getMaterialShapeDrawable();
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation(this.elevation);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBackgroundOverwritten() {
        this.backgroundOverwritten = true;
        this.materialButton.setSupportBackgroundTintList(this.backgroundTint);
        this.materialButton.setSupportBackgroundTintMode(this.backgroundTintMode);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isBackgroundOverwritten() {
        return this.backgroundOverwritten;
    }

    @NonNull
    private InsetDrawable wrapDrawableWithInset(Drawable drawable) {
        return new InsetDrawable(drawable, this.insetLeft, this.insetTop, this.insetRight, this.insetBottom);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSupportBackgroundTintList(@Nullable ColorStateList tintList) {
        if (this.backgroundTint != tintList) {
            this.backgroundTint = tintList;
            if (getMaterialShapeDrawable() != null) {
                DrawableCompat.setTintList(getMaterialShapeDrawable(), this.backgroundTint);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ColorStateList getSupportBackgroundTintList() {
        return this.backgroundTint;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.backgroundTintMode != mode) {
            this.backgroundTintMode = mode;
            if (getMaterialShapeDrawable() != null && this.backgroundTintMode != null) {
                DrawableCompat.setTintMode(getMaterialShapeDrawable(), this.backgroundTintMode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return this.backgroundTintMode;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setShouldDrawSurfaceColorStroke(boolean shouldDrawSurfaceColorStroke) {
        this.shouldDrawSurfaceColorStroke = shouldDrawSurfaceColorStroke;
        updateStroke();
    }

    private Drawable createBackground() {
        MaterialShapeDrawable backgroundDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
        Context context = this.materialButton.getContext();
        backgroundDrawable.initializeElevationOverlay(context);
        DrawableCompat.setTintList(backgroundDrawable, this.backgroundTint);
        if (this.backgroundTintMode != null) {
            DrawableCompat.setTintMode(backgroundDrawable, this.backgroundTintMode);
        }
        backgroundDrawable.setStroke(this.strokeWidth, this.strokeColor);
        MaterialShapeDrawable surfaceColorStrokeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
        surfaceColorStrokeDrawable.setTint(0);
        surfaceColorStrokeDrawable.setStroke(this.strokeWidth, this.shouldDrawSurfaceColorStroke ? MaterialColors.getColor(this.materialButton, R.attr.colorSurface) : 0);
        if (IS_LOLLIPOP) {
            this.maskDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
            DrawableCompat.setTint(this.maskDrawable, -1);
            this.rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.rippleColor), wrapDrawableWithInset(new LayerDrawable(new Drawable[]{surfaceColorStrokeDrawable, backgroundDrawable})), this.maskDrawable);
            return this.rippleDrawable;
        }
        this.maskDrawable = new RippleDrawableCompat(this.shapeAppearanceModel);
        DrawableCompat.setTintList(this.maskDrawable, RippleUtils.sanitizeRippleDrawableColor(this.rippleColor));
        this.rippleDrawable = new LayerDrawable(new Drawable[]{surfaceColorStrokeDrawable, backgroundDrawable, this.maskDrawable});
        return wrapDrawableWithInset(this.rippleDrawable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateMaskBounds(int height, int width) {
        if (this.maskDrawable != null) {
            this.maskDrawable.setBounds(this.insetLeft, this.insetTop, width - this.insetRight, height - this.insetBottom);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBackgroundColor(int color) {
        if (getMaterialShapeDrawable() != null) {
            getMaterialShapeDrawable().setTint(color);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRippleColor(@Nullable ColorStateList rippleColor) {
        if (this.rippleColor != rippleColor) {
            this.rippleColor = rippleColor;
            if (IS_LOLLIPOP && (this.materialButton.getBackground() instanceof RippleDrawable)) {
                ((RippleDrawable) this.materialButton.getBackground()).setColor(RippleUtils.sanitizeRippleDrawableColor(rippleColor));
            } else if (!IS_LOLLIPOP && (this.materialButton.getBackground() instanceof RippleDrawableCompat)) {
                ((RippleDrawableCompat) this.materialButton.getBackground()).setTintList(RippleUtils.sanitizeRippleDrawableColor(rippleColor));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList getRippleColor() {
        return this.rippleColor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setStrokeColor(@Nullable ColorStateList strokeColor) {
        if (this.strokeColor != strokeColor) {
            this.strokeColor = strokeColor;
            updateStroke();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList getStrokeColor() {
        return this.strokeColor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setStrokeWidth(int strokeWidth) {
        if (this.strokeWidth != strokeWidth) {
            this.strokeWidth = strokeWidth;
            updateStroke();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    private void updateStroke() {
        MaterialShapeDrawable materialShapeDrawable = getMaterialShapeDrawable();
        MaterialShapeDrawable surfaceColorStrokeDrawable = getSurfaceColorStrokeDrawable();
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setStroke(this.strokeWidth, this.strokeColor);
            if (surfaceColorStrokeDrawable != null) {
                surfaceColorStrokeDrawable.setStroke(this.strokeWidth, this.shouldDrawSurfaceColorStroke ? MaterialColors.getColor(this.materialButton, R.attr.colorSurface) : 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCornerRadius(int cornerRadius) {
        if (!this.cornerRadiusSet || this.cornerRadius != cornerRadius) {
            this.cornerRadius = cornerRadius;
            this.cornerRadiusSet = true;
            setShapeAppearanceModel(this.shapeAppearanceModel.withCornerSize(cornerRadius));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getCornerRadius() {
        return this.cornerRadius;
    }

    @Nullable
    private MaterialShapeDrawable getMaterialShapeDrawable(boolean getSurfaceColorStrokeDrawable) {
        if (this.rippleDrawable != null && this.rippleDrawable.getNumberOfLayers() > 0) {
            if (IS_LOLLIPOP) {
                InsetDrawable insetDrawable = (InsetDrawable) this.rippleDrawable.getDrawable(0);
                LayerDrawable layerDrawable = (LayerDrawable) insetDrawable.getDrawable();
                return (MaterialShapeDrawable) layerDrawable.getDrawable(getSurfaceColorStrokeDrawable ? 0 : 1);
            }
            return (MaterialShapeDrawable) this.rippleDrawable.getDrawable(getSurfaceColorStrokeDrawable ? 0 : 1);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public MaterialShapeDrawable getMaterialShapeDrawable() {
        return getMaterialShapeDrawable(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isCheckable() {
        return this.checkable;
    }

    @Nullable
    private MaterialShapeDrawable getSurfaceColorStrokeDrawable() {
        return getMaterialShapeDrawable(true);
    }

    private void updateButtonShape(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        if (getMaterialShapeDrawable() != null) {
            getMaterialShapeDrawable().setShapeAppearanceModel(shapeAppearanceModel);
        }
        if (getSurfaceColorStrokeDrawable() != null) {
            getSurfaceColorStrokeDrawable().setShapeAppearanceModel(shapeAppearanceModel);
        }
        if (getMaskDrawable() != null) {
            getMaskDrawable().setShapeAppearanceModel(shapeAppearanceModel);
        }
    }

    @Nullable
    public Shapeable getMaskDrawable() {
        if (this.rippleDrawable != null && this.rippleDrawable.getNumberOfLayers() > 1) {
            if (this.rippleDrawable.getNumberOfLayers() > 2) {
                return (Shapeable) this.rippleDrawable.getDrawable(2);
            }
            return (Shapeable) this.rippleDrawable.getDrawable(1);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        updateButtonShape(shapeAppearanceModel);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    public void setInsetBottom(@Dimension int newInsetBottom) {
        setVerticalInsets(this.insetTop, newInsetBottom);
    }

    public int getInsetBottom() {
        return this.insetBottom;
    }

    public void setInsetTop(@Dimension int newInsetTop) {
        setVerticalInsets(newInsetTop, this.insetBottom);
    }

    private void setVerticalInsets(@Dimension int newInsetTop, @Dimension int newInsetBottom) {
        int paddingStart = ViewCompat.getPaddingStart(this.materialButton);
        int paddingTop = this.materialButton.getPaddingTop();
        int paddingEnd = ViewCompat.getPaddingEnd(this.materialButton);
        int paddingBottom = this.materialButton.getPaddingBottom();
        int oldInsetTop = this.insetTop;
        int oldInsetBottom = this.insetBottom;
        this.insetBottom = newInsetBottom;
        this.insetTop = newInsetTop;
        if (!this.backgroundOverwritten) {
            updateBackground();
        }
        ViewCompat.setPaddingRelative(this.materialButton, paddingStart, (paddingTop + newInsetTop) - oldInsetTop, paddingEnd, (paddingBottom + newInsetBottom) - oldInsetBottom);
    }

    public int getInsetTop() {
        return this.insetTop;
    }
}
