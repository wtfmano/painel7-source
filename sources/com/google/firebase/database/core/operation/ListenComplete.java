package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.snapshot.ChildKey;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class ListenComplete extends Operation {
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ListenComplete.class.desiredAssertionStatus();
    }

    public ListenComplete(OperationSource source, Path path) {
        super(Operation.OperationType.ListenComplete, source, path);
        if (!$assertionsDisabled && source.isFromUser()) {
            throw new AssertionError("Can't have a listen complete from a user source");
        }
    }

    @Override // com.google.firebase.database.core.operation.Operation
    public Operation operationForChild(ChildKey childKey) {
        return this.path.isEmpty() ? new ListenComplete(this.source, Path.getEmptyPath()) : new ListenComplete(this.source, this.path.popFront());
    }

    public String toString() {
        return String.format("ListenComplete { path=%s, source=%s }", getPath(), getSource());
    }
}
