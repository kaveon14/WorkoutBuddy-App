package com.example.kaveon14.workoutbuddy.Data;

import java.util.List;
import java.util.Map;

public class Workout {

    private String workoutName;
    private List<String> exerciseList;
    private Map<String,String> exerciseSets;
    private Map<String,String> exerciseReps;

    public Workout(String workoutName,List<String> exerciseList,
                   Map<String,String> exerciseSets,Map<String,String> exerciseReps) {
        this.workoutName = workoutName;
        this.exerciseList = exerciseList;
        this.exerciseSets = exerciseSets;
        this.exerciseReps = exerciseReps;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public List<String> getExerciseList() {
        return exerciseList;
    }

    public Map<String,String> getExerciseSets() {
        return exerciseSets;
    }

    public Map<String,String> getExerciseReps() {
        return exerciseReps;
    }

}
