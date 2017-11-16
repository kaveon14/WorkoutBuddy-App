package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api;

import static com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI.ROOT_URL;

public abstract class ExerciseApi {

    public static final String JSON_EXERCISE_NAME = "exercise_name";
    public static final String JSON_EXERCISE_DESCRIPTION = "exercise_description";
    public static final String JSON_EXERCISE_IMAGE = "exercise_image";
    public static final String JSON_DEFAULT_EXERCISE = "default";
    private static final String EXERCISE_ROOT_URL = ROOT_URL + "RequestHandlers/ExerciseRequestHandler.php?";


    public static String getGetDefaultExerciseUrl() {//how does this work correctly
        return EXERCISE_ROOT_URL + "RequestHandlers/ExerciseRequestHandler.php?request=getDefaultExercises";
    }

    public static String getGetCustomExerciseUrl() {
        return EXERCISE_ROOT_URL + "request=getCustomExercises&userId="+CoreAPI.getUserId();
    }

    public static String getGetAllExercisesUrl() {
        return EXERCISE_ROOT_URL + "request=getAllExercises&userId="+CoreAPI.getUserId();
    }

    public static String getDownloadExercisePhotoUrl(final String file_name) {//do this with the rest of them
        return ROOT_URL + "FileDownloading/DownloadExercisePhoto.php?userId="+CoreAPI.getUserId()
                +"&file_name="+file_name;
    }
}