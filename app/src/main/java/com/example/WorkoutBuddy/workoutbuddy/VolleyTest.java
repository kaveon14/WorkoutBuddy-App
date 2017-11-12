package com.example.WorkoutBuddy.workoutbuddy;

import android.graphics.Bitmap;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.WorkoutBuddy.workoutbuddy.Activities.MainActivity;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.ProgressPhotosTable;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ProgressPhotoApi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//TODO SEnd file name and to php url

public class VolleyTest {//already got the file name r would not be able to download it

    MainActivity mainActivity;
    String url = ProgressPhotoApi.DOWNLOAD_PHOTO_URL + "calf.jpg";//downloadProgressPhotosTest


    public VolleyTest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void downloadCustomExercisemageTest() {

    }

    public void downloadProgressPhotosTest() {
        ProgressPhotosTable table = new ProgressPhotosTable(mainActivity.getApplicationContext());
        List<String> paths = table.getColumn(DataBaseContract.ProgressPhotos.COLUMN_PHOTO_PATH);
        for (String path : paths) {
            System.out.println(path);
        }
        String path = "/storage/emulated/0/Android/data/com.example.WorkoutBuddy.workoutbuddy/files/Pictures/calf.jpg";


        RequestQueue requestQueue = Volley.newRequestQueue(mainActivity.getApplicationContext());

        url = "http:/10.100.69.171/WorkoutBuddy_Scripts/FileDownloading/DownloadProgressPhoto.php?file_name=calf.jpg";

        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {//bytes in here just save to file location and done
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path);
                    response.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
                    String date = dateFormat.format(new Date());
                    table.addProgressPhoto(date, path,response);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               System.out.println(error.networkResponse);
            }
        });

        requestQueue.add(request);
        List<String> pats = table.getColumn(DataBaseContract.ProgressPhotos.COLUMN_PHOTO_PATH);
        for(String pah : pats) {
            System.out.println(pah);
        }
    }

}
