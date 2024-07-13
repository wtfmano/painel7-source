package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zab implements Parcelable.Creator<zaa> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zaa createFromParcel(Parcel parcel) {
        int i;
        Intent intent;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        int i3 = 0;
        Intent intent2 = null;
        while (true) {
            Intent intent3 = intent2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zaa(i2, i3, intent3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i3;
                    intent = intent3;
                    break;
                case 2:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    intent = intent3;
                    break;
                case 3:
                    intent = (Intent) SafeParcelReader.createParcelable(parcel, readHeader, Intent.CREATOR);
                    i = i3;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    i = i3;
                    intent = intent3;
                    break;
            }
            i3 = i;
            intent2 = intent;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zaa[] newArray(int i) {
        return new zaa[i];
    }
}
