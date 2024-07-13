package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.List;

/* loaded from: classes.dex */
public class ExtendedFloatingActionButton extends MaterialButton implements CoordinatorLayout.AttachedBehavior {
    private static final int ANIM_STATE_HIDING = 1;
    private static final int ANIM_STATE_NONE = 0;
    private static final int ANIM_STATE_SHOWING = 2;
    private int animState;
    private boolean animateShowBeforeLayout;
    @NonNull
    private final CoordinatorLayout.Behavior<ExtendedFloatingActionButton> behavior;
    private final AnimatorTracker changeVisibilityTracker;
    private final int collapsedSize;
    @NonNull
    private final MotionStrategy extendStrategy;
    private int extendedPaddingEnd;
    private int extendedPaddingStart;
    private final MotionStrategy hideStrategy;
    private boolean isExtended;
    private boolean isTransforming;
    @NonNull
    protected ColorStateList originalTextCsl;
    private final MotionStrategy showStrategy;
    @NonNull
    private final MotionStrategy shrinkStrategy;
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon;
    static final Property<View, Float> WIDTH = new Property<View, Float>(Float.class, "width") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.4
        @Override // android.util.Property
        public void set(@NonNull View object, @NonNull Float value) {
            object.getLayoutParams().width = value.intValue();
            object.requestLayout();
        }

