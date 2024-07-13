package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaaz extends GoogleApiClient implements zabt {
    @Nullable
    @VisibleForTesting
    zabr zab;
    final Map<Api.AnyClientKey<?>, Api.Client> zac;
    Set<Scope> zad;
    final ClientSettings zae;
    final Map<Api<?>, Boolean> zaf;
    final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zae, SignInOptions> zag;
    @Nullable
    Set<zacv> zah;
    final zacx zai;
    private final Lock zaj;
    private final com.google.android.gms.common.internal.zak zak;
    private final int zam;
    private final Context zan;
    private final Looper zao;
    private volatile boolean zap;
    private long zaq;
    private long zar;
    private final zaax zas;
    private final GoogleApiAvailability zat;
    private final ListenerHolders zau;
    private final ArrayList<zas> zav;
    private Integer zaw;
    private final com.google.android.gms.common.internal.zaj zax;
    @Nullable
    private zabu zal = null;
    @VisibleForTesting
    final Queue<BaseImplementation.ApiMethodImpl<?, ?>> zaa = new LinkedList();

    public zaaz(Context context, Lock lock, Looper looper, ClientSettings clientSettings, GoogleApiAvailability googleApiAvailability, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zae, SignInOptions> abstractClientBuilder, Map<Api<?>, Boolean> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.AnyClientKey<?>, Api.Client> map2, int i, int i2, ArrayList<zas> arrayList) {
        this.zaq = true != ClientLibraryUtils.isPackageSide() ? 120000L : 10000L;
        this.zar = 5000L;
        this.zad = new HashSet();
        this.zau = new ListenerHolders();
        this.zaw = null;
        this.zah = null;
        this.zax = new zaat(this);
        this.zan = context;
        this.zaj = lock;
        this.zak = new com.google.android.gms.common.internal.zak(looper, this.zax);
        this.zao = looper;
        this.zas = new zaax(this, looper);
        this.zat = googleApiAvailability;
        this.zam = i;
        if (i >= 0) {
            this.zaw = Integer.valueOf(i2);
        }
        this.zaf = map;
        this.zac = map2;
        this.zav = arrayList;
        this.zai = new zacx();
        for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : list) {
            this.zak.zaf(connectionCallbacks);
        }
        for (GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener : list2) {
            this.zak.zai(onConnectionFailedListener);
        }
        this.zae = clientSettings;
        this.zag = abstractClientBuilder;
    }

    public static int zaf(Iterable<Api.Client> iterable, boolean z) {
        boolean z2;
        Iterator<Api.Client> it = iterable.iterator();
        boolean z3 = false;
        boolean z4 = false;
        while (true) {
            z2 = z4;
            if (!it.hasNext()) {
                break;
            }
            Api.Client next = it.next();
            z3 = next.requiresSignIn() | z3;
            z4 = next.providesSignIn() | z2;
        }
        if (z3) {
            return (z2 && z) ? 2 : 1;
        }
        return 3;
    }

    static String zag(int i) {
        switch (i) {
            case 1:
                return "SIGN_IN_MODE_REQUIRED";
            case 2:
                return "SIGN_IN_MODE_OPTIONAL";
            case 3:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    public static /* synthetic */ void zah(zaaz zaazVar) {
        zaazVar.zaj.lock();
        try {
            if (zaazVar.zap) {
                zaazVar.zan();
            }
        } finally {
            zaazVar.zaj.unlock();
        }
    }

    public static /* synthetic */ void zai(zaaz zaazVar) {
        zaazVar.zaj.lock();
        try {
            if (zaazVar.zad()) {
                zaazVar.zan();
            }
        } finally {
            zaazVar.zaj.unlock();
        }
    }

    public final void zal(GoogleApiClient googleApiClient, StatusPendingResult statusPendingResult, boolean z) {
        Common.zaa.zaa(googleApiClient).setResultCallback(new zaaw(this, statusPendingResult, z, googleApiClient));
    }

    private final void zam(int i) {
        boolean z;
        Integer num = this.zaw;
        if (num == null) {
            this.zaw = Integer.valueOf(i);
        } else if (num.intValue() != i) {
            String zag = zag(i);
            String zag2 = zag(this.zaw.intValue());
            StringBuilder sb = new StringBuilder(zag.length() + 51 + zag2.length());
            sb.append("Cannot use sign-in mode: ");
            sb.append(zag);
            sb.append(". Mode was already set to ");
            sb.append(zag2);
            throw new IllegalStateException(sb.toString());
        }
        if (this.zal == null) {
            Iterator<Api.Client> it = this.zac.values().iterator();
            boolean z2 = false;
            boolean z3 = false;
            while (true) {
                z = z3;
                if (!it.hasNext()) {
                    break;
                }
                Api.Client next = it.next();
                z2 = next.requiresSignIn() | z2;
                z3 = next.providesSignIn() | z;
            }
            switch (this.zaw.intValue()) {
                case 1:
                    if (!z2) {
                        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                    }
                    if (z) {
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                    break;
                case 2:
                    if (z2) {
                        this.zal = zax.zaa(this.zan, this, this.zaj, this.zao, this.zat, this.zac, this.zae, this.zaf, this.zag, this.zav);
                        return;
                    }
                    break;
            }
            this.zal = new zabd(this.zan, this, this.zaj, this.zao, this.zat, this.zac, this.zae, this.zaf, this.zag, this.zav, this);
        }
    }

    @GuardedBy("mLock")
    private final void zan() {
        this.zak.zab();
        ((zabu) Preconditions.checkNotNull(this.zal)).zae();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final ConnectionResult blockingConnect() {
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.zaj.lock();
        try {
            if (this.zam < 0) {
                Integer num = this.zaw;
                if (num == null) {
                    this.zaw = Integer.valueOf(zaf(this.zac.values(), false));
                } else if (num.intValue() == 2) {
                    throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
                }
            } else {
                Preconditions.checkState(this.zaw != null, "Sign-in mode should have been set explicitly by auto-manage.");
            }
            zam(((Integer) Preconditions.checkNotNull(this.zaw)).intValue());
            this.zak.zab();
            return ((zabu) Preconditions.checkNotNull(this.zal)).zaf();
        } finally {
            this.zaj.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        boolean z;
        Preconditions.checkState(isConnected(), "GoogleApiClient is not connected yet.");
        Integer num = this.zaw;
        if (num != null) {
            z = num.intValue() != 2;
        } else {
            z = true;
        }
        Preconditions.checkState(z, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        StatusPendingResult statusPendingResult = new StatusPendingResult(this);
        if (this.zac.containsKey(Common.CLIENT_KEY)) {
            zal(this, statusPendingResult, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            zaau zaauVar = new zaau(this, atomicReference, statusPendingResult);
            zaav zaavVar = new zaav(this, statusPendingResult);
            GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this.zan);
            builder.addApi(Common.API);
            builder.addConnectionCallbacks(zaauVar);
            builder.addOnConnectionFailedListener(zaavVar);
            builder.setHandler(this.zas);
            GoogleApiClient build = builder.build();
            atomicReference.set(build);
            build.connect();
        }
        return statusPendingResult;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void connect() {
        boolean z;
        this.zaj.lock();
        try {
            if (this.zam >= 0) {
                Preconditions.checkState(this.zaw != null, "Sign-in mode should have been set explicitly by auto-manage.");
            } else {
                Integer num = this.zaw;
                if (num == null) {
                    this.zaw = Integer.valueOf(zaf(this.zac.values(), false));
                } else if (num.intValue() == 2) {
                    throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
                }
            }
            int intValue = ((Integer) Preconditions.checkNotNull(this.zaw)).intValue();
            this.zaj.lock();
            if (intValue == 3) {
                z = true;
            } else if (intValue == 1) {
                z = true;
            } else if (intValue == 2) {
                intValue = 2;
                z = true;
            } else {
                z = false;
            }
            StringBuilder sb = new StringBuilder(33);
            sb.append("Illegal sign-in mode: ");
            sb.append(intValue);
            Preconditions.checkArgument(z, sb.toString());
            zam(intValue);
            zan();
            this.zaj.unlock();
        } finally {
            this.zaj.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void disconnect() {
        this.zaj.lock();
        try {
            this.zai.zab();
            zabu zabuVar = this.zal;
            if (zabuVar != null) {
                zabuVar.zah();
            }
            this.zau.zab();
            for (BaseImplementation.ApiMethodImpl<?, ?> apiMethodImpl : this.zaa) {
                apiMethodImpl.zan(null);
                apiMethodImpl.cancel();
            }
            this.zaa.clear();
            if (this.zal == null) {
                return;
            }
            zad();
            this.zak.zaa();
        } finally {
            this.zaj.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void dump(String str, @Nullable FileDescriptor fileDescriptor, PrintWriter printWriter, @Nullable String[] strArr) {
        printWriter.append((CharSequence) str).append("mContext=").println(this.zan);
        printWriter.append((CharSequence) str).append("mResuming=").print(this.zap);
        printWriter.append(" mWorkQueue.size()=").print(this.zaa.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zai.zab.size());
        zabu zabuVar = this.zal;
        if (zabuVar != null) {
            zabuVar.zan(str, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        Api<?> api = t.getApi();
        boolean containsKey = this.zac.containsKey(t.getClientKey());
        String zad = api != null ? api.zad() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(zad).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(zad);
        sb.append(" required for this call.");
        Preconditions.checkArgument(containsKey, sb.toString());
        this.zaj.lock();
        try {
            zabu zabuVar = this.zal;
            if (zabuVar != null) {
                return (T) zabuVar.zab(t);
            }
            this.zaa.add(t);
            return t;
        } finally {
            this.zaj.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        Api<?> api = t.getApi();
        boolean containsKey = this.zac.containsKey(t.getClientKey());
        String zad = api != null ? api.zad() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(zad).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(zad);
        sb.append(" required for this call.");
        Preconditions.checkArgument(containsKey, sb.toString());
        this.zaj.lock();
        try {
            zabu zabuVar = this.zal;
            if (zabuVar != null) {
                if (this.zap) {
                    this.zaa.add(t);
                    while (!this.zaa.isEmpty()) {
                        BaseImplementation.ApiMethodImpl<?, ?> remove = this.zaa.remove();
                        this.zai.zaa(remove);
                        remove.setFailedResult(Status.RESULT_INTERNAL_ERROR);
                    }
                    return t;
                }
                return (T) zabuVar.zac(t);
            }
            throw new IllegalStateException("GoogleApiClient is not connected yet.");
        } finally {
            this.zaj.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    @NonNull
    public final <C extends Api.Client> C getClient(@NonNull Api.AnyClientKey<C> anyClientKey) {
        C c = (C) this.zac.get(anyClientKey);
        Preconditions.checkNotNull(c, "Appropriate Api was not requested.");
        return c;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        this.zaj.lock();
        try {
            if (isConnected() || this.zap) {
                if (this.zac.containsKey(api.zac())) {
                    ConnectionResult zad = ((zabu) Preconditions.checkNotNull(this.zal)).zad(api);
                    if (zad == null) {
                        if (this.zap) {
                            return ConnectionResult.RESULT_SUCCESS;
                        }
                        Log.w("GoogleApiClientImpl", zae());
                        Log.wtf("GoogleApiClientImpl", String.valueOf(api.zad()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                        return new ConnectionResult(8, null);
                    }
                    return zad;
                }
                throw new IllegalArgumentException(String.valueOf(api.zad()).concat(" was never registered with GoogleApiClient"));
            }
            throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
        } finally {
            this.zaj.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final Context getContext() {
        return this.zan;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final Looper getLooper() {
        return this.zao;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean hasApi(@NonNull Api<?> api) {
        return this.zac.containsKey(api.zac());
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        if (isConnected()) {
            Api.Client client = this.zac.get(api.zac());
            return client != null && client.isConnected();
        }
        return false;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnected() {
        zabu zabuVar = this.zal;
        return zabuVar != null && zabuVar.zai();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnecting() {
        zabu zabuVar = this.zal;
        return zabuVar != null && zabuVar.zaj();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zak.zag(connectionCallbacks);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zak.zaj(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        zabu zabuVar = this.zal;
        return zabuVar != null && zabuVar.zak(signInConnectionListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void maybeSignOut() {
        zabu zabuVar = this.zal;
        if (zabuVar != null) {
            zabuVar.zam();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void reconnect() {
        disconnect();
        connect();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zak.zaf(connectionCallbacks);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zak.zai(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <L> ListenerHolder<L> registerListener(@NonNull L l) {
        this.zaj.lock();
        try {
            return this.zau.zaa(l, this.zao, "NO_TYPE");
        } finally {
            this.zaj.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        LifecycleActivity lifecycleActivity = new LifecycleActivity((Activity) fragmentActivity);
        if (this.zam >= 0) {
            zak.zaa(lifecycleActivity).zac(this.zam);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zak.zah(connectionCallbacks);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zak.zak(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    @GuardedBy("mLock")
    public final void zaa(@Nullable Bundle bundle) {
        while (!this.zaa.isEmpty()) {
            execute(this.zaa.remove());
        }
        this.zak.zac(bundle);
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    @GuardedBy("mLock")
    public final void zab(ConnectionResult connectionResult) {
        if (!this.zat.isPlayServicesPossiblyUpdating(this.zan, connectionResult.getErrorCode())) {
            zad();
        }
        if (this.zap) {
            return;
        }
        this.zak.zae(connectionResult);
        this.zak.zaa();
    }

    public final String zae() {
        StringWriter stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void zao(zacv zacvVar) {
        this.zaj.lock();
        try {
            if (this.zah == null) {
                this.zah = new HashSet();
            }
            this.zah.add(zacvVar);
        } finally {
            this.zaj.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void zap(zacv zacvVar) {
        this.zaj.lock();
        try {
            Set<zacv> set = this.zah;
            if (set != null) {
                if (set.remove(zacvVar)) {
                    this.zaj.lock();
                    Set<zacv> set2 = this.zah;
                    if (set2 != null) {
                        boolean z = !set2.isEmpty();
                        this.zaj.unlock();
                        if (!z) {
                        }
                    } else {
                        this.zaj.unlock();
                    }
                    zabu zabuVar = this.zal;
                    if (zabuVar != null) {
                        zabuVar.zal();
                    }
                } else {
                    Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
                }
            } else {
                Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
            }
        } finally {
            this.zaj.unlock();
        }
    }

    @GuardedBy("mLock")
    public final boolean zad() {
        if (this.zap) {
            this.zap = false;
            this.zas.removeMessages(2);
            this.zas.removeMessages(1);
            zabr zabrVar = this.zab;
            if (zabrVar != null) {
                zabrVar.zab();
                this.zab = null;
            }
            return true;
        }
        return false;
    }

    @Override // com.google.android.gms.common.api.internal.zabt
    @GuardedBy("mLock")
    public final void zac(int i, boolean z) {
        int i2 = i;
        if (i2 == 1) {
            if (z) {
                i2 = 1;
            } else if (this.zap) {
                i2 = 1;
            } else {
                this.zap = true;
                if (this.zab == null && !ClientLibraryUtils.isPackageSide()) {
                    try {
                        this.zab = this.zat.zae(this.zan.getApplicationContext(), new zaay(this));
                    } catch (SecurityException e) {
                    }
                }
                zaax zaaxVar = this.zas;
                zaaxVar.sendMessageDelayed(zaaxVar.obtainMessage(1), this.zaq);
                zaax zaaxVar2 = this.zas;
                zaaxVar2.sendMessageDelayed(zaaxVar2.obtainMessage(2), this.zar);
                i2 = 1;
            }
        }
        for (BasePendingResult basePendingResult : (BasePendingResult[]) this.zai.zab.toArray(new BasePendingResult[0])) {
            basePendingResult.forceFailureUnlessReady(zacx.zaa);
        }
        this.zak.zad(i2);
        this.zak.zaa();
        if (i2 == 2) {
            zan();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        Preconditions.checkNotNull(timeUnit, "TimeUnit must not be null");
        this.zaj.lock();
        try {
            Integer num = this.zaw;
            if (num == null) {
                this.zaw = Integer.valueOf(zaf(this.zac.values(), false));
            } else if (num.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zam(((Integer) Preconditions.checkNotNull(this.zaw)).intValue());
            this.zak.zab();
            return ((zabu) Preconditions.checkNotNull(this.zal)).zag(j, timeUnit);
        } finally {
            this.zaj.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void connect(int i) {
        boolean z;
        int i2 = i;
        this.zaj.lock();
        if (i2 == 3) {
            z = true;
        } else if (i2 == 1) {
            z = true;
        } else if (i2 == 2) {
            i2 = 2;
            z = true;
        } else {
            z = false;
        }
        try {
            StringBuilder sb = new StringBuilder(33);
            sb.append("Illegal sign-in mode: ");
            sb.append(i2);
            Preconditions.checkArgument(z, sb.toString());
            zam(i2);
            zan();
        } finally {
            this.zaj.unlock();
        }
    }
}
