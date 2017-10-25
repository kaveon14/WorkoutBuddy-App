package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.WorkoutApi;

public class WorkoutRequestHandler {


    public String sendGetMainWorkoutsRequest(final int userID) {
        return new RequestHandler().sendGetRequest(WorkoutApi.GET_MAINWORKOUTS_URL+userID);
    }

    public String sendGetSubWorkoutRequest(final int subWorkoutId) {
        return new RequestHandler().sendGetRequest(CoreAPI.GET_SUBWORKOUT_EXERCISES_URL+subWorkoutId);
    }

}
