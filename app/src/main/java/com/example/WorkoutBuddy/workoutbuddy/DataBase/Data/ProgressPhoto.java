package com.example.WorkoutBuddy.workoutbuddy.DataBase.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ProgressPhoto {

    private String dateString;
    private Bitmap progressPhoto;
    private String photoPath;

    public ProgressPhoto(String date,Bitmap progressPhoto) {
        this.dateString = date;
        this.progressPhoto = progressPhoto;
    }

    public ProgressPhoto(String date,String photoPath) {
        this.dateString = date;
        this.photoPath = photoPath;
    }

    public Bitmap getProgressPhoto() {
        if(progressPhoto != null) {
            return progressPhoto;
        }
        return BitmapFactory.decodeFile(photoPath);
    }

    public String getDate() {
        return dateString;
    }


}
