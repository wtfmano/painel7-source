package com.google.android.gms.common.internal;

import android.content.Context;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zal {
    private final SparseIntArray zaa;
    private GoogleApiAvailabilityLight zab;

    public zal() {
        this(GoogleApiAvailability.getInstance());
    }

    public final int zaa(@NonNull Context context, @NonNull Api.Client client) {
        int i;
        int i2;
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(client);
        if (client.requiresGooglePlayServices()) {
            int minApkVersion = client.getMinApkVersion();
            int zab = zab(context, minApkVersion);
            if (zab != -1) {
                i2 = zab;
            } else {
                int i3 = 0;
                while (true) {
                    if (i3 >= this.zaa.size()) {
                        i = -1;
                        break;
                    }
                    int keyAt = this.zaa.keyAt(i3);
                    if (keyAt > minApkVersion && this.zaa.get(keyAt) == 0) {
                        i = 0;
                        break;
                    }
                    i3++;
                }
                int isGooglePlayServicesAvailable = i == -1 ? this.zab.isGooglePlayServicesAvailable(context, minApkVersion) : i;
                this.zaa.put(minApkVersion, isGooglePlayServicesAvailable);
                i2 = isGooglePlayServicesAvailable;
            }
            return i2;
        }
        return 0;
    }

    public final int zab(Context context, int i) {
        return this.zaa.get(i, -1);
    }

    public final void zac() {
        this.zaa.clear();
    }

    public zal(@NonNull GoogleApiAvailabilityLight googleApiAvailabilityLight) {
        this.zaa = new SparseIntArray();
        Preconditions.checkNotNull(googleApiAvailabilityLight);
        this.zab = googleApiAvailabilityLight;
    }
}
