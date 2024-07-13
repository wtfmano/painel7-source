package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public final class WrappedDrawableState extends Drawable.ConstantState {
    int mChangingConfigurations;
    Drawable.ConstantState mDrawableState;
    ColorStateList mTint;
    PorterDuff.Mode mTintMode;

    public WrappedDrawableState(@Nullable WrappedDrawableState orig) {
        this.mTint = null;
        this.mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE;
        if (orig != null) {
            this.mChangingConfigurations = orig.mChangingConfigurations;
            this.mDrawableState = orig.mDrawableState;
            this.mTint = orig.mTint;
            this.mTintMode = orig.mTintMode;
        }
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    @NonNull
    public Drawable newDrawable() {
        return newDrawable(null);
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    @NonNull
    public Drawable newDrawable(@Nullable Resources res) {
        return Build.VERSION.SDK_INT >= 21 ? new WrappedDrawableApi21(this, res) : new WrappedDrawableApi14(this, res);
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public int getChangingConfigurations() {
        return (this.mDrawableState != null ? this.mDrawableState.getChangingConfigurations() : 0) | this.mChangingConfigurations;
    }

    public boolean canConstantState() {
        return this.mDrawableState != null;
    }
}
