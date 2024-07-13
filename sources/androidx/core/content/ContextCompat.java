package androidx.core.content;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.http.Headers;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DoNotInline;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.os.ExecutorCompat;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class ContextCompat {
    private static final String TAG = "ContextCompat";
    private static final Object sLock = new Object();
    private static TypedValue sTempValue;

    @Nullable
    public static String getAttributionTag(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 30) {
            return Api30Impl.getAttributionTag(context);
        }
        return null;
    }

    public static boolean startActivities(@NonNull Context context, @NonNull Intent[] intents) {
        return startActivities(context, intents, null);
    }

    public static boolean startActivities(@NonNull Context context, @NonNull Intent[] intents, @Nullable Bundle options) {
        if (Build.VERSION.SDK_INT >= 16) {
            Api16Impl.startActivities(context, intents, options);
            return true;
        }
        context.startActivities(intents);
        return true;
    }

    public static void startActivity(@NonNull Context context, @NonNull Intent intent, @Nullable Bundle options) {
        if (Build.VERSION.SDK_INT >= 16) {
            Api16Impl.startActivity(context, intent, options);
        } else {
            context.startActivity(intent);
        }
    }

    @Nullable
    public static File getDataDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.getDataDir(context);
        }
        String dataDir = context.getApplicationInfo().dataDir;
        if (dataDir != null) {
            return new File(dataDir);
        }
        return null;
    }

    @NonNull
    public static File[] getObbDirs(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 19 ? Api19Impl.getObbDirs(context) : new File[]{context.getObbDir()};
    }

    @NonNull
    public static File[] getExternalFilesDirs(@NonNull Context context, @Nullable String type) {
        return Build.VERSION.SDK_INT >= 19 ? Api19Impl.getExternalFilesDirs(context, type) : new File[]{context.getExternalFilesDir(type)};
    }

    @NonNull
    public static File[] getExternalCacheDirs(@NonNull Context context) {
        return Build.VERSION.SDK_INT >= 19 ? Api19Impl.getExternalCacheDirs(context) : new File[]{context.getExternalCacheDir()};
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        int resolvedId;
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getDrawable(context, id);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return context.getResources().getDrawable(id);
        }
        synchronized (sLock) {
            if (sTempValue == null) {
                sTempValue = new TypedValue();
            }
            context.getResources().getValue(id, sTempValue, true);
            resolvedId = sTempValue.resourceId;
        }
        return context.getResources().getDrawable(resolvedId);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int id) {
        return ResourcesCompat.getColorStateList(context.getResources(), id, context.getTheme());
    }

    @ColorInt
    public static int getColor(@NonNull Context context, @ColorRes int id) {
        return Build.VERSION.SDK_INT >= 23 ? Api23Impl.getColor(context, id) : context.getResources().getColor(id);
    }

    public static int checkSelfPermission(@NonNull Context context, @NonNull String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(permission, Process.myPid(), Process.myUid());
    }

    @Nullable
    public static File getNoBackupFilesDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getNoBackupFilesDir(context);
        }
        ApplicationInfo appInfo = context.getApplicationInfo();
        return createFilesDir(new File(appInfo.dataDir, "no_backup"));
    }

    public static File getCodeCacheDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getCodeCacheDir(context);
        }
        ApplicationInfo appInfo = context.getApplicationInfo();
        return createFilesDir(new File(appInfo.dataDir, "code_cache"));
    }

    private static synchronized File createFilesDir(File file) {
        synchronized (ContextCompat.class) {
            if (!file.exists() && !file.mkdirs() && !file.exists()) {
                Log.w(TAG, "Unable to create files subdir " + file.getPath());
                file = null;
            }
        }
        return file;
    }

    @Nullable
    public static Context createDeviceProtectedStorageContext(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.createDeviceProtectedStorageContext(context);
        }
        return null;
    }

    public static boolean isDeviceProtectedStorage(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.isDeviceProtectedStorage(context);
        }
        return false;
    }

    public static Executor getMainExecutor(Context context) {
        return Build.VERSION.SDK_INT >= 28 ? Api28Impl.getMainExecutor(context) : ExecutorCompat.create(new Handler(context.getMainLooper()));
    }

    public static void startForegroundService(@NonNull Context context, @NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            Api26Impl.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }

    @Nullable
    public static <T> T getSystemService(@NonNull Context context, @NonNull Class<T> serviceClass) {
        if (Build.VERSION.SDK_INT >= 23) {
            return (T) Api23Impl.getSystemService(context, serviceClass);
        }
        String serviceName = getSystemServiceName(context, serviceClass);
        if (serviceName != null) {
            return (T) context.getSystemService(serviceName);
        }
        return null;
    }

    @Nullable
    public static String getSystemServiceName(@NonNull Context context, @NonNull Class<?> serviceClass) {
        return Build.VERSION.SDK_INT >= 23 ? Api23Impl.getSystemServiceName(context, serviceClass) : LegacyServiceMapHolder.SERVICES.get(serviceClass);
    }

    /* loaded from: classes.dex */
    public static final class LegacyServiceMapHolder {
        static final HashMap<Class<?>, String> SERVICES = new HashMap<>();

        private LegacyServiceMapHolder() {
        }

        static {
            if (Build.VERSION.SDK_INT >= 22) {
                SERVICES.put(SubscriptionManager.class, "telephony_subscription_service");
                SERVICES.put(UsageStatsManager.class, "usagestats");
            }
            if (Build.VERSION.SDK_INT >= 21) {
                SERVICES.put(AppWidgetManager.class, "appwidget");
                SERVICES.put(BatteryManager.class, "batterymanager");
                SERVICES.put(CameraManager.class, "camera");
                SERVICES.put(JobScheduler.class, "jobscheduler");
                SERVICES.put(LauncherApps.class, "launcherapps");
                SERVICES.put(MediaProjectionManager.class, "media_projection");
                SERVICES.put(MediaSessionManager.class, "media_session");
                SERVICES.put(RestrictionsManager.class, "restrictions");
                SERVICES.put(TelecomManager.class, "telecom");
                SERVICES.put(TvInputManager.class, "tv_input");
            }
            if (Build.VERSION.SDK_INT >= 19) {
                SERVICES.put(AppOpsManager.class, "appops");
                SERVICES.put(CaptioningManager.class, "captioning");
                SERVICES.put(ConsumerIrManager.class, "consumer_ir");
                SERVICES.put(PrintManager.class, "print");
            }
            if (Build.VERSION.SDK_INT >= 18) {
                SERVICES.put(BluetoothManager.class, "bluetooth");
            }
            if (Build.VERSION.SDK_INT >= 17) {
                SERVICES.put(DisplayManager.class, "display");
                SERVICES.put(UserManager.class, "user");
            }
            if (Build.VERSION.SDK_INT >= 16) {
                SERVICES.put(InputManager.class, "input");
                SERVICES.put(MediaRouter.class, "media_router");
                SERVICES.put(NsdManager.class, "servicediscovery");
            }
            SERVICES.put(AccessibilityManager.class, "accessibility");
            SERVICES.put(AccountManager.class, "account");
            SERVICES.put(ActivityManager.class, "activity");
            SERVICES.put(AlarmManager.class, NotificationCompat.CATEGORY_ALARM);
            SERVICES.put(AudioManager.class, "audio");
            SERVICES.put(ClipboardManager.class, "clipboard");
            SERVICES.put(ConnectivityManager.class, "connectivity");
            SERVICES.put(DevicePolicyManager.class, "device_policy");
            SERVICES.put(DownloadManager.class, "download");
            SERVICES.put(DropBoxManager.class, "dropbox");
            SERVICES.put(InputMethodManager.class, "input_method");
            SERVICES.put(KeyguardManager.class, "keyguard");
            SERVICES.put(LayoutInflater.class, "layout_inflater");
            SERVICES.put(LocationManager.class, Headers.LOCATION);
            SERVICES.put(NfcManager.class, "nfc");
            SERVICES.put(NotificationManager.class, "notification");
            SERVICES.put(PowerManager.class, "power");
            SERVICES.put(SearchManager.class, "search");
            SERVICES.put(SensorManager.class, "sensor");
            SERVICES.put(StorageManager.class, "storage");
            SERVICES.put(TelephonyManager.class, "phone");
            SERVICES.put(TextServicesManager.class, "textservices");
            SERVICES.put(UiModeManager.class, "uimode");
            SERVICES.put(UsbManager.class, "usb");
            SERVICES.put(Vibrator.class, "vibrator");
            SERVICES.put(WallpaperManager.class, "wallpaper");
            SERVICES.put(WifiP2pManager.class, "wifip2p");
            SERVICES.put(WifiManager.class, "wifi");
            SERVICES.put(WindowManager.class, "window");
        }
    }

    @RequiresApi(16)
    /* loaded from: classes.dex */
    public static class Api16Impl {
        private Api16Impl() {
        }

        @DoNotInline
        static void startActivities(Context obj, Intent[] intents, Bundle options) {
            obj.startActivities(intents, options);
        }

        @DoNotInline
        static void startActivity(Context obj, Intent intent, Bundle options) {
            obj.startActivity(intent, options);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(19)
    /* loaded from: classes.dex */
    public static class Api19Impl {
        private Api19Impl() {
        }

        @DoNotInline
        static File[] getExternalCacheDirs(Context obj) {
            return obj.getExternalCacheDirs();
        }

        @DoNotInline
        static File[] getExternalFilesDirs(Context obj, String type) {
            return obj.getExternalFilesDirs(type);
        }

        @DoNotInline
        static File[] getObbDirs(Context obj) {
            return obj.getObbDirs();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        static Drawable getDrawable(Context obj, int id) {
            return obj.getDrawable(id);
        }

        @DoNotInline
        static File getNoBackupFilesDir(Context obj) {
            return obj.getNoBackupFilesDir();
        }

        @DoNotInline
        static File getCodeCacheDir(Context obj) {
            return obj.getCodeCacheDir();
        }
    }

    @RequiresApi(23)
    /* loaded from: classes.dex */
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        static ColorStateList getColorStateList(Context obj, int id) {
            return obj.getColorStateList(id);
        }

        @DoNotInline
        static int getColor(Context obj, int id) {
            return obj.getColor(id);
        }

        @DoNotInline
        static <T> T getSystemService(Context obj, Class<T> serviceClass) {
            return (T) obj.getSystemService(serviceClass);
        }

        @DoNotInline
        static String getSystemServiceName(Context obj, Class<?> serviceClass) {
            return obj.getSystemServiceName(serviceClass);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(24)
    /* loaded from: classes.dex */
    public static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        static File getDataDir(Context obj) {
            return obj.getDataDir();
        }

        @DoNotInline
        static Context createDeviceProtectedStorageContext(Context obj) {
            return obj.createDeviceProtectedStorageContext();
        }

        @DoNotInline
        static boolean isDeviceProtectedStorage(Context obj) {
            return obj.isDeviceProtectedStorage();
        }
    }

    @RequiresApi(26)
    /* loaded from: classes.dex */
    static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        static ComponentName startForegroundService(Context obj, Intent service) {
            return obj.startForegroundService(service);
        }
    }

    @RequiresApi(28)
    /* loaded from: classes.dex */
    static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        static Executor getMainExecutor(Context obj) {
            return obj.getMainExecutor();
        }
    }

    @RequiresApi(30)
    /* loaded from: classes.dex */
    static class Api30Impl {
        private Api30Impl() {
        }

        @DoNotInline
        static String getAttributionTag(Context obj) {
            return obj.getAttributionTag();
        }
    }
}
