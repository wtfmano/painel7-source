package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.database.core.ValidationPath;

/* loaded from: classes.dex */
public class BottomSheetDialog extends AppCompatDialog {
    private BottomSheetBehavior<FrameLayout> behavior;
    private FrameLayout bottomSheet;
    @NonNull
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;
    boolean cancelable;
    private boolean canceledOnTouchOutside;
    private boolean canceledOnTouchOutsideSet;
    private FrameLayout container;
    private CoordinatorLayout coordinator;
    boolean dismissWithAnimation;
    private BottomSheetBehavior.BottomSheetCallback edgeToEdgeCallback;
    private boolean edgeToEdgeEnabled;

    public BottomSheetDialog(@NonNull Context context) {
        this(context, 0);
        this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    public BottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, getThemeResId(context, theme));
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.5
            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }

            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        };
        supportRequestWindowFeature(1);
        this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    protected BottomSheetDialog(@NonNull Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.5
            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }

            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        };
        supportRequestWindowFeature(1);
        this.cancelable = cancelable;
        this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.setStatusBarColor(0);
                window.addFlags(Integer.MIN_VALUE);
                if (Build.VERSION.SDK_INT < 23) {
                    window.addFlags(67108864);
                }
            }
            window.setLayout(-1, -1);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    @Override // android.app.Dialog
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (this.cancelable != cancelable) {
            this.cancelable = cancelable;
            if (this.behavior != null) {
                this.behavior.setHideable(cancelable);
            }
        }
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        if (this.behavior != null && this.behavior.getState() == 5) {
            this.behavior.setState(4);
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        if (window != null && Build.VERSION.SDK_INT >= 21) {
            boolean drawEdgeToEdge = this.edgeToEdgeEnabled && Color.alpha(window.getNavigationBarColor()) < 255;
            if (this.container != null) {
                this.container.setFitsSystemWindows(!drawEdgeToEdge);
            }
            if (this.coordinator != null) {
                this.coordinator.setFitsSystemWindows(drawEdgeToEdge ? false : true);
            }
            if (drawEdgeToEdge) {
                window.getDecorView().setSystemUiVisibility(ValidationPath.MAX_PATH_LENGTH_BYTES);
            }
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void cancel() {
        BottomSheetBehavior<FrameLayout> behavior = getBehavior();
        if (!this.dismissWithAnimation || behavior.getState() == 5) {
            super.cancel();
        } else {
            behavior.setState(5);
        }
    }

    @Override // android.app.Dialog
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !this.cancelable) {
            this.cancelable = true;
        }
        this.canceledOnTouchOutside = cancel;
        this.canceledOnTouchOutsideSet = true;
    }

    @NonNull
    public BottomSheetBehavior<FrameLayout> getBehavior() {
        if (this.behavior == null) {
            ensureContainerAndBehavior();
        }
        return this.behavior;
    }

    public void setDismissWithAnimation(boolean dismissWithAnimation) {
        this.dismissWithAnimation = dismissWithAnimation;
    }

    public boolean getDismissWithAnimation() {
        return this.dismissWithAnimation;
    }

    public boolean getEdgeToEdgeEnabled() {
        return this.edgeToEdgeEnabled;
    }

    private FrameLayout ensureContainerAndBehavior() {
        if (this.container == null) {
            this.container = (FrameLayout) View.inflate(getContext(), R.layout.design_bottom_sheet_dialog, null);
            this.coordinator = (CoordinatorLayout) this.container.findViewById(R.id.coordinator);
            this.bottomSheet = (FrameLayout) this.container.findViewById(R.id.design_bottom_sheet);
            this.behavior = BottomSheetBehavior.from(this.bottomSheet);
            this.behavior.addBottomSheetCallback(this.bottomSheetCallback);
            this.behavior.setHideable(this.cancelable);
        }
        return this.container;
    }

    private View wrapInBottomSheet(int layoutResId, @Nullable View view, @Nullable ViewGroup.LayoutParams params) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinator = (CoordinatorLayout) this.container.findViewById(R.id.coordinator);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, (ViewGroup) coordinator, false);
        }
        if (this.edgeToEdgeEnabled) {
            ViewCompat.setOnApplyWindowInsetsListener(this.bottomSheet, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat insets) {
                    if (BottomSheetDialog.this.edgeToEdgeCallback != null) {
                        BottomSheetDialog.this.behavior.removeBottomSheetCallback(BottomSheetDialog.this.edgeToEdgeCallback);
                    }
                    if (insets != null) {
                        BottomSheetDialog.this.edgeToEdgeCallback = new EdgeToEdgeCallback(BottomSheetDialog.this.bottomSheet, insets);
                        BottomSheetDialog.this.behavior.addBottomSheetCallback(BottomSheetDialog.this.edgeToEdgeCallback);
                    }
                    return insets;
                }
            });
        }
        this.bottomSheet.removeAllViews();
        if (params == null) {
            this.bottomSheet.addView(view);
        } else {
            this.bottomSheet.addView(view, params);
        }
        coordinator.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (BottomSheetDialog.this.cancelable && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.shouldWindowCloseOnTouchOutside()) {
                    BottomSheetDialog.this.cancel();
                }
            }
        });
        ViewCompat.setAccessibilityDelegate(this.bottomSheet, new AccessibilityDelegateCompat() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.3
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View host, @NonNull AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                if (BottomSheetDialog.this.cancelable) {
                    info.addAction(1048576);
                    info.setDismissable(true);
                    return;
                }
                info.setDismissable(false);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action == 1048576 && BottomSheetDialog.this.cancelable) {
                    BottomSheetDialog.this.cancel();
                    return true;
                }
                return super.performAccessibilityAction(host, action, args);
            }
        });
        this.bottomSheet.setOnTouchListener(new View.OnTouchListener() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent event) {
                return true;
            }
        });
        return this.container;
    }

    boolean shouldWindowCloseOnTouchOutside() {
        if (!this.canceledOnTouchOutsideSet) {
            TypedArray a = getContext().obtainStyledAttributes(new int[]{16843611});
            this.canceledOnTouchOutside = a.getBoolean(0, true);
            a.recycle();
            this.canceledOnTouchOutsideSet = true;
        }
        return this.canceledOnTouchOutside;
    }

    private static int getThemeResId(@NonNull Context context, int themeId) {
        if (themeId == 0) {
            TypedValue outValue = new TypedValue();
            if (context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, outValue, true)) {
                return outValue.resourceId;
            }
            return R.style.Theme_Design_Light_BottomSheetDialog;
        }
        return themeId;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeDefaultCallback() {
        this.behavior.removeBottomSheetCallback(this.bottomSheetCallback);
    }

    /* loaded from: classes.dex */
    private static class EdgeToEdgeCallback extends BottomSheetBehavior.BottomSheetCallback {
        private final WindowInsetsCompat insetsCompat;
        private final boolean lightBottomSheet;
        private final boolean lightStatusBar;

        private EdgeToEdgeCallback(@NonNull View bottomSheet, @NonNull WindowInsetsCompat insetsCompat) {
            ColorStateList backgroundTint;
            this.insetsCompat = insetsCompat;
            this.lightStatusBar = Build.VERSION.SDK_INT >= 23 && (bottomSheet.getSystemUiVisibility() & 8192) != 0;
            MaterialShapeDrawable msd = BottomSheetBehavior.from(bottomSheet).getMaterialShapeDrawable();
            if (msd != null) {
                backgroundTint = msd.getFillColor();
            } else {
                backgroundTint = ViewCompat.getBackgroundTintList(bottomSheet);
            }
            if (backgroundTint != null) {
                this.lightBottomSheet = MaterialColors.isColorLight(backgroundTint.getDefaultColor());
            } else if (bottomSheet.getBackground() instanceof ColorDrawable) {
                this.lightBottomSheet = MaterialColors.isColorLight(((ColorDrawable) bottomSheet.getBackground()).getColor());
            } else {
                this.lightBottomSheet = this.lightStatusBar;
            }
        }

        @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            setPaddingForPosition(bottomSheet);
        }

        @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            setPaddingForPosition(bottomSheet);
        }

        private void setPaddingForPosition(View bottomSheet) {
            if (bottomSheet.getTop() < this.insetsCompat.getSystemWindowInsetTop()) {
                BottomSheetDialog.setLightStatusBar(bottomSheet, this.lightBottomSheet);
                bottomSheet.setPadding(bottomSheet.getPaddingLeft(), this.insetsCompat.getSystemWindowInsetTop() - bottomSheet.getTop(), bottomSheet.getPaddingRight(), bottomSheet.getPaddingBottom());
            } else if (bottomSheet.getTop() != 0) {
                BottomSheetDialog.setLightStatusBar(bottomSheet, this.lightStatusBar);
                bottomSheet.setPadding(bottomSheet.getPaddingLeft(), 0, bottomSheet.getPaddingRight(), bottomSheet.getPaddingBottom());
            }
        }
    }

    public static void setLightStatusBar(@NonNull View view, boolean isLight) {
        int flags;
        if (Build.VERSION.SDK_INT >= 23) {
            int flags2 = view.getSystemUiVisibility();
            if (isLight) {
                flags = flags2 | 8192;
            } else {
                flags = flags2 & (-8193);
            }
            view.setSystemUiVisibility(flags);
        }
    }
}
