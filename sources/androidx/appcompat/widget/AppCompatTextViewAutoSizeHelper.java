package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class AppCompatTextViewAutoSizeHelper {
    private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
    private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
    private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
    private static final String TAG = "ACTVAutoSizeHelper";
    static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0f;
    private static final int VERY_WIDE = 1048576;
    private final Context mContext;
    private final Impl mImpl;
    private TextPaint mTempTextPaint;
    @NonNull
    private final TextView mTextView;
    private static final RectF TEMP_RECTF = new RectF();
    private static ConcurrentHashMap<String, Method> sTextViewMethodByNameCache = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Field> sTextViewFieldByNameCache = new ConcurrentHashMap<>();
    private int mAutoSizeTextType = 0;
    private boolean mNeedsAutoSizeText = false;
    private float mAutoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
    private float mAutoSizeMinTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
    private float mAutoSizeMaxTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
    private int[] mAutoSizeTextSizesInPx = new int[0];
    private boolean mHasPresetAutoSizeValues = false;

    /* loaded from: classes.dex */
    public static class Impl {
        Impl() {
        }

        boolean isHorizontallyScrollable(TextView textView) {
            return ((Boolean) AppCompatTextViewAutoSizeHelper.invokeAndReturnWithDefault(textView, "getHorizontallyScrolling", false)).booleanValue();
        }

        void computeAndSetTextDirection(StaticLayout.Builder layoutBuilder, TextView textView) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(23)
    /* loaded from: classes.dex */
    public static class Impl23 extends Impl {
        Impl23() {
        }

        @Override // androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper.Impl
        void computeAndSetTextDirection(StaticLayout.Builder layoutBuilder, TextView textView) {
            TextDirectionHeuristic textDirectionHeuristic = (TextDirectionHeuristic) AppCompatTextViewAutoSizeHelper.invokeAndReturnWithDefault(textView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR);
            layoutBuilder.setTextDirection(textDirectionHeuristic);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(29)
    /* loaded from: classes.dex */
    public static class Impl29 extends Impl23 {
        Impl29() {
        }

        @Override // androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper.Impl
        boolean isHorizontallyScrollable(TextView textView) {
            return textView.isHorizontallyScrollable();
        }

        @Override // androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper.Impl23, androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper.Impl
        void computeAndSetTextDirection(StaticLayout.Builder layoutBuilder, TextView textView) {
            layoutBuilder.setTextDirection(textView.getTextDirectionHeuristic());
        }
    }

    public AppCompatTextViewAutoSizeHelper(@NonNull TextView textView) {
        this.mTextView = textView;
        this.mContext = this.mTextView.getContext();
        if (Build.VERSION.SDK_INT >= 29) {
            this.mImpl = new Impl29();
        } else if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = new Impl23();
        } else {
            this.mImpl = new Impl();
        }
    }

    public void loadFromAttributes(@Nullable AttributeSet attrs, int defStyleAttr) {
        int autoSizeStepSizeArrayResId;
        float autoSizeMinTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
        float autoSizeMaxTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
        float autoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
        TypedArray a = this.mContext.obtainStyledAttributes(attrs, R.styleable.AppCompatTextView, defStyleAttr, 0);
        ViewCompat.saveAttributeDataForStyleable(this.mTextView, this.mTextView.getContext(), R.styleable.AppCompatTextView, attrs, a, defStyleAttr, 0);
        if (a.hasValue(R.styleable.AppCompatTextView_autoSizeTextType)) {
            this.mAutoSizeTextType = a.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0);
        }
        if (a.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity)) {
            autoSizeStepGranularityInPx = a.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE);
        }
        if (a.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize)) {
            autoSizeMinTextSizeInPx = a.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE);
        }
        if (a.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize)) {
            autoSizeMaxTextSizeInPx = a.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE);
        }
        if (a.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes) && (autoSizeStepSizeArrayResId = a.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0)) > 0) {
            TypedArray autoSizePreDefTextSizes = a.getResources().obtainTypedArray(autoSizeStepSizeArrayResId);
            setupAutoSizeUniformPresetSizes(autoSizePreDefTextSizes);
            autoSizePreDefTextSizes.recycle();
        }
        a.recycle();
        if (supportsAutoSizeText()) {
            if (this.mAutoSizeTextType == 1) {
                if (!this.mHasPresetAutoSizeValues) {
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
                    if (autoSizeMinTextSizeInPx == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) {
                        autoSizeMinTextSizeInPx = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                    }
                    if (autoSizeMaxTextSizeInPx == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) {
                        autoSizeMaxTextSizeInPx = TypedValue.applyDimension(2, 112.0f, displayMetrics);
                    }
                    if (autoSizeStepGranularityInPx == UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE) {
                        autoSizeStepGranularityInPx = 1.0f;
                    }
                    validateAndSetAutoSizeTextTypeUniformConfiguration(autoSizeMinTextSizeInPx, autoSizeMaxTextSizeInPx, autoSizeStepGranularityInPx);
                }
                setupAutoSizeText();
                return;
            }
            return;
        }
        this.mAutoSizeTextType = 0;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setAutoSizeTextTypeWithDefaults(int autoSizeTextType) {
        if (supportsAutoSizeText()) {
            switch (autoSizeTextType) {
                case 0:
                    clearAutoSizeConfiguration();
                    return;
                case 1:
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
                    float autoSizeMinTextSizeInPx = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                    float autoSizeMaxTextSizeInPx = TypedValue.applyDimension(2, 112.0f, displayMetrics);
                    validateAndSetAutoSizeTextTypeUniformConfiguration(autoSizeMinTextSizeInPx, autoSizeMaxTextSizeInPx, 1.0f);
                    if (setupAutoSizeText()) {
                        autoSizeText();
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException("Unknown auto-size text type: " + autoSizeTextType);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setAutoSizeTextTypeUniformWithConfiguration(int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
        if (supportsAutoSizeText()) {
            DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
            float autoSizeMinTextSizeInPx = TypedValue.applyDimension(unit, autoSizeMinTextSize, displayMetrics);
            float autoSizeMaxTextSizeInPx = TypedValue.applyDimension(unit, autoSizeMaxTextSize, displayMetrics);
            float autoSizeStepGranularityInPx = TypedValue.applyDimension(unit, autoSizeStepGranularity, displayMetrics);
            validateAndSetAutoSizeTextTypeUniformConfiguration(autoSizeMinTextSizeInPx, autoSizeMaxTextSizeInPx, autoSizeStepGranularityInPx);
            if (setupAutoSizeText()) {
                autoSizeText();
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
        if (supportsAutoSizeText()) {
            int presetSizesLength = presetSizes.length;
            if (presetSizesLength > 0) {
                int[] presetSizesInPx = new int[presetSizesLength];
                if (unit == 0) {
                    presetSizesInPx = Arrays.copyOf(presetSizes, presetSizesLength);
                } else {
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
                    for (int i = 0; i < presetSizesLength; i++) {
                        presetSizesInPx[i] = Math.round(TypedValue.applyDimension(unit, presetSizes[i], displayMetrics));
                    }
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(presetSizesInPx);
                if (!setupAutoSizeUniformPresetSizesConfiguration()) {
                    throw new IllegalArgumentException("None of the preset sizes is valid: " + Arrays.toString(presetSizes));
                }
            } else {
                this.mHasPresetAutoSizeValues = false;
            }
            if (setupAutoSizeText()) {
                autoSizeText();
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getAutoSizeTextType() {
        return this.mAutoSizeTextType;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getAutoSizeStepGranularity() {
        return Math.round(this.mAutoSizeStepGranularityInPx);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getAutoSizeMinTextSize() {
        return Math.round(this.mAutoSizeMinTextSizeInPx);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getAutoSizeMaxTextSize() {
        return Math.round(this.mAutoSizeMaxTextSizeInPx);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextSizesInPx;
    }

    private void setupAutoSizeUniformPresetSizes(TypedArray textSizes) {
        int textSizesLength = textSizes.length();
        int[] parsedSizes = new int[textSizesLength];
        if (textSizesLength > 0) {
            for (int i = 0; i < textSizesLength; i++) {
                parsedSizes[i] = textSizes.getDimensionPixelSize(i, -1);
            }
            this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(parsedSizes);
            setupAutoSizeUniformPresetSizesConfiguration();
        }
    }

    private boolean setupAutoSizeUniformPresetSizesConfiguration() {
        int sizesLength = this.mAutoSizeTextSizesInPx.length;
        this.mHasPresetAutoSizeValues = sizesLength > 0;
        if (this.mHasPresetAutoSizeValues) {
            this.mAutoSizeTextType = 1;
            this.mAutoSizeMinTextSizeInPx = this.mAutoSizeTextSizesInPx[0];
            this.mAutoSizeMaxTextSizeInPx = this.mAutoSizeTextSizesInPx[sizesLength - 1];
            this.mAutoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
        }
        return this.mHasPresetAutoSizeValues;
    }

    private int[] cleanupAutoSizePresetSizes(int[] presetValues) {
        int presetValuesLength = presetValues.length;
        if (presetValuesLength != 0) {
            Arrays.sort(presetValues);
            List<Integer> uniqueValidSizes = new ArrayList<>();
            for (int currentPresetValue : presetValues) {
                if (currentPresetValue > 0 && Collections.binarySearch(uniqueValidSizes, Integer.valueOf(currentPresetValue)) < 0) {
                    uniqueValidSizes.add(Integer.valueOf(currentPresetValue));
                }
            }
            if (presetValuesLength != uniqueValidSizes.size()) {
                int uniqueValidSizesLength = uniqueValidSizes.size();
                int[] cleanedUpSizes = new int[uniqueValidSizesLength];
                for (int i = 0; i < uniqueValidSizesLength; i++) {
                    cleanedUpSizes[i] = uniqueValidSizes.get(i).intValue();
                }
                return cleanedUpSizes;
            }
            return presetValues;
        }
        return presetValues;
    }

    private void validateAndSetAutoSizeTextTypeUniformConfiguration(float autoSizeMinTextSizeInPx, float autoSizeMaxTextSizeInPx, float autoSizeStepGranularityInPx) throws IllegalArgumentException {
        if (autoSizeMinTextSizeInPx <= 0.0f) {
            throw new IllegalArgumentException("Minimum auto-size text size (" + autoSizeMinTextSizeInPx + "px) is less or equal to (0px)");
        }
        if (autoSizeMaxTextSizeInPx <= autoSizeMinTextSizeInPx) {
            throw new IllegalArgumentException("Maximum auto-size text size (" + autoSizeMaxTextSizeInPx + "px) is less or equal to minimum auto-size text size (" + autoSizeMinTextSizeInPx + "px)");
        }
        if (autoSizeStepGranularityInPx <= 0.0f) {
            throw new IllegalArgumentException("The auto-size step granularity (" + autoSizeStepGranularityInPx + "px) is less or equal to (0px)");
        }
        this.mAutoSizeTextType = 1;
        this.mAutoSizeMinTextSizeInPx = autoSizeMinTextSizeInPx;
        this.mAutoSizeMaxTextSizeInPx = autoSizeMaxTextSizeInPx;
        this.mAutoSizeStepGranularityInPx = autoSizeStepGranularityInPx;
        this.mHasPresetAutoSizeValues = false;
    }

    private boolean setupAutoSizeText() {
        if (supportsAutoSizeText() && this.mAutoSizeTextType == 1) {
            if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
                int autoSizeValuesLength = ((int) Math.floor((this.mAutoSizeMaxTextSizeInPx - this.mAutoSizeMinTextSizeInPx) / this.mAutoSizeStepGranularityInPx)) + 1;
                int[] autoSizeTextSizesInPx = new int[autoSizeValuesLength];
                for (int i = 0; i < autoSizeValuesLength; i++) {
                    autoSizeTextSizesInPx[i] = Math.round(this.mAutoSizeMinTextSizeInPx + (i * this.mAutoSizeStepGranularityInPx));
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(autoSizeTextSizesInPx);
            }
            this.mNeedsAutoSizeText = true;
        } else {
            this.mNeedsAutoSizeText = false;
        }
        return this.mNeedsAutoSizeText;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void autoSizeText() {
        int availableWidth;
        if (isAutoSizeEnabled()) {
            if (this.mNeedsAutoSizeText) {
                if (this.mTextView.getMeasuredHeight() > 0 && this.mTextView.getMeasuredWidth() > 0) {
                    boolean horizontallyScrolling = this.mImpl.isHorizontallyScrollable(this.mTextView);
                    if (horizontallyScrolling) {
                        availableWidth = 1048576;
                    } else {
                        availableWidth = (this.mTextView.getMeasuredWidth() - this.mTextView.getTotalPaddingLeft()) - this.mTextView.getTotalPaddingRight();
                    }
                    int availableHeight = (this.mTextView.getHeight() - this.mTextView.getCompoundPaddingBottom()) - this.mTextView.getCompoundPaddingTop();
                    if (availableWidth > 0 && availableHeight > 0) {
                        synchronized (TEMP_RECTF) {
                            TEMP_RECTF.setEmpty();
                            TEMP_RECTF.right = availableWidth;
                            TEMP_RECTF.bottom = availableHeight;
                            float optimalTextSize = findLargestTextSizeWhichFits(TEMP_RECTF);
                            if (optimalTextSize != this.mTextView.getTextSize()) {
                                setTextSizeInternal(0, optimalTextSize);
                            }
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.mNeedsAutoSizeText = true;
        }
    }

    private void clearAutoSizeConfiguration() {
        this.mAutoSizeTextType = 0;
        this.mAutoSizeMinTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
        this.mAutoSizeMaxTextSizeInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
        this.mAutoSizeStepGranularityInPx = UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE;
        this.mAutoSizeTextSizesInPx = new int[0];
        this.mNeedsAutoSizeText = false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setTextSizeInternal(int unit, float size) {
        Resources res;
        if (this.mContext == null) {
            res = Resources.getSystem();
        } else {
            res = this.mContext.getResources();
        }
        setRawTextSize(TypedValue.applyDimension(unit, size, res.getDisplayMetrics()));
    }

    private void setRawTextSize(float size) {
        if (size != this.mTextView.getPaint().getTextSize()) {
            this.mTextView.getPaint().setTextSize(size);
            boolean isInLayout = false;
            if (Build.VERSION.SDK_INT >= 18) {
                isInLayout = this.mTextView.isInLayout();
            }
            if (this.mTextView.getLayout() != null) {
                this.mNeedsAutoSizeText = false;
                try {
                    Method method = getTextViewMethod("nullLayouts");
                    if (method != null) {
                        method.invoke(this.mTextView, new Object[0]);
                    }
                } catch (Exception ex) {
                    Log.w(TAG, "Failed to invoke TextView#nullLayouts() method", ex);
                }
                if (!isInLayout) {
                    this.mTextView.requestLayout();
                } else {
                    this.mTextView.forceLayout();
                }
                this.mTextView.invalidate();
            }
        }
    }

    private int findLargestTextSizeWhichFits(RectF availableSpace) {
        int sizesCount = this.mAutoSizeTextSizesInPx.length;
        if (sizesCount == 0) {
            throw new IllegalStateException("No available text sizes to choose from.");
        }
        int bestSizeIndex = 0;
        int lowIndex = 0 + 1;
        int highIndex = sizesCount - 1;
        while (lowIndex <= highIndex) {
            int sizeToTryIndex = (lowIndex + highIndex) / 2;
            if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[sizeToTryIndex], availableSpace)) {
                bestSizeIndex = lowIndex;
                lowIndex = sizeToTryIndex + 1;
            } else {
                highIndex = sizeToTryIndex - 1;
                bestSizeIndex = highIndex;
            }
        }
        return this.mAutoSizeTextSizesInPx[bestSizeIndex];
    }

    @VisibleForTesting
    void initTempTextPaint(int suggestedSizeInPx) {
        if (this.mTempTextPaint == null) {
            this.mTempTextPaint = new TextPaint();
        } else {
            this.mTempTextPaint.reset();
        }
        this.mTempTextPaint.set(this.mTextView.getPaint());
        this.mTempTextPaint.setTextSize(suggestedSizeInPx);
    }

    @VisibleForTesting
    StaticLayout createLayout(CharSequence text, Layout.Alignment alignment, int availableWidth, int maxLines) {
        if (Build.VERSION.SDK_INT >= 23) {
            return createStaticLayoutForMeasuring(text, alignment, availableWidth, maxLines);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return createStaticLayoutForMeasuringPre23(text, alignment, availableWidth);
        }
        return createStaticLayoutForMeasuringPre16(text, alignment, availableWidth);
    }

    private boolean suggestedSizeFitsInSpace(int suggestedSizeInPx, RectF availableSpace) {
        CharSequence transformedText;
        CharSequence text = this.mTextView.getText();
        TransformationMethod transformationMethod = this.mTextView.getTransformationMethod();
        if (transformationMethod != null && (transformedText = transformationMethod.getTransformation(text, this.mTextView)) != null) {
            text = transformedText;
        }
        int maxLines = Build.VERSION.SDK_INT >= 16 ? this.mTextView.getMaxLines() : -1;
        initTempTextPaint(suggestedSizeInPx);
        Layout.Alignment alignment = (Layout.Alignment) invokeAndReturnWithDefault(this.mTextView, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL);
        StaticLayout layout = createLayout(text, alignment, Math.round(availableSpace.right), maxLines);
        if (maxLines == -1 || (layout.getLineCount() <= maxLines && layout.getLineEnd(layout.getLineCount() - 1) == text.length())) {
            return ((float) layout.getHeight()) <= availableSpace.bottom;
        }
        return false;
    }

    @RequiresApi(23)
    private StaticLayout createStaticLayoutForMeasuring(CharSequence text, Layout.Alignment alignment, int availableWidth, int maxLines) {
        StaticLayout.Builder layoutBuilder = StaticLayout.Builder.obtain(text, 0, text.length(), this.mTempTextPaint, availableWidth);
        StaticLayout.Builder hyphenationFrequency = layoutBuilder.setAlignment(alignment).setLineSpacing(this.mTextView.getLineSpacingExtra(), this.mTextView.getLineSpacingMultiplier()).setIncludePad(this.mTextView.getIncludeFontPadding()).setBreakStrategy(this.mTextView.getBreakStrategy()).setHyphenationFrequency(this.mTextView.getHyphenationFrequency());
        if (maxLines == -1) {
            maxLines = Integer.MAX_VALUE;
        }
        hyphenationFrequency.setMaxLines(maxLines);
        try {
            this.mImpl.computeAndSetTextDirection(layoutBuilder, this.mTextView);
        } catch (ClassCastException e) {
            Log.w(TAG, "Failed to obtain TextDirectionHeuristic, auto size may be incorrect");
        }
        return layoutBuilder.build();
    }

    @RequiresApi(16)
    private StaticLayout createStaticLayoutForMeasuringPre23(CharSequence text, Layout.Alignment alignment, int availableWidth) {
        float lineSpacingMultiplier = this.mTextView.getLineSpacingMultiplier();
        float lineSpacingAdd = this.mTextView.getLineSpacingExtra();
        boolean includePad = this.mTextView.getIncludeFontPadding();
        return new StaticLayout(text, this.mTempTextPaint, availableWidth, alignment, lineSpacingMultiplier, lineSpacingAdd, includePad);
    }

    private StaticLayout createStaticLayoutForMeasuringPre16(CharSequence text, Layout.Alignment alignment, int availableWidth) {
        float lineSpacingMultiplier = ((Float) accessAndReturnWithDefault(this.mTextView, "mSpacingMult", Float.valueOf(1.0f))).floatValue();
        float lineSpacingAdd = ((Float) accessAndReturnWithDefault(this.mTextView, "mSpacingAdd", Float.valueOf(0.0f))).floatValue();
        boolean includePad = ((Boolean) accessAndReturnWithDefault(this.mTextView, "mIncludePad", true)).booleanValue();
        return new StaticLayout(text, this.mTempTextPaint, availableWidth, alignment, lineSpacingMultiplier, lineSpacingAdd, includePad);
    }

    static <T> T invokeAndReturnWithDefault(@NonNull Object object, @NonNull String methodName, @NonNull T defaultValue) {
        T result = null;
        try {
            try {
                Method method = getTextViewMethod(methodName);
                T result2 = (T) method.invoke(object, new Object[0]);
                if (result2 == null && 0 != 0) {
                    result = defaultValue;
                    return result;
                }
                return result2;
            } catch (Exception ex) {
                Log.w(TAG, "Failed to invoke TextView#" + methodName + "() method", ex);
                if (0 != 0 || 1 == 0) {
                    return null;
                }
                return defaultValue;
            }
        } catch (Throwable th) {
            if (result != null || 0 != 0) {
            }
            throw th;
        }
    }

    private static <T> T accessAndReturnWithDefault(@NonNull Object object, @NonNull String fieldName, @NonNull T defaultValue) {
        try {
            Field field = getTextViewField(fieldName);
            if (field != null) {
                return (T) field.get(object);
            }
            return defaultValue;
        } catch (IllegalAccessException e) {
            Log.w(TAG, "Failed to access TextView#" + fieldName + " member", e);
            return defaultValue;
        }
    }

    @Nullable
    private static Method getTextViewMethod(@NonNull String methodName) {
        try {
            Method method = sTextViewMethodByNameCache.get(methodName);
            if (method == null) {
                Method method2 = TextView.class.getDeclaredMethod(methodName, new Class[0]);
                if (method2 != null) {
                    method2.setAccessible(true);
                    sTextViewMethodByNameCache.put(methodName, method2);
                    return method2;
                }
                return method2;
            }
            return method;
        } catch (Exception ex) {
            Log.w(TAG, "Failed to retrieve TextView#" + methodName + "() method", ex);
            return null;
        }
    }

    @Nullable
    private static Field getTextViewField(@NonNull String fieldName) {
        try {
            Field field = sTextViewFieldByNameCache.get(fieldName);
            if (field == null) {
                Field field2 = TextView.class.getDeclaredField(fieldName);
                if (field2 != null) {
                    field2.setAccessible(true);
                    sTextViewFieldByNameCache.put(fieldName, field2);
                    return field2;
                }
                return field2;
            }
            return field;
        } catch (NoSuchFieldException e) {
            Log.w(TAG, "Failed to access TextView#" + fieldName + " member", e);
            return null;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isAutoSizeEnabled() {
        return supportsAutoSizeText() && this.mAutoSizeTextType != 0;
    }

    private boolean supportsAutoSizeText() {
        return !(this.mTextView instanceof AppCompatEditText);
    }
}
