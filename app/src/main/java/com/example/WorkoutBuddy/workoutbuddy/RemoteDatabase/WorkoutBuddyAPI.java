package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase;

public class WorkoutBuddyAPI {

    private static int USER_ID = 0;

    public static final int CODE_GET_REQUEST = 1024;

    public static final int CODE_POST_REQUEST = 1025;
//change name OF ALL OF THESE
    public static final String ROOT_URL = "http://10.100.52.153/WorkoutBuddy_Scripts/Api.php?apicall=";

    public static final String LOGIN_URL = "http:/10.100.52.153/WorkoutBuddy_Scripts/Login.php?username=";

    public static final String GET_DEFAULT_EXERCISE_URL = ROOT_URL + "getDefaultExercises";

    public static final String GET_CUSTOM_EXERCISE_URL = ROOT_URL + "getCustomExercises&userId=";

    public static final String GET_ALL_EXERCISES_URL = ROOT_URL + "getAllExercises&userId=";

    public static final String JSON_ERROR = "error";

    public static final String JSON_ERROR_MESSAGE = "message";

    public static void setUserId(int userId) {
        USER_ID = userId;
    }

    public static int getUserId() {
        return USER_ID;
    }
}
//%2B  to convert + sign