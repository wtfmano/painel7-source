package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class StateListAnimator {
    private final ArrayList<Tuple> tuples = new ArrayList<>();
    @Nullable
    private Tuple lastMatch = null;
    @Nullable
    ValueAnimator runningAnimator = null;
    private final Animator.AnimatorListener animationListener = new AnimatorListenerAdapter() { // from class: com.google.android.material.internal.StateListAnimator.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (StateListAnimator.this.runningAnimator == animator) {
                StateListAnimator.this.runningAnimator = null;
            }
        }
    };

    public void addState(int[] specs, ValueAnimator animator) {
        Tuple tuple = new Tuple(specs, animator);
        animator.addListener(this.animationListener);
        this.tuples.add(tuple);
    }

    public void setState(int[] state) {
        Tuple match = null;
        int count = this.tuples.size();
        int i = 0;
        while (true) {
            if (i >= count) {
                break;
            }
            Tuple tuple = this.tuples.get(i);
            if (!StateSet.stateSetMatches(tuple.specs, state)) {
                i++;
            } else {
                match = tuple;
                break;
            }
        }
        if (match != this.lastMatch) {
            if (this.lastMatch != null) {
                cancel();
            }
            this.lastMatch = match;
            if (match != null) {
                start(match);
            }
        }
    }

    private void start(@NonNull Tuple match) {
        this.runningAnimator = match.animator;
        this.runningAnimator.start();
    }

    private void cancel() {
        if (this.runningAnimator != null) {
            this.runningAnimator.cancel();
            this.runningAnimator = null;
        }
    }

    public void jumpToCurrentState() {
        if (this.runningAnimator != null) {
            this.runningAnimator.end();
            this.runningAnimator = null;
        }
    }

    /* loaded from: classes.dex */
    public static class Tuple {
        final ValueAnimator animator;
        final int[] specs;

        Tuple(int[] specs, ValueAnimator animator) {
            this.specs = specs;
            this.animator = animator;
        }
    }
}
