package androidx.appcompat.widget;

import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

/* loaded from: classes.dex */
final class AppCompatTextClassifierHelper {
    @Nullable
    private TextClassifier mTextClassifier;
    @NonNull
    private TextView mTextView;

    public AppCompatTextClassifierHelper(@NonNull TextView textView) {
        this.mTextView = (TextView) Preconditions.checkNotNull(textView);
    }

    @RequiresApi(api = 26)
    public void setTextClassifier(@Nullable TextClassifier textClassifier) {
        this.mTextClassifier = textClassifier;
    }

    @NonNull
    @RequiresApi(api = 26)
    public TextClassifier getTextClassifier() {
        if (this.mTextClassifier == null) {
            TextClassificationManager tcm = (TextClassificationManager) this.mTextView.getContext().getSystemService(TextClassificationManager.class);
            if (tcm != null) {
                return tcm.getTextClassifier();
            }
            return TextClassifier.NO_OP;
        }
        return this.mTextClassifier;
    }
}
