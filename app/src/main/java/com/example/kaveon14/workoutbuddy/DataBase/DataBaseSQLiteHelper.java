package com.example.kaveon14.workoutbuddy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.kaveon14.workoutbuddy.DataBase.DefaultData.DefaultExerciseNames;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_SETS;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK1;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK2;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK3;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK4;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.TABLE_NAME_WK5;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData;
import com.example.kaveon14.workoutbuddy.DataBase.DefaultData.DefaultWorkouts;

public class DataBaseSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutDataBase.db";
    private Context context;

    public DataBaseSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        new DefaultWorkoutsExtension().addDefaultWorkoutsOnCreate(database);
        database.execSQL(DataBaseContract.ExerciseData.CREATE_TABLE);
        new DefaultExercisesExtension().addDefaultExercises(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        new DefaultWorkoutsExtension().addDefaultWorkoutsOnUpgrade(database);
        //database.execSQL("DROP TABLE IF EXISTS "+ DataBaseContract.BodyData.TABLE_NAME);//not yet created
        database.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.ExerciseData.TABLE_NAME);
        onCreate(database);
    }

    private class DefaultWorkoutsExtension {
        private Map<String, String> defaultWorkoutsMap = null;

        protected void addDefaultWorkoutsOnCreate(SQLiteDatabase database) {//think about a name change
            database.execSQL(WorkoutData.createWorkoutTable(TABLE_NAME_WK1));
            database.execSQL(DataBaseContract.WorkoutData.createWorkoutTable(TABLE_NAME_WK2));
            database.execSQL(DataBaseContract.WorkoutData.createWorkoutTable(TABLE_NAME_WK3));
            database.execSQL(DataBaseContract.WorkoutData.createWorkoutTable(TABLE_NAME_WK4));
            database.execSQL(DataBaseContract.WorkoutData.createWorkoutTable(TABLE_NAME_WK5));
            addDefaultWorkoutsData(database);
        }

        private void addDefaultWorkoutsOnUpgrade(SQLiteDatabase database) {//think about a name change
            database.execSQL("DROP TABLE IF EXISTS " + WorkoutData.TABLE_NAME_WK1);
            database.execSQL("DROP TABLE IF EXISTS " + WorkoutData.TABLE_NAME_WK2);
            database.execSQL("DROP TABLE IF EXISTS " + WorkoutData.TABLE_NAME_WK3);
            database.execSQL("DROP TABLE IF EXISTS " + WorkoutData.TABLE_NAME_WK4);
            database.execSQL("DROP TABLE IF EXISTS " + WorkoutData.TABLE_NAME_WK5);
        }

        private void setDefaultWorkoutsMap() {//mot yet created or thought out
            DefaultWorkouts fileReader = new DefaultWorkouts(context, "DefaultWorkoutValues.txt");
            try {
                defaultWorkoutsMap = fileReader.getWorkoutData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void addDefaultWorkoutsData(SQLiteDatabase database) {//think about a name change
            setDefaultWorkoutsMap();
            for (int x = 1; x <= defaultWorkoutsMap.size(); x++) {
                try {
                    setSingleDefaultWorkout(database, "Workout" + String.valueOf(x) + "_wk");
                } catch (NoSuchElementException e) {
                    //scanner has reached end of string and throws an error
                }
            }
        }

        private void setSingleDefaultWorkout(SQLiteDatabase database, String workoutName) throws NoSuchElementException {
            String data = defaultWorkoutsMap.get(workoutName);
            Scanner scanner = new Scanner(data);
            Scanner insertData = new Scanner(data);
            String exercise, sets, reps;
            while (scanner.hasNext()) {
                scanner.nextLine();
                exercise = getExerciseName(insertData);
                sets = getSetsForExercise(insertData);
                reps = getRepsForExercise(insertData);
                addDefaultExerciseToWorkout(database, workoutName, exercise, sets, reps);
            }
            scanner.close();
            insertData.close();
        }

        private void addDefaultExerciseToWorkout(SQLiteDatabase database, String workoutName, String exerciseName,
                                                 String sets, String reps) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EXERCISE_NAMES, exerciseName);
            values.put(COLUMN_EXERCISE_SETS, sets);
            values.put(COLUMN_EXERCISE_REPS, reps);
            long itemId = database.insert(workoutName, null, values);
        }

        private String getExerciseName(Scanner scan) {
            return scan.next().replace("_", " ");
        }

        private String getSetsForExercise(Scanner scan) {
            return scan.next();
        }

        private String getRepsForExercise(Scanner scan) {
            return scan.next();
        }

    }

    private class DefaultExercisesExtension {

        protected void addDefaultExercises(SQLiteDatabase database) {//add exercise descriptions also nd change func name
            List<String> exerciseNames = new DefaultExerciseNames(context, "ExerciseNames.txt").
                    readFileSorted();
            ContentValues values = new ContentValues();
            for (int x = 0; x < exerciseNames.size(); x++) {
                values.put(DataBaseContract.ExerciseData.COLUMN_EXERCISES, exerciseNames.get(x));
                database.insert(DataBaseContract.ExerciseData.TABLE_NAME, null, values);
            }
        }

    }
}
