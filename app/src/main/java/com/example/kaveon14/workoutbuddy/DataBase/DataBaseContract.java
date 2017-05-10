package com.example.kaveon14.workoutbuddy.DataBase;

import android.provider.BaseColumns;

public class DataBaseContract {

    private DataBaseContract() {

    }

    public static class WorkoutData implements BaseColumns {

        public static final String TABLE_NAME_WK1 = "Workout1";//change to real workout names

        public static final String TABLE_NAME_WK2 = "Workout2";

        public static final String TABLE_NAME_WK3 = "Workout3";

        public static final String TABLE_NAME_WK4 = "Workout4";

        public static final String TABLE_NAME_WK5 = "Workout5";

        public static final String COLUMN_EXERCISE_NAMES = "Exercise_Names";

        public static final String COLUMN_EXERCISE_SETS = "Exercise_Sets";

        public static final String COLUMN_EXERCISE_REPS = "Exercise_Reps";

        public static final String createWorkoutTable(final String tableName) {
             final String WORKOUT_TABLE = " CREATE TABLE " +
                    tableName + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EXERCISE_NAMES + " TEXT, " +
                    COLUMN_EXERCISE_SETS + " INT, " +
                    COLUMN_EXERCISE_REPS +
                    " INT" + ")";
           return WORKOUT_TABLE;
        }
    }//add date

    public static class ExerciseData implements BaseColumns {

        public static final String TABLE_NAME = "Exercise_Names";

        public static final String COLUMN_EXERCISES = "Exercises";

        public static final String COLUMN_EXERCISE_DESCRIPTION = "Exercise_Content";//add line seperators

        public static final String CREATE_TABLE = " CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISES + " TEXT, " +//read in core exercise data from file reset keep in db only
                COLUMN_EXERCISE_DESCRIPTION + " BLOB" + ")";
    }

    public static final class BodyData implements BaseColumns {//DONT FORGET TOP GET HEIGHT FOR AND AGE FOR BMI

        public static final String TABLE_NAME = "Body-Data";

        public static final String COLUMN_DATE = "Date";

        public static final String COLUMN_WEIGHT = "Weight";

        public static final String COLUMN_CHEST_SIZE = "Chest_Size";

        public static final String COLUMN_BACK_SIZE = "Back_Size";

        public static final String COLUMN_BICEP_SIZE = "Bicep_Size";

        public static final String COLUMN_FOREARM_SIZE = "Forearm_Size";

        public static final String COLUMN_WAIST_SIZE = "Waist_Size";

        public static final String COLUMN_QUAD_SIZE = "Quad_Size";

        public static final String COLUMN_CALF_SIZE = "Calf_Size";

        public static final String  CREATE_TABLE = " CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_WEIGHT + " TEXT, " +
                COLUMN_CHEST_SIZE + " TEXT, " +
                COLUMN_BACK_SIZE + " TEXT, " +
                COLUMN_BICEP_SIZE + " TEXT, " +
                COLUMN_FOREARM_SIZE + " TEXT, " +
                COLUMN_WAIST_SIZE + " TEXT, " +
                COLUMN_QUAD_SIZE + " TEXT, " +
                COLUMN_CALF_SIZE + " TEXT" + ")";
    }
}
