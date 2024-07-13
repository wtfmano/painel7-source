package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public abstract class LeafNode<T extends LeafNode> implements Node {
    static final /* synthetic */ boolean $assertionsDisabled;
    private String lazyHash;
    protected final Node priority;

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public enum LeafType {
        DeferredValue,
        Boolean,
        Number,
        String
    }

    protected abstract int compareLeafValues(T t);

    public abstract boolean equals(Object obj);

    protected abstract LeafType getLeafType();

    public abstract int hashCode();

    static {
        $assertionsDisabled = !LeafNode.class.desiredAssertionStatus();
    }

    public LeafNode(Node priority) {
        this.priority = priority;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public boolean hasChild(ChildKey childKey) {
        return false;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public boolean isLeafNode() {
        return true;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Node getPriority() {
        return this.priority;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Node getChild(Path path) {
        if (!path.isEmpty()) {
            if (path.getFront().isPriorityChildName()) {
                return this.priority;
            }
            return EmptyNode.Empty();
        }
        return this;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Node updateChild(Path path, Node node) {
        ChildKey front = path.getFront();
        if (front != null) {
            if (!node.isEmpty() || front.isPriorityChildName()) {
                if ($assertionsDisabled || !path.getFront().isPriorityChildName() || path.size() == 1) {
                    return updateImmediateChild(front, EmptyNode.Empty().updateChild(path.popFront(), node));
                }
                throw new AssertionError();
            }
            return this;
        }
        return node;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public boolean isEmpty() {
        return false;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public int getChildCount() {
        return 0;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public ChildKey getPredecessorChildKey(ChildKey childKey) {
        return null;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public ChildKey getSuccessorChildKey(ChildKey childKey) {
        return null;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Node getImmediateChild(ChildKey name) {
        return name.isPriorityChildName() ? this.priority : EmptyNode.Empty();
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Object getValue(boolean useExportFormat) {
        if (!useExportFormat || this.priority.isEmpty()) {
            return getValue();
        }
        Map<String, Object> result = new HashMap<>();
        result.put(".value", getValue());
        result.put(".priority", this.priority.getValue());
        return result;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Node updateImmediateChild(ChildKey name, Node node) {
        if (name.isPriorityChildName()) {
            return updatePriority(node);
        }
        return !node.isEmpty() ? EmptyNode.Empty().updateImmediateChild(name, node).updatePriority(this.priority) : this;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public String getHash() {
        if (this.lazyHash == null) {
            this.lazyHash = Utilities.sha1HexDigest(getHashRepresentation(Node.HashVersion.V1));
        }
        return this.lazyHash;
    }

    public String getPriorityHash(Node.HashVersion version) {
        switch (version) {
            case V1:
            case V2:
                return this.priority.isEmpty() ? "" : "priority:" + this.priority.getHashRepresentation(version) + ":";
            default:
                throw new IllegalArgumentException("Unknown hash version: " + version);
        }
    }

    @Override // java.lang.Iterable
    public Iterator<NamedNode> iterator() {
        return Collections.emptyList().iterator();
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Iterator<NamedNode> reverseIterator() {
        return Collections.emptyList().iterator();
    }

    private static int compareLongDoubleNodes(LongNode longNode, DoubleNode doubleNode) {
        Double longDoubleValue = Double.valueOf(((Long) longNode.getValue()).longValue());
        return longDoubleValue.compareTo((Double) doubleNode.getValue());
    }

    @Override // java.lang.Comparable
    public int compareTo(Node other) {
        if (other.isEmpty()) {
            return 1;
        }
        if (other instanceof ChildrenNode) {
            return -1;
        }
        if ($assertionsDisabled || other.isLeafNode()) {
            if ((this instanceof LongNode) && (other instanceof DoubleNode)) {
                return compareLongDoubleNodes((LongNode) this, (DoubleNode) other);
            }
            if ((this instanceof DoubleNode) && (other instanceof LongNode)) {
                return compareLongDoubleNodes((LongNode) other, (DoubleNode) this) * (-1);
            }
            return leafCompare((LeafNode) other);
        }
        throw new AssertionError("Node is not leaf node!");
    }

    protected int leafCompare(LeafNode<?> other) {
        LeafType thisLeafType = getLeafType();
        LeafType otherLeafType = other.getLeafType();
        return thisLeafType.equals(otherLeafType) ? compareLeafValues(other) : thisLeafType.compareTo(otherLeafType);
    }

    public String toString() {
        String str = getValue(true).toString();
        return str.length() <= 100 ? str : str.substring(0, 100) + "...";
    }
}
