package com.google.firebase.database.snapshot;

import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class BooleanNode extends LeafNode<BooleanNode> {
    private final boolean value;

    public BooleanNode(Boolean value, Node priority) {
        super(priority);
        this.value = value.booleanValue();
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Object getValue() {
        return Boolean.valueOf(this.value);
    }

    @Override // com.google.firebase.database.snapshot.Node
    public String getHashRepresentation(Node.HashVersion version) {
        return getPriorityHash(version) + "boolean:" + this.value;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public BooleanNode updatePriority(Node priority) {
        return new BooleanNode(Boolean.valueOf(this.value), priority);
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    protected LeafNode.LeafType getLeafType() {
        return LeafNode.LeafType.Boolean;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public int compareLeafValues(BooleanNode other) {
        if (this.value == other.value) {
            return 0;
        }
        return this.value ? 1 : -1;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public boolean equals(Object other) {
        if (other instanceof BooleanNode) {
            BooleanNode otherBooleanNode = (BooleanNode) other;
            return this.value == otherBooleanNode.value && this.priority.equals(otherBooleanNode.priority);
        }
        return false;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public int hashCode() {
        return (this.value ? 1 : 0) + this.priority.hashCode();
    }
}
