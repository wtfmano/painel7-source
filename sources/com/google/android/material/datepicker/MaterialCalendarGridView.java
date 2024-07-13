package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import java.util.Calendar;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class MaterialCalendarGridView extends GridView {
    private final Calendar dayCompute;
    private final boolean nestedScrollable;

    public MaterialCalendarGridView(Context context) {
        this(context, null);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dayCompute = UtcDates.getUtcCalendar();
        if (MaterialDatePicker.isFullscreen(getContext())) {
            setNextFocusLeftId(R.id.cancel_button);
            setNextFocusRightId(R.id.confirm_button);
        }
        this.nestedScrollable = MaterialDatePicker.isNestedScrollable(getContext());
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: com.google.android.material.datepicker.MaterialCalendarGridView.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo(null);
            }
        });
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getAdapter2().notifyDataSetChanged();
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    public void setSelection(int position) {
        if (position < getAdapter2().firstPositionInMonth()) {
            super.setSelection(getAdapter2().firstPositionInMonth());
        } else {
            super.setSelection(position);
        }
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = super.onKeyDown(keyCode, event);
        if (result) {
            if (getSelectedItemPosition() == -1 || getSelectedItemPosition() >= getAdapter2().firstPositionInMonth()) {
                return true;
            }
            if (19 == keyCode) {
                setSelection(getAdapter2().firstPositionInMonth());
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    @NonNull
    /* renamed from: getAdapter */
    public ListAdapter getAdapter2() {
        return (MonthAdapter) super.getAdapter();
    }

    @Override // android.widget.AdapterView
    public final void setAdapter(ListAdapter adapter) {
        if (!(adapter instanceof MonthAdapter)) {
            throw new IllegalArgumentException(String.format("%1$s must have its Adapter set to a %2$s", MaterialCalendarGridView.class.getCanonicalName(), MonthAdapter.class.getCanonicalName()));
        }
        super.setAdapter(adapter);
    }

    @Override // android.view.View
    protected final void onDraw(@NonNull Canvas canvas) {
        int firstHighlightPosition;
        int rangeHighlightStart;
        int lastHighlightPosition;
        int rangeHighlightEnd;
        int left;
        int right;
        super.onDraw(canvas);
        MonthAdapter monthAdapter = getAdapter2();
        DateSelector<?> dateSelector = monthAdapter.dateSelector;
        CalendarStyle calendarStyle = monthAdapter.calendarStyle;
        Long firstOfMonth = monthAdapter.getItem(monthAdapter.firstPositionInMonth());
        Long lastOfMonth = monthAdapter.getItem(monthAdapter.lastPositionInMonth());
        for (Pair<Long, Long> range : dateSelector.getSelectedRanges()) {
            if (range.first != null && range.second != null) {
                long startItem = range.first.longValue();
                long endItem = range.second.longValue();
                if (!skipMonth(firstOfMonth, lastOfMonth, Long.valueOf(startItem), Long.valueOf(endItem))) {
                    boolean isRtl = ViewUtils.isLayoutRtl(this);
                    if (startItem < firstOfMonth.longValue()) {
                        firstHighlightPosition = monthAdapter.firstPositionInMonth();
                        if (monthAdapter.isFirstInRow(firstHighlightPosition)) {
                            rangeHighlightStart = 0;
                        } else if (!isRtl) {
                            rangeHighlightStart = getChildAt(firstHighlightPosition - 1).getRight();
                        } else {
                            rangeHighlightStart = getChildAt(firstHighlightPosition - 1).getLeft();
                        }
                    } else {
                        this.dayCompute.setTimeInMillis(startItem);
                        firstHighlightPosition = monthAdapter.dayToPosition(this.dayCompute.get(5));
                        rangeHighlightStart = horizontalMidPoint(getChildAt(firstHighlightPosition));
                    }
                    if (endItem > lastOfMonth.longValue()) {
                        lastHighlightPosition = Math.min(monthAdapter.lastPositionInMonth(), getChildCount() - 1);
                        if (monthAdapter.isLastInRow(lastHighlightPosition)) {
                            rangeHighlightEnd = getWidth();
                        } else if (!isRtl) {
                            rangeHighlightEnd = getChildAt(lastHighlightPosition).getRight();
                        } else {
                            rangeHighlightEnd = getChildAt(lastHighlightPosition).getLeft();
                        }
                    } else {
                        this.dayCompute.setTimeInMillis(endItem);
                        lastHighlightPosition = monthAdapter.dayToPosition(this.dayCompute.get(5));
                        rangeHighlightEnd = horizontalMidPoint(getChildAt(lastHighlightPosition));
                    }
                    int firstRow = (int) monthAdapter.getItemId(firstHighlightPosition);
                    int lastRow = (int) monthAdapter.getItemId(lastHighlightPosition);
                    for (int row = firstRow; row <= lastRow; row++) {
                        int firstPositionInRow = row * getNumColumns();
                        int lastPositionInRow = (getNumColumns() + firstPositionInRow) - 1;
                        View firstView = getChildAt(firstPositionInRow);
                        int top = firstView.getTop() + calendarStyle.day.getTopInset();
                        int bottom = firstView.getBottom() - calendarStyle.day.getBottomInset();
                        if (!isRtl) {
                            left = firstPositionInRow > firstHighlightPosition ? 0 : rangeHighlightStart;
                            right = lastHighlightPosition > lastPositionInRow ? getWidth() : rangeHighlightEnd;
                        } else {
                            left = lastHighlightPosition > lastPositionInRow ? 0 : rangeHighlightEnd;
                            right = firstPositionInRow > firstHighlightPosition ? getWidth() : rangeHighlightStart;
                        }
                        canvas.drawRect(left, top, right, bottom, calendarStyle.rangeFill);
                    }
                }
            }
        }
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.nestedScrollable) {
            int expandSpec = View.MeasureSpec.makeMeasureSpec(ViewCompat.MEASURED_SIZE_MASK, Integer.MIN_VALUE);
            super.onMeasure(widthMeasureSpec, expandSpec);
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (gainFocus) {
            gainFocus(direction, previouslyFocusedRect);
        } else {
            super.onFocusChanged(false, direction, previouslyFocusedRect);
        }
    }

    private void gainFocus(int direction, Rect previouslyFocusedRect) {
        if (direction == 33) {
            setSelection(getAdapter2().lastPositionInMonth());
        } else if (direction == 130) {
            setSelection(getAdapter2().firstPositionInMonth());
        } else {
            super.onFocusChanged(true, direction, previouslyFocusedRect);
        }
    }

    private static boolean skipMonth(@Nullable Long firstOfMonth, @Nullable Long lastOfMonth, @Nullable Long startDay, @Nullable Long endDay) {
        return firstOfMonth == null || lastOfMonth == null || startDay == null || endDay == null || startDay.longValue() > lastOfMonth.longValue() || endDay.longValue() < firstOfMonth.longValue();
    }

    private static int horizontalMidPoint(@NonNull View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }
}
