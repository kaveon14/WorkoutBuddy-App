package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.LiftingStatsTable;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;

import static com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.BlankSWPopupMenu.data;
import static com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.BlankSWPopupMenu.workoutData;

public class GetDatePopup extends PopupWindowManager {

    public GetDatePopup(View root) {
        setRootView(root);
        setPopupLayout(R.layout.subworkout_popup_layout);
        setPopupViewId(R.id.subWorkout_popupWindow);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setEditText();
        addDateBtn();
    }

    private void setEditText() {
        EditText editText = (EditText) popupLayout.findViewById(R.id.subWorkoutPopup_editText);
        editText.setText("YYYY-MM-DD");
    }

    private void addDateBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.subWorkoutPopupBtn);
        btn.setText("Add Date");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//do stuff
                LiftingStatsTable table = new LiftingStatsTable(context);
                String date = getDate();//here it the error
                SubWorkout subWorkout = SubWorkoutFragment.clickedSubWorkout;
                subWorkout.setDate(date);

                table.addWorkoutData(data,subWorkout);

                //table.addAWorkout(workoutData, subWorkout);//get date from subworkout
                Toast.makeText(context,"Date Added and Workout Saved",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                workoutData = null;
            }
        });
    }

    private String getDate() {
        EditText editText = (EditText) popupLayout.findViewById(R.id.subWorkoutPopup_editText);
        return editText.getText().toString();
    }
}
