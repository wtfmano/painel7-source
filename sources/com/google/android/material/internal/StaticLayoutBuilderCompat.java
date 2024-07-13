package com.google.android.material.internal;

import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import java.lang.reflect.Constructor;

/* JADX INFO: Access modifiers changed from: package-private */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class StaticLayoutBuilderCompat {
    static final int DEFAULT_HYPHENATION_FREQUENCY;
    static final float DEFAULT_LINE_SPACING_ADD = 0.0f;
    static final float DEFAULT_LINE_SPACING_MULTIPLIER = 1.0f;
    private static final String TEXT_DIRS_CLASS = "android.text.TextDirectionHeuristics";
    private static final String TEXT_DIR_CLASS = "android.text.TextDirectionHeuristic";
    private static final String TEXT_DIR_CLASS_LTR = "LTR";
    private static final String TEXT_DIR_CLASS_RTL = "RTL";
    @Nullable
    private static Constructor<StaticLayout> constructor;
    private static boolean initialized;
    @Nullable
    private static Object textDirection;
    private int end;
    private boolean isRtl;
    private final TextPaint paint;
    private CharSequence source;
    private final int width;
    private int start = 0;
    private Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
    private int maxLines = Integer.MAX_VALUE;
    private float lineSpacingAdd = 0.0f;
    private float lineSpacingMultiplier = 1.0f;
    private int hyphenationFrequency = DEFAULT_HYPHENATION_FREQUENCY;
    private boolean includePad = true;
    @Nullable
    private TextUtils.TruncateAt ellipsize = null;

    static {
        DEFAULT_HYPHENATION_FREQUENCY = Build.VERSION.SDK_INT >= 23 ? 1 : 0;
    }

    private StaticLayoutBuilderCompat(CharSequence source, TextPaint paint, int width) {
        this.source = source;
        this.paint = paint;
        this.width = width;
        this.end = source.length();
    }

    @NonNull
    public static StaticLayoutBuilderCompat obtain(@NonNull CharSequence source, @NonNull TextPaint paint, @IntRange(from = 0) int width) {
        return new StaticLayoutBuilderCompat(source, paint, width);
    }

    @NonNull
    public StaticLayoutBuilderCompat setAlignment(@NonNull Layout.Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setIncludePad(boolean includePad) {
        this.includePad = includePad;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setStart(@IntRange(from = 0) int start) {
        this.start = start;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setEnd(@IntRange(from = 0) int end) {
        this.end = end;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setMaxLines(@IntRange(from = 0) int maxLines) {
        this.maxLines = maxLines;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setLineSpacing(float spacingAdd, float lineSpacingMultiplier) {
        this.lineSpacingAdd = spacingAdd;
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setHyphenationFrequency(int hyphenationFrequency) {
        this.hyphenationFrequency = hyphenationFrequency;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setEllipsize(@Nullable TextUtils.TruncateAt ellipsize) {
        this.ellipsize = ellipsize;
        return this;
    }

    public StaticLayout build() throws StaticLayoutBuilderCompatException {
        if (this.source == null) {
            this.source = "";
        }
        int availableWidth = Math.max(0, this.width);
        CharSequence textToDraw = this.source;
        if (this.maxLines == 1) {
            textToDraw = TextUtils.ellipsize(this.source, this.paint, availableWidth, this.ellipsize);
        }
        this.end = Math.min(textToDraw.length(), this.end);
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.isRtl && this.maxLines == 1) {
                this.alignment = Layout.Alignment.ALIGN_OPPOSITE;
            }
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(textToDraw, this.start, this.end, this.paint, availableWidth);
            builder.setAlignment(this.alignment);
            builder.setIncludePad(this.includePad);
            TextDirectionHeuristic textDirectionHeuristic = this.isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
            builder.setTextDirection(textDirectionHeuristic);
            if (this.ellipsize != null) {
                builder.setEllipsize(this.ellipsize);
            }
            builder.setMaxLines(this.maxLines);
            if (this.lineSpacingAdd != 0.0f || this.lineSpacingMultiplier != 1.0f) {
                builder.setLineSpacing(this.lineSpacingAdd, this.lineSpacingMultiplier);
            }
            if (this.maxLines > 1) {
                builder.setHyphenationFrequency(this.hyphenationFrequency);
            }
            return builder.build();
        }
        createConstructorWithReflection();
        try {
            return (StaticLayout) ((Constructor) Preconditions.checkNotNull(constructor)).newInstance(textToDraw, Integer.valueOf(this.start), Integer.valueOf(this.end), this.paint, Integer.valueOf(availableWidth), this.alignment, Preconditions.checkNotNull(textDirection), Float.valueOf(1.0f), Float.valueOf(0.0f), Boolean.valueOf(this.includePad), null, Integer.valueOf(availableWidth), Integer.valueOf(this.maxLines));
        } catch (Exception cause) {
            throw new StaticLayoutBuilderCompatException(cause);
        }
    }

    private void createConstructorWithReflection() throws StaticLayoutBuilderCompatException {
        Class<?> textDirClass;
        boolean useRtl = true;
        if (!initialized) {
            try {
                if (!this.isRtl || Build.VERSION.SDK_INT < 23) {
                    useRtl = false;
                }
                if (Build.VERSION.SDK_INT >= 18) {
                    textDirClass = TextDirectionHeuristic.class;
                    textDirection = useRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
                } else {
                    ClassLoader loader = StaticLayoutBuilderCompat.class.getClassLoader();
                    String textDirClassName = this.isRtl ? TEXT_DIR_CLASS_RTL : TEXT_DIR_CLASS_LTR;
                    textDirClass = loader.loadClass(TEXT_DIR_CLASS);
                    Class<?> textDirsClass = loader.loadClass(TEXT_DIRS_CLASS);
                    textDirection = textDirsClass.getField(textDirClassName).get(textDirsClass);
                }
                Class<?>[] signature = {CharSequence.class, Integer.TYPE, Integer.TYPE, TextPaint.class, Integer.TYPE, Layout.Alignment.class, textDirClass, Float.TYPE, Float.TYPE, Boolean.TYPE, TextUtils.TruncateAt.class, Integer.TYPE, Integer.TYPE};
                constructor = StaticLayout.class.getDeclaredConstructor(signature);
                constructor.setAccessible(true);
                initialized = true;
            } catch (Exception cause) {
                throw new StaticLayoutBuilderCompatException(cause);
            }
        }
    }

    public StaticLayoutBuilderCompat setIsRtl(boolean isRtl) {
        this.isRtl = isRtl;
        return this;
    }

    /* loaded from: classes.dex */
    public static class StaticLayoutBuilderCompatException extends Exception {
        StaticLayoutBuilderCompatException(Throwable cause) {
            super("Error thrown initializing StaticLayout " + cause.getMessage(), cause);
        }
    }
}
