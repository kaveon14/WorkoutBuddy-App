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

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;//nice getting closer

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

        //int z=1;int set = 1;
        //Exercise exercise = exerciseList.get(0);
        values = addExData(values,exerciseList);
        /*while(z <= 10) {
            String c1 = "Exercise" + z;
            try {
                int index = getExerciseIndex(exerciseList, exercise);
                int base = base(exercise);
                for (int x = index - base; x < index; x++) {//sets have to always be one lower index
                    String c2 = c1 + "_Set" + set;
                    exercise = exerciseList.get(x);
                    String nameColumn = c2 + "_Name" + z;
                    String repsColumn  = c2 + "_Reps" + z;
                    String weightColumn = c2 + "_Weight" + z;

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
        }*/







        /*while(z <= 10) {
            try {
                String ex = "Exercise" + z;
                int index = getExerciseIndex(exerciseList, exercise);
                int base = base(exercise);
                for (int x = index - base; x < index; x++) {//sets have to always be one lower index
                    exercise = exerciseList.get(x);
                    String data = exercise.getExerciseName() + " " +
                            exercise.getActualReps() + " " +
                            exercise.getActualWeight();
                    //use this exercise to get data
                    String MAIN_COLUMN_NAME = ex + "_Set" + set;
                    values.put(MAIN_COLUMN_NAME,data);
                    set++;
                }
                set = 1;
                exercise = exerciseList.get(index);
                z++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }//use a double while loop*/
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.rawQuery("select* FROM "+TABLE_NAME
                +" ORDER BY date("+COLUMN_DATE+") DESC LIMIT 1",null);
        writableDatabase.close();

        /*for(int x=1;x<=exerciseList.size();x++) {//will be more complex
            Exercise exercise = exerciseList.get(x-1);
            System.out.println("name: "+exercise.getExerciseName());//needs another loop
            String COLUMN_NAME = "Exercise" + x;//need to use sets
            System.out.println("column name: "+COLUMN_NAME);
            String ex = "Exercise" + x;
            //sets = exercise.getActualSets();



            String data = exercise.getExerciseName() + " " +
                    exercise.getActualReps() + " " +
                    exercise.getActualWeight();
            values.put(COLUMN_NAME,data);
        }
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.rawQuery("select* FROM "+TABLE_NAME+" ORDER BY date("+COLUMN_DATE+") DESC LIMIT 1",null
        );*/

        //getExerciseIndex(exerciseList,exerciseList.get(0));
        //first need to get sets for the first exercise use int set
        //go to next ex in list if same as last one decrement set count
        //go to next ex in list if different ex go to next Exercise column and repeat process
    }

    private ContentValues addExData(ContentValues values,List<Exercise> exerciseList) {
        int z=1;int set = 1;
        Exercise exercise = exerciseList.get(0);
        while(z <= 15) {//use teneray for if under 15 exercises
            String c1 = "Exercise" + z;
            try {
                int index = getExerciseIndex(exerciseList, exercise);
                int base = base(exercise);//error is here
                for (int x = index - base; x <= index; x++) {//sets have to always be one lower index
                    String c2 = c1 + "_Set" + set;
                     exercise = exerciseList.get(x);
                    String nameColumn = c2 + "_Name" + set;
                    String repsColumn  = c2 + "_Reps" + set;
                    String weightColumn = c2 + "_Weight" + set;
//error data is stored 15 fucking times
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

    private void zz(int index,int base,ContentValues values) {

    }

    private int getExerciseIndex(List<Exercise> exerciseList, Exercise exercise) {
        int index = exerciseList.indexOf(exercise);
        int sets = exercise.getActualSets();
        return index+sets;
    }

    private int base(Exercise exercise) {
        return exercise.getActualSets();
    }//delete


    public List<SubWorkout> getData() {//nice we covered alot of shit
        //need to get each row
        //need to return subWorkout with date and mainWorkout
        //need to return complete exercise list
        //TODO this will get subWorkout with main and sub workout name
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        List<SubWorkout> subWorkoutList = new ArrayList<>(100);
       // int i = 1;int z = 0;
        String[] exerciseData = new String[5];//worthless cause new columns to load data bettter will be created
        while(cursor.moveToNext()) {
            String mainWorkout = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAINWORKOUT));
            String subWorkout = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBWORKOUT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            subWorkoutList.add(new SubWorkout(subWorkout,null)
                    .setMainWorkoutName(mainWorkout).setDate(date));
            /*String COLUMN_NAME = "Exercise" + i + "_Set" + i;//wrong
            if(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)) != null) {
                char[] c = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_NAME)).toCharArray();
                String ex = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int index = ex.indexOf(" ");
                String s = "";
                //need to reps and weight111//gotta find better way to get data from string
                for (int x = 0; x < c.length; x++) {//not even need
                    s = "" + c[x];
                    if (c[x] == ' ') {
                        s = s.trim();
                        exerciseData[z] = s;
                        z++;
                    }
                }
            }
            z = 0;*/
        }
        cursor.close();
        writableDatabase.close();
        return subWorkoutList;
    }

    public List<SubWorkout> getEx() {//finally got a litle something we can work with
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        List<Exercise> exerciseList = new ArrayList<>(100);
        List<SubWorkout> subWorkoutList = new ArrayList<>(100);

        //this is the last major peince for core functionality
        while(cursor.moveToNext()) {//gonna be a little harder make sure to delte all null values
            //change to string builder to vastly increase spend
            //this does not hit every column exp : Exercise1_Set2_Name1
            //needs to be wrapped in for loop

            //System.out.println("column Name: "+cursor.getColumnName(i));
            int totalSets = 0;
            int sets = 0;
            int totalReps = 0;
            int totalWeight = 0;
            for(int z=1;z<=15;z++) {
                String e1 = "Exercise" + z;
                for (int x = 1; x <= 10; x++) {//15 exercise 10 sets
                    String n = e1 + "_Set" + x + "_Name" + x;
                    String r = e1 + "_Set" + x + "_Reps" + x;
                    String w = e1 + "_Set" + x + "_Weight" + x;
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(n));
                    if (name != null) {
                        Exercise exercise = getExercise(cursor, r, w, name);
                        totalReps += exercise.getActualReps();
                        String weight = exercise.getActualWeight().replace("lbs", "").trim();
                        //weight = weight.substring(0, weight.length() - 2);
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
                //set total reps and weight
            }
            exerciseList = new ArrayList<>(100);
        }//if ex name is  null delete from list
        cursor.close();
        readableDatabase.close();
        return subWorkoutList;//return subWorkout list
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

    public void dafuq() {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        Cursor c = writableDatabase.query(TABLE_NAME,null,null,null,null,null,COLUMN_DATE+" DESC");
        Map<String,String> workoutData = new Hashtable<>();



        int x = 1;
        while(c.moveToNext()) {
            System.out.println("fuck: "+c.getString(c.getColumnIndexOrThrow(COLUMN_DATE)));
            String COLUMN_NAME = "Exercise" + x + "_Set" + x;
            //13 columns
            for(int z=1;z<=25;z++) {
                String data = c.getString(z);
                System.out.println(c.getColumnName(z)+ " : "+data);
            }
            if(x<=10) {
                x++;
            }




        }
        c.close();
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