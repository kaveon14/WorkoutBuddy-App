package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;

public class ViewCustomExercisesPopup extends PopupWindowManager {

    private boolean fromSubWorkout;
    private PopupWindow mainPopupWindow;
    private ArrayAdapter customExerciseAdapter;
    private List<Exercise> customExerciseList;

    public ViewCustomExercisesPopup(View root) {
        setRootView(root);
        setPopupLayout(R.layout.deletesubworkout_popup_layout);
        setPopupViewId(R.id.deleteSubWorkoutPopup);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setButton();
        setCustomExerciseListView();
    }

    public void setFromSubWorkout(boolean fromSubWorkout) {
        this.fromSubWorkout = fromSubWorkout;
    }

    public void setMainPopupWindow(PopupWindow mainPopupWindow) {
        this.mainPopupWindow = mainPopupWindow;
    }

    public void setCustomExerciseList(List<Exercise> customExerciseList) {
        this.customExerciseList = customExerciseList;
    }

    private void setButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.button);
        btn.setVisibility(View.INVISIBLE);
    }

    private void setCustomExerciseListView() {
        List<String> names = new ArrayList<>();
        for(int x=0;x<customExerciseList.size();x++) {
            names.add(customExerciseList.get(x).getExerciseName());
        }
        customExerciseAdapter = new ArrayAdapter(context,R.layout.simple_list_item,names);
        ListView listView = (ListView) popupLayout.findViewById(R.id.deleteSubWorkoutPopup_listView);
        listView.setAdapter(customExerciseAdapter);
        listView.setBackgroundColor(Color.WHITE);
        listViewOnClick(listView);
        listViewOnLongClick(listView);
    }

    private void listViewOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetExerciseListViewColors(parent);
                parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                ExerciseFragment.clickedExercise = customExerciseList.get(position);
                mainPopupWindow.dismiss();
                popupWindow.dismiss();
                MainActivity.activity.showBlankExerciseFragment();
            }
        });
    }

    private void listViewOnLongClick(ListView listView) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ExerciseFragment.clickedExercise = customExerciseList.get(position);
                mainPopupWindow.dismiss();
                popupWindow.dismiss();
                ExerciseToWorkoutPopup popup = new ExerciseToWorkoutPopup(popupLayout,fromSubWorkout);
                popup.showPopupWindow();
                return true;
            }
        });
    }

    private void resetExerciseListViewColors(AdapterView<?> parent) {
        for(int x=0;x<parent.getCount();x++) {
            View view = parent.getChildAt(x);
            view.setBackgroundColor(Color.WHITE);
        }
    }
}