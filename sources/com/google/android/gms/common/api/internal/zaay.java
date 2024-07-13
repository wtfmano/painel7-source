package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaay extends zabq {
    private final WeakReference<zaaz> zaa;

    public zaay(zaaz zaazVar) {
        this.zaa = new WeakReference<>(zaazVar);
    }

    @Override // com.google.android.gms.common.api.internal.zabq
    public final void zaa() {
        zaaz zaazVar = this.zaa.get();
        if (zaazVar == null) {
            return;
        }
        zaaz.zah(zaazVar);
    }
}
