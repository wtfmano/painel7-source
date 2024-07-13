package androidx.core.telephony;

import android.os.Build;
import android.telephony.SubscriptionManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(22)
/* loaded from: classes.dex */
public class SubscriptionManagerCompat {
    private static Method sGetSlotIndexMethod;

    public static int getSlotIndex(int subId) {
        int i = -1;
        if (subId == -1) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.getSlotIndex(subId);
        }
        try {
            if (sGetSlotIndexMethod == null) {
                if (Build.VERSION.SDK_INT >= 26) {
                    sGetSlotIndexMethod = SubscriptionManager.class.getDeclaredMethod("getSlotIndex", Integer.TYPE);
                } else {
                    sGetSlotIndexMethod = SubscriptionManager.class.getDeclaredMethod("getSlotId", Integer.TYPE);
                }
                sGetSlotIndexMethod.setAccessible(true);
            }
            Integer slotIdx = (Integer) sGetSlotIndexMethod.invoke(null, Integer.valueOf(subId));
            if (slotIdx != null) {
                i = slotIdx.intValue();
                return i;
            }
            return -1;
        } catch (IllegalAccessException e) {
            return i;
        } catch (NoSuchMethodException e2) {
            return i;
        } catch (InvocationTargetException e3) {
            return i;
        }
    }

    private SubscriptionManagerCompat() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(29)
    /* loaded from: classes.dex */
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static int getSlotIndex(int subId) {
            return SubscriptionManager.getSlotIndex(subId);
        }
    }
}
