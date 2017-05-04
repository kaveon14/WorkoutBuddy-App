package com.example.kaveon14.workoutbuddy.FragmentTextHandling;

import java.util.LinkedList;
import java.util.List;

public class WorkoutNames {
    /**
     * 5 standard workouts and allow the grouping of workouts into workout plans
     * Day 1: Legs/abs
     * Day 2: Chest
     * Day 3: Back/Abs
     * Day 4: Rest
     * Day 5: Cardio/Abs
     * Day 6: Arms/Shoulders
     * Day 7: Rest
     * max 10 workout plans
     * max 10 workouts in workout plan
     */

    public static List<String> workoutNames = new LinkedList<>();

    public static void standardWorkouts() {
        workoutNames.add(0,"Legs/Abs");//try using string feature from android
        workoutNames.add(1,"null");//not sure why null(placeholder) needed
        workoutNames.add(2,"Chest");
        workoutNames.add(3,"null");
        workoutNames.add(4,"Back/Abs");
        workoutNames.add(5,"null");
        workoutNames.add(6,"Cardio/Abs");
        workoutNames.add(7,"null");
        workoutNames.add(8,"Arms/Shoulders");

    }

}
