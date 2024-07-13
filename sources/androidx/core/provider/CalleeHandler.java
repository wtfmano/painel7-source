package androidx.core.provider;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
class CalleeHandler {
    private CalleeHandler() {
    }

    @NonNull
    public static Handler create() {
        if (Looper.myLooper() == null) {
            Handler handler = new Handler(Looper.getMainLooper());
            return handler;
        }
        Handler handler2 = new Handler();
        return handler2;
    }
}
