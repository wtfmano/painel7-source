package com.google.android.material.chip;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.AnimatorRes;
import androidx.annotation.BoolRes;
import androidx.annotation.CallSuper;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/* loaded from: classes.dex */
public class Chip extends AppCompatCheckBox implements ChipDrawable.Delegate, Shapeable {
    private static final String BUTTON_ACCESSIBILITY_CLASS_NAME = "android.widget.Button";
    private static final int CHIP_BODY_VIRTUAL_ID = 0;
    private static final int CLOSE_ICON_VIRTUAL_ID = 1;
    private static final String COMPOUND_BUTTON_ACCESSIBILITY_CLASS_NAME = "android.widget.CompoundButton";
    private static final String GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME = "android.view.View";
    private static final int MIN_TOUCH_TARGET_DP = 48;
    private static final String NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
    private static final String TAG = "Chip";
    @Nullable
    private ChipDrawable chipDrawable;
    private boolean closeIconFocused;
    private boolean closeIconHovered;
    private boolean closeIconPressed;
    private boolean deferredCheckedValue;
    private boolean ensureMinTouchTargetSize;
    private final TextAppearanceFontCallback fontCallback;
    @Nullable
    private InsetDrawable insetBackgroundDrawable;
    private int lastLayoutDirection;
    @Dimension(unit = 1)
    private int minTouchTargetSize;
    @Nullable
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListenerInternal;
    @Nullable
    private View.OnClickListener onCloseIconClickListener;
    private final Rect rect;
    private final RectF rectF;
    @Nullable
    private RippleDrawable ripple;
    @NonNull
    private final ChipTouchHelper touchHelper;
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Chip_Action;
    private static final Rect EMPTY_BOUNDS = new Rect();
    private static final int[] SELECTED_STATE = {16842913};
    private static final int[] CHECKABLE_STATE_SET = {16842911};

    public Chip(Context context) {
        this(context, null);
    }

