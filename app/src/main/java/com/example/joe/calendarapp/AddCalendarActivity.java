package com.example.joe.calendarapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddCalendarActivity extends AppCompatActivity {
    private EditText calendarNameTextBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_calendar_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        calendarNameTextBox = (EditText) findViewById(R.id.calendar_name_text_box);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_calendar, menu);

        return true;
    }

    public void clickSave(View view)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Add", calendarNameTextBox.getText().toString());
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void clickCancel(View view) {
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }
}
