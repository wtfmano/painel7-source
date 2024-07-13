package com.google.firebase;

import com.google.android.gms.common.annotation.KeepForSdk;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
@KeepForSdk
/* loaded from: classes.dex */
public interface FirebaseAppLifecycleListener {
    void onDeleted(String str, FirebaseOptions firebaseOptions);
}