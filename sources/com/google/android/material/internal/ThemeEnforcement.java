package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class ThemeEnforcement {
    private static final String APPCOMPAT_THEME_NAME = "Theme.AppCompat";
    private static final String MATERIAL_THEME_NAME = "Theme.MaterialComponents";
    private static final int[] APPCOMPAT_CHECK_ATTRS = {R.attr.colorPrimary};
    private static final int[] MATERIAL_CHECK_ATTRS = {R.attr.colorPrimaryVariant};

    private ThemeEnforcement() {
    }

    @NonNull
    public static TypedArray obtainStyledAttributes(@NonNull Context context, AttributeSet set, @NonNull @StyleableRes int[] attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes, @StyleableRes int... textAppearanceResIndices) {
        checkCompatibleTheme(context, set, defStyleAttr, defStyleRes);
        checkTextAppearance(context, set, attrs, defStyleAttr, defStyleRes, textAppearanceResIndices);
        return context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
    }

    public static TintTypedArray obtainTintedStyledAttributes(@NonNull Context context, AttributeSet set, @NonNull @StyleableRes int[] attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes, @StyleableRes int... textAppearanceResIndices) {
        checkCompatibleTheme(context, set, defStyleAttr, defStyleRes);
        checkTextAppearance(context, set, attrs, defStyleAttr, defStyleRes, textAppearanceResIndices);
        return TintTypedArray.obtainStyledAttributes(context, set, attrs, defStyleAttr, defStyleRes);
    }

    private static void checkCompatibleTheme(@NonNull Context context, AttributeSet set, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(set, R.styleable.ThemeEnforcement, defStyleAttr, defStyleRes);
        boolean enforceMaterialTheme = a.getBoolean(R.styleable.ThemeEnforcement_enforceMaterialTheme, false);
        a.recycle();
        if (enforceMaterialTheme) {
            TypedValue isMaterialTheme = new TypedValue();
            boolean resolvedValue = context.getTheme().resolveAttribute(R.attr.isMaterialTheme, isMaterialTheme, true);
            if (!resolvedValue || (isMaterialTheme.type == 18 && isMaterialTheme.data == 0)) {
                checkMaterialTheme(context);
            }
        }
        checkAppCompatTheme(context);
    }

    private static void checkTextAppearance(@NonNull Context context, AttributeSet set, @NonNull @StyleableRes int[] attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes, @Nullable @StyleableRes int... textAppearanceResIndices) {
        boolean validTextAppearance = false;
        TypedArray themeEnforcementAttrs = context.obtainStyledAttributes(set, R.styleable.ThemeEnforcement, defStyleAttr, defStyleRes);
        boolean enforceTextAppearance = themeEnforcementAttrs.getBoolean(R.styleable.ThemeEnforcement_enforceTextAppearance, false);
        if (!enforceTextAppearance) {
            themeEnforcementAttrs.recycle();
            return;
        }
        if (textAppearanceResIndices == null || textAppearanceResIndices.length == 0) {
            if (themeEnforcementAttrs.getResourceId(R.styleable.ThemeEnforcement_android_textAppearance, -1) != -1) {
                validTextAppearance = true;
            }
        } else {
            validTextAppearance = isCustomTextAppearanceValid(context, set, attrs, defStyleAttr, defStyleRes, textAppearanceResIndices);
        }
        themeEnforcementAttrs.recycle();
        if (!validTextAppearance) {
            throw new IllegalArgumentException("This component requires that you specify a valid TextAppearance attribute. Update your app theme to inherit from Theme.MaterialComponents (or a descendant).");
        }
    }

    private static boolean isCustomTextAppearanceValid(@NonNull Context context, AttributeSet set, @NonNull @StyleableRes int[] attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes, @NonNull @StyleableRes int... textAppearanceResIndices) {
        TypedArray componentAttrs = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
        for (int customTextAppearanceIndex : textAppearanceResIndices) {
            if (componentAttrs.getResourceId(customTextAppearanceIndex, -1) == -1) {
                componentAttrs.recycle();
                return false;
            }
        }
        componentAttrs.recycle();
        return true;
    }

    public static void checkAppCompatTheme(@NonNull Context context) {
        checkTheme(context, APPCOMPAT_CHECK_ATTRS, APPCOMPAT_THEME_NAME);
    }

    public static void checkMaterialTheme(@NonNull Context context) {
        checkTheme(context, MATERIAL_CHECK_ATTRS, MATERIAL_THEME_NAME);
    }

    public static boolean isAppCompatTheme(@NonNull Context context) {
        return isTheme(context, APPCOMPAT_CHECK_ATTRS);
    }

    public static boolean isMaterialTheme(@NonNull Context context) {
        return isTheme(context, MATERIAL_CHECK_ATTRS);
    }

    private static boolean isTheme(@NonNull Context context, @NonNull int[] themeAttributes) {
        TypedArray a = context.obtainStyledAttributes(themeAttributes);
        for (int i = 0; i < themeAttributes.length; i++) {
            if (!a.hasValue(i)) {
                a.recycle();
                return false;
            }
        }
        a.recycle();
        return true;
    }

    private static void checkTheme(@NonNull Context context, @NonNull int[] themeAttributes, String themeName) {
        if (!isTheme(context, themeAttributes)) {
            throw new IllegalArgumentException("The style on this component requires your app theme to be " + themeName + " (or a descendant).");
        }
    }
}
