package com.example.kaveon14.workoutbuddy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.Workout;

import java.util.ArrayList;
import java.util.List;

public class DataBaseSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutDataBase.db";


    public DataBaseSQLiteHelper(Context context) {//this is a practice database will have to create a new one later
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Workout.CREATE_TABLE);
        setDefaultWorkouts(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int x, int y) {
        database.execSQL("DROP TABLE IF EXISTS "+Workout.TABLE_NAME);
        onCreate(database);
    }


    public void printDataBase() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = readableDatabase.query(
                Workout.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while(cursor.moveToNext()) {
            System.out.println("exercise: "+cursor.getString(1) + " sets: "+cursor.getString(2)
            + " reps: "+cursor.getString(3));
        }
        cursor.close();
    }

    private void setDefaultWorkouts(SQLiteDatabase database) {
        defaultWorkout1Values(database);
    }

    //TODO add values to all of these below

    private void defaultWorkout1Values(SQLiteDatabase database) {//Legs and Abs / refactor this code
        long itemId;//test data
        ContentValues values = new ContentValues();
        String workout_1 = Workout.COLUMN_EXERCISE_NAMES;
        String exSets = Workout.COLUMN_EXERCISE_SETS;
        String exReps = Workout.COLUMN_EXERCISE_REPS;

        values.put(workout_1,"bench");
        values.put(exSets,"1");
        values.put(exReps,"5");

        itemId = database.insert(Workout.TABLE_NAME,null,values);

        values.put(workout_1,"squat");
        values.put(exSets,"2");
        values.put(exReps,"6");

        itemId = database.insert(Workout.TABLE_NAME,null,values);

        values.put(workout_1,"dead lift");
        values.put(exSets,"3");
        values.put(exReps,"7");


        itemId = database.insert(Workout.TABLE_NAME,null,values);
    }
}
