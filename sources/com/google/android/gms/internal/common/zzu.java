package com.google.android.gms.internal.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public abstract class zzu<E> extends zzq<E> implements List<E>, RandomAccess {
    private static final zzy<Object> zza = new zzs(zzw.zza, 0);

    public static <E> zzu<E> zzi() {
        return (zzu<E>) zzw.zza;
    }

    public static <E> zzu<E> zzj(E e) {
        Object[] objArr = {e};
        zzv.zza(objArr, 1);
        return zzn(objArr, 1);
    }

    public static <E> zzu<E> zzk(E e, E e2) {
        Object[] objArr = {e, e2};
        zzv.zza(objArr, 2);
        return zzn(objArr, 2);
    }

    public static <E> zzu<E> zzl(Iterable<? extends E> iterable) {
        zzu<Object> zzn;
        if (iterable != null) {
            if (iterable instanceof Collection) {
                zzn = zzm((Collection) iterable);
            } else {
                Iterator<? extends E> it = iterable.iterator();
                if (!it.hasNext()) {
                    zzn = zzw.zza;
                } else {
                    E next = it.next();
                    if (!it.hasNext()) {
                        zzn = zzj(next);
                    } else {
                        zzr zzrVar = new zzr(4);
                        zzrVar.zzb((zzr) next);
                        zzrVar.zzc(it);
                        zzrVar.zzc = true;
                        zzn = zzn(zzrVar.zza, zzrVar.zzb);
                    }
                }
            }
            return zzn;
        }
        throw null;
    }

    public static <E> zzu<E> zzm(Collection<? extends E> collection) {
        if (collection instanceof zzq) {
            zzu<E> zze = ((zzq) collection).zze();
            if (zze.zzf()) {
                Object[] array = zze.toArray();
                return zzn(array, array.length);
            }
            return zze;
        }
        Object[] array2 = collection.toArray();
        int length = array2.length;
        zzv.zza(array2, length);
        return zzn(array2, length);
    }

    static <E> zzu<E> zzn(Object[] objArr, int i) {
        return i == 0 ? (zzu<E>) zzw.zza : new zzw(objArr, i);
    }

    @Override // java.util.List
    @Deprecated
    public final void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    public final boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(@NullableDecl Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(@NullableDecl Object obj) {
        boolean z;
        if (obj == this) {
            z = true;
        } else if (obj instanceof List) {
            List list = (List) obj;
            int size = size();
            if (size != list.size()) {
                z = false;
            } else if (list instanceof RandomAccess) {
                int i = 0;
                while (true) {
                    if (i >= size) {
                        z = true;
                        break;
                    } else if (!zzk.zza(get(i), list.get(i))) {
                        z = false;
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                Iterator<E> it = iterator();
                Iterator<E> it2 = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!it2.hasNext()) {
                            z = false;
                            break;
                        } else if (!zzk.zza(it.next(), it2.next())) {
                            z = false;
                            break;
                        }
                    } else {
                        z = !it2.hasNext();
                    }
                }
            }
        } else {
            z = false;
        }
        return z;
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        int size = size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            i = (i * 31) + get(i2).hashCode();
        }
        return i;
    }

    @Override // java.util.List
    public final int indexOf(@NullableDecl Object obj) {
        int i;
        if (obj == null) {
            return -1;
        }
        int size = size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i = -1;
                break;
            } else if (obj.equals(get(i2))) {
                i = i2;
                break;
            } else {
                i2++;
            }
        }
        return i;
    }

    @Override // com.google.android.gms.internal.common.zzq, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public final int lastIndexOf(@NullableDecl Object obj) {
        int i;
        if (obj == null) {
            return -1;
        }
        int size = size() - 1;
        while (true) {
            if (size < 0) {
                i = -1;
                break;
            } else if (obj.equals(get(size))) {
                i = size;
                break;
            } else {
                size--;
            }
        }
        return i;
    }

    @Override // java.util.List
    public final /* bridge */ /* synthetic */ ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    @Deprecated
    public final E remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    public final E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.common.zzq
    public final zzx<E> zza() {
        return listIterator(0);
    }

    @Override // com.google.android.gms.internal.common.zzq
    public final zzu<E> zze() {
        return this;
    }

    @Override // com.google.android.gms.internal.common.zzq
    int zzg(Object[] objArr, int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i2] = get(i2);
        }
        return size;
    }

    @Override // java.util.List
    /* renamed from: zzh */
    public zzu<E> subList(int i, int i2) {
        zzl.zzc(i, i2, size());
        int i3 = i2 - i;
        if (i3 == size()) {
            return this;
        }
        if (i3 != 0) {
            return new zzt(this, i, i3);
        }
        return (zzu<E>) zzw.zza;
    }

    @Override // java.util.List
    /* renamed from: zzo */
    public final zzy<E> listIterator(int i) {
        zzl.zzb(i, size(), "index");
        return isEmpty() ? (zzy<E>) zza : new zzs(this, i);
    }
}
