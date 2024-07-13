package com.google.android.gms.common.sqlite;

import android.database.AbstractWindowedCursor;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class CursorWrapper extends android.database.CursorWrapper implements CrossProcessCursor {
    private AbstractWindowedCursor zza;

    /* JADX WARN: Illegal instructions before constructor call */
    @com.google.android.gms.common.annotation.KeepForSdk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public CursorWrapper(@androidx.annotation.RecentlyNonNull android.database.Cursor r6) {
        /*
            r5 = this;
            r0 = r5
            r1 = r6
            r3 = r0
            r4 = r1
            r3.<init>(r4)
            r3 = 0
            r2 = r3
        L9:
            r3 = r2
            r4 = 10
            if (r3 >= r4) goto L1e
            r3 = r1
            boolean r3 = r3 instanceof android.database.CursorWrapper
            if (r3 == 0) goto L1e
            r3 = r1
            android.database.CursorWrapper r3 = (android.database.CursorWrapper) r3
            android.database.Cursor r3 = r3.getWrappedCursor()
            r1 = r3
            int r2 = r2 + 1
            goto L9
        L1e:
            r3 = r1
            boolean r3 = r3 instanceof android.database.AbstractWindowedCursor
            if (r3 != 0) goto L4c
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            r2 = r3
            r3 = r1
            java.lang.Class r3 = r3.getClass()
            java.lang.String r3 = r3.getName()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r0 = r3
            java.lang.String r3 = "Unknown type: "
            r1 = r3
            r3 = r0
            int r3 = r3.length()
            if (r3 == 0) goto L53
            r3 = r1
            r4 = r0
            java.lang.String r3 = r3.concat(r4)
            r0 = r3
        L45:
            r3 = r2
            r4 = r0
            r3.<init>(r4)
            r3 = r2
            throw r3
        L4c:
            r3 = r0
            r4 = r1
            android.database.AbstractWindowedCursor r4 = (android.database.AbstractWindowedCursor) r4
            r3.zza = r4
            return
        L53:
            java.lang.String r3 = new java.lang.String
            r0 = r3
            r3 = r0
            r4 = r1
            r3.<init>(r4)
            goto L45
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.sqlite.CursorWrapper.<init>(android.database.Cursor):void");
    }

    @Override // android.database.CrossProcessCursor
    @KeepForSdk
    public void fillWindow(int i, @RecentlyNonNull CursorWindow cursorWindow) {
        this.zza.fillWindow(i, cursorWindow);
    }

    @Override // android.database.CrossProcessCursor
    @RecentlyNonNull
    @KeepForSdk
    public CursorWindow getWindow() {
        return this.zza.getWindow();
    }

    @Override // android.database.CursorWrapper
    @RecentlyNonNull
    public final /* bridge */ /* synthetic */ Cursor getWrappedCursor() {
        return this.zza;
    }

    @Override // android.database.CrossProcessCursor
    public final boolean onMove(int i, int i2) {
        return this.zza.onMove(i, i2);
    }

    @KeepForSdk
    public void setWindow(@Nullable CursorWindow cursorWindow) {
        this.zza.setWindow(cursorWindow);
    }
}
