package com.example.joe.calendarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.day_toolbar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_event:
                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
