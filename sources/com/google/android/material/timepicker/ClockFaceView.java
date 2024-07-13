package com.google.android.material.timepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.timepicker.ClockHandView;
import java.util.Arrays;

/* loaded from: classes.dex */
class ClockFaceView extends RadialViewGroup implements ClockHandView.OnRotateListener {
    private static final float EPSILON = 0.001f;
    private static final int INITIAL_CAPACITY = 12;
    private static final String VALUE_PLACEHOLDER = "";
    private final int clockHandPadding;
    private final ClockHandView clockHandView;
    private final int clockSize;
    private float currentHandRotation;
    private final int[] gradientColors;
    private final float[] gradientPositions;
    private final int minimumHeight;
    private final int minimumWidth;
    private final RectF scratch;
    private final ColorStateList textColor;
    private final SparseArray<TextView> textViewPool;
    private final Rect textViewRect;
    private final AccessibilityDelegateCompat valueAccessibilityDelegate;
    private String[] values;

    public ClockFaceView(@NonNull Context context) {
        this(context, null);
    }

    public ClockFaceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.materialClockStyle);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public ClockFaceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.textViewRect = new Rect();
        this.scratch = new RectF();
        this.textViewPool = new SparseArray<>();
        this.gradientPositions = new float[]{0.0f, 0.9f, 1.0f};
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClockFaceView, defStyleAttr, R.style.Widget_MaterialComponents_TimePicker_Clock);
        Resources res = getResources();
        this.textColor = MaterialResources.getColorStateList(context, a, R.styleable.ClockFaceView_clockNumberTextColor);
        LayoutInflater.from(context).inflate(R.layout.material_clockface_view, (ViewGroup) this, true);
        this.clockHandView = (ClockHandView) findViewById(R.id.material_clock_hand);
        this.clockHandPadding = res.getDimensionPixelSize(R.dimen.material_clock_hand_padding);
        int clockHandTextColor = this.textColor.getColorForState(new int[]{16842913}, this.textColor.getDefaultColor());
        this.gradientColors = new int[]{clockHandTextColor, clockHandTextColor, this.textColor.getDefaultColor()};
        this.clockHandView.addOnRotateListener(this);
        int defaultBackgroundColor = AppCompatResources.getColorStateList(context, R.color.material_timepicker_clockface).getDefaultColor();
        ColorStateList backgroundColor = MaterialResources.getColorStateList(context, a, R.styleable.ClockFaceView_clockFaceBackgroundColor);
        setBackgroundColor(backgroundColor != null ? backgroundColor.getDefaultColor() : defaultBackgroundColor);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.material.timepicker.ClockFaceView.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                if (ClockFaceView.this.isShown()) {
                    ClockFaceView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    int circleRadius = ((ClockFaceView.this.getHeight() / 2) - ClockFaceView.this.clockHandView.getSelectorRadius()) - ClockFaceView.this.clockHandPadding;
                    ClockFaceView.this.setRadius(circleRadius);
                }
                return true;
            }
        });
        setFocusable(true);
        a.recycle();
        this.valueAccessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.google.android.material.timepicker.ClockFaceView.2
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View host, @NonNull AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                int index = ((Integer) host.getTag(R.id.material_value_index)).intValue();
                if (index > 0) {
                    info.setTraversalAfter((View) ClockFaceView.this.textViewPool.get(index - 1));
                }
                info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, index, 1, false, host.isSelected()));
            }
        };
        String[] initialValues = new String[12];
        Arrays.fill(initialValues, "");
        setValues(initialValues, 0);
        this.minimumHeight = res.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_height);
        this.minimumWidth = res.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_width);
        this.clockSize = res.getDimensionPixelSize(R.dimen.material_clock_size);
    }

    public void setValues(String[] values, @StringRes int contentDescription) {
        this.values = values;
        updateTextViews(contentDescription);
    }

    private void updateTextViews(@StringRes int contentDescription) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        int size = this.textViewPool.size();
        for (int i = 0; i < Math.max(this.values.length, size); i++) {
            TextView textView = this.textViewPool.get(i);
            if (i >= this.values.length) {
                removeView(textView);
                this.textViewPool.remove(i);
            } else {
                if (textView == null) {
                    textView = (TextView) inflater.inflate(R.layout.material_clockface_textview, (ViewGroup) this, false);
                    this.textViewPool.put(i, textView);
                    addView(textView);
                }
                textView.setVisibility(0);
                textView.setText(this.values[i]);
                textView.setTag(R.id.material_value_index, Integer.valueOf(i));
                ViewCompat.setAccessibilityDelegate(textView, this.valueAccessibilityDelegate);
                textView.setTextColor(this.textColor);
                if (contentDescription != 0) {
                    Resources res = getResources();
                    textView.setContentDescription(res.getString(contentDescription, this.values[i]));
                }
            }
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        infoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.values.length, false, 1));
    }

    @Override // com.google.android.material.timepicker.RadialViewGroup
    public void setRadius(int radius) {
        if (radius != getRadius()) {
            super.setRadius(radius);
            this.clockHandView.setCircleRadius(getRadius());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        findIntersectingTextView();
    }

    public void setHandRotation(@FloatRange(from = 0.0d, to = 360.0d) float rotation) {
        this.clockHandView.setHandRotation(rotation);
        findIntersectingTextView();
    }

    private void findIntersectingTextView() {
        RectF selectorBox = this.clockHandView.getCurrentSelectorBox();
        for (int i = 0; i < this.textViewPool.size(); i++) {
            TextView tv = this.textViewPool.get(i);
            if (tv != null) {
                tv.getDrawingRect(this.textViewRect);
                this.textViewRect.offset(tv.getPaddingLeft(), tv.getPaddingTop());
                offsetDescendantRectToMyCoords(tv, this.textViewRect);
                this.scratch.set(this.textViewRect);
                RadialGradient radialGradient = getGradientForTextView(selectorBox, this.scratch);
                tv.getPaint().setShader(radialGradient);
                tv.invalidate();
            }
        }
    }

    private RadialGradient getGradientForTextView(RectF selectorBox, RectF tvBox) {
        if (!RectF.intersects(selectorBox, tvBox)) {
            return null;
        }
        return new RadialGradient(selectorBox.centerX() - this.scratch.left, selectorBox.centerY() - this.scratch.top, selectorBox.width() * 0.5f, this.gradientColors, this.gradientPositions, Shader.TileMode.CLAMP);
    }

    @Override // com.google.android.material.timepicker.ClockHandView.OnRotateListener
    public void onRotate(float rotation, boolean animating) {
        if (Math.abs(this.currentHandRotation - rotation) > EPSILON) {
            this.currentHandRotation = rotation;
            findIntersectingTextView();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Resources r = getResources();
        DisplayMetrics displayMetrics = r.getDisplayMetrics();
        float height = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;
        int size = (int) (this.clockSize / max3(this.minimumHeight / height, this.minimumWidth / width, 1.0f));
        int spec = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
        setMeasuredDimension(size, size);
        super.onMeasure(spec, spec);
    }

    private static float max3(float a, float b, float c) {
        return Math.max(Math.max(a, b), c);
    }
}
