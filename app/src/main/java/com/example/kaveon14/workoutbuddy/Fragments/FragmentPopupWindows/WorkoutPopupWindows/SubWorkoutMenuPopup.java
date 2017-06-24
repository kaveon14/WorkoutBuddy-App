package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class SubWorkoutMenuPopup extends PopupWindowManager {

    private ArrayAdapter subWorkoutAdapter;
    private List<String> subWorkoutNames;

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

    public void setSubWorkoutAdapter(ArrayAdapter subWorkoutAdapter) {
        this.subWorkoutAdapter = subWorkoutAdapter;
    }

    public void setSubWorkoutNames(List<String> subWorkoutNames) {
        this.subWorkoutNames = subWorkoutNames;
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
                popupWindow.dismiss();
            }
        });
    }

    private void showCustomSubWorkoutPopup() {
        CustomSubWorkoutPopup pop = new CustomSubWorkoutPopup(getRootView());
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
        popup.setSubWorkoutAdapter(subWorkoutAdapter);
        popup.setSubWorkoutNames(subWorkoutNames);
        popup.showPopupWindow();
    }
}
