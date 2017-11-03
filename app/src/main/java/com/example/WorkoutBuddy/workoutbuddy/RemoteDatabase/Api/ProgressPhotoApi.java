package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api;


public abstract class ProgressPhotoApi extends CoreAPI {

    public static final String JSON_DATE_TIME = "date_time";

    public static final String JSNON_PHOTO_PATH = "local_photo";

    private static final String PROGRESS_PHOTO_ROOT_URL = "RequestHandlers/ProgressPhotoRequestHandler.php?";

    public static final String GET_PROGRESS_PHOTO_PATH = "request=getProgressPhotoPath&userId=";
}
