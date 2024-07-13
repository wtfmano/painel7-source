package androidx.core.widget;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.OnReceiveContentListener;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public final class TextViewOnReceiveContentListener implements OnReceiveContentListener {
    private static final String LOG_TAG = "ReceiveContent";

    @Override // androidx.core.view.OnReceiveContentListener
    @Nullable
    public ContentInfoCompat onReceiveContent(@NonNull View view, @NonNull ContentInfoCompat payload) {
        if (Log.isLoggable(LOG_TAG, 3)) {
            Log.d(LOG_TAG, "onReceive: " + payload);
        }
        int source = payload.getSource();
        if (source != 2) {
            if (source == 3) {
                onReceiveForDragAndDrop((TextView) view, payload);
                return null;
            }
            ClipData clip = payload.getClip();
            int flags = payload.getFlags();
            TextView textView = (TextView) view;
            Editable editable = (Editable) textView.getText();
            Context context = textView.getContext();
            boolean didFirst = false;
            for (int i = 0; i < clip.getItemCount(); i++) {
                CharSequence itemText = coerceToText(context, clip.getItemAt(i), flags);
                if (itemText != null) {
                    if (!didFirst) {
                        replaceSelection(editable, itemText);
                        didFirst = true;
                    } else {
                        editable.insert(Selection.getSelectionEnd(editable), "\n");
                        editable.insert(Selection.getSelectionEnd(editable), itemText);
                    }
                }
            }
            return null;
        }
        return payload;
    }

    private static void onReceiveForDragAndDrop(@NonNull TextView view, @NonNull ContentInfoCompat payload) {
        CharSequence text = coerceToText(payload.getClip(), view.getContext(), payload.getFlags());
        replaceSelection((Editable) view.getText(), text);
    }

    @NonNull
    private static CharSequence coerceToText(@NonNull ClipData clip, @NonNull Context context, int flags) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int i = 0; i < clip.getItemCount(); i++) {
            CharSequence itemText = coerceToText(context, clip.getItemAt(i), flags);
            if (itemText != null) {
                ssb.append(itemText);
            }
        }
        return ssb;
    }

    private static CharSequence coerceToText(@NonNull Context context, @NonNull ClipData.Item item, int flags) {
        return Build.VERSION.SDK_INT >= 16 ? Api16Impl.coerce(context, item, flags) : ApiImpl.coerce(context, item, flags);
    }

    private static void replaceSelection(@NonNull Editable editable, @NonNull CharSequence replacement) {
        int selStart = Selection.getSelectionStart(editable);
        int selEnd = Selection.getSelectionEnd(editable);
        int start = Math.max(0, Math.min(selStart, selEnd));
        int end = Math.max(0, Math.max(selStart, selEnd));
        Selection.setSelection(editable, end);
        editable.replace(start, end, replacement);
    }

    @RequiresApi(16)
    /* loaded from: classes.dex */
    public static final class Api16Impl {
        private Api16Impl() {
        }

        static CharSequence coerce(@NonNull Context context, @NonNull ClipData.Item item, int flags) {
            if ((flags & 1) != 0) {
                CharSequence text = item.coerceToText(context);
                return text instanceof Spanned ? text.toString() : text;
            }
            return item.coerceToStyledText(context);
        }
    }

    /* loaded from: classes.dex */
    public static final class ApiImpl {
        private ApiImpl() {
        }

        static CharSequence coerce(@NonNull Context context, @NonNull ClipData.Item item, int flags) {
            CharSequence text = item.coerceToText(context);
            if ((flags & 1) != 0 && (text instanceof Spanned)) {
                return text.toString();
            }
            return text;
        }
    }
}
