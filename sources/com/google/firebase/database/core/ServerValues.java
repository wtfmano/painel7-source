package com.google.firebase.database.core;

import com.google.firebase.database.core.SparseSnapshotTree;
import com.google.firebase.database.core.utilities.Clock;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class ServerValues {
    public static final String NAME_SUBKEY_SERVERVALUE = ".sv";

    public static Map<String, Object> generateServerValues(Clock clock) {
        Map<String, Object> values = new HashMap<>();
        values.put("timestamp", Long.valueOf(clock.millis()));
        return values;
    }

    public static Object resolveDeferredValue(Object value, Map<String, Object> serverValues) {
        if (value instanceof Map) {
            Map mapValue = (Map) value;
            if (mapValue.containsKey(NAME_SUBKEY_SERVERVALUE)) {
                String serverValueKey = (String) mapValue.get(NAME_SUBKEY_SERVERVALUE);
                if (serverValues.containsKey(serverValueKey)) {
                    return serverValues.get(serverValueKey);
                }
                return value;
            }
            return value;
        }
        return value;
    }

    public static SparseSnapshotTree resolveDeferredValueTree(SparseSnapshotTree tree, final Map<String, Object> serverValues) {
        final SparseSnapshotTree resolvedTree = new SparseSnapshotTree();
        tree.forEachTree(new Path(""), new SparseSnapshotTree.SparseSnapshotTreeVisitor() { // from class: com.google.firebase.database.core.ServerValues.1
            @Override // com.google.firebase.database.core.SparseSnapshotTree.SparseSnapshotTreeVisitor
            public void visitTree(Path prefixPath, Node tree2) {
                SparseSnapshotTree.this.remember(prefixPath, ServerValues.resolveDeferredValueSnapshot(tree2, serverValues));
            }
        });
        return resolvedTree;
    }

    public static Node resolveDeferredValueSnapshot(Node data, final Map<String, Object> serverValues) {
        Object priorityVal = data.getPriority().getValue();
        if (priorityVal instanceof Map) {
            Map priorityMapValue = (Map) priorityVal;
            if (priorityMapValue.containsKey(NAME_SUBKEY_SERVERVALUE)) {
                String serverValueKey = (String) priorityMapValue.get(NAME_SUBKEY_SERVERVALUE);
                priorityVal = serverValues.get(serverValueKey);
            }
        }
        Node priority = PriorityUtilities.parsePriority(priorityVal);
        if (data.isLeafNode()) {
            Object value = resolveDeferredValue(data.getValue(), serverValues);
            if (!value.equals(data.getValue()) || !priority.equals(data.getPriority())) {
                return NodeUtilities.NodeFromJSON(value, priority);
            }
            return data;
        } else if (!data.isEmpty()) {
            ChildrenNode childNode = (ChildrenNode) data;
            final SnapshotHolder holder = new SnapshotHolder(childNode);
            childNode.forEachChild(new ChildrenNode.ChildVisitor() { // from class: com.google.firebase.database.core.ServerValues.2
                @Override // com.google.firebase.database.snapshot.ChildrenNode.ChildVisitor
                public void visitChild(ChildKey name, Node child) {
                    Node newChildNode = ServerValues.resolveDeferredValueSnapshot(child, serverValues);
                    if (newChildNode != child) {
                        holder.update(new Path(name.asString()), newChildNode);
                    }
                }
            });
            if (!holder.getRootNode().getPriority().equals(priority)) {
                return holder.getRootNode().updatePriority(priority);
            }
            return holder.getRootNode();
        } else {
            return data;
        }
    }

    public static CompoundWrite resolveDeferredValueMerge(CompoundWrite merge, Map<String, Object> serverValues) {
        CompoundWrite write = CompoundWrite.emptyWrite();
        Iterator<Map.Entry<Path, Node>> it = merge.iterator();
        while (it.hasNext()) {
            Map.Entry<Path, Node> entry = it.next();
            write = write.addWrite(entry.getKey(), resolveDeferredValueSnapshot(entry.getValue(), serverValues));
        }
        return write;
    }
}
