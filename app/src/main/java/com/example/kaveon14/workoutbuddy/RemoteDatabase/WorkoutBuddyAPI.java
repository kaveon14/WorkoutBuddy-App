package com.example.kaveon14.workoutbuddy.RemoteDatabase;

public class WorkoutBuddyAPI {

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;
//change nME OF ALL THESE
    public static final String ROOT_URL = "http://192.168.0.15/WorkoutBuddy_Scripts/WorkoutBuddyAPI.php?apicall=";

    public static final String LOGIN_URL = "http://192.168.0.15/WorkoutBuddy_Scripts/Login.php?username=";

    public static final String URL_READ_EX = ROOT_URL + "getDefaultExercises";

}
//%2B  to convert + sign