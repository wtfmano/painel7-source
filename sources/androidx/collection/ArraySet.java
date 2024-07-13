package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public final class ArraySet<E> implements Collection<E>, Set<E> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArraySet";
    @Nullable
    private static Object[] sBaseCache;
    private static int sBaseCacheSize;
    @Nullable
    private static Object[] sTwiceBaseCache;
    private static int sTwiceBaseCacheSize;
    Object[] mArray;
    private int[] mHashes;
    int mSize;
    private static final Object sBaseCacheLock = new Object();
    private static final Object sTwiceBaseCacheLock = new Object();

    private int binarySearch(int i) {
        try {
            return ContainerHelpers.binarySearch(this.mHashes, this.mSize, i);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConcurrentModificationException();
        }
    }

    private int indexOf(Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int binarySearch = binarySearch(i);
        if (binarySearch < 0) {
            return binarySearch;
        }
        if (obj.equals(this.mArray[binarySearch])) {
            return binarySearch;
        }
        int i3 = binarySearch + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = binarySearch - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.mArray[i4])) {
                return i4;
            }
        }
        return i3 ^ (-1);
    }

    private int indexOfNull() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int binarySearch = binarySearch(0);
        if (binarySearch < 0) {
            return binarySearch;
        }
        if (null == this.mArray[binarySearch]) {
            return binarySearch;
        }
        int i2 = binarySearch + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (null == this.mArray[i2]) {
                return i2;
            }
            i2++;
        }
        for (int i3 = binarySearch - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (null == this.mArray[i3]) {
                return i3;
            }
        }
        return i2 ^ (-1);
    }

    private void allocArrays(int i) {
        if (i == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (sTwiceBaseCache != null) {
                    Object[] objArr = sTwiceBaseCache;
                    try {
                        this.mArray = objArr;
                        sTwiceBaseCache = (Object[]) objArr[0];
                        this.mHashes = (int[]) objArr[1];
                        if (this.mHashes != null) {
                            objArr[1] = null;
                            objArr[0] = null;
                            sTwiceBaseCacheSize--;
                            return;
                        }
                    } catch (ClassCastException e) {
                    }
                    System.out.println("ArraySet Found corrupt ArraySet cache: [0]=" + objArr[0] + " [1]=" + objArr[1]);
                    sTwiceBaseCache = null;
                    sTwiceBaseCacheSize = 0;
                }
            }
        } else if (i == 4) {
            synchronized (sBaseCacheLock) {
                if (sBaseCache != null) {
                    Object[] objArr2 = sBaseCache;
                    try {
                        this.mArray = objArr2;
                        sBaseCache = (Object[]) objArr2[0];
                        this.mHashes = (int[]) objArr2[1];
                        if (this.mHashes != null) {
                            objArr2[1] = null;
                            objArr2[0] = null;
                            sBaseCacheSize--;
                            return;
                        }
                    } catch (ClassCastException e2) {
                    }
                    System.out.println("ArraySet Found corrupt ArraySet cache: [0]=" + objArr2[0] + " [1]=" + objArr2[1]);
                    sBaseCache = null;
                    sBaseCacheSize = 0;
                }
            }
        }
        this.mHashes = new int[i];
        this.mArray = new Object[i];
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (sTwiceBaseCacheSize < 10) {
                    objArr[0] = sTwiceBaseCache;
                    objArr[1] = iArr;
                    for (int i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    sTwiceBaseCache = objArr;
                    sTwiceBaseCacheSize++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (sBaseCacheLock) {
                if (sBaseCacheSize < 10) {
                    objArr[0] = sBaseCache;
                    objArr[1] = iArr;
                    for (int i3 = i - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    sBaseCache = objArr;
                    sBaseCacheSize++;
                }
            }
        }
    }

    public ArraySet() {
        this(0);
    }

    public ArraySet(int i) {
        if (i == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            allocArrays(i);
        }
        this.mSize = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(@Nullable ArraySet<E> arraySet) {
        this();
        if (arraySet != 0) {
            addAll((ArraySet) arraySet);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(@Nullable Collection<E> collection) {
        this();
        if (collection != 0) {
            addAll(collection);
        }
    }

    public ArraySet(@Nullable E[] eArr) {
        this();
        if (eArr != null) {
            for (E e : eArr) {
                add(e);
            }
        }
    }

    @Override // java.util.Collection, java.util.Set
    public void clear() {
        if (this.mSize != 0) {
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            int i = this.mSize;
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
            freeArrays(iArr, objArr, i);
        }
        if (this.mSize != 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int i) {
        int i2 = this.mSize;
        if (this.mHashes.length < i) {
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            allocArrays(i);
            if (this.mSize > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, this.mSize);
                System.arraycopy(objArr, 0, this.mArray, 0, this.mSize);
            }
            freeArrays(iArr, objArr, this.mSize);
        }
        if (this.mSize != i2) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean contains(@Nullable Object obj) {
        return indexOf(obj) >= 0;
    }

    public int indexOf(@Nullable Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj, obj.hashCode());
    }

    public E valueAt(int i) {
        return (E) this.mArray[i];
    }

    @Override // java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(@Nullable E e) {
        int hashCode;
        int indexOf;
        int i;
        int i2 = this.mSize;
        if (e == null) {
            hashCode = 0;
            indexOf = indexOfNull();
        } else {
            hashCode = e.hashCode();
            indexOf = indexOf(e, hashCode);
        }
        if (indexOf >= 0) {
            return false;
        }
        int i3 = indexOf ^ (-1);
        if (i2 >= this.mHashes.length) {
            if (i2 >= 8) {
                i = i2 + (i2 >> 1);
            } else {
                i = i2 >= 4 ? 8 : 4;
            }
            int i4 = i;
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            allocArrays(i4);
            if (i2 != this.mSize) {
                throw new ConcurrentModificationException();
            }
            if (this.mHashes.length > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, iArr.length);
                System.arraycopy(objArr, 0, this.mArray, 0, objArr.length);
            }
            freeArrays(iArr, objArr, i2);
        }
        if (i3 < i2) {
            System.arraycopy(this.mHashes, i3, this.mHashes, i3 + 1, i2 - i3);
            System.arraycopy(this.mArray, i3, this.mArray, i3 + 1, i2 - i3);
        }
        if (i2 != this.mSize || i3 >= this.mHashes.length) {
            throw new ConcurrentModificationException();
        }
        this.mHashes[i3] = hashCode;
        this.mArray[i3] = e;
        this.mSize++;
        return true;
    }

    public void addAll(@NonNull ArraySet<? extends E> arraySet) {
        int i = arraySet.mSize;
        ensureCapacity(this.mSize + i);
        if (this.mSize == 0) {
            if (i > 0) {
                System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, i);
                System.arraycopy(arraySet.mArray, 0, this.mArray, 0, i);
                if (0 != this.mSize) {
                    throw new ConcurrentModificationException();
                }
                this.mSize = i;
                return;
            }
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            add(arraySet.valueAt(i2));
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean remove(@Nullable Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf >= 0) {
            removeAt(indexOf);
            return true;
        }
        return false;
    }

    public E removeAt(int i) {
        int i2;
        int i3 = this.mSize;
        E e = (E) this.mArray[i];
        if (i3 <= 1) {
            clear();
        } else {
            int i4 = i3 - 1;
            if (this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3) {
                if (this.mSize > 8) {
                    i2 = this.mSize + (this.mSize >> 1);
                } else {
                    i2 = 8;
                }
                int i5 = i2;
                int[] iArr = this.mHashes;
                Object[] objArr = this.mArray;
                allocArrays(i5);
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.mHashes, 0, i);
                    System.arraycopy(objArr, 0, this.mArray, 0, i);
                }
                if (i < i4) {
                    System.arraycopy(iArr, i + 1, this.mHashes, i, i4 - i);
                    System.arraycopy(objArr, i + 1, this.mArray, i, i4 - i);
                }
            } else {
                if (i < i4) {
                    System.arraycopy(this.mHashes, i + 1, this.mHashes, i, i4 - i);
                    System.arraycopy(this.mArray, i + 1, this.mArray, i, i4 - i);
                }
                this.mArray[i4] = null;
            }
            if (i3 != this.mSize) {
                throw new ConcurrentModificationException();
            }
            this.mSize = i4;
        }
        return e;
    }

    public boolean removeAll(@NonNull ArraySet<? extends E> arraySet) {
        int i = arraySet.mSize;
        int i2 = this.mSize;
        for (int i3 = 0; i3 < i; i3++) {
            remove(arraySet.valueAt(i3));
        }
        return i2 != this.mSize;
    }

    @Override // java.util.Collection, java.util.Set
    public int size() {
        return this.mSize;
    }

    @Override // java.util.Collection, java.util.Set
    @NonNull
    public Object[] toArray() {
        Object[] objArr = new Object[this.mSize];
        System.arraycopy(this.mArray, 0, objArr, 0, this.mSize);
        return objArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12, types: [java.lang.Object[]] */
    @Override // java.util.Collection, java.util.Set
    @NonNull
    public <T> T[] toArray(@NonNull T[] tArr) {
        T[] tArr2 = tArr;
        if (tArr2.length < this.mSize) {
            tArr2 = (Object[]) Array.newInstance(tArr2.getClass().getComponentType(), this.mSize);
        }
        System.arraycopy(this.mArray, 0, tArr2, 0, this.mSize);
        if (tArr2.length > this.mSize) {
            tArr2[this.mSize] = null;
        }
        return tArr2;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            if (size() != set.size()) {
                return false;
            }
            for (int i = 0; i < this.mSize; i++) {
                try {
                    if (!set.contains(valueAt(i))) {
                        return false;
                    }
                } catch (ClassCastException e) {
                    return false;
                } catch (NullPointerException e2) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int[] iArr = this.mHashes;
        int i = 0;
        int i2 = this.mSize;
        for (int i3 = 0; i3 < i2; i3++) {
            i += iArr[i3];
        }
        return i;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 14);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    @NonNull
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ElementIterator extends IndexBasedArrayIterator<E> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ElementIterator() {
            super(r5.mSize);
            ArraySet.this = r5;
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected E elementAt(int i) {
            return (E) ArraySet.this.valueAt(i);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected void removeAt(int i) {
            ArraySet.this.removeAt(i);
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean addAll(@NonNull Collection<? extends E> collection) {
        ensureCapacity(this.mSize + collection.size());
        boolean z = false;
        for (E e : collection) {
            z |= add(e);
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(@NonNull Collection<?> collection) {
        boolean z = false;
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            z |= remove(it.next());
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(@NonNull Collection<?> collection) {
        boolean z = false;
        for (int i = this.mSize - 1; i >= 0; i--) {
            if (!collection.contains(this.mArray[i])) {
                removeAt(i);
                z = true;
            }
        }
        return z;
    }
}
