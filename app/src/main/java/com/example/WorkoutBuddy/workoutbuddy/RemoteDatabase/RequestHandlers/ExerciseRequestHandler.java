package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ExerciseApi;

public class ExerciseRequestHandler {

    public String sendGetDefaultExerciseRequest() {
        return new RequestHandler().sendGetRequest(ExerciseApi.getGetDefaultExerciseUrl());
    }

    public String sendGetCustomExerciseRequest() {//wrong should return all custom exercise not the ones for a sub workout
        return new RequestHandler().sendGetRequest(ExerciseApi.getGetCustomExerciseUrl());
    }

    public String sendGetAllExercisesRequest() {
        return new RequestHandler().sendGetRequest(ExerciseApi.getGetAllExercisesUrl());
    }
}
