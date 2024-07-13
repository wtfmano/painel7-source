package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.annotation.XmlRes;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;

/* loaded from: classes.dex */
public class BadgeDrawable extends Drawable implements TextDrawableHelper.TextDrawableDelegate {
    private static final int BADGE_NUMBER_NONE = -1;
    public static final int BOTTOM_END = 8388693;
    public static final int BOTTOM_START = 8388691;
    static final String DEFAULT_EXCEED_MAX_BADGE_NUMBER_SUFFIX = "+";
    private static final int DEFAULT_MAX_BADGE_CHARACTER_COUNT = 4;
    @StyleRes
    private static final int DEFAULT_STYLE = R.style.Widget_MaterialComponents_Badge;
    @AttrRes
    private static final int DEFAULT_THEME_ATTR = R.attr.badgeStyle;
    private static final int MAX_CIRCULAR_BADGE_NUMBER_COUNT = 9;
    public static final int TOP_END = 8388661;
    public static final int TOP_START = 8388659;
    @Nullable
    private WeakReference<View> anchorViewRef;
    @NonNull
    private final Rect badgeBounds;
    private float badgeCenterX;
    private float badgeCenterY;
    private final float badgeRadius;
    private final float badgeWidePadding;
    private final float badgeWithTextRadius;
    @NonNull
    private final WeakReference<Context> contextRef;
    private float cornerRadius;
    @Nullable
    private WeakReference<FrameLayout> customBadgeParentRef;
    private float halfBadgeHeight;
    private float halfBadgeWidth;
    private int maxBadgeNumber;
    @NonNull
    private final SavedState savedState;
    @NonNull
    private final MaterialShapeDrawable shapeDrawable;
    @NonNull
    private final TextDrawableHelper textDrawableHelper;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface BadgeGravity {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public static final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.google.android.material.badge.BadgeDrawable.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            @NonNull
            public SavedState createFromParcel(@NonNull Parcel in) {
                return new SavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            @NonNull
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        @Dimension(unit = 1)
        private int additionalHorizontalOffset;
        @Dimension(unit = 1)
        private int additionalVerticalOffset;
        private int alpha;
        @ColorInt
        private int backgroundColor;
        private int badgeGravity;
        @ColorInt
        private int badgeTextColor;
        @StringRes
        private int contentDescriptionExceedsMaxBadgeNumberRes;
        @Nullable
        private CharSequence contentDescriptionNumberless;
        @PluralsRes
        private int contentDescriptionQuantityStrings;
        @Dimension(unit = 1)
        private int horizontalOffset;
        private boolean isVisible;
        private int maxCharacterCount;
        private int number;
        @Dimension(unit = 1)
        private int verticalOffset;

        public SavedState(@NonNull Context context) {
            this.alpha = 255;
            this.number = -1;
            TextAppearance textAppearance = new TextAppearance(context, R.style.TextAppearance_MaterialComponents_Badge);
            this.badgeTextColor = textAppearance.textColor.getDefaultColor();
            this.contentDescriptionNumberless = context.getString(R.string.mtrl_badge_numberless_content_description);
            this.contentDescriptionQuantityStrings = R.plurals.mtrl_badge_content_description;
            this.contentDescriptionExceedsMaxBadgeNumberRes = R.string.mtrl_exceed_max_badge_number_content_description;
            this.isVisible = true;
        }

        protected SavedState(@NonNull Parcel in) {
            this.alpha = 255;
            this.number = -1;
            this.backgroundColor = in.readInt();
            this.badgeTextColor = in.readInt();
            this.alpha = in.readInt();
            this.number = in.readInt();
            this.maxCharacterCount = in.readInt();
            this.contentDescriptionNumberless = in.readString();
            this.contentDescriptionQuantityStrings = in.readInt();
            this.badgeGravity = in.readInt();
            this.horizontalOffset = in.readInt();
            this.verticalOffset = in.readInt();
            this.additionalHorizontalOffset = in.readInt();
            this.additionalVerticalOffset = in.readInt();
            this.isVisible = in.readInt() != 0;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeInt(this.backgroundColor);
            dest.writeInt(this.badgeTextColor);
            dest.writeInt(this.alpha);
            dest.writeInt(this.number);
            dest.writeInt(this.maxCharacterCount);
            dest.writeString(this.contentDescriptionNumberless.toString());
            dest.writeInt(this.contentDescriptionQuantityStrings);
            dest.writeInt(this.badgeGravity);
            dest.writeInt(this.horizontalOffset);
            dest.writeInt(this.verticalOffset);
            dest.writeInt(this.additionalHorizontalOffset);
            dest.writeInt(this.additionalVerticalOffset);
            dest.writeInt(this.isVisible ? 1 : 0);
        }
    }

    @NonNull
    public SavedState getSavedState() {
        return this.savedState;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public static BadgeDrawable createFromSavedState(@NonNull Context context, @NonNull SavedState savedState) {
        BadgeDrawable badge = new BadgeDrawable(context);
        badge.restoreFromSavedState(savedState);
        return badge;
    }

    @NonNull
    public static BadgeDrawable create(@NonNull Context context) {
        return createFromAttributes(context, null, DEFAULT_THEME_ATTR, DEFAULT_STYLE);
    }

    @NonNull
    public static BadgeDrawable createFromResource(@NonNull Context context, @XmlRes int id) {
        AttributeSet attrs = DrawableUtils.parseDrawableXml(context, id, "badge");
        int style = attrs.getStyleAttribute();
        if (style == 0) {
            style = DEFAULT_STYLE;
        }
        return createFromAttributes(context, attrs, DEFAULT_THEME_ATTR, style);
    }

    @NonNull
    private static BadgeDrawable createFromAttributes(@NonNull Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        BadgeDrawable badge = new BadgeDrawable(context);
        badge.loadDefaultStateFromAttributes(context, attrs, defStyleAttr, defStyleRes);
        return badge;
    }

    public void setVisible(boolean visible) {
        setVisible(visible, false);
        this.savedState.isVisible = visible;
        if (BadgeUtils.USE_COMPAT_PARENT && getCustomBadgeParent() != null && !visible) {
            ((ViewGroup) getCustomBadgeParent().getParent()).invalidate();
        }
    }

    private void restoreFromSavedState(@NonNull SavedState savedState) {
        setMaxCharacterCount(savedState.maxCharacterCount);
        if (savedState.number != -1) {
            setNumber(savedState.number);
        }
        setBackgroundColor(savedState.backgroundColor);
        setBadgeTextColor(savedState.badgeTextColor);
        setBadgeGravity(savedState.badgeGravity);
        setHorizontalOffset(savedState.horizontalOffset);
        setVerticalOffset(savedState.verticalOffset);
        setAdditionalHorizontalOffset(savedState.additionalHorizontalOffset);
        setAdditionalVerticalOffset(savedState.additionalVerticalOffset);
        setVisible(savedState.isVisible);
    }

    private void loadDefaultStateFromAttributes(Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.Badge, defStyleAttr, defStyleRes, new int[0]);
        setMaxCharacterCount(a.getInt(R.styleable.Badge_maxCharacterCount, 4));
        if (a.hasValue(R.styleable.Badge_number)) {
            setNumber(a.getInt(R.styleable.Badge_number, 0));
        }
        setBackgroundColor(readColorFromAttributes(context, a, R.styleable.Badge_backgroundColor));
        if (a.hasValue(R.styleable.Badge_badgeTextColor)) {
            setBadgeTextColor(readColorFromAttributes(context, a, R.styleable.Badge_badgeTextColor));
        }
        setBadgeGravity(a.getInt(R.styleable.Badge_badgeGravity, TOP_END));
        setHorizontalOffset(a.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0));
        setVerticalOffset(a.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0));
        a.recycle();
    }

    private static int readColorFromAttributes(Context context, @NonNull TypedArray a, @StyleableRes int index) {
        return MaterialResources.getColorStateList(context, a, index).getDefaultColor();
    }

    private BadgeDrawable(@NonNull Context context) {
        this.contextRef = new WeakReference<>(context);
        ThemeEnforcement.checkMaterialTheme(context);
        Resources res = context.getResources();
        this.badgeBounds = new Rect();
        this.shapeDrawable = new MaterialShapeDrawable();
        this.badgeRadius = res.getDimensionPixelSize(R.dimen.mtrl_badge_radius);
        this.badgeWidePadding = res.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding);
        this.badgeWithTextRadius = res.getDimensionPixelSize(R.dimen.mtrl_badge_with_text_radius);
        this.textDrawableHelper = new TextDrawableHelper(this);
        this.textDrawableHelper.getTextPaint().setTextAlign(Paint.Align.CENTER);
        this.savedState = new SavedState(context);
        setTextAppearanceResource(R.style.TextAppearance_MaterialComponents_Badge);
    }

    @Deprecated
    public void updateBadgeCoordinates(@NonNull View anchorView, @Nullable ViewGroup customBadgeParent) {
        if (!(customBadgeParent instanceof FrameLayout)) {
            throw new IllegalArgumentException("customBadgeParent must be a FrameLayout");
        }
        updateBadgeCoordinates(anchorView, (FrameLayout) customBadgeParent);
    }

    public void updateBadgeCoordinates(@NonNull View anchorView) {
        updateBadgeCoordinates(anchorView, (FrameLayout) null);
    }

    public void updateBadgeCoordinates(@NonNull View anchorView, @Nullable FrameLayout customBadgeParent) {
        this.anchorViewRef = new WeakReference<>(anchorView);
        if (BadgeUtils.USE_COMPAT_PARENT && customBadgeParent == null) {
            tryWrapAnchorInCompatParent(anchorView);
        } else {
            this.customBadgeParentRef = new WeakReference<>(customBadgeParent);
        }
        if (!BadgeUtils.USE_COMPAT_PARENT) {
            updateAnchorParentToNotClip(anchorView);
        }
        updateCenterAndBounds();
        invalidateSelf();
    }

    @Nullable
    public FrameLayout getCustomBadgeParent() {
        if (this.customBadgeParentRef != null) {
            return this.customBadgeParentRef.get();
        }
        return null;
    }

    private void tryWrapAnchorInCompatParent(final View anchorView) {
        ViewGroup anchorViewParent = (ViewGroup) anchorView.getParent();
        if (anchorViewParent == null || anchorViewParent.getId() != R.id.mtrl_anchor_parent) {
            if (this.customBadgeParentRef == null || this.customBadgeParentRef.get() != anchorViewParent) {
                updateAnchorParentToNotClip(anchorView);
                final FrameLayout frameLayout = new FrameLayout(anchorView.getContext());
                frameLayout.setId(R.id.mtrl_anchor_parent);
                frameLayout.setClipChildren(false);
                frameLayout.setClipToPadding(false);
                frameLayout.setLayoutParams(anchorView.getLayoutParams());
                frameLayout.setMinimumWidth(anchorView.getWidth());
                frameLayout.setMinimumHeight(anchorView.getHeight());
                int anchorIndex = anchorViewParent.indexOfChild(anchorView);
                anchorViewParent.removeViewAt(anchorIndex);
                anchorView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                frameLayout.addView(anchorView);
                anchorViewParent.addView(frameLayout, anchorIndex);
                this.customBadgeParentRef = new WeakReference<>(frameLayout);
                frameLayout.post(new Runnable() { // from class: com.google.android.material.badge.BadgeDrawable.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BadgeDrawable.this.updateBadgeCoordinates(anchorView, frameLayout);
                    }
                });
            }
        }
    }

    private static void updateAnchorParentToNotClip(View anchorView) {
        ViewGroup anchorViewParent = (ViewGroup) anchorView.getParent();
        anchorViewParent.setClipChildren(false);
        anchorViewParent.setClipToPadding(false);
    }

    @ColorInt
    public int getBackgroundColor() {
        return this.shapeDrawable.getFillColor().getDefaultColor();
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.savedState.backgroundColor = backgroundColor;
        ColorStateList backgroundColorStateList = ColorStateList.valueOf(backgroundColor);
        if (this.shapeDrawable.getFillColor() != backgroundColorStateList) {
            this.shapeDrawable.setFillColor(backgroundColorStateList);
            invalidateSelf();
        }
    }

    @ColorInt
    public int getBadgeTextColor() {
        return this.textDrawableHelper.getTextPaint().getColor();
    }

    public void setBadgeTextColor(@ColorInt int badgeTextColor) {
        this.savedState.badgeTextColor = badgeTextColor;
        if (this.textDrawableHelper.getTextPaint().getColor() != badgeTextColor) {
            this.textDrawableHelper.getTextPaint().setColor(badgeTextColor);
            invalidateSelf();
        }
    }

    public boolean hasNumber() {
        return this.savedState.number != -1;
    }

    public int getNumber() {
        if (hasNumber()) {
            return this.savedState.number;
        }
        return 0;
    }

    public void setNumber(int number) {
        int number2 = Math.max(0, number);
        if (this.savedState.number != number2) {
            this.savedState.number = number2;
            this.textDrawableHelper.setTextWidthDirty(true);
            updateCenterAndBounds();
            invalidateSelf();
        }
    }

    public void clearNumber() {
        this.savedState.number = -1;
        invalidateSelf();
    }

    public int getMaxCharacterCount() {
        return this.savedState.maxCharacterCount;
    }

    public void setMaxCharacterCount(int maxCharacterCount) {
        if (this.savedState.maxCharacterCount != maxCharacterCount) {
            this.savedState.maxCharacterCount = maxCharacterCount;
            updateMaxBadgeNumber();
            this.textDrawableHelper.setTextWidthDirty(true);
            updateCenterAndBounds();
            invalidateSelf();
        }
    }

    public int getBadgeGravity() {
        return this.savedState.badgeGravity;
    }

    public void setBadgeGravity(int gravity) {
        if (this.savedState.badgeGravity != gravity) {
            this.savedState.badgeGravity = gravity;
            if (this.anchorViewRef != null && this.anchorViewRef.get() != null) {
                updateBadgeCoordinates(this.anchorViewRef.get(), this.customBadgeParentRef != null ? this.customBadgeParentRef.get() : null);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.savedState.alpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.savedState.alpha = alpha;
        this.textDrawableHelper.getTextPaint().setAlpha(alpha);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.badgeBounds.height();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.badgeBounds.width();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (!bounds.isEmpty() && getAlpha() != 0 && isVisible()) {
            this.shapeDrawable.draw(canvas);
            if (hasNumber()) {
                drawText(canvas);
            }
        }
    }

    @Override // com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onTextSizeChange() {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public boolean onStateChange(int[] state) {
        return super.onStateChange(state);
    }

    public void setContentDescriptionNumberless(CharSequence charSequence) {
        this.savedState.contentDescriptionNumberless = charSequence;
    }

    public void setContentDescriptionQuantityStringsResource(@PluralsRes int stringsResource) {
        this.savedState.contentDescriptionQuantityStrings = stringsResource;
    }

    public void setContentDescriptionExceedsMaxBadgeNumberStringResource(@StringRes int stringsResource) {
        this.savedState.contentDescriptionExceedsMaxBadgeNumberRes = stringsResource;
    }

    @Nullable
    public CharSequence getContentDescription() {
        Context context;
        if (isVisible()) {
            if (hasNumber()) {
                if (this.savedState.contentDescriptionQuantityStrings <= 0 || (context = this.contextRef.get()) == null) {
                    return null;
                }
                if (getNumber() <= this.maxBadgeNumber) {
                    return context.getResources().getQuantityString(this.savedState.contentDescriptionQuantityStrings, getNumber(), Integer.valueOf(getNumber()));
                }
                return context.getString(this.savedState.contentDescriptionExceedsMaxBadgeNumberRes, Integer.valueOf(this.maxBadgeNumber));
            }
            return this.savedState.contentDescriptionNumberless;
        }
        return null;
    }

    public void setHorizontalOffset(int px) {
        this.savedState.horizontalOffset = px;
        updateCenterAndBounds();
    }

    public int getHorizontalOffset() {
        return this.savedState.horizontalOffset;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setAdditionalHorizontalOffset(int px) {
        this.savedState.additionalHorizontalOffset = px;
        updateCenterAndBounds();
    }

    int getAdditionalHorizontalOffset() {
        return this.savedState.additionalHorizontalOffset;
    }

    public void setVerticalOffset(int px) {
        this.savedState.verticalOffset = px;
        updateCenterAndBounds();
    }

    public int getVerticalOffset() {
        return this.savedState.verticalOffset;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setAdditionalVerticalOffset(int px) {
        this.savedState.additionalVerticalOffset = px;
        updateCenterAndBounds();
    }

    int getAdditionalVerticalOffset() {
        return this.savedState.additionalVerticalOffset;
    }

    private void setTextAppearanceResource(@StyleRes int id) {
        Context context = this.contextRef.get();
        if (context != null) {
            setTextAppearance(new TextAppearance(context, id));
        }
    }

    private void setTextAppearance(@Nullable TextAppearance textAppearance) {
        Context context;
        if (this.textDrawableHelper.getTextAppearance() != textAppearance && (context = this.contextRef.get()) != null) {
            this.textDrawableHelper.setTextAppearance(textAppearance, context);
            updateCenterAndBounds();
        }
    }

    private void updateCenterAndBounds() {
        Context context = this.contextRef.get();
        View anchorView = this.anchorViewRef != null ? this.anchorViewRef.get() : null;
        if (context != null && anchorView != null) {
            Rect tmpRect = new Rect();
            tmpRect.set(this.badgeBounds);
            Rect anchorRect = new Rect();
            anchorView.getDrawingRect(anchorRect);
            ViewGroup customBadgeParent = this.customBadgeParentRef != null ? (FrameLayout) this.customBadgeParentRef.get() : null;
            if (customBadgeParent != null || BadgeUtils.USE_COMPAT_PARENT) {
                ViewGroup viewGroup = customBadgeParent == null ? (ViewGroup) anchorView.getParent() : customBadgeParent;
                viewGroup.offsetDescendantRectToMyCoords(anchorView, anchorRect);
            }
            calculateCenterAndBounds(context, anchorRect, anchorView);
            BadgeUtils.updateBadgeBounds(this.badgeBounds, this.badgeCenterX, this.badgeCenterY, this.halfBadgeWidth, this.halfBadgeHeight);
            this.shapeDrawable.setCornerSize(this.cornerRadius);
            if (!tmpRect.equals(this.badgeBounds)) {
                this.shapeDrawable.setBounds(this.badgeBounds);
            }
        }
    }

    private void calculateCenterAndBounds(@NonNull Context context, @NonNull Rect anchorRect, @NonNull View anchorView) {
        int totalVerticalOffset = this.savedState.verticalOffset + this.savedState.additionalVerticalOffset;
        switch (this.savedState.badgeGravity) {
            case BOTTOM_START /* 8388691 */:
            case BOTTOM_END /* 8388693 */:
                this.badgeCenterY = anchorRect.bottom - totalVerticalOffset;
                break;
            case 8388692:
            default:
                this.badgeCenterY = anchorRect.top + totalVerticalOffset;
                break;
        }
        if (getNumber() <= 9) {
            this.cornerRadius = !hasNumber() ? this.badgeRadius : this.badgeWithTextRadius;
            this.halfBadgeHeight = this.cornerRadius;
            this.halfBadgeWidth = this.cornerRadius;
        } else {
            this.cornerRadius = this.badgeWithTextRadius;
            this.halfBadgeHeight = this.cornerRadius;
            String badgeText = getBadgeText();
            this.halfBadgeWidth = (this.textDrawableHelper.getTextWidth(badgeText) / 2.0f) + this.badgeWidePadding;
        }
        int inset = context.getResources().getDimensionPixelSize(hasNumber() ? R.dimen.mtrl_badge_text_horizontal_edge_offset : R.dimen.mtrl_badge_horizontal_edge_offset);
        int totalHorizontalOffset = this.savedState.horizontalOffset + this.savedState.additionalHorizontalOffset;
        switch (this.savedState.badgeGravity) {
            case TOP_START /* 8388659 */:
            case BOTTOM_START /* 8388691 */:
                this.badgeCenterX = ViewCompat.getLayoutDirection(anchorView) == 0 ? (anchorRect.left - this.halfBadgeWidth) + inset + totalHorizontalOffset : ((anchorRect.right + this.halfBadgeWidth) - inset) - totalHorizontalOffset;
                return;
            default:
                this.badgeCenterX = ViewCompat.getLayoutDirection(anchorView) == 0 ? ((anchorRect.right + this.halfBadgeWidth) - inset) - totalHorizontalOffset : (anchorRect.left - this.halfBadgeWidth) + inset + totalHorizontalOffset;
                return;
        }
    }

    private void drawText(Canvas canvas) {
        Rect textBounds = new Rect();
        String badgeText = getBadgeText();
        this.textDrawableHelper.getTextPaint().getTextBounds(badgeText, 0, badgeText.length(), textBounds);
        canvas.drawText(badgeText, this.badgeCenterX, this.badgeCenterY + (textBounds.height() / 2), this.textDrawableHelper.getTextPaint());
    }

    @NonNull
    private String getBadgeText() {
        if (getNumber() <= this.maxBadgeNumber) {
            return NumberFormat.getInstance().format(getNumber());
        }
        Context context = this.contextRef.get();
        if (context == null) {
            return "";
        }
        return context.getString(R.string.mtrl_exceed_max_badge_number_suffix, Integer.valueOf(this.maxBadgeNumber), DEFAULT_EXCEED_MAX_BADGE_NUMBER_SUFFIX);
    }

    private void updateMaxBadgeNumber() {
        this.maxBadgeNumber = ((int) Math.pow(10.0d, getMaxCharacterCount() - 1.0d)) - 1;
    }
}
