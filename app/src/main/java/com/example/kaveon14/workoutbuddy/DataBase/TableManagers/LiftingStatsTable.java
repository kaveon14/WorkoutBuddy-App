package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.TABLE_NAME;

public class LiftingStatsTable {//possibly change actual table name

    private Context context;
    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public LiftingStatsTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        this.context = context;
    }

    public void dd(Exercise exercise) {
        System.out.println("Yes");
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor c = writableDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        int x = 0;
        System.out.println("Please: "+x);
        while(x<74) {
            System.out.println("name: "+c.getColumnName(x));
            x++;
        }
        writableDatabase.close();
        c.close();
    }


}
