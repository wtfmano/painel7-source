package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
@SafeParcelable.Class(creator = "WebImageCreator")
/* loaded from: classes.dex */
public final class WebImage extends AbstractSafeParcelable {
    @RecentlyNonNull
    public static final Parcelable.Creator<WebImage> CREATOR = new zah();
    @SafeParcelable.VersionField(id = 1)
    final int zaa;
    @SafeParcelable.Field(getter = "getUrl", id = 2)
    private final Uri zab;
    @SafeParcelable.Field(getter = "getWidth", id = 3)
    private final int zac;
    @SafeParcelable.Field(getter = "getHeight", id = 4)
    private final int zad;

    @SafeParcelable.Constructor
    public WebImage(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) Uri uri, @SafeParcelable.Param(id = 3) int i2, @SafeParcelable.Param(id = 4) int i3) {
        this.zaa = i;
        this.zab = uri;
        this.zac = i2;
        this.zad = i3;
    }

    public int getHeight() {
        return this.zad;
    }

    @RecentlyNonNull
    public Uri getUrl() {
        return this.zab;
    }

    public int getWidth() {
        return this.zac;
    }

    public int hashCode() {
        return Objects.hashCode(this.zab, Integer.valueOf(this.zac), Integer.valueOf(this.zad));
    }

    @RecentlyNonNull
    @KeepForSdk
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ImagesContract.URL, this.zab.toString());
            jSONObject.put("width", this.zac);
            jSONObject.put("height", this.zad);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    @RecentlyNonNull
    public String toString() {
        return String.format(Locale.US, "Image %dx%d %s", Integer.valueOf(this.zac), Integer.valueOf(this.zad), this.zab.toString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeParcelable(parcel, 2, getUrl(), i, false);
        SafeParcelWriter.writeInt(parcel, 3, getWidth());
        SafeParcelWriter.writeInt(parcel, 4, getHeight());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public WebImage(@RecentlyNonNull Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(@RecentlyNonNull Uri uri, int i, int i2) throws IllegalArgumentException {
        this(1, uri, i, i2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WebImage)) {
            return false;
        }
        WebImage webImage = (WebImage) obj;
        return Objects.equal(this.zab, webImage.zab) && this.zac == webImage.zac && this.zad == webImage.zad;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @com.google.android.gms.common.annotation.KeepForSdk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public WebImage(@androidx.annotation.RecentlyNonNull org.json.JSONObject r11) throws java.lang.IllegalArgumentException {
        /*
            r10 = this;
            r0 = r10
            r1 = r11
            android.net.Uri r4 = android.net.Uri.EMPTY
            r2 = r4
            r4 = r1
            java.lang.String r5 = "url"
            boolean r4 = r4.has(r5)
            if (r4 == 0) goto L1c
            r4 = r1
            java.lang.String r5 = "url"
            java.lang.String r4 = r4.getString(r5)     // Catch: org.json.JSONException -> L32
            android.net.Uri r4 = android.net.Uri.parse(r4)     // Catch: org.json.JSONException -> L32
            r3 = r4
            r4 = r3
            r2 = r4
        L1c:
            r4 = r0
            r5 = r2
            r6 = r1
            java.lang.String r7 = "width"
            r8 = 0
            int r6 = r6.optInt(r7, r8)
            r7 = r1
            java.lang.String r8 = "height"
            r9 = 0
            int r7 = r7.optInt(r8, r9)
            r4.<init>(r5, r6, r7)
            return
        L32:
            r4 = move-exception
            goto L1c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.images.WebImage.<init>(org.json.JSONObject):void");
    }
}
