package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase;

public class WorkoutBuddyAPI {

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;
//change nME OF ALL THESE
    public static final String ROOT_URL = "http://10.100.52.153/WorkoutBuddy_Scripts/WorkoutBuddyAPI.php?apicall=";

    public static final String LOGIN_URL = "http:/10.100.52.153/WorkoutBuddy_Scripts/Login.php?username=";

    public static final String URL_READ_DEFAULT_EXERCISE = ROOT_URL + "getDefaultExercises";

    public static final String JSON_ERROR = "error";

    public static final String JSON_ERROR_MESSAGE = "message";

}
//%2B  to convert + sign