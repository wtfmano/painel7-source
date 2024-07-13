package com.google.android.gms.common.api.internal;

import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaac {
    private final ApiKey<?> zaa;
    private final TaskCompletionSource<Boolean> zab = new TaskCompletionSource<>();

    public zaac(ApiKey<?> apiKey) {
        this.zaa = apiKey;
    }

    public final ApiKey<?> zaa() {
        return this.zaa;
    }

    public final TaskCompletionSource<Boolean> zab() {
        return this.zab;
    }
}
