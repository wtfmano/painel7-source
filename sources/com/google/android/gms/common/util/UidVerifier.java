package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.wrappers.Wrappers;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public final class UidVerifier {
    private UidVerifier() {
    }

    @KeepForSdk
    public static boolean isGooglePlayServicesUid(@RecentlyNonNull Context context, int i) {
        if (uidHasPackageName(context, i, "com.google.android.gms")) {
            try {
                return GoogleSignatureVerifier.getInstance(context).isGooglePublicSignedPackage(context.getPackageManager().getPackageInfo("com.google.android.gms", 64));
            } catch (PackageManager.NameNotFoundException e) {
                if (Log.isLoggable("UidVerifier", 3)) {
                    Log.d("UidVerifier", "Package manager can't find google play services package, defaulting to false");
                }
                return false;
            }
        }
        return false;
    }

    @KeepForSdk
    @TargetApi(19)
    public static boolean uidHasPackageName(@RecentlyNonNull Context context, int i, @RecentlyNonNull String str) {
        return Wrappers.packageManager(context).zza(i, str);
    }
}
