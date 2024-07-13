package com.google.android.material.shape;

import android.graphics.RectF;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class CornerTreatment {
    @Deprecated
    public void getCornerPath(float angle, float interpolation, @NonNull ShapePath shapePath) {
    }

    public void getCornerPath(@NonNull ShapePath shapePath, float angle, float interpolation, float radius) {
        getCornerPath(angle, interpolation, shapePath);
    }

    public void getCornerPath(@NonNull ShapePath shapePath, float angle, float interpolation, @NonNull RectF bounds, @NonNull CornerSize size) {
        getCornerPath(shapePath, angle, interpolation, size.getCornerSize(bounds));
    }
}
