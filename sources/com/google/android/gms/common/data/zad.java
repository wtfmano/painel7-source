package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zad implements Parcelable.Creator<DataHolder> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ DataHolder createFromParcel(Parcel parcel) {
        CursorWindow[] cursorWindowArr;
        int i;
        Bundle bundle;
        int i2;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i3 = 0;
        String[] strArr = null;
        CursorWindow[] cursorWindowArr2 = null;
        int i4 = 0;
        Bundle bundle2 = null;
        while (true) {
            Bundle bundle3 = bundle2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                DataHolder dataHolder = new DataHolder(i3, strArr, cursorWindowArr2, i4, bundle3);
                dataHolder.zaa();
                return dataHolder;
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    strArr = SafeParcelReader.createStringArray(parcel, readHeader);
                    cursorWindowArr = cursorWindowArr2;
                    i = i4;
                    bundle = bundle3;
                    i2 = i3;
                    break;
                case 2:
                    cursorWindowArr = (CursorWindow[]) SafeParcelReader.createTypedArray(parcel, readHeader, CursorWindow.CREATOR);
                    i = i4;
                    bundle = bundle3;
                    i2 = i3;
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    cursorWindowArr = cursorWindowArr2;
                    bundle = bundle3;
                    i2 = i3;
                    break;
                case 4:
                    bundle = SafeParcelReader.createBundle(parcel, readHeader);
                    cursorWindowArr = cursorWindowArr2;
                    i = i4;
                    i2 = i3;
                    break;
                case 1000:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    cursorWindowArr = cursorWindowArr2;
                    i = i4;
                    bundle = bundle3;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    cursorWindowArr = cursorWindowArr2;
                    i = i4;
                    bundle = bundle3;
                    i2 = i3;
                    break;
            }
            i3 = i2;
            cursorWindowArr2 = cursorWindowArr;
            i4 = i;
            bundle2 = bundle;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ DataHolder[] newArray(int i) {
        return new DataHolder[i];
    }
}
