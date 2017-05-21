package com.example.kaveon14.workoutbuddy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutTable;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class WorkoutFragment extends Fragment {
    // TODO create on click stuff
    private Context context;
    public static String workoutName;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        ListView listView = (ListView) view.findViewById(R.id.wokoutListView);
        listView.setAdapter(getAdapter());
        WorkoutFragment workoutFragment = this;
        openWorkoutOnClick(listView,workoutFragment);
        return view;
    }

    private void openWorkoutOnClick(ListView listView,WorkoutFragment workoutFragment) {
        BlankWorkoutFragment blankWorkoutFragment = new BlankWorkoutFragment();
        blankWorkoutFragment.setContext(context);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.fragId = R.id.blankWorkout_fragment;
                workoutName = parent.getItemAtPosition(position).toString();
                getFragmentManager().beginTransaction()
                        .hide(workoutFragment)
                        .add(R.id.blankWorkout_fragment,blankWorkoutFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private ArrayAdapter getAdapter() {
        WorkoutTable workoutTable = new WorkoutTable(context);
        List<String> list = workoutTable.getWorkoutNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.simple_list_item,list);
        return adapter;
    }

}
