package com.google.android.gms.common.stats;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class ConnectionTracker {
    private static final Object zzb = new Object();
    @Nullable
    private static volatile ConnectionTracker zzc;
    @RecentlyNonNull
    @VisibleForTesting
    public ConcurrentHashMap<ServiceConnection, ServiceConnection> zza = new ConcurrentHashMap<>();

    private ConnectionTracker() {
    }

    @RecentlyNonNull
    @KeepForSdk
    public static ConnectionTracker getInstance() {
        if (zzc == null) {
            synchronized (zzb) {
                if (zzc == null) {
                    zzc = new ConnectionTracker();
                }
            }
        }
        ConnectionTracker connectionTracker = zzc;
        Preconditions.checkNotNull(connectionTracker);
        return connectionTracker;
    }

    @SuppressLint({"UntrackedBindService"})
    private final boolean zzb(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i, boolean z) {
        boolean bindService;
        ComponentName component = intent.getComponent();
        if (component != null) {
            String packageName = component.getPackageName();
            "com.google.android.gms".equals(packageName);
            try {
                if ((Wrappers.packageManager(context).getApplicationInfo(packageName, 0).flags & 2097152) != 0) {
                    Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
                    return false;
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        if (zzc(serviceConnection)) {
            ServiceConnection putIfAbsent = this.zza.putIfAbsent(serviceConnection, serviceConnection);
            if (putIfAbsent != null && serviceConnection != putIfAbsent) {
                Log.w("ConnectionTracker", String.format("Duplicate binding with the same ServiceConnection: %s, %s, %s.", serviceConnection, str, intent.getAction()));
            }
            try {
                boolean bindService2 = context.bindService(intent, serviceConnection, i);
                if (!bindService2) {
                    this.zza.remove(serviceConnection, serviceConnection);
                    return false;
                }
                bindService = bindService2;
            } catch (Throwable th) {
                this.zza.remove(serviceConnection, serviceConnection);
                throw th;
            }
        } else {
            bindService = context.bindService(intent, serviceConnection, i);
        }
        return bindService;
    }

    private static boolean zzc(ServiceConnection serviceConnection) {
        return !(serviceConnection instanceof zzr);
    }

    @SuppressLint({"UntrackedBindService"})
    private static void zzd(Context context, ServiceConnection serviceConnection) {
        try {
            context.unbindService(serviceConnection);
        } catch (IllegalArgumentException e) {
        } catch (IllegalStateException e2) {
        } catch (NoSuchElementException e3) {
        }
    }

    @KeepForSdk
    public boolean bindService(@RecentlyNonNull Context context, @RecentlyNonNull Intent intent, @RecentlyNonNull ServiceConnection serviceConnection, int i) {
        return zzb(context, context.getClass().getName(), intent, serviceConnection, i, true);
    }

    @KeepForSdk
    @SuppressLint({"UntrackedBindService"})
    public void unbindService(@RecentlyNonNull Context context, @RecentlyNonNull ServiceConnection serviceConnection) {
        if (!zzc(serviceConnection) || !this.zza.containsKey(serviceConnection)) {
            zzd(context, serviceConnection);
            return;
        }
        try {
            zzd(context, this.zza.get(serviceConnection));
        } finally {
            this.zza.remove(serviceConnection);
        }
    }

    @KeepForSdk
    @SuppressLint({"UntrackedBindService"})
    public void unbindServiceSafe(@RecentlyNonNull Context context, @RecentlyNonNull ServiceConnection serviceConnection) {
        try {
            unbindService(context, serviceConnection);
        } catch (IllegalArgumentException e) {
        }
    }

    public final boolean zza(@RecentlyNonNull Context context, @RecentlyNonNull String str, @RecentlyNonNull Intent intent, @RecentlyNonNull ServiceConnection serviceConnection, int i) {
        return zzb(context, str, intent, serviceConnection, i, true);
    }
}
