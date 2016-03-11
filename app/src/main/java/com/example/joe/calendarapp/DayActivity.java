package com.example.joe.calendarapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daogenerator.Event;
import com.utils.DBUtility;
import com.utils.EventAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DayActivity extends AppCompatActivity {

    private Calendar date;
    private TextView dateTextView;
    private List<Event> eventList;
    private EventAdapter adapter;
    private ListView eventListView;

    private static final SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
    private static final int ADD_ACTIVITY_RESULT = 1;

    /**
     * Initializes View elements and sets listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        // Initializes the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.day_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        // Sets the listeners and finds the views
        dateTextView = (TextView) findViewById(R.id.dateText);
        eventListView = (ListView) findViewById(R.id.listView);
        eventListView.setOnItemClickListener(onItemClicked);

        // Get the date data that was passed in
        Bundle extras = getIntent().getExtras();
        long dateAsLong = (long) extras.get("DATE");
        date = Calendar.getInstance();
        date.setTimeInMillis(dateAsLong);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);

        // Initizlizes the database (if it hasn't been initialized)
        DBUtility.initDatabase(getApplicationContext());

        // Prints the correct information to each TextView/ListView
        refreshActivity();


    }

    /**
     * Runs after the options menu is created
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day, menu);
        return true;
    }

    /**
     * Handles the selecting of options views
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            // Add event option is selected
            case R.id.add_event:
                // Starts an AddEventActivity and passes it the currently selected date
                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                intent.putExtra("date", date);
                startActivityForResult(intent, ADD_ACTIVITY_RESULT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when the AddEventActivity returns
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_ACTIVITY_RESULT) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // If the user saved a new event for a different day, switch to that day and
                    // display the events for that day
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

    /**
     * Refreshes all of the events in the Event list
     */
    private void refreshActivity() {
        dateTextView.setText(format.format(date.getTime()));
        eventList = new ArrayList<Event>();

        adapter = new EventAdapter(DayActivity.this, eventList);
        eventListView.setAdapter(adapter);

        List<Event> events = DBUtility.getEvents(date);
        for(Event e : events)
        {
            eventList.add(e);
        }

        adapter.notifyDataSetChanged();

    }

    /**
     * Moves to the next day
     * @param v
     */
    public void nextButtonClicked(View v) {
        date.add(Calendar.DAY_OF_MONTH, 1);
        refreshActivity();
    }

    /**
     * Moves to the previous day
     * @param v
     */
    public void prevButtonClicked(View v) {
        date.add(Calendar.DAY_OF_MONTH, -1);
        refreshActivity();
    }

    /**
     * Listener for when a list item is clicked. Displays a delete dialog and deletes based on the
     * user's input
     */
    private AdapterView.OnItemClickListener onItemClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Event clickedEvent = (Event) adapter.getItem(position);
            AlertDialog.Builder dialog = new AlertDialog.Builder(DayActivity.this);
            dialog.setTitle("Delete Event");
            dialog.setMessage("Would you like to delete the following event:\n" + clickedEvent.getName() + "?");
            dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DBUtility.deleteEvent(clickedEvent);
                    refreshActivity();
                }
            }).setNegativeButton("Cancel", null).show();
        }
    };
}
