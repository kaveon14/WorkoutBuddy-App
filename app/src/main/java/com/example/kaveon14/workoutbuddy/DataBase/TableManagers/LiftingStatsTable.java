package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_DATE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_MAINWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_SUBWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_TOTAL_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_TOTAL_SETS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_TOTAL_WEIGHT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.TABLE_NAME;

public class LiftingStatsTable extends TableManager {//possibly change actual table name

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;
    private final int TOTAL_REPS = 0, TOTAL_WEIGHT = 1, TOTAL_SETS = 2, UNIT_OF_MEAS = 3;
    private final int NAME_COLUMN = 0, REPS_COLUMN = 1, WEIGHT_COLUMN = 2;

    public LiftingStatsTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        setTableName(TABLE_NAME);
    }

    public void addWorkoutData(List<WorkoutExercise> workouts, SubWorkout subWorkout) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String mainWorkoutName = subWorkout.getMainWorkoutName();
        String subWorkoutName = subWorkout.getSubWorkoutName();

        values.put(COLUMN_MAINWORKOUT, mainWorkoutName);
        values.put(COLUMN_SUBWORKOUT, subWorkoutName);
        values.put(COLUMN_DATE, subWorkout.getDate());

        int exNum = 1;
        int totalReps = 0;
        int totalSets = 0;
        int totalWeight = 0;
        for (WorkoutExercise w : workouts) {
            totalReps += w.getTotalReps();
            totalSets += w.getTotalSets();
            totalWeight += Integer.valueOf(w.getTotalWeight()[WorkoutExercise.WEIGHT]);

            values = addMainExerciseData(values, w, exNum);
            exNum++;
        }
        values.put(COLUMN_TOTAL_REPS, totalReps);
        values.put(COLUMN_TOTAL_SETS, totalSets);
        values.put(COLUMN_TOTAL_WEIGHT, totalWeight +
                workouts.get(0).getWeight("Set 1")[WorkoutExercise.UNIT_OF_MEAS]);

        writableDatabase.insert(TABLE_NAME, null, values);
        writableDatabase.close();
    }

    private ContentValues addMainExerciseData(ContentValues values, WorkoutExercise workout, int exNum) {//the new add a workout
        Map<String, String> data = workout.getWorkoutData();
        int setIncrement = 1;

        String exCol = "Exercise" + exNum;
        for (int x = 0; x < data.size(); x++) {
            String[] columns = getColumnNames(exCol, setIncrement);
            String SET = "Set " + (x + 1);
            int reps = workout.getReps(SET);
            String[] weightData = workout.getWeight(SET);
            String weight = weightData[WorkoutExercise.WEIGHT] +
                    weightData[WorkoutExercise.UNIT_OF_MEAS];

            values.put(columns[NAME_COLUMN], workout.getExerciseName());
            values.put(columns[REPS_COLUMN], reps);
            values.put(columns[WEIGHT_COLUMN], weight);
            setIncrement++;
        }
        return values;
    }

    public List<SubWorkout> getCompletedWorkouts() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, COLUMN_DATE + " DESC");
        List<WorkoutExercise> workoutData = new ArrayList<>(100);
        List<SubWorkout> subWorkoutList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int[] data = getTotalsData(cursor);
            workoutData = getWorkoutExerciseData(cursor);
            getSubWorkoutData(cursor, subWorkoutList, workoutData, data);
            workoutData = new ArrayList<>();
        }
        cursor.close();
        readableDatabase.close();
        return subWorkoutList;
    }

    private int[] getTotalsData(Cursor cursor) {

        int totalSets = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_SETS));
        int totalReps = cursor.getInt(cursor
                .getColumnIndexOrThrow(COLUMN_TOTAL_REPS));
        String totalWeight = cursor.getString(cursor
                .getColumnIndexOrThrow(COLUMN_TOTAL_WEIGHT));

        int unitOfMeas;
        int[] data = new int[4];
        if (totalWeight != null) {
            int index = totalWeight.length();
            String substring = totalWeight.substring(index - 3, index);
            String unitOfMeasurement = substring.equalsIgnoreCase("lbs") ? "lbs" : "kgs";
            unitOfMeas = unitOfMeasurement.equalsIgnoreCase("lbs") ? 1 : 0;


            data[TOTAL_SETS] = totalSets;
            data[TOTAL_REPS] = totalReps;
            data[TOTAL_WEIGHT] = Integer.valueOf(totalWeight.substring(0, index - 3));
            data[UNIT_OF_MEAS] = unitOfMeas;
        }
        return data;
    }

    private List<WorkoutExercise> getWorkoutExerciseData(Cursor cursor) {
        List<WorkoutExercise> workoutData = new ArrayList<>();
        Map<String,String> dataMap = new Hashtable<>();

        StringBuilder builder;
        WorkoutExercise workout = null;
        for (int z = 1; z <= 15; z++) {
            String columnStart = "Exercise" + z;
            for (int x = 1; x <= 10; x++) {
                String[] columns = getColumnNames(columnStart, x);
                String exerciseName = cursor.getString(cursor
                        .getColumnIndexOrThrow(columns[NAME_COLUMN]));
                String reps = cursor.getString(cursor
                        .getColumnIndexOrThrow(columns[REPS_COLUMN]));
                if (exerciseName != null && reps != null) {
                    Exercise exercise = new Exercise(exerciseName,null);
                    String weight = cursor.getString(cursor
                            .getColumnIndexOrThrow(columns[WEIGHT_COLUMN]));
                    workout = new WorkoutExercise(exercise);
                    builder = new StringBuilder(reps);
                    String data = builder.append("/").append(weight).toString();
                    dataMap.put("Set "+x,data);
                }
            }if (workout != null) {
                workout.setWorkoutData(dataMap);
                workoutData.add(workout);
                workout = null;
            }
            dataMap = new Hashtable<>();
        }
        return workoutData;
    }

    private Exercise getExercise(Cursor cursor, String repsCol, String weightCol
            , String name, int sets) {

        String reps = cursor.getString(cursor.getColumnIndexOrThrow(repsCol));
        String weight = cursor.getString(cursor.getColumnIndexOrThrow(weightCol));

        if (reps != null) {
            Exercise exercise = new Exercise(name, null);
            exercise.setActualSets(sets);
            exercise.setActualReps(Integer.valueOf(reps));
            exercise.setActualWeight(weight);
            return exercise;
        } else {
            return null;
        }
    }

    private String[] getColumnNames(String columnStart, int x) {
        StringBuilder builder = new StringBuilder(columnStart);

        String exerciseNameColumn = builder.append("_Name").toString();
        builder = new StringBuilder(columnStart);


        String exerciseRepsColumn = builder.append("_Set").append(x).append("_Reps")
                .append(x).toString();
        builder = new StringBuilder(columnStart);

        String exerciseWeightColumn = builder.append("_Set").append(x).append("_Weight")
                .append(x).toString();

        String[] columns = new String[3];
        columns[NAME_COLUMN] = exerciseNameColumn;
        columns[REPS_COLUMN] = exerciseRepsColumn;
        columns[WEIGHT_COLUMN] = exerciseWeightColumn;
        return columns;
    }

    private void getSubWorkoutData(Cursor cursor, List<SubWorkout> subWorkoutList
            , List<WorkoutExercise> workoutData, int[] data) {
        int totalSets, totalReps, totalWeight;
        totalSets = data[TOTAL_SETS];
        totalReps = data[TOTAL_REPS];
        totalWeight = data[TOTAL_WEIGHT];

        String subName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBWORKOUT));
        if (subName != null) {
            SubWorkout subWorkout = new SubWorkout(subName,null);
            subWorkout.setWorkoutData(workoutData);
            subWorkout = getPartialSubWorkout(cursor, subWorkout);
            subWorkout.setTotalSets(totalSets);
            subWorkout.setTotalReps(totalReps);
            String unitOfMeas = data[UNIT_OF_MEAS] == 1 ? "lbs" : "kgs";
            subWorkout.setTotalWeight(String.valueOf(totalWeight) + unitOfMeas);
            subWorkoutList.add(subWorkout);
        }
    }

    private SubWorkout getPartialSubWorkout(Cursor cursor, SubWorkout subWorkout) {
        String mainName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAINWORKOUT));
        subWorkout.setMainWorkoutName(mainName);
        String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
        subWorkout.setDate(date);
        return subWorkout;
    }
}
