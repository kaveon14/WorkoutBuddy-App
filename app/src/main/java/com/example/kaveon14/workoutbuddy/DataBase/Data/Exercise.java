package com.example.kaveon14.workoutbuddy.DataBase.Data;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

public  class Exercise {

    private String exerciseName;
    private String exerciseDescription;
    private String goalReps;
    private String goalSets;
    private String goalWeight;
    private Bitmap exerciseImage;
    private int actualSets;//delete
    private int actualReps;//delete
    private String actualWeight;//delete

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

    public void setGoalWeight(String weight) {
        this.goalWeight = weight;
    }

    public void setActualSets(int actualSets) {
        this.actualSets = actualSets;
    }//delete

    public void setActualReps(int actualReps) {
        this.actualReps = actualReps;
    }//delete

    public void setActualWeight(String actualWeight) {
        this.actualWeight = actualWeight;
    }//delete

    public void setExerciseImage(Bitmap exerciseImage) {
        this.exerciseImage = exerciseImage;
    }



    public String getGoalReps() {
        return goalReps;
    }

    public String getGoalSets() {
        return goalSets;
    }

    public String getGoalWeight() {
        return goalWeight;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public int getActualSets() {
        return actualSets;
    }//delete

    public int getActualReps() {
        return actualReps;
    }//delete

    public String getActualWeight() {
        return actualWeight;
    }//delete

    public Bitmap getExerciseImage() {
        return exerciseImage;
    }
}