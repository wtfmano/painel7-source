package com.google.android.gms.common.server.response;

import androidx.annotation.Nullable;
import com.google.android.gms.common.server.response.FastParser;
import java.io.BufferedReader;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
/* loaded from: classes.dex */
final class zaf implements zai<String> {
    @Override // com.google.android.gms.common.server.response.zai
    @Nullable
    public final /* bridge */ /* synthetic */ String zaa(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        String zal;
        zal = fastParser.zal(bufferedReader);
        return zal;
    }
}
