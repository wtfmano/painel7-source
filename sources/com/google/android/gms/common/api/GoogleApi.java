package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.NonGmsServiceBrokerClient;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zaab;
import com.google.android.gms.common.api.internal.zabl;
import com.google.android.gms.common.api.internal.zabp;
import com.google.android.gms.common.api.internal.zaco;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Set;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public abstract class GoogleApi<O extends Api.ApiOptions> implements HasApiKey<O> {
    @RecentlyNonNull
    protected final GoogleApiManager zaa;
    private final Context zab;
    @Nullable
    private final String zac;
    private final Api<O> zad;
    private final O zae;
    private final ApiKey<O> zaf;
    private final Looper zag;
    private final int zah;
    @NotOnlyInitialized
    private final GoogleApiClient zai;
    private final StatusExceptionMapper zaj;

    /* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
    @KeepForSdk
    /* loaded from: classes.dex */
    public static class Settings {
        @RecentlyNonNull
        @KeepForSdk
        public static final Settings DEFAULT_SETTINGS = new Builder().build();
        @RecentlyNonNull
        public final StatusExceptionMapper zaa;
        @RecentlyNonNull
        public final Looper zab;

        /* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
        @KeepForSdk
        /* loaded from: classes.dex */
        public static class Builder {
            private StatusExceptionMapper zaa;
            private Looper zab;

            @RecentlyNonNull
            @KeepForSdk
            public Settings build() {
                if (this.zaa == null) {
                    this.zaa = new ApiExceptionMapper();
                }
                if (this.zab == null) {
                    this.zab = Looper.getMainLooper();
                }
                return new Settings(this.zaa, this.zab);
            }

            @RecentlyNonNull
            @KeepForSdk
            public Builder setLooper(@RecentlyNonNull Looper looper) {
                Preconditions.checkNotNull(looper, "Looper must not be null.");
                this.zab = looper;
                return this;
            }

            @RecentlyNonNull
            @KeepForSdk
            public Builder setMapper(@RecentlyNonNull StatusExceptionMapper statusExceptionMapper) {
                Preconditions.checkNotNull(statusExceptionMapper, "StatusExceptionMapper must not be null.");
                this.zaa = statusExceptionMapper;
                return this;
            }
        }

        @KeepForSdk
        private Settings(StatusExceptionMapper statusExceptionMapper, Account account, Looper looper) {
            this.zaa = statusExceptionMapper;
            this.zab = looper;
        }
    }

    @KeepForSdk
    @MainThread
    public GoogleApi(@RecentlyNonNull Activity activity, @RecentlyNonNull Api<O> api, @RecentlyNonNull O o, @RecentlyNonNull Settings settings) {
        Preconditions.checkNotNull(activity, "Null activity is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(settings, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.zab = activity.getApplicationContext();
        this.zac = zaf(activity);
        this.zad = api;
        this.zae = o;
        this.zag = settings.zab;
        this.zaf = ApiKey.zaa(this.zad, this.zae, this.zac);
        this.zai = new zabp(this);
        this.zaa = GoogleApiManager.zaa(this.zab);
        this.zah = this.zaa.zac();
        this.zaj = settings.zaa;
        if (!(activity instanceof GoogleApiActivity) && Looper.myLooper() == Looper.getMainLooper()) {
            zaab.zaa(activity, this.zaa, this.zaf);
        }
        this.zaa.zad(this);
    }

    private final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zad(int i, @NonNull T t) {
        t.zak();
        this.zaa.zak(this, i, t);
        return t;
    }

    private final <TResult, A extends Api.AnyClient> Task<TResult> zae(int i, @NonNull TaskApiCall<A, TResult> taskApiCall) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zaa.zal(this, i, taskApiCall, taskCompletionSource, this.zaj);
        return taskCompletionSource.getTask();
    }

    @Nullable
    private static String zaf(Object obj) {
        if (PlatformVersion.isAtLeastR()) {
            try {
                return (String) Context.class.getMethod("getAttributionTag", new Class[0]).invoke(obj, new Object[0]);
            } catch (IllegalAccessException e) {
            } catch (NoSuchMethodException e2) {
            } catch (InvocationTargetException e3) {
            }
        }
        return null;
    }

    @RecentlyNonNull
    @KeepForSdk
    public GoogleApiClient asGoogleApiClient() {
        return this.zai;
    }

    @RecentlyNonNull
    @KeepForSdk
    protected ClientSettings.Builder createClientSettingsBuilder() {
        Account account;
        Set emptySet;
        GoogleSignInAccount googleSignInAccount;
        ClientSettings.Builder builder = new ClientSettings.Builder();
        O o = this.zae;
        if (!(o instanceof Api.ApiOptions.HasGoogleSignInAccountOptions) || (googleSignInAccount = ((Api.ApiOptions.HasGoogleSignInAccountOptions) o).getGoogleSignInAccount()) == null) {
            O o2 = this.zae;
            account = o2 instanceof Api.ApiOptions.HasAccountOptions ? ((Api.ApiOptions.HasAccountOptions) o2).getAccount() : null;
        } else {
            account = googleSignInAccount.getAccount();
        }
        builder.zaa(account);
        O o3 = this.zae;
        if (o3 instanceof Api.ApiOptions.HasGoogleSignInAccountOptions) {
            GoogleSignInAccount googleSignInAccount2 = ((Api.ApiOptions.HasGoogleSignInAccountOptions) o3).getGoogleSignInAccount();
            if (googleSignInAccount2 == null) {
                emptySet = Collections.emptySet();
            } else {
                emptySet = googleSignInAccount2.getRequestedScopes();
            }
        } else {
            emptySet = Collections.emptySet();
        }
        builder.zab(emptySet);
        builder.zac(this.zab.getClass().getName());
        builder.setRealClientPackageName(this.zab.getPackageName());
        return builder;
    }

    @RecentlyNonNull
    @KeepForSdk
    protected Task<Boolean> disconnectService() {
        return this.zaa.zaj(this);
    }

    @RecentlyNonNull
    @KeepForSdk
    public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doBestEffortWrite(@RecentlyNonNull T t) {
        zad(2, t);
        return t;
    }

    @RecentlyNonNull
    @KeepForSdk
    public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doRead(@RecentlyNonNull T t) {
        zad(0, t);
        return t;
    }

    @RecentlyNonNull
    @KeepForSdk
    @Deprecated
    public <A extends Api.AnyClient, T extends RegisterListenerMethod<A, ?>, U extends UnregisterListenerMethod<A, ?>> Task<Void> doRegisterEventListener(@RecentlyNonNull T t, @RecentlyNonNull U u) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(u);
        Preconditions.checkNotNull(t.getListenerKey(), "Listener has already been released.");
        Preconditions.checkNotNull(u.getListenerKey(), "Listener has already been released.");
        Preconditions.checkArgument(Objects.equal(t.getListenerKey(), u.getListenerKey()), "Listener registration and unregistration methods must be constructed with the same ListenerHolder.");
        return this.zaa.zan(this, t, u, zac.zaa);
    }

    @RecentlyNonNull
    @KeepForSdk
    public Task<Boolean> doUnregisterEventListener(@RecentlyNonNull ListenerHolder.ListenerKey<?> listenerKey) {
        return doUnregisterEventListener(listenerKey, 0);
    }

    @RecentlyNonNull
    @KeepForSdk
    public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doWrite(@RecentlyNonNull T t) {
        zad(1, t);
        return t;
    }

    @Override // com.google.android.gms.common.api.HasApiKey
    @RecentlyNonNull
    public final ApiKey<O> getApiKey() {
        return this.zaf;
    }

    @RecentlyNonNull
    @KeepForSdk
    public O getApiOptions() {
        return this.zae;
    }

    @RecentlyNonNull
    @KeepForSdk
    public Context getApplicationContext() {
        return this.zab;
    }

    @RecentlyNullable
    @KeepForSdk
    protected String getContextAttributionTag() {
        return this.zac;
    }

    @RecentlyNullable
    @KeepForSdk
    @Deprecated
    protected String getContextFeatureId() {
        return this.zac;
    }

    @RecentlyNonNull
    @KeepForSdk
    public Looper getLooper() {
        return this.zag;
    }

    @RecentlyNonNull
    @KeepForSdk
    public <L> ListenerHolder<L> registerListener(@RecentlyNonNull L l, @RecentlyNonNull String str) {
        return ListenerHolders.createListenerHolder(l, this.zag, str);
    }

    @WorkerThread
    public final Api.Client zaa(Looper looper, zabl<O> zablVar) {
        Api.Client buildClient = ((Api.AbstractClientBuilder) Preconditions.checkNotNull(this.zad.zab())).buildClient(this.zab, looper, createClientSettingsBuilder().build(), (ClientSettings) this.zae, (GoogleApiClient.ConnectionCallbacks) zablVar, (GoogleApiClient.OnConnectionFailedListener) zablVar);
        String contextAttributionTag = getContextAttributionTag();
        if (contextAttributionTag != null && (buildClient instanceof BaseGmsClient)) {
            ((BaseGmsClient) buildClient).setAttributionTag(contextAttributionTag);
        }
        if (contextAttributionTag != null && (buildClient instanceof NonGmsServiceBrokerClient)) {
            ((NonGmsServiceBrokerClient) buildClient).zaa(contextAttributionTag);
        }
        return buildClient;
    }

    public final int zab() {
        return this.zah;
    }

    public final zaco zac(Context context, Handler handler) {
        return new zaco(context, handler, createClientSettingsBuilder().build());
    }

    @RecentlyNonNull
    @KeepForSdk
    public <TResult, A extends Api.AnyClient> Task<TResult> doBestEffortWrite(@RecentlyNonNull TaskApiCall<A, TResult> taskApiCall) {
        return zae(2, taskApiCall);
    }

    @RecentlyNonNull
    @KeepForSdk
    public <TResult, A extends Api.AnyClient> Task<TResult> doRead(@RecentlyNonNull TaskApiCall<A, TResult> taskApiCall) {
        return zae(0, taskApiCall);
    }

    @RecentlyNonNull
    @KeepForSdk
    public Task<Boolean> doUnregisterEventListener(@RecentlyNonNull ListenerHolder.ListenerKey<?> listenerKey, int i) {
        Preconditions.checkNotNull(listenerKey, "Listener key cannot be null.");
        return this.zaa.zao(this, listenerKey, i);
    }

    @RecentlyNonNull
    @KeepForSdk
    public <TResult, A extends Api.AnyClient> Task<TResult> doWrite(@RecentlyNonNull TaskApiCall<A, TResult> taskApiCall) {
        return zae(1, taskApiCall);
    }

    @RecentlyNonNull
    @KeepForSdk
    public <A extends Api.AnyClient> Task<Void> doRegisterEventListener(@RecentlyNonNull RegistrationMethods<A, ?> registrationMethods) {
        Preconditions.checkNotNull(registrationMethods);
        Preconditions.checkNotNull(registrationMethods.register.getListenerKey(), "Listener has already been released.");
        Preconditions.checkNotNull(registrationMethods.zaa.getListenerKey(), "Listener has already been released.");
        return this.zaa.zan(this, registrationMethods.register, registrationMethods.zaa, registrationMethods.zab);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @com.google.android.gms.common.annotation.KeepForSdk
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public GoogleApi(@androidx.annotation.RecentlyNonNull android.app.Activity r12, @androidx.annotation.RecentlyNonNull com.google.android.gms.common.api.Api<O> r13, @androidx.annotation.RecentlyNonNull O r14, @androidx.annotation.RecentlyNonNull com.google.android.gms.common.api.internal.StatusExceptionMapper r15) {
        /*
            r11 = this;
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            com.google.android.gms.common.api.GoogleApi$Settings$Builder r6 = new com.google.android.gms.common.api.GoogleApi$Settings$Builder
            r5 = r6
            r6 = r5
            r6.<init>()
            r6 = r5
            r7 = r4
            com.google.android.gms.common.api.GoogleApi$Settings$Builder r6 = r6.setMapper(r7)
            r6 = r5
            r7 = r1
            android.os.Looper r7 = r7.getMainLooper()
            com.google.android.gms.common.api.GoogleApi$Settings$Builder r6 = r6.setLooper(r7)
            r6 = r0
            r7 = r1
            r8 = r2
            r9 = r3
            r10 = r5
            com.google.android.gms.common.api.GoogleApi$Settings r10 = r10.build()
            r6.<init>(r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.internal.StatusExceptionMapper):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @com.google.android.gms.common.annotation.KeepForSdk
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public GoogleApi(@androidx.annotation.RecentlyNonNull android.content.Context r13, @androidx.annotation.RecentlyNonNull com.google.android.gms.common.api.Api<O> r14, @androidx.annotation.RecentlyNonNull O r15, @androidx.annotation.RecentlyNonNull android.os.Looper r16, @androidx.annotation.RecentlyNonNull com.google.android.gms.common.api.internal.StatusExceptionMapper r17) {
        /*
            r12 = this;
            r0 = r12
            r1 = r13
            r2 = r14
            r3 = r15
            r4 = r16
            r5 = r17
            com.google.android.gms.common.api.GoogleApi$Settings$Builder r7 = new com.google.android.gms.common.api.GoogleApi$Settings$Builder
            r6 = r7
            r7 = r6
            r7.<init>()
            r7 = r6
            r8 = r4
            com.google.android.gms.common.api.GoogleApi$Settings$Builder r7 = r7.setLooper(r8)
            r7 = r6
            r8 = r5
            com.google.android.gms.common.api.GoogleApi$Settings$Builder r7 = r7.setMapper(r8)
            r7 = r0
            r8 = r1
            r9 = r2
            r10 = r3
            r11 = r6
            com.google.android.gms.common.api.GoogleApi$Settings r11 = r11.build()
            r7.<init>(r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.GoogleApi.<init>(android.content.Context, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, android.os.Looper, com.google.android.gms.common.api.internal.StatusExceptionMapper):void");
    }

    @KeepForSdk
    public GoogleApi(@RecentlyNonNull Context context, @RecentlyNonNull Api<O> api, @RecentlyNonNull O o, @RecentlyNonNull Settings settings) {
        Preconditions.checkNotNull(context, "Null context is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(settings, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.zab = context.getApplicationContext();
        this.zac = zaf(context);
        this.zad = api;
        this.zae = o;
        this.zag = settings.zab;
        this.zaf = ApiKey.zaa(this.zad, this.zae, this.zac);
        this.zai = new zabp(this);
        this.zaa = GoogleApiManager.zaa(this.zab);
        this.zah = this.zaa.zac();
        this.zaj = settings.zaa;
        this.zaa.zad(this);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @com.google.android.gms.common.annotation.KeepForSdk
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public GoogleApi(@androidx.annotation.RecentlyNonNull android.content.Context r12, @androidx.annotation.RecentlyNonNull com.google.android.gms.common.api.Api<O> r13, @androidx.annotation.RecentlyNonNull O r14, @androidx.annotation.RecentlyNonNull com.google.android.gms.common.api.internal.StatusExceptionMapper r15) {
        /*
            r11 = this;
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            com.google.android.gms.common.api.GoogleApi$Settings$Builder r6 = new com.google.android.gms.common.api.GoogleApi$Settings$Builder
            r5 = r6
            r6 = r5
            r6.<init>()
            r6 = r5
            r7 = r4
            com.google.android.gms.common.api.GoogleApi$Settings$Builder r6 = r6.setMapper(r7)
            r6 = r0
            r7 = r1
            r8 = r2
            r9 = r3
            r10 = r5
            com.google.android.gms.common.api.GoogleApi$Settings r10 = r10.build()
            r6.<init>(r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.GoogleApi.<init>(android.content.Context, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.internal.StatusExceptionMapper):void");
    }
}
