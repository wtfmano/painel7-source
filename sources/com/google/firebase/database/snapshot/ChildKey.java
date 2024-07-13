package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class ChildKey implements Comparable<ChildKey> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final ChildKey INFO_CHILD_KEY;
    private static final ChildKey MAX_KEY;
    private static final ChildKey MIN_KEY;
    private static final ChildKey PRIORITY_CHILD_KEY;
    private final String key;

    static {
        $assertionsDisabled = !ChildKey.class.desiredAssertionStatus();
        MIN_KEY = new ChildKey("[MIN_KEY]");
        MAX_KEY = new ChildKey("[MAX_KEY]");
        PRIORITY_CHILD_KEY = new ChildKey(".priority");
        INFO_CHILD_KEY = new ChildKey(".info");
    }

    public static ChildKey getMinName() {
        return MIN_KEY;
    }

    public static ChildKey getMaxName() {
        return MAX_KEY;
    }

    public static ChildKey getPriorityKey() {
        return PRIORITY_CHILD_KEY;
    }

    public static ChildKey getInfoKey() {
        return INFO_CHILD_KEY;
    }

    private ChildKey(String key) {
        this.key = key;
    }

    public String asString() {
        return this.key;
    }

    public boolean isPriorityChildName() {
        return equals(PRIORITY_CHILD_KEY);
    }

    protected boolean isInt() {
        return false;
    }

    protected int intValue() {
        return 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(ChildKey other) {
        if (this == other) {
            return 0;
        }
        if (this == MIN_KEY || other == MAX_KEY) {
            return -1;
        }
        if (other == MIN_KEY || this == MAX_KEY) {
            return 1;
        }
        if (isInt()) {
            if (other.isInt()) {
                int cmp = Utilities.compareInts(intValue(), other.intValue());
                return cmp == 0 ? Utilities.compareInts(this.key.length(), other.key.length()) : cmp;
            }
            return -1;
        } else if (other.isInt()) {
            return 1;
        } else {
            return this.key.compareTo(other.key);
        }
    }

    public String toString() {
        return "ChildKey(\"" + this.key + "\")";
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ChildKey)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ChildKey other = (ChildKey) obj;
        return this.key.equals(other.key);
    }

    public static ChildKey fromString(String key) {
        Integer intValue = Utilities.tryParseInt(key);
        if (intValue != null) {
            return new IntegerChildKey(key, intValue.intValue());
        }
        if (key.equals(".priority")) {
            return PRIORITY_CHILD_KEY;
        }
        if ($assertionsDisabled || !key.contains("/")) {
            return new ChildKey(key);
        }
        throw new AssertionError();
    }

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public static class IntegerChildKey extends ChildKey {
        private final int intValue;

        @Override // com.google.firebase.database.snapshot.ChildKey, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(ChildKey childKey) {
            return super.compareTo(childKey);
        }

        IntegerChildKey(String name, int intValue) {
            super(name);
            this.intValue = intValue;
        }

        @Override // com.google.firebase.database.snapshot.ChildKey
        protected boolean isInt() {
            return true;
        }

        @Override // com.google.firebase.database.snapshot.ChildKey
        protected int intValue() {
            return this.intValue;
        }

        @Override // com.google.firebase.database.snapshot.ChildKey
        public String toString() {
            return "IntegerChildName(\"" + ((ChildKey) this).key + "\")";
        }
    }
}
