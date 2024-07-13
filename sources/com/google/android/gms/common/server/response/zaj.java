package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.response.FastJsonResponse;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaj implements Parcelable.Creator<FastJsonResponse.Field> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ FastJsonResponse.Field createFromParcel(Parcel parcel) {
        int i;
        boolean z;
        int i2;
        boolean z2;
        String str;
        int i3;
        String str2;
        com.google.android.gms.common.server.converter.zaa zaaVar;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i4 = 0;
        int i5 = 0;
        boolean z3 = false;
        int i6 = 0;
        boolean z4 = false;
        String str3 = null;
        int i7 = 0;
        String str4 = null;
        com.google.android.gms.common.server.converter.zaa zaaVar2 = null;
        while (true) {
            com.google.android.gms.common.server.converter.zaa zaaVar3 = zaaVar2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new FastJsonResponse.Field(i4, i5, z3, i6, z4, str3, i7, str4, zaaVar3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i4 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i5;
                    z = z3;
                    i2 = i6;
                    z2 = z4;
                    str = str3;
                    i3 = i7;
                    str2 = str4;
                    zaaVar = zaaVar3;
                    break;
                case 2:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    z = z3;
                    i2 = i6;
                    z2 = z4;
                    str = str3;
                    i3 = i7;
                    str2 = str4;
                    zaaVar = zaaVar3;
                    break;
                case 3:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    z2 = z4;
                    str = str3;
                    i3 = i7;
                    str2 = str4;
                    zaaVar = zaaVar3;
                    break;
                case 4:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i5;
                    z = z3;
                    z2 = z4;
                    str = str3;
                    i3 = i7;
                    str2 = str4;
                    zaaVar = zaaVar3;
                    break;
                case 5:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    i = i5;
                    z = z3;
                    i2 = i6;
                    str = str3;
                    i3 = i7;
                    str2 = str4;
                    zaaVar = zaaVar3;
                    break;
                case 6:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    i = i5;
                    z = z3;
                    i2 = i6;
                    z2 = z4;
                    i3 = i7;
                    str2 = str4;
                    zaaVar = zaaVar3;
                    break;
                case 7:
                    i3 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i5;
                    z = z3;
                    i2 = i6;
                    z2 = z4;
                    str = str3;
                    str2 = str4;
                    zaaVar = zaaVar3;
                    break;
                case 8:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    i = i5;
                    z = z3;
                    i2 = i6;
                    z2 = z4;
                    str = str3;
                    i3 = i7;
                    zaaVar = zaaVar3;
                    break;
                case 9:
                    zaaVar = (com.google.android.gms.common.server.converter.zaa) SafeParcelReader.createParcelable(parcel, readHeader, com.google.android.gms.common.server.converter.zaa.CREATOR);
                    i = i5;
                    z = z3;
                    i2 = i6;
                    z2 = z4;
                    str = str3;
                    i3 = i7;
                    str2 = str4;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    i = i5;
                    z = z3;
                    i2 = i6;
                    z2 = z4;
                    str = str3;
                    i3 = i7;
                    str2 = str4;
                    zaaVar = zaaVar3;
                    break;
            }
            i5 = i;
            z3 = z;
            i6 = i2;
            z4 = z2;
            str3 = str;
            i7 = i3;
            str4 = str2;
            zaaVar2 = zaaVar;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ FastJsonResponse.Field[] newArray(int i) {
        return new FastJsonResponse.Field[i];
    }
}
