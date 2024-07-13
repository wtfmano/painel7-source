package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.color.MaterialColors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class CircularDrawingDelegate extends DrawingDelegate<CircularProgressIndicatorSpec> {
    private float adjustedRadius;
    private int arcDirectionFactor;
    private float displayedCornerRadius;
    private float displayedTrackThickness;

    public CircularDrawingDelegate(@NonNull CircularProgressIndicatorSpec spec) {
        super(spec);
        this.arcDirectionFactor = 1;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public int getPreferredWidth() {
        return getSize();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public int getPreferredHeight() {
        return getSize();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void adjustCanvas(@NonNull Canvas canvas, @FloatRange(from = 0.0d, to = 1.0d) float trackThicknessFraction) {
        float outerRadiusWithInset = (((CircularProgressIndicatorSpec) this.spec).indicatorSize / 2.0f) + ((CircularProgressIndicatorSpec) this.spec).indicatorInset;
        canvas.translate(outerRadiusWithInset, outerRadiusWithInset);
        canvas.rotate(-90.0f);
        canvas.clipRect(-outerRadiusWithInset, -outerRadiusWithInset, outerRadiusWithInset, outerRadiusWithInset);
        this.arcDirectionFactor = ((CircularProgressIndicatorSpec) this.spec).indicatorDirection == 0 ? 1 : -1;
        this.displayedTrackThickness = ((CircularProgressIndicatorSpec) this.spec).trackThickness * trackThicknessFraction;
        this.displayedCornerRadius = ((CircularProgressIndicatorSpec) this.spec).trackCornerRadius * trackThicknessFraction;
        this.adjustedRadius = (((CircularProgressIndicatorSpec) this.spec).indicatorSize - ((CircularProgressIndicatorSpec) this.spec).trackThickness) / 2.0f;
        if ((!this.drawable.isShowing() || ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior != 2) && (!this.drawable.isHiding() || ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior != 1)) {
            if ((!this.drawable.isShowing() || ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior != 1) && (!this.drawable.isHiding() || ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior != 2)) {
                return;
            }
            this.adjustedRadius -= (((CircularProgressIndicatorSpec) this.spec).trackThickness * (1.0f - trackThicknessFraction)) / 2.0f;
            return;
        }
        this.adjustedRadius = ((((CircularProgressIndicatorSpec) this.spec).trackThickness * (1.0f - trackThicknessFraction)) / 2.0f) + this.adjustedRadius;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void fillIndicator(@NonNull Canvas canvas, @NonNull Paint paint, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @ColorInt int color) {
        if (startFraction != endFraction) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.BUTT);
            paint.setAntiAlias(true);
            paint.setColor(color);
            paint.setStrokeWidth(this.displayedTrackThickness);
            float startDegree = 360.0f * startFraction * this.arcDirectionFactor;
            float arcDegree = endFraction >= startFraction ? (endFraction - startFraction) * 360.0f * this.arcDirectionFactor : ((1.0f + endFraction) - startFraction) * 360.0f * this.arcDirectionFactor;
            RectF arcBound = new RectF(-this.adjustedRadius, -this.adjustedRadius, this.adjustedRadius, this.adjustedRadius);
            canvas.drawArc(arcBound, startDegree, arcDegree, false, paint);
            if (this.displayedCornerRadius > 0.0f && Math.abs(arcDegree) < 360.0f) {
                paint.setStyle(Paint.Style.FILL);
                RectF cornerPatternRectBound = new RectF(-this.displayedCornerRadius, -this.displayedCornerRadius, this.displayedCornerRadius, this.displayedCornerRadius);
                drawRoundedEnd(canvas, paint, this.displayedTrackThickness, this.displayedCornerRadius, startDegree, true, cornerPatternRectBound);
                drawRoundedEnd(canvas, paint, this.displayedTrackThickness, this.displayedCornerRadius, startDegree + arcDegree, false, cornerPatternRectBound);
            }
        }
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void fillTrack(@NonNull Canvas canvas, @NonNull Paint paint) {
        int trackColor = MaterialColors.compositeARGBWithAlpha(((CircularProgressIndicatorSpec) this.spec).trackColor, this.drawable.getAlpha());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setColor(trackColor);
        paint.setStrokeWidth(this.displayedTrackThickness);
        RectF arcBound = new RectF(-this.adjustedRadius, -this.adjustedRadius, this.adjustedRadius, this.adjustedRadius);
        canvas.drawArc(arcBound, 0.0f, 360.0f, false, paint);
    }

    private int getSize() {
        return (((CircularProgressIndicatorSpec) this.spec).indicatorInset * 2) + ((CircularProgressIndicatorSpec) this.spec).indicatorSize;
    }

    private void drawRoundedEnd(Canvas canvas, Paint paint, float trackSize, float cornerRadius, float positionInDeg, boolean isStartPosition, RectF cornerPatternRectBound) {
        float startOrEndFactor = isStartPosition ? -1.0f : 1.0f;
        canvas.save();
        canvas.rotate(positionInDeg);
        canvas.drawRect((this.adjustedRadius - (trackSize / 2.0f)) + cornerRadius, Math.min(0.0f, startOrEndFactor * cornerRadius * this.arcDirectionFactor), (this.adjustedRadius + (trackSize / 2.0f)) - cornerRadius, Math.max(0.0f, startOrEndFactor * cornerRadius * this.arcDirectionFactor), paint);
        canvas.translate((this.adjustedRadius - (trackSize / 2.0f)) + cornerRadius, 0.0f);
        canvas.drawArc(cornerPatternRectBound, 180.0f, (-startOrEndFactor) * 90.0f * this.arcDirectionFactor, true, paint);
        canvas.translate(trackSize - (2.0f * cornerRadius), 0.0f);
        canvas.drawArc(cornerPatternRectBound, 0.0f, 90.0f * startOrEndFactor * this.arcDirectionFactor, true, paint);
        canvas.restore();
    }
}
