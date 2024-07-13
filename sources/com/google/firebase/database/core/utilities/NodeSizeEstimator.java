package com.google.firebase.database.core.utilities;

import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.LongNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.StringNode;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class NodeSizeEstimator {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int LEAF_PRIORITY_OVERHEAD = 24;

    static {
        $assertionsDisabled = !NodeSizeEstimator.class.desiredAssertionStatus();
    }

    private static long estimateLeafNodeSize(LeafNode<?> node) {
        long valueSize;
        if (node instanceof DoubleNode) {
            valueSize = 8;
        } else if (node instanceof LongNode) {
            valueSize = 8;
        } else if (node instanceof BooleanNode) {
            valueSize = 4;
        } else if (node instanceof StringNode) {
            valueSize = 2 + ((String) node.getValue()).length();
        } else {
            throw new IllegalArgumentException("Unknown leaf node type: " + node.getClass());
        }
        if (node.getPriority().isEmpty()) {
            return valueSize;
        }
        return 24 + valueSize + estimateLeafNodeSize((LeafNode) node.getPriority());
    }

    public static long estimateSerializedNodeSize(Node node) {
        if (node.isEmpty()) {
            return 4L;
        }
        if (node.isLeafNode()) {
            long sum = estimateLeafNodeSize((LeafNode) node);
            return sum;
        } else if ($assertionsDisabled || (node instanceof ChildrenNode)) {
            long sum2 = 1;
            for (NamedNode entry : node) {
                sum2 = sum2 + entry.getName().asString().length() + 4 + estimateSerializedNodeSize(entry.getNode());
            }
            if (!node.getPriority().isEmpty()) {
                return sum2 + 12 + estimateLeafNodeSize((LeafNode) node.getPriority());
            }
            return sum2;
        } else {
            throw new AssertionError("Unexpected node type: " + node.getClass());
        }
    }

    public static int nodeCount(Node node) {
        if (node.isEmpty()) {
            return 0;
        }
        if (node.isLeafNode()) {
            return 1;
        }
        if ($assertionsDisabled || (node instanceof ChildrenNode)) {
            int sum = 0;
            for (NamedNode entry : node) {
                sum += nodeCount(entry.getNode());
            }
            return sum;
        }
        throw new AssertionError("Unexpected node type: " + node.getClass());
    }
}
