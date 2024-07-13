package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@ShowFirstParty
@KeepForSdk
@CheckReturnValue
/* loaded from: classes.dex */
public class GoogleSignatureVerifier {
    @Nullable
    private static GoogleSignatureVerifier zza;
    private final Context zzb;
    private volatile String zzc;

    public GoogleSignatureVerifier(@RecentlyNonNull Context context) {
        this.zzb = context.getApplicationContext();
    }

    @RecentlyNonNull
    @KeepForSdk
    public static GoogleSignatureVerifier getInstance(@RecentlyNonNull Context context) {
        Preconditions.checkNotNull(context);
        synchronized (GoogleSignatureVerifier.class) {
            if (zza == null) {
                zzm.zza(context);
                zza = new GoogleSignatureVerifier(context);
            }
        }
        return zza;
    }

    @Nullable
    static final zzi zza(PackageInfo packageInfo, zzi... zziVarArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzj zzjVar = new zzj(packageInfo.signatures[0].toByteArray());
        for (int i = 0; i < zziVarArr.length; i++) {
            if (zziVarArr[i].equals(zzjVar)) {
                return zziVarArr[i];
            }
        }
        return null;
    }

    public static final boolean zzb(@RecentlyNonNull PackageInfo packageInfo, boolean z) {
        if (packageInfo != null && packageInfo.signatures != null) {
            if ((z ? zza(packageInfo, zzl.zza) : zza(packageInfo, zzl.zza[0])) != null) {
                return true;
            }
        }
        return false;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isPackageGoogleSigned(@RecentlyNonNull String str) {
        zzw zzc = zzc(str, false, false);
        zzc.zzf();
        return zzc.zza;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isUidGoogleSigned(int i) {
        zzw zzd;
        int length;
        String[] packagesForUid = this.zzb.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null && (length = packagesForUid.length) != 0) {
            zzw zzwVar = null;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    zzwVar = zzc(packagesForUid[i2], false, false);
                    if (zzwVar.zza) {
                        zzd = zzwVar;
                        break;
                    }
                    i2++;
                } else {
                    Preconditions.checkNotNull(zzwVar);
                    zzd = zzwVar;
                    break;
                }
            }
        } else {
            zzd = zzw.zzd("no pkgs");
        }
        zzd.zzf();
        return zzd.zza;
    }

    @KeepForSdk
    public boolean isGooglePublicSignedPackage(@RecentlyNonNull PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zzb(packageInfo, false)) {
            return true;
        }
        if (zzb(packageInfo, true)) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    @SuppressLint({"PackageManagerGetSignatures"})
    private final zzw zzc(String str, boolean z, boolean z2) {
        zzw zzd;
        if (str == null) {
            return zzw.zzd("null pkg");
        }
        if (str.equals(this.zzc)) {
            return zzw.zzb();
        }
        if (zzm.zzd()) {
            zzd = zzm.zzb(str, GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb), false, false);
        } else {
            try {
                PackageInfo packageInfo = this.zzb.getPackageManager().getPackageInfo(str, 64);
                boolean honorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb);
                if (packageInfo == null) {
                    zzd = zzw.zzd("null pkg");
                } else if (packageInfo.signatures == null || packageInfo.signatures.length != 1) {
                    zzd = zzw.zzd("single cert required");
                } else {
                    zzj zzjVar = new zzj(packageInfo.signatures[0].toByteArray());
                    String str2 = packageInfo.packageName;
                    zzw zzc = zzm.zzc(str2, zzjVar, honorsDebugCertificates, false);
                    if (!zzc.zza) {
                        zzd = zzc;
                    } else if (packageInfo.applicationInfo == null) {
                        zzd = zzc;
                    } else if ((packageInfo.applicationInfo.flags & 2) != 0) {
                        zzd = zzm.zzc(str2, zzjVar, false, true).zza ? zzw.zzd("debuggable release cert app rejected") : zzc;
                    } else {
                        zzd = zzc;
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                return zzw.zze(str.length() != 0 ? "no pkg ".concat(str) : new String("no pkg "), e);
            }
        }
        if (zzd.zza) {
            this.zzc = str;
        }
        return zzd;
    }
}
