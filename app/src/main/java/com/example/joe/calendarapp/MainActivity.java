package com.example.joe.calendarapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.daogenerator.DaoMaster;
import com.daogenerator.DaoSession;
import com.daogenerator.EventDao;
import com.utils.DBUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    private long selectedDate;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.view_events:
                Intent intent = new Intent(getApplicationContext(), DayActivity.class);
                intent.putExtra("DATE", selectedDate);
                startActivity(intent);
                return true;
            case R.id.delete_all_events_option:
                DBUtility.clean();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


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
