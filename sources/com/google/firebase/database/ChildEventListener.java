package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public interface ChildEventListener {
    void onCancelled(@NonNull DatabaseError databaseError);

    void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String str);

    void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String str);

    void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String str);

    void onChildRemoved(@NonNull DataSnapshot dataSnapshot);
}
