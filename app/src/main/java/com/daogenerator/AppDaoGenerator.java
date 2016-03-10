package com.daogenerator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.DaoGenerator;

public class AppDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.daogenerator");
        Entity event = schema.addEntity("Event");
        event.addIdProperty();

        event.addStringProperty("name");
        event.addDateProperty("start");
        event.addDateProperty("end");

        new DaoGenerator().generateAll(schema, "./app/src/main/java/");
    }
}
