package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.SnapshotHolder;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class MutableData {
    private final SnapshotHolder holder;
    private final Path prefixPath;

    public MutableData(Node node) {
        this(new SnapshotHolder(node), new Path(""));
    }

    private MutableData(SnapshotHolder holder, Path path) {
        this.holder = holder;
        this.prefixPath = path;
        ValidationPath.validateWithObject(this.prefixPath, getValue());
    }

    public Node getNode() {
        return this.holder.getNode(this.prefixPath);
    }

    public boolean hasChildren() {
        Node node = getNode();
        return (node.isLeafNode() || node.isEmpty()) ? false : true;
    }

    public boolean hasChild(@NonNull String path) {
        return !getNode().getChild(new Path(path)).isEmpty();
    }

    @NonNull
    public MutableData child(@NonNull String path) {
        Validation.validatePathString(path);
        return new MutableData(this.holder, this.prefixPath.child(new Path(path)));
    }

    public long getChildrenCount() {
        return getNode().getChildCount();
    }

    @NonNull
    public Iterable<MutableData> getChildren() {
        Node node = getNode();
        if (node.isEmpty() || node.isLeafNode()) {
            return new Iterable<MutableData>() { // from class: com.google.firebase.database.MutableData.1
                @Override // java.lang.Iterable
                public Iterator<MutableData> iterator() {
                    return new Iterator<MutableData>() { // from class: com.google.firebase.database.MutableData.1.1
                        @Override // java.util.Iterator
                        public boolean hasNext() {
                            return false;
                        }

                        @Override // java.util.Iterator
                        @NonNull
                        public MutableData next() {
                            throw new NoSuchElementException();
                        }

                        @Override // java.util.Iterator
                        public void remove() {
                            throw new UnsupportedOperationException("remove called on immutable collection");
                        }
                    };
                }
            };
        }
        final Iterator<NamedNode> iter = IndexedNode.from(node).iterator();
        return new Iterable<MutableData>() { // from class: com.google.firebase.database.MutableData.2
            @Override // java.lang.Iterable
            public Iterator<MutableData> iterator() {
                return new Iterator<MutableData>() { // from class: com.google.firebase.database.MutableData.2.1
                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return iter.hasNext();
                    }

                    @Override // java.util.Iterator
                    @NonNull
                    public MutableData next() {
                        NamedNode namedNode = (NamedNode) iter.next();
                        return new MutableData(MutableData.this.holder, MutableData.this.prefixPath.child(namedNode.getName()));
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        throw new UnsupportedOperationException("remove called on immutable collection");
                    }
                };
            }
        };
    }

    @Nullable
    public String getKey() {
        if (this.prefixPath.getBack() != null) {
            return this.prefixPath.getBack().asString();
        }
        return null;
    }

    @Nullable
    public Object getValue() {
        return getNode().getValue();
    }

    @Nullable
    public <T> T getValue(@NonNull Class<T> valueType) {
        Object value = getNode().getValue();
        return (T) CustomClassMapper.convertToCustomClass(value, valueType);
    }

    @Nullable
    public <T> T getValue(@NonNull GenericTypeIndicator<T> t) {
        Object value = getNode().getValue();
        return (T) CustomClassMapper.convertToCustomClass(value, t);
    }

    public void setValue(@Nullable Object value) throws DatabaseException {
        ValidationPath.validateWithObject(this.prefixPath, value);
        Object bouncedValue = CustomClassMapper.convertToPlainJavaTypes(value);
        Validation.validateWritableObject(bouncedValue);
        this.holder.update(this.prefixPath, NodeUtilities.NodeFromJSON(bouncedValue));
    }

    public void setPriority(@Nullable Object priority) {
        this.holder.update(this.prefixPath, getNode().updatePriority(PriorityUtilities.parsePriority(this.prefixPath, priority)));
    }

    @Nullable
    public Object getPriority() {
        return getNode().getPriority().getValue();
    }

    public boolean equals(Object o) {
        return (o instanceof MutableData) && this.holder.equals(((MutableData) o).holder) && this.prefixPath.equals(((MutableData) o).prefixPath);
    }

    public String toString() {
        ChildKey front = this.prefixPath.getFront();
        return "MutableData { key = " + (front != null ? front.asString() : "<none>") + ", value = " + this.holder.getRootNode().getValue(true) + " }";
    }
}
