package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.Iterator;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class MaterialCalendar<S> extends PickerFragment<S> {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    private static final String CURRENT_MONTH_KEY = "CURRENT_MONTH_KEY";
    private static final String GRID_SELECTOR_KEY = "GRID_SELECTOR_KEY";
    private static final int SMOOTH_SCROLL_MAX = 3;
    private static final String THEME_RES_ID_KEY = "THEME_RES_ID_KEY";
    @Nullable
    private CalendarConstraints calendarConstraints;
    private CalendarSelector calendarSelector;
    private CalendarStyle calendarStyle;
    @Nullable
    private Month current;
    @Nullable
    private DateSelector<S> dateSelector;
    private View dayFrame;
    private RecyclerView recyclerView;
    @StyleRes
    private int themeResId;
    private View yearFrame;
    private RecyclerView yearSelector;
    @VisibleForTesting
    static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
    @VisibleForTesting
    static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
    @VisibleForTesting
    static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
    @VisibleForTesting
    static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";

    /* loaded from: classes.dex */
    public enum CalendarSelector {
        DAY,
        YEAR
    }

    /* loaded from: classes.dex */
    public interface OnDayClickListener {
        void onDayClick(long j);
    }

    @NonNull
    public static <T> MaterialCalendar<T> newInstance(@NonNull DateSelector<T> dateSelector, @StyleRes int themeResId, @NonNull CalendarConstraints calendarConstraints) {
        MaterialCalendar<T> materialCalendar = new MaterialCalendar<>();
        Bundle args = new Bundle();
        args.putInt(THEME_RES_ID_KEY, themeResId);
        args.putParcelable(GRID_SELECTOR_KEY, dateSelector);
        args.putParcelable(CALENDAR_CONSTRAINTS_KEY, calendarConstraints);
        args.putParcelable(CURRENT_MONTH_KEY, calendarConstraints.getOpenAt());
        materialCalendar.setArguments(args);
        return materialCalendar;
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(THEME_RES_ID_KEY, this.themeResId);
        bundle.putParcelable(GRID_SELECTOR_KEY, this.dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, this.calendarConstraints);
        bundle.putParcelable(CURRENT_MONTH_KEY, this.current);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Bundle activeBundle = bundle == null ? getArguments() : bundle;
        this.themeResId = activeBundle.getInt(THEME_RES_ID_KEY);
        this.dateSelector = (DateSelector) activeBundle.getParcelable(GRID_SELECTOR_KEY);
        this.calendarConstraints = (CalendarConstraints) activeBundle.getParcelable(CALENDAR_CONSTRAINTS_KEY);
        this.current = (Month) activeBundle.getParcelable(CURRENT_MONTH_KEY);
    }

    @Override // androidx.fragment.app.Fragment
    @NonNull
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        int layout;
        int orientation;
        ContextThemeWrapper themedContext = new ContextThemeWrapper(getContext(), this.themeResId);
        this.calendarStyle = new CalendarStyle(themedContext);
        LayoutInflater themedInflater = layoutInflater.cloneInContext(themedContext);
        Month earliestMonth = this.calendarConstraints.getStart();
        if (MaterialDatePicker.isFullscreen(themedContext)) {
            layout = R.layout.mtrl_calendar_vertical;
            orientation = 1;
        } else {
            layout = R.layout.mtrl_calendar_horizontal;
            orientation = 0;
        }
        View root = themedInflater.inflate(layout, viewGroup, false);
        GridView daysHeader = (GridView) root.findViewById(R.id.mtrl_calendar_days_of_week);
        ViewCompat.setAccessibilityDelegate(daysHeader, new AccessibilityDelegateCompat() { // from class: com.google.android.material.datepicker.MaterialCalendar.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo(null);
            }
        });
        daysHeader.setAdapter((ListAdapter) new DaysOfWeekAdapter());
        daysHeader.setNumColumns(earliestMonth.daysInWeek);
        daysHeader.setEnabled(false);
        this.recyclerView = (RecyclerView) root.findViewById(R.id.mtrl_calendar_months);
        final int i = orientation;
        SmoothCalendarLayoutManager layoutManager = new SmoothCalendarLayoutManager(getContext(), orientation, false) { // from class: com.google.android.material.datepicker.MaterialCalendar.2
            @Override // androidx.recyclerview.widget.LinearLayoutManager
            protected void calculateExtraLayoutSpace(@NonNull RecyclerView.State state, @NonNull int[] ints) {
                if (i == 0) {
                    ints[0] = MaterialCalendar.this.recyclerView.getWidth();
                    ints[1] = MaterialCalendar.this.recyclerView.getWidth();
                    return;
                }
                ints[0] = MaterialCalendar.this.recyclerView.getHeight();
                ints[1] = MaterialCalendar.this.recyclerView.getHeight();
            }
        };
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
        MonthsPagerAdapter monthsPagerAdapter = new MonthsPagerAdapter(themedContext, this.dateSelector, this.calendarConstraints, new OnDayClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.3
            @Override // com.google.android.material.datepicker.MaterialCalendar.OnDayClickListener
            public void onDayClick(long day) {
                if (MaterialCalendar.this.calendarConstraints.getDateValidator().isValid(day)) {
                    MaterialCalendar.this.dateSelector.select(day);
                    Iterator<OnSelectionChangedListener<S>> it = MaterialCalendar.this.onSelectionChangedListeners.iterator();
                    while (it.hasNext()) {
                        OnSelectionChangedListener<S> listener = it.next();
                        listener.onSelectionChanged((S) MaterialCalendar.this.dateSelector.getSelection());
                    }
                    MaterialCalendar.this.recyclerView.getAdapter().notifyDataSetChanged();
                    if (MaterialCalendar.this.yearSelector != null) {
                        MaterialCalendar.this.yearSelector.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        });
        this.recyclerView.setAdapter(monthsPagerAdapter);
        int columns = themedContext.getResources().getInteger(R.integer.mtrl_calendar_year_selector_span);
        this.yearSelector = (RecyclerView) root.findViewById(R.id.mtrl_calendar_year_selector_frame);
        if (this.yearSelector != null) {
            this.yearSelector.setHasFixedSize(true);
            this.yearSelector.setLayoutManager(new GridLayoutManager((Context) themedContext, columns, 1, false));
            this.yearSelector.setAdapter(new YearGridAdapter(this));
            this.yearSelector.addItemDecoration(createItemDecoration());
        }
        if (root.findViewById(R.id.month_navigation_fragment_toggle) != null) {
            addActionsToMonthNavigation(root, monthsPagerAdapter);
        }
        if (!MaterialDatePicker.isFullscreen(themedContext)) {
            new PagerSnapHelper().attachToRecyclerView(this.recyclerView);
        }
        this.recyclerView.scrollToPosition(monthsPagerAdapter.getPosition(this.current));
        return root;
    }

    @NonNull
    private RecyclerView.ItemDecoration createItemDecoration() {
        return new RecyclerView.ItemDecoration() { // from class: com.google.android.material.datepicker.MaterialCalendar.4
            private final Calendar startItem = UtcDates.getUtcCalendar();
            private final Calendar endItem = UtcDates.getUtcCalendar();

            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                int right;
                if ((recyclerView.getAdapter() instanceof YearGridAdapter) && (recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
                    YearGridAdapter adapter = (YearGridAdapter) recyclerView.getAdapter();
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    for (Pair<Long, Long> range : MaterialCalendar.this.dateSelector.getSelectedRanges()) {
                        if (range.first != null && range.second != null) {
                            this.startItem.setTimeInMillis(range.first.longValue());
                            this.endItem.setTimeInMillis(range.second.longValue());
                            int firstHighlightPosition = adapter.getPositionForYear(this.startItem.get(1));
                            int lastHighlightPosition = adapter.getPositionForYear(this.endItem.get(1));
                            View firstView = layoutManager.findViewByPosition(firstHighlightPosition);
                            View lastView = layoutManager.findViewByPosition(lastHighlightPosition);
                            int firstRow = firstHighlightPosition / layoutManager.getSpanCount();
                            int lastRow = lastHighlightPosition / layoutManager.getSpanCount();
                            int row = firstRow;
                            while (row <= lastRow) {
                                int firstPositionInRow = row * layoutManager.getSpanCount();
                                View viewInRow = layoutManager.findViewByPosition(firstPositionInRow);
                                if (viewInRow != null) {
                                    int top = viewInRow.getTop() + MaterialCalendar.this.calendarStyle.year.getTopInset();
                                    int bottom = viewInRow.getBottom() - MaterialCalendar.this.calendarStyle.year.getBottomInset();
                                    int left = row == firstRow ? firstView.getLeft() + (firstView.getWidth() / 2) : 0;
                                    if (row == lastRow) {
                                        right = lastView.getLeft() + (lastView.getWidth() / 2);
                                    } else {
                                        right = recyclerView.getWidth();
                                    }
                                    canvas.drawRect(left, top, right, bottom, MaterialCalendar.this.calendarStyle.rangeFill);
                                }
                                row++;
                            }
                        }
                    }
                }
            }
        };
    }

    @Nullable
    public Month getCurrentMonth() {
        return this.current;
    }

    @Nullable
    public CalendarConstraints getCalendarConstraints() {
        return this.calendarConstraints;
    }

    public void setCurrentMonth(Month moveTo) {
        MonthsPagerAdapter adapter = (MonthsPagerAdapter) this.recyclerView.getAdapter();
        int moveToPosition = adapter.getPosition(moveTo);
        int distance = moveToPosition - adapter.getPosition(this.current);
        boolean jump = Math.abs(distance) > 3;
        boolean isForward = distance > 0;
        this.current = moveTo;
        if (jump && isForward) {
            this.recyclerView.scrollToPosition(moveToPosition - 3);
            postSmoothRecyclerViewScroll(moveToPosition);
        } else if (jump) {
            this.recyclerView.scrollToPosition(moveToPosition + 3);
            postSmoothRecyclerViewScroll(moveToPosition);
        } else {
            postSmoothRecyclerViewScroll(moveToPosition);
        }
    }

    @Override // com.google.android.material.datepicker.PickerFragment
    @Nullable
    public DateSelector<S> getDateSelector() {
        return this.dateSelector;
    }

    public CalendarStyle getCalendarStyle() {
        return this.calendarStyle;
    }

    @Px
    public static int getDayHeight(@NonNull Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height);
    }

    public void setSelector(CalendarSelector selector) {
        this.calendarSelector = selector;
        if (selector == CalendarSelector.YEAR) {
            this.yearSelector.getLayoutManager().scrollToPosition(((YearGridAdapter) this.yearSelector.getAdapter()).getPositionForYear(this.current.year));
            this.yearFrame.setVisibility(0);
            this.dayFrame.setVisibility(8);
        } else if (selector == CalendarSelector.DAY) {
            this.yearFrame.setVisibility(8);
            this.dayFrame.setVisibility(0);
            setCurrentMonth(this.current);
        }
    }

    void toggleVisibleSelector() {
        if (this.calendarSelector == CalendarSelector.YEAR) {
            setSelector(CalendarSelector.DAY);
        } else if (this.calendarSelector == CalendarSelector.DAY) {
            setSelector(CalendarSelector.YEAR);
        }
    }

    private void addActionsToMonthNavigation(@NonNull View root, @NonNull final MonthsPagerAdapter monthsPagerAdapter) {
        final MaterialButton monthDropSelect = (MaterialButton) root.findViewById(R.id.month_navigation_fragment_toggle);
        monthDropSelect.setTag(SELECTOR_TOGGLE_TAG);
        ViewCompat.setAccessibilityDelegate(monthDropSelect, new AccessibilityDelegateCompat() { // from class: com.google.android.material.datepicker.MaterialCalendar.5
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                String string;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                if (MaterialCalendar.this.dayFrame.getVisibility() == 0) {
                    string = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_year_selection);
                } else {
                    string = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_day_selection);
                }
                accessibilityNodeInfoCompat.setHintText(string);
            }
        });
        MaterialButton monthPrev = (MaterialButton) root.findViewById(R.id.month_navigation_previous);
        monthPrev.setTag(NAVIGATION_PREV_TAG);
        MaterialButton monthNext = (MaterialButton) root.findViewById(R.id.month_navigation_next);
        monthNext.setTag(NAVIGATION_NEXT_TAG);
        this.yearFrame = root.findViewById(R.id.mtrl_calendar_year_selector_frame);
        this.dayFrame = root.findViewById(R.id.mtrl_calendar_day_selector_frame);
        setSelector(CalendarSelector.DAY);
        monthDropSelect.setText(this.current.getLongName(root.getContext()));
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.6
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int currentItem;
                if (dx < 0) {
                    currentItem = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
                } else {
                    currentItem = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
                }
                MaterialCalendar.this.current = monthsPagerAdapter.getPageMonth(currentItem);
                monthDropSelect.setText(monthsPagerAdapter.getPageTitle(currentItem));
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    CharSequence announcementText = monthDropSelect.getText();
                    if (Build.VERSION.SDK_INT >= 16) {
                        recyclerView.announceForAccessibility(announcementText);
                    } else {
                        recyclerView.sendAccessibilityEvent(2048);
                    }
                }
            }
        });
        monthDropSelect.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MaterialCalendar.this.toggleVisibleSelector();
            }
        });
        monthNext.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int currentItem = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
                if (currentItem + 1 < MaterialCalendar.this.recyclerView.getAdapter().getItemCount()) {
                    MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.getPageMonth(currentItem + 1));
                }
            }
        });
        monthPrev.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int currentItem = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
                if (currentItem - 1 >= 0) {
                    MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.getPageMonth(currentItem - 1));
                }
            }
        });
    }

    private void postSmoothRecyclerViewScroll(final int position) {
        this.recyclerView.post(new Runnable() { // from class: com.google.android.material.datepicker.MaterialCalendar.10
            @Override // java.lang.Runnable
            public void run() {
                MaterialCalendar.this.recyclerView.smoothScrollToPosition(position);
            }
        });
    }

    @NonNull
    LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) this.recyclerView.getLayoutManager();
    }

    @Override // com.google.android.material.datepicker.PickerFragment
    public boolean addOnSelectionChangedListener(@NonNull OnSelectionChangedListener<S> listener) {
        return super.addOnSelectionChangedListener(listener);
    }
}
