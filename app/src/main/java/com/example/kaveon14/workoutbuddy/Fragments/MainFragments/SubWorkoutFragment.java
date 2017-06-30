package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;
// TODO put cap to max sets to 10 and to max exercises at 15
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.SubWorkoutMenuPopup;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankSubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.LinkedList;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_SETS;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment.clickedMainWorkoutName;

public class SubWorkoutFragment extends Fragment {

    public static SubWorkout clickedSubWorkout;
    private ArrayAdapter subWorkoutAdapter;
    private List<String> subWorkoutNames;

    public SubWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subworkout, container, false);
        ListView listView = (ListView) view.findViewById(R.id.subWokoutListView);
        if(getAdapter() != null) {
            listView.setAdapter(getAdapter());
        }
        openWorkoutOnClick(listView);
        addExerciseToSubWorkout(listView);
        setFloatingActionButton();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            setFloatingActionButton();
        }
    }

    private FloatingActionButton setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setImageResource(R.drawable.ic_menu_manage);
            handleFloatingActionButtonEvents(fab);
        }
        return fab;
    }

    private void handleFloatingActionButtonEvents(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSubWorkoutPopupMenu();
            }
        });
    }

    private void showSubWorkoutPopupMenu() {
        SubWorkoutMenuPopup popup = new SubWorkoutMenuPopup(getView());
        popup.setSubWorkoutAdapter(subWorkoutAdapter);
        popup.setSubWorkoutNames(subWorkoutNames);
        popup.showPopupWindow();
    }

    private ArrayAdapter getAdapter() {
        MainWorkoutTable workoutTable = new MainWorkoutTable(getContext());
        subWorkoutNames = workoutTable.getSubWorkoutNames(clickedMainWorkoutName);
        if(subWorkoutNames.size()!=0) {
            if (subWorkoutNames.get(0) == null) {
                return null;
            }
        }
        subWorkoutAdapter = new ArrayAdapter<>(getContext(),
                R.layout.simple_list_item,subWorkoutNames);
        return subWorkoutAdapter;
    }

    private void openWorkoutOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String subWorkoutName = parent.getItemAtPosition(position).toString();
                clickedSubWorkout = getSubWorkout(subWorkoutName);
                showBlankWorkoutFragment();
            }
        });
    }

    private void addExerciseToSubWorkout(ListView listView) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String subWorkoutName = parent.getItemAtPosition(position).toString();
                clickedSubWorkout = getSubWorkout(subWorkoutName);
                ExerciseFragment exerciseFragment = showExercisefragment();
                exerciseFragment.addExerciseFromSubWorkout(true);
                return true;
            }
        });
    }

    private SubWorkout getSubWorkout(String subWorkoutName) {
        SubWorkout subWorkout = new SubWorkout(subWorkoutName,
                getExercisesForClickedSubWorkout(subWorkoutName));
        subWorkout.setMainWorkoutName(clickedMainWorkoutName);
        return subWorkout;
    }

    private List<Exercise> getExercisesForClickedSubWorkout(String subWorkoutName) {
        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(getContext());
        String tableName = subWorkoutName +"_wk";
        List<Exercise> exerciseList = new LinkedList<>();
        List<String> exerciseNames = subWorkoutTable.getColumn(tableName,COLUMN_EXERCISE_NAMES);
        List<String> exerciseSets = subWorkoutTable.getColumn(tableName,COLUMN_EXERCISE_SETS);
        List<String> exerciseReps = subWorkoutTable.getColumn(tableName,COLUMN_EXERCISE_REPS);

        for(int x=0;x<exerciseNames.size();x++) {
            Exercise exercise = new Exercise(exerciseNames.get(x),null);
            exercise.setExerciseSets(exerciseSets.get(x));
            exercise.setExerciseReps(exerciseReps.get(x));
            exerciseList.add(exercise);
        }
        return exerciseList;
    }

    private void showBlankWorkoutFragment() {
        BlankSubWorkoutFragment blankSubWorkoutFragment = new BlankSubWorkoutFragment();
        SubWorkoutFragment subWorkoutFragment = this;
        getFragmentManager().beginTransaction()
                .hide(subWorkoutFragment)
                .add(R.id.blankWorkout_fragment, blankSubWorkoutFragment)
                .addToBackStack(null)
                .commit();
    }

    private ExerciseFragment showExercisefragment() {
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.exercise_fragment,exerciseFragment)
                .addToBackStack(null)
                .commit();
        return exerciseFragment;
    }

}//if sets entered over cap warn user that the sets have been changed to ten,the maximum
