package com.google.android.material.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes.dex */
public class NavigationView extends ScrimInsetsFrameLayout {
    private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;
    OnNavigationItemSelectedListener listener;
    private final int maxWidth;
    @NonNull
    private final NavigationMenu menu;
    private MenuInflater menuInflater;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private final NavigationMenuPresenter presenter;
    private final int[] tmpLocation;
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int[] DISABLED_STATE_SET = {-16842910};
    private static final int DEF_STYLE_RES = R.style.Widget_Design_NavigationView;

    /* loaded from: classes.dex */
    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    public NavigationView(@NonNull Context context) {
        this(context, null);
    }

    public NavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.navigationViewStyle);
    }

    public NavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        ColorStateList itemIconTint;
        this.presenter = new NavigationMenuPresenter();
        this.tmpLocation = new int[2];
        Context context2 = getContext();
        this.menu = new NavigationMenu(context2);
        TintTypedArray a = ThemeEnforcement.obtainTintedStyledAttributes(context2, attrs, R.styleable.NavigationView, defStyleAttr, DEF_STYLE_RES, new int[0]);
        if (a.hasValue(R.styleable.NavigationView_android_background)) {
            ViewCompat.setBackground(this, a.getDrawable(R.styleable.NavigationView_android_background));
        }
        if (getBackground() == null || (getBackground() instanceof ColorDrawable)) {
            ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder(context2, attrs, defStyleAttr, DEF_STYLE_RES).build();
            Drawable orig = getBackground();
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
            if (orig instanceof ColorDrawable) {
                materialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable) orig).getColor()));
            }
            materialShapeDrawable.initializeElevationOverlay(context2);
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
        if (a.hasValue(R.styleable.NavigationView_elevation)) {
            setElevation(a.getDimensionPixelSize(R.styleable.NavigationView_elevation, 0));
        }
        setFitsSystemWindows(a.getBoolean(R.styleable.NavigationView_android_fitsSystemWindows, false));
        this.maxWidth = a.getDimensionPixelSize(R.styleable.NavigationView_android_maxWidth, 0);
        if (a.hasValue(R.styleable.NavigationView_itemIconTint)) {
            itemIconTint = a.getColorStateList(R.styleable.NavigationView_itemIconTint);
        } else {
            itemIconTint = createDefaultColorStateList(16842808);
        }
        boolean textAppearanceSet = false;
        int textAppearance = 0;
        if (a.hasValue(R.styleable.NavigationView_itemTextAppearance)) {
            textAppearance = a.getResourceId(R.styleable.NavigationView_itemTextAppearance, 0);
            textAppearanceSet = true;
        }
        if (a.hasValue(R.styleable.NavigationView_itemIconSize)) {
            setItemIconSize(a.getDimensionPixelSize(R.styleable.NavigationView_itemIconSize, 0));
        }
        ColorStateList itemTextColor = a.hasValue(R.styleable.NavigationView_itemTextColor) ? a.getColorStateList(R.styleable.NavigationView_itemTextColor) : null;
        if (!textAppearanceSet && itemTextColor == null) {
            itemTextColor = createDefaultColorStateList(16842806);
        }
        Drawable itemBackground = a.getDrawable(R.styleable.NavigationView_itemBackground);
        if (itemBackground == null && hasShapeAppearance(a)) {
            itemBackground = createDefaultItemBackground(a);
        }
        if (a.hasValue(R.styleable.NavigationView_itemHorizontalPadding)) {
            int itemHorizontalPadding = a.getDimensionPixelSize(R.styleable.NavigationView_itemHorizontalPadding, 0);
            this.presenter.setItemHorizontalPadding(itemHorizontalPadding);
        }
        int itemIconPadding = a.getDimensionPixelSize(R.styleable.NavigationView_itemIconPadding, 0);
        setItemMaxLines(a.getInt(R.styleable.NavigationView_itemMaxLines, 1));
        this.menu.setCallback(new MenuBuilder.Callback() { // from class: com.google.android.material.navigation.NavigationView.1
            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                return NavigationView.this.listener != null && NavigationView.this.listener.onNavigationItemSelected(item);
            }

            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public void onMenuModeChange(MenuBuilder menu) {
            }
        });
        this.presenter.setId(1);
        this.presenter.initForMenu(context2, this.menu);
        this.presenter.setItemIconTintList(itemIconTint);
        this.presenter.setOverScrollMode(getOverScrollMode());
        if (textAppearanceSet) {
            this.presenter.setItemTextAppearance(textAppearance);
        }
        this.presenter.setItemTextColor(itemTextColor);
        this.presenter.setItemBackground(itemBackground);
        this.presenter.setItemIconPadding(itemIconPadding);
        this.menu.addMenuPresenter(this.presenter);
        addView((View) this.presenter.getMenuView(this));
        if (a.hasValue(R.styleable.NavigationView_menu)) {
            inflateMenu(a.getResourceId(R.styleable.NavigationView_menu, 0));
        }
        if (a.hasValue(R.styleable.NavigationView_headerLayout)) {
            inflateHeaderView(a.getResourceId(R.styleable.NavigationView_headerLayout, 0));
        }
        a.recycle();
        setupInsetScrimsListener();
    }

    @Override // android.view.View
    public void setOverScrollMode(int overScrollMode) {
        super.setOverScrollMode(overScrollMode);
        if (this.presenter != null) {
            this.presenter.setOverScrollMode(overScrollMode);
        }
    }

    private boolean hasShapeAppearance(@NonNull TintTypedArray a) {
        return a.hasValue(R.styleable.NavigationView_itemShapeAppearance) || a.hasValue(R.styleable.NavigationView_itemShapeAppearanceOverlay);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.material.internal.ScrimInsetsFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.view.View
    public void setElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.setElevation(elevation);
        }
        MaterialShapeUtils.setElevation(this, elevation);
    }

    @NonNull
    private final Drawable createDefaultItemBackground(@NonNull TintTypedArray a) {
        int shapeAppearanceResId = a.getResourceId(R.styleable.NavigationView_itemShapeAppearance, 0);
        int shapeAppearanceOverlayResId = a.getResourceId(R.styleable.NavigationView_itemShapeAppearanceOverlay, 0);
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(getContext(), shapeAppearanceResId, shapeAppearanceOverlayResId).build());
        materialShapeDrawable.setFillColor(MaterialResources.getColorStateList(getContext(), a, R.styleable.NavigationView_itemShapeFillColor));
        int insetLeft = a.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetStart, 0);
        int insetTop = a.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetTop, 0);
        int insetRight = a.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetEnd, 0);
        int insetBottom = a.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetBottom, 0);
        return new InsetDrawable((Drawable) materialShapeDrawable, insetLeft, insetTop, insetRight, insetBottom);
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState state = new SavedState(superState);
        state.menuState = new Bundle();
        this.menu.savePresenterStates(state.menuState);
        return state;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable savedState) {
        if (!(savedState instanceof SavedState)) {
            super.onRestoreInstanceState(savedState);
            return;
        }
        SavedState state = (SavedState) savedState;
        super.onRestoreInstanceState(state.getSuperState());
        this.menu.restorePresenterStates(state.menuState);
    }

    public void setNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener listener) {
        this.listener = listener;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthSpec, int heightSpec) {
        switch (View.MeasureSpec.getMode(widthSpec)) {
            case Integer.MIN_VALUE:
                widthSpec = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(widthSpec), this.maxWidth), 1073741824);
                break;
            case 0:
                widthSpec = View.MeasureSpec.makeMeasureSpec(this.maxWidth, 1073741824);
                break;
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override // com.google.android.material.internal.ScrimInsetsFrameLayout
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected void onInsetsChanged(@NonNull WindowInsetsCompat insets) {
        this.presenter.dispatchApplyWindowInsets(insets);
    }

    public void inflateMenu(int resId) {
        this.presenter.setUpdateSuspended(true);
        getMenuInflater().inflate(resId, this.menu);
        this.presenter.setUpdateSuspended(false);
        this.presenter.updateMenuView(false);
    }

    @NonNull
    public Menu getMenu() {
        return this.menu;
    }

    public View inflateHeaderView(@LayoutRes int res) {
        return this.presenter.inflateHeaderView(res);
    }

    public void addHeaderView(@NonNull View view) {
        this.presenter.addHeaderView(view);
    }

    public void removeHeaderView(@NonNull View view) {
        this.presenter.removeHeaderView(view);
    }

    public int getHeaderCount() {
        return this.presenter.getHeaderCount();
    }

    public View getHeaderView(int index) {
        return this.presenter.getHeaderView(index);
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.presenter.getItemTintList();
    }

    public void setItemIconTintList(@Nullable ColorStateList tint) {
        this.presenter.setItemIconTintList(tint);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.presenter.getItemTextColor();
    }

    public void setItemTextColor(@Nullable ColorStateList textColor) {
        this.presenter.setItemTextColor(textColor);
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.presenter.getItemBackground();
    }

    public void setItemBackgroundResource(@DrawableRes int resId) {
        setItemBackground(ContextCompat.getDrawable(getContext(), resId));
    }

    public void setItemBackground(@Nullable Drawable itemBackground) {
        this.presenter.setItemBackground(itemBackground);
    }

    @Dimension
    public int getItemHorizontalPadding() {
        return this.presenter.getItemHorizontalPadding();
    }

    public void setItemHorizontalPadding(@Dimension int padding) {
        this.presenter.setItemHorizontalPadding(padding);
    }

    public void setItemHorizontalPaddingResource(@DimenRes int paddingResource) {
        this.presenter.setItemHorizontalPadding(getResources().getDimensionPixelSize(paddingResource));
    }

    @Dimension
    public int getItemIconPadding() {
        return this.presenter.getItemIconPadding();
    }

    public void setItemIconPadding(@Dimension int padding) {
        this.presenter.setItemIconPadding(padding);
    }

    public void setItemIconPaddingResource(int paddingResource) {
        this.presenter.setItemIconPadding(getResources().getDimensionPixelSize(paddingResource));
    }

    public void setCheckedItem(@IdRes int id) {
        MenuItem item = this.menu.findItem(id);
        if (item != null) {
            this.presenter.setCheckedItem((MenuItemImpl) item);
        }
    }

    public void setCheckedItem(@NonNull MenuItem checkedItem) {
        MenuItem item = this.menu.findItem(checkedItem.getItemId());
        if (item != null) {
            this.presenter.setCheckedItem((MenuItemImpl) item);
            return;
        }
        throw new IllegalArgumentException("Called setCheckedItem(MenuItem) with an item that is not in the current menu.");
    }

    @Nullable
    public MenuItem getCheckedItem() {
        return this.presenter.getCheckedItem();
    }

    public void setItemTextAppearance(@StyleRes int resId) {
        this.presenter.setItemTextAppearance(resId);
    }

    public void setItemIconSize(@Dimension int iconSize) {
        this.presenter.setItemIconSize(iconSize);
    }

    public void setItemMaxLines(int itemMaxLines) {
        this.presenter.setItemMaxLines(itemMaxLines);
    }

    public int getItemMaxLines() {
        return this.presenter.getItemMaxLines();
    }

    private MenuInflater getMenuInflater() {
        if (this.menuInflater == null) {
            this.menuInflater = new SupportMenuInflater(getContext());
        }
        return this.menuInflater;
    }

    @Nullable
    private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        TypedValue value = new TypedValue();
        if (getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            ColorStateList baseColor = AppCompatResources.getColorStateList(getContext(), value.resourceId);
            if (getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, value, true)) {
                int colorPrimary = value.data;
                int defaultColor = baseColor.getDefaultColor();
                return new ColorStateList(new int[][]{DISABLED_STATE_SET, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{baseColor.getColorForState(DISABLED_STATE_SET, defaultColor), colorPrimary, defaultColor});
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.material.internal.ScrimInsetsFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT < 16) {
            getViewTreeObserver().removeGlobalOnLayoutListener(this.onGlobalLayoutListener);
        } else {
            getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
        }
    }

    private void setupInsetScrimsListener() {
        this.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.google.android.material.navigation.NavigationView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                boolean z = true;
                NavigationView.this.getLocationOnScreen(NavigationView.this.tmpLocation);
                boolean isBehindStatusBar = NavigationView.this.tmpLocation[1] == 0;
                NavigationView.this.presenter.setBehindStatusBar(isBehindStatusBar);
                NavigationView.this.setDrawTopInsetForeground(isBehindStatusBar);
                Activity activity = ContextUtils.getActivity(NavigationView.this.getContext());
                if (activity != null && Build.VERSION.SDK_INT >= 21) {
                    boolean isBehindSystemNav = activity.findViewById(16908290).getHeight() == NavigationView.this.getHeight();
                    boolean hasNonZeroAlpha = Color.alpha(activity.getWindow().getNavigationBarColor()) != 0;
                    NavigationView navigationView = NavigationView.this;
                    if (!isBehindSystemNav || !hasNonZeroAlpha) {
                        z = false;
                    }
                    navigationView.setDrawBottomInsetForeground(z);
                }
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }

    /* loaded from: classes.dex */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.navigation.NavigationView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            @NonNull
            public SavedState createFromParcel(@NonNull Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Override // android.os.Parcelable.Creator
            @Nullable
            public SavedState createFromParcel(@NonNull Parcel in) {
                return new SavedState(in, null);
            }

            @Override // android.os.Parcelable.Creator
            @NonNull
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        @Nullable
        public Bundle menuState;

        public SavedState(@NonNull Parcel in, @Nullable ClassLoader loader) {
            super(in, loader);
            this.menuState = in.readBundle(loader);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeBundle(this.menuState);
        }
    }
}
