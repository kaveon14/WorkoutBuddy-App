package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BodyTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public BodyTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }
// TODO needs to be redone like the body objects
    public void addStatsToBodyTable(Body body) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,body.getStringDate());
        values.put(COLUMN_WEIGHT,body.getWeight());
        values.put(COLUMN_CHEST_SIZE,body.getChestSize());
        values.put(COLUMN_BACK_SIZE,body.getBackSize());
        values.put(COLUMN_ARM_SIZE,body.getArmSize());
        values.put(COLUMN_FOREARM_SIZE,body.getForearmSize());
        values.put(COLUMN_WAIST_SIZE,body.getWaistSize());
        values.put(COLUMN_QUAD_SIZE,body.getQuadSize());
        values.put(COLUMN_CALF_SIZE,body.getCalfSize());
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    public List<String> getColumn(String columnName) {
        List<String> columnList = new ArrayList<>();
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            columnList.add(cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
        }
        readableDatabase.close();
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
        readableDatabase.close();
        cursor.close();
    }

    public void deleteRow(String datesToDelete[]) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        database.delete(TABLE_NAME,COLUMN_DATE+"=?",datesToDelete);
        database.close();
    }
}
