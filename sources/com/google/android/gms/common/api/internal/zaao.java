package com.google.android.gms.common.api.internal;

import androidx.annotation.BinderThread;
import java.lang.ref.WeakReference;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
final class zaao extends com.google.android.gms.signin.internal.zac {
    private final WeakReference<zaar> zaa;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zaao(zaar zaarVar) {
        this.zaa = new WeakReference<>(zaarVar);
    }

    @Override // com.google.android.gms.signin.internal.zac, com.google.android.gms.signin.internal.zae
    @BinderThread
    public final void zab(com.google.android.gms.signin.internal.zak zakVar) {
        zabd zabdVar;
        zaar zaarVar = this.zaa.get();
        if (zaarVar != null) {
            zabdVar = zaarVar.zaa;
            zabdVar.zar(new zaan(this, zaarVar, zaarVar, zakVar));
        }
    }
}
