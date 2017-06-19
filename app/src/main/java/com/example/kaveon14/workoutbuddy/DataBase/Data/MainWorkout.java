package com.example.kaveon14.workoutbuddy.DataBase.Data;
// go and put initial size in all array lists
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainWorkout {

    private String mainWorkoutName;
    private List<SubWorkout> subWorkouts;
//make sure it is an array list
    public MainWorkout(String mainWorkoutName, List<SubWorkout> subWorkouts) {
        this.mainWorkoutName = mainWorkoutName;
        this.subWorkouts = subWorkouts;
    }

    public String getMainWorkoutName() {
        return mainWorkoutName;
    }

    public List<String> getAllSubWorkoutNames() {
        List<String> subWorkoutNames = new ArrayList<>(7);
        for(SubWorkout data : subWorkouts) {
            subWorkoutNames.add(data.getSubWorkoutName());
        }
        return subWorkoutNames;
    }

    public List<SubWorkout> getSubWorkoutsList() {
        return subWorkouts;
    }

    public Map<String,SubWorkout> getSubWorkoutMap() {
        Map<String,SubWorkout> subWorkoutMap = new Hashtable<>();
        for(SubWorkout subWorkout : subWorkouts) {
            subWorkoutMap.put(subWorkout.getSubWorkoutName(),subWorkout);
        }
        return subWorkoutMap;
    }
}
