package com.google.firebase.database.core.utilities;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class Tree<T> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private ChildKey name;
    private TreeNode<T> node;
    private Tree<T> parent;

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public interface TreeFilter<T> {
        boolean filterTreeNode(Tree<T> tree);
    }

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public interface TreeVisitor<T> {
        void visitTree(Tree<T> tree);
    }

    static {
        $assertionsDisabled = !Tree.class.desiredAssertionStatus();
    }

    public Tree(ChildKey name, Tree<T> parent, TreeNode<T> node) {
        this.name = name;
        this.parent = parent;
        this.node = node;
    }

    public Tree() {
        this(null, null, new TreeNode());
    }

    public TreeNode<T> lastNodeOnPath(Path path) {
        TreeNode<T> current = this.node;
        ChildKey next = path.getFront();
        while (next != null) {
            TreeNode<T> childNode = current.children.containsKey(next) ? current.children.get(next) : null;
            if (childNode == null) {
                break;
            }
            current = childNode;
            path = path.popFront();
            next = path.getFront();
        }
        return current;
    }

    public Tree<T> subTree(Path path) {
        ChildKey next = path.getFront();
        Tree<T> child = this;
        while (next != null) {
            TreeNode<T> childNode = child.node.children.containsKey(next) ? child.node.children.get(next) : new TreeNode<>();
            Tree<T> child2 = new Tree<>(next, child, childNode);
            path = path.popFront();
            next = path.getFront();
            child = child2;
        }
        return child;
    }

    public T getValue() {
        return this.node.value;
    }

    public void setValue(T value) {
        this.node.value = value;
        updateParents();
    }

    public Tree<T> getParent() {
        return this.parent;
    }

    public ChildKey getName() {
        return this.name;
    }

    public Path getPath() {
        if (this.parent == null) {
            return this.name != null ? new Path(this.name) : Path.getEmptyPath();
        } else if ($assertionsDisabled || this.name != null) {
            return this.parent.getPath().child(this.name);
        } else {
            throw new AssertionError();
        }
    }

    public boolean hasChildren() {
        return !this.node.children.isEmpty();
    }

    public boolean isEmpty() {
        return this.node.value == null && this.node.children.isEmpty();
    }

    public void forEachDescendant(TreeVisitor<T> visitor) {
        forEachDescendant(visitor, false, false);
    }

    public void forEachDescendant(TreeVisitor<T> visitor, boolean includeSelf) {
        forEachDescendant(visitor, includeSelf, false);
    }

    public void forEachDescendant(final TreeVisitor<T> visitor, boolean includeSelf, final boolean childrenFirst) {
        if (includeSelf && !childrenFirst) {
            visitor.visitTree(this);
        }
        forEachChild(new TreeVisitor<T>() { // from class: com.google.firebase.database.core.utilities.Tree.1
            @Override // com.google.firebase.database.core.utilities.Tree.TreeVisitor
            public void visitTree(Tree<T> tree) {
                tree.forEachDescendant(visitor, true, childrenFirst);
            }
        });
        if (includeSelf && childrenFirst) {
            visitor.visitTree(this);
        }
    }

    public boolean forEachAncestor(TreeFilter<T> filter) {
        return forEachAncestor(filter, false);
    }

    public boolean forEachAncestor(TreeFilter<T> filter, boolean includeSelf) {
        for (Tree<T> tree = includeSelf ? this : this.parent; tree != null; tree = tree.parent) {
            if (filter.filterTreeNode(tree)) {
                return true;
            }
        }
        return false;
    }

    public void forEachChild(TreeVisitor<T> visitor) {
        Object[] entries = this.node.children.entrySet().toArray();
        for (Object obj : entries) {
            Map.Entry<ChildKey, TreeNode<T>> entry = (Map.Entry) obj;
            Tree<T> subTree = new Tree<>(entry.getKey(), this, entry.getValue());
            visitor.visitTree(subTree);
        }
    }

    private void updateParents() {
        if (this.parent != null) {
            this.parent.updateChild(this.name, this);
        }
    }

    private void updateChild(ChildKey name, Tree<T> child) {
        boolean childEmpty = child.isEmpty();
        boolean childExists = this.node.children.containsKey(name);
        if (childEmpty && childExists) {
            this.node.children.remove(name);
            updateParents();
        } else if (!childEmpty && !childExists) {
            this.node.children.put(name, child.node);
            updateParents();
        }
    }

    public String toString() {
        return toString("");
    }

    String toString(String prefix) {
        String nodeName = this.name == null ? "<anon>" : this.name.asString();
        return prefix + nodeName + "\n" + this.node.toString(prefix + "\t");
    }
}
