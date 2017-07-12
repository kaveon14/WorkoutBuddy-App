package com.example.kaveon14.workoutbuddy.DataBase.Data;

import java.util.List;

public class Workout {

    private List<Exercise> exerciseData;

    public Workout(List<Exercise> exerciseData) {
        this.exerciseData = exerciseData;
    }

    public List<Exercise> getExerciseData() {
        return exerciseData;
    }
}
