package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets;
import androidx.core.os.BuildCompat;
import androidx.core.util.ObjectsCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class DisplayCutoutCompat {
    private final Object mDisplayCutout;

    public DisplayCutoutCompat(Rect safeInsets, List<Rect> boundingRects) {
        this(Build.VERSION.SDK_INT >= 28 ? new DisplayCutout(safeInsets, boundingRects) : null);
    }

    public DisplayCutoutCompat(@NonNull Insets safeInsets, @Nullable Rect boundLeft, @Nullable Rect boundTop, @Nullable Rect boundRight, @Nullable Rect boundBottom, @NonNull Insets waterfallInsets) {
        this(constructDisplayCutout(safeInsets, boundLeft, boundTop, boundRight, boundBottom, waterfallInsets));
    }

    private static DisplayCutout constructDisplayCutout(@NonNull Insets safeInsets, @Nullable Rect boundLeft, @Nullable Rect boundTop, @Nullable Rect boundRight, @Nullable Rect boundBottom, @NonNull Insets waterfallInsets) {
        if (BuildCompat.isAtLeastR()) {
            return new DisplayCutout(safeInsets.toPlatformInsets(), boundLeft, boundTop, boundRight, boundBottom, waterfallInsets.toPlatformInsets());
        }
        if (Build.VERSION.SDK_INT >= 29) {
            return new DisplayCutout(safeInsets.toPlatformInsets(), boundLeft, boundTop, boundRight, boundBottom);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            Rect safeInsetRect = new Rect(safeInsets.left, safeInsets.top, safeInsets.right, safeInsets.bottom);
            ArrayList<Rect> boundingRects = new ArrayList<>();
            if (boundLeft != null) {
                boundingRects.add(boundLeft);
            }
            if (boundTop != null) {
                boundingRects.add(boundTop);
            }
            if (boundRight != null) {
                boundingRects.add(boundRight);
            }
            if (boundBottom != null) {
                boundingRects.add(boundBottom);
            }
            return new DisplayCutout(safeInsetRect, boundingRects);
        }
        return null;
    }

    private DisplayCutoutCompat(Object displayCutout) {
        this.mDisplayCutout = displayCutout;
    }

    public int getSafeInsetTop() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.mDisplayCutout).getSafeInsetTop();
        }
        return 0;
    }

    public int getSafeInsetBottom() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.mDisplayCutout).getSafeInsetBottom();
        }
        return 0;
    }

    public int getSafeInsetLeft() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.mDisplayCutout).getSafeInsetLeft();
        }
        return 0;
    }

    public int getSafeInsetRight() {
        if (Build.VERSION.SDK_INT >= 28) {
            return ((DisplayCutout) this.mDisplayCutout).getSafeInsetRight();
        }
        return 0;
    }

    @NonNull
    public List<Rect> getBoundingRects() {
        return Build.VERSION.SDK_INT >= 28 ? ((DisplayCutout) this.mDisplayCutout).getBoundingRects() : Collections.emptyList();
    }

    @NonNull
    public Insets getWaterfallInsets() {
        return BuildCompat.isAtLeastR() ? Insets.toCompatInsets(((DisplayCutout) this.mDisplayCutout).getWaterfallInsets()) : Insets.NONE;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DisplayCutoutCompat other = (DisplayCutoutCompat) o;
        return ObjectsCompat.equals(this.mDisplayCutout, other.mDisplayCutout);
    }

    public int hashCode() {
        if (this.mDisplayCutout == null) {
            return 0;
        }
        return this.mDisplayCutout.hashCode();
    }

    public String toString() {
        return "DisplayCutoutCompat{" + this.mDisplayCutout + "}";
    }

    public static DisplayCutoutCompat wrap(Object displayCutout) {
        if (displayCutout == null) {
            return null;
        }
        return new DisplayCutoutCompat(displayCutout);
    }

    @RequiresApi(api = 28)
    public DisplayCutout unwrap() {
        return (DisplayCutout) this.mDisplayCutout;
    }
}
