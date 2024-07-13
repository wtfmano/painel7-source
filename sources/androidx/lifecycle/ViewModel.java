package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class ViewModel {
    @Nullable
    private final Map<String, Object> mBagOfTags = new HashMap();
    private volatile boolean mCleared = false;

    public void onCleared() {
    }

    @MainThread
    public final void clear() {
        this.mCleared = true;
        if (this.mBagOfTags != null) {
            synchronized (this.mBagOfTags) {
                for (Object obj : this.mBagOfTags.values()) {
                    closeWithRuntimeException(obj);
                }
            }
        }
        onCleared();
    }

    public <T> T setTagIfAbsent(String str, T t) {
        Object obj;
        synchronized (this.mBagOfTags) {
            obj = this.mBagOfTags.get(str);
            if (obj == 0) {
                this.mBagOfTags.put(str, t);
            }
        }
        T t2 = obj == 0 ? t : obj;
        if (this.mCleared) {
            closeWithRuntimeException(t2);
        }
        return t2;
    }

    public <T> T getTag(String str) {
        T t;
        if (this.mBagOfTags == null) {
            return null;
        }
        synchronized (this.mBagOfTags) {
            t = (T) this.mBagOfTags.get(str);
        }
        return t;
    }

    private static void closeWithRuntimeException(Object obj) {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
