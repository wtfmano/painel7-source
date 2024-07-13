package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.IAccountAccessor;
import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zabo implements BaseGmsClient.ConnectionProgressReportCallbacks, zacn {
    final /* synthetic */ GoogleApiManager zaa;
    private final Api.Client zab;
    private final ApiKey<?> zac;
    @Nullable
    private IAccountAccessor zad = null;
    @Nullable
    private Set<Scope> zae = null;
    private boolean zaf = false;

    public zabo(GoogleApiManager googleApiManager, Api.Client client, ApiKey<?> apiKey) {
        this.zaa = googleApiManager;
        this.zab = client;
        this.zac = apiKey;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean zad(zabo zaboVar, boolean z) {
        zaboVar.zaf = true;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zag() {
        IAccountAccessor iAccountAccessor;
        if (!this.zaf || (iAccountAccessor = this.zad) == null) {
            return;
        }
        this.zab.getRemoteService(iAccountAccessor, this.zae);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
    public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
        Handler handler;
        handler = this.zaa.zat;
        handler.post(new zabn(this, connectionResult));
    }

    @Override // com.google.android.gms.common.api.internal.zacn
    @WorkerThread
    public final void zaa(ConnectionResult connectionResult) {
        Map map;
        map = this.zaa.zap;
        zabl zablVar = (zabl) map.get(this.zac);
        if (zablVar != null) {
            zablVar.zab(connectionResult);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zacn
    @WorkerThread
    public final void zab(@Nullable IAccountAccessor iAccountAccessor, @Nullable Set<Scope> set) {
        if (iAccountAccessor == null || set == null) {
            Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
            zaa(new ConnectionResult(4));
            return;
        }
        this.zad = iAccountAccessor;
        this.zae = set;
        zag();
    }
}
