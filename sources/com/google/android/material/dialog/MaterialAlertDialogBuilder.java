package com.google.android.material.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes.dex */
public class MaterialAlertDialogBuilder extends AlertDialog.Builder {
    @AttrRes
    private static final int DEF_STYLE_ATTR = R.attr.alertDialogStyle;
    @StyleRes
    private static final int DEF_STYLE_RES = R.style.MaterialAlertDialog_MaterialComponents;
    @AttrRes
    private static final int MATERIAL_ALERT_DIALOG_THEME_OVERLAY = R.attr.materialAlertDialogTheme;
    @Nullable
    private Drawable background;
    @NonNull
    @Dimension
    private final Rect backgroundInsets;

    private static int getMaterialAlertDialogThemeOverlay(@NonNull Context context) {
        TypedValue materialAlertDialogThemeOverlay = MaterialAttributes.resolve(context, MATERIAL_ALERT_DIALOG_THEME_OVERLAY);
        if (materialAlertDialogThemeOverlay == null) {
            return 0;
        }
        return materialAlertDialogThemeOverlay.data;
    }

    private static Context createMaterialAlertDialogThemedContext(@NonNull Context context) {
        int themeOverlayId = getMaterialAlertDialogThemeOverlay(context);
        Context themedContext = MaterialThemeOverlay.wrap(context, null, DEF_STYLE_ATTR, DEF_STYLE_RES);
        return themeOverlayId == 0 ? themedContext : new ContextThemeWrapper(themedContext, themeOverlayId);
    }

    private static int getOverridingThemeResId(@NonNull Context context, int overrideThemeResId) {
        if (overrideThemeResId != 0) {
            return overrideThemeResId;
        }
        return getMaterialAlertDialogThemeOverlay(context);
    }

    public MaterialAlertDialogBuilder(@NonNull Context context) {
        this(context, 0);
    }

