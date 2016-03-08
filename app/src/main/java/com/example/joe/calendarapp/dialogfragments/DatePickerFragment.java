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

    public DatePickerFragment() {
        // Do nothing
    }

    public DatePickerFragment(OnDateSetListener listener) {
        dateSetListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog returnDatePicker = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        returnDatePicker.getDatePicker().setCalendarViewShown(false);
        return returnDatePicker;
    }
}
