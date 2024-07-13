package com.google.android.gms.dynamic;

import android.os.IBinder;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.RetainForClient;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.lang.reflect.Field;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@RetainForClient
@KeepForSdk
/* loaded from: classes.dex */
public final class ObjectWrapper<T> extends IObjectWrapper.Stub {
    private final T zza;

    private ObjectWrapper(T t) {
        this.zza = t;
    }

    @RecentlyNonNull
    @KeepForSdk
    public static <T> T unwrap(@RecentlyNonNull IObjectWrapper iObjectWrapper) {
        if (iObjectWrapper instanceof ObjectWrapper) {
            return ((ObjectWrapper) iObjectWrapper).zza;
        }
        IBinder asBinder = iObjectWrapper.asBinder();
        Field[] declaredFields = asBinder.getClass().getDeclaredFields();
        int length = declaredFields.length;
        Field field = null;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            Field field2 = declaredFields[i2];
            if (!field2.isSynthetic()) {
                i++;
                field = field2;
            }
            i2++;
            i = i;
        }
        if (i == 1) {
            Preconditions.checkNotNull(field);
            if (!field.isAccessible()) {
                field.setAccessible(true);
                try {
                    return (T) field.get(asBinder);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("Could not access the field in remoteBinder.", e);
                } catch (NullPointerException e2) {
                    throw new IllegalArgumentException("Binder object is null.", e2);
                }
            }
            throw new IllegalArgumentException("IObjectWrapper declared field not private!");
        }
        int length2 = declaredFields.length;
        StringBuilder sb = new StringBuilder(64);
        sb.append("Unexpected number of IObjectWrapper declared fields: ");
        sb.append(length2);
        throw new IllegalArgumentException(sb.toString());
    }

    @RecentlyNonNull
    @KeepForSdk
    public static <T> IObjectWrapper wrap(@RecentlyNonNull T t) {
        return new ObjectWrapper(t);
    }
}
