package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api;

import static com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI.ROOT_URL;

public class WorkoutApi {

    public static final String JSON_MAINWORKOUT_NAME = "main_workout_name";

    public static final String JSON_SUBWORKOUT_NAME = "sub_workout_name";

    public static final String WORKOUT_ROOT_URL = ROOT_URL + "RequestHandlers/WorkoutRequestHandler.php?";

    public static final String GET_MAINWORKOUTS_URL = WORKOUT_ROOT_URL + "request=getMainWorkoutNames&userId=";

    public static final String GET_SUBWORKOUTS_URL = WORKOUT_ROOT_URL + "request=getSubWorkoutNames&userId=";

    public static final String GET_SUBWORKOUT_EXERCISES_URL
            = ROOT_URL + "getGoalExercises&subWorkoutId=";
}
