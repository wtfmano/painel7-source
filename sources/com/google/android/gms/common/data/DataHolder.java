package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
@KeepForSdk
@KeepName
@SafeParcelable.Class(creator = "DataHolderCreator", validate = true)
/* loaded from: classes.dex */
public final class DataHolder extends AbstractSafeParcelable implements Closeable {
    @RecentlyNonNull
    @KeepForSdk
    public static final Parcelable.Creator<DataHolder> CREATOR = new zad();
    private static final Builder zak = new zab(new String[0], null);
    @SafeParcelable.VersionField(id = 1000)
    final int zaa;
    Bundle zab;
    int[] zac;
    int zad;
    boolean zae;
    @SafeParcelable.Field(getter = "getColumns", id = 1)
    private final String[] zaf;
    @SafeParcelable.Field(getter = "getWindows", id = 2)
    private final CursorWindow[] zag;
    @SafeParcelable.Field(getter = "getStatusCode", id = 3)
    private final int zah;
    @Nullable
    @SafeParcelable.Field(getter = "getMetadata", id = 4)
    private final Bundle zai;
    private boolean zaj;

    /* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
    @KeepForSdk
    /* loaded from: classes.dex */
    public static class Builder {
        private final String[] zaa;
        private final ArrayList<HashMap<String, Object>> zab = new ArrayList<>();
        private final HashMap<Object, Integer> zac = new HashMap<>();

        public /* synthetic */ Builder(String[] strArr, String str, zab zabVar) {
            this.zaa = (String[]) Preconditions.checkNotNull(strArr);
        }

        @RecentlyNonNull
        @KeepForSdk
        public DataHolder build(int i) {
            return new DataHolder(this, i, (Bundle) null, (zab) null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public Builder withRow(@RecentlyNonNull ContentValues contentValues) {
            Asserts.checkNotNull(contentValues);
            HashMap<String, Object> hashMap = new HashMap<>(contentValues.size());
            for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
                hashMap.put(entry.getKey(), entry.getValue());
            }
            return zaa(hashMap);
        }

        @RecentlyNonNull
        public Builder zaa(@RecentlyNonNull HashMap<String, Object> hashMap) {
            Asserts.checkNotNull(hashMap);
            this.zab.add(hashMap);
            return this;
        }

        @RecentlyNonNull
        @KeepForSdk
        public DataHolder build(int i, @RecentlyNonNull Bundle bundle) {
            return new DataHolder(this, i, bundle, -1, (zab) null);
        }
    }

    @SafeParcelable.Constructor
    public DataHolder(@SafeParcelable.Param(id = 1000) int i, @SafeParcelable.Param(id = 1) String[] strArr, @SafeParcelable.Param(id = 2) CursorWindow[] cursorWindowArr, @SafeParcelable.Param(id = 3) int i2, @Nullable @SafeParcelable.Param(id = 4) Bundle bundle) {
        this.zae = false;
        this.zaj = true;
        this.zaa = i;
        this.zaf = strArr;
        this.zag = cursorWindowArr;
        this.zah = i2;
        this.zai = bundle;
    }

    /* synthetic */ DataHolder(Builder builder, int i, Bundle bundle, int i2, zab zabVar) {
        this(builder.zaa, zae(builder, -1), i, bundle);
    }

    /* synthetic */ DataHolder(Builder builder, int i, Bundle bundle, zab zabVar) {
        this(builder, i, (Bundle) null);
    }

    @RecentlyNonNull
    @KeepForSdk
    public static Builder builder(@RecentlyNonNull String[] strArr) {
        return new Builder(strArr, null, null);
    }

