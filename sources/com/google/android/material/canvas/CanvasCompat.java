package com.google.android.material.canvas;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class CanvasCompat {
    private CanvasCompat() {
    }

    public static int saveLayerAlpha(@NonNull Canvas canvas, @Nullable RectF bounds, int alpha) {
        return Build.VERSION.SDK_INT > 21 ? canvas.saveLayerAlpha(bounds, alpha) : canvas.saveLayerAlpha(bounds, alpha, 31);
    }

    public static int saveLayerAlpha(@NonNull Canvas canvas, float left, float top, float right, float bottom, int alpha) {
        return Build.VERSION.SDK_INT > 21 ? canvas.saveLayerAlpha(left, top, right, bottom, alpha) : canvas.saveLayerAlpha(left, top, right, bottom, alpha, 31);
    }
}
