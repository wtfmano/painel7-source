package com.google.android.gms.common.internal.service;

import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.TelemetryData;
import com.google.android.gms.common.internal.TelemetryLoggingClient;
import com.google.android.gms.common.internal.TelemetryLoggingOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zao extends GoogleApi<TelemetryLoggingOptions> implements TelemetryLoggingClient {
    public static final /* synthetic */ int zab = 0;
    private static final Api.ClientKey<zap> zac = new Api.ClientKey<>();
    private static final Api.AbstractClientBuilder<zap, TelemetryLoggingOptions> zad = new zan();
    private static final Api<TelemetryLoggingOptions> zae = new Api<>("ClientTelemetry.API", zad, zac);

    public zao(Context context, TelemetryLoggingOptions telemetryLoggingOptions) {
        super(context, zae, telemetryLoggingOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @Override // com.google.android.gms.common.internal.TelemetryLoggingClient
    public final Task<Void> log(TelemetryData telemetryData) {
        TaskApiCall.Builder builder = TaskApiCall.builder();
        builder.setFeatures(com.google.android.gms.internal.base.zad.zaa);
        builder.setAutoResolveMissingFeatures(false);
        builder.run(new RemoteCall(telemetryData) { // from class: com.google.android.gms.common.internal.service.zam
            private final TelemetryData zaa;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.zaa = telemetryData;
            }

            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                TelemetryData telemetryData2 = this.zaa;
                int i = zao.zab;
                ((zai) ((zap) obj).getService()).zae(telemetryData2);
                ((TaskCompletionSource) obj2).setResult(null);
            }
        });
        return doBestEffortWrite(builder.build());
    }
}
