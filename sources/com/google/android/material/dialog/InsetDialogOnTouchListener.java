package com.google.android.material.dialog;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class InsetDialogOnTouchListener implements View.OnTouchListener {
    @NonNull
    private final Dialog dialog;
    private final int leftInset;
    private final int prePieSlop;
    private final int topInset;

    public InsetDialogOnTouchListener(@NonNull Dialog dialog, @NonNull Rect insets) {
        this.dialog = dialog;
        this.leftInset = insets.left;
        this.topInset = insets.top;
        this.prePieSlop = ViewConfiguration.get(dialog.getContext()).getScaledWindowTouchSlop();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(@NonNull View view, @NonNull MotionEvent event) {
        View insetView = view.findViewById(16908290);
        int insetLeft = this.leftInset + insetView.getLeft();
        int insetRight = insetLeft + insetView.getWidth();
        int insetTop = this.topInset + insetView.getTop();
        int insetBottom = insetTop + insetView.getHeight();
        RectF dialogWindow = new RectF(insetLeft, insetTop, insetRight, insetBottom);
        if (dialogWindow.contains(event.getX(), event.getY())) {
            return false;
        }
        MotionEvent outsideEvent = MotionEvent.obtain(event);
        if (event.getAction() == 1) {
            outsideEvent.setAction(4);
        }
        if (Build.VERSION.SDK_INT < 28) {
            outsideEvent.setAction(0);
            outsideEvent.setLocation((-this.prePieSlop) - 1, (-this.prePieSlop) - 1);
        }
        view.performClick();
        return this.dialog.onTouchEvent(outsideEvent);
    }
}
