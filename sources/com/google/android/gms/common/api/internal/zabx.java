package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.common.internal.MethodInvocation;
import com.google.android.gms.common.internal.RootTelemetryConfigManager;
import com.google.android.gms.common.internal.RootTelemetryConfiguration;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zabx<T> implements OnCompleteListener<T> {
    private final GoogleApiManager zaa;
    private final int zab;
    private final ApiKey<?> zac;
    private final long zad;

    @VisibleForTesting
    zabx(GoogleApiManager googleApiManager, int i, ApiKey<?> apiKey, long j, @Nullable String str, @Nullable String str2) {
        this.zaa = googleApiManager;
        this.zab = i;
        this.zac = apiKey;
        this.zad = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static <T> zabx<T> zaa(GoogleApiManager googleApiManager, int i, ApiKey<?> apiKey) {
        boolean z;
        if (googleApiManager.zam()) {
            RootTelemetryConfiguration config = RootTelemetryConfigManager.getInstance().getConfig();
            if (config == null) {
                z = true;
            } else if (!config.getMethodInvocationTelemetryEnabled()) {
                return null;
            } else {
                z = config.getMethodTimingTelemetryEnabled();
                zabl zag = googleApiManager.zag(apiKey);
                if (zag != null) {
                    if (!(zag.zaf() instanceof BaseGmsClient)) {
                        return null;
                    }
                    BaseGmsClient baseGmsClient = (BaseGmsClient) zag.zaf();
                    if (baseGmsClient.hasConnectionInfo() && !baseGmsClient.isConnecting()) {
                        ConnectionTelemetryConfiguration zab = zab(zag, baseGmsClient, i);
                        if (zab == null) {
                            return null;
                        }
                        zag.zas();
                        z = zab.getMethodTimingTelemetryEnabled();
                    }
                }
            }
            return new zabx<>(googleApiManager, i, apiKey, z ? System.currentTimeMillis() : 0L, null, null);
        }
        return null;
    }

    @Nullable
    private static ConnectionTelemetryConfiguration zab(zabl<?> zablVar, BaseGmsClient<?> baseGmsClient, int i) {
        int[] methodInvocationMethodKeyAllowlist;
        int[] methodInvocationMethodKeyDisallowlist;
        ConnectionTelemetryConfiguration telemetryConfiguration = baseGmsClient.getTelemetryConfiguration();
        if (telemetryConfiguration == null || !telemetryConfiguration.getMethodInvocationTelemetryEnabled() || ((methodInvocationMethodKeyAllowlist = telemetryConfiguration.getMethodInvocationMethodKeyAllowlist()) != null ? !ArrayUtils.contains(methodInvocationMethodKeyAllowlist, i) : !((methodInvocationMethodKeyDisallowlist = telemetryConfiguration.getMethodInvocationMethodKeyDisallowlist()) == null || !ArrayUtils.contains(methodInvocationMethodKeyDisallowlist, i)))) {
            return null;
        }
        if (zablVar.zar() < telemetryConfiguration.getMaxMethodInvocationsLogged()) {
            return telemetryConfiguration;
        }
        return null;
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    @WorkerThread
    public final void onComplete(@NonNull Task<T> task) {
        zabl zag;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        long j;
        long j2;
        if (this.zaa.zam()) {
            RootTelemetryConfiguration config = RootTelemetryConfigManager.getInstance().getConfig();
            if ((config == null || config.getMethodInvocationTelemetryEnabled()) && (zag = this.zaa.zag(this.zac)) != null && (zag.zaf() instanceof BaseGmsClient)) {
                BaseGmsClient baseGmsClient = (BaseGmsClient) zag.zaf();
                boolean z = this.zad > 0;
                int gCoreServiceId = baseGmsClient.getGCoreServiceId();
                if (config != null) {
                    z &= config.getMethodTimingTelemetryEnabled();
                    int batchPeriodMillis = config.getBatchPeriodMillis();
                    int maxMethodInvocationsInBatch = config.getMaxMethodInvocationsInBatch();
                    int version = config.getVersion();
                    if (!baseGmsClient.hasConnectionInfo()) {
                        i = maxMethodInvocationsInBatch;
                        i2 = batchPeriodMillis;
                        i3 = version;
                    } else if (baseGmsClient.isConnecting()) {
                        i = maxMethodInvocationsInBatch;
                        i2 = batchPeriodMillis;
                        i3 = version;
                    } else {
                        ConnectionTelemetryConfiguration zab = zab(zag, baseGmsClient, this.zab);
                        if (zab != null) {
                            z = zab.getMethodTimingTelemetryEnabled() ? this.zad > 0 : false;
                            i = zab.getMaxMethodInvocationsLogged();
                            i2 = batchPeriodMillis;
                            i3 = version;
                        } else {
                            return;
                        }
                    }
                } else {
                    i = 100;
                    i2 = 5000;
                    i3 = 0;
                }
                GoogleApiManager googleApiManager = this.zaa;
                if (task.isSuccessful()) {
                    i4 = 0;
                    i5 = 0;
                } else if (task.isCanceled()) {
                    i4 = 100;
                    i5 = -1;
                } else {
                    Exception exception = task.getException();
                    if (exception instanceof ApiException) {
                        Status status = ((ApiException) exception).getStatus();
                        int statusCode = status.getStatusCode();
                        ConnectionResult connectionResult = status.getConnectionResult();
                        i5 = connectionResult == null ? -1 : connectionResult.getErrorCode();
                        i4 = statusCode;
                    } else {
                        i4 = 101;
                        i5 = -1;
                    }
                }
                if (z) {
                    j = this.zad;
                    j2 = System.currentTimeMillis();
                } else {
                    j = 0;
                    j2 = 0;
                }
                googleApiManager.zar(new MethodInvocation(this.zab, i4, i5, j, j2, null, null, gCoreServiceId), i3, i2, i);
            }
        }
    }
}
