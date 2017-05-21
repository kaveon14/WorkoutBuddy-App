package com.example.kaveon14.workoutbuddy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.LinkedList;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.ExerciseData.COLUMN_EXERCISE_DESCRIPTION;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.ExerciseData.TABLE_NAME;

public class ExerciseTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public ExerciseTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void addAnExercise(String exerciseName,String exerciseData) {
        // TODO both will come from the fragment
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISES,exerciseName);
        values.put(COLUMN_EXERCISE_DESCRIPTION,exerciseData);
        long itemID = writableDatabase.insert(TABLE_NAME,null,values);
    }

    public List<String> getColumn(String columnName) {
        List<String> columnList = new LinkedList<>();
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        int increment = 0;
        while(cursor.moveToNext()) {
            columnList.add(increment,cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
            increment++;
        }
        cursor.close();
        return columnList;
    }

    public void printExerciseTable() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            System.out.println("Exercise Name: "+cursor.getString(1) +
                    "Exercise Description: "+cursor.getString(2));
        }
        cursor.close();
    }
}
