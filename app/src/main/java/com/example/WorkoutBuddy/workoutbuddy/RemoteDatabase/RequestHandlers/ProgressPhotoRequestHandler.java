package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;


import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ProgressPhotoApi;

import java.io.IOException;

public class ProgressPhotoRequestHandler extends RequestHandler {

    public String sendGetProgressPhotoPathRequest(final long userID) {
        return new RequestHandler().sendGetRequest(ProgressPhotoApi.GET_PROGRESS_PHOTO_PATH+userID);
    }

    public String sendPostImageRequest(String path) {
        try {
            return new RequestHandler().sendPostFileRequest(ProgressPhotoApi.UPLOAD_PHOTO_URL,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return "";
    }



}
