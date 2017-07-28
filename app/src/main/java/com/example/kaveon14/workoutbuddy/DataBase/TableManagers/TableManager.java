package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import java.util.ArrayList;
import java.util.List;

public abstract class TableManager {

    public Context context;
    private String tableName;
    private DataBaseSQLiteHelper dataBaseSQLiteHelper;
    private String[] SEARCHABLE_COLUMN;

    public void setContext(Context context) {
        this.context = context;
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected void setSearchableColumns(String[] searchableColumns) {
        if(SEARCHABLE_COLUMN == null || SEARCHABLE_COLUMN.length == 0) {
            SEARCHABLE_COLUMN = new String[10];
        }
        this.SEARCHABLE_COLUMN = searchableColumns;
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

    public List<String> searchTable(String searchedItem) {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        String query = new StringBuilder("SELECT * FROM ").append(tableName)
                .append(" WHERE ").append(SEARCHABLE_COLUMN[0]).append(" LIKE ").append("'")
                .append(searchedItem).append('%').append("'").toString();
        Cursor cursor = readableDatabase.rawQuery(query,null);


        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        return convertCursorToList(cursor);
    }

    private List<String> convertCursorToList(Cursor cursor) {
        int count = cursor.getCount();
        List<String> list = new ArrayList<>();
        while(count>0) {
            list.add(cursor.getString(cursor
                    .getColumnIndexOrThrow(SEARCHABLE_COLUMN[0])));
            count--;

        }

        return list;
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
