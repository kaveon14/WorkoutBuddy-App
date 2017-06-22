package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment.clickedMainWorkout;

public class ExercisePopupMenu extends PopupWindowManager {//nice everything is working just
//create rest of buttons

    public ExercisePopupMenu(View root) {
        setRootView(root);
        setPopupLayout(R.layout.popup_window);
        setPopupViewId(R.id.popupWin);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        addCustomExerciseBtn();
    }

    private void addCustomExerciseBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.showAddExercisePopupWindow();
                popupWindow.dismiss();
            }
        });
    }

    private ListView setPopupListView() {//not needed here
        ListView listView = (ListView) popupLayout.findViewById(R.id.exercisePopup_listView);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(getMainWorkoutAdapter());
        return listView;
    }

    private ArrayAdapter getMainWorkoutAdapter() {//not needed
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        List<String> list = mainWorkoutTable.getMainWorkoutNames();
        ArrayAdapter adapter = new ArrayAdapter<>(context,
                R.layout.simple_list_item,list);
        return adapter;
    }

    private ArrayAdapter getSubWorkoutAdapter() {//not needed
        MainWorkoutTable workoutTable = new MainWorkoutTable(context);
        List<String> list = workoutTable.getSubWorkoutNames(clickedMainWorkout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.simple_list_item,list);
        return adapter;
    }
}
