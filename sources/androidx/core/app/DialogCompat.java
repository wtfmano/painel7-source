package androidx.core.app;

import android.app.Dialog;
import android.os.Build;
import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class DialogCompat {
    private DialogCompat() {
    }

    @NonNull
    public static View requireViewById(@NonNull Dialog dialog, int id) {
        if (Build.VERSION.SDK_INT >= 28) {
            return dialog.requireViewById(id);
        }
        View view = dialog.findViewById(id);
        if (view == null) {
            throw new IllegalArgumentException("ID does not reference a View inside this Dialog");
        }
        return view;
    }
}
