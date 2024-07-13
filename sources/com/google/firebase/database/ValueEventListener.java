package com.google.firebase.database;

import androidx.annotation.NonNull;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public interface ValueEventListener {
    void onCancelled(@NonNull DatabaseError databaseError);

    void onDataChange(@NonNull DataSnapshot dataSnapshot);
}
