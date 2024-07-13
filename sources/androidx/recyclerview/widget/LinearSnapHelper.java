package androidx.recyclerview.widget;

import android.graphics.PointF;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class LinearSnapHelper extends SnapHelper {
    private static final float INVALID_DISTANCE = 1.0f;
    @Nullable
    private OrientationHelper mHorizontalHelper;
    @Nullable
    private OrientationHelper mVerticalHelper;

    @Override // androidx.recyclerview.widget.SnapHelper
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToCenter(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToCenter(targetView, getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    @Override // androidx.recyclerview.widget.SnapHelper
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int itemCount;
        View currentView;
        int currentPosition;
        int hDeltaJump;
        int vDeltaJump;
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) || (itemCount = layoutManager.getItemCount()) == 0 || (currentView = findSnapView(layoutManager)) == null || (currentPosition = layoutManager.getPosition(currentView)) == -1) {
            return -1;
        }
        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider = (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
        PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd != null) {
            if (layoutManager.canScrollHorizontally()) {
                hDeltaJump = estimateNextPositionDiffForFling(layoutManager, getHorizontalHelper(layoutManager), velocityX, 0);
                if (vectorForEnd.x < 0.0f) {
                    hDeltaJump = -hDeltaJump;
                }
            } else {
                hDeltaJump = 0;
            }
            if (layoutManager.canScrollVertically()) {
                vDeltaJump = estimateNextPositionDiffForFling(layoutManager, getVerticalHelper(layoutManager), 0, velocityY);
                if (vectorForEnd.y < 0.0f) {
                    vDeltaJump = -vDeltaJump;
                }
            } else {
                vDeltaJump = 0;
            }
            int deltaJump = layoutManager.canScrollVertically() ? vDeltaJump : hDeltaJump;
            if (deltaJump != 0) {
                int targetPos = currentPosition + deltaJump;
                if (targetPos < 0) {
                    targetPos = 0;
                }
                if (targetPos >= itemCount) {
                    return itemCount - 1;
                }
                return targetPos;
            }
            return -1;
        }
        return -1;
    }

    @Override // androidx.recyclerview.widget.SnapHelper
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return findCenterView(layoutManager, getVerticalHelper(layoutManager));
        }
        if (layoutManager.canScrollHorizontally()) {
            return findCenterView(layoutManager, getHorizontalHelper(layoutManager));
        }
        return null;
    }

    private int distanceToCenter(@NonNull View targetView, OrientationHelper helper) {
        int childCenter = helper.getDecoratedStart(targetView) + (helper.getDecoratedMeasurement(targetView) / 2);
        int containerCenter = helper.getStartAfterPadding() + (helper.getTotalSpace() / 2);
        return childCenter - containerCenter;
    }

    private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager, OrientationHelper helper, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0.0f) {
            return 0;
        }
        int distance = Math.abs(distances[0]) > Math.abs(distances[1]) ? distances[0] : distances[1];
        return Math.round(distance / distancePerChild);
    }

    @Nullable
    private View findCenterView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }
        View closestChild = null;
        int center = helper.getStartAfterPadding() + (helper.getTotalSpace() / 2);
        int absClosest = Integer.MAX_VALUE;
        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            int childCenter = helper.getDecoratedStart(child) + (helper.getDecoratedMeasurement(child) / 2);
            int absDistance = Math.abs(childCenter - center);
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }

    private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return 1.0f;
        }
        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            int pos = layoutManager.getPosition(child);
            if (pos != -1) {
                if (pos < minPos) {
                    minPos = pos;
                    minPosView = child;
                }
                if (pos > maxPos) {
                    maxPos = pos;
                    maxPosView = child;
                }
            }
        }
        if (minPosView == null || maxPosView == null) {
            return 1.0f;
        }
        int start = Math.min(helper.getDecoratedStart(minPosView), helper.getDecoratedStart(maxPosView));
        int end = Math.max(helper.getDecoratedEnd(minPosView), helper.getDecoratedEnd(maxPosView));
        int distance = end - start;
        if (distance == 0) {
            return 1.0f;
        }
        return (1.0f * distance) / ((maxPos - minPos) + 1);
    }

    @NonNull
    private OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (this.mVerticalHelper == null || this.mVerticalHelper.mLayoutManager != layoutManager) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.mVerticalHelper;
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (this.mHorizontalHelper == null || this.mHorizontalHelper.mLayoutManager != layoutManager) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }
}
