package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamite.DynamiteModule;
import java.lang.reflect.Method;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public class ProviderInstaller {
    @RecentlyNonNull
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static final GoogleApiAvailabilityLight zza = GoogleApiAvailabilityLight.getInstance();
    private static final Object zzb = new Object();
    @GuardedBy("ProviderInstaller.lock")
    private static Method zzc = null;
    @GuardedBy("ProviderInstaller.lock")
    private static Method zzd = null;

    /* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
    /* loaded from: classes.dex */
    public interface ProviderInstallListener {
        void onProviderInstallFailed(int i, @Nullable Intent intent);

        void onProviderInstalled();
    }

    public static void installIfNeeded(@RecentlyNonNull Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        Context context2;
        Preconditions.checkNotNull(context, "Context must not be null");
        zza.verifyGooglePlayServicesIsAvailable(context, 11925000);
        synchronized (zzb) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            try {
                context2 = DynamiteModule.load(context, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.providerinstaller.dynamite").getModuleContext();
            } catch (DynamiteModule.LoadingException e) {
                String valueOf = String.valueOf(e.getMessage());
                Log.w("ProviderInstaller", valueOf.length() != 0 ? "Failed to load providerinstaller module: ".concat(valueOf) : new String("Failed to load providerinstaller module: "));
                context2 = null;
            }
            if (context2 == null) {
                long elapsedRealtime2 = SystemClock.elapsedRealtime();
                Context remoteContext = GooglePlayServicesUtilLight.getRemoteContext(context);
                if (remoteContext != null) {
                    try {
                        if (zzd == null) {
                            zzd = zzc(remoteContext, "com.google.android.gms.common.security.ProviderInstallerImpl", "reportRequestStats", new Class[]{Context.class, Long.TYPE, Long.TYPE});
                        }
                        zzd.invoke(null, context, Long.valueOf(elapsedRealtime), Long.valueOf(elapsedRealtime2));
                    } catch (Exception e2) {
                        String valueOf2 = String.valueOf(e2.getMessage());
                        Log.w("ProviderInstaller", valueOf2.length() != 0 ? "Failed to report request stats: ".concat(valueOf2) : new String("Failed to report request stats: "));
                    }
                }
                if (remoteContext != null) {
                    zzb(remoteContext, context, "com.google.android.gms.common.security.ProviderInstallerImpl");
                    return;
                } else {
                    Log.e("ProviderInstaller", "Failed to get remote context");
                    throw new GooglePlayServicesNotAvailableException(8);
                }
            }
            zzb(context2, context, "com.google.android.gms.providerinstaller.ProviderInstallerImpl");
        }
    }

    public static void installIfNeededAsync(@RecentlyNonNull Context context, @RecentlyNonNull ProviderInstallListener providerInstallListener) {
        Preconditions.checkNotNull(context, "Context must not be null");
        Preconditions.checkNotNull(providerInstallListener, "Listener must not be null");
        Preconditions.checkMainThread("Must be called on the UI thread");
        new zza(context, providerInstallListener).execute(new Void[0]);
    }

    @GuardedBy("ProviderInstaller.lock")
    private static void zzb(Context context, Context context2, String str) throws GooglePlayServicesNotAvailableException {
        try {
            if (zzc == null) {
                zzc = zzc(context, str, "insertProvider", new Class[]{Context.class});
            }
            zzc.invoke(null, context);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (Log.isLoggable("ProviderInstaller", 6)) {
                String valueOf = String.valueOf(cause == null ? e.getMessage() : cause.getMessage());
                Log.e("ProviderInstaller", valueOf.length() != 0 ? "Failed to install provider: ".concat(valueOf) : new String("Failed to install provider: "));
            }
            throw new GooglePlayServicesNotAvailableException(8);
        }
    }

    private static Method zzc(Context context, String str, String str2, Class[] clsArr) throws ClassNotFoundException, NoSuchMethodException {
        return context.getClassLoader().loadClass(str).getMethod(str2, clsArr);
    }
}
