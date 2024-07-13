package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

@RequiresApi(api = 28)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class CoreComponentFactory extends android.app.AppComponentFactory {

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public interface CompatWrapped {
        Object getWrapper();
    }

    @Override // android.app.AppComponentFactory
    @NonNull
    public Activity instantiateActivity(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Activity) checkCompatWrapper(super.instantiateActivity(cl, className, intent));
    }

    @Override // android.app.AppComponentFactory
    @NonNull
    public Application instantiateApplication(@NonNull ClassLoader cl, @NonNull String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Application) checkCompatWrapper(super.instantiateApplication(cl, className));
    }

    @Override // android.app.AppComponentFactory
    @NonNull
    public BroadcastReceiver instantiateReceiver(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (BroadcastReceiver) checkCompatWrapper(super.instantiateReceiver(cl, className, intent));
    }

    @Override // android.app.AppComponentFactory
    @NonNull
    public ContentProvider instantiateProvider(@NonNull ClassLoader cl, @NonNull String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (ContentProvider) checkCompatWrapper(super.instantiateProvider(cl, className));
    }

    @Override // android.app.AppComponentFactory
    @NonNull
    public Service instantiateService(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Service) checkCompatWrapper(super.instantiateService(cl, className, intent));
    }

    public static <T> T checkCompatWrapper(T obj) {
        T wrapper;
        return (!(obj instanceof CompatWrapped) || (wrapper = (T) ((CompatWrapped) obj).getWrapper()) == null) ? obj : wrapper;
    }
}
