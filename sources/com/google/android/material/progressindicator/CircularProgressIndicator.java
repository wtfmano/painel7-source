package com.google.android.material.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class CircularProgressIndicator extends BaseProgressIndicator<CircularProgressIndicatorSpec> {
    public static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CircularProgressIndicator;
    public static final int INDICATOR_DIRECTION_CLOCKWISE = 0;
    public static final int INDICATOR_DIRECTION_COUNTERCLOCKWISE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public @interface IndicatorDirection {
    }

    public CircularProgressIndicator(@NonNull Context context) {
        this(context, null);
    }

    public CircularProgressIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.circularProgressIndicatorStyle);
    }

    public CircularProgressIndicator(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr, DEF_STYLE_RES);
        initializeDrawables();
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public CircularProgressIndicatorSpec createSpec(@NonNull Context context, @NonNull AttributeSet attrs) {
        return new CircularProgressIndicatorSpec(context, attrs);
    }

    private void initializeDrawables() {
        setIndeterminateDrawable(IndeterminateDrawable.createCircularDrawable(getContext(), (CircularProgressIndicatorSpec) this.spec));
        setProgressDrawable(DeterminateDrawable.createCircularDrawable(getContext(), (CircularProgressIndicatorSpec) this.spec));
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public void setTrackThickness(int trackThickness) {
        super.setTrackThickness(trackThickness);
        ((CircularProgressIndicatorSpec) this.spec).validateSpec();
    }

    @Px
    public int getIndicatorInset() {
        return ((CircularProgressIndicatorSpec) this.spec).indicatorInset;
    }

    public void setIndicatorInset(@Px int indicatorInset) {
        if (((CircularProgressIndicatorSpec) this.spec).indicatorInset != indicatorInset) {
            ((CircularProgressIndicatorSpec) this.spec).indicatorInset = indicatorInset;
            invalidate();
        }
    }

    @Px
    public int getIndicatorSize() {
        return ((CircularProgressIndicatorSpec) this.spec).indicatorSize;
    }

    public void setIndicatorSize(@Px int indicatorSize) {
        int indicatorSize2 = Math.max(indicatorSize, getTrackThickness() * 2);
        if (((CircularProgressIndicatorSpec) this.spec).indicatorSize != indicatorSize2) {
            ((CircularProgressIndicatorSpec) this.spec).indicatorSize = indicatorSize2;
            ((CircularProgressIndicatorSpec) this.spec).validateSpec();
            invalidate();
        }
    }

    public int getIndicatorDirection() {
        return ((CircularProgressIndicatorSpec) this.spec).indicatorDirection;
    }

    public void setIndicatorDirection(int indicatorDirection) {
        ((CircularProgressIndicatorSpec) this.spec).indicatorDirection = indicatorDirection;
        invalidate();
    }
}
