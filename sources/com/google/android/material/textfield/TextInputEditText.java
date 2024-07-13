package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes.dex */
public class TextInputEditText extends AppCompatEditText {
    private final Rect parentRect;
    private boolean textInputLayoutFocusedRectEnabled;

    public TextInputEditText(@NonNull Context context) {
        this(context, null);
    }

    public TextInputEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public TextInputEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, 0), attrs, defStyleAttr);
        this.parentRect = new Rect();
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.TextInputEditText, defStyleAttr, R.style.Widget_Design_TextInputEditText, new int[0]);
        setTextInputLayoutFocusedRectEnabled(attributes.getBoolean(R.styleable.TextInputEditText_textInputLayoutFocusedRectEnabled, false));
        attributes.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout layout = getTextInputLayout();
        if (layout != null && layout.isProvidingHint() && super.getHint() == null && ManufacturerUtils.isMeizuDevice()) {
            setHint("");
        }
    }

    @Override // android.widget.TextView
    @Nullable
    public CharSequence getHint() {
        TextInputLayout layout = getTextInputLayout();
        return (layout == null || !layout.isProvidingHint()) ? super.getHint() : layout.getHint();
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.TextView, android.view.View
    @Nullable
    public InputConnection onCreateInputConnection(@NonNull EditorInfo outAttrs) {
        InputConnection ic = super.onCreateInputConnection(outAttrs);
        if (ic != null && outAttrs.hintText == null) {
            outAttrs.hintText = getHintFromLayout();
        }
        return ic;
    }

    @Nullable
    private TextInputLayout getTextInputLayout() {
        for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    @Nullable
    private CharSequence getHintFromLayout() {
        TextInputLayout layout = getTextInputLayout();
        if (layout != null) {
            return layout.getHint();
        }
        return null;
    }

    public void setTextInputLayoutFocusedRectEnabled(boolean textInputLayoutFocusedRectEnabled) {
        this.textInputLayoutFocusedRectEnabled = textInputLayoutFocusedRectEnabled;
    }

    public boolean isTextInputLayoutFocusedRectEnabled() {
        return this.textInputLayoutFocusedRectEnabled;
    }

    @Override // android.widget.TextView, android.view.View
    public void getFocusedRect(@Nullable Rect r) {
        super.getFocusedRect(r);
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout != null && this.textInputLayoutFocusedRectEnabled && r != null) {
            textInputLayout.getFocusedRect(this.parentRect);
            r.bottom = this.parentRect.bottom;
        }
    }

    @Override // android.view.View
    public boolean getGlobalVisibleRect(@Nullable Rect r, @Nullable Point globalOffset) {
        boolean result = super.getGlobalVisibleRect(r, globalOffset);
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout != null && this.textInputLayoutFocusedRectEnabled && r != null) {
            textInputLayout.getGlobalVisibleRect(this.parentRect, globalOffset);
            r.bottom = this.parentRect.bottom;
        }
        return result;
    }

    @Override // android.view.View
    public boolean requestRectangleOnScreen(@Nullable Rect rectangle) {
        boolean result = super.requestRectangleOnScreen(rectangle);
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout != null && this.textInputLayoutFocusedRectEnabled) {
            this.parentRect.set(0, textInputLayout.getHeight() - getResources().getDimensionPixelOffset(R.dimen.mtrl_edittext_rectangle_top_offset), textInputLayout.getWidth(), textInputLayout.getHeight());
            textInputLayout.requestRectangleOnScreen(this.parentRect, true);
        }
        return result;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        TextInputLayout layout = getTextInputLayout();
        if (Build.VERSION.SDK_INT < 23 && layout != null) {
            info.setText(getAccessibilityNodeInfoText(layout));
        }
    }

    @NonNull
    private String getAccessibilityNodeInfoText(@NonNull TextInputLayout layout) {
        CharSequence inputText = getText();
        CharSequence hintText = layout.getHint();
        boolean showingText = !TextUtils.isEmpty(inputText);
        boolean hasHint = !TextUtils.isEmpty(hintText);
        if (Build.VERSION.SDK_INT >= 17) {
            setLabelFor(R.id.textinput_helper_text);
        }
        String hint = hasHint ? hintText.toString() : "";
        if (showingText) {
            return ((Object) inputText) + (!TextUtils.isEmpty(hint) ? ", " + hint : "");
        }
        return TextUtils.isEmpty(hint) ? "" : hint;
    }
}
