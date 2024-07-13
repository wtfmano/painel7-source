package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzah implements Parcelable.Creator<RootTelemetryConfiguration> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ RootTelemetryConfiguration createFromParcel(Parcel parcel) {
        boolean z;
        boolean z2;
        int i;
        int i2;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i3 = 0;
        boolean z3 = false;
        boolean z4 = false;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int i6 = i5;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new RootTelemetryConfiguration(i3, z3, z4, i4, i6);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i3 = SafeParcelReader.readInt(parcel, readHeader);
                    z = z3;
                    z2 = z4;
                    i = i4;
                    i2 = i6;
                    break;
                case 2:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    z2 = z4;
                    i = i4;
                    i2 = i6;
                    break;
                case 3:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    z = z3;
                    i = i4;
                    i2 = i6;
                    break;
                case 4:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    z = z3;
                    z2 = z4;
                    i2 = i6;
                    break;
                case 5:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    z = z3;
                    z2 = z4;
                    i = i4;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    z = z3;
                    z2 = z4;
                    i = i4;
                    i2 = i6;
                    break;
            }
            z3 = z;
            z4 = z2;
            i4 = i;
            i5 = i2;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ RootTelemetryConfiguration[] newArray(int i) {
        return new RootTelemetryConfiguration[i];
    }
}
