package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class SubWorkoutMenuPopup extends PopupWindowManager {

    private ArrayAdapter subWorkoutAdapter;
    private List<String> subWorkoutNames;
    private String clickedMainWorkoutName;
    private int subWorkoutCount;

    public SubWorkoutMenuPopup(View root) {
        setRootView(root);
        setPopupLayout(R.layout.popup_window);
        setPopupViewId(R.id.popupWin);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        hidePopupBtn();
        setAddCustomSubWorkoutBtn();
        setDeleteSubWorkoutBtn();
    }

    public void setClickedMainWorkoutName(String clickedMainWorkoutName) {
        this.clickedMainWorkoutName = clickedMainWorkoutName;
    }

    public void setSubWorkoutAdapter(ArrayAdapter subWorkoutAdapter) {
        this.subWorkoutAdapter = subWorkoutAdapter;
    }

    public void setSubWorkoutNames(List<String> subWorkoutNames) {
        this.subWorkoutNames = subWorkoutNames;
    }

    public void setCurrentSubWorkoutCount(int subWorkoutCount) {
        this.subWorkoutCount = subWorkoutCount;
    }

    private void hidePopupBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.addCustomExerciseBtn);
        btn.setVisibility(View.INVISIBLE);
    }

    private void setAddCustomSubWorkoutBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.deleteCustomExerciseBtn);
        btn.setText("Add Custom SubWorkout");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showCustomSubWorkoutPopup();
            }
        });
    }

    private void showCustomSubWorkoutPopup() {
        CustomSubWorkoutPopup pop = new CustomSubWorkoutPopup(getRootView());
        pop.setSubWorkoutCount(subWorkoutCount);
        pop.setClickedMainWorkout(clickedMainWorkoutName);
        pop.subWorkoutNamesList(subWorkoutNames);
        pop.setAdapter(subWorkoutAdapter);
        pop.showPopupWindow();
    }

    private void setDeleteSubWorkoutBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.showCustomExercisesBtn);
        btn.setText("Delete a SubWorkout");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteSubWorkoutPopup();
            }
        });
    }
    private void showDeleteSubWorkoutPopup() {
        DeleteSubWorkoutPopup popup = new DeleteSubWorkoutPopup(getRootView());
        popup.setCurrentSubWorkoutCount(subWorkoutCount);
        popup.setSubWorkoutAdapter(subWorkoutAdapter);
        popup.setSubWorkoutNames(subWorkoutNames);
        popup.showPopupWindow();
    }
}
