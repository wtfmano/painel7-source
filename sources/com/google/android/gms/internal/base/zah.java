package com.google.android.gms.internal.base;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zah extends Drawable.ConstantState {
    int zaa;
    int zab;

    public zah(@Nullable zah zahVar) {
        if (zahVar != null) {
            this.zaa = zahVar.zaa;
            this.zab = zahVar.zab;
        }
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final int getChangingConfigurations() {
        return this.zaa;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        return new zai(this);
    }
}
