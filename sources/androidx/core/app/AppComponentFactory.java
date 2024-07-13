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
import java.lang.reflect.InvocationTargetException;

@RequiresApi(28)
/* loaded from: classes.dex */
public class AppComponentFactory extends android.app.AppComponentFactory {
    @Override // android.app.AppComponentFactory
    @NonNull
    public final Activity instantiateActivity(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Activity) CoreComponentFactory.checkCompatWrapper(instantiateActivityCompat(cl, className, intent));
    }

    @Override // android.app.AppComponentFactory
    @NonNull
    public final Application instantiateApplication(@NonNull ClassLoader cl, @NonNull String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Application) CoreComponentFactory.checkCompatWrapper(instantiateApplicationCompat(cl, className));
    }

    @Override // android.app.AppComponentFactory
    @NonNull
    public final BroadcastReceiver instantiateReceiver(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (BroadcastReceiver) CoreComponentFactory.checkCompatWrapper(instantiateReceiverCompat(cl, className, intent));
    }

    @Override // android.app.AppComponentFactory
    @NonNull
    public final ContentProvider instantiateProvider(@NonNull ClassLoader cl, @NonNull String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (ContentProvider) CoreComponentFactory.checkCompatWrapper(instantiateProviderCompat(cl, className));
    }

    @Override // android.app.AppComponentFactory
    @NonNull
    public final Service instantiateService(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Service) CoreComponentFactory.checkCompatWrapper(instantiateServiceCompat(cl, className, intent));
    }

    @NonNull
    public Application instantiateApplicationCompat(@NonNull ClassLoader cl, @NonNull String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        try {
            return (Application) Class.forName(className, false, cl).asSubclass(Application.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }

    @NonNull
    public Activity instantiateActivityCompat(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        try {
            return (Activity) Class.forName(className, false, cl).asSubclass(Activity.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }

    @NonNull
    public BroadcastReceiver instantiateReceiverCompat(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        try {
            return (BroadcastReceiver) Class.forName(className, false, cl).asSubclass(BroadcastReceiver.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }

    @NonNull
    public Service instantiateServiceCompat(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        try {
            return (Service) Class.forName(className, false, cl).asSubclass(Service.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }

    @NonNull
    public ContentProvider instantiateProviderCompat(@NonNull ClassLoader cl, @NonNull String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        try {
            return (ContentProvider) Class.forName(className, false, cl).asSubclass(ContentProvider.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't call constructor", e);
        }
    }
}
