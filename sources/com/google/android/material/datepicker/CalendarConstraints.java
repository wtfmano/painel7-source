package com.google.android.material.datepicker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class CalendarConstraints implements Parcelable {
    public static final Parcelable.Creator<CalendarConstraints> CREATOR = new Parcelable.Creator<CalendarConstraints>() { // from class: com.google.android.material.datepicker.CalendarConstraints.1
        @Override // android.os.Parcelable.Creator
        @NonNull
        public CalendarConstraints createFromParcel(@NonNull Parcel source) {
            Month start = (Month) source.readParcelable(Month.class.getClassLoader());
            Month end = (Month) source.readParcelable(Month.class.getClassLoader());
            Month openAt = (Month) source.readParcelable(Month.class.getClassLoader());
            DateValidator validator = (DateValidator) source.readParcelable(DateValidator.class.getClassLoader());
            return new CalendarConstraints(start, end, validator, openAt);
        }

        @Override // android.os.Parcelable.Creator
        @NonNull
        public CalendarConstraints[] newArray(int size) {
            return new CalendarConstraints[size];
        }
    };
    @NonNull
    private final Month end;
    private final int monthSpan;
    @Nullable
    private Month openAt;
    @NonNull
    private final Month start;
    @NonNull
    private final DateValidator validator;
    private final int yearSpan;

    /* loaded from: classes.dex */
    public interface DateValidator extends Parcelable {
        boolean isValid(long j);
    }

    private CalendarConstraints(@NonNull Month start, @NonNull Month end, @NonNull DateValidator validator, @Nullable Month openAt) {
        this.start = start;
        this.end = end;
        this.openAt = openAt;
        this.validator = validator;
        if (openAt != null && start.compareTo(openAt) > 0) {
            throw new IllegalArgumentException("start Month cannot be after current Month");
        }
        if (openAt != null && openAt.compareTo(end) > 0) {
            throw new IllegalArgumentException("current Month cannot be after end Month");
        }
        this.monthSpan = start.monthsUntil(end) + 1;
        this.yearSpan = (end.year - start.year) + 1;
    }

    public boolean isWithinBounds(long date) {
        return this.start.getDay(1) <= date && date <= this.end.getDay(this.end.daysInMonth);
    }

    public DateValidator getDateValidator() {
        return this.validator;
    }

    @NonNull
    public Month getStart() {
        return this.start;
    }

    @NonNull
    public Month getEnd() {
        return this.end;
    }

    @Nullable
    public Month getOpenAt() {
        return this.openAt;
    }

    public void setOpenAt(@Nullable Month openAt) {
        this.openAt = openAt;
    }

    public int getMonthSpan() {
        return this.monthSpan;
    }

    public int getYearSpan() {
        return this.yearSpan;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof CalendarConstraints) {
            CalendarConstraints that = (CalendarConstraints) o;
            return this.start.equals(that.start) && this.end.equals(that.end) && ObjectsCompat.equals(this.openAt, that.openAt) && this.validator.equals(that.validator);
        }
        return false;
    }

    public int hashCode() {
        Object[] hashedFields = {this.start, this.end, this.openAt, this.validator};
        return Arrays.hashCode(hashedFields);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.start, 0);
        dest.writeParcelable(this.end, 0);
        dest.writeParcelable(this.openAt, 0);
        dest.writeParcelable(this.validator, 0);
    }

    public Month clamp(Month month) {
        if (month.compareTo(this.start) < 0) {
            return this.start;
        }
        if (month.compareTo(this.end) > 0) {
            return this.end;
        }
        return month;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private static final String DEEP_COPY_VALIDATOR_KEY = "DEEP_COPY_VALIDATOR_KEY";
        private long end;
        private Long openAt;
        private long start;
        private DateValidator validator;
        static final long DEFAULT_START = UtcDates.canonicalYearMonthDay(Month.create(1900, 0).timeInMillis);
        static final long DEFAULT_END = UtcDates.canonicalYearMonthDay(Month.create(2100, 11).timeInMillis);

        public Builder() {
            this.start = DEFAULT_START;
            this.end = DEFAULT_END;
            this.validator = DateValidatorPointForward.from(Long.MIN_VALUE);
        }

        public Builder(@NonNull CalendarConstraints clone) {
            this.start = DEFAULT_START;
            this.end = DEFAULT_END;
            this.validator = DateValidatorPointForward.from(Long.MIN_VALUE);
            this.start = clone.start.timeInMillis;
            this.end = clone.end.timeInMillis;
            this.openAt = Long.valueOf(clone.openAt.timeInMillis);
            this.validator = clone.validator;
        }

        @NonNull
        public Builder setStart(long month) {
            this.start = month;
            return this;
        }

        @NonNull
        public Builder setEnd(long month) {
            this.end = month;
            return this;
        }

        @NonNull
        public Builder setOpenAt(long month) {
            this.openAt = Long.valueOf(month);
            return this;
        }

        @NonNull
        public Builder setValidator(@NonNull DateValidator validator) {
            this.validator = validator;
            return this;
        }

        @NonNull
        public CalendarConstraints build() {
            Bundle deepCopyBundle = new Bundle();
            deepCopyBundle.putParcelable(DEEP_COPY_VALIDATOR_KEY, this.validator);
            return new CalendarConstraints(Month.create(this.start), Month.create(this.end), (DateValidator) deepCopyBundle.getParcelable(DEEP_COPY_VALIDATOR_KEY), this.openAt == null ? null : Month.create(this.openAt.longValue()));
        }
    }
}
