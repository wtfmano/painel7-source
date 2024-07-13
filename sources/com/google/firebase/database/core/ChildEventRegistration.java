package com.google.firebase.database.core;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.database.core.view.QuerySpec;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class ChildEventRegistration extends EventRegistration {
    private final ChildEventListener eventListener;
    private final Repo repo;
    private final QuerySpec spec;

    public ChildEventRegistration(@NotNull Repo repo, @NotNull ChildEventListener eventListener, @NotNull QuerySpec spec) {
        this.repo = repo;
        this.eventListener = eventListener;
        this.spec = spec;
    }

    @Override // com.google.firebase.database.core.EventRegistration
    public boolean respondsTo(Event.EventType eventType) {
        return eventType != Event.EventType.VALUE;
    }

    public boolean equals(Object other) {
        return (other instanceof ChildEventRegistration) && ((ChildEventRegistration) other).eventListener.equals(this.eventListener) && ((ChildEventRegistration) other).repo.equals(this.repo) && ((ChildEventRegistration) other).spec.equals(this.spec);
    }

    public int hashCode() {
        int result = this.eventListener.hashCode();
        return (((result * 31) + this.repo.hashCode()) * 31) + this.spec.hashCode();
    }

    @Override // com.google.firebase.database.core.EventRegistration
    public DataEvent createEvent(Change change, QuerySpec query) {
        DatabaseReference ref = InternalHelpers.createReference(this.repo, query.getPath().child(change.getChildKey()));
        DataSnapshot snapshot = InternalHelpers.createDataSnapshot(ref, change.getIndexedNode());
        String prevName = change.getPrevName() != null ? change.getPrevName().asString() : null;
        return new DataEvent(change.getEventType(), this, snapshot, prevName);
    }

    @Override // com.google.firebase.database.core.EventRegistration
    public void fireEvent(DataEvent eventData) {
        if (!isZombied()) {
            switch (eventData.getEventType()) {
                case CHILD_ADDED:
                    this.eventListener.onChildAdded(eventData.getSnapshot(), eventData.getPreviousName());
                    return;
                case CHILD_CHANGED:
                    this.eventListener.onChildChanged(eventData.getSnapshot(), eventData.getPreviousName());
                    return;
                case CHILD_MOVED:
                    this.eventListener.onChildMoved(eventData.getSnapshot(), eventData.getPreviousName());
                    return;
                case CHILD_REMOVED:
                    this.eventListener.onChildRemoved(eventData.getSnapshot());
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.google.firebase.database.core.EventRegistration
    public void fireCancelEvent(DatabaseError error) {
        this.eventListener.onCancelled(error);
    }

    @Override // com.google.firebase.database.core.EventRegistration
    public EventRegistration clone(QuerySpec newQuery) {
        return new ChildEventRegistration(this.repo, this.eventListener, newQuery);
    }

    @Override // com.google.firebase.database.core.EventRegistration
    public boolean isSameListener(EventRegistration other) {
        return (other instanceof ChildEventRegistration) && ((ChildEventRegistration) other).eventListener.equals(this.eventListener);
    }

    @Override // com.google.firebase.database.core.EventRegistration
    @NotNull
    public QuerySpec getQuerySpec() {
        return this.spec;
    }

    public String toString() {
        return "ChildEventRegistration";
    }

    @Override // com.google.firebase.database.core.EventRegistration
    Repo getRepo() {
        return this.repo;
    }
}
