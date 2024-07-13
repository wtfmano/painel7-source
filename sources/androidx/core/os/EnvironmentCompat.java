package androidx.core.os;

import android.os.Build;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    @NonNull
    public static String getStorageState(@NonNull File path) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Environment.getExternalStorageState(path);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return Environment.getStorageState(path);
        }
        try {
            String canonicalPath = path.getCanonicalPath();
            String canonicalExternal = Environment.getExternalStorageDirectory().getCanonicalPath();
            if (canonicalPath.startsWith(canonicalExternal)) {
                return Environment.getExternalStorageState();
            }
        } catch (IOException e) {
            Log.w(TAG, "Failed to resolve canonical path: " + e);
        }
        return MEDIA_UNKNOWN;
    }

    private EnvironmentCompat() {
    }
}
