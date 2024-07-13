package com.google.firebase.database;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.Logger;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.RepoInfo;
import com.google.firebase.database.core.RepoManager;
import com.google.firebase.database.core.utilities.ParsedUrl;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class FirebaseDatabase {
    private static final String SDK_VERSION = "19.0.0";
    private final FirebaseApp app;
    private final DatabaseConfig config;
    private Repo repo;
    private final RepoInfo repoInfo;

    @NonNull
    public static FirebaseDatabase getInstance() {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance == null) {
            throw new DatabaseException("You must call FirebaseApp.initialize() first.");
        }
        return getInstance(instance, instance.getOptions().getDatabaseUrl());
    }

    @NonNull
    public static FirebaseDatabase getInstance(@NonNull String url) {
        FirebaseApp instance = FirebaseApp.getInstance();
        if (instance == null) {
            throw new DatabaseException("You must call FirebaseApp.initialize() first.");
        }
        return getInstance(instance, url);
    }

    @NonNull
    public static FirebaseDatabase getInstance(@NonNull FirebaseApp app) {
        return getInstance(app, app.getOptions().getDatabaseUrl());
    }

    @NonNull
    public static synchronized FirebaseDatabase getInstance(@NonNull FirebaseApp app, @NonNull String url) {
        FirebaseDatabase firebaseDatabase;
        synchronized (FirebaseDatabase.class) {
            if (TextUtils.isEmpty(url)) {
                throw new DatabaseException("Failed to get FirebaseDatabase instance: Specify DatabaseURL within FirebaseApp or from your getInstance() call.");
            }
            ParsedUrl parsedUrl = Utilities.parseUrl(url);
            if (!parsedUrl.path.isEmpty()) {
                throw new DatabaseException("Specified Database URL '" + url + "' is invalid. It should point to the root of a Firebase Database but it includes a path: " + parsedUrl.path.toString());
            }
            Preconditions.checkNotNull(app, "Provided FirebaseApp must not be null.");
            FirebaseDatabaseComponent component = (FirebaseDatabaseComponent) app.get(FirebaseDatabaseComponent.class);
            Preconditions.checkNotNull(component, "Firebase Database component is not present.");
            firebaseDatabase = component.get(parsedUrl.repoInfo);
        }
        return firebaseDatabase;
    }

    public static FirebaseDatabase createForTests(FirebaseApp app, RepoInfo repoInfo, DatabaseConfig config) {
        FirebaseDatabase db = new FirebaseDatabase(app, repoInfo, config);
        db.ensureRepo();
        return db;
    }

    public FirebaseDatabase(FirebaseApp app, RepoInfo repoInfo, DatabaseConfig config) {
        this.app = app;
        this.repoInfo = repoInfo;
        this.config = config;
    }

    @NonNull
    public FirebaseApp getApp() {
        return this.app;
    }

    @NonNull
    public DatabaseReference getReference() {
        ensureRepo();
        return new DatabaseReference(this.repo, Path.getEmptyPath());
    }

    @NonNull
    public DatabaseReference getReference(@NonNull String path) {
        ensureRepo();
        if (path == null) {
            throw new NullPointerException("Can't pass null for argument 'pathString' in FirebaseDatabase.getReference()");
        }
        Validation.validateRootPathString(path);
        Path childPath = new Path(path);
        return new DatabaseReference(this.repo, childPath);
    }

    @NonNull
    public DatabaseReference getReferenceFromUrl(@NonNull String url) {
        ensureRepo();
        if (url == null) {
            throw new NullPointerException("Can't pass null for argument 'url' in FirebaseDatabase.getReferenceFromUrl()");
        }
        ParsedUrl parsedUrl = Utilities.parseUrl(url);
        if (!parsedUrl.repoInfo.host.equals(this.repo.getRepoInfo().host)) {
            throw new DatabaseException("Invalid URL (" + url + ") passed to getReference().  URL was expected to match configured Database URL: " + getReference().toString());
        }
        return new DatabaseReference(this.repo, parsedUrl.path);
    }

    public void purgeOutstandingWrites() {
        ensureRepo();
        this.repo.scheduleNow(new Runnable() { // from class: com.google.firebase.database.FirebaseDatabase.1
            @Override // java.lang.Runnable
            public void run() {
                FirebaseDatabase.this.repo.purgeOutstandingWrites();
            }
        });
    }

    public void goOnline() {
        ensureRepo();
        RepoManager.resume(this.repo);
    }

    public void goOffline() {
        ensureRepo();
        RepoManager.interrupt(this.repo);
    }

    public synchronized void setLogLevel(@NonNull Logger.Level logLevel) {
        assertUnfrozen("setLogLevel");
        this.config.setLogLevel(logLevel);
    }

    public synchronized void setPersistenceEnabled(boolean isEnabled) {
        assertUnfrozen("setPersistenceEnabled");
        this.config.setPersistenceEnabled(isEnabled);
    }

    public synchronized void setPersistenceCacheSizeBytes(long cacheSizeInBytes) {
        assertUnfrozen("setPersistenceCacheSizeBytes");
        this.config.setPersistenceCacheSizeBytes(cacheSizeInBytes);
    }

    @NonNull
    public static String getSdkVersion() {
        return "19.0.0";
    }

    private void assertUnfrozen(String methodCalled) {
        if (this.repo != null) {
            throw new DatabaseException("Calls to " + methodCalled + "() must be made before any other usage of FirebaseDatabase instance.");
        }
    }

    private synchronized void ensureRepo() {
        if (this.repo == null) {
            this.repo = RepoManager.createRepo(this.config, this.repoInfo, this);
        }
    }

    DatabaseConfig getConfig() {
        return this.config;
    }
}
