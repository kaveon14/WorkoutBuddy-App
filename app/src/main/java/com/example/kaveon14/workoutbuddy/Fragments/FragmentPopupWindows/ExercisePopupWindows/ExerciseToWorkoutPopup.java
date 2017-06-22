package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment.clickedExercise;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment.clickedMainWorkout;

public class ExerciseToWorkoutPopup extends PopupWindowManager {

    private boolean fromSubWorkout;

    public ExerciseToWorkoutPopup(View root, boolean fromSubWorkout) {
        this.fromSubWorkout = fromSubWorkout;
      setRootView(root);
      setPopupLayout(R.layout.exercise_popup_layout);
      setPopupViewId(R.id.exercise_popupWindow);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        handlePopupEvents(setPopupListView(popupLayout));
    }

    private ListView setPopupListView(View popupLayout) {
        ListView listView = (ListView) popupLayout.findViewById(R.id.exercisePopup_listView);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(getMainWorkoutAdapter());
        return listView;
    }

    private void handlePopupEvents(ListView listView) {
        if(!fromSubWorkout) {
            mainWorkoutClicked(listView);
        } else {
            listView.setVisibility(View.INVISIBLE);
            showPartialSubWorkoutPopup();
        }
    }

    private void mainWorkoutClicked(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedMainWorkout = parent.getItemAtPosition(position).toString();
                listView.setAdapter(getSubWorkoutAdapter());
                subWorkoutClicked(listView);
            }
        });
    }

    private void subWorkoutClicked(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetSubWorkoutListViewColors(parent);
                parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                showFullSubWorkoutPopup(listView,position);
            }
        });
    }

    private void resetSubWorkoutListViewColors(AdapterView<?> parent) {
        for(int x=0;x<parent.getCount();x++) {
            View view = parent.getChildAt(x);
            view.setBackgroundColor(Color.WHITE);
        }
    }


    private void showFullSubWorkoutPopup(AdapterView<?> parent,int position) {
        String subWorkoutName = parent.getItemAtPosition(position).toString();
        popupButtonClicked(clickedExercise, subWorkoutName);
        setUpTextViews();
        showPopupButton();
    }

    private void showPartialSubWorkoutPopup() {
        setUpTextViews();
        showPopupButton();
        popupButtonClicked(clickedExercise,
                SubWorkoutFragment.clickedSubWorkout.getSubWorkoutName());
    }

    private void showPopupButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.exercisePopupBtn);
        btn.setVisibility(View.VISIBLE);
    }

    private void setUpTextViews() {
        setSetsView();
        setRepsView();
    }

    private void setSetsView() {
        EditText setsView = (EditText) popupLayout.findViewById(R.id.exercisePopup_setsTextView);
        setsView.setBackgroundColor(Color.WHITE);
        setsView.setVisibility(View.VISIBLE);
        setsView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (setsView.getText().toString().equalsIgnoreCase("Sets:"))
                    setsView.getText().clear();
                return false;
            }
        });
     }

     private void setRepsView() {
         EditText repsView = (EditText) popupLayout.findViewById(R.id.exercisePopup_repsTextView);
         repsView.setBackgroundColor(Color.WHITE);
         repsView.setVisibility(View.VISIBLE);
         repsView.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 if (repsView.getText().toString().equalsIgnoreCase("Reps:"))
                     repsView.getText().clear();
                 return false;
             }
         });
     }

    private void popupButtonClicked(Exercise exercise, String subWorkoutName) {
        Button btn = (Button) popupLayout.findViewById(R.id.exercisePopupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExerciseToSubWorkout(exercise,
                        subWorkoutName);
                Toast.makeText(context,"Exercise Successfully Added!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addExerciseToSubWorkout(Exercise exercise,String subWorkoutName) {
        exercise.setExerciseReps(getExerciseReps());
        exercise.setExerciseSets(getExerciseSets());

        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(context);
        subWorkoutTable.
                addExerciseToSubWorkout(clickedMainWorkout,subWorkoutName+"_wk",
                        exercise);
    }


    private String getExerciseSets() {
        EditText setsView = (EditText) popupLayout.findViewById(R.id.exercisePopup_setsTextView);
        return setsView.getText().toString();
    }

    private String getExerciseReps() {
        EditText repsView = (EditText) popupLayout.findViewById(R.id.exercisePopup_repsTextView);
        return repsView.getText().toString();
    }

    private ArrayAdapter getMainWorkoutAdapter() {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        List<String> list = mainWorkoutTable.getMainWorkoutNames();
        ArrayAdapter adapter = new ArrayAdapter<>(context,
                R.layout.simple_list_item,list);
        return adapter;
    }

    private ArrayAdapter getSubWorkoutAdapter() {
        MainWorkoutTable workoutTable = new MainWorkoutTable(context);
        List<String> list = workoutTable.getSubWorkoutNames(clickedMainWorkout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.simple_list_item,list);
        return adapter;
    }
}
