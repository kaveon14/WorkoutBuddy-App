package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;

public class WorkoutRequestHandler {


    public String sendGetMainWorkoutsRequest(final int userID) {
        return new RequestHandler().sendGetRequest(null);
    }

    public String sendGetSubWorkoutRequest(final int subWorkoutId) {
        return new RequestHandler().sendGetRequest(CoreAPI.GET_SUBWORKOUT_EXERCISES_URL+subWorkoutId);
    }

}
