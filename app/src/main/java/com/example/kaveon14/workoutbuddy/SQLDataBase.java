package com.example.kaveon14.workoutbuddy;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class SQLDataBase extends SQLiteOpenHelper {//ignore asnd learn sqlikte first

    //use this to open database
     //SQLiteDatabase db = SQLiteDatabase.openDatabase("/Users/kaveon14/sqlite/newDatabase.db", null, 0);

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WorkoutDatabase.db";

    public SQLDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    public void onDowngrde(SQLiteDatabase database,int oldVersion,int newVersion) {
        onUpgrade(database,oldVersion,newVersion);
    }





    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }

}
