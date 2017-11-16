package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import android.content.Context;
import android.graphics.Bitmap;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.Exercise;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ExerciseApi;
//may take a list
public class ExerciseRequestHandler {//needs to be async task or handled in an async task

    public String sendGetDefaultExerciseRequest() {
        return new RequestHandler().sendGetRequest(ExerciseApi.getGetDefaultExerciseUrl());
    }

    public String sendGetCustomExerciseRequest() {//wrong should return all custom exercise not the ones for a sub workout
        return new RequestHandler().sendGetRequest(ExerciseApi.getGetCustomExerciseUrl());
    }

    public String sendGetAllExercisesRequest() {
        return new RequestHandler().sendGetRequest(ExerciseApi.getGetAllExercisesUrl());
    }

    //need to get the image name from the custom exercise(so either check online or check local database)
    //when u pull in new custom exercises,store there image path also
    //when downloading image first check if file exists if not download image
    public void sendGetExerciseImageRequest(final String imageName,Context context,Exercise exercise) {//may ned new name or location
        FileDownloadRequest request = new FileDownloadRequest(context);
        String wtf = ExerciseApi.getDownloadExercisePhotoUrl(imageName);
        System.out.println(wtf);
        ImageRequest imageRequest = new ImageRequest(ExerciseApi.getDownloadExercisePhotoUrl(imageName),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        exercise.setExerciseImage(response);
                        System.out.println("Yes it worked");
                        new ExerciseTable(context).addAnExercise(exercise);
                    }
                }, 500, 500, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("DID NOT WORK");
                System.out.println(error.networkResponse);
            }
        });//play with this some more
        request.addToRequestQueue(imageRequest);
    }
}
