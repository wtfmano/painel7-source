package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AppBarLayout extends LinearLayout implements CoordinatorLayout.AttachedBehavior {
    private static final int DEF_STYLE_RES = R.style.Widget_Design_AppBarLayout;
    private static final int INVALID_SCROLL_RANGE = -1;
    static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
    static final int PENDING_ACTION_COLLAPSED = 2;
    static final int PENDING_ACTION_EXPANDED = 1;
    static final int PENDING_ACTION_FORCE = 8;
    static final int PENDING_ACTION_NONE = 0;
    private int currentOffset;
    private int downPreScrollRange;
    private int downScrollRange;
    @Nullable
    private ValueAnimator elevationOverlayAnimator;
    private boolean haveChildWithInterpolator;
    @Nullable
    private WindowInsetsCompat lastInsets;
    private boolean liftOnScroll;
    @Nullable
    private WeakReference<View> liftOnScrollTargetView;
    @IdRes
    private int liftOnScrollTargetViewId;
    private boolean liftable;
    private boolean liftableOverride;
    private boolean lifted;
    private List<BaseOnOffsetChangedListener> listeners;
    private int pendingAction;
    @Nullable
    private Drawable statusBarForeground;
    private int[] tmpStatesArray;
    private int totalScrollRange;

    /* loaded from: classes.dex */
    public interface BaseOnOffsetChangedListener<T extends AppBarLayout> {
        void onOffsetChanged(T t, int i);
    }

    /* loaded from: classes.dex */
    public interface OnOffsetChangedListener extends BaseOnOffsetChangedListener<AppBarLayout> {
        @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    public AppBarLayout(@NonNull Context context) {
        this(context, null);
    }

    public AppBarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.appBarLayoutStyle);
    }

    public AppBarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        this.pendingAction = 0;
        Context context2 = getContext();
        setOrientation(1);
        if (Build.VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setBoundsViewOutlineProvider(this);
            ViewUtilsLollipop.setStateListAnimatorFromAttrs(this, attrs, defStyleAttr, DEF_STYLE_RES);
        }
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.AppBarLayout, defStyleAttr, DEF_STYLE_RES, new int[0]);
        ViewCompat.setBackground(this, a.getDrawable(R.styleable.AppBarLayout_android_background));
        if (getBackground() instanceof ColorDrawable) {
            ColorDrawable background = (ColorDrawable) getBackground();
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(background.getColor()));
            materialShapeDrawable.initializeElevationOverlay(context2);
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
        if (a.hasValue(R.styleable.AppBarLayout_expanded)) {
            setExpanded(a.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
        }
        if (Build.VERSION.SDK_INT >= 21 && a.hasValue(R.styleable.AppBarLayout_elevation)) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, a.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (a.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                setKeyboardNavigationCluster(a.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }
            if (a.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                setTouchscreenBlocksFocus(a.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }
        this.liftOnScroll = a.getBoolean(R.styleable.AppBarLayout_liftOnScroll, false);
        this.liftOnScrollTargetViewId = a.getResourceId(R.styleable.AppBarLayout_liftOnScrollTargetViewId, -1);
        setStatusBarForeground(a.getDrawable(R.styleable.AppBarLayout_statusBarForeground));
        a.recycle();
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.appbar.AppBarLayout.1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                return AppBarLayout.this.onWindowInsetChanged(insets);
            }
        });
    }

    public void addOnOffsetChangedListener(@Nullable BaseOnOffsetChangedListener listener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        if (listener != null && !this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener listener) {
        addOnOffsetChangedListener((BaseOnOffsetChangedListener) listener);
    }

    public void removeOnOffsetChangedListener(@Nullable BaseOnOffsetChangedListener listener) {
        if (this.listeners != null && listener != null) {
            this.listeners.remove(listener);
        }
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener listener) {
        removeOnOffsetChangedListener((BaseOnOffsetChangedListener) listener);
    }

    public void setStatusBarForeground(@Nullable Drawable drawable) {
        if (this.statusBarForeground != drawable) {
            if (this.statusBarForeground != null) {
                this.statusBarForeground.setCallback(null);
            }
            this.statusBarForeground = drawable != null ? drawable.mutate() : null;
            if (this.statusBarForeground != null) {
                if (this.statusBarForeground.isStateful()) {
                    this.statusBarForeground.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.statusBarForeground, ViewCompat.getLayoutDirection(this));
                this.statusBarForeground.setVisible(getVisibility() == 0, false);
                this.statusBarForeground.setCallback(this);
            }
            updateWillNotDraw();
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setStatusBarForegroundColor(@ColorInt int color) {
        setStatusBarForeground(new ColorDrawable(color));
    }

    public void setStatusBarForegroundResource(@DrawableRes int resId) {
        setStatusBarForeground(AppCompatResources.getDrawable(getContext(), resId));
    }

    @Nullable
    public Drawable getStatusBarForeground() {
        return this.statusBarForeground;
    }

    @Override // android.view.View
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        if (shouldDrawStatusBarForeground()) {
            int saveCount = canvas.save();
            canvas.translate(0.0f, -this.currentOffset);
            this.statusBarForeground.draw(canvas);
            canvas.restoreToCount(saveCount);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] state = getDrawableState();
        Drawable d = this.statusBarForeground;
        if (d != null && d.isStateful() && d.setState(state)) {
            invalidateDrawable(d);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who) || who == this.statusBarForeground;
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        boolean visible = visibility == 0;
        if (this.statusBarForeground != null) {
            this.statusBarForeground.setVisible(visible, false);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != 1073741824 && ViewCompat.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
            int newHeight = getMeasuredHeight();
            switch (heightMode) {
                case Integer.MIN_VALUE:
                    newHeight = MathUtils.clamp(getMeasuredHeight() + getTopInset(), 0, View.MeasureSpec.getSize(heightMeasureSpec));
                    break;
                case 0:
                    newHeight += getTopInset();
                    break;
            }
            setMeasuredDimension(getMeasuredWidth(), newHeight);
        }
        invalidateScrollRanges();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (ViewCompat.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
            int topInset = getTopInset();
            for (int z = getChildCount() - 1; z >= 0; z--) {
                ViewCompat.offsetTopAndBottom(getChildAt(z), topInset);
            }
        }
        invalidateScrollRanges();
        this.haveChildWithInterpolator = false;
        int i = 0;
        int z2 = getChildCount();
        while (true) {
            if (i >= z2) {
                break;
            }
            View child = getChildAt(i);
            LayoutParams childLp = (LayoutParams) child.getLayoutParams();
            Interpolator interpolator = childLp.getScrollInterpolator();
            if (interpolator == null) {
                i++;
            } else {
                this.haveChildWithInterpolator = true;
                break;
            }
        }
        if (this.statusBarForeground != null) {
            this.statusBarForeground.setBounds(0, 0, getWidth(), getTopInset());
        }
        if (!this.liftableOverride) {
            setLiftableState(this.liftOnScroll || hasCollapsibleChild());
        }
    }

    private void updateWillNotDraw() {
        setWillNotDraw(!shouldDrawStatusBarForeground());
    }

    private boolean shouldDrawStatusBarForeground() {
        return this.statusBarForeground != null && getTopInset() > 0;
    }

    private boolean hasCollapsibleChild() {
        int z = getChildCount();
        for (int i = 0; i < z; i++) {
            if (((LayoutParams) getChildAt(i).getLayoutParams()).isCollapsible()) {
                return true;
            }
        }
        return false;
    }

    private void invalidateScrollRanges() {
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int orientation) {
        if (orientation != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(orientation);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    @NonNull
    public CoordinatorLayout.Behavior<AppBarLayout> getBehavior() {
        return new Behavior();
    }

    @Override // android.view.View
    @RequiresApi(21)
    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeUtils.setElevation(this, elevation);
    }

    public void setExpanded(boolean expanded) {
        setExpanded(expanded, ViewCompat.isLaidOut(this));
    }

    public void setExpanded(boolean expanded, boolean animate) {
        setExpanded(expanded, animate, true);
    }

    private void setExpanded(boolean expanded, boolean animate, boolean force) {
        this.pendingAction = (force ? 8 : 0) | (animate ? 4 : 0) | (expanded ? 1 : 2);
        requestLayout();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (Build.VERSION.SDK_INT >= 19 && (p instanceof LinearLayout.LayoutParams)) {
            return new LayoutParams((LinearLayout.LayoutParams) p);
        }
        if (p instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) p);
        }
        return new LayoutParams(p);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearLiftOnScrollTargetView();
    }

    boolean hasChildWithInterpolator() {
        return this.haveChildWithInterpolator;
    }

    public final int getTotalScrollRange() {
        if (this.totalScrollRange != -1) {
            return this.totalScrollRange;
        }
        int range = 0;
        int i = 0;
        int z = getChildCount();
        while (true) {
            if (i >= z) {
                break;
            }
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int childHeight = child.getMeasuredHeight();
            int flags = lp.scrollFlags;
            if ((flags & 1) == 0) {
                break;
            }
            range += lp.topMargin + childHeight + lp.bottomMargin;
            if (i == 0 && ViewCompat.getFitsSystemWindows(child)) {
                range -= getTopInset();
            }
            if ((flags & 2) == 0) {
                i++;
            } else {
                range -= ViewCompat.getMinimumHeight(child);
                break;
            }
        }
        int max = Math.max(0, range);
        this.totalScrollRange = max;
        return max;
    }

    boolean hasScrollableChildren() {
        return getTotalScrollRange() != 0;
    }

    int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    int getDownNestedPreScrollRange() {
        int childRange;
        if (this.downPreScrollRange != -1) {
            return this.downPreScrollRange;
        }
        int range = 0;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int childHeight = child.getMeasuredHeight();
            int flags = lp.scrollFlags;
            if ((flags & 5) == 5) {
                int childRange2 = lp.topMargin + lp.bottomMargin;
                if ((flags & 8) != 0) {
                    childRange = childRange2 + ViewCompat.getMinimumHeight(child);
                } else if ((flags & 2) != 0) {
                    childRange = childRange2 + (childHeight - ViewCompat.getMinimumHeight(child));
                } else {
                    childRange = childRange2 + childHeight;
                }
                if (i == 0 && ViewCompat.getFitsSystemWindows(child)) {
                    childRange = Math.min(childRange, childHeight - getTopInset());
                }
                range += childRange;
            } else if (range > 0) {
                break;
            }
        }
        int max = Math.max(0, range);
        this.downPreScrollRange = max;
        return max;
    }

    int getDownNestedScrollRange() {
        if (this.downScrollRange != -1) {
            return this.downScrollRange;
        }
        int range = 0;
        int i = 0;
        int z = getChildCount();
        while (true) {
            if (i >= z) {
                break;
            }
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int childHeight = child.getMeasuredHeight();
            int childHeight2 = childHeight + lp.topMargin + lp.bottomMargin;
            int flags = lp.scrollFlags;
            if ((flags & 1) == 0) {
                break;
            }
            range += childHeight2;
            if ((flags & 2) == 0) {
                i++;
            } else {
                range -= ViewCompat.getMinimumHeight(child);
                break;
            }
        }
        int max = Math.max(0, range);
        this.downScrollRange = max;
        return max;
    }

    void onOffsetChanged(int offset) {
        this.currentOffset = offset;
        if (!willNotDraw()) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        if (this.listeners != null) {
            int z = this.listeners.size();
            for (int i = 0; i < z; i++) {
                BaseOnOffsetChangedListener listener = this.listeners.get(i);
                if (listener != null) {
                    listener.onOffsetChanged(this, offset);
                }
            }
        }
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int minHeight = ViewCompat.getMinimumHeight(this);
        if (minHeight != 0) {
            return (minHeight * 2) + topInset;
        }
        int childCount = getChildCount();
        int lastChildMinHeight = childCount >= 1 ? ViewCompat.getMinimumHeight(getChildAt(childCount - 1)) : 0;
        if (lastChildMinHeight != 0) {
            return (lastChildMinHeight * 2) + topInset;
        }
        return getHeight() / 3;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.tmpStatesArray == null) {
            this.tmpStatesArray = new int[4];
        }
        int[] extraStates = this.tmpStatesArray;
        int[] states = super.onCreateDrawableState(extraStates.length + extraSpace);
        extraStates[0] = this.liftable ? R.attr.state_liftable : -R.attr.state_liftable;
        extraStates[1] = (this.liftable && this.lifted) ? R.attr.state_lifted : -R.attr.state_lifted;
        extraStates[2] = this.liftable ? R.attr.state_collapsible : -R.attr.state_collapsible;
        extraStates[3] = (this.liftable && this.lifted) ? R.attr.state_collapsed : -R.attr.state_collapsed;
        return mergeDrawableStates(states, extraStates);
    }

    public boolean setLiftable(boolean liftable) {
        this.liftableOverride = true;
        return setLiftableState(liftable);
    }

    private boolean setLiftableState(boolean liftable) {
        if (this.liftable != liftable) {
            this.liftable = liftable;
            refreshDrawableState();
            return true;
        }
        return false;
    }

    public boolean setLifted(boolean lifted) {
        return setLiftedState(lifted);
    }

    public boolean isLifted() {
        return this.lifted;
    }

    boolean setLiftedState(boolean lifted) {
        if (this.lifted != lifted) {
            this.lifted = lifted;
            refreshDrawableState();
            if (this.liftOnScroll && (getBackground() instanceof MaterialShapeDrawable)) {
                startLiftOnScrollElevationOverlayAnimation((MaterialShapeDrawable) getBackground(), lifted);
            }
            return true;
        }
        return false;
    }

    private void startLiftOnScrollElevationOverlayAnimation(@NonNull final MaterialShapeDrawable background, boolean lifted) {
        float appBarElevation = getResources().getDimension(R.dimen.design_appbar_elevation);
        float fromElevation = lifted ? 0.0f : appBarElevation;
        float toElevation = lifted ? appBarElevation : 0.0f;
        if (this.elevationOverlayAnimator != null) {
            this.elevationOverlayAnimator.cancel();
        }
        this.elevationOverlayAnimator = ValueAnimator.ofFloat(fromElevation, toElevation);
        this.elevationOverlayAnimator.setDuration(getResources().getInteger(R.integer.app_bar_elevation_anim_duration));
        this.elevationOverlayAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        this.elevationOverlayAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                background.setElevation(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.elevationOverlayAnimator.start();
    }

    public void setLiftOnScroll(boolean liftOnScroll) {
        this.liftOnScroll = liftOnScroll;
    }

    public boolean isLiftOnScroll() {
        return this.liftOnScroll;
    }

    public void setLiftOnScrollTargetViewId(@IdRes int liftOnScrollTargetViewId) {
        this.liftOnScrollTargetViewId = liftOnScrollTargetViewId;
        clearLiftOnScrollTargetView();
    }

    @IdRes
    public int getLiftOnScrollTargetViewId() {
        return this.liftOnScrollTargetViewId;
    }

    boolean shouldLift(@Nullable View defaultScrollingView) {
        View scrollingView = findLiftOnScrollTargetView(defaultScrollingView);
        if (scrollingView == null) {
            scrollingView = defaultScrollingView;
        }
        return scrollingView != null && (scrollingView.canScrollVertically(-1) || scrollingView.getScrollY() > 0);
    }

    @Nullable
    private View findLiftOnScrollTargetView(@Nullable View defaultScrollingView) {
        if (this.liftOnScrollTargetView == null && this.liftOnScrollTargetViewId != -1) {
            View targetView = null;
            if (defaultScrollingView != null) {
                targetView = defaultScrollingView.findViewById(this.liftOnScrollTargetViewId);
            }
            if (targetView == null && (getParent() instanceof ViewGroup)) {
                targetView = ((ViewGroup) getParent()).findViewById(this.liftOnScrollTargetViewId);
            }
            if (targetView != null) {
                this.liftOnScrollTargetView = new WeakReference<>(targetView);
            }
        }
        if (this.liftOnScrollTargetView != null) {
            return this.liftOnScrollTargetView.get();
        }
        return null;
    }

    private void clearLiftOnScrollTargetView() {
        if (this.liftOnScrollTargetView != null) {
            this.liftOnScrollTargetView.clear();
        }
        this.liftOnScrollTargetView = null;
    }

    @Deprecated
    public void setTargetElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, elevation);
        }
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    int getPendingAction() {
        return this.pendingAction;
    }

    void resetPendingAction() {
        this.pendingAction = 0;
    }

    @VisibleForTesting
    final int getTopInset() {
        if (this.lastInsets != null) {
            return this.lastInsets.getSystemWindowInsetTop();
        }
        return 0;
    }

    private boolean shouldOffsetFirstChild() {
        if (getChildCount() > 0) {
            View firstChild = getChildAt(0);
            return (firstChild.getVisibility() == 8 || ViewCompat.getFitsSystemWindows(firstChild)) ? false : true;
        }
        return false;
    }

    WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat insets) {
        WindowInsetsCompat newInsets = null;
        if (ViewCompat.getFitsSystemWindows(this)) {
            newInsets = insets;
        }
        if (!ObjectsCompat.equals(this.lastInsets, newInsets)) {
            this.lastInsets = newInsets;
            updateWillNotDraw();
            requestLayout();
        }
        return insets;
    }

    /* loaded from: classes.dex */
    public static class LayoutParams extends LinearLayout.LayoutParams {
        static final int COLLAPSIBLE_FLAGS = 10;
        static final int FLAG_QUICK_RETURN = 5;
        static final int FLAG_SNAP = 17;
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_NO_SCROLL = 0;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        public static final int SCROLL_FLAG_SNAP_MARGINS = 32;
        int scrollFlags;
        Interpolator scrollInterpolator;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes.dex */
        public @interface ScrollFlags {
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.scrollFlags = 1;
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.AppBarLayout_Layout);
            this.scrollFlags = a.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (a.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                int resId = a.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0);
                this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(c, resId);
            }
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.scrollFlags = 1;
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height, weight);
            this.scrollFlags = 1;
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
            this.scrollFlags = 1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
            this.scrollFlags = 1;
        }

        @RequiresApi(19)
        public LayoutParams(LinearLayout.LayoutParams source) {
            super(source);
            this.scrollFlags = 1;
        }

        @RequiresApi(19)
        public LayoutParams(@NonNull LayoutParams source) {
            super((LinearLayout.LayoutParams) source);
            this.scrollFlags = 1;
            this.scrollFlags = source.scrollFlags;
            this.scrollInterpolator = source.scrollInterpolator;
        }

        public void setScrollFlags(int flags) {
            this.scrollFlags = flags;
        }

        public int getScrollFlags() {
            return this.scrollFlags;
        }

        public void setScrollInterpolator(Interpolator interpolator) {
            this.scrollInterpolator = interpolator;
        }

        public Interpolator getScrollInterpolator() {
            return this.scrollInterpolator;
        }

        boolean isCollapsible() {
            return (this.scrollFlags & 1) == 1 && (this.scrollFlags & 10) != 0;
        }
    }

    /* loaded from: classes.dex */
    public static class Behavior extends BaseBehavior<AppBarLayout> {

        /* loaded from: classes.dex */
        public static abstract class DragCallback extends BaseBehavior.BaseDragCallback<AppBarLayout> {
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean isHorizontalOffsetEnabled() {
            return super.isHorizontalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean isVerticalOffsetEnabled() {
            return super.isVerticalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, int i) {
            return super.onLayoutChild(coordinatorLayout, (CoordinatorLayout) appBarLayout, i);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, (CoordinatorLayout) appBarLayout, i, i2, i3, i4);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
            super.onNestedPreScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, i, i2, iArr, i3);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            super.onNestedScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, i, i2, i3, i4, i5, iArr);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, Parcelable parcelable) {
            super.onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) appBarLayout, parcelable);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout) {
            return super.onSaveInstanceState(coordinatorLayout, (CoordinatorLayout) appBarLayout);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, @NonNull View view, View view2, int i, int i2) {
            return super.onStartNestedScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, view2, i, i2);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout appBarLayout, View view, int i) {
            super.onStopNestedScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, view, i);
        }

        @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior
        public /* bridge */ /* synthetic */ void setDragCallback(@Nullable BaseBehavior.BaseDragCallback baseDragCallback) {
            super.setDragCallback(baseDragCallback);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ void setHorizontalOffsetEnabled(boolean z) {
            super.setHorizontalOffsetEnabled(z);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ void setVerticalOffsetEnabled(boolean z) {
            super.setVerticalOffsetEnabled(z);
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static class BaseBehavior<T extends AppBarLayout> extends HeaderBehavior<T> {
        private static final int INVALID_POSITION = -1;
        private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
        @Nullable
        private WeakReference<View> lastNestedScrollingChildRef;
        private int lastStartedType;
        private ValueAnimator offsetAnimator;
        private int offsetDelta;
        private int offsetToChildIndexOnLayout;
        private boolean offsetToChildIndexOnLayoutIsMinHeight;
        private float offsetToChildIndexOnLayoutPerc;
        private BaseDragCallback onDragCallback;

        /* loaded from: classes.dex */
        public static abstract class BaseDragCallback<T extends AppBarLayout> {
            public abstract boolean canDrag(@NonNull T t);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* bridge */ /* synthetic */ boolean canDragView(View view) {
            return canDragView((BaseBehavior<T>) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* bridge */ /* synthetic */ int getMaxDragOffset(@NonNull View view) {
            return getMaxDragOffset((BaseBehavior<T>) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* bridge */ /* synthetic */ int getScrollRangeForDragFling(@NonNull View view) {
            return getScrollRangeForDragFling((BaseBehavior<T>) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* bridge */ /* synthetic */ void onFlingFinished(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view) {
            onFlingFinished(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.ViewOffsetBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i) {
            return onLayoutChild(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i, int i2, int i3, int i4) {
            return onMeasureChild(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), i, i2, i3, i4);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull View view, View view2, int i, int i2, int[] iArr, int i3) {
            onNestedPreScroll(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), view2, i, i2, iArr, i3);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            onNestedScroll(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), view2, i, i2, i3, i4, i5, iArr);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ void onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, Parcelable parcelable) {
            onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), parcelable);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view) {
            return onSaveInstanceState(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, View view3, int i, int i2) {
            return onStartNestedScroll(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), view2, view3, i, i2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull View view, View view2, int i) {
            onStopNestedScroll(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), view2, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.material.appbar.HeaderBehavior
        /* bridge */ /* synthetic */ int setHeaderTopBottomOffset(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i, int i2, int i3) {
            return setHeaderTopBottomOffset(coordinatorLayout, (CoordinatorLayout) ((AppBarLayout) view), i, i2, i3);
        }

        public BaseBehavior() {
            this.offsetToChildIndexOnLayout = -1;
        }

        public BaseBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.offsetToChildIndexOnLayout = -1;
        }

        public boolean onStartNestedScroll(@NonNull CoordinatorLayout parent, @NonNull T child, @NonNull View directTargetChild, View target, int nestedScrollAxes, int type) {
            boolean started = (nestedScrollAxes & 2) != 0 && (child.isLiftOnScroll() || canScrollChildren(parent, child, directTargetChild));
            if (started && this.offsetAnimator != null) {
                this.offsetAnimator.cancel();
            }
            this.lastNestedScrollingChildRef = null;
            this.lastStartedType = type;
            return started;
        }

        private boolean canScrollChildren(@NonNull CoordinatorLayout parent, @NonNull T child, @NonNull View directTargetChild) {
            return child.hasScrollableChildren() && parent.getHeight() - directTargetChild.getHeight() <= child.getHeight();
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull T child, View target, int dx, int dy, int[] consumed, int type) {
            int min;
            int max;
            if (dy != 0) {
                if (dy < 0) {
                    min = -child.getTotalScrollRange();
                    max = min + child.getDownNestedPreScrollRange();
                } else {
                    min = -child.getUpNestedPreScrollRange();
                    max = 0;
                }
                if (min != max) {
                    consumed[1] = scroll(coordinatorLayout, child, dy, min, max);
                }
            }
            if (child.isLiftOnScroll()) {
                child.setLiftedState(child.shouldLift(target));
            }
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull T child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, int[] consumed) {
            if (dyUnconsumed < 0) {
                consumed[1] = scroll(coordinatorLayout, child, dyUnconsumed, -child.getDownNestedScrollRange(), 0);
            }
            if (dyUnconsumed == 0) {
                updateAccessibilityActions(coordinatorLayout, child);
            }
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull T abl, View target, int type) {
            if (this.lastStartedType == 0 || type == 1) {
                snapToChildIfNeeded(coordinatorLayout, abl);
                if (abl.isLiftOnScroll()) {
                    abl.setLiftedState(abl.shouldLift(target));
                }
            }
            this.lastNestedScrollingChildRef = new WeakReference<>(target);
        }

        public void setDragCallback(@Nullable BaseDragCallback callback) {
            this.onDragCallback = callback;
        }

        private void animateOffsetTo(CoordinatorLayout coordinatorLayout, @NonNull T child, int offset, float velocity) {
            int duration;
            int distance = Math.abs(getTopBottomOffsetForScrollingSibling() - offset);
            float velocity2 = Math.abs(velocity);
            if (velocity2 > 0.0f) {
                duration = Math.round(1000.0f * (distance / velocity2)) * 3;
            } else {
                float distanceRatio = distance / child.getHeight();
                duration = (int) ((1.0f + distanceRatio) * 150.0f);
            }
            animateOffsetWithDuration(coordinatorLayout, child, offset, duration);
        }

        private void animateOffsetWithDuration(final CoordinatorLayout coordinatorLayout, final T child, int offset, int duration) {
            int currentOffset = getTopBottomOffsetForScrollingSibling();
            if (currentOffset == offset) {
                if (this.offsetAnimator != null && this.offsetAnimator.isRunning()) {
                    this.offsetAnimator.cancel();
                    return;
                }
                return;
            }
            if (this.offsetAnimator == null) {
                this.offsetAnimator = new ValueAnimator();
                this.offsetAnimator.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                this.offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                        BaseBehavior.this.setHeaderTopBottomOffset(coordinatorLayout, child, ((Integer) animator.getAnimatedValue()).intValue());
                    }
                });
            } else {
                this.offsetAnimator.cancel();
            }
            this.offsetAnimator.setDuration(Math.min(duration, (int) MAX_OFFSET_ANIMATION_DURATION));
            this.offsetAnimator.setIntValues(currentOffset, offset);
            this.offsetAnimator.start();
        }

        private int getChildIndexOnOffset(@NonNull T abl, int offset) {
            int count = abl.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = abl.getChildAt(i);
                int top = child.getTop();
                int bottom = child.getBottom();
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (checkFlag(lp.getScrollFlags(), 32)) {
                    top -= lp.topMargin;
                    bottom += lp.bottomMargin;
                }
                if (top <= (-offset) && bottom >= (-offset)) {
                    return i;
                }
            }
            return -1;
        }

        private void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, @NonNull T abl) {
            int offset = getTopBottomOffsetForScrollingSibling();
            int offsetChildIndex = getChildIndexOnOffset(abl, offset);
            if (offsetChildIndex >= 0) {
                View offsetChild = abl.getChildAt(offsetChildIndex);
                LayoutParams lp = (LayoutParams) offsetChild.getLayoutParams();
                int flags = lp.getScrollFlags();
                if ((flags & 17) == 17) {
                    int snapTop = -offsetChild.getTop();
                    int snapBottom = -offsetChild.getBottom();
                    if (offsetChildIndex == abl.getChildCount() - 1) {
                        snapBottom += abl.getTopInset();
                    }
                    if (checkFlag(flags, 2)) {
                        snapBottom += ViewCompat.getMinimumHeight(offsetChild);
                    } else if (checkFlag(flags, 5)) {
                        int seam = snapBottom + ViewCompat.getMinimumHeight(offsetChild);
                        if (offset < seam) {
                            snapTop = seam;
                        } else {
                            snapBottom = seam;
                        }
                    }
                    if (checkFlag(flags, 32)) {
                        snapTop += lp.topMargin;
                        snapBottom -= lp.bottomMargin;
                    }
                    int newOffset = offset < (snapBottom + snapTop) / 2 ? snapBottom : snapTop;
                    animateOffsetTo(coordinatorLayout, abl, MathUtils.clamp(newOffset, -abl.getTotalScrollRange(), 0), 0.0f);
                }
            }
        }

        private static boolean checkFlag(int flags, int check) {
            return (flags & check) == check;
        }

        public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull T child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            if (lp.height == -2) {
                parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, View.MeasureSpec.makeMeasureSpec(0, 0), heightUsed);
                return true;
            }
            return super.onMeasureChild(parent, (CoordinatorLayout) child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        }

        public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull T abl, int layoutDirection) {
            int offset;
            boolean handled = super.onLayoutChild(parent, (CoordinatorLayout) abl, layoutDirection);
            int pendingAction = abl.getPendingAction();
            if (this.offsetToChildIndexOnLayout >= 0 && (pendingAction & 8) == 0) {
                View child = abl.getChildAt(this.offsetToChildIndexOnLayout);
                int offset2 = -child.getBottom();
                if (this.offsetToChildIndexOnLayoutIsMinHeight) {
                    offset = offset2 + ViewCompat.getMinimumHeight(child) + abl.getTopInset();
                } else {
                    offset = offset2 + Math.round(child.getHeight() * this.offsetToChildIndexOnLayoutPerc);
                }
                setHeaderTopBottomOffset(parent, abl, offset);
            } else if (pendingAction != 0) {
                boolean animate = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    int offset3 = -abl.getUpNestedPreScrollRange();
                    if (animate) {
                        animateOffsetTo(parent, abl, offset3, 0.0f);
                    } else {
                        setHeaderTopBottomOffset(parent, abl, offset3);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (animate) {
                        animateOffsetTo(parent, abl, 0, 0.0f);
                    } else {
                        setHeaderTopBottomOffset(parent, abl, 0);
                    }
                }
            }
            abl.resetPendingAction();
            this.offsetToChildIndexOnLayout = -1;
            setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -abl.getTotalScrollRange(), 0));
            updateAppBarLayoutDrawableState(parent, abl, getTopAndBottomOffset(), 0, true);
            abl.onOffsetChanged(getTopAndBottomOffset());
            updateAccessibilityActions(parent, abl);
            return handled;
        }

        private void updateAccessibilityActions(CoordinatorLayout coordinatorLayout, @NonNull T appBarLayout) {
            ViewCompat.removeAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
            ViewCompat.removeAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
            View scrollingView = findFirstScrollingChild(coordinatorLayout);
            if (scrollingView != null && appBarLayout.getTotalScrollRange() != 0) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) scrollingView.getLayoutParams();
                if (lp.getBehavior() instanceof ScrollingViewBehavior) {
                    addAccessibilityScrollActions(coordinatorLayout, appBarLayout, scrollingView);
                }
            }
        }

        private void addAccessibilityScrollActions(final CoordinatorLayout coordinatorLayout, @NonNull final T appBarLayout, @NonNull final View scrollingView) {
            if (getTopBottomOffsetForScrollingSibling() != (-appBarLayout.getTotalScrollRange()) && scrollingView.canScrollVertically(1)) {
                addActionToExpand(coordinatorLayout, appBarLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD, false);
            }
            if (getTopBottomOffsetForScrollingSibling() != 0) {
                if (scrollingView.canScrollVertically(-1)) {
                    final int dy = -appBarLayout.getDownNestedPreScrollRange();
                    if (dy != 0) {
                        ViewCompat.replaceAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, null, new AccessibilityViewCommand() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.2
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments arguments) {
                                BaseBehavior.this.onNestedPreScroll(coordinatorLayout, (CoordinatorLayout) appBarLayout, scrollingView, 0, dy, new int[]{0, 0}, 1);
                                return true;
                            }
                        });
                        return;
                    }
                    return;
                }
                addActionToExpand(coordinatorLayout, appBarLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, true);
            }
        }

        private void addActionToExpand(CoordinatorLayout parent, @NonNull final T appBarLayout, @NonNull AccessibilityNodeInfoCompat.AccessibilityActionCompat action, final boolean expand) {
            ViewCompat.replaceAccessibilityAction(parent, action, null, new AccessibilityViewCommand() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.3
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments arguments) {
                    appBarLayout.setExpanded(expand);
                    return true;
                }
            });
        }

        boolean canDragView(T view) {
            if (this.onDragCallback != null) {
                return this.onDragCallback.canDrag(view);
            }
            if (this.lastNestedScrollingChildRef != null) {
                View scrollingView = this.lastNestedScrollingChildRef.get();
                return (scrollingView == null || !scrollingView.isShown() || scrollingView.canScrollVertically(-1)) ? false : true;
            }
            return true;
        }

        void onFlingFinished(@NonNull CoordinatorLayout parent, @NonNull T layout) {
            snapToChildIfNeeded(parent, layout);
            if (layout.isLiftOnScroll()) {
                layout.setLiftedState(layout.shouldLift(findFirstScrollingChild(parent)));
            }
        }

        int getMaxDragOffset(@NonNull T view) {
            return -view.getDownNestedScrollRange();
        }

        int getScrollRangeForDragFling(@NonNull T view) {
            return view.getTotalScrollRange();
        }

        int setHeaderTopBottomOffset(@NonNull CoordinatorLayout coordinatorLayout, @NonNull T appBarLayout, int newOffset, int minOffset, int maxOffset) {
            int curOffset = getTopBottomOffsetForScrollingSibling();
            int consumed = 0;
            if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
                int newOffset2 = MathUtils.clamp(newOffset, minOffset, maxOffset);
                if (curOffset != newOffset2) {
                    int interpolatedOffset = appBarLayout.hasChildWithInterpolator() ? interpolateOffset(appBarLayout, newOffset2) : newOffset2;
                    boolean offsetChanged = setTopAndBottomOffset(interpolatedOffset);
                    consumed = curOffset - newOffset2;
                    this.offsetDelta = newOffset2 - interpolatedOffset;
                    if (!offsetChanged && appBarLayout.hasChildWithInterpolator()) {
                        coordinatorLayout.dispatchDependentViewsChanged(appBarLayout);
                    }
                    appBarLayout.onOffsetChanged(getTopAndBottomOffset());
                    updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, newOffset2, newOffset2 < curOffset ? -1 : 1, false);
                }
            } else {
                this.offsetDelta = 0;
            }
            updateAccessibilityActions(coordinatorLayout, appBarLayout);
            return consumed;
        }

        @VisibleForTesting
        boolean isOffsetAnimatorRunning() {
            return this.offsetAnimator != null && this.offsetAnimator.isRunning();
        }

        private int interpolateOffset(@NonNull T layout, int offset) {
            int absOffset = Math.abs(offset);
            int z = layout.getChildCount();
            for (int i = 0; i < z; i++) {
                View child = layout.getChildAt(i);
                LayoutParams childLp = (LayoutParams) child.getLayoutParams();
                Interpolator interpolator = childLp.getScrollInterpolator();
                if (absOffset >= child.getTop() && absOffset <= child.getBottom()) {
                    if (interpolator != null) {
                        int childScrollableHeight = 0;
                        int flags = childLp.getScrollFlags();
                        if ((flags & 1) != 0) {
                            childScrollableHeight = 0 + child.getHeight() + childLp.topMargin + childLp.bottomMargin;
                            if ((flags & 2) != 0) {
                                childScrollableHeight -= ViewCompat.getMinimumHeight(child);
                            }
                        }
                        if (ViewCompat.getFitsSystemWindows(child)) {
                            childScrollableHeight -= layout.getTopInset();
                        }
                        if (childScrollableHeight > 0) {
                            int offsetForView = absOffset - child.getTop();
                            int interpolatedDiff = Math.round(childScrollableHeight * interpolator.getInterpolation(offsetForView / childScrollableHeight));
                            return Integer.signum(offset) * (child.getTop() + interpolatedDiff);
                        }
                        return offset;
                    } else {
                        return offset;
                    }
                }
            }
            return offset;
        }

        private void updateAppBarLayoutDrawableState(@NonNull CoordinatorLayout parent, @NonNull T layout, int offset, int direction, boolean forceJump) {
            View child = getAppBarChildOnOffset(layout, offset);
            if (child != null) {
                LayoutParams childLp = (LayoutParams) child.getLayoutParams();
                int flags = childLp.getScrollFlags();
                boolean lifted = false;
                if ((flags & 1) != 0) {
                    int minHeight = ViewCompat.getMinimumHeight(child);
                    if (direction > 0 && (flags & 12) != 0) {
                        lifted = (-offset) >= (child.getBottom() - minHeight) - layout.getTopInset();
                    } else if ((flags & 2) != 0) {
                        lifted = (-offset) >= (child.getBottom() - minHeight) - layout.getTopInset();
                    }
                }
                if (layout.isLiftOnScroll()) {
                    lifted = layout.shouldLift(findFirstScrollingChild(parent));
                }
                boolean changed = layout.setLiftedState(lifted);
                if (forceJump || (changed && shouldJumpElevationState(parent, layout))) {
                    layout.jumpDrawablesToCurrentState();
                }
            }
        }

        private boolean shouldJumpElevationState(@NonNull CoordinatorLayout parent, @NonNull T layout) {
            List<View> dependencies = parent.getDependents(layout);
            int size = dependencies.size();
            for (int i = 0; i < size; i++) {
                View dependency = dependencies.get(i);
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) dependency.getLayoutParams();
                CoordinatorLayout.Behavior behavior = lp.getBehavior();
                if (behavior instanceof ScrollingViewBehavior) {
                    return ((ScrollingViewBehavior) behavior).getOverlayTop() != 0;
                }
            }
            return false;
        }

        @Nullable
        private static View getAppBarChildOnOffset(@NonNull AppBarLayout layout, int offset) {
            int absOffset = Math.abs(offset);
            int z = layout.getChildCount();
            for (int i = 0; i < z; i++) {
                View child = layout.getChildAt(i);
                if (absOffset >= child.getTop() && absOffset <= child.getBottom()) {
                    return child;
                }
            }
            return null;
        }

        @Nullable
        private View findFirstScrollingChild(@NonNull CoordinatorLayout parent) {
            int z = parent.getChildCount();
            for (int i = 0; i < z; i++) {
                View child = parent.getChildAt(i);
                if ((child instanceof NestedScrollingChild) || (child instanceof ListView) || (child instanceof ScrollView)) {
                    return child;
                }
            }
            return null;
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        int getTopBottomOffsetForScrollingSibling() {
            return getTopAndBottomOffset() + this.offsetDelta;
        }

        public Parcelable onSaveInstanceState(@NonNull CoordinatorLayout parent, @NonNull T abl) {
            Parcelable superState = super.onSaveInstanceState(parent, (CoordinatorLayout) abl);
            int offset = getTopAndBottomOffset();
            int count = abl.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = abl.getChildAt(i);
                int visBottom = child.getBottom() + offset;
                if (child.getTop() + offset <= 0 && visBottom >= 0) {
                    SavedState ss = new SavedState(superState);
                    ss.firstVisibleChildIndex = i;
                    ss.firstVisibleChildAtMinimumHeight = visBottom == ViewCompat.getMinimumHeight(child) + abl.getTopInset();
                    ss.firstVisibleChildPercentageShown = visBottom / child.getHeight();
                    return ss;
                }
            }
            return superState;
        }

        public void onRestoreInstanceState(@NonNull CoordinatorLayout parent, @NonNull T appBarLayout, Parcelable state) {
            if (state instanceof SavedState) {
                SavedState ss = (SavedState) state;
                super.onRestoreInstanceState(parent, (CoordinatorLayout) appBarLayout, ss.getSuperState());
                this.offsetToChildIndexOnLayout = ss.firstVisibleChildIndex;
                this.offsetToChildIndexOnLayoutPerc = ss.firstVisibleChildPercentageShown;
                this.offsetToChildIndexOnLayoutIsMinHeight = ss.firstVisibleChildAtMinimumHeight;
                return;
            }
            super.onRestoreInstanceState(parent, (CoordinatorLayout) appBarLayout, state);
            this.offsetToChildIndexOnLayout = -1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* loaded from: classes.dex */
        public static class SavedState extends AbsSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.SavedState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.ClassLoaderCreator
                @NonNull
                public SavedState createFromParcel(@NonNull Parcel source, ClassLoader loader) {
                    return new SavedState(source, loader);
                }

                @Override // android.os.Parcelable.Creator
                @Nullable
                public SavedState createFromParcel(@NonNull Parcel source) {
                    return new SavedState(source, null);
                }

                @Override // android.os.Parcelable.Creator
                @NonNull
                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
            boolean firstVisibleChildAtMinimumHeight;
            int firstVisibleChildIndex;
            float firstVisibleChildPercentageShown;

            public SavedState(@NonNull Parcel source, ClassLoader loader) {
                super(source, loader);
                this.firstVisibleChildIndex = source.readInt();
                this.firstVisibleChildPercentageShown = source.readFloat();
                this.firstVisibleChildAtMinimumHeight = source.readByte() != 0;
            }

            public SavedState(Parcelable superState) {
                super(superState);
            }

            @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(@NonNull Parcel dest, int flags) {
                super.writeToParcel(dest, flags);
                dest.writeInt(this.firstVisibleChildIndex);
                dest.writeFloat(this.firstVisibleChildPercentageShown);
                dest.writeByte((byte) (this.firstVisibleChildAtMinimumHeight ? 1 : 0));
            }
        }
    }

    /* loaded from: classes.dex */
    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        @Nullable
        /* bridge */ /* synthetic */ View findFirstDependency(@NonNull List list) {
            return findFirstDependency((List<View>) list);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean isHorizontalOffsetEnabled() {
            return super.isHorizontalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean isVerticalOffsetEnabled() {
            return super.isVerticalOffsetEnabled();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i) {
            return super.onLayoutChild(coordinatorLayout, view, i);
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, view, i, i2, i3, i4);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ void setHorizontalOffsetEnabled(boolean z) {
            super.setHorizontalOffsetEnabled(z);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ void setVerticalOffsetEnabled(boolean z) {
            super.setVerticalOffsetEnabled(z);
        }

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollingViewBehavior_Layout);
            setOverlayTop(a.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            a.recycle();
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            return dependency instanceof AppBarLayout;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
            offsetChildAsNeeded(child, dependency);
            updateLiftedStateIfNeeded(child, dependency);
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onDependentViewRemoved(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
            if (dependency instanceof AppBarLayout) {
                ViewCompat.removeAccessibilityAction(parent, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
                ViewCompat.removeAccessibilityAction(parent, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onRequestChildRectangleOnScreen(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull Rect rectangle, boolean immediate) {
            AppBarLayout header = findFirstDependency(parent.getDependencies(child));
            if (header != null) {
                rectangle.offset(child.getLeft(), child.getTop());
                Rect parentRect = this.tempRect1;
                parentRect.set(0, 0, parent.getWidth(), parent.getHeight());
                if (!parentRect.contains(rectangle)) {
                    header.setExpanded(false, !immediate);
                    return true;
                }
            }
            return false;
        }

        private void offsetChildAsNeeded(@NonNull View child, @NonNull View dependency) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
            if (behavior instanceof BaseBehavior) {
                BaseBehavior ablBehavior = (BaseBehavior) behavior;
                ViewCompat.offsetTopAndBottom(child, (((dependency.getBottom() - child.getTop()) + ablBehavior.offsetDelta) + getVerticalLayoutGap()) - getOverlapPixelsForOffset(dependency));
            }
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        float getOverlapRatioForOffset(View header) {
            int availScrollRange;
            if (header instanceof AppBarLayout) {
                AppBarLayout abl = (AppBarLayout) header;
                int totalScrollRange = abl.getTotalScrollRange();
                int preScrollDown = abl.getDownNestedPreScrollRange();
                int offset = getAppBarLayoutOffset(abl);
                if ((preScrollDown == 0 || totalScrollRange + offset > preScrollDown) && (availScrollRange = totalScrollRange - preScrollDown) != 0) {
                    return 1.0f + (offset / availScrollRange);
                }
                return 0.0f;
            }
            return 0.0f;
        }

        private static int getAppBarLayoutOffset(@NonNull AppBarLayout abl) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) abl.getLayoutParams()).getBehavior();
            if (behavior instanceof BaseBehavior) {
                return ((BaseBehavior) behavior).getTopBottomOffsetForScrollingSibling();
            }
            return 0;
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        @Nullable
        AppBarLayout findFirstDependency(@NonNull List<View> views) {
            int z = views.size();
            for (int i = 0; i < z; i++) {
                View view = views.get(i);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        @Override // com.google.android.material.appbar.HeaderScrollingViewBehavior
        int getScrollRange(View v) {
            return v instanceof AppBarLayout ? ((AppBarLayout) v).getTotalScrollRange() : super.getScrollRange(v);
        }

        private void updateLiftedStateIfNeeded(View child, View dependency) {
            if (dependency instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) dependency;
                if (appBarLayout.isLiftOnScroll()) {
                    appBarLayout.setLiftedState(appBarLayout.shouldLift(child));
                }
            }
        }
    }
}
