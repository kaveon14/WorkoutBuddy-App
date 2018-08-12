package com.example.WorkoutBuddy.workoutbuddy.DataBase.Data;

import android.support.annotation.Nullable;
import java.util.List;

public class MainWorkout {

    private String mainWorkoutName;
    private List<SubWorkout> subWorkouts;
    private long rowId;

    public MainWorkout(String mainWorkoutName,@Nullable List<SubWorkout> subWorkouts) {
        this.mainWorkoutName = mainWorkoutName;
        this.subWorkouts = subWorkouts;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getMainWorkoutName() {
        return mainWorkoutName;
    }

    public List<SubWorkout> getSubWorkoutsList() {
        return subWorkouts;
    }

    public long getRowId() {
        return rowId;
    }
}
