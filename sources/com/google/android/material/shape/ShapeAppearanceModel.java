package com.google.android.material.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import androidx.annotation.AttrRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import com.google.android.material.R;

/* loaded from: classes.dex */
public class ShapeAppearanceModel {
    public static final CornerSize PILL = new RelativeCornerSize(0.5f);
    EdgeTreatment bottomEdge;
    CornerTreatment bottomLeftCorner;
    CornerSize bottomLeftCornerSize;
    CornerTreatment bottomRightCorner;
    CornerSize bottomRightCornerSize;
    EdgeTreatment leftEdge;
    EdgeTreatment rightEdge;
    EdgeTreatment topEdge;
    CornerTreatment topLeftCorner;
    CornerSize topLeftCornerSize;
    CornerTreatment topRightCorner;
    CornerSize topRightCornerSize;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public interface CornerSizeUnaryOperator {
        @NonNull
        CornerSize apply(@NonNull CornerSize cornerSize);
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        @NonNull
        private EdgeTreatment bottomEdge;
        @NonNull
        private CornerTreatment bottomLeftCorner;
        @NonNull
        private CornerSize bottomLeftCornerSize;
        @NonNull
        private CornerTreatment bottomRightCorner;
        @NonNull
        private CornerSize bottomRightCornerSize;
        @NonNull
        private EdgeTreatment leftEdge;
        @NonNull
        private EdgeTreatment rightEdge;
        @NonNull
        private EdgeTreatment topEdge;
        @NonNull
        private CornerTreatment topLeftCorner;
        @NonNull
        private CornerSize topLeftCornerSize;
        @NonNull
        private CornerTreatment topRightCorner;
        @NonNull
        private CornerSize topRightCornerSize;

        public Builder() {
            this.topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
            this.topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
            this.bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
            this.bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
            this.topLeftCornerSize = new AbsoluteCornerSize(0.0f);
            this.topRightCornerSize = new AbsoluteCornerSize(0.0f);
            this.bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
            this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
            this.topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
            this.rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
            this.bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
            this.leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        }

        public Builder(@NonNull ShapeAppearanceModel other) {
            this.topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
            this.topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
            this.bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
            this.bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
            this.topLeftCornerSize = new AbsoluteCornerSize(0.0f);
            this.topRightCornerSize = new AbsoluteCornerSize(0.0f);
            this.bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
            this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
            this.topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
            this.rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
            this.bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
            this.leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
            this.topLeftCorner = other.topLeftCorner;
            this.topRightCorner = other.topRightCorner;
            this.bottomRightCorner = other.bottomRightCorner;
            this.bottomLeftCorner = other.bottomLeftCorner;
            this.topLeftCornerSize = other.topLeftCornerSize;
            this.topRightCornerSize = other.topRightCornerSize;
            this.bottomRightCornerSize = other.bottomRightCornerSize;
            this.bottomLeftCornerSize = other.bottomLeftCornerSize;
            this.topEdge = other.topEdge;
            this.rightEdge = other.rightEdge;
            this.bottomEdge = other.bottomEdge;
            this.leftEdge = other.leftEdge;
        }

        @NonNull
        public Builder setAllCorners(int cornerFamily, @Dimension float cornerSize) {
            return setAllCorners(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setAllCornerSizes(cornerSize);
        }

        @NonNull
        public Builder setAllCorners(@NonNull CornerTreatment cornerTreatment) {
            return setTopLeftCorner(cornerTreatment).setTopRightCorner(cornerTreatment).setBottomRightCorner(cornerTreatment).setBottomLeftCorner(cornerTreatment);
        }

        @NonNull
        public Builder setAllCornerSizes(@NonNull CornerSize cornerSize) {
            return setTopLeftCornerSize(cornerSize).setTopRightCornerSize(cornerSize).setBottomRightCornerSize(cornerSize).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setAllCornerSizes(@Dimension float cornerSize) {
            return setTopLeftCornerSize(cornerSize).setTopRightCornerSize(cornerSize).setBottomRightCornerSize(cornerSize).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopLeftCornerSize(@Dimension float cornerSize) {
            this.topLeftCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        @NonNull
        public Builder setTopLeftCornerSize(@NonNull CornerSize cornerSize) {
            this.topLeftCornerSize = cornerSize;
            return this;
        }

        @NonNull
        public Builder setTopRightCornerSize(@Dimension float cornerSize) {
            this.topRightCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        @NonNull
        public Builder setTopRightCornerSize(@NonNull CornerSize cornerSize) {
            this.topRightCornerSize = cornerSize;
            return this;
        }

        @NonNull
        public Builder setBottomRightCornerSize(@Dimension float cornerSize) {
            this.bottomRightCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        @NonNull
        public Builder setBottomRightCornerSize(@NonNull CornerSize cornerSize) {
            this.bottomRightCornerSize = cornerSize;
            return this;
        }

        @NonNull
        public Builder setBottomLeftCornerSize(@Dimension float cornerSize) {
            this.bottomLeftCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        @NonNull
        public Builder setBottomLeftCornerSize(@NonNull CornerSize cornerSize) {
            this.bottomLeftCornerSize = cornerSize;
            return this;
        }

        @NonNull
        public Builder setTopLeftCorner(int cornerFamily, @Dimension float cornerSize) {
            return setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopLeftCorner(int cornerFamily, @NonNull CornerSize cornerSize) {
            return setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopLeftCorner(@NonNull CornerTreatment topLeftCorner) {
            this.topLeftCorner = topLeftCorner;
            float size = compatCornerTreatmentSize(topLeftCorner);
            if (size != -1.0f) {
                setTopLeftCornerSize(size);
            }
            return this;
        }

        @NonNull
        public Builder setTopRightCorner(int cornerFamily, @Dimension float cornerSize) {
            return setTopRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopRightCorner(int cornerFamily, @NonNull CornerSize cornerSize) {
            return setTopRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setTopRightCorner(@NonNull CornerTreatment topRightCorner) {
            this.topRightCorner = topRightCorner;
            float size = compatCornerTreatmentSize(topRightCorner);
            if (size != -1.0f) {
                setTopRightCornerSize(size);
            }
            return this;
        }

        @NonNull
        public Builder setBottomRightCorner(int cornerFamily, @Dimension float cornerSize) {
            return setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomRightCorner(int cornerFamily, @NonNull CornerSize cornerSize) {
            return setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomRightCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomRightCorner(@NonNull CornerTreatment bottomRightCorner) {
            this.bottomRightCorner = bottomRightCorner;
            float size = compatCornerTreatmentSize(bottomRightCorner);
            if (size != -1.0f) {
                setBottomRightCornerSize(size);
            }
            return this;
        }

        @NonNull
        public Builder setBottomLeftCorner(int cornerFamily, @Dimension float cornerSize) {
            return setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomLeftCorner(int cornerFamily, @NonNull CornerSize cornerSize) {
            return setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomLeftCornerSize(cornerSize);
        }

        @NonNull
        public Builder setBottomLeftCorner(@NonNull CornerTreatment bottomLeftCorner) {
            this.bottomLeftCorner = bottomLeftCorner;
            float size = compatCornerTreatmentSize(bottomLeftCorner);
            if (size != -1.0f) {
                setBottomLeftCornerSize(size);
            }
            return this;
        }

        @NonNull
        public Builder setAllEdges(@NonNull EdgeTreatment edgeTreatment) {
            return setLeftEdge(edgeTreatment).setTopEdge(edgeTreatment).setRightEdge(edgeTreatment).setBottomEdge(edgeTreatment);
        }

        @NonNull
        public Builder setLeftEdge(@NonNull EdgeTreatment leftEdge) {
            this.leftEdge = leftEdge;
            return this;
        }

        @NonNull
        public Builder setTopEdge(@NonNull EdgeTreatment topEdge) {
            this.topEdge = topEdge;
            return this;
        }

        @NonNull
        public Builder setRightEdge(@NonNull EdgeTreatment rightEdge) {
            this.rightEdge = rightEdge;
            return this;
        }

        @NonNull
        public Builder setBottomEdge(@NonNull EdgeTreatment bottomEdge) {
            this.bottomEdge = bottomEdge;
            return this;
        }

        private static float compatCornerTreatmentSize(CornerTreatment treatment) {
            if (treatment instanceof RoundedCornerTreatment) {
                return ((RoundedCornerTreatment) treatment).radius;
            }
            if (treatment instanceof CutCornerTreatment) {
                return ((CutCornerTreatment) treatment).size;
            }
            return -1.0f;
        }

        @NonNull
        public ShapeAppearanceModel build() {
            return new ShapeAppearanceModel(this);
        }
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        return builder(context, attrs, defStyleAttr, defStyleRes, 0);
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes, int defaultCornerSize) {
        return builder(context, attrs, defStyleAttr, defStyleRes, new AbsoluteCornerSize(defaultCornerSize));
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes, @NonNull CornerSize defaultCornerSize) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialShape, defStyleAttr, defStyleRes);
        int shapeAppearanceResId = a.getResourceId(R.styleable.MaterialShape_shapeAppearance, 0);
        int shapeAppearanceOverlayResId = a.getResourceId(R.styleable.MaterialShape_shapeAppearanceOverlay, 0);
        a.recycle();
        return builder(context, shapeAppearanceResId, shapeAppearanceOverlayResId, defaultCornerSize);
    }

    @NonNull
    public static Builder builder(Context context, @StyleRes int shapeAppearanceResId, @StyleRes int shapeAppearanceOverlayResId) {
        return builder(context, shapeAppearanceResId, shapeAppearanceOverlayResId, 0);
    }

    @NonNull
    private static Builder builder(Context context, @StyleRes int shapeAppearanceResId, @StyleRes int shapeAppearanceOverlayResId, int defaultCornerSize) {
        return builder(context, shapeAppearanceResId, shapeAppearanceOverlayResId, new AbsoluteCornerSize(defaultCornerSize));
    }

    @NonNull
    private static Builder builder(Context context, @StyleRes int shapeAppearanceResId, @StyleRes int shapeAppearanceOverlayResId, @NonNull CornerSize defaultCornerSize) {
        if (shapeAppearanceOverlayResId != 0) {
            Context context2 = new ContextThemeWrapper(context, shapeAppearanceResId);
            shapeAppearanceResId = shapeAppearanceOverlayResId;
            context = context2;
        }
        TypedArray a = context.obtainStyledAttributes(shapeAppearanceResId, R.styleable.ShapeAppearance);
        try {
            int cornerFamily = a.getInt(R.styleable.ShapeAppearance_cornerFamily, 0);
            int cornerFamilyTopLeft = a.getInt(R.styleable.ShapeAppearance_cornerFamilyTopLeft, cornerFamily);
            int cornerFamilyTopRight = a.getInt(R.styleable.ShapeAppearance_cornerFamilyTopRight, cornerFamily);
            int cornerFamilyBottomRight = a.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomRight, cornerFamily);
            int cornerFamilyBottomLeft = a.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomLeft, cornerFamily);
            CornerSize cornerSize = getCornerSize(a, R.styleable.ShapeAppearance_cornerSize, defaultCornerSize);
            CornerSize cornerSizeTopLeft = getCornerSize(a, R.styleable.ShapeAppearance_cornerSizeTopLeft, cornerSize);
            CornerSize cornerSizeTopRight = getCornerSize(a, R.styleable.ShapeAppearance_cornerSizeTopRight, cornerSize);
            CornerSize cornerSizeBottomRight = getCornerSize(a, R.styleable.ShapeAppearance_cornerSizeBottomRight, cornerSize);
            CornerSize cornerSizeBottomLeft = getCornerSize(a, R.styleable.ShapeAppearance_cornerSizeBottomLeft, cornerSize);
            return new Builder().setTopLeftCorner(cornerFamilyTopLeft, cornerSizeTopLeft).setTopRightCorner(cornerFamilyTopRight, cornerSizeTopRight).setBottomRightCorner(cornerFamilyBottomRight, cornerSizeBottomRight).setBottomLeftCorner(cornerFamilyBottomLeft, cornerSizeBottomLeft);
        } finally {
            a.recycle();
        }
    }

    @NonNull
    private static CornerSize getCornerSize(TypedArray a, int index, @NonNull CornerSize defaultValue) {
        TypedValue value = a.peekValue(index);
        if (value != null) {
            if (value.type == 5) {
                return new AbsoluteCornerSize(TypedValue.complexToDimensionPixelSize(value.data, a.getResources().getDisplayMetrics()));
            }
            if (value.type == 6) {
                return new RelativeCornerSize(value.getFraction(1.0f, 1.0f));
            }
            return defaultValue;
        }
        return defaultValue;
    }

    private ShapeAppearanceModel(@NonNull Builder builder) {
        this.topLeftCorner = builder.topLeftCorner;
        this.topRightCorner = builder.topRightCorner;
        this.bottomRightCorner = builder.bottomRightCorner;
        this.bottomLeftCorner = builder.bottomLeftCorner;
        this.topLeftCornerSize = builder.topLeftCornerSize;
        this.topRightCornerSize = builder.topRightCornerSize;
        this.bottomRightCornerSize = builder.bottomRightCornerSize;
        this.bottomLeftCornerSize = builder.bottomLeftCornerSize;
        this.topEdge = builder.topEdge;
        this.rightEdge = builder.rightEdge;
        this.bottomEdge = builder.bottomEdge;
        this.leftEdge = builder.leftEdge;
    }

    public ShapeAppearanceModel() {
        this.topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.topLeftCornerSize = new AbsoluteCornerSize(0.0f);
        this.topRightCornerSize = new AbsoluteCornerSize(0.0f);
        this.bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
        this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
        this.topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        this.rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        this.bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        this.leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    }

    @NonNull
    public CornerTreatment getTopLeftCorner() {
        return this.topLeftCorner;
    }

    @NonNull
    public CornerTreatment getTopRightCorner() {
        return this.topRightCorner;
    }

    @NonNull
    public CornerTreatment getBottomRightCorner() {
        return this.bottomRightCorner;
    }

    @NonNull
    public CornerTreatment getBottomLeftCorner() {
        return this.bottomLeftCorner;
    }

    @NonNull
    public CornerSize getTopLeftCornerSize() {
        return this.topLeftCornerSize;
    }

    @NonNull
    public CornerSize getTopRightCornerSize() {
        return this.topRightCornerSize;
    }

    @NonNull
    public CornerSize getBottomRightCornerSize() {
        return this.bottomRightCornerSize;
    }

    @NonNull
    public CornerSize getBottomLeftCornerSize() {
        return this.bottomLeftCornerSize;
    }

    @NonNull
    public EdgeTreatment getLeftEdge() {
        return this.leftEdge;
    }

    @NonNull
    public EdgeTreatment getTopEdge() {
        return this.topEdge;
    }

    @NonNull
    public EdgeTreatment getRightEdge() {
        return this.rightEdge;
    }

    @NonNull
    public EdgeTreatment getBottomEdge() {
        return this.bottomEdge;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this);
    }

    @NonNull
    public ShapeAppearanceModel withCornerSize(float cornerSize) {
        return toBuilder().setAllCornerSizes(cornerSize).build();
    }

    @NonNull
    public ShapeAppearanceModel withCornerSize(@NonNull CornerSize cornerSize) {
        return toBuilder().setAllCornerSizes(cornerSize).build();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ShapeAppearanceModel withTransformedCornerSizes(@NonNull CornerSizeUnaryOperator op) {
        return toBuilder().setTopLeftCornerSize(op.apply(getTopLeftCornerSize())).setTopRightCornerSize(op.apply(getTopRightCornerSize())).setBottomLeftCornerSize(op.apply(getBottomLeftCornerSize())).setBottomRightCornerSize(op.apply(getBottomRightCornerSize())).build();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isRoundRect(@NonNull RectF bounds) {
        boolean hasDefaultEdges = this.leftEdge.getClass().equals(EdgeTreatment.class) && this.rightEdge.getClass().equals(EdgeTreatment.class) && this.topEdge.getClass().equals(EdgeTreatment.class) && this.bottomEdge.getClass().equals(EdgeTreatment.class);
        float cornerSize = this.topLeftCornerSize.getCornerSize(bounds);
        boolean cornersHaveSameSize = this.topRightCornerSize.getCornerSize(bounds) == cornerSize && this.bottomLeftCornerSize.getCornerSize(bounds) == cornerSize && this.bottomRightCornerSize.getCornerSize(bounds) == cornerSize;
        boolean hasRoundedCorners = (this.topRightCorner instanceof RoundedCornerTreatment) && (this.topLeftCorner instanceof RoundedCornerTreatment) && (this.bottomRightCorner instanceof RoundedCornerTreatment) && (this.bottomLeftCorner instanceof RoundedCornerTreatment);
        return hasDefaultEdges && cornersHaveSameSize && hasRoundedCorners;
    }
}
