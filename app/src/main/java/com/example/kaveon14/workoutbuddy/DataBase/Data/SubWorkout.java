package com.example.kaveon14.workoutbuddy.DataBase.Data;

import java.util.List;
import java.util.Map;

public class SubWorkout {

    private String subWorkoutName;
    private List<Exercise> exerciseList;
    private Map<String,String> exerciseSets;
    private Map<String,String> exerciseReps;

    public SubWorkout(String subWorkoutName, List<Exercise> exerciseList,
                      Map<String,String> exerciseSets, Map<String,String> exerciseReps) {
        this.subWorkoutName = subWorkoutName;
        this.exerciseList = exerciseList;
        this.exerciseSets = exerciseSets;
        this.exerciseReps = exerciseReps;
    }

    public String getSubWorkoutName() {
        return subWorkoutName;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public Map<String,String> getExerciseSets() {
        return exerciseSets;
    }

    public Map<String,String> getExerciseReps() {
        return exerciseReps;
    }

}
