package androidx.recyclerview.widget;

import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public interface ViewTypeStorage {

    /* loaded from: classes.dex */
    public interface ViewTypeLookup {
        void dispose();

        int globalToLocal(int i);

        int localToGlobal(int i);
    }

    @NonNull
    ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper nestedAdapterWrapper);

    @NonNull
    NestedAdapterWrapper getWrapperForGlobalType(int i);

    /* loaded from: classes.dex */
    public static class SharedIdRangeViewTypeStorage implements ViewTypeStorage {
        SparseArray<List<NestedAdapterWrapper>> mGlobalTypeToWrapper = new SparseArray<>();

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int globalViewType) {
            List<NestedAdapterWrapper> nestedAdapterWrappers = this.mGlobalTypeToWrapper.get(globalViewType);
            if (nestedAdapterWrappers == null || nestedAdapterWrappers.isEmpty()) {
                throw new IllegalArgumentException("Cannot find the wrapper for global view type " + globalViewType);
            }
            return nestedAdapterWrappers.get(0);
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            return new WrapperViewTypeLookup(wrapper);
        }

        void removeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            for (int i = this.mGlobalTypeToWrapper.size() - 1; i >= 0; i--) {
                List<NestedAdapterWrapper> wrappers = this.mGlobalTypeToWrapper.valueAt(i);
                if (wrappers.remove(wrapper) && wrappers.isEmpty()) {
                    this.mGlobalTypeToWrapper.removeAt(i);
                }
            }
        }

        /* loaded from: classes.dex */
        class WrapperViewTypeLookup implements ViewTypeLookup {
            final NestedAdapterWrapper mWrapper;

            WrapperViewTypeLookup(NestedAdapterWrapper wrapper) {
                SharedIdRangeViewTypeStorage.this = this$0;
                this.mWrapper = wrapper;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int localToGlobal(int localType) {
                List<NestedAdapterWrapper> wrappers = SharedIdRangeViewTypeStorage.this.mGlobalTypeToWrapper.get(localType);
                if (wrappers == null) {
                    wrappers = new ArrayList<>();
                    SharedIdRangeViewTypeStorage.this.mGlobalTypeToWrapper.put(localType, wrappers);
                }
                if (!wrappers.contains(this.mWrapper)) {
                    wrappers.add(this.mWrapper);
                }
                return localType;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int globalToLocal(int globalType) {
                return globalType;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public void dispose() {
                SharedIdRangeViewTypeStorage.this.removeWrapper(this.mWrapper);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class IsolatedViewTypeStorage implements ViewTypeStorage {
        SparseArray<NestedAdapterWrapper> mGlobalTypeToWrapper = new SparseArray<>();
        int mNextViewType = 0;

        int obtainViewType(NestedAdapterWrapper wrapper) {
            int nextId = this.mNextViewType;
            this.mNextViewType = nextId + 1;
            this.mGlobalTypeToWrapper.put(nextId, wrapper);
            return nextId;
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int globalViewType) {
            NestedAdapterWrapper wrapper = this.mGlobalTypeToWrapper.get(globalViewType);
            if (wrapper == null) {
                throw new IllegalArgumentException("Cannot find the wrapper for global view type " + globalViewType);
            }
            return wrapper;
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            return new WrapperViewTypeLookup(wrapper);
        }

        void removeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            for (int i = this.mGlobalTypeToWrapper.size() - 1; i >= 0; i--) {
                NestedAdapterWrapper existingWrapper = this.mGlobalTypeToWrapper.valueAt(i);
                if (existingWrapper == wrapper) {
                    this.mGlobalTypeToWrapper.removeAt(i);
                }
            }
        }

        /* loaded from: classes.dex */
        class WrapperViewTypeLookup implements ViewTypeLookup {
            final NestedAdapterWrapper mWrapper;
            private SparseIntArray mLocalToGlobalMapping = new SparseIntArray(1);
            private SparseIntArray mGlobalToLocalMapping = new SparseIntArray(1);

            WrapperViewTypeLookup(NestedAdapterWrapper wrapper) {
                IsolatedViewTypeStorage.this = this$0;
                this.mWrapper = wrapper;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int localToGlobal(int localType) {
                int index = this.mLocalToGlobalMapping.indexOfKey(localType);
                if (index > -1) {
                    return this.mLocalToGlobalMapping.valueAt(index);
                }
                int globalType = IsolatedViewTypeStorage.this.obtainViewType(this.mWrapper);
                this.mLocalToGlobalMapping.put(localType, globalType);
                this.mGlobalToLocalMapping.put(globalType, localType);
                return globalType;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int globalToLocal(int globalType) {
                int index = this.mGlobalToLocalMapping.indexOfKey(globalType);
                if (index < 0) {
                    throw new IllegalStateException("requested global type " + globalType + " does not belong to the adapter:" + this.mWrapper.adapter);
                }
                return this.mGlobalToLocalMapping.valueAt(index);
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public void dispose() {
                IsolatedViewTypeStorage.this.removeWrapper(this.mWrapper);
            }
        }
    }
}
