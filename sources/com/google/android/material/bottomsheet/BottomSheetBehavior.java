package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final int CORNER_ANIMATION_DURATION = 500;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_BottomSheet_Modal;
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    private static final int NO_WIDTH = -1;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int SAVE_ALL = -1;
    public static final int SAVE_FIT_TO_CONTENTS = 2;
    public static final int SAVE_HIDEABLE = 4;
    public static final int SAVE_NONE = 0;
    public static final int SAVE_PEEK_HEIGHT = 1;
    public static final int SAVE_SKIP_COLLAPSED = 8;
    private static final int SIGNIFICANT_VEL_THRESHOLD = 500;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "BottomSheetBehavior";
    int activePointerId;
    @NonNull
    private final ArrayList<BottomSheetCallback> callbacks;
    private int childHeight;
    int collapsedOffset;
    private final ViewDragHelper.Callback dragCallback;
    private boolean draggable;
    float elevation;
    private int expandHalfwayActionId;
    int expandedOffset;
    private boolean fitToContents;
    int fitToContentsOffset;
    private int gestureInsetBottom;
    private boolean gestureInsetBottomIgnored;
    int halfExpandedOffset;
    float halfExpandedRatio;
    boolean hideable;
    private boolean ignoreEvents;
    @Nullable
    private Map<View, Integer> importantForAccessibilityMap;
    private int initialY;
    private int insetBottom;
    private int insetTop;
    @Nullable
    private ValueAnimator interpolatorAnimator;
    private boolean isShapeExpanded;
    private int lastNestedScrollDy;
    private MaterialShapeDrawable materialShapeDrawable;
    private int maxWidth;
    private float maximumVelocity;
    private boolean nestedScrolled;
    @Nullable
    WeakReference<View> nestedScrollingChildRef;
    private boolean paddingBottomSystemWindowInsets;
    private boolean paddingLeftSystemWindowInsets;
    private boolean paddingRightSystemWindowInsets;
    private boolean paddingTopSystemWindowInsets;
    int parentHeight;
    int parentWidth;
    private int peekHeight;
    private boolean peekHeightAuto;
    private int peekHeightGestureInsetBuffer;
    private int peekHeightMin;
    private int saveFlags;
    private BottomSheetBehavior<V>.SettleRunnable settleRunnable;
    private ShapeAppearanceModel shapeAppearanceModelDefault;
    private boolean shapeThemingEnabled;
    private boolean skipCollapsed;
    int state;
    boolean touchingScrollingChild;
    private boolean updateImportantForAccessibilityOnSiblings;
    @Nullable
    private VelocityTracker velocityTracker;
    @Nullable
    ViewDragHelper viewDragHelper;
    @Nullable
    WeakReference<V> viewRef;

    /* loaded from: classes.dex */
    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f);

        public abstract void onStateChanged(@NonNull View view, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public @interface SaveFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public @interface State {
    }

    public BottomSheetBehavior() {
        this.saveFlags = 0;
        this.fitToContents = true;
        this.updateImportantForAccessibilityOnSiblings = false;
        this.maxWidth = -1;
        this.settleRunnable = null;
        this.halfExpandedRatio = HIDE_THRESHOLD;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.callbacks = new ArrayList<>();
        this.expandHalfwayActionId = -1;
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                if (BottomSheetBehavior.this.state == 1 || BottomSheetBehavior.this.touchingScrollingChild) {
                    return false;
                }
                if (BottomSheetBehavior.this.state == 3 && BottomSheetBehavior.this.activePointerId == pointerId) {
                    View scroll = BottomSheetBehavior.this.nestedScrollingChildRef != null ? BottomSheetBehavior.this.nestedScrollingChildRef.get() : null;
                    if (scroll != null && scroll.canScrollVertically(-1)) {
                        return false;
                    }
                }
                return BottomSheetBehavior.this.viewRef != null && BottomSheetBehavior.this.viewRef.get() == child;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                BottomSheetBehavior.this.dispatchOnSlide(top);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int state) {
                if (state == 1 && BottomSheetBehavior.this.draggable) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            private boolean releasedLow(@NonNull View child) {
                return child.getTop() > (BottomSheetBehavior.this.parentHeight + BottomSheetBehavior.this.getExpandedOffset()) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                int top;
                int targetState;
                if (yvel < 0.0f) {
                    if (BottomSheetBehavior.this.fitToContents) {
                        top = BottomSheetBehavior.this.fitToContentsOffset;
                        targetState = 3;
                    } else if (releasedChild.getTop() > BottomSheetBehavior.this.halfExpandedOffset) {
                        top = BottomSheetBehavior.this.halfExpandedOffset;
                        targetState = 6;
                    } else {
                        top = BottomSheetBehavior.this.getExpandedOffset();
                        targetState = 3;
                    }
                } else if (BottomSheetBehavior.this.hideable && BottomSheetBehavior.this.shouldHide(releasedChild, yvel)) {
                    if ((Math.abs(xvel) >= Math.abs(yvel) || yvel <= 500.0f) && !releasedLow(releasedChild)) {
                        if (BottomSheetBehavior.this.fitToContents) {
                            top = BottomSheetBehavior.this.fitToContentsOffset;
                            targetState = 3;
                        } else if (Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                            top = BottomSheetBehavior.this.getExpandedOffset();
                            targetState = 3;
                        } else {
                            top = BottomSheetBehavior.this.halfExpandedOffset;
                            targetState = 6;
                        }
                    } else {
                        top = BottomSheetBehavior.this.parentHeight;
                        targetState = 5;
                    }
                } else if (yvel != 0.0f && Math.abs(xvel) <= Math.abs(yvel)) {
                    if (BottomSheetBehavior.this.fitToContents) {
                        top = BottomSheetBehavior.this.collapsedOffset;
                        targetState = 4;
                    } else {
                        int currentTop = releasedChild.getTop();
                        if (Math.abs(currentTop - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                            top = BottomSheetBehavior.this.halfExpandedOffset;
                            targetState = 6;
                        } else {
                            top = BottomSheetBehavior.this.collapsedOffset;
                            targetState = 4;
                        }
                    }
                } else {
                    int currentTop2 = releasedChild.getTop();
                    if (BottomSheetBehavior.this.fitToContents) {
                        if (Math.abs(currentTop2 - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(currentTop2 - BottomSheetBehavior.this.collapsedOffset)) {
                            top = BottomSheetBehavior.this.fitToContentsOffset;
                            targetState = 3;
                        } else {
                            top = BottomSheetBehavior.this.collapsedOffset;
                            targetState = 4;
                        }
                    } else if (currentTop2 < BottomSheetBehavior.this.halfExpandedOffset) {
                        if (currentTop2 < Math.abs(currentTop2 - BottomSheetBehavior.this.collapsedOffset)) {
                            top = BottomSheetBehavior.this.getExpandedOffset();
                            targetState = 3;
                        } else {
                            top = BottomSheetBehavior.this.halfExpandedOffset;
                            targetState = 6;
                        }
                    } else if (Math.abs(currentTop2 - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(currentTop2 - BottomSheetBehavior.this.collapsedOffset)) {
                        top = BottomSheetBehavior.this.halfExpandedOffset;
                        targetState = 6;
                    } else {
                        top = BottomSheetBehavior.this.collapsedOffset;
                        targetState = 4;
                    }
                }
                BottomSheetBehavior.this.startSettlingAnimation(releasedChild, targetState, top, true);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return MathUtils.clamp(top, BottomSheetBehavior.this.getExpandedOffset(), BottomSheetBehavior.this.hideable ? BottomSheetBehavior.this.parentHeight : BottomSheetBehavior.this.collapsedOffset);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return child.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View child) {
                return BottomSheetBehavior.this.hideable ? BottomSheetBehavior.this.parentHeight : BottomSheetBehavior.this.collapsedOffset;
            }
        };
    }

    public BottomSheetBehavior(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.saveFlags = 0;
        this.fitToContents = true;
        this.updateImportantForAccessibilityOnSiblings = false;
        this.maxWidth = -1;
        this.settleRunnable = null;
        this.halfExpandedRatio = HIDE_THRESHOLD;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.callbacks = new ArrayList<>();
        this.expandHalfwayActionId = -1;
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                if (BottomSheetBehavior.this.state == 1 || BottomSheetBehavior.this.touchingScrollingChild) {
                    return false;
                }
                if (BottomSheetBehavior.this.state == 3 && BottomSheetBehavior.this.activePointerId == pointerId) {
                    View scroll = BottomSheetBehavior.this.nestedScrollingChildRef != null ? BottomSheetBehavior.this.nestedScrollingChildRef.get() : null;
                    if (scroll != null && scroll.canScrollVertically(-1)) {
                        return false;
                    }
                }
                return BottomSheetBehavior.this.viewRef != null && BottomSheetBehavior.this.viewRef.get() == child;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                BottomSheetBehavior.this.dispatchOnSlide(top);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int state) {
                if (state == 1 && BottomSheetBehavior.this.draggable) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            private boolean releasedLow(@NonNull View child) {
                return child.getTop() > (BottomSheetBehavior.this.parentHeight + BottomSheetBehavior.this.getExpandedOffset()) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                int top;
                int targetState;
                if (yvel < 0.0f) {
                    if (BottomSheetBehavior.this.fitToContents) {
                        top = BottomSheetBehavior.this.fitToContentsOffset;
                        targetState = 3;
                    } else if (releasedChild.getTop() > BottomSheetBehavior.this.halfExpandedOffset) {
                        top = BottomSheetBehavior.this.halfExpandedOffset;
                        targetState = 6;
                    } else {
                        top = BottomSheetBehavior.this.getExpandedOffset();
                        targetState = 3;
                    }
                } else if (BottomSheetBehavior.this.hideable && BottomSheetBehavior.this.shouldHide(releasedChild, yvel)) {
                    if ((Math.abs(xvel) >= Math.abs(yvel) || yvel <= 500.0f) && !releasedLow(releasedChild)) {
                        if (BottomSheetBehavior.this.fitToContents) {
                            top = BottomSheetBehavior.this.fitToContentsOffset;
                            targetState = 3;
                        } else if (Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                            top = BottomSheetBehavior.this.getExpandedOffset();
                            targetState = 3;
                        } else {
                            top = BottomSheetBehavior.this.halfExpandedOffset;
                            targetState = 6;
                        }
                    } else {
                        top = BottomSheetBehavior.this.parentHeight;
                        targetState = 5;
                    }
                } else if (yvel != 0.0f && Math.abs(xvel) <= Math.abs(yvel)) {
                    if (BottomSheetBehavior.this.fitToContents) {
                        top = BottomSheetBehavior.this.collapsedOffset;
                        targetState = 4;
                    } else {
                        int currentTop = releasedChild.getTop();
                        if (Math.abs(currentTop - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                            top = BottomSheetBehavior.this.halfExpandedOffset;
                            targetState = 6;
                        } else {
                            top = BottomSheetBehavior.this.collapsedOffset;
                            targetState = 4;
                        }
                    }
                } else {
                    int currentTop2 = releasedChild.getTop();
                    if (BottomSheetBehavior.this.fitToContents) {
                        if (Math.abs(currentTop2 - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(currentTop2 - BottomSheetBehavior.this.collapsedOffset)) {
                            top = BottomSheetBehavior.this.fitToContentsOffset;
                            targetState = 3;
                        } else {
                            top = BottomSheetBehavior.this.collapsedOffset;
                            targetState = 4;
                        }
                    } else if (currentTop2 < BottomSheetBehavior.this.halfExpandedOffset) {
                        if (currentTop2 < Math.abs(currentTop2 - BottomSheetBehavior.this.collapsedOffset)) {
                            top = BottomSheetBehavior.this.getExpandedOffset();
                            targetState = 3;
                        } else {
                            top = BottomSheetBehavior.this.halfExpandedOffset;
                            targetState = 6;
                        }
                    } else if (Math.abs(currentTop2 - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(currentTop2 - BottomSheetBehavior.this.collapsedOffset)) {
                        top = BottomSheetBehavior.this.halfExpandedOffset;
                        targetState = 6;
                    } else {
                        top = BottomSheetBehavior.this.collapsedOffset;
                        targetState = 4;
                    }
                }
                BottomSheetBehavior.this.startSettlingAnimation(releasedChild, targetState, top, true);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return MathUtils.clamp(top, BottomSheetBehavior.this.getExpandedOffset(), BottomSheetBehavior.this.hideable ? BottomSheetBehavior.this.parentHeight : BottomSheetBehavior.this.collapsedOffset);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return child.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View child) {
                return BottomSheetBehavior.this.hideable ? BottomSheetBehavior.this.parentHeight : BottomSheetBehavior.this.collapsedOffset;
            }
        };
        this.peekHeightGestureInsetBuffer = context.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomSheetBehavior_Layout);
        this.shapeThemingEnabled = a.hasValue(R.styleable.BottomSheetBehavior_Layout_shapeAppearance);
        boolean hasBackgroundTint = a.hasValue(R.styleable.BottomSheetBehavior_Layout_backgroundTint);
        if (hasBackgroundTint) {
            ColorStateList bottomSheetColor = MaterialResources.getColorStateList(context, a, R.styleable.BottomSheetBehavior_Layout_backgroundTint);
            createMaterialShapeDrawable(context, attrs, hasBackgroundTint, bottomSheetColor);
        } else {
            createMaterialShapeDrawable(context, attrs, hasBackgroundTint);
        }
        createShapeValueAnimator();
        if (Build.VERSION.SDK_INT >= 21) {
            this.elevation = a.getDimension(R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0f);
        }
        if (a.hasValue(R.styleable.BottomSheetBehavior_Layout_android_maxWidth)) {
            setMaxWidth(a.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_android_maxWidth, -1));
        }
        TypedValue value = a.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (value != null && value.data == -1) {
            setPeekHeight(value.data);
        } else {
            setPeekHeight(a.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        }
        setHideable(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setGestureInsetBottomIgnored(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
        setFitToContents(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        setSkipCollapsed(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        setDraggable(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
        setSaveFlags(a.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
        setHalfExpandedRatio(a.getFloat(R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, HIDE_THRESHOLD));
        TypedValue value2 = a.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset);
        if (value2 != null && value2.type == 16) {
            setExpandedOffset(value2.data);
        } else {
            setExpandedOffset(a.getDimensionPixelOffset(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset, 0));
        }
        this.paddingBottomSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingRightSystemWindowInsets, false);
        this.paddingTopSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingTopSystemWindowInsets, true);
        a.recycle();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.maximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    @NonNull
    public Parcelable onSaveInstanceState(@NonNull CoordinatorLayout parent, @NonNull V child) {
        return new SavedState(super.onSaveInstanceState(parent, child), (BottomSheetBehavior<?>) this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        restoreOptionalState(ss);
        if (ss.state == 1 || ss.state == 2) {
            this.state = 4;
        } else {
            this.state = ss.state;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull final V child, int layoutDirection) {
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            child.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin = parent.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            setWindowInsetsListener(child);
            this.viewRef = new WeakReference<>(child);
            if (this.shapeThemingEnabled && this.materialShapeDrawable != null) {
                ViewCompat.setBackground(child, this.materialShapeDrawable);
            }
            if (this.materialShapeDrawable != null) {
                this.materialShapeDrawable.setElevation(this.elevation == -1.0f ? ViewCompat.getElevation(child) : this.elevation);
                this.isShapeExpanded = this.state == 3;
                this.materialShapeDrawable.setInterpolation(this.isShapeExpanded ? 0.0f : 1.0f);
            }
            updateAccessibilityActions();
            if (ViewCompat.getImportantForAccessibility(child) == 0) {
                ViewCompat.setImportantForAccessibility(child, 1);
            }
            int width = child.getMeasuredWidth();
            if (width > this.maxWidth && this.maxWidth != -1) {
                final ViewGroup.LayoutParams lp = child.getLayoutParams();
                lp.width = this.maxWidth;
                child.post(new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.1
                    @Override // java.lang.Runnable
                    public void run() {
                        child.setLayoutParams(lp);
                    }
                });
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(parent, this.dragCallback);
        }
        int savedTop = child.getTop();
        parent.onLayoutChild(child, layoutDirection);
        this.parentWidth = parent.getWidth();
        this.parentHeight = parent.getHeight();
        this.childHeight = child.getHeight();
        if (this.parentHeight - this.childHeight < this.insetTop) {
            if (this.paddingTopSystemWindowInsets) {
                this.childHeight = this.parentHeight;
            } else {
                this.childHeight = this.parentHeight - this.insetTop;
            }
        }
        this.fitToContentsOffset = Math.max(0, this.parentHeight - this.childHeight);
        calculateHalfExpandedOffset();
        calculateCollapsedOffset();
        if (this.state == 3) {
            ViewCompat.offsetTopAndBottom(child, getExpandedOffset());
        } else if (this.state == 6) {
            ViewCompat.offsetTopAndBottom(child, this.halfExpandedOffset);
        } else if (this.hideable && this.state == 5) {
            ViewCompat.offsetTopAndBottom(child, this.parentHeight);
        } else if (this.state == 4) {
            ViewCompat.offsetTopAndBottom(child, this.collapsedOffset);
        } else if (this.state == 1 || this.state == 2) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(child));
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent event) {
        boolean z = true;
        if (!child.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int action = event.getActionMasked();
        if (action == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
        switch (action) {
            case 0:
                int initialX = (int) event.getX();
                this.initialY = (int) event.getY();
                if (this.state != 2) {
                    View scroll = this.nestedScrollingChildRef != null ? this.nestedScrollingChildRef.get() : null;
                    if (scroll != null && parent.isPointInChildBounds(scroll, initialX, this.initialY)) {
                        this.activePointerId = event.getPointerId(event.getActionIndex());
                        this.touchingScrollingChild = true;
                    }
                }
                this.ignoreEvents = this.activePointerId == -1 && !parent.isPointInChildBounds(child, initialX, this.initialY);
                break;
            case 1:
            case 3:
                this.touchingScrollingChild = false;
                this.activePointerId = -1;
                if (this.ignoreEvents) {
                    this.ignoreEvents = false;
                    return false;
                }
                break;
        }
        if (this.ignoreEvents || this.viewDragHelper == null || !this.viewDragHelper.shouldInterceptTouchEvent(event)) {
            View scroll2 = this.nestedScrollingChildRef != null ? this.nestedScrollingChildRef.get() : null;
            if (action != 2 || scroll2 == null || this.ignoreEvents || this.state == 1 || parent.isPointInChildBounds(scroll2, (int) event.getX(), (int) event.getY()) || this.viewDragHelper == null || Math.abs(this.initialY - event.getY()) <= this.viewDragHelper.getTouchSlop()) {
                z = false;
            }
            return z;
        }
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent event) {
        if (child.isShown()) {
            int action = event.getActionMasked();
            if (this.state == 1 && action == 0) {
                return true;
            }
            if (this.viewDragHelper != null) {
                this.viewDragHelper.processTouchEvent(event);
            }
            if (action == 0) {
                reset();
            }
            if (this.velocityTracker == null) {
                this.velocityTracker = VelocityTracker.obtain();
            }
            this.velocityTracker.addMovement(event);
            if (this.viewDragHelper != null && action == 2 && !this.ignoreEvents && Math.abs(this.initialY - event.getY()) > this.viewDragHelper.getTouchSlop()) {
                this.viewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
            }
            return !this.ignoreEvents;
        }
        return false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (axes & 2) != 0;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (type != 1) {
            View scrollingChild = this.nestedScrollingChildRef != null ? this.nestedScrollingChildRef.get() : null;
            if (target == scrollingChild) {
                int currentTop = child.getTop();
                int newTop = currentTop - dy;
                if (dy > 0) {
                    if (newTop < getExpandedOffset()) {
                        consumed[1] = currentTop - getExpandedOffset();
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        setStateInternal(3);
                    } else if (this.draggable) {
                        consumed[1] = dy;
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        setStateInternal(1);
                    } else {
                        return;
                    }
                } else if (dy < 0 && !target.canScrollVertically(-1)) {
                    if (newTop <= this.collapsedOffset || this.hideable) {
                        if (this.draggable) {
                            consumed[1] = dy;
                            ViewCompat.offsetTopAndBottom(child, -dy);
                            setStateInternal(1);
                        } else {
                            return;
                        }
                    } else {
                        consumed[1] = currentTop - this.collapsedOffset;
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        setStateInternal(4);
                    }
                }
                dispatchOnSlide(child.getTop());
                this.lastNestedScrollDy = dy;
                this.nestedScrolled = true;
            }
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
        int top;
        int targetState;
        if (child.getTop() == getExpandedOffset()) {
            setStateInternal(3);
        } else if (this.nestedScrollingChildRef != null && target == this.nestedScrollingChildRef.get() && this.nestedScrolled) {
            if (this.lastNestedScrollDy > 0) {
                if (this.fitToContents) {
                    top = this.fitToContentsOffset;
                    targetState = 3;
                } else if (child.getTop() > this.halfExpandedOffset) {
                    top = this.halfExpandedOffset;
                    targetState = 6;
                } else {
                    top = getExpandedOffset();
                    targetState = 3;
                }
            } else if (this.hideable && shouldHide(child, getYVelocity())) {
                top = this.parentHeight;
                targetState = 5;
            } else if (this.lastNestedScrollDy == 0) {
                int currentTop = child.getTop();
                if (this.fitToContents) {
                    if (Math.abs(currentTop - this.fitToContentsOffset) < Math.abs(currentTop - this.collapsedOffset)) {
                        top = this.fitToContentsOffset;
                        targetState = 3;
                    } else {
                        top = this.collapsedOffset;
                        targetState = 4;
                    }
                } else if (currentTop < this.halfExpandedOffset) {
                    if (currentTop < Math.abs(currentTop - this.collapsedOffset)) {
                        top = getExpandedOffset();
                        targetState = 3;
                    } else {
                        top = this.halfExpandedOffset;
                        targetState = 6;
                    }
                } else if (Math.abs(currentTop - this.halfExpandedOffset) < Math.abs(currentTop - this.collapsedOffset)) {
                    top = this.halfExpandedOffset;
                    targetState = 6;
                } else {
                    top = this.collapsedOffset;
                    targetState = 4;
                }
            } else if (this.fitToContents) {
                top = this.collapsedOffset;
                targetState = 4;
            } else {
                int currentTop2 = child.getTop();
                if (Math.abs(currentTop2 - this.halfExpandedOffset) < Math.abs(currentTop2 - this.collapsedOffset)) {
                    top = this.halfExpandedOffset;
                    targetState = 6;
                } else {
                    top = this.collapsedOffset;
                    targetState = 4;
                }
            }
            startSettlingAnimation(child, targetState, top, false);
            this.nestedScrolled = false;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, float velocityX, float velocityY) {
        if (this.nestedScrollingChildRef == null || target != this.nestedScrollingChildRef.get()) {
            return false;
        }
        return this.state != 3 || super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public void setFitToContents(boolean fitToContents) {
        if (this.fitToContents != fitToContents) {
            this.fitToContents = fitToContents;
            if (this.viewRef != null) {
                calculateCollapsedOffset();
            }
            setStateInternal((this.fitToContents && this.state == 6) ? 3 : this.state);
            updateAccessibilityActions();
        }
    }

    public void setMaxWidth(@Px int maxWidth) {
        this.maxWidth = maxWidth;
    }

    @Px
    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setPeekHeight(int peekHeight) {
        setPeekHeight(peekHeight, false);
    }

    public final void setPeekHeight(int peekHeight, boolean animate) {
        boolean layout = false;
        if (peekHeight == -1) {
            if (!this.peekHeightAuto) {
                this.peekHeightAuto = true;
                layout = true;
            }
        } else if (this.peekHeightAuto || this.peekHeight != peekHeight) {
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, peekHeight);
            layout = true;
        }
        if (layout) {
            updatePeekHeight(animate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePeekHeight(boolean animate) {
        V view;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state == 4 && (view = this.viewRef.get()) != null) {
                if (animate) {
                    settleToStatePendingLayout(this.state);
                } else {
                    view.requestLayout();
                }
            }
        }
    }

    public int getPeekHeight() {
        if (this.peekHeightAuto) {
            return -1;
        }
        return this.peekHeight;
    }

    public void setHalfExpandedRatio(@FloatRange(from = 0.0d, to = 1.0d) float ratio) {
        if (ratio <= 0.0f || ratio >= 1.0f) {
            throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        }
        this.halfExpandedRatio = ratio;
        if (this.viewRef != null) {
            calculateHalfExpandedOffset();
        }
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getHalfExpandedRatio() {
        return this.halfExpandedRatio;
    }

    public void setExpandedOffset(int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset must be greater than or equal to 0");
        }
        this.expandedOffset = offset;
    }

    public int getExpandedOffset() {
        if (this.fitToContents) {
            return this.fitToContentsOffset;
        }
        return Math.max(this.expandedOffset, this.paddingTopSystemWindowInsets ? 0 : this.insetTop);
    }

    public void setHideable(boolean hideable) {
        if (this.hideable != hideable) {
            this.hideable = hideable;
            if (!hideable && this.state == 5) {
                setState(4);
            }
            updateAccessibilityActions();
        }
    }

    public boolean isHideable() {
        return this.hideable;
    }

    public void setSkipCollapsed(boolean skipCollapsed) {
        this.skipCollapsed = skipCollapsed;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setSaveFlags(int flags) {
        this.saveFlags = flags;
    }

    public int getSaveFlags() {
        return this.saveFlags;
    }

    @Deprecated
    public void setBottomSheetCallback(BottomSheetCallback callback) {
        Log.w(TAG, "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
        this.callbacks.clear();
        if (callback != null) {
            this.callbacks.add(callback);
        }
    }

    public void addBottomSheetCallback(@NonNull BottomSheetCallback callback) {
        if (!this.callbacks.contains(callback)) {
            this.callbacks.add(callback);
        }
    }

    public void removeBottomSheetCallback(@NonNull BottomSheetCallback callback) {
        this.callbacks.remove(callback);
    }

    public void setState(int state) {
        if (state != this.state) {
            if (this.viewRef == null) {
                if (state == 4 || state == 3 || state == 6 || (this.hideable && state == 5)) {
                    this.state = state;
                    return;
                }
                return;
            }
            settleToStatePendingLayout(state);
        }
    }

    public void setGestureInsetBottomIgnored(boolean gestureInsetBottomIgnored) {
        this.gestureInsetBottomIgnored = gestureInsetBottomIgnored;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    private void settleToStatePendingLayout(final int state) {
        final V child = this.viewRef.get();
        if (child != null) {
            ViewParent parent = child.getParent();
            if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(child)) {
                child.post(new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.2
                    @Override // java.lang.Runnable
                    public void run() {
                        BottomSheetBehavior.this.settleToState(child, state);
                    }
                });
            } else {
                settleToState(child, state);
            }
        }
    }

    public int getState() {
        return this.state;
    }

    void setStateInternal(int state) {
        View bottomSheet;
        if (this.state != state) {
            this.state = state;
            if (this.viewRef != null && (bottomSheet = this.viewRef.get()) != null) {
                if (state == 3) {
                    updateImportantForAccessibility(true);
                } else if (state == 6 || state == 5 || state == 4) {
                    updateImportantForAccessibility(false);
                }
                updateDrawableForTargetState(state);
                for (int i = 0; i < this.callbacks.size(); i++) {
                    this.callbacks.get(i).onStateChanged(bottomSheet, state);
                }
                updateAccessibilityActions();
            }
        }
    }

    private void updateDrawableForTargetState(int state) {
        if (state != 2) {
            boolean expand = state == 3;
            if (this.isShapeExpanded != expand) {
                this.isShapeExpanded = expand;
                if (this.materialShapeDrawable != null && this.interpolatorAnimator != null) {
                    if (this.interpolatorAnimator.isRunning()) {
                        this.interpolatorAnimator.reverse();
                        return;
                    }
                    float to = expand ? 0.0f : 1.0f;
                    float from = 1.0f - to;
                    this.interpolatorAnimator.setFloatValues(from, to);
                    this.interpolatorAnimator.start();
                }
            }
        }
    }

    private int calculatePeekHeight() {
        if (this.peekHeightAuto) {
            int desiredHeight = Math.max(this.peekHeightMin, this.parentHeight - ((this.parentWidth * 9) / 16));
            return Math.min(desiredHeight, this.childHeight) + this.insetBottom;
        } else if (!this.gestureInsetBottomIgnored && !this.paddingBottomSystemWindowInsets && this.gestureInsetBottom > 0) {
            return Math.max(this.peekHeight, this.gestureInsetBottom + this.peekHeightGestureInsetBuffer);
        } else {
            return this.peekHeight + this.insetBottom;
        }
    }

    private void calculateCollapsedOffset() {
        int peek = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - peek, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - peek;
        }
    }

    private void calculateHalfExpandedOffset() {
        this.halfExpandedOffset = (int) (this.parentHeight * (1.0f - this.halfExpandedRatio));
    }

    private void reset() {
        this.activePointerId = -1;
        if (this.velocityTracker != null) {
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private void restoreOptionalState(@NonNull SavedState ss) {
        if (this.saveFlags != 0) {
            if (this.saveFlags == -1 || (this.saveFlags & 1) == 1) {
                this.peekHeight = ss.peekHeight;
            }
            if (this.saveFlags == -1 || (this.saveFlags & 2) == 2) {
                this.fitToContents = ss.fitToContents;
            }
            if (this.saveFlags == -1 || (this.saveFlags & 4) == 4) {
                this.hideable = ss.hideable;
            }
            if (this.saveFlags == -1 || (this.saveFlags & 8) == 8) {
                this.skipCollapsed = ss.skipCollapsed;
            }
        }
    }

    boolean shouldHide(@NonNull View child, float yvel) {
        if (this.skipCollapsed) {
            return true;
        }
        if (child.getTop() < this.collapsedOffset) {
            return false;
        }
        int peek = calculatePeekHeight();
        float newTop = child.getTop() + (HIDE_FRICTION * yvel);
        return Math.abs(newTop - ((float) this.collapsedOffset)) / ((float) peek) > HIDE_THRESHOLD;
    }

    @Nullable
    @VisibleForTesting
    View findScrollingChild(View view) {
        if (!ViewCompat.isNestedScrollingEnabled(view)) {
            if (view instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) view;
                int count = group.getChildCount();
                for (int i = 0; i < count; i++) {
                    View scrollingChild = findScrollingChild(group.getChildAt(i));
                    if (scrollingChild != null) {
                        return scrollingChild;
                    }
                }
            }
            return null;
        }
        return view;
    }

    private void createMaterialShapeDrawable(@NonNull Context context, AttributeSet attrs, boolean hasBackgroundTint) {
        createMaterialShapeDrawable(context, attrs, hasBackgroundTint, null);
    }

    private void createMaterialShapeDrawable(@NonNull Context context, AttributeSet attrs, boolean hasBackgroundTint, @Nullable ColorStateList bottomSheetColor) {
        if (this.shapeThemingEnabled) {
            this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(context, attrs, R.attr.bottomSheetStyle, DEF_STYLE_RES).build();
            this.materialShapeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModelDefault);
            this.materialShapeDrawable.initializeElevationOverlay(context);
            if (hasBackgroundTint && bottomSheetColor != null) {
                this.materialShapeDrawable.setFillColor(bottomSheetColor);
                return;
            }
            TypedValue defaultColor = new TypedValue();
            context.getTheme().resolveAttribute(16842801, defaultColor, true);
            this.materialShapeDrawable.setTint(defaultColor.data);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MaterialShapeDrawable getMaterialShapeDrawable() {
        return this.materialShapeDrawable;
    }

    private void createShapeValueAnimator() {
        this.interpolatorAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.interpolatorAnimator.setDuration(500L);
        this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                if (BottomSheetBehavior.this.materialShapeDrawable != null) {
                    BottomSheetBehavior.this.materialShapeDrawable.setInterpolation(value);
                }
            }
        });
    }

    private void setWindowInsetsListener(@NonNull View child) {
        final boolean shouldHandleGestureInsets = (Build.VERSION.SDK_INT < 29 || isGestureInsetBottomIgnored() || this.peekHeightAuto) ? false : true;
        if (this.paddingBottomSystemWindowInsets || this.paddingLeftSystemWindowInsets || this.paddingRightSystemWindowInsets || shouldHandleGestureInsets) {
            ViewUtils.doOnApplyWindowInsets(child, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.4
                @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                    BottomSheetBehavior.this.insetTop = insets.getSystemWindowInsetTop();
                    boolean isRtl = ViewUtils.isLayoutRtl(view);
                    int bottomPadding = view.getPaddingBottom();
                    int leftPadding = view.getPaddingLeft();
                    int rightPadding = view.getPaddingRight();
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets) {
                        BottomSheetBehavior.this.insetBottom = insets.getSystemWindowInsetBottom();
                        bottomPadding = initialPadding.bottom + BottomSheetBehavior.this.insetBottom;
                    }
                    if (BottomSheetBehavior.this.paddingLeftSystemWindowInsets) {
                        int leftPadding2 = isRtl ? initialPadding.end : initialPadding.start;
                        leftPadding = leftPadding2 + insets.getSystemWindowInsetLeft();
                    }
                    if (BottomSheetBehavior.this.paddingRightSystemWindowInsets) {
                        int rightPadding2 = isRtl ? initialPadding.start : initialPadding.end;
                        rightPadding = rightPadding2 + insets.getSystemWindowInsetRight();
                    }
                    view.setPadding(leftPadding, view.getPaddingTop(), rightPadding, bottomPadding);
                    if (shouldHandleGestureInsets) {
                        BottomSheetBehavior.this.gestureInsetBottom = insets.getMandatorySystemGestureInsets().bottom;
                    }
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets || shouldHandleGestureInsets) {
                        BottomSheetBehavior.this.updatePeekHeight(false);
                    }
                    return insets;
                }
            });
        }
    }

    private float getYVelocity() {
        if (this.velocityTracker == null) {
            return 0.0f;
        }
        this.velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    void settleToState(@NonNull View child, int state) {
        int top;
        if (state == 4) {
            top = this.collapsedOffset;
        } else if (state == 6) {
            top = this.halfExpandedOffset;
            if (this.fitToContents && top <= this.fitToContentsOffset) {
                state = 3;
                top = this.fitToContentsOffset;
            }
        } else if (state == 3) {
            top = getExpandedOffset();
        } else if (this.hideable && state == 5) {
            top = this.parentHeight;
        } else {
            throw new IllegalArgumentException("Illegal state argument: " + state);
        }
        startSettlingAnimation(child, state, top, false);
    }

    void startSettlingAnimation(View child, int state, int top, boolean settleFromViewDragHelper) {
        boolean startedSettling = this.viewDragHelper != null && (!settleFromViewDragHelper ? !this.viewDragHelper.smoothSlideViewTo(child, child.getLeft(), top) : !this.viewDragHelper.settleCapturedViewAt(child.getLeft(), top));
        if (startedSettling) {
            setStateInternal(2);
            updateDrawableForTargetState(state);
            if (this.settleRunnable == null) {
                this.settleRunnable = new SettleRunnable(child, state);
            }
            if (!((SettleRunnable) this.settleRunnable).isPosted) {
                this.settleRunnable.targetState = state;
                ViewCompat.postOnAnimation(child, this.settleRunnable);
                ((SettleRunnable) this.settleRunnable).isPosted = true;
                return;
            }
            this.settleRunnable.targetState = state;
            return;
        }
        setStateInternal(state);
    }

    void dispatchOnSlide(int top) {
        View bottomSheet = this.viewRef.get();
        if (bottomSheet != null && !this.callbacks.isEmpty()) {
            float slideOffset = (top > this.collapsedOffset || this.collapsedOffset == getExpandedOffset()) ? (this.collapsedOffset - top) / (this.parentHeight - this.collapsedOffset) : (this.collapsedOffset - top) / (this.collapsedOffset - getExpandedOffset());
            for (int i = 0; i < this.callbacks.size(); i++) {
                this.callbacks.get(i).onSlide(bottomSheet, slideOffset);
            }
        }
    }

    @VisibleForTesting
    int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class SettleRunnable implements Runnable {
        private boolean isPosted;
        int targetState;
        private final View view;

        SettleRunnable(View view, int targetState) {
            this.view = view;
            this.targetState = targetState;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BottomSheetBehavior.this.viewDragHelper != null && BottomSheetBehavior.this.viewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.view, this);
            } else {
                BottomSheetBehavior.this.setStateInternal(this.targetState);
            }
            this.isPosted = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            @NonNull
            public SavedState createFromParcel(@NonNull Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Override // android.os.Parcelable.Creator
            @Nullable
            public SavedState createFromParcel(@NonNull Parcel in) {
                return new SavedState(in, (ClassLoader) null);
            }

            @Override // android.os.Parcelable.Creator
            @NonNull
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean fitToContents;
        boolean hideable;
        int peekHeight;
        boolean skipCollapsed;
        final int state;

        public SavedState(@NonNull Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(@NonNull Parcel source, ClassLoader loader) {
            super(source, loader);
            this.state = source.readInt();
            this.peekHeight = source.readInt();
            this.fitToContents = source.readInt() == 1;
            this.hideable = source.readInt() == 1;
            this.skipCollapsed = source.readInt() == 1;
        }

        public SavedState(Parcelable superState, @NonNull BottomSheetBehavior<?> behavior) {
            super(superState);
            this.state = behavior.state;
            this.peekHeight = ((BottomSheetBehavior) behavior).peekHeight;
            this.fitToContents = ((BottomSheetBehavior) behavior).fitToContents;
            this.hideable = behavior.hideable;
            this.skipCollapsed = ((BottomSheetBehavior) behavior).skipCollapsed;
        }

        @Deprecated
        public SavedState(Parcelable superstate, int state) {
            super(superstate);
            this.state = state;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
            out.writeInt(this.peekHeight);
            out.writeInt(this.fitToContents ? 1 : 0);
            out.writeInt(this.hideable ? 1 : 0);
            out.writeInt(this.skipCollapsed ? 1 : 0);
        }
    }

    @NonNull
    public static <V extends View> BottomSheetBehavior<V> from(@NonNull V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior<?> behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof BottomSheetBehavior)) {
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        return (BottomSheetBehavior) behavior;
    }

    public void setUpdateImportantForAccessibilityOnSiblings(boolean updateImportantForAccessibilityOnSiblings) {
        this.updateImportantForAccessibilityOnSiblings = updateImportantForAccessibilityOnSiblings;
    }

    private void updateImportantForAccessibility(boolean expanded) {
        if (this.viewRef != null) {
            ViewParent viewParent = this.viewRef.get().getParent();
            if (viewParent instanceof CoordinatorLayout) {
                CoordinatorLayout parent = (CoordinatorLayout) viewParent;
                int childCount = parent.getChildCount();
                if (Build.VERSION.SDK_INT >= 16 && expanded) {
                    if (this.importantForAccessibilityMap == null) {
                        this.importantForAccessibilityMap = new HashMap(childCount);
                    } else {
                        return;
                    }
                }
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    if (child != this.viewRef.get()) {
                        if (expanded) {
                            if (Build.VERSION.SDK_INT >= 16) {
                                this.importantForAccessibilityMap.put(child, Integer.valueOf(child.getImportantForAccessibility()));
                            }
                            if (this.updateImportantForAccessibilityOnSiblings) {
                                ViewCompat.setImportantForAccessibility(child, 4);
                            }
                        } else if (this.updateImportantForAccessibilityOnSiblings && this.importantForAccessibilityMap != null && this.importantForAccessibilityMap.containsKey(child)) {
                            ViewCompat.setImportantForAccessibility(child, this.importantForAccessibilityMap.get(child).intValue());
                        }
                    }
                }
                if (!expanded) {
                    this.importantForAccessibilityMap = null;
                } else if (this.updateImportantForAccessibilityOnSiblings) {
                    this.viewRef.get().sendAccessibilityEvent(8);
                }
            }
        }
    }

    private void updateAccessibilityActions() {
        V child;
        if (this.viewRef != null && (child = this.viewRef.get()) != null) {
            ViewCompat.removeAccessibilityAction(child, 524288);
            ViewCompat.removeAccessibilityAction(child, 262144);
            ViewCompat.removeAccessibilityAction(child, 1048576);
            if (this.expandHalfwayActionId != -1) {
                ViewCompat.removeAccessibilityAction(child, this.expandHalfwayActionId);
            }
            if (!this.fitToContents && this.state != 6) {
                this.expandHalfwayActionId = addAccessibilityActionForState(child, R.string.bottomsheet_action_expand_halfway, 6);
            }
            if (this.hideable && this.state != 5) {
                replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
            }
            switch (this.state) {
                case 3:
                    int nextState = this.fitToContents ? 4 : 6;
                    replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, nextState);
                    return;
                case 4:
                    int nextState2 = this.fitToContents ? 3 : 6;
                    replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, nextState2);
                    return;
                case 5:
                default:
                    return;
                case 6:
                    replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
                    replaceAccessibilityActionForState(child, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
                    return;
            }
        }
    }

    private void replaceAccessibilityActionForState(V child, AccessibilityNodeInfoCompat.AccessibilityActionCompat action, int state) {
        ViewCompat.replaceAccessibilityAction(child, action, null, createAccessibilityViewCommandForState(state));
    }

    private int addAccessibilityActionForState(V child, @StringRes int stringResId, int state) {
        return ViewCompat.addAccessibilityAction(child, child.getResources().getString(stringResId), createAccessibilityViewCommandForState(state));
    }

    private AccessibilityViewCommand createAccessibilityViewCommandForState(final int state) {
        return new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.6
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments arguments) {
                BottomSheetBehavior.this.setState(state);
                return true;
            }
        };
    }
}
