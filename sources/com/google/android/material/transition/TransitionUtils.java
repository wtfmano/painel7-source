package com.google.android.material.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.transition.PathMotion;
import androidx.transition.PatternPathMotion;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

/* loaded from: classes.dex */
class TransitionUtils {
    private static final String EASING_TYPE_CUBIC_BEZIER = "cubic-bezier";
    private static final String EASING_TYPE_FORMAT_END = ")";
    private static final String EASING_TYPE_FORMAT_START = "(";
    private static final String EASING_TYPE_PATH = "path";
    @AttrRes
    static final int NO_ATTR_RES_ID = 0;
    static final int NO_DURATION = -1;
    private static final int PATH_TYPE_ARC = 1;
    private static final int PATH_TYPE_LINEAR = 0;
    private static final RectF transformAlphaRectF = new RectF();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface CanvasOperation {
        void run(Canvas canvas);
    }

    /* loaded from: classes.dex */
    public interface CornerSizeBinaryOperator {
        @NonNull
        CornerSize apply(@NonNull CornerSize cornerSize, @NonNull CornerSize cornerSize2);
    }

    private TransitionUtils() {
    }

    public static boolean maybeApplyThemeInterpolator(Transition transition, Context context, @AttrRes int attrResId, TimeInterpolator defaultInterpolator) {
        if (attrResId == 0 || transition.getInterpolator() != null) {
            return false;
        }
        TimeInterpolator interpolator = resolveThemeInterpolator(context, attrResId, defaultInterpolator);
        transition.setInterpolator(interpolator);
        return true;
    }

    public static boolean maybeApplyThemeDuration(Transition transition, Context context, @AttrRes int attrResId) {
        int duration;
        if (attrResId == 0 || transition.getDuration() != -1 || (duration = MaterialAttributes.resolveInteger(context, attrResId, -1)) == -1) {
            return false;
        }
        transition.setDuration(duration);
        return true;
    }

    public static boolean maybeApplyThemePath(Transition transition, Context context, @AttrRes int attrResId) {
        PathMotion pathMotion;
        if (attrResId == 0 || (pathMotion = resolveThemePath(context, attrResId)) == null) {
            return false;
        }
        transition.setPathMotion(pathMotion);
        return true;
    }

