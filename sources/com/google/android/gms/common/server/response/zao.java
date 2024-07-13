package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zao implements Parcelable.Creator<zan> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zan createFromParcel(Parcel parcel) {
        ArrayList arrayList;
        String str;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        ArrayList arrayList2 = null;
        String str2 = null;
        while (true) {
            String str3 = str2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zan(i, arrayList2, str3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    arrayList = arrayList2;
                    str = str3;
                    break;
                case 2:
                    arrayList = SafeParcelReader.createTypedList(parcel, readHeader, zal.CREATOR);
                    str = str3;
                    break;
                case 3:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    arrayList = arrayList2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    arrayList = arrayList2;
                    str = str3;
                    break;
            }
            arrayList2 = arrayList;
            str2 = str;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zan[] newArray(int i) {
        return new zan[i];
    }
}
