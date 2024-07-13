package androidx.core.graphics.drawable;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import org.apache.http.protocol.HTTP;

/* loaded from: classes.dex */
public class IconCompat extends CustomVersionedParcelable {
    private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25f;
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float BLUR_FACTOR = 0.010416667f;
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    @VisibleForTesting
    static final String EXTRA_INT1 = "int1";
    @VisibleForTesting
    static final String EXTRA_INT2 = "int2";
    @VisibleForTesting
    static final String EXTRA_OBJ = "obj";
    @VisibleForTesting
    static final String EXTRA_STRING1 = "string1";
    @VisibleForTesting
    static final String EXTRA_TINT_LIST = "tint_list";
    @VisibleForTesting
    static final String EXTRA_TINT_MODE = "tint_mode";
    @VisibleForTesting
    static final String EXTRA_TYPE = "type";
    private static final float ICON_DIAMETER_FACTOR = 0.9166667f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334f;
    private static final String TAG = "IconCompat";
    public static final int TYPE_ADAPTIVE_BITMAP = 5;
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_RESOURCE = 2;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_URI = 4;
    public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public byte[] mData;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mInt1;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mInt2;
    Object mObj1;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Parcelable mParcelable;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mString1;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ColorStateList mTintList;
    PorterDuff.Mode mTintMode;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mTintModeStr;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int mType;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public @interface IconType {
    }

    public static IconCompat createWithResource(Context context, @DrawableRes int resId) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        return createWithResource(context.getResources(), context.getPackageName(), resId);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createWithResource(Resources r, String pkg, @DrawableRes int resId) {
        if (pkg == null) {
            throw new IllegalArgumentException("Package must not be null.");
        }
        if (resId == 0) {
            throw new IllegalArgumentException("Drawable resource ID must not be 0");
        }
        IconCompat rep = new IconCompat(2);
        rep.mInt1 = resId;
        if (r != null) {
            try {
                rep.mObj1 = r.getResourceName(resId);
            } catch (Resources.NotFoundException e) {
                throw new IllegalArgumentException("Icon resource cannot be found");
            }
        } else {
            rep.mObj1 = pkg;
        }
        rep.mString1 = pkg;
        return rep;
    }

