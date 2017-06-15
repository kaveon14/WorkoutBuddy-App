package com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.kaveon14.workoutbuddy.DataBase.DefaultData.DefaultExerciseNames;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_MAINWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_1;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_2;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_3;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_4;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_5;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_6;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_7;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_SETS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK1;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK2;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK3;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK4;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK5;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData;
import com.example.kaveon14.workoutbuddy.DataBase.DefaultData.DefaultWorkouts;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;

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
        database.execSQL(DataBaseContract.MainWorkoutData.CREATE_TABLE);
        testData(database);
        new DefaultWorkoutsExtension().addDefaultWorkoutsOnCreate(database);
        database.execSQL(DataBaseContract.ExerciseData.CREATE_TABLE);
        new DefaultExercisesExtension().addDefaultExercises(database);
        database.execSQL(DataBaseContract.BodyData.CREATE_TABLE);
        database.execSQL(createLiftDataTable());
    }

    private void testData(SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINWORKOUT,"TEST WORKOUT");
        values.put(COLUMN_SUBWORKOUT_1,"Chest_Day");
        values.put(COLUMN_SUBWORKOUT_2,"Back_Day");
        values.put(COLUMN_SUBWORKOUT_3,"Shoulder_Day");
        values.put(COLUMN_SUBWORKOUT_4,"Leg_Day");
        values.put(COLUMN_SUBWORKOUT_5,"Arm_Day");
        values.put(COLUMN_SUBWORKOUT_6,"NULL_1");
        values.put(COLUMN_SUBWORKOUT_7,"NULL_2");
        long itemID = database.insert(DataBaseContract.MainWorkoutData.TABLE_NAME,null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        new DefaultWorkoutsExtension().addDefaultWorkoutsOnUpgrade(database);
        //database.execSQL("DROP TABLE IF EXISTS "+ DataBaseContract.BodyData.TABLE_NAME);//not yet created
        database.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.ExerciseData.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.BodyData.TABLE_NAME);
        onCreate(database);
    }

    private String createLiftDataTable() {
        List<String> exerciseNames = new
                DefaultExerciseNames(context,"ExerciseNames.txt").readFileSorted();
        String columnName;
        for(int x=0;x<exerciseNames.size();x++) {
            columnName = exerciseNames.get(x).replace(" ","_").replace("-","_");
            DataBaseContract.LiftData.createLiftingStatsColumn(columnName);
        }
        DataBaseContract.LiftData.setColumns();
        String createTable  = DataBaseContract.LiftData.CREATE_TABLE +
                DataBaseContract.LiftData.setColumns();
        return createTable;
    }

    private class DefaultWorkoutsExtension {
        private List<String> defaultWorkoutNames = null;
        private Map<String, String> defaultWorkoutsMap = null;

        protected void addDefaultWorkoutsOnCreate(SQLiteDatabase database) {//think about a name change
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK1));
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK2));
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK3));
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK4));
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK5));
            addDefaultWorkoutsData(database);
        }

        private void addDefaultWorkoutsOnUpgrade(SQLiteDatabase database) {//think about a name change
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK1);
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK2);
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK3);
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK4);
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK5);
        }

        private void setDefaultWorkoutsMap() {
            DefaultWorkouts fileReader = new DefaultWorkouts(context, "DefaultWorkoutValues.txt");
            MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
            try {
                defaultWorkoutsMap = fileReader.getSubWorkoutData();
                defaultWorkoutNames = new LinkedList<>();
                defaultWorkoutNames.add("Chest_Day");//needs to read from a file
                defaultWorkoutNames.add("Back_Day");
                defaultWorkoutNames.add("Shoulder_Day");
                defaultWorkoutNames.add("Leg_Day");
                defaultWorkoutNames.add("Arm_Day");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void addDefaultWorkoutsData(SQLiteDatabase database) {
            setDefaultWorkoutsMap();
            for (int x = 0; x < defaultWorkoutsMap.size(); x++) {
                try {
                    setSingleDefaultWorkout(database, defaultWorkoutNames.get(x)+"_wk");
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
