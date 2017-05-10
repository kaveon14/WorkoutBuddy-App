package com.example.kaveon14.workoutbuddy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.TABLE_NAME;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.COLUMN_WAIST_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.COLUMN_BACK_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.COLUMN_CHEST_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.COLUMN_BICEP_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.COLUMN_CALF_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.COLUMN_DATE;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.COLUMN_FOREARM_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.COLUMN_WEIGHT;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.BodyData.COLUMN_QUAD_SIZE;
import com.example.kaveon14.workoutbuddy.DataBase.DataBaseSQLiteHelper;

public class BodyTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public BodyTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void addStatsToBodyTable(String date,String weight,String chestSize,String backSize,
    String bicepSize,String forearmSize,String waistSize,String quadSize,String calfSize) {//allow nullable to view most up to date stats that are not zero
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,date);
        values.put(COLUMN_WEIGHT,weight);
        values.put(COLUMN_CHEST_SIZE,chestSize);
        values.put(COLUMN_BACK_SIZE,backSize);
        values.put(COLUMN_BICEP_SIZE,bicepSize);
        values.put(COLUMN_FOREARM_SIZE,forearmSize);
        values.put(COLUMN_WAIST_SIZE,waistSize);
        values.put(COLUMN_QUAD_SIZE,quadSize);
        values.put(COLUMN_CALF_SIZE,calfSize);
        long itemID = writableDatabase.insert(TABLE_NAME,null,values);
    }

    public void printBodyTable() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            System.out.println("Date: "+ cursor.getString(1) + " Weight: "+cursor.getString(2)
            +" Chest Size: "+cursor.getString(3) + " Back Size: "+cursor.getString(4) +
            " Bicep Size: "+cursor.getString(4) + " Forearm Size: "+cursor.getString(5) +
            " Waist Size: "+cursor.getString(6) + " Quad Size: "+cursor.getString(7) +
            "Calf Size: "+cursor.getString(8));
        }
    }
}
