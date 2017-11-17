package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import android.content.Context;
import android.graphics.Bitmap;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.Exercise;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.ExerciseTable;
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

    //need better name or possibly be moved altogether
    public void sendGetExerciseImageRequest(final String imageName,Context context,Exercise exercise) {//may ned new name or location
        FileDownloadRequest request = new FileDownloadRequest(context);
        String wtf = ExerciseApi.getDownloadExercisePhotoUrl(imageName);
        System.out.println(wtf);
        ImageRequest imageRequest = new ImageRequest(ExerciseApi.getDownloadExercisePhotoUrl(imageName),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        exercise.setExerciseImage(response);
                        new ExerciseTable(context).addAnExercise(exercise);
                    }
                }, 500, 500, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.networkResponse);
            }
        });
        request.addToRequestQueue(imageRequest);
    }
}
