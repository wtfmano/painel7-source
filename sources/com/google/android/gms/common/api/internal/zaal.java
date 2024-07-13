package com.google.android.gms.common.api.internal;

import android.content.Context;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaal extends zaaq {
    final /* synthetic */ zaar zaa;
    private final Map<Api.Client, zaai> zac;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaal(zaar zaarVar, Map<Api.Client, zaai> map) {
        super(zaarVar, null);
        this.zaa = zaarVar;
        this.zac = map;
    }

    @Override // com.google.android.gms.common.api.internal.zaaq
    @GuardedBy("mLock")
    @WorkerThread
    public final void zaa() {
        GoogleApiAvailabilityLight googleApiAvailabilityLight;
        int i;
        Context context;
        boolean z;
        Context context2;
        zabd zabdVar;
        com.google.android.gms.signin.zae zaeVar;
        com.google.android.gms.signin.zae zaeVar2;
        zabd zabdVar2;
        Context context3;
        boolean z2;
        googleApiAvailabilityLight = this.zaa.zad;
        com.google.android.gms.common.internal.zal zalVar = new com.google.android.gms.common.internal.zal(googleApiAvailabilityLight);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Api.Client client : this.zac.keySet()) {
            if (client.requiresGooglePlayServices()) {
                z2 = this.zac.get(client).zac;
                if (!z2) {
                    arrayList.add(client);
                }
            }
            arrayList2.add(client);
        }
        if (arrayList.isEmpty()) {
            int size = arrayList2.size();
            int i2 = 0;
            int i3 = -1;
            while (true) {
                if (i2 >= size) {
                    i = i3;
                    break;
                }
                context3 = this.zaa.zac;
                i3 = zalVar.zaa(context3, (Api.Client) arrayList2.get(i2));
                i2++;
                if (i3 == 0) {
                    i = i3;
                    break;
                }
            }
        } else {
            int size2 = arrayList.size();
            int i4 = 0;
            int i5 = -1;
            while (true) {
                if (i4 >= size2) {
                    i = i5;
                    break;
                }
                context = this.zaa.zac;
                i5 = zalVar.zaa(context, (Api.Client) arrayList.get(i4));
                i4++;
                if (i5 != 0) {
                    i = i5;
                    break;
                }
            }
        }
        if (i != 0) {
            ConnectionResult connectionResult = new ConnectionResult(i, null);
            zabdVar2 = this.zaa.zaa;
            zabdVar2.zar(new zaaj(this, this.zaa, connectionResult));
            return;
        }
        z = this.zaa.zam;
        if (z) {
            zaeVar = this.zaa.zak;
            if (zaeVar != null) {
                zaeVar2 = this.zaa.zak;
                zaeVar2.zad();
            }
        }
        for (Api.Client client2 : this.zac.keySet()) {
            zaai zaaiVar = this.zac.get(client2);
            if (client2.requiresGooglePlayServices()) {
                context2 = this.zaa.zac;
                if (zalVar.zaa(context2, client2) != 0) {
                    zabdVar = this.zaa.zaa;
                    zabdVar.zar(new zaak(this, this.zaa, zaaiVar));
                }
            }
            client2.connect(zaaiVar);
        }
    }
}
