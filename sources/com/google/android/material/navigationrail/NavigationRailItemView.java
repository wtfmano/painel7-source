package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import androidx.annotation.DimenRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.google.android.material.R;
import com.google.android.material.navigation.NavigationBarItemView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
final class NavigationRailItemView extends NavigationBarItemView {
    public NavigationRailItemView(@NonNull Context context) {
        super(context);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (View.MeasureSpec.getMode(heightMeasureSpec) == 0) {
            int preferredHeight = View.MeasureSpec.getSize(heightMeasureSpec);
            int measuredHeight = getMeasuredHeight();
            int bestHeight = Math.max(measuredHeight, preferredHeight);
            setMeasuredDimension(getMeasuredWidthAndState(), View.resolveSizeAndState(bestHeight, heightMeasureSpec, 0));
        }
    }

    @Override // com.google.android.material.navigation.NavigationBarItemView
    @LayoutRes
    protected int getItemLayoutResId() {
        return R.layout.mtrl_navigation_rail_item;
    }

    @Override // com.google.android.material.navigation.NavigationBarItemView
    @DimenRes
    protected int getItemDefaultMarginResId() {
        return R.dimen.mtrl_navigation_rail_icon_margin;
    }
}
