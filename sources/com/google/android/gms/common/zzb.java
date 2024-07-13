package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzb implements Parcelable.Creator<ConnectionResult> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ ConnectionResult createFromParcel(Parcel parcel) {
        int i;
        PendingIntent pendingIntent;
        String str;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        int i3 = 0;
        PendingIntent pendingIntent2 = null;
        String str2 = null;
        while (true) {
            String str3 = str2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new ConnectionResult(i2, i3, pendingIntent2, str3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i3;
                    pendingIntent = pendingIntent2;
                    str = str3;
                    break;
                case 2:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    pendingIntent = pendingIntent2;
                    str = str3;
                    break;
                case 3:
                    pendingIntent = (PendingIntent) SafeParcelReader.createParcelable(parcel, readHeader, PendingIntent.CREATOR);
                    i = i3;
                    str = str3;
                    break;
                case 4:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    i = i3;
                    pendingIntent = pendingIntent2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    i = i3;
                    pendingIntent = pendingIntent2;
                    str = str3;
                    break;
            }
            i3 = i;
            pendingIntent2 = pendingIntent;
            str2 = str;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ ConnectionResult[] newArray(int i) {
        return new ConnectionResult[i];
    }
}
