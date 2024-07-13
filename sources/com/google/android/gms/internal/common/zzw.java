package com.google.android.gms.internal.common;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzw<E> extends zzu<E> {
    static final zzu<Object> zza = new zzw(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    public zzw(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // java.util.List
    public final E get(int i) {
        zzl.zza(i, this.zzc, "index");
        return (E) this.zzb[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.common.zzq
    public final Object[] zzb() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.common.zzq
    public final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.common.zzq
    final int zzd() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.common.zzq
    public final boolean zzf() {
        return false;
    }

    @Override // com.google.android.gms.internal.common.zzu, com.google.android.gms.internal.common.zzq
    final int zzg(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }
}
