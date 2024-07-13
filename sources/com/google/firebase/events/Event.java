package com.google.firebase.events;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class Event<T> {
    private final T payload;
    private final Class<T> type;

    @KeepForSdk
    public Event(Class<T> type, T payload) {
        this.type = (Class) Preconditions.checkNotNull(type);
        this.payload = (T) Preconditions.checkNotNull(payload);
    }

    @KeepForSdk
    public Class<T> getType() {
        return this.type;
    }

    @KeepForSdk
    public T getPayload() {
        return this.payload;
    }

    public String toString() {
        return String.format("Event{type: %s, payload: %s}", this.type, this.payload);
    }
}
