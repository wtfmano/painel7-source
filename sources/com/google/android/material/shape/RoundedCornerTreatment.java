package com.google.android.material.shape;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class RoundedCornerTreatment extends CornerTreatment {
    float radius;

    public RoundedCornerTreatment() {
        this.radius = -1.0f;
    }

    @Deprecated
    public RoundedCornerTreatment(float radius) {
        this.radius = -1.0f;
        this.radius = radius;
    }

    @Override // com.google.android.material.shape.CornerTreatment
    public void getCornerPath(@NonNull ShapePath shapePath, float angle, float interpolation, float radius) {
        shapePath.reset(0.0f, radius * interpolation, 180.0f, 180.0f - angle);
        shapePath.addArc(0.0f, 0.0f, 2.0f * radius * interpolation, 2.0f * radius * interpolation, 180.0f, angle);
    }
}
