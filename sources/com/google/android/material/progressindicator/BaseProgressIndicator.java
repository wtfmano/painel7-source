package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.ProgressBar;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.ViewCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class BaseProgressIndicator<S extends BaseProgressIndicatorSpec> extends ProgressBar {
    static final float DEFAULT_OPACITY = 0.2f;
    static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ProgressIndicator;
    public static final int HIDE_INWARD = 2;
    public static final int HIDE_NONE = 0;
    public static final int HIDE_OUTWARD = 1;
    static final int MAX_ALPHA = 255;
    static final int MAX_HIDE_DELAY = 1000;
    public static final int SHOW_INWARD = 2;
    public static final int SHOW_NONE = 0;
    public static final int SHOW_OUTWARD = 1;
    AnimatorDurationScaleProvider animatorDurationScaleProvider;
    private final Runnable delayedHide;
    private final Runnable delayedShow;
    private final Animatable2Compat.AnimationCallback hideAnimationCallback;
    private boolean isIndeterminateModeChangeRequested;
    private boolean isParentDoneInitializing;
    private long lastShowStartTime;
    private final int minHideDelay;
    private final int showDelay;
    S spec;
    private int storedProgress;
    private boolean storedProgressAnimated;
    private final Animatable2Compat.AnimationCallback switchIndeterminateModeCallback;
    private int visibilityAfterHide;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public @interface HideAnimationBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public @interface ShowAnimationBehavior {
    }

    abstract S createSpec(@NonNull Context context, @NonNull AttributeSet attributeSet);

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseProgressIndicator(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.lastShowStartTime = -1L;
        this.isIndeterminateModeChangeRequested = false;
        this.visibilityAfterHide = 4;
        this.delayedShow = new Runnable() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.1
            @Override // java.lang.Runnable
            public void run() {
                BaseProgressIndicator.this.internalShow();
            }
        };
        this.delayedHide = new Runnable() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.2
            @Override // java.lang.Runnable
            public void run() {
                BaseProgressIndicator.this.internalHide();
                BaseProgressIndicator.this.lastShowStartTime = -1L;
            }
        };
        this.switchIndeterminateModeCallback = new Animatable2Compat.AnimationCallback() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.3
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                BaseProgressIndicator.this.setIndeterminate(false);
                BaseProgressIndicator.this.setProgressCompat(0, false);
                BaseProgressIndicator.this.setProgressCompat(BaseProgressIndicator.this.storedProgress, BaseProgressIndicator.this.storedProgressAnimated);
            }
        };
        this.hideAnimationCallback = new Animatable2Compat.AnimationCallback() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.4
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                if (!BaseProgressIndicator.this.isIndeterminateModeChangeRequested) {
                    BaseProgressIndicator.this.setVisibility(BaseProgressIndicator.this.visibilityAfterHide);
                }
            }
        };
        Context context2 = getContext();
        this.spec = createSpec(context2, attrs);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.BaseProgressIndicator, defStyleAttr, defStyleRes, new int[0]);
        this.showDelay = a.getInt(R.styleable.BaseProgressIndicator_showDelay, -1);
        int minHideDelayUncapped = a.getInt(R.styleable.BaseProgressIndicator_minHideDelay, -1);
        this.minHideDelay = Math.min(minHideDelayUncapped, 1000);
        a.recycle();
        this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
        this.isParentDoneInitializing = true;
    }

    private void registerAnimationCallbacks() {
        if (getProgressDrawable() != null && getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().getAnimatorDelegate().registerAnimatorsCompleteCallback(this.switchIndeterminateModeCallback);
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
    }

    private void unregisterAnimationCallbacks() {
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
            getIndeterminateDrawable().getAnimatorDelegate().unregisterAnimatorsCompleteCallback();
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
        }
    }

    public void show() {
        if (this.showDelay > 0) {
            removeCallbacks(this.delayedShow);
            postDelayed(this.delayedShow, this.showDelay);
            return;
        }
        this.delayedShow.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalShow() {
        if (this.minHideDelay > 0) {
            this.lastShowStartTime = SystemClock.uptimeMillis();
        }
        setVisibility(0);
    }

    public void hide() {
        if (getVisibility() != 0) {
            removeCallbacks(this.delayedShow);
            return;
        }
        removeCallbacks(this.delayedHide);
        long timeElapsedSinceShowStart = SystemClock.uptimeMillis() - this.lastShowStartTime;
        boolean enoughTimeElapsed = timeElapsedSinceShowStart >= ((long) this.minHideDelay);
        if (enoughTimeElapsed) {
            this.delayedHide.run();
        } else {
            postDelayed(this.delayedHide, this.minHideDelay - timeElapsedSinceShowStart);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalHide() {
        ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).setVisible(false, false, true);
        if (isNoLongerNeedToBeVisible()) {
            setVisibility(4);
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        applyNewVisibility(visibility == 0);
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        applyNewVisibility(false);
    }

    protected void applyNewVisibility(boolean animate) {
        if (this.isParentDoneInitializing) {
            ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).setVisible(visibleToUser(), false, animate);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerAnimationCallbacks();
        if (visibleToUser()) {
            internalShow();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.delayedHide);
        removeCallbacks(this.delayedShow);
        ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).hideNow();
        unregisterAnimationCallbacks();
        super.onDetachedFromWindow();
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(@NonNull Canvas canvas) {
        int saveCount = canvas.save();
        if (getPaddingLeft() != 0 || getPaddingTop() != 0) {
            canvas.translate(getPaddingLeft(), getPaddingTop());
        }
        if (getPaddingRight() != 0 || getPaddingBottom() != 0) {
            int w = getWidth() - (getPaddingLeft() + getPaddingRight());
            int h = getHeight() - (getPaddingTop() + getPaddingBottom());
            canvas.clipRect(0, 0, w, h);
        }
        getCurrentDrawable().draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingLeft;
        int paddingTop;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DrawingDelegate<S> drawingDelegate = getCurrentDrawingDelegate();
        if (drawingDelegate != null) {
            int drawableMeasuredWidth = drawingDelegate.getPreferredWidth();
            int drawableMeasuredHeight = drawingDelegate.getPreferredHeight();
            if (drawableMeasuredWidth < 0) {
                paddingLeft = getMeasuredWidth();
            } else {
                paddingLeft = getPaddingLeft() + drawableMeasuredWidth + getPaddingRight();
            }
            if (drawableMeasuredHeight < 0) {
                paddingTop = getMeasuredHeight();
            } else {
                paddingTop = getPaddingTop() + drawableMeasuredHeight + getPaddingBottom();
            }
            setMeasuredDimension(paddingLeft, paddingTop);
        }
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        if (getCurrentDrawable() != null) {
            getCurrentDrawable().invalidateSelf();
        }
    }

    @Override // android.widget.ProgressBar
    @Nullable
    public Drawable getCurrentDrawable() {
        return isIndeterminate() ? getIndeterminateDrawable() : getProgressDrawable();
    }

    @Nullable
    private DrawingDelegate<S> getCurrentDrawingDelegate() {
        if (isIndeterminate()) {
            if (getIndeterminateDrawable() == null) {
                return null;
            }
            return getIndeterminateDrawable().getDrawingDelegate();
        } else if (getProgressDrawable() != null) {
            return getProgressDrawable().getDrawingDelegate();
        } else {
            return null;
        }
    }

    @Override // android.widget.ProgressBar
    public void setProgressDrawable(@Nullable Drawable drawable) {
        if (drawable == null) {
            super.setProgressDrawable(null);
        } else if (drawable instanceof DeterminateDrawable) {
            DeterminateDrawable<S> determinateDrawable = (DeterminateDrawable) drawable;
            determinateDrawable.hideNow();
            super.setProgressDrawable(determinateDrawable);
            determinateDrawable.setLevelByFraction(getProgress() / getMax());
        } else {
            throw new IllegalArgumentException("Cannot set framework drawable as progress drawable.");
        }
    }

    @Override // android.widget.ProgressBar
    public void setIndeterminateDrawable(@Nullable Drawable drawable) {
        if (drawable == null) {
            super.setIndeterminateDrawable(null);
        } else if (drawable instanceof IndeterminateDrawable) {
            ((DrawableWithAnimatedVisibilityChange) drawable).hideNow();
            super.setIndeterminateDrawable(drawable);
        } else {
            throw new IllegalArgumentException("Cannot set framework drawable as indeterminate drawable.");
        }
    }

    @Override // android.widget.ProgressBar
    @Nullable
    public DeterminateDrawable<S> getProgressDrawable() {
        return (DeterminateDrawable) super.getProgressDrawable();
    }

    @Override // android.widget.ProgressBar
    @Nullable
    public IndeterminateDrawable<S> getIndeterminateDrawable() {
        return (IndeterminateDrawable) super.getIndeterminateDrawable();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean visibleToUser() {
        return ViewCompat.isAttachedToWindow(this) && getWindowVisibility() == 0 && isEffectivelyVisible();
    }

    boolean isEffectivelyVisible() {
        View current = this;
        while (current.getVisibility() == 0) {
            ViewParent parent = current.getParent();
            if (parent == null) {
                return getWindowVisibility() == 0;
            } else if (!(parent instanceof View)) {
                return true;
            } else {
                current = (View) parent;
            }
        }
        return false;
    }

    private boolean isNoLongerNeedToBeVisible() {
        return (getProgressDrawable() == null || !getProgressDrawable().isVisible()) && (getIndeterminateDrawable() == null || !getIndeterminateDrawable().isVisible());
    }

    @Override // android.widget.ProgressBar
    public synchronized void setIndeterminate(boolean indeterminate) {
        if (indeterminate != isIndeterminate()) {
            if (visibleToUser() && indeterminate) {
                throw new IllegalStateException("Cannot switch to indeterminate mode while the progress indicator is visible.");
            }
            DrawableWithAnimatedVisibilityChange oldDrawable = (DrawableWithAnimatedVisibilityChange) getCurrentDrawable();
            if (oldDrawable != null) {
                oldDrawable.hideNow();
            }
            super.setIndeterminate(indeterminate);
            DrawableWithAnimatedVisibilityChange newDrawable = (DrawableWithAnimatedVisibilityChange) getCurrentDrawable();
            if (newDrawable != null) {
                newDrawable.setVisible(visibleToUser(), false, false);
            }
            this.isIndeterminateModeChangeRequested = false;
        }
    }

    @Px
    public int getTrackThickness() {
        return this.spec.trackThickness;
    }

    public void setTrackThickness(@Px int trackThickness) {
        if (this.spec.trackThickness != trackThickness) {
            this.spec.trackThickness = trackThickness;
            requestLayout();
        }
    }

    @NonNull
    public int[] getIndicatorColor() {
        return this.spec.indicatorColors;
    }

    public void setIndicatorColor(@ColorInt int... indicatorColors) {
        if (indicatorColors.length == 0) {
            indicatorColors = new int[]{MaterialColors.getColor(getContext(), R.attr.colorPrimary, -1)};
        }
        if (!Arrays.equals(getIndicatorColor(), indicatorColors)) {
            this.spec.indicatorColors = indicatorColors;
            getIndeterminateDrawable().getAnimatorDelegate().invalidateSpecValues();
            invalidate();
        }
    }

    @ColorInt
    public int getTrackColor() {
        return this.spec.trackColor;
    }

    public void setTrackColor(@ColorInt int trackColor) {
        if (this.spec.trackColor != trackColor) {
            this.spec.trackColor = trackColor;
            invalidate();
        }
    }

    @Px
    public int getTrackCornerRadius() {
        return this.spec.trackCornerRadius;
    }

    public void setTrackCornerRadius(@Px int trackCornerRadius) {
        if (this.spec.trackCornerRadius != trackCornerRadius) {
            this.spec.trackCornerRadius = Math.min(trackCornerRadius, this.spec.trackThickness / 2);
        }
    }

    public int getShowAnimationBehavior() {
        return this.spec.showAnimationBehavior;
    }

    public void setShowAnimationBehavior(int showAnimationBehavior) {
        this.spec.showAnimationBehavior = showAnimationBehavior;
        invalidate();
    }

    public int getHideAnimationBehavior() {
        return this.spec.hideAnimationBehavior;
    }

    public void setHideAnimationBehavior(int hideAnimationBehavior) {
        this.spec.hideAnimationBehavior = hideAnimationBehavior;
        invalidate();
    }

    @Override // android.widget.ProgressBar
    public synchronized void setProgress(int progress) {
        if (!isIndeterminate()) {
            setProgressCompat(progress, false);
        }
    }

    public void setProgressCompat(int progress, boolean animated) {
        if (isIndeterminate()) {
            if (getProgressDrawable() != null) {
                this.storedProgress = progress;
                this.storedProgressAnimated = animated;
                this.isIndeterminateModeChangeRequested = true;
                if (!getIndeterminateDrawable().isVisible() || this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(getContext().getContentResolver()) == 0.0f) {
                    this.switchIndeterminateModeCallback.onAnimationEnd(getIndeterminateDrawable());
                    return;
                } else {
                    getIndeterminateDrawable().getAnimatorDelegate().requestCancelAnimatorAfterCurrentCycle();
                    return;
                }
            }
            return;
        }
        super.setProgress(progress);
        if (getProgressDrawable() != null && !animated) {
            getProgressDrawable().jumpToCurrentState();
        }
    }

    public void setVisibilityAfterHide(int visibility) {
        if (visibility != 0 && visibility != 4 && visibility != 8) {
            throw new IllegalArgumentException("The component's visibility must be one of VISIBLE, INVISIBLE, and GONE defined in View.");
        }
        this.visibilityAfterHide = visibility;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public void setAnimatorDurationScaleProvider(@NonNull AnimatorDurationScaleProvider animatorDurationScaleProvider) {
        this.animatorDurationScaleProvider = animatorDurationScaleProvider;
        if (getProgressDrawable() != null) {
            getProgressDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider;
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider;
        }
    }
}
