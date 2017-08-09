package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.kaveon14.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.COLUMN_DATE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.COLUMN_PHOTO;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.TABLE_NAME;

public class ProgressPhotosTable extends TableManager {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public ProgressPhotosTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        searchTable(TABLE_NAME);
        setSearchableColumns(new String[]{COLUMN_DATE});
    }

    public void addProgressPhoto(String date, Bitmap photo) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,date);
        try {
            values.put(COLUMN_PHOTO, getImageData(photo));
        } catch (IOException e) {

        }
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    public void addProgressPhoto(ProgressPhoto progressPhoto) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,progressPhoto.getDate());
        try {
            values.put(COLUMN_PHOTO,getImageData(progressPhoto.getProgressPhoto()));
        } catch (IOException e) {

        }
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    private byte[] getImageData(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();
        stream.close();
        return data;
    }





}
