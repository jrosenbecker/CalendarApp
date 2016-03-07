package com.daogenerator;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table EVENT.
 */
public class Event {

    private Long id;
    /** Not-null value. */
    private java.util.Date start_date;
    private String start_time;
    private String name;
    private java.util.Date end_date;
    private String end_time;

    public Event() {
    }

    public Event(Long id) {
        this.id = id;
    }

    public Event(Long id, java.util.Date start_date, String start_time, String name, java.util.Date end_date, String end_time) {
        this.id = id;
        this.start_date = start_date;
        this.start_time = start_time;
        this.name = name;
        this.end_date = end_date;
        this.end_time = end_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public java.util.Date getStart_date() {
        return start_date;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setStart_date(java.util.Date start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(java.util.Date end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

}
