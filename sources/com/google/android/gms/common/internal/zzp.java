package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.core.os.EnvironmentCompat;
import java.util.HashMap;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzp implements Handler.Callback {
    final /* synthetic */ zzq zza;

    public /* synthetic */ zzp(zzq zzqVar, zzn zznVar) {
        this.zza = zzqVar;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        HashMap hashMap;
        HashMap hashMap2;
        HashMap hashMap3;
        HashMap hashMap4;
        HashMap hashMap5;
        switch (message.what) {
            case 0:
                hashMap3 = this.zza.zza;
                synchronized (hashMap3) {
                    zzm zzmVar = (zzm) message.obj;
                    hashMap4 = this.zza.zza;
                    zzo zzoVar = (zzo) hashMap4.get(zzmVar);
                    if (zzoVar != null && zzoVar.zzh()) {
                        if (zzoVar.zze()) {
                            zzoVar.zzb("GmsClientSupervisor");
                        }
                        hashMap5 = this.zza.zza;
                        hashMap5.remove(zzmVar);
                    }
                }
                return true;
            case 1:
                hashMap = this.zza.zza;
                synchronized (hashMap) {
                    zzm zzmVar2 = (zzm) message.obj;
                    hashMap2 = this.zza.zza;
                    zzo zzoVar2 = (zzo) hashMap2.get(zzmVar2);
                    if (zzoVar2 != null && zzoVar2.zzf() == 3) {
                        String valueOf = String.valueOf(zzmVar2);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47);
                        sb.append("Timeout waiting for ServiceConnection callback ");
                        sb.append(valueOf);
                        Log.e("GmsClientSupervisor", sb.toString(), new Exception());
                        ComponentName zzj = zzoVar2.zzj();
                        if (zzj == null) {
                            zzj = zzmVar2.zzb();
                        }
                        if (zzj == null) {
                            String zza = zzmVar2.zza();
                            Preconditions.checkNotNull(zza);
                            zzj = new ComponentName(zza, EnvironmentCompat.MEDIA_UNKNOWN);
                        }
                        zzoVar2.onServiceDisconnected(zzj);
                    }
                }
                return true;
            default:
                return false;
        }
    }
}
