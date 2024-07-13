package androidx.lifecycle;

import android.view.View;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.viewmodel.R;

/* loaded from: classes.dex */
public class ViewTreeViewModelStoreOwner {
    private ViewTreeViewModelStoreOwner() {
    }

    public static void set(@NonNull View view, @Nullable ViewModelStoreOwner viewModelStoreOwner) {
        view.setTag(R.id.view_tree_view_model_store_owner, viewModelStoreOwner);
    }

    @Nullable
    public static ViewModelStoreOwner get(@NonNull View view) {
        ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) view.getTag(R.id.view_tree_view_model_store_owner);
        if (viewModelStoreOwner != null) {
            return viewModelStoreOwner;
        }
        ViewParent parent = view.getParent();
        while (true) {
            ViewParent viewParent = parent;
            if (viewModelStoreOwner != null || !(viewParent instanceof View)) {
                break;
            }
            View view2 = (View) viewParent;
            viewModelStoreOwner = (ViewModelStoreOwner) view2.getTag(R.id.view_tree_view_model_store_owner);
            parent = view2.getParent();
        }
        return viewModelStoreOwner;
    }
}
