package com.google.firebase.database;

import androidx.annotation.RestrictTo;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class DatabaseException extends RuntimeException {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public DatabaseException(String message) {
        super(message);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