    public static IconCompat createWithBitmap(Bitmap bits) {
        if (bits == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat rep = new IconCompat(1);
        rep.mObj1 = bits;
        return rep;
    }

    public static IconCompat createWithAdaptiveBitmap(Bitmap bits) {
        if (bits == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        IconCompat rep = new IconCompat(5);
        rep.mObj1 = bits;
        return rep;
    }

    public static IconCompat createWithData(byte[] data, int offset, int length) {
        if (data == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
        IconCompat rep = new IconCompat(3);
        rep.mObj1 = data;
        rep.mInt1 = offset;
        rep.mInt2 = length;
        return rep;
    }

    public static IconCompat createWithContentUri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        IconCompat rep = new IconCompat(4);
        rep.mObj1 = uri;
        return rep;
    }

    public static IconCompat createWithContentUri(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        return createWithContentUri(uri.toString());
    }

    @NonNull
    public static IconCompat createWithAdaptiveBitmapContentUri(@NonNull String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        IconCompat rep = new IconCompat(6);
        rep.mObj1 = uri;
        return rep;
    }

    @NonNull
    public static IconCompat createWithAdaptiveBitmapContentUri(@NonNull Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        return createWithAdaptiveBitmapContentUri(uri.toString());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public IconCompat() {
        this.mType = -1;
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.mTintMode = DEFAULT_TINT_MODE;
        this.mTintModeStr = null;
    }

    private IconCompat(int mType) {
        this.mType = -1;
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.mTintMode = DEFAULT_TINT_MODE;
        this.mTintModeStr = null;
        this.mType = mType;
    }

    public int getType() {
        return (this.mType != -1 || Build.VERSION.SDK_INT < 23) ? this.mType : getType((Icon) this.mObj1);
    }

    @NonNull
    public String getResPackage() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return getResPackage((Icon) this.mObj1);
        }
        if (this.mType != 2) {
            throw new IllegalStateException("called getResPackage() on " + this);
        }
        if (TextUtils.isEmpty(this.mString1)) {
            return ((String) this.mObj1).split(":", -1)[0];
        }
        return this.mString1;
    }

    @IdRes
    public int getResId() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return getResId((Icon) this.mObj1);
        }
        if (this.mType != 2) {
            throw new IllegalStateException("called getResId() on " + this);
        }
        return this.mInt1;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Bitmap getBitmap() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            if (this.mObj1 instanceof Bitmap) {
                return (Bitmap) this.mObj1;
            }
            return null;
        } else if (this.mType == 1) {
            return (Bitmap) this.mObj1;
        } else {
            if (this.mType == 5) {
                return createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, true);
            }
            throw new IllegalStateException("called getBitmap() on " + this);
        }
    }

    @NonNull
    public Uri getUri() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return getUri((Icon) this.mObj1);
        }
        if (this.mType != 4 && this.mType != 6) {
            throw new IllegalStateException("called getUri() on " + this);
        }
        return Uri.parse((String) this.mObj1);
    }

    public IconCompat setTint(@ColorInt int tint) {
        return setTintList(ColorStateList.valueOf(tint));
    }

    public IconCompat setTintList(ColorStateList tintList) {
        this.mTintList = tintList;
        return this;
    }

    public IconCompat setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        return this;
    }

    @NonNull
    @RequiresApi(23)
    @Deprecated
    public Icon toIcon() {
        return toIcon(null);
    }

    @NonNull
    @RequiresApi(23)
    public Icon toIcon(@Nullable Context context) {
        Icon icon;
        switch (this.mType) {
            case -1:
                return (Icon) this.mObj1;
            case 0:
            default:
                throw new IllegalArgumentException("Unknown type");
            case 1:
                icon = Icon.createWithBitmap((Bitmap) this.mObj1);
                break;
            case 2:
                icon = Icon.createWithResource(getResPackage(), this.mInt1);
                break;
            case 3:
                icon = Icon.createWithData((byte[]) this.mObj1, this.mInt1, this.mInt2);
                break;
            case 4:
                icon = Icon.createWithContentUri((String) this.mObj1);
                break;
            case 5:
                if (Build.VERSION.SDK_INT >= 26) {
                    icon = Icon.createWithAdaptiveBitmap((Bitmap) this.mObj1);
                    break;
                } else {
                    icon = Icon.createWithBitmap(createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, false));
                    break;
                }
            case 6:
                if (Build.VERSION.SDK_INT >= 30) {
                    icon = Icon.createWithAdaptiveBitmapContentUri(getUri());
                    break;
                } else if (context == null) {
                    throw new IllegalArgumentException("Context is required to resolve the file uri of the icon: " + getUri());
                } else {
                    InputStream is = getUriInputStream(context);
                    if (is == null) {
                        throw new IllegalStateException("Cannot load adaptive icon from uri: " + getUri());
                    }
                    if (Build.VERSION.SDK_INT >= 26) {
                        icon = Icon.createWithAdaptiveBitmap(BitmapFactory.decodeStream(is));
                        break;
                    } else {
                        icon = Icon.createWithBitmap(createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream(is), false));
                        break;
                    }
                }
        }
        if (this.mTintList != null) {
            icon.setTintList(this.mTintList);
        }
        if (this.mTintMode != DEFAULT_TINT_MODE) {
            icon.setTintMode(this.mTintMode);
        }
        return icon;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void checkResource(@NonNull Context context) {
        if (this.mType == 2 && this.mObj1 != null) {
            String fullResName = (String) this.mObj1;
            if (fullResName.contains(":")) {
                String resName = fullResName.split(":", -1)[1];
                String resType = resName.split("/", -1)[0];
                String resName2 = resName.split("/", -1)[1];
                String resPackage = fullResName.split(":", -1)[0];
                if ("0_resource_name_obfuscated".equals(resName2)) {
                    Log.i(TAG, "Found obfuscated resource, not trying to update resource id for it");
                    return;
                }
                String appPackage = getResPackage();
                Resources res = getResources(context, appPackage);
                int id = res.getIdentifier(resName2, resType, resPackage);
                if (this.mInt1 != id) {
                    Log.i(TAG, "Id has changed for " + appPackage + " " + fullResName);
                    this.mInt1 = id;
                }
            }
        }
    }

    @Nullable
    public Drawable loadDrawable(@NonNull Context context) {
        checkResource(context);
        if (Build.VERSION.SDK_INT >= 23) {
            return toIcon(context).loadDrawable(context);
        }
        Drawable result = loadDrawableInner(context);
        if (result != null) {
            if (this.mTintList != null || this.mTintMode != DEFAULT_TINT_MODE) {
                result.mutate();
                DrawableCompat.setTintList(result, this.mTintList);
                DrawableCompat.setTintMode(result, this.mTintMode);
                return result;
            }
            return result;
        }
        return result;
    }

    private Drawable loadDrawableInner(Context context) {
        switch (this.mType) {
            case 1:
                return new BitmapDrawable(context.getResources(), (Bitmap) this.mObj1);
            case 2:
                String resPackage = getResPackage();
                if (TextUtils.isEmpty(resPackage)) {
                    resPackage = context.getPackageName();
                }
                Resources res = getResources(context, resPackage);
                try {
                    return ResourcesCompat.getDrawable(res, this.mInt1, context.getTheme());
                } catch (RuntimeException e) {
                    Log.e(TAG, String.format("Unable to load resource 0x%08x from pkg=%s", Integer.valueOf(this.mInt1), this.mObj1), e);
                    break;
                }
            case 3:
                return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray((byte[]) this.mObj1, this.mInt1, this.mInt2));
            case 4:
                InputStream is = getUriInputStream(context);
                if (is != null) {
                    return new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(is));
                }
                break;
            case 5:
                return new BitmapDrawable(context.getResources(), createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, false));
            case 6:
                InputStream is2 = getUriInputStream(context);
                if (is2 != null) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        return new AdaptiveIconDrawable(null, new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(is2)));
                    }
                    return new BitmapDrawable(context.getResources(), createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream(is2), false));
                }
                break;
        }
        return null;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public InputStream getUriInputStream(@NonNull Context context) {
        Uri uri = getUri();
        String scheme = uri.getScheme();
        if ("content".equals(scheme) || "file".equals(scheme)) {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (Exception e) {
                Log.w(TAG, "Unable to load image from URI: " + uri, e);
            }
        } else {
            try {
                return new FileInputStream(new File((String) this.mObj1));
            } catch (FileNotFoundException e2) {
                Log.w(TAG, "Unable to load image from path: " + uri, e2);
            }
        }
        return null;
    }

    private static Resources getResources(Context context, String resPackage) {
        if ("android".equals(resPackage)) {
            return Resources.getSystem();
        }
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(resPackage, 8192);
            if (ai != null) {
                return pm.getResourcesForApplication(ai);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, String.format("Unable to find pkg=%s for icon", resPackage), e);
            return null;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void addToShortcutIntent(@NonNull Intent outIntent, @Nullable Drawable badge, @NonNull Context c) {
        Bitmap icon;
        checkResource(c);
        switch (this.mType) {
            case 1:
                icon = (Bitmap) this.mObj1;
                if (badge != null) {
                    icon = icon.copy(icon.getConfig(), true);
                    break;
                }
                break;
            case 2:
                try {
                    Context context = c.createPackageContext(getResPackage(), 0);
                    if (badge == null) {
                        outIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(context, this.mInt1));
                        return;
                    }
                    Drawable dr = ContextCompat.getDrawable(context, this.mInt1);
                    if (dr.getIntrinsicWidth() <= 0 || dr.getIntrinsicHeight() <= 0) {
                        int size = ((ActivityManager) context.getSystemService("activity")).getLauncherLargeIconSize();
                        icon = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                    } else {
                        icon = Bitmap.createBitmap(dr.getIntrinsicWidth(), dr.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    }
                    dr.setBounds(0, 0, icon.getWidth(), icon.getHeight());
                    dr.draw(new Canvas(icon));
                    break;
                } catch (PackageManager.NameNotFoundException e) {
                    throw new IllegalArgumentException("Can't find package " + this.mObj1, e);
                }
                break;
            case 3:
            case 4:
            default:
                throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
            case 5:
                icon = createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, true);
                break;
        }
        if (badge != null) {
            int w = icon.getWidth();
            int h = icon.getHeight();
            badge.setBounds(w / 2, h / 2, w, h);
            badge.draw(new Canvas(icon));
        }
        outIntent.putExtra("android.intent.extra.shortcut.ICON", icon);
    }

    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        switch (this.mType) {
            case -1:
                bundle.putParcelable(EXTRA_OBJ, (Parcelable) this.mObj1);
                break;
            case 0:
            default:
                throw new IllegalArgumentException("Invalid icon");
            case 1:
            case 5:
                bundle.putParcelable(EXTRA_OBJ, (Bitmap) this.mObj1);
                break;
            case 2:
            case 4:
            case 6:
                bundle.putString(EXTRA_OBJ, (String) this.mObj1);
                break;
            case 3:
                bundle.putByteArray(EXTRA_OBJ, (byte[]) this.mObj1);
                break;
        }
        bundle.putInt(EXTRA_TYPE, this.mType);
        bundle.putInt(EXTRA_INT1, this.mInt1);
        bundle.putInt(EXTRA_INT2, this.mInt2);
        bundle.putString(EXTRA_STRING1, this.mString1);
        if (this.mTintList != null) {
            bundle.putParcelable(EXTRA_TINT_LIST, this.mTintList);
        }
        if (this.mTintMode != DEFAULT_TINT_MODE) {
            bundle.putString(EXTRA_TINT_MODE, this.mTintMode.name());
        }
        return bundle;
    }

    @NonNull
    public String toString() {
        if (this.mType == -1) {
            return String.valueOf(this.mObj1);
        }
        StringBuilder sb = new StringBuilder("Icon(typ=").append(typeToString(this.mType));
        switch (this.mType) {
            case 1:
            case 5:
                sb.append(" size=").append(((Bitmap) this.mObj1).getWidth()).append("x").append(((Bitmap) this.mObj1).getHeight());
                break;
            case 2:
                sb.append(" pkg=").append(this.mString1).append(" id=").append(String.format("0x%08x", Integer.valueOf(getResId())));
                break;
            case 3:
                sb.append(" len=").append(this.mInt1);
                if (this.mInt2 != 0) {
                    sb.append(" off=").append(this.mInt2);
                    break;
                }
                break;
            case 4:
            case 6:
                sb.append(" uri=").append(this.mObj1);
                break;
        }
        if (this.mTintList != null) {
            sb.append(" tint=");
            sb.append(this.mTintList);
        }
        if (this.mTintMode != DEFAULT_TINT_MODE) {
            sb.append(" mode=").append(this.mTintMode);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    public void onPreParceling(boolean isStream) {
        this.mTintModeStr = this.mTintMode.name();
        switch (this.mType) {
            case -1:
                if (isStream) {
                    throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
                }
                this.mParcelable = (Parcelable) this.mObj1;
                return;
            case 0:
            default:
                return;
            case 1:
            case 5:
                if (isStream) {
                    Bitmap bitmap = (Bitmap) this.mObj1;
                    ByteArrayOutputStream data = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, data);
                    this.mData = data.toByteArray();
                    return;
                }
                this.mParcelable = (Parcelable) this.mObj1;
                return;
            case 2:
                this.mData = ((String) this.mObj1).getBytes(Charset.forName(HTTP.UTF_16));
                return;
            case 3:
                this.mData = (byte[]) this.mObj1;
                return;
            case 4:
            case 6:
                this.mData = this.mObj1.toString().getBytes(Charset.forName(HTTP.UTF_16));
                return;
        }
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    public void onPostParceling() {
        this.mTintMode = PorterDuff.Mode.valueOf(this.mTintModeStr);
        switch (this.mType) {
            case -1:
                if (this.mParcelable != null) {
                    this.mObj1 = this.mParcelable;
                    return;
                }
                throw new IllegalArgumentException("Invalid icon");
            case 0:
            default:
                return;
            case 1:
            case 5:
                if (this.mParcelable != null) {
                    this.mObj1 = this.mParcelable;
                    return;
                }
                this.mObj1 = this.mData;
                this.mType = 3;
                this.mInt1 = 0;
                this.mInt2 = this.mData.length;
                return;
            case 2:
            case 4:
            case 6:
                this.mObj1 = new String(this.mData, Charset.forName(HTTP.UTF_16));
                if (this.mType == 2 && this.mString1 == null) {
                    this.mString1 = ((String) this.mObj1).split(":", -1)[0];
                    return;
                }
                return;
            case 3:
                this.mObj1 = this.mData;
                return;
        }
    }

    private static String typeToString(int x) {
        switch (x) {
            case 1:
                return "BITMAP";
            case 2:
                return "RESOURCE";
            case 3:
                return "DATA";
            case 4:
                return "URI";
            case 5:
                return "BITMAP_MASKABLE";
            case 6:
                return "URI_MASKABLE";
            default:
                return "UNKNOWN";
        }
    }

    @Nullable
    public static IconCompat createFromBundle(@NonNull Bundle bundle) {
        int type = bundle.getInt(EXTRA_TYPE);
        IconCompat icon = new IconCompat(type);
        icon.mInt1 = bundle.getInt(EXTRA_INT1);
        icon.mInt2 = bundle.getInt(EXTRA_INT2);
        icon.mString1 = bundle.getString(EXTRA_STRING1);
        if (bundle.containsKey(EXTRA_TINT_LIST)) {
            icon.mTintList = (ColorStateList) bundle.getParcelable(EXTRA_TINT_LIST);
        }
        if (bundle.containsKey(EXTRA_TINT_MODE)) {
            icon.mTintMode = PorterDuff.Mode.valueOf(bundle.getString(EXTRA_TINT_MODE));
        }
        switch (type) {
            case -1:
            case 1:
            case 5:
                icon.mObj1 = bundle.getParcelable(EXTRA_OBJ);
                return icon;
            case 0:
            default:
                Log.w(TAG, "Unknown type " + type);
                return null;
            case 2:
            case 4:
            case 6:
                icon.mObj1 = bundle.getString(EXTRA_OBJ);
                return icon;
            case 3:
                icon.mObj1 = bundle.getByteArray(EXTRA_OBJ);
                return icon;
        }
    }

    @Nullable
    @RequiresApi(23)
    public static IconCompat createFromIcon(@NonNull Context context, @NonNull Icon icon) {
        Preconditions.checkNotNull(icon);
        switch (getType(icon)) {
            case 2:
                String resPackage = getResPackage(icon);
                try {
                    IconCompat iconCompat = createWithResource(getResources(context, resPackage), resPackage, getResId(icon));
                    return iconCompat;
                } catch (Resources.NotFoundException e) {
                    throw new IllegalArgumentException("Icon resource cannot be found");
                }
            case 3:
            case 5:
            default:
                IconCompat iconCompat2 = new IconCompat(-1);
                iconCompat2.mObj1 = icon;
                return iconCompat2;
            case 4:
                IconCompat iconCompat3 = createWithContentUri(getUri(icon));
                return iconCompat3;
            case 6:
                IconCompat iconCompat4 = createWithAdaptiveBitmapContentUri(getUri(icon));
                return iconCompat4;
        }
    }

    @Nullable
    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createFromIcon(@NonNull Icon icon) {
        Preconditions.checkNotNull(icon);
        switch (getType(icon)) {
            case 2:
                IconCompat iconCompat = createWithResource(null, getResPackage(icon), getResId(icon));
                return iconCompat;
            case 3:
            case 5:
            default:
                IconCompat iconCompat2 = new IconCompat(-1);
                iconCompat2.mObj1 = icon;
                return iconCompat2;
            case 4:
                IconCompat iconCompat3 = createWithContentUri(getUri(icon));
                return iconCompat3;
            case 6:
                IconCompat iconCompat4 = createWithAdaptiveBitmapContentUri(getUri(icon));
                return iconCompat4;
        }
    }

    @Nullable
    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createFromIconOrNullIfZeroResId(@NonNull Icon icon) {
        if (getType(icon) == 2 && getResId(icon) == 0) {
            return null;
        }
        return createFromIcon(icon);
    }

    @RequiresApi(23)
    private static int getType(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getType();
        }
        try {
            return ((Integer) icon.getClass().getMethod("getType", new Class[0]).invoke(icon, new Object[0])).intValue();
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to get icon type " + icon, e);
            return -1;
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Unable to get icon type " + icon, e2);
            return -1;
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Unable to get icon type " + icon, e3);
            return -1;
        }
    }

    @Nullable
    @RequiresApi(23)
    private static String getResPackage(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResPackage();
        }
        try {
            return (String) icon.getClass().getMethod("getResPackage", new Class[0]).invoke(icon, new Object[0]);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to get icon package", e);
            return null;
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Unable to get icon package", e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Unable to get icon package", e3);
            return null;
        }
    }

    @DrawableRes
    @IdRes
    @RequiresApi(23)
    private static int getResId(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResId();
        }
        try {
            return ((Integer) icon.getClass().getMethod("getResId", new Class[0]).invoke(icon, new Object[0])).intValue();
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to get icon resource", e);
            return 0;
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Unable to get icon resource", e2);
            return 0;
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Unable to get icon resource", e3);
            return 0;
        }
    }

    @Nullable
    @RequiresApi(23)
    private static Uri getUri(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getUri();
        }
        try {
            return (Uri) icon.getClass().getMethod("getUri", new Class[0]).invoke(icon, new Object[0]);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to get icon uri", e);
            return null;
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Unable to get icon uri", e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Unable to get icon uri", e3);
            return null;
        }
    }

    @VisibleForTesting
    static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap adaptiveIconBitmap, boolean addShadow) {
        int size = (int) (DEFAULT_VIEW_PORT_SCALE * Math.min(adaptiveIconBitmap.getWidth(), adaptiveIconBitmap.getHeight()));
        Bitmap icon = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(icon);
        Paint paint = new Paint(3);
        float center = size * 0.5f;
        float radius = center * ICON_DIAMETER_FACTOR;
        if (addShadow) {
            float blur = BLUR_FACTOR * size;
            paint.setColor(0);
            paint.setShadowLayer(blur, 0.0f, KEY_SHADOW_OFFSET_FACTOR * size, 1023410176);
            canvas.drawCircle(center, center, radius, paint);
            paint.setShadowLayer(blur, 0.0f, 0.0f, 503316480);
            canvas.drawCircle(center, center, radius, paint);
            paint.clearShadowLayer();
        }
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        BitmapShader shader = new BitmapShader(adaptiveIconBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix shift = new Matrix();
        shift.setTranslate((-(adaptiveIconBitmap.getWidth() - size)) / 2, (-(adaptiveIconBitmap.getHeight() - size)) / 2);
        shader.setLocalMatrix(shift);
        paint.setShader(shader);
        canvas.drawCircle(center, center, radius, paint);
        canvas.setBitmap(null);
        return icon;
    }
}
