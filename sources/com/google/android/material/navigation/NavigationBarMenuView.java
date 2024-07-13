package com.google.android.material.navigation;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.util.Pools;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.TextScale;
import java.util.HashSet;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public abstract class NavigationBarMenuView extends ViewGroup implements MenuView {
    private static final long ACTIVE_ANIMATION_DURATION_MS = 115;
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int[] DISABLED_STATE_SET = {-16842910};
    private static final int ITEM_POOL_SIZE = 5;
    @NonNull
    private SparseArray<BadgeDrawable> badgeDrawables;
    @Nullable
    private NavigationBarItemView[] buttons;
    private Drawable itemBackground;
    private int itemBackgroundRes;
    @Dimension
    private int itemIconSize;
    @Nullable
    private ColorStateList itemIconTint;
    private final Pools.Pool<NavigationBarItemView> itemPool;
    @StyleRes
    private int itemTextAppearanceActive;
    @StyleRes
    private int itemTextAppearanceInactive;
    @Nullable
    private final ColorStateList itemTextColorDefault;
    private ColorStateList itemTextColorFromUser;
    private int labelVisibilityMode;
    private MenuBuilder menu;
    @NonNull
    private final View.OnClickListener onClickListener;
    @NonNull
    private final SparseArray<View.OnTouchListener> onTouchListeners;
    private NavigationBarPresenter presenter;
    private int selectedItemId;
    private int selectedItemPosition;
    @NonNull
    private final TransitionSet set;

    @NonNull
    protected abstract NavigationBarItemView createNavigationBarItemView(@NonNull Context context);

    public NavigationBarMenuView(@NonNull Context context) {
        super(context);
        this.itemPool = new Pools.SynchronizedPool(5);
        this.onTouchListeners = new SparseArray<>(5);
        this.selectedItemId = 0;
        this.selectedItemPosition = 0;
        this.badgeDrawables = new SparseArray<>(5);
        this.itemTextColorDefault = createDefaultColorStateList(16842808);
        this.set = new AutoTransition();
        this.set.setOrdering(0);
        this.set.setDuration(ACTIVE_ANIMATION_DURATION_MS);
        this.set.setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator());
        this.set.addTransition(new TextScale());
        this.onClickListener = new View.OnClickListener() { // from class: com.google.android.material.navigation.NavigationBarMenuView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                NavigationBarItemView itemView = (NavigationBarItemView) v;
                MenuItem item = itemView.getItemData();
                if (!NavigationBarMenuView.this.menu.performItemAction(item, NavigationBarMenuView.this.presenter, 0)) {
                    item.setChecked(true);
                }
            }
        };
        ViewCompat.setImportantForAccessibility(this, 1);
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public void initialize(@NonNull MenuBuilder menu) {
        this.menu = menu;
    }

    @Override // androidx.appcompat.view.menu.MenuView
    public int getWindowAnimations() {
        return 0;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        infoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.menu.getVisibleItems().size(), false, 1));
    }

    public void setIconTintList(@Nullable ColorStateList tint) {
        NavigationBarItemView[] navigationBarItemViewArr;
        this.itemIconTint = tint;
        if (this.buttons != null) {
            for (NavigationBarItemView item : this.buttons) {
                item.setIconTintList(tint);
            }
        }
    }

    @Nullable
    public ColorStateList getIconTintList() {
        return this.itemIconTint;
    }

    public void setItemIconSize(@Dimension int iconSize) {
        NavigationBarItemView[] navigationBarItemViewArr;
        this.itemIconSize = iconSize;
        if (this.buttons != null) {
            for (NavigationBarItemView item : this.buttons) {
                item.setIconSize(iconSize);
            }
        }
    }

    @Dimension
    public int getItemIconSize() {
        return this.itemIconSize;
    }

    public void setItemTextColor(@Nullable ColorStateList color) {
        NavigationBarItemView[] navigationBarItemViewArr;
        this.itemTextColorFromUser = color;
        if (this.buttons != null) {
            for (NavigationBarItemView item : this.buttons) {
                item.setTextColor(color);
            }
        }
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.itemTextColorFromUser;
    }

    public void setItemTextAppearanceInactive(@StyleRes int textAppearanceRes) {
        NavigationBarItemView[] navigationBarItemViewArr;
        this.itemTextAppearanceInactive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarItemView item : this.buttons) {
                item.setTextAppearanceInactive(textAppearanceRes);
                if (this.itemTextColorFromUser != null) {
                    item.setTextColor(this.itemTextColorFromUser);
                }
            }
        }
    }

    @StyleRes
    public int getItemTextAppearanceInactive() {
        return this.itemTextAppearanceInactive;
    }

    public void setItemTextAppearanceActive(@StyleRes int textAppearanceRes) {
        NavigationBarItemView[] navigationBarItemViewArr;
        this.itemTextAppearanceActive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarItemView item : this.buttons) {
                item.setTextAppearanceActive(textAppearanceRes);
                if (this.itemTextColorFromUser != null) {
                    item.setTextColor(this.itemTextColorFromUser);
                }
            }
        }
    }

    @StyleRes
    public int getItemTextAppearanceActive() {
        return this.itemTextAppearanceActive;
    }

    public void setItemBackgroundRes(int background) {
        NavigationBarItemView[] navigationBarItemViewArr;
        this.itemBackgroundRes = background;
        if (this.buttons != null) {
            for (NavigationBarItemView item : this.buttons) {
                item.setItemBackground(background);
            }
        }
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.itemBackgroundRes;
    }

    public void setItemBackground(@Nullable Drawable background) {
        NavigationBarItemView[] navigationBarItemViewArr;
        this.itemBackground = background;
        if (this.buttons != null) {
            for (NavigationBarItemView item : this.buttons) {
                item.setItemBackground(background);
            }
        }
    }

    @Nullable
    public Drawable getItemBackground() {
        return (this.buttons == null || this.buttons.length <= 0) ? this.itemBackground : this.buttons[0].getBackground();
    }

    public void setLabelVisibilityMode(int labelVisibilityMode) {
        this.labelVisibilityMode = labelVisibilityMode;
    }

    public int getLabelVisibilityMode() {
        return this.labelVisibilityMode;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void setItemOnTouchListener(int menuItemId, @Nullable View.OnTouchListener onTouchListener) {
        NavigationBarItemView[] navigationBarItemViewArr;
        if (onTouchListener == null) {
            this.onTouchListeners.remove(menuItemId);
        } else {
            this.onTouchListeners.put(menuItemId, onTouchListener);
        }
        if (this.buttons != null) {
            for (NavigationBarItemView item : this.buttons) {
                if (item.getItemData().getItemId() == menuItemId) {
                    item.setOnTouchListener(onTouchListener);
                }
            }
        }
    }

    @Nullable
    public ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        TypedValue value = new TypedValue();
        if (getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            ColorStateList baseColor = AppCompatResources.getColorStateList(getContext(), value.resourceId);
            if (getContext().getTheme().resolveAttribute(R.attr.colorPrimary, value, true)) {
                int colorPrimary = value.data;
                int defaultColor = baseColor.getDefaultColor();
                return new ColorStateList(new int[][]{DISABLED_STATE_SET, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{baseColor.getColorForState(DISABLED_STATE_SET, defaultColor), colorPrimary, defaultColor});
            }
            return null;
        }
        return null;
    }

    public void setPresenter(@NonNull NavigationBarPresenter presenter) {
        this.presenter = presenter;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void buildMenuView() {
        NavigationBarItemView[] navigationBarItemViewArr;
        removeAllViews();
        if (this.buttons != null) {
            for (NavigationBarItemView item : this.buttons) {
                if (item != null) {
                    this.itemPool.release(item);
                    item.removeBadge();
                }
            }
        }
        if (this.menu.size() == 0) {
            this.selectedItemId = 0;
            this.selectedItemPosition = 0;
            this.buttons = null;
            return;
        }
        removeUnusedBadges();
        this.buttons = new NavigationBarItemView[this.menu.size()];
        boolean shifting = isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());
        for (int i = 0; i < this.menu.size(); i++) {
            this.presenter.setUpdateSuspended(true);
            this.menu.getItem(i).setCheckable(true);
            this.presenter.setUpdateSuspended(false);
            NavigationBarItemView child = getNewItem();
            this.buttons[i] = child;
            child.setIconTintList(this.itemIconTint);
            child.setIconSize(this.itemIconSize);
            child.setTextColor(this.itemTextColorDefault);
            child.setTextAppearanceInactive(this.itemTextAppearanceInactive);
            child.setTextAppearanceActive(this.itemTextAppearanceActive);
            child.setTextColor(this.itemTextColorFromUser);
            if (this.itemBackground != null) {
                child.setItemBackground(this.itemBackground);
            } else {
                child.setItemBackground(this.itemBackgroundRes);
            }
            child.setShifting(shifting);
            child.setLabelVisibilityMode(this.labelVisibilityMode);
            MenuItemImpl item2 = (MenuItemImpl) this.menu.getItem(i);
            child.initialize(item2, 0);
            child.setItemPosition(i);
            int itemId = item2.getItemId();
            child.setOnTouchListener(this.onTouchListeners.get(itemId));
            child.setOnClickListener(this.onClickListener);
            if (this.selectedItemId != 0 && itemId == this.selectedItemId) {
                this.selectedItemPosition = i;
            }
            setBadgeIfNeeded(child);
            addView(child);
        }
        this.selectedItemPosition = Math.min(this.menu.size() - 1, this.selectedItemPosition);
        this.menu.getItem(this.selectedItemPosition).setChecked(true);
    }

    public void updateMenuView() {
        if (this.menu != null && this.buttons != null) {
            int menuSize = this.menu.size();
            if (menuSize != this.buttons.length) {
                buildMenuView();
                return;
            }
            int previousSelectedId = this.selectedItemId;
            for (int i = 0; i < menuSize; i++) {
                MenuItem item = this.menu.getItem(i);
                if (item.isChecked()) {
                    this.selectedItemId = item.getItemId();
                    this.selectedItemPosition = i;
                }
            }
            if (previousSelectedId != this.selectedItemId) {
                TransitionManager.beginDelayedTransition(this, this.set);
            }
            boolean shifting = isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());
            for (int i2 = 0; i2 < menuSize; i2++) {
                this.presenter.setUpdateSuspended(true);
                this.buttons[i2].setLabelVisibilityMode(this.labelVisibilityMode);
                this.buttons[i2].setShifting(shifting);
                this.buttons[i2].initialize((MenuItemImpl) this.menu.getItem(i2), 0);
                this.presenter.setUpdateSuspended(false);
            }
        }
    }

    private NavigationBarItemView getNewItem() {
        NavigationBarItemView item = this.itemPool.acquire();
        if (item == null) {
            return createNavigationBarItemView(getContext());
        }
        return item;
    }

    public int getSelectedItemId() {
        return this.selectedItemId;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isShifting(int labelVisibilityMode, int childCount) {
        return labelVisibilityMode == -1 ? childCount > 3 : labelVisibilityMode == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void tryRestoreSelectedItemId(int itemId) {
        int size = this.menu.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = this.menu.getItem(i);
            if (itemId == item.getItemId()) {
                this.selectedItemId = itemId;
                this.selectedItemPosition = i;
                item.setChecked(true);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SparseArray<BadgeDrawable> getBadgeDrawables() {
        return this.badgeDrawables;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBadgeDrawables(SparseArray<BadgeDrawable> badgeDrawables) {
        NavigationBarItemView[] navigationBarItemViewArr;
        this.badgeDrawables = badgeDrawables;
        if (this.buttons != null) {
            for (NavigationBarItemView itemView : this.buttons) {
                itemView.setBadge(badgeDrawables.get(itemView.getId()));
            }
        }
    }

    @Nullable
    public BadgeDrawable getBadge(int menuItemId) {
        return this.badgeDrawables.get(menuItemId);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BadgeDrawable getOrCreateBadge(int menuItemId) {
        validateMenuItemId(menuItemId);
        BadgeDrawable badgeDrawable = this.badgeDrawables.get(menuItemId);
        if (badgeDrawable == null) {
            badgeDrawable = BadgeDrawable.create(getContext());
            this.badgeDrawables.put(menuItemId, badgeDrawable);
        }
        NavigationBarItemView itemView = findItemView(menuItemId);
        if (itemView != null) {
            itemView.setBadge(badgeDrawable);
        }
        return badgeDrawable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeBadge(int menuItemId) {
        validateMenuItemId(menuItemId);
        BadgeDrawable badgeDrawable = this.badgeDrawables.get(menuItemId);
        NavigationBarItemView itemView = findItemView(menuItemId);
        if (itemView != null) {
            itemView.removeBadge();
        }
        if (badgeDrawable != null) {
            this.badgeDrawables.remove(menuItemId);
        }
    }

    private void setBadgeIfNeeded(@NonNull NavigationBarItemView child) {
        BadgeDrawable badgeDrawable;
        int childId = child.getId();
        if (isValidId(childId) && (badgeDrawable = this.badgeDrawables.get(childId)) != null) {
            child.setBadge(badgeDrawable);
        }
    }

    private void removeUnusedBadges() {
        HashSet<Integer> activeKeys = new HashSet<>();
        for (int i = 0; i < this.menu.size(); i++) {
            activeKeys.add(Integer.valueOf(this.menu.getItem(i).getItemId()));
        }
        for (int i2 = 0; i2 < this.badgeDrawables.size(); i2++) {
            int key = this.badgeDrawables.keyAt(i2);
            if (!activeKeys.contains(Integer.valueOf(key))) {
                this.badgeDrawables.delete(key);
            }
        }
    }

    @Nullable
    public NavigationBarItemView findItemView(int menuItemId) {
        NavigationBarItemView[] navigationBarItemViewArr;
        validateMenuItemId(menuItemId);
        if (this.buttons != null) {
            for (NavigationBarItemView itemView : this.buttons) {
                if (itemView.getId() == menuItemId) {
                    return itemView;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSelectedItemPosition() {
        return this.selectedItemPosition;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Nullable
    public MenuBuilder getMenu() {
        return this.menu;
    }

    private boolean isValidId(int viewId) {
        return viewId != -1;
    }

    private void validateMenuItemId(int viewId) {
        if (!isValidId(viewId)) {
            throw new IllegalArgumentException(viewId + " is not a valid view id");
        }
    }
}
