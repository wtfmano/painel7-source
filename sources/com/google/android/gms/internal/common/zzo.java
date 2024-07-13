package com.google.android.gms.internal.common;

import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
class zzo<E> extends zzp<E> {
    Object[] zza = new Object[4];
    int zzb = 0;
    boolean zzc;

    public zzo(int i) {
    }

    private final void zzb(int i) {
        int i2;
        Object[] objArr = this.zza;
        int length = objArr.length;
        if (length >= i) {
            if (this.zzc) {
                this.zza = (Object[]) objArr.clone();
                this.zzc = false;
                return;
            }
            return;
        }
        int i3 = length + (length >> 1) + 1;
        if (i3 < i) {
            int highestOneBit = Integer.highestOneBit(i - 1);
            i2 = highestOneBit + highestOneBit;
        } else {
            i2 = i3;
        }
        if (i2 < 0) {
            i2 = Integer.MAX_VALUE;
        }
        this.zza = Arrays.copyOf(objArr, i2);
        this.zzc = false;
    }

    public final zzo<E> zza(E e) {
        if (e != null) {
            zzb(this.zzb + 1);
            Object[] objArr = this.zza;
            int i = this.zzb;
            this.zzb = i + 1;
            objArr[i] = e;
            return this;
        }
        throw null;
    }
}
