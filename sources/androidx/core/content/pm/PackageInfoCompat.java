package androidx.core.content.pm;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.Size;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class PackageInfoCompat {
    public static long getLongVersionCode(@NonNull PackageInfo info) {
        return Build.VERSION.SDK_INT >= 28 ? info.getLongVersionCode() : info.versionCode;
    }

    @NonNull
    public static List<Signature> getSignatures(@NonNull PackageManager packageManager, @NonNull String packageName) throws PackageManager.NameNotFoundException {
        Signature[] array;
        if (Build.VERSION.SDK_INT >= 28) {
            PackageInfo pkgInfo = packageManager.getPackageInfo(packageName, 134217728);
            SigningInfo signingInfo = pkgInfo.signingInfo;
            if (Api28Impl.hasMultipleSigners(signingInfo)) {
                array = Api28Impl.getApkContentsSigners(signingInfo);
            } else {
                array = Api28Impl.getSigningCertificateHistory(signingInfo);
            }
        } else {
            PackageInfo pkgInfo2 = packageManager.getPackageInfo(packageName, 64);
            array = pkgInfo2.signatures;
        }
        if (array == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(array);
    }

    public static boolean hasSignatures(@NonNull PackageManager packageManager, @NonNull String packageName, @NonNull @Size(min = 1) Map<byte[], Integer> certificatesAndType, boolean matchExact) throws PackageManager.NameNotFoundException {
        if (certificatesAndType.isEmpty()) {
            return false;
        }
        Set<byte[]> expectedCertBytes = certificatesAndType.keySet();
        for (byte[] bytes : expectedCertBytes) {
            if (bytes == null) {
                throw new IllegalArgumentException("Cert byte array cannot be null when verifying " + packageName);
            }
            Integer type = certificatesAndType.get(bytes);
            if (type == null) {
                throw new IllegalArgumentException("Type must be specified for cert when verifying " + packageName);
            }
            switch (type.intValue()) {
                case 0:
                case 1:
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported certificate type " + type + " when verifying " + packageName);
            }
        }
        List<Signature> signers = getSignatures(packageManager, packageName);
        if (!matchExact && Build.VERSION.SDK_INT >= 28) {
            for (byte[] bytes2 : expectedCertBytes) {
                if (!Api28Impl.hasSigningCertificate(packageManager, packageName, bytes2, certificatesAndType.get(bytes2).intValue())) {
                    return false;
                }
            }
            return true;
        } else if (signers.size() == 0 || certificatesAndType.size() > signers.size() || (matchExact && certificatesAndType.size() != signers.size())) {
            return false;
        } else {
            boolean hasSha256 = certificatesAndType.containsValue(1);
            byte[][] sha256Digests = null;
            if (hasSha256) {
                sha256Digests = new byte[signers.size()];
                for (int index = 0; index < signers.size(); index++) {
                    sha256Digests[index] = computeSHA256Digest(signers.get(index).toByteArray());
                }
            }
            Iterator<byte[]> it = expectedCertBytes.iterator();
            if (it.hasNext()) {
                byte[] bytes3 = it.next();
                Integer type2 = certificatesAndType.get(bytes3);
                switch (type2.intValue()) {
                    case 0:
                        Signature expectedSignature = new Signature(bytes3);
                        if (!signers.contains(expectedSignature)) {
                            return false;
                        }
                        break;
                    case 1:
                        if (!byteArrayContains(sha256Digests, bytes3)) {
                            return false;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported certificate type " + type2);
                }
                return true;
            }
            return false;
        }
    }

    private static boolean byteArrayContains(@NonNull byte[][] array, @NonNull byte[] expected) {
        for (byte[] item : array) {
            if (Arrays.equals(expected, item)) {
                return true;
            }
        }
        return false;
    }

    private static byte[] computeSHA256Digest(byte[] bytes) {
        try {
            return MessageDigest.getInstance("SHA256").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Device doesn't support SHA256 cert checking", e);
        }
    }

    private PackageInfoCompat() {
    }

    @RequiresApi(28)
    /* loaded from: classes.dex */
    public static class Api28Impl {
        private Api28Impl() {
        }

        static boolean hasSigningCertificate(@NonNull PackageManager packageManager, @NonNull String packageName, @NonNull byte[] bytes, int type) {
            return packageManager.hasSigningCertificate(packageName, bytes, type);
        }

        static boolean hasMultipleSigners(@NonNull SigningInfo signingInfo) {
            return signingInfo.hasMultipleSigners();
        }

        @Nullable
        static Signature[] getApkContentsSigners(@NonNull SigningInfo signingInfo) {
            return signingInfo.getApkContentsSigners();
        }

        @Nullable
        static Signature[] getSigningCertificateHistory(@NonNull SigningInfo signingInfo) {
            return signingInfo.getSigningCertificateHistory();
        }
    }
}
