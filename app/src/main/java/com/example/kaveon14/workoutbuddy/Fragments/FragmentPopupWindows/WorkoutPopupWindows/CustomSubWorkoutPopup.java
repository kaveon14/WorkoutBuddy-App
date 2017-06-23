package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;
// create class before that asks if they want to add a new subworkout like exercise fragmenent
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
// TODO DELETE the day part of the mainWorkout table and instead tell user to delete a subWorkout first if limit is at 7
// and dont allow editing of mainWorkout after done testing
public class CustomSubWorkoutPopup extends PopupWindowManager {

    private int SUBWORKOUT_DAY = 1;
    private ArrayAdapter adapter;
    private List<String> subWorkoutNames;

    public CustomSubWorkoutPopup(View root) {
        setRootView(root);
        setPopupLayout(R.layout.subworkout_popup_layout);
        setPopupViewId(R.id.subWorkout_popupWindow);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setTextView();
        setAddSubWorkoutBtn();
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewSubWorkoutOnClick();
                Toast.makeText(context,"SubWorkout Successfully Created!",Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });
    }

    private void addNewSubWorkoutOnClick() {
        String subWorkoutName = getSubWorkoutName();
        addSubWorkoutToDatatable(subWorkoutName);
        subWorkoutNames.add(subWorkoutName);
        if(adapter != null) {
            adapter.notifyDataSetChanged();//error here for some reason
        }
        SUBWORKOUT_DAY++;
    }

    private void addSubWorkoutToDatatable(String subWorkoutName) {
        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(context);
        subWorkoutTable.addSubWorkoutTable(subWorkoutName);

        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        mainWorkoutTable.addSubWorkout(MainWorkoutFragment.clickedMainWorkout
                ,subWorkoutName);//day must be done differently
    }

    private String getSubWorkoutName() {
        EditText subWorkoutEditText = (EditText) popupLayout.findViewById(R.id.subWorkoutPopup_editText);
        return subWorkoutEditText.getText().toString();
    }

}
