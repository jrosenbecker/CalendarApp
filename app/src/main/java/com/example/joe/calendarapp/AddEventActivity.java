package com.example.joe.calendarapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class AddEventActivity extends AppCompatActivity {

    TextView startDateTextView;
    TextView startTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        startDateTextView = (TextView) findViewById(R.id.start_date_picker);
        startTimeTextView = (TextView) findViewById(R.id.start_time_picker);

        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getApplicationContext(), dateSetListener, 1994, 6, 17).show();

            }
        });
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            startDateTextView.setText(monthOfYear + "-" + dayOfMonth + "-" + year);
        }
    };


}
