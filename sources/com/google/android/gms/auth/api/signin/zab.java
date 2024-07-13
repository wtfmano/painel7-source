package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zab implements Parcelable.Creator<GoogleSignInAccount> {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ GoogleSignInAccount createFromParcel(Parcel parcel) {
        String str;
        String str2;
        String str3;
        String str4;
        Uri uri;
        String str5;
        long j;
        String str6;
        ArrayList arrayList;
        String str7;
        String str8;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        String str9 = null;
        String str10 = null;
        String str11 = null;
        String str12 = null;
        Uri uri2 = null;
        String str13 = null;
        long j2 = 0;
        String str14 = null;
        ArrayList arrayList2 = null;
        String str15 = null;
        String str16 = null;
        while (true) {
            String str17 = str16;
            if (parcel.dataPosition() >= validateObjectHeader) {
                SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
                return new GoogleSignInAccount(i, str9, str10, str11, str12, uri2, str13, j2, str14, arrayList2, str15, str17);
            }
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 3:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    str = str9;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 4:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    str = str9;
                    str2 = str10;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 5:
                    str4 = SafeParcelReader.createString(parcel, readHeader);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 6:
                    uri = (Uri) SafeParcelReader.createParcelable(parcel, readHeader, Uri.CREATOR);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 7:
                    str5 = SafeParcelReader.createString(parcel, readHeader);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 8:
                    j = SafeParcelReader.readLong(parcel, readHeader);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 9:
                    str6 = SafeParcelReader.createString(parcel, readHeader);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 10:
                    arrayList = SafeParcelReader.createTypedList(parcel, readHeader, Scope.CREATOR);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    str7 = str15;
                    str8 = str17;
                    break;
                case 11:
                    str7 = SafeParcelReader.createString(parcel, readHeader);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str8 = str17;
                    break;
                case 12:
                    str8 = SafeParcelReader.createString(parcel, readHeader);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    str = str9;
                    str2 = str10;
                    str3 = str11;
                    str4 = str12;
                    uri = uri2;
                    str5 = str13;
                    j = j2;
                    str6 = str14;
                    arrayList = arrayList2;
                    str7 = str15;
                    str8 = str17;
                    break;
            }
            str9 = str;
            str10 = str2;
            str11 = str3;
            str12 = str4;
            uri2 = uri;
            str13 = str5;
            j2 = j;
            str14 = str6;
            arrayList2 = arrayList;
            str15 = str7;
            str16 = str8;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ GoogleSignInAccount[] newArray(int i) {
        return new GoogleSignInAccount[i];
    }
}
