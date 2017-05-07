package com.example.kaveon14.workoutbuddy.DataBase;

import android.provider.BaseColumns;

public class DataBaseContract {

    private DataBaseContract() {

    }

    public static class Workout implements BaseColumns {

        public static final String TABLE_NAME = "Legs_Abs";

        public static final String COLUMN_EXERCISE_NAMES = "Legs_Abs";

        public static final String COLUMN_EXERCISE_SETS = "Chest";

        public static final String COLUMN_EXERCISE_REPS = "Back";

        public static final String CREATE_TABLE = " CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISE_NAMES + " TEXT, " +
                COLUMN_EXERCISE_SETS + " INT, " +
                COLUMN_EXERCISE_REPS + " INT" + ")";
    }

}
