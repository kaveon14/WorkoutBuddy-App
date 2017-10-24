package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.ApiConstants;
l
public abstract class WorkoutBuddyAPI {//carry over alll

    private static int USER_ID = 0;

    public static final String JSON_KEY = "RequestResponse";l

    public static final int CODE_GET_REQUEST = 1024;

    public static final int CODE_POST_REQUEST = 1025;
    //change name OF ALL OF THESEll
    private static final String ROOT_URL
            = "http://10.100.52.153/WorkoutBuddy_Scripts/";

    public static final String LOGIN_URL
            = "http:/10.100.52.153/WorkoutBuddy_Scripts/RequestHandlers/Login.php?username=";


    public static final String GET_SUBWORKOUT_EXERCISES_URL
            = ROOT_URL + "getGoalExercises&subWorkoutId=";

    public static final String GET_MAINWORKOUTS_URL = "";

    public static final String JSON_ERROR = "error";

    public static final String JSON_ERROR_MESSAGE = "message";

    public static void setUserId(int userId) {
        USER_ID = userId;
    }

    public static int getUserId() {
        return USER_ID;
    }
}//%2B  to convert + sign