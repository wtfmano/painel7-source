package com.google.firebase.database;

import androidx.annotation.NonNull;
import com.google.firebase.database.core.ServerValues;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class ServerValue {
    @NonNull
    public static final Map<String, String> TIMESTAMP = createServerValuePlaceholder("timestamp");

    private static Map<String, String> createServerValuePlaceholder(String key) {
        Map<String, String> result = new HashMap<>();
        result.put(ServerValues.NAME_SUBKEY_SERVERVALUE, key);
        return Collections.unmodifiableMap(result);
    }
}
