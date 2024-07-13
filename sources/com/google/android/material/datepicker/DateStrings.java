package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class DateStrings {
    private DateStrings() {
    }

    public static String getYearMonth(Context context, long timeInMillis) {
        long offsetMillis = TimeZone.getDefault().getOffset(timeInMillis);
        return DateUtils.formatDateTime(context, timeInMillis - offsetMillis, 36);
    }

    public static String getYearMonthDay(long timeInMillis) {
        return getYearMonthDay(timeInMillis, Locale.getDefault());
    }

    static String getYearMonthDay(long timeInMillis, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getYearAbbrMonthDayFormat(locale).format(new Date(timeInMillis)) : UtcDates.getMediumFormat(locale).format(new Date(timeInMillis));
    }

    static String getMonthDay(long timeInMillis) {
        return getMonthDay(timeInMillis, Locale.getDefault());
    }

    static String getMonthDay(long timeInMillis, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getAbbrMonthDayFormat(locale).format(new Date(timeInMillis)) : UtcDates.getMediumNoYear(locale).format(new Date(timeInMillis));
    }

    public static String getMonthDayOfWeekDay(long timeInMillis) {
        return getMonthDayOfWeekDay(timeInMillis, Locale.getDefault());
    }

    static String getMonthDayOfWeekDay(long timeInMillis, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getAbbrMonthWeekdayDayFormat(locale).format(new Date(timeInMillis)) : UtcDates.getFullFormat(locale).format(new Date(timeInMillis));
    }

    public static String getYearMonthDayOfWeekDay(long timeInMillis) {
        return getYearMonthDayOfWeekDay(timeInMillis, Locale.getDefault());
    }

    static String getYearMonthDayOfWeekDay(long timeInMillis, Locale locale) {
        return Build.VERSION.SDK_INT >= 24 ? UtcDates.getYearAbbrMonthWeekdayDayFormat(locale).format(new Date(timeInMillis)) : UtcDates.getFullFormat(locale).format(new Date(timeInMillis));
    }

    public static String getDateString(long timeInMillis) {
        return getDateString(timeInMillis, null);
    }

    static String getDateString(long timeInMillis, @Nullable SimpleDateFormat userDefinedDateFormat) {
        Calendar currentCalendar = UtcDates.getTodayCalendar();
        Calendar calendarDate = UtcDates.getUtcCalendar();
        calendarDate.setTimeInMillis(timeInMillis);
        if (userDefinedDateFormat != null) {
            Date date = new Date(timeInMillis);
            return userDefinedDateFormat.format(date);
        } else if (currentCalendar.get(1) == calendarDate.get(1)) {
            return getMonthDay(timeInMillis);
        } else {
            return getYearMonthDay(timeInMillis);
        }
    }

    public static Pair<String, String> getDateRangeString(@Nullable Long start, @Nullable Long end) {
        return getDateRangeString(start, end, null);
    }

    static Pair<String, String> getDateRangeString(@Nullable Long start, @Nullable Long end, @Nullable SimpleDateFormat userDefinedDateFormat) {
        if (start == null && end == null) {
            return Pair.create(null, null);
        }
        if (start == null) {
            return Pair.create(null, getDateString(end.longValue(), userDefinedDateFormat));
        }
        if (end == null) {
            return Pair.create(getDateString(start.longValue(), userDefinedDateFormat), null);
        }
        Calendar currentCalendar = UtcDates.getTodayCalendar();
        Calendar startCalendar = UtcDates.getUtcCalendar();
        startCalendar.setTimeInMillis(start.longValue());
        Calendar endCalendar = UtcDates.getUtcCalendar();
        endCalendar.setTimeInMillis(end.longValue());
        if (userDefinedDateFormat != null) {
            Date startDate = new Date(start.longValue());
            Date endDate = new Date(end.longValue());
            return Pair.create(userDefinedDateFormat.format(startDate), userDefinedDateFormat.format(endDate));
        } else if (startCalendar.get(1) == endCalendar.get(1)) {
            if (startCalendar.get(1) == currentCalendar.get(1)) {
                return Pair.create(getMonthDay(start.longValue(), Locale.getDefault()), getMonthDay(end.longValue(), Locale.getDefault()));
            }
            return Pair.create(getMonthDay(start.longValue(), Locale.getDefault()), getYearMonthDay(end.longValue(), Locale.getDefault()));
        } else {
            return Pair.create(getYearMonthDay(start.longValue(), Locale.getDefault()), getYearMonthDay(end.longValue(), Locale.getDefault()));
        }
    }
}
