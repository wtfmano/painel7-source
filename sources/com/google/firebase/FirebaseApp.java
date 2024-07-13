package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.collection.ArrayMap;
import androidx.core.os.UserManagerCompat;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentDiscovery;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.ComponentRuntime;
import com.google.firebase.components.Lazy;
import com.google.firebase.events.Publisher;
import com.google.firebase.internal.DataCollectionConfigStorage;
import com.google.firebase.platforminfo.DefaultUserAgentPublisher;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
/* loaded from: classes.dex */
public class FirebaseApp {
    @NonNull
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    private static final String FIREBASE_ANDROID = "fire-android";
    private static final String FIREBASE_COMMON = "fire-core";
    private static final String LOG_TAG = "FirebaseApp";
    private final Context applicationContext;
    private final ComponentRuntime componentRuntime;
    private final Lazy<DataCollectionConfigStorage> dataCollectionConfigStorage;
    private final String name;
    private final FirebaseOptions options;
    private static final Object LOCK = new Object();
    private static final Executor UI_EXECUTOR = new UiExecutor();
    @GuardedBy("LOCK")
    static final Map<String, FirebaseApp> INSTANCES = new ArrayMap();
    private final AtomicBoolean automaticResourceManagementEnabled = new AtomicBoolean(false);
    private final AtomicBoolean deleted = new AtomicBoolean();
    private final List<BackgroundStateChangeListener> backgroundStateChangeListeners = new CopyOnWriteArrayList();
    private final List<FirebaseAppLifecycleListener> lifecycleListeners = new CopyOnWriteArrayList();

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    @KeepForSdk
    /* loaded from: classes.dex */
    public interface BackgroundStateChangeListener {
        @KeepForSdk
        void onBackgroundStateChanged(boolean z);
    }

    @NonNull
    public Context getApplicationContext() {
        checkNotDeleted();
        return this.applicationContext;
    }

    @NonNull
    public String getName() {
        checkNotDeleted();
        return this.name;
    }

    @NonNull
    public FirebaseOptions getOptions() {
        checkNotDeleted();
        return this.options;
    }

