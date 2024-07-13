package com.google.android.gms.common.wrappers;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class Wrappers {
    private static Wrappers zzb = new Wrappers();
    @Nullable
    private PackageManagerWrapper zza = null;

    @RecentlyNonNull
    @KeepForSdk
    public static PackageManagerWrapper packageManager(@RecentlyNonNull Context context) {
        return zzb.zza(context);
    }

    @RecentlyNonNull
    @VisibleForTesting
    public final synchronized PackageManagerWrapper zza(@RecentlyNonNull Context context) {
        PackageManagerWrapper packageManagerWrapper;
        Context context2 = context;
        synchronized (this) {
            if (this.zza == null) {
                if (context2.getApplicationContext() != null) {
                    context2 = context2.getApplicationContext();
                }
                this.zza = new PackageManagerWrapper(context2);
            }
            packageManagerWrapper = this.zza;
        }
        return packageManagerWrapper;
    }
}
