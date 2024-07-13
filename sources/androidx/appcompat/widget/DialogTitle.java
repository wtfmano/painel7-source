package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class DialogTitle extends AppCompatTextView {
    public DialogTitle(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DialogTitle(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogTitle(@NonNull Context context) {
        super(context);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int lineCount;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        if (layout != null && (lineCount = layout.getLineCount()) > 0) {
            int ellipsisCount = layout.getEllipsisCount(lineCount - 1);
            if (ellipsisCount > 0) {
                setSingleLine(false);
                setMaxLines(2);
                TypedArray a = getContext().obtainStyledAttributes(null, R.styleable.TextAppearance, 16842817, 16973892);
                int textSize = a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
                if (textSize != 0) {
                    setTextSize(0, textSize);
                }
                a.recycle();
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }
}
