package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
@KeepForSdk
/* loaded from: classes.dex */
public final class Dependency {
    private final Class<?> anInterface;
    private final int injection;
    private final int type;

    private Dependency(Class<?> anInterface, int type, int injection) {
        this.anInterface = (Class) Preconditions.checkNotNull(anInterface, "Null dependency anInterface.");
        this.type = type;
        this.injection = injection;
    }

    @KeepForSdk
    public static Dependency optional(Class<?> anInterface) {
        return new Dependency(anInterface, 0, 0);
    }

    @KeepForSdk
    public static Dependency required(Class<?> anInterface) {
        return new Dependency(anInterface, 1, 0);
    }

    @KeepForSdk
    public static Dependency setOf(Class<?> anInterface) {
        return new Dependency(anInterface, 2, 0);
    }

    @KeepForSdk
    public static Dependency optionalProvider(Class<?> anInterface) {
        return new Dependency(anInterface, 0, 1);
    }

    @KeepForSdk
    public static Dependency requiredProvider(Class<?> anInterface) {
        return new Dependency(anInterface, 1, 1);
    }

    @KeepForSdk
    public static Dependency setOfProvider(Class<?> anInterface) {
        return new Dependency(anInterface, 2, 1);
    }

    public Class<?> getInterface() {
        return this.anInterface;
    }

    public boolean isRequired() {
        return this.type == 1;
    }

    public boolean isSet() {
        return this.type == 2;
    }

    public boolean isDirectInjection() {
        return this.injection == 0;
    }

    public boolean equals(Object o) {
        if (o instanceof Dependency) {
            Dependency other = (Dependency) o;
            return this.anInterface == other.anInterface && this.type == other.type && this.injection == other.injection;
        }
        return false;
    }

    public int hashCode() {
        int h = 1000003 ^ this.anInterface.hashCode();
        return (((h * 1000003) ^ this.type) * 1000003) ^ this.injection;
    }

    public String toString() {
        String str;
        StringBuilder append = new StringBuilder("Dependency{anInterface=").append(this.anInterface).append(", type=");
        if (this.type == 1) {
            str = "required";
        } else {
            str = this.type == 0 ? "optional" : "set";
        }
        StringBuilder sb = append.append(str).append(", direct=").append(this.injection == 0).append("}");
        return sb.toString();
    }
}
