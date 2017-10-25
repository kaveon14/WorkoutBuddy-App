package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.WorkoutApi;

public class WorkoutRequestHandler {


    public String sendGetMainWorkoutsRequest(final long userID) {
        return new RequestHandler().sendGetRequest(WorkoutApi.GET_MAINWORKOUTS_URL+userID);
    }

    public String sendGetSubWorkoutsRequest(final long mainWorkoutId) {
        return new RequestHandler().sendGetRequest(WorkoutApi.GET_SUBWORKOUTS_URL+mainWorkoutId);
    }

    public String sendGetSubWorkoutExercisesRequest(final long subWorkoutId) {
        return new RequestHandler().sendGetRequest(CoreAPI.GET_SUBWORKOUT_EXERCISES_URL+subWorkoutId);
    }

}