    static TimeInterpolator resolveThemeInterpolator(Context context, @AttrRes int attrResId, @NonNull TimeInterpolator defaultInterpolator) {
        TypedValue easingValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrResId, easingValue, true)) {
            if (easingValue.type != 3) {
                throw new IllegalArgumentException("Motion easing theme attribute must be a string");
            }
            String easingString = String.valueOf(easingValue.string);
            if (isEasingType(easingString, EASING_TYPE_CUBIC_BEZIER)) {
                String controlPointsString = getEasingContent(easingString, EASING_TYPE_CUBIC_BEZIER);
                String[] controlPoints = controlPointsString.split(",");
                if (controlPoints.length != 4) {
                    throw new IllegalArgumentException("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: " + controlPoints.length);
                }
                float controlX1 = getControlPoint(controlPoints, 0);
                float controlY1 = getControlPoint(controlPoints, 1);
                float controlX2 = getControlPoint(controlPoints, 2);
                float controlY2 = getControlPoint(controlPoints, 3);
                TimeInterpolator defaultInterpolator2 = PathInterpolatorCompat.create(controlX1, controlY1, controlX2, controlY2);
                return defaultInterpolator2;
            } else if (isEasingType(easingString, "path")) {
                String path = getEasingContent(easingString, "path");
                TimeInterpolator defaultInterpolator3 = PathInterpolatorCompat.create(PathParser.createPathFromPathData(path));
                return defaultInterpolator3;
            } else {
                throw new IllegalArgumentException("Invalid motion easing type: " + easingString);
            }
        }
        return defaultInterpolator;
    }

    private static boolean isEasingType(String easingString, String easingType) {
        return easingString.startsWith(new StringBuilder().append(easingType).append(EASING_TYPE_FORMAT_START).toString()) && easingString.endsWith(EASING_TYPE_FORMAT_END);
    }

    private static String getEasingContent(String easingString, String easingType) {
        return easingString.substring(easingType.length() + EASING_TYPE_FORMAT_START.length(), easingString.length() - EASING_TYPE_FORMAT_END.length());
    }

    private static float getControlPoint(String[] controlPoints, int index) {
        float controlPoint = Float.parseFloat(controlPoints[index]);
        if (controlPoint < 0.0f || controlPoint > 1.0f) {
            throw new IllegalArgumentException("Motion easing control point value must be between 0 and 1; instead got: " + controlPoint);
        }
        return controlPoint;
    }

    @Nullable
    static PathMotion resolveThemePath(Context context, @AttrRes int attrResId) {
        TypedValue pathValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrResId, pathValue, true)) {
            if (pathValue.type == 16) {
                int pathInt = pathValue.data;
                if (pathInt == 0) {
                    return null;
                }
                if (pathInt == 1) {
                    return new MaterialArcMotion();
                }
                throw new IllegalArgumentException("Invalid motion path type: " + pathInt);
            } else if (pathValue.type == 3) {
                String pathString = String.valueOf(pathValue.string);
                return new PatternPathMotion(PathParser.createPathFromPathData(pathString));
            } else {
                throw new IllegalArgumentException("Motion path theme attribute must either be an enum value or path data string");
            }
        }
        return null;
    }

    public static ShapeAppearanceModel convertToRelativeCornerSizes(ShapeAppearanceModel shapeAppearanceModel, final RectF bounds) {
        return shapeAppearanceModel.withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() { // from class: com.google.android.material.transition.TransitionUtils.1
            @Override // com.google.android.material.shape.ShapeAppearanceModel.CornerSizeUnaryOperator
            @NonNull
            public CornerSize apply(@NonNull CornerSize cornerSize) {
                return cornerSize instanceof RelativeCornerSize ? cornerSize : new RelativeCornerSize(cornerSize.getCornerSize(bounds) / bounds.height());
            }
        });
    }

    static ShapeAppearanceModel transformCornerSizes(ShapeAppearanceModel shapeAppearanceModel1, ShapeAppearanceModel shapeAppearanceModel2, RectF shapeAppearanceModel1Bounds, CornerSizeBinaryOperator op) {
        ShapeAppearanceModel shapeAppearanceModel = isShapeAppearanceSignificant(shapeAppearanceModel1, shapeAppearanceModel1Bounds) ? shapeAppearanceModel1 : shapeAppearanceModel2;
        return shapeAppearanceModel.toBuilder().setTopLeftCornerSize(op.apply(shapeAppearanceModel1.getTopLeftCornerSize(), shapeAppearanceModel2.getTopLeftCornerSize())).setTopRightCornerSize(op.apply(shapeAppearanceModel1.getTopRightCornerSize(), shapeAppearanceModel2.getTopRightCornerSize())).setBottomLeftCornerSize(op.apply(shapeAppearanceModel1.getBottomLeftCornerSize(), shapeAppearanceModel2.getBottomLeftCornerSize())).setBottomRightCornerSize(op.apply(shapeAppearanceModel1.getBottomRightCornerSize(), shapeAppearanceModel2.getBottomRightCornerSize())).build();
    }

    private static boolean isShapeAppearanceSignificant(ShapeAppearanceModel shapeAppearanceModel, RectF bounds) {
        return (shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(bounds) == 0.0f && shapeAppearanceModel.getTopRightCornerSize().getCornerSize(bounds) == 0.0f && shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(bounds) == 0.0f && shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(bounds) == 0.0f) ? false : true;
    }

    public static float lerp(float startValue, float endValue, float fraction) {
        return ((endValue - startValue) * fraction) + startValue;
    }

    public static float lerp(float startValue, float endValue, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        return lerp(startValue, endValue, startFraction, endFraction, fraction, false);
    }

    public static float lerp(float startValue, float endValue, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @FloatRange(from = 0.0d) float fraction, boolean allowOvershoot) {
        if (allowOvershoot && (fraction < 0.0f || fraction > 1.0f)) {
            return lerp(startValue, endValue, fraction);
        }
        if (fraction >= startFraction) {
            return fraction > endFraction ? endValue : lerp(startValue, endValue, (fraction - startFraction) / (endFraction - startFraction));
        }
        return startValue;
    }

    public static int lerp(int startValue, int endValue, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, @FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        if (fraction < startFraction) {
            return startValue;
        }
        return fraction > endFraction ? endValue : (int) lerp(startValue, endValue, (fraction - startFraction) / (endFraction - startFraction));
    }

    public static ShapeAppearanceModel lerp(ShapeAppearanceModel startValue, ShapeAppearanceModel endValue, final RectF startBounds, final RectF endBounds, @FloatRange(from = 0.0d, to = 1.0d) final float startFraction, @FloatRange(from = 0.0d, to = 1.0d) final float endFraction, @FloatRange(from = 0.0d, to = 1.0d) final float fraction) {
        if (fraction < startFraction) {
            return startValue;
        }
        return fraction > endFraction ? endValue : transformCornerSizes(startValue, endValue, startBounds, new CornerSizeBinaryOperator() { // from class: com.google.android.material.transition.TransitionUtils.2
            @Override // com.google.android.material.transition.TransitionUtils.CornerSizeBinaryOperator
            @NonNull
            public CornerSize apply(@NonNull CornerSize cornerSize1, @NonNull CornerSize cornerSize2) {
                float startCornerSize = cornerSize1.getCornerSize(startBounds);
                float endCornerSize = cornerSize2.getCornerSize(endBounds);
                float cornerSize = TransitionUtils.lerp(startCornerSize, endCornerSize, startFraction, endFraction, fraction);
                return new AbsoluteCornerSize(cornerSize);
            }
        });
    }

    public static Shader createColorShader(@ColorInt int color) {
        return new LinearGradient(0.0f, 0.0f, 0.0f, 0.0f, color, color, Shader.TileMode.CLAMP);
    }

    public static View findDescendantOrAncestorById(View view, @IdRes int viewId) {
        View descendant = view.findViewById(viewId);
        return descendant != null ? descendant : findAncestorById(view, viewId);
    }

    public static View findAncestorById(View view, @IdRes int ancestorId) {
        String resourceName = view.getResources().getResourceName(ancestorId);
        while (view != null) {
            if (view.getId() == ancestorId) {
                return view;
            }
            ViewParent parent = view.getParent();
            if (!(parent instanceof View)) {
                break;
            }
            view = (View) parent;
        }
        throw new IllegalArgumentException(resourceName + " is not a valid ancestor");
    }

    public static RectF getRelativeBounds(View view) {
        return new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    static Rect getRelativeBoundsRect(View view) {
        return new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    public static RectF getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();
        return new RectF(left, top, right, bottom);
    }

    @NonNull
    public static <T> T defaultIfNull(@Nullable T value, @NonNull T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static float calculateArea(@NonNull RectF bounds) {
        return bounds.width() * bounds.height();
    }

    private static int saveLayerAlphaCompat(Canvas canvas, Rect bounds, int alpha) {
        transformAlphaRectF.set(bounds);
        return Build.VERSION.SDK_INT >= 21 ? canvas.saveLayerAlpha(transformAlphaRectF, alpha) : canvas.saveLayerAlpha(transformAlphaRectF.left, transformAlphaRectF.top, transformAlphaRectF.right, transformAlphaRectF.bottom, alpha, 31);
    }

    public static void transform(Canvas canvas, Rect bounds, float dx, float dy, float scale, int alpha, CanvasOperation op) {
        if (alpha > 0) {
            int checkpoint = canvas.save();
            canvas.translate(dx, dy);
            canvas.scale(scale, scale);
            if (alpha < 255) {
                saveLayerAlphaCompat(canvas, bounds, alpha);
            }
            op.run(canvas);
            canvas.restoreToCount(checkpoint);
        }
    }

    static void maybeAddTransition(TransitionSet transitionSet, @Nullable Transition transition) {
        if (transition != null) {
            transitionSet.addTransition(transition);
        }
    }

    static void maybeRemoveTransition(TransitionSet transitionSet, @Nullable Transition transition) {
        if (transition != null) {
            transitionSet.removeTransition(transition);
        }
    }
}
