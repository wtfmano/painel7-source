package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class OnDisconnect {
    private Path path;
    private Repo repo;

    /* JADX INFO: Access modifiers changed from: package-private */
    public OnDisconnect(Repo repo, Path path) {
        this.repo = repo;
        this.path = path;
    }

    @NonNull
    public Task<Void> setValue(@Nullable Object value) {
        return onDisconnectSetInternal(value, PriorityUtilities.NullPriority(), null);
    }

    @NonNull
    public Task<Void> setValue(@Nullable Object value, @Nullable String priority) {
        return onDisconnectSetInternal(value, PriorityUtilities.parsePriority(this.path, priority), null);
    }

    @NonNull
    public Task<Void> setValue(@Nullable Object value, double priority) {
        return onDisconnectSetInternal(value, PriorityUtilities.parsePriority(this.path, Double.valueOf(priority)), null);
    }

    public void setValue(@Nullable Object value, @Nullable DatabaseReference.CompletionListener listener) {
        onDisconnectSetInternal(value, PriorityUtilities.NullPriority(), listener);
    }

    public void setValue(@Nullable Object value, @Nullable String priority, @Nullable DatabaseReference.CompletionListener listener) {
        onDisconnectSetInternal(value, PriorityUtilities.parsePriority(this.path, priority), listener);
    }

    public void setValue(@Nullable Object value, double priority, @Nullable DatabaseReference.CompletionListener listener) {
        onDisconnectSetInternal(value, PriorityUtilities.parsePriority(this.path, Double.valueOf(priority)), listener);
    }

    public void setValue(@Nullable Object value, @Nullable Map priority, @Nullable DatabaseReference.CompletionListener listener) {
        onDisconnectSetInternal(value, PriorityUtilities.parsePriority(this.path, priority), listener);
    }

    private Task<Void> onDisconnectSetInternal(Object value, Node priority, DatabaseReference.CompletionListener optListener) {
        Validation.validateWritablePath(this.path);
        ValidationPath.validateWithObject(this.path, value);
        Object bouncedValue = CustomClassMapper.convertToPlainJavaTypes(value);
        Validation.validateWritableObject(bouncedValue);
        final Node node = NodeUtilities.NodeFromJSON(bouncedValue, priority);
        final Pair<Task<Void>, DatabaseReference.CompletionListener> wrapped = Utilities.wrapOnComplete(optListener);
        this.repo.scheduleNow(new Runnable() { // from class: com.google.firebase.database.OnDisconnect.1
            @Override // java.lang.Runnable
            public void run() {
                OnDisconnect.this.repo.onDisconnectSetValue(OnDisconnect.this.path, node, (DatabaseReference.CompletionListener) wrapped.getSecond());
            }
        });
        return wrapped.getFirst();
    }

    @NonNull
    public Task<Void> updateChildren(@NonNull Map<String, Object> update) {
        return updateChildrenInternal(update, null);
    }

    public void updateChildren(@NonNull Map<String, Object> update, @Nullable DatabaseReference.CompletionListener listener) {
        updateChildrenInternal(update, listener);
    }

    private Task<Void> updateChildrenInternal(final Map<String, Object> update, DatabaseReference.CompletionListener optListener) {
        final Map<Path, Node> parsedUpdate = Validation.parseAndValidateUpdate(this.path, update);
        final Pair<Task<Void>, DatabaseReference.CompletionListener> wrapped = Utilities.wrapOnComplete(optListener);
        this.repo.scheduleNow(new Runnable() { // from class: com.google.firebase.database.OnDisconnect.2
            @Override // java.lang.Runnable
            public void run() {
                OnDisconnect.this.repo.onDisconnectUpdate(OnDisconnect.this.path, parsedUpdate, (DatabaseReference.CompletionListener) wrapped.getSecond(), update);
            }
        });
        return wrapped.getFirst();
    }

    @NonNull
    public Task<Void> removeValue() {
        return setValue(null);
    }

    public void removeValue(@Nullable DatabaseReference.CompletionListener listener) {
        setValue((Object) null, listener);
    }

    @NonNull
    public Task<Void> cancel() {
        return cancelInternal(null);
    }

    public void cancel(@NonNull DatabaseReference.CompletionListener listener) {
        cancelInternal(listener);
    }

    private Task<Void> cancelInternal(DatabaseReference.CompletionListener optListener) {
        final Pair<Task<Void>, DatabaseReference.CompletionListener> wrapped = Utilities.wrapOnComplete(optListener);
        this.repo.scheduleNow(new Runnable() { // from class: com.google.firebase.database.OnDisconnect.3
            @Override // java.lang.Runnable
            public void run() {
                OnDisconnect.this.repo.onDisconnectCancel(OnDisconnect.this.path, (DatabaseReference.CompletionListener) wrapped.getSecond());
            }
        });
        return wrapped.getFirst();
    }
}
