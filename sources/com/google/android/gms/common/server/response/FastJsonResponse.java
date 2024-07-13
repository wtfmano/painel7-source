package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
@ShowFirstParty
@KeepForSdk
/* loaded from: classes.dex */
public abstract class FastJsonResponse {

    /* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
    @ShowFirstParty
    /* loaded from: classes.dex */
    public interface FieldConverter<I, O> {
        int zaa();

        int zab();

        @RecentlyNonNull
        I zac(@RecentlyNonNull O o);

        @RecentlyNullable
        O zad(@RecentlyNonNull I i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @RecentlyNonNull
    public static final <O, I> I zaD(@RecentlyNonNull Field<I, O> field, @Nullable Object obj) {
        return ((Field) field).zak != null ? field.zai(obj) : obj;
    }

    private final <I, O> void zaE(Field<I, O> field, @Nullable I i) {
        String str = field.zae;
        O zah = field.zah(i);
        int i2 = field.zac;
        switch (i2) {
            case 0:
                if (zah != null) {
                    setIntegerInternal(field, str, ((Integer) zah).intValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 1:
                zat(field, str, (BigInteger) zah);
                return;
            case 2:
                if (zah != null) {
                    setLongInternal(field, str, ((Long) zah).longValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 3:
            default:
                StringBuilder sb = new StringBuilder(44);
                sb.append("Unsupported type for conversion: ");
                sb.append(i2);
                throw new IllegalStateException(sb.toString());
            case 4:
                if (zah != null) {
                    zay(field, str, ((Double) zah).doubleValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 5:
                zaA(field, str, (BigDecimal) zah);
                return;
            case 6:
                if (zah != null) {
                    setBooleanInternal(field, str, ((Boolean) zah).booleanValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 7:
                setStringInternal(field, str, (String) zah);
                return;
            case 8:
            case 9:
                if (zah != null) {
                    setDecodedBytesInternal(field, str, (byte[]) zah);
                    return;
                } else {
                    zaG(str);
                    return;
                }
        }
    }

    private static final void zaF(StringBuilder sb, Field field, Object obj) {
        int i = field.zaa;
        if (i == 11) {
            Class<? extends FastJsonResponse> cls = field.zag;
            Preconditions.checkNotNull(cls);
            sb.append(cls.cast(obj).toString());
        } else if (i == 7) {
            sb.append("\"");
            sb.append(JsonUtils.escapeString((String) obj));
            sb.append("\"");
        } else {
            sb.append(obj);
        }
    }

    private static final <O> void zaG(String str) {
        if (Log.isLoggable("FastJsonResponse", 6)) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 58);
            sb.append("Output field (");
            sb.append(str);
            sb.append(") has a null value, but expected a primitive");
            Log.e("FastJsonResponse", sb.toString());
        }
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@RecentlyNonNull Field field, @RecentlyNonNull String str, @Nullable ArrayList<T> arrayList) {
        throw new UnsupportedOperationException("Concrete type array not supported");
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeInternal(@RecentlyNonNull Field field, @RecentlyNonNull String str, @RecentlyNonNull T t) {
        throw new UnsupportedOperationException("Concrete type not supported");
    }

    @RecentlyNonNull
    @KeepForSdk
    public abstract Map<String, Field<?, ?>> getFieldMappings();

    @RecentlyNullable
    @KeepForSdk
    protected abstract Object getValueObject(@RecentlyNonNull String str);

    @KeepForSdk
    protected abstract boolean isPrimitiveFieldSet(@RecentlyNonNull String str);

    @KeepForSdk
    protected void setBooleanInternal(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, boolean z) {
        throw new UnsupportedOperationException("Boolean not supported");
    }

    @KeepForSdk
    protected void setDecodedBytesInternal(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable byte[] bArr) {
        throw new UnsupportedOperationException("byte[] not supported");
    }

    @KeepForSdk
    protected void setIntegerInternal(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, int i) {
        throw new UnsupportedOperationException("Integer not supported");
    }

    @KeepForSdk
    protected void setLongInternal(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, long j) {
        throw new UnsupportedOperationException("Long not supported");
    }

    @KeepForSdk
    protected void setStringInternal(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable String str2) {
        throw new UnsupportedOperationException("String not supported");
    }

    @KeepForSdk
    protected void setStringMapInternal(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable Map<String, String> map) {
        throw new UnsupportedOperationException("String map not supported");
    }

    @KeepForSdk
    protected void setStringsInternal(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable ArrayList<String> arrayList) {
        throw new UnsupportedOperationException("String list not supported");
    }

    @RecentlyNonNull
    @KeepForSdk
    public String toString() {
        Map<String, Field<?, ?>> fieldMappings = getFieldMappings();
        StringBuilder sb = new StringBuilder(100);
        for (String str : fieldMappings.keySet()) {
            Field<?, ?> field = fieldMappings.get(str);
            if (isFieldSet(field)) {
                Object zaD = zaD(field, getFieldValue(field));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append("\"");
                sb.append(str);
                sb.append("\":");
                if (zaD == null) {
                    sb.append("null");
                } else {
                    switch (field.zac) {
                        case 8:
                            sb.append("\"");
                            sb.append(Base64Utils.encode((byte[]) zaD));
                            sb.append("\"");
                            continue;
                        case 9:
                            sb.append("\"");
                            sb.append(Base64Utils.encodeUrlSafe((byte[]) zaD));
                            sb.append("\"");
                            continue;
                        case 10:
                            MapUtils.writeStringMapToJson(sb, (HashMap) zaD);
                            continue;
                        default:
                            if (field.zab) {
                                ArrayList arrayList = (ArrayList) zaD;
                                sb.append("[");
                                int size = arrayList.size();
                                for (int i = 0; i < size; i++) {
                                    if (i > 0) {
                                        sb.append(",");
                                    }
                                    Object obj = arrayList.get(i);
                                    if (obj != null) {
                                        zaF(sb, field, obj);
                                    }
                                }
                                sb.append("]");
                                break;
                            } else {
                                zaF(sb, field, zaD);
                                continue;
                            }
                    }
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }

    protected void zaA(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("BigDecimal not supported");
    }

    protected void zaB(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable ArrayList<BigDecimal> arrayList) {
        throw new UnsupportedOperationException("BigDecimal list not supported");
    }

    protected void zaC(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable ArrayList<Boolean> arrayList) {
        throw new UnsupportedOperationException("Boolean list not supported");
    }

    public final <O> void zaa(@RecentlyNonNull Field<Integer, O> field, int i) {
        if (((Field) field).zak == null) {
            setIntegerInternal(field, field.zae, i);
        } else {
            zaE(field, Integer.valueOf(i));
        }
    }

    public final <O> void zab(@RecentlyNonNull Field<ArrayList<Integer>, O> field, @Nullable ArrayList<Integer> arrayList) {
        if (((Field) field).zak == null) {
            zas(field, field.zae, arrayList);
        } else {
            zaE(field, arrayList);
        }
    }

    public final <O> void zac(@RecentlyNonNull Field<BigInteger, O> field, @Nullable BigInteger bigInteger) {
        if (((Field) field).zak == null) {
            zat(field, field.zae, bigInteger);
        } else {
            zaE(field, bigInteger);
        }
    }

    public final <O> void zad(@RecentlyNonNull Field<ArrayList<BigInteger>, O> field, @Nullable ArrayList<BigInteger> arrayList) {
        if (((Field) field).zak == null) {
            zau(field, field.zae, arrayList);
        } else {
            zaE(field, arrayList);
        }
    }

    public final <O> void zae(@RecentlyNonNull Field<Long, O> field, long j) {
        if (((Field) field).zak == null) {
            setLongInternal(field, field.zae, j);
        } else {
            zaE(field, Long.valueOf(j));
        }
    }

    public final <O> void zaf(@RecentlyNonNull Field<ArrayList<Long>, O> field, @Nullable ArrayList<Long> arrayList) {
        if (((Field) field).zak == null) {
            zav(field, field.zae, arrayList);
        } else {
            zaE(field, arrayList);
        }
    }

    public final <O> void zag(@RecentlyNonNull Field<Float, O> field, float f) {
        if (((Field) field).zak == null) {
            zaw(field, field.zae, f);
        } else {
            zaE(field, Float.valueOf(f));
        }
    }

    public final <O> void zah(@RecentlyNonNull Field<ArrayList<Float>, O> field, @Nullable ArrayList<Float> arrayList) {
        if (((Field) field).zak == null) {
            zax(field, field.zae, arrayList);
        } else {
            zaE(field, arrayList);
        }
    }

    public final <O> void zai(@RecentlyNonNull Field<Double, O> field, double d) {
        if (((Field) field).zak == null) {
            zay(field, field.zae, d);
        } else {
            zaE(field, Double.valueOf(d));
        }
    }

    public final <O> void zaj(@RecentlyNonNull Field<ArrayList<Double>, O> field, @Nullable ArrayList<Double> arrayList) {
        if (((Field) field).zak == null) {
            zaz(field, field.zae, arrayList);
        } else {
            zaE(field, arrayList);
        }
    }

    public final <O> void zak(@RecentlyNonNull Field<BigDecimal, O> field, @Nullable BigDecimal bigDecimal) {
        if (((Field) field).zak == null) {
            zaA(field, field.zae, bigDecimal);
        } else {
            zaE(field, bigDecimal);
        }
    }

    public final <O> void zal(@RecentlyNonNull Field<ArrayList<BigDecimal>, O> field, @Nullable ArrayList<BigDecimal> arrayList) {
        if (((Field) field).zak == null) {
            zaB(field, field.zae, arrayList);
        } else {
            zaE(field, arrayList);
        }
    }

    public final <O> void zam(@RecentlyNonNull Field<Boolean, O> field, boolean z) {
        if (((Field) field).zak == null) {
            setBooleanInternal(field, field.zae, z);
        } else {
            zaE(field, Boolean.valueOf(z));
        }
    }

    public final <O> void zan(@RecentlyNonNull Field<ArrayList<Boolean>, O> field, @Nullable ArrayList<Boolean> arrayList) {
        if (((Field) field).zak == null) {
            zaC(field, field.zae, arrayList);
        } else {
            zaE(field, arrayList);
        }
    }

    public final <O> void zao(@RecentlyNonNull Field<String, O> field, @Nullable String str) {
        if (((Field) field).zak == null) {
            setStringInternal(field, field.zae, str);
        } else {
            zaE(field, str);
        }
    }

    public final <O> void zap(@RecentlyNonNull Field<ArrayList<String>, O> field, @Nullable ArrayList<String> arrayList) {
        if (((Field) field).zak == null) {
            setStringsInternal(field, field.zae, arrayList);
        } else {
            zaE(field, arrayList);
        }
    }

    public final <O> void zaq(@RecentlyNonNull Field<byte[], O> field, @Nullable byte[] bArr) {
        if (((Field) field).zak == null) {
            setDecodedBytesInternal(field, field.zae, bArr);
        } else {
            zaE(field, bArr);
        }
    }

    public final <O> void zar(@RecentlyNonNull Field<Map<String, String>, O> field, @Nullable Map<String, String> map) {
        if (((Field) field).zak == null) {
            setStringMapInternal(field, field.zae, map);
        } else {
            zaE(field, map);
        }
    }

    protected void zas(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable ArrayList<Integer> arrayList) {
        throw new UnsupportedOperationException("Integer list not supported");
    }

    protected void zat(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable BigInteger bigInteger) {
        throw new UnsupportedOperationException("BigInteger not supported");
    }

    protected void zau(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable ArrayList<BigInteger> arrayList) {
        throw new UnsupportedOperationException("BigInteger list not supported");
    }

    protected void zav(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable ArrayList<Long> arrayList) {
        throw new UnsupportedOperationException("Long list not supported");
    }

    protected void zaw(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, float f) {
        throw new UnsupportedOperationException("Float not supported");
    }

    protected void zax(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable ArrayList<Float> arrayList) {
        throw new UnsupportedOperationException("Float list not supported");
    }

    protected void zay(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, double d) {
        throw new UnsupportedOperationException("Double not supported");
    }

    protected void zaz(@RecentlyNonNull Field<?, ?> field, @RecentlyNonNull String str, @Nullable ArrayList<Double> arrayList) {
        throw new UnsupportedOperationException("Double list not supported");
    }

    /* compiled from: com.google.android.gms:play-services-base@@17.6.0 */
    @VisibleForTesting
    @SafeParcelable.Class(creator = "FieldCreator")
    @ShowFirstParty
    @KeepForSdk
    /* loaded from: classes.dex */
    public static class Field<I, O> extends AbstractSafeParcelable {
        public static final zaj CREATOR = new zaj();
        @SafeParcelable.Field(getter = "getTypeIn", id = 2)
        protected final int zaa;
        @SafeParcelable.Field(getter = "isTypeInArray", id = 3)
        protected final boolean zab;
        @SafeParcelable.Field(getter = "getTypeOut", id = 4)
        protected final int zac;
        @SafeParcelable.Field(getter = "isTypeOutArray", id = 5)
        protected final boolean zad;
        @RecentlyNonNull
        @SafeParcelable.Field(getter = "getOutputFieldName", id = 6)
        protected final String zae;
        @SafeParcelable.Field(getter = "getSafeParcelableFieldId", id = 7)
        protected final int zaf;
        @RecentlyNullable
        protected final Class<? extends FastJsonResponse> zag;
        @RecentlyNullable
        @SafeParcelable.Field(getter = "getConcreteTypeName", id = 8)
        protected final String zah;
        @SafeParcelable.VersionField(getter = "getVersionCode", id = 1)
        private final int zai;
        private zan zaj;
        @Nullable
        @SafeParcelable.Field(getter = "getWrappedConverter", id = 9, type = "com.google.android.gms.common.server.converter.ConverterWrapper")
        private FieldConverter<I, O> zak;

        @SafeParcelable.Constructor
        public Field(@SafeParcelable.Param(id = 1) int i, @SafeParcelable.Param(id = 2) int i2, @SafeParcelable.Param(id = 3) boolean z, @SafeParcelable.Param(id = 4) int i3, @SafeParcelable.Param(id = 5) boolean z2, @SafeParcelable.Param(id = 6) String str, @SafeParcelable.Param(id = 7) int i4, @Nullable @SafeParcelable.Param(id = 8) String str2, @Nullable @SafeParcelable.Param(id = 9) com.google.android.gms.common.server.converter.zaa zaaVar) {
            this.zai = i;
            this.zaa = i2;
            this.zab = z;
            this.zac = i3;
            this.zad = z2;
            this.zae = str;
            this.zaf = i4;
            if (str2 == null) {
                this.zag = null;
                this.zah = null;
            } else {
                this.zag = SafeParcelResponse.class;
                this.zah = str2;
            }
            if (zaaVar != null) {
                this.zak = (FieldConverter<I, O>) zaaVar.zab();
            } else {
                this.zak = null;
            }
        }

        @RecentlyNonNull
        @VisibleForTesting
        @KeepForSdk
        public static Field<byte[], byte[]> forBase64(@RecentlyNonNull String str, int i) {
            return new Field<>(8, false, 8, false, str, i, null, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static Field<Boolean, Boolean> forBoolean(@RecentlyNonNull String str, int i) {
            return new Field<>(6, false, 6, false, str, i, null, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(@RecentlyNonNull String str, int i, @RecentlyNonNull Class<T> cls) {
            return new Field<>(11, false, 11, false, str, i, cls, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(@RecentlyNonNull String str, int i, @RecentlyNonNull Class<T> cls) {
            return new Field<>(11, true, 11, true, str, i, cls, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static Field<Double, Double> forDouble(@RecentlyNonNull String str, int i) {
            return new Field<>(4, false, 4, false, str, i, null, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static Field<Float, Float> forFloat(@RecentlyNonNull String str, int i) {
            return new Field<>(3, false, 3, false, str, i, null, null);
        }

        @RecentlyNonNull
        @VisibleForTesting
        @KeepForSdk
        public static Field<Integer, Integer> forInteger(@RecentlyNonNull String str, int i) {
            return new Field<>(0, false, 0, false, str, i, null, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static Field<Long, Long> forLong(@RecentlyNonNull String str, int i) {
            return new Field<>(2, false, 2, false, str, i, null, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static Field<String, String> forString(@RecentlyNonNull String str, int i) {
            return new Field<>(7, false, 7, false, str, i, null, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static Field<HashMap<String, String>, HashMap<String, String>> forStringMap(@RecentlyNonNull String str, int i) {
            return new Field<>(10, false, 10, false, str, i, null, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static Field<ArrayList<String>, ArrayList<String>> forStrings(@RecentlyNonNull String str, int i) {
            return new Field<>(7, true, 7, true, str, i, null, null);
        }

        @RecentlyNonNull
        @KeepForSdk
        public static Field withConverter(@RecentlyNonNull String str, int i, @RecentlyNonNull FieldConverter<?, ?> fieldConverter, boolean z) {
            fieldConverter.zaa();
            fieldConverter.zab();
            return new Field(7, z, 0, false, str, i, null, fieldConverter);
        }

        @KeepForSdk
        public int getSafeParcelableFieldId() {
            return this.zaf;
        }

        @RecentlyNonNull
        public final String toString() {
            Objects.ToStringHelper add = Objects.toStringHelper(this).add("versionCode", Integer.valueOf(this.zai)).add("typeIn", Integer.valueOf(this.zaa)).add("typeInArray", Boolean.valueOf(this.zab)).add("typeOut", Integer.valueOf(this.zac)).add("typeOutArray", Boolean.valueOf(this.zad)).add("outputFieldName", this.zae).add("safeParcelFieldId", Integer.valueOf(this.zaf)).add("concreteTypeName", zab());
            Class<? extends FastJsonResponse> cls = this.zag;
            if (cls != null) {
                add.add("concreteType.class", cls.getCanonicalName());
            }
            FieldConverter<I, O> fieldConverter = this.zak;
            if (fieldConverter != null) {
                add.add("converterName", fieldConverter.getClass().getCanonicalName());
            }
            return add.toString();
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, this.zai);
            SafeParcelWriter.writeInt(parcel, 2, this.zaa);
            SafeParcelWriter.writeBoolean(parcel, 3, this.zab);
            SafeParcelWriter.writeInt(parcel, 4, this.zac);
            SafeParcelWriter.writeBoolean(parcel, 5, this.zad);
            SafeParcelWriter.writeString(parcel, 6, this.zae, false);
            SafeParcelWriter.writeInt(parcel, 7, getSafeParcelableFieldId());
            SafeParcelWriter.writeString(parcel, 8, zab(), false);
            SafeParcelWriter.writeParcelable(parcel, 9, zae(), i, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }

        @RecentlyNonNull
        public final Field<I, O> zaa() {
            return new Field<>(this.zai, this.zaa, this.zab, this.zac, this.zad, this.zae, this.zaf, this.zah, zae());
        }

        @Nullable
        final String zab() {
            String str = this.zah;
            if (str == null) {
                return null;
            }
            return str;
        }

        public final boolean zac() {
            return this.zak != null;
        }

        public final void zad(zan zanVar) {
            this.zaj = zanVar;
        }

        @Nullable
        final com.google.android.gms.common.server.converter.zaa zae() {
            FieldConverter<I, O> fieldConverter = this.zak;
            if (fieldConverter == null) {
                return null;
            }
            return com.google.android.gms.common.server.converter.zaa.zaa(fieldConverter);
        }

        @RecentlyNonNull
        public final FastJsonResponse zaf() throws InstantiationException, IllegalAccessException {
            Preconditions.checkNotNull(this.zag);
            Class<? extends FastJsonResponse> cls = this.zag;
            if (cls == SafeParcelResponse.class) {
                Preconditions.checkNotNull(this.zah);
                Preconditions.checkNotNull(this.zaj, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
                return new SafeParcelResponse(this.zaj, this.zah);
            }
            return cls.newInstance();
        }

        @RecentlyNonNull
        public final Map<String, Field<?, ?>> zag() {
            Preconditions.checkNotNull(this.zah);
            Preconditions.checkNotNull(this.zaj);
            return (Map) Preconditions.checkNotNull(this.zaj.zad(this.zah));
        }

        @RecentlyNonNull
        public final O zah(@Nullable I i) {
            Preconditions.checkNotNull(this.zak);
            return (O) Preconditions.checkNotNull(this.zak.zad(i));
        }

        @RecentlyNonNull
        public final I zai(@RecentlyNonNull O o) {
            Preconditions.checkNotNull(this.zak);
            return this.zak.zac(o);
        }

        protected Field(int i, boolean z, int i2, boolean z2, @RecentlyNonNull String str, int i3, @Nullable Class<? extends FastJsonResponse> cls, @Nullable FieldConverter<I, O> fieldConverter) {
            this.zai = 1;
            this.zaa = i;
            this.zab = z;
            this.zac = i2;
            this.zad = z2;
            this.zae = str;
            this.zaf = i3;
            this.zag = cls;
            if (cls == null) {
                this.zah = null;
            } else {
                this.zah = cls.getCanonicalName();
            }
            this.zak = fieldConverter;
        }
    }

    @RecentlyNullable
    @KeepForSdk
    public Object getFieldValue(@RecentlyNonNull Field field) {
        String str = field.zae;
        if (field.zag == null) {
            return getValueObject(str);
        }
        Preconditions.checkState(getValueObject(str) == null, "Concrete field shouldn't be value object: %s", field.zae);
        boolean z = field.zad;
        try {
            char upperCase = Character.toUpperCase(str.charAt(0));
            String substring = str.substring(1);
            StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 4);
            sb.append("get");
            sb.append(upperCase);
            sb.append(substring);
            return getClass().getMethod(sb.toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KeepForSdk
    public boolean isFieldSet(@RecentlyNonNull Field field) {
        if (field.zac == 11) {
            if (!field.zad) {
                String str = field.zae;
                throw new UnsupportedOperationException("Concrete types not supported");
            }
            String str2 = field.zae;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        return isPrimitiveFieldSet(field.zae);
    }
}
