package com.example.kaveon14.workoutbuddy.DataBase.Data;

import android.support.annotation.Nullable;
import java.util.List;
import java.util.Map;

public final class Exercise {

    private String exerciseName;
    private String exerciseDescripion;
    private Map<String,String> exerciseMap;
    private List<String> exerciseList;
    private String exerciseReps;
    private String exerciseSets;
    //nullable just for now until exercise descriptions created

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

    public List<String> getExerciseList() {
        return exerciseList;
    }

    public Map<String,String> getExerciseMap() {
        return exerciseMap;
    }

}