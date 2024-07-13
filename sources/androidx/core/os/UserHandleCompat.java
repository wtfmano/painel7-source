package androidx.core.os;

import android.os.Build;
import android.os.UserHandle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(17)
/* loaded from: classes.dex */
public class UserHandleCompat {
    @Nullable
    private static Method sGetUserIdMethod;
    @Nullable
    private static Constructor<UserHandle> sUserHandleConstructor;

    private UserHandleCompat() {
    }

    @NonNull
    public static UserHandle getUserHandleForUid(int uid) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.getUserHandleForUid(uid);
        }
        try {
            Integer userId = (Integer) getGetUserIdMethod().invoke(null, Integer.valueOf(uid));
            return getUserHandleConstructor().newInstance(userId);
        } catch (IllegalAccessException e) {
            Error error = new IllegalAccessError();
            error.initCause(e);
            throw error;
        } catch (InstantiationException e2) {
            Error error2 = new InstantiationError();
            error2.initCause(e2);
            throw error2;
        } catch (NoSuchMethodException e3) {
            Error error3 = new NoSuchMethodError();
            error3.initCause(e3);
            throw error3;
        } catch (InvocationTargetException e4) {
            throw new RuntimeException(e4);
        }
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    private static class Api24Impl {
        private Api24Impl() {
        }

        @NonNull
        static UserHandle getUserHandleForUid(int uid) {
            return UserHandle.getUserHandleForUid(uid);
        }
    }

    private static Method getGetUserIdMethod() throws NoSuchMethodException {
        if (sGetUserIdMethod == null) {
            sGetUserIdMethod = UserHandle.class.getDeclaredMethod("getUserId", Integer.TYPE);
            sGetUserIdMethod.setAccessible(true);
        }
        return sGetUserIdMethod;
    }

    private static Constructor<UserHandle> getUserHandleConstructor() throws NoSuchMethodException {
        if (sUserHandleConstructor == null) {
            sUserHandleConstructor = UserHandle.class.getDeclaredConstructor(Integer.TYPE);
            sUserHandleConstructor.setAccessible(true);
        }
        return sUserHandleConstructor;
    }
}
