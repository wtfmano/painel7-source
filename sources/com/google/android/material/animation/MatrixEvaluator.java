package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class MatrixEvaluator implements TypeEvaluator<Matrix> {
    private final float[] tempStartValues = new float[9];
    private final float[] tempEndValues = new float[9];
    private final Matrix tempMatrix = new Matrix();

    @Override // android.animation.TypeEvaluator
    @NonNull
    public Matrix evaluate(float fraction, @NonNull Matrix startValue, @NonNull Matrix endValue) {
        startValue.getValues(this.tempStartValues);
        endValue.getValues(this.tempEndValues);
        for (int i = 0; i < 9; i++) {
            float diff = this.tempEndValues[i] - this.tempStartValues[i];
            this.tempEndValues[i] = this.tempStartValues[i] + (fraction * diff);
        }
        this.tempMatrix.setValues(this.tempEndValues);
        return this.tempMatrix;
    }
}
