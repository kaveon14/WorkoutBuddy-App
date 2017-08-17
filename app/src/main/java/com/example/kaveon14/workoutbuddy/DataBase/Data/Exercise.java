package com.example.kaveon14.workoutbuddy.DataBase.Data;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

public  class Exercise {

    private String goalReps;
    private String goalSets;
    private String exerciseName;
    private Bitmap exerciseImage;
    private String exerciseDescription;

    public Exercise() {

    }

    public Exercise(String exerciseName,@Nullable String exerciseDescription) {
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
    }

    public void setGoalReps(String repsRange) {
        this.goalReps = repsRange;
    }

    public void setGoalSets(String sets) {
        this.goalSets = sets;
    }

    public void setExerciseImage(Bitmap exerciseImage) {
        this.exerciseImage = exerciseImage;
    }

    public String getGoalReps() {
        return goalReps;
    }

    public String getGoalSets() {
        return goalSets;
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