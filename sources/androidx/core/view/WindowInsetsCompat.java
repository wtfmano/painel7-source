package androidx.core.view;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/* loaded from: classes.dex */
public class WindowInsetsCompat {
    @NonNull
    public static final WindowInsetsCompat CONSUMED;
    private static final String TAG = "WindowInsetsCompat";
    private final Impl mImpl;

    static {
        if (Build.VERSION.SDK_INT >= 30) {
            CONSUMED = Impl30.CONSUMED;
        } else {
            CONSUMED = Impl.CONSUMED;
        }
    }

    @RequiresApi(20)
    private WindowInsetsCompat(@NonNull WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImpl = new Impl30(this, insets);
        } else if (Build.VERSION.SDK_INT >= 29) {
            this.mImpl = new Impl29(this, insets);
        } else if (Build.VERSION.SDK_INT >= 28) {
            this.mImpl = new Impl28(this, insets);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new Impl21(this, insets);
        } else if (Build.VERSION.SDK_INT >= 20) {
            this.mImpl = new Impl20(this, insets);
        } else {
            this.mImpl = new Impl(this);
        }
    }

    public WindowInsetsCompat(@Nullable WindowInsetsCompat src) {
        if (src != null) {
            Impl srcImpl = src.mImpl;
            if (Build.VERSION.SDK_INT >= 30 && (srcImpl instanceof Impl30)) {
                this.mImpl = new Impl30(this, (Impl30) srcImpl);
            } else if (Build.VERSION.SDK_INT >= 29 && (srcImpl instanceof Impl29)) {
                this.mImpl = new Impl29(this, (Impl29) srcImpl);
            } else if (Build.VERSION.SDK_INT >= 28 && (srcImpl instanceof Impl28)) {
                this.mImpl = new Impl28(this, (Impl28) srcImpl);
            } else if (Build.VERSION.SDK_INT >= 21 && (srcImpl instanceof Impl21)) {
                this.mImpl = new Impl21(this, (Impl21) srcImpl);
            } else if (Build.VERSION.SDK_INT >= 20 && (srcImpl instanceof Impl20)) {
                this.mImpl = new Impl20(this, (Impl20) srcImpl);
            } else {
                this.mImpl = new Impl(this);
            }
            srcImpl.copyWindowDataInto(this);
            return;
        }
        this.mImpl = new Impl(this);
    }

    @NonNull
    @RequiresApi(20)
    public static WindowInsetsCompat toWindowInsetsCompat(@NonNull WindowInsets insets) {
        return toWindowInsetsCompat(insets, null);
    }

    @NonNull
    @RequiresApi(20)
    public static WindowInsetsCompat toWindowInsetsCompat(@NonNull WindowInsets insets, @Nullable View view) {
        WindowInsetsCompat wic = new WindowInsetsCompat((WindowInsets) Preconditions.checkNotNull(insets));
        if (view != null && view.isAttachedToWindow()) {
            wic.setRootWindowInsets(ViewCompat.getRootWindowInsets(view));
            wic.copyRootViewBounds(view.getRootView());
        }
        return wic;
    }

    @Deprecated
    public int getSystemWindowInsetLeft() {
        return this.mImpl.getSystemWindowInsets().left;
    }

    @Deprecated
    public int getSystemWindowInsetTop() {
        return this.mImpl.getSystemWindowInsets().top;
    }

    @Deprecated
    public int getSystemWindowInsetRight() {
        return this.mImpl.getSystemWindowInsets().right;
    }

    @Deprecated
    public int getSystemWindowInsetBottom() {
        return this.mImpl.getSystemWindowInsets().bottom;
    }

    @Deprecated
    public boolean hasSystemWindowInsets() {
        return !this.mImpl.getSystemWindowInsets().equals(Insets.NONE);
    }

    public boolean hasInsets() {
        return (getInsets(Type.all()).equals(Insets.NONE) && getInsetsIgnoringVisibility(Type.all() ^ Type.ime()).equals(Insets.NONE) && getDisplayCutout() == null) ? false : true;
    }

    public boolean isConsumed() {
        return this.mImpl.isConsumed();
    }

    public boolean isRound() {
        return this.mImpl.isRound();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat consumeSystemWindowInsets() {
        return this.mImpl.consumeSystemWindowInsets();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(int left, int top, int right, int bottom) {
        return new Builder(this).setSystemWindowInsets(Insets.of(left, top, right, bottom)).build();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(@NonNull Rect systemWindowInsets) {
        return new Builder(this).setSystemWindowInsets(Insets.of(systemWindowInsets)).build();
    }

    @Deprecated
    public int getStableInsetTop() {
        return this.mImpl.getStableInsets().top;
    }

    @Deprecated
    public int getStableInsetLeft() {
        return this.mImpl.getStableInsets().left;
    }

    @Deprecated
    public int getStableInsetRight() {
        return this.mImpl.getStableInsets().right;
    }

    @Deprecated
    public int getStableInsetBottom() {
        return this.mImpl.getStableInsets().bottom;
    }

    @Deprecated
    public boolean hasStableInsets() {
        return !this.mImpl.getStableInsets().equals(Insets.NONE);
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat consumeStableInsets() {
        return this.mImpl.consumeStableInsets();
    }

    @Nullable
    public DisplayCutoutCompat getDisplayCutout() {
        return this.mImpl.getDisplayCutout();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat consumeDisplayCutout() {
        return this.mImpl.consumeDisplayCutout();
    }

    @NonNull
    @Deprecated
    public Insets getSystemWindowInsets() {
        return this.mImpl.getSystemWindowInsets();
    }

    @NonNull
    @Deprecated
    public Insets getStableInsets() {
        return this.mImpl.getStableInsets();
    }

    @NonNull
    @Deprecated
    public Insets getMandatorySystemGestureInsets() {
        return this.mImpl.getMandatorySystemGestureInsets();
    }

    @NonNull
    @Deprecated
    public Insets getTappableElementInsets() {
        return this.mImpl.getTappableElementInsets();
    }

    @NonNull
    @Deprecated
    public Insets getSystemGestureInsets() {
        return this.mImpl.getSystemGestureInsets();
    }

    @NonNull
    public WindowInsetsCompat inset(@NonNull Insets insets) {
        return inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    @NonNull
    public WindowInsetsCompat inset(@IntRange(from = 0) int left, @IntRange(from = 0) int top, @IntRange(from = 0) int right, @IntRange(from = 0) int bottom) {
        return this.mImpl.inset(left, top, right, bottom);
    }

    @NonNull
    public Insets getInsets(int typeMask) {
        return this.mImpl.getInsets(typeMask);
    }

    @NonNull
    public Insets getInsetsIgnoringVisibility(int typeMask) {
        return this.mImpl.getInsetsIgnoringVisibility(typeMask);
    }

    public boolean isVisible(int typeMask) {
        return this.mImpl.isVisible(typeMask);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WindowInsetsCompat)) {
            return false;
        }
        WindowInsetsCompat other = (WindowInsetsCompat) o;
        return ObjectsCompat.equals(this.mImpl, other.mImpl);
    }

    public int hashCode() {
        if (this.mImpl == null) {
            return 0;
        }
        return this.mImpl.hashCode();
    }

    @Nullable
    @RequiresApi(20)
    public WindowInsets toWindowInsets() {
        if (this.mImpl instanceof Impl20) {
            return ((Impl20) this.mImpl).mPlatformInsets;
        }
        return null;
    }

    /* loaded from: classes.dex */
    public static class Impl {
        @NonNull
        static final WindowInsetsCompat CONSUMED = new Builder().build().consumeDisplayCutout().consumeStableInsets().consumeSystemWindowInsets();
        final WindowInsetsCompat mHost;

        Impl(@NonNull WindowInsetsCompat host) {
            this.mHost = host;
        }

        boolean isRound() {
            return false;
        }

        boolean isConsumed() {
            return false;
        }

        @NonNull
        WindowInsetsCompat consumeSystemWindowInsets() {
            return this.mHost;
        }

        @NonNull
        WindowInsetsCompat consumeStableInsets() {
            return this.mHost;
        }

        @Nullable
        DisplayCutoutCompat getDisplayCutout() {
            return null;
        }

        @NonNull
        WindowInsetsCompat consumeDisplayCutout() {
            return this.mHost;
        }

        @NonNull
        Insets getSystemWindowInsets() {
            return Insets.NONE;
        }

        @NonNull
        Insets getStableInsets() {
            return Insets.NONE;
        }

        @NonNull
        Insets getSystemGestureInsets() {
            return getSystemWindowInsets();
        }

        @NonNull
        Insets getMandatorySystemGestureInsets() {
            return getSystemWindowInsets();
        }

        @NonNull
        Insets getTappableElementInsets() {
            return getSystemWindowInsets();
        }

        @NonNull
        WindowInsetsCompat inset(int left, int top, int right, int bottom) {
            return CONSUMED;
        }

        @NonNull
        Insets getInsets(int typeMask) {
            return Insets.NONE;
        }

        @NonNull
        Insets getInsetsIgnoringVisibility(int typeMask) {
            if ((typeMask & 8) != 0) {
                throw new IllegalArgumentException("Unable to query the maximum insets for IME");
            }
            return Insets.NONE;
        }

        boolean isVisible(int typeMask) {
            return true;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof Impl) {
                Impl impl = (Impl) o;
                return isRound() == impl.isRound() && isConsumed() == impl.isConsumed() && ObjectsCompat.equals(getSystemWindowInsets(), impl.getSystemWindowInsets()) && ObjectsCompat.equals(getStableInsets(), impl.getStableInsets()) && ObjectsCompat.equals(getDisplayCutout(), impl.getDisplayCutout());
            }
            return false;
        }

        public int hashCode() {
            return ObjectsCompat.hash(Boolean.valueOf(isRound()), Boolean.valueOf(isConsumed()), getSystemWindowInsets(), getStableInsets(), getDisplayCutout());
        }

        void setRootWindowInsets(@Nullable WindowInsetsCompat rootWindowInsets) {
        }

        void setRootViewData(@NonNull Insets visibleInsets) {
        }

        void copyRootViewBounds(@NonNull View rootView) {
        }

        void copyWindowDataInto(@NonNull WindowInsetsCompat other) {
        }

        public void setOverriddenInsets(Insets[] insetsTypeMask) {
        }

        public void setStableInsets(Insets stableInsets) {
        }
    }

    @RequiresApi(20)
    /* loaded from: classes.dex */
    public static class Impl20 extends Impl {
        private static Class<?> sAttachInfoClass;
        private static Field sAttachInfoField;
        private static Method sGetViewRootImplMethod;
        private static Class<?> sViewRootImplClass;
        private static Field sVisibleInsetsField;
        private static boolean sVisibleRectReflectionFetched = false;
        private Insets[] mOverriddenInsets;
        @NonNull
        final WindowInsets mPlatformInsets;
        Insets mRootViewVisibleInsets;
        private WindowInsetsCompat mRootWindowInsets;
        private Insets mSystemWindowInsets;

        Impl20(@NonNull WindowInsetsCompat host, @NonNull WindowInsets insets) {
            super(host);
            this.mSystemWindowInsets = null;
            this.mPlatformInsets = insets;
        }

        Impl20(@NonNull WindowInsetsCompat host, @NonNull Impl20 other) {
            this(host, new WindowInsets(other.mPlatformInsets));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        boolean isRound() {
            return this.mPlatformInsets.isRound();
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        public Insets getInsets(int typeMask) {
            return getInsets(typeMask, false);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        public Insets getInsetsIgnoringVisibility(int typeMask) {
            return getInsets(typeMask, true);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @SuppressLint({"WrongConstant"})
        boolean isVisible(int typeMask) {
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) != 0 && !isTypeVisible(i)) {
                    return false;
                }
            }
            return true;
        }

        @NonNull
        @SuppressLint({"WrongConstant"})
        private Insets getInsets(int typeMask, boolean ignoreVisibility) {
            Insets result = Insets.NONE;
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) != 0) {
                    result = Insets.max(result, getInsetsForType(i, ignoreVisibility));
                }
            }
            return result;
        }

        @NonNull
        protected Insets getInsetsForType(int type, boolean ignoreVisibility) {
            DisplayCutoutCompat cutout;
            switch (type) {
                case 1:
                    if (ignoreVisibility) {
                        return Insets.of(0, Math.max(getRootStableInsets().top, getSystemWindowInsets().top), 0, 0);
                    }
                    return Insets.of(0, getSystemWindowInsets().top, 0, 0);
                case 2:
                    if (ignoreVisibility) {
                        Insets rootStable = getRootStableInsets();
                        Insets stable = getStableInsets();
                        return Insets.of(Math.max(rootStable.left, stable.left), 0, Math.max(rootStable.right, stable.right), Math.max(rootStable.bottom, stable.bottom));
                    }
                    Insets systemWindow = getSystemWindowInsets();
                    Insets rootStable2 = this.mRootWindowInsets != null ? this.mRootWindowInsets.getStableInsets() : null;
                    int bottom = systemWindow.bottom;
                    if (rootStable2 != null) {
                        bottom = Math.min(bottom, rootStable2.bottom);
                    }
                    return Insets.of(systemWindow.left, 0, systemWindow.right, bottom);
                case 8:
                    Insets overriddenInsets = this.mOverriddenInsets != null ? this.mOverriddenInsets[Type.indexOf(8)] : null;
                    if (overriddenInsets == null) {
                        Insets systemWindow2 = getSystemWindowInsets();
                        Insets rootStable3 = getRootStableInsets();
                        if (systemWindow2.bottom > rootStable3.bottom) {
                            return Insets.of(0, 0, 0, systemWindow2.bottom);
                        } else if (this.mRootViewVisibleInsets != null && !this.mRootViewVisibleInsets.equals(Insets.NONE) && this.mRootViewVisibleInsets.bottom > rootStable3.bottom) {
                            return Insets.of(0, 0, 0, this.mRootViewVisibleInsets.bottom);
                        } else {
                            return Insets.NONE;
                        }
                    }
                    return overriddenInsets;
                case 16:
                    return getSystemGestureInsets();
                case 32:
                    return getMandatorySystemGestureInsets();
                case 64:
                    return getTappableElementInsets();
                case 128:
                    if (this.mRootWindowInsets != null) {
                        cutout = this.mRootWindowInsets.getDisplayCutout();
                    } else {
                        cutout = getDisplayCutout();
                    }
                    if (cutout != null) {
                        return Insets.of(cutout.getSafeInsetLeft(), cutout.getSafeInsetTop(), cutout.getSafeInsetRight(), cutout.getSafeInsetBottom());
                    }
                    return Insets.NONE;
                default:
                    return Insets.NONE;
            }
        }

        protected boolean isTypeVisible(int type) {
            switch (type) {
                case 1:
                case 2:
                case 8:
                case 128:
                    return !getInsetsForType(type, false).equals(Insets.NONE);
                case 4:
                    return false;
                default:
                    return true;
            }
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        final Insets getSystemWindowInsets() {
            if (this.mSystemWindowInsets == null) {
                this.mSystemWindowInsets = Insets.of(this.mPlatformInsets.getSystemWindowInsetLeft(), this.mPlatformInsets.getSystemWindowInsetTop(), this.mPlatformInsets.getSystemWindowInsetRight(), this.mPlatformInsets.getSystemWindowInsetBottom());
            }
            return this.mSystemWindowInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat inset(int left, int top, int right, int bottom) {
            Builder b = new Builder(WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets));
            b.setSystemWindowInsets(WindowInsetsCompat.insetInsets(getSystemWindowInsets(), left, top, right, bottom));
            b.setStableInsets(WindowInsetsCompat.insetInsets(getStableInsets(), left, top, right, bottom));
            return b.build();
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void copyWindowDataInto(@NonNull WindowInsetsCompat other) {
            other.setRootWindowInsets(this.mRootWindowInsets);
            other.setRootViewData(this.mRootViewVisibleInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void setRootWindowInsets(@Nullable WindowInsetsCompat rootWindowInsets) {
            this.mRootWindowInsets = rootWindowInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void setRootViewData(@NonNull Insets visibleInsets) {
            this.mRootViewVisibleInsets = visibleInsets;
        }

        private Insets getRootStableInsets() {
            return this.mRootWindowInsets != null ? this.mRootWindowInsets.getStableInsets() : Insets.NONE;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void copyRootViewBounds(@NonNull View rootView) {
            Insets visibleInsets = getVisibleInsets(rootView);
            if (visibleInsets == null) {
                visibleInsets = Insets.NONE;
            }
            setRootViewData(visibleInsets);
        }

        @Nullable
        private Insets getVisibleInsets(@NonNull View rootView) {
            Insets insets = null;
            if (Build.VERSION.SDK_INT >= 30) {
                throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
            }
            if (!sVisibleRectReflectionFetched) {
                loadReflectionField();
            }
            if (sGetViewRootImplMethod != null && sAttachInfoClass != null && sVisibleInsetsField != null) {
                try {
                    Object viewRootImpl = sGetViewRootImplMethod.invoke(rootView, new Object[0]);
                    if (viewRootImpl == null) {
                        Log.w(WindowInsetsCompat.TAG, "Failed to get visible insets. getViewRootImpl() returned null from the provided view. This means that the view is either not attached or the method has been overridden", new NullPointerException());
                    } else {
                        Object mAttachInfo = sAttachInfoField.get(viewRootImpl);
                        Rect visibleRect = (Rect) sVisibleInsetsField.get(mAttachInfo);
                        if (visibleRect != null) {
                            insets = Insets.of(visibleRect);
                        }
                    }
                } catch (ReflectiveOperationException e) {
                    Log.e(WindowInsetsCompat.TAG, "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
                }
            }
            return insets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void setOverriddenInsets(Insets[] insetsTypeMask) {
            this.mOverriddenInsets = insetsTypeMask;
        }

        @SuppressLint({"PrivateApi"})
        private static void loadReflectionField() {
            try {
                sGetViewRootImplMethod = View.class.getDeclaredMethod("getViewRootImpl", new Class[0]);
                sViewRootImplClass = Class.forName("android.view.ViewRootImpl");
                sAttachInfoClass = Class.forName("android.view.View$AttachInfo");
                sVisibleInsetsField = sAttachInfoClass.getDeclaredField("mVisibleInsets");
                sAttachInfoField = sViewRootImplClass.getDeclaredField("mAttachInfo");
                sVisibleInsetsField.setAccessible(true);
                sAttachInfoField.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                Log.e(WindowInsetsCompat.TAG, "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
            }
            sVisibleRectReflectionFetched = true;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public boolean equals(Object o) {
            if (super.equals(o)) {
                Impl20 impl20 = (Impl20) o;
                return Objects.equals(this.mRootViewVisibleInsets, impl20.mRootViewVisibleInsets);
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class Impl21 extends Impl20 {
        private Insets mStableInsets;

        Impl21(@NonNull WindowInsetsCompat host, @NonNull WindowInsets insets) {
            super(host, insets);
            this.mStableInsets = null;
        }

        Impl21(@NonNull WindowInsetsCompat host, @NonNull Impl21 other) {
            super(host, other);
            this.mStableInsets = null;
            this.mStableInsets = other.mStableInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        boolean isConsumed() {
            return this.mPlatformInsets.isConsumed();
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat consumeStableInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeStableInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat consumeSystemWindowInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeSystemWindowInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        final Insets getStableInsets() {
            if (this.mStableInsets == null) {
                this.mStableInsets = Insets.of(this.mPlatformInsets.getStableInsetLeft(), this.mPlatformInsets.getStableInsetTop(), this.mPlatformInsets.getStableInsetRight(), this.mPlatformInsets.getStableInsetBottom());
            }
            return this.mStableInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void setStableInsets(@Nullable Insets stableInsets) {
            this.mStableInsets = stableInsets;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(28)
    /* loaded from: classes.dex */
    public static class Impl28 extends Impl21 {
        Impl28(@NonNull WindowInsetsCompat host, @NonNull WindowInsets insets) {
            super(host, insets);
        }

        Impl28(@NonNull WindowInsetsCompat host, @NonNull Impl28 other) {
            super(host, other);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @Nullable
        DisplayCutoutCompat getDisplayCutout() {
            return DisplayCutoutCompat.wrap(this.mPlatformInsets.getDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat consumeDisplayCutout() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof Impl28) {
                Impl28 otherImpl28 = (Impl28) o;
                return Objects.equals(this.mPlatformInsets, otherImpl28.mPlatformInsets) && Objects.equals(this.mRootViewVisibleInsets, otherImpl28.mRootViewVisibleInsets);
            }
            return false;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public int hashCode() {
            return this.mPlatformInsets.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(29)
    /* loaded from: classes.dex */
    public static class Impl29 extends Impl28 {
        private Insets mMandatorySystemGestureInsets;
        private Insets mSystemGestureInsets;
        private Insets mTappableElementInsets;

        Impl29(@NonNull WindowInsetsCompat host, @NonNull WindowInsets insets) {
            super(host, insets);
            this.mSystemGestureInsets = null;
            this.mMandatorySystemGestureInsets = null;
            this.mTappableElementInsets = null;
        }

        Impl29(@NonNull WindowInsetsCompat host, @NonNull Impl29 other) {
            super(host, other);
            this.mSystemGestureInsets = null;
            this.mMandatorySystemGestureInsets = null;
            this.mTappableElementInsets = null;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        Insets getSystemGestureInsets() {
            if (this.mSystemGestureInsets == null) {
                this.mSystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getSystemGestureInsets());
            }
            return this.mSystemGestureInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        Insets getMandatorySystemGestureInsets() {
            if (this.mMandatorySystemGestureInsets == null) {
                this.mMandatorySystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getMandatorySystemGestureInsets());
            }
            return this.mMandatorySystemGestureInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        Insets getTappableElementInsets() {
            if (this.mTappableElementInsets == null) {
                this.mTappableElementInsets = Insets.toCompatInsets(this.mPlatformInsets.getTappableElementInsets());
            }
            return this.mTappableElementInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat inset(int left, int top, int right, int bottom) {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.inset(left, top, right, bottom));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl21, androidx.core.view.WindowInsetsCompat.Impl
        public void setStableInsets(@Nullable Insets stableInsets) {
        }
    }

    public static Insets insetInsets(@NonNull Insets insets, int left, int top, int right, int bottom) {
        int newLeft = Math.max(0, insets.left - left);
        int newTop = Math.max(0, insets.top - top);
        int newRight = Math.max(0, insets.right - right);
        int newBottom = Math.max(0, insets.bottom - bottom);
        return (newLeft == left && newTop == top && newRight == right && newBottom == bottom) ? insets : Insets.of(newLeft, newTop, newRight, newBottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class Impl30 extends Impl29 {
        @NonNull
        static final WindowInsetsCompat CONSUMED = WindowInsetsCompat.toWindowInsetsCompat(WindowInsets.CONSUMED);

        Impl30(@NonNull WindowInsetsCompat host, @NonNull WindowInsets insets) {
            super(host, insets);
        }

        Impl30(@NonNull WindowInsetsCompat host, @NonNull Impl30 other) {
            super(host, other);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        public Insets getInsets(int typeMask) {
            return Insets.toCompatInsets(this.mPlatformInsets.getInsets(TypeImpl30.toPlatformType(typeMask)));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        public Insets getInsetsIgnoringVisibility(int typeMask) {
            return Insets.toCompatInsets(this.mPlatformInsets.getInsetsIgnoringVisibility(TypeImpl30.toPlatformType(typeMask)));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public boolean isVisible(int typeMask) {
            return this.mPlatformInsets.isVisible(TypeImpl30.toPlatformType(typeMask));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        final void copyRootViewBounds(@NonNull View rootView) {
        }
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private final BuilderImpl mImpl;

        public Builder() {
            if (Build.VERSION.SDK_INT >= 30) {
                this.mImpl = new BuilderImpl30();
            } else if (Build.VERSION.SDK_INT >= 29) {
                this.mImpl = new BuilderImpl29();
            } else if (Build.VERSION.SDK_INT >= 20) {
                this.mImpl = new BuilderImpl20();
            } else {
                this.mImpl = new BuilderImpl();
            }
        }

        public Builder(@NonNull WindowInsetsCompat insets) {
            if (Build.VERSION.SDK_INT >= 30) {
                this.mImpl = new BuilderImpl30(insets);
            } else if (Build.VERSION.SDK_INT >= 29) {
                this.mImpl = new BuilderImpl29(insets);
            } else if (Build.VERSION.SDK_INT >= 20) {
                this.mImpl = new BuilderImpl20(insets);
            } else {
                this.mImpl = new BuilderImpl(insets);
            }
        }

        @NonNull
        @Deprecated
        public Builder setSystemWindowInsets(@NonNull Insets insets) {
            this.mImpl.setSystemWindowInsets(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setSystemGestureInsets(@NonNull Insets insets) {
            this.mImpl.setSystemGestureInsets(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setMandatorySystemGestureInsets(@NonNull Insets insets) {
            this.mImpl.setMandatorySystemGestureInsets(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setTappableElementInsets(@NonNull Insets insets) {
            this.mImpl.setTappableElementInsets(insets);
            return this;
        }

        @NonNull
        public Builder setInsets(int typeMask, @NonNull Insets insets) {
            this.mImpl.setInsets(typeMask, insets);
            return this;
        }

        @NonNull
        public Builder setInsetsIgnoringVisibility(int typeMask, @NonNull Insets insets) {
            this.mImpl.setInsetsIgnoringVisibility(typeMask, insets);
            return this;
        }

        @NonNull
        public Builder setVisible(int typeMask, boolean visible) {
            this.mImpl.setVisible(typeMask, visible);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setStableInsets(@NonNull Insets insets) {
            this.mImpl.setStableInsets(insets);
            return this;
        }

        @NonNull
        public Builder setDisplayCutout(@Nullable DisplayCutoutCompat displayCutout) {
            this.mImpl.setDisplayCutout(displayCutout);
            return this;
        }

        @NonNull
        public WindowInsetsCompat build() {
            return this.mImpl.build();
        }
    }

    /* loaded from: classes.dex */
    public static class BuilderImpl {
        private final WindowInsetsCompat mInsets;
        Insets[] mInsetsTypeMask;

        BuilderImpl() {
            this(new WindowInsetsCompat((WindowInsetsCompat) null));
        }

        BuilderImpl(@NonNull WindowInsetsCompat insets) {
            this.mInsets = insets;
        }

        void setSystemWindowInsets(@NonNull Insets insets) {
        }

        void setSystemGestureInsets(@NonNull Insets insets) {
        }

        void setMandatorySystemGestureInsets(@NonNull Insets insets) {
        }

        void setTappableElementInsets(@NonNull Insets insets) {
        }

        void setStableInsets(@NonNull Insets insets) {
        }

        void setDisplayCutout(@Nullable DisplayCutoutCompat displayCutout) {
        }

        void setInsets(int typeMask, @NonNull Insets insets) {
            if (this.mInsetsTypeMask == null) {
                this.mInsetsTypeMask = new Insets[9];
            }
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) != 0) {
                    this.mInsetsTypeMask[Type.indexOf(i)] = insets;
                }
            }
        }

        void setInsetsIgnoringVisibility(int typeMask, @NonNull Insets insets) {
            if (typeMask == 8) {
                throw new IllegalArgumentException("Ignoring visibility inset not available for IME");
            }
        }

        void setVisible(int typeMask, boolean visible) {
        }

        protected final void applyInsetTypes() {
            if (this.mInsetsTypeMask != null) {
                Insets statusBars = this.mInsetsTypeMask[Type.indexOf(1)];
                Insets navigationBars = this.mInsetsTypeMask[Type.indexOf(2)];
                if (navigationBars == null) {
                    navigationBars = this.mInsets.getInsets(2);
                }
                if (statusBars == null) {
                    statusBars = this.mInsets.getInsets(1);
                }
                setSystemWindowInsets(Insets.max(statusBars, navigationBars));
                Insets i = this.mInsetsTypeMask[Type.indexOf(16)];
                if (i != null) {
                    setSystemGestureInsets(i);
                }
                Insets i2 = this.mInsetsTypeMask[Type.indexOf(32)];
                if (i2 != null) {
                    setMandatorySystemGestureInsets(i2);
                }
                Insets i3 = this.mInsetsTypeMask[Type.indexOf(64)];
                if (i3 != null) {
                    setTappableElementInsets(i3);
                }
            }
        }

        @NonNull
        WindowInsetsCompat build() {
            applyInsetTypes();
            return this.mInsets;
        }
    }

    void setOverriddenInsets(Insets[] insetsTypeMask) {
        this.mImpl.setOverriddenInsets(insetsTypeMask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 20)
    /* loaded from: classes.dex */
    public static class BuilderImpl20 extends BuilderImpl {
        private static Constructor<WindowInsets> sConstructor;
        private static Field sConsumedField;
        private WindowInsets mPlatformInsets;
        private Insets mStableInsets;
        private static boolean sConsumedFieldFetched = false;
        private static boolean sConstructorFetched = false;

        BuilderImpl20() {
            this.mPlatformInsets = createWindowInsetsInstance();
        }

        BuilderImpl20(@NonNull WindowInsetsCompat insets) {
            super(insets);
            this.mPlatformInsets = insets.toWindowInsets();
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setSystemWindowInsets(@NonNull Insets insets) {
            if (this.mPlatformInsets != null) {
                this.mPlatformInsets = this.mPlatformInsets.replaceSystemWindowInsets(insets.left, insets.top, insets.right, insets.bottom);
            }
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setStableInsets(@Nullable Insets insets) {
            this.mStableInsets = insets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        @NonNull
        WindowInsetsCompat build() {
            applyInsetTypes();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets);
            windowInsetsCompat.setOverriddenInsets(this.mInsetsTypeMask);
            windowInsetsCompat.setStableInsets(this.mStableInsets);
            return windowInsetsCompat;
        }

        @Nullable
        private static WindowInsets createWindowInsetsInstance() {
            if (!sConsumedFieldFetched) {
                try {
                    sConsumedField = WindowInsets.class.getDeclaredField("CONSUMED");
                } catch (ReflectiveOperationException e) {
                    Log.i(WindowInsetsCompat.TAG, "Could not retrieve WindowInsets.CONSUMED field", e);
                }
                sConsumedFieldFetched = true;
            }
            if (sConsumedField != null) {
                try {
                    WindowInsets consumed = (WindowInsets) sConsumedField.get(null);
                    if (consumed != null) {
                        return new WindowInsets(consumed);
                    }
                } catch (ReflectiveOperationException e2) {
                    Log.i(WindowInsetsCompat.TAG, "Could not get value from WindowInsets.CONSUMED field", e2);
                }
            }
            if (!sConstructorFetched) {
                try {
                    sConstructor = WindowInsets.class.getConstructor(Rect.class);
                } catch (ReflectiveOperationException e3) {
                    Log.i(WindowInsetsCompat.TAG, "Could not retrieve WindowInsets(Rect) constructor", e3);
                }
                sConstructorFetched = true;
            }
            if (sConstructor != null) {
                try {
                    return sConstructor.newInstance(new Rect());
                } catch (ReflectiveOperationException e4) {
                    Log.i(WindowInsetsCompat.TAG, "Could not invoke WindowInsets(Rect) constructor", e4);
                }
            }
            return null;
        }
    }

    void setStableInsets(@Nullable Insets stableInsets) {
        this.mImpl.setStableInsets(stableInsets);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 29)
    /* loaded from: classes.dex */
    public static class BuilderImpl29 extends BuilderImpl {
        final WindowInsets.Builder mPlatBuilder;

        BuilderImpl29() {
            this.mPlatBuilder = new WindowInsets.Builder();
        }

        BuilderImpl29(@NonNull WindowInsetsCompat insets) {
            super(insets);
            WindowInsets.Builder builder;
            WindowInsets platInsets = insets.toWindowInsets();
            if (platInsets != null) {
                builder = new WindowInsets.Builder(platInsets);
            } else {
                builder = new WindowInsets.Builder();
            }
            this.mPlatBuilder = builder;
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setSystemWindowInsets(@NonNull Insets insets) {
            this.mPlatBuilder.setSystemWindowInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setSystemGestureInsets(@NonNull Insets insets) {
            this.mPlatBuilder.setSystemGestureInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setMandatorySystemGestureInsets(@NonNull Insets insets) {
            this.mPlatBuilder.setMandatorySystemGestureInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setTappableElementInsets(@NonNull Insets insets) {
            this.mPlatBuilder.setTappableElementInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setStableInsets(@NonNull Insets insets) {
            this.mPlatBuilder.setStableInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setDisplayCutout(@Nullable DisplayCutoutCompat displayCutout) {
            this.mPlatBuilder.setDisplayCutout(displayCutout != null ? displayCutout.unwrap() : null);
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        @NonNull
        WindowInsetsCompat build() {
            applyInsetTypes();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(this.mPlatBuilder.build());
            windowInsetsCompat.setOverriddenInsets(this.mInsetsTypeMask);
            return windowInsetsCompat;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class BuilderImpl30 extends BuilderImpl29 {
        BuilderImpl30() {
        }

        BuilderImpl30(@NonNull WindowInsetsCompat insets) {
            super(insets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setInsets(int typeMask, @NonNull Insets insets) {
            this.mPlatBuilder.setInsets(TypeImpl30.toPlatformType(typeMask), insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setInsetsIgnoringVisibility(int typeMask, @NonNull Insets insets) {
            this.mPlatBuilder.setInsetsIgnoringVisibility(TypeImpl30.toPlatformType(typeMask), insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void setVisible(int typeMask, boolean visible) {
            this.mPlatBuilder.setVisible(TypeImpl30.toPlatformType(typeMask), visible);
        }
    }

    /* loaded from: classes.dex */
    public static final class Type {
        static final int CAPTION_BAR = 4;
        static final int DISPLAY_CUTOUT = 128;
        static final int FIRST = 1;
        static final int IME = 8;
        static final int LAST = 256;
        static final int MANDATORY_SYSTEM_GESTURES = 32;
        static final int NAVIGATION_BARS = 2;
        static final int SIZE = 9;
        static final int STATUS_BARS = 1;
        static final int SYSTEM_GESTURES = 16;
        static final int TAPPABLE_ELEMENT = 64;
        static final int WINDOW_DECOR = 256;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* loaded from: classes.dex */
        public @interface InsetsType {
        }

        private Type() {
        }

        public static int statusBars() {
            return 1;
        }

        public static int navigationBars() {
            return 2;
        }

        public static int captionBar() {
            return 4;
        }

        public static int ime() {
            return 8;
        }

        public static int systemGestures() {
            return 16;
        }

        public static int mandatorySystemGestures() {
            return 32;
        }

        public static int tappableElement() {
            return 64;
        }

        public static int displayCutout() {
            return 128;
        }

        public static int systemBars() {
            return 7;
        }

        @SuppressLint({"WrongConstant"})
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        static int all() {
            return -1;
        }

        static int indexOf(int type) {
            switch (type) {
                case 1:
                    return 0;
                case 2:
                    return 1;
                case 4:
                    return 2;
                case 8:
                    return 3;
                case 16:
                    return 4;
                case 32:
                    return 5;
                case 64:
                    return 6;
                case 128:
                    return 7;
                case 256:
                    return 8;
                default:
                    throw new IllegalArgumentException("type needs to be >= FIRST and <= LAST, type=" + type);
            }
        }
    }

    @RequiresApi(30)
    /* loaded from: classes.dex */
    private static final class TypeImpl30 {
        private TypeImpl30() {
        }

        static int toPlatformType(int typeMask) {
            int result = 0;
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) != 0) {
                    switch (i) {
                        case 1:
                            result |= WindowInsets.Type.statusBars();
                            continue;
                        case 2:
                            result |= WindowInsets.Type.navigationBars();
                            continue;
                        case 4:
                            result |= WindowInsets.Type.captionBar();
                            continue;
                        case 8:
                            result |= WindowInsets.Type.ime();
                            continue;
                        case 16:
                            result |= WindowInsets.Type.systemGestures();
                            continue;
                        case 32:
                            result |= WindowInsets.Type.mandatorySystemGestures();
                            continue;
                        case 64:
                            result |= WindowInsets.Type.tappableElement();
                            continue;
                        case 128:
                            result |= WindowInsets.Type.displayCutout();
                            continue;
                    }
                }
            }
            return result;
        }
    }

    public void setRootWindowInsets(@Nullable WindowInsetsCompat rootWindowInsets) {
        this.mImpl.setRootWindowInsets(rootWindowInsets);
    }

    void setRootViewData(@NonNull Insets visibleInsets) {
        this.mImpl.setRootViewData(visibleInsets);
    }

    public void copyRootViewBounds(@NonNull View rootView) {
        this.mImpl.copyRootViewBounds(rootView);
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class Api21ReflectionHolder {
        private static Field sContentInsets;
        private static boolean sReflectionSucceeded;
        private static Field sStableInsets;
        private static Field sViewAttachInfoField;

        private Api21ReflectionHolder() {
        }

        static {
            try {
                sViewAttachInfoField = View.class.getDeclaredField("mAttachInfo");
                sViewAttachInfoField.setAccessible(true);
                Class<?> sAttachInfoClass = Class.forName("android.view.View$AttachInfo");
                sStableInsets = sAttachInfoClass.getDeclaredField("mStableInsets");
                sStableInsets.setAccessible(true);
                sContentInsets = sAttachInfoClass.getDeclaredField("mContentInsets");
                sContentInsets.setAccessible(true);
                sReflectionSucceeded = true;
            } catch (ReflectiveOperationException e) {
                Log.w(WindowInsetsCompat.TAG, "Failed to get visible insets from AttachInfo " + e.getMessage(), e);
            }
        }

        @Nullable
        public static WindowInsetsCompat getRootWindowInsets(@NonNull View v) {
            if (sReflectionSucceeded && v.isAttachedToWindow()) {
                View rootView = v.getRootView();
                try {
                    Object attachInfo = sViewAttachInfoField.get(rootView);
                    if (attachInfo != null) {
                        Rect stableInsets = (Rect) sStableInsets.get(attachInfo);
                        Rect visibleInsets = (Rect) sContentInsets.get(attachInfo);
                        if (stableInsets != null && visibleInsets != null) {
                            WindowInsetsCompat insets = new Builder().setStableInsets(Insets.of(stableInsets)).setSystemWindowInsets(Insets.of(visibleInsets)).build();
                            insets.setRootWindowInsets(insets);
                            insets.copyRootViewBounds(v.getRootView());
                            return insets;
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.w(WindowInsetsCompat.TAG, "Failed to get insets from AttachInfo. " + e.getMessage(), e);
                }
                return null;
            }
            return null;
        }
    }
}
