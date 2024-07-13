package androidx.versionedparcelable;

import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseBooleanArray;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArraySet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public abstract class VersionedParcel {
    private static final int EX_BAD_PARCELABLE = -2;
    private static final int EX_ILLEGAL_ARGUMENT = -3;
    private static final int EX_ILLEGAL_STATE = -5;
    private static final int EX_NETWORK_MAIN_THREAD = -6;
    private static final int EX_NULL_POINTER = -4;
    private static final int EX_PARCELABLE = -9;
    private static final int EX_SECURITY = -1;
    private static final int EX_UNSUPPORTED_OPERATION = -7;
    private static final String TAG = "VersionedParcel";
    private static final int TYPE_BINDER = 5;
    private static final int TYPE_PARCELABLE = 2;
    private static final int TYPE_SERIALIZABLE = 3;
    private static final int TYPE_STRING = 4;
    private static final int TYPE_VERSIONED_PARCELABLE = 1;

    protected abstract void closeField();

    protected abstract VersionedParcel createSubParcel();

    protected abstract boolean readBoolean();

    protected abstract Bundle readBundle();

    protected abstract byte[] readByteArray();

    protected abstract double readDouble();

    protected abstract boolean readField(int i);

    protected abstract float readFloat();

    protected abstract int readInt();

    protected abstract long readLong();

    protected abstract <T extends Parcelable> T readParcelable();

    protected abstract String readString();

    protected abstract IBinder readStrongBinder();

    protected abstract void setOutputField(int i);

    protected abstract void writeBoolean(boolean z);

    protected abstract void writeBundle(Bundle bundle);

    protected abstract void writeByteArray(byte[] bArr);

    protected abstract void writeByteArray(byte[] bArr, int i, int i2);

    protected abstract void writeDouble(double d);

    protected abstract void writeFloat(float f);

    protected abstract void writeInt(int i);

    protected abstract void writeLong(long j);

    protected abstract void writeParcelable(Parcelable parcelable);

    protected abstract void writeString(String str);

    protected abstract void writeStrongBinder(IBinder iBinder);

    protected abstract void writeStrongInterface(IInterface iInterface);

    public boolean isStream() {
        return false;
    }

    public void setSerializationFlags(boolean allowSerialization, boolean ignoreParcelables) {
    }

    public void writeStrongInterface(IInterface val, int fieldId) {
        setOutputField(fieldId);
        writeStrongInterface(val);
    }

    public void writeBundle(Bundle val, int fieldId) {
        setOutputField(fieldId);
        writeBundle(val);
    }

    public void writeBoolean(boolean val, int fieldId) {
        setOutputField(fieldId);
        writeBoolean(val);
    }

    public void writeByteArray(byte[] b, int fieldId) {
        setOutputField(fieldId);
        writeByteArray(b);
    }

    public void writeByteArray(byte[] b, int offset, int len, int fieldId) {
        setOutputField(fieldId);
        writeByteArray(b, offset, len);
    }

    public void writeInt(int val, int fieldId) {
        setOutputField(fieldId);
        writeInt(val);
    }

    public void writeLong(long val, int fieldId) {
        setOutputField(fieldId);
        writeLong(val);
    }

    public void writeFloat(float val, int fieldId) {
        setOutputField(fieldId);
        writeFloat(val);
    }

    public void writeDouble(double val, int fieldId) {
        setOutputField(fieldId);
        writeDouble(val);
    }

    public void writeString(String val, int fieldId) {
        setOutputField(fieldId);
        writeString(val);
    }

    public void writeStrongBinder(IBinder val, int fieldId) {
        setOutputField(fieldId);
        writeStrongBinder(val);
    }

    public void writeParcelable(Parcelable p, int fieldId) {
        setOutputField(fieldId);
        writeParcelable(p);
    }

    public boolean readBoolean(boolean def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        boolean def2 = readBoolean();
        return def2;
    }

    public int readInt(int def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        int def2 = readInt();
        return def2;
    }

    public long readLong(long def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        long def2 = readLong();
        return def2;
    }

    public float readFloat(float def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        float def2 = readFloat();
        return def2;
    }

    public double readDouble(double def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        double def2 = readDouble();
        return def2;
    }

    public String readString(String def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        String def2 = readString();
        return def2;
    }

    public IBinder readStrongBinder(IBinder def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        IBinder def2 = readStrongBinder();
        return def2;
    }

    public byte[] readByteArray(byte[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        byte[] def2 = readByteArray();
        return def2;
    }

    public <T extends Parcelable> T readParcelable(T def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        T def2 = (T) readParcelable();
        return def2;
    }

    public Bundle readBundle(Bundle def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        Bundle def2 = readBundle();
        return def2;
    }

    public void writeByte(byte val, int fieldId) {
        setOutputField(fieldId);
        writeInt(val);
    }

    @RequiresApi(api = 21)
    public void writeSize(Size val, int fieldId) {
        setOutputField(fieldId);
        writeBoolean(val != null);
        if (val != null) {
            writeInt(val.getWidth());
            writeInt(val.getHeight());
        }
    }

    @RequiresApi(api = 21)
    public void writeSizeF(SizeF val, int fieldId) {
        setOutputField(fieldId);
        writeBoolean(val != null);
        if (val != null) {
            writeFloat(val.getWidth());
            writeFloat(val.getHeight());
        }
    }

    public void writeSparseBooleanArray(SparseBooleanArray val, int fieldId) {
        setOutputField(fieldId);
        if (val == null) {
            writeInt(-1);
            return;
        }
        int n = val.size();
        writeInt(n);
        for (int i = 0; i < n; i++) {
            writeInt(val.keyAt(i));
            writeBoolean(val.valueAt(i));
        }
    }

    public void writeBooleanArray(boolean[] val, int fieldId) {
        setOutputField(fieldId);
        writeBooleanArray(val);
    }

    public void writeBooleanArray(boolean[] val) {
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (boolean z : val) {
                writeInt(z ? 1 : 0);
            }
            return;
        }
        writeInt(-1);
    }

    public boolean[] readBooleanArray(boolean[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        boolean[] def2 = readBooleanArray();
        return def2;
    }

    public boolean[] readBooleanArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        boolean[] val = new boolean[n];
        for (int i = 0; i < n; i++) {
            val[i] = readInt() != 0;
        }
        return val;
    }

    public void writeCharArray(char[] val, int fieldId) {
        setOutputField(fieldId);
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (char c : val) {
                writeInt(c);
            }
            return;
        }
        writeInt(-1);
    }

    public char[] readCharArray(char[] def, int fieldId) {
        if (readField(fieldId)) {
            int n = readInt();
            if (n < 0) {
                return null;
            }
            char[] val = new char[n];
            for (int i = 0; i < n; i++) {
                val[i] = (char) readInt();
            }
            return val;
        }
        return def;
    }

    public void writeIntArray(int[] val, int fieldId) {
        setOutputField(fieldId);
        writeIntArray(val);
    }

    public void writeIntArray(int[] val) {
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (int i : val) {
                writeInt(i);
            }
            return;
        }
        writeInt(-1);
    }

    public int[] readIntArray(int[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        int[] def2 = readIntArray();
        return def2;
    }

    public int[] readIntArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        int[] val = new int[n];
        for (int i = 0; i < n; i++) {
            val[i] = readInt();
        }
        return val;
    }

    public void writeLongArray(long[] val, int fieldId) {
        setOutputField(fieldId);
        writeLongArray(val);
    }

    public void writeLongArray(long[] val) {
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (long j : val) {
                writeLong(j);
            }
            return;
        }
        writeInt(-1);
    }

    public long[] readLongArray(long[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        long[] def2 = readLongArray();
        return def2;
    }

    public long[] readLongArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        long[] val = new long[n];
        for (int i = 0; i < n; i++) {
            val[i] = readLong();
        }
        return val;
    }

    public void writeFloatArray(float[] val, int fieldId) {
        setOutputField(fieldId);
        writeFloatArray(val);
    }

    public void writeFloatArray(float[] val) {
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (float f : val) {
                writeFloat(f);
            }
            return;
        }
        writeInt(-1);
    }

    public float[] readFloatArray(float[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        float[] def2 = readFloatArray();
        return def2;
    }

    public float[] readFloatArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        float[] val = new float[n];
        for (int i = 0; i < n; i++) {
            val[i] = readFloat();
        }
        return val;
    }

    public void writeDoubleArray(double[] val, int fieldId) {
        setOutputField(fieldId);
        writeDoubleArray(val);
    }

    public void writeDoubleArray(double[] val) {
        if (val != null) {
            int n = val.length;
            writeInt(n);
            for (double d : val) {
                writeDouble(d);
            }
            return;
        }
        writeInt(-1);
    }

    public double[] readDoubleArray(double[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        double[] def2 = readDoubleArray();
        return def2;
    }

    public double[] readDoubleArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        double[] val = new double[n];
        for (int i = 0; i < n; i++) {
            val[i] = readDouble();
        }
        return val;
    }

    public <T> void writeSet(Set<T> val, int fieldId) {
        writeCollection(val, fieldId);
    }

    public <T> void writeList(List<T> val, int fieldId) {
        writeCollection(val, fieldId);
    }

    private <T> void writeCollection(Collection<T> val, int fieldId) {
        setOutputField(fieldId);
        if (val == null) {
            writeInt(-1);
            return;
        }
        int n = val.size();
        writeInt(n);
        if (n > 0) {
            int type = getType(val.iterator().next());
            writeInt(type);
            switch (type) {
                case 1:
                    for (T v : val) {
                        writeVersionedParcelable((VersionedParcelable) v);
                    }
                    return;
                case 2:
                    for (T v2 : val) {
                        writeParcelable((Parcelable) v2);
                    }
                    return;
                case 3:
                    for (T v3 : val) {
                        writeSerializable((Serializable) v3);
                    }
                    return;
                case 4:
                    for (T v4 : val) {
                        writeString((String) v4);
                    }
                    return;
                case 5:
                    for (T v5 : val) {
                        writeStrongBinder((IBinder) v5);
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public <T> void writeArray(T[] val, int fieldId) {
        setOutputField(fieldId);
        writeArray(val);
    }

    public <T> void writeArray(T[] val) {
        if (val == null) {
            writeInt(-1);
            return;
        }
        int n = val.length;
        int i = 0;
        writeInt(n);
        if (n > 0) {
            int type = getType(val[0]);
            writeInt(type);
            switch (type) {
                case 1:
                    while (i < n) {
                        writeVersionedParcelable((VersionedParcelable) val[i]);
                        i++;
                    }
                    return;
                case 2:
                    while (i < n) {
                        writeParcelable((Parcelable) val[i]);
                        i++;
                    }
                    return;
                case 3:
                    while (i < n) {
                        writeSerializable((Serializable) val[i]);
                        i++;
                    }
                    return;
                case 4:
                    while (i < n) {
                        writeString((String) val[i]);
                        i++;
                    }
                    return;
                case 5:
                    while (i < n) {
                        writeStrongBinder((IBinder) val[i]);
                        i++;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private <T> int getType(T t) {
        if (t instanceof String) {
            return 4;
        }
        if (t instanceof Parcelable) {
            return 2;
        }
        if (t instanceof VersionedParcelable) {
            return 1;
        }
        if (t instanceof Serializable) {
            return 3;
        }
        if (t instanceof IBinder) {
            return 5;
        }
        throw new IllegalArgumentException(t.getClass().getName() + " cannot be VersionedParcelled");
    }

    public void writeVersionedParcelable(VersionedParcelable p, int fieldId) {
        setOutputField(fieldId);
        writeVersionedParcelable(p);
    }

    public void writeVersionedParcelable(VersionedParcelable p) {
        if (p == null) {
            writeString(null);
            return;
        }
        writeVersionedParcelableCreator(p);
        VersionedParcel subParcel = createSubParcel();
        writeToParcel(p, subParcel);
        subParcel.closeField();
    }

    private void writeVersionedParcelableCreator(VersionedParcelable p) {
        try {
            Class name = findParcelClass((Class<? extends VersionedParcelable>) p.getClass());
            writeString(name.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(p.getClass().getSimpleName() + " does not have a Parcelizer", e);
        }
    }

    public void writeSerializable(Serializable s, int fieldId) {
        setOutputField(fieldId);
        writeSerializable(s);
    }

    private void writeSerializable(Serializable s) {
        if (s == null) {
            writeString(null);
            return;
        }
        String name = s.getClass().getName();
        writeString(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(s);
            oos.close();
            writeByteArray(baos.toByteArray());
        } catch (IOException ioe) {
            throw new RuntimeException("VersionedParcelable encountered IOException writing serializable object (name = " + name + ")", ioe);
        }
    }

    public void writeException(Exception e, int fieldId) {
        setOutputField(fieldId);
        if (e == null) {
            writeNoException();
            return;
        }
        int code = 0;
        if ((e instanceof Parcelable) && e.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
            code = -9;
        } else if (e instanceof SecurityException) {
            code = -1;
        } else if (e instanceof BadParcelableException) {
            code = -2;
        } else if (e instanceof IllegalArgumentException) {
            code = -3;
        } else if (e instanceof NullPointerException) {
            code = -4;
        } else if (e instanceof IllegalStateException) {
            code = -5;
        } else if (e instanceof NetworkOnMainThreadException) {
            code = -6;
        } else if (e instanceof UnsupportedOperationException) {
            code = -7;
        }
        writeInt(code);
        if (code == 0) {
            if (e instanceof RuntimeException) {
                throw ((RuntimeException) e);
            }
            throw new RuntimeException(e);
        }
        writeString(e.getMessage());
        switch (code) {
            case -9:
                writeParcelable((Parcelable) e);
                return;
            default:
                return;
        }
    }

    protected void writeNoException() {
        writeInt(0);
    }

    public Exception readException(Exception def, int fieldId) {
        int code;
        if (readField(fieldId) && (code = readExceptionCode()) != 0) {
            String msg = readString();
            return readException(code, msg);
        }
        return def;
    }

    private int readExceptionCode() {
        int code = readInt();
        return code;
    }

    private Exception readException(int code, String msg) {
        Exception e = createException(code, msg);
        return e;
    }

    @NonNull
    protected static Throwable getRootCause(@NonNull Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }

    private Exception createException(int code, String msg) {
        switch (code) {
            case -9:
                return (Exception) readParcelable();
            case -8:
            default:
                return new RuntimeException("Unknown exception code: " + code + " msg " + msg);
            case -7:
                return new UnsupportedOperationException(msg);
            case -6:
                return new NetworkOnMainThreadException();
            case -5:
                return new IllegalStateException(msg);
            case -4:
                return new NullPointerException(msg);
            case -3:
                return new IllegalArgumentException(msg);
            case -2:
                return new BadParcelableException(msg);
            case -1:
                return new SecurityException(msg);
        }
    }

    public byte readByte(byte def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        byte def2 = (byte) (readInt() & 255);
        return def2;
    }

    @RequiresApi(api = 21)
    public Size readSize(Size def, int fieldId) {
        if (readField(fieldId)) {
            if (readBoolean()) {
                int width = readInt();
                int height = readInt();
                Size def2 = new Size(width, height);
                return def2;
            }
            return null;
        }
        return def;
    }

    @RequiresApi(api = 21)
    public SizeF readSizeF(SizeF def, int fieldId) {
        if (readField(fieldId)) {
            if (readBoolean()) {
                float width = readFloat();
                float height = readFloat();
                SizeF def2 = new SizeF(width, height);
                return def2;
            }
            return null;
        }
        return def;
    }

    public SparseBooleanArray readSparseBooleanArray(SparseBooleanArray def, int fieldId) {
        if (readField(fieldId)) {
            int n = readInt();
            if (n < 0) {
                return null;
            }
            SparseBooleanArray sa = new SparseBooleanArray(n);
            for (int i = 0; i < n; i++) {
                sa.put(readInt(), readBoolean());
            }
            return sa;
        }
        return def;
    }

    public <T> Set<T> readSet(Set<T> def, int fieldId) {
        if (readField(fieldId)) {
            Set<T> def2 = (Set) readCollection(fieldId, new ArraySet());
            return def2;
        }
        return def;
    }

    public <T> List<T> readList(List<T> def, int fieldId) {
        if (readField(fieldId)) {
            List<T> def2 = (List) readCollection(fieldId, new ArrayList());
            return def2;
        }
        return def;
    }

    private <T, S extends Collection<T>> S readCollection(int fieldId, S list) {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        if (n != 0) {
            int type = readInt();
            if (n < 0) {
                return null;
            }
            switch (type) {
                case 1:
                    while (n > 0) {
                        list.add(readVersionedParcelable());
                        n--;
                    }
                    return list;
                case 2:
                    while (n > 0) {
                        list.add(readParcelable());
                        n--;
                    }
                    return list;
                case 3:
                    while (n > 0) {
                        list.add(readSerializable());
                        n--;
                    }
                    return list;
                case 4:
                    while (n > 0) {
                        list.add(readString());
                        n--;
                    }
                    return list;
                case 5:
                    while (n > 0) {
                        list.add(readStrongBinder());
                        n--;
                    }
                    return list;
                default:
                    return list;
            }
        }
        return list;
    }

    public <T> T[] readArray(T[] def, int fieldId) {
        return !readField(fieldId) ? def : (T[]) readArray(def);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public <T> T[] readArray(T[] def) {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        ArrayList<T> list = new ArrayList<>(n);
        if (n != 0) {
            int type = readInt();
            if (n >= 0) {
                switch (type) {
                    case 1:
                        while (n > 0) {
                            list.add(readVersionedParcelable());
                            n--;
                        }
                        break;
                    case 2:
                        while (n > 0) {
                            list.add(readParcelable());
                            n--;
                        }
                        break;
                    case 3:
                        while (n > 0) {
                            list.add(readSerializable());
                            n--;
                        }
                        break;
                    case 4:
                        while (n > 0) {
                            list.add(readString());
                            n--;
                        }
                        break;
                    case 5:
                        while (n > 0) {
                            list.add(readStrongBinder());
                            n--;
                        }
                        break;
                }
            } else {
                return null;
            }
        }
        return (T[]) list.toArray(def);
    }

    public <T extends VersionedParcelable> T readVersionedParcelable(T def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        T def2 = (T) readVersionedParcelable();
        return def2;
    }

    public <T extends VersionedParcelable> T readVersionedParcelable() {
        String name = readString();
        if (name == null) {
            return null;
        }
        return (T) readFromParcel(name, createSubParcel());
    }

    protected Serializable readSerializable() {
        String name = readString();
        if (name == null) {
            return null;
        }
        byte[] serializedData = readByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais) { // from class: androidx.versionedparcelable.VersionedParcel.1
                @Override // java.io.ObjectInputStream
                protected Class<?> resolveClass(ObjectStreamClass osClass) throws IOException, ClassNotFoundException {
                    Class<?> c = Class.forName(osClass.getName(), false, getClass().getClassLoader());
                    return c != null ? c : super.resolveClass(osClass);
                }
            };
            return (Serializable) ois.readObject();
        } catch (IOException ioe) {
            throw new RuntimeException("VersionedParcelable encountered IOException reading a Serializable object (name = " + name + ")", ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException("VersionedParcelable encountered ClassNotFoundException reading a Serializable object (name = " + name + ")", cnfe);
        }
    }

    protected static <T extends VersionedParcelable> T readFromParcel(String parcelCls, VersionedParcel versionedParcel) {
        try {
            Class cls = Class.forName(parcelCls, true, VersionedParcel.class.getClassLoader());
            return (T) cls.getDeclaredMethod("read", VersionedParcel.class).invoke(null, versionedParcel);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (InvocationTargetException e4) {
            if (e4.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e4.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
        }
    }

    protected static <T extends VersionedParcelable> void writeToParcel(T val, VersionedParcel versionedParcel) {
        try {
            Class cls = findParcelClass(val);
            cls.getDeclaredMethod("write", val.getClass(), VersionedParcel.class).invoke(null, val, versionedParcel);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (InvocationTargetException e4) {
            if (e4.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e4.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
        }
    }

    private static <T extends VersionedParcelable> Class findParcelClass(T val) throws ClassNotFoundException {
        return findParcelClass((Class<? extends VersionedParcelable>) val.getClass());
    }

    private static Class findParcelClass(Class<? extends VersionedParcelable> cls) throws ClassNotFoundException {
        String pkg = cls.getPackage().getName();
        String c = String.format("%s.%sParcelizer", pkg, cls.getSimpleName());
        return Class.forName(c, false, cls.getClassLoader());
    }

    /* loaded from: classes.dex */
    public static class ParcelException extends RuntimeException {
        public ParcelException(Throwable source) {
            super(source);
        }
    }
}
