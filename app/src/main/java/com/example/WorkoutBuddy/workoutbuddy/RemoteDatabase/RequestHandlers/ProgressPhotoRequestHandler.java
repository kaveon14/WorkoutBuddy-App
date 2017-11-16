package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers;


import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.ProgressPhotosTable;
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

    public void sendGetProgressPhotoRequest(final String imageName, Context context) {
        FileDownloadRequest request = new FileDownloadRequest(context);
        ImageRequest imageRequest = new ImageRequest(ProgressPhotoApi.getDownloadProgressPhotoUrl(imageName),
                new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {//this one is fucking working
                //photo.
                System.out.println("Workoing");
                new ProgressPhotosTable(context).addProgressPhoto("2017-12-12 17:35:22","create path",response);
            }
        },0,0,null,null);//play with this some more
        request.addToRequestQueue(imageRequest);
    }
}
