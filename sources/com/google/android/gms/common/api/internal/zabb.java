package com.google.android.gms.common.api.internal;

import java.util.concurrent.locks.Lock;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public abstract class zabb {
    private final zaba zaa;

    public zabb(zaba zabaVar) {
        this.zaa = zabaVar;
    }

    protected abstract void zaa();

    public final void zab(zabd zabdVar) {
        Lock lock;
        Lock lock2;
        zaba zabaVar;
        lock = zabdVar.zai;
        lock.lock();
        try {
            zabaVar = zabdVar.zan;
            if (zabaVar != this.zaa) {
                return;
            }
            zaa();
        } finally {
            lock2 = zabdVar.zai;
            lock2.unlock();
        }
    }
}
