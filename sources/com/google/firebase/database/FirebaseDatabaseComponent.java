package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.database.android.AndroidAuthTokenProvider;
import com.google.firebase.database.core.AuthTokenProvider;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.RepoInfo;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class FirebaseDatabaseComponent {
    private final FirebaseApp app;
    private final AuthTokenProvider authProvider;
    private final Map<RepoInfo, FirebaseDatabase> instances = new HashMap();

    public FirebaseDatabaseComponent(@NonNull FirebaseApp app, @Nullable InternalAuthProvider authProvider) {
        this.app = app;
        if (authProvider != null) {
            this.authProvider = AndroidAuthTokenProvider.forAuthenticatedAccess(authProvider);
        } else {
            this.authProvider = AndroidAuthTokenProvider.forUnauthenticatedAccess();
        }
    }

    @NonNull
    public synchronized FirebaseDatabase get(RepoInfo repo) {
        FirebaseDatabase database;
        database = this.instances.get(repo);
        if (database == null) {
            DatabaseConfig config = new DatabaseConfig();
            if (!this.app.isDefaultApp()) {
                config.setSessionPersistenceKey(this.app.getName());
            }
            config.setFirebaseApp(this.app);
            config.setAuthTokenProvider(this.authProvider);
            database = new FirebaseDatabase(this.app, repo, config);
            this.instances.put(repo, database);
        }
        return database;
    }
}
