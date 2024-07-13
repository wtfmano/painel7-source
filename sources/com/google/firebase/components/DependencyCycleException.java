package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Arrays;
import java.util.List;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class DependencyCycleException extends DependencyException {
    private final List<Component<?>> componentsInCycle;

    @KeepForSdk
    public DependencyCycleException(List<Component<?>> componentsInCycle) {
        super("Dependency cycle detected: " + Arrays.toString(componentsInCycle.toArray()));
        this.componentsInCycle = componentsInCycle;
    }

    @KeepForSdk
    public List<Component<?>> getComponentsInCycle() {
        return this.componentsInCycle;
    }
}
