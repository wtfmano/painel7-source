package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.zat;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaj implements Parcelable.Creator<zai> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zai createFromParcel(Parcel parcel) {
        zat zatVar;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        zat zatVar2 = null;
        while (true) {
            zat zatVar3 = zatVar2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zai(i, zatVar3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    zatVar = zatVar3;
                    break;
                case 2:
                    zatVar = (zat) SafeParcelReader.createParcelable(parcel, readHeader, zat.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    zatVar = zatVar3;
                    break;
            }
            zatVar2 = zatVar;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zai[] newArray(int i) {
        return new zai[i];
    }
}
