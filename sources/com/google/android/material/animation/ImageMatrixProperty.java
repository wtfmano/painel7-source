package com.google.android.material.animation;

import android.graphics.Matrix;
import android.util.Property;
import android.widget.ImageView;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class ImageMatrixProperty extends Property<ImageView, Matrix> {
    private final Matrix matrix;

    public ImageMatrixProperty() {
        super(Matrix.class, "imageMatrixProperty");
        this.matrix = new Matrix();
    }

    @Override // android.util.Property
    public void set(@NonNull ImageView object, @NonNull Matrix value) {
        object.setImageMatrix(value);
    }

    @Override // android.util.Property
    @NonNull
    public Matrix get(@NonNull ImageView object) {
        this.matrix.set(object.getImageMatrix());
        return this.matrix;
    }
}
