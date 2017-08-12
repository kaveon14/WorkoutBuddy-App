package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kaveon14.workoutbuddy.DataBase.Data.MainWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import java.util.ArrayList;
import java.util.List;

import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.COLUMN_ROWID;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_MAINWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.TABLE_NAME;

public class MainWorkoutTable extends TableManager {//need to increase table columns to 10(days)

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public MainWorkoutTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        setTableName(TABLE_NAME);
        setSearchableColumns(new String[]{COLUMN_MAINWORKOUT});
    }

    public void addMainWorkout(String workoutName) {
        SQLiteDatabase writableDatabase  = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINWORKOUT,workoutName);
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    public void addSubWorkout(String mainWorkoutName,String subWorkoutNames) {
        List<String> rowValues = getSubWorkoutNames(mainWorkoutName);
        deleteRow(mainWorkoutName);
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();

        int day = 1;
        rowValues.add(subWorkoutNames);
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINWORKOUT,mainWorkoutName);
        for(int x=0;x<rowValues.size();x++) {
            String COLUMN_NAME = "Day"+day + "_Workout"+day;
            values.put(COLUMN_NAME,rowValues.get(x));
            day++;
        }
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    public List<MainWorkout> getMainWorkouts() {
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseContract.MainWorkoutData.TABLE_NAME,
                null,null,null,null,null,COLUMN_MAINWORKOUT+" COLLATE NOCASE ASC");
        List<MainWorkout> mainWorkouts = new ArrayList<>();
        List<SubWorkout> subWorkouts;
        while(cursor.moveToNext()) {
            String mainWorkoutName = cursor.getString(cursor.
                    getColumnIndex(COLUMN_MAINWORKOUT));
            long rowID = cursor.getLong(cursor.getColumnIndex(COLUMN_ROWID));
            subWorkouts = getSubWorkouts(mainWorkoutName,cursor);
            MainWorkout mainWorkout = new MainWorkout(mainWorkoutName,subWorkouts);
            mainWorkout.setRowId(rowID);
            mainWorkouts.add(mainWorkout);
        }
        database.close();
        cursor.close();
        return mainWorkouts;
    }

    private List<SubWorkout> getSubWorkouts(String mainWorkoutName,Cursor cursor) {
        List<SubWorkout> subWorkouts = new ArrayList<>();
        for(int x=2;x<=16;x++) {
            String subWorkoutName = cursor.getString(x);
            if(subWorkoutName != null) {
                SubWorkout subWorkout = new SubWorkout(subWorkoutName, null);
                subWorkout.setMainWorkoutName(mainWorkoutName);
                subWorkouts.add(subWorkout);
            }
        }
        return subWorkouts;
    }

    public List<String> getMainWorkoutNames() {
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseContract.MainWorkoutData.TABLE_NAME,
                null,null,null,null,null,COLUMN_MAINWORKOUT+" COLLATE NOCASE ASC");
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
                for(int x=2;x<=16;x++) {
                    rowData.add(cursor.getString(x));
                }
                break;
            }
        }
        rowData = deleteNullValues(rowData);
        database.close();
        cursor.close();
        return rowData;
    }

    public void deleteMainWorkout(String mainWorkoutName) {//also need to delete the subWorkout tables
        //but not yet setup for searching
        deleteRow(mainWorkoutName);
    }

    public void deleteSubWorkout(String mainWorkoutName,String subWorkoutName) {
        List<String> rowValues = getSubWorkoutNames(mainWorkoutName);
        rowValues.remove(subWorkoutName);
        deleteRow(mainWorkoutName);
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();

        int day = 1;
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINWORKOUT,mainWorkoutName);
        for(int x=0;x<rowValues.size();x++) {
            String COLUMN_NAME = "Day"+day + "_Workout"+day;
            values.put(COLUMN_NAME,rowValues.get(x));
            day++;
        }
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    private void deleteRow(String mainWorkout) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        String[] data = new String[]{
                mainWorkout
        };
        database.delete(TABLE_NAME,COLUMN_MAINWORKOUT+"=?",data);
        database.close();
    }

    private List<String> deleteNullValues(List<String> rowData) {
        int limit = rowData.size()*2;
        for(int x=0;x<limit;x++) {
            rowData.remove(null);
        }
        return rowData;
    }
}