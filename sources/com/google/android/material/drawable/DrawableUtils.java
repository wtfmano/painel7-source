package com.google.android.material.drawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.XmlRes;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class DrawableUtils {
    private DrawableUtils() {
    }

    @Nullable
    public static PorterDuffColorFilter updateTintFilter(@NonNull Drawable drawable, @Nullable ColorStateList tint, @Nullable PorterDuff.Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }
        int color = tint.getColorForState(drawable.getState(), 0);
        return new PorterDuffColorFilter(color, tintMode);
    }

    @NonNull
    public static AttributeSet parseDrawableXml(@NonNull Context context, @XmlRes int id, @NonNull CharSequence startTag) {
        int type;
        try {
            XmlPullParser parser = context.getResources().getXml(id);
            do {
                type = parser.next();
                if (type == 2) {
                    break;
                }
            } while (type != 1);
            if (type != 2) {
                throw new XmlPullParserException("No start tag found");
            }
            if (!TextUtils.equals(parser.getName(), startTag)) {
                throw new XmlPullParserException("Must have a <" + ((Object) startTag) + "> start tag");
            }
            AttributeSet attrs = Xml.asAttributeSet(parser);
            return attrs;
        } catch (IOException e) {
            e = e;
            Resources.NotFoundException exception = new Resources.NotFoundException("Can't load badge resource ID #0x" + Integer.toHexString(id));
            exception.initCause(e);
            throw exception;
        } catch (XmlPullParserException e2) {
            e = e2;
            Resources.NotFoundException exception2 = new Resources.NotFoundException("Can't load badge resource ID #0x" + Integer.toHexString(id));
            exception2.initCause(e);
            throw exception2;
        }
    }

    @TargetApi(21)
    public static void setRippleDrawableRadius(@Nullable RippleDrawable drawable, int radius) {
        if (Build.VERSION.SDK_INT >= 23) {
            drawable.setRadius(radius);
            return;
        }
        try {
            Method setMaxRadiusMethod = RippleDrawable.class.getDeclaredMethod("setMaxRadius", Integer.TYPE);
            setMaxRadiusMethod.invoke(drawable, Integer.valueOf(radius));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException("Couldn't set RippleDrawable radius", e);
        }
    }
}
