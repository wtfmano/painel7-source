package com.google.android.gms.common.api.internal;

import android.app.Activity;
import androidx.annotation.MainThread;
import androidx.annotation.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
@VisibleForTesting(otherwise = 2)
/* loaded from: classes.dex */
final class zaa extends LifecycleCallback {
    private List<Runnable> zaa;

    private zaa(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.zaa = new ArrayList();
        this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
    }

    public static /* synthetic */ zaa zaa(Activity activity) {
        zaa zaaVar;
        synchronized (activity) {
            LifecycleFragment fragment = getFragment(activity);
            zaa zaaVar2 = (zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", zaa.class);
            zaaVar = zaaVar2 == null ? new zaa(fragment) : zaaVar2;
        }
        return zaaVar;
    }

    public final synchronized void zac(Runnable runnable) {
        this.zaa.add(runnable);
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    @MainThread
    public final void onStop() {
        List<Runnable> list;
        synchronized (this) {
            list = this.zaa;
            this.zaa = new ArrayList();
        }
        for (Runnable runnable : list) {
            runnable.run();
        }
    }
}
