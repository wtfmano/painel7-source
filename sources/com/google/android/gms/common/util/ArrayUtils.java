package com.google.android.gms.common.util;

import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@VisibleForTesting
@KeepForSdk
/* loaded from: classes.dex */
public final class ArrayUtils {
    private ArrayUtils() {
    }

    @RecentlyNonNull
    @KeepForSdk
    public static <T> T[] appendToArray(@RecentlyNonNull T[] tArr, @RecentlyNonNull T t) {
        Object[] copyOf;
        if (tArr != null || t != null) {
            if (tArr == null) {
                copyOf = (Object[]) Array.newInstance(t.getClass(), 1);
            } else {
                copyOf = Arrays.copyOf(tArr, tArr.length + 1);
            }
            copyOf[copyOf.length - 1] = t;
            return (T[]) copyOf;
        }
        throw new IllegalArgumentException("Cannot generate array of generic type w/o class info");
    }

    @RecentlyNonNull
    @KeepForSdk
    public static <T> T[] concat(@RecentlyNonNull T[]... tArr) {
        if (tArr.length != 0) {
            int i = 0;
            for (T[] tArr2 : tArr) {
                i += tArr2.length;
            }
            T[] tArr3 = (T[]) Arrays.copyOf(tArr[0], i);
            int length = tArr[0].length;
            for (int i2 = 1; i2 < tArr.length; i2++) {
                T[] tArr4 = tArr[i2];
                int length2 = tArr4.length;
                System.arraycopy(tArr4, 0, tArr3, length, length2);
                length += length2;
            }
            return tArr3;
        }
        return (T[]) ((Object[]) Array.newInstance(tArr.getClass(), 0));
    }

    @RecentlyNonNull
    @KeepForSdk
    public static byte[] concatByteArrays(@RecentlyNonNull byte[]... bArr) {
        if (bArr.length != 0) {
            int i = 0;
            for (byte[] bArr2 : bArr) {
                i += bArr2.length;
            }
            byte[] copyOf = Arrays.copyOf(bArr[0], i);
            int length = bArr[0].length;
            for (int i2 = 1; i2 < bArr.length; i2++) {
                byte[] bArr3 = bArr[i2];
                int length2 = bArr3.length;
                System.arraycopy(bArr3, 0, copyOf, length, length2);
                length += length2;
            }
            return copyOf;
        }
        return new byte[0];
    }

    @KeepForSdk
    public static boolean contains(@RecentlyNonNull int[] iArr, int i) {
        if (iArr == null) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    @RecentlyNonNull
    @KeepForSdk
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    @RecentlyNonNull
    @KeepForSdk
    public static <T> ArrayList<T> toArrayList(@RecentlyNonNull T[] tArr) {
        ArrayList<T> arrayList = new ArrayList<>(tArr.length);
        for (T t : tArr) {
            arrayList.add(t);
        }
        return arrayList;
    }

    @RecentlyNonNull
    @KeepForSdk
    public static int[] toPrimitiveArray(@RecentlyNonNull Collection<Integer> collection) {
        if (collection == null || collection.size() == 0) {
            return new int[0];
        }
        int[] iArr = new int[collection.size()];
        Iterator<Integer> it = collection.iterator();
        int i = 0;
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return iArr;
            }
            iArr[i2] = it.next().intValue();
            i = i2 + 1;
        }
    }

    @RecentlyNullable
    @KeepForSdk
    public static Integer[] toWrapperArray(@RecentlyNonNull int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        Integer[] numArr = new Integer[length];
        for (int i = 0; i < length; i++) {
            numArr[i] = Integer.valueOf(iArr[i]);
        }
        return numArr;
    }

    @KeepForSdk
    public static void writeArray(@RecentlyNonNull StringBuilder sb, @RecentlyNonNull double[] dArr) {
        int length = dArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Double.toString(dArr[i]));
        }
    }

    @KeepForSdk
    public static void writeStringArray(@RecentlyNonNull StringBuilder sb, @RecentlyNonNull String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append("\"");
            sb.append(strArr[i]);
            sb.append("\"");
        }
    }

    @KeepForSdk
    public static <T> boolean contains(@RecentlyNonNull T[] tArr, @RecentlyNonNull T t) {
        int length = tArr != null ? tArr.length : 0;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (!Objects.equal(tArr[i], t)) {
                i++;
            } else if (i >= 0) {
                return true;
            }
        }
        return false;
    }

    @KeepForSdk
    public static void writeArray(@RecentlyNonNull StringBuilder sb, @RecentlyNonNull float[] fArr) {
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Float.toString(fArr[i]));
        }
    }

    @KeepForSdk
    public static void writeArray(@RecentlyNonNull StringBuilder sb, @RecentlyNonNull int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Integer.toString(iArr[i]));
        }
    }

    @RecentlyNullable
    @KeepForSdk
    public static <T> T[] removeAll(@RecentlyNonNull T[] tArr, @RecentlyNonNull T... tArr2) {
        int length;
        int i;
        Object[] objArr;
        if (tArr == null) {
            return null;
        }
        if (tArr2 == null || (length = tArr2.length) == 0) {
            return (T[]) Arrays.copyOf(tArr, tArr.length);
        }
        Object[] objArr2 = (Object[]) Array.newInstance(tArr2.getClass().getComponentType(), tArr.length);
        if (length == 1) {
            int i2 = 0;
            for (T t : tArr) {
                if (!Objects.equal(tArr2[0], t)) {
                    objArr2[i2] = t;
                    i2++;
                }
            }
            i = i2;
        } else {
            int i3 = 0;
            for (T t2 : tArr) {
                if (!contains(tArr2, t2)) {
                    objArr2[i3] = t2;
                    i3++;
                }
            }
            i = i3;
        }
        if (objArr2 == null) {
            objArr = null;
        } else if (i != objArr2.length) {
            return (T[]) Arrays.copyOf(objArr2, i);
        } else {
            objArr = objArr2;
        }
        return (T[]) objArr;
    }

    @KeepForSdk
    public static void writeArray(@RecentlyNonNull StringBuilder sb, @RecentlyNonNull long[] jArr) {
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Long.toString(jArr[i]));
        }
    }

    @KeepForSdk
    public static <T> void writeArray(@RecentlyNonNull StringBuilder sb, @RecentlyNonNull T[] tArr) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(tArr[i]);
        }
    }

    @KeepForSdk
    public static void writeArray(@RecentlyNonNull StringBuilder sb, @RecentlyNonNull boolean[] zArr) {
        int length = zArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Boolean.toString(zArr[i]));
        }
    }
}
