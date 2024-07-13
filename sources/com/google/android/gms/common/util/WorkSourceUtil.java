package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.WorkSource;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class WorkSourceUtil {
    private static final int zza = Process.myUid();
    @Nullable
    private static final Method zzb;
    @Nullable
    private static final Method zzc;
    @Nullable
    private static final Method zzd;
    @Nullable
    private static final Method zze;
    @Nullable
    private static final Method zzf;
    @Nullable
    private static final Method zzg;
    @Nullable
    private static final Method zzh;

    static {
        Method method;
        Method method2;
        Method method3;
        Method method4;
        Method method5;
        Method method6;
        Method method7;
        try {
            method = WorkSource.class.getMethod("add", Integer.TYPE);
        } catch (Exception e) {
            method = null;
        }
        zzb = method;
        if (PlatformVersion.isAtLeastJellyBeanMR2()) {
            try {
                method2 = WorkSource.class.getMethod("add", Integer.TYPE, String.class);
            } catch (Exception e2) {
                method2 = null;
            }
        } else {
            method2 = null;
        }
        zzc = method2;
        try {
            method3 = WorkSource.class.getMethod("size", new Class[0]);
        } catch (Exception e3) {
            method3 = null;
        }
        zzd = method3;
        try {
            method4 = WorkSource.class.getMethod("get", Integer.TYPE);
        } catch (Exception e4) {
            method4 = null;
        }
        zze = method4;
        if (PlatformVersion.isAtLeastJellyBeanMR2()) {
            try {
                method5 = WorkSource.class.getMethod("getName", Integer.TYPE);
            } catch (Exception e5) {
                method5 = null;
            }
        } else {
            method5 = null;
        }
        zzf = method5;
        if (PlatformVersion.isAtLeastP()) {
            try {
                method6 = WorkSource.class.getMethod("createWorkChain", new Class[0]);
            } catch (Exception e6) {
                Log.w("WorkSourceUtil", "Missing WorkChain API createWorkChain", e6);
                method6 = null;
            }
        } else {
            method6 = null;
        }
        zzg = method6;
        if (PlatformVersion.isAtLeastP()) {
            try {
                method7 = Class.forName("android.os.WorkSource$WorkChain").getMethod("addNode", Integer.TYPE, String.class);
            } catch (Exception e7) {
                Log.w("WorkSourceUtil", "Missing WorkChain class", e7);
                method7 = null;
            }
        } else {
            method7 = null;
        }
        zzh = method7;
    }

    private WorkSourceUtil() {
    }

    @RecentlyNullable
    @KeepForSdk
    public static WorkSource fromPackage(@RecentlyNonNull Context context, @Nullable String str) {
        if (context == null || context.getPackageManager() == null || str == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = Wrappers.packageManager(context).getApplicationInfo(str, 0);
            if (applicationInfo == null) {
                Log.e("WorkSourceUtil", str.length() != 0 ? "Could not get applicationInfo from package: ".concat(str) : new String("Could not get applicationInfo from package: "));
                return null;
            }
            int i = applicationInfo.uid;
            WorkSource workSource = new WorkSource();
            zza(workSource, i, str);
            return workSource;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("WorkSourceUtil", str.length() != 0 ? "Could not find package: ".concat(str) : new String("Could not find package: "));
            return null;
        }
    }

    @RecentlyNullable
    @KeepForSdk
    public static WorkSource fromPackageAndModuleExperimentalPi(@RecentlyNonNull Context context, @RecentlyNonNull String str, @RecentlyNonNull String str2) {
        int i;
        if (context == null || context.getPackageManager() == null || str2 == null || str == null) {
            Log.w("WorkSourceUtil", "Unexpected null arguments");
            return null;
        }
        try {
            ApplicationInfo applicationInfo = Wrappers.packageManager(context).getApplicationInfo(str, 0);
            if (applicationInfo == null) {
                Log.e("WorkSourceUtil", str.length() != 0 ? "Could not get applicationInfo from package: ".concat(str) : new String("Could not get applicationInfo from package: "));
                i = -1;
            } else {
                i = applicationInfo.uid;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("WorkSourceUtil", str.length() != 0 ? "Could not find package: ".concat(str) : new String("Could not find package: "));
            i = -1;
        }
        if (i < 0) {
            return null;
        }
        WorkSource workSource = new WorkSource();
        if (zzg == null || zzh == null) {
            zza(workSource, i, str);
        } else {
            try {
                Object invoke = zzg.invoke(workSource, new Object[0]);
                if (i != zza) {
                    zzh.invoke(invoke, Integer.valueOf(i), str);
                }
                zzh.invoke(invoke, Integer.valueOf(zza), str2);
            } catch (Exception e2) {
                Log.w("WorkSourceUtil", "Unable to assign chained blame through WorkSource", e2);
            }
        }
        return workSource;
    }

    @RecentlyNonNull
    @KeepForSdk
    public static List<String> getNames(@Nullable WorkSource workSource) {
        int i;
        String str;
        ArrayList arrayList = new ArrayList();
        if (workSource == null) {
            i = 0;
        } else if (zzd != null) {
            try {
                Object invoke = zzd.invoke(workSource, new Object[0]);
                Preconditions.checkNotNull(invoke);
                i = ((Integer) invoke).intValue();
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
                i = 0;
            }
        } else {
            i = 0;
        }
        if (i != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                if (zzf != null) {
                    try {
                        str = (String) zzf.invoke(workSource, Integer.valueOf(i2));
                    } catch (Exception e2) {
                        Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e2);
                        str = null;
                    }
                } else {
                    str = null;
                }
                if (!Strings.isEmptyOrWhitespace(str)) {
                    Preconditions.checkNotNull(str);
                    arrayList.add(str);
                }
            }
            return arrayList;
        }
        return arrayList;
    }

    public static void zza(@RecentlyNonNull WorkSource workSource, int i, @Nullable String str) {
        if (zzc == null) {
            if (zzb != null) {
                try {
                    zzb.invoke(workSource, Integer.valueOf(i));
                    return;
                } catch (Exception e) {
                    Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
                    return;
                }
            }
            return;
        }
        try {
            zzc.invoke(workSource, Integer.valueOf(i), str);
        } catch (Exception e2) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e2);
        }
    }

    @KeepForSdk
    public static boolean hasWorkSourcePermission(@RecentlyNonNull Context context) {
        if (context != null && context.getPackageManager() != null && Wrappers.packageManager(context).checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) == 0) {
            return true;
        }
        return false;
    }
}
