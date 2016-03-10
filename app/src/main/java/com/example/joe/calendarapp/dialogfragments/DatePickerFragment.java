package com.example.joe.calendarapp.dialogfragments;


import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Joe on 3/7/2016.
 */
public class DatePickerFragment extends DialogFragment {

    private OnDateSetListener dateSetListener;
    Calendar initDate;

    public DatePickerFragment() {
        // Do nothing
    }

    public DatePickerFragment(OnDateSetListener listener, Calendar date) {
        dateSetListener = listener;
        initDate = Calendar.getInstance();
        initDate.setTimeInMillis(date.getTimeInMillis());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = initDate.get(Calendar.YEAR);
        int month = initDate.get(Calendar.MONTH);
        int day = initDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog returnDatePicker = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        returnDatePicker.getDatePicker().setCalendarViewShown(false);
        return returnDatePicker;
    }
}
