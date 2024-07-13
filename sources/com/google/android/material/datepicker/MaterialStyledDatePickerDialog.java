package com.google.android.material.datepicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import com.google.android.material.R;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP, RestrictTo.Scope.TESTS})
/* loaded from: classes.dex */
public class MaterialStyledDatePickerDialog extends DatePickerDialog {
    @AttrRes
    private static final int DEF_STYLE_ATTR = 16843612;
    @StyleRes
    private static final int DEF_STYLE_RES = R.style.MaterialAlertDialog_MaterialComponents_Picker_Date_Spinner;
    @NonNull
    private final Drawable background;
    @NonNull
    private final Rect backgroundInsets;

    public MaterialStyledDatePickerDialog(@NonNull Context context) {
        this(context, 0);
    }

    public MaterialStyledDatePickerDialog(@NonNull Context context, int themeResId) {
        this(context, themeResId, null, -1, -1, -1);
    }

    public MaterialStyledDatePickerDialog(@NonNull Context context, @Nullable DatePickerDialog.OnDateSetListener listener, int year, int month, int dayOfMonth) {
        this(context, 0, listener, year, month, dayOfMonth);
    }

    public MaterialStyledDatePickerDialog(@NonNull Context context, int themeResId, @Nullable DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
        Context context2 = getContext();
        int surfaceColor = MaterialAttributes.resolveOrThrow(getContext(), R.attr.colorSurface, getClass().getCanonicalName());
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(context2, null, DEF_STYLE_ATTR, DEF_STYLE_RES);
        if (Build.VERSION.SDK_INT >= 21) {
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(surfaceColor));
        } else {
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(0));
        }
        this.backgroundInsets = MaterialDialogs.getDialogBackgroundInsets(context2, DEF_STYLE_ATTR, DEF_STYLE_RES);
        this.background = MaterialDialogs.insetDrawable(materialShapeDrawable, this.backgroundInsets);
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(this.background);
        getWindow().getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(this, this.backgroundInsets));
    }
}
