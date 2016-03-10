package com.example.joe.calendarapp;


import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;

import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    TextView startDateTextView;
    TextView startTimeTextView;
    TextView endDateTextView;
    TextView endTimeTextView;

    private Calendar startTime;
    private Calendar endTime;

    SimpleDateFormat timeFormat;
    SimpleDateFormat dateFormat;

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

        Bundle extras = getIntent().getExtras();
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        startTime = (Calendar) extras.get("date");
        startTime.set(Calendar.HOUR_OF_DAY, 8);
        startTime.set(Calendar.MINUTE, 0);

        endTime.setTimeInMillis(startTime.getTimeInMillis());
        endTime.add(Calendar.HOUR, 1);

        dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        startDateTextView.setText(dateFormat.format(startTime.getTime()));
        endDateTextView.setText(dateFormat.format(endTime.getTime()));

        timeFormat = new SimpleDateFormat("hh:mm aa");
        startTimeTextView.setText(timeFormat.format(startTime.getTime()));
        endTimeTextView.setText(timeFormat.format(endTime.getTime()));

    }

    private OnDateSetListener startDateListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startTime.set(Calendar.YEAR, year);
            startTime.set(Calendar.MONTH, monthOfYear);
            startTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            if(endTime.before(startTime))
            {
                endTime.setTimeInMillis(startTime.getTimeInMillis());
                endTime.add(Calendar.HOUR_OF_DAY, 1);
            }
            updateTextViews();
        }
    };


    private OnTimeSetListener startTimeListener = new OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startTime.set(Calendar.MINUTE, minute);

            if(endTime.before(startTime))
            {
                endTime.setTimeInMillis(startTime.getTimeInMillis());
                endTime.add(Calendar.HOUR_OF_DAY, 1);
            }
            updateTextViews();
        }
    };

    private OnDateSetListener endDateListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endTime.set(Calendar.YEAR, year);
            endTime.set(Calendar.MONTH, monthOfYear);
            endTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            if(startTime.after(endTime))
            {
                startTime.setTimeInMillis(endTime.getTimeInMillis());
                startTime.add(Calendar.HOUR_OF_DAY, -1);
            }
            updateTextViews();
        }
    };

    private OnTimeSetListener endTimeListener = new OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            endTime.set(Calendar.MINUTE, minute);
            if(startTime.after(endTime))
            {
                startTime.setTimeInMillis(endTime.getTimeInMillis());
                startTime.add(Calendar.HOUR_OF_DAY, -1);
            }
            updateTextViews();
        }
    };

    private void updateTextViews() {
        startDateTextView.setText(dateFormat.format(startTime.getTime()));
        startTimeTextView.setText(timeFormat.format(startTime.getTime()));
        endDateTextView.setText(dateFormat.format(endTime.getTime()));
        endTimeTextView.setText(timeFormat.format(endTime.getTime()));
    }





    private View.OnClickListener startDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DialogFragment newFrag = new DatePickerFragment(startDateListener, startTime);
            newFrag.show(ft, "date_picker");
        }
    };

    private View.OnClickListener startTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DialogFragment newFrag = new TimePickerFragment(startTimeListener, startTime);
            newFrag.show(ft, "time_picker");
        }
    };

    private View.OnClickListener endDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DialogFragment newFrag = new DatePickerFragment(endDateListener, endTime);
            newFrag.show(ft, "date_picker");
        }
    };

    private View.OnClickListener endTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            TimePickerFragment newFrag = new TimePickerFragment(endTimeListener, endTime);
            newFrag.show(ft, "time_picker");
        }
    };

    public void onSubmitClick(View view) {
        // TODO: Add the newly created event to the database
        Intent resultIntent = new Intent();
        resultIntent.putExtra("start_date", startTime);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void onCancelClick(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
