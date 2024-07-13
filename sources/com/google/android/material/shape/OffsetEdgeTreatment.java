package com.google.android.material.shape;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public final class OffsetEdgeTreatment extends EdgeTreatment {
    private final float offset;
    private final EdgeTreatment other;

    public OffsetEdgeTreatment(@NonNull EdgeTreatment other, float offset) {
        this.other = other;
        this.offset = offset;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float length, float center, float interpolation, @NonNull ShapePath shapePath) {
        this.other.getEdgePath(length, center - this.offset, interpolation, shapePath);
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public boolean forceIntersection() {
        return this.other.forceIntersection();
    }
}