        @Override // android.util.Property
        @NonNull
        public Float get(@NonNull View object) {
            return Float.valueOf(object.getLayoutParams().width);
        }
    };
    static final Property<View, Float> HEIGHT = new Property<View, Float>(Float.class, "height") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.5
        @Override // android.util.Property
        public void set(@NonNull View object, @NonNull Float value) {
            object.getLayoutParams().height = value.intValue();
            object.requestLayout();
        }

        @Override // android.util.Property
        @NonNull
        public Float get(@NonNull View object) {
            return Float.valueOf(object.getLayoutParams().height);
        }
    };
    static final Property<View, Float> PADDING_START = new Property<View, Float>(Float.class, "paddingStart") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.6
        @Override // android.util.Property
        public void set(@NonNull View object, @NonNull Float value) {
            ViewCompat.setPaddingRelative(object, value.intValue(), object.getPaddingTop(), ViewCompat.getPaddingEnd(object), object.getPaddingBottom());
        }

        @Override // android.util.Property
        @NonNull
        public Float get(@NonNull View object) {
            return Float.valueOf(ViewCompat.getPaddingStart(object));
        }
    };
    static final Property<View, Float> PADDING_END = new Property<View, Float>(Float.class, "paddingEnd") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.7
        @Override // android.util.Property
        public void set(@NonNull View object, @NonNull Float value) {
            ViewCompat.setPaddingRelative(object, ViewCompat.getPaddingStart(object), object.getPaddingTop(), value.intValue(), object.getPaddingBottom());
        }

        @Override // android.util.Property
        @NonNull
        public Float get(@NonNull View object) {
            return Float.valueOf(ViewCompat.getPaddingEnd(object));
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface Size {
        int getHeight();

        ViewGroup.LayoutParams getLayoutParams();

        int getPaddingEnd();

        int getPaddingStart();

        int getWidth();
    }

    /* loaded from: classes.dex */
    public static abstract class OnChangedCallback {
        public void onShown(ExtendedFloatingActionButton extendedFab) {
        }

        public void onHidden(ExtendedFloatingActionButton extendedFab) {
        }

        public void onExtended(ExtendedFloatingActionButton extendedFab) {
        }

        public void onShrunken(ExtendedFloatingActionButton extendedFab) {
        }
    }

    public ExtendedFloatingActionButton(@NonNull Context context) {
        this(context, null);
    }

    public ExtendedFloatingActionButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.extendedFloatingActionButtonStyle);
    }

    public ExtendedFloatingActionButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.animState = 0;
        this.changeVisibilityTracker = new AnimatorTracker();
        this.showStrategy = new ShowStrategy(this.changeVisibilityTracker);
        this.hideStrategy = new HideStrategy(this.changeVisibilityTracker);
        this.isExtended = true;
        this.isTransforming = false;
        this.animateShowBeforeLayout = false;
        Context context2 = getContext();
        this.behavior = new ExtendedFloatingActionButtonBehavior(context2, attrs);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.ExtendedFloatingActionButton, defStyleAttr, DEF_STYLE_RES, new int[0]);
        MotionSpec showMotionSpec = MotionSpec.createFromAttribute(context2, a, R.styleable.ExtendedFloatingActionButton_showMotionSpec);
        MotionSpec hideMotionSpec = MotionSpec.createFromAttribute(context2, a, R.styleable.ExtendedFloatingActionButton_hideMotionSpec);
        MotionSpec extendMotionSpec = MotionSpec.createFromAttribute(context2, a, R.styleable.ExtendedFloatingActionButton_extendMotionSpec);
        MotionSpec shrinkMotionSpec = MotionSpec.createFromAttribute(context2, a, R.styleable.ExtendedFloatingActionButton_shrinkMotionSpec);
        this.collapsedSize = a.getDimensionPixelSize(R.styleable.ExtendedFloatingActionButton_collapsedSize, -1);
        this.extendedPaddingStart = ViewCompat.getPaddingStart(this);
        this.extendedPaddingEnd = ViewCompat.getPaddingEnd(this);
        AnimatorTracker changeSizeTracker = new AnimatorTracker();
        this.extendStrategy = new ChangeSizeStrategy(changeSizeTracker, new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.1
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public int getWidth() {
                return (ExtendedFloatingActionButton.this.getMeasuredWidth() - (ExtendedFloatingActionButton.this.getCollapsedPadding() * 2)) + ExtendedFloatingActionButton.this.extendedPaddingStart + ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public int getHeight() {
                return ExtendedFloatingActionButton.this.getMeasuredHeight();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public int getPaddingStart() {
                return ExtendedFloatingActionButton.this.extendedPaddingStart;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public ViewGroup.LayoutParams getLayoutParams() {
                return new ViewGroup.LayoutParams(-2, -2);
            }
        }, true);
        this.shrinkStrategy = new ChangeSizeStrategy(changeSizeTracker, new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.2
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public int getWidth() {
                return ExtendedFloatingActionButton.this.getCollapsedSize();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public int getHeight() {
                return ExtendedFloatingActionButton.this.getCollapsedSize();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public int getPaddingStart() {
                return ExtendedFloatingActionButton.this.getCollapsedPadding();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.getCollapsedPadding();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public ViewGroup.LayoutParams getLayoutParams() {
                return new ViewGroup.LayoutParams(getWidth(), getHeight());
            }
        }, false);
        this.showStrategy.setMotionSpec(showMotionSpec);
        this.hideStrategy.setMotionSpec(hideMotionSpec);
        this.extendStrategy.setMotionSpec(extendMotionSpec);
        this.shrinkStrategy.setMotionSpec(shrinkMotionSpec);
        a.recycle();
        ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder(context2, attrs, defStyleAttr, DEF_STYLE_RES, ShapeAppearanceModel.PILL).build();
        setShapeAppearanceModel(shapeAppearanceModel);
        saveOriginalTextCsl();
    }

    @Override // android.widget.TextView
    public void setTextColor(int color) {
        super.setTextColor(color);
        saveOriginalTextCsl();
    }

    @Override // android.widget.TextView
    public void setTextColor(@NonNull ColorStateList colors) {
        super.setTextColor(colors);
        saveOriginalTextCsl();
    }

    private void saveOriginalTextCsl() {
        this.originalTextCsl = getTextColors();
    }

    public void silentlyUpdateTextColor(@NonNull ColorStateList csl) {
        super.setTextColor(csl);
    }

    @Override // com.google.android.material.button.MaterialButton, android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isExtended && TextUtils.isEmpty(getText()) && getIcon() != null) {
            this.isExtended = false;
            this.shrinkStrategy.performNow();
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    @NonNull
    public CoordinatorLayout.Behavior<ExtendedFloatingActionButton> getBehavior() {
        return this.behavior;
    }

    public void setExtended(boolean extended) {
        if (this.isExtended != extended) {
            MotionStrategy motionStrategy = extended ? this.extendStrategy : this.shrinkStrategy;
            if (!motionStrategy.shouldCancel()) {
                motionStrategy.performNow();
            }
        }
    }

    public final boolean isExtended() {
        return this.isExtended;
    }

    public void setAnimateShowBeforeLayout(boolean animateShowBeforeLayout) {
        this.animateShowBeforeLayout = animateShowBeforeLayout;
    }

    @Override // android.widget.TextView, android.view.View
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        if (this.isExtended && !this.isTransforming) {
            this.extendedPaddingStart = start;
            this.extendedPaddingEnd = end;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        if (this.isExtended && !this.isTransforming) {
            this.extendedPaddingStart = ViewCompat.getPaddingStart(this);
            this.extendedPaddingEnd = ViewCompat.getPaddingEnd(this);
        }
    }

    public void addOnShowAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.showStrategy.addAnimationListener(listener);
    }

    public void removeOnShowAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.showStrategy.removeAnimationListener(listener);
    }

    public void addOnHideAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.hideStrategy.addAnimationListener(listener);
    }

    public void removeOnHideAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.hideStrategy.removeAnimationListener(listener);
    }

    public void addOnShrinkAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.shrinkStrategy.addAnimationListener(listener);
    }

    public void removeOnShrinkAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.shrinkStrategy.removeAnimationListener(listener);
    }

    public void addOnExtendAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.extendStrategy.addAnimationListener(listener);
    }

    public void removeOnExtendAnimationListener(@NonNull Animator.AnimatorListener listener) {
        this.extendStrategy.removeAnimationListener(listener);
    }

    public void hide() {
        performMotion(this.hideStrategy, null);
    }

    public void hide(@NonNull OnChangedCallback callback) {
        performMotion(this.hideStrategy, callback);
    }

    public void show() {
        performMotion(this.showStrategy, null);
    }

    public void show(@NonNull OnChangedCallback callback) {
        performMotion(this.showStrategy, callback);
    }

    public void extend() {
        performMotion(this.extendStrategy, null);
    }

    public void extend(@NonNull OnChangedCallback callback) {
        performMotion(this.extendStrategy, callback);
    }

    public void shrink() {
        performMotion(this.shrinkStrategy, null);
    }

    public void shrink(@NonNull OnChangedCallback callback) {
        performMotion(this.shrinkStrategy, callback);
    }

    @Nullable
    public MotionSpec getShowMotionSpec() {
        return this.showStrategy.getMotionSpec();
    }

    public void setShowMotionSpec(@Nullable MotionSpec spec) {
        this.showStrategy.setMotionSpec(spec);
    }

    public void setShowMotionSpecResource(@AnimatorRes int id) {
        setShowMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    @Nullable
    public MotionSpec getHideMotionSpec() {
        return this.hideStrategy.getMotionSpec();
    }

    public void setHideMotionSpec(@Nullable MotionSpec spec) {
        this.hideStrategy.setMotionSpec(spec);
    }

    public void setHideMotionSpecResource(@AnimatorRes int id) {
        setHideMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    @Nullable
    public MotionSpec getExtendMotionSpec() {
        return this.extendStrategy.getMotionSpec();
    }

    public void setExtendMotionSpec(@Nullable MotionSpec spec) {
        this.extendStrategy.setMotionSpec(spec);
    }

    public void setExtendMotionSpecResource(@AnimatorRes int id) {
        setExtendMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    @Nullable
    public MotionSpec getShrinkMotionSpec() {
        return this.shrinkStrategy.getMotionSpec();
    }

    public void setShrinkMotionSpec(@Nullable MotionSpec spec) {
        this.shrinkStrategy.setMotionSpec(spec);
    }

    public void setShrinkMotionSpecResource(@AnimatorRes int id) {
        setShrinkMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    public void performMotion(@NonNull final MotionStrategy strategy, @Nullable final OnChangedCallback callback) {
        if (!strategy.shouldCancel()) {
            boolean shouldAnimate = shouldAnimateVisibilityChange();
            if (!shouldAnimate) {
                strategy.performNow();
                strategy.onChange(callback);
                return;
            }
            measure(0, 0);
            Animator animator = strategy.createAnimator();
            animator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.3
                private boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation) {
                    strategy.onAnimationStart(animation);
                    this.cancelled = false;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                    this.cancelled = true;
                    strategy.onAnimationCancel();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    strategy.onAnimationEnd();
                    if (!this.cancelled) {
                        strategy.onChange(callback);
                    }
                }
            });
            for (Animator.AnimatorListener l : strategy.getListeners()) {
                animator.addListener(l);
            }
            animator.start();
        }
    }

    public boolean isOrWillBeShown() {
        return getVisibility() != 0 ? this.animState == 2 : this.animState != 1;
    }

    public boolean isOrWillBeHidden() {
        return getVisibility() == 0 ? this.animState == 1 : this.animState != 2;
    }

    private boolean shouldAnimateVisibilityChange() {
        return (ViewCompat.isLaidOut(this) || (!isOrWillBeShown() && this.animateShowBeforeLayout)) && !isInEditMode();
    }

    @VisibleForTesting
    int getCollapsedSize() {
        return this.collapsedSize < 0 ? (Math.min(ViewCompat.getPaddingStart(this), ViewCompat.getPaddingEnd(this)) * 2) + getIconSize() : this.collapsedSize;
    }

    int getCollapsedPadding() {
        return (getCollapsedSize() - getIconSize()) / 2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton> extends CoordinatorLayout.Behavior<T> {
        private static final boolean AUTO_HIDE_DEFAULT = false;
        private static final boolean AUTO_SHRINK_DEFAULT = true;
        private boolean autoHideEnabled;
        private boolean autoShrinkEnabled;
        @Nullable
        private OnChangedCallback internalAutoHideCallback;
        @Nullable
        private OnChangedCallback internalAutoShrinkCallback;
        private Rect tmpRect;

        public ExtendedFloatingActionButtonBehavior() {
            this.autoHideEnabled = false;
            this.autoShrinkEnabled = AUTO_SHRINK_DEFAULT;
        }

        public ExtendedFloatingActionButtonBehavior(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExtendedFloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = a.getBoolean(R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoHide, false);
            this.autoShrinkEnabled = a.getBoolean(R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoShrink, AUTO_SHRINK_DEFAULT);
            a.recycle();
        }

        public void setAutoHideEnabled(boolean autoHide) {
            this.autoHideEnabled = autoHide;
        }

        public boolean isAutoHideEnabled() {
            return this.autoHideEnabled;
        }

        public void setAutoShrinkEnabled(boolean autoShrink) {
            this.autoShrinkEnabled = autoShrink;
        }

        public boolean isAutoShrinkEnabled() {
            return this.autoShrinkEnabled;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout parent, @NonNull ExtendedFloatingActionButton child, @NonNull Rect rect) {
            return super.getInsetDodgeRect(parent, (CoordinatorLayout) child, rect);
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams lp) {
            if (lp.dodgeInsetEdges == 0) {
                lp.dodgeInsetEdges = 80;
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onDependentViewChanged(CoordinatorLayout parent, @NonNull ExtendedFloatingActionButton child, View dependency) {
            if (dependency instanceof AppBarLayout) {
                updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child);
                return false;
            } else if (isBottomSheet(dependency)) {
                updateFabVisibilityForBottomSheet(dependency, child);
                return false;
            } else {
                return false;
            }
        }

        private static boolean isBottomSheet(@NonNull View view) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                return ((CoordinatorLayout.LayoutParams) lp).getBehavior() instanceof BottomSheetBehavior;
            }
            return false;
        }

        @VisibleForTesting
        void setInternalAutoHideCallback(@Nullable OnChangedCallback callback) {
            this.internalAutoHideCallback = callback;
        }

        @VisibleForTesting
        void setInternalAutoShrinkCallback(@Nullable OnChangedCallback callback) {
            this.internalAutoShrinkCallback = callback;
        }

        private boolean shouldUpdateVisibility(@NonNull View dependency, @NonNull ExtendedFloatingActionButton child) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            if ((this.autoHideEnabled || this.autoShrinkEnabled) && lp.getAnchorId() == dependency.getId()) {
                return AUTO_SHRINK_DEFAULT;
            }
            return false;
        }

        private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout parent, @NonNull AppBarLayout appBarLayout, @NonNull ExtendedFloatingActionButton child) {
            if (!shouldUpdateVisibility(appBarLayout, child)) {
                return false;
            }
            if (this.tmpRect == null) {
                this.tmpRect = new Rect();
            }
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(parent, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                shrinkOrHide(child);
            } else {
                extendOrShow(child);
            }
            return AUTO_SHRINK_DEFAULT;
        }

        private boolean updateFabVisibilityForBottomSheet(@NonNull View bottomSheet, @NonNull ExtendedFloatingActionButton child) {
            if (!shouldUpdateVisibility(bottomSheet, child)) {
                return false;
            }
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            if (bottomSheet.getTop() < (child.getHeight() / 2) + lp.topMargin) {
                shrinkOrHide(child);
            } else {
                extendOrShow(child);
            }
            return AUTO_SHRINK_DEFAULT;
        }

        protected void shrinkOrHide(@NonNull ExtendedFloatingActionButton fab) {
            OnChangedCallback callback = this.autoShrinkEnabled ? this.internalAutoShrinkCallback : this.internalAutoHideCallback;
            MotionStrategy strategy = this.autoShrinkEnabled ? fab.shrinkStrategy : fab.hideStrategy;
            fab.performMotion(strategy, callback);
        }

        protected void extendOrShow(@NonNull ExtendedFloatingActionButton fab) {
            OnChangedCallback callback = this.autoShrinkEnabled ? this.internalAutoShrinkCallback : this.internalAutoHideCallback;
            MotionStrategy strategy = this.autoShrinkEnabled ? fab.extendStrategy : fab.showStrategy;
            fab.performMotion(strategy, callback);
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull ExtendedFloatingActionButton child, int layoutDirection) {
            List<View> dependencies = parent.getDependencies(child);
            int count = dependencies.size();
            for (int i = 0; i < count; i++) {
                View dependency = dependencies.get(i);
                if (dependency instanceof AppBarLayout) {
                    if (updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child)) {
                        break;
                    }
                } else {
                    if (isBottomSheet(dependency) && updateFabVisibilityForBottomSheet(dependency, child)) {
                        break;
                    }
                }
            }
            parent.onLayoutChild(child, layoutDirection);
            return AUTO_SHRINK_DEFAULT;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ChangeSizeStrategy extends BaseMotionStrategy {
        private final boolean extending;
        private final Size size;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ChangeSizeStrategy(AnimatorTracker animatorTracker, Size size, boolean extending) {
            super(this$0, animatorTracker);
            ExtendedFloatingActionButton.this = this$0;
            this.size = size;
            this.extending = extending;
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public void performNow() {
            ExtendedFloatingActionButton.this.isExtended = this.extending;
            ViewGroup.LayoutParams layoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.size.getLayoutParams().width;
                layoutParams.height = this.size.getLayoutParams().height;
                ViewCompat.setPaddingRelative(ExtendedFloatingActionButton.this, this.size.getPaddingStart(), ExtendedFloatingActionButton.this.getPaddingTop(), this.size.getPaddingEnd(), ExtendedFloatingActionButton.this.getPaddingBottom());
                ExtendedFloatingActionButton.this.requestLayout();
            }
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public void onChange(@Nullable OnChangedCallback callback) {
            if (callback != null) {
                if (this.extending) {
                    callback.onExtended(ExtendedFloatingActionButton.this);
                } else {
                    callback.onShrunken(ExtendedFloatingActionButton.this);
                }
            }
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public int getDefaultMotionSpecResource() {
            return this.extending ? R.animator.mtrl_extended_fab_change_size_expand_motion_spec : R.animator.mtrl_extended_fab_change_size_collapse_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        @NonNull
        public AnimatorSet createAnimator() {
            MotionSpec spec = getCurrentMotionSpec();
            if (spec.hasPropertyValues("width")) {
                PropertyValuesHolder[] widthValues = spec.getPropertyValues("width");
                widthValues[0].setFloatValues(ExtendedFloatingActionButton.this.getWidth(), this.size.getWidth());
                spec.setPropertyValues("width", widthValues);
            }
            if (spec.hasPropertyValues("height")) {
                PropertyValuesHolder[] heightValues = spec.getPropertyValues("height");
                heightValues[0].setFloatValues(ExtendedFloatingActionButton.this.getHeight(), this.size.getHeight());
                spec.setPropertyValues("height", heightValues);
            }
            if (spec.hasPropertyValues("paddingStart")) {
                PropertyValuesHolder[] paddingValues = spec.getPropertyValues("paddingStart");
                paddingValues[0].setFloatValues(ViewCompat.getPaddingStart(ExtendedFloatingActionButton.this), this.size.getPaddingStart());
                spec.setPropertyValues("paddingStart", paddingValues);
            }
            if (spec.hasPropertyValues("paddingEnd")) {
                PropertyValuesHolder[] paddingValues2 = spec.getPropertyValues("paddingEnd");
                paddingValues2[0].setFloatValues(ViewCompat.getPaddingEnd(ExtendedFloatingActionButton.this), this.size.getPaddingEnd());
                spec.setPropertyValues("paddingEnd", paddingValues2);
            }
            if (spec.hasPropertyValues("labelOpacity")) {
                PropertyValuesHolder[] labelOpacityValues = spec.getPropertyValues("labelOpacity");
                float startValue = this.extending ? 0.0f : 1.0f;
                float endValue = this.extending ? 1.0f : 0.0f;
                labelOpacityValues[0].setFloatValues(startValue, endValue);
                spec.setPropertyValues("labelOpacity", labelOpacityValues);
            }
            return super.createAnimator(spec);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            ExtendedFloatingActionButton.this.isExtended = this.extending;
            ExtendedFloatingActionButton.this.isTransforming = true;
            ExtendedFloatingActionButton.this.setHorizontallyScrolling(true);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public void onAnimationEnd() {
            super.onAnimationEnd();
            ExtendedFloatingActionButton.this.isTransforming = false;
            ExtendedFloatingActionButton.this.setHorizontallyScrolling(false);
            ViewGroup.LayoutParams layoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.size.getLayoutParams().width;
                layoutParams.height = this.size.getLayoutParams().height;
            }
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public boolean shouldCancel() {
            return this.extending == ExtendedFloatingActionButton.this.isExtended || ExtendedFloatingActionButton.this.getIcon() == null || TextUtils.isEmpty(ExtendedFloatingActionButton.this.getText());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ShowStrategy extends BaseMotionStrategy {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ShowStrategy(AnimatorTracker animatorTracker) {
            super(this$0, animatorTracker);
            ExtendedFloatingActionButton.this = this$0;
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.setAlpha(1.0f);
            ExtendedFloatingActionButton.this.setScaleY(1.0f);
            ExtendedFloatingActionButton.this.setScaleX(1.0f);
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public void onChange(@Nullable OnChangedCallback callback) {
            if (callback != null) {
                callback.onShown(ExtendedFloatingActionButton.this);
            }
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public int getDefaultMotionSpecResource() {
            return R.animator.mtrl_extended_fab_show_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.animState = 2;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public void onAnimationEnd() {
            super.onAnimationEnd();
            ExtendedFloatingActionButton.this.animState = 0;
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public boolean shouldCancel() {
            return ExtendedFloatingActionButton.this.isOrWillBeShown();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class HideStrategy extends BaseMotionStrategy {
        private boolean isCancelled;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public HideStrategy(AnimatorTracker animatorTracker) {
            super(this$0, animatorTracker);
            ExtendedFloatingActionButton.this = this$0;
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(8);
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public void onChange(@Nullable OnChangedCallback callback) {
            if (callback != null) {
                callback.onHidden(ExtendedFloatingActionButton.this);
            }
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public boolean shouldCancel() {
            return ExtendedFloatingActionButton.this.isOrWillBeHidden();
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public int getDefaultMotionSpecResource() {
            return R.animator.mtrl_extended_fab_hide_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            this.isCancelled = false;
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.animState = 1;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public void onAnimationCancel() {
            super.onAnimationCancel();
            this.isCancelled = true;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public void onAnimationEnd() {
            super.onAnimationEnd();
            ExtendedFloatingActionButton.this.animState = 0;
            if (!this.isCancelled) {
                ExtendedFloatingActionButton.this.setVisibility(8);
            }
        }
    }
}
