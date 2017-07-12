package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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
        values = addExData(values,exerciseList);
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.rawQuery("select* FROM "+TABLE_NAME
                +" ORDER BY date("+COLUMN_DATE+") DESC LIMIT 1",null);
        writableDatabase.close();
    }

    private ContentValues addExData(ContentValues values,List<Exercise> exerciseList) {
        int z=1;int set = 1;
        Exercise exercise = exerciseList.get(0);
        while(z <= 15) {
            String c1 = "Exercise" + z;
            try {
                int index = getExerciseIndex(exerciseList, exercise);
                int base = base(exercise);
                for (int x = index - base; x <= index; x++) {
                    String c2 = c1 + "_Set" + set;
                     exercise = exerciseList.get(x);
                    String nameColumn = c2 + "_Name" + set;
                    String repsColumn  = c2 + "_Reps" + set;
                    String weightColumn = c2 + "_Weight" + set;

                    values.put(nameColumn,exercise.getExerciseName());
                    values.put(repsColumn,exercise.getActualReps());
                    values.put(weightColumn,exercise.getActualWeight());
                    set++;
                }
                set = 1;
                z++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return values;
    }

    private int getExerciseIndex(List<Exercise> exerciseList, Exercise exercise) {
        int index = exerciseList.indexOf(exercise);
        int sets = exercise.getActualSets();
        return index+sets;
    }

    private int base(Exercise exercise) {
        return exercise.getActualSets();
    }//delete


    public List<SubWorkout> getData() {//delete
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        List<SubWorkout> subWorkoutList = new ArrayList<>(100);
        String[] exerciseData = new String[5];
        while(cursor.moveToNext()) {
            String mainWorkout = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAINWORKOUT));
            String subWorkout = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBWORKOUT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            subWorkoutList.add(new SubWorkout(subWorkout,null)
                    .setMainWorkoutName(mainWorkout).setDate(date));
        }
        cursor.close();
        writableDatabase.close();
        return subWorkoutList;
    }

    public List<SubWorkout> getEx() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null,COLUMN_DATE+" DESC");
        List<Exercise> exerciseList = new ArrayList<>(100);
        List<SubWorkout> subWorkoutList = new ArrayList<>(100);
        while(cursor.moveToNext()) {
            int totalSets = 0;
            int sets = 0;
            int totalReps = 0;
            int totalWeight = 0;
            for(int z=1;z<=15;z++) {
                String e1 = "Exercise" + z;
                for (int x = 1; x <= 10; x++) {
                    String n = e1 + "_Set" + x + "_Name" + x;
                    String r = e1 + "_Set" + x + "_Reps" + x;
                    String w = e1 + "_Set" + x + "_Weight" + x;
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(n));
                    if (name != null) {
                        Exercise exercise = getExercise(cursor, r, w, name);
                        totalReps += exercise.getActualReps();
                        String weight = exercise.getActualWeight().replace("lbs", "").trim();
                        totalWeight += Integer.valueOf(weight);
                        exerciseList.add(exercise);
                        sets = x;
                    }
                }
                totalSets += sets;
                sets = 0;
            }


            String subName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBWORKOUT));
            if(subName != null) {
                SubWorkout subWorkout = getSubWorkout(cursor,exerciseList,subName);
                subWorkout.setTotalSets(totalSets);
                subWorkout.setTotalReps(totalReps);
                subWorkout.setTotalWeight(String.valueOf(totalWeight)+"lbs");
                subWorkoutList.add(subWorkout);
            }
            exerciseList = new ArrayList<>(100);
        }
        cursor.close();
        readableDatabase.close();
        return subWorkoutList;
    }


    private Exercise getExercise(Cursor cursor,String repsColumn,String weightColumn,String name) {
        String reps = cursor.getString(cursor.getColumnIndexOrThrow(repsColumn));
        String weight = cursor.getString(cursor.getColumnIndexOrThrow(weightColumn));

        Exercise exercise = new Exercise(name,null);
        exercise.setActualReps(Integer.valueOf(reps));
        exercise.setActualWeight(weight);
        return exercise;
    }

    private SubWorkout getSubWorkout(Cursor cursor,List<Exercise> exerciseList, String subName) {
        String mainName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAINWORKOUT));
        SubWorkout subWorkout = new SubWorkout(subName,exerciseList);
        subWorkout.setMainWorkoutName(mainName);
        String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
        subWorkout.setDate(date);
        return subWorkout;
    }


    public void testSort(String date) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DATE,date);
        writableDatabase.insert(TABLE_NAME,null,values);
        String arg[] = {
                date
        };
        writableDatabase.rawQuery("select* FROM "+TABLE_NAME+" ORDER BY date("+COLUMN_DATE+") DESC LIMIT 1",null);
        writableDatabase.close();
    }

    public void dafuq() {//test
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        Cursor c = writableDatabase.query(TABLE_NAME, null, null, null, null, null, COLUMN_DATE + " DESC");
        Map<String, String> workoutData = new Hashtable<>();

        int x = 1;
        while (c.moveToNext()) {
            System.out.println("fuck: " + c.getString(c.getColumnIndexOrThrow(COLUMN_DATE)));
            String COLUMN_NAME = "Exercise" + x + "_Set" + x;
            for (int z = 1; z <= 25; z++) {
                String data = c.getString(z);
                System.out.println(c.getColumnName(z) + " : " + data);
            }
            if (x <= 10) {
                x++;
            }
        }
        c.close();
        writableDatabase.close();
    }
}
