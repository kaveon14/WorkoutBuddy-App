package com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.WorkoutBuddy.workoutbuddy.R;
import java.util.List;

public class SubWorkoutMenuPopup extends PopupWindowManager {

    private ArrayAdapter subWorkoutAdapter;
    private List<String> subWorkoutNames;
    private String clickedMainWorkoutName;
    private int subWorkoutCount;

    public SubWorkoutMenuPopup(View root,Context context) {
        setRootView(root);
        setWindowManagerContext(context);
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
        CustomSubWorkoutPopup pop = new CustomSubWorkoutPopup(getRootView(),context);
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
        DeleteSubWorkoutPopup popup = new DeleteSubWorkoutPopup(getRootView(),context);
        popup.setCurrentSubWorkoutCount(subWorkoutCount);
        popup.setSubWorkoutAdapter(subWorkoutAdapter);
        popup.setSubWorkoutNames(subWorkoutNames);
        popup.showPopupWindow();
    }
}
