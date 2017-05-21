package com.example.kaveon14.workoutbuddy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.LinkedList;
import java.util.List;
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
        tableName = correctTableName(tableName);
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        createWorkoutTable(tableName);
        writableDatabase.execSQL(createWorkoutTable(tableName));
    }

    public void addExerciseToWorkout(String workoutName) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        // TODO get data off of screen
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAMES,"");
        values.put(COLUMN_EXERCISE_SETS,"");
        values.put(COLUMN_EXERCISE_REPS,"");
        long itemID = writableDatabase.insert(workoutName,null,values);
    }

    public List<String> getColumn(String tableName,String columnName) {
        tableName = correctTableName(tableName);
        List<String> columnList = new LinkedList<>();
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(tableName,null,null,null,null,null,null);
        int increment = 0;
        while(cursor.moveToNext()) {
            columnList.add(increment,cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
            increment++;
        }
        cursor.close();
        return columnList;
    }

    public List<String> getWorkoutNames() {
        List<String> workoutNames = new LinkedList<>();
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
        while(cursor.moveToNext()) {
            String tableName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            if(isTableNameCorrect(tableName))  {
                tableName = tableName.substring(0,tableName.length()-3);
                workoutNames.add(tableName);
            }
        }
        return workoutNames;
    }

    public void printWorkoutTable(String tableName) {
        tableName = correctTableName(tableName);
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(tableName,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            System.out.println("Exercise: "+cursor.getString(1) + " Sets: "+cursor.getString(2)
                    + " Reps: "+cursor.getString(3));
        }
        cursor.close();
    }

    private boolean isTableNameCorrect(String tableName) {
        int length = tableName.length();
        if(tableName.substring(length-3,length).equalsIgnoreCase("_wk")) {
            return true;
        } else {
            return false;
        }
    }

    private String correctTableName(String tableName) {//create new name
        int length = tableName.length();
        if(tableName.substring(length-3,length).equalsIgnoreCase("_wk")) {
            return tableName;
        } else {
            return tableName + "_wk";
        }

    }
}

