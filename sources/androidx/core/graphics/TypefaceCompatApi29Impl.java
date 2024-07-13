package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.io.InputStream;

@RequiresApi(29)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] fonts, int style) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromInputStream(Context context, InputStream is) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    @Nullable
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fonts, int style) {
        FontFamily.Builder familyBuilder;
        ContentResolver resolver = context.getContentResolver();
        try {
            int length = fonts.length;
            int i = 0;
            FontFamily.Builder familyBuilder2 = null;
            while (i < length) {
                try {
                    FontsContractCompat.FontInfo font = fonts[i];
                    try {
                        ParcelFileDescriptor pfd = resolver.openFileDescriptor(font.getUri(), "r", cancellationSignal);
                        if (pfd != null) {
                            try {
                                Font platformFont = new Font.Builder(pfd).setWeight(font.getWeight()).setSlant(font.isItalic() ? 1 : 0).setTtcIndex(font.getTtcIndex()).build();
                                if (familyBuilder2 == null) {
                                    familyBuilder = new FontFamily.Builder(platformFont);
                                } else {
                                    familyBuilder2.addFont(platformFont);
                                    familyBuilder = familyBuilder2;
                                }
                                if (pfd != null) {
                                    try {
                                        pfd.close();
                                    } catch (IOException e) {
                                    }
                                }
                            } catch (Throwable th) {
                                if (pfd != null) {
                                    try {
                                        pfd.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                                break;
                            }
                        } else {
                            if (pfd != null) {
                                pfd.close();
                            }
                            familyBuilder = familyBuilder2;
                        }
                    } catch (IOException e2) {
                        familyBuilder = familyBuilder2;
                    }
                    i++;
                    familyBuilder2 = familyBuilder;
                } catch (Exception e3) {
                    return null;
                }
            }
            if (familyBuilder2 == null) {
                return null;
            }
            FontStyle defaultStyle = new FontStyle((style & 1) != 0 ? 700 : 400, (style & 2) != 0 ? 1 : 0);
            return new Typeface.CustomFallbackBuilder(familyBuilder2.build()).setStyle(defaultStyle).build();
        } catch (Exception e4) {
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    @Nullable
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry familyEntry, Resources resources, int style) {
        FontFamily.Builder familyBuilder;
        try {
            FontResourcesParserCompat.FontFileResourceEntry[] entries = familyEntry.getEntries();
            int length = entries.length;
            int i = 0;
            FontFamily.Builder familyBuilder2 = null;
            while (i < length) {
                try {
                    FontResourcesParserCompat.FontFileResourceEntry entry = entries[i];
                    try {
                        Font platformFont = new Font.Builder(resources, entry.getResourceId()).setWeight(entry.getWeight()).setSlant(entry.isItalic() ? 1 : 0).setTtcIndex(entry.getTtcIndex()).setFontVariationSettings(entry.getVariationSettings()).build();
                        if (familyBuilder2 == null) {
                            familyBuilder = new FontFamily.Builder(platformFont);
                        } else {
                            familyBuilder2.addFont(platformFont);
                            familyBuilder = familyBuilder2;
                        }
                    } catch (IOException e) {
                        familyBuilder = familyBuilder2;
                    }
                    i++;
                    familyBuilder2 = familyBuilder;
                } catch (Exception e2) {
                    return null;
                }
            }
            if (familyBuilder2 == null) {
                return null;
            }
            FontStyle defaultStyle = new FontStyle((style & 1) != 0 ? 700 : 400, (style & 2) != 0 ? 1 : 0);
            return new Typeface.CustomFallbackBuilder(familyBuilder2.build()).setStyle(defaultStyle).build();
        } catch (Exception e3) {
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        try {
            Font font = new Font.Builder(resources, id).build();
            FontFamily family = new FontFamily.Builder(font).build();
            return new Typeface.CustomFallbackBuilder(family).setStyle(font.getStyle()).build();
        } catch (Exception e) {
            return null;
        }
    }
}
