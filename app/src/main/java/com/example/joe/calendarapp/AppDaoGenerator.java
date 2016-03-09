package com.example.joe.calendarapp;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.DaoGenerator;

public class AppDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.daogenerator");
        Entity event = schema.addEntity("Event");
        event.addIdProperty();
        event.addStringProperty("start_date");
        event.addStringProperty("start_time");
        event.addStringProperty("name");
        event.addStringProperty("end_date");
        event.addStringProperty("end_time");

        new DaoGenerator().generateAll(schema, "./app/src/main/java/");
    }
}
