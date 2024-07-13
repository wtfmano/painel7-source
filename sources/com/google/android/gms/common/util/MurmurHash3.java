package com.google.android.gms.common.util;

import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class MurmurHash3 {
    private MurmurHash3() {
    }

    @KeepForSdk
    public static int murmurhash3_x86_32(@RecentlyNonNull byte[] bArr, int i, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7 = i3;
        int i8 = i + (i2 & (-4));
        for (int i9 = i; i9 < i8; i9 += 4) {
            int i10 = ((bArr[i9] & 255) | ((bArr[i9 + 1] & 255) << 8) | ((bArr[i9 + 2] & 255) << 16) | (bArr[i9 + 3] << 24)) * (-862048943);
            int i11 = i7 ^ (((i10 << 15) | (i10 >>> 17)) * 461845907);
            i7 = (((i11 << 13) | (i11 >>> 19)) * 5) - 430675100;
        }
        switch (i2 & 3) {
            case 1:
                i5 = 0;
                int i12 = (i5 | (bArr[i8] & 255)) * (-862048943);
                i6 = i7 ^ (((i12 << 15) | (i12 >>> 17)) * 461845907);
                break;
            case 2:
                i4 = 0;
                i5 = i4 | ((bArr[i8 + 1] & 255) << 8);
                int i122 = (i5 | (bArr[i8] & 255)) * (-862048943);
                i6 = i7 ^ (((i122 << 15) | (i122 >>> 17)) * 461845907);
                break;
            case 3:
                i4 = (bArr[i8 + 2] & 255) << 16;
                i5 = i4 | ((bArr[i8 + 1] & 255) << 8);
                int i1222 = (i5 | (bArr[i8] & 255)) * (-862048943);
                i6 = i7 ^ (((i1222 << 15) | (i1222 >>> 17)) * 461845907);
                break;
            default:
                i6 = i7;
                break;
        }
        int i13 = i6 ^ i2;
        int i14 = (i13 ^ (i13 >>> 16)) * (-2048144789);
        int i15 = (i14 ^ (i14 >>> 13)) * (-1028477387);
        return i15 ^ (i15 >>> 16);
    }
}
