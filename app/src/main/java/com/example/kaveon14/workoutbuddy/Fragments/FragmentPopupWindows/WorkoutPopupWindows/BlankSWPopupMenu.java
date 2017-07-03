package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.view.View;
import android.widget.Button;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class BlankSWPopupMenu extends PopupWindowManager {

    private List<Exercise> exerciseList;

    public BlankSWPopupMenu(View root) {
        setRootView(root);
        setPopupLayout(R.layout.workout_popup_menu);
        setPopupViewId(R.id.workoutPopupMenu);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setViewExerciseBtn();
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
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
        //show popup window
    }

    private void setStartWorkoutBtn() {
        //show popup window
    }

    private void setSaveWorkoutBtn() {
        //show popup window
    }

}
