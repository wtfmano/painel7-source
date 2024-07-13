package com.google.android.gms.common.util;

import android.database.CharArrayBuffer;
import android.graphics.Bitmap;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.ByteArrayOutputStream;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public final class DataUtils {
    @KeepForSdk
    public static void copyStringToBuffer(@Nullable String str, @RecentlyNonNull CharArrayBuffer charArrayBuffer) {
        if (!TextUtils.isEmpty(str)) {
            if (charArrayBuffer.data == null || charArrayBuffer.data.length < str.length()) {
                charArrayBuffer.data = str.toCharArray();
            } else {
                str.getChars(0, str.length(), charArrayBuffer.data, 0);
            }
            charArrayBuffer.sizeCopied = str.length();
            return;
        }
        charArrayBuffer.sizeCopied = 0;
    }

    @RecentlyNonNull
    @KeepForSdk
    public static byte[] loadImageBytes(@RecentlyNonNull Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
