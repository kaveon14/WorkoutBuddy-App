package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;

import java.util.List;

public class DeleteSubWorkoutPopup extends PopupWindowManager {

    private ArrayAdapter subWorkoutAdapter;
    private List<String> subWorkoutNames;

    public DeleteSubWorkoutPopup(View root) {
        setRootView(root);
        setPopupLayout(R.layout.deletesubworkout_popup_layout);
        setPopupViewId(R.id.deleteSubWorkoutPopup);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        listView();
    }

    public void setSubWorkoutAdapter(ArrayAdapter subWorkoutAdapter) {
        this.subWorkoutAdapter = subWorkoutAdapter;
    }

    public void setSubWorkoutNames(List<String> subWorkoutNames) {
        this.subWorkoutNames = subWorkoutNames;
    }

    private void listView() {
        ListView listView = (ListView) popupLayout.findViewById(R.id.deleteSubWorkoutPopup_listView);
        listView.setAdapter(subWorkoutAdapter);
        listView.setBackgroundColor(Color.WHITE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetSubWorkoutListViewColors(parent);
                parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                btn(parent.getItemAtPosition(position).toString());
            }
        });
    }

    private void btn(String subWorkoutName) {
        Button btn = (Button) popupLayout.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSubWorkout(subWorkoutName);
            }
        });
    }

    private void deleteSubWorkout(String subWorkoutName) {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        //methods not created yet

        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(context);
        //methods not created yet
    }

    private void resetSubWorkoutListViewColors(AdapterView<?> parent) {
        for(int x=0;x<parent.getCount();x++) {
            View view = parent.getChildAt(x);
            view.setBackgroundColor(Color.WHITE);
        }
    }

}
