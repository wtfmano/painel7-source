package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import java.util.concurrent.locks.Lock;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zav implements zabt {
    final /* synthetic */ zax zaa;

    public /* synthetic */ zav(zax zaxVar, zau zauVar) {
        this.zaa = zaxVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    public final void zaa(@Nullable Bundle bundle) {
        Lock lock;
        Lock lock2;
        lock = this.zaa.zam;
        lock.lock();
        try {
            zax.zaq(this.zaa, bundle);
            this.zaa.zaj = ConnectionResult.RESULT_SUCCESS;
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
            this.zaa.zaj = connectionResult;
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
        ConnectionResult connectionResult;
        ConnectionResult connectionResult2;
        zabd zabdVar;
        lock = this.zaa.zam;
        lock.lock();
        try {
            z2 = this.zaa.zal;
            if (!z2) {
                connectionResult = this.zaa.zak;
                if (connectionResult != null) {
                    connectionResult2 = this.zaa.zak;
                    if (connectionResult2.isSuccess()) {
                        this.zaa.zal = true;
                        zabdVar = this.zaa.zae;
                        zabdVar.onConnectionSuspended(i);
                        return;
                    }
                }
            }
            this.zaa.zal = false;
            zax.zav(this.zaa, i, z);
        } finally {
            lock2 = this.zaa.zam;
            lock2.unlock();
        }
    }
}
