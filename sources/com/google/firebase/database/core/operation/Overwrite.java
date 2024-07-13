package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class Overwrite extends Operation {
    private final Node snapshot;

    public Overwrite(OperationSource source, Path path, Node snapshot) {
        super(Operation.OperationType.Overwrite, source, path);
        this.snapshot = snapshot;
    }

    public Node getSnapshot() {
        return this.snapshot;
    }

    @Override // com.google.firebase.database.core.operation.Operation
    public Operation operationForChild(ChildKey childKey) {
        return this.path.isEmpty() ? new Overwrite(this.source, Path.getEmptyPath(), this.snapshot.getImmediateChild(childKey)) : new Overwrite(this.source, this.path.popFront(), this.snapshot);
    }

    public String toString() {
        return String.format("Overwrite { path=%s, source=%s, snapshot=%s }", getPath(), getSource(), this.snapshot);
    }
}
