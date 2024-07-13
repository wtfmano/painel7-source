package androidx.core.util;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public class ObjectsCompat {
    private ObjectsCompat() {
    }

    public static boolean equals(@Nullable Object a, @Nullable Object b) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Objects.equals(a, b);
        }
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(@Nullable Object o) {
        if (o != null) {
            return o.hashCode();
        }
        return 0;
    }

    public static int hash(@Nullable Object... values) {
        return Build.VERSION.SDK_INT >= 19 ? Objects.hash(values) : Arrays.hashCode(values);
    }

    @Nullable
    public static String toString(@Nullable Object o, @Nullable String nullDefault) {
        if (o != null) {
            String nullDefault2 = o.toString();
            return nullDefault2;
        }
        return nullDefault;
    }

    @NonNull
    public static <T> T requireNonNull(@Nullable T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    @NonNull
    public static <T> T requireNonNull(@Nullable T obj, @NonNull String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
        return obj;
    }
}
