package com.example.joe.calendarapp;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.utils.DBUtility;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private long selectedDate;

    /**
     * Creates the calendar view and sets the tool bar
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(getDateChangeListener());
        selectedDate = calendarView.getDate();
        DBUtility.initDatabase(getApplicationContext());
    }

    /**
     * Creates the options menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles when an options item is selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            // Opens the DayActivity screen and passes in the selected day
            case R.id.view_events:
                Intent intent = new Intent(getApplicationContext(), DayActivity.class);
                intent.putExtra("DATE", selectedDate);
                startActivity(intent);
                return true;
            // Clears out the database if "Delete all events" is selected
            case R.id.delete_all_events_option:
                DBUtility.clean();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Listener  which changes the Calendar variable for the date every time
     * the date changes
     * @return
     */
    private CalendarView.OnDateChangeListener getDateChangeListener() {
        return new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDate = c.getTimeInMillis();
            }
        };
    }

}
