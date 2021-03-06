package com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.MainWorkout;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment;
import com.example.WorkoutBuddy.workoutbuddy.R;
import java.util.List;

public class MainWorkoutPopupMenu extends PopupWindowManager {

    private List<MainWorkout> mainWorkoutList;
    private MainWorkoutFragment.RecyclerAdapter recyclerAdapter;

    public MainWorkoutPopupMenu(View root,Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.popup_window);
        setPopupViewId(R.id.popupWin);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        hidePopupBtn();
        setAddCustomMainWorkoutBtn();
        setDeleteMainWorkoutBtn();
    }

    public void setRecyclerAdapter(MainWorkoutFragment.RecyclerAdapter recyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter;
    }

    public void setMainWorkoutList(List<MainWorkout> mainWorkoutList) {
        this.mainWorkoutList = mainWorkoutList;
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
        CustomMainWorkoutPopup pop = new CustomMainWorkoutPopup(getRootView(),context);
        pop.setRecyclerAdapter(recyclerAdapter);
        pop.setMainWorkoutList(mainWorkoutList);
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
        DeleteMainWorkoutPopup popup = new DeleteMainWorkoutPopup(getRootView(),context);
        popup.setMainWorkoutList(mainWorkoutList);
        popup.setRecyclerAdapter(recyclerAdapter);
        popup.showPopupWindow();
    }
}
