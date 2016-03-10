package com.example.joe.calendarapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daogenerator.Event;
import com.utils.DBUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayActivity extends AppCompatActivity {

    private Calendar date;
    private TextView dateTextView;
    private List<String> eventList;
    private ArrayAdapter<String> adapter;
    private ListView eventListView;

    private static final SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
    private static final int ADD_ACTIVITY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Toolbar toolbar = (Toolbar) findViewById(R.id.day_toolbar);
        setSupportActionBar(toolbar);

        dateTextView = (TextView) findViewById(R.id.dateText);
        eventListView = (ListView) findViewById(R.id.listView);

        // Get the date data that was passed in
        Bundle extras = getIntent().getExtras();
        long dateAsLong = (long) extras.get("DATE");
        date = Calendar.getInstance();
        date.setTimeInMillis(dateAsLong);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);

        DBUtility.initDatabase(getApplicationContext());

        refreshActivity();


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
                intent.putExtra("date", date);
                startActivityForResult(intent, ADD_ACTIVITY_RESULT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_ACTIVITY_RESULT) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // TODO: Handle a date change from the activity
                    date.setTimeInMillis(((Calendar) data.getExtras().get("start_date")).getTimeInMillis());
                    date.set(Calendar.HOUR_OF_DAY, 0);
                    date.set(Calendar.MINUTE, 0);
                    refreshActivity();
                    break;
                case Activity.RESULT_CANCELED:
                    refreshActivity();
            }
        }
    }

    private void refreshActivity() {
        dateTextView.setText(format.format(date.getTime()));
        eventList = new ArrayList<String>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        eventListView.setAdapter(adapter);

        List<Event> events = DBUtility.getEvents(date);
        for(Event e : events)
        {
            eventList.add(e.getName());
        }

        adapter.notifyDataSetChanged();

    }
}