    public MaterialAlertDialogBuilder(@NonNull Context context, int overrideThemeResId) {
        super(createMaterialAlertDialogThemedContext(context), getOverridingThemeResId(context, overrideThemeResId));
        Context context2 = getContext();
        Resources.Theme theme = context2.getTheme();
        this.backgroundInsets = MaterialDialogs.getDialogBackgroundInsets(context2, DEF_STYLE_ATTR, DEF_STYLE_RES);
        int surfaceColor = MaterialColors.getColor(context2, R.attr.colorSurface, getClass().getCanonicalName());
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(context2, null, DEF_STYLE_ATTR, DEF_STYLE_RES);
        materialShapeDrawable.initializeElevationOverlay(context2);
        materialShapeDrawable.setFillColor(ColorStateList.valueOf(surfaceColor));
        if (Build.VERSION.SDK_INT >= 28) {
            TypedValue dialogCornerRadiusValue = new TypedValue();
            theme.resolveAttribute(16844145, dialogCornerRadiusValue, true);
            float dialogCornerRadius = dialogCornerRadiusValue.getDimension(getContext().getResources().getDisplayMetrics());
            if (dialogCornerRadiusValue.type == 5 && dialogCornerRadius >= 0.0f) {
                materialShapeDrawable.setCornerSize(dialogCornerRadius);
            }
        }
        this.background = materialShapeDrawable;
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public AlertDialog create() {
        AlertDialog alertDialog = super.create();
        Window window = alertDialog.getWindow();
        View decorView = window.getDecorView();
        if (this.background instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable) this.background).setElevation(ViewCompat.getElevation(decorView));
        }
        Drawable insetDrawable = MaterialDialogs.insetDrawable(this.background, this.backgroundInsets);
        window.setBackgroundDrawable(insetDrawable);
        decorView.setOnTouchListener(new InsetDialogOnTouchListener(alertDialog, this.backgroundInsets));
        return alertDialog;
    }

    @Nullable
    public Drawable getBackground() {
        return this.background;
    }

    @NonNull
    public MaterialAlertDialogBuilder setBackground(@Nullable Drawable background) {
        this.background = background;
        return this;
    }

    @NonNull
    public MaterialAlertDialogBuilder setBackgroundInsetStart(@Px int backgroundInsetStart) {
        if (Build.VERSION.SDK_INT >= 17 && getContext().getResources().getConfiguration().getLayoutDirection() == 1) {
            this.backgroundInsets.right = backgroundInsetStart;
        } else {
            this.backgroundInsets.left = backgroundInsetStart;
        }
        return this;
    }

    @NonNull
    public MaterialAlertDialogBuilder setBackgroundInsetTop(@Px int backgroundInsetTop) {
        this.backgroundInsets.top = backgroundInsetTop;
        return this;
    }

    @NonNull
    public MaterialAlertDialogBuilder setBackgroundInsetEnd(@Px int backgroundInsetEnd) {
        if (Build.VERSION.SDK_INT >= 17 && getContext().getResources().getConfiguration().getLayoutDirection() == 1) {
            this.backgroundInsets.left = backgroundInsetEnd;
        } else {
            this.backgroundInsets.right = backgroundInsetEnd;
        }
        return this;
    }

    @NonNull
    public MaterialAlertDialogBuilder setBackgroundInsetBottom(@Px int backgroundInsetBottom) {
        this.backgroundInsets.bottom = backgroundInsetBottom;
        return this;
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setTitle(@StringRes int titleId) {
        return (MaterialAlertDialogBuilder) super.setTitle(titleId);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setTitle(@Nullable CharSequence title) {
        return (MaterialAlertDialogBuilder) super.setTitle(title);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setCustomTitle(@Nullable View customTitleView) {
        return (MaterialAlertDialogBuilder) super.setCustomTitle(customTitleView);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setMessage(@StringRes int messageId) {
        return (MaterialAlertDialogBuilder) super.setMessage(messageId);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setMessage(@Nullable CharSequence message) {
        return (MaterialAlertDialogBuilder) super.setMessage(message);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setIcon(@DrawableRes int iconId) {
        return (MaterialAlertDialogBuilder) super.setIcon(iconId);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setIcon(@Nullable Drawable icon) {
        return (MaterialAlertDialogBuilder) super.setIcon(icon);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setIconAttribute(@AttrRes int attrId) {
        return (MaterialAlertDialogBuilder) super.setIconAttribute(attrId);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setPositiveButton(@StringRes int textId, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setPositiveButton(textId, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setPositiveButton(@Nullable CharSequence text, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setPositiveButton(text, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setPositiveButtonIcon(@Nullable Drawable icon) {
        return (MaterialAlertDialogBuilder) super.setPositiveButtonIcon(icon);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setNegativeButton(@StringRes int textId, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setNegativeButton(textId, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setNegativeButton(@Nullable CharSequence text, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setNegativeButton(text, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setNegativeButtonIcon(@Nullable Drawable icon) {
        return (MaterialAlertDialogBuilder) super.setNegativeButtonIcon(icon);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setNeutralButton(@StringRes int textId, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setNeutralButton(textId, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setNeutralButton(@Nullable CharSequence text, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setNeutralButton(text, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setNeutralButtonIcon(@Nullable Drawable icon) {
        return (MaterialAlertDialogBuilder) super.setNeutralButtonIcon(icon);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setCancelable(boolean cancelable) {
        return (MaterialAlertDialogBuilder) super.setCancelable(cancelable);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setOnCancelListener(@Nullable DialogInterface.OnCancelListener onCancelListener) {
        return (MaterialAlertDialogBuilder) super.setOnCancelListener(onCancelListener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setOnDismissListener(@Nullable DialogInterface.OnDismissListener onDismissListener) {
        return (MaterialAlertDialogBuilder) super.setOnDismissListener(onDismissListener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setOnKeyListener(@Nullable DialogInterface.OnKeyListener onKeyListener) {
        return (MaterialAlertDialogBuilder) super.setOnKeyListener(onKeyListener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setItems(@ArrayRes int itemsId, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setItems(itemsId, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setItems(@Nullable CharSequence[] items, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setItems(items, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setAdapter(@Nullable ListAdapter adapter, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setAdapter(adapter, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setCursor(@Nullable Cursor cursor, @Nullable DialogInterface.OnClickListener listener, @NonNull String labelColumn) {
        return (MaterialAlertDialogBuilder) super.setCursor(cursor, listener, labelColumn);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setMultiChoiceItems(@ArrayRes int itemsId, @Nullable boolean[] checkedItems, @Nullable DialogInterface.OnMultiChoiceClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setMultiChoiceItems(itemsId, checkedItems, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setMultiChoiceItems(@Nullable CharSequence[] items, @Nullable boolean[] checkedItems, @Nullable DialogInterface.OnMultiChoiceClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setMultiChoiceItems(items, checkedItems, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setMultiChoiceItems(@Nullable Cursor cursor, @NonNull String isCheckedColumn, @NonNull String labelColumn, @Nullable DialogInterface.OnMultiChoiceClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setSingleChoiceItems(itemsId, checkedItem, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setSingleChoiceItems(@Nullable Cursor cursor, int checkedItem, @NonNull String labelColumn, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setSingleChoiceItems(@Nullable CharSequence[] items, int checkedItem, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setSingleChoiceItems(items, checkedItem, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setSingleChoiceItems(@Nullable ListAdapter adapter, int checkedItem, @Nullable DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setSingleChoiceItems(adapter, checkedItem, listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setOnItemSelectedListener(@Nullable AdapterView.OnItemSelectedListener listener) {
        return (MaterialAlertDialogBuilder) super.setOnItemSelectedListener(listener);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setView(int layoutResId) {
        return (MaterialAlertDialogBuilder) super.setView(layoutResId);
    }

    @Override // androidx.appcompat.app.AlertDialog.Builder
    @NonNull
    public MaterialAlertDialogBuilder setView(@Nullable View view) {
        return (MaterialAlertDialogBuilder) super.setView(view);
    }
}
