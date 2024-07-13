package com.google.android.material.card;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;

/* JADX INFO: Access modifiers changed from: package-private */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class MaterialCardViewHelper {
    private static final float CARD_VIEW_SHADOW_MULTIPLIER = 1.5f;
    private static final int CHECKED_ICON_LAYER_INDEX = 2;
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    private static final int DEFAULT_STROKE_VALUE = -1;
    @NonNull
    private final MaterialShapeDrawable bgDrawable;
    private boolean checkable;
    @Nullable
    private Drawable checkedIcon;
    @Dimension
    private int checkedIconMargin;
    @Dimension
    private int checkedIconSize;
    @Nullable
    private ColorStateList checkedIconTint;
    @Nullable
    private LayerDrawable clickableForegroundDrawable;
    @Nullable
    private MaterialShapeDrawable compatRippleDrawable;
    @Nullable
    private Drawable fgDrawable;
    @NonNull
    private final MaterialShapeDrawable foregroundContentDrawable;
    @Nullable
    private MaterialShapeDrawable foregroundShapeDrawable;
    @NonNull
    private final MaterialCardView materialCardView;
    @Nullable
    private ColorStateList rippleColor;
    @Nullable
    private Drawable rippleDrawable;
    @Nullable
    private ShapeAppearanceModel shapeAppearanceModel;
    @Nullable
    private ColorStateList strokeColor;
    @Dimension
    private int strokeWidth;
    @NonNull
    private final Rect userContentPadding = new Rect();
    private boolean isBackgroundOverwritten = false;

    public MaterialCardViewHelper(@NonNull MaterialCardView card, AttributeSet attrs, int defStyleAttr, @StyleRes int defStyleRes) {
        this.materialCardView = card;
        this.bgDrawable = new MaterialShapeDrawable(card.getContext(), attrs, defStyleAttr, defStyleRes);
        this.bgDrawable.initializeElevationOverlay(card.getContext());
        this.bgDrawable.setShadowColor(-12303292);
        ShapeAppearanceModel.Builder shapeAppearanceModelBuilder = this.bgDrawable.getShapeAppearanceModel().toBuilder();
        TypedArray cardViewAttributes = card.getContext().obtainStyledAttributes(attrs, R.styleable.CardView, defStyleAttr, R.style.CardView);
        if (cardViewAttributes.hasValue(R.styleable.CardView_cardCornerRadius)) {
            shapeAppearanceModelBuilder.setAllCornerSizes(cardViewAttributes.getDimension(R.styleable.CardView_cardCornerRadius, 0.0f));
        }
        this.foregroundContentDrawable = new MaterialShapeDrawable();
        setShapeAppearanceModel(shapeAppearanceModelBuilder.build());
        cardViewAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void loadFromAttributes(@NonNull TypedArray attributes) {
        this.strokeColor = MaterialResources.getColorStateList(this.materialCardView.getContext(), attributes, R.styleable.MaterialCardView_strokeColor);
        if (this.strokeColor == null) {
            this.strokeColor = ColorStateList.valueOf(-1);
        }
        this.strokeWidth = attributes.getDimensionPixelSize(R.styleable.MaterialCardView_strokeWidth, 0);
        this.checkable = attributes.getBoolean(R.styleable.MaterialCardView_android_checkable, false);
        this.materialCardView.setLongClickable(this.checkable);
        this.checkedIconTint = MaterialResources.getColorStateList(this.materialCardView.getContext(), attributes, R.styleable.MaterialCardView_checkedIconTint);
        setCheckedIcon(MaterialResources.getDrawable(this.materialCardView.getContext(), attributes, R.styleable.MaterialCardView_checkedIcon));
        setCheckedIconSize(attributes.getDimensionPixelSize(R.styleable.MaterialCardView_checkedIconSize, 0));
        setCheckedIconMargin(attributes.getDimensionPixelSize(R.styleable.MaterialCardView_checkedIconMargin, 0));
        this.rippleColor = MaterialResources.getColorStateList(this.materialCardView.getContext(), attributes, R.styleable.MaterialCardView_rippleColor);
        if (this.rippleColor == null) {
            this.rippleColor = ColorStateList.valueOf(MaterialColors.getColor(this.materialCardView, R.attr.colorControlHighlight));
        }
        ColorStateList foregroundColor = MaterialResources.getColorStateList(this.materialCardView.getContext(), attributes, R.styleable.MaterialCardView_cardForegroundColor);
        setCardForegroundColor(foregroundColor);
        updateRippleColor();
        updateElevation();
        updateStroke();
        this.materialCardView.setBackgroundInternal(insetDrawable(this.bgDrawable));
        this.fgDrawable = this.materialCardView.isClickable() ? getClickableForeground() : this.foregroundContentDrawable;
        this.materialCardView.setForeground(insetDrawable(this.fgDrawable));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isBackgroundOverwritten() {
        return this.isBackgroundOverwritten;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBackgroundOverwritten(boolean isBackgroundOverwritten) {
        this.isBackgroundOverwritten = isBackgroundOverwritten;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setStrokeColor(ColorStateList strokeColor) {
        if (this.strokeColor != strokeColor) {
            this.strokeColor = strokeColor;
            updateStroke();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @ColorInt
    public int getStrokeColor() {
        if (this.strokeColor == null) {
            return -1;
        }
        return this.strokeColor.getDefaultColor();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList getStrokeColorStateList() {
        return this.strokeColor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setStrokeWidth(@Dimension int strokeWidth) {
        if (strokeWidth != this.strokeWidth) {
            this.strokeWidth = strokeWidth;
            updateStroke();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Dimension
    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public MaterialShapeDrawable getBackground() {
        return this.bgDrawable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCardBackgroundColor(ColorStateList color) {
        this.bgDrawable.setFillColor(color);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ColorStateList getCardBackgroundColor() {
        return this.bgDrawable.getFillColor();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCardForegroundColor(@Nullable ColorStateList foregroundColor) {
        MaterialShapeDrawable materialShapeDrawable = this.foregroundContentDrawable;
        if (foregroundColor == null) {
            foregroundColor = ColorStateList.valueOf(0);
        }
        materialShapeDrawable.setFillColor(foregroundColor);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ColorStateList getCardForegroundColor() {
        return this.foregroundContentDrawable.getFillColor();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setUserContentPadding(int left, int top, int right, int bottom) {
        this.userContentPadding.set(left, top, right, bottom);
        updateContentPadding();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public Rect getUserContentPadding() {
        return this.userContentPadding;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateClickable() {
        Drawable previousFgDrawable = this.fgDrawable;
        this.fgDrawable = this.materialCardView.isClickable() ? getClickableForeground() : this.foregroundContentDrawable;
        if (previousFgDrawable != this.fgDrawable) {
            updateInsetForeground(this.fgDrawable);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCornerRadius(float cornerRadius) {
        setShapeAppearanceModel(this.shapeAppearanceModel.withCornerSize(cornerRadius));
        this.fgDrawable.invalidateSelf();
        if (shouldAddCornerPaddingOutsideCardBackground() || shouldAddCornerPaddingInsideCardBackground()) {
            updateContentPadding();
        }
        if (shouldAddCornerPaddingOutsideCardBackground()) {
            updateInsets();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getCornerRadius() {
        return this.bgDrawable.getTopLeftCornerResolvedSize();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        this.bgDrawable.setInterpolation(progress);
        if (this.foregroundContentDrawable != null) {
            this.foregroundContentDrawable.setInterpolation(progress);
        }
        if (this.foregroundShapeDrawable != null) {
            this.foregroundShapeDrawable.setInterpolation(progress);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @FloatRange(from = COS_45, to = 1.0d)
    public float getProgress() {
        return this.bgDrawable.getInterpolation();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateElevation() {
        this.bgDrawable.setElevation(this.materialCardView.getCardElevation());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateInsets() {
        if (!isBackgroundOverwritten()) {
            this.materialCardView.setBackgroundInternal(insetDrawable(this.bgDrawable));
        }
        this.materialCardView.setForeground(insetDrawable(this.fgDrawable));
    }

    void updateStroke() {
        this.foregroundContentDrawable.setStroke(this.strokeWidth, this.strokeColor);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateContentPadding() {
        boolean includeCornerPadding = shouldAddCornerPaddingInsideCardBackground() || shouldAddCornerPaddingOutsideCardBackground();
        int contentPaddingOffset = (int) ((includeCornerPadding ? calculateActualCornerPadding() : 0.0f) - getParentCardViewCalculatedCornerPadding());
        this.materialCardView.setAncestorContentPadding(this.userContentPadding.left + contentPaddingOffset, this.userContentPadding.top + contentPaddingOffset, this.userContentPadding.right + contentPaddingOffset, this.userContentPadding.bottom + contentPaddingOffset);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isCheckable() {
        return this.checkable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRippleColor(@Nullable ColorStateList rippleColor) {
        this.rippleColor = rippleColor;
        updateRippleColor();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCheckedIconTint(@Nullable ColorStateList checkedIconTint) {
        this.checkedIconTint = checkedIconTint;
        if (this.checkedIcon != null) {
            DrawableCompat.setTintList(this.checkedIcon, checkedIconTint);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList getCheckedIconTint() {
        return this.checkedIconTint;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList getRippleColor() {
        return this.rippleColor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Drawable getCheckedIcon() {
        return this.checkedIcon;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCheckedIcon(@Nullable Drawable checkedIcon) {
        this.checkedIcon = checkedIcon;
        if (checkedIcon != null) {
            this.checkedIcon = DrawableCompat.wrap(checkedIcon.mutate());
            DrawableCompat.setTintList(this.checkedIcon, this.checkedIconTint);
        }
        if (this.clickableForegroundDrawable != null) {
            Drawable checkedLayer = createCheckedIconLayer();
            this.clickableForegroundDrawable.setDrawableByLayerId(R.id.mtrl_card_checked_layer_id, checkedLayer);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Dimension
    public int getCheckedIconSize() {
        return this.checkedIconSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCheckedIconSize(@Dimension int checkedIconSize) {
        this.checkedIconSize = checkedIconSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Dimension
    public int getCheckedIconMargin() {
        return this.checkedIconMargin;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCheckedIconMargin(@Dimension int checkedIconMargin) {
        this.checkedIconMargin = checkedIconMargin;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onMeasure(int measuredWidth, int measuredHeight) {
        if (this.clickableForegroundDrawable != null) {
            int left = (measuredWidth - this.checkedIconMargin) - this.checkedIconSize;
            int bottom = (measuredHeight - this.checkedIconMargin) - this.checkedIconSize;
            boolean isPreLollipop = Build.VERSION.SDK_INT < 21;
            if (isPreLollipop || this.materialCardView.getUseCompatPadding()) {
                bottom -= (int) Math.ceil(calculateVerticalBackgroundPadding() * 2.0f);
                left -= (int) Math.ceil(calculateHorizontalBackgroundPadding() * 2.0f);
            }
            int right = this.checkedIconMargin;
            if (ViewCompat.getLayoutDirection(this.materialCardView) == 1) {
                right = left;
                left = right;
            }
            this.clickableForegroundDrawable.setLayerInset(2, left, this.checkedIconMargin, right, bottom);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(api = 23)
    public void forceRippleRedraw() {
        if (this.rippleDrawable != null) {
            Rect bounds = this.rippleDrawable.getBounds();
            int bottom = bounds.bottom;
            this.rippleDrawable.setBounds(bounds.left, bounds.top, bounds.right, bottom - 1);
            this.rippleDrawable.setBounds(bounds.left, bounds.top, bounds.right, bottom);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        this.bgDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        this.bgDrawable.setShadowBitmapDrawingEnable(!this.bgDrawable.isRoundRect());
        if (this.foregroundContentDrawable != null) {
            this.foregroundContentDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        }
        if (this.foregroundShapeDrawable != null) {
            this.foregroundShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        }
        if (this.compatRippleDrawable != null) {
            this.compatRippleDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    private void updateInsetForeground(Drawable insetForeground) {
        if (Build.VERSION.SDK_INT >= 23 && (this.materialCardView.getForeground() instanceof InsetDrawable)) {
            ((InsetDrawable) this.materialCardView.getForeground()).setDrawable(insetForeground);
        } else {
            this.materialCardView.setForeground(insetDrawable(insetForeground));
        }
    }

    @NonNull
    private Drawable insetDrawable(Drawable originalDrawable) {
        int insetVertical = 0;
        int insetHorizontal = 0;
        boolean isPreLollipop = Build.VERSION.SDK_INT < 21;
        if (isPreLollipop || this.materialCardView.getUseCompatPadding()) {
            insetVertical = (int) Math.ceil(calculateVerticalBackgroundPadding());
            insetHorizontal = (int) Math.ceil(calculateHorizontalBackgroundPadding());
        }
        return new InsetDrawable(originalDrawable, insetHorizontal, insetVertical, insetHorizontal, insetVertical) { // from class: com.google.android.material.card.MaterialCardViewHelper.1
            @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
            public boolean getPadding(Rect padding) {
                return false;
            }

            @Override // android.graphics.drawable.Drawable
            public int getMinimumWidth() {
                return -1;
            }

            @Override // android.graphics.drawable.Drawable
            public int getMinimumHeight() {
                return -1;
            }
        };
    }

    private float calculateVerticalBackgroundPadding() {
        return (shouldAddCornerPaddingOutsideCardBackground() ? calculateActualCornerPadding() : 0.0f) + (CARD_VIEW_SHADOW_MULTIPLIER * this.materialCardView.getMaxCardElevation());
    }

    private float calculateHorizontalBackgroundPadding() {
        return (shouldAddCornerPaddingOutsideCardBackground() ? calculateActualCornerPadding() : 0.0f) + this.materialCardView.getMaxCardElevation();
    }

    private boolean canClipToOutline() {
        return Build.VERSION.SDK_INT >= 21 && this.bgDrawable.isRoundRect();
    }

    private float getParentCardViewCalculatedCornerPadding() {
        if (!this.materialCardView.getPreventCornerOverlap() || (Build.VERSION.SDK_INT >= 21 && !this.materialCardView.getUseCompatPadding())) {
            return 0.0f;
        }
        return (float) ((1.0d - COS_45) * this.materialCardView.getCardViewRadius());
    }

    private boolean shouldAddCornerPaddingInsideCardBackground() {
        return this.materialCardView.getPreventCornerOverlap() && !canClipToOutline();
    }

    private boolean shouldAddCornerPaddingOutsideCardBackground() {
        return this.materialCardView.getPreventCornerOverlap() && canClipToOutline() && this.materialCardView.getUseCompatPadding();
    }

    private float calculateActualCornerPadding() {
        return Math.max(Math.max(calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getTopLeftCorner(), this.bgDrawable.getTopLeftCornerResolvedSize()), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getTopRightCorner(), this.bgDrawable.getTopRightCornerResolvedSize())), Math.max(calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getBottomRightCorner(), this.bgDrawable.getBottomRightCornerResolvedSize()), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getBottomLeftCorner(), this.bgDrawable.getBottomLeftCornerResolvedSize())));
    }

    private float calculateCornerPaddingForCornerTreatment(CornerTreatment treatment, float size) {
        if (treatment instanceof RoundedCornerTreatment) {
            return (float) ((1.0d - COS_45) * size);
        }
        if (treatment instanceof CutCornerTreatment) {
            return size / 2.0f;
        }
        return 0.0f;
    }

    @NonNull
    private Drawable getClickableForeground() {
        if (this.rippleDrawable == null) {
            this.rippleDrawable = createForegroundRippleDrawable();
        }
        if (this.clickableForegroundDrawable == null) {
            Drawable checkedLayer = createCheckedIconLayer();
            this.clickableForegroundDrawable = new LayerDrawable(new Drawable[]{this.rippleDrawable, this.foregroundContentDrawable, checkedLayer});
            this.clickableForegroundDrawable.setId(2, R.id.mtrl_card_checked_layer_id);
        }
        return this.clickableForegroundDrawable;
    }

    @NonNull
    private Drawable createForegroundRippleDrawable() {
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            this.foregroundShapeDrawable = createForegroundShapeDrawable();
            return new RippleDrawable(this.rippleColor, null, this.foregroundShapeDrawable);
        }
        return createCompatRippleDrawable();
    }

    @NonNull
    private Drawable createCompatRippleDrawable() {
        StateListDrawable rippleDrawable = new StateListDrawable();
        this.compatRippleDrawable = createForegroundShapeDrawable();
        this.compatRippleDrawable.setFillColor(this.rippleColor);
        rippleDrawable.addState(new int[]{16842919}, this.compatRippleDrawable);
        return rippleDrawable;
    }

    private void updateRippleColor() {
        if (RippleUtils.USE_FRAMEWORK_RIPPLE && this.rippleDrawable != null) {
            ((RippleDrawable) this.rippleDrawable).setColor(this.rippleColor);
        } else if (this.compatRippleDrawable != null) {
            this.compatRippleDrawable.setFillColor(this.rippleColor);
        }
    }

    @NonNull
    private Drawable createCheckedIconLayer() {
        StateListDrawable checkedLayer = new StateListDrawable();
        if (this.checkedIcon != null) {
            checkedLayer.addState(CHECKED_STATE_SET, this.checkedIcon);
        }
        return checkedLayer;
    }

    @NonNull
    private MaterialShapeDrawable createForegroundShapeDrawable() {
        return new MaterialShapeDrawable(this.shapeAppearanceModel);
    }
}
