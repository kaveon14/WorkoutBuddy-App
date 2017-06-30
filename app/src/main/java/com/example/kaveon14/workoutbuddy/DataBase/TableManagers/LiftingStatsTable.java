package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_DATE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_MAINWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_SUBWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.TABLE_NAME;

public class LiftingStatsTable extends TableManager {//possibly change actual table name

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public LiftingStatsTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        setTableName(TABLE_NAME);
    }

    public void addAWorkout(List<Exercise> exerciseList,SubWorkout subWorkout,String date) {//change name
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String mainWorkoutName = subWorkout.getMainWorkoutName();
        String subWorkoutName = subWorkout.getSubWorkoutName();

        values.put(COLUMN_MAINWORKOUT,mainWorkoutName);
        values.put(COLUMN_SUBWORKOUT,subWorkoutName);
        values.put(COLUMN_DATE,date);

        for(int x=0;x<exerciseList.size();x++) {
            Exercise exercise = exerciseList.get(x);
            String COLUMN_NAME = "Exercise" + x + "_Set" + x;
            String data = exercise.getExerciseName() + System.lineSeparator() +
                    exercise.getActualReps() + System.lineSeparator() +
                    exercise.getActualWeight();
            values.put(COLUMN_NAME,data);
        }
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }









}
//hopefully final column stup
// MainWorkout_Name     SubWorkout_Name     Workout_Date    Exercise#_Set#

//example workout
//   Exercise#_Set#      Exercise1_Set1 Exercise1_Set2 Exercise2_Set1
// Exercise name          Bench Press    Bench Press     Front Squat
// Reps                       6              8               10
// Weight                  200lbs          180lbs           250lbs

// key || -> || for searching purposes
/*
------------------
 MainWorkout_Name
------------------
 Full Body Program ->
-------------------
 SubWorkout_Name ->
------------------
 Chest Day ->
------------------
 Workout_Date
------------------
 08/23/1997 ->
------------------
 Exercise1_Set1
------------------
 Bench Press
 6
 200lbs
------------------
 Exercise4_Set9
 Dumbbell Fly
 20
 15lbs
-----------------
*/