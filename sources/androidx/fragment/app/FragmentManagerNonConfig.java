package androidx.fragment.app;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelStore;
import java.util.Collection;
import java.util.Map;

@Deprecated
/* loaded from: classes.dex */
public class FragmentManagerNonConfig {
    @Nullable
    private final Map<String, FragmentManagerNonConfig> mChildNonConfigs;
    @Nullable
    private final Collection<Fragment> mFragments;
    @Nullable
    private final Map<String, ViewModelStore> mViewModelStores;

    public FragmentManagerNonConfig(@Nullable Collection<Fragment> collection, @Nullable Map<String, FragmentManagerNonConfig> map, @Nullable Map<String, ViewModelStore> map2) {
        this.mFragments = collection;
        this.mChildNonConfigs = map;
        this.mViewModelStores = map2;
    }

    boolean isRetaining(Fragment fragment) {
        if (this.mFragments != null) {
            return this.mFragments.contains(fragment);
        }
        return false;
    }

    @Nullable
    public Collection<Fragment> getFragments() {
        return this.mFragments;
    }

    @Nullable
    public Map<String, FragmentManagerNonConfig> getChildNonConfigs() {
        return this.mChildNonConfigs;
    }

    @Nullable
    public Map<String, ViewModelStore> getViewModelStores() {
        return this.mViewModelStores;
    }
}
