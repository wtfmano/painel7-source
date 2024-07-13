package com.google.firebase.components;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.VisibleForTesting;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
/* loaded from: classes.dex */
public final class ComponentDiscovery<T> {
    private static final String COMPONENT_KEY_PREFIX = "com.google.firebase.components:";
    private static final String COMPONENT_SENTINEL_VALUE = "com.google.firebase.components.ComponentRegistrar";
    private static final String TAG = "ComponentDiscovery";
    private final T context;
    private final RegistrarNameRetriever<T> retriever;

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public interface RegistrarNameRetriever<T> {
        List<String> retrieve(T t);
    }

    public static ComponentDiscovery<Context> forContext(Context context) {
        return new ComponentDiscovery<>(context, new MetadataRegistrarNameRetriever());
    }

    @VisibleForTesting
    ComponentDiscovery(T context, RegistrarNameRetriever<T> retriever) {
        this.context = context;
        this.retriever = retriever;
    }

    public List<ComponentRegistrar> discover() {
        return instantiate(this.retriever.retrieve(this.context));
    }

    private static List<ComponentRegistrar> instantiate(List<String> registrarNames) {
        List<ComponentRegistrar> registrars = new ArrayList<>();
        for (String name : registrarNames) {
            try {
                Class<?> loadedClass = Class.forName(name);
                if (!ComponentRegistrar.class.isAssignableFrom(loadedClass)) {
                    Log.w(TAG, String.format("Class %s is not an instance of %s", name, COMPONENT_SENTINEL_VALUE));
                } else {
                    registrars.add((ComponentRegistrar) loadedClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                }
            } catch (ClassNotFoundException e) {
                Log.w(TAG, String.format("Class %s is not an found.", name), e);
            } catch (IllegalAccessException e2) {
                Log.w(TAG, String.format("Could not instantiate %s.", name), e2);
            } catch (InstantiationException e3) {
                Log.w(TAG, String.format("Could not instantiate %s.", name), e3);
            } catch (NoSuchMethodException e4) {
                Log.w(TAG, String.format("Could not instantiate %s", name), e4);
            } catch (InvocationTargetException e5) {
                Log.w(TAG, String.format("Could not instantiate %s", name), e5);
            }
        }
        return registrars;
    }

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    /* loaded from: classes.dex */
    public static class MetadataRegistrarNameRetriever implements RegistrarNameRetriever<Context> {
        private MetadataRegistrarNameRetriever() {
        }

        @Override // com.google.firebase.components.ComponentDiscovery.RegistrarNameRetriever
        public List<String> retrieve(Context ctx) {
            Bundle metadata = getMetadata(ctx);
            if (metadata == null) {
                Log.w(ComponentDiscovery.TAG, "Could not retrieve metadata, returning empty list of registrars.");
                return Collections.emptyList();
            }
            List<String> registrarNames = new ArrayList<>();
            for (String key : metadata.keySet()) {
                Object rawValue = metadata.get(key);
                if (ComponentDiscovery.COMPONENT_SENTINEL_VALUE.equals(rawValue) && key.startsWith(ComponentDiscovery.COMPONENT_KEY_PREFIX)) {
                    registrarNames.add(key.substring(ComponentDiscovery.COMPONENT_KEY_PREFIX.length()));
                }
            }
            return registrarNames;
        }

        private static Bundle getMetadata(Context context) {
            Bundle bundle = null;
            try {
                PackageManager manager = context.getPackageManager();
                if (manager == null) {
                    Log.w(ComponentDiscovery.TAG, "Context has no PackageManager.");
                } else {
                    ServiceInfo info = manager.getServiceInfo(new ComponentName(context, ComponentDiscoveryService.class), 128);
                    if (info == null) {
                        Log.w(ComponentDiscovery.TAG, "ComponentDiscoveryService has no service info.");
                    } else {
                        bundle = info.metaData;
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.w(ComponentDiscovery.TAG, "Application info not found.");
            }
            return bundle;
        }
    }
}
