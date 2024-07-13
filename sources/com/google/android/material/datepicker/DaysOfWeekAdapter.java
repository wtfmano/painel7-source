package com.google.android.material.datepicker;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.R;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes.dex */
class DaysOfWeekAdapter extends BaseAdapter {
    private static final int CALENDAR_DAY_STYLE;
    private static final int NARROW_FORMAT = 4;
    @NonNull
    private final Calendar calendar = UtcDates.getUtcCalendar();
    private final int daysInWeek = this.calendar.getMaximum(7);
    private final int firstDayOfWeek = this.calendar.getFirstDayOfWeek();

    static {
        CALENDAR_DAY_STYLE = Build.VERSION.SDK_INT >= 26 ? 4 : 1;
    }

    @Override // android.widget.Adapter
    @Nullable
    public Integer getItem(int position) {
        if (position >= this.daysInWeek) {
            return null;
        }
        return Integer.valueOf(positionToDayOfWeek(position));
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return 0L;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.daysInWeek;
    }

    @Override // android.widget.Adapter
    @Nullable
    @SuppressLint({"WrongConstant"})
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView dayOfWeek = (TextView) convertView;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            dayOfWeek = (TextView) layoutInflater.inflate(R.layout.mtrl_calendar_day_of_week, parent, false);
        }
        this.calendar.set(7, positionToDayOfWeek(position));
        Locale locale = dayOfWeek.getResources().getConfiguration().locale;
        dayOfWeek.setText(this.calendar.getDisplayName(7, CALENDAR_DAY_STYLE, locale));
        dayOfWeek.setContentDescription(String.format(parent.getContext().getString(R.string.mtrl_picker_day_of_week_column_header), this.calendar.getDisplayName(7, 2, Locale.getDefault())));
        return dayOfWeek;
    }

    private int positionToDayOfWeek(int position) {
        int dayConstant = position + this.firstDayOfWeek;
        if (dayConstant > this.daysInWeek) {
            return dayConstant - this.daysInWeek;
        }
        return dayConstant;
    }
}
