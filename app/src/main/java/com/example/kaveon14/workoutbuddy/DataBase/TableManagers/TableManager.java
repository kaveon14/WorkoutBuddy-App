package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class TableManager {

    public Context context;
    private String tableName;
    private DataBaseSQLiteHelper dataBaseSQLiteHelper;
    private String[] SEARCHABLE_COLUMNS;

    public void setContext(Context context) {
        this.context = context;
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected void setSearchableColumns(String[] searchableColumns) {
        if(SEARCHABLE_COLUMNS == null || SEARCHABLE_COLUMNS.length == 0) {
            SEARCHABLE_COLUMNS = new String[10];
        }
        this.SEARCHABLE_COLUMNS = searchableColumns;
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

    public Map<String,List<String>> searchTable(String searchedItem) {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(getSearchQueryStatement(searchedItem),null);
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return convertCursorToMap(cursor);
    }

    private String getSearchQueryStatement(String searchedItem) {
        StringBuilder builder = new StringBuilder("SELECT * FROM ")
                .append(tableName).append(" WHERE ");
        int x=0;
        for(;x<SEARCHABLE_COLUMNS.length-1;x++) {
            builder.append(SEARCHABLE_COLUMNS[x])
                    .append(" LIKE '%").append(searchedItem).append("%' OR ");
        }
        builder.append(SEARCHABLE_COLUMNS[x])
                .append(" LIKE '%").append(searchedItem).append("%'");
        return builder.toString();
    }


    private Map<String,List<String>> convertCursorToMap(Cursor cursor) {
        int count = cursor.getCount();
        Map<String,List<String>> queriedData = new Hashtable<>();
        for(int x=0;x<SEARCHABLE_COLUMNS.length;x++) {
            queriedData.put(SEARCHABLE_COLUMNS[x],new ArrayList<String>());
        }

        while(count>0) {
            for(int x=0;x<SEARCHABLE_COLUMNS.length;x++) {
                queriedData.get(SEARCHABLE_COLUMNS[x]).add(getCursorString(cursor,x));
            }
            cursor.moveToNext();
            count--;
        }
        return queriedData;
    }

    private String getCursorString(Cursor cursor,int x) {
        return cursor.getString(cursor.getColumnIndexOrThrow(SEARCHABLE_COLUMNS[x]));
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
