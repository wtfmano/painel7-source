package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class DataSnapshot {
    private final IndexedNode node;
    private final DatabaseReference query;

    public DataSnapshot(DatabaseReference ref, IndexedNode node) {
        this.node = node;
        this.query = ref;
    }

    @NonNull
    public DataSnapshot child(@NonNull String path) {
        DatabaseReference childRef = this.query.child(path);
        Node childNode = this.node.getNode().getChild(new Path(path));
        return new DataSnapshot(childRef, IndexedNode.from(childNode));
    }

    public boolean hasChild(@NonNull String path) {
        if (this.query.getParent() == null) {
            Validation.validateRootPathString(path);
        } else {
            Validation.validatePathString(path);
        }
        return !this.node.getNode().getChild(new Path(path)).isEmpty();
    }

    public boolean hasChildren() {
        return this.node.getNode().getChildCount() > 0;
    }

    public boolean exists() {
        return !this.node.getNode().isEmpty();
    }

    @Nullable
    public Object getValue() {
        return this.node.getNode().getValue();
    }

    @Nullable
    public Object getValue(boolean useExportFormat) {
        return this.node.getNode().getValue(useExportFormat);
    }

    @Nullable
    public <T> T getValue(@NonNull Class<T> valueType) {
        Object value = this.node.getNode().getValue();
        return (T) CustomClassMapper.convertToCustomClass(value, valueType);
    }

    @Nullable
    public <T> T getValue(@NonNull GenericTypeIndicator<T> t) {
        Object value = this.node.getNode().getValue();
        return (T) CustomClassMapper.convertToCustomClass(value, t);
    }

    public long getChildrenCount() {
        return this.node.getNode().getChildCount();
    }

    @NonNull
    public DatabaseReference getRef() {
        return this.query;
    }

    @Nullable
    public String getKey() {
        return this.query.getKey();
    }

    @NonNull
    public Iterable<DataSnapshot> getChildren() {
        final Iterator<NamedNode> iter = this.node.iterator();
        return new Iterable<DataSnapshot>() { // from class: com.google.firebase.database.DataSnapshot.1
            @Override // java.lang.Iterable
            public Iterator<DataSnapshot> iterator() {
                return new Iterator<DataSnapshot>() { // from class: com.google.firebase.database.DataSnapshot.1.1
                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return iter.hasNext();
                    }

                    @Override // java.util.Iterator
                    @NonNull
                    public DataSnapshot next() {
                        NamedNode namedNode = (NamedNode) iter.next();
                        return new DataSnapshot(DataSnapshot.this.query.child(namedNode.getName().asString()), IndexedNode.from(namedNode.getNode()));
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
    public Object getPriority() {
        Object priority = this.node.getNode().getPriority().getValue();
        if (priority instanceof Long) {
            return Double.valueOf(((Long) priority).longValue());
        }
        return priority;
    }

    public String toString() {
        return "DataSnapshot { key = " + this.query.getKey() + ", value = " + this.node.getNode().getValue(true) + " }";
    }
}
