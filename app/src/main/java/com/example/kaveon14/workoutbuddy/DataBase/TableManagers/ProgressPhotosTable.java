package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.kaveon14.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.COLUMN_DATE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.COLUMN_PHOTO;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.TABLE_NAME;

public class ProgressPhotosTable extends TableManager {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public ProgressPhotosTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        setTableName(TABLE_NAME);
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

    public List<ProgressPhoto> getProgressPhotos() {//get data sorted
        List<String> dates = getColumn(COLUMN_DATE);
        List<Bitmap> photos = getImageData();
        List<ProgressPhoto> progressPhotos = new ArrayList<>(dates.size());

        for(int x=0;x<dates.size();x++) {
            ProgressPhoto progressPhoto =
                     new ProgressPhoto(dates.get(x),photos.get(x));
            progressPhotos.add(progressPhoto);
        }
        return progressPhotos;
    }

    public List<Bitmap> getImageData() {
        byte[] data;
        List<Bitmap> photos = new ArrayList<>();
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            data = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PHOTO));
            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            photos.add(bitmap);
        }
        return photos;
    }

    public List<Bitmap> getImageData(int count) {
        byte[] data;
        List<Bitmap> photos = new ArrayList<>();
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        int increment = 0;
        while(cursor.moveToNext() && increment<count) {
            data = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PHOTO));
            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            photos.add(bitmap);
            increment++;
        }
        return photos;
    }

    public List<Bitmap> getImageData(int count,String query) {
        byte[] data;
        List<Bitmap> photos = new ArrayList<>();
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, query);
        int increment = 0;
        while(cursor.moveToNext() && increment<count) {
            data = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PHOTO));
            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            photos.add(bitmap);
            increment++;
        }
        return photos;
    }

    private byte[] getImageData(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();
        stream.close();
        return data;
    }
}
