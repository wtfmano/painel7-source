package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import java.lang.ref.WeakReference;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class TextDrawableHelper {
    @Nullable
    private TextAppearance textAppearance;
    private float textWidth;
    private final TextPaint textPaint = new TextPaint(1);
    private final TextAppearanceFontCallback fontCallback = new TextAppearanceFontCallback() { // from class: com.google.android.material.internal.TextDrawableHelper.1
        @Override // com.google.android.material.resources.TextAppearanceFontCallback
        public void onFontRetrieved(@NonNull Typeface typeface, boolean fontResolvedSynchronously) {
            if (!fontResolvedSynchronously) {
                TextDrawableHelper.this.textWidthDirty = true;
                TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) TextDrawableHelper.this.delegate.get();
                if (textDrawableDelegate != null) {
                    textDrawableDelegate.onTextSizeChange();
                }
            }
        }

        @Override // com.google.android.material.resources.TextAppearanceFontCallback
        public void onFontRetrievalFailed(int reason) {
            TextDrawableHelper.this.textWidthDirty = true;
            TextDrawableDelegate textDrawableDelegate = (TextDrawableDelegate) TextDrawableHelper.this.delegate.get();
            if (textDrawableDelegate != null) {
                textDrawableDelegate.onTextSizeChange();
            }
        }
    };
    private boolean textWidthDirty = true;
    @Nullable
    private WeakReference<TextDrawableDelegate> delegate = new WeakReference<>(null);

    /* loaded from: classes.dex */
    public interface TextDrawableDelegate {
        @NonNull
        int[] getState();

        boolean onStateChange(int[] iArr);

        void onTextSizeChange();
    }

    public TextDrawableHelper(@Nullable TextDrawableDelegate delegate) {
        setDelegate(delegate);
    }

    public void setDelegate(@Nullable TextDrawableDelegate delegate) {
        this.delegate = new WeakReference<>(delegate);
    }

    @NonNull
    public TextPaint getTextPaint() {
        return this.textPaint;
    }

    public void setTextWidthDirty(boolean dirty) {
        this.textWidthDirty = dirty;
    }

    public boolean isTextWidthDirty() {
        return this.textWidthDirty;
    }

    public float getTextWidth(String text) {
        if (!this.textWidthDirty) {
            return this.textWidth;
        }
        this.textWidth = calculateTextWidth(text);
        this.textWidthDirty = false;
        return this.textWidth;
    }

    private float calculateTextWidth(@Nullable CharSequence charSequence) {
        if (charSequence == null) {
            return 0.0f;
        }
        return this.textPaint.measureText(charSequence, 0, charSequence.length());
    }

    @Nullable
    public TextAppearance getTextAppearance() {
        return this.textAppearance;
    }

    public void setTextAppearance(@Nullable TextAppearance textAppearance, Context context) {
        if (this.textAppearance != textAppearance) {
            this.textAppearance = textAppearance;
            if (textAppearance != null) {
                textAppearance.updateMeasureState(context, this.textPaint, this.fontCallback);
                TextDrawableDelegate textDrawableDelegate = this.delegate.get();
                if (textDrawableDelegate != null) {
                    this.textPaint.drawableState = textDrawableDelegate.getState();
                }
                textAppearance.updateDrawState(context, this.textPaint, this.fontCallback);
                this.textWidthDirty = true;
            }
            TextDrawableDelegate textDrawableDelegate2 = this.delegate.get();
            if (textDrawableDelegate2 != null) {
                textDrawableDelegate2.onTextSizeChange();
                textDrawableDelegate2.onStateChange(textDrawableDelegate2.getState());
            }
        }
    }

    public void updateTextPaintDrawState(Context context) {
        this.textAppearance.updateDrawState(context, this.textPaint, this.fontCallback);
    }
}