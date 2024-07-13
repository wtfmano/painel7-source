package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class LongNode extends LeafNode<LongNode> {
    private final long value;

    public LongNode(Long value, Node priority) {
        super(priority);
        this.value = value.longValue();
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Object getValue() {
        return Long.valueOf(this.value);
    }

    @Override // com.google.firebase.database.snapshot.Node
    public String getHashRepresentation(Node.HashVersion version) {
        String toHash = getPriorityHash(version);
        return (toHash + "number:") + Utilities.doubleToHashString(this.value);
    }

    @Override // com.google.firebase.database.snapshot.Node
    public LongNode updatePriority(Node priority) {
        return new LongNode(Long.valueOf(this.value), priority);
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    protected LeafNode.LeafType getLeafType() {
        return LeafNode.LeafType.Number;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public int compareLeafValues(LongNode other) {
        return Utilities.compareLongs(this.value, other.value);
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public boolean equals(Object other) {
        if (other instanceof LongNode) {
            LongNode otherLongNode = (LongNode) other;
            return this.value == otherLongNode.value && this.priority.equals(otherLongNode.priority);
        }
        return false;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public int hashCode() {
        return ((int) (this.value ^ (this.value >>> 32))) + this.priority.hashCode();
    }
}
