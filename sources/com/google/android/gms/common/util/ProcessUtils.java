package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class ProcessUtils {
    @Nullable
    private static String zza = null;
    private static int zzb = 0;

    private ProcessUtils() {
    }

    @RecentlyNullable
    @KeepForSdk
    public static String getMyProcessName() {
        Throwable th;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        String str;
        if (zza == null) {
            if (zzb == 0) {
                zzb = Process.myPid();
            }
            int i = zzb;
            if (i <= 0) {
                str = null;
            } else {
                try {
                    StringBuilder sb = new StringBuilder(25);
                    sb.append("/proc/");
                    sb.append(i);
                    sb.append("/cmdline");
                    String sb2 = sb.toString();
                    StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
                    try {
                        BufferedReader bufferedReader3 = new BufferedReader(new FileReader(sb2));
                        try {
                            String readLine = bufferedReader3.readLine();
                            Preconditions.checkNotNull(readLine);
                            str = readLine.trim();
                            IOUtils.closeQuietly(bufferedReader3);
                        } catch (IOException e) {
                            bufferedReader2 = bufferedReader3;
                            IOUtils.closeQuietly(bufferedReader2);
                            str = null;
                            zza = str;
                            return zza;
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedReader = bufferedReader3;
                            IOUtils.closeQuietly(bufferedReader);
                            throw th;
                        }
                    } finally {
                        StrictMode.setThreadPolicy(allowThreadDiskReads);
                    }
                } catch (IOException e2) {
                    bufferedReader2 = null;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = null;
                }
            }
            zza = str;
        }
        return zza;
    }
}
