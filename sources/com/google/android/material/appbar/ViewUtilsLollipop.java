package com.google.android.material.appbar;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.motion.widget.Key;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;

/* JADX INFO: Access modifiers changed from: package-private */
@RequiresApi(21)
/* loaded from: classes.dex */
public class ViewUtilsLollipop {
    private static final int[] STATE_LIST_ANIM_ATTRS = {16843848};

    ViewUtilsLollipop() {
    }

    public static void setBoundsViewOutlineProvider(@NonNull View view) {
        view.setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }

    public static void setStateListAnimatorFromAttrs(@NonNull View view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context = view.getContext();
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, STATE_LIST_ANIM_ATTRS, defStyleAttr, defStyleRes, new int[0]);
        try {
            if (a.hasValue(0)) {
                StateListAnimator sla = AnimatorInflater.loadStateListAnimator(context, a.getResourceId(0, 0));
                view.setStateListAnimator(sla);
            }
        } finally {
            a.recycle();
        }
    }

    public static void setDefaultAppBarLayoutStateListAnimator(@NonNull View view, float elevation) {
        int dur = view.getResources().getInteger(R.integer.app_bar_elevation_anim_duration);
        StateListAnimator sla = new StateListAnimator();
        sla.addState(new int[]{16842766, R.attr.state_liftable, -R.attr.state_lifted}, ObjectAnimator.ofFloat(view, Key.ELEVATION, 0.0f).setDuration(dur));
        sla.addState(new int[]{16842766}, ObjectAnimator.ofFloat(view, Key.ELEVATION, elevation).setDuration(dur));
        sla.addState(new int[0], ObjectAnimator.ofFloat(view, Key.ELEVATION, 0.0f).setDuration(0L));
        view.setStateListAnimator(sla);
    }
}
