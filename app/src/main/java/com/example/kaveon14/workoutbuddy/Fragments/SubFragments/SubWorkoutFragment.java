package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment.clickedMainWorkout;
// TODO on every on long click return true
public class SubWorkoutFragment extends Fragment {

    public static String clickedSubWorkoutName;// TODO make this an actual subworkout

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

    private void openWorkoutOnClick(ListView listView) {
        BlankWorkoutFragment blankWorkoutFragment = new BlankWorkoutFragment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedSubWorkoutName = parent.getItemAtPosition(position).toString();
                showBlankWorkoutFragment();
            }
        });
    }

    private void addExerciseToSubWorkout(ListView listView) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clickedSubWorkoutName = parent.getItemAtPosition(position).toString();
                ExerciseFragment exerciseFragment = showExercisefragment();
                exerciseFragment.addExerciseFromSubWorkout(true);
                return true;
            }
        });
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

    private ArrayAdapter getAdapter() {
        MainWorkoutTable workoutTable = new MainWorkoutTable(getContext());
        List<String> list = workoutTable.getSubWorkoutNames(clickedMainWorkout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.simple_list_item,list);
        return adapter;
    }


}
