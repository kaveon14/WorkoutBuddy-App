package com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.Exercise;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.DefaultData.DefaultExerciseContent;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ExerciseApi;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers.FileDownloadRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISE_DESCRIPTION;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISE_IMAGES;
import static com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.TABLE_NAME;

public class ExerciseTable extends TableManager {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;
    private static int DEFAULT_EXERCISE_COUNT = 1;

    public ExerciseTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        setContext(context);
        setTableName(TABLE_NAME);
        setSearchableColumns(new String[]{COLUMN_EXERCISES});
    }

    public void setDefaultExerciseCount() {
        DEFAULT_EXERCISE_COUNT = new DefaultExerciseContent(context)
                .getExerciseNames().size();
    }

    public void addAnExercise(Exercise exercise) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISES,exercise.getExerciseName());
        values.put(COLUMN_EXERCISE_DESCRIPTION,exercise.getExerciseDescription());
        //if app is slowing down image chosen  may be too big
        try {
            values.put(COLUMN_EXERCISE_IMAGES, getImageData(exercise));
        } catch (IOException e) {
            e.printStackTrace();
        }

        writableDatabase.insert(TABLE_NAME,null,values);
        writableDatabase.close();
    }

    private byte[] getImageData(Exercise exercise) throws IOException {
        Bitmap bitmap = exercise.getExerciseImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();
        stream.close();
        return data;
    }

    public Exercise getExercise(String exerciseName) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_EXERCISES
                +" = '"+exerciseName+"'",null);
        Exercise exercise = null;
        if(cursor != null && cursor.moveToFirst()) {
            exercise = new Exercise(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISE_DESCRIPTION)));
            exercise.setExerciseImage(getExerciseImage(exercise));
        }
        cursor.close();
        return exercise;
    }

    public List<Exercise> getExercises() {
        List<String> exerciseNames = getColumn(COLUMN_EXERCISES);
        List<String> exerciseDescriptions = getColumn(COLUMN_EXERCISE_DESCRIPTION);
        List<Exercise> exerciseList = new ArrayList<>(exerciseNames.size());

        for(int x=0;x<exerciseNames.size();x++) {
            Exercise exercise = new Exercise(exerciseNames.get(x),
                    exerciseDescriptions.get(x));
            exercise.setExerciseImage(getExerciseImage(exercise));
            exerciseList.add(exercise);
        }
        return exerciseList;
    }

    public List<Exercise> getCustomExercises() {
        if(DEFAULT_EXERCISE_COUNT==1) {
            setDefaultExerciseCount();
        }

        List<Exercise> exerciseList = new ArrayList<>();
        List<String> exerciseNames = getColumn(COLUMN_EXERCISES);
        List<String> exerciseDescriptions = getColumn(COLUMN_EXERCISE_DESCRIPTION);
        for(int x=DEFAULT_EXERCISE_COUNT;x<exerciseNames.size();x++) {//needs to be refactored
            Exercise exercise = new Exercise(exerciseNames.get(x),
                    exerciseDescriptions.get(x));

            exercise.setExerciseImage(getExerciseImage(exercise));
            exerciseList.add(exercise);
        }
        return exerciseList;
    }

    public void downloadAndStoreExerciseImage(final String imageName,Exercise exercise) {
        FileDownloadRequest request = new FileDownloadRequest(context);
        ImageRequest imageRequest = new ImageRequest(ExerciseApi.getDownloadExercisePhotoUrl(imageName),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        exercise.setExerciseImage(response);
                        new ExerciseTable(context).addAnExercise(exercise);
                    }
                }, 500, 500, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.networkResponse);
            }
        });
        request.addToRequestQueue(imageRequest);
    }

    public void deleteExercise(Exercise exercise) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        String[] data = new String[]{
                exercise.getExerciseName()
        };
        database.delete(TABLE_NAME,COLUMN_EXERCISES+"=?",data);
        database.close();
    }

    public Bitmap getExerciseImage(Exercise exercise) {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME, null, null,
                null, null, null, null);
        Bitmap bitmap = getImageData(cursor,exercise);
        cursor.close();
        readableDatabase.close();
        return bitmap;
    }

    private Bitmap getImageData(Cursor cursor,Exercise exercise) {
        byte[] data;
        while (cursor.moveToNext()) {
            data = getImageBytes(cursor,exercise);
            if(data != null) {
                return BitmapFactory.decodeByteArray(data,0,data.length);
            }
        }
        return null;
    }

    private byte[] getImageBytes(Cursor cursor, Exercise exercise) {
        while (cursor.moveToNext()) {
            String exerciseName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISES));
            if (exerciseName.equalsIgnoreCase(exercise.getExerciseName())) {
                return cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_EXERCISE_IMAGES));
            }
        }
        return null;
    }
}
