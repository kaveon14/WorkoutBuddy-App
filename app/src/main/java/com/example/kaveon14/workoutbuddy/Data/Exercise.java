package com.example.kaveon14.workoutbuddy.Data;

import android.support.annotation.Nullable;
import java.util.List;
import java.util.Map;

public final class Exercise {//TODO most of the swtuff will be deleted
    // TODO use exerciuse ibject to get name,and description
    private String exerciseName;
    private String exerciseDescripion;
    private Map<String,String> exerciseMap;
    private List<String> exerciseList;
    //nullable just for now until exercise descriptions created
    public Exercise(String exerciseName,@Nullable String exerciseDescription) {
        this.exerciseName = exerciseName;
        this.exerciseDescripion = exerciseDescription;
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