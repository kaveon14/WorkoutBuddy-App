package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import com.example.kaveon14.workoutbuddy.Activities.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class ExercisePopupMenu extends PopupWindowManager {

    private List<Exercise> customExerciseList;
    private boolean fromSubWorkout;
    private MainActivity mainActivity;
    private ExerciseFragment exercise_frag;

    public ExercisePopupMenu(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.popup_window);
        setPopupViewId(R.id.popupWin);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        addCustomExerciseBtn();
        showCustomExercisesBtn();
        deleteCustomExercisesBtn();
    }

    public void setExerciseFragment(ExerciseFragment exercise_frag) {
        this.exercise_frag = exercise_frag;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
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
                mainActivity.showAddExercisePopupWindow();
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
        ViewCustomExercisesPopup popup = new ViewCustomExercisesPopup(getRootView(),context);
        popup.setCustomExerciseList(customExerciseList);
        popup.setMainPopupWindow(popupWindow);
        popup.setMainActivity(mainActivity);
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
        DeleteExercisesPopup popup = new DeleteExercisesPopup(getRootView(),context);
        popup.setCustomExerciseList(customExerciseList);
        popup.setExerciseFragment(exercise_frag);
        popup.showPopupWindow();
    }
}
