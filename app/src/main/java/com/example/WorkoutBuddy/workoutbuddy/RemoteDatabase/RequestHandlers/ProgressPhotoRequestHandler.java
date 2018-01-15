package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;

import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ProgressPhotoApi;

import java.io.IOException;
import java.util.HashMap;

public class ProgressPhotoRequestHandler extends RequestHandler {

    public String sendGetProgressPhotoPathRequest() {
        return new RequestHandler().sendGetRequest(ProgressPhotoApi.getGetProgressPhotoPathUrl());
    }

    public String sendPostImageRequest(String path,String date_time) {
        try {
            HashMap<String,String> map = new HashMap<>();
            map.put("userId", CoreAPI.getUserId()+"");
            map.put("date_time",date_time);
            map.put("local_path",path);
            String[] strings = path.split("/");
            map.put("file_name",strings[strings.length-1]);
            new RequestHandler().sendPostRequest(ProgressPhotoApi.getUploadPhotoUrl(),map);
            return new RequestHandler().sendPostFileRequest(ProgressPhotoApi.getUploadPhotoUrl(),path);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return "";
    }
}
