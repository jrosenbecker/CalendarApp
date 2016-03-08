package com.example.joe.calendarapp;


import android.app.DatePickerDialog.OnDateSetListener;

import android.app.TimePickerDialog.OnTimeSetListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.joe.calendarapp.dialogfragments.DatePickerFragment;
import com.example.joe.calendarapp.dialogfragments.TimePickerFragment;

public class AddEventActivity extends AppCompatActivity {

    TextView startDateTextView;
    TextView startTimeTextView;
    TextView endDateTextView;
    TextView endTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        startDateTextView = (TextView) findViewById(R.id.start_date_picker);
        startTimeTextView = (TextView) findViewById(R.id.start_time_picker);
        endDateTextView = (TextView) findViewById(R.id.end_date_picker);
        endTimeTextView = (TextView) findViewById(R.id.end_time_picker);


        startDateTextView.setOnClickListener(startDateClickListener);
        startTimeTextView.setOnClickListener(startTimeClickListener);
        endDateTextView.setOnClickListener(endDateClickListener);
        endTimeTextView.setOnClickListener(endTimeClickListener);
    }

    private OnDateSetListener startDateListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDateTextView.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        }
    };


    private OnTimeSetListener startTimeListener = new OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startTimeTextView.setText(hourOfDay + ":" + minute);
        }
    };

    private OnDateSetListener endDateListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endDateTextView.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        }
    };

    private OnTimeSetListener endTimeListener = new OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            endTimeTextView.setText(hourOfDay + ":" + minute);
        }
    };





    private View.OnClickListener startDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DialogFragment newFrag = new DatePickerFragment(startDateListener);
            newFrag.show(ft, "date_picker");
        }
    };

    private View.OnClickListener startTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DialogFragment newFrag = new TimePickerFragment(startTimeListener);
            newFrag.show(ft, "time_picker");
        }
    };

    private View.OnClickListener endDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DialogFragment newFrag = new DatePickerFragment(endDateListener);
            newFrag.show(ft, "date_picker");
        }
    };

    private View.OnClickListener endTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DialogFragment newFrag = new TimePickerFragment(endTimeListener);
            newFrag.show(ft, "time_picker");
        }
    };
}
