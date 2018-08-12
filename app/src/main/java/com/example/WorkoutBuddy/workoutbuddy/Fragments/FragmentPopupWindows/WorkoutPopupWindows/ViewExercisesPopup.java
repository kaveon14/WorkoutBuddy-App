package com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.example.WorkoutBuddy.workoutbuddy.Activities.MainActivity;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.Exercise;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.WorkoutBuddy.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;

public class ViewExercisesPopup extends PopupWindowManager {

    private List<Exercise> exerciseList;
    private ArrayAdapter exerciseAdapter;
    private PopupWindow mainPopupWindow;
    private MainActivity mainActivity;

    public ViewExercisesPopup(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.view_exercise_popup);
        setPopupViewId(R.id.viewExercisesPopup);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setListView();
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void setMainPopupWindow(PopupWindow mainPopupWindow) {
        this.mainPopupWindow = mainPopupWindow;
    }

    private void setListView() {
        List<String> exerciseNames = new ArrayList<>(10);
        for(int x=0;x<exerciseList.size();x++) {
            exerciseNames.add(exerciseList.get(x).getExerciseName());
        }
        exerciseAdapter = new ArrayAdapter(context,R.layout.simple_list_item,exerciseNames);
        ListView listView = (ListView) popupLayout.findViewById(R.id.exerciseListView_popup);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(exerciseAdapter);
        if(exerciseAdapter.isEmpty()) {
           listView.setEmptyView(popupLayout.findViewById(R.id.emptyListView));
        }
        listViewOnClick(listView);
    }

    private void listViewOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//load ex from table
                ExerciseTable table = new ExerciseTable(context);
                Exercise exercise = table.getExercise(exerciseList.get(position).getExerciseName());
                ExerciseFragment.setClickedExercise(exercise);
                mainActivity.showBlankExerciseFragment();//think about doing same for ex frag,only load  ex names
                mainPopupWindow.dismiss();
                popupWindow.dismiss();
            }
        });
    }
}
