package com.example.kaveon14.workoutbuddy.DataBase;
//implement were nessaccry
// Todo possibly change workourtData to a list
import android.graphics.Bitmap;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;

import java.util.Map;

public class WorkoutExercise extends Exercise {

    private Exercise mExercise;
    private Map<String,String> workoutData;


    public static final int WEIGHT = 0;
    public static final int UNIT_OF_MEAS = 1;

    public WorkoutExercise() {}

    public WorkoutExercise(Exercise mExercise) {
        this.mExercise = mExercise;

    }

    @Override
    public String getExerciseName() {
        return mExercise.getExerciseName();
    }

    @Override
    public String getExerciseDescription() {
        return mExercise.getExerciseDescription();
    }

    @Override
    public Bitmap getExerciseImage() {
        return mExercise.getExerciseImage();
    }

    @Override
    public String getGoalSets() {
        return mExercise.getGoalSets();
    }

    @Override
    public String getGoalReps() {
        return mExercise.getGoalReps();
    }

    @Override
    public String getGoalWeight() {
        return mExercise.getGoalWeight();
    }

    public void setWorkoutData(Map<String,String> workoutData) {
        this.workoutData = workoutData;
    }

    public Map<String,String> getWorkoutData() {
        return workoutData;
    }

    public int getTotalSets() {
        return workoutData.size();
    }

    public int getReps(String set) {
        String data = workoutData.get(set);
        int index = data.indexOf("/");
        String reps = data.substring(0,index).trim();
        return Integer.valueOf(reps);
    }

    public String[] getWeight(String set) {
        String setData = workoutData.get(set);
        int index = setData.indexOf("/") + 1;
        String uOfm = setData.contains("lbs") ? "lbs" : "kgs";
        String weight = setData.substring(index,setData.length()-3).trim();

        String[] data = new String[2];
        data[WEIGHT] = weight;
        data[UNIT_OF_MEAS] = uOfm;
        return data;
    }

    public int getTotalReps() {
        int totalReps = 0;
        for(int x = 1;x<=workoutData.size();x++) {
            String data = workoutData.get("Set "+x);
            int index = data.indexOf("/");
            String reps = data.substring(0,index).trim();
            totalReps += Integer.valueOf(reps);
        }
        return totalReps;
    }

    public String[]  getTotalWeight() {
        int totalWeight = 0;
        String uOfm = workoutData.get("Set 1").contains("lbs") ? "lbs" : "kgs";
        for(int x  = 1;x<=workoutData.size();x++) {
            String data = workoutData.get("Set "+x);
            int index = data.indexOf("/") + 1;
            String weight = data.substring(index,data.length()-3).trim();
            totalWeight += Integer.valueOf(weight);
        }

        String[] data = new String[2];
        data[WEIGHT] = String.valueOf(totalWeight);
        data[UNIT_OF_MEAS] = uOfm;
        return data;
    }


}
