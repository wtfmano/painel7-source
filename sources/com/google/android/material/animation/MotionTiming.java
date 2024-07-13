package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class MotionTiming {
    private long delay;
    private long duration;
    @Nullable
    private TimeInterpolator interpolator;
    private int repeatCount;
    private int repeatMode;

    public MotionTiming(long delay, long duration) {
        this.delay = 0L;
        this.duration = 300L;
        this.interpolator = null;
        this.repeatCount = 0;
        this.repeatMode = 1;
        this.delay = delay;
        this.duration = duration;
    }

    public MotionTiming(long delay, long duration, @NonNull TimeInterpolator interpolator) {
        this.delay = 0L;
        this.duration = 300L;
        this.interpolator = null;
        this.repeatCount = 0;
        this.repeatMode = 1;
        this.delay = delay;
        this.duration = duration;
        this.interpolator = interpolator;
    }

    public void apply(@NonNull Animator animator) {
        animator.setStartDelay(getDelay());
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        if (animator instanceof ValueAnimator) {
            ((ValueAnimator) animator).setRepeatCount(getRepeatCount());
            ((ValueAnimator) animator).setRepeatMode(getRepeatMode());
        }
    }

    public long getDelay() {
        return this.delay;
    }

    public long getDuration() {
        return this.duration;
    }

    @Nullable
    public TimeInterpolator getInterpolator() {
        return this.interpolator != null ? this.interpolator : AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    }

    public int getRepeatCount() {
        return this.repeatCount;
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    @NonNull
    public static MotionTiming createFromAnimator(@NonNull ValueAnimator animator) {
        MotionTiming timing = new MotionTiming(animator.getStartDelay(), animator.getDuration(), getInterpolatorCompat(animator));
        timing.repeatCount = animator.getRepeatCount();
        timing.repeatMode = animator.getRepeatMode();
        return timing;
    }

    private static TimeInterpolator getInterpolatorCompat(@NonNull ValueAnimator animator) {
        TimeInterpolator interpolator = animator.getInterpolator();
        if ((interpolator instanceof AccelerateDecelerateInterpolator) || interpolator == null) {
            return AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
        }
        if (interpolator instanceof AccelerateInterpolator) {
            return AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
        }
        if (interpolator instanceof DecelerateInterpolator) {
            return AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
        }
        return interpolator;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof MotionTiming) {
            MotionTiming that = (MotionTiming) o;
            if (getDelay() == that.getDelay() && getDuration() == that.getDuration() && getRepeatCount() == that.getRepeatCount() && getRepeatMode() == that.getRepeatMode()) {
                return getInterpolator().getClass().equals(that.getInterpolator().getClass());
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        int result = (int) (getDelay() ^ (getDelay() >>> 32));
        return (((((((result * 31) + ((int) (getDuration() ^ (getDuration() >>> 32)))) * 31) + getInterpolator().getClass().hashCode()) * 31) + getRepeatCount()) * 31) + getRepeatMode();
    }

    @NonNull
    public String toString() {
        return '\n' + getClass().getName() + '{' + Integer.toHexString(System.identityHashCode(this)) + " delay: " + getDelay() + " duration: " + getDuration() + " interpolator: " + getInterpolator().getClass() + " repeatCount: " + getRepeatCount() + " repeatMode: " + getRepeatMode() + "}\n";
    }
}
