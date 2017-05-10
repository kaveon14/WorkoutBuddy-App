package com.example.kaveon14.workoutbuddy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import com.example.kaveon14.workoutbuddy.readFile;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK1;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK2;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK3;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK4;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK5;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_SETS;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_REPS;


public class DataBaseSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutDataBase.db";
    private Context context;
    private Scanner scan;
    private Map<String,String> defaultWorkouts = null;

    public DataBaseSQLiteHelper(Context context) {//this is a practice database will have to create a new one later
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(WorkoutData.createWorkoutTable(TABLE_NAME_WK1));
        database.execSQL(WorkoutData.createWorkoutTable(TABLE_NAME_WK2));
        database.execSQL(WorkoutData.createWorkoutTable(TABLE_NAME_WK3));
        database.execSQL(WorkoutData.createWorkoutTable(TABLE_NAME_WK4));
        database.execSQL(WorkoutData.createWorkoutTable(TABLE_NAME_WK5));
        //database.execSQL(DataBaseContract.ExerciseData.CREATE_TABLE); no test data yet
        //database.execSQL(DataBaseContract.BodyData.TABLE_NAME); no test data yet
        addDefaultWorkoutsData(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS "+ WorkoutData.TABLE_NAME_WK1);//must do for all tables
        database.execSQL("DROP TABLE IF EXISTS "+ WorkoutData.TABLE_NAME_WK2);//manually created
        database.execSQL("DROP TABLE IF EXISTS "+ WorkoutData.TABLE_NAME_WK3);
        database.execSQL("DROP TABLE IF EXISTS "+ WorkoutData.TABLE_NAME_WK4);
        database.execSQL("DROP TABLE IF EXISTS "+ WorkoutData.TABLE_NAME_WK5);
        //database.execSQL("DROP TABLE IF EXISTS "+ DataBaseContract.BodyData.TABLE_NAME);
        //database.execSQL("DROP TABLE IF EXISTS "+ DataBaseContract.ExerciseData.TABLE_NAME);
        onCreate(database);
    }

    private void setDefaultWorkoutsMap() {
        readFile rf = new readFile(context,"DefaultWorkoutValues.txt");
        try {
            defaultWorkouts = rf.readFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void addDefaultWorkoutsData(SQLiteDatabase database) {
        setDefaultWorkoutsMap();
        for (int x=1;x<=defaultWorkouts.size();x++) {
            try {
                setSingleDefaultWorkout(database,"Workout"+String.valueOf(x));
            } catch (NoSuchElementException e) {
                //scanner has reached end of string and throws an error
            }
        }
    }

    private void setSingleDefaultWorkout(SQLiteDatabase database,String workoutName) throws NoSuchElementException {
        String data = defaultWorkouts.get(workoutName);
        scan = new Scanner(data);
        Scanner s = new Scanner(data);
        Scanner x = new Scanner(data);
        String exercise,sets,reps;
        while (s.hasNext()) {
            s.nextLine();
            exercise = getExerciseName(x);
            sets = getSetsForExercise(x);
            reps = getRepsForExercise(x);
            addDefaultExerciseToWorkout(database,workoutName,exercise,sets,reps);
        }
    }

    private void addDefaultExerciseToWorkout(SQLiteDatabase database,String workoutName,String exerciseName,
                                             String sets,String reps) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAMES, exerciseName);
        values.put(COLUMN_EXERCISE_SETS,sets);
        values.put(COLUMN_EXERCISE_REPS,reps);
        long itemId = database.insert(workoutName,null,values);
    }

    private String getExerciseName(Scanner scan) {
        return scan.next().replace("_"," ");
    }

    private String getSetsForExercise(Scanner scan) {
        return scan.next();
    }

    private String getRepsForExercise(Scanner scan) {
        return scan.next();
    }



    /*public void ff() {
        String fileName = "DefaultWorkoutValues.txt";
        readFile rf = new readFile(context,fileName);
        try {
            defaultWorkouts = rf.readFile();
            System.out.println("size: "+ defaultWorkouts.size());
            for (int x = 1; x <= defaultWorkouts.size(); x++) {
                //System.out.println("test file: "+ defaultWorkouts.get("Workout"+String.valueOf(x)));
            }
        } catch(IOException ex) {
            ex.printStackTrace();
            ex.getCause();
        }

        String data = defaultWorkouts.get("Workout1");
        scan = new Scanner(data);
        Scanner s = new Scanner(data);
        String exercise,sets,reps;
        try {
            while (s.hasNext()) {
                s.nextLine();
                exercise = getExerciseName();
                sets = getSetsForExercise();
                reps = getRepsForExercise();
                System.out.println("exercise: "+exercise + " sets: "+sets+" reps: "+reps);
            }
        } catch(NoSuchElementException ex) {
            // error thrown because end of "data" string reached
        }
    }*/
}
