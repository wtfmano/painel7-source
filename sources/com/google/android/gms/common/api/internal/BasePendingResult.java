package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
@KeepForSdk
@KeepName
/* loaded from: classes.dex */
public abstract class BasePendingResult<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zaa = new zaq();
    public static final /* synthetic */ int zad = 0;
    @KeepName
    private zar mResultGuardian;
    @RecentlyNonNull
    protected final CallbackHandler<R> zab;
    @RecentlyNonNull
    protected final WeakReference<GoogleApiClient> zac;
    private final Object zae;
    private final CountDownLatch zaf;
    private final ArrayList<PendingResult.StatusListener> zag;
    @Nullable
    private ResultCallback<? super R> zah;
    private final AtomicReference<zacw> zai;
    @Nullable
    private R zaj;
    private Status zak;
    private volatile boolean zal;
    private boolean zam;
    private boolean zan;
    @Nullable
    private ICancelToken zao;
    private volatile zacv<R> zap;
    private boolean zaq;

    @Deprecated
    BasePendingResult() {
        this.zae = new Object();
        this.zaf = new CountDownLatch(1);
        this.zag = new ArrayList<>();
        this.zai = new AtomicReference<>();
        this.zaq = false;
        this.zab = new CallbackHandler<>(Looper.getMainLooper());
        this.zac = new WeakReference<>(null);
    }

    private final R zaa() {
        R r;
        synchronized (this.zae) {
            Preconditions.checkState(!this.zal, "Result has already been consumed.");
            Preconditions.checkState(isReady(), "Result is not ready.");
            r = this.zaj;
            this.zaj = null;
            this.zah = null;
            this.zal = true;
        }
        zacw andSet = this.zai.getAndSet(null);
        if (andSet != null) {
            andSet.zaa.zab.remove(this);
        }
        return (R) Preconditions.checkNotNull(r);
    }

    private final void zab(R r) {
        this.zaj = r;
        this.zak = r.getStatus();
        this.zao = null;
        this.zaf.countDown();
        if (this.zam) {
            this.zah = null;
        } else {
            ResultCallback<? super R> resultCallback = this.zah;
            if (resultCallback == null) {
                if (this.zaj instanceof Releasable) {
                    this.mResultGuardian = new zar(this, null);
                }
            } else {
                this.zab.removeMessages(2);
                this.zab.zaa(resultCallback, zaa());
            }
        }
        ArrayList<PendingResult.StatusListener> arrayList = this.zag;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).onComplete(this.zak);
        }
        this.zag.clear();
    }

    public static void zal(@Nullable Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                String valueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(valueOf);
                Log.w("BasePendingResult", sb.toString(), e);
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void addStatusListener(@RecentlyNonNull PendingResult.StatusListener statusListener) {
        Preconditions.checkArgument(statusListener != null, "Callback cannot be null.");
        synchronized (this.zae) {
            if (!isReady()) {
                this.zag.add(statusListener);
            } else {
                statusListener.onComplete(this.zak);
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @RecentlyNonNull
    public final R await() {
        Preconditions.checkNotMainThread("await must not be called on the UI thread");
        Preconditions.checkState(!this.zal, "Result has already been consumed");
        Preconditions.checkState(this.zap == null, "Cannot await if then() has been called.");
        try {
            this.zaf.await();
        } catch (InterruptedException e) {
            forceFailureUnlessReady(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return zaa();
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @KeepForSdk
    public void cancel() {
        synchronized (this.zae) {
            if (!this.zam && !this.zal) {
                ICancelToken iCancelToken = this.zao;
                if (iCancelToken != null) {
                    try {
                        iCancelToken.cancel();
                    } catch (RemoteException e) {
                    }
                }
                zal(this.zaj);
                this.zam = true;
                zab(createFailedResult(Status.RESULT_CANCELED));
            }
        }
    }

    @NonNull
    @KeepForSdk
    public abstract R createFailedResult(@RecentlyNonNull Status status);

    @KeepForSdk
    @Deprecated
    public final void forceFailureUnlessReady(@RecentlyNonNull Status status) {
        synchronized (this.zae) {
            if (!isReady()) {
                setResult(createFailedResult(status));
                this.zan = true;
            }
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final boolean isCanceled() {
        boolean z;
        synchronized (this.zae) {
            z = this.zam;
        }
        return z;
    }

    @KeepForSdk
    public final boolean isReady() {
        return this.zaf.getCount() == 0;
    }

    @KeepForSdk
    protected final void setCancelToken(@RecentlyNonNull ICancelToken iCancelToken) {
        synchronized (this.zae) {
            this.zao = iCancelToken;
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @KeepForSdk
    public final void setResultCallback(@Nullable ResultCallback<? super R> resultCallback) {
        synchronized (this.zae) {
            if (resultCallback != null) {
                Preconditions.checkState(!this.zal, "Result has already been consumed.");
                Preconditions.checkState(this.zap == null, "Cannot set callbacks if then() has been called.");
                if (!isCanceled()) {
                    if (isReady()) {
                        this.zab.zaa(resultCallback, zaa());
                    } else {
                        this.zah = resultCallback;
                    }
                    return;
                }
                return;
            }
            this.zah = null;
        }
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @RecentlyNonNull
    public final <S extends Result> TransformedResult<S> then(@RecentlyNonNull ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        Preconditions.checkState(!this.zal, "Result has already been consumed.");
        synchronized (this.zae) {
            Preconditions.checkState(this.zap == null, "Cannot call then() twice.");
            Preconditions.checkState(this.zah == null, "Cannot call then() if callbacks are set.");
            Preconditions.checkState(!this.zam, "Cannot call then() if result was canceled.");
            this.zaq = true;
            this.zap = new zacv<>(this.zac);
            then = this.zap.then(resultTransform);
            if (isReady()) {
                this.zab.zaa(this.zap, zaa());
            } else {
                this.zah = this.zap;
            }
        }
        return then;
    }

    public final boolean zaj() {
        boolean isCanceled;
        synchronized (this.zae) {
            if (this.zac.get() == null || !this.zaq) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    public final void zak() {
        boolean z;
        if (this.zaq) {
            z = true;
        } else {
            z = zaa.get().booleanValue();
        }
        this.zaq = z;
    }

    public final void zan(@Nullable zacw zacwVar) {
        this.zai.set(zacwVar);
    }

    /* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class CallbackHandler<R extends Result> extends com.google.android.gms.internal.base.zap {
        public CallbackHandler() {
            super(Looper.getMainLooper());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public final void handleMessage(@RecentlyNonNull Message message) {
            switch (message.what) {
                case 1:
                    Pair pair = (Pair) message.obj;
                    ResultCallback resultCallback = (ResultCallback) pair.first;
                    Result result = (Result) pair.second;
                    try {
                        resultCallback.onResult(result);
                        return;
                    } catch (RuntimeException e) {
                        BasePendingResult.zal(result);
                        throw e;
                    }
                case 2:
                    ((BasePendingResult) message.obj).forceFailureUnlessReady(Status.RESULT_TIMEOUT);
                    return;
                default:
                    int i = message.what;
                    StringBuilder sb = new StringBuilder(45);
                    sb.append("Don't know how to handle message: ");
                    sb.append(i);
                    Log.wtf("BasePendingResult", sb.toString(), new Exception());
                    return;
            }
        }

        public final void zaa(@RecentlyNonNull ResultCallback<? super R> resultCallback, @RecentlyNonNull R r) {
            int i = BasePendingResult.zad;
            sendMessage(obtainMessage(1, new Pair((ResultCallback) Preconditions.checkNotNull(resultCallback), r)));
        }

        public CallbackHandler(@RecentlyNonNull Looper looper) {
            super(looper);
        }
    }

    @KeepForSdk
    public final void setResult(@RecentlyNonNull R r) {
        synchronized (this.zae) {
            if (this.zan || this.zam) {
                zal(r);
                return;
            }
            isReady();
            Preconditions.checkState(!isReady(), "Results have already been set");
            Preconditions.checkState(!this.zal, "Result has already been consumed");
            zab(r);
        }
    }

    @KeepForSdk
    @Deprecated
    public BasePendingResult(@RecentlyNonNull Looper looper) {
        this.zae = new Object();
        this.zaf = new CountDownLatch(1);
        this.zag = new ArrayList<>();
        this.zai = new AtomicReference<>();
        this.zaq = false;
        this.zab = new CallbackHandler<>(looper);
        this.zac = new WeakReference<>(null);
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @RecentlyNonNull
    public final R await(long j, @RecentlyNonNull TimeUnit timeUnit) {
        if (j > 0) {
            Preconditions.checkNotMainThread("await must not be called on the UI thread when time is greater than zero.");
        }
        Preconditions.checkState(!this.zal, "Result has already been consumed.");
        Preconditions.checkState(this.zap == null, "Cannot await if then() has been called.");
        try {
            if (!this.zaf.await(j, timeUnit)) {
                forceFailureUnlessReady(Status.RESULT_TIMEOUT);
            }
        } catch (InterruptedException e) {
            forceFailureUnlessReady(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return zaa();
    }

    @Override // com.google.android.gms.common.api.PendingResult
    @KeepForSdk
    public final void setResultCallback(@RecentlyNonNull ResultCallback<? super R> resultCallback, long j, @RecentlyNonNull TimeUnit timeUnit) {
        synchronized (this.zae) {
            if (resultCallback == null) {
                this.zah = null;
                return;
            }
            Preconditions.checkState(!this.zal, "Result has already been consumed.");
            Preconditions.checkState(this.zap == null, "Cannot set callbacks if then() has been called.");
            if (isCanceled()) {
                return;
            }
            if (isReady()) {
                this.zab.zaa(resultCallback, zaa());
            } else {
                this.zah = resultCallback;
                CallbackHandler<R> callbackHandler = this.zab;
                callbackHandler.sendMessageDelayed(callbackHandler.obtainMessage(2, this), timeUnit.toMillis(j));
            }
        }
    }

    @KeepForSdk
    public BasePendingResult(@Nullable GoogleApiClient googleApiClient) {
        this.zae = new Object();
        this.zaf = new CountDownLatch(1);
        this.zag = new ArrayList<>();
        this.zai = new AtomicReference<>();
        this.zaq = false;
        this.zab = new CallbackHandler<>(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.zac = new WeakReference<>(googleApiClient);
    }

    @VisibleForTesting
    @KeepForSdk
    public BasePendingResult(@RecentlyNonNull CallbackHandler<R> callbackHandler) {
        this.zae = new Object();
        this.zaf = new CountDownLatch(1);
        this.zag = new ArrayList<>();
        this.zai = new AtomicReference<>();
        this.zaq = false;
        this.zab = (CallbackHandler) Preconditions.checkNotNull(callbackHandler, "CallbackHandler must not be null");
        this.zac = new WeakReference<>(null);
    }
}
