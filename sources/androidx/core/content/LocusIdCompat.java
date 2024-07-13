package androidx.core.content;

import android.content.LocusId;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

/* loaded from: classes.dex */
public final class LocusIdCompat {
    private final String mId;
    private final LocusId mWrapped;

    public LocusIdCompat(@NonNull String id) {
        this.mId = (String) Preconditions.checkStringNotEmpty(id, "id cannot be empty");
        if (Build.VERSION.SDK_INT >= 29) {
            this.mWrapped = Api29Impl.create(id);
        } else {
            this.mWrapped = null;
        }
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        int result = (this.mId == null ? 0 : this.mId.hashCode()) + 31;
        return result;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            LocusIdCompat other = (LocusIdCompat) obj;
            if (this.mId == null) {
                return other.mId == null;
            }
            return this.mId.equals(other.mId);
        }
        return false;
    }

    @NonNull
    public String toString() {
        return "LocusIdCompat[" + getSanitizedId() + "]";
    }

    @NonNull
    @RequiresApi(29)
    public LocusId toLocusId() {
        return this.mWrapped;
    }

    @NonNull
    @RequiresApi(29)
    public static LocusIdCompat toLocusIdCompat(@NonNull LocusId locusId) {
        Preconditions.checkNotNull(locusId, "locusId cannot be null");
        return new LocusIdCompat((String) Preconditions.checkStringNotEmpty(Api29Impl.getId(locusId), "id cannot be empty"));
    }

    @NonNull
    private String getSanitizedId() {
        int size = this.mId.length();
        return size + "_chars";
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(29)
    /* loaded from: classes.dex */
    public static class Api29Impl {
        private Api29Impl() {
        }

        @NonNull
        static LocusId create(@NonNull String id) {
            return new LocusId(id);
        }

        @NonNull
        static String getId(@NonNull LocusId obj) {
            return obj.getId();
        }
    }
}
