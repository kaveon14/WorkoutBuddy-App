package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankSubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class DeleteExFromSWPopup extends PopupWindowManager {

    private List<Exercise> exerciseList;
    private ListView listView;
    BlankSubWorkoutFragment.WorkoutAdapter adapter;
    private int position;

    public DeleteExFromSWPopup(View root) {
        setRootView(root);
        setPopupLayout(R.layout.bodystats_popup_layout);
        setPopupViewId(R.id.bodyStats_popupWindow);
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setAdapter(BlankSubWorkoutFragment.WorkoutAdapter adapter) {
        this.adapter = adapter;
    }

    public void setListViewPosition(int position) {
        this.position = position;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setTextView();
        setYesButton();
        setNoButton();
    }

    private void setTextView() {
        TextView textView = (TextView) popupLayout.findViewById(R.id.bodystatsPopup_textView);
        textView.setText("Would You Like to Delete an Exercise?");
    }

    private void setYesButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.bodyStats_yes_popupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise exercise = exerciseList.get(position);
                deleteExerciseFromSubWorkout(exercise);
                Toast.makeText(context,"Exercise Successfully Deleted!"
                        ,Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    private void deleteExerciseFromSubWorkout(Exercise exercise) {
        SubWorkoutTable subWorkoutTable  = new SubWorkoutTable(context);
        subWorkoutTable.deleteExerciseFromSubWorkout(exercise
                , SubWorkoutFragment.clickedSubWorkout.getSubWorkoutName());
        adapter.notifyDataSetChanged();
        exerciseList.remove(exercise);
    }


    private void setNoButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.bodyStats_no_popupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


}
