package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import java.util.concurrent.locks.Lock;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaw implements zabt {
    final /* synthetic */ zax zaa;

    public /* synthetic */ zaw(zax zaxVar, zau zauVar) {
        this.zaa = zaxVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    public final void zaa(@Nullable Bundle bundle) {
        Lock lock;
        Lock lock2;
        lock = this.zaa.zam;
        lock.lock();
        try {
            this.zaa.zak = ConnectionResult.RESULT_SUCCESS;
            zax.zap(this.zaa);
        } finally {
            lock2 = this.zaa.zam;
            lock2.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    public final void zab(@NonNull ConnectionResult connectionResult) {
        Lock lock;
        Lock lock2;
        lock = this.zaa.zam;
        lock.lock();
        try {
            this.zaa.zak = connectionResult;
            zax.zap(this.zaa);
        } finally {
            lock2 = this.zaa.zam;
            lock2.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    public final void zac(int i, boolean z) {
        Lock lock;
        Lock lock2;
        boolean z2;
        zabd zabdVar;
        lock = this.zaa.zam;
        lock.lock();
        try {
            z2 = this.zaa.zal;
            if (z2) {
                this.zaa.zal = false;
                zax.zav(this.zaa, i, z);
                return;
            }
            this.zaa.zal = true;
            zabdVar = this.zaa.zad;
            zabdVar.onConnectionSuspended(i);
        } finally {
            lock2 = this.zaa.zam;
            lock2.unlock();
        }
    }
}
