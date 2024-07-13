package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.color.MaterialColors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class LinearDrawingDelegate extends DrawingDelegate<LinearProgressIndicatorSpec> {
    private float displayedCornerRadius;
    private float displayedTrackThickness;
    private float trackLength;

    public LinearDrawingDelegate(@NonNull LinearProgressIndicatorSpec spec) {
        super(spec);
        this.trackLength = 300.0f;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public int getPreferredWidth() {
        return -1;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public int getPreferredHeight() {
        return ((LinearProgressIndicatorSpec) this.spec).trackThickness;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void adjustCanvas(@NonNull Canvas canvas, @FloatRange(from = 0.0d, to = 1.0d) float trackThicknessFraction) {
        Rect clipBounds = canvas.getClipBounds();
        this.trackLength = clipBounds.width();
        float trackSize = ((LinearProgressIndicatorSpec) this.spec).trackThickness;
        canvas.translate((clipBounds.width() / 2.0f) + clipBounds.left, Math.max(0.0f, (clipBounds.height() - ((LinearProgressIndicatorSpec) this.spec).trackThickness) / 2.0f) + (clipBounds.height() / 2.0f) + clipBounds.top);
        if (((LinearProgressIndicatorSpec) this.spec).drawHorizontallyInverse) {
            canvas.scale(-1.0f, 1.0f);
        }
        if ((this.drawable.isShowing() && ((LinearProgressIndicatorSpec) this.spec).showAnimationBehavior == 1) || (this.drawable.isHiding() && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior == 2)) {
            canvas.scale(1.0f, -1.0f);
        }
        if (this.drawable.isShowing() || this.drawable.isHiding()) {
            canvas.translate(0.0f, (((LinearProgressIndicatorSpec) this.spec).trackThickness * (trackThicknessFraction - 1.0f)) / 2.0f);
        }
        canvas.clipRect((-this.trackLength) / 2.0f, (-trackSize) / 2.0f, this.trackLength / 2.0f, trackSize / 2.0f);
        this.displayedTrackThickness = ((LinearProgressIndicatorSpec) this.spec).trackThickness * trackThicknessFraction;
        this.displayedCornerRadius = ((LinearProgressIndicatorSpec) this.spec).trackCornerRadius * trackThicknessFraction;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void fillIndicator(@NonNull Canvas canvas, @NonNull Paint paint, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @ColorInt int color) {
        if (startFraction != endFraction) {
            float adjustedStartX = ((-this.trackLength) / 2.0f) + this.displayedCornerRadius + ((this.trackLength - (2.0f * this.displayedCornerRadius)) * startFraction);
            float adjustedEndX = ((-this.trackLength) / 2.0f) + this.displayedCornerRadius + ((this.trackLength - (2.0f * this.displayedCornerRadius)) * endFraction);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setColor(color);
            canvas.drawRect(adjustedStartX, (-this.displayedTrackThickness) / 2.0f, adjustedEndX, this.displayedTrackThickness / 2.0f, paint);
            RectF cornerPatternRectBound = new RectF(-this.displayedCornerRadius, -this.displayedCornerRadius, this.displayedCornerRadius, this.displayedCornerRadius);
            drawRoundedEnd(canvas, paint, this.displayedTrackThickness, this.displayedCornerRadius, adjustedStartX, true, cornerPatternRectBound);
            drawRoundedEnd(canvas, paint, this.displayedTrackThickness, this.displayedCornerRadius, adjustedEndX, false, cornerPatternRectBound);
        }
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void fillTrack(@NonNull Canvas canvas, @NonNull Paint paint) {
        int trackColor = MaterialColors.compositeARGBWithAlpha(((LinearProgressIndicatorSpec) this.spec).trackColor, this.drawable.getAlpha());
        float adjustedStartX = ((-this.trackLength) / 2.0f) + this.displayedCornerRadius;
        float adjustedEndX = -adjustedStartX;
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(trackColor);
        canvas.drawRect(adjustedStartX, (-this.displayedTrackThickness) / 2.0f, adjustedEndX, this.displayedTrackThickness / 2.0f, paint);
        RectF cornerPatternRectBound = new RectF(-this.displayedCornerRadius, -this.displayedCornerRadius, this.displayedCornerRadius, this.displayedCornerRadius);
        drawRoundedEnd(canvas, paint, this.displayedTrackThickness, this.displayedCornerRadius, adjustedStartX, true, cornerPatternRectBound);
        drawRoundedEnd(canvas, paint, this.displayedTrackThickness, this.displayedCornerRadius, adjustedEndX, false, cornerPatternRectBound);
    }

    private static void drawRoundedEnd(Canvas canvas, Paint paint, float trackSize, float cornerRadius, float x, boolean isStartPosition, RectF cornerPatternRectBound) {
        canvas.save();
        canvas.translate(x, 0.0f);
        if (!isStartPosition) {
            canvas.rotate(180.0f);
        }
        canvas.drawRect(-cornerRadius, ((-trackSize) / 2.0f) + cornerRadius, 0.0f, (trackSize / 2.0f) - cornerRadius, paint);
        canvas.save();
        canvas.translate(0.0f, ((-trackSize) / 2.0f) + cornerRadius);
        canvas.drawArc(cornerPatternRectBound, 180.0f, 90.0f, true, paint);
        canvas.restore();
        canvas.translate(0.0f, (trackSize / 2.0f) - cornerRadius);
        canvas.drawArc(cornerPatternRectBound, 180.0f, -90.0f, true, paint);
        canvas.restore();
    }
}
