package com.google.firebase.database.core;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class Tag {
    private final long tagNumber;

    public Tag(long tagNumber) {
        this.tagNumber = tagNumber;
    }

    public long getTagNumber() {
        return this.tagNumber;
    }

    public String toString() {
        return "Tag{tagNumber=" + this.tagNumber + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return this.tagNumber == tag.tagNumber;
    }

    public int hashCode() {
        return (int) (this.tagNumber ^ (this.tagNumber >>> 32));
    }
}
