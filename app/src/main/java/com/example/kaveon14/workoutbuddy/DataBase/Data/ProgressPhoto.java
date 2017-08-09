package com.example.kaveon14.workoutbuddy.DataBase.Data;

import android.graphics.Bitmap;
import java.util.Date;

public class ProgressPhoto {

    private String dateString;
    private Date dateForSorting;
    private Bitmap progressPhoto;

    public ProgressPhoto(String date,Bitmap progressPhoto) {
        this.dateString = date;
        dateForSorting = new Date(date);
        this.progressPhoto = progressPhoto;
    }

    public Bitmap getProgressPhoto() {
        return progressPhoto;
    }

    public String getDate() {
        return dateString;
    }

    public Date getDateForSorting() {
        return dateForSorting;
    }
}
