package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.ApiConstants;

/**
 * Created by kaveon14 on 10/24/17.
 */

public abstract class ExerciseApi extends WorkoutBuddyAPI {

    public static final JSON_EXERCISE_NAME = "exercise_name";
    public static final JSON_EXERCISE_DESCRIPTION = "exercise_description";

    public static final String GET_DEFAULT_EXERCISE_URL
            = ROOT_URL + "RequestHandlers/ExerciseRequestHandler.php?getDefaultExercises";

    public static final String GET_CUSTOM_EXERCISE_URL = ROOT_URL + "getCustomExercises&userId=";

    public static final String GET_ALL_EXERCISES_URL = ROOT_URL + "RequestHandlers/ExerciseRequestHandler.php?request=" +
            "getAllExercises&userId=";
}

}
