package com.google.firebase.components;

import androidx.annotation.GuardedBy;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Publisher;
import com.google.firebase.events.Subscriber;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
/* loaded from: classes.dex */
public class EventBus implements Subscriber, Publisher {
    private final Executor defaultExecutor;
    @GuardedBy("this")
    private final Map<Class<?>, ConcurrentHashMap<EventHandler<Object>, Executor>> handlerMap = new HashMap();
    @GuardedBy("this")
    private Queue<Event<?>> pendingEvents = new ArrayDeque();

    public EventBus(Executor defaultExecutor) {
        this.defaultExecutor = defaultExecutor;
    }

    @Override // com.google.firebase.events.Publisher
    public void publish(Event<?> event) {
        Preconditions.checkNotNull(event);
        synchronized (this) {
            if (this.pendingEvents != null) {
                this.pendingEvents.add(event);
                return;
            }
            for (Map.Entry<EventHandler<Object>, Executor> handlerData : getHandlers(event)) {
                handlerData.getValue().execute(EventBus$$Lambda$1.lambdaFactory$(handlerData, event));
            }
        }
    }

    private synchronized Set<Map.Entry<EventHandler<Object>, Executor>> getHandlers(Event<?> event) {
        Map<EventHandler<Object>, Executor> handlers;
        handlers = this.handlerMap.get(event.getType());
        return handlers == null ? Collections.emptySet() : handlers.entrySet();
    }

    @Override // com.google.firebase.events.Subscriber
    public synchronized <T> void subscribe(Class<T> type, Executor executor, EventHandler<? super T> handler) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(handler);
        Preconditions.checkNotNull(executor);
        if (!this.handlerMap.containsKey(type)) {
            this.handlerMap.put(type, new ConcurrentHashMap<>());
        }
        this.handlerMap.get(type).put(handler, executor);
    }

    @Override // com.google.firebase.events.Subscriber
    public <T> void subscribe(Class<T> type, EventHandler<? super T> handler) {
        subscribe(type, this.defaultExecutor, handler);
    }

    @Override // com.google.firebase.events.Subscriber
    public synchronized <T> void unsubscribe(Class<T> type, EventHandler<? super T> handler) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(handler);
        if (this.handlerMap.containsKey(type)) {
            ConcurrentHashMap<EventHandler<Object>, Executor> handlers = this.handlerMap.get(type);
            handlers.remove(handler);
            if (handlers.isEmpty()) {
                this.handlerMap.remove(type);
            }
        }
    }

    public void enablePublishingAndFlushPending() {
        Queue<Event<?>> pending = null;
        synchronized (this) {
            if (this.pendingEvents != null) {
                pending = this.pendingEvents;
                this.pendingEvents = null;
            }
        }
        if (pending != null) {
            for (Event<?> event : pending) {
                publish(event);
            }
        }
    }
}
