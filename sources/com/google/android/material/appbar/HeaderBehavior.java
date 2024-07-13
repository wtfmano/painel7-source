package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int activePointerId;
    @Nullable
    private Runnable flingRunnable;
    private boolean isBeingDragged;
    private int lastMotionY;
    OverScroller scroller;
    private int touchSlop;
    @Nullable
    private VelocityTracker velocityTracker;

    public HeaderBehavior() {
        this.activePointerId = -1;
        this.touchSlop = -1;
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activePointerId = -1;
        this.touchSlop = -1;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent ev) {
        int pointerIndex;
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        if (ev.getActionMasked() == 2 && this.isBeingDragged) {
            if (this.activePointerId == -1 || (pointerIndex = ev.findPointerIndex(this.activePointerId)) == -1) {
                return false;
            }
            int y = (int) ev.getY(pointerIndex);
            int yDiff = Math.abs(y - this.lastMotionY);
            if (yDiff > this.touchSlop) {
                this.lastMotionY = y;
                return true;
            }
        }
        if (ev.getActionMasked() == 0) {
            this.activePointerId = -1;
            int x = (int) ev.getX();
            int y2 = (int) ev.getY();
            this.isBeingDragged = canDragView(child) && parent.isPointInChildBounds(child, x, y2);
            if (this.isBeingDragged) {
                this.lastMotionY = y2;
                this.activePointerId = ev.getPointerId(0);
                ensureVelocityTracker();
                if (this.scroller != null && !this.scroller.isFinished()) {
                    this.scroller.abortAnimation();
                    return true;
                }
            }
        }
        if (this.velocityTracker != null) {
            this.velocityTracker.addMovement(ev);
            return false;
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0098  */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(@androidx.annotation.NonNull androidx.coordinatorlayout.widget.CoordinatorLayout r16, @androidx.annotation.NonNull V r17, @androidx.annotation.NonNull android.view.MotionEvent r18) {
        /*
            r15 = this;
            r12 = 0
            int r1 = r18.getActionMasked()
            switch(r1) {
                case 1: goto L63;
                case 2: goto L1b;
                case 3: goto L8e;
                case 4: goto L8;
                case 5: goto L8;
                case 6: goto L45;
                default: goto L8;
            }
        L8:
            android.view.VelocityTracker r1 = r15.velocityTracker
            if (r1 == 0) goto L13
            android.view.VelocityTracker r1 = r15.velocityTracker
            r0 = r18
            r1.addMovement(r0)
        L13:
            boolean r1 = r15.isBeingDragged
            if (r1 != 0) goto L19
            if (r12 == 0) goto La2
        L19:
            r1 = 1
        L1a:
            return r1
        L1b:
            int r1 = r15.activePointerId
            r0 = r18
            int r11 = r0.findPointerIndex(r1)
            r1 = -1
            if (r11 != r1) goto L28
            r1 = 0
            goto L1a
        L28:
            r0 = r18
            float r1 = r0.getY(r11)
            int r14 = (int) r1
            int r1 = r15.lastMotionY
            int r4 = r1 - r14
            r15.lastMotionY = r14
            r0 = r17
            int r5 = r15.getMaxDragOffset(r0)
            r6 = 0
            r1 = r15
            r2 = r16
            r3 = r17
            r1.scroll(r2, r3, r4, r5, r6)
            goto L8
        L45:
            int r1 = r18.getActionIndex()
            if (r1 != 0) goto L61
            r13 = 1
        L4c:
            r0 = r18
            int r1 = r0.getPointerId(r13)
            r15.activePointerId = r1
            r0 = r18
            float r1 = r0.getY(r13)
            r2 = 1056964608(0x3f000000, float:0.5)
            float r1 = r1 + r2
            int r1 = (int) r1
            r15.lastMotionY = r1
            goto L8
        L61:
            r13 = 0
            goto L4c
        L63:
            android.view.VelocityTracker r1 = r15.velocityTracker
            if (r1 == 0) goto L8e
            r12 = 1
            android.view.VelocityTracker r1 = r15.velocityTracker
            r0 = r18
            r1.addMovement(r0)
            android.view.VelocityTracker r1 = r15.velocityTracker
            r2 = 1000(0x3e8, float:1.401E-42)
            r1.computeCurrentVelocity(r2)
            android.view.VelocityTracker r1 = r15.velocityTracker
            int r2 = r15.activePointerId
            float r10 = r1.getYVelocity(r2)
            r0 = r17
            int r1 = r15.getScrollRangeForDragFling(r0)
            int r8 = -r1
            r9 = 0
            r5 = r15
            r6 = r16
            r7 = r17
            r5.fling(r6, r7, r8, r9, r10)
        L8e:
            r1 = 0
            r15.isBeingDragged = r1
            r1 = -1
            r15.activePointerId = r1
            android.view.VelocityTracker r1 = r15.velocityTracker
            if (r1 == 0) goto L8
            android.view.VelocityTracker r1 = r15.velocityTracker
            r1.recycle()
            r1 = 0
            r15.velocityTracker = r1
            goto L8
        La2:
            r1 = 0
            goto L1a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.HeaderBehavior.onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    public int setHeaderTopBottomOffset(CoordinatorLayout parent, V header, int newOffset) {
        return setHeaderTopBottomOffset(parent, header, newOffset, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, V header, int newOffset, int minOffset, int maxOffset) {
        int newOffset2;
        int curOffset = getTopAndBottomOffset();
        if (minOffset == 0 || curOffset < minOffset || curOffset > maxOffset || curOffset == (newOffset2 = MathUtils.clamp(newOffset, minOffset, maxOffset))) {
            return 0;
        }
        setTopAndBottomOffset(newOffset2);
        int consumed = curOffset - newOffset2;
        return consumed;
    }

    int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    public final int scroll(CoordinatorLayout coordinatorLayout, V header, int dy, int minOffset, int maxOffset) {
        return setHeaderTopBottomOffset(coordinatorLayout, header, getTopBottomOffsetForScrollingSibling() - dy, minOffset, maxOffset);
    }

    final boolean fling(CoordinatorLayout coordinatorLayout, @NonNull V layout, int minOffset, int maxOffset, float velocityY) {
        if (this.flingRunnable != null) {
            layout.removeCallbacks(this.flingRunnable);
            this.flingRunnable = null;
        }
        if (this.scroller == null) {
            this.scroller = new OverScroller(layout.getContext());
        }
        this.scroller.fling(0, getTopAndBottomOffset(), 0, Math.round(velocityY), 0, 0, minOffset, maxOffset);
        if (this.scroller.computeScrollOffset()) {
            this.flingRunnable = new FlingRunnable(coordinatorLayout, layout);
            ViewCompat.postOnAnimation(layout, this.flingRunnable);
            return true;
        }
        onFlingFinished(coordinatorLayout, layout);
        return false;
    }

    void onFlingFinished(CoordinatorLayout parent, V layout) {
    }

    boolean canDragView(V view) {
        return false;
    }

    int getMaxDragOffset(@NonNull V view) {
        return -view.getHeight();
    }

    int getScrollRangeForDragFling(@NonNull V view) {
        return view.getHeight();
    }

    private void ensureVelocityTracker() {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
    }

    /* loaded from: classes.dex */
    public class FlingRunnable implements Runnable {
        private final V layout;
        private final CoordinatorLayout parent;

        FlingRunnable(CoordinatorLayout parent, V layout) {
            HeaderBehavior.this = r1;
            this.parent = parent;
            this.layout = layout;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.layout != null && HeaderBehavior.this.scroller != null) {
                if (HeaderBehavior.this.scroller.computeScrollOffset()) {
                    HeaderBehavior.this.setHeaderTopBottomOffset(this.parent, this.layout, HeaderBehavior.this.scroller.getCurrY());
                    ViewCompat.postOnAnimation(this.layout, this);
                    return;
                }
                HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
            }
        }
    }
}
