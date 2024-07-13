package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
@KeepForSdk
/* loaded from: classes.dex */
public interface ComponentFactory<T> {
    @KeepForSdk
    T create(ComponentContainer componentContainer);
}
