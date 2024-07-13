package com.google.android.material.timepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.timepicker.ClockHandView;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class TimePickerView extends ConstraintLayout implements TimePickerControls {
    private final ClockFaceView clockFace;
    private final ClockHandView clockHandView;
    private final Chip hourView;
    private final Chip minuteView;
    private OnDoubleTapListener onDoubleTapListener;
    private OnPeriodChangeListener onPeriodChangeListener;
    private OnSelectionChange onSelectionChangeListener;
    private final View.OnClickListener selectionListener;
    private final MaterialButtonToggleGroup toggle;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface OnDoubleTapListener {
        void onDoubleTap();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface OnPeriodChangeListener {
        void onPeriodChange(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface OnSelectionChange {
        void onSelectionChanged(int i);
    }

    public TimePickerView(Context context) {
        this(context, null);
    }

    public TimePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.selectionListener = new View.OnClickListener() { // from class: com.google.android.material.timepicker.TimePickerView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (TimePickerView.this.onSelectionChangeListener != null) {
                    TimePickerView.this.onSelectionChangeListener.onSelectionChanged(((Integer) v.getTag(R.id.selection_type)).intValue());
                }
            }
        };
        LayoutInflater.from(context).inflate(R.layout.material_timepicker, this);
        this.clockFace = (ClockFaceView) findViewById(R.id.material_clock_face);
        this.toggle = (MaterialButtonToggleGroup) findViewById(R.id.material_clock_period_toggle);
        this.toggle.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() { // from class: com.google.android.material.timepicker.TimePickerView.2
            @Override // com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                int period = checkedId == R.id.material_clock_period_pm_button ? 1 : 0;
                if (TimePickerView.this.onPeriodChangeListener != null && isChecked) {
                    TimePickerView.this.onPeriodChangeListener.onPeriodChange(period);
                }
            }
        });
        this.minuteView = (Chip) findViewById(R.id.material_minute_tv);
        this.hourView = (Chip) findViewById(R.id.material_hour_tv);
        this.clockHandView = (ClockHandView) findViewById(R.id.material_clock_hand);
        setupDoubleTap();
        setUpDisplay();
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void setupDoubleTap() {
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.google.android.material.timepicker.TimePickerView.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e) {
                boolean ret = super.onDoubleTap(e);
                if (TimePickerView.this.onDoubleTapListener != null) {
                    TimePickerView.this.onDoubleTapListener.onDoubleTap();
                }
                return ret;
            }
        });
        View.OnTouchListener onTouchListener = new View.OnTouchListener() { // from class: com.google.android.material.timepicker.TimePickerView.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                if (((Checkable) v).isChecked()) {
                    return gestureDetector.onTouchEvent(event);
                }
                return false;
            }
        };
        this.minuteView.setOnTouchListener(onTouchListener);
        this.hourView.setOnTouchListener(onTouchListener);
    }

    public void setMinuteHourDelegate(AccessibilityDelegateCompat clickActionDelegate) {
        ViewCompat.setAccessibilityDelegate(this.hourView, clickActionDelegate);
    }

    public void setHourClickDelegate(AccessibilityDelegateCompat clickActionDelegate) {
        ViewCompat.setAccessibilityDelegate(this.minuteView, clickActionDelegate);
    }

    private void setUpDisplay() {
        this.minuteView.setTag(R.id.selection_type, 12);
        this.hourView.setTag(R.id.selection_type, 10);
        this.minuteView.setOnClickListener(this.selectionListener);
        this.hourView.setOnClickListener(this.selectionListener);
    }

    @Override // com.google.android.material.timepicker.TimePickerControls
    public void setValues(String[] values, @StringRes int contentDescription) {
        this.clockFace.setValues(values, contentDescription);
    }

    @Override // com.google.android.material.timepicker.TimePickerControls
    public void setHandRotation(float rotation) {
        this.clockHandView.setHandRotation(rotation);
    }

    public void setHandRotation(float rotation, boolean animate) {
        this.clockHandView.setHandRotation(rotation, animate);
    }

    public void setAnimateOnTouchUp(boolean animating) {
        this.clockHandView.setAnimateOnTouchUp(animating);
    }

    @Override // com.google.android.material.timepicker.TimePickerControls
    @SuppressLint({"DefaultLocale"})
    public void updateTime(int period, int hourOfDay, int minute) {
        int checkedId = period == 1 ? R.id.material_clock_period_pm_button : R.id.material_clock_period_am_button;
        this.toggle.check(checkedId);
        Locale current = getResources().getConfiguration().locale;
        String minuteFormatted = String.format(current, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(minute));
        String hourFormatted = String.format(current, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(hourOfDay));
        this.minuteView.setText(minuteFormatted);
        this.hourView.setText(hourFormatted);
    }

    @Override // com.google.android.material.timepicker.TimePickerControls
    public void setActiveSelection(int selection) {
        this.minuteView.setChecked(selection == 12);
        this.hourView.setChecked(selection == 10);
    }

    public void addOnRotateListener(ClockHandView.OnRotateListener onRotateListener) {
        this.clockHandView.addOnRotateListener(onRotateListener);
    }

    public void setOnActionUpListener(ClockHandView.OnActionUpListener onActionUpListener) {
        this.clockHandView.setOnActionUpListener(onActionUpListener);
    }

    public void setOnPeriodChangeListener(OnPeriodChangeListener onPeriodChangeListener) {
        this.onPeriodChangeListener = onPeriodChangeListener;
    }

    public void setOnSelectionChangeListener(OnSelectionChange onSelectionChangeListener) {
        this.onSelectionChangeListener = onSelectionChangeListener;
    }

    public void setOnDoubleTapListener(@Nullable OnDoubleTapListener listener) {
        this.onDoubleTapListener = listener;
    }

    public void showToggle() {
        this.toggle.setVisibility(0);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this && visibility == 0) {
            updateToggleConstraints();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateToggleConstraints();
    }

    private void updateToggleConstraints() {
        if (this.toggle.getVisibility() == 0) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            boolean isLtr = ViewCompat.getLayoutDirection(this) == 0;
            int sideToClear = isLtr ? 2 : 1;
            constraintSet.clear(R.id.material_clock_display, sideToClear);
            constraintSet.applyTo(this);
        }
    }
}
