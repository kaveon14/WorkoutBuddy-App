package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankSubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;

public class BlankSWPopupMenu extends PopupWindowManager {

    private List<Exercise> exerciseList;
    private BlankSubWorkoutFragment.WorkoutAdapter adapter;
    public static List<WorkoutExercise> workoutData = new ArrayList<>(15);
    private MainActivity mainActivity;

    public BlankSWPopupMenu(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.workout_popup_menu);
        setPopupViewId(R.id.workoutPopupMenu);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
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
        ViewExercisesPopup popup = new ViewExercisesPopup(getRootView(),context);
        popup.setMainPopupWindow(popupWindow);
        popup.setMainActivity(mainActivity);
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
        DeleteExFromSWPopup popup = new DeleteExFromSWPopup(getRootView(),context);
        popup.setWorkoutAdapter(adapter);
        popup.setExerciseList(exerciseList);
        popup.showPopupWindow();
    }

    private void setStartWorkoutBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.startWorkoutButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // adapter.showCheckBoxes();will be deleted
                popupWindow.dismiss();
                workoutData = new ArrayList<>(5);
                Toast.makeText(context, SubWorkoutFragment.getClickedSubWorkout().getSubWorkoutName()
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
                popupWindow.dismiss();
                if(workoutData.size() != 0) {
                    showGetDatePopup();
                } else {
                    Toast.makeText(context,"No workoutData added to Workout"+System.lineSeparator()
                            +"Push the 'DD WORKOUT DATA BUTTON' and try again! ",Toast.LENGTH_LONG)
                            .show();
                }
                btn.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showGetDatePopup() {
        GetDatePopup popup = new GetDatePopup(getRootView(),context);
        popup.showPopupWindow();
    }
}
