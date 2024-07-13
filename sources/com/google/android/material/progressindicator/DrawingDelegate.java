package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class DrawingDelegate<S extends BaseProgressIndicatorSpec> {
    protected DrawableWithAnimatedVisibilityChange drawable;
    S spec;

    abstract void adjustCanvas(@NonNull Canvas canvas, @FloatRange(from = 0.0d, to = 1.0d) float f);

    public abstract void fillIndicator(@NonNull Canvas canvas, @NonNull Paint paint, @FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2, @ColorInt int i);

    public abstract void fillTrack(@NonNull Canvas canvas, @NonNull Paint paint);

    public abstract int getPreferredHeight();

    public abstract int getPreferredWidth();

    public DrawingDelegate(S spec) {
        this.spec = spec;
    }

    public void registerDrawable(@NonNull DrawableWithAnimatedVisibilityChange drawable) {
        this.drawable = drawable;
    }

    public void validateSpecAndAdjustCanvas(@NonNull Canvas canvas, @FloatRange(from = 0.0d, to = 1.0d) float trackThicknessFraction) {
        this.spec.validateSpec();
        adjustCanvas(canvas, trackThicknessFraction);
    }
}
