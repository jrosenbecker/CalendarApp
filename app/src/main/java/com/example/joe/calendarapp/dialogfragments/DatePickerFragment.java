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
    private Calendar initDate;

    /**
     * Empty constructor
     */
    public DatePickerFragment() {
        // Do nothing
    }

    /**
     * Constructor. Sets the date and listener
     * @param listener
     * @param date
     */
    public DatePickerFragment(OnDateSetListener listener, Calendar date) {
        dateSetListener = listener;
        initDate = Calendar.getInstance();
        initDate.setTimeInMillis(date.getTimeInMillis());
    }

    /**
     * Brings up the date picker dialog
     * @param savedInstanceState
     * @return
     */
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
