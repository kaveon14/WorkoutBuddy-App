package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.BodyApi;

public class BodyDataRequestHandler {

    public String sendGetBodyDataRequest(final long userId) {
        return new RequestHandler().sendGetRequest(BodyApi.GET_BODY_DATA_URL+userId);
    }

}
