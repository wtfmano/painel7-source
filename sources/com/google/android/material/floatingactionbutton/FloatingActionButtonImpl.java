package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ImageMatrixProperty;
import com.google.android.material.animation.MatrixEvaluator;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.internal.StateListAnimator;
import com.google.android.material.ripple.RippleDrawableCompat;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class FloatingActionButtonImpl {
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_SHOWING = 2;
    static final long ELEVATION_ANIM_DELAY = 100;
    static final long ELEVATION_ANIM_DURATION = 100;
    private static final float HIDE_ICON_SCALE = 0.0f;
    private static final float HIDE_OPACITY = 0.0f;
    private static final float HIDE_SCALE = 0.0f;
    static final float SHADOW_MULTIPLIER = 1.5f;
    private static final float SHOW_ICON_SCALE = 1.0f;
    private static final float SHOW_OPACITY = 1.0f;
    private static final float SHOW_SCALE = 1.0f;
    @Nullable
    BorderDrawable borderDrawable;
    @Nullable
    Drawable contentBackground;
    @Nullable
    private Animator currentAnimator;
    @Nullable
    private MotionSpec defaultHideMotionSpec;
    @Nullable
    private MotionSpec defaultShowMotionSpec;
    float elevation;
    boolean ensureMinTouchTargetSize;
    private ArrayList<Animator.AnimatorListener> hideListeners;
    @Nullable
    private MotionSpec hideMotionSpec;
    float hoveredFocusedTranslationZ;
    private int maxImageSize;
    int minTouchTargetSize;
    @Nullable
    private ViewTreeObserver.OnPreDrawListener preDrawListener;
    float pressedTranslationZ;
    @Nullable
    Drawable rippleDrawable;
    private float rotation;
    final ShadowViewDelegate shadowViewDelegate;
    @Nullable
    ShapeAppearanceModel shapeAppearance;
    @Nullable
    MaterialShapeDrawable shapeDrawable;
    private ArrayList<Animator.AnimatorListener> showListeners;
    @Nullable
    private MotionSpec showMotionSpec;
    private ArrayList<InternalTransformationCallback> transformationCallbacks;
    final FloatingActionButton view;
    static final TimeInterpolator ELEVATION_ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    static final int[] PRESSED_ENABLED_STATE_SET = {16842919, 16842910};
    static final int[] HOVERED_FOCUSED_ENABLED_STATE_SET = {16843623, 16842908, 16842910};
    static final int[] FOCUSED_ENABLED_STATE_SET = {16842908, 16842910};
    static final int[] HOVERED_ENABLED_STATE_SET = {16843623, 16842910};
    static final int[] ENABLED_STATE_SET = {16842910};
    static final int[] EMPTY_STATE_SET = new int[0];
    boolean shadowPaddingEnabled = true;
    private float imageMatrixScale = 1.0f;
    private int animState = 0;
    private final Rect tmpRect = new Rect();
    private final RectF tmpRectF1 = new RectF();
    private final RectF tmpRectF2 = new RectF();
    private final Matrix tmpMatrix = new Matrix();
    @NonNull
    private final StateListAnimator stateListAnimator = new StateListAnimator();

    /* loaded from: classes.dex */
    interface InternalTransformationCallback {
        void onScaleChanged();

        void onTranslationChanged();
    }

    /* loaded from: classes.dex */
    interface InternalVisibilityChangedListener {
        void onHidden();

        void onShown();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FloatingActionButtonImpl(FloatingActionButton view, ShadowViewDelegate shadowViewDelegate) {
        this.view = view;
        this.shadowViewDelegate = shadowViewDelegate;
        this.stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToPressedTranslationZAnimation()));
        this.stateListAnimator.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        this.stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        this.stateListAnimator.addState(HOVERED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        this.stateListAnimator.addState(ENABLED_STATE_SET, createElevationAnimator(new ResetElevationAnimation()));
        this.stateListAnimator.addState(EMPTY_STATE_SET, createElevationAnimator(new DisabledElevationAnimation()));
        this.rotation = this.view.getRotation();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void initializeBackgroundDrawable(ColorStateList backgroundTint, @Nullable PorterDuff.Mode backgroundTintMode, ColorStateList rippleColor, int borderWidth) {
        this.shapeDrawable = createShapeDrawable();
        this.shapeDrawable.setTintList(backgroundTint);
        if (backgroundTintMode != null) {
            this.shapeDrawable.setTintMode(backgroundTintMode);
        }
        this.shapeDrawable.setShadowColor(-12303292);
        this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
        RippleDrawableCompat touchFeedbackShape = new RippleDrawableCompat(this.shapeDrawable.getShapeAppearanceModel());
        touchFeedbackShape.setTintList(RippleUtils.sanitizeRippleDrawableColor(rippleColor));
        this.rippleDrawable = touchFeedbackShape;
        Drawable[] layers = {(Drawable) Preconditions.checkNotNull(this.shapeDrawable), touchFeedbackShape};
        this.contentBackground = new LayerDrawable(layers);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        if (this.shapeDrawable != null) {
            this.shapeDrawable.setTintList(tint);
        }
        if (this.borderDrawable != null) {
            this.borderDrawable.setBorderTint(tint);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (this.shapeDrawable != null) {
            this.shapeDrawable.setTintMode(tintMode);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setMinTouchTargetSize(int minTouchTargetSize) {
        this.minTouchTargetSize = minTouchTargetSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRippleColor(@Nullable ColorStateList rippleColor) {
        if (this.rippleDrawable != null) {
            DrawableCompat.setTintList(this.rippleDrawable, RippleUtils.sanitizeRippleDrawableColor(rippleColor));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setElevation(float elevation) {
        if (this.elevation != elevation) {
            this.elevation = elevation;
            onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getElevation() {
        return this.elevation;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getHoveredFocusedTranslationZ() {
        return this.hoveredFocusedTranslationZ;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getPressedTranslationZ() {
        return this.pressedTranslationZ;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setHoveredFocusedTranslationZ(float translationZ) {
        if (this.hoveredFocusedTranslationZ != translationZ) {
            this.hoveredFocusedTranslationZ = translationZ;
            onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setPressedTranslationZ(float translationZ) {
        if (this.pressedTranslationZ != translationZ) {
            this.pressedTranslationZ = translationZ;
            onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setMaxImageSize(int maxImageSize) {
        if (this.maxImageSize != maxImageSize) {
            this.maxImageSize = maxImageSize;
            updateImageMatrixScale();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void updateImageMatrixScale() {
        setImageMatrixScale(this.imageMatrixScale);
    }

    final void setImageMatrixScale(float scale) {
        this.imageMatrixScale = scale;
        Matrix matrix = this.tmpMatrix;
        calculateImageMatrixFromScale(scale, matrix);
        this.view.setImageMatrix(matrix);
    }

    private void calculateImageMatrixFromScale(float scale, @NonNull Matrix matrix) {
        matrix.reset();
        Drawable drawable = this.view.getDrawable();
        if (drawable != null && this.maxImageSize != 0) {
            RectF drawableBounds = this.tmpRectF1;
            RectF imageBounds = this.tmpRectF2;
            drawableBounds.set(0.0f, 0.0f, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            imageBounds.set(0.0f, 0.0f, this.maxImageSize, this.maxImageSize);
            matrix.setRectToRect(drawableBounds, imageBounds, Matrix.ScaleToFit.CENTER);
            matrix.postScale(scale, scale, this.maxImageSize / 2.0f, this.maxImageSize / 2.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setShapeAppearance(@NonNull ShapeAppearanceModel shapeAppearance) {
        this.shapeAppearance = shapeAppearance;
        if (this.shapeDrawable != null) {
            this.shapeDrawable.setShapeAppearanceModel(shapeAppearance);
        }
        if (this.rippleDrawable instanceof Shapeable) {
            ((Shapeable) this.rippleDrawable).setShapeAppearanceModel(shapeAppearance);
        }
        if (this.borderDrawable != null) {
            this.borderDrawable.setShapeAppearanceModel(shapeAppearance);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final ShapeAppearanceModel getShapeAppearance() {
        return this.shapeAppearance;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final MotionSpec getShowMotionSpec() {
        return this.showMotionSpec;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setShowMotionSpec(@Nullable MotionSpec spec) {
        this.showMotionSpec = spec;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final MotionSpec getHideMotionSpec() {
        return this.hideMotionSpec;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setHideMotionSpec(@Nullable MotionSpec spec) {
        this.hideMotionSpec = spec;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean shouldExpandBoundsForA11y() {
        return !this.ensureMinTouchTargetSize || this.view.getSizeDimension() >= this.minTouchTargetSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean getEnsureMinTouchTargetSize() {
        return this.ensureMinTouchTargetSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setEnsureMinTouchTargetSize(boolean flag) {
        this.ensureMinTouchTargetSize = flag;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setShadowPaddingEnabled(boolean shadowPaddingEnabled) {
        this.shadowPaddingEnabled = shadowPaddingEnabled;
        updatePadding();
    }

    void onElevationsChanged(float elevation, float hoveredFocusedTranslationZ, float pressedTranslationZ) {
        updatePadding();
        updateShapeElevation(elevation);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateShapeElevation(float elevation) {
        if (this.shapeDrawable != null) {
            this.shapeDrawable.setElevation(elevation);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onDrawableStateChanged(int[] state) {
        this.stateListAnimator.setState(state);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void jumpDrawableToCurrentState() {
        this.stateListAnimator.jumpToCurrentState();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addOnShowAnimationListener(@NonNull Animator.AnimatorListener listener) {
        if (this.showListeners == null) {
            this.showListeners = new ArrayList<>();
        }
        this.showListeners.add(listener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeOnShowAnimationListener(@NonNull Animator.AnimatorListener listener) {
        if (this.showListeners != null) {
            this.showListeners.remove(listener);
        }
    }

    public void addOnHideAnimationListener(@NonNull Animator.AnimatorListener listener) {
        if (this.hideListeners == null) {
            this.hideListeners = new ArrayList<>();
        }
        this.hideListeners.add(listener);
    }

    public void removeOnHideAnimationListener(@NonNull Animator.AnimatorListener listener) {
        if (this.hideListeners != null) {
            this.hideListeners.remove(listener);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void hide(@Nullable final InternalVisibilityChangedListener listener, final boolean fromUser) {
        if (!isOrWillBeHidden()) {
            if (this.currentAnimator != null) {
                this.currentAnimator.cancel();
            }
            if (shouldAnimateVisibilityChange()) {
                AnimatorSet set = createAnimator(this.hideMotionSpec != null ? this.hideMotionSpec : getDefaultHideMotionSpec(), 0.0f, 0.0f, 0.0f);
                set.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.1
                    private boolean cancelled;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animation) {
                        FloatingActionButtonImpl.this.view.internalSetVisibility(0, fromUser);
                        FloatingActionButtonImpl.this.animState = 1;
                        FloatingActionButtonImpl.this.currentAnimator = animation;
                        this.cancelled = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animation) {
                        this.cancelled = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        FloatingActionButtonImpl.this.animState = 0;
                        FloatingActionButtonImpl.this.currentAnimator = null;
                        if (!this.cancelled) {
                            FloatingActionButtonImpl.this.view.internalSetVisibility(fromUser ? 8 : 4, fromUser);
                            if (listener != null) {
                                listener.onHidden();
                            }
                        }
                    }
                });
                if (this.hideListeners != null) {
                    Iterator<Animator.AnimatorListener> it = this.hideListeners.iterator();
                    while (it.hasNext()) {
                        Animator.AnimatorListener l = it.next();
                        set.addListener(l);
                    }
                }
                set.start();
                return;
            }
            this.view.internalSetVisibility(fromUser ? 8 : 4, fromUser);
            if (listener != null) {
                listener.onHidden();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void show(@Nullable final InternalVisibilityChangedListener listener, final boolean fromUser) {
        if (!isOrWillBeShown()) {
            if (this.currentAnimator != null) {
                this.currentAnimator.cancel();
            }
            if (shouldAnimateVisibilityChange()) {
                if (this.view.getVisibility() != 0) {
                    this.view.setAlpha(0.0f);
                    this.view.setScaleY(0.0f);
                    this.view.setScaleX(0.0f);
                    setImageMatrixScale(0.0f);
                }
                AnimatorSet set = createAnimator(this.showMotionSpec != null ? this.showMotionSpec : getDefaultShowMotionSpec(), 1.0f, 1.0f, 1.0f);
                set.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animation) {
                        FloatingActionButtonImpl.this.view.internalSetVisibility(0, fromUser);
                        FloatingActionButtonImpl.this.animState = 2;
                        FloatingActionButtonImpl.this.currentAnimator = animation;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        FloatingActionButtonImpl.this.animState = 0;
                        FloatingActionButtonImpl.this.currentAnimator = null;
                        if (listener != null) {
                            listener.onShown();
                        }
                    }
                });
                if (this.showListeners != null) {
                    Iterator<Animator.AnimatorListener> it = this.showListeners.iterator();
                    while (it.hasNext()) {
                        Animator.AnimatorListener l = it.next();
                        set.addListener(l);
                    }
                }
                set.start();
                return;
            }
            this.view.internalSetVisibility(0, fromUser);
            this.view.setAlpha(1.0f);
            this.view.setScaleY(1.0f);
            this.view.setScaleX(1.0f);
            setImageMatrixScale(1.0f);
            if (listener != null) {
                listener.onShown();
            }
        }
    }

    private MotionSpec getDefaultShowMotionSpec() {
        if (this.defaultShowMotionSpec == null) {
            this.defaultShowMotionSpec = MotionSpec.createFromResource(this.view.getContext(), R.animator.design_fab_show_motion_spec);
        }
        return (MotionSpec) Preconditions.checkNotNull(this.defaultShowMotionSpec);
    }

    private MotionSpec getDefaultHideMotionSpec() {
        if (this.defaultHideMotionSpec == null) {
            this.defaultHideMotionSpec = MotionSpec.createFromResource(this.view.getContext(), R.animator.design_fab_hide_motion_spec);
        }
        return (MotionSpec) Preconditions.checkNotNull(this.defaultHideMotionSpec);
    }

    @NonNull
    private AnimatorSet createAnimator(@NonNull MotionSpec spec, float opacity, float scale, float iconScale) {
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator animatorOpacity = ObjectAnimator.ofFloat(this.view, View.ALPHA, opacity);
        spec.getTiming("opacity").apply(animatorOpacity);
        animators.add(animatorOpacity);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(this.view, View.SCALE_X, scale);
        spec.getTiming("scale").apply(animatorScaleX);
        workAroundOreoBug(animatorScaleX);
        animators.add(animatorScaleX);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(this.view, View.SCALE_Y, scale);
        spec.getTiming("scale").apply(animatorScaleY);
        workAroundOreoBug(animatorScaleY);
        animators.add(animatorScaleY);
        calculateImageMatrixFromScale(iconScale, this.tmpMatrix);
        ObjectAnimator animatorIconScale = ObjectAnimator.ofObject(this.view, new ImageMatrixProperty(), new MatrixEvaluator() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.google.android.material.animation.MatrixEvaluator, android.animation.TypeEvaluator
            public Matrix evaluate(float fraction, @NonNull Matrix startValue, @NonNull Matrix endValue) {
                FloatingActionButtonImpl.this.imageMatrixScale = fraction;
                return super.evaluate(fraction, startValue, endValue);
            }
        }, new Matrix(this.tmpMatrix));
        spec.getTiming("iconScale").apply(animatorIconScale);
        animators.add(animatorIconScale);
        AnimatorSet set = new AnimatorSet();
        AnimatorSetCompat.playTogether(set, animators);
        return set;
    }

    private void workAroundOreoBug(ObjectAnimator animator) {
        if (Build.VERSION.SDK_INT == 26) {
            animator.setEvaluator(new TypeEvaluator<Float>() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.4
                FloatEvaluator floatEvaluator = new FloatEvaluator();

                @Override // android.animation.TypeEvaluator
                public Float evaluate(float fraction, Float startValue, Float endValue) {
                    float evaluated = this.floatEvaluator.evaluate(fraction, (Number) startValue, (Number) endValue).floatValue();
                    if (evaluated < 0.1f) {
                        evaluated = 0.0f;
                    }
                    return Float.valueOf(evaluated);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addTransformationCallback(@NonNull InternalTransformationCallback listener) {
        if (this.transformationCallbacks == null) {
            this.transformationCallbacks = new ArrayList<>();
        }
        this.transformationCallbacks.add(listener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeTransformationCallback(@NonNull InternalTransformationCallback listener) {
        if (this.transformationCallbacks != null) {
            this.transformationCallbacks.remove(listener);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onTranslationChanged() {
        if (this.transformationCallbacks != null) {
            Iterator<InternalTransformationCallback> it = this.transformationCallbacks.iterator();
            while (it.hasNext()) {
                InternalTransformationCallback l = it.next();
                l.onTranslationChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onScaleChanged() {
        if (this.transformationCallbacks != null) {
            Iterator<InternalTransformationCallback> it = this.transformationCallbacks.iterator();
            while (it.hasNext()) {
                InternalTransformationCallback l = it.next();
                l.onScaleChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final Drawable getContentBackground() {
        return this.contentBackground;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onCompatShadowChanged() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void updatePadding() {
        Rect rect = this.tmpRect;
        getPadding(rect);
        onPaddingUpdated(rect);
        this.shadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getPadding(@NonNull Rect rect) {
        int minPadding = this.ensureMinTouchTargetSize ? (this.minTouchTargetSize - this.view.getSizeDimension()) / 2 : 0;
        float maxShadowSize = this.shadowPaddingEnabled ? getElevation() + this.pressedTranslationZ : 0.0f;
        int hPadding = Math.max(minPadding, (int) Math.ceil(maxShadowSize));
        int vPadding = Math.max(minPadding, (int) Math.ceil(SHADOW_MULTIPLIER * maxShadowSize));
        rect.set(hPadding, vPadding, hPadding, vPadding);
    }

    void onPaddingUpdated(@NonNull Rect padding) {
        Preconditions.checkNotNull(this.contentBackground, "Didn't initialize content background");
        if (shouldAddPadding()) {
            InsetDrawable insetDrawable = new InsetDrawable(this.contentBackground, padding.left, padding.top, padding.right, padding.bottom);
            this.shadowViewDelegate.setBackgroundDrawable(insetDrawable);
            return;
        }
        this.shadowViewDelegate.setBackgroundDrawable(this.contentBackground);
    }

    boolean shouldAddPadding() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onAttachedToWindow() {
        if (this.shapeDrawable != null) {
            MaterialShapeUtils.setParentAbsoluteElevation(this.view, this.shapeDrawable);
        }
        if (requirePreDrawListener()) {
            this.view.getViewTreeObserver().addOnPreDrawListener(getOrCreatePreDrawListener());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onDetachedFromWindow() {
        ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
        if (this.preDrawListener != null) {
            viewTreeObserver.removeOnPreDrawListener(this.preDrawListener);
            this.preDrawListener = null;
        }
    }

    boolean requirePreDrawListener() {
        return true;
    }

    void onPreDraw() {
        float rotation = this.view.getRotation();
        if (this.rotation != rotation) {
            this.rotation = rotation;
            updateFromViewRotation();
        }
    }

    @NonNull
    private ViewTreeObserver.OnPreDrawListener getOrCreatePreDrawListener() {
        if (this.preDrawListener == null) {
            this.preDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.5
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    FloatingActionButtonImpl.this.onPreDraw();
                    return true;
                }
            };
        }
        return this.preDrawListener;
    }

    MaterialShapeDrawable createShapeDrawable() {
        ShapeAppearanceModel shapeAppearance = (ShapeAppearanceModel) Preconditions.checkNotNull(this.shapeAppearance);
        return new MaterialShapeDrawable(shapeAppearance);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isOrWillBeShown() {
        return this.view.getVisibility() != 0 ? this.animState == 2 : this.animState != 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isOrWillBeHidden() {
        return this.view.getVisibility() == 0 ? this.animState == 1 : this.animState != 2;
    }

    @NonNull
    private ValueAnimator createElevationAnimator(@NonNull ShadowAnimatorImpl impl) {
        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
        animator.setDuration(100L);
        animator.addListener(impl);
        animator.addUpdateListener(impl);
        animator.setFloatValues(0.0f, 1.0f);
        return animator;
    }

    /* loaded from: classes.dex */
    private abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private float shadowSizeEnd;
        private float shadowSizeStart;
        private boolean validValues;

        protected abstract float getTargetShadowSize();

        private ShadowAnimatorImpl() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(@NonNull ValueAnimator animator) {
            if (!this.validValues) {
                this.shadowSizeStart = FloatingActionButtonImpl.this.shapeDrawable == null ? 0.0f : FloatingActionButtonImpl.this.shapeDrawable.getElevation();
                this.shadowSizeEnd = getTargetShadowSize();
                this.validValues = true;
            }
            FloatingActionButtonImpl.this.updateShapeElevation((int) (this.shadowSizeStart + ((this.shadowSizeEnd - this.shadowSizeStart) * animator.getAnimatedFraction())));
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            FloatingActionButtonImpl.this.updateShapeElevation((int) this.shadowSizeEnd);
            this.validValues = false;
        }
    }

    /* loaded from: classes.dex */
    private class ResetElevationAnimation extends ShadowAnimatorImpl {
        ResetElevationAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.elevation;
        }
    }

    /* loaded from: classes.dex */
    private class ElevateToHoveredFocusedTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToHoveredFocusedTranslationZAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.elevation + FloatingActionButtonImpl.this.hoveredFocusedTranslationZ;
        }
    }

    /* loaded from: classes.dex */
    private class ElevateToPressedTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToPressedTranslationZAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.elevation + FloatingActionButtonImpl.this.pressedTranslationZ;
        }
    }

    /* loaded from: classes.dex */
    private class DisabledElevationAnimation extends ShadowAnimatorImpl {
        DisabledElevationAnimation() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.FloatingActionButtonImpl.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return 0.0f;
        }
    }

    private boolean shouldAnimateVisibilityChange() {
        return ViewCompat.isLaidOut(this.view) && !this.view.isInEditMode();
    }

    void updateFromViewRotation() {
        if (Build.VERSION.SDK_INT == 19) {
            if (this.rotation % 90.0f != 0.0f) {
                if (this.view.getLayerType() != 1) {
                    this.view.setLayerType(1, null);
                }
            } else if (this.view.getLayerType() != 0) {
                this.view.setLayerType(0, null);
            }
        }
        if (this.shapeDrawable != null) {
            this.shapeDrawable.setShadowCompatRotation((int) this.rotation);
        }
    }
}
