package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import java.util.concurrent.locks.Lock;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
abstract class zaaq implements Runnable {
    final /* synthetic */ zaar zab;

    @Override // java.lang.Runnable
    @WorkerThread
    public final void run() {
        Lock lock;
        zabd zabdVar;
        Lock lock2;
        Lock lock3;
        lock = this.zab.zab;
        lock.lock();
        try {
            if (!Thread.interrupted()) {
                zaa();
                return;
            }
            lock3 = this.zab.zab;
            lock3.unlock();
            return;
        } catch (RuntimeException e) {
            zabdVar = this.zab.zaa;
            zabdVar.zas(e);
            return;
        } finally {
            lock2 = this.zab.zab;
            lock2.unlock();
        }
        lock2 = this.zab.zab;
        lock2.unlock();
    }

    @WorkerThread
    protected abstract void zaa();
}
