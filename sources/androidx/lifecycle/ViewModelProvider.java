package androidx.lifecycle;

import android.app.Application;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class ViewModelProvider {
    private static final String DEFAULT_KEY = "androidx.lifecycle.ViewModelProvider.DefaultKey";
    private final Factory mFactory;
    private final ViewModelStore mViewModelStore;

    /* loaded from: classes.dex */
    public interface Factory {
        @NonNull
        <T extends ViewModel> T create(@NonNull Class<T> cls);
    }

    /* loaded from: classes.dex */
    public static class OnRequeryFactory {
        OnRequeryFactory() {
        }

        void onRequery(@NonNull ViewModel viewModel) {
        }
    }

    /* loaded from: classes.dex */
    public static abstract class KeyedFactory extends OnRequeryFactory implements Factory {
        @NonNull
        public abstract <T extends ViewModel> T create(@NonNull String str, @NonNull Class<T> cls);

        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> cls) {
            throw new UnsupportedOperationException("create(String, Class<?>) must be called on implementaions of KeyedFactory");
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ViewModelProvider(@androidx.annotation.NonNull androidx.lifecycle.ViewModelStoreOwner r6) {
        /*
            r5 = this;
            r0 = r5
            r1 = r6
            r2 = r0
            r3 = r1
            androidx.lifecycle.ViewModelStore r3 = r3.getViewModelStore()
            r4 = r1
            boolean r4 = r4 instanceof androidx.lifecycle.HasDefaultViewModelProviderFactory
            if (r4 == 0) goto L18
            r4 = r1
            androidx.lifecycle.HasDefaultViewModelProviderFactory r4 = (androidx.lifecycle.HasDefaultViewModelProviderFactory) r4
            androidx.lifecycle.ViewModelProvider$Factory r4 = r4.getDefaultViewModelProviderFactory()
        L14:
            r2.<init>(r3, r4)
            return
        L18:
            androidx.lifecycle.ViewModelProvider$NewInstanceFactory r4 = androidx.lifecycle.ViewModelProvider.NewInstanceFactory.getInstance()
            goto L14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.ViewModelProvider.<init>(androidx.lifecycle.ViewModelStoreOwner):void");
    }

    public ViewModelProvider(@NonNull ViewModelStoreOwner viewModelStoreOwner, @NonNull Factory factory) {
        this(viewModelStoreOwner.getViewModelStore(), factory);
    }

    public ViewModelProvider(@NonNull ViewModelStore viewModelStore, @NonNull Factory factory) {
        this.mFactory = factory;
        this.mViewModelStore = viewModelStore;
    }

    @NonNull
    @MainThread
    public <T extends ViewModel> T get(@NonNull Class<T> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return (T) get("androidx.lifecycle.ViewModelProvider.DefaultKey:" + canonicalName, cls);
    }

    @NonNull
    @MainThread
    public <T extends ViewModel> T get(@NonNull String str, @NonNull Class<T> cls) {
        ViewModel create;
        T t = (T) this.mViewModelStore.get(str);
        if (cls.isInstance(t)) {
            if (this.mFactory instanceof OnRequeryFactory) {
                ((OnRequeryFactory) this.mFactory).onRequery(t);
            }
            return t;
        }
        if (t != null) {
        }
        if (this.mFactory instanceof KeyedFactory) {
            create = ((KeyedFactory) this.mFactory).create(str, cls);
        } else {
            create = this.mFactory.create(cls);
        }
        this.mViewModelStore.put(str, create);
        return (T) create;
    }

    /* loaded from: classes.dex */
    public static class NewInstanceFactory implements Factory {
        private static NewInstanceFactory sInstance;

        @NonNull
        public static NewInstanceFactory getInstance() {
            if (sInstance == null) {
                sInstance = new NewInstanceFactory();
            }
            return sInstance;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> cls) {
            try {
                return cls.newInstance();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + cls, e);
            } catch (InstantiationException e2) {
                throw new RuntimeException("Cannot create an instance of " + cls, e2);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class AndroidViewModelFactory extends NewInstanceFactory {
        private static AndroidViewModelFactory sInstance;
        private Application mApplication;

        @NonNull
        public static AndroidViewModelFactory getInstance(@NonNull Application application) {
            if (sInstance == null) {
                sInstance = new AndroidViewModelFactory(application);
            }
            return sInstance;
        }

        public AndroidViewModelFactory(@NonNull Application application) {
            this.mApplication = application;
        }

        @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> cls) {
            if (AndroidViewModel.class.isAssignableFrom(cls)) {
                try {
                    return cls.getConstructor(Application.class).newInstance(this.mApplication);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e);
                } catch (InstantiationException e2) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e2);
                } catch (NoSuchMethodException e3) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e3);
                } catch (InvocationTargetException e4) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e4);
                }
            }
            return (T) super.create(cls);
        }
    }
}
