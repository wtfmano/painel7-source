package com.google.firebase.platforminfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
/* loaded from: classes.dex */
public class GlobalLibraryVersionRegistrar {
    private static volatile GlobalLibraryVersionRegistrar INSTANCE;
    private final Set<LibraryVersion> infos = new HashSet();

    GlobalLibraryVersionRegistrar() {
    }

    public void registerVersion(String sdkName, String version) {
        synchronized (this.infos) {
            this.infos.add(LibraryVersion.create(sdkName, version));
        }
    }

    public Set<LibraryVersion> getRegisteredVersions() {
        Set<LibraryVersion> unmodifiableSet;
        synchronized (this.infos) {
            unmodifiableSet = Collections.unmodifiableSet(this.infos);
        }
        return unmodifiableSet;
    }

    public static GlobalLibraryVersionRegistrar getInstance() {
        GlobalLibraryVersionRegistrar localRef = INSTANCE;
        if (localRef == null) {
            synchronized (GlobalLibraryVersionRegistrar.class) {
                try {
                    localRef = INSTANCE;
                    if (localRef == null) {
                        GlobalLibraryVersionRegistrar localRef2 = new GlobalLibraryVersionRegistrar();
                        try {
                            INSTANCE = localRef2;
                            localRef = localRef2;
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }
        return localRef;
    }
}
