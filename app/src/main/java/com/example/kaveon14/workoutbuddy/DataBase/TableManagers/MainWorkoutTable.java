package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import java.util.ArrayList;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_MAINWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.TABLE_NAME;

public class MainWorkoutTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public MainWorkoutTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void addMainWorkout(String workoutName) {
        SQLiteDatabase writableDatabase  = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINWORKOUT,workoutName);
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    public void addSubWorkout(String mainWorkoutName,String subWorkoutNames,int day) {
        List<String> rowValues = getSubWorkouts(mainWorkoutName);
        deleteRow(mainWorkoutName);
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();

        rowValues.remove(day);
        rowValues.add(day,subWorkoutNames);
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINWORKOUT,mainWorkoutName);
        for(int x=1;x<=7;x++) {
            String COLUMN_NAME = "Day"+x+"_Workout"+x;
            values.put(COLUMN_NAME,rowValues.get(x));
        }
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    public List<String> getMainWorkoutNames() {
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseContract.MainWorkoutData.TABLE_NAME,
                null,null,null,null,null,null);
        List<String> columnData = new ArrayList<>();
        while(cursor.moveToNext()) {
            columnData.add(cursor.getString(1));
        }
        database.close();
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
                    rowData.add(cursor.getString(x));
                }
                break;
            }
        }
        database.close();
        cursor.close();
        return rowData;
    }

    private List<String> getSubWorkouts(String mainWorkout) {
        List<String> rowData = new ArrayList<>();
        rowData.add("*/* SPACE HOLDER */*");
        rowData.addAll(getSubWorkoutNames(mainWorkout));
        return rowData;
    }

    private void deleteRow(String mainWorkout) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        String[] data = new String[]{
                mainWorkout
        };
        database.delete(TABLE_NAME,"Main_Workout_Names=?",data);
        database.close();
    }

    public List<String> getColumn(String tableName,String columnName) {
        List<String> columnList = new ArrayList<>();
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(tableName,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            columnList.add(cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
        }
        readableDatabase.close();
        cursor.close();
        return columnList;

    }
}