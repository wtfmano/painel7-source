package com.google.android.material.shape;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class EdgeTreatment {
    @Deprecated
    public void getEdgePath(float length, float interpolation, @NonNull ShapePath shapePath) {
        float center = length / 2.0f;
        getEdgePath(length, center, interpolation, shapePath);
    }

    public void getEdgePath(float length, float center, float interpolation, @NonNull ShapePath shapePath) {
        shapePath.lineTo(length, 0.0f);
    }

    public boolean forceIntersection() {
        return false;
    }
}
