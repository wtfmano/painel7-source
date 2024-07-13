package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* loaded from: classes.dex */
public final class Month implements Comparable<Month>, Parcelable {
    public static final Parcelable.Creator<Month> CREATOR = new Parcelable.Creator<Month>() { // from class: com.google.android.material.datepicker.Month.1
        @Override // android.os.Parcelable.Creator
        @NonNull
        public Month createFromParcel(@NonNull Parcel source) {
            int year = source.readInt();
            int month = source.readInt();
            return Month.create(year, month);
        }

        @Override // android.os.Parcelable.Creator
        @NonNull
        public Month[] newArray(int size) {
            return new Month[size];
        }
    };
    final int daysInMonth;
    final int daysInWeek;
    @NonNull
    private final Calendar firstOfMonth;
    @Nullable
    private String longName;
    final int month;
    final long timeInMillis;
    final int year;

    private Month(@NonNull Calendar rawCalendar) {
        rawCalendar.set(5, 1);
        this.firstOfMonth = UtcDates.getDayCopy(rawCalendar);
        this.month = this.firstOfMonth.get(2);
        this.year = this.firstOfMonth.get(1);
        this.daysInWeek = this.firstOfMonth.getMaximum(7);
        this.daysInMonth = this.firstOfMonth.getActualMaximum(5);
        this.timeInMillis = this.firstOfMonth.getTimeInMillis();
    }

    @NonNull
    public static Month create(long timeInMillis) {
        Calendar calendar = UtcDates.getUtcCalendar();
        calendar.setTimeInMillis(timeInMillis);
        return new Month(calendar);
    }

    @NonNull
    public static Month create(int year, int month) {
        Calendar calendar = UtcDates.getUtcCalendar();
        calendar.set(1, year);
        calendar.set(2, month);
        return new Month(calendar);
    }

    @NonNull
    public static Month current() {
        return new Month(UtcDates.getTodayCalendar());
    }

    public int daysFromStartOfWeekToFirstOfMonth() {
        int difference = this.firstOfMonth.get(7) - this.firstOfMonth.getFirstDayOfWeek();
        if (difference < 0) {
            return difference + this.daysInWeek;
        }
        return difference;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Month) {
            Month that = (Month) o;
            return this.month == that.month && this.year == that.year;
        }
        return false;
    }

    public int hashCode() {
        Object[] hashedFields = {Integer.valueOf(this.month), Integer.valueOf(this.year)};
        return Arrays.hashCode(hashedFields);
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull Month other) {
        return this.firstOfMonth.compareTo(other.firstOfMonth);
    }

    public int monthsUntil(@NonNull Month other) {
        if (this.firstOfMonth instanceof GregorianCalendar) {
            return ((other.year - this.year) * 12) + (other.month - this.month);
        }
        throw new IllegalArgumentException("Only Gregorian calendars are supported.");
    }

    public long getStableId() {
        return this.firstOfMonth.getTimeInMillis();
    }

    public long getDay(int day) {
        Calendar dayCalendar = UtcDates.getDayCopy(this.firstOfMonth);
        dayCalendar.set(5, day);
        return dayCalendar.getTimeInMillis();
    }

    public int getDayOfMonth(long date) {
        Calendar dayCalendar = UtcDates.getDayCopy(this.firstOfMonth);
        dayCalendar.setTimeInMillis(date);
        return dayCalendar.get(5);
    }

    @NonNull
    public Month monthsLater(int months) {
        Calendar laterMonth = UtcDates.getDayCopy(this.firstOfMonth);
        laterMonth.add(2, months);
        return new Month(laterMonth);
    }

    @NonNull
    public String getLongName(Context context) {
        if (this.longName == null) {
            this.longName = DateStrings.getYearMonth(context, this.firstOfMonth.getTimeInMillis());
        }
        return this.longName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.year);
        dest.writeInt(this.month);
    }
}
