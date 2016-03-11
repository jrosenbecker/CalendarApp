package com.example.joe.calendarapp;


import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;

import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.joe.calendarapp.dialogfragments.DatePickerFragment;
import com.example.joe.calendarapp.dialogfragments.TimePickerFragment;
import com.utils.DBUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    private TextView startDateTextView;
    private TextView startTimeTextView;
    private TextView endDateTextView;
    private TextView endTimeTextView;

    private EditText nameInput;

    private Calendar startTime;
    private Calendar endTime;

    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dateFormat;

    /**
     * Runs when the view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // Set up the EditText element so that the enter key will get rid of the on-screen keyboard
        nameInput = (EditText) findViewById(R.id.name_input);
        nameInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    nameInput.clearFocus();
                    return true;
                }
                return false;
            }
        });

        // Initialize all of the TextViews
        startDateTextView = (TextView) findViewById(R.id.start_date_picker);
        startTimeTextView = (TextView) findViewById(R.id.start_time_picker);
        endDateTextView = (TextView) findViewById(R.id.end_date_picker);
        endTimeTextView = (TextView) findViewById(R.id.end_time_picker);

        // Set the listeners for all of the TextViews
        startDateTextView.setOnClickListener(startDateClickListener);
        startTimeTextView.setOnClickListener(startTimeClickListener);
        endDateTextView.setOnClickListener(endDateClickListener);
        endTimeTextView.setOnClickListener(endTimeClickListener);

        // Extract the date from the previous activity (passed via an Intent)
        Bundle extras = getIntent().getExtras();
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        startTime = (Calendar) extras.get("date");

        // Sets the start and end variables to default values
        startTime.set(Calendar.HOUR_OF_DAY, 8);
        startTime.set(Calendar.MINUTE, 0);

        endTime.setTimeInMillis(startTime.getTimeInMillis());
        endTime.add(Calendar.HOUR, 1);

        // Display the times in the TextViews
        dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        startDateTextView.setText(dateFormat.format(startTime.getTime()));
        endDateTextView.setText(dateFormat.format(endTime.getTime()));

        timeFormat = new SimpleDateFormat("hh:mm aa");
        startTimeTextView.setText(timeFormat.format(startTime.getTime()));
        endTimeTextView.setText(timeFormat.format(endTime.getTime()));

    }

    /**
     * Listener for the start date TextView
     */
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

    /**
     * Listener for the start time TextView
     */
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

    /**
     * Listener for the end date TextView
     */
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

    /**
     * Listener for the start time TextView
     */
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

    /**
     * Updates the text views if the Calendar variables change
     */
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
        if(nameInput.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Event name field must not be blank", Toast.LENGTH_LONG).show();
        } else {
            DBUtility.saveEvent(getApplicationContext(), nameInput.getText().toString(), startTime, endTime);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("start_date", startTime);
            setResult(Activity.RESULT_OK, resultIntent);
            Toast.makeText(getApplicationContext(), "Successfully added event!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onCancelClick(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
