package com.google.firebase.database.snapshot;

import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class DeferredValueNode extends LeafNode<DeferredValueNode> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Map<Object, Object> value;

    static {
        $assertionsDisabled = !DeferredValueNode.class.desiredAssertionStatus();
    }

    public DeferredValueNode(Map<Object, Object> value, Node priority) {
        super(priority);
        this.value = value;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public Object getValue() {
        return this.value;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public String getHashRepresentation(Node.HashVersion version) {
        return getPriorityHash(version) + "deferredValue:" + this.value;
    }

    @Override // com.google.firebase.database.snapshot.Node
    public DeferredValueNode updatePriority(Node priority) {
        if ($assertionsDisabled || PriorityUtilities.isValidPriority(priority)) {
            return new DeferredValueNode(this.value, priority);
        }
        throw new AssertionError();
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    protected LeafNode.LeafType getLeafType() {
        return LeafNode.LeafType.DeferredValue;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public int compareLeafValues(DeferredValueNode other) {
        return 0;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public boolean equals(Object other) {
        if (other instanceof DeferredValueNode) {
            DeferredValueNode otherDeferredValueNode = (DeferredValueNode) other;
            return this.value.equals(otherDeferredValueNode.value) && this.priority.equals(otherDeferredValueNode.priority);
        }
        return false;
    }

    @Override // com.google.firebase.database.snapshot.LeafNode
    public int hashCode() {
        return this.value.hashCode() + this.priority.hashCode();
    }
}
