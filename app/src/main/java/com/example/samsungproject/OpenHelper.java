package com.example.samsungproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "app_database";
    public static final int DATABASE_VERSION = 1;

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + SheduleDB.TABLE_NAME + " (" +
                SheduleDB.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SheduleDB.COLUMN_NAME + " TEXT, " +
                SheduleDB.COLUMN_START + " TEXT, " +
                SheduleDB.COLUMN_FINISH + " TEXT, " +
                SheduleDB.COLUMN_DAY + " INTEGER);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SheduleDB.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
