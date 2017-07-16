package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;
// TODO change all string addition to use String builder to valsy increase speed
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Workout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.LiftingStatsTable;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankSubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.WorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
// TODO get date
public class BlankSWPopupMenu extends PopupWindowManager {

    private List<Exercise> exerciseList;
    private BlankSubWorkoutFragment.WorkoutAdapter adapter;
    public static List<Exercise> workoutData;//need to change type of data stored
    public static List<WorkoutExercise> data = new ArrayList<>(15);

    public BlankSWPopupMenu(View root) {
        setRootView(root);
        setPopupLayout(R.layout.workout_popup_menu);
        setPopupViewId(R.id.workoutPopupMenu);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setViewExerciseBtn();
        setDeleteExerciseBtn();
        setStartWorkoutBtn();
        setSaveWorkoutBtn();
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void setAdapter(BlankSubWorkoutFragment.WorkoutAdapter adapter) {
        this.adapter = adapter;
    }

    private void setViewExerciseBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.viewExerciseButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewExercisesPopup();
            }
        });
    }

    private void showViewExercisesPopup() {
        ViewExercisesPopup popup = new ViewExercisesPopup(getRootView());
        popup.setMainPopupWindow(popupWindow);
        popup.setExerciseList(exerciseList);
        popup.showPopupWindow();
    }

    private void setDeleteExerciseBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.deleteExerciseButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteExercisePopup();
            }
        });
    }

    private void showDeleteExercisePopup() {
        DeleteExFromSWPopup popup = new DeleteExFromSWPopup(getRootView());
        popup.setWorkoutAdapter(adapter);
        popup.setExerciseList(exerciseList);
        popup.showPopupWindow();
    }

    private void setStartWorkoutBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.startWorkoutButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // adapter.showCheckBoxes();
                popupWindow.dismiss();
                //start process to get data
                workoutData = new ArrayList<>(5);
                Toast.makeText(context, SubWorkoutFragment.clickedSubWorkout.getSubWorkoutName()
                        +" Workout Started",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSaveWorkoutBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.saveWorkoutButton);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workoutData == null) {
                    workoutData = new ArrayList<>(5);
                }
                adapter.hideCheckBoxes();
                popupWindow.dismiss();
                showGetDatePopup();
                btn.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showGetDatePopup() {
        GetDatePopup popup = new GetDatePopup(getRootView());
        popup.showPopupWindow();
    }
}
