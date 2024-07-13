package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public abstract class BaseGmsClient<T extends IInterface> {
    @KeepForSdk
    public static final int CONNECT_STATE_CONNECTED = 4;
    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTED = 1;
    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTING = 5;
    @RecentlyNonNull
    @KeepForSdk
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    @RecentlyNonNull
    @KeepForSdk
    public static final String KEY_PENDING_INTENT = "pendingIntent";
    @Nullable
    private volatile String zzA;
    @Nullable
    private ConnectionResult zzB;
    private boolean zzC;
    @Nullable
    private volatile zzi zzD;
    @VisibleForTesting
    zzt zza;
    final Handler zzb;
    @RecentlyNonNull
    @VisibleForTesting
    protected ConnectionProgressReportCallbacks zzc;
    @RecentlyNonNull
    @VisibleForTesting
    protected AtomicInteger zzd;
    private int zzf;
    private long zzg;
    private long zzh;
    private int zzi;
    private long zzj;
    @Nullable
    private volatile String zzk;
    private final Context zzl;
    private final Looper zzm;
    private final GmsClientSupervisor zzn;
    private final GoogleApiAvailabilityLight zzo;
    private final Object zzp;
    private final Object zzq;
    @Nullable
    @GuardedBy("mServiceBrokerLock")
    private IGmsServiceBroker zzr;
    @Nullable
    @GuardedBy("mLock")
    private T zzs;
    private final ArrayList<zzc<?>> zzt;
    @Nullable
    @GuardedBy("mLock")
    private zze zzu;
    @GuardedBy("mLock")
    private int zzv;
    @Nullable
    private final BaseConnectionCallbacks zzw;
    @Nullable
    private final BaseOnConnectionFailedListener zzx;
    private final int zzy;
    @Nullable
    private final String zzz;
    private static final Feature[] zze = new Feature[0];
    @RecentlyNonNull
    @KeepForSdk
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = {"service_esmobile", "service_googleme"};

    /* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
    @KeepForSdk
    /* loaded from: classes.dex */
    public interface BaseConnectionCallbacks {
        @KeepForSdk
        public static final int CAUSE_DEAD_OBJECT_EXCEPTION = 3;
        @KeepForSdk
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        @KeepForSdk
        void onConnected(@Nullable Bundle bundle);

        @KeepForSdk
        void onConnectionSuspended(int i);
    }

    /* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
    @KeepForSdk
    /* loaded from: classes.dex */
    public interface BaseOnConnectionFailedListener {
        @KeepForSdk
        void onConnectionFailed(@RecentlyNonNull ConnectionResult connectionResult);
    }

    /* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
    @KeepForSdk
    /* loaded from: classes.dex */
    public interface ConnectionProgressReportCallbacks {
        @KeepForSdk
        void onReportServiceBinding(@RecentlyNonNull ConnectionResult connectionResult);
    }

    /* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
    /* loaded from: classes.dex */
    protected class LegacyClientCallbackAdapter implements ConnectionProgressReportCallbacks {
        @KeepForSdk
        public LegacyClientCallbackAdapter() {
            BaseGmsClient.this = r5;
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
        public final void onReportServiceBinding(@RecentlyNonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                baseGmsClient.getRemoteService(null, baseGmsClient.getScopes());
            } else if (BaseGmsClient.this.zzx != null) {
                BaseGmsClient.this.zzx.onConnectionFailed(connectionResult);
            }
        }
    }

    /* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
    @KeepForSdk
    /* loaded from: classes.dex */
    public interface SignOutCallbacks {
        @KeepForSdk
        void onSignOutComplete();
    }

    @VisibleForTesting
    @KeepForSdk
    public BaseGmsClient(@RecentlyNonNull Context context, @RecentlyNonNull Handler handler, @RecentlyNonNull GmsClientSupervisor gmsClientSupervisor, @RecentlyNonNull GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, @Nullable BaseConnectionCallbacks baseConnectionCallbacks, @Nullable BaseOnConnectionFailedListener baseOnConnectionFailedListener) {
        this.zzk = null;
        this.zzp = new Object();
        this.zzq = new Object();
        this.zzt = new ArrayList<>();
        this.zzv = 1;
        this.zzB = null;
        this.zzC = false;
        this.zzD = null;
        this.zzd = new AtomicInteger(0);
        Preconditions.checkNotNull(context, "Context must not be null");
        this.zzl = context;
        Preconditions.checkNotNull(handler, "Handler must not be null");
        this.zzb = handler;
        this.zzm = handler.getLooper();
        Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzn = gmsClientSupervisor;
        Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzo = googleApiAvailabilityLight;
        this.zzy = i;
        this.zzw = baseConnectionCallbacks;
        this.zzx = baseOnConnectionFailedListener;
        this.zzz = null;
    }

    public static /* synthetic */ void zzc(BaseGmsClient baseGmsClient, int i) {
        int i2;
        int i3;
        synchronized (baseGmsClient.zzp) {
            i2 = baseGmsClient.zzv;
        }
        if (i2 == 3) {
            baseGmsClient.zzC = true;
            i3 = 5;
        } else {
            i3 = 4;
        }
        Handler handler = baseGmsClient.zzb;
        handler.sendMessage(handler.obtainMessage(i3, baseGmsClient.zzd.get(), 16));
    }

    public static /* synthetic */ boolean zzg(BaseGmsClient baseGmsClient) {
        boolean z;
        if (baseGmsClient.zzC) {
            z = false;
        } else if (TextUtils.isEmpty(baseGmsClient.getServiceDescriptor())) {
            z = false;
        } else if (TextUtils.isEmpty(baseGmsClient.getLocalStartServiceAction())) {
            z = false;
        } else {
            try {
                Class.forName(baseGmsClient.getServiceDescriptor());
                z = true;
            } catch (ClassNotFoundException e) {
                z = false;
            }
        }
        return z;
    }

    public static /* synthetic */ boolean zzl(BaseGmsClient baseGmsClient, int i, int i2, IInterface iInterface) {
        boolean z;
        synchronized (baseGmsClient.zzp) {
            if (baseGmsClient.zzv != i) {
                z = false;
            } else {
                baseGmsClient.zzp(i2, iInterface);
                z = true;
            }
        }
        return z;
    }

    public static /* synthetic */ void zzo(BaseGmsClient baseGmsClient, zzi zziVar) {
        baseGmsClient.zzD = zziVar;
        if (baseGmsClient.usesClientTelemetry()) {
            ConnectionTelemetryConfiguration connectionTelemetryConfiguration = zziVar.zzd;
            RootTelemetryConfigManager.getInstance().zza(connectionTelemetryConfiguration == null ? null : connectionTelemetryConfiguration.getRootTelemetryConfiguration());
        }
    }

    public final void zzp(int i, @Nullable T t) {
        zzt zztVar;
        Preconditions.checkArgument((i == 4) == (t != null));
        synchronized (this.zzp) {
            this.zzv = i;
            this.zzs = t;
            switch (i) {
                case 1:
                    zze zzeVar = this.zzu;
                    if (zzeVar != null) {
                        GmsClientSupervisor gmsClientSupervisor = this.zzn;
                        String zza = this.zza.zza();
                        Preconditions.checkNotNull(zza);
                        gmsClientSupervisor.zza(zza, this.zza.zzb(), this.zza.zzc(), zzeVar, zza(), this.zza.zzd());
                        this.zzu = null;
                        break;
                    }
                    break;
                case 2:
                case 3:
                    zze zzeVar2 = this.zzu;
                    if (zzeVar2 != null && (zztVar = this.zza) != null) {
                        String zza2 = zztVar.zza();
                        String zzb = this.zza.zzb();
                        StringBuilder sb = new StringBuilder(String.valueOf(zza2).length() + 70 + String.valueOf(zzb).length());
                        sb.append("Calling connect() while still connected, missing disconnect() for ");
                        sb.append(zza2);
                        sb.append(" on ");
                        sb.append(zzb);
                        Log.e("GmsClient", sb.toString());
                        GmsClientSupervisor gmsClientSupervisor2 = this.zzn;
                        String zza3 = this.zza.zza();
                        Preconditions.checkNotNull(zza3);
                        gmsClientSupervisor2.zza(zza3, this.zza.zzb(), this.zza.zzc(), zzeVar2, zza(), this.zza.zzd());
                        this.zzd.incrementAndGet();
                    }
                    zze zzeVar3 = new zze(this, this.zzd.get());
                    this.zzu = zzeVar3;
                    this.zza = (this.zzv != 3 || getLocalStartServiceAction() == null) ? new zzt(getStartServicePackage(), getStartServiceAction(), false, GmsClientSupervisor.getDefaultBindFlags(), getUseDynamicLookup()) : new zzt(getContext().getPackageName(), getLocalStartServiceAction(), true, GmsClientSupervisor.getDefaultBindFlags(), false);
                    if (this.zza.zzd() && getMinApkVersion() < 17895000) {
                        String valueOf = String.valueOf(this.zza.zza());
                        throw new IllegalStateException(valueOf.length() != 0 ? "Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: ".concat(valueOf) : new String("Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: "));
                    }
                    GmsClientSupervisor gmsClientSupervisor3 = this.zzn;
                    String zza4 = this.zza.zza();
                    Preconditions.checkNotNull(zza4);
                    if (!gmsClientSupervisor3.zzb(new zzm(zza4, this.zza.zzb(), this.zza.zzc(), this.zza.zzd()), zzeVar3, zza())) {
                        String zza5 = this.zza.zza();
                        String zzb2 = this.zza.zzb();
                        StringBuilder sb2 = new StringBuilder(String.valueOf(zza5).length() + 34 + String.valueOf(zzb2).length());
                        sb2.append("unable to connect to service: ");
                        sb2.append(zza5);
                        sb2.append(" on ");
                        sb2.append(zzb2);
                        Log.e("GmsClient", sb2.toString());
                        zzb(16, null, this.zzd.get());
                        break;
                    }
                    break;
                case 4:
                    Preconditions.checkNotNull(t);
                    onConnectedLocked(t);
                    break;
            }
        }
    }

    @KeepForSdk
    public void checkAvailabilityAndConnect() {
        int isGooglePlayServicesAvailable = this.zzo.isGooglePlayServicesAvailable(this.zzl, getMinApkVersion());
        if (isGooglePlayServicesAvailable != 0) {
            zzp(1, null);
            triggerNotAvailable(new LegacyClientCallbackAdapter(), isGooglePlayServicesAvailable, null);
            return;
        }
        connect(new LegacyClientCallbackAdapter());
    }

    @KeepForSdk
    protected final void checkConnected() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    @KeepForSdk
    public void connect(@RecentlyNonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        this.zzc = connectionProgressReportCallbacks;
        zzp(2, null);
    }

    @RecentlyNullable
    @KeepForSdk
    public abstract T createServiceInterface(@RecentlyNonNull IBinder iBinder);

    @KeepForSdk
    public void disconnect() {
        this.zzd.incrementAndGet();
        synchronized (this.zzt) {
            int size = this.zzt.size();
            for (int i = 0; i < size; i++) {
                this.zzt.get(i).zzg();
            }
            this.zzt.clear();
        }
        synchronized (this.zzq) {
            this.zzr = null;
        }
        zzp(1, null);
    }

    @KeepForSdk
    public void dump(@RecentlyNonNull String str, @RecentlyNonNull FileDescriptor fileDescriptor, @RecentlyNonNull PrintWriter printWriter, @RecentlyNonNull String[] strArr) {
        int i;
        T t;
        IGmsServiceBroker iGmsServiceBroker;
        synchronized (this.zzp) {
            i = this.zzv;
            t = this.zzs;
        }
        synchronized (this.zzq) {
            iGmsServiceBroker = this.zzr;
        }
        printWriter.append((CharSequence) str).append("mConnectState=");
        switch (i) {
            case 1:
                printWriter.print("DISCONNECTED");
                break;
            case 2:
                printWriter.print("REMOTE_CONNECTING");
                break;
            case 3:
                printWriter.print("LOCAL_CONNECTING");
                break;
            case 4:
                printWriter.print("CONNECTED");
                break;
            case 5:
                printWriter.print("DISCONNECTING");
                break;
            default:
                printWriter.print("UNKNOWN");
                break;
        }
        printWriter.append(" mService=");
        if (t == null) {
            printWriter.append("null");
        } else {
            printWriter.append((CharSequence) getServiceDescriptor()).append("@").append((CharSequence) Integer.toHexString(System.identityHashCode(t.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (iGmsServiceBroker == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(iGmsServiceBroker.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzh > 0) {
            PrintWriter append = printWriter.append((CharSequence) str).append("lastConnectedTime=");
            long j = this.zzh;
            String format = simpleDateFormat.format(new Date(j));
            StringBuilder sb = new StringBuilder(String.valueOf(format).length() + 21);
            sb.append(j);
            sb.append(" ");
            sb.append(format);
            append.println(sb.toString());
        }
        if (this.zzg > 0) {
            printWriter.append((CharSequence) str).append("lastSuspendedCause=");
            int i2 = this.zzf;
            switch (i2) {
                case 1:
                    printWriter.append("CAUSE_SERVICE_DISCONNECTED");
                    break;
                case 2:
                    printWriter.append("CAUSE_NETWORK_LOST");
                    break;
                case 3:
                    printWriter.append("CAUSE_DEAD_OBJECT_EXCEPTION");
                    break;
                default:
                    printWriter.append((CharSequence) String.valueOf(i2));
                    break;
            }
            PrintWriter append2 = printWriter.append(" lastSuspendedTime=");
            long j2 = this.zzg;
            String format2 = simpleDateFormat.format(new Date(j2));
            StringBuilder sb2 = new StringBuilder(String.valueOf(format2).length() + 21);
            sb2.append(j2);
            sb2.append(" ");
            sb2.append(format2);
            append2.println(sb2.toString());
        }
        if (this.zzj > 0) {
            printWriter.append((CharSequence) str).append("lastFailedStatus=").append((CharSequence) CommonStatusCodes.getStatusCodeString(this.zzi));
            PrintWriter append3 = printWriter.append(" lastFailedTime=");
            long j3 = this.zzj;
            String format3 = simpleDateFormat.format(new Date(j3));
            StringBuilder sb3 = new StringBuilder(String.valueOf(format3).length() + 21);
            sb3.append(j3);
            sb3.append(" ");
            sb3.append(format3);
            append3.println(sb3.toString());
        }
    }

    @KeepForSdk
    public boolean enableLocalFallback() {
        return false;
    }

    @RecentlyNullable
    @KeepForSdk
    public Account getAccount() {
        return null;
    }

    @RecentlyNonNull
    @KeepForSdk
    public Feature[] getApiFeatures() {
        return zze;
    }

    @RecentlyNullable
    @KeepForSdk
    public final Feature[] getAvailableFeatures() {
        zzi zziVar = this.zzD;
        if (zziVar == null) {
            return null;
        }
        return zziVar.zzb;
    }

    @RecentlyNullable
    @KeepForSdk
    public Bundle getConnectionHint() {
        return null;
    }

    @RecentlyNonNull
    @KeepForSdk
    public final Context getContext() {
        return this.zzl;
    }

    @RecentlyNonNull
    @KeepForSdk
    public String getEndpointPackageName() {
        zzt zztVar;
        if (!isConnected() || (zztVar = this.zza) == null) {
            throw new RuntimeException("Failed to connect when checking package");
        }
        return zztVar.zzb();
    }

    @KeepForSdk
    public int getGCoreServiceId() {
        return this.zzy;
    }

    @RecentlyNonNull
    @KeepForSdk
    protected Bundle getGetServiceRequestExtraArgs() {
        return new Bundle();
    }

    @RecentlyNullable
    @KeepForSdk
    public String getLastDisconnectMessage() {
        return this.zzk;
    }

    @RecentlyNullable
    @KeepForSdk
    protected String getLocalStartServiceAction() {
        return null;
    }

    @RecentlyNonNull
    @KeepForSdk
    public final Looper getLooper() {
        return this.zzm;
    }

    @KeepForSdk
    public int getMinApkVersion() {
        return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    @KeepForSdk
    @WorkerThread
    public void getRemoteService(@Nullable IAccountAccessor iAccountAccessor, @RecentlyNonNull Set<Scope> set) {
        Bundle getServiceRequestExtraArgs = getGetServiceRequestExtraArgs();
        GetServiceRequest getServiceRequest = new GetServiceRequest(this.zzy, this.zzA);
        getServiceRequest.zzd = this.zzl.getPackageName();
        getServiceRequest.zzg = getServiceRequestExtraArgs;
        if (set != null) {
            getServiceRequest.zzf = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (requiresSignIn()) {
            Account account = getAccount();
            if (account == null) {
                account = new Account("<<default account>>", AccountType.GOOGLE);
            }
            getServiceRequest.zzh = account;
            if (iAccountAccessor != null) {
                getServiceRequest.zze = iAccountAccessor.asBinder();
            }
        } else if (requiresAccount()) {
            getServiceRequest.zzh = getAccount();
        }
        getServiceRequest.zzi = zze;
        getServiceRequest.zzj = getApiFeatures();
        if (usesClientTelemetry()) {
            getServiceRequest.zzm = true;
        }
        try {
            synchronized (this.zzq) {
                IGmsServiceBroker iGmsServiceBroker = this.zzr;
                if (iGmsServiceBroker != null) {
                    iGmsServiceBroker.getService(new zzd(this, this.zzd.get()), getServiceRequest);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            triggerConnectionSuspended(3);
        } catch (RemoteException e2) {
            e = e2;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.zzd.get());
        } catch (SecurityException e3) {
            throw e3;
        } catch (RuntimeException e4) {
            e = e4;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.zzd.get());
        }
    }

    @RecentlyNonNull
    @KeepForSdk
    protected Set<Scope> getScopes() {
        return Collections.emptySet();
    }

    @RecentlyNonNull
    @KeepForSdk
    public final T getService() throws DeadObjectException {
        T t;
        synchronized (this.zzp) {
            if (this.zzv == 5) {
                throw new DeadObjectException();
            }
            checkConnected();
            t = this.zzs;
            Preconditions.checkNotNull(t, "Client is connected but service is null");
        }
        return t;
    }

    @NonNull
    @KeepForSdk
    public abstract String getServiceDescriptor();

    @RecentlyNonNull
    @KeepForSdk
    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    @NonNull
    @KeepForSdk
    protected abstract String getStartServiceAction();

    @RecentlyNonNull
    @KeepForSdk
    protected String getStartServicePackage() {
        return "com.google.android.gms";
    }

    @RecentlyNullable
    @KeepForSdk
    public ConnectionTelemetryConfiguration getTelemetryConfiguration() {
        zzi zziVar = this.zzD;
        if (zziVar == null) {
            return null;
        }
        return zziVar.zzd;
    }

    @KeepForSdk
    protected boolean getUseDynamicLookup() {
        return false;
    }

    @KeepForSdk
    public boolean hasConnectionInfo() {
        return this.zzD != null;
    }

    @KeepForSdk
    public boolean isConnected() {
        boolean z;
        synchronized (this.zzp) {
            z = this.zzv == 4;
        }
        return z;
    }

    @KeepForSdk
    public boolean isConnecting() {
        boolean z;
        synchronized (this.zzp) {
            int i = this.zzv;
            z = i != 2 ? i == 3 : true;
        }
        return z;
    }

    @KeepForSdk
    @CallSuper
    protected void onConnectedLocked(@RecentlyNonNull T t) {
        this.zzh = System.currentTimeMillis();
    }

    @KeepForSdk
    @CallSuper
    public void onConnectionFailed(@RecentlyNonNull ConnectionResult connectionResult) {
        this.zzi = connectionResult.getErrorCode();
        this.zzj = System.currentTimeMillis();
    }

    @KeepForSdk
    @CallSuper
    public void onConnectionSuspended(int i) {
        this.zzf = i;
        this.zzg = System.currentTimeMillis();
    }

    @KeepForSdk
    public void onPostInitHandler(int i, @Nullable IBinder iBinder, @Nullable Bundle bundle, int i2) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(1, i2, -1, new zzf(this, i, iBinder, bundle)));
    }

    @KeepForSdk
    public void onUserSignOut(@RecentlyNonNull SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
    }

    @KeepForSdk
    public boolean providesSignIn() {
        return false;
    }

    @KeepForSdk
    public boolean requiresAccount() {
        return false;
    }

    @KeepForSdk
    public boolean requiresGooglePlayServices() {
        return true;
    }

    @KeepForSdk
    public boolean requiresSignIn() {
        return false;
    }

    @KeepForSdk
    public void setAttributionTag(@RecentlyNonNull String str) {
        this.zzA = str;
    }

    @KeepForSdk
    public void triggerConnectionSuspended(int i) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(6, this.zzd.get(), i));
    }

    @VisibleForTesting
    @KeepForSdk
    protected void triggerNotAvailable(@RecentlyNonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks, int i, @Nullable PendingIntent pendingIntent) {
        Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        this.zzc = connectionProgressReportCallbacks;
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(3, this.zzd.get(), i, pendingIntent));
    }

    @KeepForSdk
    public boolean usesClientTelemetry() {
        return false;
    }

    @RecentlyNonNull
    protected final String zza() {
        String str = this.zzz;
        return str == null ? this.zzl.getClass().getName() : str;
    }

    public final void zzb(int i, @Nullable Bundle bundle, int i2) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(7, i2, -1, new zzg(this, i, null)));
    }

    @RecentlyNullable
    @KeepForSdk
    public IBinder getServiceBrokerBinder() {
        synchronized (this.zzq) {
            IGmsServiceBroker iGmsServiceBroker = this.zzr;
            if (iGmsServiceBroker == null) {
                return null;
            }
            return iGmsServiceBroker.asBinder();
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @com.google.android.gms.common.annotation.KeepForSdk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected BaseGmsClient(@androidx.annotation.RecentlyNonNull android.content.Context r19, @androidx.annotation.RecentlyNonNull android.os.Looper r20, int r21, @androidx.annotation.Nullable com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks r22, @androidx.annotation.Nullable com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener r23, @androidx.annotation.Nullable java.lang.String r24) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            r4 = r22
            r5 = r23
            r6 = r24
            r9 = r1
            com.google.android.gms.common.internal.GmsClientSupervisor r9 = com.google.android.gms.common.internal.GmsClientSupervisor.getInstance(r9)
            r7 = r9
            com.google.android.gms.common.GoogleApiAvailabilityLight r9 = com.google.android.gms.common.GoogleApiAvailabilityLight.getInstance()
            r8 = r9
            r9 = r4
            java.lang.Object r9 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r9)
            r9 = r5
            java.lang.Object r9 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r9)
            r9 = r0
            r10 = r1
            r11 = r2
            r12 = r7
            r13 = r8
            r14 = r3
            r15 = r4
            r16 = r5
            r17 = r6
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.BaseGmsClient.<init>(android.content.Context, android.os.Looper, int, com.google.android.gms.common.internal.BaseGmsClient$BaseConnectionCallbacks, com.google.android.gms.common.internal.BaseGmsClient$BaseOnConnectionFailedListener, java.lang.String):void");
    }

    @KeepForSdk
    public void disconnect(@RecentlyNonNull String str) {
        this.zzk = str;
        disconnect();
    }

    @VisibleForTesting
    @KeepForSdk
    public BaseGmsClient(@RecentlyNonNull Context context, @RecentlyNonNull Looper looper, @RecentlyNonNull GmsClientSupervisor gmsClientSupervisor, @RecentlyNonNull GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, @Nullable BaseConnectionCallbacks baseConnectionCallbacks, @Nullable BaseOnConnectionFailedListener baseOnConnectionFailedListener, @Nullable String str) {
        this.zzk = null;
        this.zzp = new Object();
        this.zzq = new Object();
        this.zzt = new ArrayList<>();
        this.zzv = 1;
        this.zzB = null;
        this.zzC = false;
        this.zzD = null;
        this.zzd = new AtomicInteger(0);
        Preconditions.checkNotNull(context, "Context must not be null");
        this.zzl = context;
        Preconditions.checkNotNull(looper, "Looper must not be null");
        this.zzm = looper;
        Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzn = gmsClientSupervisor;
        Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzo = googleApiAvailabilityLight;
        this.zzb = new zzb(this, looper);
        this.zzy = i;
        this.zzw = baseConnectionCallbacks;
        this.zzx = baseOnConnectionFailedListener;
        this.zzz = str;
    }
}
