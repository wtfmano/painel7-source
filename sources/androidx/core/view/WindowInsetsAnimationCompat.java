package androidx.core.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.R;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class WindowInsetsAnimationCompat {
    private static final boolean DEBUG = false;
    private static final String TAG = "WindowInsetsAnimCompat";
    private Impl mImpl;

    public WindowInsetsAnimationCompat(int typeMask, @Nullable Interpolator interpolator, long durationMillis) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImpl = new Impl30(typeMask, interpolator, durationMillis);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new Impl21(typeMask, interpolator, durationMillis);
        } else {
            this.mImpl = new Impl(0, interpolator, durationMillis);
        }
    }

    @RequiresApi(30)
    private WindowInsetsAnimationCompat(@NonNull WindowInsetsAnimation animation) {
        this(0, null, 0L);
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImpl = new Impl30(animation);
        }
    }

    public int getTypeMask() {
        return this.mImpl.getTypeMask();
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getFraction() {
        return this.mImpl.getFraction();
    }

    public float getInterpolatedFraction() {
        return this.mImpl.getInterpolatedFraction();
    }

    @Nullable
    public Interpolator getInterpolator() {
        return this.mImpl.getInterpolator();
    }

    public long getDurationMillis() {
        return this.mImpl.getDurationMillis();
    }

    public void setFraction(@FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        this.mImpl.setFraction(fraction);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getAlpha() {
        return this.mImpl.getAlpha();
    }

    public void setAlpha(@FloatRange(from = 0.0d, to = 1.0d) float alpha) {
        this.mImpl.setAlpha(alpha);
    }

    /* loaded from: classes.dex */
    public static final class BoundsCompat {
        private final Insets mLowerBound;
        private final Insets mUpperBound;

        public BoundsCompat(@NonNull Insets lowerBound, @NonNull Insets upperBound) {
            this.mLowerBound = lowerBound;
            this.mUpperBound = upperBound;
        }

        @RequiresApi(30)
        private BoundsCompat(@NonNull WindowInsetsAnimation.Bounds bounds) {
            this.mLowerBound = Impl30.getLowerBounds(bounds);
            this.mUpperBound = Impl30.getHigherBounds(bounds);
        }

        @NonNull
        public Insets getLowerBound() {
            return this.mLowerBound;
        }

        @NonNull
        public Insets getUpperBound() {
            return this.mUpperBound;
        }

        @NonNull
        public BoundsCompat inset(@NonNull Insets insets) {
            return new BoundsCompat(WindowInsetsCompat.insetInsets(this.mLowerBound, insets.left, insets.top, insets.right, insets.bottom), WindowInsetsCompat.insetInsets(this.mUpperBound, insets.left, insets.top, insets.right, insets.bottom));
        }

        public String toString() {
            return "Bounds{lower=" + this.mLowerBound + " upper=" + this.mUpperBound + "}";
        }

        @NonNull
        @RequiresApi(30)
        public WindowInsetsAnimation.Bounds toBounds() {
            return Impl30.createPlatformBounds(this);
        }

        @NonNull
        @RequiresApi(30)
        public static BoundsCompat toBoundsCompat(@NonNull WindowInsetsAnimation.Bounds bounds) {
            return new BoundsCompat(bounds);
        }
    }

    @RequiresApi(30)
    static WindowInsetsAnimationCompat toWindowInsetsAnimationCompat(WindowInsetsAnimation windowInsetsAnimation) {
        return new WindowInsetsAnimationCompat(windowInsetsAnimation);
    }

    /* loaded from: classes.dex */
    public static abstract class Callback {
        public static final int DISPATCH_MODE_CONTINUE_ON_SUBTREE = 1;
        public static final int DISPATCH_MODE_STOP = 0;
        WindowInsets mDispachedInsets;
        private final int mDispatchMode;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes.dex */
        public @interface DispatchMode {
        }

        @NonNull
        public abstract WindowInsetsCompat onProgress(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull List<WindowInsetsAnimationCompat> list);

        public Callback(int dispatchMode) {
            this.mDispatchMode = dispatchMode;
        }

        public final int getDispatchMode() {
            return this.mDispatchMode;
        }

        public void onPrepare(@NonNull WindowInsetsAnimationCompat animation) {
        }

        @NonNull
        public BoundsCompat onStart(@NonNull WindowInsetsAnimationCompat animation, @NonNull BoundsCompat bounds) {
            return bounds;
        }

        public void onEnd(@NonNull WindowInsetsAnimationCompat animation) {
        }
    }

    public static void setCallback(@NonNull View view, @Nullable Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            Impl30.setCallback(view, callback);
        } else if (Build.VERSION.SDK_INT >= 21) {
            Impl21.setCallback(view, callback);
        }
    }

    /* loaded from: classes.dex */
    public static class Impl {
        private float mAlpha;
        private final long mDurationMillis;
        private float mFraction;
        @Nullable
        private final Interpolator mInterpolator;
        private final int mTypeMask;

        Impl(int typeMask, @Nullable Interpolator interpolator, long durationMillis) {
            this.mTypeMask = typeMask;
            this.mInterpolator = interpolator;
            this.mDurationMillis = durationMillis;
        }

        public int getTypeMask() {
            return this.mTypeMask;
        }

        public float getFraction() {
            return this.mFraction;
        }

        public float getInterpolatedFraction() {
            return this.mInterpolator != null ? this.mInterpolator.getInterpolation(this.mFraction) : this.mFraction;
        }

        @Nullable
        public Interpolator getInterpolator() {
            return this.mInterpolator;
        }

        public long getDurationMillis() {
            return this.mDurationMillis;
        }

        public float getAlpha() {
            return this.mAlpha;
        }

        public void setFraction(float fraction) {
            this.mFraction = fraction;
        }

        public void setAlpha(float alpha) {
            this.mAlpha = alpha;
        }
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class Impl21 extends Impl {
        Impl21(int typeMask, @Nullable Interpolator interpolator, long durationMillis) {
            super(typeMask, interpolator, durationMillis);
        }

        static void setCallback(@NonNull View view, @Nullable Callback callback) {
            Object userListener = view.getTag(R.id.tag_on_apply_window_listener);
            if (callback == null) {
                view.setTag(R.id.tag_window_insets_animation_callback, null);
                if (userListener == null) {
                    view.setOnApplyWindowInsetsListener(null);
                    return;
                }
                return;
            }
            View.OnApplyWindowInsetsListener proxyListener = createProxyListener(view, callback);
            view.setTag(R.id.tag_window_insets_animation_callback, proxyListener);
            if (userListener == null) {
                view.setOnApplyWindowInsetsListener(proxyListener);
            }
        }

        @NonNull
        private static View.OnApplyWindowInsetsListener createProxyListener(@NonNull View view, @NonNull Callback callback) {
            return new Impl21OnApplyWindowInsetsListener(view, callback);
        }

        @NonNull
        static BoundsCompat computeAnimationBounds(@NonNull WindowInsetsCompat targetInsets, @NonNull WindowInsetsCompat startingInsets, int mask) {
            Insets targetInsetsInsets = targetInsets.getInsets(mask);
            Insets startingInsetsInsets = startingInsets.getInsets(mask);
            Insets lowerBound = Insets.of(Math.min(targetInsetsInsets.left, startingInsetsInsets.left), Math.min(targetInsetsInsets.top, startingInsetsInsets.top), Math.min(targetInsetsInsets.right, startingInsetsInsets.right), Math.min(targetInsetsInsets.bottom, startingInsetsInsets.bottom));
            Insets upperBound = Insets.of(Math.max(targetInsetsInsets.left, startingInsetsInsets.left), Math.max(targetInsetsInsets.top, startingInsetsInsets.top), Math.max(targetInsetsInsets.right, startingInsetsInsets.right), Math.max(targetInsetsInsets.bottom, startingInsetsInsets.bottom));
            return new BoundsCompat(lowerBound, upperBound);
        }

        @SuppressLint({"WrongConstant"})
        static int buildAnimationMask(@NonNull WindowInsetsCompat targetInsets, @NonNull WindowInsetsCompat currentInsets) {
            int animatingMask = 0;
            for (int i = 1; i <= 256; i <<= 1) {
                if (!targetInsets.getInsets(i).equals(currentInsets.getInsets(i))) {
                    animatingMask |= i;
                }
            }
            return animatingMask;
        }

        @SuppressLint({"WrongConstant"})
        static WindowInsetsCompat interpolateInsets(WindowInsetsCompat target, WindowInsetsCompat starting, float fraction, int typeMask) {
            WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(target);
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) == 0) {
                    builder.setInsets(i, target.getInsets(i));
                } else {
                    Insets targetInsets = target.getInsets(i);
                    Insets startingInsets = starting.getInsets(i);
                    Insets interpolatedInsets = WindowInsetsCompat.insetInsets(targetInsets, (int) (0.5d + ((targetInsets.left - startingInsets.left) * (1.0f - fraction))), (int) (0.5d + ((targetInsets.top - startingInsets.top) * (1.0f - fraction))), (int) (0.5d + ((targetInsets.right - startingInsets.right) * (1.0f - fraction))), (int) (0.5d + ((targetInsets.bottom - startingInsets.bottom) * (1.0f - fraction))));
                    builder.setInsets(i, interpolatedInsets);
                }
            }
            return builder.build();
        }

        @RequiresApi(21)
        /* loaded from: classes.dex */
        public static class Impl21OnApplyWindowInsetsListener implements View.OnApplyWindowInsetsListener {
            private static final int COMPAT_ANIMATION_DURATION = 160;
            final Callback mCallback;
            private WindowInsetsCompat mLastInsets;

            Impl21OnApplyWindowInsetsListener(@NonNull View view, @NonNull Callback callback) {
                WindowInsetsCompat windowInsetsCompat;
                this.mCallback = callback;
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view);
                if (rootWindowInsets != null) {
                    windowInsetsCompat = new WindowInsetsCompat.Builder(rootWindowInsets).build();
                } else {
                    windowInsetsCompat = null;
                }
                this.mLastInsets = windowInsetsCompat;
            }

            @Override // android.view.View.OnApplyWindowInsetsListener
            public WindowInsets onApplyWindowInsets(final View v, WindowInsets insets) {
                if (!v.isLaidOut()) {
                    this.mLastInsets = WindowInsetsCompat.toWindowInsetsCompat(insets, v);
                    return Impl21.forwardToViewIfNeeded(v, insets);
                }
                final WindowInsetsCompat targetInsets = WindowInsetsCompat.toWindowInsetsCompat(insets, v);
                if (this.mLastInsets == null) {
                    this.mLastInsets = ViewCompat.getRootWindowInsets(v);
                }
                if (this.mLastInsets == null) {
                    this.mLastInsets = targetInsets;
                    return Impl21.forwardToViewIfNeeded(v, insets);
                }
                Callback callback = Impl21.getCallback(v);
                if (callback != null && Objects.equals(callback.mDispachedInsets, insets)) {
                    return Impl21.forwardToViewIfNeeded(v, insets);
                }
                final int animationMask = Impl21.buildAnimationMask(targetInsets, this.mLastInsets);
                if (animationMask == 0) {
                    return Impl21.forwardToViewIfNeeded(v, insets);
                }
                final WindowInsetsCompat startingInsets = this.mLastInsets;
                final WindowInsetsAnimationCompat anim = new WindowInsetsAnimationCompat(animationMask, new DecelerateInterpolator(), 160L);
                anim.setFraction(0.0f);
                final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(anim.getDurationMillis());
                final BoundsCompat animationBounds = Impl21.computeAnimationBounds(targetInsets, startingInsets, animationMask);
                Impl21.dispatchOnPrepare(v, anim, insets, false);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.core.view.WindowInsetsAnimationCompat.Impl21.Impl21OnApplyWindowInsetsListener.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator animator2) {
                        anim.setFraction(animator2.getAnimatedFraction());
                        WindowInsetsCompat interpolateInsets = Impl21.interpolateInsets(targetInsets, startingInsets, anim.getInterpolatedFraction(), animationMask);
                        List<WindowInsetsAnimationCompat> runningAnimations = Collections.singletonList(anim);
                        Impl21.dispatchOnProgress(v, interpolateInsets, runningAnimations);
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.core.view.WindowInsetsAnimationCompat.Impl21.Impl21OnApplyWindowInsetsListener.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator2) {
                        anim.setFraction(1.0f);
                        Impl21.dispatchOnEnd(v, anim);
                    }
                });
                OneShotPreDrawListener.add(v, new Runnable() { // from class: androidx.core.view.WindowInsetsAnimationCompat.Impl21.Impl21OnApplyWindowInsetsListener.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Impl21.dispatchOnStart(v, anim, animationBounds);
                        animator.start();
                    }
                });
                this.mLastInsets = targetInsets;
                return Impl21.forwardToViewIfNeeded(v, insets);
            }
        }

        @NonNull
        static WindowInsets forwardToViewIfNeeded(@NonNull View v, @NonNull WindowInsets insets) {
            return v.getTag(R.id.tag_on_apply_window_listener) != null ? insets : v.onApplyWindowInsets(insets);
        }

        static void dispatchOnPrepare(View v, WindowInsetsAnimationCompat anim, WindowInsets insets, boolean stopDispatch) {
            Callback callback = getCallback(v);
            if (callback != null) {
                callback.mDispachedInsets = insets;
                if (!stopDispatch) {
                    callback.onPrepare(anim);
                    stopDispatch = callback.getDispatchMode() == 0;
                }
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View child = viewGroup.getChildAt(i);
                    dispatchOnPrepare(child, anim, insets, stopDispatch);
                }
            }
        }

        static void dispatchOnStart(View v, WindowInsetsAnimationCompat anim, BoundsCompat animationBounds) {
            Callback callback = getCallback(v);
            if (callback != null) {
                callback.onStart(anim, animationBounds);
                if (callback.getDispatchMode() == 0) {
                    return;
                }
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View child = viewGroup.getChildAt(i);
                    dispatchOnStart(child, anim, animationBounds);
                }
            }
        }

        static void dispatchOnProgress(@NonNull View v, @NonNull WindowInsetsCompat interpolateInsets, @NonNull List<WindowInsetsAnimationCompat> runningAnimations) {
            Callback callback = getCallback(v);
            WindowInsetsCompat insets = interpolateInsets;
            if (callback != null) {
                insets = callback.onProgress(insets, runningAnimations);
                if (callback.getDispatchMode() == 0) {
                    return;
                }
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View child = viewGroup.getChildAt(i);
                    dispatchOnProgress(child, insets, runningAnimations);
                }
            }
        }

        static void dispatchOnEnd(@NonNull View v, @NonNull WindowInsetsAnimationCompat anim) {
            Callback callback = getCallback(v);
            if (callback != null) {
                callback.onEnd(anim);
                if (callback.getDispatchMode() == 0) {
                    return;
                }
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View child = viewGroup.getChildAt(i);
                    dispatchOnEnd(child, anim);
                }
            }
        }

        @Nullable
        static Callback getCallback(View child) {
            Object listener = child.getTag(R.id.tag_window_insets_animation_callback);
            if (!(listener instanceof Impl21OnApplyWindowInsetsListener)) {
                return null;
            }
            Callback callback = ((Impl21OnApplyWindowInsetsListener) listener).mCallback;
            return callback;
        }
    }

    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class Impl30 extends Impl {
        @NonNull
        private final WindowInsetsAnimation mWrapped;

        Impl30(@NonNull WindowInsetsAnimation wrapped) {
            super(0, null, 0L);
            this.mWrapped = wrapped;
        }

        Impl30(int typeMask, Interpolator interpolator, long durationMillis) {
            this(new WindowInsetsAnimation(typeMask, interpolator, durationMillis));
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public int getTypeMask() {
            return this.mWrapped.getTypeMask();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        @Nullable
        public Interpolator getInterpolator() {
            return this.mWrapped.getInterpolator();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public long getDurationMillis() {
            return this.mWrapped.getDurationMillis();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public float getFraction() {
            return this.mWrapped.getFraction();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public void setFraction(float fraction) {
            this.mWrapped.setFraction(fraction);
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public float getInterpolatedFraction() {
            return this.mWrapped.getInterpolatedFraction();
        }

        @RequiresApi(30)
        /* loaded from: classes.dex */
        public static class ProxyCallback extends WindowInsetsAnimation.Callback {
            private final HashMap<WindowInsetsAnimation, WindowInsetsAnimationCompat> mAnimations;
            private final Callback mCompat;
            private List<WindowInsetsAnimationCompat> mRORunningAnimations;
            private ArrayList<WindowInsetsAnimationCompat> mTmpRunningAnimations;

            ProxyCallback(@NonNull Callback compat) {
                super(compat.getDispatchMode());
                this.mAnimations = new HashMap<>();
                this.mCompat = compat;
            }

            @NonNull
            private WindowInsetsAnimationCompat getWindowInsetsAnimationCompat(@NonNull WindowInsetsAnimation animation) {
                WindowInsetsAnimationCompat animationCompat = this.mAnimations.get(animation);
                if (animationCompat == null) {
                    WindowInsetsAnimationCompat animationCompat2 = WindowInsetsAnimationCompat.toWindowInsetsAnimationCompat(animation);
                    this.mAnimations.put(animation, animationCompat2);
                    return animationCompat2;
                }
                return animationCompat;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onPrepare(@NonNull WindowInsetsAnimation animation) {
                this.mCompat.onPrepare(getWindowInsetsAnimationCompat(animation));
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            @NonNull
            public WindowInsetsAnimation.Bounds onStart(@NonNull WindowInsetsAnimation animation, @NonNull WindowInsetsAnimation.Bounds bounds) {
                return this.mCompat.onStart(getWindowInsetsAnimationCompat(animation), BoundsCompat.toBoundsCompat(bounds)).toBounds();
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            @NonNull
            public WindowInsets onProgress(@NonNull WindowInsets insets, @NonNull List<WindowInsetsAnimation> runningAnimations) {
                if (this.mTmpRunningAnimations == null) {
                    this.mTmpRunningAnimations = new ArrayList<>(runningAnimations.size());
                    this.mRORunningAnimations = Collections.unmodifiableList(this.mTmpRunningAnimations);
                } else {
                    this.mTmpRunningAnimations.clear();
                }
                for (int i = runningAnimations.size() - 1; i >= 0; i--) {
                    WindowInsetsAnimation animation = runningAnimations.get(i);
                    WindowInsetsAnimationCompat animationCompat = getWindowInsetsAnimationCompat(animation);
                    animationCompat.setFraction(animation.getFraction());
                    this.mTmpRunningAnimations.add(animationCompat);
                }
                return this.mCompat.onProgress(WindowInsetsCompat.toWindowInsetsCompat(insets), this.mRORunningAnimations).toWindowInsets();
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onEnd(@NonNull WindowInsetsAnimation animation) {
                this.mCompat.onEnd(getWindowInsetsAnimationCompat(animation));
                this.mAnimations.remove(animation);
            }
        }

        public static void setCallback(@NonNull View view, @Nullable Callback callback) {
            WindowInsetsAnimation.Callback platformCallback = callback != null ? new ProxyCallback(callback) : null;
            view.setWindowInsetsAnimationCallback(platformCallback);
        }

        @NonNull
        public static WindowInsetsAnimation.Bounds createPlatformBounds(@NonNull BoundsCompat bounds) {
            return new WindowInsetsAnimation.Bounds(bounds.getLowerBound().toPlatformInsets(), bounds.getUpperBound().toPlatformInsets());
        }

        @NonNull
        public static Insets getLowerBounds(@NonNull WindowInsetsAnimation.Bounds bounds) {
            return Insets.toCompatInsets(bounds.getLowerBound());
        }

        @NonNull
        public static Insets getHigherBounds(@NonNull WindowInsetsAnimation.Bounds bounds) {
            return Insets.toCompatInsets(bounds.getUpperBound());
        }
    }
}
