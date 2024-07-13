package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.Key;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.animation.ChildrenAlphaProperty;
import com.google.android.material.animation.DrawableAlphaProperty;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.animation.Positioning;
import com.google.android.material.circularreveal.CircularRevealCompat;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.math.MathUtils;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes.dex */
public abstract class FabTransformationBehavior extends ExpandableTransformationBehavior {
    private float dependencyOriginalTranslationX;
    private float dependencyOriginalTranslationY;
    private final int[] tmpArray;
    private final Rect tmpRect;
    private final RectF tmpRectF1;
    private final RectF tmpRectF2;

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static class FabTransformationSpec {
        public Positioning positioning;
        @Nullable
        public MotionSpec timings;
    }

    protected abstract FabTransformationSpec onCreateMotionSpec(Context context, boolean z);

    public FabTransformationBehavior() {
        this.tmpRect = new Rect();
        this.tmpRectF1 = new RectF();
        this.tmpRectF2 = new RectF();
        this.tmpArray = new int[2];
    }

    public FabTransformationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.tmpRect = new Rect();
        this.tmpRectF1 = new RectF();
        this.tmpRectF2 = new RectF();
        this.tmpArray = new int[2];
    }

    @Override // com.google.android.material.transformation.ExpandableBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    @CallSuper
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (child.getVisibility() == 8) {
            throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
        }
        if (dependency instanceof FloatingActionButton) {
            int expandedComponentIdHint = ((FloatingActionButton) dependency).getExpandedComponentIdHint();
            return expandedComponentIdHint == 0 || expandedComponentIdHint == child.getId();
        }
        return false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    @CallSuper
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams lp) {
        if (lp.dodgeInsetEdges == 0) {
            lp.dodgeInsetEdges = 80;
        }
    }

    @Override // com.google.android.material.transformation.ExpandableTransformationBehavior
    @NonNull
    protected AnimatorSet onCreateExpandedStateChangeAnimation(@NonNull final View dependency, @NonNull final View child, final boolean expanded, boolean isAnimating) {
        FabTransformationSpec spec = onCreateMotionSpec(child.getContext(), expanded);
        if (expanded) {
            this.dependencyOriginalTranslationX = dependency.getTranslationX();
            this.dependencyOriginalTranslationY = dependency.getTranslationY();
        }
        List<Animator> animations = new ArrayList<>();
        List<Animator.AnimatorListener> listeners = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 21) {
            createElevationAnimation(dependency, child, expanded, isAnimating, spec, animations, listeners);
        }
        RectF childBounds = this.tmpRectF1;
        createTranslationAnimation(dependency, child, expanded, isAnimating, spec, animations, listeners, childBounds);
        float childWidth = childBounds.width();
        float childHeight = childBounds.height();
        createDependencyTranslationAnimation(dependency, child, expanded, spec, animations);
        createIconFadeAnimation(dependency, child, expanded, isAnimating, spec, animations, listeners);
        createExpansionAnimation(dependency, child, expanded, isAnimating, spec, childWidth, childHeight, animations, listeners);
        createColorAnimation(dependency, child, expanded, isAnimating, spec, animations, listeners);
        createChildrenFadeAnimation(dependency, child, expanded, isAnimating, spec, animations, listeners);
        AnimatorSet set = new AnimatorSet();
        AnimatorSetCompat.playTogether(set, animations);
        set.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.FabTransformationBehavior.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (expanded) {
                    child.setVisibility(0);
                    dependency.setAlpha(0.0f);
                    dependency.setVisibility(4);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (!expanded) {
                    child.setVisibility(4);
                    dependency.setAlpha(1.0f);
                    dependency.setVisibility(0);
                }
            }
        });
        int count = listeners.size();
        for (int i = 0; i < count; i++) {
            set.addListener(listeners.get(i));
        }
        return set;
    }

    @TargetApi(21)
    private void createElevationAnimation(View dependency, @NonNull View child, boolean expanded, boolean currentlyAnimating, @NonNull FabTransformationSpec spec, @NonNull List<Animator> animations, List<Animator.AnimatorListener> unusedListeners) {
        Animator animator;
        float translationZ = ViewCompat.getElevation(child) - ViewCompat.getElevation(dependency);
        if (expanded) {
            if (!currentlyAnimating) {
                child.setTranslationZ(-translationZ);
            }
            animator = ObjectAnimator.ofFloat(child, View.TRANSLATION_Z, 0.0f);
        } else {
            animator = ObjectAnimator.ofFloat(child, View.TRANSLATION_Z, -translationZ);
        }
        MotionTiming timing = spec.timings.getTiming(Key.ELEVATION);
        timing.apply(animator);
        animations.add(animator);
    }

    private void createDependencyTranslationAnimation(@NonNull View dependency, @NonNull View child, boolean expanded, @NonNull FabTransformationSpec spec, @NonNull List<Animator> animations) {
        float translationX = calculateTranslationX(dependency, child, spec.positioning);
        float translationY = calculateTranslationY(dependency, child, spec.positioning);
        Pair<MotionTiming, MotionTiming> motionTiming = calculateMotionTiming(translationX, translationY, expanded, spec);
        MotionTiming translationXTiming = (MotionTiming) motionTiming.first;
        MotionTiming translationYTiming = (MotionTiming) motionTiming.second;
        Property property = View.TRANSLATION_X;
        float[] fArr = new float[1];
        if (!expanded) {
            translationX = this.dependencyOriginalTranslationX;
        }
        fArr[0] = translationX;
        ValueAnimator translationXAnimator = ObjectAnimator.ofFloat(dependency, property, fArr);
        Property property2 = View.TRANSLATION_Y;
        float[] fArr2 = new float[1];
        if (!expanded) {
            translationY = this.dependencyOriginalTranslationY;
        }
        fArr2[0] = translationY;
        ValueAnimator translationYAnimator = ObjectAnimator.ofFloat(dependency, property2, fArr2);
        translationXTiming.apply(translationXAnimator);
        translationYTiming.apply(translationYAnimator);
        animations.add(translationXAnimator);
        animations.add(translationYAnimator);
    }

    private void createTranslationAnimation(@NonNull View dependency, @NonNull View child, boolean expanded, boolean currentlyAnimating, @NonNull FabTransformationSpec spec, @NonNull List<Animator> animations, List<Animator.AnimatorListener> unusedListeners, @NonNull RectF childBounds) {
        ObjectAnimator ofFloat;
        ObjectAnimator ofFloat2;
        float translationX = calculateTranslationX(dependency, child, spec.positioning);
        float translationY = calculateTranslationY(dependency, child, spec.positioning);
        Pair<MotionTiming, MotionTiming> motionTiming = calculateMotionTiming(translationX, translationY, expanded, spec);
        MotionTiming translationXTiming = (MotionTiming) motionTiming.first;
        MotionTiming translationYTiming = (MotionTiming) motionTiming.second;
        if (expanded) {
            if (!currentlyAnimating) {
                child.setTranslationX(-translationX);
                child.setTranslationY(-translationY);
            }
            ofFloat = ObjectAnimator.ofFloat(child, View.TRANSLATION_X, 0.0f);
            ofFloat2 = ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, 0.0f);
            calculateChildVisibleBoundsAtEndOfExpansion(child, spec, translationXTiming, translationYTiming, -translationX, -translationY, 0.0f, 0.0f, childBounds);
        } else {
            ofFloat = ObjectAnimator.ofFloat(child, View.TRANSLATION_X, -translationX);
            ofFloat2 = ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, -translationY);
        }
        translationXTiming.apply(ofFloat);
        translationYTiming.apply(ofFloat2);
        animations.add(ofFloat);
        animations.add(ofFloat2);
    }

    private void createIconFadeAnimation(View dependency, final View child, boolean expanded, boolean currentlyAnimating, @NonNull FabTransformationSpec spec, @NonNull List<Animator> animations, @NonNull List<Animator.AnimatorListener> listeners) {
        ObjectAnimator animator;
        if ((child instanceof CircularRevealWidget) && (dependency instanceof ImageView)) {
            final CircularRevealWidget circularRevealChild = (CircularRevealWidget) child;
            ImageView dependencyImageView = (ImageView) dependency;
            final Drawable icon = dependencyImageView.getDrawable();
            if (icon != null) {
                icon.mutate();
                if (expanded) {
                    if (!currentlyAnimating) {
                        icon.setAlpha(255);
                    }
                    animator = ObjectAnimator.ofInt(icon, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, 0);
                } else {
                    animator = ObjectAnimator.ofInt(icon, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, 255);
                }
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.transformation.FabTransformationBehavior.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator animation) {
                        child.invalidate();
                    }
                });
                MotionTiming timing = spec.timings.getTiming("iconFade");
                timing.apply(animator);
                animations.add(animator);
                listeners.add(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.FabTransformationBehavior.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animation) {
                        circularRevealChild.setCircularRevealOverlayDrawable(icon);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        circularRevealChild.setCircularRevealOverlayDrawable(null);
                    }
                });
            }
        }
    }

    private void createExpansionAnimation(@NonNull View dependency, View child, boolean expanded, boolean currentlyAnimating, @NonNull FabTransformationSpec spec, float childWidth, float childHeight, @NonNull List<Animator> animations, @NonNull List<Animator.AnimatorListener> listeners) {
        Animator animator;
        if (child instanceof CircularRevealWidget) {
            final CircularRevealWidget circularRevealChild = (CircularRevealWidget) child;
            float revealCenterX = calculateRevealCenterX(dependency, child, spec.positioning);
            float revealCenterY = calculateRevealCenterY(dependency, child, spec.positioning);
            ((FloatingActionButton) dependency).getContentRect(this.tmpRect);
            float dependencyRadius = this.tmpRect.width() / 2.0f;
            MotionTiming timing = spec.timings.getTiming("expansion");
            if (expanded) {
                if (!currentlyAnimating) {
                    circularRevealChild.setRevealInfo(new CircularRevealWidget.RevealInfo(revealCenterX, revealCenterY, dependencyRadius));
                }
                float fromRadius = currentlyAnimating ? circularRevealChild.getRevealInfo().radius : dependencyRadius;
                float toRadius = MathUtils.distanceToFurthestCorner(revealCenterX, revealCenterY, 0.0f, 0.0f, childWidth, childHeight);
                animator = CircularRevealCompat.createCircularReveal(circularRevealChild, revealCenterX, revealCenterY, toRadius);
                animator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.FabTransformationBehavior.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        CircularRevealWidget.RevealInfo revealInfo = circularRevealChild.getRevealInfo();
                        revealInfo.radius = Float.MAX_VALUE;
                        circularRevealChild.setRevealInfo(revealInfo);
                    }
                });
                createPreFillRadialExpansion(child, timing.getDelay(), (int) revealCenterX, (int) revealCenterY, fromRadius, animations);
            } else {
                float fromRadius2 = circularRevealChild.getRevealInfo().radius;
                animator = CircularRevealCompat.createCircularReveal(circularRevealChild, revealCenterX, revealCenterY, dependencyRadius);
                createPreFillRadialExpansion(child, timing.getDelay(), (int) revealCenterX, (int) revealCenterY, fromRadius2, animations);
                createPostFillRadialExpansion(child, timing.getDelay(), timing.getDuration(), spec.timings.getTotalDuration(), (int) revealCenterX, (int) revealCenterY, dependencyRadius, animations);
            }
            timing.apply(animator);
            animations.add(animator);
            listeners.add(CircularRevealCompat.createCircularRevealListener(circularRevealChild));
        }
    }

    private void createColorAnimation(@NonNull View dependency, View child, boolean expanded, boolean currentlyAnimating, @NonNull FabTransformationSpec spec, @NonNull List<Animator> animations, List<Animator.AnimatorListener> unusedListeners) {
        ObjectAnimator animator;
        if (child instanceof CircularRevealWidget) {
            CircularRevealWidget circularRevealChild = (CircularRevealWidget) child;
            int tint = getBackgroundTint(dependency);
            int transparent = tint & ViewCompat.MEASURED_SIZE_MASK;
            if (expanded) {
                if (!currentlyAnimating) {
                    circularRevealChild.setCircularRevealScrimColor(tint);
                }
                animator = ObjectAnimator.ofInt(circularRevealChild, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, transparent);
            } else {
                animator = ObjectAnimator.ofInt(circularRevealChild, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, tint);
            }
            animator.setEvaluator(ArgbEvaluatorCompat.getInstance());
            MotionTiming timing = spec.timings.getTiming("color");
            timing.apply(animator);
            animations.add(animator);
        }
    }

    private void createChildrenFadeAnimation(View unusedDependency, View child, boolean expanded, boolean currentlyAnimating, @NonNull FabTransformationSpec spec, @NonNull List<Animator> animations, List<Animator.AnimatorListener> unusedListeners) {
        ViewGroup childContentContainer;
        Animator animator;
        if (child instanceof ViewGroup) {
            if ((!(child instanceof CircularRevealWidget) || CircularRevealHelper.STRATEGY != 0) && (childContentContainer = calculateChildContentContainer(child)) != null) {
                if (expanded) {
                    if (!currentlyAnimating) {
                        ChildrenAlphaProperty.CHILDREN_ALPHA.set(childContentContainer, Float.valueOf(0.0f));
                    }
                    animator = ObjectAnimator.ofFloat(childContentContainer, ChildrenAlphaProperty.CHILDREN_ALPHA, 1.0f);
                } else {
                    animator = ObjectAnimator.ofFloat(childContentContainer, ChildrenAlphaProperty.CHILDREN_ALPHA, 0.0f);
                }
                MotionTiming timing = spec.timings.getTiming("contentFade");
                timing.apply(animator);
                animations.add(animator);
            }
        }
    }

    @NonNull
    private Pair<MotionTiming, MotionTiming> calculateMotionTiming(float translationX, float translationY, boolean expanded, @NonNull FabTransformationSpec spec) {
        MotionTiming translationXTiming;
        MotionTiming translationYTiming;
        if (translationX == 0.0f || translationY == 0.0f) {
            translationXTiming = spec.timings.getTiming("translationXLinear");
            translationYTiming = spec.timings.getTiming("translationYLinear");
        } else if ((expanded && translationY < 0.0f) || (!expanded && translationY > 0.0f)) {
            translationXTiming = spec.timings.getTiming("translationXCurveUpwards");
            translationYTiming = spec.timings.getTiming("translationYCurveUpwards");
        } else {
            translationXTiming = spec.timings.getTiming("translationXCurveDownwards");
            translationYTiming = spec.timings.getTiming("translationYCurveDownwards");
        }
        return new Pair<>(translationXTiming, translationYTiming);
    }

    private float calculateTranslationX(@NonNull View dependency, @NonNull View child, @NonNull Positioning positioning) {
        RectF dependencyBounds = this.tmpRectF1;
        RectF childBounds = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, dependencyBounds);
        calculateWindowBounds(child, childBounds);
        float translationX = 0.0f;
        switch (positioning.gravity & 7) {
            case 1:
                translationX = childBounds.centerX() - dependencyBounds.centerX();
                break;
            case 3:
                translationX = childBounds.left - dependencyBounds.left;
                break;
            case 5:
                translationX = childBounds.right - dependencyBounds.right;
                break;
        }
        return translationX + positioning.xAdjustment;
    }

    private float calculateTranslationY(@NonNull View dependency, @NonNull View child, @NonNull Positioning positioning) {
        RectF dependencyBounds = this.tmpRectF1;
        RectF childBounds = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, dependencyBounds);
        calculateWindowBounds(child, childBounds);
        float translationY = 0.0f;
        switch (positioning.gravity & 112) {
            case 16:
                translationY = childBounds.centerY() - dependencyBounds.centerY();
                break;
            case 48:
                translationY = childBounds.top - dependencyBounds.top;
                break;
            case 80:
                translationY = childBounds.bottom - dependencyBounds.bottom;
                break;
        }
        return translationY + positioning.yAdjustment;
    }

    private void calculateWindowBounds(@NonNull View view, RectF rect) {
        rect.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
        int[] windowLocation = this.tmpArray;
        view.getLocationInWindow(windowLocation);
        rect.offsetTo(windowLocation[0], windowLocation[1]);
        rect.offset((int) (-view.getTranslationX()), (int) (-view.getTranslationY()));
    }

    private void calculateDependencyWindowBounds(@NonNull View view, @NonNull RectF rect) {
        calculateWindowBounds(view, rect);
        rect.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
    }

    private float calculateRevealCenterX(@NonNull View dependency, @NonNull View child, @NonNull Positioning positioning) {
        RectF dependencyBounds = this.tmpRectF1;
        RectF childBounds = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, dependencyBounds);
        calculateWindowBounds(child, childBounds);
        float translationX = calculateTranslationX(dependency, child, positioning);
        childBounds.offset(-translationX, 0.0f);
        return dependencyBounds.centerX() - childBounds.left;
    }

    private float calculateRevealCenterY(@NonNull View dependency, @NonNull View child, @NonNull Positioning positioning) {
        RectF dependencyBounds = this.tmpRectF1;
        RectF childBounds = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, dependencyBounds);
        calculateWindowBounds(child, childBounds);
        float translationY = calculateTranslationY(dependency, child, positioning);
        childBounds.offset(0.0f, -translationY);
        return dependencyBounds.centerY() - childBounds.top;
    }

    private void calculateChildVisibleBoundsAtEndOfExpansion(@NonNull View child, @NonNull FabTransformationSpec spec, @NonNull MotionTiming translationXTiming, @NonNull MotionTiming translationYTiming, float fromX, float fromY, float toX, float toY, @NonNull RectF childBounds) {
        float translationX = calculateValueOfAnimationAtEndOfExpansion(spec, translationXTiming, fromX, toX);
        float translationY = calculateValueOfAnimationAtEndOfExpansion(spec, translationYTiming, fromY, toY);
        Rect window = this.tmpRect;
        child.getWindowVisibleDisplayFrame(window);
        RectF windowF = this.tmpRectF1;
        windowF.set(window);
        RectF childVisibleBounds = this.tmpRectF2;
        calculateWindowBounds(child, childVisibleBounds);
        childVisibleBounds.offset(translationX, translationY);
        childVisibleBounds.intersect(windowF);
        childBounds.set(childVisibleBounds);
    }

    private float calculateValueOfAnimationAtEndOfExpansion(@NonNull FabTransformationSpec spec, @NonNull MotionTiming timing, float from, float to) {
        long delay = timing.getDelay();
        long duration = timing.getDuration();
        MotionTiming expansionTiming = spec.timings.getTiming("expansion");
        long expansionEnd = expansionTiming.getDelay() + expansionTiming.getDuration();
        float fraction = ((float) ((expansionEnd + 17) - delay)) / ((float) duration);
        return AnimationUtils.lerp(from, to, timing.getInterpolator().getInterpolation(fraction));
    }

    @Nullable
    private ViewGroup calculateChildContentContainer(@NonNull View view) {
        View childContentContainer = view.findViewById(R.id.mtrl_child_content_container);
        if (childContentContainer != null) {
            return toViewGroupOrNull(childContentContainer);
        }
        if ((view instanceof TransformationChildLayout) || (view instanceof TransformationChildCard)) {
            return toViewGroupOrNull(((ViewGroup) view).getChildAt(0));
        }
        return toViewGroupOrNull(view);
    }

    @Nullable
    private ViewGroup toViewGroupOrNull(View view) {
        if (view instanceof ViewGroup) {
            return (ViewGroup) view;
        }
        return null;
    }

    private int getBackgroundTint(@NonNull View view) {
        ColorStateList tintList = ViewCompat.getBackgroundTintList(view);
        if (tintList != null) {
            return tintList.getColorForState(view.getDrawableState(), tintList.getDefaultColor());
        }
        return 0;
    }

    private void createPreFillRadialExpansion(View child, long delay, int revealCenterX, int revealCenterY, float fromRadius, @NonNull List<Animator> animations) {
        if (Build.VERSION.SDK_INT >= 21 && delay > 0) {
            Animator animator = ViewAnimationUtils.createCircularReveal(child, revealCenterX, revealCenterY, fromRadius, fromRadius);
            animator.setStartDelay(0L);
            animator.setDuration(delay);
            animations.add(animator);
        }
    }

    private void createPostFillRadialExpansion(View child, long delay, long duration, long totalDuration, int revealCenterX, int revealCenterY, float toRadius, @NonNull List<Animator> animations) {
        if (Build.VERSION.SDK_INT >= 21 && delay + duration < totalDuration) {
            Animator animator = ViewAnimationUtils.createCircularReveal(child, revealCenterX, revealCenterY, toRadius, toRadius);
            animator.setStartDelay(delay + duration);
            animator.setDuration(totalDuration - (delay + duration));
            animations.add(animator);
        }
    }
}
