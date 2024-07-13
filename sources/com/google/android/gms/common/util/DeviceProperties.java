package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.annotation.KeepForSdk;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public final class DeviceProperties {
    @Nullable
    private static Boolean zza;
    @Nullable
    private static Boolean zzb;
    @Nullable
    private static Boolean zzc;
    @Nullable
    private static Boolean zzd;
    @Nullable
    private static Boolean zze;
    @Nullable
    private static Boolean zzf;
    @Nullable
    private static Boolean zzg;
    @Nullable
    private static Boolean zzh;

    private DeviceProperties() {
    }

    @KeepForSdk
    public static boolean isAuto(@RecentlyNonNull Context context) {
        return isAuto(context.getPackageManager());
    }

    @KeepForSdk
    @Deprecated
    public static boolean isFeaturePhone(@RecentlyNonNull Context context) {
        return false;
    }

    @KeepForSdk
    public static boolean isLatchsky(@RecentlyNonNull Context context) {
        boolean z;
        if (zze == null) {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.hasSystemFeature("com.google.android.feature.services_updater")) {
                z = packageManager.hasSystemFeature("cn.google.services");
            } else {
                z = false;
            }
            zze = Boolean.valueOf(z);
        }
        return zze.booleanValue();
    }

    @KeepForSdk
    @TargetApi(21)
    public static boolean isSidewinder(@RecentlyNonNull Context context) {
        return zza(context);
    }

    @KeepForSdk
    public static boolean isTv(@RecentlyNonNull Context context) {
        return isTv(context.getPackageManager());
    }

    @KeepForSdk
    public static boolean isUserBuild() {
        int i = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        return "user".equals(Build.TYPE);
    }

    @KeepForSdk
    @TargetApi(20)
    public static boolean isWearable(@RecentlyNonNull Context context) {
        return isWearable(context.getPackageManager());
    }

    @KeepForSdk
    @TargetApi(26)
    public static boolean isWearableWithoutPlayStore(@RecentlyNonNull Context context) {
        boolean z;
        if (!isWearable(context)) {
            z = false;
        } else if (!PlatformVersion.isAtLeastN()) {
            z = true;
        } else if (!zza(context)) {
            z = false;
        } else if (!PlatformVersion.isAtLeastO()) {
            return true;
        } else {
            z = false;
        }
        return z;
    }

    @TargetApi(21)
    public static boolean zza(@RecentlyNonNull Context context) {
        boolean z;
        if (zzd == null) {
            if (PlatformVersion.isAtLeastLollipop()) {
                z = context.getPackageManager().hasSystemFeature("cn.google");
            } else {
                z = false;
            }
            zzd = Boolean.valueOf(z);
        }
        return zzd.booleanValue();
    }

    public static boolean zzb(@RecentlyNonNull Context context) {
        boolean z;
        if (zzf == null) {
            if (context.getPackageManager().hasSystemFeature("android.hardware.type.iot")) {
                z = true;
            } else {
                z = context.getPackageManager().hasSystemFeature("android.hardware.type.embedded");
            }
            zzf = Boolean.valueOf(z);
        }
        return zzf.booleanValue();
    }

    @KeepForSdk
    public static boolean isAuto(@RecentlyNonNull PackageManager packageManager) {
        boolean z;
        if (zzg == null) {
            if (PlatformVersion.isAtLeastO()) {
                z = packageManager.hasSystemFeature("android.hardware.type.automotive");
            } else {
                z = false;
            }
            zzg = Boolean.valueOf(z);
        }
        return zzg.booleanValue();
    }

    @KeepForSdk
    public static boolean isTv(@RecentlyNonNull PackageManager packageManager) {
        boolean z;
        if (zzh == null) {
            if (packageManager.hasSystemFeature("com.google.android.tv")) {
                z = true;
            } else if (packageManager.hasSystemFeature("android.hardware.type.television")) {
                z = true;
            } else {
                z = packageManager.hasSystemFeature("android.software.leanback");
            }
            zzh = Boolean.valueOf(z);
        }
        return zzh.booleanValue();
    }

    @KeepForSdk
    @TargetApi(20)
    public static boolean isWearable(@RecentlyNonNull PackageManager packageManager) {
        boolean z;
        if (zzc == null) {
            if (PlatformVersion.isAtLeastKitKatWatch()) {
                z = packageManager.hasSystemFeature("android.hardware.type.watch");
            } else {
                z = false;
            }
            zzc = Boolean.valueOf(z);
        }
        return zzc.booleanValue();
    }

    @KeepForSdk
    public static boolean isTablet(@RecentlyNonNull Resources resources) {
        boolean z;
        if (resources == null) {
            return false;
        }
        if (zza == null) {
            if ((resources.getConfiguration().screenLayout & 15) > 3) {
                z = true;
            } else {
                if (zzb == null) {
                    Configuration configuration = resources.getConfiguration();
                    zzb = Boolean.valueOf((configuration.screenLayout & 15) <= 3 ? configuration.smallestScreenWidthDp >= 600 : false);
                }
                z = zzb.booleanValue();
            }
            zza = Boolean.valueOf(z);
        }
        return zza.booleanValue();
    }
}
