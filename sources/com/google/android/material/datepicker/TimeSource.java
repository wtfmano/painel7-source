package com.google.android.material.datepicker;

import androidx.annotation.Nullable;
import java.util.Calendar;
import java.util.TimeZone;

/* loaded from: classes.dex */
class TimeSource {
    private static final TimeSource SYSTEM_TIME_SOURCE = new TimeSource(null, null);
    @Nullable
    private final Long fixedTimeMs;
    @Nullable
    private final TimeZone fixedTimeZone;

    private TimeSource(@Nullable Long fixedTimeMs, @Nullable TimeZone fixedTimeZone) {
        this.fixedTimeMs = fixedTimeMs;
        this.fixedTimeZone = fixedTimeZone;
    }

    public static TimeSource system() {
        return SYSTEM_TIME_SOURCE;
    }

    static TimeSource fixed(long epochMs, @Nullable TimeZone timeZone) {
        return new TimeSource(Long.valueOf(epochMs), timeZone);
    }

    static TimeSource fixed(long epochMs) {
        return new TimeSource(Long.valueOf(epochMs), null);
    }

    public Calendar now() {
        return now(this.fixedTimeZone);
    }

    Calendar now(@Nullable TimeZone timeZone) {
        Calendar calendar = timeZone == null ? Calendar.getInstance() : Calendar.getInstance(timeZone);
        if (this.fixedTimeMs != null) {
            calendar.setTimeInMillis(this.fixedTimeMs.longValue());
        }
        return calendar;
    }
}
