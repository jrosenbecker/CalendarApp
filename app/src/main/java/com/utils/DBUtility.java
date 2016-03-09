package com.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.daogenerator.DaoMaster;
import com.daogenerator.DaoSession;
import com.daogenerator.Event;
import com.daogenerator.EventDao;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by JoeLaptop on 3/9/2016.
 */
public class DBUtility {
    private static DaoMaster.DevOpenHelper dbHelper;
    private static SQLiteDatabase database;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static EventDao eventDao;
    private static List<Event> eventList;

    public static void initDatabase(Context context)
    {
        dbHelper = new DaoMaster.DevOpenHelper(context, "ORM.sqlite", null);
        database = dbHelper.getWritableDatabase();

        daoMaster = new DaoMaster(database);

        daoMaster.createAllTables(database, true);
        daoSession = daoMaster.newSession();
        eventDao = daoSession.getEventDao();

        QueryBuilder<Event> qb = eventDao.queryBuilder();
        qb.where(EventDao.Properties.Id.isNotNull());
        eventList = qb.list();
        if(eventList == null)
        {
            closeReopenDatabase(context);
        }
    }

    public static void closeReopenDatabase(Context context) {
        closeDatabase();
        dbHelper = new DaoMaster.DevOpenHelper(context, "ORM.sqlite", null);
        database = dbHelper.getWritableDatabase();

        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        eventDao = daoSession.getEventDao();
    }

    public static void closeDatabase() {
        daoSession.clear();
        database.close();
        dbHelper.close();
    }

    public static void saveEvent(Context context, String name, Calendar startDate, Calendar endDate) {
        Random rand = new Random();
        String startDateString = startDate.get(Calendar.YEAR) + "-" + startDate.get(Calendar.MONTH) + "-" + startDate.get(Calendar.DAY_OF_MONTH);
        String endDateString = endDate.get(Calendar.YEAR) + "-" + endDate.get(Calendar.MONTH) + "-" + endDate.get(Calendar.DAY_OF_MONTH);

        Event event = new Event(rand.nextLong(), startDateString,
                startDate.get(Calendar.HOUR_OF_DAY) + ":" + startDate.get(Calendar.MINUTE),
                name, endDateString, endDate.get(Calendar.HOUR_OF_DAY) + ":" + endDate.get(Calendar.MINUTE));
        eventDao.insert(event);
        closeReopenDatabase(context);
    }

    public static List<Event> getEvents(Calendar date) {
        QueryBuilder<Event> qb = eventDao.queryBuilder();
        String dateString = date.get(Calendar.YEAR) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.DAY_OF_MONTH);
        qb.where(EventDao.Properties.Start_date.eq(dateString));
        return qb.list();
    }
}
