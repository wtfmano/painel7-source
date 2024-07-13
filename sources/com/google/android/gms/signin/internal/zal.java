package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.zav;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zal implements Parcelable.Creator<zak> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zak createFromParcel(Parcel parcel) {
        ConnectionResult connectionResult;
        zav zavVar;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        ConnectionResult connectionResult2 = null;
        zav zavVar2 = null;
        while (true) {
            zav zavVar3 = zavVar2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zak(i, connectionResult2, zavVar3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    connectionResult = connectionResult2;
                    zavVar = zavVar3;
                    break;
                case 2:
                    connectionResult = (ConnectionResult) SafeParcelReader.createParcelable(parcel, readHeader, ConnectionResult.CREATOR);
                    zavVar = zavVar3;
                    break;
                case 3:
                    zavVar = (zav) SafeParcelReader.createParcelable(parcel, readHeader, zav.CREATOR);
                    connectionResult = connectionResult2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    connectionResult = connectionResult2;
                    zavVar = zavVar3;
                    break;
            }
            connectionResult2 = connectionResult;
            zavVar2 = zavVar;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zak[] newArray(int i) {
        return new zak[i];
    }
}
