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

import de.greenrobot.dao.query.DeleteQuery;
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

        Event event = new Event(rand.nextLong(), name, startDate.getTime(), endDate.getTime());
        eventDao.insert(event);
        closeReopenDatabase(context);
    }

    public static List<Event> getEvents(Calendar startDate) {
        QueryBuilder<Event> qb = eventDao.queryBuilder();
        Calendar endTime = Calendar.getInstance();

        // Create a calendar element to represent the end of the day (startDate already should be midnight)
        endTime.setTimeInMillis(startDate.getTimeInMillis());
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        qb.where(EventDao.Properties.Start.le(endTime.getTime()), EventDao.Properties.End.ge(startDate.getTime())).orderAsc(EventDao.Properties.Start);
        return qb.list();
    }

    public static void deleteEvent(Event e) {
        eventDao.delete(e);
    }

    public static void clean() {
        daoMaster.dropAllTables(database, true);
    }
}
