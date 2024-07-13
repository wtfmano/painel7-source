package androidx.core.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;

/* loaded from: classes.dex */
public class ActivityOptionsCompat {
    public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";

    @NonNull
    public static ActivityOptionsCompat makeCustomAnimation(@NonNull Context context, int enterResId, int exitResId) {
        return Build.VERSION.SDK_INT >= 16 ? new ActivityOptionsCompatImpl(ActivityOptions.makeCustomAnimation(context, enterResId, exitResId)) : new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeScaleUpAnimation(@NonNull View source, int startX, int startY, int startWidth, int startHeight) {
        return Build.VERSION.SDK_INT >= 16 ? new ActivityOptionsCompatImpl(ActivityOptions.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight)) : new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeClipRevealAnimation(@NonNull View source, int startX, int startY, int width, int height) {
        return Build.VERSION.SDK_INT >= 23 ? new ActivityOptionsCompatImpl(ActivityOptions.makeClipRevealAnimation(source, startX, startY, width, height)) : new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(@NonNull View source, @NonNull Bitmap thumbnail, int startX, int startY) {
        return Build.VERSION.SDK_INT >= 16 ? new ActivityOptionsCompatImpl(ActivityOptions.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY)) : new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(@NonNull Activity activity, @NonNull View sharedElement, @NonNull String sharedElementName) {
        return Build.VERSION.SDK_INT >= 21 ? new ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName)) : new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(@NonNull Activity activity, Pair<View, String>... sharedElements) {
        if (Build.VERSION.SDK_INT >= 21) {
            android.util.Pair<View, String>[] pairs = null;
            if (sharedElements != null) {
                pairs = new android.util.Pair[sharedElements.length];
                for (int i = 0; i < sharedElements.length; i++) {
                    pairs[i] = android.util.Pair.create(sharedElements[i].first, sharedElements[i].second);
                }
            }
            return new ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation(activity, pairs));
        }
        return new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeTaskLaunchBehind() {
        return Build.VERSION.SDK_INT >= 21 ? new ActivityOptionsCompatImpl(ActivityOptions.makeTaskLaunchBehind()) : new ActivityOptionsCompat();
    }

    @NonNull
    public static ActivityOptionsCompat makeBasic() {
        return Build.VERSION.SDK_INT >= 23 ? new ActivityOptionsCompatImpl(ActivityOptions.makeBasic()) : new ActivityOptionsCompat();
    }

    @RequiresApi(16)
    /* loaded from: classes.dex */
    private static class ActivityOptionsCompatImpl extends ActivityOptionsCompat {
        private final ActivityOptions mActivityOptions;

        ActivityOptionsCompatImpl(ActivityOptions activityOptions) {
            this.mActivityOptions = activityOptions;
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        public Bundle toBundle() {
            return this.mActivityOptions.toBundle();
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        public void update(@NonNull ActivityOptionsCompat otherOptions) {
            if (otherOptions instanceof ActivityOptionsCompatImpl) {
                ActivityOptionsCompatImpl otherImpl = (ActivityOptionsCompatImpl) otherOptions;
                this.mActivityOptions.update(otherImpl.mActivityOptions);
            }
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        public void requestUsageTimeReport(@NonNull PendingIntent receiver) {
            if (Build.VERSION.SDK_INT >= 23) {
                this.mActivityOptions.requestUsageTimeReport(receiver);
            }
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        @NonNull
        public ActivityOptionsCompat setLaunchBounds(@Nullable Rect screenSpacePixelRect) {
            if (Build.VERSION.SDK_INT < 24) {
                return this;
            }
            return new ActivityOptionsCompatImpl(this.mActivityOptions.setLaunchBounds(screenSpacePixelRect));
        }

        @Override // androidx.core.app.ActivityOptionsCompat
        public Rect getLaunchBounds() {
            if (Build.VERSION.SDK_INT < 24) {
                return null;
            }
            return this.mActivityOptions.getLaunchBounds();
        }
    }

    protected ActivityOptionsCompat() {
    }

    @NonNull
    public ActivityOptionsCompat setLaunchBounds(@Nullable Rect screenSpacePixelRect) {
        return this;
    }

    @Nullable
    public Rect getLaunchBounds() {
        return null;
    }

    @Nullable
    public Bundle toBundle() {
        return null;
    }

    public void update(@NonNull ActivityOptionsCompat otherOptions) {
    }

    public void requestUsageTimeReport(@NonNull PendingIntent receiver) {
    }
}
