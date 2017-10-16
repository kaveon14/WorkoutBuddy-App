package com.example.kaveon14.workoutbuddy.RemoteDatabase;

import java.io.Serializable;
import java.text.DateFormat;

public class DefaultExercise {

    private String exerciseName;

    public DefaultExercise(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() {
        return exerciseName;
    }
}
