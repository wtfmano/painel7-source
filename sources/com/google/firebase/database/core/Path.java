package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class Path implements Iterable<ChildKey>, Comparable<Path> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Path EMPTY_PATH;
    private final int end;
    private final ChildKey[] pieces;
    private final int start;

    static {
        $assertionsDisabled = !Path.class.desiredAssertionStatus();
        EMPTY_PATH = new Path("");
    }

    public static Path getRelative(Path from, Path to) {
        ChildKey outerFront = from.getFront();
        ChildKey innerFront = to.getFront();
        if (outerFront != null) {
            if (outerFront.equals(innerFront)) {
                return getRelative(from.popFront(), to.popFront());
            }
            throw new DatabaseException("INTERNAL ERROR: " + to + " is not contained in " + from);
        }
        return to;
    }

    public static Path getEmptyPath() {
        return EMPTY_PATH;
    }

    public Path(ChildKey... segments) {
        this.pieces = (ChildKey[]) Arrays.copyOf(segments, segments.length);
        this.start = 0;
        this.end = segments.length;
        for (ChildKey name : segments) {
            if (!$assertionsDisabled && name == null) {
                throw new AssertionError("Can't construct a path with a null value!");
            }
        }
    }

    public Path(List<String> segments) {
        this.pieces = new ChildKey[segments.size()];
        int i = 0;
        for (String segment : segments) {
            this.pieces[i] = ChildKey.fromString(segment);
            i++;
        }
        this.start = 0;
        this.end = segments.size();
    }

    public Path(String pathString) {
        int j;
        String[] segments = pathString.split("/", -1);
        int count = 0;
        for (String segment : segments) {
            if (segment.length() > 0) {
                count++;
            }
        }
        this.pieces = new ChildKey[count];
        int length = segments.length;
        int i = 0;
        int j2 = 0;
        while (i < length) {
            String segment2 = segments[i];
            if (segment2.length() > 0) {
                j = j2 + 1;
                this.pieces[j2] = ChildKey.fromString(segment2);
            } else {
                j = j2;
            }
            i++;
            j2 = j;
        }
        this.start = 0;
        this.end = this.pieces.length;
    }

    private Path(ChildKey[] pieces, int start, int end) {
        this.pieces = pieces;
        this.start = start;
        this.end = end;
    }

    public Path child(Path path) {
        int newSize = size() + path.size();
        ChildKey[] newPieces = new ChildKey[newSize];
        System.arraycopy(this.pieces, this.start, newPieces, 0, size());
        System.arraycopy(path.pieces, path.start, newPieces, size(), path.size());
        return new Path(newPieces, 0, newSize);
    }

    public Path child(ChildKey child) {
        int size = size();
        ChildKey[] newPieces = new ChildKey[size + 1];
        System.arraycopy(this.pieces, this.start, newPieces, 0, size);
        newPieces[size] = child;
        return new Path(newPieces, 0, size + 1);
    }

    public String toString() {
        if (isEmpty()) {
            return "/";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = this.start; i < this.end; i++) {
            builder.append("/");
            builder.append(this.pieces[i].asString());
        }
        return builder.toString();
    }

    public String wireFormat() {
        if (isEmpty()) {
            return "/";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = this.start; i < this.end; i++) {
            if (i > this.start) {
                builder.append("/");
            }
            builder.append(this.pieces[i].asString());
        }
        return builder.toString();
    }

    public List<String> asList() {
        List<String> result = new ArrayList<>(size());
        Iterator<ChildKey> it = iterator();
        while (it.hasNext()) {
            ChildKey key = it.next();
            result.add(key.asString());
        }
        return result;
    }

    public ChildKey getFront() {
        if (isEmpty()) {
            return null;
        }
        return this.pieces[this.start];
    }

    public Path popFront() {
        int newStart = this.start;
        if (!isEmpty()) {
            newStart++;
        }
        return new Path(this.pieces, newStart, this.end);
    }

    public Path getParent() {
        if (isEmpty()) {
            return null;
        }
        return new Path(this.pieces, this.start, this.end - 1);
    }

    public ChildKey getBack() {
        if (isEmpty()) {
            return null;
        }
        return this.pieces[this.end - 1];
    }

    public boolean isEmpty() {
        return this.start >= this.end;
    }

    public int size() {
        return this.end - this.start;
    }

    @Override // java.lang.Iterable
    public Iterator<ChildKey> iterator() {
        return new Iterator<ChildKey>() { // from class: com.google.firebase.database.core.Path.1
            int offset;

            {
                Path.this = this;
                this.offset = Path.this.start;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.offset < Path.this.end;
            }

            @Override // java.util.Iterator
            public ChildKey next() {
                if (hasNext()) {
                    ChildKey child = Path.this.pieces[this.offset];
                    this.offset++;
                    return child;
                }
                throw new NoSuchElementException("No more elements.");
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Can't remove component from immutable Path!");
            }
        };
    }

    public boolean contains(Path other) {
        if (size() > other.size()) {
            return false;
        }
        int i = this.start;
        int j = other.start;
        while (i < this.end) {
            if (!this.pieces[i].equals(other.pieces[j])) {
                return false;
            }
            i++;
            j++;
        }
        return true;
    }

    public boolean equals(Object other) {
        if (other instanceof Path) {
            if (this == other) {
                return true;
            }
            Path otherPath = (Path) other;
            if (size() == otherPath.size()) {
                int i = this.start;
                for (int j = otherPath.start; i < this.end && j < otherPath.end; j++) {
                    if (!this.pieces[i].equals(otherPath.pieces[j])) {
                        return false;
                    }
                    i++;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = 0;
        for (int i = this.start; i < this.end; i++) {
            hashCode = (hashCode * 37) + this.pieces[i].hashCode();
        }
        return hashCode;
    }

    @Override // java.lang.Comparable
    public int compareTo(Path other) {
        int i = this.start;
        int j = other.start;
        while (i < this.end && j < other.end) {
            int comp = this.pieces[i].compareTo(other.pieces[j]);
            if (comp == 0) {
                i++;
                j++;
            } else {
                return comp;
            }
        }
        if (i == this.end && j == other.end) {
            return 0;
        }
        if (i == this.end) {
            return -1;
        }
        return 1;
    }
}
