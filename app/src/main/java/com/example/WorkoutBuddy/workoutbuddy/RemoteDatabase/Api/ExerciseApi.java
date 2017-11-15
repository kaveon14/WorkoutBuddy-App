package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api;

import static com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI.ROOT_URL;

public abstract class ExerciseApi {

    public static final String JSON_EXERCISE_NAME = "exercise_name";

    public static final String JSON_EXERCISE_DESCRIPTION = "exercise_description";

    public static final String JSON_EXERCISE_IMAGE = "exercise_image";

    public static final String JSON_DEFAULT_EXERCISE = "default";

    private static final String EXERCISE_ROOT_URL = ROOT_URL + "RequestHandlers/ExerciseRequestHandler.php?";

    public static final String GET_DEFAULT_EXERCISE_URL
            = EXERCISE_ROOT_URL + "RequestHandlers/ExerciseRequestHandler.php?getDefaultExercises";

    public static final String GET_CUSTOM_EXERCISE_URL = EXERCISE_ROOT_URL + "request=getCustomExercises&userId=";

    public static final String GET_ALL_EXERCISES_URL = EXERCISE_ROOT_URL + "request=getAllExercises&userId=";
}