package com.google.android.gms.common.util;

import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class MapUtils {
    @KeepForSdk
    public static void writeStringMapToJson(@RecentlyNonNull StringBuilder sb, @RecentlyNonNull HashMap<String, String> hashMap) {
        sb.append("{");
        Iterator<String> it = hashMap.keySet().iterator();
        boolean z = true;
        while (true) {
            boolean z2 = z;
            if (it.hasNext()) {
                String next = it.next();
                if (!z2) {
                    sb.append(",");
                }
                String str = hashMap.get(next);
                sb.append("\"");
                sb.append(next);
                sb.append("\":");
                if (str == null) {
                    sb.append("null");
                    z = false;
                } else {
                    sb.append("\"");
                    sb.append(str);
                    sb.append("\"");
                    z = false;
                }
            } else {
                sb.append("}");
                return;
            }
        }
    }
}
