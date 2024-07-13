package com.google.android.material.shape;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class TriangleEdgeTreatment extends EdgeTreatment {
    private final boolean inside;
    private final float size;

    public TriangleEdgeTreatment(float size, boolean inside) {
        this.size = size;
        this.inside = inside;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float length, float center, float interpolation, @NonNull ShapePath shapePath) {
        shapePath.lineTo(center - (this.size * interpolation), 0.0f);
        shapePath.lineTo(center, this.inside ? this.size * interpolation : (-this.size) * interpolation);
        shapePath.lineTo((this.size * interpolation) + center, 0.0f);
        shapePath.lineTo(length, 0.0f);
    }
}
