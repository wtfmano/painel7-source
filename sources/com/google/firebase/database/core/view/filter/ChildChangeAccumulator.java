package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class ChildChangeAccumulator {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Map<ChildKey, Change> changeMap = new HashMap();

    static {
        $assertionsDisabled = !ChildChangeAccumulator.class.desiredAssertionStatus();
    }

    public void trackChildChange(Change change) {
        Event.EventType type = change.getEventType();
        ChildKey childKey = change.getChildKey();
        if (!$assertionsDisabled && type != Event.EventType.CHILD_ADDED && type != Event.EventType.CHILD_CHANGED && type != Event.EventType.CHILD_REMOVED) {
            throw new AssertionError("Only child changes supported for tracking");
        }
        if (!$assertionsDisabled && change.getChildKey().isPriorityChildName()) {
            throw new AssertionError();
        }
        if (this.changeMap.containsKey(childKey)) {
            Change oldChange = this.changeMap.get(childKey);
            Event.EventType oldType = oldChange.getEventType();
            if (type == Event.EventType.CHILD_ADDED && oldType == Event.EventType.CHILD_REMOVED) {
                this.changeMap.put(change.getChildKey(), Change.childChangedChange(childKey, change.getIndexedNode(), oldChange.getIndexedNode()));
                return;
            } else if (type == Event.EventType.CHILD_REMOVED && oldType == Event.EventType.CHILD_ADDED) {
                this.changeMap.remove(childKey);
                return;
            } else if (type == Event.EventType.CHILD_REMOVED && oldType == Event.EventType.CHILD_CHANGED) {
                this.changeMap.put(childKey, Change.childRemovedChange(childKey, oldChange.getOldIndexedNode()));
                return;
            } else if (type == Event.EventType.CHILD_CHANGED && oldType == Event.EventType.CHILD_ADDED) {
                this.changeMap.put(childKey, Change.childAddedChange(childKey, change.getIndexedNode()));
                return;
            } else if (type == Event.EventType.CHILD_CHANGED && oldType == Event.EventType.CHILD_CHANGED) {
                this.changeMap.put(childKey, Change.childChangedChange(childKey, change.getIndexedNode(), oldChange.getOldIndexedNode()));
                return;
            } else {
                throw new IllegalStateException("Illegal combination of changes: " + change + " occurred after " + oldChange);
            }
        }
        this.changeMap.put(change.getChildKey(), change);
    }

    public List<Change> getChanges() {
        return new ArrayList(this.changeMap.values());
    }
}
