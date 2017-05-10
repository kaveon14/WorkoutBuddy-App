package com.example.kaveon14.workoutbuddy.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.ExerciseData.COLUMN_EXERCISE_DESCRIPTION;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.ExerciseData.TABLE_NAME;

import com.example.kaveon14.workoutbuddy.DataBase.DataBaseSQLiteHelper;

public class ExerciseTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public ExerciseTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void addAnExercise(String exerciseName,String exerciseData) {
        // TODO both will come from the fragment
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        //will most likely require entirely new method to get data
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISES,exerciseName);
        values.put(COLUMN_EXERCISE_DESCRIPTION,exerciseData);
        long itemID = writableDatabase.insert(TABLE_NAME,null,values);
    }

    public void printExerciseTable() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null,null);

        while(cursor.moveToNext()) {
            System.out.println("Exercise Name: "+cursor.getString(1) +
                    "Exercise Description: "+cursor.getString(2));
        }
    }
}
