package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.XmlRes;
import androidx.core.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public final class ColorStateListInflaterCompat {
    private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();

    private ColorStateListInflaterCompat() {
    }

    @Nullable
    public static ColorStateList inflate(@NonNull Resources resources, @XmlRes int resId, @Nullable Resources.Theme theme) {
        try {
            XmlPullParser parser = resources.getXml(resId);
            return createFromXml(resources, parser, theme);
        } catch (Exception e) {
            Log.e("CSLCompat", "Failed to inflate ColorStateList.", e);
            return null;
        }
    }

    @NonNull
    public static ColorStateList createFromXml(@NonNull Resources r, @NonNull XmlPullParser parser, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int type;
        AttributeSet attrs = Xml.asAttributeSet(parser);
        do {
            type = parser.next();
            if (type == 2) {
                break;
            }
        } while (type != 1);
        if (type != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        return createFromXmlInner(r, parser, attrs, theme);
    }

    @NonNull
    public static ColorStateList createFromXmlInner(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = parser.getName();
        if (!name.equals("selector")) {
            throw new XmlPullParserException(parser.getPositionDescription() + ": invalid color state list tag " + name);
        }
        return inflate(r, parser, attrs, theme);
    }

    private static ColorStateList inflate(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int baseColor;
        int j;
        int innerDepth = parser.getDepth() + 1;
        int[][] stateSpecList = new int[20];
        int[] colorList = new int[stateSpecList.length];
        int listSize = 0;
        while (true) {
            int type = parser.next();
            if (type == 1 || ((depth = parser.getDepth()) < innerDepth && type == 3)) {
                break;
            } else if (type == 2 && depth <= innerDepth && parser.getName().equals("item")) {
                TypedArray a = obtainAttributes(r, theme, attrs, R.styleable.ColorStateListItem);
                int resourceId = a.getResourceId(R.styleable.ColorStateListItem_android_color, -1);
                if (resourceId != -1 && !isColorInt(r, resourceId)) {
                    try {
                        baseColor = createFromXml(r, r.getXml(resourceId), theme).getDefaultColor();
                    } catch (Exception e) {
                        baseColor = a.getColor(R.styleable.ColorStateListItem_android_color, -65281);
                    }
                } else {
                    baseColor = a.getColor(R.styleable.ColorStateListItem_android_color, -65281);
                }
                float alphaMod = 1.0f;
                if (a.hasValue(R.styleable.ColorStateListItem_android_alpha)) {
                    alphaMod = a.getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0f);
                } else if (a.hasValue(R.styleable.ColorStateListItem_alpha)) {
                    alphaMod = a.getFloat(R.styleable.ColorStateListItem_alpha, 1.0f);
                }
                a.recycle();
                int j2 = 0;
                int numAttrs = attrs.getAttributeCount();
                int[] stateSpec = new int[numAttrs];
                int i = 0;
                while (true) {
                    j = j2;
                    if (i >= numAttrs) {
                        break;
                    }
                    int stateResId = attrs.getAttributeNameResource(i);
                    if (stateResId == 16843173 || stateResId == 16843551 || stateResId == R.attr.alpha) {
                        j2 = j;
                    } else {
                        j2 = j + 1;
                        if (!attrs.getAttributeBooleanValue(i, false)) {
                            stateResId = -stateResId;
                        }
                        stateSpec[j] = stateResId;
                    }
                    i++;
                }
                int[] stateSpec2 = StateSet.trimStateSet(stateSpec, j);
                int color = modulateColorAlpha(baseColor, alphaMod);
                colorList = GrowingArrayUtils.append(colorList, listSize, color);
                stateSpecList = (int[][]) GrowingArrayUtils.append(stateSpecList, listSize, stateSpec2);
                listSize++;
            }
        }
        int[] colors = new int[listSize];
        int[][] stateSpecs = new int[listSize];
        System.arraycopy(colorList, 0, colors, 0, listSize);
        System.arraycopy(stateSpecList, 0, stateSpecs, 0, listSize);
        return new ColorStateList(stateSpecs, colors);
    }

    private static boolean isColorInt(@NonNull Resources r, @ColorRes int resId) {
        TypedValue value = getTypedValue();
        r.getValue(resId, value, true);
        return value.type >= 28 && value.type <= 31;
    }

    @NonNull
    private static TypedValue getTypedValue() {
        TypedValue tv = sTempTypedValue.get();
        if (tv == null) {
            TypedValue tv2 = new TypedValue();
            sTempTypedValue.set(tv2);
            return tv2;
        }
        return tv;
    }

    private static TypedArray obtainAttributes(Resources res, Resources.Theme theme, AttributeSet set, int[] attrs) {
        return theme == null ? res.obtainAttributes(set, attrs) : theme.obtainStyledAttributes(set, attrs, 0, 0);
    }

    @ColorInt
    private static int modulateColorAlpha(@ColorInt int color, @FloatRange(from = 0.0d, to = 1.0d) float alphaMod) {
        int alpha = Math.round(Color.alpha(color) * alphaMod);
        return (16777215 & color) | (alpha << 24);
    }
}
