package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzr implements Parcelable.Creator<zzq> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzq createFromParcel(Parcel parcel) {
        String str;
        int i;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        boolean z = false;
        String str2 = null;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zzq(z, str2, i3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    str = str2;
                    i = i3;
                    break;
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    i = i3;
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    str = str2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    str = str2;
                    i = i3;
                    break;
            }
            str2 = str;
            i2 = i;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzq[] newArray(int i) {
        return new zzq[i];
    }
}
