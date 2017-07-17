package com.example.kaveon14.workoutbuddy.DataBase.Data;
// TODO implement the actual main workout name into this
import android.support.annotation.Nullable;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SubWorkout {

    private String mainWorkoutName;
    private String subWorkoutName;
    private String date;
    private List<Exercise> exerciseList;
    private int totalReps;
    private int totalSets;
    private String totalWeight;
    private List<WorkoutExercise> workoutData;

    public SubWorkout() {

    }

    public SubWorkout(String subWorkoutName,@Nullable List<Exercise> exerciseList) {
        this.subWorkoutName = subWorkoutName;
        this.exerciseList = exerciseList;
    }

    public SubWorkout setMainWorkoutName(String mainWorkoutName) {
        this.mainWorkoutName = mainWorkoutName;
        return this;
    }

    public SubWorkout setDate(String date) {
        this.date = date;
        return this;
    }

    public void setTotalReps(int totalReps) {
        this.totalReps = totalReps;
    }

    public void setTotalSets(int totalSets) {
        this.totalSets = totalSets;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setWorkoutData(List<WorkoutExercise> workoutData) {
        this.workoutData = workoutData;
    }

    public String getMainWorkoutName() {
        return mainWorkoutName;
    }

    public String getSubWorkoutName() {
        return subWorkoutName;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public String getDate() {
        return date;
    }

    public int getTotalReps() {
        return totalReps;
    }

    public int getTotalSets() {//delete
        return totalSets;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public List<WorkoutExercise> getWorkoutData() {
        return workoutData;
    }

    public List<Exercise> addExercise(Exercise exercise) {
        exerciseList.add(exercise);
        return exerciseList;
    }

    public List<WorkoutExercise> addWorkoutExercises(List<Map<String,String>> map) {//this is how workoutData will be gotten
        int x = 0;
        for(Exercise e:exerciseList) {
            WorkoutExercise we = new WorkoutExercise(e);
            we.setWorkoutData(map.get(x));
            workoutData.add(we);
        }
        return workoutData;
    }

    public Map<String,String> getExerciseSets() {
        Map<String,String> exerciseSets = new Hashtable<>();
        for(Exercise exercise : exerciseList) {
            exerciseSets.put(exercise.getExerciseName(),exercise.getGoalSets());
        }
        return exerciseSets;
    }

    public Map<String,String> getExerciseReps() {
        Map<String,String> exerciseReps = new Hashtable<>();
        for(Exercise exercise : exerciseList) {
            exerciseReps.put(exercise.getExerciseName(),exercise.getGoalReps());
        }
        return exerciseReps;
    }
}
