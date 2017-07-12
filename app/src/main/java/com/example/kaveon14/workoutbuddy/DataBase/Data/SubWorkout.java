package com.example.kaveon14.workoutbuddy.DataBase.Data;
// TODO implement the actual main workout name into this
import android.support.annotation.Nullable;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SubWorkout {

    private String mainWorkoutName;
    private String subWorkoutName;
    private String date;
    private List<Exercise> exerciseList;
    private int totalReps;
    private int totalSets;
    private String totalWeight;

    public SubWorkout() {

    }

    public SubWorkout(String subWorkoutName,@Nullable List<Exercise> exerciseList) {
        this.subWorkoutName = subWorkoutName;
        this.exerciseList = exerciseList;

    }

    public SubWorkout setMainWorkoutName(String mainWorkoutName) {
        this.mainWorkoutName = mainWorkoutName;
        return this;
    }

    public SubWorkout setDate(String date) {
        this.date = date;
        return this;
    }

    public void setTotalReps(int totalReps) {
        this.totalReps = totalReps;
    }

    public void setTotalSets(int totalSets) {
        this.totalSets = totalSets;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getMainWorkoutName() {
        return mainWorkoutName;
    }

    public String getSubWorkoutName() {
        return subWorkoutName;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public String getDate() {
        return date;
    }

    public int getTotalReps() {
        return totalReps;
    }

    public int getTotalSets() {
        return totalSets;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public List<Exercise> addExercise(Exercise exercise) {
        exerciseList.add(exercise);
        return exerciseList;
    }

    public Map<String,String> getExerciseSets() {
        Map<String,String> exerciseSets = new Hashtable<>();
        for(Exercise exercise : exerciseList) {
            exerciseSets.put(exercise.getExerciseName(),exercise.getExerciseSets());
        }
        return exerciseSets;
    }

    public Map<String,String> getExerciseReps() {
        Map<String,String> exerciseReps = new Hashtable<>();
        for(Exercise exercise : exerciseList) {
            exerciseReps.put(exercise.getExerciseName(),exercise.getExerciseReps());
        }
        return exerciseReps;
    }
}
