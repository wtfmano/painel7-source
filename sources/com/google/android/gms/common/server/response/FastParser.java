package com.google.android.gms.common.server.response;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
@ShowFirstParty
@KeepForSdk
/* loaded from: classes.dex */
public class FastParser<T extends FastJsonResponse> {
    private static final char[] zaf = {'u', 'l', 'l'};
    private static final char[] zag = {'r', 'u', 'e'};
    private static final char[] zah = {'r', 'u', 'e', '\"'};
    private static final char[] zai = {'a', 'l', 's', 'e'};
    private static final char[] zaj = {'a', 'l', 's', 'e', '\"'};
    private static final char[] zak = {'\n'};
    private static final zai<Integer> zam = new zaa();
    private static final zai<Long> zan = new zab();
    private static final zai<Float> zao = new zac();
    private static final zai<Double> zap = new zad();
    private static final zai<Boolean> zaq = new zae();
    private static final zai<String> zar = new zaf();
    private static final zai<BigInteger> zas = new zag();
    private static final zai<BigDecimal> zat = new zah();
    private final char[] zaa = new char[1];
    private final char[] zab = new char[32];
    private final char[] zac = new char[1024];
    private final StringBuilder zad = new StringBuilder(32);
    private final StringBuilder zae = new StringBuilder(1024);
    private final Stack<Integer> zal = new Stack<>();

    /* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
    @ShowFirstParty
    @KeepForSdk
    /* loaded from: classes.dex */
    public static class ParseException extends Exception {
        public ParseException(@RecentlyNonNull String str) {
            super(str);
        }

        public ParseException(@RecentlyNonNull String str, @RecentlyNonNull Throwable th) {
            super("Error instantiating inner object", th);
        }

        public ParseException(@RecentlyNonNull Throwable th) {
            super(th);
        }
    }

