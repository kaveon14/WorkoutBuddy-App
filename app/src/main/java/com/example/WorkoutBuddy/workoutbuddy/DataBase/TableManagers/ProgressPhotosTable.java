package com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers.ProgressPhotoRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ProgressPhotos.COLUMN_DATE;
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

    public void addProgressPhoto(String date, String photoPath) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHOTO_PATH,photoPath);
        values.put(COLUMN_DATE,date);
        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    public List<ProgressPhoto> getProgressPhotos() {
        List<String> paths = getColumn(COLUMN_PHOTO_PATH);
        List<String> dates = getColumn(COLUMN_DATE);
        List<ProgressPhoto> progressPhotos = new ArrayList<>(paths.size());

        for(int x=0;x<paths.size();x++) {
            String path = paths.get(x);
            String date = dates.get(x);
            progressPhotos.add(new ProgressPhoto(date,BitmapFactory.decodeFile(path)));
        }
        return progressPhotos;
    }

    public void deleteRow(String datesToDelete[]) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        database.delete(TABLE_NAME,COLUMN_DATE+"=?",datesToDelete);
        database.close();
    }

    public void uploadPhoto(String path) {
       new UploadFileToServer(path).execute();

    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        String filePath;

        public UploadFileToServer(String filePath) {
            this.filePath = filePath;
        }

        @Override
        protected String doInBackground(Void... params) {
            ProgressPhotoRequestHandler requestHandler = new ProgressPhotoRequestHandler();
            return requestHandler.sendPostImageRequest(filePath);
        }

       /* @Override
        protected void onPostExecute(String s) {
            System.out.println("WTF");
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray(CoreAPI.JSON_KEY);
                JSONObject object = ((JSONObject) array.get(0));
                System.out.println(CoreAPI.JSON_ERROR+": "+object.getBoolean(CoreAPI.JSON_ERROR));
                System.out.println(CoreAPI.JSON_ERROR_MESSAGE+": "+object.getBoolean(CoreAPI.JSON_ERROR_MESSAGE));
                System.out.println(CoreAPI.JSON_KEY+": "+object.getBoolean(CoreAPI.JSON_KEY));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
    }

}
