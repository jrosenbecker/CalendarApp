package com.example.joe.calendarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DayActivity extends AppCompatActivity {

    private Calendar date;
    private TextView dateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        dateTextView = (TextView) findViewById(R.id.dateText);

        // Get the date data that was passed in
        Bundle extras = getIntent().getExtras();
        long dateAsLong = (long) extras.get("DATE");
        date = Calendar.getInstance();
        date.setTimeInMillis(dateAsLong);

        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        dateTextView.setText(format.format(date.getTime()));

    }
}
