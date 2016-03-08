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

    public TimePickerFragment() {
        // Do nothing
    }

    public TimePickerFragment(OnTimeSetListener listener) {
        timeSetListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();


        TimePickerDialog returnTimePicker = new TimePickerDialog(getActivity(), timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        return returnTimePicker;
    }
}
