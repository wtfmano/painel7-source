package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zah implements Parcelable.Creator<WebImage> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ WebImage createFromParcel(Parcel parcel) {
        Uri uri;
        int i;
        int i2;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i3 = 0;
        Uri uri2 = null;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int i6 = i5;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new WebImage(i3, uri2, i4, i6);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i3 = SafeParcelReader.readInt(parcel, readHeader);
                    uri = uri2;
                    i = i4;
                    i2 = i6;
                    break;
                case 2:
                    uri = (Uri) SafeParcelReader.createParcelable(parcel, readHeader, Uri.CREATOR);
                    i = i4;
                    i2 = i6;
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    uri = uri2;
                    i2 = i6;
                    break;
                case 4:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    uri = uri2;
                    i = i4;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    uri = uri2;
                    i = i4;
                    i2 = i6;
                    break;
            }
            uri2 = uri;
            i4 = i;
            i5 = i2;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ WebImage[] newArray(int i) {
        return new WebImage[i];
    }
}
