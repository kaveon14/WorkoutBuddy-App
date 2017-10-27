package com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api;

public abstract class BodyApi extends CoreAPI {

    public static final String JSON_DATE = "date";

    public static final String JSON_WEIGHT = "weight";

    public static final String JSON_CHEST_SIZE = "chest_size";

    public static final String JSON_BACK_SIZE = "back_size";

    public static final String JSON_ARM_SIZE = "arm_size";

    public static final String JSON_FOREARM_SIZE = "forearm_size";

    public static final String JSON_WAIST_SIZE = "waist_size";

    public static final String JSON_QUAD_SIZE = "quad_size";

    public static final String JSON_CALF_SIZE = "calf_size";

    private static final String BODY_ROOT_URL = "RequestHandlers/BodyDataRequestHandler.php?";

    public static final String GET_BODY_DATA_URL = BODY_ROOT_URL + "request=getBodyData&userId=";
}
