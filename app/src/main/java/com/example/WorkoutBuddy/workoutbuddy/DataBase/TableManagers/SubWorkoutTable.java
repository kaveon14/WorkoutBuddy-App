package com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.Exercise;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import java.util.ArrayList;
import java.util.List;

import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_SETS;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.createWorkoutTable;
//not possible to extend table manager class because this class handles multiple tables
public class SubWorkoutTable {
// _wk is used to identify workout tables
    private Context context;
    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public SubWorkoutTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        this.context = context;
    }

    public void addSubWorkoutTable(String tableName) {
        tableName = getCorrectSubWorkoutName(tableName);
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        createWorkoutTable(tableName);
        writableDatabase.execSQL(createWorkoutTable(tableName));
        writableDatabase.close();
    }

    public void addExerciseToSubWorkout(String mainWorkoutName,String subWorkoutName,Exercise ex) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        String tableName = getCorrectTableName(mainWorkoutName,subWorkoutName);


        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAMES,ex.getExerciseName());
        values.put(COLUMN_EXERCISE_SETS,ex.getGoalSets());
        values.put(COLUMN_EXERCISE_REPS,ex.getGoalReps());
        writableDatabase.insert(tableName,null,values);
        writableDatabase.close();
    }

    public void deleteExerciseFromSubWorkout(Exercise exercise,String tableName) {
        tableName = getCorrectSubWorkoutName(tableName);
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        String[] data = new String[] {
                exercise.getExerciseName()
        };
        writableDatabase.delete(tableName,COLUMN_EXERCISE_NAMES+"=?",data);
        writableDatabase.close();
    }

    public List<String> getColumn(String tableName,String columnName) {
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

    public List<SubWorkout> getSubworkouts() {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        List<String> mainWorkoutNames = mainWorkoutTable.getMainWorkoutNames();

        List<SubWorkout> subWorkouts = new ArrayList<>(15);
        for(int x=0;x<mainWorkoutNames.size();x++) {

            String mainWorkoutName = mainWorkoutNames.get(x);
            List<String> subWorkoutNames = mainWorkoutTable.getSubWorkoutNames(mainWorkoutName);

            for(int z=0;z<subWorkoutNames.size();z++) {
                String subWorkoutName = subWorkoutNames.get(z);
                List<Exercise> exerciseList = getSubWorkoutExercises(subWorkoutName);
                SubWorkout subWorkout = new SubWorkout(subWorkoutName);
                subWorkout.setMainWorkoutName(mainWorkoutName);
                subWorkouts.add(subWorkout);
            }
        }
        return subWorkouts;
    }

    public List<Exercise> getSubWorkoutExercises(String tableName) {
        List<String> exerciseNames = getColumn(tableName,COLUMN_EXERCISE_NAMES);
        List<String> exerciseSets = getColumn(tableName,COLUMN_EXERCISE_SETS);
        List<String> exerciseReps = getColumn(tableName,COLUMN_EXERCISE_REPS);

        List<Exercise> exerciselist = new ArrayList<>(10);
        for(int x=0;x<exerciseNames.size();x++) {
            Exercise exercise = new Exercise(exerciseNames.get(x),null);
            exercise.setGoalSets(exerciseSets.get(x));
            exercise.setGoalReps(exerciseReps.get(x));
            exerciselist.add(exercise);
        }
        return exerciselist;
    }

    public void deleteSubWorkoutTable(String tableName) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        writableDatabase.execSQL("DROP TABLE IF EXISTS "+tableName);
    }

    public void printSubWorkoutTable(String tableName) {
        tableName = getCorrectSubWorkoutName(tableName);
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(tableName,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            System.out.println("Exercise: "+cursor.getString(1) + " Sets: "+cursor.getString(2)
                    + " Reps: "+cursor.getString(3));
        }
        readableDatabase.close();
        cursor.close();
    }

    public String getCorrectTableName(String mainWorkoutName,String subWorkoutName) {
        subWorkoutName = getCorrectSubWorkoutName(subWorkoutName);
        mainWorkoutName = mainWorkoutName.replace(" ","_");
        return new StringBuilder(mainWorkoutName).append("_").append(subWorkoutName).toString();
    }

    private String getCorrectSubWorkoutName(String tableName) {
        if(isSubWorkoutNameCorrect(tableName)) {
            return tableName;
        } else if(tableName.contains(" ")) {
            return new StringBuilder(tableName.replace(" ","_")).append("_wk").toString();
        } else {
            return new StringBuilder(tableName).append("_wk").toString();
        }
    }

    private boolean isSubWorkoutNameCorrect(String tableName) {
        int length = tableName.length();
        if(tableName.substring(length-3,length).equalsIgnoreCase("_wk")) {
            return true;
        } else {
            return false;
        }
    }
}