    public boolean equals(Object o) {
        if (o instanceof FirebaseApp) {
            return this.name.equals(((FirebaseApp) o).getName());
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return Objects.toStringHelper(this).add("name", this.name).add("options", this.options).toString();
    }

    @NonNull
    public static List<FirebaseApp> getApps(@NonNull Context context) {
        ArrayList arrayList;
        synchronized (LOCK) {
            arrayList = new ArrayList(INSTANCES.values());
        }
        return arrayList;
    }

    @NonNull
    public static FirebaseApp getInstance() {
        FirebaseApp defaultApp;
        synchronized (LOCK) {
            defaultApp = INSTANCES.get(DEFAULT_APP_NAME);
            if (defaultApp == null) {
                throw new IllegalStateException("Default FirebaseApp is not initialized in this process " + ProcessUtils.getMyProcessName() + ". Make sure to call FirebaseApp.initializeApp(Context) first.");
            }
        }
        return defaultApp;
    }

    @NonNull
    public static FirebaseApp getInstance(@NonNull String name) {
        FirebaseApp firebaseApp;
        String availableAppNamesMessage;
        synchronized (LOCK) {
            firebaseApp = INSTANCES.get(normalize(name));
            if (firebaseApp == null) {
                List<String> availableAppNames = getAllAppNames();
                if (availableAppNames.isEmpty()) {
                    availableAppNamesMessage = "";
                } else {
                    availableAppNamesMessage = "Available app names: " + TextUtils.join(", ", availableAppNames);
                }
                String errorMessage = String.format("FirebaseApp with name %s doesn't exist. %s", name, availableAppNamesMessage);
                throw new IllegalStateException(errorMessage);
            }
        }
        return firebaseApp;
    }

    @Nullable
    public static FirebaseApp initializeApp(@NonNull Context context) {
        FirebaseApp initializeApp;
        synchronized (LOCK) {
            if (INSTANCES.containsKey(DEFAULT_APP_NAME)) {
                initializeApp = getInstance();
            } else {
                FirebaseOptions firebaseOptions = FirebaseOptions.fromResource(context);
                if (firebaseOptions == null) {
                    Log.w(LOG_TAG, "Default FirebaseApp failed to initialize because no default options were found. This usually means that com.google.gms:google-services was not applied to your gradle project.");
                    initializeApp = null;
                } else {
                    initializeApp = initializeApp(context, firebaseOptions);
                }
            }
        }
        return initializeApp;
    }

    @NonNull
    public static FirebaseApp initializeApp(@NonNull Context context, @NonNull FirebaseOptions options) {
        return initializeApp(context, options, DEFAULT_APP_NAME);
    }

    @NonNull
    public static FirebaseApp initializeApp(@NonNull Context context, @NonNull FirebaseOptions options, @NonNull String name) {
        Context applicationContext;
        FirebaseApp firebaseApp;
        GlobalBackgroundStateListener.ensureBackgroundStateListenerRegistered(context);
        String normalizedName = normalize(name);
        if (context.getApplicationContext() == null) {
            applicationContext = context;
        } else {
            applicationContext = context.getApplicationContext();
        }
        synchronized (LOCK) {
            Preconditions.checkState(!INSTANCES.containsKey(normalizedName), "FirebaseApp name " + normalizedName + " already exists!");
            Preconditions.checkNotNull(applicationContext, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(applicationContext, normalizedName, options);
            INSTANCES.put(normalizedName, firebaseApp);
        }
        firebaseApp.initializeAllApis();
        return firebaseApp;
    }

    public void delete() {
        boolean valueChanged = this.deleted.compareAndSet(false, true);
        if (valueChanged) {
            synchronized (LOCK) {
                INSTANCES.remove(this.name);
            }
            notifyOnAppDeleted();
        }
    }

    @KeepForSdk
    public <T> T get(Class<T> anInterface) {
        checkNotDeleted();
        return (T) this.componentRuntime.get(anInterface);
    }

    public void setAutomaticResourceManagementEnabled(boolean enabled) {
        checkNotDeleted();
        boolean updated = this.automaticResourceManagementEnabled.compareAndSet(!enabled, enabled);
        if (updated) {
            boolean inBackground = BackgroundDetector.getInstance().isInBackground();
            if (enabled && inBackground) {
                notifyBackgroundStateChangeListeners(true);
            } else if (!enabled && inBackground) {
                notifyBackgroundStateChangeListeners(false);
            }
        }
    }

    @KeepForSdk
    public boolean isDataCollectionDefaultEnabled() {
        checkNotDeleted();
        return this.dataCollectionConfigStorage.get().isEnabled();
    }

    @KeepForSdk
    public void setDataCollectionDefaultEnabled(boolean enabled) {
        checkNotDeleted();
        this.dataCollectionConfigStorage.get().setEnabled(enabled);
    }

    protected FirebaseApp(Context applicationContext, String name, FirebaseOptions options) {
        this.applicationContext = (Context) Preconditions.checkNotNull(applicationContext);
        this.name = Preconditions.checkNotEmpty(name);
        this.options = (FirebaseOptions) Preconditions.checkNotNull(options);
        List<ComponentRegistrar> registrars = ComponentDiscovery.forContext(applicationContext).discover();
        this.componentRuntime = new ComponentRuntime(UI_EXECUTOR, registrars, Component.of(applicationContext, Context.class, new Class[0]), Component.of(this, FirebaseApp.class, new Class[0]), Component.of(options, FirebaseOptions.class, new Class[0]), LibraryVersionComponent.create(FIREBASE_ANDROID, ""), LibraryVersionComponent.create(FIREBASE_COMMON, "19.0.0"), DefaultUserAgentPublisher.component());
        this.dataCollectionConfigStorage = new Lazy<>(FirebaseApp$$Lambda$1.lambdaFactory$(this, applicationContext));
    }

    public static /* synthetic */ DataCollectionConfigStorage lambda$new$0(FirebaseApp firebaseApp, Context applicationContext) {
        return new DataCollectionConfigStorage(applicationContext, firebaseApp.getPersistenceKey(), (Publisher) firebaseApp.componentRuntime.get(Publisher.class));
    }

    private void checkNotDeleted() {
        Preconditions.checkState(!this.deleted.get(), "FirebaseApp was deleted");
    }

    @KeepForSdk
    @VisibleForTesting
    public boolean isDefaultApp() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    public void notifyBackgroundStateChangeListeners(boolean background) {
        Log.d(LOG_TAG, "Notifying background state change listeners.");
        for (BackgroundStateChangeListener listener : this.backgroundStateChangeListeners) {
            listener.onBackgroundStateChanged(background);
        }
    }

    @KeepForSdk
    public void addBackgroundStateChangeListener(BackgroundStateChangeListener listener) {
        checkNotDeleted();
        if (this.automaticResourceManagementEnabled.get() && BackgroundDetector.getInstance().isInBackground()) {
            listener.onBackgroundStateChanged(true);
        }
        this.backgroundStateChangeListeners.add(listener);
    }

    @KeepForSdk
    public void removeBackgroundStateChangeListener(BackgroundStateChangeListener listener) {
        checkNotDeleted();
        this.backgroundStateChangeListeners.remove(listener);
    }

    @KeepForSdk
    public String getPersistenceKey() {
        return Base64Utils.encodeUrlSafeNoPadding(getName().getBytes(Charset.defaultCharset())) + "+" + Base64Utils.encodeUrlSafeNoPadding(getOptions().getApplicationId().getBytes(Charset.defaultCharset()));
    }

    @KeepForSdk
    public void addLifecycleEventListener(@NonNull FirebaseAppLifecycleListener listener) {
        checkNotDeleted();
        Preconditions.checkNotNull(listener);
        this.lifecycleListeners.add(listener);
    }

    @KeepForSdk
    public void removeLifecycleEventListener(@NonNull FirebaseAppLifecycleListener listener) {
        checkNotDeleted();
        Preconditions.checkNotNull(listener);
        this.lifecycleListeners.remove(listener);
    }

    private void notifyOnAppDeleted() {
        for (FirebaseAppLifecycleListener listener : this.lifecycleListeners) {
            listener.onDeleted(this.name, this.options);
        }
    }

    @VisibleForTesting
    public static void clearInstancesForTest() {
        synchronized (LOCK) {
            INSTANCES.clear();
        }
    }

    @KeepForSdk
    public static String getPersistenceKey(String name, FirebaseOptions options) {
        return Base64Utils.encodeUrlSafeNoPadding(name.getBytes(Charset.defaultCharset())) + "+" + Base64Utils.encodeUrlSafeNoPadding(options.getApplicationId().getBytes(Charset.defaultCharset()));
    }

    private static List<String> getAllAppNames() {
        List<String> allAppNames = new ArrayList<>();
        synchronized (LOCK) {
            for (FirebaseApp app : INSTANCES.values()) {
                allAppNames.add(app.getName());
            }
        }
        Collections.sort(allAppNames);
        return allAppNames;
    }

    public void initializeAllApis() {
        boolean inDirectBoot = !UserManagerCompat.isUserUnlocked(this.applicationContext);
        if (!inDirectBoot) {
            this.componentRuntime.initializeEagerComponents(isDefaultApp());
        } else {
            UserUnlockReceiver.ensureReceiverRegistered(this.applicationContext);
        }
    }

    private static String normalize(@NonNull String name) {
        return name.trim();
    }

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    @TargetApi(24)
    /* loaded from: classes.dex */
    public static class UserUnlockReceiver extends BroadcastReceiver {
        private static AtomicReference<UserUnlockReceiver> INSTANCE = new AtomicReference<>();
        private final Context applicationContext;

        public UserUnlockReceiver(Context applicationContext) {
            this.applicationContext = applicationContext;
        }

        public static void ensureReceiverRegistered(Context applicationContext) {
            if (INSTANCE.get() == null) {
                UserUnlockReceiver receiver = new UserUnlockReceiver(applicationContext);
                if (INSTANCE.compareAndSet(null, receiver)) {
                    IntentFilter intentFilter = new IntentFilter("android.intent.action.USER_UNLOCKED");
                    applicationContext.registerReceiver(receiver, intentFilter);
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            synchronized (FirebaseApp.LOCK) {
                for (FirebaseApp app : FirebaseApp.INSTANCES.values()) {
                    app.initializeAllApis();
                }
            }
            unregister();
        }

        public void unregister() {
            this.applicationContext.unregisterReceiver(this);
        }
    }

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    @TargetApi(14)
    /* loaded from: classes.dex */
    public static class GlobalBackgroundStateListener implements BackgroundDetector.BackgroundStateChangeListener {
        private static AtomicReference<GlobalBackgroundStateListener> INSTANCE = new AtomicReference<>();

        private GlobalBackgroundStateListener() {
        }

        public static void ensureBackgroundStateListenerRegistered(Context context) {
            if (PlatformVersion.isAtLeastIceCreamSandwich() && (context.getApplicationContext() instanceof Application)) {
                Application application = (Application) context.getApplicationContext();
                if (INSTANCE.get() == null) {
                    GlobalBackgroundStateListener listener = new GlobalBackgroundStateListener();
                    if (INSTANCE.compareAndSet(null, listener)) {
                        BackgroundDetector.initialize(application);
                        BackgroundDetector.getInstance().addListener(listener);
                    }
                }
            }
        }

        @Override // com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener
        public void onBackgroundStateChanged(boolean background) {
            synchronized (FirebaseApp.LOCK) {
                Iterator it = new ArrayList(FirebaseApp.INSTANCES.values()).iterator();
                while (it.hasNext()) {
                    FirebaseApp app = (FirebaseApp) it.next();
                    if (app.automaticResourceManagementEnabled.get()) {
                        app.notifyBackgroundStateChangeListeners(background);
                    }
                }
            }
        }
    }

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    /* loaded from: classes.dex */
    private static class UiExecutor implements Executor {
        private static final Handler HANDLER = new Handler(Looper.getMainLooper());

        private UiExecutor() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable command) {
            HANDLER.post(command);
        }
    }
}
