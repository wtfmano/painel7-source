package com.google.android.gms.common.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Asserts;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
final class zaa implements Runnable {
    final /* synthetic */ ImageManager zaa;
    private final Uri zab;
    @Nullable
    private final ParcelFileDescriptor zac;

    public zaa(ImageManager imageManager, @Nullable Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
        this.zaa = imageManager;
        this.zab = uri;
        this.zac = parcelFileDescriptor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        Bitmap bitmap;
        Handler handler;
        Bitmap bitmap2;
        Asserts.checkNotMainThread("LoadBitmapFromDiskRunnable can't be executed in the main thread");
        ParcelFileDescriptor parcelFileDescriptor = this.zac;
        if (parcelFileDescriptor != null) {
            try {
                bitmap2 = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
                z = false;
            } catch (OutOfMemoryError e) {
                String valueOf = String.valueOf(this.zab);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34);
                sb.append("OOM while loading bitmap for uri: ");
                sb.append(valueOf);
                Log.e("ImageManager", sb.toString(), e);
                z = true;
                bitmap2 = null;
            }
            try {
                this.zac.close();
                bitmap = bitmap2;
            } catch (IOException e2) {
                Log.e("ImageManager", "closed failed", e2);
                bitmap = bitmap2;
            }
        } else {
            z = false;
            bitmap = null;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        handler = this.zaa.zae;
        handler.post(new zac(this.zaa, this.zab, bitmap, z, countDownLatch));
        try {
            countDownLatch.await();
        } catch (InterruptedException e3) {
            String valueOf2 = String.valueOf(this.zab);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 32);
            sb2.append("Latch interrupted while posting ");
            sb2.append(valueOf2);
            Log.w("ImageManager", sb2.toString());
        }
    }
}
