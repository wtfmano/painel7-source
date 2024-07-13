package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public abstract class zap extends LifecycleCallback implements DialogInterface.OnCancelListener {
    protected volatile boolean zaa;
    protected final AtomicReference<zam> zab;
    protected final GoogleApiAvailability zac;
    private final Handler zad;

    @VisibleForTesting
    public zap(LifecycleFragment lifecycleFragment, GoogleApiAvailability googleApiAvailability) {
        super(lifecycleFragment);
        this.zab = new AtomicReference<>(null);
        this.zad = new com.google.android.gms.internal.base.zap(Looper.getMainLooper());
        this.zac = googleApiAvailability;
    }

    public final void zaa() {
        this.zab.set(null);
        zae();
    }

    public final void zab(ConnectionResult connectionResult, int i) {
        this.zab.set(null);
        zad(connectionResult, i);
    }

    private static final int zac(@Nullable zam zamVar) {
        if (zamVar == null) {
            return -1;
        }
        return zamVar.zaa();
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public final void onActivityResult(int i, int i2, Intent intent) {
        zam zamVar = this.zab.get();
        switch (i) {
            case 1:
                if (i2 == -1) {
                    zaa();
                    return;
                } else if (i2 == 0) {
                    if (zamVar == null) {
                        return;
                    }
                    zab(new ConnectionResult(intent != null ? intent.getIntExtra("<<ResolutionFailureErrorDetail>>", 13) : 13, null, zamVar.zab().toString()), zac(zamVar));
                    return;
                }
                break;
            case 2:
                int isGooglePlayServicesAvailable = this.zac.isGooglePlayServicesAvailable(getActivity());
                if (isGooglePlayServicesAvailable == 0) {
                    zaa();
                    return;
                } else if (zamVar == null) {
                    return;
                } else {
                    if (zamVar.zab().getErrorCode() == 18 && isGooglePlayServicesAvailable == 18) {
                        return;
                    }
                }
                break;
        }
        if (zamVar != null) {
            zab(zamVar.zab(), zamVar.zaa());
        }
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        zab(new ConnectionResult(13, null), zac(this.zab.get()));
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public final void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zab.set(bundle.getBoolean("resolving_error", false) ? new zam(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1)) : null);
        }
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zam zamVar = this.zab.get();
        if (zamVar != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", zamVar.zaa());
            bundle.putInt("failed_status", zamVar.zab().getErrorCode());
            bundle.putParcelable("failed_resolution", zamVar.zab().getResolution());
        }
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStart() {
        super.onStart();
        this.zaa = true;
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStop() {
        super.onStop();
        this.zaa = false;
    }

    protected abstract void zad(ConnectionResult connectionResult, int i);

    protected abstract void zae();

    public final void zaf(ConnectionResult connectionResult, int i) {
        zam zamVar = new zam(connectionResult, i);
        if (this.zab.compareAndSet(null, zamVar)) {
            this.zad.post(new zao(this, zamVar));
        }
    }
}
