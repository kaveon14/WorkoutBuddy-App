package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;

import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.TABLE_NAME;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_WAIST_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_BACK_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_CHEST_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_ARM_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_CALF_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_DATE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_FOREARM_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_WEIGHT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_QUAD_SIZE;
import java.util.LinkedList;
import java.util.List;

public class BodyTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public BodyTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void addStatsToBodyTable(String date,String weight,String chestSize,String backSize,
    String bicepSize,String forearmSize,String waistSize,String quadSize,String calfSize) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,date);
        values.put(COLUMN_WEIGHT,weight);
        values.put(COLUMN_CHEST_SIZE,chestSize);
        values.put(COLUMN_BACK_SIZE,backSize);
        values.put(COLUMN_ARM_SIZE,bicepSize);
        values.put(COLUMN_FOREARM_SIZE,forearmSize);
        values.put(COLUMN_WAIST_SIZE,waistSize);
        values.put(COLUMN_QUAD_SIZE,quadSize);
        values.put(COLUMN_CALF_SIZE,calfSize);
        long itemID = writableDatabase.insert(TABLE_NAME,null,values);
    }

    public List<String> getColumn(String columnName) {
        List<String> columnList = new LinkedList<>();
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        int increment = 0;
        while (cursor.moveToNext()) {
            columnList.add(increment, cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
            increment++;
        }
        cursor.close();
        return columnList;
    }

    public void printBodyTable() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            System.out.println("Date: "+ cursor.getString(1) + " Weight: "+cursor.getString(2)
            +" Chest Size: "+cursor.getString(3) + " Back Size: "+cursor.getString(4) +
            " Arm Size: "+cursor.getString(4) + " Forearm Size: "+cursor.getString(5) +
            " Waist Size: "+cursor.getString(6) + " Quad Size: "+cursor.getString(7) +
            " Calf Size: "+cursor.getString(8));
        }
    }

    public void deleteRow(String datesToDelete[]) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        database.delete(TABLE_NAME,COLUMN_DATE+"=?",datesToDelete);
    }
}