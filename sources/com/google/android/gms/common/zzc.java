package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzc implements Parcelable.Creator<Feature> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Feature createFromParcel(Parcel parcel) {
        int i;
        long j;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str = null;
        int i2 = 0;
        long j2 = -1;
        while (true) {
            long j3 = j2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new Feature(str, i2, j3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    i = i2;
                    j = j3;
                    break;
                case 2:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    j = j3;
                    break;
                case 3:
                    j = SafeParcelReader.readLong(parcel, readHeader);
                    i = i2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    i = i2;
                    j = j3;
                    break;
            }
            i2 = i;
            j2 = j;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Feature[] newArray(int i) {
        return new Feature[i];
    }
}