    private static final String zaA(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, @Nullable char[] cArr2) throws ParseException, IOException {
        boolean z;
        sb.setLength(0);
        bufferedReader.mark(cArr.length);
        boolean z2 = false;
        boolean z3 = false;
        loop0: while (true) {
            boolean z4 = z3;
            int read = bufferedReader.read(cArr);
            if (read != -1) {
                boolean z5 = z4;
                int i = 0;
                while (i < read) {
                    char c = cArr[i];
                    if (Character.isISOControl(c)) {
                        if (cArr2 == null) {
                            break loop0;
                        }
                        for (char c2 : cArr2) {
                            if (c2 != c) {
                            }
                        }
                        break loop0;
                    }
                    if (c == '\"') {
                        if (!z2) {
                            sb.append(cArr, 0, i);
                            bufferedReader.reset();
                            bufferedReader.skip(i + 1);
                            if (z5) {
                                return JsonUtils.unescapeString(sb.toString());
                            }
                            return sb.toString();
                        }
                        z2 = false;
                        z = z5;
                    } else if (c == '\\') {
                        z2 = !z2;
                        z = true;
                    } else {
                        z2 = false;
                        z = z5;
                    }
                    i++;
                    z5 = z;
                }
                sb.append(cArr, 0, read);
                bufferedReader.mark(cArr.length);
                z3 = z5;
            } else {
                throw new ParseException("Unexpected EOF while parsing string");
            }
        }
        throw new ParseException("Unexpected control character while reading string");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean zai(BufferedReader bufferedReader, FastJsonResponse fastJsonResponse) throws ParseException, IOException {
        HashMap hashMap;
        Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = fastJsonResponse.getFieldMappings();
        String zaj2 = zaj(bufferedReader);
        if (zaj2 != null) {
            while (zaj2 != null) {
                FastJsonResponse.Field<?, ?> field = fieldMappings.get(zaj2);
                if (field == null) {
                    zaj2 = zak(bufferedReader);
                } else {
                    this.zal.push(4);
                    int i = field.zaa;
                    switch (i) {
                        case 0:
                            if (field.zab) {
                                fastJsonResponse.zab(field, zam(bufferedReader, zam));
                                break;
                            } else {
                                fastJsonResponse.zaa(field, zao(bufferedReader));
                                break;
                            }
                        case 1:
                            if (field.zab) {
                                fastJsonResponse.zad(field, zam(bufferedReader, zas));
                                break;
                            } else {
                                fastJsonResponse.zac(field, zaq(bufferedReader));
                                break;
                            }
                        case 2:
                            if (field.zab) {
                                fastJsonResponse.zaf(field, zam(bufferedReader, zan));
                                break;
                            } else {
                                fastJsonResponse.zae(field, zap(bufferedReader));
                                break;
                            }
                        case 3:
                            if (field.zab) {
                                fastJsonResponse.zah(field, zam(bufferedReader, zao));
                                break;
                            } else {
                                fastJsonResponse.zag(field, zas(bufferedReader));
                                break;
                            }
                        case 4:
                            if (field.zab) {
                                fastJsonResponse.zaj(field, zam(bufferedReader, zap));
                                break;
                            } else {
                                fastJsonResponse.zai(field, zat(bufferedReader));
                                break;
                            }
                        case 5:
                            if (field.zab) {
                                fastJsonResponse.zal(field, zam(bufferedReader, zat));
                                break;
                            } else {
                                fastJsonResponse.zak(field, zau(bufferedReader));
                                break;
                            }
                        case 6:
                            if (field.zab) {
                                fastJsonResponse.zan(field, zam(bufferedReader, zaq));
                                break;
                            } else {
                                fastJsonResponse.zam(field, zar(bufferedReader, false));
                                break;
                            }
                        case 7:
                            if (field.zab) {
                                fastJsonResponse.zap(field, zam(bufferedReader, zar));
                                break;
                            } else {
                                fastJsonResponse.zao(field, zal(bufferedReader));
                                break;
                            }
                        case 8:
                            fastJsonResponse.zaq(field, Base64Utils.decode(zan(bufferedReader, this.zac, this.zae, zak)));
                            break;
                        case 9:
                            fastJsonResponse.zaq(field, Base64Utils.decodeUrlSafe(zan(bufferedReader, this.zac, this.zae, zak)));
                            break;
                        case 10:
                            char zaw = zaw(bufferedReader);
                            if (zaw != 'n') {
                                if (zaw == '{') {
                                    this.zal.push(1);
                                    hashMap = new HashMap();
                                    while (true) {
                                        switch (zaw(bufferedReader)) {
                                            case 0:
                                                throw new ParseException("Unexpected EOF");
                                            case '\"':
                                                String zaA = zaA(bufferedReader, this.zab, this.zad, null);
                                                if (zaw(bufferedReader) == ':') {
                                                    if (zaw(bufferedReader) == '\"') {
                                                        hashMap.put(zaA, zaA(bufferedReader, this.zab, this.zad, null));
                                                        char zaw2 = zaw(bufferedReader);
                                                        if (zaw2 != ',') {
                                                            if (zaw2 == '}') {
                                                                zaz(1);
                                                                break;
                                                            } else {
                                                                StringBuilder sb = new StringBuilder(48);
                                                                sb.append("Unexpected character while parsing string map: ");
                                                                sb.append(zaw2);
                                                                throw new ParseException(sb.toString());
                                                            }
                                                        }
                                                    } else {
                                                        String valueOf = String.valueOf(zaA);
                                                        throw new ParseException(valueOf.length() != 0 ? "Expected String value for key ".concat(valueOf) : new String("Expected String value for key "));
                                                    }
                                                } else {
                                                    String valueOf2 = String.valueOf(zaA);
                                                    throw new ParseException(valueOf2.length() != 0 ? "No map value found for key ".concat(valueOf2) : new String("No map value found for key "));
                                                }
                                            case '}':
                                                zaz(1);
                                                break;
                                        }
                                    }
                                } else {
                                    throw new ParseException("Expected start of a map object");
                                }
                            } else {
                                zay(bufferedReader, zaf);
                                hashMap = null;
                            }
                            fastJsonResponse.zar(field, hashMap);
                            break;
                        case 11:
                            if (field.zab) {
                                char zaw3 = zaw(bufferedReader);
                                if (zaw3 == 'n') {
                                    zay(bufferedReader, zaf);
                                    fastJsonResponse.addConcreteTypeArrayInternal(field, field.zae, null);
                                    break;
                                } else {
                                    this.zal.push(5);
                                    if (zaw3 == '[') {
                                        fastJsonResponse.addConcreteTypeArrayInternal(field, field.zae, zav(bufferedReader, field));
                                        break;
                                    } else {
                                        throw new ParseException("Expected array start");
                                    }
                                }
                            } else {
                                char zaw4 = zaw(bufferedReader);
                                if (zaw4 == 'n') {
                                    zay(bufferedReader, zaf);
                                    fastJsonResponse.addConcreteTypeInternal(field, field.zae, null);
                                    break;
                                } else {
                                    this.zal.push(1);
                                    if (zaw4 == '{') {
                                        try {
                                            FastJsonResponse zaf2 = field.zaf();
                                            zai(bufferedReader, zaf2);
                                            fastJsonResponse.addConcreteTypeInternal(field, field.zae, zaf2);
                                            break;
                                        } catch (IllegalAccessException e) {
                                            throw new ParseException("Error instantiating inner object", e);
                                        } catch (InstantiationException e2) {
                                            throw new ParseException("Error instantiating inner object", e2);
                                        }
                                    } else {
                                        throw new ParseException("Expected start of object");
                                    }
                                }
                            }
                        default:
                            StringBuilder sb2 = new StringBuilder(30);
                            sb2.append("Invalid field type ");
                            sb2.append(i);
                            throw new ParseException(sb2.toString());
                    }
                    zaz(4);
                    zaz(2);
                    char zaw5 = zaw(bufferedReader);
                    switch (zaw5) {
                        case ',':
                            zaj2 = zaj(bufferedReader);
                            continue;
                        case '}':
                            zaj2 = null;
                            continue;
                        default:
                            StringBuilder sb3 = new StringBuilder(55);
                            sb3.append("Expected end of object or field separator, but found: ");
                            sb3.append(zaw5);
                            throw new ParseException(sb3.toString());
                    }
                }
            }
            zaz(1);
            return true;
        }
        zaz(1);
        return false;
    }

    @Nullable
    private final String zaj(BufferedReader bufferedReader) throws ParseException, IOException {
        this.zal.push(2);
        char zaw = zaw(bufferedReader);
        switch (zaw) {
            case '\"':
                this.zal.push(3);
                String zaA = zaA(bufferedReader, this.zab, this.zad, null);
                zaz(3);
                if (zaw(bufferedReader) != ':') {
                    throw new ParseException("Expected key/value separator");
                }
                return zaA;
            case ']':
                zaz(2);
                zaz(1);
                zaz(5);
                return null;
            case '}':
                zaz(2);
                return null;
            default:
                StringBuilder sb = new StringBuilder(19);
                sb.append("Unexpected token: ");
                sb.append(zaw);
                throw new ParseException(sb.toString());
        }
    }

    @Nullable
    private final String zak(BufferedReader bufferedReader) throws ParseException, IOException {
        char c;
        boolean z;
        char c2;
        int i;
        bufferedReader.mark(1024);
        switch (zaw(bufferedReader)) {
            case '\"':
                if (bufferedReader.read(this.zaa) == -1) {
                    throw new ParseException("Unexpected EOF while parsing string");
                }
                char c3 = this.zaa[0];
                boolean z2 = false;
                do {
                    if (c3 != '\"') {
                        c = c3;
                    } else if (!z2) {
                        break;
                    } else {
                        z2 = true;
                        c = '\"';
                    }
                    z2 = c == '\\' ? !z2 : false;
                    if (bufferedReader.read(this.zaa) == -1) {
                        throw new ParseException("Unexpected EOF while parsing string");
                    }
                    c3 = this.zaa[0];
                } while (!Character.isISOControl(c3));
                throw new ParseException("Unexpected control character while reading string");
            case ',':
                throw new ParseException("Missing value");
            case '[':
                this.zal.push(5);
                bufferedReader.mark(32);
                if (zaw(bufferedReader) == ']') {
                    zaz(5);
                    break;
                } else {
                    bufferedReader.reset();
                    boolean z3 = false;
                    boolean z4 = false;
                    int i2 = 1;
                    while (i2 > 0) {
                        char zaw = zaw(bufferedReader);
                        if (zaw != 0) {
                            if (Character.isISOControl(zaw)) {
                                throw new ParseException("Unexpected control character while reading array");
                            }
                            if (zaw != '\"') {
                                z = z4;
                            } else if (z3) {
                                zaw = '\"';
                                z = z4;
                            } else {
                                z = !z4;
                                zaw = '\"';
                            }
                            if (zaw != '[') {
                                c2 = zaw;
                                i = i2;
                            } else if (z) {
                                c2 = '[';
                                i = i2;
                            } else {
                                i = i2 + 1;
                                c2 = '[';
                            }
                            i2 = c2 == ']' ? !z ? i - 1 : i : i;
                            if (c2 != '\\') {
                                z3 = false;
                                z4 = z;
                            } else if (z) {
                                z3 = !z3;
                                z4 = z;
                            } else {
                                z3 = false;
                                z4 = z;
                            }
                        } else {
                            throw new ParseException("Unexpected EOF while parsing array");
                        }
                    }
                    zaz(5);
                    break;
                }
            case '{':
                this.zal.push(1);
                bufferedReader.mark(32);
                char zaw2 = zaw(bufferedReader);
                if (zaw2 == '}') {
                    zaz(1);
                    break;
                } else if (zaw2 == '\"') {
                    bufferedReader.reset();
                    zaj(bufferedReader);
                    do {
                    } while (zak(bufferedReader) != null);
                    zaz(1);
                    break;
                } else {
                    StringBuilder sb = new StringBuilder(18);
                    sb.append("Unexpected token ");
                    sb.append(zaw2);
                    throw new ParseException(sb.toString());
                }
            default:
                bufferedReader.reset();
                zax(bufferedReader, this.zac);
                break;
        }
        char zaw3 = zaw(bufferedReader);
        switch (zaw3) {
            case ',':
                zaz(2);
                return zaj(bufferedReader);
            case '}':
                zaz(2);
                return null;
            default:
                StringBuilder sb2 = new StringBuilder(18);
                sb2.append("Unexpected token ");
                sb2.append(zaw3);
                throw new ParseException(sb2.toString());
        }
    }

    @Nullable
    public final String zal(BufferedReader bufferedReader) throws ParseException, IOException {
        return zan(bufferedReader, this.zab, this.zad, null);
    }

    @Nullable
    private final <O> ArrayList<O> zam(BufferedReader bufferedReader, zai<O> zaiVar) throws ParseException, IOException {
        char zaw = zaw(bufferedReader);
        if (zaw == 'n') {
            zay(bufferedReader, zaf);
            return null;
        } else if (zaw != '[') {
            throw new ParseException("Expected start of array");
        } else {
            this.zal.push(5);
            ArrayList<O> arrayList = new ArrayList<>();
            while (true) {
                bufferedReader.mark(1024);
                switch (zaw(bufferedReader)) {
                    case 0:
                        throw new ParseException("Unexpected EOF");
                    case ',':
                        break;
                    case ']':
                        zaz(5);
                        return arrayList;
                    default:
                        bufferedReader.reset();
                        arrayList.add(zaiVar.zaa(this, bufferedReader));
                        break;
                }
            }
        }
    }

    @Nullable
    private final String zan(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, @Nullable char[] cArr2) throws ParseException, IOException {
        switch (zaw(bufferedReader)) {
            case '\"':
                return zaA(bufferedReader, cArr, sb, cArr2);
            case 'n':
                zay(bufferedReader, zaf);
                return null;
            default:
                throw new ParseException("Expected string");
        }
    }

    public final int zao(BufferedReader bufferedReader) throws ParseException, IOException {
        int i;
        int i2;
        int i3;
        int zax = zax(bufferedReader, this.zac);
        if (zax == 0) {
            return 0;
        }
        char[] cArr = this.zac;
        if (zax > 0) {
            char c = cArr[0];
            int i4 = c == '-' ? Integer.MIN_VALUE : -2147483647;
            int i5 = c == '-' ? 1 : 0;
            if (i5 < zax) {
                i2 = i5 + 1;
                int digit = Character.digit(cArr[i5], 10);
                if (digit < 0) {
                    throw new ParseException("Unexpected non-digit character");
                }
                i = -digit;
            } else {
                i = 0;
                i2 = i5;
            }
            while (i2 < zax) {
                int i6 = i2 + 1;
                int digit2 = Character.digit(cArr[i2], 10);
                if (digit2 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                }
                if (i < -214748364) {
                    throw new ParseException("Number too large");
                }
                int i7 = i * 10;
                if (i7 < i4 + digit2) {
                    throw new ParseException("Number too large");
                }
                i = i7 - digit2;
                i2 = i6;
            }
            if (i5 == 0) {
                i3 = -i;
            } else if (i2 <= 1) {
                throw new ParseException("No digits to parse");
            } else {
                i3 = i;
            }
            return i3;
        }
        throw new ParseException("No number to parse");
    }

    public final long zap(BufferedReader bufferedReader) throws ParseException, IOException {
        long j;
        int i;
        long j2;
        int zax = zax(bufferedReader, this.zac);
        if (zax == 0) {
            return 0L;
        }
        char[] cArr = this.zac;
        if (zax > 0) {
            char c = cArr[0];
            long j3 = c == '-' ? Long.MIN_VALUE : -9223372036854775807L;
            int i2 = c == '-' ? 1 : 0;
            if (i2 < zax) {
                i = i2 + 1;
                int digit = Character.digit(cArr[i2], 10);
                if (digit < 0) {
                    throw new ParseException("Unexpected non-digit character");
                }
                j = -digit;
            } else {
                j = 0;
                i = i2;
            }
            while (i < zax) {
                int i3 = i + 1;
                int digit2 = Character.digit(cArr[i], 10);
                if (digit2 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                }
                if (j < -922337203685477580L) {
                    throw new ParseException("Number too large");
                }
                long j4 = j * 10;
                long j5 = digit2;
                if (j4 < j3 + j5) {
                    throw new ParseException("Number too large");
                }
                j = j4 - j5;
                i = i3;
            }
            if (i2 == 0) {
                j2 = -j;
            } else if (i <= 1) {
                throw new ParseException("No digits to parse");
            } else {
                j2 = j;
            }
            return j2;
        }
        throw new ParseException("No number to parse");
    }

    @Nullable
    public final BigInteger zaq(BufferedReader bufferedReader) throws ParseException, IOException {
        int zax = zax(bufferedReader, this.zac);
        if (zax == 0) {
            return null;
        }
        return new BigInteger(new String(this.zac, 0, zax));
    }

    public final boolean zar(BufferedReader bufferedReader, boolean z) throws ParseException, IOException {
        char zaw = zaw(bufferedReader);
        switch (zaw) {
            case '\"':
                if (z) {
                    throw new ParseException("No boolean value found in string");
                }
                return zar(bufferedReader, true);
            case 'f':
                zay(bufferedReader, z ? zaj : zai);
                return false;
            case 'n':
                zay(bufferedReader, zaf);
                return false;
            case 't':
                zay(bufferedReader, z ? zah : zag);
                return true;
            default:
                StringBuilder sb = new StringBuilder(19);
                sb.append("Unexpected token: ");
                sb.append(zaw);
                throw new ParseException(sb.toString());
        }
    }

    public final float zas(BufferedReader bufferedReader) throws ParseException, IOException {
        int zax = zax(bufferedReader, this.zac);
        if (zax == 0) {
            return 0.0f;
        }
        return Float.parseFloat(new String(this.zac, 0, zax));
    }

    public final double zat(BufferedReader bufferedReader) throws ParseException, IOException {
        int zax = zax(bufferedReader, this.zac);
        if (zax == 0) {
            return 0.0d;
        }
        return Double.parseDouble(new String(this.zac, 0, zax));
    }

    @Nullable
    public final BigDecimal zau(BufferedReader bufferedReader) throws ParseException, IOException {
        int zax = zax(bufferedReader, this.zac);
        if (zax == 0) {
            return null;
        }
        return new BigDecimal(new String(this.zac, 0, zax));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    private final <T extends FastJsonResponse> ArrayList<T> zav(BufferedReader bufferedReader, FastJsonResponse.Field<?, ?> field) throws ParseException, IOException {
        ArrayList<T> arrayList = (ArrayList<T>) new ArrayList();
        char zaw = zaw(bufferedReader);
        switch (zaw) {
            case ']':
                zaz(5);
                return arrayList;
            case 'n':
                zay(bufferedReader, zaf);
                zaz(5);
                return null;
            case '{':
                this.zal.push(1);
                while (true) {
                    try {
                        FastJsonResponse zaf2 = field.zaf();
                        if (zai(bufferedReader, zaf2)) {
                            arrayList.add(zaf2);
                            char zaw2 = zaw(bufferedReader);
                            switch (zaw2) {
                                case ',':
                                    if (zaw(bufferedReader) == '{') {
                                        this.zal.push(1);
                                    } else {
                                        throw new ParseException("Expected start of next object in array");
                                    }
                                case ']':
                                    zaz(5);
                                    return arrayList;
                                default:
                                    StringBuilder sb = new StringBuilder(19);
                                    sb.append("Unexpected token: ");
                                    sb.append(zaw2);
                                    throw new ParseException(sb.toString());
                            }
                        } else {
                            return arrayList;
                        }
                    } catch (IllegalAccessException e) {
                        throw new ParseException("Error instantiating inner object", e);
                    } catch (InstantiationException e2) {
                        throw new ParseException("Error instantiating inner object", e2);
                    }
                }
            default:
                StringBuilder sb2 = new StringBuilder(19);
                sb2.append("Unexpected token: ");
                sb2.append(zaw);
                throw new ParseException(sb2.toString());
        }
    }

    private final char zaw(BufferedReader bufferedReader) throws ParseException, IOException {
        if (bufferedReader.read(this.zaa) != -1) {
            while (Character.isWhitespace(this.zaa[0])) {
                if (bufferedReader.read(this.zaa) == -1) {
                    return (char) 0;
                }
            }
            return this.zaa[0];
        }
        return (char) 0;
    }

    private final int zax(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        int i;
        boolean z;
        char zaw = zaw(bufferedReader);
        if (zaw == 0) {
            throw new ParseException("Unexpected EOF");
        }
        if (zaw == ',') {
            throw new ParseException("Missing value");
        }
        if (zaw == 'n') {
            zay(bufferedReader, zaf);
            return 0;
        }
        bufferedReader.mark(1024);
        if (zaw == '\"') {
            i = 0;
            boolean z2 = false;
            while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                char c = cArr[i];
                if (Character.isISOControl(c)) {
                    throw new ParseException("Unexpected control character while reading string");
                }
                if (c != '\"') {
                    z = c == '\\' ? !z2 : false;
                } else if (!z2) {
                    bufferedReader.reset();
                    bufferedReader.skip(i + 1);
                    return i;
                } else {
                    z = false;
                }
                z2 = z;
                i++;
            }
        } else {
            cArr[0] = zaw;
            i = 1;
            while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                char c2 = cArr[i];
                if (c2 == '}' || c2 == ',' || Character.isWhitespace(c2) || cArr[i] == ']') {
                    bufferedReader.reset();
                    bufferedReader.skip(i - 1);
                    cArr[i] = 0;
                    return i;
                }
                i++;
            }
        }
        if (i == cArr.length) {
            throw new ParseException("Absurdly long value");
        }
        throw new ParseException("Unexpected EOF");
    }

