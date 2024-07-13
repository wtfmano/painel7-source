package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public final class zzl implements Parcelable.Creator<GetServiceRequest> {
    public static void zza(GetServiceRequest getServiceRequest, Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getServiceRequest.zza);
        SafeParcelWriter.writeInt(parcel, 2, getServiceRequest.zzb);
        SafeParcelWriter.writeInt(parcel, 3, getServiceRequest.zzc);
        SafeParcelWriter.writeString(parcel, 4, getServiceRequest.zzd, false);
        SafeParcelWriter.writeIBinder(parcel, 5, getServiceRequest.zze, false);
        SafeParcelWriter.writeTypedArray(parcel, 6, getServiceRequest.zzf, i, false);
        SafeParcelWriter.writeBundle(parcel, 7, getServiceRequest.zzg, false);
        SafeParcelWriter.writeParcelable(parcel, 8, getServiceRequest.zzh, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 10, getServiceRequest.zzi, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 11, getServiceRequest.zzj, i, false);
        SafeParcelWriter.writeBoolean(parcel, 12, getServiceRequest.zzk);
        SafeParcelWriter.writeInt(parcel, 13, getServiceRequest.zzl);
        SafeParcelWriter.writeBoolean(parcel, 14, getServiceRequest.zzm);
        SafeParcelWriter.writeString(parcel, 15, getServiceRequest.zza(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ GetServiceRequest createFromParcel(Parcel parcel) {
        int i;
        int i2;
        String str;
        IBinder iBinder;
        Scope[] scopeArr;
        Bundle bundle;
        Account account;
        Feature[] featureArr;
        Feature[] featureArr2;
        boolean z;
        int i3;
        boolean z2;
        String str2;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        String str3 = null;
        IBinder iBinder2 = null;
        Scope[] scopeArr2 = null;
        Bundle bundle2 = null;
        Account account2 = null;
        Feature[] featureArr3 = null;
        Feature[] featureArr4 = null;
        boolean z3 = false;
        int i7 = 0;
        boolean z4 = false;
        String str4 = null;
        while (true) {
            String str5 = str4;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new GetServiceRequest(i4, i5, i6, str3, iBinder2, scopeArr2, bundle2, account2, featureArr3, featureArr4, z3, i7, z4, str5);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i4 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 2:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 3:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i5;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 4:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 5:
                    iBinder = SafeParcelReader.readIBinder(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 6:
                    scopeArr = (Scope[]) SafeParcelReader.createTypedArray(parcel, readHeader, Scope.CREATOR);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 7:
                    bundle = SafeParcelReader.createBundle(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 8:
                    account = (Account) SafeParcelReader.createParcelable(parcel, readHeader, Account.CREATOR);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 9:
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 10:
                    featureArr = (Feature[]) SafeParcelReader.createTypedArray(parcel, readHeader, Feature.CREATOR);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 11:
                    featureArr2 = (Feature[]) SafeParcelReader.createTypedArray(parcel, readHeader, Feature.CREATOR);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 12:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    i3 = i7;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 13:
                    i3 = SafeParcelReader.readInt(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    z2 = z4;
                    str2 = str5;
                    break;
                case 14:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    str2 = str5;
                    break;
                case 15:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    i = i5;
                    i2 = i6;
                    str = str3;
                    iBinder = iBinder2;
                    scopeArr = scopeArr2;
                    bundle = bundle2;
                    account = account2;
                    featureArr = featureArr3;
                    featureArr2 = featureArr4;
                    z = z3;
                    i3 = i7;
                    z2 = z4;
                    break;
            }
            i5 = i;
            i6 = i2;
            str3 = str;
            iBinder2 = iBinder;
            scopeArr2 = scopeArr;
            bundle2 = bundle;
            account2 = account;
            featureArr3 = featureArr;
            featureArr4 = featureArr2;
            z3 = z;
            i7 = i3;
            z4 = z2;
            str4 = str2;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ GetServiceRequest[] newArray(int i) {
        return new GetServiceRequest[i];
    }
}
