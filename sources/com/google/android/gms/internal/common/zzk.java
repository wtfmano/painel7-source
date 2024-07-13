package com.google.android.gms.internal.common;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzk extends zzj {
    public static boolean zza(@NullableDecl Object obj, @NullableDecl Object obj2) {
        boolean z;
        if (obj == obj2) {
            z = true;
        } else if (obj == null) {
            z = false;
        } else if (!obj.equals(obj2)) {
            return false;
        } else {
            z = true;
        }
        return z;
    }
}
