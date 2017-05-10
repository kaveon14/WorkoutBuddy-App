package com.example.kaveon14.workoutbuddy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData;
import com.example.kaveon14.workoutbuddy.DataBase.DataBaseSQLiteHelper;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_SETS;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.createWorkoutTable;

public class WorkoutTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public WorkoutTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void addWorkoutTable(String tableName) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();//create new table
        WorkoutData wd = new WorkoutData();
        wd.createWorkoutTable(tableName);
        writableDatabase.execSQL(createWorkoutTable(tableName));
    }

    public void addExerciseToWorkout(String workoutName) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        // TODO get data off of screen
        ContentValues values = new ContentValues();//will most likely require entirely new method to get data
        values.put(COLUMN_EXERCISE_NAMES,"");
        values.put(COLUMN_EXERCISE_SETS,"");
        values.put(COLUMN_EXERCISE_REPS,"");
        long itemID = writableDatabase.insert(workoutName,null,values);;
    }

    public void printWorkoutTable(String tableName) {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(tableName,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            System.out.println("Exercise: "+cursor.getString(1) + " Sets: "+cursor.getString(2)
                    + " Reps: "+cursor.getString(3));
        }
        cursor.close();
    }
}

