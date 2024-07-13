package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
@KeepForSdk
/* loaded from: classes.dex */
public final class DynamiteModule {
    @Nullable
    @GuardedBy("DynamiteModule.class")
    private static Boolean zzb;
    @Nullable
    @GuardedBy("DynamiteModule.class")
    private static String zzc;
    @Nullable
    @GuardedBy("DynamiteModule.class")
    private static zzo zzi;
    @Nullable
    @GuardedBy("DynamiteModule.class")
    private static zzp zzj;
    private final Context zzh;
    @GuardedBy("DynamiteModule.class")
    private static int zzd = -1;
    private static final ThreadLocal<zzk> zze = new ThreadLocal<>();
    private static final ThreadLocal<Long> zzf = new zzb();
    private static final zzm zzg = new zzc();
    @RecentlyNonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_REMOTE = new zzd();
    @RecentlyNonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_LOCAL = new zze();
    @RecentlyNonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_REMOTE_VERSION_NO_FORCE_STAGING = new zzf();
    @RecentlyNonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new zzg();
    @RecentlyNonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new zzh();
    @RecentlyNonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new zzi();
    @RecentlyNonNull
    public static final VersionPolicy zza = new zzj();

    /* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
    @DynamiteApi
    /* loaded from: classes.dex */
    public static class DynamiteLoaderClassLoader {
        @RecentlyNullable
        @GuardedBy("DynamiteLoaderClassLoader.class")
        public static ClassLoader sClassLoader;
    }

    /* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
    @KeepForSdk
    /* loaded from: classes.dex */
    public static class LoadingException extends Exception {
        /* synthetic */ LoadingException(String str, zzb zzbVar) {
            super(str);
        }

        /* synthetic */ LoadingException(String str, Throwable th, zzb zzbVar) {
            super(str, th);
        }
    }

    /* compiled from: com.google.android.gms:play-services-basement@@17.6.0 */
    /* loaded from: classes.dex */
    public interface VersionPolicy {
        zzn zza(Context context, String str, zzm zzmVar) throws LoadingException;
    }

    private DynamiteModule(Context context) {
        Preconditions.checkNotNull(context);
        this.zzh = context;
    }

