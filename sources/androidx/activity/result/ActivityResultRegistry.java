package androidx.activity.result;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/* loaded from: classes.dex */
public abstract class ActivityResultRegistry {
    private static final int INITIAL_REQUEST_CODE_VALUE = 65536;
    private static final String KEY_COMPONENT_ACTIVITY_PENDING_RESULTS = "KEY_COMPONENT_ACTIVITY_PENDING_RESULT";
    private static final String KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT = "KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT";
    private static final String KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS = "KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS";
    private static final String KEY_COMPONENT_ACTIVITY_REGISTERED_RCS = "KEY_COMPONENT_ACTIVITY_REGISTERED_RCS";
    private static final String LOG_TAG = "ActivityResultRegistry";
    private Random mRandom = new Random();
    private final Map<Integer, String> mRcToKey = new HashMap();
    private final Map<String, Integer> mKeyToRc = new HashMap();
    private final Map<String, LifecycleContainer> mKeyToLifecycleContainers = new HashMap();
    final transient Map<String, CallbackAndContract<?>> mKeyToCallback = new HashMap();
    final Map<String, Object> mParsedPendingResults = new HashMap();
    final Bundle mPendingResults = new Bundle();

    @MainThread
    public abstract <I, O> void onLaunch(int i, @NonNull ActivityResultContract<I, O> activityResultContract, @SuppressLint({"UnknownNullness"}) I i2, @Nullable ActivityOptionsCompat activityOptionsCompat);

