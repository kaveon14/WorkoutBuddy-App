package com.example.kaveon14.workoutbuddy.DataBase.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import com.example.kaveon14.workoutbuddy.DataBase.DefaultData.DefaultExerciseNames;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISE_DESCRIPTION;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISE_IMAGES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.TABLE_NAME;

public class ExerciseTable extends TableManager {

    //private Context context;
    private DataBaseSQLiteHelper dataBaseSQLiteHelper;
    public static int DEFAULT_EXERCISE_COUNT = 1;
    public static int CUSTOM_EXERCISE_COUNT = 1;//do later

    public ExerciseTable(Context context) {
        dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
        //this.context = context;
        setContext(context);
        setTableName(TABLE_NAME);
    }

    public void setDefaultExerciseCount() {
        DEFAULT_EXERCISE_COUNT = new DefaultExerciseNames(context,"ExerciseNames.txt")
                .readFileSorted().size();
    }

    public void addAnExercise(Exercise exercise) {
        SQLiteDatabase writableDatabase = dataBaseSQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISES,exercise.getExerciseName());
        values.put(COLUMN_EXERCISE_DESCRIPTION,exercise.getExerciseDescription());
        //if app is slowing down image chosen  may be too big

        try {//take out
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

    /*public List<String> getColumn(String columnName) {
        List<String> columnList = new ArrayList<>();
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
    }*/

    public List<Exercise> getExercises() {
        List<Exercise> exerciseList = new ArrayList<>();
        List<String> exerciseNames = getColumn(COLUMN_EXERCISES);
        List<String> exerciseDescriptions = getColumn(COLUMN_EXERCISE_DESCRIPTION);

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
        for(int x=DEFAULT_EXERCISE_COUNT;x<exerciseNames.size();x++) {
            Exercise exercise = new Exercise(exerciseNames.get(x),
                    exerciseDescriptions.get(x));

            exercise.setExerciseImage(getExerciseImage(exercise));
            exerciseList.add(exercise);
        }
        return exerciseList;
    }

    public void deleteExercise(Exercise exercise) {
        SQLiteDatabase database = dataBaseSQLiteHelper.getWritableDatabase();
        String[] data = new String[]{
                exercise.getExerciseName()
        };
        database.delete(TABLE_NAME,COLUMN_EXERCISES+"=?",data);
        database.close();
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

    public Bitmap getExerciseImage(Exercise exercise) {
        SQLiteDatabase readableDatabase = dataBaseSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        Bitmap bitmap = getImageData(cursor,exercise);
        if(bitmap != null) {
            cursor.close();
            readableDatabase.close();
            return bitmap;
        }
        cursor.close();
        readableDatabase.close();
        return null;
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
