package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzb implements Parcelable.Creator<Status> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Status createFromParcel(Parcel parcel) {
        String str;
        PendingIntent pendingIntent;
        ConnectionResult connectionResult;
        int i;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        int i3 = 0;
        String str2 = null;
        PendingIntent pendingIntent2 = null;
        ConnectionResult connectionResult2 = null;
        while (true) {
            ConnectionResult connectionResult3 = connectionResult2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new Status(i2, i3, str2, pendingIntent2, connectionResult3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i3 = SafeParcelReader.readInt(parcel, readHeader);
                    str = str2;
                    pendingIntent = pendingIntent2;
                    connectionResult = connectionResult3;
                    i = i2;
                    break;
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    pendingIntent = pendingIntent2;
                    connectionResult = connectionResult3;
                    i = i2;
                    break;
                case 3:
                    pendingIntent = (PendingIntent) SafeParcelReader.createParcelable(parcel, readHeader, PendingIntent.CREATOR);
                    str = str2;
                    connectionResult = connectionResult3;
                    i = i2;
                    break;
                case 4:
                    connectionResult = (ConnectionResult) SafeParcelReader.createParcelable(parcel, readHeader, ConnectionResult.CREATOR);
                    str = str2;
                    pendingIntent = pendingIntent2;
                    i = i2;
                    break;
                case 1000:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    str = str2;
                    pendingIntent = pendingIntent2;
                    connectionResult = connectionResult3;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    str = str2;
                    pendingIntent = pendingIntent2;
                    connectionResult = connectionResult3;
                    i = i2;
                    break;
            }
            i2 = i;
            str2 = str;
            pendingIntent2 = pendingIntent;
            connectionResult2 = connectionResult;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Status[] newArray(int i) {
        return new Status[i];
    }
}
