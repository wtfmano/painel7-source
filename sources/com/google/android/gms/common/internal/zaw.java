package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaw implements Parcelable.Creator<zav> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zav createFromParcel(Parcel parcel) {
        IBinder iBinder;
        ConnectionResult connectionResult;
        boolean z;
        boolean z2;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        IBinder iBinder2 = null;
        ConnectionResult connectionResult2 = null;
        boolean z3 = false;
        boolean z4 = false;
        while (true) {
            boolean z5 = z4;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zav(i, iBinder2, connectionResult2, z3, z5);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    iBinder = iBinder2;
                    connectionResult = connectionResult2;
                    z = z3;
                    z2 = z5;
                    break;
                case 2:
                    iBinder = SafeParcelReader.readIBinder(parcel, readHeader);
                    connectionResult = connectionResult2;
                    z = z3;
                    z2 = z5;
                    break;
                case 3:
                    connectionResult = (ConnectionResult) SafeParcelReader.createParcelable(parcel, readHeader, ConnectionResult.CREATOR);
                    iBinder = iBinder2;
                    z = z3;
                    z2 = z5;
                    break;
                case 4:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    iBinder = iBinder2;
                    connectionResult = connectionResult2;
                    z2 = z5;
                    break;
                case 5:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    iBinder = iBinder2;
                    connectionResult = connectionResult2;
                    z = z3;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    iBinder = iBinder2;
                    connectionResult = connectionResult2;
                    z = z3;
                    z2 = z5;
                    break;
            }
            iBinder2 = iBinder;
            connectionResult2 = connectionResult;
            z3 = z;
            z4 = z2;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zav[] newArray(int i) {
        return new zav[i];
    }
}
