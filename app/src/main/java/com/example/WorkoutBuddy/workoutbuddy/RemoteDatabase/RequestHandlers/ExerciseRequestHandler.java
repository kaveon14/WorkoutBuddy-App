package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ExerciseApi;
//may take a list
public class ExerciseRequestHandler {//needs to be async task or handled in an async task

    public String sendGetDefaultExerciseRequest() {
        return new RequestHandler().sendGetRequest(ExerciseApi.GET_DEFAULT_EXERCISE_URL);
    }

    public String sendGetCustomExerciseRequest(final long userId) {//wrong should return all custom exercise not the ones for a sub workout
        return new RequestHandler().sendGetRequest(ExerciseApi.GET_CUSTOM_EXERCISE_URL+userId);
    }

    public String sendGetAllExercisesRequest(final long userId) {
        return new RequestHandler().sendGetRequest(ExerciseApi.GET_ALL_EXERCISES_URL+userId);
    }

    public void sendGetExerciseImageRequest(final String imageName,Context context) {//should this return a bitmap>\
        FileDownloadRequest request = new FileDownloadRequest(context);
        /*ImageRequest imageRequest = new ImageRequest("", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

            }
        },0,0,null,null);//play with this some more
        request.addToRequestQueue(imageRequest);*/
    }

}
