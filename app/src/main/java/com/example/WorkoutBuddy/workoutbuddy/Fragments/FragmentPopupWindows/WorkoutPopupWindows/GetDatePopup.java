package com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.WorkoutStatsTable;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.WorkoutBuddy.workoutbuddy.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.BlankSWPopupMenu.workoutData;

public class GetDatePopup extends PopupWindowManager {

    public GetDatePopup(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.simple_edittext_button_popup_layout);
        setPopupViewId(R.id.subWorkout_popupWindow);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setEditText();
        addDateBtn();
    }

    private void setEditText() {
        EditText editText = (EditText) popupLayout.findViewById(R.id.subWorkoutPopup_editText);
        DateFormat dateFormat = new SimpleDateFormat("MM/getProgressPhoto/yyyy");
        String date = dateFormat.format(new Date());
        editText.setText(date);
    }

    private void addDateBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.subWorkoutPopupBtn);
        btn.setText("Add Date");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addWorkoutData();
                popupWindow.dismiss();
            }
        });
    }

    private void addWorkoutData() {
        WorkoutStatsTable table = new WorkoutStatsTable(context);
        String date = getDate();
        SubWorkout subWorkout = SubWorkoutFragment.getClickedSubWorkout();
        subWorkout.setDate(date);

        table.addWorkoutData(workoutData, subWorkout);
        Toast.makeText(context, "Date Added and Workout Saved", Toast.LENGTH_SHORT).show();
        workoutData = null;
    }

    private String getDate() {
        EditText editText = (EditText) popupLayout.findViewById(R.id.subWorkoutPopup_editText);
        return editText.getText().toString();
    }
}
