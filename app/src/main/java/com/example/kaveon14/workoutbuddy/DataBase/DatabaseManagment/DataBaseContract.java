package com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment;

import android.provider.BaseColumns;
import java.util.LinkedList;
import java.util.List;

public class DataBaseContract {

    public static class MainWorkoutData implements BaseColumns {//is _wk needed????

        public static final String TABLE_NAME = "Main_Workouts";

        public static final String COLUMN_MAINWORKOUT = "Main_Workout_Names";

        public static final String COLUMN_SUBWORKOUT_1 = "Day1_Workout1";

        public static final String COLUMN_SUBWORKOUT_2 = "Day2_Workout2";

        public static final String COLUMN_SUBWORKOUT_3 = "Day3_Workout3";

        public static final String COLUMN_SUBWORKOUT_4 = "Day4_Workout4";

        public static final String COLUMN_SUBWORKOUT_5 = "Day5_Workout5";

        public static final String COLUMN_SUBWORKOUT_6 = "Day6_Workout6";

        public static final String COLUMN_SUBWORKOUT_7 = "Day7_Workout7";

        public static final String CREATE_TABLE = " CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MAINWORKOUT + " TEXT, " +
                COLUMN_SUBWORKOUT_1 + " TEXT, " +
                COLUMN_SUBWORKOUT_2 + " TEXT, " +
                COLUMN_SUBWORKOUT_3 + " TEXT, " +
                COLUMN_SUBWORKOUT_4 + " TEXT, " +
                COLUMN_SUBWORKOUT_5 + " TEXT, " +
                COLUMN_SUBWORKOUT_6 + " TEXT, " +
                COLUMN_SUBWORKOUT_7 + " TEXT" + ")";
    }

    public static class SubWorkoutData implements BaseColumns {

        public static final String TABLE_NAME_WK1 = "Chest_Day_wk";

        public static final String TABLE_NAME_WK2 = "Back_Day_wk";

        public static final String TABLE_NAME_WK3 = "Leg_Day_wk";

        public static final String TABLE_NAME_WK4 = "Arm_Day_wk";

        public static final String TABLE_NAME_WK5 = "Shoulder_Day_wk";

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
    }

    public static class ExerciseData implements BaseColumns {//change table name

        public static final String TABLE_NAME = "Exercise_Names";

        public static final String COLUMN_EXERCISES = "Exercises";

        public static final String COLUMN_EXERCISE_DESCRIPTION = "Exercise_Content";//add line seperators

        public static final String COLUMN_EXERCISE_IMAGES = "Exercise_Image_IDs";

        public static final String COLUMN_DATE = "Date";

        public static final String COLUMN_MAX = "MaxWeight";

        public static final String CREATE_TABLE = " CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISES + " TEXT, " +
                COLUMN_EXERCISE_DESCRIPTION + " BLOB, " +
                COLUMN_EXERCISE_IMAGES + " BLOB" +
                COLUMN_DATE + " TEXT, " +
                COLUMN_MAX + " TEXT" + ")";
    }

    public static final class BodyData implements BaseColumns {//DONT FORGET TOP GET HEIGHT FOR AND AGE FOR BMI

        public static final String TABLE_NAME = "Body_Data";

        public static final String COLUMN_DATE = "Date";

        public static final String COLUMN_WEIGHT = "Weight";

        public static final String COLUMN_CHEST_SIZE = "Chest_Size";

        public static final String COLUMN_BACK_SIZE = "Back_Size";

        public static final String COLUMN_ARM_SIZE = "Arm_Size";

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
                COLUMN_ARM_SIZE + " TEXT, " +
                COLUMN_FOREARM_SIZE + " TEXT, " +
                COLUMN_WAIST_SIZE + " TEXT, " +
                COLUMN_QUAD_SIZE + " TEXT, " +
                COLUMN_CALF_SIZE + " TEXT" + ")";
    }

    public static final class LiftData implements BaseColumns {//gonna have to expand a lot

        static List<String> columnNames = new LinkedList<>();

        public static final String TABLE_NAME = "Exercise_Stats";

        public static final String COLUMN_MAINWORKOUT = "MainWorkoutName";

        public static final String COLUMN_SUBWORKOUT = "SubWorkoutName";

        public static final String COLUMN_DATE = "Date";

        public static void createLiftingStatsColumn(final String columnName) {
            columnNames.add(columnName);
        }

        public static String setColumns() {//are the specific exercise columns necessary ????
            String columns = "";
            for(int x=0;x<columnNames.size();x++) {
                if(x==columnNames.size()-1) {
                    columns = columns + columnNames.get(x) + " TEXT" + ")";
                } else  {
                    columns = columns + columnNames.get(x) + " TEXT, ";
                }
            }
            return columns;
        }

        public static final String CREATE_TABLE = " CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MAINWORKOUT + " TEXT, " +
                COLUMN_SUBWORKOUT + " TEXT, " +
                COLUMN_DATE + " TEXT, ";

    }//need to add 10 set columns to the table need to store both weight and reps
}//Set1_Reps,Set1_Weight
