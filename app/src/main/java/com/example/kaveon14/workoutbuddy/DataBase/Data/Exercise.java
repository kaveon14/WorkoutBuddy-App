package com.example.kaveon14.workoutbuddy.DataBase.Data;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

public final class Exercise {

    private String exerciseName;
    private String exerciseDescription;
    private String exerciseReps;
    private String exerciseSets;
    private Bitmap exerciseImage;

    public Exercise(String exerciseName,@Nullable String exerciseDescription) {
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
    }

    public void setExerciseReps(String repsRange) {
        this.exerciseReps = repsRange;
    }

    public void setExerciseSets(String sets) {
        this.exerciseSets = sets;
    }

    public void setExerciseImage(Bitmap exerciseImage) {
        this.exerciseImage = exerciseImage;
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

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public Bitmap getExerciseImage() {
        return exerciseImage;
    }
}