    @RecentlyNonNull
    @KeepForSdk
    public static DataHolder empty(int i) {
        return new DataHolder(zak, i, (Bundle) null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:139:0x01af, code lost:
        if (r3 == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x01bd, code lost:
        throw new com.google.android.gms.common.data.zac("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x01da, code lost:
        r13 = new java.lang.StringBuilder(74);
        r13.append("Couldn't populate window data for row ");
        r13.append(r4);
        r13.append(" - allocating new window.");
        android.util.Log.d("DataHolder", r13.toString());
        r2.freeLastRow();
        r2 = new android.database.CursorWindow(false);
        r2.setStartPosition(r4);
        r2.setNumColumns(r18.zaa.length);
        r13.add(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0224, code lost:
        r5 = r4 - 1;
        r3 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.database.CursorWindow[] zae(com.google.android.gms.common.data.DataHolder.Builder r18, int r19) {
        /*
            Method dump skipped, instructions count: 669
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.DataHolder.zae(com.google.android.gms.common.data.DataHolder$Builder, int):android.database.CursorWindow[]");
    }

    private final void zaf(String str, int i) {
        Bundle bundle = this.zab;
        if (bundle == null || !bundle.containsKey(str)) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "No such column: ".concat(valueOf) : new String("No such column: "));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else {
            if (i < 0 || i >= this.zad) {
                throw new CursorIndexOutOfBoundsException(i, this.zad);
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    @KeepForSdk
    public void close() {
        synchronized (this) {
            if (!this.zae) {
                this.zae = true;
                int i = 0;
                while (true) {
                    CursorWindow[] cursorWindowArr = this.zag;
                    if (i >= cursorWindowArr.length) {
                        break;
                    }
                    cursorWindowArr[i].close();
                    i++;
                }
            }
        }
    }

    protected final void finalize() throws Throwable {
        try {
            if (this.zaj && this.zag.length > 0 && !isClosed()) {
                close();
                String obj = toString();
                StringBuilder sb = new StringBuilder(String.valueOf(obj).length() + 178);
                sb.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
                sb.append(obj);
                sb.append(")");
                Log.e("DataBuffer", sb.toString());
            }
        } finally {
            super.finalize();
        }
    }

    @KeepForSdk
    public boolean getBoolean(@RecentlyNonNull String str, int i, int i2) {
        zaf(str, i);
        return Long.valueOf(this.zag[i2].getLong(i, this.zab.getInt(str))).longValue() == 1;
    }

    @RecentlyNonNull
    @KeepForSdk
    public byte[] getByteArray(@RecentlyNonNull String str, int i, int i2) {
        zaf(str, i);
        return this.zag[i2].getBlob(i, this.zab.getInt(str));
    }

    @KeepForSdk
    public int getCount() {
        return this.zad;
    }

    @KeepForSdk
    public int getInteger(@RecentlyNonNull String str, int i, int i2) {
        zaf(str, i);
        return this.zag[i2].getInt(i, this.zab.getInt(str));
    }

    @KeepForSdk
    public long getLong(@RecentlyNonNull String str, int i, int i2) {
        zaf(str, i);
        return this.zag[i2].getLong(i, this.zab.getInt(str));
    }

    @RecentlyNullable
    @KeepForSdk
    public Bundle getMetadata() {
        return this.zai;
    }

    @KeepForSdk
    public int getStatusCode() {
        return this.zah;
    }

    @RecentlyNonNull
    @KeepForSdk
    public String getString(@RecentlyNonNull String str, int i, int i2) {
        zaf(str, i);
        return this.zag[i2].getString(i, this.zab.getInt(str));
    }

    @KeepForSdk
    public int getWindowIndex(int i) {
        int length;
        int i2;
        Preconditions.checkState(i >= 0 ? i < this.zad : false);
        int i3 = 0;
        while (true) {
            int[] iArr = this.zac;
            length = iArr.length;
            if (i3 >= length) {
                i2 = i3;
                break;
            } else if (i < iArr[i3]) {
                i2 = i3 - 1;
                break;
            } else {
                i3++;
            }
        }
        return i2 == length ? i2 - 1 : i2;
    }

    @KeepForSdk
    public boolean hasColumn(@RecentlyNonNull String str) {
        return this.zab.containsKey(str);
    }

    @KeepForSdk
    public boolean hasNull(@RecentlyNonNull String str, int i, int i2) {
        zaf(str, i);
        return this.zag[i2].isNull(i, this.zab.getInt(str));
    }

    @KeepForSdk
    public boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.zae;
        }
        return z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringArray(parcel, 1, this.zaf, false);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zag, i, false);
        SafeParcelWriter.writeInt(parcel, 3, getStatusCode());
        SafeParcelWriter.writeBundle(parcel, 4, getMetadata(), false);
        SafeParcelWriter.writeInt(parcel, 1000, this.zaa);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        if ((i & 1) != 0) {
            close();
        }
    }

    public final void zaa() {
        this.zab = new Bundle();
        int i = 0;
        while (true) {
            String[] strArr = this.zaf;
            if (i >= strArr.length) {
                break;
            }
            this.zab.putInt(strArr[i], i);
            i++;
        }
        this.zac = new int[this.zag.length];
        int i2 = 0;
        int i3 = 0;
        while (true) {
            CursorWindow[] cursorWindowArr = this.zag;
            if (i3 >= cursorWindowArr.length) {
                this.zad = i2;
                return;
            }
            this.zac[i3] = i2;
            i2 += this.zag[i3].getNumRows() - (i2 - cursorWindowArr[i3].getStartPosition());
            i3++;
        }
    }

    public final float zab(@RecentlyNonNull String str, int i, int i2) {
        zaf(str, i);
        return this.zag[i2].getFloat(i, this.zab.getInt(str));
    }

    public final double zac(@RecentlyNonNull String str, int i, int i2) {
        zaf(str, i);
        return this.zag[i2].getDouble(i, this.zab.getInt(str));
    }

    public final void zad(@RecentlyNonNull String str, int i, int i2, @RecentlyNonNull CharArrayBuffer charArrayBuffer) {
        zaf(str, i);
        this.zag[i2].copyStringToBuffer(i, this.zab.getInt(str), charArrayBuffer);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @com.google.android.gms.common.annotation.KeepForSdk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DataHolder(@androidx.annotation.RecentlyNonNull android.database.Cursor r15, int r16, @androidx.annotation.Nullable android.os.Bundle r17) {
        /*
            Method dump skipped, instructions count: 200
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.DataHolder.<init>(android.database.Cursor, int, android.os.Bundle):void");
    }

    private DataHolder(Builder builder, int i, @Nullable Bundle bundle) {
        this(builder.zaa, zae(builder, -1), i, (Bundle) null);
    }

    @KeepForSdk
    public DataHolder(@RecentlyNonNull String[] strArr, @RecentlyNonNull CursorWindow[] cursorWindowArr, int i, @Nullable Bundle bundle) {
        this.zae = false;
        this.zaj = true;
        this.zaa = 1;
        this.zaf = (String[]) Preconditions.checkNotNull(strArr);
        this.zag = (CursorWindow[]) Preconditions.checkNotNull(cursorWindowArr);
        this.zah = i;
        this.zai = bundle;
        zaa();
    }
}
