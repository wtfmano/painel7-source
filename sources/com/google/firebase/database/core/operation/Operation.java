package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public abstract class Operation {
    protected final Path path;
    protected final OperationSource source;
    protected final OperationType type;

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public enum OperationType {
        Overwrite,
        Merge,
        AckUserWrite,
        ListenComplete
    }

    public abstract Operation operationForChild(ChildKey childKey);

    public Operation(OperationType type, OperationSource source, Path path) {
        this.type = type;
        this.source = source;
        this.path = path;
    }

    public Path getPath() {
        return this.path;
    }

    public OperationSource getSource() {
        return this.source;
    }

    public OperationType getType() {
        return this.type;
    }
}
