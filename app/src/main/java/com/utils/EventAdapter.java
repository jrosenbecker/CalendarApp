package com.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daogenerator.Event;
import com.example.joe.calendarapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Joe on 3/10/2016.
 */
public class EventAdapter extends BaseAdapter {
    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, List<Event> events) {
        this.context = context;
        eventList = events;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(R.layout.single_row_layout, parent, false);
        TextView eventNameTextView = (TextView) row.findViewById(R.id.list_item_name);
        TextView eventTimeTextView = (TextView) row.findViewById(R.id.list_item_time);

        Event event = eventList.get(position);
        eventNameTextView.setText(event.getName());

        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");
        String startTime = format.format(event.getStart());
        String endTime = format.format(event.getEnd());
        eventTimeTextView.setText("Starts " + startTime + "\n" + "Ends " + endTime);
        return row;
    }
}
