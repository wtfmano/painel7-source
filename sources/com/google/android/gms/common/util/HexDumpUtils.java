package com.google.android.gms.common.util;

import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.common.annotation.KeepForSdk;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public final class HexDumpUtils {
    @RecentlyNullable
    @KeepForSdk
    public static String dump(@RecentlyNonNull byte[] bArr, int i, int i2, boolean z) {
        int length;
        int i3;
        if (bArr == null || (length = bArr.length) == 0 || i < 0 || i2 <= 0 || i + i2 > length) {
            return null;
        }
        StringBuilder sb = new StringBuilder((z ? 75 : 57) * ((i2 + 15) / 16));
        int i4 = 0;
        int i5 = 0;
        int i6 = i2;
        int i7 = i;
        while (i6 > 0) {
            if (i4 == 0) {
                if (i2 < 65536) {
                    sb.append(String.format("%04X:", Integer.valueOf(i7)));
                    i5 = i7;
                } else {
                    sb.append(String.format("%08X:", Integer.valueOf(i7)));
                    i5 = i7;
                }
            } else if (i4 == 8) {
                sb.append(" -");
            }
            sb.append(String.format(" %02X", Integer.valueOf(bArr[i7] & 255)));
            i6--;
            int i8 = i4 + 1;
            if (z && (i8 == 16 || i6 == 0)) {
                int i9 = 16 - i8;
                if (i9 > 0) {
                    for (int i10 = 0; i10 < i9; i10++) {
                        sb.append("   ");
                    }
                }
                if (i9 >= 8) {
                    sb.append("  ");
                }
                sb.append("  ");
                for (int i11 = 0; i11 < i8; i11++) {
                    char c = (char) bArr[i5 + i11];
                    if (c < ' ') {
                        c = '.';
                    } else if (c > '~') {
                        c = '.';
                    }
                    sb.append(c);
                }
            }
            if (i8 == 16 || i6 == 0) {
                sb.append('\n');
                i3 = 0;
            } else {
                i3 = i8;
            }
            i4 = i3;
            i7++;
        }
        return sb.toString();
    }
}
