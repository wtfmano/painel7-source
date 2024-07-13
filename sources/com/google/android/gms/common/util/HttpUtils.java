package com.google.android.gms.common.util;

import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public class HttpUtils {
    private static final Pattern zza = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern zzb = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern zzc = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    private HttpUtils() {
    }

    @RecentlyNonNull
    @KeepForSdk
    public static Map<String, String> parse(@RecentlyNonNull URI uri, @RecentlyNonNull String str) {
        HashMap hashMap;
        Map<String, String> emptyMap = Collections.emptyMap();
        String rawQuery = uri.getRawQuery();
        if (rawQuery == null) {
            hashMap = emptyMap;
        } else if (rawQuery.length() > 0) {
            HashMap hashMap2 = new HashMap();
            Scanner scanner = new Scanner(rawQuery);
            scanner.useDelimiter("&");
            while (scanner.hasNext()) {
                String[] split = scanner.next().split("=");
                int length = split.length;
                if (length != 0 && length <= 2) {
                    hashMap2.put(zza(split[0], str), length == 2 ? zza(split[1], str) : null);
                } else {
                    throw new IllegalArgumentException("bad parameter");
                }
            }
            hashMap = hashMap2;
        } else {
            hashMap = emptyMap;
        }
        return hashMap;
    }

    private static String zza(String str, String str2) {
        String str3 = str2;
        if (str3 == null) {
            str3 = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode(str, str3);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
