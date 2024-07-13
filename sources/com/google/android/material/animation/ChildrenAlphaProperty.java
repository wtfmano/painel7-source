package com.google.android.material.animation;

import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.google.android.material.R;

/* loaded from: classes.dex */
public class ChildrenAlphaProperty extends Property<ViewGroup, Float> {
    public static final Property<ViewGroup, Float> CHILDREN_ALPHA = new ChildrenAlphaProperty("childrenAlpha");

    private ChildrenAlphaProperty(String name) {
        super(Float.class, name);
    }

    @Override // android.util.Property
    @NonNull
    public Float get(@NonNull ViewGroup object) {
        Float alpha = (Float) object.getTag(R.id.mtrl_internal_children_alpha_tag);
        return alpha != null ? alpha : Float.valueOf(1.0f);
    }

    @Override // android.util.Property
    public void set(@NonNull ViewGroup object, @NonNull Float value) {
        float alpha = value.floatValue();
        object.setTag(R.id.mtrl_internal_children_alpha_tag, Float.valueOf(alpha));
        int count = object.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = object.getChildAt(i);
            child.setAlpha(alpha);
        }
    }
}
