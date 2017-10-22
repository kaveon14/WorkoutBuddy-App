package com.example.WorkoutBuddy.workoutbuddy.DataBase.Data;

import java.util.List;

public class SubWorkout {

    private String mainWorkoutName;
    private String subWorkoutName;
    private String date;
    private int totalReps;
    private int totalSets;
    private String totalWeight;
    private List<WorkoutExercise> workoutData;

    public SubWorkout() {

    }

    public SubWorkout(String subWorkoutName) {
        this.subWorkoutName = subWorkoutName;
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

    public void setWorkoutData(List<WorkoutExercise> workoutData) {
        this.workoutData = workoutData;
    }

    public String getMainWorkoutName() {
        return mainWorkoutName;
    }

    public String getSubWorkoutName() {
        return subWorkoutName;
    }

    public String getDate() {
        return date;
    }

    public int getTotalReps() {
        return totalReps;
    }

    public int getTotalSets() {//delete
        return totalSets;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public List<WorkoutExercise> getWorkoutData() {
        return workoutData;
    }
}
