package androidx.core.telephony;

import android.annotation.SuppressLint;
import android.os.Build;
import android.telephony.TelephonyManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class TelephonyManagerCompat {
    private static Method sGetDeviceIdMethod;
    private static Method sGetSubIdMethod;

    @Nullable
    @RequiresPermission("android.permission.READ_PHONE_STATE")
    @SuppressLint({"MissingPermission"})
    public static String getImei(@NonNull TelephonyManager telephonyManager) {
        int subId;
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.getImei(telephonyManager);
        }
        if (Build.VERSION.SDK_INT >= 22 && (subId = getSubscriptionId(telephonyManager)) != Integer.MAX_VALUE && subId != -1) {
            int slotIndex = SubscriptionManagerCompat.getSlotIndex(subId);
            if (Build.VERSION.SDK_INT >= 23) {
                return Api23Impl.getDeviceId(telephonyManager, slotIndex);
            }
            try {
                if (sGetDeviceIdMethod == null) {
                    sGetDeviceIdMethod = TelephonyManager.class.getDeclaredMethod("getDeviceId", Integer.TYPE);
                    sGetDeviceIdMethod.setAccessible(true);
                }
                return (String) sGetDeviceIdMethod.invoke(telephonyManager, Integer.valueOf(slotIndex));
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                return null;
            }
        }
        return telephonyManager.getDeviceId();
    }

    @SuppressLint({"SoonBlockedPrivateApi"})
    public static int getSubscriptionId(@NonNull TelephonyManager telephonyManager) {
        if (Build.VERSION.SDK_INT >= 30) {
            return Api30Impl.getSubscriptionId(telephonyManager);
        }
        if (Build.VERSION.SDK_INT >= 22) {
            try {
                if (sGetSubIdMethod == null) {
                    sGetSubIdMethod = TelephonyManager.class.getDeclaredMethod("getSubId", new Class[0]);
                    sGetSubIdMethod.setAccessible(true);
                }
                Integer subId = (Integer) sGetSubIdMethod.invoke(telephonyManager, new Object[0]);
                if (subId != null && subId.intValue() != -1) {
                    return subId.intValue();
                }
            } catch (IllegalAccessException e) {
            } catch (NoSuchMethodException e2) {
            } catch (InvocationTargetException e3) {
            }
        }
        return Integer.MAX_VALUE;
    }

    private TelephonyManagerCompat() {
    }

    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class Api30Impl {
        private Api30Impl() {
        }

        @DoNotInline
        static int getSubscriptionId(TelephonyManager telephonyManager) {
            return telephonyManager.getSubscriptionId();
        }
    }

    @RequiresApi(26)
    /* loaded from: classes.dex */
    private static class Api26Impl {
        private Api26Impl() {
        }

        @SuppressLint({"MissingPermission"})
        @Nullable
        @RequiresPermission("android.permission.READ_PHONE_STATE")
        @DoNotInline
        static String getImei(TelephonyManager telephonyManager) {
            return telephonyManager.getImei();
        }
    }

    @RequiresApi(23)
    /* loaded from: classes.dex */
    private static class Api23Impl {
        private Api23Impl() {
        }

        @SuppressLint({"MissingPermission"})
        @Nullable
        @RequiresPermission("android.permission.READ_PHONE_STATE")
        @DoNotInline
        static String getDeviceId(TelephonyManager telephonyManager, int slotIndex) {
            return telephonyManager.getDeviceId(slotIndex);
        }
    }
}
