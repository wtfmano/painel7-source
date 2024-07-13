package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONException;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class Storage {
    private static final Lock zaa = new ReentrantLock();
    @Nullable
    @GuardedBy("sLk")
    private static Storage zab;
    private final Lock zac = new ReentrantLock();
    @GuardedBy("mLk")
    private final SharedPreferences zad;

    @VisibleForTesting
    Storage(Context context) {
        this.zad = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    @RecentlyNonNull
    @KeepForSdk
    public static Storage getInstance(@RecentlyNonNull Context context) {
        Preconditions.checkNotNull(context);
        zaa.lock();
        try {
            if (zab == null) {
                zab = new Storage(context.getApplicationContext());
            }
            return zab;
        } finally {
            zaa.unlock();
        }
    }

    private static final String zae(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        return sb.toString();
    }

    @KeepForSdk
    public void clear() {
        this.zac.lock();
        try {
            this.zad.edit().clear().apply();
        } finally {
            this.zac.unlock();
        }
    }

    @RecentlyNullable
    @KeepForSdk
    public GoogleSignInAccount getSavedDefaultGoogleSignInAccount() {
        GoogleSignInAccount googleSignInAccount;
        String zab2 = zab("defaultGoogleSignInAccount");
        if (TextUtils.isEmpty(zab2)) {
            googleSignInAccount = null;
        } else {
            String zab3 = zab(zae("googleSignInAccount", zab2));
            if (zab3 != null) {
                try {
                    googleSignInAccount = GoogleSignInAccount.zaa(zab3);
                } catch (JSONException e) {
                    googleSignInAccount = null;
                }
            } else {
                googleSignInAccount = null;
            }
        }
        return googleSignInAccount;
    }

    @RecentlyNullable
    @KeepForSdk
    public GoogleSignInOptions getSavedDefaultGoogleSignInOptions() {
        GoogleSignInOptions googleSignInOptions;
        String zab2 = zab("defaultGoogleSignInAccount");
        if (TextUtils.isEmpty(zab2)) {
            googleSignInOptions = null;
        } else {
            String zab3 = zab(zae("googleSignInOptions", zab2));
            if (zab3 != null) {
                try {
                    googleSignInOptions = GoogleSignInOptions.zaa(zab3);
                } catch (JSONException e) {
                    googleSignInOptions = null;
                }
            } else {
                googleSignInOptions = null;
            }
        }
        return googleSignInOptions;
    }

    @RecentlyNullable
    @KeepForSdk
    public String getSavedRefreshToken() {
        return zab("refreshToken");
    }

    @KeepForSdk
    public void saveDefaultGoogleSignInAccount(@RecentlyNonNull GoogleSignInAccount googleSignInAccount, @RecentlyNonNull GoogleSignInOptions googleSignInOptions) {
        Preconditions.checkNotNull(googleSignInAccount);
        Preconditions.checkNotNull(googleSignInOptions);
        zaa("defaultGoogleSignInAccount", googleSignInAccount.zac());
        Preconditions.checkNotNull(googleSignInAccount);
        Preconditions.checkNotNull(googleSignInOptions);
        String zac = googleSignInAccount.zac();
        zaa(zae("googleSignInAccount", zac), googleSignInAccount.zad());
        zaa(zae("googleSignInOptions", zac), googleSignInOptions.zab());
    }

    protected final void zaa(@RecentlyNonNull String str, @RecentlyNonNull String str2) {
        this.zac.lock();
        try {
            this.zad.edit().putString(str, str2).apply();
        } finally {
            this.zac.unlock();
        }
    }

    @RecentlyNullable
    protected final String zab(@RecentlyNonNull String str) {
        this.zac.lock();
        try {
            return this.zad.getString(str, null);
        } finally {
            this.zac.unlock();
        }
    }

    public final void zac() {
        String zab2 = zab("defaultGoogleSignInAccount");
        zad("defaultGoogleSignInAccount");
        if (!TextUtils.isEmpty(zab2)) {
            zad(zae("googleSignInAccount", zab2));
            zad(zae("googleSignInOptions", zab2));
        }
    }

    protected final void zad(@RecentlyNonNull String str) {
        this.zac.lock();
        try {
            this.zad.edit().remove(str).apply();
        } finally {
            this.zac.unlock();
        }
    }
}
