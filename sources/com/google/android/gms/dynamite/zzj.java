package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
final class zzj implements DynamiteModule.VersionPolicy {
    @Override // com.google.android.gms.dynamite.DynamiteModule.VersionPolicy
    public final zzn zza(Context context, String str, zzm zzmVar) throws DynamiteModule.LoadingException {
        int zza;
        int i;
        int i2;
        zzn zznVar = new zzn();
        int zzb = zzmVar.zzb(context, str);
        zznVar.zza = zzb;
        if (zzb != 0) {
            zza = zzmVar.zza(context, str, false);
            zznVar.zzb = zza;
            i = zza;
        } else {
            zza = zzmVar.zza(context, str, true);
            zznVar.zzb = zza;
            i = zza;
        }
        int i3 = zznVar.zza;
        if (i3 != 0) {
            i2 = i3;
        } else if (zza == 0) {
            zznVar.zzc = 0;
            return zznVar;
        } else {
            i2 = 0;
        }
        if (i >= i2) {
            zznVar.zzc = 1;
        } else {
            zznVar.zzc = -1;
        }
        return zznVar;
    }
}