    private final void zay(BufferedReader bufferedReader, char[] cArr) throws ParseException, IOException {
        int i = 0;
        while (true) {
            int i2 = i;
            int length = cArr.length;
            if (i2 >= length) {
                return;
            }
            int read = bufferedReader.read(this.zab, 0, length - i2);
            if (read == -1) {
                throw new ParseException("Unexpected EOF");
            }
            for (int i3 = 0; i3 < read; i3++) {
                if (cArr[i3 + i2] != this.zab[i3]) {
                    throw new ParseException("Unexpected character");
                }
            }
            i = i2 + read;
        }
    }

    private final void zaz(int i) throws ParseException {
        if (this.zal.isEmpty()) {
            StringBuilder sb = new StringBuilder(46);
            sb.append("Expected state ");
            sb.append(i);
            sb.append(" but had empty stack");
            throw new ParseException(sb.toString());
        }
        int intValue = this.zal.pop().intValue();
        if (intValue != i) {
            StringBuilder sb2 = new StringBuilder(46);
            sb2.append("Expected state ");
            sb2.append(i);
            sb2.append(" but had ");
            sb2.append(intValue);
            throw new ParseException(sb2.toString());
        }
    }

    @KeepForSdk
    public void parse(@RecentlyNonNull InputStream inputStream, @RecentlyNonNull T t) throws ParseException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        try {
            try {
                this.zal.push(0);
                char zaw = zaw(bufferedReader);
                switch (zaw) {
                    case 0:
                        throw new ParseException("No data to parse");
                    case '[':
                        this.zal.push(5);
                        Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = t.getFieldMappings();
                        if (fieldMappings.size() != 1) {
                            throw new ParseException("Object array response class must have a single Field");
                        }
                        FastJsonResponse.Field<?, ?> value = fieldMappings.entrySet().iterator().next().getValue();
                        t.addConcreteTypeArrayInternal(value, value.zae, zav(bufferedReader, value));
                        break;
                    case '{':
                        this.zal.push(1);
                        zai(bufferedReader, t);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder(19);
                        sb.append("Unexpected token: ");
                        sb.append(zaw);
                        throw new ParseException(sb.toString());
                }
                zaz(0);
            } catch (IOException e) {
                throw new ParseException(e);
            }
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e2) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
        }
    }
}
