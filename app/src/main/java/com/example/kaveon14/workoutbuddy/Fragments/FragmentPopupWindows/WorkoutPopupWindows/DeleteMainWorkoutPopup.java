package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;

import android.content.Context;
import android.graphics.Color;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kaveon14.workoutbuddy.DataBase.Data.MainWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;

import java.util.ArrayList;
import java.util.List;
// TODO put context in constructor for all pw's
public class DeleteMainWorkoutPopup extends PopupWindowManager {

    private int position;
    private MainWorkoutFragment.RecyclerAdapter recyclerAdapter;
    private List<MainWorkout> mainWorkoutList;

    public DeleteMainWorkoutPopup(View root,Context context) {
        setRootView(root);
        setPopupViewId(R.id.deleteSubWorkoutPopup);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.deletesubworkout_popup_layout);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setMainWorkoutListView();
        Button btn = (Button) popupLayout.findViewById(R.id.button);
        btn.setText("Delete MainWorkout");
    }

    public void setRecyclerAdapter(MainWorkoutFragment.RecyclerAdapter recyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter;
    }

    public void setMainWorkoutList(List<MainWorkout> mainWorkoutList) {
        this.mainWorkoutList = mainWorkoutList;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void setMainWorkoutListView() {
        ListView listView = (ListView) popupLayout.findViewById(R.id.deleteSubWorkoutPopup_listView);
       // listView.setAdapter(mainWorkoutAdapter);
        listView.setBackgroundColor(Color.WHITE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                if(position != 0) {
                    resetSubWorkoutListViewColors(parent);
                    setDeleteButton(mainWorkoutList.get(position));
                } else {
                    resetDeleteButton();
                    Toast.makeText(context,"Can Not Delete This MainWorkout!"
                            ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDeleteButton(MainWorkout mainWorkout) {
        Button btn = (Button) popupLayout.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMainWorkout(mainWorkout);
                Toast.makeText(context,"MainWorkout Successfully Deleted!"
                        ,Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    private void resetDeleteButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Can Not Delete This MainWorkout!"
                        ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteMainWorkout(MainWorkout mainWorkout) {
        mainWorkoutList.remove(mainWorkout);
        recyclerAdapter.notifyItemRemoved(position);

        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        mainWorkoutTable.deleteMainWorkout(mainWorkout.getMainWorkoutName());
    }

    private void resetSubWorkoutListViewColors(AdapterView<?> parent) {
        for(int x=0;x<parent.getCount();x++) {
            View view = parent.getChildAt(x);
            view.setBackgroundColor(Color.WHITE);
        }
    }
}
