package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISE_DESCRIPTION;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISE_IMAGES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.TABLE_NAME;

public class ExerciseTable {

    private DataBaseSQLiteHelper dataBaseSQLiteHelper;

    public ExerciseTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    }

    public void addAnExercise(Exercise exercise) {//needs to include
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISES,exercise.getExerciseName());
        values.put(COLUMN_EXERCISE_DESCRIPTION,exercise.getExerciseDescripion());
        //image chosen was may be too big
        values.put(COLUMN_EXERCISE_IMAGES,getImageData(exercise));
        long itemID = writableDatabase.insert(TABLE_NAME,null,values);
    }

    private byte[] getImageData(Exercise exercise) {
        Bitmap bitmap = exercise.getExerciseImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();
        return data;
    }

    public List<String> getColumn(String columnName) {
        List<String> columnList = new LinkedList<>();
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        int increment = 0;
        while(cursor.moveToNext()) {
            columnList.add(increment,cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
            increment++;
        }
        readableDatabase.close();
        cursor.close();
        return columnList;
    }

    public void printExerciseTable() {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            System.out.println("Exercise Name: "+cursor
                    .getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISES)) +
                    " Exercise Description: "+cursor.
                    getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISE_DESCRIPTION)));
        }
        readableDatabase.close();
        cursor.close();
    }

    public Bitmap getImage(Exercise exercise) {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {//possibly not loading right content
            String exerciseName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISES));
            if (exerciseName.equalsIgnoreCase(exercise.getExerciseName())) {
                byte[] imageId = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_EXERCISE_IMAGES));
                readableDatabase.close();
                cursor.close();
                return BitmapFactory.decodeByteArray(imageId, 0, imageId.length);
            }
        }
        return null;
    }
}
