package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zap implements Parcelable.Creator<zal> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zal createFromParcel(Parcel parcel) {
        String str;
        ArrayList arrayList;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        String str2 = null;
        ArrayList arrayList2 = null;
        while (true) {
            ArrayList arrayList3 = arrayList2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zal(i, str2, arrayList3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    str = str2;
                    arrayList = arrayList3;
                    break;
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    arrayList = arrayList3;
                    break;
                case 3:
                    arrayList = SafeParcelReader.createTypedList(parcel, readHeader, zam.CREATOR);
                    str = str2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    str = str2;
                    arrayList = arrayList3;
                    break;
            }
            str2 = str;
            arrayList2 = arrayList;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zal[] newArray(int i) {
        return new zal[i];
    }
}
