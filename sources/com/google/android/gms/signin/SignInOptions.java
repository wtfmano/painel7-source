package com.google.android.gms.signin;

import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class SignInOptions implements Api.ApiOptions.Optional {
    @RecentlyNonNull
    public static final SignInOptions zaa = new SignInOptions(false, false, null, false, null, null, false, null, null, null);
    private final boolean zab = false;
    private final boolean zac = false;
    @Nullable
    private final String zad = null;
    private final boolean zae = false;
    private final boolean zah = false;
    @Nullable
    private final String zaf = null;
    @Nullable
    private final String zag = null;
    @Nullable
    private final Long zai = null;
    @Nullable
    private final Long zaj = null;

    /* synthetic */ SignInOptions(boolean z, boolean z2, String str, boolean z3, String str2, String str3, boolean z4, Long l, Long l2, zaf zafVar) {
    }

    public final int hashCode() {
        return Objects.hashCode(false, false, null, false, false, null, null, null, null);
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SignInOptions) {
            SignInOptions signInOptions = (SignInOptions) obj;
            boolean z = signInOptions.zab;
            boolean z2 = signInOptions.zac;
            String str = signInOptions.zad;
            if (Objects.equal(null, null)) {
                boolean z3 = signInOptions.zae;
                boolean z4 = signInOptions.zah;
                String str2 = signInOptions.zaf;
                if (Objects.equal(null, null)) {
                    String str3 = signInOptions.zag;
                    if (Objects.equal(null, null)) {
                        Long l = signInOptions.zai;
                        if (Objects.equal(null, null)) {
                            Long l2 = signInOptions.zaj;
                            if (Objects.equal(null, null)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
}
