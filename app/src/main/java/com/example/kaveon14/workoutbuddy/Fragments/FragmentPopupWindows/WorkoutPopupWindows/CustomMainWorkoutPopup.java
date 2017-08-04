package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class CustomMainWorkoutPopup extends PopupWindowManager {

    private List<String> mainWorkoutNames;
    private ArrayAdapter adapter;

    public CustomMainWorkoutPopup(View root,Context context) {
        setRootView(root);
        setPopupViewId(R.id.mainWorkout_popupWindow);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.mainworkout_popup_layout);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setAddMainWorkoutBtn();
        setTextView();
    }

    public void setMainWorkoutList(List<String> mainWorkoutNames) {
        this.mainWorkoutNames = mainWorkoutNames;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }

    private void setAddMainWorkoutBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.mainWorkoutPopupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMainWorkoutOnClick();
                Toast.makeText(context,"MainWorkout Successfully Created!",Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });
    }

    private void setTextView() {
        EditText editText = (EditText) popupLayout.findViewById(R.id.mainWorkoutPopup_editText);
        editText.setBackgroundColor(Color.WHITE);

    }

    private void addNewMainWorkoutOnClick() {
        String mainWorkoutName = getMainWorkoutName();
        addMainWorkoutToDatatable(mainWorkoutName);
        mainWorkoutNames.add(mainWorkoutName);
        adapter.notifyDataSetChanged();
    }

    private void addMainWorkoutToDatatable(String mainWorkoutName) {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        mainWorkoutTable.addMainWorkout(mainWorkoutName);
    }

    private String getMainWorkoutName() {
        EditText mainWorkoutEditText = (EditText) popupLayout.findViewById(R.id.mainWorkoutPopup_editText);
        return mainWorkoutEditText.getText().toString();
    }
}
