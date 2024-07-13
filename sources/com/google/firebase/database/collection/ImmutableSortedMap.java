package com.google.firebase.database.collection;

import com.google.firebase.database.collection.LLRBNode;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
/* loaded from: classes.dex */
public abstract class ImmutableSortedMap<K, V> implements Iterable<Map.Entry<K, V>> {
    public abstract boolean containsKey(K k);

    public abstract V get(K k);

    public abstract Comparator<K> getComparator();

    public abstract K getMaxKey();

    public abstract K getMinKey();

    public abstract K getPredecessorKey(K k);

    public abstract K getSuccessorKey(K k);

    public abstract void inOrderTraversal(LLRBNode.NodeVisitor<K, V> nodeVisitor);

    public abstract int indexOf(K k);

    public abstract ImmutableSortedMap<K, V> insert(K k, V v);

    public abstract boolean isEmpty();

    @Override // java.lang.Iterable
    public abstract Iterator<Map.Entry<K, V>> iterator();

    public abstract Iterator<Map.Entry<K, V>> iteratorFrom(K k);

    public abstract ImmutableSortedMap<K, V> remove(K k);

    public abstract Iterator<Map.Entry<K, V>> reverseIterator();

    public abstract Iterator<Map.Entry<K, V>> reverseIteratorFrom(K k);

    public abstract int size();

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ImmutableSortedMap) {
            ImmutableSortedMap<K, V> that = (ImmutableSortedMap) o;
            if (getComparator().equals(that.getComparator()) && size() == that.size()) {
                Iterator<Map.Entry<K, V>> thisIterator = iterator();
                Iterator<Map.Entry<K, V>> thatIterator = that.iterator();
                while (thisIterator.hasNext()) {
                    if (!thisIterator.next().equals(thatIterator.next())) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        int result = getComparator().hashCode();
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            result = (result * 31) + entry.hashCode();
        }
        return result;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(getClass().getSimpleName());
        b.append("{");
        boolean first = true;
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            if (first) {
                first = false;
            } else {
                b.append(", ");
            }
            b.append("(");
            b.append(entry.getKey());
            b.append("=>");
            b.append(entry.getValue());
            b.append(")");
        }
        b.append("};");
        return b.toString();
    }

    /* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
    /* loaded from: classes.dex */
    public static class Builder {
        static final int ARRAY_TO_RB_TREE_SIZE_THRESHOLD = 25;
        private static final KeyTranslator IDENTITY_TRANSLATOR;

        /* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
        /* loaded from: classes.dex */
        public interface KeyTranslator<C, D> {
            D translate(C c);
        }

        public static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<K> comparator) {
            return new ArraySortedMap(comparator);
        }

        static {
            KeyTranslator keyTranslator;
            keyTranslator = ImmutableSortedMap$Builder$$Lambda$1.instance;
            IDENTITY_TRANSLATOR = keyTranslator;
        }

        public static /* synthetic */ Object lambda$static$0(Object key) {
            return key;
        }

        public static <A> KeyTranslator<A, A> identityTranslator() {
            return IDENTITY_TRANSLATOR;
        }

        public static <A, B> ImmutableSortedMap<A, B> fromMap(Map<A, B> values, Comparator<A> comparator) {
            return values.size() < 25 ? ArraySortedMap.fromMap(values, comparator) : RBTreeSortedMap.fromMap(values, comparator);
        }

        public static <A, B, C> ImmutableSortedMap<A, C> buildFrom(List<A> keys, Map<B, C> values, KeyTranslator<A, B> translator, Comparator<A> comparator) {
            return keys.size() < 25 ? ArraySortedMap.buildFrom(keys, values, translator, comparator) : RBTreeSortedMap.buildFrom(keys, values, translator, comparator);
        }
    }
}
