package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@ShowFirstParty
@KeepForSdk
@Deprecated
/* loaded from: classes.dex */
public final class IOUtils {
    private IOUtils() {
    }

    @KeepForSdk
    public static void closeQuietly(@Nullable ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
            }
        }
    }

    @KeepForSdk
    @Deprecated
    public static long copyStream(@RecentlyNonNull InputStream inputStream, @RecentlyNonNull OutputStream outputStream) throws IOException {
        return copyStream(inputStream, outputStream, false, 1024);
    }

    @KeepForSdk
    public static boolean isGzipByteBuffer(@RecentlyNonNull byte[] bArr) {
        return bArr.length > 1 && ((bArr[0] & 255) | ((bArr[1] & 255) << 8)) == 35615;
    }

    @RecentlyNonNull
    @KeepForSdk
    @Deprecated
    public static byte[] readInputStreamFully(@RecentlyNonNull InputStream inputStream) throws IOException {
        return readInputStreamFully(inputStream, true);
    }

    @RecentlyNonNull
    @KeepForSdk
    @Deprecated
    public static byte[] toByteArray(@RecentlyNonNull InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(byteArrayOutputStream);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    @KeepForSdk
    public static void closeQuietly(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    @KeepForSdk
    @Deprecated
    public static long copyStream(@RecentlyNonNull InputStream inputStream, @RecentlyNonNull OutputStream outputStream, boolean z, int i) throws IOException {
        long j;
        byte[] bArr = new byte[i];
        long j2 = 0;
        while (true) {
            j = j2;
            try {
                int read = inputStream.read(bArr, 0, bArr.length);
                if (read == -1) {
                    break;
                }
                long j3 = j + read;
                outputStream.write(bArr, 0, read);
                j2 = j3;
            } catch (Throwable th) {
                if (z) {
                    closeQuietly(inputStream);
                    closeQuietly(outputStream);
                }
                throw th;
            }
        }
        if (z) {
            closeQuietly(inputStream);
            closeQuietly(outputStream);
        }
        return j;
    }

    @RecentlyNonNull
    @KeepForSdk
    @Deprecated
    public static byte[] readInputStreamFully(@RecentlyNonNull InputStream inputStream, boolean z) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copyStream(inputStream, byteArrayOutputStream, z, 1024);
        return byteArrayOutputStream.toByteArray();
    }
}