    @NonNull
    public final <I, O> ActivityResultLauncher<I> register(@NonNull final String str, @NonNull LifecycleOwner lifecycleOwner, @NonNull final ActivityResultContract<I, O> activityResultContract, @NonNull final ActivityResultCallback<O> activityResultCallback) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            throw new IllegalStateException("LifecycleOwner " + lifecycleOwner + " is attempting to register while current state is " + lifecycle.getCurrentState() + ". LifecycleOwners must call register before they are STARTED.");
        }
        final int registerKey = registerKey(str);
        LifecycleContainer lifecycleContainer = this.mKeyToLifecycleContainers.get(str);
        if (lifecycleContainer == null) {
            lifecycleContainer = new LifecycleContainer(lifecycle);
        }
        lifecycleContainer.addObserver(new LifecycleEventObserver() { // from class: androidx.activity.result.ActivityResultRegistry.1
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner2, @NonNull Lifecycle.Event event) {
                if (Lifecycle.Event.ON_START.equals(event)) {
                    ActivityResultRegistry.this.mKeyToCallback.put(str, new CallbackAndContract<>(activityResultCallback, activityResultContract));
                    if (ActivityResultRegistry.this.mParsedPendingResults.containsKey(str)) {
                        Object obj = ActivityResultRegistry.this.mParsedPendingResults.get(str);
                        ActivityResultRegistry.this.mParsedPendingResults.remove(str);
                        activityResultCallback.onActivityResult(obj);
                    }
                    ActivityResult activityResult = (ActivityResult) ActivityResultRegistry.this.mPendingResults.getParcelable(str);
                    if (activityResult != null) {
                        ActivityResultRegistry.this.mPendingResults.remove(str);
                        activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.getResultCode(), activityResult.getData()));
                    }
                } else if (Lifecycle.Event.ON_STOP.equals(event)) {
                    ActivityResultRegistry.this.mKeyToCallback.remove(str);
                } else if (Lifecycle.Event.ON_DESTROY.equals(event)) {
                    ActivityResultRegistry.this.unregister(str);
                }
            }
        });
        this.mKeyToLifecycleContainers.put(str, lifecycleContainer);
        return new ActivityResultLauncher<I>() { // from class: androidx.activity.result.ActivityResultRegistry.2
            @Override // androidx.activity.result.ActivityResultLauncher
            public void launch(I i, @Nullable ActivityOptionsCompat activityOptionsCompat) {
                ActivityResultRegistry.this.onLaunch(registerKey, activityResultContract, i, activityOptionsCompat);
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            public void unregister() {
                ActivityResultRegistry.this.unregister(str);
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            @NonNull
            public ActivityResultContract<I, ?> getContract() {
                return activityResultContract;
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public final <I, O> ActivityResultLauncher<I> register(@NonNull final String str, @NonNull final ActivityResultContract<I, O> activityResultContract, @NonNull ActivityResultCallback<O> activityResultCallback) {
        final int registerKey = registerKey(str);
        this.mKeyToCallback.put(str, new CallbackAndContract<>(activityResultCallback, activityResultContract));
        if (this.mParsedPendingResults.containsKey(str)) {
            Object obj = this.mParsedPendingResults.get(str);
            this.mParsedPendingResults.remove(str);
            activityResultCallback.onActivityResult(obj);
        }
        ActivityResult activityResult = (ActivityResult) this.mPendingResults.getParcelable(str);
        if (activityResult != null) {
            this.mPendingResults.remove(str);
            activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.getResultCode(), activityResult.getData()));
        }
        return new ActivityResultLauncher<I>() { // from class: androidx.activity.result.ActivityResultRegistry.3
            @Override // androidx.activity.result.ActivityResultLauncher
            public void launch(I i, @Nullable ActivityOptionsCompat activityOptionsCompat) {
                ActivityResultRegistry.this.onLaunch(registerKey, activityResultContract, i, activityOptionsCompat);
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            public void unregister() {
                ActivityResultRegistry.this.unregister(str);
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            @NonNull
            public ActivityResultContract<I, ?> getContract() {
                return activityResultContract;
            }
        };
    }

    @MainThread
    final void unregister(@NonNull String str) {
        Integer remove = this.mKeyToRc.remove(str);
        if (remove != null) {
            this.mRcToKey.remove(remove);
        }
        this.mKeyToCallback.remove(str);
        if (this.mParsedPendingResults.containsKey(str)) {
            Log.w(LOG_TAG, "Dropping pending result for request " + str + ": " + this.mParsedPendingResults.get(str));
            this.mParsedPendingResults.remove(str);
        }
        if (this.mPendingResults.containsKey(str)) {
            Log.w(LOG_TAG, "Dropping pending result for request " + str + ": " + this.mPendingResults.getParcelable(str));
            this.mPendingResults.remove(str);
        }
        LifecycleContainer lifecycleContainer = this.mKeyToLifecycleContainers.get(str);
        if (lifecycleContainer != null) {
            lifecycleContainer.clearObservers();
            this.mKeyToLifecycleContainers.remove(str);
        }
    }

    public final void onSaveInstanceState(@NonNull Bundle bundle) {
        bundle.putIntegerArrayList(KEY_COMPONENT_ACTIVITY_REGISTERED_RCS, new ArrayList<>(this.mRcToKey.keySet()));
        bundle.putStringArrayList(KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS, new ArrayList<>(this.mRcToKey.values()));
        bundle.putBundle(KEY_COMPONENT_ACTIVITY_PENDING_RESULTS, (Bundle) this.mPendingResults.clone());
        bundle.putSerializable(KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT, this.mRandom);
    }

    public final void onRestoreInstanceState(@Nullable Bundle bundle) {
        if (bundle != null) {
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(KEY_COMPONENT_ACTIVITY_REGISTERED_RCS);
            ArrayList<String> stringArrayList = bundle.getStringArrayList(KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS);
            if (stringArrayList != null && integerArrayList != null) {
                int size = stringArrayList.size();
                for (int i = 0; i < size; i++) {
                    bindRcKey(integerArrayList.get(i).intValue(), stringArrayList.get(i));
                }
                this.mRandom = (Random) bundle.getSerializable(KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT);
                this.mPendingResults.putAll(bundle.getBundle(KEY_COMPONENT_ACTIVITY_PENDING_RESULTS));
            }
        }
    }

    @MainThread
    public final boolean dispatchResult(int i, int i2, @Nullable Intent intent) {
        String str = this.mRcToKey.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        doDispatch(str, i2, intent, this.mKeyToCallback.get(str));
        return true;
    }

    @MainThread
    public final <O> boolean dispatchResult(int i, @SuppressLint({"UnknownNullness"}) O o) {
        String str = this.mRcToKey.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        CallbackAndContract<?> callbackAndContract = this.mKeyToCallback.get(str);
        if (callbackAndContract == null || callbackAndContract.mCallback == null) {
            this.mPendingResults.remove(str);
            this.mParsedPendingResults.put(str, o);
        } else {
            callbackAndContract.mCallback.onActivityResult(o);
        }
        return true;
    }

    private <O> void doDispatch(String str, int i, @Nullable Intent intent, @Nullable CallbackAndContract<O> callbackAndContract) {
        if (callbackAndContract != null && callbackAndContract.mCallback != null) {
            callbackAndContract.mCallback.onActivityResult(callbackAndContract.mContract.parseResult(i, intent));
            return;
        }
        this.mParsedPendingResults.remove(str);
        this.mPendingResults.putParcelable(str, new ActivityResult(i, intent));
    }

    private int registerKey(String str) {
        Integer num = this.mKeyToRc.get(str);
        if (num != null) {
            return num.intValue();
        }
        int generateRandomNumber = generateRandomNumber();
        bindRcKey(generateRandomNumber, str);
        return generateRandomNumber;
    }

    private int generateRandomNumber() {
        int nextInt = this.mRandom.nextInt(2147418112);
        while (true) {
            int i = nextInt + 65536;
            if (this.mRcToKey.containsKey(Integer.valueOf(i))) {
                nextInt = this.mRandom.nextInt(2147418112);
            } else {
                return i;
            }
        }
    }

    private void bindRcKey(int i, String str) {
        this.mRcToKey.put(Integer.valueOf(i), str);
        this.mKeyToRc.put(str, Integer.valueOf(i));
    }

    /* loaded from: classes.dex */
    public static class CallbackAndContract<O> {
        final ActivityResultCallback<O> mCallback;
        final ActivityResultContract<?, O> mContract;

        CallbackAndContract(ActivityResultCallback<O> activityResultCallback, ActivityResultContract<?, O> activityResultContract) {
            this.mCallback = activityResultCallback;
            this.mContract = activityResultContract;
        }
    }

    /* loaded from: classes.dex */
    public static class LifecycleContainer {
        final Lifecycle mLifecycle;
        private final ArrayList<LifecycleEventObserver> mObservers = new ArrayList<>();

        LifecycleContainer(@NonNull Lifecycle lifecycle) {
            this.mLifecycle = lifecycle;
        }

        void addObserver(@NonNull LifecycleEventObserver lifecycleEventObserver) {
            this.mLifecycle.addObserver(lifecycleEventObserver);
            this.mObservers.add(lifecycleEventObserver);
        }

        void clearObservers() {
            Iterator<LifecycleEventObserver> it = this.mObservers.iterator();
            while (it.hasNext()) {
                this.mLifecycle.removeObserver(it.next());
            }
            this.mObservers.clear();
        }
    }
}
