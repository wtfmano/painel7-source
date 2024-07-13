package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SnackbarContentLayout extends LinearLayout implements ContentViewCallback {
    private Button actionView;
    private int maxInlineActionWidth;
    private int maxWidth;
    private TextView messageView;

    public SnackbarContentLayout(@NonNull Context context) {
        this(context, null);
    }

    public SnackbarContentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
        this.maxWidth = a.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
        this.maxInlineActionWidth = a.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
        a.recycle();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.messageView = (TextView) findViewById(R.id.snackbar_text);
        this.actionView = (Button) findViewById(R.id.snackbar_action);
    }

    public TextView getMessageView() {
        return this.messageView;
    }

    public Button getActionView() {
        return this.actionView;
    }

    public void updateActionTextColorAlphaIfNeeded(float actionTextColorAlpha) {
        if (actionTextColorAlpha != 1.0f) {
            int originalActionTextColor = this.actionView.getCurrentTextColor();
            int colorSurface = MaterialColors.getColor(this, R.attr.colorSurface);
            int actionTextColor = MaterialColors.layer(colorSurface, originalActionTextColor, actionTextColorAlpha);
            this.actionView.setTextColor(actionTextColor);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.maxWidth > 0 && getMeasuredWidth() > this.maxWidth) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.maxWidth, 1073741824);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        int multiLineVPadding = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
        int singleLineVPadding = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);
        boolean isMultiLine = this.messageView.getLayout().getLineCount() > 1;
        boolean remeasure = false;
        if (isMultiLine && this.maxInlineActionWidth > 0 && this.actionView.getMeasuredWidth() > this.maxInlineActionWidth) {
            if (updateViewsWithinLayout(1, multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
                remeasure = true;
            }
        } else {
            int messagePadding = isMultiLine ? multiLineVPadding : singleLineVPadding;
            if (updateViewsWithinLayout(0, messagePadding, messagePadding)) {
                remeasure = true;
            }
        }
        if (remeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean updateViewsWithinLayout(int orientation, int messagePadTop, int messagePadBottom) {
        boolean changed = false;
        if (orientation != getOrientation()) {
            setOrientation(orientation);
            changed = true;
        }
        if (this.messageView.getPaddingTop() != messagePadTop || this.messageView.getPaddingBottom() != messagePadBottom) {
            updateTopBottomPadding(this.messageView, messagePadTop, messagePadBottom);
            return true;
        }
        return changed;
    }

    private static void updateTopBottomPadding(@NonNull View view, int topPadding, int bottomPadding) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), topPadding, ViewCompat.getPaddingEnd(view), bottomPadding);
        } else {
            view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), bottomPadding);
        }
    }

    @Override // com.google.android.material.snackbar.ContentViewCallback
    public void animateContentIn(int delay, int duration) {
        this.messageView.setAlpha(0.0f);
        this.messageView.animate().alpha(1.0f).setDuration(duration).setStartDelay(delay).start();
        if (this.actionView.getVisibility() == 0) {
            this.actionView.setAlpha(0.0f);
            this.actionView.animate().alpha(1.0f).setDuration(duration).setStartDelay(delay).start();
        }
    }

    @Override // com.google.android.material.snackbar.ContentViewCallback
    public void animateContentOut(int delay, int duration) {
        this.messageView.setAlpha(1.0f);
        this.messageView.animate().alpha(0.0f).setDuration(duration).setStartDelay(delay).start();
        if (this.actionView.getVisibility() == 0) {
            this.actionView.setAlpha(1.0f);
            this.actionView.animate().alpha(0.0f).setDuration(duration).setStartDelay(delay).start();
        }
    }

    public void setMaxInlineActionWidth(int width) {
        this.maxInlineActionWidth = width;
    }
}
