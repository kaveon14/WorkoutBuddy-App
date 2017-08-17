package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows;

import android.content.Context;
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
import com.example.kaveon14.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment.getClickedExercise;
//TODO fix this class
public class ExerciseToWorkoutPopup extends PopupWindowManager {

    private boolean fromSubWorkout;

    public ExerciseToWorkoutPopup(View root, Context context
            ,boolean fromSubWorkout) {
        this.fromSubWorkout = fromSubWorkout;
        setRootView(root);
        setWindowManagerContext(context);
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
                listView.setAdapter(getSubWorkoutAdapter(
                        parent.getItemAtPosition(position).toString()));
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
        popupButtonClicked(getClickedExercise(), subWorkoutName);
        setUpTextViews();
        showPopupButton();
    }

    private void showPartialSubWorkoutPopup() {
        setUpTextViews();
        showPopupButton();
        popupButtonClicked(getClickedExercise(),
                SubWorkoutFragment.getClickedSubWorkout().getSubWorkoutName());
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
        String sets = getExerciseSets();
        int[] exerciseSets;
        if(sets.contains("-")) {
            int index = sets.indexOf("-");

            exerciseSets = new int[2];
            exerciseSets[0] = Integer.valueOf(sets.substring(0,index));
            exerciseSets[1] = Integer.valueOf(sets.substring(index+1,sets.length()));
            if(exerciseSets[0] > 10 || exerciseSets[1] >10) {
                Toast.makeText(context,"Maximum of 10 sets allowed for an exercise!"
                        ,Toast.LENGTH_LONG).show();
                return;
            }
        }
        exercise.setGoalReps(getExerciseReps());
        exercise.setGoalSets(sets);
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

    private ArrayAdapter getSubWorkoutAdapter(String mainWorkoutName) {
        MainWorkoutTable workoutTable = new MainWorkoutTable(context);
        List<String> list = workoutTable.getSubWorkoutNames(mainWorkoutName);
        return  new ArrayAdapter<>(context,
                R.layout.simple_list_item,list);
    }
}
