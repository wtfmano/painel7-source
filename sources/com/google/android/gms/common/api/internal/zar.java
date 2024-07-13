package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Result;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zar {
    final /* synthetic */ BasePendingResult zaa;

    public /* synthetic */ zar(BasePendingResult basePendingResult, zaq zaqVar) {
        this.zaa = basePendingResult;
    }

    protected final void finalize() throws Throwable {
        Result result;
        result = this.zaa.zaj;
        BasePendingResult.zal(result);
        super.finalize();
    }
}
