package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzt implements Parcelable.Creator<zzs> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzs createFromParcel(Parcel parcel) {
        IBinder iBinder;
        boolean z;
        boolean z2;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str = null;
        IBinder iBinder2 = null;
        boolean z3 = false;
        boolean z4 = false;
        while (true) {
            boolean z5 = z4;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zzs(str, iBinder2, z3, z5);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    iBinder = iBinder2;
                    z = z3;
                    z2 = z5;
                    break;
                case 2:
                    iBinder = SafeParcelReader.readIBinder(parcel, readHeader);
                    z = z3;
                    z2 = z5;
                    break;
                case 3:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    iBinder = iBinder2;
                    z2 = z5;
                    break;
                case 4:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    iBinder = iBinder2;
                    z = z3;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    iBinder = iBinder2;
                    z = z3;
                    z2 = z5;
                    break;
            }
            iBinder2 = iBinder;
            z3 = z;
            z4 = z2;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzs[] newArray(int i) {
        return new zzs[i];
    }
}
