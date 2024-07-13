package androidx.core.os;

import android.os.Parcel;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public final class ParcelCompat {
    public static boolean readBoolean(@NonNull Parcel in) {
        return in.readInt() != 0;
    }

    public static void writeBoolean(@NonNull Parcel out, boolean value) {
        out.writeInt(value ? 1 : 0);
    }

    private ParcelCompat() {
    }
}
