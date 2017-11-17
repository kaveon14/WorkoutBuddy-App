package com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ProgressPhotoApi;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers.FileDownloadRequest;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers.ProgressPhotoRequestHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.COLUMN_DATE;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.COLUMN_PHOTO;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.COLUMN_PHOTO_PATH;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.TABLE_NAME;
//add method to delete date on long click(on a different branch)
public class ProgressPhotosTable extends TableManager {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public ProgressPhotosTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        setTableName(TABLE_NAME);
        setSearchableColumns(new String[]{COLUMN_DATE});
    }

    public void addProgressPhoto(String date, String photoPath,Bitmap bitmap) {
        try {
            SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_PHOTO_PATH, photoPath);
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_PHOTO, getImageData(bitmap));
            writableDatabase.insert(TABLE_NAME, null, values);
            writableDatabase.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getImageData(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();
        stream.close();
        return data;
    }

    public List<ProgressPhoto> getProgressPhoto() {
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

    public void downloadAndStoreProgressPhoto(final String imageName) {
        FileDownloadRequest request = new FileDownloadRequest(context);
        ImageRequest imageRequest = new ImageRequest(ProgressPhotoApi.getDownloadProgressPhotoUrl(imageName),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {//these of course need to contain real values
                        new ProgressPhotosTable(context).addProgressPhoto("2017-12-12 17:35:22", "create path", response);
                    }
                }, 500, 500, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.networkResponse);
            }
        });
        request.addToRequestQueue(imageRequest);
    }

    public List<Bitmap> getImageData() {
        byte[] data;
        List<Bitmap> photos = new ArrayList<>();
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null,COLUMN_DATE+" DESC");
        while(cursor.moveToNext()) {
            data = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PHOTO));
            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            photos.add(bitmap);
        }
        return photos;
    }

    public ProgressPhoto getFullPhoto(String path) {
        return null;
    }

    public void deleteRow(String datesToDelete[]) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        database.delete(TABLE_NAME,COLUMN_DATE+"=?",datesToDelete);
        database.close();
    }
}
