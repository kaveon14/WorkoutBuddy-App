package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;


import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ProgressPhotoApi;

import java.io.IOException;
import java.util.HashMap;

public class ProgressPhotoRequestHandler extends RequestHandler {

    public String sendGetProgressPhotoPathRequest(final long userID) {
        return new RequestHandler().sendGetRequest(ProgressPhotoApi.GET_PROGRESS_PHOTO_PATH+userID);
    }

    public String sendPostImageRequest(String path,String date_time) {
        try {
            HashMap<String,String> map = new HashMap<>();
            map.put("userId", CoreAPI.getUserId()+"");
            map.put("date_time",date_time);
            map.put("local_path",path);
            String[] strings = path.split("/");
            map.put("file_name",strings[strings.length-1]);
            new RequestHandler().sendPostRequest(ProgressPhotoApi.UPLOAD_PHOTO_URL,map);
            return new RequestHandler().sendPostFileRequest(ProgressPhotoApi.UPLOAD_PHOTO_URL,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return "";
    }
}
