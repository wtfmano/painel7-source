package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.database.core.view.QuerySpec;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public abstract class EventRegistration {
    static final /* synthetic */ boolean $assertionsDisabled;
    private EventRegistrationZombieListener listener;
    private AtomicBoolean zombied = new AtomicBoolean(false);
    private boolean isUserInitiated = false;

    public abstract EventRegistration clone(QuerySpec querySpec);

    public abstract DataEvent createEvent(Change change, QuerySpec querySpec);

    public abstract void fireCancelEvent(DatabaseError databaseError);

    public abstract void fireEvent(DataEvent dataEvent);

    @NotNull
    public abstract QuerySpec getQuerySpec();

    public abstract boolean isSameListener(EventRegistration eventRegistration);

    public abstract boolean respondsTo(Event.EventType eventType);

    static {
        $assertionsDisabled = !EventRegistration.class.desiredAssertionStatus();
    }

    public void zombify() {
        if (this.zombied.compareAndSet(false, true) && this.listener != null) {
            this.listener.onZombied(this);
            this.listener = null;
        }
    }

    public boolean isZombied() {
        return this.zombied.get();
    }

    public void setOnZombied(EventRegistrationZombieListener listener) {
        if (!$assertionsDisabled && isZombied()) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.listener != null) {
            throw new AssertionError();
        }
        this.listener = listener;
    }

    public boolean isUserInitiated() {
        return this.isUserInitiated;
    }

    public void setIsUserInitiated(boolean isUserInitiated) {
        this.isUserInitiated = isUserInitiated;
    }

    Repo getRepo() {
        return null;
    }
}
