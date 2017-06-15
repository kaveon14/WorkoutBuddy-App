package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;

import android.os.Bundle;
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
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.LinkedList;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_SETS;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment.clickedMainWorkout;

public class SubWorkoutFragment extends Fragment {

    public static SubWorkout clickedSubWorkout;

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
        listView.setAdapter(getAdapter());
        openWorkoutOnClick(listView);
        addExerciseToSubWorkout(listView);
        return view;
    }

    private ArrayAdapter getAdapter() {
        MainWorkoutTable workoutTable = new MainWorkoutTable(getContext());
        List<String> list = workoutTable.getSubWorkoutNames(clickedMainWorkout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.simple_list_item,list);
        return adapter;
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
        return new SubWorkout(subWorkoutName,
                getExercisesForClickedSubWorkout(subWorkoutName));
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
        BlankWorkoutFragment blankWorkoutFragment = new BlankWorkoutFragment();
        SubWorkoutFragment subWorkoutFragment = this;
        getFragmentManager().beginTransaction()
                .hide(subWorkoutFragment)
                .add(R.id.blankWorkout_fragment,blankWorkoutFragment)
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

}