    @KeepForSdk
    public static int getLocalVersion(@RecentlyNonNull Context context, @RecentlyNonNull String str) {
        int i;
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 61);
            sb.append("com.google.android.gms.dynamite.descriptors.");
            sb.append(str);
            sb.append(".");
            sb.append("ModuleDescriptor");
            Class<?> loadClass = classLoader.loadClass(sb.toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (!Objects.equal(declaredField.get(null), str)) {
                String valueOf = String.valueOf(declaredField.get(null));
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 51 + String.valueOf(str).length());
                sb2.append("Module descriptor id '");
                sb2.append(valueOf);
                sb2.append("' didn't match expected id '");
                sb2.append(str);
                sb2.append("'");
                Log.e("DynamiteModule", sb2.toString());
                i = 0;
            } else {
                i = declaredField2.getInt(null);
            }
            return i;
        } catch (ClassNotFoundException e) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 45);
            sb3.append("Local module descriptor class for ");
            sb3.append(str);
            sb3.append(" not found.");
            Log.w("DynamiteModule", sb3.toString());
            return 0;
        } catch (Exception e2) {
            String valueOf2 = String.valueOf(e2.getMessage());
            Log.e("DynamiteModule", valueOf2.length() != 0 ? "Failed to load module descriptor class: ".concat(valueOf2) : new String("Failed to load module descriptor class: "));
            return 0;
        }
    }

    @KeepForSdk
    public static int getRemoteVersion(@RecentlyNonNull Context context, @RecentlyNonNull String str) {
        return zza(context, str, false);
    }

    @RecentlyNonNull
    @KeepForSdk
    public static DynamiteModule load(@RecentlyNonNull Context context, @RecentlyNonNull VersionPolicy versionPolicy, @RecentlyNonNull String str) throws LoadingException {
        Boolean bool;
        IObjectWrapper zze2;
        DynamiteModule dynamiteModule;
        zzp zzpVar;
        Boolean valueOf;
        IObjectWrapper zze3;
        zzk zzkVar = zze.get();
        zzk zzkVar2 = new zzk(null);
        zze.set(zzkVar2);
        long longValue = zzf.get().longValue();
        try {
            zzf.set(Long.valueOf(SystemClock.elapsedRealtime()));
            zzn zza2 = versionPolicy.zza(context, str, zzg);
            int i = zza2.zza;
            int i2 = zza2.zzb;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str).length());
            sb.append("Considering local module ");
            sb.append(str);
            sb.append(":");
            sb.append(i);
            sb.append(" and remote module ");
            sb.append(str);
            sb.append(":");
            sb.append(i2);
            Log.i("DynamiteModule", sb.toString());
            int i3 = zza2.zzc;
            if (i3 == 0 || ((i3 == -1 && zza2.zza == 0) || (i3 == 1 && zza2.zzb == 0))) {
                int i4 = zza2.zza;
                int i5 = zza2.zzb;
                StringBuilder sb2 = new StringBuilder(91);
                sb2.append("No acceptable module found. Local version is ");
                sb2.append(i4);
                sb2.append(" and remote version is ");
                sb2.append(i5);
                sb2.append(".");
                throw new LoadingException(sb2.toString(), null);
            } else if (i3 == -1) {
                DynamiteModule zzd2 = zzd(context, str);
                if (longValue == 0) {
                    zzf.remove();
                } else {
                    zzf.set(Long.valueOf(longValue));
                }
                Cursor cursor = zzkVar2.zza;
                if (cursor != null) {
                    cursor.close();
                }
                zze.set(zzkVar);
                return zzd2;
            } else if (i3 != 1) {
                StringBuilder sb3 = new StringBuilder(47);
                sb3.append("VersionPolicy returned invalid code:");
                sb3.append(0);
                throw new LoadingException(sb3.toString(), null);
            } else {
                try {
                    int i6 = zza2.zzb;
                    try {
                        synchronized (DynamiteModule.class) {
                            bool = zzb;
                        }
                        if (bool == null) {
                            throw new LoadingException("Failed to determine which loading route to use.", null);
                        }
                        if (bool.booleanValue()) {
                            StringBuilder sb4 = new StringBuilder(String.valueOf(str).length() + 51);
                            sb4.append("Selected remote version of ");
                            sb4.append(str);
                            sb4.append(", version >= ");
                            sb4.append(i6);
                            Log.i("DynamiteModule", sb4.toString());
                            synchronized (DynamiteModule.class) {
                                zzpVar = zzj;
                            }
                            if (zzpVar == null) {
                                throw new LoadingException("DynamiteLoaderV2 was not cached.", null);
                            }
                            zzk zzkVar3 = zze.get();
                            if (zzkVar3 == null || zzkVar3.zza == null) {
                                throw new LoadingException("No result cursor", null);
                            }
                            Context applicationContext = context.getApplicationContext();
                            Cursor cursor2 = zzkVar3.zza;
                            ObjectWrapper.wrap(null);
                            synchronized (DynamiteModule.class) {
                                valueOf = Boolean.valueOf(zzd >= 2);
                            }
                            if (valueOf.booleanValue()) {
                                Log.v("DynamiteModule", "Dynamite loader version >= 2, using loadModule2NoCrashUtils");
                                zze3 = zzpVar.zzf(ObjectWrapper.wrap(applicationContext), str, i6, ObjectWrapper.wrap(cursor2));
                            } else {
                                Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to loadModule2");
                                zze3 = zzpVar.zze(ObjectWrapper.wrap(applicationContext), str, i6, ObjectWrapper.wrap(cursor2));
                            }
                            Context context2 = (Context) ObjectWrapper.unwrap(zze3);
                            if (context2 == null) {
                                throw new LoadingException("Failed to get module context", null);
                            }
                            dynamiteModule = new DynamiteModule(context2);
                        } else {
                            StringBuilder sb5 = new StringBuilder(String.valueOf(str).length() + 51);
                            sb5.append("Selected remote version of ");
                            sb5.append(str);
                            sb5.append(", version >= ");
                            sb5.append(i6);
                            Log.i("DynamiteModule", sb5.toString());
                            zzo zzf2 = zzf(context);
                            if (zzf2 == null) {
                                throw new LoadingException("Failed to create IDynamiteLoader.", null);
                            }
                            int zzi2 = zzf2.zzi();
                            if (zzi2 >= 3) {
                                zzk zzkVar4 = zze.get();
                                if (zzkVar4 == null) {
                                    throw new LoadingException("No cached result cursor holder", null);
                                }
                                zze2 = zzf2.zzk(ObjectWrapper.wrap(context), str, i6, ObjectWrapper.wrap(zzkVar4.zza));
                            } else if (zzi2 == 2) {
                                Log.w("DynamiteModule", "IDynamite loader version = 2");
                                zze2 = zzf2.zzg(ObjectWrapper.wrap(context), str, i6);
                            } else {
                                Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to createModuleContext");
                                zze2 = zzf2.zze(ObjectWrapper.wrap(context), str, i6);
                            }
                            if (ObjectWrapper.unwrap(zze2) == null) {
                                throw new LoadingException("Failed to load remote module.", null);
                            }
                            dynamiteModule = new DynamiteModule((Context) ObjectWrapper.unwrap(zze2));
                        }
                        if (longValue == 0) {
                            zzf.remove();
                        } else {
                            zzf.set(Long.valueOf(longValue));
                        }
                        Cursor cursor3 = zzkVar2.zza;
                        if (cursor3 != null) {
                            cursor3.close();
                        }
                        zze.set(zzkVar);
                        return dynamiteModule;
                    } catch (RemoteException e) {
                        throw new LoadingException("Failed to load remote module.", e, null);
                    } catch (LoadingException e2) {
                        throw e2;
                    } catch (Throwable th) {
                        CrashUtils.addDynamiteErrorToDropBox(context, th);
                        throw new LoadingException("Failed to load remote module.", th, null);
                    }
                } catch (LoadingException e3) {
                    String valueOf2 = String.valueOf(e3.getMessage());
                    Log.w("DynamiteModule", valueOf2.length() != 0 ? "Failed to load remote module: ".concat(valueOf2) : new String("Failed to load remote module: "));
                    int i7 = zza2.zza;
                    if (i7 == 0 || versionPolicy.zza(context, str, new zzl(i7, 0)).zzc != -1) {
                        throw new LoadingException("Remote load failed. No local fallback found.", e3, null);
                    }
                    DynamiteModule zzd3 = zzd(context, str);
                    if (longValue == 0) {
                        zzf.remove();
                    } else {
                        zzf.set(Long.valueOf(longValue));
                    }
                    Cursor cursor4 = zzkVar2.zza;
                    if (cursor4 != null) {
                        cursor4.close();
                    }
                    zze.set(zzkVar);
                    return zzd3;
                }
            }
        } catch (Throwable th2) {
            if (longValue == 0) {
                zzf.remove();
            } else {
                zzf.set(Long.valueOf(longValue));
            }
            Cursor cursor5 = zzkVar2.zza;
            if (cursor5 != null) {
                cursor5.close();
            }
            zze.set(zzkVar);
            throw th2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:326:0x0264 A[Catch: all -> 0x01a9, TryCatch #3 {all -> 0x01a9, blocks: (B:188:0x000a, B:208:0x0053, B:212:0x005e, B:307:0x01fe, B:309:0x0217, B:310:0x021e, B:339:0x0284, B:216:0x0067, B:247:0x00d7, B:321:0x0256, B:326:0x0264, B:328:0x0269, B:234:0x00b5, B:189:0x000b, B:193:0x0012, B:194:0x0032, B:204:0x004e, B:282:0x018f, B:273:0x015d, B:302:0x01f9, B:296:0x01bb, B:206:0x0051), top: B:350:0x000a, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int zza(@androidx.annotation.RecentlyNonNull android.content.Context r16, @androidx.annotation.RecentlyNonNull java.lang.String r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 674
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, java.lang.String, boolean):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:95:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int zzb(android.content.Context r14, java.lang.String r15, boolean r16) throws com.google.android.gms.dynamite.DynamiteModule.LoadingException {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzb(android.content.Context, java.lang.String, boolean):int");
    }

    private static boolean zzc(Cursor cursor) {
        zzk zzkVar = zze.get();
        if (zzkVar == null || zzkVar.zza != null) {
            return false;
        }
        zzkVar.zza = cursor;
        return true;
    }

    private static DynamiteModule zzd(Context context, String str) {
        String valueOf = String.valueOf(str);
        Log.i("DynamiteModule", valueOf.length() != 0 ? "Selected local version of ".concat(valueOf) : new String("Selected local version of "));
        return new DynamiteModule(context.getApplicationContext());
    }

    @GuardedBy("DynamiteModule.class")
    private static void zze(ClassLoader classLoader) throws LoadingException {
        zzp zzpVar;
        try {
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzpVar = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                if (queryLocalInterface instanceof zzp) {
                    zzpVar = (zzp) queryLocalInterface;
                } else {
                    zzpVar = new zzp(iBinder);
                }
            }
            zzj = zzpVar;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new LoadingException("Failed to instantiate dynamite loader", e, null);
        }
    }

    @Nullable
    private static zzo zzf(Context context) {
        zzo zzoVar;
        synchronized (DynamiteModule.class) {
            if (zzi != null) {
                return zzi;
            }
            try {
                IBinder iBinder = (IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                if (iBinder == null) {
                    zzoVar = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    if (queryLocalInterface instanceof zzo) {
                        zzoVar = (zzo) queryLocalInterface;
                    } else {
                        zzoVar = new zzo(iBinder);
                    }
                }
                if (zzoVar != null) {
                    zzi = zzoVar;
                    return zzoVar;
                }
            } catch (Exception e) {
                String valueOf = String.valueOf(e.getMessage());
                Log.e("DynamiteModule", valueOf.length() != 0 ? "Failed to load IDynamiteLoader from GmsCore: ".concat(valueOf) : new String("Failed to load IDynamiteLoader from GmsCore: "));
            }
            return null;
        }
    }

    @RecentlyNonNull
    @KeepForSdk
    public Context getModuleContext() {
        return this.zzh;
    }

    @RecentlyNonNull
    @KeepForSdk
    public IBinder instantiate(@RecentlyNonNull String str) throws LoadingException {
        try {
            return (IBinder) this.zzh.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String valueOf = String.valueOf(str);
            throw new LoadingException(valueOf.length() != 0 ? "Failed to instantiate module class: ".concat(valueOf) : new String("Failed to instantiate module class: "), e, null);
        }
    }
}
