package com.google.firebase.database.core.view;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.WriteTreeRef;
import com.google.firebase.database.core.operation.AckUserWrite;
import com.google.firebase.database.core.operation.Merge;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.operation.Overwrite;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.view.filter.ChildChangeAccumulator;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.KeyIndex;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class ViewProcessor {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static NodeFilter.CompleteChildSource NO_COMPLETE_SOURCE;
    private final NodeFilter filter;

    static {
        $assertionsDisabled = !ViewProcessor.class.desiredAssertionStatus();
        NO_COMPLETE_SOURCE = new NodeFilter.CompleteChildSource() { // from class: com.google.firebase.database.core.view.ViewProcessor.1
            @Override // com.google.firebase.database.core.view.filter.NodeFilter.CompleteChildSource
            public Node getCompleteChild(ChildKey childKey) {
                return null;
            }

            @Override // com.google.firebase.database.core.view.filter.NodeFilter.CompleteChildSource
            public NamedNode getChildAfterChild(Index index, NamedNode child, boolean reverse) {
                return null;
            }
        };
    }

    public ViewProcessor(NodeFilter filter) {
        this.filter = filter;
    }

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public static class ProcessorResult {
        public final List<Change> changes;
        public final ViewCache viewCache;

        public ProcessorResult(ViewCache viewCache, List<Change> changes) {
            this.viewCache = viewCache;
            this.changes = changes;
        }
    }

    public ProcessorResult applyOperation(ViewCache oldViewCache, Operation operation, WriteTreeRef writesCache, Node optCompleteCache) {
        ViewCache newViewCache;
        ChildChangeAccumulator accumulator = new ChildChangeAccumulator();
        switch (operation.getType()) {
            case Overwrite:
                Overwrite overwrite = (Overwrite) operation;
                if (overwrite.getSource().isFromUser()) {
                    newViewCache = applyUserOverwrite(oldViewCache, overwrite.getPath(), overwrite.getSnapshot(), writesCache, optCompleteCache, accumulator);
                    break;
                } else if ($assertionsDisabled || overwrite.getSource().isFromServer()) {
                    boolean filterServerNode = overwrite.getSource().isTagged() || (oldViewCache.getServerCache().isFiltered() && !overwrite.getPath().isEmpty());
                    newViewCache = applyServerOverwrite(oldViewCache, overwrite.getPath(), overwrite.getSnapshot(), writesCache, optCompleteCache, filterServerNode, accumulator);
                    break;
                } else {
                    throw new AssertionError();
                }
                break;
            case Merge:
                Merge merge = (Merge) operation;
                if (merge.getSource().isFromUser()) {
                    newViewCache = applyUserMerge(oldViewCache, merge.getPath(), merge.getChildren(), writesCache, optCompleteCache, accumulator);
                    break;
                } else if ($assertionsDisabled || merge.getSource().isFromServer()) {
                    boolean filterServerNode2 = merge.getSource().isTagged() || oldViewCache.getServerCache().isFiltered();
                    newViewCache = applyServerMerge(oldViewCache, merge.getPath(), merge.getChildren(), writesCache, optCompleteCache, filterServerNode2, accumulator);
                    break;
                } else {
                    throw new AssertionError();
                }
                break;
            case AckUserWrite:
                AckUserWrite ackUserWrite = (AckUserWrite) operation;
                if (!ackUserWrite.isRevert()) {
                    newViewCache = ackUserWrite(oldViewCache, ackUserWrite.getPath(), ackUserWrite.getAffectedTree(), writesCache, optCompleteCache, accumulator);
                    break;
                } else {
                    newViewCache = revertUserWrite(oldViewCache, ackUserWrite.getPath(), writesCache, optCompleteCache, accumulator);
                    break;
                }
            case ListenComplete:
                newViewCache = listenComplete(oldViewCache, operation.getPath(), writesCache, optCompleteCache, accumulator);
                break;
            default:
                throw new AssertionError("Unknown operation: " + operation.getType());
        }
        List<Change> changes = new ArrayList<>(accumulator.getChanges());
        maybeAddValueEvent(oldViewCache, newViewCache, changes);
        return new ProcessorResult(newViewCache, changes);
    }

    private void maybeAddValueEvent(ViewCache oldViewCache, ViewCache newViewCache, List<Change> accumulator) {
        CacheNode eventSnap = newViewCache.getEventCache();
        if (eventSnap.isFullyInitialized()) {
            boolean isLeafOrEmpty = eventSnap.getNode().isLeafNode() || eventSnap.getNode().isEmpty();
            if (!accumulator.isEmpty() || !oldViewCache.getEventCache().isFullyInitialized() || ((isLeafOrEmpty && !eventSnap.getNode().equals(oldViewCache.getCompleteEventSnap())) || !eventSnap.getNode().getPriority().equals(oldViewCache.getCompleteEventSnap().getPriority()))) {
                accumulator.add(Change.valueChange(eventSnap.getIndexedNode()));
            }
        }
    }

    private ViewCache generateEventCacheAfterServerEvent(ViewCache viewCache, Path changePath, WriteTreeRef writesCache, NodeFilter.CompleteChildSource source, ChildChangeAccumulator accumulator) {
        Node newEventChild;
        IndexedNode newEventCache;
        Node nodeWithLocalWrites;
        CacheNode oldEventSnap = viewCache.getEventCache();
        if (writesCache.shadowingWrite(changePath) == null) {
            if (changePath.isEmpty()) {
                if (!$assertionsDisabled && !viewCache.getServerCache().isFullyInitialized()) {
                    throw new AssertionError("If change path is empty, we must have complete server data");
                }
                if (viewCache.getServerCache().isFiltered()) {
                    Node serverCache = viewCache.getCompleteServerSnap();
                    Node completeChildren = serverCache instanceof ChildrenNode ? serverCache : EmptyNode.Empty();
                    nodeWithLocalWrites = writesCache.calcCompleteEventChildren(completeChildren);
                } else {
                    nodeWithLocalWrites = writesCache.calcCompleteEventCache(viewCache.getCompleteServerSnap());
                }
                IndexedNode indexedNode = IndexedNode.from(nodeWithLocalWrites, this.filter.getIndex());
                newEventCache = this.filter.updateFullNode(viewCache.getEventCache().getIndexedNode(), indexedNode, accumulator);
            } else {
                ChildKey childKey = changePath.getFront();
                if (childKey.isPriorityChildName()) {
                    if (!$assertionsDisabled && changePath.size() != 1) {
                        throw new AssertionError("Can't have a priority with additional path components");
                    }
                    Node oldEventNode = oldEventSnap.getNode();
                    Node serverNode = viewCache.getServerCache().getNode();
                    Node updatedPriority = writesCache.calcEventCacheAfterServerOverwrite(changePath, oldEventNode, serverNode);
                    if (updatedPriority != null) {
                        newEventCache = this.filter.updatePriority(oldEventSnap.getIndexedNode(), updatedPriority);
                    } else {
                        newEventCache = oldEventSnap.getIndexedNode();
                    }
                } else {
                    Path childChangePath = changePath.popFront();
                    if (oldEventSnap.isCompleteForChild(childKey)) {
                        Node serverNode2 = viewCache.getServerCache().getNode();
                        Node eventChildUpdate = writesCache.calcEventCacheAfterServerOverwrite(changePath, oldEventSnap.getNode(), serverNode2);
                        if (eventChildUpdate != null) {
                            newEventChild = oldEventSnap.getNode().getImmediateChild(childKey).updateChild(childChangePath, eventChildUpdate);
                        } else {
                            newEventChild = oldEventSnap.getNode().getImmediateChild(childKey);
                        }
                    } else {
                        newEventChild = writesCache.calcCompleteChild(childKey, viewCache.getServerCache());
                    }
                    if (newEventChild != null) {
                        newEventCache = this.filter.updateChild(oldEventSnap.getIndexedNode(), childKey, newEventChild, childChangePath, source, accumulator);
                    } else {
                        newEventCache = oldEventSnap.getIndexedNode();
                    }
                }
            }
            return viewCache.updateEventSnap(newEventCache, oldEventSnap.isFullyInitialized() || changePath.isEmpty(), this.filter.filtersNodes());
        }
        return viewCache;
    }

    private ViewCache applyServerOverwrite(ViewCache oldViewCache, Path changePath, Node changedSnap, WriteTreeRef writesCache, Node optCompleteCache, boolean filterServerNode, ChildChangeAccumulator accumulator) {
        IndexedNode newServerCache;
        CacheNode oldServerSnap = oldViewCache.getServerCache();
        NodeFilter serverFilter = filterServerNode ? this.filter : this.filter.getIndexedFilter();
        if (changePath.isEmpty()) {
            newServerCache = serverFilter.updateFullNode(oldServerSnap.getIndexedNode(), IndexedNode.from(changedSnap, serverFilter.getIndex()), null);
        } else if (serverFilter.filtersNodes() && !oldServerSnap.isFiltered()) {
            if (!$assertionsDisabled && changePath.isEmpty()) {
                throw new AssertionError("An empty path should have been caught in the other branch");
            }
            ChildKey childKey = changePath.getFront();
            Path updatePath = changePath.popFront();
            Node newChild = oldServerSnap.getNode().getImmediateChild(childKey).updateChild(updatePath, changedSnap);
            IndexedNode newServerNode = oldServerSnap.getIndexedNode().updateChild(childKey, newChild);
            newServerCache = serverFilter.updateFullNode(oldServerSnap.getIndexedNode(), newServerNode, null);
        } else {
            ChildKey childKey2 = changePath.getFront();
            if (!oldServerSnap.isCompleteForPath(changePath) && changePath.size() > 1) {
                return oldViewCache;
            }
            Path childChangePath = changePath.popFront();
            Node childNode = oldServerSnap.getNode().getImmediateChild(childKey2);
            Node newChildNode = childNode.updateChild(childChangePath, changedSnap);
            if (childKey2.isPriorityChildName()) {
                newServerCache = serverFilter.updatePriority(oldServerSnap.getIndexedNode(), newChildNode);
            } else {
                newServerCache = serverFilter.updateChild(oldServerSnap.getIndexedNode(), childKey2, newChildNode, childChangePath, NO_COMPLETE_SOURCE, null);
            }
        }
        ViewCache newViewCache = oldViewCache.updateServerSnap(newServerCache, oldServerSnap.isFullyInitialized() || changePath.isEmpty(), serverFilter.filtersNodes());
        NodeFilter.CompleteChildSource source = new WriteTreeCompleteChildSource(writesCache, newViewCache, optCompleteCache);
        return generateEventCacheAfterServerEvent(newViewCache, changePath, writesCache, source, accumulator);
    }

    private ViewCache applyUserOverwrite(ViewCache oldViewCache, Path changePath, Node changedSnap, WriteTreeRef writesCache, Node optCompleteCache, ChildChangeAccumulator accumulator) {
        Node newChild;
        CacheNode oldEventSnap = oldViewCache.getEventCache();
        NodeFilter.CompleteChildSource source = new WriteTreeCompleteChildSource(writesCache, oldViewCache, optCompleteCache);
        if (changePath.isEmpty()) {
            IndexedNode newIndexed = IndexedNode.from(changedSnap, this.filter.getIndex());
            IndexedNode newEventCache = this.filter.updateFullNode(oldViewCache.getEventCache().getIndexedNode(), newIndexed, accumulator);
            ViewCache newViewCache = oldViewCache.updateEventSnap(newEventCache, true, this.filter.filtersNodes());
            return newViewCache;
        }
        ChildKey childKey = changePath.getFront();
        if (childKey.isPriorityChildName()) {
            IndexedNode newEventCache2 = this.filter.updatePriority(oldViewCache.getEventCache().getIndexedNode(), changedSnap);
            ViewCache newViewCache2 = oldViewCache.updateEventSnap(newEventCache2, oldEventSnap.isFullyInitialized(), oldEventSnap.isFiltered());
            return newViewCache2;
        }
        Path childChangePath = changePath.popFront();
        Node oldChild = oldEventSnap.getNode().getImmediateChild(childKey);
        if (childChangePath.isEmpty()) {
            newChild = changedSnap;
        } else {
            Node childNode = source.getCompleteChild(childKey);
            if (childNode != null) {
                if (childChangePath.getBack().isPriorityChildName() && childNode.getChild(childChangePath.getParent()).isEmpty()) {
                    newChild = childNode;
                } else {
                    newChild = childNode.updateChild(childChangePath, changedSnap);
                }
            } else {
                newChild = EmptyNode.Empty();
            }
        }
        if (!oldChild.equals(newChild)) {
            IndexedNode newEventSnap = this.filter.updateChild(oldEventSnap.getIndexedNode(), childKey, newChild, childChangePath, source, accumulator);
            ViewCache newViewCache3 = oldViewCache.updateEventSnap(newEventSnap, oldEventSnap.isFullyInitialized(), this.filter.filtersNodes());
            return newViewCache3;
        }
        return oldViewCache;
    }

    private static boolean cacheHasChild(ViewCache viewCache, ChildKey childKey) {
        return viewCache.getEventCache().isCompleteForChild(childKey);
    }

    private ViewCache applyUserMerge(ViewCache viewCache, Path path, CompoundWrite changedChildren, WriteTreeRef writesCache, Node serverCache, ChildChangeAccumulator accumulator) {
        if ($assertionsDisabled || changedChildren.rootWrite() == null) {
            ViewCache currentViewCache = viewCache;
            Iterator<Map.Entry<Path, Node>> it = changedChildren.iterator();
            while (it.hasNext()) {
                Map.Entry<Path, Node> entry = it.next();
                Path writePath = path.child(entry.getKey());
                if (cacheHasChild(viewCache, writePath.getFront())) {
                    currentViewCache = applyUserOverwrite(currentViewCache, writePath, entry.getValue(), writesCache, serverCache, accumulator);
                }
            }
            Iterator<Map.Entry<Path, Node>> it2 = changedChildren.iterator();
            while (it2.hasNext()) {
                Map.Entry<Path, Node> entry2 = it2.next();
                Path writePath2 = path.child(entry2.getKey());
                if (!cacheHasChild(viewCache, writePath2.getFront())) {
                    currentViewCache = applyUserOverwrite(currentViewCache, writePath2, entry2.getValue(), writesCache, serverCache, accumulator);
                }
            }
            return currentViewCache;
        }
        throw new AssertionError("Can't have a merge that is an overwrite");
    }

    private ViewCache applyServerMerge(ViewCache viewCache, Path path, CompoundWrite changedChildren, WriteTreeRef writesCache, Node serverCache, boolean filterServerNode, ChildChangeAccumulator accumulator) {
        CompoundWrite actualMerge;
        if (!viewCache.getServerCache().getNode().isEmpty() || viewCache.getServerCache().isFullyInitialized()) {
            ViewCache curViewCache = viewCache;
            if ($assertionsDisabled || changedChildren.rootWrite() == null) {
                if (path.isEmpty()) {
                    actualMerge = changedChildren;
                } else {
                    actualMerge = CompoundWrite.emptyWrite().addWrites(path, changedChildren);
                }
                Node serverNode = viewCache.getServerCache().getNode();
                Map<ChildKey, CompoundWrite> childCompoundWrites = actualMerge.childCompoundWrites();
                for (Map.Entry<ChildKey, CompoundWrite> childMerge : childCompoundWrites.entrySet()) {
                    ChildKey childKey = childMerge.getKey();
                    if (serverNode.hasChild(childKey)) {
                        Node serverChild = serverNode.getImmediateChild(childKey);
                        Node newChild = childMerge.getValue().apply(serverChild);
                        curViewCache = applyServerOverwrite(curViewCache, new Path(childKey), newChild, writesCache, serverCache, filterServerNode, accumulator);
                    }
                }
                for (Map.Entry<ChildKey, CompoundWrite> childMerge2 : childCompoundWrites.entrySet()) {
                    ChildKey childKey2 = childMerge2.getKey();
                    CompoundWrite childCompoundWrite = childMerge2.getValue();
                    boolean isUnknownDeepMerge = !viewCache.getServerCache().isCompleteForChild(childKey2) && childCompoundWrite.rootWrite() == null;
                    if (!serverNode.hasChild(childKey2) && !isUnknownDeepMerge) {
                        Node serverChild2 = serverNode.getImmediateChild(childKey2);
                        Node newChild2 = childMerge2.getValue().apply(serverChild2);
                        curViewCache = applyServerOverwrite(curViewCache, new Path(childKey2), newChild2, writesCache, serverCache, filterServerNode, accumulator);
                    }
                }
                return curViewCache;
            }
            throw new AssertionError("Can't have a merge that is an overwrite");
        }
        return viewCache;
    }

    private ViewCache ackUserWrite(ViewCache viewCache, Path ackPath, ImmutableTree<Boolean> affectedTree, WriteTreeRef writesCache, Node optCompleteCache, ChildChangeAccumulator accumulator) {
        if (writesCache.shadowingWrite(ackPath) == null) {
            boolean filterServerNode = viewCache.getServerCache().isFiltered();
            CacheNode serverCache = viewCache.getServerCache();
            if (affectedTree.getValue() != null) {
                if ((ackPath.isEmpty() && serverCache.isFullyInitialized()) || serverCache.isCompleteForPath(ackPath)) {
                    return applyServerOverwrite(viewCache, ackPath, serverCache.getNode().getChild(ackPath), writesCache, optCompleteCache, filterServerNode, accumulator);
                }
                if (ackPath.isEmpty()) {
                    CompoundWrite changedChildren = CompoundWrite.emptyWrite();
                    for (NamedNode child : serverCache.getNode()) {
                        changedChildren = changedChildren.addWrite(child.getName(), child.getNode());
                    }
                    return applyServerMerge(viewCache, ackPath, changedChildren, writesCache, optCompleteCache, filterServerNode, accumulator);
                }
                return viewCache;
            }
            CompoundWrite changedChildren2 = CompoundWrite.emptyWrite();
            Iterator<Map.Entry<Path, Boolean>> it = affectedTree.iterator();
            while (it.hasNext()) {
                Map.Entry<Path, Boolean> entry = it.next();
                Path mergePath = entry.getKey();
                Path serverCachePath = ackPath.child(mergePath);
                if (serverCache.isCompleteForPath(serverCachePath)) {
                    changedChildren2 = changedChildren2.addWrite(mergePath, serverCache.getNode().getChild(serverCachePath));
                }
            }
            return applyServerMerge(viewCache, ackPath, changedChildren2, writesCache, optCompleteCache, filterServerNode, accumulator);
        }
        return viewCache;
    }

    public ViewCache revertUserWrite(ViewCache viewCache, Path path, WriteTreeRef writesCache, Node optCompleteServerCache, ChildChangeAccumulator accumulator) {
        Node newNode;
        IndexedNode newEventCache;
        if (writesCache.shadowingWrite(path) == null) {
            NodeFilter.CompleteChildSource source = new WriteTreeCompleteChildSource(writesCache, viewCache, optCompleteServerCache);
            IndexedNode oldEventCache = viewCache.getEventCache().getIndexedNode();
            if (path.isEmpty() || path.getFront().isPriorityChildName()) {
                if (viewCache.getServerCache().isFullyInitialized()) {
                    newNode = writesCache.calcCompleteEventCache(viewCache.getCompleteServerSnap());
                } else {
                    newNode = writesCache.calcCompleteEventChildren(viewCache.getServerCache().getNode());
                }
                IndexedNode indexedNode = IndexedNode.from(newNode, this.filter.getIndex());
                newEventCache = this.filter.updateFullNode(oldEventCache, indexedNode, accumulator);
            } else {
                ChildKey childKey = path.getFront();
                Node newChild = writesCache.calcCompleteChild(childKey, viewCache.getServerCache());
                if (newChild == null && viewCache.getServerCache().isCompleteForChild(childKey)) {
                    newChild = oldEventCache.getNode().getImmediateChild(childKey);
                }
                if (newChild != null) {
                    newEventCache = this.filter.updateChild(oldEventCache, childKey, newChild, path.popFront(), source, accumulator);
                } else if (newChild == null && viewCache.getEventCache().getNode().hasChild(childKey)) {
                    newEventCache = this.filter.updateChild(oldEventCache, childKey, EmptyNode.Empty(), path.popFront(), source, accumulator);
                } else {
                    newEventCache = oldEventCache;
                }
                if (newEventCache.getNode().isEmpty() && viewCache.getServerCache().isFullyInitialized()) {
                    Node complete = writesCache.calcCompleteEventCache(viewCache.getCompleteServerSnap());
                    if (complete.isLeafNode()) {
                        IndexedNode indexedNode2 = IndexedNode.from(complete, this.filter.getIndex());
                        newEventCache = this.filter.updateFullNode(newEventCache, indexedNode2, accumulator);
                    }
                }
            }
            return viewCache.updateEventSnap(newEventCache, viewCache.getServerCache().isFullyInitialized() || writesCache.shadowingWrite(Path.getEmptyPath()) != null, this.filter.filtersNodes());
        }
        return viewCache;
    }

    private ViewCache listenComplete(ViewCache viewCache, Path path, WriteTreeRef writesCache, Node serverCache, ChildChangeAccumulator accumulator) {
        CacheNode oldServerNode = viewCache.getServerCache();
        ViewCache newViewCache = viewCache.updateServerSnap(oldServerNode.getIndexedNode(), oldServerNode.isFullyInitialized() || path.isEmpty(), oldServerNode.isFiltered());
        return generateEventCacheAfterServerEvent(newViewCache, path, writesCache, NO_COMPLETE_SOURCE, accumulator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public static class WriteTreeCompleteChildSource implements NodeFilter.CompleteChildSource {
        private final Node optCompleteServerCache;
        private final ViewCache viewCache;
        private final WriteTreeRef writes;

        public WriteTreeCompleteChildSource(WriteTreeRef writes, ViewCache viewCache, Node optCompleteServerCache) {
            this.writes = writes;
            this.viewCache = viewCache;
            this.optCompleteServerCache = optCompleteServerCache;
        }

        @Override // com.google.firebase.database.core.view.filter.NodeFilter.CompleteChildSource
        public Node getCompleteChild(ChildKey childKey) {
            CacheNode serverNode;
            CacheNode node = this.viewCache.getEventCache();
            if (node.isCompleteForChild(childKey)) {
                return node.getNode().getImmediateChild(childKey);
            }
            if (this.optCompleteServerCache != null) {
                serverNode = new CacheNode(IndexedNode.from(this.optCompleteServerCache, KeyIndex.getInstance()), true, false);
            } else {
                serverNode = this.viewCache.getServerCache();
            }
            return this.writes.calcCompleteChild(childKey, serverNode);
        }

        @Override // com.google.firebase.database.core.view.filter.NodeFilter.CompleteChildSource
        public NamedNode getChildAfterChild(Index index, NamedNode child, boolean reverse) {
            Node completeServerData;
            if (this.optCompleteServerCache != null) {
                completeServerData = this.optCompleteServerCache;
            } else {
                completeServerData = this.viewCache.getCompleteServerSnap();
            }
            return this.writes.calcNextNodeAfterPost(completeServerData, child, reverse, index);
        }
    }
}
