package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zay implements Parcelable.Creator<zax> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zax createFromParcel(Parcel parcel) {
        int i;
        int i2;
        Scope[] scopeArr;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        Scope[] scopeArr2 = null;
        while (true) {
            Scope[] scopeArr3 = scopeArr2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zax(i3, i4, i5, scopeArr3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i3 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i4;
                    i2 = i5;
                    scopeArr = scopeArr3;
                    break;
                case 2:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    i2 = i5;
                    scopeArr = scopeArr3;
                    break;
                case 3:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i4;
                    scopeArr = scopeArr3;
                    break;
                case 4:
                    scopeArr = (Scope[]) SafeParcelReader.createTypedArray(parcel, readHeader, Scope.CREATOR);
                    i = i4;
                    i2 = i5;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    i = i4;
                    i2 = i5;
                    scopeArr = scopeArr3;
                    break;
            }
            i4 = i;
            i5 = i2;
            scopeArr2 = scopeArr;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zax[] newArray(int i) {
        return new zax[i];
    }
}
