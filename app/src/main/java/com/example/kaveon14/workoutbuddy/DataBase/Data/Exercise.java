package com.example.kaveon14.workoutbuddy.DataBase.Data;

import android.support.annotation.Nullable;

public final class Exercise {

    private String exerciseName;
    private String exerciseDescripion;
    private String exerciseReps;
    private String exerciseSets;

    public Exercise(String exerciseName,@Nullable String exerciseDescription) {
        this.exerciseName = exerciseName;
        this.exerciseDescripion = exerciseDescription;
    }

    public void setExerciseReps(String repsRange) {
        this.exerciseReps = repsRange;
    }

    public void setExerciseSets(String sets) {
        this.exerciseSets = sets;
    }

    public String getExerciseReps() {
        return exerciseReps;
    }

    public String getExerciseSets() {
        return exerciseSets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseDescripion() {
        return exerciseDescripion;
    }
}