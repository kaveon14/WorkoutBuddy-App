package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api;


import static com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI.ROOT_URL;

public abstract class ProgressPhotoApi  {

    public static final String JSON_DATE_TIME = "date_time";

    public static final String JSNON_PHOTO_PATH = "local_photo";

    private static final String PROGRESS_PHOTO_ROOT_URL = ROOT_URL+"RequestHandlers/ProgressPhotoRequestHandler.php?";

    public static String getGetProgressPhotoPathUrl() {
        return PROGRESS_PHOTO_ROOT_URL + "request=getProgressPhotoPath&userId="+CoreAPI.getUserId();
    }

    public static String getUploadPhotoUrl() {
        return ROOT_URL+"FileUploading/UploadProgressPhoto.php";
    }

    public static String getDownloadProgressPhotoUrl(final String file_name) {
        return ROOT_URL + "FileDownloading/DownloadProgressPhoto.php?userId="+CoreAPI.getUserId()
                +"&file_name="+file_name;
    }
}
