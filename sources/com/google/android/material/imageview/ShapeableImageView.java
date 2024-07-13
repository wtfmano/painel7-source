package com.google.android.material.imageview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes.dex */
public class ShapeableImageView extends AppCompatImageView implements Shapeable {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ShapeableImageView;
    private static final int UNDEFINED_PADDING = Integer.MIN_VALUE;
    private final Paint borderPaint;
    @Dimension
    private int bottomContentPadding;
    private final Paint clearPaint;
    private final RectF destination;
    @Dimension
    private int endContentPadding;
    private boolean hasAdjustedPaddingAfterLayoutDirectionResolved;
    @Dimension
    private int leftContentPadding;
    private Path maskPath;
    private final RectF maskRect;
    private final Path path;
    private final ShapeAppearancePathProvider pathProvider;
    @Dimension
    private int rightContentPadding;
    @Nullable
    private MaterialShapeDrawable shadowDrawable;
    private ShapeAppearanceModel shapeAppearanceModel;
    @Dimension
    private int startContentPadding;
    @Nullable
    private ColorStateList strokeColor;
    @Dimension
    private float strokeWidth;
    @Dimension
    private int topContentPadding;

    public ShapeableImageView(Context context) {
        this(context, null, 0);
    }

    public ShapeableImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeableImageView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyle, DEF_STYLE_RES), attrs, defStyle);
        this.pathProvider = ShapeAppearancePathProvider.getInstance();
        this.path = new Path();
        this.hasAdjustedPaddingAfterLayoutDirectionResolved = false;
        Context context2 = getContext();
        this.clearPaint = new Paint();
        this.clearPaint.setAntiAlias(true);
        this.clearPaint.setColor(-1);
        this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.destination = new RectF();
        this.maskRect = new RectF();
        this.maskPath = new Path();
        TypedArray attributes = context2.obtainStyledAttributes(attrs, R.styleable.ShapeableImageView, defStyle, DEF_STYLE_RES);
        this.strokeColor = MaterialResources.getColorStateList(context2, attributes, R.styleable.ShapeableImageView_strokeColor);
        this.strokeWidth = attributes.getDimensionPixelSize(R.styleable.ShapeableImageView_strokeWidth, 0);
        int contentPadding = attributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPadding, 0);
        this.leftContentPadding = contentPadding;
        this.topContentPadding = contentPadding;
        this.rightContentPadding = contentPadding;
        this.bottomContentPadding = contentPadding;
        this.leftContentPadding = attributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingLeft, contentPadding);
        this.topContentPadding = attributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingTop, contentPadding);
        this.rightContentPadding = attributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingRight, contentPadding);
        this.bottomContentPadding = attributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingBottom, contentPadding);
        this.startContentPadding = attributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingStart, Integer.MIN_VALUE);
        this.endContentPadding = attributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingEnd, Integer.MIN_VALUE);
        attributes.recycle();
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setAntiAlias(true);
        this.shapeAppearanceModel = ShapeAppearanceModel.builder(context2, attrs, defStyle, DEF_STYLE_RES).build();
        if (Build.VERSION.SDK_INT >= 21) {
            setOutlineProvider(new OutlineProvider());
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        setLayerType(0, null);
        super.onDetachedFromWindow();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(2, null);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!this.hasAdjustedPaddingAfterLayoutDirectionResolved) {
            if (Build.VERSION.SDK_INT <= 19 || isLayoutDirectionResolved()) {
                this.hasAdjustedPaddingAfterLayoutDirectionResolved = true;
                if (Build.VERSION.SDK_INT >= 21 && (isPaddingRelative() || isContentPaddingRelative())) {
                    setPaddingRelative(super.getPaddingStart(), super.getPaddingTop(), super.getPaddingEnd(), super.getPaddingBottom());
                } else {
                    setPadding(super.getPaddingLeft(), super.getPaddingTop(), super.getPaddingRight(), super.getPaddingBottom());
                }
            }
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.maskPath, this.clearPaint);
        drawStroke(canvas);
    }

    @Override // android.view.View
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        updateShapeMask(width, height);
    }

    public void setContentPadding(@Dimension int left, @Dimension int top, @Dimension int right, @Dimension int bottom) {
        this.startContentPadding = Integer.MIN_VALUE;
        this.endContentPadding = Integer.MIN_VALUE;
        super.setPadding((super.getPaddingLeft() - this.leftContentPadding) + left, (super.getPaddingTop() - this.topContentPadding) + top, (super.getPaddingRight() - this.rightContentPadding) + right, (super.getPaddingBottom() - this.bottomContentPadding) + bottom);
        this.leftContentPadding = left;
        this.topContentPadding = top;
        this.rightContentPadding = right;
        this.bottomContentPadding = bottom;
    }

    @RequiresApi(17)
    public void setContentPaddingRelative(@Dimension int start, @Dimension int top, @Dimension int end, @Dimension int bottom) {
        super.setPaddingRelative((super.getPaddingStart() - getContentPaddingStart()) + start, (super.getPaddingTop() - this.topContentPadding) + top, (super.getPaddingEnd() - getContentPaddingEnd()) + end, (super.getPaddingBottom() - this.bottomContentPadding) + bottom);
        this.leftContentPadding = isRtl() ? end : start;
        this.topContentPadding = top;
        if (!isRtl()) {
            start = end;
        }
        this.rightContentPadding = start;
        this.bottomContentPadding = bottom;
    }

    private boolean isContentPaddingRelative() {
        return (this.startContentPadding == Integer.MIN_VALUE && this.endContentPadding == Integer.MIN_VALUE) ? false : true;
    }

    @Dimension
    public int getContentPaddingBottom() {
        return this.bottomContentPadding;
    }

    @Dimension
    public final int getContentPaddingEnd() {
        if (this.endContentPadding != Integer.MIN_VALUE) {
            return this.endContentPadding;
        }
        return isRtl() ? this.leftContentPadding : this.rightContentPadding;
    }

    @Dimension
    public int getContentPaddingLeft() {
        if (isContentPaddingRelative()) {
            if (isRtl() && this.endContentPadding != Integer.MIN_VALUE) {
                return this.endContentPadding;
            }
            if (!isRtl() && this.startContentPadding != Integer.MIN_VALUE) {
                return this.startContentPadding;
            }
        }
        return this.leftContentPadding;
    }

    @Dimension
    public int getContentPaddingRight() {
        if (isContentPaddingRelative()) {
            if (isRtl() && this.startContentPadding != Integer.MIN_VALUE) {
                return this.startContentPadding;
            }
            if (!isRtl() && this.endContentPadding != Integer.MIN_VALUE) {
                return this.endContentPadding;
            }
        }
        return this.rightContentPadding;
    }

    @Dimension
    public final int getContentPaddingStart() {
        if (this.startContentPadding != Integer.MIN_VALUE) {
            return this.startContentPadding;
        }
        return isRtl() ? this.rightContentPadding : this.leftContentPadding;
    }

    @Dimension
    public int getContentPaddingTop() {
        return this.topContentPadding;
    }

    private boolean isRtl() {
        return Build.VERSION.SDK_INT >= 17 && getLayoutDirection() == 1;
    }

    @Override // android.view.View
    public void setPadding(@Dimension int left, @Dimension int top, @Dimension int right, @Dimension int bottom) {
        super.setPadding(getContentPaddingLeft() + left, getContentPaddingTop() + top, getContentPaddingRight() + right, getContentPaddingBottom() + bottom);
    }

    @Override // android.view.View
    public void setPaddingRelative(@Dimension int start, @Dimension int top, @Dimension int end, @Dimension int bottom) {
        super.setPaddingRelative(getContentPaddingStart() + start, getContentPaddingTop() + top, getContentPaddingEnd() + end, getContentPaddingBottom() + bottom);
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingBottom() {
        return super.getPaddingBottom() - getContentPaddingBottom();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingEnd() {
        return super.getPaddingEnd() - getContentPaddingEnd();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingLeft() {
        return super.getPaddingLeft() - getContentPaddingLeft();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingRight() {
        return super.getPaddingRight() - getContentPaddingRight();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingStart() {
        return super.getPaddingStart() - getContentPaddingStart();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingTop() {
        return super.getPaddingTop() - getContentPaddingTop();
    }

    @Override // com.google.android.material.shape.Shapeable
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        if (this.shadowDrawable != null) {
            this.shadowDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        }
        updateShapeMask(getWidth(), getHeight());
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    @Override // com.google.android.material.shape.Shapeable
    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    private void updateShapeMask(int width, int height) {
        this.destination.set(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height - getPaddingBottom());
        this.pathProvider.calculatePath(this.shapeAppearanceModel, 1.0f, this.destination, this.path);
        this.maskPath.rewind();
        this.maskPath.addPath(this.path);
        this.maskRect.set(0.0f, 0.0f, width, height);
        this.maskPath.addRect(this.maskRect, Path.Direction.CCW);
    }

    private void drawStroke(Canvas canvas) {
        if (this.strokeColor != null) {
            this.borderPaint.setStrokeWidth(this.strokeWidth);
            int colorForState = this.strokeColor.getColorForState(getDrawableState(), this.strokeColor.getDefaultColor());
            if (this.strokeWidth > 0.0f && colorForState != 0) {
                this.borderPaint.setColor(colorForState);
                canvas.drawPath(this.path, this.borderPaint);
            }
        }
    }

    public void setStrokeColorResource(@ColorRes int strokeColorResourceId) {
        setStrokeColor(AppCompatResources.getColorStateList(getContext(), strokeColorResourceId));
    }

    @Nullable
    public ColorStateList getStrokeColor() {
        return this.strokeColor;
    }

    public void setStrokeWidth(@Dimension float strokeWidth) {
        if (this.strokeWidth != strokeWidth) {
            this.strokeWidth = strokeWidth;
            invalidate();
        }
    }

    public void setStrokeWidthResource(@DimenRes int strokeWidthResourceId) {
        setStrokeWidth(getResources().getDimensionPixelSize(strokeWidthResourceId));
    }

    @Dimension
    public float getStrokeWidth() {
        return this.strokeWidth;
    }

    public void setStrokeColor(@Nullable ColorStateList strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }

    @TargetApi(21)
    /* loaded from: classes.dex */
    class OutlineProvider extends ViewOutlineProvider {
        private final Rect rect = new Rect();

        OutlineProvider() {
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            if (ShapeableImageView.this.shapeAppearanceModel != null) {
                if (ShapeableImageView.this.shadowDrawable == null) {
                    ShapeableImageView.this.shadowDrawable = new MaterialShapeDrawable(ShapeableImageView.this.shapeAppearanceModel);
                }
                ShapeableImageView.this.destination.round(this.rect);
                ShapeableImageView.this.shadowDrawable.setBounds(this.rect);
                ShapeableImageView.this.shadowDrawable.getOutline(outline);
            }
        }
    }
}
