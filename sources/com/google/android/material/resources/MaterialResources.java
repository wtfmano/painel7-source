package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TintTypedArray;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class MaterialResources {
    private static final float FONT_SCALE_1_3 = 1.3f;
    private static final float FONT_SCALE_2_0 = 2.0f;

    private MaterialResources() {
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Context context, @NonNull TypedArray attributes, @StyleableRes int index) {
        int color;
        int resourceId;
        ColorStateList value;
        if (!attributes.hasValue(index) || (resourceId = attributes.getResourceId(index, 0)) == 0 || (value = AppCompatResources.getColorStateList(context, resourceId)) == null) {
            if (Build.VERSION.SDK_INT <= 15 && (color = attributes.getColor(index, -1)) != -1) {
                ColorStateList value2 = ColorStateList.valueOf(color);
                return value2;
            }
            ColorStateList value3 = attributes.getColorStateList(index);
            return value3;
        }
        return value;
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Context context, @NonNull TintTypedArray attributes, @StyleableRes int index) {
        int color;
        int resourceId;
        ColorStateList value;
        if (!attributes.hasValue(index) || (resourceId = attributes.getResourceId(index, 0)) == 0 || (value = AppCompatResources.getColorStateList(context, resourceId)) == null) {
            if (Build.VERSION.SDK_INT <= 15 && (color = attributes.getColor(index, -1)) != -1) {
                ColorStateList value2 = ColorStateList.valueOf(color);
                return value2;
            }
            ColorStateList value3 = attributes.getColorStateList(index);
            return value3;
        }
        return value;
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @NonNull TypedArray attributes, @StyleableRes int index) {
        int resourceId;
        Drawable value;
        return (!attributes.hasValue(index) || (resourceId = attributes.getResourceId(index, 0)) == 0 || (value = AppCompatResources.getDrawable(context, resourceId)) == null) ? attributes.getDrawable(index) : value;
    }

    @Nullable
    public static TextAppearance getTextAppearance(@NonNull Context context, @NonNull TypedArray attributes, @StyleableRes int index) {
        int resourceId;
        if (!attributes.hasValue(index) || (resourceId = attributes.getResourceId(index, 0)) == 0) {
            return null;
        }
        return new TextAppearance(context, resourceId);
    }

    public static int getDimensionPixelSize(@NonNull Context context, @NonNull TypedArray attributes, @StyleableRes int index, int defaultValue) {
        TypedValue value = new TypedValue();
        if (!attributes.getValue(index, value) || value.type != 2) {
            return attributes.getDimensionPixelSize(index, defaultValue);
        }
        TypedArray styledAttrs = context.getTheme().obtainStyledAttributes(new int[]{value.data});
        int dimensionPixelSize = styledAttrs.getDimensionPixelSize(0, defaultValue);
        styledAttrs.recycle();
        return dimensionPixelSize;
    }

    public static boolean isFontScaleAtLeast1_3(@NonNull Context context) {
        return context.getResources().getConfiguration().fontScale >= FONT_SCALE_1_3;
    }

    public static boolean isFontScaleAtLeast2_0(@NonNull Context context) {
        return context.getResources().getConfiguration().fontScale >= FONT_SCALE_2_0;
    }

    @StyleableRes
    public static int getIndexWithValue(@NonNull TypedArray attributes, @StyleableRes int a, @StyleableRes int b) {
        return attributes.hasValue(a) ? a : b;
    }
}
