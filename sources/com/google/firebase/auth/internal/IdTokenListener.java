package com.google.firebase.auth.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.internal.InternalTokenResult;

@KeepForSdk
/* loaded from: classes.dex */
public interface IdTokenListener {
    @KeepForSdk
    void onIdTokenChanged(@NonNull InternalTokenResult internalTokenResult);
}
