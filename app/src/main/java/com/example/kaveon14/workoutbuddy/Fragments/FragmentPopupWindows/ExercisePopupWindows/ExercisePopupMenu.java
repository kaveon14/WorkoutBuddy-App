package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows;

import android.view.View;
import android.widget.Button;
import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class ExercisePopupMenu extends PopupWindowManager {

    private List<Exercise> customExerciseList;
    private boolean fromSubWorkout;

    public ExercisePopupMenu(View root) {
        setRootView(root);
        setPopupLayout(R.layout.popup_window);
        setPopupViewId(R.id.popupWin);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        addCustomExerciseBtn();
        showCustomExercisesBtn();
        deleteCustomExercisesBtn();
    }

    public void setCustomExerciseList(List<Exercise> customExerciseList) {
        this.customExerciseList = customExerciseList;
    }

    public void setFromSubWorkout(boolean fromSubWorkout) {
        this.fromSubWorkout = fromSubWorkout;
    }

    private void addCustomExerciseBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.addCustomExerciseBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.showAddExercisePopupWindow();
            }
        });
    }

    private void showCustomExercisesBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.showCustomExercisesBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showCustomExercisePopupWindow();
            }
        });
    }

    private void showCustomExercisePopupWindow() {
        ViewCustomExercisesPopup popup = new ViewCustomExercisesPopup(getRootView());
        popup.setCustomExerciseList(customExerciseList);
        popup.setMainPopupWindow(popupWindow);
        popup.setFromSubWorkout(fromSubWorkout);
        popup.showPopupWindow();
    }

    private void deleteCustomExercisesBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.deleteCustomExerciseBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteExercisePopupWindow();
            }
        });
    }

    private void showDeleteExercisePopupWindow() {
        DeleteExercisesPopup popup = new DeleteExercisesPopup(getRootView());
        popup.setCustomExerciseList(customExerciseList);
        popup.showPopupWindow();
    }
}
