package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import java.util.ArrayList;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_SETS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.createWorkoutTable;

public class SubWorkoutTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public SubWorkoutTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void addSubWorkoutTable(String tableName) {
        tableName = getCorrectTableName(tableName);
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        createWorkoutTable(tableName);
        writableDatabase.execSQL(createWorkoutTable(tableName));
        writableDatabase.close();
    }

    public void addExerciseToSubWorkout(String mainWorkoutName,String subWorkoutName,Exercise ex) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        // TODO make function require the main workout name //might have to subworkouts with same name
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAMES,ex.getExerciseName());
        values.put(COLUMN_EXERCISE_SETS,ex.getExerciseSets());
        values.put(COLUMN_EXERCISE_REPS,ex.getExerciseReps());
        writableDatabase.insert(subWorkoutName,null,values);
        writableDatabase.close();
    }

    public List<String> getColumn(String tableName,String columnName) {
        tableName = getCorrectTableName(tableName);
        List<String> columnList = new ArrayList<>();
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(tableName,null,null,null,null,null,null);
        int increment = 0;
        while(cursor.moveToNext()) {
            columnList.add(increment,cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
            increment++;
        }
        readableDatabase.close();
        cursor.close();
        return columnList;
    }

    public void printSubWorkoutTable(String tableName) {
        tableName = getCorrectTableName(tableName);
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(tableName,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            System.out.println("Exercise: "+cursor.getString(1) + " Sets: "+cursor.getString(2)
                    + " Reps: "+cursor.getString(3));
        }
        readableDatabase.close();
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

    private String getCorrectTableName(String tableName) {
        if(isTableNameCorrect(tableName)) {
            return tableName;
        } else {
            return tableName + "_wk";
        }
    }
}

