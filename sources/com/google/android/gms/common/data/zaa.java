package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zaa implements Parcelable.Creator<BitmapTeleporter> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ BitmapTeleporter createFromParcel(Parcel parcel) {
        ParcelFileDescriptor parcelFileDescriptor;
        int i;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        ParcelFileDescriptor parcelFileDescriptor2 = null;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new BitmapTeleporter(i2, parcelFileDescriptor2, i4);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    parcelFileDescriptor = parcelFileDescriptor2;
                    i = i4;
                    break;
                case 2:
                    parcelFileDescriptor = (ParcelFileDescriptor) SafeParcelReader.createParcelable(parcel, readHeader, ParcelFileDescriptor.CREATOR);
                    i = i4;
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    parcelFileDescriptor = parcelFileDescriptor2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    parcelFileDescriptor = parcelFileDescriptor2;
                    i = i4;
                    break;
            }
            parcelFileDescriptor2 = parcelFileDescriptor;
            i3 = i;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ BitmapTeleporter[] newArray(int i) {
        return new BitmapTeleporter[i];
    }
}
