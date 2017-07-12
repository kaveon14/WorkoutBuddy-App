package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import java.util.ArrayList;
import java.util.List;

public abstract class TableManager {

    public Context context;
    private String tableName;
    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public void setContext(Context context) {
        this.context = context;
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumn(String columnName) {
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

    public void printTable() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(tableName,null,null,null,null,null,null);
        int columnCount = cursor.getColumnCount();
        while(cursor.moveToNext()) {
            for(int x=0;x<columnCount;x++) {
                String columnName = cursor.getColumnName(x);
                System.out.print(" "+columnName + ": " +
                        cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
            }
            System.out.println("");
        }
        readableDatabase.close();
        cursor.close();
    }
}
