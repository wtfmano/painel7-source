package com.google.android.gms.internal.common;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public abstract class zzq<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] zza = new Object[0];

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        return toArray(zza);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    /* renamed from: zza */
    public abstract zzx<E> iterator();

    @NullableDecl
    public Object[] zzb() {
        throw null;
    }

    public int zzc() {
        throw null;
    }

    int zzd() {
        throw null;
    }

    public zzu<E> zze() {
        throw null;
    }

    public abstract boolean zzf();

    int zzg(Object[] objArr, int i) {
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v21, types: [java.lang.Object[]] */
    @Override // java.util.AbstractCollection, java.util.Collection
    public final <T> T[] toArray(T[] tArr) {
        T[] tArr2 = tArr;
        if (tArr2 != null) {
            int size = size();
            int length = tArr2.length;
            if (length < size) {
                Object[] zzb = zzb();
                if (zzb == null) {
                    tArr2 = (Object[]) Array.newInstance(tArr2.getClass().getComponentType(), size);
                } else {
                    return (T[]) Arrays.copyOfRange(zzb, zzc(), zzd(), tArr2.getClass());
                }
            } else if (length > size) {
                tArr2[size] = null;
            }
            zzg(tArr2, 0);
            return tArr2;
        }
        throw null;
    }
}
