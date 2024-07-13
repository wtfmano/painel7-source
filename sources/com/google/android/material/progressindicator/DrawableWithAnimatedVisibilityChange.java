package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Property;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class DrawableWithAnimatedVisibilityChange extends Drawable implements Animatable2Compat {
    private static final boolean DEFAULT_DRAWABLE_RESTART = false;
    private static final int GROW_DURATION = 500;
    private static final Property<DrawableWithAnimatedVisibilityChange, Float> GROW_FRACTION = new Property<DrawableWithAnimatedVisibilityChange, Float>(Float.class, "growFraction") { // from class: com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange.3
        @Override // android.util.Property
        public Float get(DrawableWithAnimatedVisibilityChange drawable) {
            return Float.valueOf(drawable.getGrowFraction());
        }

        @Override // android.util.Property
        public void set(DrawableWithAnimatedVisibilityChange drawable, Float value) {
            drawable.setGrowFraction(value.floatValue());
        }
    };
    private List<Animatable2Compat.AnimationCallback> animationCallbacks;
    final BaseProgressIndicatorSpec baseSpec;
    final Context context;
    private float growFraction;
    private ValueAnimator hideAnimator;
    private boolean ignoreCallbacks;
    private Animatable2Compat.AnimationCallback internalAnimationCallback;
    private float mockGrowFraction;
    private boolean mockHideAnimationRunning;
    private boolean mockShowAnimationRunning;
    private ValueAnimator showAnimator;
    private int totalAlpha;
    final Paint paint = new Paint();
    AnimatorDurationScaleProvider animatorDurationScaleProvider = new AnimatorDurationScaleProvider();

    public DrawableWithAnimatedVisibilityChange(@NonNull Context context, @NonNull BaseProgressIndicatorSpec baseSpec) {
        this.context = context;
        this.baseSpec = baseSpec;
        setAlpha(255);
    }

    private void maybeInitializeAnimators() {
        if (this.showAnimator == null) {
            this.showAnimator = ObjectAnimator.ofFloat(this, GROW_FRACTION, 0.0f, 1.0f);
            this.showAnimator.setDuration(500L);
            this.showAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            setShowAnimator(this.showAnimator);
        }
        if (this.hideAnimator == null) {
            this.hideAnimator = ObjectAnimator.ofFloat(this, GROW_FRACTION, 1.0f, 0.0f);
            this.hideAnimator.setDuration(500L);
            this.hideAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            setHideAnimator(this.hideAnimator);
        }
    }

    public void registerAnimationCallback(@NonNull Animatable2Compat.AnimationCallback callback) {
        if (this.animationCallbacks == null) {
            this.animationCallbacks = new ArrayList();
        }
        if (!this.animationCallbacks.contains(callback)) {
            this.animationCallbacks.add(callback);
        }
    }

    public boolean unregisterAnimationCallback(@NonNull Animatable2Compat.AnimationCallback callback) {
        if (this.animationCallbacks == null || !this.animationCallbacks.contains(callback)) {
            return false;
        }
        this.animationCallbacks.remove(callback);
        if (this.animationCallbacks.isEmpty()) {
            this.animationCallbacks = null;
        }
        return true;
    }

    public void clearAnimationCallbacks() {
        this.animationCallbacks.clear();
        this.animationCallbacks = null;
    }

    void setInternalAnimationCallback(@NonNull Animatable2Compat.AnimationCallback callback) {
        this.internalAnimationCallback = callback;
    }

    public void dispatchAnimationStart() {
        if (this.internalAnimationCallback != null) {
            this.internalAnimationCallback.onAnimationStart(this);
        }
        if (this.animationCallbacks != null && !this.ignoreCallbacks) {
            for (Animatable2Compat.AnimationCallback callback : this.animationCallbacks) {
                callback.onAnimationStart(this);
            }
        }
    }

    public void dispatchAnimationEnd() {
        if (this.internalAnimationCallback != null) {
            this.internalAnimationCallback.onAnimationEnd(this);
        }
        if (this.animationCallbacks != null && !this.ignoreCallbacks) {
            for (Animatable2Compat.AnimationCallback callback : this.animationCallbacks) {
                callback.onAnimationEnd(this);
            }
        }
    }

    public void start() {
        setVisibleInternal(true, true, false);
    }

    public void stop() {
        setVisibleInternal(false, true, false);
    }

    public boolean isRunning() {
        return isShowing() || isHiding();
    }

    public boolean isShowing() {
        return (this.showAnimator != null && this.showAnimator.isRunning()) || this.mockShowAnimationRunning;
    }

    public boolean isHiding() {
        return (this.hideAnimator != null && this.hideAnimator.isRunning()) || this.mockHideAnimationRunning;
    }

    public boolean hideNow() {
        return setVisible(false, false, false);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean visible, boolean restart) {
        return setVisible(visible, restart, true);
    }

    public boolean setVisible(boolean visible, boolean restart, boolean animate) {
        float systemAnimatorDurationScale = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
        return setVisibleInternal(visible, restart, animate && systemAnimatorDurationScale > 0.0f);
    }

    public boolean setVisibleInternal(boolean visible, boolean restart, boolean animate) {
        maybeInitializeAnimators();
        if (isVisible() || visible) {
            ValueAnimator animatorInAction = visible ? this.showAnimator : this.hideAnimator;
            if (!animate) {
                if (animatorInAction.isRunning()) {
                    animatorInAction.end();
                } else {
                    endAnimatorWithoutCallbacks(animatorInAction);
                }
                return super.setVisible(visible, false);
            } else if (animate && animatorInAction.isRunning()) {
                return false;
            } else {
                boolean changed = !visible || super.setVisible(visible, false);
                boolean specAnimationEnabled = visible ? this.baseSpec.isShowAnimationEnabled() : this.baseSpec.isHideAnimationEnabled();
                if (specAnimationEnabled) {
                    if (restart || Build.VERSION.SDK_INT < 19 || !animatorInAction.isPaused()) {
                        animatorInAction.start();
                        return changed;
                    }
                    animatorInAction.resume();
                    return changed;
                }
                endAnimatorWithoutCallbacks(animatorInAction);
                return changed;
            }
        }
        return false;
    }

    private void endAnimatorWithoutCallbacks(@NonNull ValueAnimator... animators) {
        boolean ignoreCallbacksOrig = this.ignoreCallbacks;
        this.ignoreCallbacks = true;
        for (ValueAnimator animator : animators) {
            animator.end();
        }
        this.ignoreCallbacks = ignoreCallbacksOrig;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.totalAlpha = alpha;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.totalAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    private void setShowAnimator(@NonNull ValueAnimator showAnimator) {
        if (this.showAnimator != null && this.showAnimator.isRunning()) {
            throw new IllegalArgumentException("Cannot set showAnimator while the current showAnimator is running.");
        }
        this.showAnimator = showAnimator;
        showAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                DrawableWithAnimatedVisibilityChange.this.dispatchAnimationStart();
            }
        });
    }

    @NonNull
    ValueAnimator getHideAnimator() {
        return this.hideAnimator;
    }

    private void setHideAnimator(@NonNull ValueAnimator hideAnimator) {
        if (this.hideAnimator != null && this.hideAnimator.isRunning()) {
            throw new IllegalArgumentException("Cannot set hideAnimator while the current hideAnimator is running.");
        }
        this.hideAnimator = hideAnimator;
        hideAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                DrawableWithAnimatedVisibilityChange.super.setVisible(false, false);
                DrawableWithAnimatedVisibilityChange.this.dispatchAnimationEnd();
            }
        });
    }

    public float getGrowFraction() {
        if (!this.baseSpec.isShowAnimationEnabled() && !this.baseSpec.isHideAnimationEnabled()) {
            return 1.0f;
        }
        if (this.mockHideAnimationRunning || this.mockShowAnimationRunning) {
            return this.mockGrowFraction;
        }
        return this.growFraction;
    }

    public void setGrowFraction(@FloatRange(from = 0.0d, to = 1.0d) float growFraction) {
        if (this.growFraction != growFraction) {
            this.growFraction = growFraction;
            invalidateSelf();
        }
    }

    @VisibleForTesting
    void setMockShowAnimationRunning(boolean running, @FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        this.mockShowAnimationRunning = running;
        this.mockGrowFraction = fraction;
    }

    @VisibleForTesting
    void setMockHideAnimationRunning(boolean running, @FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        this.mockHideAnimationRunning = running;
        this.mockGrowFraction = fraction;
    }
}
