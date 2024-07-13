package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class Hold extends Visibility {
    @Override // android.transition.Visibility
    @NonNull
    public Animator onAppear(@NonNull ViewGroup sceneRoot, @NonNull View view, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        return ValueAnimator.ofFloat(0.0f);
    }

    @Override // android.transition.Visibility
    @NonNull
    public Animator onDisappear(@NonNull ViewGroup sceneRoot, @NonNull View view, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        return ValueAnimator.ofFloat(0.0f);
    }
}
