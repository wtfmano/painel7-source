package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zab implements Parcelable.Creator<zaa> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zaa createFromParcel(Parcel parcel) {
        StringToIntConverter stringToIntConverter;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        StringToIntConverter stringToIntConverter2 = null;
        while (true) {
            StringToIntConverter stringToIntConverter3 = stringToIntConverter2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zaa(i, stringToIntConverter3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    stringToIntConverter = stringToIntConverter3;
                    break;
                case 2:
                    stringToIntConverter = (StringToIntConverter) SafeParcelReader.createParcelable(parcel, readHeader, StringToIntConverter.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    stringToIntConverter = stringToIntConverter3;
                    break;
            }
            stringToIntConverter2 = stringToIntConverter;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zaa[] newArray(int i) {
        return new zaa[i];
    }
}
