package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzk implements Parcelable.Creator<ConnectionTelemetryConfiguration> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ ConnectionTelemetryConfiguration createFromParcel(Parcel parcel) {
        boolean z;
        boolean z2;
        int[] iArr;
        int i;
        int[] iArr2;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        RootTelemetryConfiguration rootTelemetryConfiguration = null;
        boolean z3 = false;
        boolean z4 = false;
        int[] iArr3 = null;
        int i2 = 0;
        int[] iArr4 = null;
        while (true) {
            int[] iArr5 = iArr4;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new ConnectionTelemetryConfiguration(rootTelemetryConfiguration, z3, z4, iArr3, i2, iArr5);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    rootTelemetryConfiguration = (RootTelemetryConfiguration) SafeParcelReader.createParcelable(parcel, readHeader, RootTelemetryConfiguration.CREATOR);
                    z = z3;
                    z2 = z4;
                    iArr = iArr3;
                    i = i2;
                    iArr2 = iArr5;
                    break;
                case 2:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    z2 = z4;
                    iArr = iArr3;
                    i = i2;
                    iArr2 = iArr5;
                    break;
                case 3:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    z = z3;
                    iArr = iArr3;
                    i = i2;
                    iArr2 = iArr5;
                    break;
                case 4:
                    iArr = SafeParcelReader.createIntArray(parcel, readHeader);
                    z = z3;
                    z2 = z4;
                    i = i2;
                    iArr2 = iArr5;
                    break;
                case 5:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    z = z3;
                    z2 = z4;
                    iArr = iArr3;
                    iArr2 = iArr5;
                    break;
                case 6:
                    iArr2 = SafeParcelReader.createIntArray(parcel, readHeader);
                    z = z3;
                    z2 = z4;
                    iArr = iArr3;
                    i = i2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    z = z3;
                    z2 = z4;
                    iArr = iArr3;
                    i = i2;
                    iArr2 = iArr5;
                    break;
            }
            z3 = z;
            z4 = z2;
            iArr3 = iArr;
            i2 = i;
            iArr4 = iArr2;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ ConnectionTelemetryConfiguration[] newArray(int i) {
        return new ConnectionTelemetryConfiguration[i];
    }
}
