package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public interface IStatusCallback extends IInterface {
    void onResult(@RecentlyNonNull Status status) throws RemoteException;

    /* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
    /* loaded from: classes.dex */
    public static abstract class Stub extends com.google.android.gms.internal.base.zab implements IStatusCallback {
        public Stub() {
            super("com.google.android.gms.common.api.internal.IStatusCallback");
        }

        @Override // com.google.android.gms.internal.base.zab
        protected final boolean zaa(int i, @RecentlyNonNull Parcel parcel, @RecentlyNonNull Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                onResult((Status) com.google.android.gms.internal.base.zac.zab(parcel, Status.CREATOR));
                return true;
            }
            return false;
        }

        @RecentlyNonNull
        public static IStatusCallback asInterface(@RecentlyNonNull IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.api.internal.IStatusCallback");
            if (queryLocalInterface instanceof IStatusCallback) {
                return (IStatusCallback) queryLocalInterface;
            }
            return new zabs(iBinder);
        }
    }
}
