package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.animation.AnimationUtils;

/* loaded from: classes.dex */
public class ElasticTabIndicatorInterpolator extends TabIndicatorInterpolator {
    private static float decInterp(@FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        return (float) Math.sin((fraction * 3.141592653589793d) / 2.0d);
    }

    private static float accInterp(@FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        return (float) (1.0d - Math.cos((fraction * 3.141592653589793d) / 2.0d));
    }

    @Override // com.google.android.material.tabs.TabIndicatorInterpolator
    public void setIndicatorBoundsForOffset(TabLayout tabLayout, View startTitle, View endTitle, float offset, @NonNull Drawable indicator) {
        float leftFraction;
        float rightFraction;
        RectF startIndicator = calculateIndicatorWidthForTab(tabLayout, startTitle);
        RectF endIndicator = calculateIndicatorWidthForTab(tabLayout, endTitle);
        boolean isMovingRight = startIndicator.left < endIndicator.left;
        if (isMovingRight) {
            leftFraction = accInterp(offset);
            rightFraction = decInterp(offset);
        } else {
            leftFraction = decInterp(offset);
            rightFraction = accInterp(offset);
        }
        indicator.setBounds(AnimationUtils.lerp((int) startIndicator.left, (int) endIndicator.left, leftFraction), indicator.getBounds().top, AnimationUtils.lerp((int) startIndicator.right, (int) endIndicator.right, rightFraction), indicator.getBounds().bottom);
    }
}
