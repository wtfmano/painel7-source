package com.google.android.material.navigationrail;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.navigation.NavigationBarView;

/* loaded from: classes.dex */
public class NavigationRailView extends NavigationBarView {
    private static final int DEFAULT_HEADER_GRAVITY = 49;
    static final int DEFAULT_MENU_GRAVITY = 49;
    static final int MAX_ITEM_COUNT = 7;
    @Nullable
    private View headerView;
    private final int topMargin;

    public NavigationRailView(@NonNull Context context) {
        this(context, null);
    }

    public NavigationRailView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.navigationRailStyle);
    }

    public NavigationRailView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.Widget_MaterialComponents_NavigationRailView);
    }

    public NavigationRailView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Resources res = getResources();
        this.topMargin = res.getDimensionPixelSize(R.dimen.mtrl_navigation_rail_margin);
        Context context2 = getContext();
        TintTypedArray attributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attrs, R.styleable.NavigationRailView, defStyleAttr, defStyleRes, new int[0]);
        int headerLayoutRes = attributes.getResourceId(R.styleable.NavigationRailView_headerLayout, 0);
        if (headerLayoutRes != 0) {
            addHeaderView(headerLayoutRes);
        }
        setMenuGravity(attributes.getInt(R.styleable.NavigationRailView_menuGravity, 49));
        attributes.recycle();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minWidthSpec = makeMinWidthSpec(widthMeasureSpec);
        super.onMeasure(minWidthSpec, heightMeasureSpec);
        if (isHeaderViewVisible()) {
            int maxMenuHeight = (getMeasuredHeight() - this.headerView.getMeasuredHeight()) - this.topMargin;
            int menuHeightSpec = View.MeasureSpec.makeMeasureSpec(maxMenuHeight, Integer.MIN_VALUE);
            measureChild(getNavigationRailMenuView(), minWidthSpec, menuHeightSpec);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        NavigationRailMenuView menuView = getNavigationRailMenuView();
        int offsetY = 0;
        if (isHeaderViewVisible()) {
            int usedTop = this.headerView.getBottom() + this.topMargin;
            int menuTop = menuView.getTop();
            if (menuTop < usedTop) {
                offsetY = usedTop - menuTop;
            }
        } else if (menuView.isTopGravity()) {
            offsetY = this.topMargin;
        }
        if (offsetY > 0) {
            menuView.layout(menuView.getLeft(), menuView.getTop() + offsetY, menuView.getRight(), menuView.getBottom() + offsetY);
        }
    }

    public void addHeaderView(@LayoutRes int layoutRes) {
        addHeaderView(LayoutInflater.from(getContext()).inflate(layoutRes, (ViewGroup) this, false));
    }

    public void addHeaderView(@NonNull View headerView) {
        removeHeaderView();
        this.headerView = headerView;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = 49;
        params.topMargin = this.topMargin;
        addView(headerView, 0, params);
    }

    @Nullable
    public View getHeaderView() {
        return this.headerView;
    }

    public void removeHeaderView() {
        if (this.headerView != null) {
            removeView(this.headerView);
            this.headerView = null;
        }
    }

    public void setMenuGravity(int gravity) {
        getNavigationRailMenuView().setMenuGravity(gravity);
    }

    public int getMenuGravity() {
        return getNavigationRailMenuView().getMenuGravity();
    }

    @Override // com.google.android.material.navigation.NavigationBarView
    public int getMaxItemCount() {
        return 7;
    }

    private NavigationRailMenuView getNavigationRailMenuView() {
        return (NavigationRailMenuView) getMenuView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.material.navigation.NavigationBarView
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public NavigationRailMenuView createNavigationBarMenuView(@NonNull Context context) {
        return new NavigationRailMenuView(context);
    }

    private int makeMinWidthSpec(int measureSpec) {
        int minWidth = getSuggestedMinimumWidth();
        if (View.MeasureSpec.getMode(measureSpec) != 1073741824 && minWidth > 0) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(measureSpec), minWidth + getPaddingLeft() + getPaddingRight()), 1073741824);
        }
        return measureSpec;
    }

    private boolean isHeaderViewVisible() {
        return (this.headerView == null || this.headerView.getVisibility() == 8) ? false : true;
    }
}
