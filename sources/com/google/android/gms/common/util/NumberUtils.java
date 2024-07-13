package com.google.android.gms.common.util;

import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@VisibleForTesting
@KeepForSdk
/* loaded from: classes.dex */
public class NumberUtils {
    private NumberUtils() {
    }

    @KeepForSdk
    public static long parseHexLong(@RecentlyNonNull String str) {
        if (str.length() > 16) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 37);
            sb.append("Invalid input: ");
            sb.append(str);
            sb.append(" exceeds 16 characters");
            throw new NumberFormatException(sb.toString());
        } else if (str.length() == 16) {
            return Long.parseLong(str.substring(8), 16) | (Long.parseLong(str.substring(0, 8), 16) << 32);
        } else {
            return Long.parseLong(str, 16);
        }
    }
}
