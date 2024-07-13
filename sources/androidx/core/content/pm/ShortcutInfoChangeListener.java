package androidx.core.content.pm;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public abstract class ShortcutInfoChangeListener {
    @AnyThread
    public void onShortcutAdded(@NonNull List<ShortcutInfoCompat> shortcuts) {
    }

    @AnyThread
    public void onShortcutUpdated(@NonNull List<ShortcutInfoCompat> shortcuts) {
    }

    @AnyThread
    public void onShortcutRemoved(@NonNull List<String> shortcutIds) {
    }

    @AnyThread
    public void onAllShortcutsRemoved() {
    }

    @AnyThread
    public void onShortcutUsageReported(@NonNull List<String> shortcutIds) {
    }
}
