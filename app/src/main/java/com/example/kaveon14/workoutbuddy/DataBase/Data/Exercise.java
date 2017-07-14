package com.example.kaveon14.workoutbuddy.DataBase.Data;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

public final class Exercise {

    private String exerciseName;
    private String exerciseDescription;
    private String exerciseReps;
    private String exerciseSets;
    private Bitmap exerciseImage;
    private int actualSets;
    private int actualReps;
    private String actualWeight;

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

    public void setActualSets(int actualSets) {
        this.actualSets = actualSets;
    }

    public void setActualReps(int actualReps) {
        this.actualReps = actualReps;
    }

    public void setActualWeight(String actualWeight) {
        this.actualWeight = actualWeight;
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

    public int getActualSets() {
        return actualSets;
    }

    public int getActualReps() {
        return actualReps;
    }

    public String getActualWeight() {
        return actualWeight;
    }

    public Bitmap getExerciseImage() {
        return exerciseImage;
    }
}