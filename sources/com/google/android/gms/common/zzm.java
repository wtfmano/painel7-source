package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.common.internal.zzae;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import java.util.concurrent.Callable;
import javax.annotation.CheckReturnValue;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@CheckReturnValue
/* loaded from: classes.dex */
public final class zzm {
    private static volatile zzae zze;
    private static Context zzg;
    static final zzk zza = new zze(zzi.zzf("0\u0082\u0005È0\u0082\u0003° \u0003\u0002\u0001\u0002\u0002\u0014\u0010\u008ae\bsù/\u008eQí"));
    static final zzk zzb = new zzf(zzi.zzf("0\u0082\u0006\u00040\u0082\u0003ì \u0003\u0002\u0001\u0002\u0002\u0014\u0003£²\u00ad×árÊkì"));
    static final zzk zzc = new zzg(zzi.zzf("0\u0082\u0004C0\u0082\u0003+ \u0003\u0002\u0001\u0002\u0002\t\u0000Âà\u0087FdJ0\u008d0"));
    static final zzk zzd = new zzh(zzi.zzf("0\u0082\u0004¨0\u0082\u0003\u0090 \u0003\u0002\u0001\u0002\u0002\t\u0000Õ\u0085¸l}ÓNõ0"));
    private static final Object zzf = new Object();

    public static synchronized void zza(Context context) {
        synchronized (zzm.class) {
            if (zzg != null) {
                Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
            } else if (context != null) {
                zzg = context.getApplicationContext();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r9v1, types: [com.google.android.gms.dynamic.IObjectWrapper, android.os.IBinder] */
    public static zzw zzb(String str, boolean z, boolean z2, boolean z3) {
        zzw zze2;
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            Preconditions.checkNotNull(zzg);
            try {
                zzf();
                try {
                    zzq zzf2 = zze.zzf(new zzn(str, z, false, ObjectWrapper.wrap(zzg), false));
                    if (zzf2.zza()) {
                        zze2 = zzw.zzb();
                    } else {
                        String zzb2 = zzf2.zzb();
                        if (zzb2 == null) {
                            zzb2 = "error checking package certificate";
                        }
                        zze2 = zzf2.zzc() == 4 ? zzw.zze(zzb2, new PackageManager.NameNotFoundException()) : zzw.zzd(zzb2);
                    }
                } catch (RemoteException e) {
                    Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                    zze2 = zzw.zze("module call", e);
                }
            } catch (DynamiteModule.LoadingException e2) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e2);
                String valueOf = String.valueOf(e2.getMessage());
                zze2 = zzw.zze(valueOf.length() != 0 ? "module init: ".concat(valueOf) : new String("module init: "), e2);
            }
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return zze2;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th;
        }
    }

    public static zzw zzc(String str, zzi zziVar, boolean z, boolean z2) {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            return zzg(str, zziVar, z, z2);
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.os.StrictMode$ThreadPolicy] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [boolean] */
    public static boolean zzd() {
        ?? allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            try {
                zzf();
                boolean zzg2 = zze.zzg();
                StrictMode.setThreadPolicy(allowThreadDiskReads);
                allowThreadDiskReads = zzg2;
                return allowThreadDiskReads;
            } catch (RemoteException | DynamiteModule.LoadingException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                StrictMode.setThreadPolicy(allowThreadDiskReads);
                return false;
            }
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzw zzg(String str, zzi zziVar, boolean z, boolean z2) {
        try {
            zzf();
            Preconditions.checkNotNull(zzg);
            try {
                return zze.zze(new zzs(str, zziVar, z, z2), ObjectWrapper.wrap(zzg.getPackageManager())) ? zzw.zzb() : zzw.zzc(new Callable(z, str, zziVar) { // from class: com.google.android.gms.common.zzd
                    private final boolean zza;
                    private final String zzb;
                    private final zzi zzc;

                    /* JADX INFO: Access modifiers changed from: package-private */
                    {
                        this.zza = z;
                        this.zzb = str;
                        this.zzc = zziVar;
                    }

                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        String zzg2;
                        zzg2 = zzw.zzg(this.zzb, this.zzc, this.zza, !r8 ? zzm.zzg(r9, r10, true, false).zza : false);
                        return zzg2;
                    }
                });
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                return zzw.zze("module call", e);
            }
        } catch (DynamiteModule.LoadingException e2) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e2);
            String valueOf = String.valueOf(e2.getMessage());
            return zzw.zze(valueOf.length() != 0 ? "module init: ".concat(valueOf) : new String("module init: "), e2);
        }
    }

    private static void zzf() throws DynamiteModule.LoadingException {
        if (zze == null) {
            Preconditions.checkNotNull(zzg);
            synchronized (zzf) {
                if (zze == null) {
                    zze = zzad.zzb(DynamiteModule.load(zzg, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.googlecertificates").instantiate("com.google.android.gms.common.GoogleCertificatesImpl"));
                }
            }
        }
    }
}
