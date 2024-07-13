package com.google.firebase.components;

import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import com.google.firebase.inject.Provider;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class Lazy<T> implements Provider<T> {
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance;
    private volatile Provider<T> provider;

    Lazy(T instance) {
        this.instance = UNINITIALIZED;
        this.instance = instance;
    }

    public Lazy(Provider<T> provider) {
        this.instance = UNINITIALIZED;
        this.provider = provider;
    }

    @Override // com.google.firebase.inject.Provider
    public T get() {
        Object result = this.instance;
        if (result == UNINITIALIZED) {
            synchronized (this) {
                result = this.instance;
                if (result == UNINITIALIZED) {
                    result = this.provider.get();
                    this.instance = result;
                    this.provider = null;
                }
            }
        }
        T tResult = (T) result;
        return tResult;
    }

    @VisibleForTesting
    boolean isInitialized() {
        return this.instance != UNINITIALIZED;
    }
}
