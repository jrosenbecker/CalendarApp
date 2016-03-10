package com.example.joe.calendarapp.dialogfragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import java.util.Calendar;


/**
 * Created by Joe on 3/8/2016.
 */
public class TimePickerFragment extends DialogFragment {

    private OnTimeSetListener timeSetListener;
    private Calendar initTime;

    public TimePickerFragment() {
        // Do nothing
    }

    
    public TimePickerFragment(OnTimeSetListener listener, Calendar time) {
        timeSetListener = listener;
        initTime = Calendar.getInstance();
        initTime.setTimeInMillis(time.getTimeInMillis());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TimePickerDialog returnTimePicker = new TimePickerDialog(getActivity(), timeSetListener, initTime.get(Calendar.HOUR_OF_DAY), initTime.get(Calendar.MINUTE), false);

        return returnTimePicker;
    }
}
