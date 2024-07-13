package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
/* loaded from: classes.dex */
public abstract class zzaa extends com.google.android.gms.internal.common.zzb implements IGmsCallbacks {
    public zzaa() {
        super("com.google.android.gms.common.internal.IGmsCallbacks");
    }

    @Override // com.google.android.gms.internal.common.zzb
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                onPostInitComplete(parcel.readInt(), parcel.readStrongBinder(), (Bundle) com.google.android.gms.internal.common.zzc.zzc(parcel, Bundle.CREATOR));
                break;
            case 2:
                zzb(parcel.readInt(), (Bundle) com.google.android.gms.internal.common.zzc.zzc(parcel, Bundle.CREATOR));
                break;
            case 3:
                zzc(parcel.readInt(), parcel.readStrongBinder(), (zzi) com.google.android.gms.internal.common.zzc.zzc(parcel, zzi.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
