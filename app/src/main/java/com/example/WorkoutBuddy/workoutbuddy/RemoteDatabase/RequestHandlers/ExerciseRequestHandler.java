package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.TableManagers;

import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandler;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.WorkoutBuddyAPI;

//rethink name
public class ExerciseRequestHandler {//needs to be async task or handled in an async task


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
