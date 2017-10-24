package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.ApiConstants.WorkoutBuddyAPI;
//may take a list
public class ExerciseRequestHandler {//needs to be async task or handled in an async task

    public static final JSON_EXERCISE_NAME = "exercise_name";
    public static final JSON_EXERCISE_DESCRIPTION = "exercise_description";

    public String sendGetDefaultExerciseRequest() {
        return new RequestHandler().sendGetRequest(WorkoutBuddyAPI.GET_DEFAULT_EXERCISE_URL);
    }

    public String sendGetCustomExerciseRequest(final int userId) {//wrong should return all custom exercise not the ones for a sub workout
        return new RequestHandler().sendGetRequest(WorkoutBuddyAPI.GET_CUSTOM_EXERCISE_URL+userId);
    }

    public String sendGetAllExercisesRequest(final int userId) {
        return new RequestHandler().sendGetRequest(WorkoutBuddyAPI.GET_ALL_EXERCISES_URL+userId);
    }
}
