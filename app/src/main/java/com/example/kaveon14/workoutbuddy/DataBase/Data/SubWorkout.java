package com.example.kaveon14.workoutbuddy.DataBase.Data;
// TODO implement the actual main workout name into this
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SubWorkout {

    private String mainWorkoutName;
    private String subWorkoutName;
    private List<Exercise> exerciseList;

    public SubWorkout() {

    }

    public SubWorkout(String subWorkoutName, List<Exercise> exerciseList) {
        this.subWorkoutName = subWorkoutName;
        this.exerciseList = exerciseList;

    }

    public void setMainWorkoutName(String mainWorkoutName) {
        this.mainWorkoutName = mainWorkoutName;
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

    public List<Exercise> addExercise(Exercise exercise) {
        exerciseList.add(exercise);
        return exerciseList;
    }

    public Map<String,String> getExerciseSets() {
        Map<String,String> exerciseSets = new Hashtable<>();
        for(Exercise exercise : exerciseList) {
            exerciseSets.put(exercise.getExerciseName(),exercise.getExerciseSets());
        }
        return exerciseSets;
    }

    public Map<String,String> getExerciseReps() {
        Map<String,String> exerciseReps = new Hashtable<>();
        for(Exercise exercise : exerciseList) {
            exerciseReps.put(exercise.getExerciseName(),exercise.getExerciseReps());
        }
        return exerciseReps;
    }
}
