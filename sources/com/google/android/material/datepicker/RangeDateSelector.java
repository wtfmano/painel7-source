package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Pair;
import androidx.core.util.Preconditions;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class RangeDateSelector implements DateSelector<Pair<Long, Long>> {
    public static final Parcelable.Creator<RangeDateSelector> CREATOR = new Parcelable.Creator<RangeDateSelector>() { // from class: com.google.android.material.datepicker.RangeDateSelector.3
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NonNull
        public RangeDateSelector createFromParcel(@NonNull Parcel source) {
            RangeDateSelector rangeDateSelector = new RangeDateSelector();
            rangeDateSelector.selectedStartItem = (Long) source.readValue(Long.class.getClassLoader());
            rangeDateSelector.selectedEndItem = (Long) source.readValue(Long.class.getClassLoader());
            return rangeDateSelector;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NonNull
        public RangeDateSelector[] newArray(int size) {
            return new RangeDateSelector[size];
        }
    };
    private String invalidRangeStartError;
    private final String invalidRangeEndError = " ";
    @Nullable
    private Long selectedStartItem = null;
    @Nullable
    private Long selectedEndItem = null;
    @Nullable
    private Long proposedTextStart = null;
    @Nullable
    private Long proposedTextEnd = null;

    @Override // com.google.android.material.datepicker.DateSelector
    public void select(long selection) {
        if (this.selectedStartItem == null) {
            this.selectedStartItem = Long.valueOf(selection);
        } else if (this.selectedEndItem == null && isValidRange(this.selectedStartItem.longValue(), selection)) {
            this.selectedEndItem = Long.valueOf(selection);
        } else {
            this.selectedEndItem = null;
            this.selectedStartItem = Long.valueOf(selection);
        }
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public boolean isSelectionComplete() {
        return (this.selectedStartItem == null || this.selectedEndItem == null || !isValidRange(this.selectedStartItem.longValue(), this.selectedEndItem.longValue())) ? false : true;
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public void setSelection(@NonNull Pair<Long, Long> selection) {
        if (selection.first != null && selection.second != null) {
            Preconditions.checkArgument(isValidRange(selection.first.longValue(), selection.second.longValue()));
        }
        this.selectedStartItem = selection.first == null ? null : Long.valueOf(UtcDates.canonicalYearMonthDay(selection.first.longValue()));
        this.selectedEndItem = selection.second != null ? Long.valueOf(UtcDates.canonicalYearMonthDay(selection.second.longValue())) : null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.material.datepicker.DateSelector
    @NonNull
    public Pair<Long, Long> getSelection() {
        return new Pair<>(this.selectedStartItem, this.selectedEndItem);
    }

    @Override // com.google.android.material.datepicker.DateSelector
    @NonNull
    public Collection<Pair<Long, Long>> getSelectedRanges() {
        if (this.selectedStartItem == null || this.selectedEndItem == null) {
            return new ArrayList();
        }
        ArrayList<Pair<Long, Long>> ranges = new ArrayList<>();
        Pair<Long, Long> range = new Pair<>(this.selectedStartItem, this.selectedEndItem);
        ranges.add(range);
        return ranges;
    }

    @Override // com.google.android.material.datepicker.DateSelector
    @NonNull
    public Collection<Long> getSelectedDays() {
        ArrayList<Long> selections = new ArrayList<>();
        if (this.selectedStartItem != null) {
            selections.add(this.selectedStartItem);
        }
        if (this.selectedEndItem != null) {
            selections.add(this.selectedEndItem);
        }
        return selections;
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public int getDefaultThemeResId(@NonNull Context context) {
        Resources res = context.getResources();
        DisplayMetrics display = res.getDisplayMetrics();
        int maximumDefaultFullscreenMinorAxis = res.getDimensionPixelSize(R.dimen.mtrl_calendar_maximum_default_fullscreen_minor_axis);
        int minorAxisPx = Math.min(display.widthPixels, display.heightPixels);
        int defaultThemeAttr = minorAxisPx > maximumDefaultFullscreenMinorAxis ? R.attr.materialCalendarTheme : R.attr.materialCalendarFullscreenTheme;
        return MaterialAttributes.resolveOrThrow(context, defaultThemeAttr, MaterialDatePicker.class.getCanonicalName());
    }

    @Override // com.google.android.material.datepicker.DateSelector
    @NonNull
    public String getSelectionDisplayString(@NonNull Context context) {
        Resources res = context.getResources();
        if (this.selectedStartItem == null && this.selectedEndItem == null) {
            return res.getString(R.string.mtrl_picker_range_header_unselected);
        }
        if (this.selectedEndItem == null) {
            return res.getString(R.string.mtrl_picker_range_header_only_start_selected, DateStrings.getDateString(this.selectedStartItem.longValue()));
        }
        if (this.selectedStartItem == null) {
            return res.getString(R.string.mtrl_picker_range_header_only_end_selected, DateStrings.getDateString(this.selectedEndItem.longValue()));
        }
        Pair<String, String> dateRangeStrings = DateStrings.getDateRangeString(this.selectedStartItem, this.selectedEndItem);
        return res.getString(R.string.mtrl_picker_range_header_selected, dateRangeStrings.first, dateRangeStrings.second);
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public int getDefaultTitleResId() {
        return R.string.mtrl_picker_range_header_title;
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public View onCreateTextInputView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle, CalendarConstraints constraints, @NonNull final OnSelectionChangedListener<Pair<Long, Long>> listener) {
        View root = layoutInflater.inflate(R.layout.mtrl_picker_text_input_date_range, viewGroup, false);
        final TextInputLayout startTextInput = (TextInputLayout) root.findViewById(R.id.mtrl_picker_text_input_range_start);
        final TextInputLayout endTextInput = (TextInputLayout) root.findViewById(R.id.mtrl_picker_text_input_range_end);
        EditText startEditText = startTextInput.getEditText();
        EditText endEditText = endTextInput.getEditText();
        if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
            startEditText.setInputType(17);
            endEditText.setInputType(17);
        }
        this.invalidRangeStartError = root.getResources().getString(R.string.mtrl_picker_invalid_range);
        SimpleDateFormat format = UtcDates.getTextInputFormat();
        if (this.selectedStartItem != null) {
            startEditText.setText(format.format(this.selectedStartItem));
            this.proposedTextStart = this.selectedStartItem;
        }
        if (this.selectedEndItem != null) {
            endEditText.setText(format.format(this.selectedEndItem));
            this.proposedTextEnd = this.selectedEndItem;
        }
        String formatHint = UtcDates.getTextInputHint(root.getResources(), format);
        startTextInput.setPlaceholderText(formatHint);
        endTextInput.setPlaceholderText(formatHint);
        startEditText.addTextChangedListener(new DateFormatTextWatcher(formatHint, format, startTextInput, constraints) { // from class: com.google.android.material.datepicker.RangeDateSelector.1
            @Override // com.google.android.material.datepicker.DateFormatTextWatcher
            void onValidDate(@Nullable Long day) {
                RangeDateSelector.this.proposedTextStart = day;
                RangeDateSelector.this.updateIfValidTextProposal(startTextInput, endTextInput, listener);
            }

            @Override // com.google.android.material.datepicker.DateFormatTextWatcher
            void onInvalidDate() {
                RangeDateSelector.this.proposedTextStart = null;
                RangeDateSelector.this.updateIfValidTextProposal(startTextInput, endTextInput, listener);
            }
        });
        endEditText.addTextChangedListener(new DateFormatTextWatcher(formatHint, format, endTextInput, constraints) { // from class: com.google.android.material.datepicker.RangeDateSelector.2
            @Override // com.google.android.material.datepicker.DateFormatTextWatcher
            void onValidDate(@Nullable Long day) {
                RangeDateSelector.this.proposedTextEnd = day;
                RangeDateSelector.this.updateIfValidTextProposal(startTextInput, endTextInput, listener);
            }

            @Override // com.google.android.material.datepicker.DateFormatTextWatcher
            void onInvalidDate() {
                RangeDateSelector.this.proposedTextEnd = null;
                RangeDateSelector.this.updateIfValidTextProposal(startTextInput, endTextInput, listener);
            }
        });
        ViewUtils.requestFocusAndShowKeyboard(startEditText);
        return root;
    }

    private boolean isValidRange(long start, long end) {
        return start <= end;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIfValidTextProposal(@NonNull TextInputLayout startTextInput, @NonNull TextInputLayout endTextInput, @NonNull OnSelectionChangedListener<Pair<Long, Long>> listener) {
        if (this.proposedTextStart == null || this.proposedTextEnd == null) {
            clearInvalidRange(startTextInput, endTextInput);
            listener.onIncompleteSelectionChanged();
        } else if (isValidRange(this.proposedTextStart.longValue(), this.proposedTextEnd.longValue())) {
            this.selectedStartItem = this.proposedTextStart;
            this.selectedEndItem = this.proposedTextEnd;
            listener.onSelectionChanged(getSelection());
        } else {
            setInvalidRange(startTextInput, endTextInput);
            listener.onIncompleteSelectionChanged();
        }
    }

    private void clearInvalidRange(@NonNull TextInputLayout start, @NonNull TextInputLayout end) {
        if (start.getError() != null && this.invalidRangeStartError.contentEquals(start.getError())) {
            start.setError(null);
        }
        if (end.getError() != null && " ".contentEquals(end.getError())) {
            end.setError(null);
        }
    }

    private void setInvalidRange(@NonNull TextInputLayout start, @NonNull TextInputLayout end) {
        start.setError(this.invalidRangeStartError);
        end.setError(" ");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeValue(this.selectedStartItem);
        dest.writeValue(this.selectedEndItem);
    }
}
