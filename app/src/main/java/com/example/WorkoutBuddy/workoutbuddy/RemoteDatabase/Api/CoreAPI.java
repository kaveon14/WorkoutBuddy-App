package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api;

public abstract class CoreAPI {//carry over all

    private static long USER_ID = 0;

    public static final String JSON_ROW_ID = "id";

    public static final String JSON_KEY = "RequestResponse";

    public static final int CODE_GET_REQUEST = 1024;

    public static final int CODE_POST_REQUEST = 1025;
    //change name OF ALL OF THESEll
    protected static final String ROOT_URL
            = "http://10.100.69.171/WorkoutBuddy_Scripts/";

    public static final String LOGIN_URL
            = ROOT_URL+ "RequestHandlers/Login.php?username=";

    public static final String GET_SUBWORKOUT_EXERCISES_URL
            = ROOT_URL + "getGoalExercises&subWorkoutId=";

    public static final String GET_MAINWORKOUTS_URL = "";

    public static final String JSON_ERROR = "error";

    public static final String JSON_ERROR_MESSAGE = "message";

    public static void setUserId(int userId) {
        USER_ID = userId;
    }

    public static long getUserId() {
        return USER_ID;
    }
}//%2B  to convert + sign