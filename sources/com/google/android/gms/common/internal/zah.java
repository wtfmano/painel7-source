package com.google.android.gms.common.internal;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.internal.BaseGmsClient;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zah implements BaseGmsClient.BaseConnectionCallbacks {
    final /* synthetic */ ConnectionCallbacks zaa;

    public zah(ConnectionCallbacks connectionCallbacks) {
        this.zaa = connectionCallbacks;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnected(@Nullable Bundle bundle) {
        this.zaa.onConnected(bundle);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnectionSuspended(int i) {
        this.zaa.onConnectionSuspended(i);
    }
}
