package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public abstract class NavigationBarItemView extends FrameLayout implements MenuView.ItemView {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int INVALID_ITEM_POSITION = -1;
    @Nullable
    private BadgeDrawable badgeDrawable;
    private final int defaultMargin;
    private ImageView icon;
    @Nullable
    private ColorStateList iconTint;
    private boolean isShifting;
    @Nullable
    private MenuItemImpl itemData;
    private int itemPosition;
    private final ViewGroup labelGroup;
    private int labelVisibilityMode;
    private final TextView largeLabel;
    @Nullable
    private Drawable originalIconDrawable;
    private float scaleDownFactor;
    private float scaleUpFactor;
    private float shiftAmount;
    private final TextView smallLabel;
    @Nullable
    private Drawable wrappedIconDrawable;

    @LayoutRes
    protected abstract int getItemLayoutResId();

    public NavigationBarItemView(@NonNull Context context) {
        super(context);
        this.itemPosition = -1;
        LayoutInflater.from(context).inflate(getItemLayoutResId(), (ViewGroup) this, true);
        this.icon = (ImageView) findViewById(R.id.navigation_bar_item_icon_view);
        this.labelGroup = (ViewGroup) findViewById(R.id.navigation_bar_item_labels_group);
        this.smallLabel = (TextView) findViewById(R.id.navigation_bar_item_small_label_view);
        this.largeLabel = (TextView) findViewById(R.id.navigation_bar_item_large_label_view);
        setBackgroundResource(getItemBackgroundResId());
        this.defaultMargin = getResources().getDimensionPixelSize(getItemDefaultMarginResId());
        this.labelGroup.setTag(R.id.mtrl_view_tag_bottom_padding, Integer.valueOf(this.labelGroup.getPaddingBottom()));
        ViewCompat.setImportantForAccessibility(this.smallLabel, 2);
        ViewCompat.setImportantForAccessibility(this.largeLabel, 2);
        setFocusable(true);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
        if (this.icon != null) {
            this.icon.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.navigation.NavigationBarItemView.1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (NavigationBarItemView.this.icon.getVisibility() == 0) {
                        NavigationBarItemView.this.tryUpdateBadgeBounds(NavigationBarItemView.this.icon);
                    }
                }
            });
        }
    }

    @Override // android.view.View
    protected int getSuggestedMinimumWidth() {
        FrameLayout.LayoutParams labelGroupParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        int labelWidth = labelGroupParams.leftMargin + this.labelGroup.getMeasuredWidth() + labelGroupParams.rightMargin;
        return Math.max(getSuggestedIconWidth(), labelWidth);
    }

    @Override // android.view.View
    protected int getSuggestedMinimumHeight() {
        FrameLayout.LayoutParams labelGroupParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        return getSuggestedIconHeight() + labelGroupParams.topMargin + this.labelGroup.getMeasuredHeight() + labelGroupParams.bottomMargin;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void initialize(@NonNull MenuItemImpl itemData, int menuType) {
        CharSequence tooltipText;
        this.itemData = itemData;
        setCheckable(itemData.isCheckable());
        setChecked(itemData.isChecked());
        setEnabled(itemData.isEnabled());
        setIcon(itemData.getIcon());
        setTitle(itemData.getTitle());
        setId(itemData.getItemId());
        if (!TextUtils.isEmpty(itemData.getContentDescription())) {
            setContentDescription(itemData.getContentDescription());
        }
        if (!TextUtils.isEmpty(itemData.getTooltipText())) {
            tooltipText = itemData.getTooltipText();
        } else {
            tooltipText = itemData.getTitle();
        }
        if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT > 23) {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
        setVisibility(itemData.isVisible() ? 0 : 8);
    }

    public void setItemPosition(int position) {
        this.itemPosition = position;
    }

    public int getItemPosition() {
        return this.itemPosition;
    }

    public void setShifting(boolean shifting) {
        if (this.isShifting != shifting) {
            this.isShifting = shifting;
            boolean initialized = this.itemData != null;
            if (initialized) {
                setChecked(this.itemData.isChecked());
            }
        }
    }

    public void setLabelVisibilityMode(int mode) {
        if (this.labelVisibilityMode != mode) {
            this.labelVisibilityMode = mode;
            boolean initialized = this.itemData != null;
            if (initialized) {
                setChecked(this.itemData.isChecked());
            }
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    @Nullable
    public MenuItemImpl getItemData() {
        return this.itemData;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setTitle(@Nullable CharSequence title) {
        this.smallLabel.setText(title);
        this.largeLabel.setText(title);
        if (this.itemData == null || TextUtils.isEmpty(this.itemData.getContentDescription())) {
            setContentDescription(title);
        }
        CharSequence tooltipText = (this.itemData == null || TextUtils.isEmpty(this.itemData.getTooltipText())) ? title : this.itemData.getTooltipText();
        if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT > 23) {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setCheckable(boolean checkable) {
        refreshDrawableState();
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setChecked(boolean checked) {
        this.largeLabel.setPivotX(this.largeLabel.getWidth() / 2);
        this.largeLabel.setPivotY(this.largeLabel.getBaseline());
        this.smallLabel.setPivotX(this.smallLabel.getWidth() / 2);
        this.smallLabel.setPivotY(this.smallLabel.getBaseline());
        switch (this.labelVisibilityMode) {
            case -1:
                if (this.isShifting) {
                    if (checked) {
                        setViewLayoutParams(this.icon, this.defaultMargin, 49);
                        updateViewPaddingBottom(this.labelGroup, ((Integer) this.labelGroup.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                        this.largeLabel.setVisibility(0);
                    } else {
                        setViewLayoutParams(this.icon, this.defaultMargin, 17);
                        updateViewPaddingBottom(this.labelGroup, 0);
                        this.largeLabel.setVisibility(4);
                    }
                    this.smallLabel.setVisibility(4);
                    break;
                } else {
                    updateViewPaddingBottom(this.labelGroup, ((Integer) this.labelGroup.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                    if (checked) {
                        setViewLayoutParams(this.icon, (int) (this.defaultMargin + this.shiftAmount), 49);
                        setViewScaleValues(this.largeLabel, 1.0f, 1.0f, 0);
                        setViewScaleValues(this.smallLabel, this.scaleUpFactor, this.scaleUpFactor, 4);
                        break;
                    } else {
                        setViewLayoutParams(this.icon, this.defaultMargin, 49);
                        setViewScaleValues(this.largeLabel, this.scaleDownFactor, this.scaleDownFactor, 4);
                        setViewScaleValues(this.smallLabel, 1.0f, 1.0f, 0);
                        break;
                    }
                }
            case 0:
                if (checked) {
                    setViewLayoutParams(this.icon, this.defaultMargin, 49);
                    updateViewPaddingBottom(this.labelGroup, ((Integer) this.labelGroup.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                    this.largeLabel.setVisibility(0);
                } else {
                    setViewLayoutParams(this.icon, this.defaultMargin, 17);
                    updateViewPaddingBottom(this.labelGroup, 0);
                    this.largeLabel.setVisibility(4);
                }
                this.smallLabel.setVisibility(4);
                break;
            case 1:
                updateViewPaddingBottom(this.labelGroup, ((Integer) this.labelGroup.getTag(R.id.mtrl_view_tag_bottom_padding)).intValue());
                if (checked) {
                    setViewLayoutParams(this.icon, (int) (this.defaultMargin + this.shiftAmount), 49);
                    setViewScaleValues(this.largeLabel, 1.0f, 1.0f, 0);
                    setViewScaleValues(this.smallLabel, this.scaleUpFactor, this.scaleUpFactor, 4);
                    break;
                } else {
                    setViewLayoutParams(this.icon, this.defaultMargin, 49);
                    setViewScaleValues(this.largeLabel, this.scaleDownFactor, this.scaleDownFactor, 4);
                    setViewScaleValues(this.smallLabel, 1.0f, 1.0f, 0);
                    break;
                }
            case 2:
                setViewLayoutParams(this.icon, this.defaultMargin, 17);
                this.largeLabel.setVisibility(8);
                this.smallLabel.setVisibility(8);
                break;
        }
        refreshDrawableState();
        setSelected(checked);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (this.badgeDrawable != null && this.badgeDrawable.isVisible()) {
            CharSequence customContentDescription = this.itemData.getTitle();
            if (!TextUtils.isEmpty(this.itemData.getContentDescription())) {
                customContentDescription = this.itemData.getContentDescription();
            }
            info.setContentDescription(((Object) customContentDescription) + ", " + ((Object) this.badgeDrawable.getContentDescription()));
        }
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        infoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, getItemVisiblePosition(), 1, false, isSelected()));
        if (isSelected()) {
            infoCompat.setClickable(false);
            infoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
        }
        infoCompat.setRoleDescription(getResources().getString(R.string.item_view_role_description));
    }

    private int getItemVisiblePosition() {
        ViewGroup parent = (ViewGroup) getParent();
        int index = parent.indexOfChild(this);
        int visiblePosition = 0;
        for (int i = 0; i < index; i++) {
            View child = parent.getChildAt(i);
            if ((child instanceof NavigationBarItemView) && child.getVisibility() == 0) {
                visiblePosition++;
            }
        }
        return visiblePosition;
    }

    private static void setViewLayoutParams(@NonNull View view, int topMargin, int gravity) {
        FrameLayout.LayoutParams viewParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        viewParams.topMargin = topMargin;
        viewParams.gravity = gravity;
        view.setLayoutParams(viewParams);
    }

    private static void setViewScaleValues(@NonNull View view, float scaleX, float scaleY, int visibility) {
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
        view.setVisibility(visibility);
    }

    private static void updateViewPaddingBottom(@NonNull View view, int paddingBottom) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), paddingBottom);
    }

    @Override // android.view.View, androidx.appcompat.view.menu.MenuView.ItemView
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.smallLabel.setEnabled(enabled);
        this.largeLabel.setEnabled(enabled);
        this.icon.setEnabled(enabled);
        if (enabled) {
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND));
        } else {
            ViewCompat.setPointerIcon(this, null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @NonNull
    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (this.itemData != null && this.itemData.isCheckable() && this.itemData.isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public void setIcon(@Nullable Drawable iconDrawable) {
        if (iconDrawable != this.originalIconDrawable) {
            this.originalIconDrawable = iconDrawable;
            if (iconDrawable != null) {
                Drawable.ConstantState state = iconDrawable.getConstantState();
                if (state != null) {
                    iconDrawable = state.newDrawable();
                }
                iconDrawable = DrawableCompat.wrap(iconDrawable).mutate();
                this.wrappedIconDrawable = iconDrawable;
                if (this.iconTint != null) {
                    DrawableCompat.setTintList(this.wrappedIconDrawable, this.iconTint);
                }
            }
            this.icon.setImageDrawable(iconDrawable);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }

    public void setIconTintList(@Nullable ColorStateList tint) {
        this.iconTint = tint;
        if (this.itemData != null && this.wrappedIconDrawable != null) {
            DrawableCompat.setTintList(this.wrappedIconDrawable, this.iconTint);
            this.wrappedIconDrawable.invalidateSelf();
        }
    }

    public void setIconSize(int iconSize) {
        FrameLayout.LayoutParams iconParams = (FrameLayout.LayoutParams) this.icon.getLayoutParams();
        iconParams.width = iconSize;
        iconParams.height = iconSize;
        this.icon.setLayoutParams(iconParams);
    }

    public void setTextAppearanceInactive(@StyleRes int inactiveTextAppearance) {
        TextViewCompat.setTextAppearance(this.smallLabel, inactiveTextAppearance);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public void setTextAppearanceActive(@StyleRes int activeTextAppearance) {
        TextViewCompat.setTextAppearance(this.largeLabel, activeTextAppearance);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public void setTextColor(@Nullable ColorStateList color) {
        if (color != null) {
            this.smallLabel.setTextColor(color);
            this.largeLabel.setTextColor(color);
        }
    }

    private void calculateTextScaleFactors(float smallLabelSize, float largeLabelSize) {
        this.shiftAmount = smallLabelSize - largeLabelSize;
        this.scaleUpFactor = (1.0f * largeLabelSize) / smallLabelSize;
        this.scaleDownFactor = (1.0f * smallLabelSize) / largeLabelSize;
    }

    public void setItemBackground(int background) {
        Drawable backgroundDrawable = background == 0 ? null : ContextCompat.getDrawable(getContext(), background);
        setItemBackground(backgroundDrawable);
    }

    public void setItemBackground(@Nullable Drawable background) {
        if (background != null && background.getConstantState() != null) {
            background = background.getConstantState().newDrawable().mutate();
        }
        ViewCompat.setBackground(this, background);
    }

    public void setBadge(@NonNull BadgeDrawable badgeDrawable) {
        this.badgeDrawable = badgeDrawable;
        if (this.icon != null) {
            tryAttachBadgeToAnchor(this.icon);
        }
    }

    @Nullable
    public BadgeDrawable getBadge() {
        return this.badgeDrawable;
    }

    public void removeBadge() {
        tryRemoveBadgeFromAnchor(this.icon);
    }

    private boolean hasBadge() {
        return this.badgeDrawable != null;
    }

    public void tryUpdateBadgeBounds(View anchorView) {
        if (hasBadge()) {
            BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, anchorView, getCustomParentForBadge(anchorView));
        }
    }

    private void tryAttachBadgeToAnchor(@Nullable View anchorView) {
        if (hasBadge() && anchorView != null) {
            setClipChildren(false);
            setClipToPadding(false);
            BadgeUtils.attachBadgeDrawable(this.badgeDrawable, anchorView, getCustomParentForBadge(anchorView));
        }
    }

    private void tryRemoveBadgeFromAnchor(@Nullable View anchorView) {
        if (hasBadge()) {
            if (anchorView != null) {
                setClipChildren(true);
                setClipToPadding(true);
                BadgeUtils.detachBadgeDrawable(this.badgeDrawable, anchorView);
            }
            this.badgeDrawable = null;
        }
    }

    @Nullable
    private FrameLayout getCustomParentForBadge(View anchorView) {
        if (anchorView == this.icon && BadgeUtils.USE_COMPAT_PARENT) {
            return (FrameLayout) this.icon.getParent();
        }
        return null;
    }

    private int getSuggestedIconWidth() {
        int badgeWidth = this.badgeDrawable == null ? 0 : this.badgeDrawable.getMinimumWidth() - this.badgeDrawable.getHorizontalOffset();
        FrameLayout.LayoutParams iconParams = (FrameLayout.LayoutParams) this.icon.getLayoutParams();
        return Math.max(badgeWidth, iconParams.leftMargin) + this.icon.getMeasuredWidth() + Math.max(badgeWidth, iconParams.rightMargin);
    }

    private int getSuggestedIconHeight() {
        int badgeHeight = 0;
        if (this.badgeDrawable != null) {
            badgeHeight = this.badgeDrawable.getMinimumHeight() / 2;
        }
        FrameLayout.LayoutParams iconParams = (FrameLayout.LayoutParams) this.icon.getLayoutParams();
        return Math.max(badgeHeight, iconParams.topMargin) + this.icon.getMeasuredWidth() + badgeHeight;
    }

    @DrawableRes
    protected int getItemBackgroundResId() {
        return R.drawable.mtrl_navigation_bar_item_background;
    }

    @DimenRes
    protected int getItemDefaultMarginResId() {
        return R.dimen.mtrl_navigation_bar_item_default_margin;
    }
}
