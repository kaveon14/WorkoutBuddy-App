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

    public void addAWorkout(Exercise exercise) {//possibly also store the main and sub workout name for searching
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        String COLUMN_NAME = exercise.getExerciseName().replace(" ","_").replace("-","_");

        ContentValues values = new ContentValues();//
    }
}
// use system line seperator
//   Exercise#_Set#      Exercise1_Set1 Exercise1_Set2 Exercise2_Set1
// Exercise name          Bench Press    Bench Press     Front Squat
// Reps                       6              8               10
// Weight                  200lbs          180lbs           250lbs