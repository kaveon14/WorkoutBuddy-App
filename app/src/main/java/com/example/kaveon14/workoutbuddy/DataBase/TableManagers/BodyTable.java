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
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.COLUMN_ROWID;
import java.util.ArrayList;
import java.util.List;

public class BodyTable extends TableManager {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public BodyTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        setTableName(TABLE_NAME);
    }

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

    public List<Body> getSortedBodyStats() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null
                ,COLUMN_DATE+" DESC");
        Body body;
        List<Body> bodyList = new ArrayList<>();
        while(cursor.moveToNext()) {
            body = new Body().setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)))
                    .setWeight(cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT)))
                    .setChestSize(cursor.getString(cursor.getColumnIndex(COLUMN_CHEST_SIZE)))
                    .setBackSize(cursor.getString(cursor.getColumnIndex(COLUMN_BACK_SIZE)))
                    .setArmSize(cursor.getString(cursor.getColumnIndex(COLUMN_ARM_SIZE)))
                    .setForearmSize(cursor.getString(cursor.getColumnIndex(COLUMN_FOREARM_SIZE)))
                    .setWaistSize(cursor.getColumnName(cursor.getColumnIndex(COLUMN_WAIST_SIZE)))
                    .setQuadSize(cursor.getString(cursor.getColumnIndex(COLUMN_QUAD_SIZE)))
                    .setCalfSize(cursor.getString(cursor.getColumnIndex(COLUMN_CALF_SIZE)))
                    .setRowID(cursor.getLong(cursor.getColumnIndex(COLUMN_ROWID)));
            bodyList.add(body);
        }
        cursor.close();
        readableDatabase.close();
        return bodyList;
    }

    public void updateRow(Body body,long rowId) {
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
        writableDatabase.update(TABLE_NAME,values,"_id="+rowId,null);
    }

    public void deleteRow(String datesToDelete[]) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        database.delete(TABLE_NAME,COLUMN_DATE+"=?",datesToDelete);
        database.close();
    }
}
