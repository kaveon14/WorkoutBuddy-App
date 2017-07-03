package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.view.View;

import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;

public class BlankSWPopupMenu extends PopupWindowManager {

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

    private void setViewExerciseBtn() {
        //show popup window
    }

    private void setDeleteExerciseBtn() {
        //show popup window
    }

    private void setStartWorkoutBtn() {
        //show popup window
    }

    private void setSaveWorkoutBtn() {
        //show popup window
    }

}
