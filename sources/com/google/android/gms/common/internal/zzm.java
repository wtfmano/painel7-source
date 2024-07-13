package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzm {
    private static final Uri zzf = new Uri.Builder().scheme("content").authority("com.google.android.gms.chimera").build();
    @Nullable
    private final String zza;
    @Nullable
    private final String zzb;
    @Nullable
    private final ComponentName zzc;
    private final int zzd;
    private final boolean zze;

    public zzm(ComponentName componentName, int i) {
        this.zza = null;
        this.zzb = null;
        Preconditions.checkNotNull(componentName);
        this.zzc = componentName;
        this.zzd = i;
        this.zze = false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, Integer.valueOf(this.zzd), Boolean.valueOf(this.zze));
    }

    public final String toString() {
        String str = this.zza;
        if (str == null) {
            Preconditions.checkNotNull(this.zzc);
            return this.zzc.flattenToString();
        }
        return str;
    }

    @Nullable
    public final String zza() {
        return this.zzb;
    }

    @Nullable
    public final ComponentName zzb() {
        return this.zzc;
    }

    public final int zzc() {
        return this.zzd;
    }

    public final Intent zzd(Context context) {
        Intent component;
        Intent intent;
        Bundle bundle;
        if (this.zza != null) {
            if (this.zze) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("serviceActionBundleKey", this.zza);
                try {
                    bundle = context.getContentResolver().call(zzf, "serviceIntentCall", (String) null, bundle2);
                } catch (IllegalArgumentException e) {
                    String valueOf = String.valueOf(e);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34);
                    sb.append("Dynamic intent resolution failed: ");
                    sb.append(valueOf);
                    Log.w("ConnectionStatusConfig", sb.toString());
                    bundle = null;
                }
                intent = bundle == null ? null : (Intent) bundle.getParcelable("serviceResponseIntentKey");
                if (intent == null) {
                    String valueOf2 = String.valueOf(this.zza);
                    Log.w("ConnectionStatusConfig", valueOf2.length() != 0 ? "Dynamic lookup for intent failed for action: ".concat(valueOf2) : new String("Dynamic lookup for intent failed for action: "));
                }
            } else {
                intent = null;
            }
            if (intent == null) {
                return new Intent(this.zza).setPackage(this.zzb);
            }
            component = intent;
        } else {
            component = new Intent().setComponent(this.zzc);
        }
        return component;
    }

    public zzm(String str, String str2, int i) {
        this(str, "com.google.android.gms", i, false);
    }

    public zzm(String str, String str2, int i, boolean z) {
        Preconditions.checkNotEmpty(str);
        this.zza = str;
        Preconditions.checkNotEmpty(str2);
        this.zzb = str2;
        this.zzc = null;
        this.zzd = i;
        this.zze = z;
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzm) {
            zzm zzmVar = (zzm) obj;
            return Objects.equal(this.zza, zzmVar.zza) && Objects.equal(this.zzb, zzmVar.zzb) && Objects.equal(this.zzc, zzmVar.zzc) && this.zzd == zzmVar.zzd && this.zze == zzmVar.zze;
        }
        return false;
    }
}
