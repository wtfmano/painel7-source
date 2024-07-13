package com.google.android.material.ripple;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.StateSet;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.graphics.ColorUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class RippleUtils {
    private static final int[] ENABLED_PRESSED_STATE_SET;
    private static final int[] FOCUSED_STATE_SET;
    private static final int[] HOVERED_FOCUSED_STATE_SET;
    private static final int[] HOVERED_STATE_SET;
    @VisibleForTesting
    static final String LOG_TAG;
    private static final int[] PRESSED_STATE_SET;
    private static final int[] SELECTED_FOCUSED_STATE_SET;
    private static final int[] SELECTED_HOVERED_FOCUSED_STATE_SET;
    private static final int[] SELECTED_HOVERED_STATE_SET;
    private static final int[] SELECTED_PRESSED_STATE_SET;
    private static final int[] SELECTED_STATE_SET;
    @VisibleForTesting
    static final String TRANSPARENT_DEFAULT_COLOR_WARNING = "Use a non-transparent color for the default color as it will be used to finish ripple animations.";
    public static final boolean USE_FRAMEWORK_RIPPLE;

    static {
        USE_FRAMEWORK_RIPPLE = Build.VERSION.SDK_INT >= 21;
        PRESSED_STATE_SET = new int[]{16842919};
        HOVERED_FOCUSED_STATE_SET = new int[]{16843623, 16842908};
        FOCUSED_STATE_SET = new int[]{16842908};
        HOVERED_STATE_SET = new int[]{16843623};
        SELECTED_PRESSED_STATE_SET = new int[]{16842913, 16842919};
        SELECTED_HOVERED_FOCUSED_STATE_SET = new int[]{16842913, 16843623, 16842908};
        SELECTED_FOCUSED_STATE_SET = new int[]{16842913, 16842908};
        SELECTED_HOVERED_STATE_SET = new int[]{16842913, 16843623};
        SELECTED_STATE_SET = new int[]{16842913};
        ENABLED_PRESSED_STATE_SET = new int[]{16842910, 16842919};
        LOG_TAG = RippleUtils.class.getSimpleName();
    }

    private RippleUtils() {
    }

    @NonNull
    public static ColorStateList convertToRippleDrawableColor(@Nullable ColorStateList rippleColor) {
        if (USE_FRAMEWORK_RIPPLE) {
            int[][] states = new int[2];
            int[] colors = new int[2];
            states[0] = SELECTED_STATE_SET;
            colors[0] = getColorForState(rippleColor, SELECTED_PRESSED_STATE_SET);
            int i = 0 + 1;
            states[i] = StateSet.NOTHING;
            colors[i] = getColorForState(rippleColor, PRESSED_STATE_SET);
            int i2 = i + 1;
            return new ColorStateList(states, colors);
        }
        int[][] states2 = new int[10];
        int[] colors2 = new int[10];
        states2[0] = SELECTED_PRESSED_STATE_SET;
        colors2[0] = getColorForState(rippleColor, SELECTED_PRESSED_STATE_SET);
        int i3 = 0 + 1;
        states2[i3] = SELECTED_HOVERED_FOCUSED_STATE_SET;
        colors2[i3] = getColorForState(rippleColor, SELECTED_HOVERED_FOCUSED_STATE_SET);
        int i4 = i3 + 1;
        states2[i4] = SELECTED_FOCUSED_STATE_SET;
        colors2[i4] = getColorForState(rippleColor, SELECTED_FOCUSED_STATE_SET);
        int i5 = i4 + 1;
        states2[i5] = SELECTED_HOVERED_STATE_SET;
        colors2[i5] = getColorForState(rippleColor, SELECTED_HOVERED_STATE_SET);
        int i6 = i5 + 1;
        states2[i6] = SELECTED_STATE_SET;
        colors2[i6] = 0;
        int i7 = i6 + 1;
        states2[i7] = PRESSED_STATE_SET;
        colors2[i7] = getColorForState(rippleColor, PRESSED_STATE_SET);
        int i8 = i7 + 1;
        states2[i8] = HOVERED_FOCUSED_STATE_SET;
        colors2[i8] = getColorForState(rippleColor, HOVERED_FOCUSED_STATE_SET);
        int i9 = i8 + 1;
        states2[i9] = FOCUSED_STATE_SET;
        colors2[i9] = getColorForState(rippleColor, FOCUSED_STATE_SET);
        int i10 = i9 + 1;
        states2[i10] = HOVERED_STATE_SET;
        colors2[i10] = getColorForState(rippleColor, HOVERED_STATE_SET);
        int i11 = i10 + 1;
        states2[i11] = StateSet.NOTHING;
        colors2[i11] = 0;
        int i12 = i11 + 1;
        return new ColorStateList(states2, colors2);
    }

    @NonNull
    public static ColorStateList sanitizeRippleDrawableColor(@Nullable ColorStateList rippleColor) {
        if (rippleColor != null) {
            if (Build.VERSION.SDK_INT >= 22 && Build.VERSION.SDK_INT <= 27 && Color.alpha(rippleColor.getDefaultColor()) == 0 && Color.alpha(rippleColor.getColorForState(ENABLED_PRESSED_STATE_SET, 0)) != 0) {
                Log.w(LOG_TAG, TRANSPARENT_DEFAULT_COLOR_WARNING);
                return rippleColor;
            }
            return rippleColor;
        }
        return ColorStateList.valueOf(0);
    }

    public static boolean shouldDrawRippleCompat(@NonNull int[] stateSet) {
        boolean enabled = false;
        boolean interactedState = false;
        for (int state : stateSet) {
            if (state == 16842910) {
                enabled = true;
            } else if (state == 16842908) {
                interactedState = true;
            } else if (state == 16842919) {
                interactedState = true;
            } else if (state == 16843623) {
                interactedState = true;
            }
        }
        return enabled && interactedState;
    }

    @ColorInt
    private static int getColorForState(@Nullable ColorStateList rippleColor, int[] state) {
        int color;
        if (rippleColor != null) {
            color = rippleColor.getColorForState(state, rippleColor.getDefaultColor());
        } else {
            color = 0;
        }
        return USE_FRAMEWORK_RIPPLE ? doubleAlpha(color) : color;
    }

    @ColorInt
    @TargetApi(21)
    private static int doubleAlpha(@ColorInt int color) {
        int alpha = Math.min(Color.alpha(color) * 2, 255);
        return ColorUtils.setAlphaComponent(color, alpha);
    }
}
