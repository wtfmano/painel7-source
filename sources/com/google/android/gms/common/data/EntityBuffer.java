package com.google.android.gms.common.data;

import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public abstract class EntityBuffer<T> extends AbstractDataBuffer<T> {
    private boolean zaa;
    private ArrayList<Integer> zab;

    @KeepForSdk
    protected EntityBuffer(@RecentlyNonNull DataHolder dataHolder) {
        super(dataHolder);
        this.zaa = false;
    }

    private final void zab() {
        synchronized (this) {
            if (!this.zaa) {
                int count = ((DataHolder) Preconditions.checkNotNull(this.mDataHolder)).getCount();
                this.zab = new ArrayList<>();
                if (count > 0) {
                    this.zab.add(0);
                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                    String string = this.mDataHolder.getString(primaryDataMarkerColumn, 0, this.mDataHolder.getWindowIndex(0));
                    for (int i = 1; i < count; i++) {
                        int windowIndex = this.mDataHolder.getWindowIndex(i);
                        String string2 = this.mDataHolder.getString(primaryDataMarkerColumn, i, windowIndex);
                        if (string2 != null) {
                            if (!string2.equals(string)) {
                                this.zab.add(Integer.valueOf(i));
                                string = string2;
                            }
                        } else {
                            StringBuilder sb = new StringBuilder(String.valueOf(primaryDataMarkerColumn).length() + 78);
                            sb.append("Missing value for markerColumn: ");
                            sb.append(primaryDataMarkerColumn);
                            sb.append(", at row: ");
                            sb.append(i);
                            sb.append(", for window: ");
                            sb.append(windowIndex);
                            throw new NullPointerException(sb.toString());
                        }
                    }
                }
                this.zaa = true;
            }
        }
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    @RecentlyNonNull
    @KeepForSdk
    public final T get(int i) {
        int i2;
        int intValue;
        zab();
        int zaa = zaa(i);
        if (i < 0) {
            i2 = 0;
        } else if (i == this.zab.size()) {
            i2 = 0;
        } else {
            if (i == this.zab.size() - 1) {
                intValue = ((DataHolder) Preconditions.checkNotNull(this.mDataHolder)).getCount() - this.zab.get(i).intValue();
            } else {
                intValue = this.zab.get(i + 1).intValue() - this.zab.get(i).intValue();
            }
            if (intValue == 1) {
                int zaa2 = zaa(i);
                int windowIndex = ((DataHolder) Preconditions.checkNotNull(this.mDataHolder)).getWindowIndex(zaa2);
                String childDataMarkerColumn = getChildDataMarkerColumn();
                if (childDataMarkerColumn != null) {
                    i2 = this.mDataHolder.getString(childDataMarkerColumn, zaa2, windowIndex) == null ? 0 : 1;
                } else {
                    i2 = 1;
                }
            } else {
                i2 = intValue;
            }
        }
        return getEntry(zaa, i2);
    }

    @RecentlyNullable
    @KeepForSdk
    protected String getChildDataMarkerColumn() {
        return null;
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    @KeepForSdk
    public int getCount() {
        zab();
        return this.zab.size();
    }

    @RecentlyNonNull
    @KeepForSdk
    protected abstract T getEntry(int i, int i2);

    @RecentlyNonNull
    @KeepForSdk
    protected abstract String getPrimaryDataMarkerColumn();

    final int zaa(int i) {
        if (i < 0 || i >= this.zab.size()) {
            StringBuilder sb = new StringBuilder(53);
            sb.append("Position ");
            sb.append(i);
            sb.append(" is out of bounds for this buffer");
            throw new IllegalArgumentException(sb.toString());
        }
        return this.zab.get(i).intValue();
    }
}
