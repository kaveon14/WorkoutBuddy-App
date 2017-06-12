package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_MAINWORKOUT;

public class MainWorkoutTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public MainWorkoutTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void addMainWorkout(String workoutName) {
        SQLiteDatabase writableDatabase  = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINWORKOUT,workoutName);
        long itemId = writableDatabase.insert("Main_Workouts",null,values);
    }

    public void addSubWorkout(String mainWorkoutName,String subWorkoutNames,int day) {

        SQLiteDatabase writableDatabase  = dataBaseSQLiteHelper.getWritableDatabase();
        List<String> rowValues = getSubWorkouts(mainWorkoutName);
        deleteRow(mainWorkoutName);

        rowValues.remove(day);
        rowValues.add(day,subWorkoutNames);

        ContentValues values = new ContentValues();
        values.put("Main_Workout",mainWorkoutName);
        for(int x=1;x<=7;x++) {
            String COLUMN_NAME = "Day"+x+"_Workout"+x;
            values.put(COLUMN_NAME,rowValues.get(x));
        }
        long itemID = writableDatabase.insert("Main_Workouts",null,values);
    }

    public List<String> getMainWorkoutNames() {
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseContract.MainWorkoutData.TABLE_NAME,
                null,null,null,null,null,null);
        List<String> columnData = new ArrayList<>();
        while(cursor.moveToNext()) {
            columnData.add(cursor.getString(1));
        }
        cursor.close();
        return columnData;
    }

    public List<String> getSubWorkoutNames(String mainWorkout) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseContract.MainWorkoutData.TABLE_NAME,
                null,null,null,null,null,null);
        List<String> rowData = new ArrayList<>();
        while(cursor.moveToNext()) {
            if(cursor.getString(1).equalsIgnoreCase(mainWorkout)) {
                for(int x=2;x<=8;x++) {
                    rowData.add(x-2,cursor.getString(x));
                }
            }
        }
        cursor.close();
        return rowData;
    }

    private List<String> getSubWorkouts(String mainWorkout) {//needs to be renamed
        List<String> rowData = new ArrayList<>();
        rowData.add(0,"*/* SPACE HOLDER */*");
        for(int x=1;x<=7;x++) {
            rowData.add(x,getSubWorkoutNames(mainWorkout).get(x));
        }

        return rowData;
    }

    private void deleteRow(String mainWorkout) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        String[] data = new String[]{
                mainWorkout
        };
        database.delete("Main_Workouts","Main_Workout=?",data);
    }

    public List<String> getColumn(String tableName,String columnName) {
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
}