package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import java.util.Comparator;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public abstract class Index implements Comparator<NamedNode> {
    public abstract String getQueryDefinition();

    public abstract boolean isDefinedOn(Node node);

    public abstract NamedNode makePost(ChildKey childKey, Node node);

    public abstract NamedNode maxPost();

    public boolean indexedValueChanged(Node oldNode, Node newNode) {
        NamedNode oldWrapped = new NamedNode(ChildKey.getMinName(), oldNode);
        NamedNode newWrapped = new NamedNode(ChildKey.getMinName(), newNode);
        return compare(oldWrapped, newWrapped) != 0;
    }

    public NamedNode minPost() {
        return NamedNode.getMinNode();
    }

    public static Index fromQueryDefinition(String str) {
        if (str.equals(".value")) {
            return ValueIndex.getInstance();
        }
        if (str.equals(".key")) {
            return KeyIndex.getInstance();
        }
        if (str.equals(".priority")) {
            throw new IllegalStateException("queryDefinition shouldn't ever be .priority since it's the default");
        }
        return new PathIndex(new Path(str));
    }

    public int compare(NamedNode one, NamedNode two, boolean reverse) {
        return reverse ? compare(two, one) : compare(one, two);
    }
}
