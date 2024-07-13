package com.google.firebase.database.core.view;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.filter.IndexedFilter;
import com.google.firebase.database.core.view.filter.LimitedFilter;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.core.view.filter.RangedFilter;
import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.LongNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityIndex;
import com.google.firebase.database.snapshot.PriorityUtilities;
import com.google.firebase.database.snapshot.StringNode;
import com.google.firebase.database.util.JsonMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class QueryParams {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final QueryParams DEFAULT_PARAMS;
    private static final String INDEX = "i";
    private static final String INDEX_END_NAME = "en";
    private static final String INDEX_END_VALUE = "ep";
    private static final String INDEX_START_NAME = "sn";
    private static final String INDEX_START_VALUE = "sp";
    private static final String LIMIT = "l";
    private static final String VIEW_FROM = "vf";
    private Integer limit;
    private ViewFrom viewFrom;
    private Node indexStartValue = null;
    private ChildKey indexStartName = null;
    private Node indexEndValue = null;
    private ChildKey indexEndName = null;
    private Index index = PriorityIndex.getInstance();
    private String jsonSerialization = null;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public enum ViewFrom {
        LEFT,
        RIGHT
    }

    static {
        $assertionsDisabled = !QueryParams.class.desiredAssertionStatus();
        DEFAULT_PARAMS = new QueryParams();
    }

    public boolean hasStart() {
        return this.indexStartValue != null;
    }

    public Node getIndexStartValue() {
        if (!hasStart()) {
            throw new IllegalArgumentException("Cannot get index start value if start has not been set");
        }
        return this.indexStartValue;
    }

    public ChildKey getIndexStartName() {
        if (hasStart()) {
            return this.indexStartName != null ? this.indexStartName : ChildKey.getMinName();
        }
        throw new IllegalArgumentException("Cannot get index start name if start has not been set");
    }

    public boolean hasEnd() {
        return this.indexEndValue != null;
    }

    public Node getIndexEndValue() {
        if (!hasEnd()) {
            throw new IllegalArgumentException("Cannot get index end value if start has not been set");
        }
        return this.indexEndValue;
    }

    public ChildKey getIndexEndName() {
        if (hasEnd()) {
            return this.indexEndName != null ? this.indexEndName : ChildKey.getMaxName();
        }
        throw new IllegalArgumentException("Cannot get index end name if start has not been set");
    }

    public boolean hasLimit() {
        return this.limit != null;
    }

    public boolean hasAnchoredLimit() {
        return hasLimit() && this.viewFrom != null;
    }

    public int getLimit() {
        if (!hasLimit()) {
            throw new IllegalArgumentException("Cannot get limit if limit has not been set");
        }
        return this.limit.intValue();
    }

    public Index getIndex() {
        return this.index;
    }

    private QueryParams copy() {
        QueryParams params = new QueryParams();
        params.limit = this.limit;
        params.indexStartValue = this.indexStartValue;
        params.indexStartName = this.indexStartName;
        params.indexEndValue = this.indexEndValue;
        params.indexEndName = this.indexEndName;
        params.viewFrom = this.viewFrom;
        params.index = this.index;
        return params;
    }

    public QueryParams limitToFirst(int limit) {
        QueryParams copy = copy();
        copy.limit = Integer.valueOf(limit);
        copy.viewFrom = ViewFrom.LEFT;
        return copy;
    }

    public QueryParams limitToLast(int limit) {
        QueryParams copy = copy();
        copy.limit = Integer.valueOf(limit);
        copy.viewFrom = ViewFrom.RIGHT;
        return copy;
    }

    public QueryParams startAt(Node indexStartValue, ChildKey indexStartName) {
        if ($assertionsDisabled || indexStartValue.isLeafNode() || indexStartValue.isEmpty()) {
            Utilities.hardAssert(!(indexStartValue instanceof LongNode));
            QueryParams copy = copy();
            copy.indexStartValue = indexStartValue;
            copy.indexStartName = indexStartName;
            return copy;
        }
        throw new AssertionError();
    }

    public QueryParams endAt(Node indexEndValue, ChildKey indexEndName) {
        if ($assertionsDisabled || indexEndValue.isLeafNode() || indexEndValue.isEmpty()) {
            Utilities.hardAssert(!(indexEndValue instanceof LongNode));
            QueryParams copy = copy();
            copy.indexEndValue = indexEndValue;
            copy.indexEndName = indexEndName;
            return copy;
        }
        throw new AssertionError();
    }

    public QueryParams orderBy(Index index) {
        QueryParams copy = copy();
        copy.index = index;
        return copy;
    }

    public boolean isViewFromLeft() {
        return this.viewFrom != null ? this.viewFrom == ViewFrom.LEFT : hasStart();
    }

    public Map<String, Object> getWireProtocolParams() {
        Map<String, Object> queryObject = new HashMap<>();
        if (hasStart()) {
            queryObject.put(INDEX_START_VALUE, this.indexStartValue.getValue());
            if (this.indexStartName != null) {
                queryObject.put(INDEX_START_NAME, this.indexStartName.asString());
            }
        }
        if (hasEnd()) {
            queryObject.put(INDEX_END_VALUE, this.indexEndValue.getValue());
            if (this.indexEndName != null) {
                queryObject.put(INDEX_END_NAME, this.indexEndName.asString());
            }
        }
        if (this.limit != null) {
            queryObject.put(LIMIT, this.limit);
            ViewFrom viewFromToAdd = this.viewFrom;
            if (viewFromToAdd == null) {
                if (hasStart()) {
                    viewFromToAdd = ViewFrom.LEFT;
                } else {
                    viewFromToAdd = ViewFrom.RIGHT;
                }
            }
            switch (viewFromToAdd) {
                case LEFT:
                    queryObject.put(VIEW_FROM, LIMIT);
                    break;
                case RIGHT:
                    queryObject.put(VIEW_FROM, "r");
                    break;
            }
        }
        if (!this.index.equals(PriorityIndex.getInstance())) {
            queryObject.put(INDEX, this.index.getQueryDefinition());
        }
        return queryObject;
    }

    public boolean loadsAllData() {
        return (hasStart() || hasEnd() || hasLimit()) ? false : true;
    }

    public boolean isDefault() {
        return loadsAllData() && this.index.equals(PriorityIndex.getInstance());
    }

    public boolean isValid() {
        return (hasStart() && hasEnd() && hasLimit() && !hasAnchoredLimit()) ? false : true;
    }

    public String toJSON() {
        if (this.jsonSerialization == null) {
            try {
                this.jsonSerialization = JsonMapper.serializeJson(getWireProtocolParams());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.jsonSerialization;
    }

    public static QueryParams fromQueryObject(Map<String, Object> map) {
        QueryParams params = new QueryParams();
        params.limit = (Integer) map.get(LIMIT);
        if (map.containsKey(INDEX_START_VALUE)) {
            Object indexStartValue = map.get(INDEX_START_VALUE);
            params.indexStartValue = normalizeValue(NodeUtilities.NodeFromJSON(indexStartValue));
            String indexStartName = (String) map.get(INDEX_START_NAME);
            if (indexStartName != null) {
                params.indexStartName = ChildKey.fromString(indexStartName);
            }
        }
        if (map.containsKey(INDEX_END_VALUE)) {
            Object indexEndValue = map.get(INDEX_END_VALUE);
            params.indexEndValue = normalizeValue(NodeUtilities.NodeFromJSON(indexEndValue));
            String indexEndName = (String) map.get(INDEX_END_NAME);
            if (indexEndName != null) {
                params.indexEndName = ChildKey.fromString(indexEndName);
            }
        }
        String viewFrom = (String) map.get(VIEW_FROM);
        if (viewFrom != null) {
            params.viewFrom = viewFrom.equals(LIMIT) ? ViewFrom.LEFT : ViewFrom.RIGHT;
        }
        String indexStr = (String) map.get(INDEX);
        if (indexStr != null) {
            params.index = Index.fromQueryDefinition(indexStr);
        }
        return params;
    }

    public NodeFilter getNodeFilter() {
        if (loadsAllData()) {
            return new IndexedFilter(getIndex());
        }
        if (hasLimit()) {
            return new LimitedFilter(this);
        }
        return new RangedFilter(this);
    }

    public String toString() {
        return getWireProtocolParams().toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryParams that = (QueryParams) o;
        if (this.limit == null ? that.limit != null : !this.limit.equals(that.limit)) {
            return false;
        }
        if (this.index == null ? that.index != null : !this.index.equals(that.index)) {
            return false;
        }
        if (this.indexEndName == null ? that.indexEndName != null : !this.indexEndName.equals(that.indexEndName)) {
            return false;
        }
        if (this.indexEndValue == null ? that.indexEndValue != null : !this.indexEndValue.equals(that.indexEndValue)) {
            return false;
        }
        if (this.indexStartName == null ? that.indexStartName != null : !this.indexStartName.equals(that.indexStartName)) {
            return false;
        }
        if (this.indexStartValue == null ? that.indexStartValue != null : !this.indexStartValue.equals(that.indexStartValue)) {
            return false;
        }
        return isViewFromLeft() == that.isViewFromLeft();
    }

    public int hashCode() {
        int result = this.limit != null ? this.limit.intValue() : 0;
        return (((((((((((result * 31) + (isViewFromLeft() ? 1231 : 1237)) * 31) + (this.indexStartValue != null ? this.indexStartValue.hashCode() : 0)) * 31) + (this.indexStartName != null ? this.indexStartName.hashCode() : 0)) * 31) + (this.indexEndValue != null ? this.indexEndValue.hashCode() : 0)) * 31) + (this.indexEndName != null ? this.indexEndName.hashCode() : 0)) * 31) + (this.index != null ? this.index.hashCode() : 0);
    }

    private static Node normalizeValue(Node value) {
        if (!(value instanceof StringNode) && !(value instanceof BooleanNode) && !(value instanceof DoubleNode) && !(value instanceof EmptyNode)) {
            if (value instanceof LongNode) {
                return new DoubleNode(Double.valueOf(((Long) value.getValue()).doubleValue()), PriorityUtilities.NullPriority());
            }
            throw new IllegalStateException("Unexpected value passed to normalizeValue: " + value.getValue());
        }
        return value;
    }
}
