package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;
// TODO add new columns to store total reps,sets and weight
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_DATE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_MAINWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.COLUMN_SUBWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.LiftData.TABLE_NAME;

public class LiftingStatsTable extends TableManager {//possibly change actual table name

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;
    private final int TOTAL_REPS=0,TOTAL_WEIGHT=1,TOTAL_SETS=2,UNIT_OF_MEAS=3;
    private final int NAME_COLUMN=0,REPS_COLUMN=1,WEIGHT_COLUMN=2;

    public LiftingStatsTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        setTableName(TABLE_NAME);
    }

    public void addAWorkout(List<Exercise> exerciseList,SubWorkout subWorkout) {//change name
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String mainWorkoutName = subWorkout.getMainWorkoutName();
        String subWorkoutName = subWorkout.getSubWorkoutName();

        values.put(COLUMN_MAINWORKOUT,mainWorkoutName);
        values.put(COLUMN_SUBWORKOUT,subWorkoutName);
        values.put(COLUMN_DATE,subWorkout.getDate());
        values = addExerciseData(values,exerciseList);
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    public void addWorkoutData(List<WorkoutExercise> workouts, SubWorkout subWorkout) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String mainWorkoutName = subWorkout.getMainWorkoutName();
        String subWorkoutName = subWorkout.getSubWorkoutName();

        values.put(COLUMN_MAINWORKOUT,mainWorkoutName);
        values.put(COLUMN_SUBWORKOUT,subWorkoutName);
        values.put(COLUMN_DATE,subWorkout.getDate());

        int exNum = 1;
        for(WorkoutExercise w : workouts) {
            values = dd(values, w,exNum);
            exNum++;
        }
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    private ContentValues dd(ContentValues values, WorkoutExercise workout,int exNum) {//the new add a workout
        Map<String,String> data = workout.getWorkoutData();
        int setIncrement=1;

        String exCol = "Exercise" + exNum;
        String[] columns = getColumnNames(exCol,setIncrement);
        for (int x = 0; x < data.size(); x++) {
            String SET = "Set "+ (x + 1);
            int reps = workout.getReps(SET);
            String[] weightData = workout.getWeight(SET);
            String weight = weightData[WorkoutExercise.WEIGHT] +
                    weightData[WorkoutExercise.UNIT_OF_MEAS];

            values.put(columns[NAME_COLUMN],workout.getExerciseName());
            values.put(columns[REPS_COLUMN],reps);
            values.put(columns[WEIGHT_COLUMN],weight);
        }
        return values;
    }

    private ContentValues addExerciseData(ContentValues values, List<Exercise> exerciseList) {
        int z=1;int set = 1;
        Exercise exercise = exerciseList.get(0);
        while(z <= 15) {
            String columnNamePart1 = "Exercise" + z;
            try {
                int index = getExerciseIndex(exerciseList, exercise);
                int base = getBaseIndex(exercise);
                for (int x = index - base; x <= index; x++) {
                     exercise = exerciseList.get(x);
                    String[] columns = getColumnNames(columnNamePart1,set);

                    values.put(columns[NAME_COLUMN],exercise.getExerciseName());
                    values.put(columns[REPS_COLUMN],exercise.getActualReps());
                    values.put(columns[WEIGHT_COLUMN],exercise.getActualWeight());
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

    private int getBaseIndex(Exercise exercise) {
        return exercise.getActualSets();
    }

    public List<SubWorkout> getCompletedWorkouts() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, COLUMN_DATE + " DESC");
        List<Exercise> exerciseList = new ArrayList<>(100);
        List<SubWorkout> subWorkoutList = new ArrayList<>(100);
        while (cursor.moveToNext()) {
            int[] data = getExerciseData(cursor,exerciseList);
            getSubWorkoutData(cursor,subWorkoutList,exerciseList,data);
            exerciseList = new ArrayList<>(100);
        }
        cursor.close();
        readableDatabase.close();
        return subWorkoutList;
    }

    private int[] getExerciseData(Cursor cursor, List<Exercise> exerciseList) {//will be vastly reduced
        int totalSets = 0,totalReps = 0,totalWeight = 0,unitOfMeas = 0,sets = 0;
        for(int z=1;z<=15;z++) {
            String columnNamePart1 = "Exercise" + z;
            for (int x = 1; x <= 10; x++) {
                String[] columns = getColumnNames(columnNamePart1,x);
                String exerciseName = cursor.getString(cursor
                        .getColumnIndexOrThrow(columns[NAME_COLUMN]));
                if (exerciseName != null) {
                    Exercise exercise = getExercise(cursor, columns[REPS_COLUMN],
                            columns[WEIGHT_COLUMN], exerciseName,x);//delete

                    totalReps += exercise.getActualReps();//delete
                    int index = exercise.getActualWeight().length();//delete
                    String substring = exercise.getActualWeight().substring(index - 3,index);//maybe delete
                    String unitOfMeasurement = substring.equalsIgnoreCase("lbs") ? "lbs" : "kgs";//maybe delete
                    unitOfMeas = unitOfMeasurement.equalsIgnoreCase("lbs") ? 1 : 0;

                    String weight = exercise.getActualWeight()//delete
                            .replace(unitOfMeasurement, "").trim();//delete
                    totalWeight += Integer.valueOf(weight);//delete
                    exerciseList.add(exercise);//delete
                    sets = x;
                }
            }
            totalSets += sets;//delete
            sets = 0;//delete
        }
        int[] data = new int[4];//delete
        data[TOTAL_SETS] = totalSets;//delete
        data[TOTAL_REPS] = totalReps;//delete
        data[TOTAL_WEIGHT] = totalWeight;//delete
        data[UNIT_OF_MEAS] = unitOfMeas;//delete
        return data;
    }

    private String[] getColumnNames(String columnNamePart1, int x) {
        StringBuilder builder = new StringBuilder(columnNamePart1);

        String exerciseNameColumn = builder.append("_Set" + x + "_Name" + x).toString();
        builder = new StringBuilder(columnNamePart1);


        String exerciseRepsColumn = builder.append("_Set" + x + "_Reps" + x).toString();
        builder = new StringBuilder(columnNamePart1);

        String exerciseWeightColumn = builder.append("_Set" + x + "_Weight" + x).toString();

        String[] columns = new String[3];
        columns[NAME_COLUMN] = exerciseNameColumn;
        columns[REPS_COLUMN] = exerciseRepsColumn;
        columns[WEIGHT_COLUMN] = exerciseWeightColumn;
        return columns;
    }

    private void getSubWorkoutData(Cursor cursor, List<SubWorkout> subWorkoutList
            ,@Nullable List<Exercise> exerciseList, int[] data) {// will be vastly changed
        int totalSets,totalReps,totalWeight;
        totalSets = data[TOTAL_SETS];//delete
        totalReps = data[TOTAL_REPS];//delete
        totalWeight = data[TOTAL_WEIGHT];//delete

        String subName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBWORKOUT));
        if(subName != null) {
            SubWorkout subWorkout = getSubWorkout(cursor,exerciseList,subName);
            subWorkout.setTotalSets(totalSets);//delete
            subWorkout.setTotalReps(totalReps);//delete
            String unitOfMeas = data[UNIT_OF_MEAS] == 1 ? "lbs" : "kgs";//delete
            subWorkout.setTotalWeight(String.valueOf(totalWeight)+unitOfMeas);//delete
            subWorkoutList.add(subWorkout);
        }
    }

    private Exercise getExercise(Cursor cursor,String repsColumn,String weightColumn,String name,
                                 int sets) {//will most likely be deleted
        String reps = cursor.getString(cursor.getColumnIndexOrThrow(repsColumn));
        String weight = cursor.getString(cursor.getColumnIndexOrThrow(weightColumn));

        Exercise exercise = new Exercise(name,null);
        exercise.setActualSets(sets);//does not most likely the actual sets
        exercise.setActualReps(Integer.valueOf(reps));
        exercise.setActualWeight(weight);
        return exercise;
    }

    private SubWorkout getSubWorkout(Cursor cursor, @Nullable List<Exercise> exerciseList, String subName) {
        String mainName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAINWORKOUT));
        SubWorkout subWorkout = new SubWorkout(subName,exerciseList);
        subWorkout.setMainWorkoutName(mainName);
        String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
        subWorkout.setDate(date);
        return subWorkout;
    }
}/*setIncrement = 1;
        /*while(z <= 15) {//unloop this
            String columnNamePart1 = "Exercise" + z;
            for (int x = 0; x < data.size(); x++) {
                String SET = "Set "+ x + 1;
                int reps = workout.getReps(SET);
                String[] weightData = workout.getWeight(SET);
                String weight = weightData[WorkoutExercise.WEIGHT] +
                        weightData[WorkoutExercise.UNIT_OF_MEAS];

                String[] cols = getColumnNames(columnNamePart1,setIncrement);
                values.put(cols[NAME_COLUMN],workout.getExerciseName());
                values.put(cols[REPS_COLUMN],reps);
                values.put(cols[WEIGHT_COLUMN],weight);
            }
            z++;
            setIncrement = 1;
        }*/
