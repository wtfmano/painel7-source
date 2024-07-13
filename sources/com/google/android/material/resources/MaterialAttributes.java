package com.google.android.material.resources;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import com.google.android.material.R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class MaterialAttributes {
    @Nullable
    public static TypedValue resolve(@NonNull Context context, @AttrRes int attributeResId) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attributeResId, typedValue, true)) {
            return typedValue;
        }
        return null;
    }

    public static int resolveOrThrow(@NonNull Context context, @AttrRes int attributeResId, @NonNull String errorMessageComponent) {
        TypedValue typedValue = resolve(context, attributeResId);
        if (typedValue == null) {
            throw new IllegalArgumentException(String.format("%1$s requires a value for the %2$s attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).", errorMessageComponent, context.getResources().getResourceName(attributeResId)));
        }
        return typedValue.data;
    }

    public static int resolveOrThrow(@NonNull View componentView, @AttrRes int attributeResId) {
        return resolveOrThrow(componentView.getContext(), attributeResId, componentView.getClass().getCanonicalName());
    }

    public static boolean resolveBooleanOrThrow(@NonNull Context context, @AttrRes int attributeResId, @NonNull String errorMessageComponent) {
        return resolveOrThrow(context, attributeResId, errorMessageComponent) != 0;
    }

    public static boolean resolveBoolean(@NonNull Context context, @AttrRes int attributeResId, boolean defaultValue) {
        TypedValue typedValue = resolve(context, attributeResId);
        return (typedValue == null || typedValue.type != 18) ? defaultValue : typedValue.data != 0;
    }

    public static int resolveInteger(@NonNull Context context, @AttrRes int attributeResId, int defaultValue) {
        TypedValue typedValue = resolve(context, attributeResId);
        return (typedValue == null || typedValue.type != 16) ? defaultValue : typedValue.data;
    }

    @Px
    public static int resolveMinimumAccessibleTouchTarget(@NonNull Context context) {
        return resolveDimension(context, R.attr.minTouchTargetSize, R.dimen.mtrl_min_touch_target_size);
    }

    @Px
    public static int resolveDimension(@NonNull Context context, @AttrRes int attributeResId, @DimenRes int defaultDimenResId) {
        TypedValue dimensionValue = resolve(context, attributeResId);
        return (dimensionValue == null || dimensionValue.type != 5) ? (int) context.getResources().getDimension(defaultDimenResId) : (int) dimensionValue.getDimension(context.getResources().getDisplayMetrics());
    }
}
