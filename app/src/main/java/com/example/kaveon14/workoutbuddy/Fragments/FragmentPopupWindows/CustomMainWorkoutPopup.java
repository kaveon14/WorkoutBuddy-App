package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows;


import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class MWPop extends PopupWindowManager {

    private List<String> mainWorkoutNames;
    private ArrayAdapter adapter;

    public MWPop(View root) {
        setRootView(root);
        setPopupLayout(R.layout.mainworkout_popup_layout);
        setPopupViewId(R.id.mainWorkout_popupWindow);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        popupButtonClicked();
        setupPopupWindowContent();
    }

    public void setMainWorkoutList(List<String> mainWorkoutNames) {
        this.mainWorkoutNames = mainWorkoutNames;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }

    private void popupButtonClicked() {
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

    private void setupPopupWindowContent() {
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
