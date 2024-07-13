package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.response.FastJsonResponse;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zak implements Parcelable.Creator<zam> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zam createFromParcel(Parcel parcel) {
        String str;
        FastJsonResponse.Field field;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        String str2 = null;
        FastJsonResponse.Field field2 = null;
        while (true) {
            FastJsonResponse.Field field3 = field2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zam(i, str2, field3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    str = str2;
                    field = field3;
                    break;
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    field = field3;
                    break;
                case 3:
                    field = (FastJsonResponse.Field) SafeParcelReader.createParcelable(parcel, readHeader, FastJsonResponse.Field.CREATOR);
                    str = str2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    str = str2;
                    field = field3;
                    break;
            }
            str2 = str;
            field2 = field;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zam[] newArray(int i) {
        return new zam[i];
    }
}
