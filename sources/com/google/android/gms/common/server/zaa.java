package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaa implements Parcelable.Creator<FavaDiagnosticsEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ FavaDiagnosticsEntity createFromParcel(Parcel parcel) {
        String str;
        int i;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        String str2 = null;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new FavaDiagnosticsEntity(i2, str2, i4);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    str = str2;
                    i = i4;
                    break;
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    i = i4;
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    str = str2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    str = str2;
                    i = i4;
                    break;
            }
            str2 = str;
            i3 = i;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ FavaDiagnosticsEntity[] newArray(int i) {
        return new FavaDiagnosticsEntity[i];
    }
}
