package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzj implements Parcelable.Creator<zzi> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzi createFromParcel(Parcel parcel) {
        Feature[] featureArr;
        int i;
        ConnectionTelemetryConfiguration connectionTelemetryConfiguration;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        Bundle bundle = null;
        Feature[] featureArr2 = null;
        int i2 = 0;
        ConnectionTelemetryConfiguration connectionTelemetryConfiguration2 = null;
        while (true) {
            ConnectionTelemetryConfiguration connectionTelemetryConfiguration3 = connectionTelemetryConfiguration2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zzi(bundle, featureArr2, i2, connectionTelemetryConfiguration3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    bundle = SafeParcelReader.createBundle(parcel, readHeader);
                    featureArr = featureArr2;
                    i = i2;
                    connectionTelemetryConfiguration = connectionTelemetryConfiguration3;
                    break;
                case 2:
                    featureArr = (Feature[]) SafeParcelReader.createTypedArray(parcel, readHeader, Feature.CREATOR);
                    i = i2;
                    connectionTelemetryConfiguration = connectionTelemetryConfiguration3;
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    featureArr = featureArr2;
                    connectionTelemetryConfiguration = connectionTelemetryConfiguration3;
                    break;
                case 4:
                    connectionTelemetryConfiguration = (ConnectionTelemetryConfiguration) SafeParcelReader.createParcelable(parcel, readHeader, ConnectionTelemetryConfiguration.CREATOR);
                    featureArr = featureArr2;
                    i = i2;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    featureArr = featureArr2;
                    i = i2;
                    connectionTelemetryConfiguration = connectionTelemetryConfiguration3;
                    break;
            }
            featureArr2 = featureArr;
            i2 = i;
            connectionTelemetryConfiguration2 = connectionTelemetryConfiguration;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zzi[] newArray(int i) {
        return new zzi[i];
    }
}
