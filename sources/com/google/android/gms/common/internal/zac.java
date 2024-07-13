package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import androidx.core.os.ConfigurationCompat;
import com.google.android.gms.base.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.Wrappers;
import java.util.Locale;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
public final class zac {
    @GuardedBy("sCache")
    private static final SimpleArrayMap<String, String> zaa = new SimpleArrayMap<>();
    @Nullable
    @GuardedBy("sCache")
    private static Locale zab;

    @Nullable
    public static String zaa(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                return resources.getString(R.string.common_google_play_services_install_title);
            case 2:
                return resources.getString(R.string.common_google_play_services_update_title);
            case 3:
                return resources.getString(R.string.common_google_play_services_enable_title);
            case 4:
            case 6:
            case 18:
                return null;
            case 5:
                Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
                return zai(context, "common_google_play_services_invalid_account_title");
            case 7:
                Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
                return zai(context, "common_google_play_services_network_error_title");
            case 8:
                Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
                return null;
            case 9:
                Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
                return null;
            case 10:
                Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
                return null;
            case 11:
                Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
                return null;
            case 12:
            case 13:
            case 14:
            case 15:
            case 19:
            default:
                StringBuilder sb = new StringBuilder(33);
                sb.append("Unexpected error code ");
                sb.append(i);
                Log.e("GoogleApiAvailability", sb.toString());
                return null;
            case 16:
                Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
                return null;
            case 17:
                Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
                return zai(context, "common_google_play_services_sign_in_failed_title");
            case 20:
                Log.e("GoogleApiAvailability", "The current user profile is restricted and could not use authenticated features.");
                return zai(context, "common_google_play_services_restricted_profile_title");
        }
    }

    @NonNull
    public static String zab(Context context, int i) {
        String zaa2;
        if (i == 6) {
            zaa2 = zai(context, "common_google_play_services_resolution_required_title");
        } else {
            zaa2 = zaa(context, i);
        }
        return zaa2 == null ? context.getResources().getString(R.string.common_google_play_services_notification_ticker) : zaa2;
    }

    @NonNull
    public static String zac(Context context, int i) {
        Resources resources = context.getResources();
        String zaf = zaf(context);
        switch (i) {
            case 1:
                return resources.getString(R.string.common_google_play_services_install_text, zaf);
            case 2:
                return DeviceProperties.isWearableWithoutPlayStore(context) ? resources.getString(R.string.common_google_play_services_wear_update_text) : resources.getString(R.string.common_google_play_services_update_text, zaf);
            case 3:
                return resources.getString(R.string.common_google_play_services_enable_text, zaf);
            case 4:
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 19:
            default:
                return resources.getString(com.google.android.gms.common.R.string.common_google_play_services_unknown_issue, zaf);
            case 5:
                return zah(context, "common_google_play_services_invalid_account_text", zaf);
            case 7:
                return zah(context, "common_google_play_services_network_error_text", zaf);
            case 9:
                return resources.getString(R.string.common_google_play_services_unsupported_text, zaf);
            case 16:
                return zah(context, "common_google_play_services_api_unavailable_text", zaf);
            case 17:
                return zah(context, "common_google_play_services_sign_in_failed_text", zaf);
            case 18:
                return resources.getString(R.string.common_google_play_services_updating_text, zaf);
            case 20:
                return zah(context, "common_google_play_services_restricted_profile_text", zaf);
        }
    }

    @NonNull
    public static String zad(Context context, int i) {
        if (i == 6 || i == 19) {
            return zah(context, "common_google_play_services_resolution_required_text", zaf(context));
        }
        return zac(context, i);
    }

    @NonNull
    public static String zae(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                return resources.getString(R.string.common_google_play_services_install_button);
            case 2:
                return resources.getString(R.string.common_google_play_services_update_button);
            case 3:
                return resources.getString(R.string.common_google_play_services_enable_button);
            default:
                return resources.getString(17039370);
        }
    }

    public static String zaf(Context context) {
        String packageName = context.getPackageName();
        try {
            return Wrappers.packageManager(context).getApplicationLabel(packageName).toString();
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            String str = context.getApplicationInfo().name;
            return TextUtils.isEmpty(str) ? packageName : str;
        }
    }

    public static String zag(Context context) {
        return context.getResources().getString(R.string.common_google_play_services_notification_channel_name);
    }

    private static String zah(Context context, String str, String str2) {
        Resources resources = context.getResources();
        String zai = zai(context, str);
        if (zai == null) {
            zai = resources.getString(com.google.android.gms.common.R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, zai, str2);
    }

    @Nullable
    private static String zai(Context context, String str) {
        synchronized (zaa) {
            Locale locale = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
            if (!locale.equals(zab)) {
                zaa.clear();
                zab = locale;
            }
            String str2 = zaa.get(str);
            if (str2 != null) {
                return str2;
            }
            Resources remoteResource = GooglePlayServicesUtil.getRemoteResource(context);
            if (remoteResource == null) {
                return null;
            }
            int identifier = remoteResource.getIdentifier(str, "string", "com.google.android.gms");
            if (identifier == 0) {
                Log.w("GoogleApiAvailability", str.length() != 0 ? "Missing resource: ".concat(str) : new String("Missing resource: "));
                return null;
            }
            String string = remoteResource.getString(identifier);
            if (TextUtils.isEmpty(string)) {
                Log.w("GoogleApiAvailability", str.length() != 0 ? "Got empty resource: ".concat(str) : new String("Got empty resource: "));
                return null;
            }
            zaa.put(str, string);
            return string;
        }
    }
}
