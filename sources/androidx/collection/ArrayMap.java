package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes.dex */
public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {
    @Nullable
    ArrayMap<K, V>.EntrySet mEntrySet;
    @Nullable
    ArrayMap<K, V>.KeySet mKeySet;
    @Nullable
    ArrayMap<K, V>.ValueCollection mValues;

    public ArrayMap() {
    }

    public ArrayMap(int i) {
        super(i);
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap);
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Map
    public void putAll(@NonNull Map<? extends K, ? extends V> map) {
        ensureCapacity(this.mSize + map.size());
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(@NonNull Collection<?> collection) {
        int i = this.mSize;
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
        return i != this.mSize;
    }

    public boolean retainAll(@NonNull Collection<?> collection) {
        int i = this.mSize;
        for (int i2 = this.mSize - 1; i2 >= 0; i2--) {
            if (!collection.contains(keyAt(i2))) {
                removeAt(i2);
            }
        }
        return i != this.mSize;
    }

    @Override // java.util.Map
    @NonNull
    public Set<Map.Entry<K, V>> entrySet() {
        ArrayMap<K, V>.EntrySet entrySet = this.mEntrySet;
        if (entrySet == null) {
            ArrayMap<K, V>.EntrySet entrySet2 = new EntrySet();
            this.mEntrySet = entrySet2;
            entrySet = entrySet2;
        }
        return entrySet;
    }

    @Override // java.util.Map
    @NonNull
    public Set<K> keySet() {
        ArrayMap<K, V>.KeySet keySet = this.mKeySet;
        if (keySet == null) {
            ArrayMap<K, V>.KeySet keySet2 = new KeySet();
            this.mKeySet = keySet2;
            keySet = keySet2;
        }
        return keySet;
    }

    @Override // java.util.Map
    @NonNull
    public Collection<V> values() {
        ArrayMap<K, V>.ValueCollection valueCollection = this.mValues;
        if (valueCollection == null) {
            ArrayMap<K, V>.ValueCollection valueCollection2 = new ValueCollection();
            this.mValues = valueCollection2;
            valueCollection = valueCollection2;
        }
        return valueCollection;
    }

    /* loaded from: classes.dex */
    final class EntrySet implements Set<Map.Entry<K, V>> {
        EntrySet() {
            ArrayMap.this = r5;
        }

        @Override // java.util.Set, java.util.Collection
        public /* bridge */ /* synthetic */ boolean add(Object obj) {
            return add((Map.Entry) ((Map.Entry) obj));
        }

        public boolean add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            int i = ArrayMap.this.mSize;
            for (Map.Entry<K, V> entry : collection) {
                ArrayMap.this.put(entry.getKey(), entry.getValue());
            }
            return i != ArrayMap.this.mSize;
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            ArrayMap.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int indexOfKey = ArrayMap.this.indexOfKey(entry.getKey());
            if (indexOfKey < 0) {
                return false;
            }
            return ContainerHelpers.equal(ArrayMap.this.valueAt(indexOfKey), entry.getValue());
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new MapIterator();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return ArrayMap.this.mSize;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return ArrayMap.equalsSetHelper(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int i = 0;
            for (int i2 = ArrayMap.this.mSize - 1; i2 >= 0; i2--) {
                K keyAt = ArrayMap.this.keyAt(i2);
                V valueAt = ArrayMap.this.valueAt(i2);
                i += (keyAt == null ? 0 : keyAt.hashCode()) ^ (valueAt == null ? 0 : valueAt.hashCode());
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class KeySet implements Set<K> {
        KeySet() {
            ArrayMap.this = r5;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            ArrayMap.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return ArrayMap.this.containsKey(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return ArrayMap.this.containsAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            int indexOfKey = ArrayMap.this.indexOfKey(obj);
            if (indexOfKey >= 0) {
                ArrayMap.this.removeAt(indexOfKey);
                return true;
            }
            return false;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            return ArrayMap.this.removeAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return ArrayMap.this.retainAll(collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return ArrayMap.this.mSize;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            int i = ArrayMap.this.mSize;
            Object[] objArr = new Object[i];
            for (int i2 = 0; i2 < i; i2++) {
                objArr[i2] = ArrayMap.this.keyAt(i2);
            }
            return objArr;
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) ArrayMap.this.toArrayHelper(tArr, 0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return ArrayMap.equalsSetHelper(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int i = 0;
            for (int i2 = ArrayMap.this.mSize - 1; i2 >= 0; i2--) {
                K keyAt = ArrayMap.this.keyAt(i2);
                i += keyAt == null ? 0 : keyAt.hashCode();
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class ValueCollection implements Collection<V> {
        ValueCollection() {
            ArrayMap.this = r5;
        }

        @Override // java.util.Collection
        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public void clear() {
            ArrayMap.this.clear();
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return ArrayMap.this.indexOfValue(obj) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            int indexOfValue = ArrayMap.this.indexOfValue(obj);
            if (indexOfValue >= 0) {
                ArrayMap.this.removeAt(indexOfValue);
                return true;
            }
            return false;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            int i = ArrayMap.this.mSize;
            boolean z = false;
            int i2 = 0;
            while (i2 < i) {
                if (collection.contains(ArrayMap.this.valueAt(i2))) {
                    ArrayMap.this.removeAt(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            int i = ArrayMap.this.mSize;
            boolean z = false;
            int i2 = 0;
            while (i2 < i) {
                if (!collection.contains(ArrayMap.this.valueAt(i2))) {
                    ArrayMap.this.removeAt(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        @Override // java.util.Collection
        public int size() {
            return ArrayMap.this.mSize;
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            int i = ArrayMap.this.mSize;
            Object[] objArr = new Object[i];
            for (int i2 = 0; i2 < i; i2++) {
                objArr[i2] = ArrayMap.this.valueAt(i2);
            }
            return objArr;
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) ArrayMap.this.toArrayHelper(tArr, 1);
        }
    }

    /* loaded from: classes.dex */
    final class KeyIterator extends IndexBasedArrayIterator<K> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        KeyIterator() {
            super(r5.mSize);
            ArrayMap.this = r5;
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected K elementAt(int i) {
            return ArrayMap.this.keyAt(i);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected void removeAt(int i) {
            ArrayMap.this.removeAt(i);
        }
    }

    /* loaded from: classes.dex */
    final class ValueIterator extends IndexBasedArrayIterator<V> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ValueIterator() {
            super(r5.mSize);
            ArrayMap.this = r5;
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected V elementAt(int i) {
            return ArrayMap.this.valueAt(i);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected void removeAt(int i) {
            ArrayMap.this.removeAt(i);
        }
    }

    /* loaded from: classes.dex */
    final class MapIterator implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
        int mEnd;
        boolean mEntryValid;
        int mIndex = -1;

        MapIterator() {
            ArrayMap.this = r6;
            this.mEnd = r6.mSize - 1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mIndex < this.mEnd;
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            if (hasNext()) {
                this.mIndex++;
                this.mEntryValid = true;
                return this;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.mEntryValid) {
                ArrayMap.this.removeAt(this.mIndex);
                this.mIndex--;
                this.mEnd--;
                this.mEntryValid = false;
                return;
            }
            throw new IllegalStateException();
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.mEntryValid) {
                return ArrayMap.this.keyAt(this.mIndex);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.mEntryValid) {
                return ArrayMap.this.valueAt(this.mIndex);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (this.mEntryValid) {
                return ArrayMap.this.setValueAt(this.mIndex, v);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return ContainerHelpers.equal(entry.getKey(), ArrayMap.this.keyAt(this.mIndex)) && ContainerHelpers.equal(entry.getValue(), ArrayMap.this.valueAt(this.mIndex));
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            if (this.mEntryValid) {
                K keyAt = ArrayMap.this.keyAt(this.mIndex);
                V valueAt = ArrayMap.this.valueAt(this.mIndex);
                return (keyAt == null ? 0 : keyAt.hashCode()) ^ (valueAt == null ? 0 : valueAt.hashCode());
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v15, types: [java.lang.Object[]] */
    <T> T[] toArrayHelper(T[] tArr, int i) {
        T[] tArr2 = tArr;
        int i2 = this.mSize;
        if (tArr2.length < i2) {
            tArr2 = (Object[]) Array.newInstance(tArr2.getClass().getComponentType(), i2);
        }
        for (int i3 = 0; i3 < i2; i3++) {
            tArr2[i3] = this.mArray[(i3 << 1) + i];
        }
        if (tArr2.length > i2) {
            tArr2[i2] = null;
        }
        return tArr2;
    }

    static <T> boolean equalsSetHelper(Set<T> set, Object obj) {
        boolean z;
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        z = true;
                        return z;
                    }
                }
                z = false;
                return z;
            } catch (ClassCastException e) {
            } catch (NullPointerException e2) {
            }
        }
        return false;
    }
}
