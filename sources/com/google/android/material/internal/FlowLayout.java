package com.google.android.material.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class FlowLayout extends ViewGroup {
    private int itemSpacing;
    private int lineSpacing;
    private int rowCount;
    private boolean singleLine;

    public FlowLayout(@NonNull Context context) {
        this(context, null);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.singleLine = false;
        loadFromAttributes(context, attrs);
    }

    @TargetApi(21)
    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.singleLine = false;
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0);
        this.lineSpacing = array.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, 0);
        this.itemSpacing = array.getDimensionPixelSize(R.styleable.FlowLayout_itemSpacing, 0);
        array.recycle();
    }

    protected int getLineSpacing() {
        return this.lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    protected int getItemSpacing() {
        return this.itemSpacing;
    }

    public void setItemSpacing(int itemSpacing) {
        this.itemSpacing = itemSpacing;
    }

    public boolean isSingleLine() {
        return this.singleLine;
    }

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int maxWidth = (widthMode == Integer.MIN_VALUE || widthMode == 1073741824) ? width : Integer.MAX_VALUE;
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int childBottom = childTop;
        int maxChildRight = 0;
        int maxRight = maxWidth - getPaddingRight();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                ViewGroup.LayoutParams lp = child.getLayoutParams();
                int leftMargin = 0;
                int rightMargin = 0;
                if (lp instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLp = (ViewGroup.MarginLayoutParams) lp;
                    leftMargin = 0 + marginLp.leftMargin;
                    rightMargin = 0 + marginLp.rightMargin;
                }
                if (childLeft + leftMargin + child.getMeasuredWidth() > maxRight && !isSingleLine()) {
                    childLeft = getPaddingLeft();
                    childTop = childBottom + this.lineSpacing;
                }
                int childRight = childLeft + leftMargin + child.getMeasuredWidth();
                childBottom = childTop + child.getMeasuredHeight();
                if (childRight > maxChildRight) {
                    maxChildRight = childRight;
                }
                childLeft += leftMargin + rightMargin + child.getMeasuredWidth() + this.itemSpacing;
                if (i == getChildCount() - 1) {
                    maxChildRight += rightMargin;
                }
            }
        }
        int finalWidth = getMeasuredDimension(width, widthMode, maxChildRight + getPaddingRight());
        int finalHeight = getMeasuredDimension(height, heightMode, childBottom + getPaddingBottom());
        setMeasuredDimension(finalWidth, finalHeight);
    }

    private static int getMeasuredDimension(int size, int mode, int childrenEdge) {
        switch (mode) {
            case Integer.MIN_VALUE:
                return Math.min(childrenEdge, size);
            case 1073741824:
                return size;
            default:
                return childrenEdge;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean sizeChanged, int left, int top, int right, int bottom) {
        if (getChildCount() == 0) {
            this.rowCount = 0;
            return;
        }
        this.rowCount = 1;
        boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
        int paddingStart = isRtl ? getPaddingRight() : getPaddingLeft();
        int paddingEnd = isRtl ? getPaddingLeft() : getPaddingRight();
        int childStart = paddingStart;
        int childTop = getPaddingTop();
        int childBottom = childTop;
        int maxChildEnd = (right - left) - paddingEnd;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == 8) {
                child.setTag(R.id.row_index_key, -1);
            } else {
                ViewGroup.LayoutParams lp = child.getLayoutParams();
                int startMargin = 0;
                int endMargin = 0;
                if (lp instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLp = (ViewGroup.MarginLayoutParams) lp;
                    startMargin = MarginLayoutParamsCompat.getMarginStart(marginLp);
                    endMargin = MarginLayoutParamsCompat.getMarginEnd(marginLp);
                }
                int childEnd = childStart + startMargin + child.getMeasuredWidth();
                if (!this.singleLine && childEnd > maxChildEnd) {
                    childStart = paddingStart;
                    childTop = childBottom + this.lineSpacing;
                    this.rowCount++;
                }
                child.setTag(R.id.row_index_key, Integer.valueOf(this.rowCount - 1));
                int childEnd2 = childStart + startMargin + child.getMeasuredWidth();
                childBottom = childTop + child.getMeasuredHeight();
                if (isRtl) {
                    child.layout(maxChildEnd - childEnd2, childTop, (maxChildEnd - childStart) - startMargin, childBottom);
                } else {
                    child.layout(childStart + startMargin, childTop, childEnd2, childBottom);
                }
                childStart += startMargin + endMargin + child.getMeasuredWidth() + this.itemSpacing;
            }
        }
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public int getRowIndex(@NonNull View child) {
        Object index = child.getTag(R.id.row_index_key);
        if (index instanceof Integer) {
            return ((Integer) index).intValue();
        }
        return -1;
    }
}
