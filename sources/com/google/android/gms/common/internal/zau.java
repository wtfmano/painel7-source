package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zau implements Parcelable.Creator<zat> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zat createFromParcel(Parcel parcel) {
        Account account;
        int i;
        GoogleSignInAccount googleSignInAccount;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        Account account2 = null;
        int i3 = 0;
        GoogleSignInAccount googleSignInAccount2 = null;
        while (true) {
            GoogleSignInAccount googleSignInAccount3 = googleSignInAccount2;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new zat(i2, account2, i3, googleSignInAccount3);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    account = account2;
                    i = i3;
                    googleSignInAccount = googleSignInAccount3;
                    break;
                case 2:
                    account = (Account) SafeParcelReader.createParcelable(parcel, readHeader, Account.CREATOR);
                    i = i3;
                    googleSignInAccount = googleSignInAccount3;
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    account = account2;
                    googleSignInAccount = googleSignInAccount3;
                    break;
                case 4:
                    googleSignInAccount = (GoogleSignInAccount) SafeParcelReader.createParcelable(parcel, readHeader, GoogleSignInAccount.CREATOR);
                    account = account2;
                    i = i3;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    account = account2;
                    i = i3;
                    googleSignInAccount = googleSignInAccount3;
                    break;
            }
            account2 = account;
            i3 = i;
            googleSignInAccount2 = googleSignInAccount;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ zat[] newArray(int i) {
        return new zat[i];
    }
}