    public Chip(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.chipStyle);
    }

    public Chip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.rect = new Rect();
        this.rectF = new RectF();
        this.fontCallback = new TextAppearanceFontCallback() { // from class: com.google.android.material.chip.Chip.1
            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public void onFontRetrieved(@NonNull Typeface typeface, boolean fontResolvedSynchronously) {
                CharSequence text;
                Chip chip = Chip.this;
                if (Chip.this.chipDrawable.shouldDrawText()) {
                    text = Chip.this.chipDrawable.getText();
                } else {
                    text = Chip.this.getText();
                }
                chip.setText(text);
                Chip.this.requestLayout();
                Chip.this.invalidate();
            }

            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public void onFontRetrievalFailed(int reason) {
            }
        };
        Context context2 = getContext();
        validateAttributes(attrs);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(context2, attrs, defStyleAttr, DEF_STYLE_RES);
        initMinTouchTarget(context2, attrs, defStyleAttr);
        setChipDrawable(drawable);
        drawable.setElevation(ViewCompat.getElevation(this));
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.Chip, defStyleAttr, DEF_STYLE_RES, new int[0]);
        if (Build.VERSION.SDK_INT < 23) {
            setTextColor(MaterialResources.getColorStateList(context2, a, R.styleable.Chip_android_textColor));
        }
        boolean hasShapeAppearanceAttribute = a.hasValue(R.styleable.Chip_shapeAppearance);
        a.recycle();
        this.touchHelper = new ChipTouchHelper(this);
        updateAccessibilityDelegate();
        if (!hasShapeAppearanceAttribute) {
            initOutlineProvider();
        }
        setChecked(this.deferredCheckedValue);
        setText(drawable.getText());
        setEllipsize(drawable.getEllipsize());
        updateTextPaintDrawState();
        if (!this.chipDrawable.shouldDrawText()) {
            setLines(1);
            setHorizontallyScrolling(true);
        }
        setGravity(8388627);
        updatePaddingInternal();
        if (shouldEnsureMinTouchTargetSize()) {
            setMinHeight(this.minTouchTargetSize);
        }
        this.lastLayoutDirection = ViewCompat.getLayoutDirection(this);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.chipDrawable);
    }

    @Override // android.view.View
    @RequiresApi(21)
    public void setElevation(float elevation) {
        super.setElevation(elevation);
        if (this.chipDrawable != null) {
            this.chipDrawable.setElevation(elevation);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (isCheckable() || isClickable()) {
            info.setClassName(isCheckable() ? COMPOUND_BUTTON_ACCESSIBILITY_CLASS_NAME : BUTTON_ACCESSIBILITY_CLASS_NAME);
        } else {
            info.setClassName(GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME);
        }
        info.setCheckable(isCheckable());
        info.setClickable(isClickable());
        if (getParent() instanceof ChipGroup) {
            ChipGroup chipGroup = (ChipGroup) getParent();
            AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
            int columnIndex = chipGroup.isSingleLine() ? chipGroup.getIndexOfChip(this) : -1;
            infoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(chipGroup.getRowIndex(this), 1, columnIndex, 1, false, isChecked()));
        }
    }

    private void updateAccessibilityDelegate() {
        if (hasCloseIcon() && isCloseIconVisible() && this.onCloseIconClickListener != null) {
            ViewCompat.setAccessibilityDelegate(this, this.touchHelper);
        } else {
            ViewCompat.setAccessibilityDelegate(this, null);
        }
    }

    private void initMinTouchTarget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.Chip, defStyleAttr, DEF_STYLE_RES, new int[0]);
        this.ensureMinTouchTargetSize = a.getBoolean(R.styleable.Chip_ensureMinTouchTargetSize, false);
        float defaultMinTouchTargetSize = (float) Math.ceil(ViewUtils.dpToPx(getContext(), 48));
        this.minTouchTargetSize = (int) Math.ceil(a.getDimension(R.styleable.Chip_chipMinTouchTargetSize, defaultMinTouchTargetSize));
        a.recycle();
    }

    private void updatePaddingInternal() {
        if (!TextUtils.isEmpty(getText()) && this.chipDrawable != null) {
            int paddingEnd = (int) (this.chipDrawable.getChipEndPadding() + this.chipDrawable.getTextEndPadding() + this.chipDrawable.calculateCloseIconWidth());
            int paddingStart = (int) (this.chipDrawable.getChipStartPadding() + this.chipDrawable.getTextStartPadding() + this.chipDrawable.calculateChipIconWidth());
            if (this.insetBackgroundDrawable != null) {
                Rect padding = new Rect();
                this.insetBackgroundDrawable.getPadding(padding);
                paddingStart += padding.left;
                paddingEnd += padding.right;
            }
            ViewCompat.setPaddingRelative(this, paddingStart, getPaddingTop(), paddingEnd, getPaddingBottom());
        }
    }

    @Override // android.widget.TextView, android.view.View
    @TargetApi(17)
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        if (this.lastLayoutDirection != layoutDirection) {
            this.lastLayoutDirection = layoutDirection;
            updatePaddingInternal();
        }
    }

    private void validateAttributes(@Nullable AttributeSet attributeSet) {
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "background") != null) {
                Log.w(TAG, "Do not set the background; Chip manages its own background drawable.");
            }
            if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableLeft") != null) {
                throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
            }
            if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableStart") != null) {
                throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
            }
            if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableEnd") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            }
            if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableRight") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            }
            if (!attributeSet.getAttributeBooleanValue(NAMESPACE_ANDROID, "singleLine", true) || attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "lines", 1) != 1 || attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "minLines", 1) != 1 || attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "maxLines", 1) != 1) {
                throw new UnsupportedOperationException("Chip does not support multi-line text");
            }
            if (attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "gravity", 8388627) != 8388627) {
                Log.w(TAG, "Chip text must be vertically center and start aligned");
            }
        }
    }

    private void initOutlineProvider() {
        if (Build.VERSION.SDK_INT >= 21) {
            setOutlineProvider(new ViewOutlineProvider() { // from class: com.google.android.material.chip.Chip.2
                @Override // android.view.ViewOutlineProvider
                @TargetApi(21)
                public void getOutline(View view, @NonNull Outline outline) {
                    if (Chip.this.chipDrawable != null) {
                        Chip.this.chipDrawable.getOutline(outline);
                    } else {
                        outline.setAlpha(0.0f);
                    }
                }
            });
        }
    }

    public Drawable getChipDrawable() {
        return this.chipDrawable;
    }

    public void setChipDrawable(@NonNull ChipDrawable drawable) {
        if (this.chipDrawable != drawable) {
            unapplyChipDrawable(this.chipDrawable);
            this.chipDrawable = drawable;
            this.chipDrawable.setShouldDrawText(false);
            applyChipDrawable(this.chipDrawable);
            ensureAccessibleTouchTarget(this.minTouchTargetSize);
        }
    }

    private void updateBackgroundDrawable() {
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            updateFrameworkRippleBackground();
            return;
        }
        this.chipDrawable.setUseCompatRipple(true);
        ViewCompat.setBackground(this, getBackgroundDrawable());
        updatePaddingInternal();
        ensureChipDrawableHasCallback();
    }

    private void ensureChipDrawableHasCallback() {
        if (getBackgroundDrawable() == this.insetBackgroundDrawable && this.chipDrawable.getCallback() == null) {
            this.chipDrawable.setCallback(this.insetBackgroundDrawable);
        }
    }

    @Nullable
    public Drawable getBackgroundDrawable() {
        return this.insetBackgroundDrawable == null ? this.chipDrawable : this.insetBackgroundDrawable;
    }

    private void updateFrameworkRippleBackground() {
        this.ripple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.chipDrawable.getRippleColor()), getBackgroundDrawable(), null);
        this.chipDrawable.setUseCompatRipple(false);
        ViewCompat.setBackground(this, this.ripple);
        updatePaddingInternal();
    }

    private void unapplyChipDrawable(@Nullable ChipDrawable chipDrawable) {
        if (chipDrawable != null) {
            chipDrawable.setDelegate(null);
        }
    }

    private void applyChipDrawable(@NonNull ChipDrawable chipDrawable) {
        chipDrawable.setDelegate(this);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] state = super.onCreateDrawableState(extraSpace + 2);
        if (isChecked()) {
            mergeDrawableStates(state, SELECTED_STATE);
        }
        if (isCheckable()) {
            mergeDrawableStates(state, CHECKABLE_STATE_SET);
        }
        return state;
    }

    @Override // android.widget.TextView
    public void setGravity(int gravity) {
        if (gravity != 8388627) {
            Log.w(TAG, "Chip text must be vertically center and start aligned");
        } else {
            super.setGravity(gravity);
        }
    }

    @Override // android.view.View
    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        Log.w(TAG, "Do not set the background tint list; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        Log.w(TAG, "Do not set the background tint mode; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public void setBackgroundColor(int color) {
        Log.w(TAG, "Do not set the background color; Chip manages its own background drawable.");
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public void setBackgroundResource(int resid) {
        Log.w(TAG, "Do not set the background resource; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public void setBackground(Drawable background) {
        if (background != getBackgroundDrawable() && background != this.ripple) {
            Log.w(TAG, "Do not set the background; Chip manages its own background drawable.");
        } else {
            super.setBackground(background);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public void setBackgroundDrawable(Drawable background) {
        if (background != getBackgroundDrawable() && background != this.ripple) {
            Log.w(TAG, "Do not set the background drawable; Chip manages its own background drawable.");
        } else {
            super.setBackgroundDrawable(background);
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (left != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (right != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        if (left != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (right != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (left != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        }
        if (right != null) {
            throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelative(@Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        if (start != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (end != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelative(start, top, end, bottom);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        if (start != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (end != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        if (start != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (end != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    @Override // android.widget.TextView
    @Nullable
    public TextUtils.TruncateAt getEllipsize() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getEllipsize();
        }
        return null;
    }

    @Override // android.widget.TextView
    public void setEllipsize(TextUtils.TruncateAt where) {
        if (this.chipDrawable != null) {
            if (where == TextUtils.TruncateAt.MARQUEE) {
                throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
            }
            super.setEllipsize(where);
            if (this.chipDrawable != null) {
                this.chipDrawable.setEllipsize(where);
            }
        }
    }

    @Override // android.widget.TextView
    public void setSingleLine(boolean singleLine) {
        if (!singleLine) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setSingleLine(singleLine);
    }

    @Override // android.widget.TextView
    public void setLines(int lines) {
        if (lines > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setLines(lines);
    }

    @Override // android.widget.TextView
    public void setMinLines(int minLines) {
        if (minLines > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMinLines(minLines);
    }

    @Override // android.widget.TextView
    public void setMaxLines(int maxLines) {
        if (maxLines > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMaxLines(maxLines);
    }

    @Override // android.widget.TextView
    public void setMaxWidth(@Px int maxWidth) {
        super.setMaxWidth(maxWidth);
        if (this.chipDrawable != null) {
            this.chipDrawable.setMaxWidth(maxWidth);
        }
    }

    @Override // com.google.android.material.chip.ChipDrawable.Delegate
    public void onChipDrawableSizeChange() {
        ensureAccessibleTouchTarget(this.minTouchTargetSize);
        requestLayout();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean checked) {
        if (this.chipDrawable == null) {
            this.deferredCheckedValue = checked;
        } else if (this.chipDrawable.isCheckable()) {
            boolean wasChecked = isChecked();
            super.setChecked(checked);
            if (wasChecked != checked && this.onCheckedChangeListenerInternal != null) {
                this.onCheckedChangeListenerInternal.onCheckedChanged(this, checked);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOnCheckedChangeListenerInternal(CompoundButton.OnCheckedChangeListener listener) {
        this.onCheckedChangeListenerInternal = listener;
    }

    public void setOnCloseIconClickListener(View.OnClickListener listener) {
        this.onCloseIconClickListener = listener;
        updateAccessibilityDelegate();
    }

    @CallSuper
    public boolean performCloseIconClick() {
        boolean result;
        playSoundEffect(0);
        if (this.onCloseIconClickListener != null) {
            this.onCloseIconClickListener.onClick(this);
            result = true;
        } else {
            result = false;
        }
        this.touchHelper.sendEventForVirtualView(1, 1);
        return result;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.widget.TextView, android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        boolean handled = false;
        int action = event.getActionMasked();
        boolean eventInCloseIcon = getCloseIconTouchBounds().contains(event.getX(), event.getY());
        switch (action) {
            case 0:
                if (eventInCloseIcon) {
                    setCloseIconPressed(true);
                    handled = true;
                    break;
                }
                break;
            case 1:
                if (this.closeIconPressed) {
                    performCloseIconClick();
                    handled = true;
                }
                setCloseIconPressed(false);
                break;
            case 2:
                if (this.closeIconPressed) {
                    if (!eventInCloseIcon) {
                        setCloseIconPressed(false);
                    }
                    handled = true;
                    break;
                }
                break;
            case 3:
                setCloseIconPressed(false);
                break;
        }
        return handled || super.onTouchEvent(event);
    }

    @Override // android.view.View
    public boolean onHoverEvent(@NonNull MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case 7:
                setCloseIconHovered(getCloseIconTouchBounds().contains(event.getX(), event.getY()));
                break;
            case 10:
                setCloseIconHovered(false);
                break;
        }
        return super.onHoverEvent(event);
    }

    @SuppressLint({"PrivateApi"})
    private boolean handleAccessibilityExit(@NonNull MotionEvent event) {
        if (event.getAction() == 10) {
            try {
                Field f = ExploreByTouchHelper.class.getDeclaredField("mHoveredVirtualViewId");
                f.setAccessible(true);
                int mHoveredVirtualViewId = ((Integer) f.get(this.touchHelper)).intValue();
                if (mHoveredVirtualViewId != Integer.MIN_VALUE) {
                    Method m = ExploreByTouchHelper.class.getDeclaredMethod("updateHoveredVirtualView", Integer.TYPE);
                    m.setAccessible(true);
                    m.invoke(this.touchHelper, Integer.MIN_VALUE);
                    return true;
                }
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Unable to send Accessibility Exit event", e);
            } catch (NoSuchFieldException e2) {
                Log.e(TAG, "Unable to send Accessibility Exit event", e2);
            } catch (NoSuchMethodException e3) {
                Log.e(TAG, "Unable to send Accessibility Exit event", e3);
            } catch (InvocationTargetException e4) {
                Log.e(TAG, "Unable to send Accessibility Exit event", e4);
            }
        }
        return false;
    }

    @Override // android.view.View
    protected boolean dispatchHoverEvent(@NonNull MotionEvent event) {
        return handleAccessibilityExit(event) || this.touchHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = this.touchHelper.dispatchKeyEvent(event);
        if (!handled || this.touchHelper.getKeyboardFocusedVirtualViewId() == Integer.MIN_VALUE) {
            return super.dispatchKeyEvent(event);
        }
        return true;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        this.touchHelper.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override // android.widget.TextView, android.view.View
    public void getFocusedRect(@NonNull Rect r) {
        if (this.touchHelper.getKeyboardFocusedVirtualViewId() == 1 || this.touchHelper.getAccessibilityFocusedVirtualViewId() == 1) {
            r.set(getCloseIconTouchBoundsInt());
        } else {
            super.getFocusedRect(r);
        }
    }

    private void setCloseIconPressed(boolean pressed) {
        if (this.closeIconPressed != pressed) {
            this.closeIconPressed = pressed;
            refreshDrawableState();
        }
    }

    private void setCloseIconHovered(boolean hovered) {
        if (this.closeIconHovered != hovered) {
            this.closeIconHovered = hovered;
            refreshDrawableState();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        boolean changed = false;
        if (this.chipDrawable != null && this.chipDrawable.isCloseIconStateful()) {
            changed = this.chipDrawable.setCloseIconState(createCloseIconDrawableState());
        }
        if (changed) {
            invalidate();
        }
    }

    @NonNull
    private int[] createCloseIconDrawableState() {
        int count = 0;
        if (isEnabled()) {
            count = 0 + 1;
        }
        if (this.closeIconFocused) {
            count++;
        }
        if (this.closeIconHovered) {
            count++;
        }
        if (this.closeIconPressed) {
            count++;
        }
        if (isChecked()) {
            count++;
        }
        int[] stateSet = new int[count];
        int i = 0;
        if (isEnabled()) {
            stateSet[0] = 16842910;
            i = 0 + 1;
        }
        if (this.closeIconFocused) {
            stateSet[i] = 16842908;
            i++;
        }
        if (this.closeIconHovered) {
            stateSet[i] = 16843623;
            i++;
        }
        if (this.closeIconPressed) {
            stateSet[i] = 16842919;
            i++;
        }
        if (isChecked()) {
            stateSet[i] = 16842913;
            int i2 = i + 1;
        }
        return stateSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasCloseIcon() {
        return (this.chipDrawable == null || this.chipDrawable.getCloseIcon() == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public RectF getCloseIconTouchBounds() {
        this.rectF.setEmpty();
        if (hasCloseIcon() && this.onCloseIconClickListener != null) {
            this.chipDrawable.getCloseIconTouchBounds(this.rectF);
        }
        return this.rectF;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public Rect getCloseIconTouchBoundsInt() {
        RectF bounds = getCloseIconTouchBounds();
        this.rect.set((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom);
        return this.rect;
    }

    @Override // android.widget.Button, android.widget.TextView, android.view.View
    @Nullable
    @TargetApi(24)
    public PointerIcon onResolvePointerIcon(@NonNull MotionEvent event, int pointerIndex) {
        if (getCloseIconTouchBounds().contains(event.getX(), event.getY()) && isEnabled()) {
            return PointerIcon.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ChipTouchHelper extends ExploreByTouchHelper {
        ChipTouchHelper(Chip view) {
            super(view);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float x, float y) {
            return (Chip.this.hasCloseIcon() && Chip.this.getCloseIconTouchBounds().contains(x, y)) ? 1 : 0;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(@NonNull List<Integer> virtualViewIds) {
            virtualViewIds.add(0);
            if (Chip.this.hasCloseIcon() && Chip.this.isCloseIconVisible() && Chip.this.onCloseIconClickListener != null) {
                virtualViewIds.add(1);
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onVirtualViewKeyboardFocusChanged(int virtualViewId, boolean hasFocus) {
            if (virtualViewId == 1) {
                Chip.this.closeIconFocused = hasFocus;
                Chip.this.refreshDrawableState();
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int virtualViewId, @NonNull AccessibilityNodeInfoCompat node) {
            if (virtualViewId == 1) {
                CharSequence closeIconContentDescription = Chip.this.getCloseIconContentDescription();
                if (closeIconContentDescription != null) {
                    node.setContentDescription(closeIconContentDescription);
                } else {
                    CharSequence chipText = Chip.this.getText();
                    Context context = Chip.this.getContext();
                    int i = R.string.mtrl_chip_close_icon_content_description;
                    Object[] objArr = new Object[1];
                    if (TextUtils.isEmpty(chipText)) {
                        chipText = "";
                    }
                    objArr[0] = chipText;
                    node.setContentDescription(context.getString(i, objArr).trim());
                }
                node.setBoundsInParent(Chip.this.getCloseIconTouchBoundsInt());
                node.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                node.setEnabled(Chip.this.isEnabled());
                return;
            }
            node.setContentDescription("");
            node.setBoundsInParent(Chip.EMPTY_BOUNDS);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForHost(@NonNull AccessibilityNodeInfoCompat node) {
            node.setCheckable(Chip.this.isCheckable());
            node.setClickable(Chip.this.isClickable());
            if (Chip.this.isCheckable() || Chip.this.isClickable()) {
                node.setClassName(Chip.this.isCheckable() ? Chip.COMPOUND_BUTTON_ACCESSIBILITY_CLASS_NAME : Chip.BUTTON_ACCESSIBILITY_CLASS_NAME);
            } else {
                node.setClassName(Chip.GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME);
            }
            CharSequence chipText = Chip.this.getText();
            if (Build.VERSION.SDK_INT >= 23) {
                node.setText(chipText);
            } else {
                node.setContentDescription(chipText);
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (action == 16) {
                if (virtualViewId == 0) {
                    return Chip.this.performClick();
                }
                if (virtualViewId == 1) {
                    return Chip.this.performCloseIconClick();
                }
            }
            return false;
        }
    }

    @Nullable
    public ColorStateList getChipBackgroundColor() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getChipBackgroundColor();
        }
        return null;
    }

    public void setChipBackgroundColorResource(@ColorRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipBackgroundColorResource(id);
        }
    }

    public void setChipBackgroundColor(@Nullable ColorStateList chipBackgroundColor) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipBackgroundColor(chipBackgroundColor);
        }
    }

    public float getChipMinHeight() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getChipMinHeight();
        }
        return 0.0f;
    }

    public void setChipMinHeightResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipMinHeightResource(id);
        }
    }

    public void setChipMinHeight(float minHeight) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipMinHeight(minHeight);
        }
    }

    public float getChipCornerRadius() {
        if (this.chipDrawable != null) {
            return Math.max(0.0f, this.chipDrawable.getChipCornerRadius());
        }
        return 0.0f;
    }

    @Deprecated
    public void setChipCornerRadiusResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipCornerRadiusResource(id);
        }
    }

    @Override // com.google.android.material.shape.Shapeable
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.chipDrawable.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @Override // com.google.android.material.shape.Shapeable
    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.chipDrawable.getShapeAppearanceModel();
    }

    @Deprecated
    public void setChipCornerRadius(float chipCornerRadius) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipCornerRadius(chipCornerRadius);
        }
    }

    @Nullable
    public ColorStateList getChipStrokeColor() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getChipStrokeColor();
        }
        return null;
    }

    public void setChipStrokeColorResource(@ColorRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipStrokeColorResource(id);
        }
    }

    public void setChipStrokeColor(@Nullable ColorStateList chipStrokeColor) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipStrokeColor(chipStrokeColor);
        }
    }

    public float getChipStrokeWidth() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getChipStrokeWidth();
        }
        return 0.0f;
    }

    public void setChipStrokeWidthResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipStrokeWidthResource(id);
        }
    }

    public void setChipStrokeWidth(float chipStrokeWidth) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipStrokeWidth(chipStrokeWidth);
        }
    }

    @Nullable
    public ColorStateList getRippleColor() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getRippleColor();
        }
        return null;
    }

    public void setRippleColorResource(@ColorRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setRippleColorResource(id);
            if (!this.chipDrawable.getUseCompatRipple()) {
                updateFrameworkRippleBackground();
            }
        }
    }

    public void setRippleColor(@Nullable ColorStateList rippleColor) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setRippleColor(rippleColor);
        }
        if (!this.chipDrawable.getUseCompatRipple()) {
            updateFrameworkRippleBackground();
        }
    }

    @Deprecated
    public CharSequence getChipText() {
        return getText();
    }

    @Override // android.view.View
    public void setLayoutDirection(int layoutDirection) {
        if (this.chipDrawable != null && Build.VERSION.SDK_INT >= 17) {
            super.setLayoutDirection(layoutDirection);
        }
    }

    @Override // android.widget.TextView
    public void setText(CharSequence text, TextView.BufferType type) {
        if (this.chipDrawable != null) {
            if (text == null) {
                text = "";
            }
            super.setText(this.chipDrawable.shouldDrawText() ? null : text, type);
            if (this.chipDrawable != null) {
                this.chipDrawable.setText(text);
            }
        }
    }

    @Deprecated
    public void setChipTextResource(@StringRes int id) {
        setText(getResources().getString(id));
    }

    @Deprecated
    public void setChipText(@Nullable CharSequence chipText) {
        setText(chipText);
    }

    public void setTextAppearanceResource(@StyleRes int id) {
        setTextAppearance(getContext(), id);
    }

    public void setTextAppearance(@Nullable TextAppearance textAppearance) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setTextAppearance(textAppearance);
        }
        updateTextPaintDrawState();
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        if (this.chipDrawable != null) {
            this.chipDrawable.setTextAppearanceResource(resId);
        }
        updateTextPaintDrawState();
    }

    @Override // android.widget.TextView
    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);
        if (this.chipDrawable != null) {
            this.chipDrawable.setTextAppearanceResource(resId);
        }
        updateTextPaintDrawState();
    }

    private void updateTextPaintDrawState() {
        TextPaint textPaint = getPaint();
        if (this.chipDrawable != null) {
            textPaint.drawableState = this.chipDrawable.getState();
        }
        TextAppearance textAppearance = getTextAppearance();
        if (textAppearance != null) {
            textAppearance.updateDrawState(getContext(), textPaint, this.fontCallback);
        }
    }

    @Nullable
    private TextAppearance getTextAppearance() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getTextAppearance();
        }
        return null;
    }

    public boolean isChipIconVisible() {
        return this.chipDrawable != null && this.chipDrawable.isChipIconVisible();
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return isChipIconVisible();
    }

    public void setChipIconVisible(@BoolRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipIconVisible(id);
        }
    }

    public void setChipIconVisible(boolean chipIconVisible) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipIconVisible(chipIconVisible);
        }
    }

    @Deprecated
    public void setChipIconEnabledResource(@BoolRes int id) {
        setChipIconVisible(id);
    }

    @Deprecated
    public void setChipIconEnabled(boolean chipIconEnabled) {
        setChipIconVisible(chipIconEnabled);
    }

    @Nullable
    public Drawable getChipIcon() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getChipIcon();
        }
        return null;
    }

    public void setChipIconResource(@DrawableRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipIconResource(id);
        }
    }

    public void setChipIcon(@Nullable Drawable chipIcon) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipIcon(chipIcon);
        }
    }

    @Nullable
    public ColorStateList getChipIconTint() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getChipIconTint();
        }
        return null;
    }

    public void setChipIconTintResource(@ColorRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipIconTintResource(id);
        }
    }

    public void setChipIconTint(@Nullable ColorStateList chipIconTint) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipIconTint(chipIconTint);
        }
    }

    public float getChipIconSize() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getChipIconSize();
        }
        return 0.0f;
    }

    public void setChipIconSizeResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipIconSizeResource(id);
        }
    }

    public void setChipIconSize(float chipIconSize) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipIconSize(chipIconSize);
        }
    }

    public boolean isCloseIconVisible() {
        return this.chipDrawable != null && this.chipDrawable.isCloseIconVisible();
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return isCloseIconVisible();
    }

    public void setCloseIconVisible(@BoolRes int id) {
        setCloseIconVisible(getResources().getBoolean(id));
    }

    public void setCloseIconVisible(boolean closeIconVisible) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconVisible(closeIconVisible);
        }
        updateAccessibilityDelegate();
    }

    @Deprecated
    public void setCloseIconEnabledResource(@BoolRes int id) {
        setCloseIconVisible(id);
    }

    @Deprecated
    public void setCloseIconEnabled(boolean closeIconEnabled) {
        setCloseIconVisible(closeIconEnabled);
    }

    @Nullable
    public Drawable getCloseIcon() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getCloseIcon();
        }
        return null;
    }

    public void setCloseIconResource(@DrawableRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconResource(id);
        }
        updateAccessibilityDelegate();
    }

    public void setCloseIcon(@Nullable Drawable closeIcon) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIcon(closeIcon);
        }
        updateAccessibilityDelegate();
    }

    @Nullable
    public ColorStateList getCloseIconTint() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getCloseIconTint();
        }
        return null;
    }

    public void setCloseIconTintResource(@ColorRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconTintResource(id);
        }
    }

    public void setCloseIconTint(@Nullable ColorStateList closeIconTint) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconTint(closeIconTint);
        }
    }

    public float getCloseIconSize() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getCloseIconSize();
        }
        return 0.0f;
    }

    public void setCloseIconSizeResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconSizeResource(id);
        }
    }

    public void setCloseIconSize(float closeIconSize) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconSize(closeIconSize);
        }
    }

    public void setCloseIconContentDescription(@Nullable CharSequence closeIconContentDescription) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconContentDescription(closeIconContentDescription);
        }
    }

    @Nullable
    public CharSequence getCloseIconContentDescription() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getCloseIconContentDescription();
        }
        return null;
    }

    public boolean isCheckable() {
        return this.chipDrawable != null && this.chipDrawable.isCheckable();
    }

    public void setCheckableResource(@BoolRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCheckableResource(id);
        }
    }

    public void setCheckable(boolean checkable) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCheckable(checkable);
        }
    }

    public boolean isCheckedIconVisible() {
        return this.chipDrawable != null && this.chipDrawable.isCheckedIconVisible();
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return isCheckedIconVisible();
    }

    public void setCheckedIconVisible(@BoolRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCheckedIconVisible(id);
        }
    }

    public void setCheckedIconVisible(boolean checkedIconVisible) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCheckedIconVisible(checkedIconVisible);
        }
    }

    @Deprecated
    public void setCheckedIconEnabledResource(@BoolRes int id) {
        setCheckedIconVisible(id);
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean checkedIconEnabled) {
        setCheckedIconVisible(checkedIconEnabled);
    }

    @Nullable
    public Drawable getCheckedIcon() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getCheckedIcon();
        }
        return null;
    }

    public void setCheckedIconResource(@DrawableRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCheckedIconResource(id);
        }
    }

    public void setCheckedIcon(@Nullable Drawable checkedIcon) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCheckedIcon(checkedIcon);
        }
    }

    @Nullable
    public ColorStateList getCheckedIconTint() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getCheckedIconTint();
        }
        return null;
    }

    public void setCheckedIconTintResource(@ColorRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCheckedIconTintResource(id);
        }
    }

    public void setCheckedIconTint(@Nullable ColorStateList checkedIconTint) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCheckedIconTint(checkedIconTint);
        }
    }

    @Nullable
    public MotionSpec getShowMotionSpec() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getShowMotionSpec();
        }
        return null;
    }

    public void setShowMotionSpecResource(@AnimatorRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setShowMotionSpecResource(id);
        }
    }

    public void setShowMotionSpec(@Nullable MotionSpec showMotionSpec) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setShowMotionSpec(showMotionSpec);
        }
    }

    @Nullable
    public MotionSpec getHideMotionSpec() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getHideMotionSpec();
        }
        return null;
    }

    public void setHideMotionSpecResource(@AnimatorRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setHideMotionSpecResource(id);
        }
    }

    public void setHideMotionSpec(@Nullable MotionSpec hideMotionSpec) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setHideMotionSpec(hideMotionSpec);
        }
    }

    public float getChipStartPadding() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getChipStartPadding();
        }
        return 0.0f;
    }

    public void setChipStartPaddingResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipStartPaddingResource(id);
        }
    }

    public void setChipStartPadding(float chipStartPadding) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipStartPadding(chipStartPadding);
        }
    }

    public float getIconStartPadding() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getIconStartPadding();
        }
        return 0.0f;
    }

    public void setIconStartPaddingResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setIconStartPaddingResource(id);
        }
    }

    public void setIconStartPadding(float iconStartPadding) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setIconStartPadding(iconStartPadding);
        }
    }

    public float getIconEndPadding() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getIconEndPadding();
        }
        return 0.0f;
    }

    public void setIconEndPaddingResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setIconEndPaddingResource(id);
        }
    }

    public void setIconEndPadding(float iconEndPadding) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setIconEndPadding(iconEndPadding);
        }
    }

    public float getTextStartPadding() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getTextStartPadding();
        }
        return 0.0f;
    }

    public void setTextStartPaddingResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setTextStartPaddingResource(id);
        }
    }

    public void setTextStartPadding(float textStartPadding) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setTextStartPadding(textStartPadding);
        }
    }

    public float getTextEndPadding() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getTextEndPadding();
        }
        return 0.0f;
    }

    public void setTextEndPaddingResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setTextEndPaddingResource(id);
        }
    }

    public void setTextEndPadding(float textEndPadding) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setTextEndPadding(textEndPadding);
        }
    }

    public float getCloseIconStartPadding() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getCloseIconStartPadding();
        }
        return 0.0f;
    }

    public void setCloseIconStartPaddingResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconStartPaddingResource(id);
        }
    }

    public void setCloseIconStartPadding(float closeIconStartPadding) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconStartPadding(closeIconStartPadding);
        }
    }

    public float getCloseIconEndPadding() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getCloseIconEndPadding();
        }
        return 0.0f;
    }

    public void setCloseIconEndPaddingResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconEndPaddingResource(id);
        }
    }

    public void setCloseIconEndPadding(float closeIconEndPadding) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setCloseIconEndPadding(closeIconEndPadding);
        }
    }

    public float getChipEndPadding() {
        if (this.chipDrawable != null) {
            return this.chipDrawable.getChipEndPadding();
        }
        return 0.0f;
    }

    public void setChipEndPaddingResource(@DimenRes int id) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipEndPaddingResource(id);
        }
    }

    public void setChipEndPadding(float chipEndPadding) {
        if (this.chipDrawable != null) {
            this.chipDrawable.setChipEndPadding(chipEndPadding);
        }
    }

    public boolean shouldEnsureMinTouchTargetSize() {
        return this.ensureMinTouchTargetSize;
    }

    public void setEnsureMinTouchTargetSize(boolean flag) {
        this.ensureMinTouchTargetSize = flag;
        ensureAccessibleTouchTarget(this.minTouchTargetSize);
    }

    public boolean ensureAccessibleTouchTarget(@Dimension int minTargetPx) {
        this.minTouchTargetSize = minTargetPx;
        if (!shouldEnsureMinTouchTargetSize()) {
            if (this.insetBackgroundDrawable != null) {
                removeBackgroundInset();
                return false;
            }
            updateBackgroundDrawable();
            return false;
        }
        int deltaHeight = Math.max(0, minTargetPx - this.chipDrawable.getIntrinsicHeight());
        int deltaWidth = Math.max(0, minTargetPx - this.chipDrawable.getIntrinsicWidth());
        if (deltaWidth <= 0 && deltaHeight <= 0) {
            if (this.insetBackgroundDrawable != null) {
                removeBackgroundInset();
                return false;
            }
            updateBackgroundDrawable();
            return false;
        }
        int deltaX = deltaWidth > 0 ? deltaWidth / 2 : 0;
        int deltaY = deltaHeight > 0 ? deltaHeight / 2 : 0;
        if (this.insetBackgroundDrawable != null) {
            Rect padding = new Rect();
            this.insetBackgroundDrawable.getPadding(padding);
            if (padding.top == deltaY && padding.bottom == deltaY && padding.left == deltaX && padding.right == deltaX) {
                updateBackgroundDrawable();
                return true;
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            if (getMinHeight() != minTargetPx) {
                setMinHeight(minTargetPx);
            }
            if (getMinWidth() != minTargetPx) {
                setMinWidth(minTargetPx);
            }
        } else {
            setMinHeight(minTargetPx);
            setMinWidth(minTargetPx);
        }
        insetChipBackgroundDrawable(deltaX, deltaY, deltaX, deltaY);
        updateBackgroundDrawable();
        return true;
    }

    private void removeBackgroundInset() {
        if (this.insetBackgroundDrawable != null) {
            this.insetBackgroundDrawable = null;
            setMinWidth(0);
            setMinHeight((int) getChipMinHeight());
            updateBackgroundDrawable();
        }
    }

    private void insetChipBackgroundDrawable(int insetLeft, int insetTop, int insetRight, int insetBottom) {
        this.insetBackgroundDrawable = new InsetDrawable((Drawable) this.chipDrawable, insetLeft, insetTop, insetRight, insetBottom);
    }
}
