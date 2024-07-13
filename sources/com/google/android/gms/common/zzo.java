package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzo implements Parcelable.Creator<zzn> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzn createFromParcel(Parcel parcel) {
        boolean z;
        boolean z2;
        IBinder iBinder;
        boolean z3;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str = null;
        boolean z4 = false;
        boolean z5 = false;
        IBinder iBinder2 = null;
        boolean z6 = false;
        while (true) {
            boolean z7 = z6;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zzn(str, z4, z5, iBinder2, z7);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    z = z4;
                    z2 = z5;
                    iBinder = iBinder2;
                    z3 = z7;
                    break;
                case 2:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    z2 = z5;
                    iBinder = iBinder2;
                    z3 = z7;
                    break;
                case 3:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    z = z4;
                    iBinder = iBinder2;
                    z3 = z7;
                    break;
                case 4:
                    iBinder = SafeParcelReader.readIBinder(parcel, readHeader);
                    z = z4;
                    z2 = z5;
                    z3 = z7;
                    break;
                case 5:
                    z3 = SafeParcelReader.readBoolean(parcel, readHeader);
                    z = z4;
                    z2 = z5;
                    iBinder = iBinder2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    z = z4;
                    z2 = z5;
                    iBinder = iBinder2;
                    z3 = z7;
                    break;
            }
            z4 = z;
            z5 = z2;
            iBinder2 = iBinder;
            z6 = z3;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzn[] newArray(int i) {
        return new zzn[i];
    }
}
