package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.common.util.VisibleForTesting;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@VisibleForTesting
/* loaded from: classes.dex */
public final class zze implements ServiceConnection {
    final /* synthetic */ BaseGmsClient zza;
    private final int zzb;

    public zze(BaseGmsClient baseGmsClient, int i) {
        this.zza = baseGmsClient;
        this.zzb = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v24, types: [com.google.android.gms.common.internal.IGmsServiceBroker] */
    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Object obj;
        zzab zzabVar;
        if (iBinder == null) {
            BaseGmsClient.zzc(this.zza, 16);
            return;
        }
        obj = this.zza.zzq;
        synchronized (obj) {
            BaseGmsClient baseGmsClient = this.zza;
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IGmsServiceBroker)) {
                zzabVar = new zzab(iBinder);
            } else {
                zzabVar = (IGmsServiceBroker) queryLocalInterface;
            }
            baseGmsClient.zzr = zzabVar;
        }
        this.zza.zzb(0, null, this.zzb);
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        Object obj;
        obj = this.zza.zzq;
        synchronized (obj) {
            this.zza.zzr = null;
        }
        Handler handler = this.zza.zzb;
        handler.sendMessage(handler.obtainMessage(6, this.zzb, 1));
    }
}
