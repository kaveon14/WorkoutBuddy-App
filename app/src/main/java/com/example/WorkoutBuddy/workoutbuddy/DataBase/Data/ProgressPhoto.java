package com.example.WorkoutBuddy.workoutbuddy.DataBase.Data;

import android.graphics.Bitmap;

public class ProgressPhoto {

    private String dateString;
    private Bitmap progressPhoto;

    public ProgressPhoto(String date,Bitmap progressPhoto) {
        this.dateString = date;
        this.progressPhoto = progressPhoto;
    }

    public Bitmap getProgressPhoto() {
        return progressPhoto;
    }

    public String getDate() {
        return dateString;
    }

}
