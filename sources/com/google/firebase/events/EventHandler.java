package com.google.firebase.events;

import com.google.android.gms.common.annotation.KeepForSdk;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
@KeepForSdk
/* loaded from: classes.dex */
public interface EventHandler<T> {
    @KeepForSdk
    void handle(Event<T> event);
}
