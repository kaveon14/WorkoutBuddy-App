package com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.DefaultData.DefaultExerciseNames;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_MAINWORKOUT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_1;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_2;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_3;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_4;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.MainWorkoutData.COLUMN_SUBWORKOUT_5;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_SETS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK1;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK2;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK3;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK4;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.TABLE_NAME_WK5;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData;
import com.example.kaveon14.workoutbuddy.DataBase.DefaultData.DefaultWorkouts;
import com.example.kaveon14.workoutbuddy.R;

public class DataBaseSQLiteHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workoutDataBase.db";
    private Context context;

    public DataBaseSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DataBaseContract.MainWorkoutData.CREATE_TABLE);
        defaultWorkoutData(database);
        new DefaultWorkoutsExtension().addDefaultWorkoutsOnCreate(database);
        database.execSQL(DataBaseContract.ExerciseData.CREATE_TABLE);
        new DefaultExercisesExtension().addDefaultExercises(database);
        database.execSQL(DataBaseContract.BodyData.CREATE_TABLE);
        database.execSQL(createWorkoutDataTable());
        database.execSQL(DataBaseContract.ProgressPhotos.CREATE_TABLE);
    }

    private void defaultWorkoutData(SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINWORKOUT,"Default Workouts");
        values.put(COLUMN_SUBWORKOUT_1,"Chest Day");
        values.put(COLUMN_SUBWORKOUT_2,"Back Day");
        values.put(COLUMN_SUBWORKOUT_3,"Shoulder Day");
        values.put(COLUMN_SUBWORKOUT_4,"Leg Day");
        values.put(COLUMN_SUBWORKOUT_5,"Arm Day");
        database.insert(DataBaseContract.MainWorkoutData.TABLE_NAME,null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        new DefaultWorkoutsExtension().addDefaultWorkoutsOnUpgrade(database);
        database.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.ExerciseData.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + DataBaseContract.BodyData.TABLE_NAME);
        onCreate(database);
    }

    private String createWorkoutDataTable() {
        int NAME_COL = 0,SETS_COL =1,REPS_COL = 2,WEIGHT_COL = 3;
        StringBuilder builder;
        String[] columns = new String[4];
        for(int x=1;x<=15;x++) {
            String columnStart = "Exercise" + x;
            builder = new StringBuilder(columnStart);
            columns[NAME_COL] = builder.append("_Name").toString();

            DataBaseContract.WorkoutData.createLiftingStatsColumn(columns[NAME_COL]);//moved from bottom so name is first
            for(int z=1;z<=10;z++) {//delete weight and reps num Exercise1_Sets1_Reps1
                builder = new StringBuilder(columnStart);
                columns[SETS_COL] = builder.append("_Set").append(z).toString();
                builder = new StringBuilder(columns[SETS_COL]);
                columns[REPS_COL] = builder.append("_Reps").toString();
                System.out.println("repsCol: "+columns[REPS_COL]);
                builder = new StringBuilder(columns[SETS_COL]);
                columns[WEIGHT_COL] = builder.append("_Weight").toString();
                DataBaseContract.WorkoutData.createLiftingStatsColumn(columns[REPS_COL]);
                DataBaseContract.WorkoutData.createLiftingStatsColumn(columns[WEIGHT_COL]);
            }

        }
        DataBaseContract.WorkoutData.setColumns();
        String createTable  = DataBaseContract.WorkoutData.CREATE_TABLE +
                DataBaseContract.WorkoutData.setColumns();
        return createTable;
    }

    private class DefaultWorkoutsExtension {
        private List<String> defaultWorkoutNames = null;
        private Map<String, String> defaultWorkoutsMap = null;

        protected void addDefaultWorkoutsOnCreate(SQLiteDatabase database) {//think about a name change
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK1));
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK2));
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK3));
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK4));
            database.execSQL(SubWorkoutData.createWorkoutTable(TABLE_NAME_WK5));
            addDefaultWorkoutsData(database);
        }

        private void addDefaultWorkoutsOnUpgrade(SQLiteDatabase database) {//think about a name change
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK1);
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK2);
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK3);
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK4);
            database.execSQL("DROP TABLE IF EXISTS " + SubWorkoutData.TABLE_NAME_WK5);
        }

        private void setDefaultWorkoutsMap() {
            DefaultWorkouts fileReader = new DefaultWorkouts(context, "DefaultWorkoutValues.txt");
            try {
                defaultWorkoutsMap = fileReader.getSubWorkoutData();
                defaultWorkoutNames = new ArrayList<>(5);//these are wrong fool
                defaultWorkoutNames.add("Chest_Day");
                defaultWorkoutNames.add("Back_Day");
                defaultWorkoutNames.add("Shoulder_Day");
                defaultWorkoutNames.add("Leg_Day");
                defaultWorkoutNames.add("Arm_Day");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void addDefaultWorkoutsData(SQLiteDatabase database) {
            setDefaultWorkoutsMap();
            for(String workoutName : defaultWorkoutNames) {
                try {
                    setSingleDefaultWorkout(database,"Default_Workouts_"+workoutName+"_wk");
                } catch (NoSuchElementException e) {
                    //scanner has reached end of string and throws an error
                }
            }
        }

        private void setSingleDefaultWorkout(SQLiteDatabase database, String tableName) throws NoSuchElementException {
            String data = defaultWorkoutsMap.get(tableName);
            Scanner scanner = new Scanner(data);
            Scanner insertData = new Scanner(data);
            String exercise, sets, reps;
            while (scanner.hasNext()) {
                scanner.nextLine();
                exercise = getExerciseName(insertData);
                sets = getSetsForExercise(insertData);
                reps = getRepsForExercise(insertData);
                addDefaultExerciseToWorkout(database, tableName, exercise, sets, reps);
            }
            scanner.close();
            insertData.close();
        }

        private void addDefaultExerciseToWorkout(SQLiteDatabase database, String tableName, String exerciseName,
                                                 String sets, String reps) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EXERCISE_NAMES, exerciseName);
            values.put(COLUMN_EXERCISE_SETS, sets);
            values.put(COLUMN_EXERCISE_REPS, reps);
            database.insert(tableName, null, values);
        }

        private String getExerciseName(Scanner scan) {
            return scan.next().replace("_", " ");
        }

        private String getSetsForExercise(Scanner scan) {
            return scan.next();
        }

        private String getRepsForExercise(Scanner scan) {
            return scan.next();
        }
    }

    private class DefaultExercisesExtension {

        protected void addDefaultExercises(SQLiteDatabase database) {//add exercise descriptions also change func name
            List<String> exerciseNames = new DefaultExerciseNames(context, "ExerciseNames.txt")
                    .readFileSorted();
            ContentValues values = new ContentValues();
            for(String exerciseName : exerciseNames) {
                values.put(DataBaseContract.ExerciseData.COLUMN_EXERCISES, exerciseName);
                byte[] data = getDefaultImages(exerciseName);
                values.put(DataBaseContract.ExerciseData.COLUMN_EXERCISE_IMAGES,data);
                database.insert(DataBaseContract.ExerciseData.TABLE_NAME, null, values);
            }
        }


        private byte[] getDefaultImages(String exerciseName) {
            final Field[] mipmapFields = R.mipmap.class.getDeclaredFields();
            try {
                return getExerciseImage(exerciseName,mipmapFields);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        private byte[] getExerciseImage(String exerciseName, Field[] mipmapFields) throws IllegalAccessException {
            for (int x = 0; x < mipmapFields.length; x++) {
                String imageName = mipmapFields[x].getName();
                if(doImageAndExerciseMatch(imageName,exerciseName)) {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                            mipmapFields[x].getInt(new R.mipmap()));
                    return getExerciseImageData(exerciseName,bitmap);
                }
            }
            return null;
        }

        private boolean doImageAndExerciseMatch(String imageName, String exerciseName) {
            imageName = imageName.replace("_"," ");
            exerciseName = exerciseName.replace("-"," ");
            return imageName.equalsIgnoreCase(exerciseName);
        }


        private byte[] getExerciseImageData(String exerciseName,Bitmap bitmap) {
            Exercise exercise = new Exercise(exerciseName, null);
            exercise.setExerciseImage(bitmap);
            return getImageData(exercise);
        }

        private byte[] getImageData(Exercise exercise) {
            Bitmap bitmap = exercise.getExerciseImage();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] data = stream.toByteArray();
            return data;
        }
    }
}
