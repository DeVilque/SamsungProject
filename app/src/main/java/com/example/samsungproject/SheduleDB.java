package com.example.samsungproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SheduleDB {
    public static final String TABLE_NAME = "shedule";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_START = "start_time";
    public static final String COLUMN_FINISH = "finish_time";
    public static final String COLUMN_DAY = "day_id";

    private final SQLiteDatabase database;

    public SheduleDB(Context context) {
        OpenHelper oh = new OpenHelper(context);
        database = oh.getWritableDatabase();
    }

    public long insert(Loader.Lesson l) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, l.name);
        cv.put(COLUMN_START, l.start_time);
        cv.put(COLUMN_FINISH, l.finish_time);
        cv.put(COLUMN_DAY, l.day_id);

        return database.insert(SheduleDB.TABLE_NAME, null, cv);
    }

    public long deleteAll() {
        return database.delete(TABLE_NAME, null, null);
    }

    public ArrayList<Loader.Lesson> getShedule() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Loader.Lesson> shedule = new ArrayList<>();

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String start_time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START));
                String finish_time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FINISH));
                int day_id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DAY));

                shedule.add(new Loader.Lesson(id, name, start_time, finish_time, day_id));
            } while (cursor.moveToNext());
        }
        return shedule;
    }
}
