package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public class ApiException extends Exception {
    @RecentlyNonNull
    @Deprecated
    protected final Status mStatus;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ApiException(@androidx.annotation.RecentlyNonNull com.google.android.gms.common.api.Status r10) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            r6 = r1
            int r6 = r6.getStatusCode()
            r3 = r6
            r6 = r1
            java.lang.String r6 = r6.getStatusMessage()
            if (r6 == 0) goto L4e
            r6 = r1
            java.lang.String r6 = r6.getStatusMessage()
            r2 = r6
        L15:
            r6 = r2
            java.lang.String r6 = java.lang.String.valueOf(r6)
            r4 = r6
            r6 = r4
            int r6 = r6.length()
            r4 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r5 = r6
            r6 = r5
            r7 = r4
            r8 = 13
            int r7 = r7 + 13
            r6.<init>(r7)
            r6 = r5
            r7 = r3
            java.lang.StringBuilder r6 = r6.append(r7)
            r6 = r5
            java.lang.String r7 = ": "
            java.lang.StringBuilder r6 = r6.append(r7)
            r6 = r5
            r7 = r2
            java.lang.StringBuilder r6 = r6.append(r7)
            r6 = r0
            r7 = r5
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            r6 = r0
            r7 = r1
            r6.mStatus = r7
            return
        L4e:
            java.lang.String r6 = ""
            r2 = r6
            goto L15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.ApiException.<init>(com.google.android.gms.common.api.Status):void");
    }

    @NonNull
    public Status getStatus() {
        return this.mStatus;
    }

    public int getStatusCode() {
        return this.mStatus.getStatusCode();
    }

    @RecentlyNullable
    @Deprecated
    public String getStatusMessage() {
        return this.mStatus.getStatusMessage();
    }
}