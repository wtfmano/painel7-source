package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class Transaction {

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public interface Handler {
        @NonNull
        Result doTransaction(@NonNull MutableData mutableData);

        void onComplete(@Nullable DatabaseError databaseError, boolean z, @Nullable DataSnapshot dataSnapshot);
    }

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public static class Result {
        private Node data;
        private boolean success;

        private Result(boolean success, Node data) {
            this.success = success;
            this.data = data;
        }

        public boolean isSuccess() {
            return this.success;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Node getNode() {
            return this.data;
        }
    }

    @NonNull
    public static Result abort() {
        return new Result(false, null);
    }

    @NonNull
    public static Result success(@NonNull MutableData resultData) {
        return new Result(true, resultData.getNode());
    }
}
