package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ConcurrentModificationException;
import java.util.Map;

/* loaded from: classes.dex */
public class SimpleArrayMap<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";
    @Nullable
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    @Nullable
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    int[] mHashes;
    int mSize;

    private static int binarySearchHashes(int[] iArr, int i, int i2) {
        try {
            return ContainerHelpers.binarySearch(iArr, i, i2);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConcurrentModificationException();
        }
    }

    int indexOf(Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int binarySearchHashes = binarySearchHashes(this.mHashes, i2, i);
        if (binarySearchHashes < 0) {
            return binarySearchHashes;
        }
        if (obj.equals(this.mArray[binarySearchHashes << 1])) {
            return binarySearchHashes;
        }
        int i3 = binarySearchHashes + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = binarySearchHashes - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.mArray[i4 << 1])) {
                return i4;
            }
        }
        return i3 ^ (-1);
    }

    int indexOfNull() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int binarySearchHashes = binarySearchHashes(this.mHashes, i, 0);
        if (binarySearchHashes < 0) {
            return binarySearchHashes;
        }
        if (null == this.mArray[binarySearchHashes << 1]) {
            return binarySearchHashes;
        }
        int i2 = binarySearchHashes + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (null == this.mArray[i2 << 1]) {
                return i2;
            }
            i2++;
        }
        for (int i3 = binarySearchHashes - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (null == this.mArray[i3 << 1]) {
                return i3;
            }
        }
        return i2 ^ (-1);
    }

    private void allocArrays(int i) {
        if (i == 8) {
            synchronized (SimpleArrayMap.class) {
                if (mTwiceBaseCache != null) {
                    Object[] objArr = mTwiceBaseCache;
                    this.mArray = objArr;
                    mTwiceBaseCache = (Object[]) objArr[0];
                    this.mHashes = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    mTwiceBaseCacheSize--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (SimpleArrayMap.class) {
                if (mBaseCache != null) {
                    Object[] objArr2 = mBaseCache;
                    this.mArray = objArr2;
                    mBaseCache = (Object[]) objArr2[0];
                    this.mHashes = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    mBaseCacheSize--;
                    return;
                }
            }
        }
        this.mHashes = new int[i];
        this.mArray = new Object[i << 1];
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (SimpleArrayMap.class) {
                if (mTwiceBaseCacheSize < 10) {
                    objArr[0] = mTwiceBaseCache;
                    objArr[1] = iArr;
                    for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    mTwiceBaseCache = objArr;
                    mTwiceBaseCacheSize++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (SimpleArrayMap.class) {
                if (mBaseCacheSize < 10) {
                    objArr[0] = mBaseCache;
                    objArr[1] = iArr;
                    for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    mBaseCache = objArr;
                    mBaseCacheSize++;
                }
            }
        }
    }

    public SimpleArrayMap() {
        this.mHashes = ContainerHelpers.EMPTY_INTS;
        this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        this.mSize = 0;
    }

    public SimpleArrayMap(int i) {
        if (i == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            allocArrays(i);
        }
        this.mSize = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleArrayMap(SimpleArrayMap<K, V> simpleArrayMap) {
        this();
        if (simpleArrayMap != 0) {
            putAll(simpleArrayMap);
        }
    }

    public void clear() {
        if (this.mSize > 0) {
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            int i = this.mSize;
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
            freeArrays(iArr, objArr, i);
        }
        if (this.mSize > 0) {
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
                System.arraycopy(iArr, 0, this.mHashes, 0, i2);
                System.arraycopy(objArr, 0, this.mArray, 0, i2 << 1);
            }
            freeArrays(iArr, objArr, i2);
        }
        if (this.mSize != i2) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(@Nullable Object obj) {
        return indexOfKey(obj) >= 0 ? CONCURRENT_MODIFICATION_EXCEPTIONS : false;
    }

    public int indexOfKey(@Nullable Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj, obj.hashCode());
    }

    public int indexOfValue(Object obj) {
        int i = this.mSize * 2;
        Object[] objArr = this.mArray;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
        } else {
            for (int i3 = 1; i3 < i; i3 += 2) {
                if (obj.equals(objArr[i3])) {
                    return i3 >> 1;
                }
            }
        }
        return -1;
    }

    public boolean containsValue(Object obj) {
        return indexOfValue(obj) >= 0 ? CONCURRENT_MODIFICATION_EXCEPTIONS : false;
    }

    @Nullable
    public V get(Object obj) {
        return getOrDefault(obj, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v7 */
    public V getOrDefault(Object obj, V v) {
        int indexOfKey = indexOfKey(obj);
        return indexOfKey >= 0 ? this.mArray[(indexOfKey << 1) + 1] : v;
    }

    public K keyAt(int i) {
        return (K) this.mArray[i << 1];
    }

    public V valueAt(int i) {
        return (V) this.mArray[(i << 1) + 1];
    }

    public V setValueAt(int i, V v) {
        int i2 = (i << 1) + 1;
        V v2 = (V) this.mArray[i2];
        this.mArray[i2] = v;
        return v2;
    }

    public boolean isEmpty() {
        return this.mSize <= 0 ? CONCURRENT_MODIFICATION_EXCEPTIONS : false;
    }

    @Nullable
    public V put(K k, V v) {
        int hashCode;
        int indexOf;
        int i;
        int i2 = this.mSize;
        if (k == null) {
            hashCode = 0;
            indexOf = indexOfNull();
        } else {
            hashCode = k.hashCode();
            indexOf = indexOf(k, hashCode);
        }
        if (indexOf >= 0) {
            int i3 = (indexOf << 1) + 1;
            V v2 = (V) this.mArray[i3];
            this.mArray[i3] = v;
            return v2;
        }
        int i4 = indexOf ^ (-1);
        if (i2 >= this.mHashes.length) {
            if (i2 >= 8) {
                i = i2 + (i2 >> 1);
            } else {
                i = i2 >= 4 ? 8 : 4;
            }
            int i5 = i;
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            allocArrays(i5);
            if (i2 != this.mSize) {
                throw new ConcurrentModificationException();
            }
            if (this.mHashes.length > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, iArr.length);
                System.arraycopy(objArr, 0, this.mArray, 0, objArr.length);
            }
            freeArrays(iArr, objArr, i2);
        }
        if (i4 < i2) {
            System.arraycopy(this.mHashes, i4, this.mHashes, i4 + 1, i2 - i4);
            System.arraycopy(this.mArray, i4 << 1, this.mArray, (i4 + 1) << 1, (this.mSize - i4) << 1);
        }
        if (i2 != this.mSize || i4 >= this.mHashes.length) {
            throw new ConcurrentModificationException();
        }
        this.mHashes[i4] = hashCode;
        this.mArray[i4 << 1] = k;
        this.mArray[(i4 << 1) + 1] = v;
        this.mSize++;
        return null;
    }

    public void putAll(@NonNull SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        int i = simpleArrayMap.mSize;
        ensureCapacity(this.mSize + i);
        if (this.mSize == 0) {
            if (i > 0) {
                System.arraycopy(simpleArrayMap.mHashes, 0, this.mHashes, 0, i);
                System.arraycopy(simpleArrayMap.mArray, 0, this.mArray, 0, i << 1);
                this.mSize = i;
                return;
            }
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            put(simpleArrayMap.keyAt(i2), simpleArrayMap.valueAt(i2));
        }
    }

    @Nullable
    public V putIfAbsent(K k, V v) {
        V v2 = get(k);
        if (v2 == null) {
            v2 = put(k, v);
        }
        return v2;
    }

    @Nullable
    public V remove(Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return removeAt(indexOfKey);
        }
        return null;
    }

    public boolean remove(Object obj, Object obj2) {
        V valueAt;
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey < 0 || (obj2 != (valueAt = valueAt(indexOfKey)) && (obj2 == null || !obj2.equals(valueAt)))) {
            return false;
        }
        removeAt(indexOfKey);
        return CONCURRENT_MODIFICATION_EXCEPTIONS;
    }

    public V removeAt(int i) {
        V v = (V) this.mArray[(i << 1) + 1];
        int i2 = this.mSize;
        if (i2 <= 1) {
            clear();
        } else {
            int i3 = i2 - 1;
            if (this.mHashes.length > 8 && i2 < this.mHashes.length / 3) {
                int i4 = i2 > 8 ? i2 + (i2 >> 1) : 8;
                int[] iArr = this.mHashes;
                Object[] objArr = this.mArray;
                allocArrays(i4);
                if (i2 != this.mSize) {
                    throw new ConcurrentModificationException();
                }
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.mHashes, 0, i);
                    System.arraycopy(objArr, 0, this.mArray, 0, i << 1);
                }
                if (i < i3) {
                    System.arraycopy(iArr, i + 1, this.mHashes, i, i3 - i);
                    System.arraycopy(objArr, (i + 1) << 1, this.mArray, i << 1, (i3 - i) << 1);
                }
            } else {
                if (i < i3) {
                    System.arraycopy(this.mHashes, i + 1, this.mHashes, i, i3 - i);
                    System.arraycopy(this.mArray, (i + 1) << 1, this.mArray, i << 1, (i3 - i) << 1);
                }
                this.mArray[i3 << 1] = null;
                this.mArray[(i3 << 1) + 1] = null;
            }
            if (i2 != this.mSize) {
                throw new ConcurrentModificationException();
            }
            this.mSize = i3;
        }
        return v;
    }

    @Nullable
    public V replace(K k, V v) {
        int indexOfKey = indexOfKey(k);
        if (indexOfKey >= 0) {
            return setValueAt(indexOfKey, v);
        }
        return null;
    }

    public boolean replace(K k, V v, V v2) {
        V valueAt;
        int indexOfKey = indexOfKey(k);
        if (indexOfKey < 0 || ((valueAt = valueAt(indexOfKey)) != v && (v == null || !v.equals(valueAt)))) {
            return false;
        }
        setValueAt(indexOfKey, v2);
        return CONCURRENT_MODIFICATION_EXCEPTIONS;
    }

    public int size() {
        return this.mSize;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return CONCURRENT_MODIFICATION_EXCEPTIONS;
        }
        if (obj instanceof SimpleArrayMap) {
            SimpleArrayMap simpleArrayMap = (SimpleArrayMap) obj;
            if (size() != simpleArrayMap.size()) {
                return false;
            }
            for (int i = 0; i < this.mSize; i++) {
                K keyAt = keyAt(i);
                V valueAt = valueAt(i);
                Object obj2 = simpleArrayMap.get(keyAt);
                if (valueAt == null) {
                    if (obj2 != null || !simpleArrayMap.containsKey(keyAt)) {
                        return false;
                    }
                } else if (!valueAt.equals(obj2)) {
                    return false;
                }
            }
            return CONCURRENT_MODIFICATION_EXCEPTIONS;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (size() != map.size()) {
                return false;
            }
            for (int i2 = 0; i2 < this.mSize; i2++) {
                K keyAt2 = keyAt(i2);
                V valueAt2 = valueAt(i2);
                Object obj3 = map.get(keyAt2);
                if (valueAt2 == null) {
                    if (obj3 != null || !map.containsKey(keyAt2)) {
                        return false;
                    }
                } else if (!valueAt2.equals(obj3)) {
                    return false;
                }
            }
            return CONCURRENT_MODIFICATION_EXCEPTIONS;
        }
        return false;
    }

    public int hashCode() {
        int[] iArr = this.mHashes;
        Object[] objArr = this.mArray;
        int i = 0;
        int i2 = 0;
        int i3 = 1;
        int i4 = this.mSize;
        while (i2 < i4) {
            Object obj = objArr[i3];
            i += iArr[i2] ^ (obj == null ? 0 : obj.hashCode());
            i2++;
            i3 += 2;
        }
        return i;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            K keyAt = keyAt(i);
            if (keyAt != this) {
                sb.append(keyAt);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            V valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
