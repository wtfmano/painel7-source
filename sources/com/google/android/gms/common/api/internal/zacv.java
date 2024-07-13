package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zacv<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    private final WeakReference<GoogleApiClient> zag;
    private final zacu zah;
    @Nullable
    private ResultTransform<? super R, ? extends Result> zaa = null;
    @Nullable
    private zacv<? extends Result> zab = null;
    @Nullable
    private volatile ResultCallbacks<? super R> zac = null;
    @Nullable
    private PendingResult<R> zad = null;
    private final Object zae = new Object();
    @Nullable
    private Status zaf = null;
    private boolean zai = false;

    public zacv(WeakReference<GoogleApiClient> weakReference) {
        Preconditions.checkNotNull(weakReference, "GoogleApiClient reference must not be null");
        this.zag = weakReference;
        GoogleApiClient googleApiClient = this.zag.get();
        this.zah = new zacu(this, googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
    }

    @GuardedBy("mSyncToken")
    private final void zaj() {
        if (this.zaa != null || this.zac != null) {
            GoogleApiClient googleApiClient = this.zag.get();
            if (!this.zai && this.zaa != null && googleApiClient != null) {
                googleApiClient.zao(this);
                this.zai = true;
            }
            Status status = this.zaf;
            if (status != null) {
                zal(status);
                return;
            }
            PendingResult<R> pendingResult = this.zad;
            if (pendingResult != null) {
                pendingResult.setResultCallback(this);
            }
        }
    }

    public final void zak(Status status) {
        synchronized (this.zae) {
            this.zaf = status;
            zal(this.zaf);
        }
    }

    private final void zal(Status status) {
        synchronized (this.zae) {
            ResultTransform<? super R, ? extends Result> resultTransform = this.zaa;
            if (resultTransform != null) {
                ((zacv) Preconditions.checkNotNull(this.zab)).zak((Status) Preconditions.checkNotNull(resultTransform.onFailure(status), "onFailure must not return null"));
            } else if (zam()) {
                ((ResultCallbacks) Preconditions.checkNotNull(this.zac)).onFailure(status);
            }
        }
    }

    @GuardedBy("mSyncToken")
    private final boolean zam() {
        return (this.zac == null || this.zag.get() == null) ? false : true;
    }

    public static final void zan(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                String valueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(valueOf);
                Log.w("TransformedResultImpl", sb.toString(), e);
            }
        }
    }

    @Override // com.google.android.gms.common.api.TransformedResult
    public final void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        synchronized (this.zae) {
            Preconditions.checkState(this.zac == null, "Cannot call andFinally() twice.");
            Preconditions.checkState(this.zaa == null, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zac = resultCallbacks;
            zaj();
        }
    }

    @Override // com.google.android.gms.common.api.ResultCallback
    public final void onResult(R r) {
        synchronized (this.zae) {
            if (!r.getStatus().isSuccess()) {
                zak(r.getStatus());
                zan(r);
            } else if (this.zaa != null) {
                zacj.zaa().submit(new zact(this, r));
            } else if (zam()) {
                ((ResultCallbacks) Preconditions.checkNotNull(this.zac)).onSuccess(r);
            }
        }
    }

    @Override // com.google.android.gms.common.api.TransformedResult
    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        zacv<? extends Result> zacvVar;
        synchronized (this.zae) {
            Preconditions.checkState(this.zaa == null, "Cannot call then() twice.");
            Preconditions.checkState(this.zac == null, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zaa = resultTransform;
            zacvVar = new zacv<>(this.zag);
            this.zab = zacvVar;
            zaj();
        }
        return zacvVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zaa(PendingResult<?> pendingResult) {
        synchronized (this.zae) {
            this.zad = pendingResult;
            zaj();
        }
    }

    public final void zab() {
        this.zac = null;
    }
}
