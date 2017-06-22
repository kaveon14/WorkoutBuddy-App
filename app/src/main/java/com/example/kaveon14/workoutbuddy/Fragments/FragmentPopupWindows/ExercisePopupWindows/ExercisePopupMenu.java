package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class ExercisePopupMenu extends PopupWindowManager {//nice everything is working just
//create rest of buttons

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

    private void addCustomExerciseBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.showCustomExercisePopupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.showAddExercisePopupWindow();
                popupWindow.dismiss();
            }
        });
    }

    private void showCustomExercisesBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.showCustomExercisesBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }//need static list with all custom exercise

    private void deleteCustomExercisesBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.deleteCustomExerciseBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }//need static list with all custom exercise

    private ArrayAdapter getMainWorkoutAdapter() {//not needed
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        List<String> list = mainWorkoutTable.getMainWorkoutNames();
        ArrayAdapter adapter = new ArrayAdapter<>(context,
                R.layout.simple_list_item,list);
        return adapter;
    }
}
