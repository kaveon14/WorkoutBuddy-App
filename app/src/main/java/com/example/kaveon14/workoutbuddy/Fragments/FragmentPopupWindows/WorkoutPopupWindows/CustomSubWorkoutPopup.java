package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;
// create class before that asks if they want to add a new subworkout like exercise fragmenent
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

// and don't allow editing of mainWorkout after done testing
public class CustomSubWorkoutPopup extends PopupWindowManager {

    private ArrayAdapter adapter;
    private List<String> subWorkoutNames;
    private String clkickedMainWorkoutName;
    private int subWorkoutCount;

    public CustomSubWorkoutPopup(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.simple_edittext_button_popup_layout);
        setPopupViewId(R.id.subWorkout_popupWindow);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setTextView();
        setAddSubWorkoutBtn();
    }

    public void setSubWorkoutCount(int subWorkoutCount) {
        this.subWorkoutCount = subWorkoutCount;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }

    public void setClickedMainWorkout(String clickedMainWorkoutName) {
        this.clkickedMainWorkoutName = clickedMainWorkoutName;
    }

    public void subWorkoutNamesList(List<String> subWorkoutNames) {
        this.subWorkoutNames = subWorkoutNames;
    }

    private void setTextView() {
        EditText editText = (EditText) popupLayout.findViewById(R.id.subWorkoutPopup_editText);
        editText.setBackgroundColor(Color.WHITE);

    }

    private void setAddSubWorkoutBtn() {
        Button btn = (Button) popupLayout.findViewById(R.id.subWorkoutPopupBtn);
        btn.setText("Add SubWorkout");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subWorkoutCount<15) {
                    addNewSubWorkoutOnClick();
                    Toast.makeText(context, "SubWorkout Successfully Created!", Toast.LENGTH_LONG).show();
                } else {
                    popupWindow.dismiss();
                    Toast.makeText(context, "Maximum of 15 SubWorkouts Allowed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addNewSubWorkoutOnClick() {
        String subWorkoutName = getSubWorkoutName();
        addSubWorkoutToDatatable(subWorkoutName);
        subWorkoutNames.add(subWorkoutName);
        subWorkoutCount++;
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void addSubWorkoutToDatatable(String subWorkoutName) {
        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(context);
        String tableName = subWorkoutTable.getCorrectTableName(clkickedMainWorkoutName
                ,subWorkoutName);

        subWorkoutTable.addSubWorkoutTable(tableName);

       // MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        //mainWorkoutTable.addSubWorkout(MainWorkoutFragment.getClickedMainWorkoutName()
          //      ,subWorkoutName);
    }

    private String getSubWorkoutName() {
        EditText subWorkoutEditText = (EditText) popupLayout.findViewById(R.id.subWorkoutPopup_editText);
        return subWorkoutEditText.getText().toString();
    }

}
