package com.google.android.material.appbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes.dex */
public class MaterialToolbar extends Toolbar {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Toolbar;
    @Nullable
    private Integer navigationIconTint;
    private boolean subtitleCentered;
    private boolean titleCentered;

    public MaterialToolbar(@NonNull Context context) {
        this(context, null);
    }

    public MaterialToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public MaterialToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        Context context2 = getContext();
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.MaterialToolbar, defStyleAttr, DEF_STYLE_RES, new int[0]);
        if (a.hasValue(R.styleable.MaterialToolbar_navigationIconTint)) {
            setNavigationIconTint(a.getColor(R.styleable.MaterialToolbar_navigationIconTint, -1));
        }
        this.titleCentered = a.getBoolean(R.styleable.MaterialToolbar_titleCentered, false);
        this.subtitleCentered = a.getBoolean(R.styleable.MaterialToolbar_subtitleCentered, false);
        a.recycle();
        initBackground(context2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        maybeCenterTitleViews();
    }

    private void maybeCenterTitleViews() {
        if (this.titleCentered || this.subtitleCentered) {
            TextView titleTextView = ToolbarUtils.getTitleTextView(this);
            TextView subtitleTextView = ToolbarUtils.getSubtitleTextView(this);
            if (titleTextView != null || subtitleTextView != null) {
                Pair<Integer, Integer> titleBoundLimits = calculateTitleBoundLimits(titleTextView, subtitleTextView);
                if (this.titleCentered && titleTextView != null) {
                    layoutTitleCenteredHorizontally(titleTextView, titleBoundLimits);
                }
                if (this.subtitleCentered && subtitleTextView != null) {
                    layoutTitleCenteredHorizontally(subtitleTextView, titleBoundLimits);
                }
            }
        }
    }

    private Pair<Integer, Integer> calculateTitleBoundLimits(@Nullable TextView titleTextView, @Nullable TextView subtitleTextView) {
        int width = getMeasuredWidth();
        int midpoint = width / 2;
        int leftLimit = getPaddingLeft();
        int rightLimit = width - getPaddingRight();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8 && child != titleTextView && child != subtitleTextView) {
                if (child.getRight() < midpoint && child.getRight() > leftLimit) {
                    leftLimit = child.getRight();
                }
                if (child.getLeft() > midpoint && child.getLeft() < rightLimit) {
                    rightLimit = child.getLeft();
                }
            }
        }
        return new Pair<>(Integer.valueOf(leftLimit), Integer.valueOf(rightLimit));
    }

    private void layoutTitleCenteredHorizontally(View titleView, Pair<Integer, Integer> titleBoundLimits) {
        int width = getMeasuredWidth();
        int titleWidth = titleView.getMeasuredWidth();
        int titleLeft = (width / 2) - (titleWidth / 2);
        int titleRight = titleLeft + titleWidth;
        int leftOverlap = Math.max(((Integer) titleBoundLimits.first).intValue() - titleLeft, 0);
        int rightOverlap = Math.max(titleRight - ((Integer) titleBoundLimits.second).intValue(), 0);
        int overlap = Math.max(leftOverlap, rightOverlap);
        if (overlap > 0) {
            titleLeft += overlap;
            titleRight -= overlap;
            titleView.measure(View.MeasureSpec.makeMeasureSpec(titleRight - titleLeft, 1073741824), titleView.getMeasuredHeightAndState());
        }
        titleView.layout(titleLeft, titleView.getTop(), titleRight, titleView.getBottom());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.view.View
    @RequiresApi(21)
    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeUtils.setElevation(this, elevation);
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setNavigationIcon(@Nullable Drawable drawable) {
        super.setNavigationIcon(maybeTintNavigationIcon(drawable));
    }

    public void setNavigationIconTint(@ColorInt int navigationIconTint) {
        this.navigationIconTint = Integer.valueOf(navigationIconTint);
        Drawable navigationIcon = getNavigationIcon();
        if (navigationIcon != null) {
            setNavigationIcon(navigationIcon);
        }
    }

    public void setTitleCentered(boolean titleCentered) {
        if (this.titleCentered != titleCentered) {
            this.titleCentered = titleCentered;
            requestLayout();
        }
    }

    public boolean isTitleCentered() {
        return this.titleCentered;
    }

    public void setSubtitleCentered(boolean subtitleCentered) {
        if (this.subtitleCentered != subtitleCentered) {
            this.subtitleCentered = subtitleCentered;
            requestLayout();
        }
    }

    public boolean isSubtitleCentered() {
        return this.subtitleCentered;
    }

    private void initBackground(Context context) {
        Drawable background = getBackground();
        if (background == null || (background instanceof ColorDrawable)) {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            int backgroundColor = background != null ? ((ColorDrawable) background).getColor() : 0;
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(backgroundColor));
            materialShapeDrawable.initializeElevationOverlay(context);
            materialShapeDrawable.setElevation(ViewCompat.getElevation(this));
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
    }

    @Nullable
    private Drawable maybeTintNavigationIcon(@Nullable Drawable navigationIcon) {
        if (navigationIcon == null || this.navigationIconTint == null) {
            return navigationIcon;
        }
        Drawable wrappedNavigationIcon = DrawableCompat.wrap(navigationIcon);
        DrawableCompat.setTint(wrappedNavigationIcon, this.navigationIconTint.intValue());
        return wrappedNavigationIcon;
    }
}
