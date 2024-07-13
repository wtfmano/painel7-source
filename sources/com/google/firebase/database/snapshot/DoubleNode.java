package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class DoubleNode extends LeafNode<DoubleNode> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Double value;

    static {
        $assertionsDisabled = !DoubleNode.class.desiredAssertionStatus();
    }

    public DoubleNode(Double value, Node priority) {
        super(priority);
        this.value = value;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Object getValue() {
        return this.value;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public String getHashRepresentation(Node.HashVersion version) {
        String toHash = getPriorityHash(version);
        return (toHash + "number:") + Utilities.doubleToHashString(this.value.doubleValue());
    }

    @Override // com.google.firebase.database.snapshot.Node
    public DoubleNode updatePriority(Node priority) {
        if ($assertionsDisabled || PriorityUtilities.isValidPriority(priority)) {
            return new DoubleNode(this.value, priority);
        }
        throw new AssertionError();
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    protected LeafNode.LeafType getLeafType() {
        return LeafNode.LeafType.Number;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public int compareLeafValues(DoubleNode other) {
        return this.value.compareTo(other.value);
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public boolean equals(Object other) {
        if (other instanceof DoubleNode) {
            DoubleNode otherDoubleNode = (DoubleNode) other;
            return this.value.equals(otherDoubleNode.value) && this.priority.equals(otherDoubleNode.priority);
        }
        return false;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public int hashCode() {
        return this.value.hashCode() + this.priority.hashCode();
    }
}
