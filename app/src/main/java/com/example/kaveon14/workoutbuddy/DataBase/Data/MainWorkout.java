package com.example.kaveon14.workoutbuddy.DataBase.Data;

import java.util.LinkedList;
import java.util.List;

public class MainWorkout {

    private String mainWorkoutName;
    private List<SubWorkout> subWorkouts;

    public MainWorkout(String mainWorkoutName, List<SubWorkout> subWorkouts) {
        this.mainWorkoutName = mainWorkoutName;
        this.subWorkouts = subWorkouts;
    }

    public String getMainWorkoutName() {
        return mainWorkoutName;
    }

    public List<String> getAllSubWorkoutNames() {
        List<String> subWorkoutNames = new LinkedList<>();
        for(int x=0;x<subWorkouts.size();x++) {
            subWorkoutNames.add(subWorkouts.get(x).getSubWorkoutName());
        }
        return subWorkoutNames;
    }
}
