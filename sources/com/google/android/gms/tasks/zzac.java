package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.ExecutionException;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tasks@@17.2.1 */
/* loaded from: classes.dex */
public final class zzac implements zzab {
    private final Object zza = new Object();
    private final int zzb;
    private final zzw<Void> zzc;
    @GuardedBy("mLock")
    private int zzd;
    @GuardedBy("mLock")
    private int zze;
    @GuardedBy("mLock")
    private int zzf;
    @GuardedBy("mLock")
    private Exception zzg;
    @GuardedBy("mLock")
    private boolean zzh;

    public zzac(int i, zzw<Void> zzwVar) {
        this.zzb = i;
        this.zzc = zzwVar;
    }

    @GuardedBy("mLock")
    private final void zza() {
        int i = this.zzd;
        int i2 = this.zze;
        int i3 = this.zzf;
        int i4 = this.zzb;
        if (i + i2 + i3 == i4) {
            if (this.zzg != null) {
                zzw<Void> zzwVar = this.zzc;
                StringBuilder sb = new StringBuilder(54);
                sb.append(i2);
                sb.append(" out of ");
                sb.append(i4);
                sb.append(" underlying tasks failed");
                zzwVar.zzc(new ExecutionException(sb.toString(), this.zzg));
            } else if (this.zzh) {
                this.zzc.zze();
            } else {
                this.zzc.zza(null);
            }
        }
    }

    @Override // com.google.android.gms.tasks.OnCanceledListener
    public final void onCanceled() {
        synchronized (this.zza) {
            this.zzf++;
            this.zzh = true;
            zza();
        }
    }

    @Override // com.google.android.gms.tasks.OnFailureListener
    public final void onFailure(@NonNull Exception exc) {
        synchronized (this.zza) {
            this.zze++;
            this.zzg = exc;
            zza();
        }
    }

    @Override // com.google.android.gms.tasks.OnSuccessListener
    public final void onSuccess(Object obj) {
        synchronized (this.zza) {
            this.zzd++;
            zza();
        }
    }
}
