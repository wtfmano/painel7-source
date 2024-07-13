package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaq implements Parcelable.Creator<SafeParcelResponse> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ SafeParcelResponse createFromParcel(Parcel parcel) {
        Parcel parcel2;
        zan zanVar;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        Parcel parcel3 = null;
        zan zanVar2 = null;
        while (true) {
            zan zanVar3 = zanVar2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new SafeParcelResponse(i, parcel3, zanVar3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    parcel2 = parcel3;
                    zanVar = zanVar3;
                    break;
                case 2:
                    parcel2 = SafeParcelReader.createParcel(parcel, readHeader);
                    zanVar = zanVar3;
                    break;
                case 3:
                    zanVar = (zan) SafeParcelReader.createParcelable(parcel, readHeader, zan.CREATOR);
                    parcel2 = parcel3;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    parcel2 = parcel3;
                    zanVar = zanVar3;
                    break;
            }
            parcel3 = parcel2;
            zanVar2 = zanVar;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ SafeParcelResponse[] newArray(int i) {
        return new SafeParcelResponse[i];
    }
}
