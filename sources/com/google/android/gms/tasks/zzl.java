package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-tasks@@17.2.1 */
/* loaded from: classes.dex */
public final class zzl<TResult> implements zzq<TResult> {
    private final Executor zza;
    private final Object zzb = new Object();
    @GuardedBy("mLock")
    @Nullable
    private OnFailureListener zzc;

    public zzl(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zza = executor;
        this.zzc = onFailureListener;
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void zza(@NonNull Task<TResult> task) {
        if (!task.isSuccessful() && !task.isCanceled()) {
            Executor executor = this.zzb;
            synchronized (executor) {
                try {
                    if (this.zzc == null) {
                        executor = executor;
                    } else {
                        Executor executor2 = this.zza;
                        executor2.execute(new zzk(this, task));
                        executor = executor2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.google.android.gms.tasks.zzq
    public final void zzb() {
        synchronized (this.zzb) {
            this.zzc = null;
        }
    }
}
