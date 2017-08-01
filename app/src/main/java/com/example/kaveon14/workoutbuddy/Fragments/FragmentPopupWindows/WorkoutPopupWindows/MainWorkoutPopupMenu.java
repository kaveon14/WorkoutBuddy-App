package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class MainWorkoutPopupMenu extends PopupWindowManager {

    private ArrayAdapter mainWorkoutAdapter;
    private List<String> mainWorkoutNames;

    public MainWorkoutPopupMenu(View root) {
        setRootView(root);
        setPopupLayout(R.layout.popup_window);
        setPopupViewId(R.id.popupWin);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        hidePopupBtn();
        setAddCustomMainWorkoutBtn();
        setDeleteMainWorkoutBtn();
    }

    public void setMainWorkoutAdapter(ArrayAdapter mainWorkoutAdapter) {
        this.mainWorkoutAdapter = mainWorkoutAdapter;
    }

    public void setMainWorkoutNames(List<String> mainWorkoutNames) {
        this.mainWorkoutNames = mainWorkoutNames;
    }

    private void hidePopupBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.addCustomExerciseBtn);
        btn.setVisibility(View.INVISIBLE);
    }

    private void setAddCustomMainWorkoutBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.deleteCustomExerciseBtn);
        btn.setText("Add Custom MainWorkout");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomMainWorkoutPopup();
            }
        });
    }

    private void showCustomMainWorkoutPopup() {
        CustomMainWorkoutPopup pop = new CustomMainWorkoutPopup(getRootView());
        pop.setMainWorkoutList(mainWorkoutNames);
        pop.setAdapter(mainWorkoutAdapter);
        pop.showPopupWindow();
    }

    private void setDeleteMainWorkoutBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.showCustomExercisesBtn);
        btn.setText("Delete a MainWorkout");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteMainWorkoutPopup();
            }
        });
    }
    private void showDeleteMainWorkoutPopup() {
       DeleteMainWorkoutPopup popup = new DeleteMainWorkoutPopup(getRootView());
        popup.setMainWorkoutAdapter(mainWorkoutAdapter);
        popup.setMainWorkoutNames(mainWorkoutNames);
        popup.showPopupWindow();
    }

}
