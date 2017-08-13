package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaveon14.workoutbuddy.DataBase.Data.MainWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class CustomMainWorkoutPopup extends PopupWindowManager {

    private MainWorkoutFragment.RecyclerAdapter recyclerAdapter;
    private List<MainWorkout> mainWorkoutList;

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

    public void setRecyclerAdapter(MainWorkoutFragment.RecyclerAdapter recyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter;
    }

    public void setMainWorkoutList(List<MainWorkout> mainWorkoutList) {
        this.mainWorkoutList = mainWorkoutList;
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
        addMainWorkoutToDataTable(mainWorkoutName);
        mainWorkoutList.add(new MainWorkout(mainWorkoutName,null));
        recyclerAdapter.notifyDataSetChanged();
    }

    private void addMainWorkoutToDataTable(String mainWorkoutName) {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        mainWorkoutTable.addMainWorkout(mainWorkoutName);
    }

    private String getMainWorkoutName() {
        EditText mainWorkoutEditText = (EditText) popupLayout.findViewById(R.id.mainWorkoutPopup_editText);
        return mainWorkoutEditText.getText().toString();
    }
}
