package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;

import java.util.List;

public class MainWorkoutFragment extends Fragment {

    public static String clickedMainWorkout;

    public MainWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_workout, container, false);
        setListView(root);
        return root;
    }

    private void setListView(View root) {
        MainWorkoutFragment mainWorkoutFragment = this;
        ListView listView = (ListView) root.findViewById(R.id.mainWorkout_listView);
        listView.setAdapter(getAdapter());
        openWorkoutOnClick(listView,mainWorkoutFragment);
    }

    private void openWorkoutOnClick(ListView listView,MainWorkoutFragment mainWorkoutFragment) {
        SubWorkoutFragment subWorkoutFragment = new SubWorkoutFragment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedMainWorkout = parent.getItemAtPosition(position).toString();
                getFragmentManager().beginTransaction()
                        .hide(mainWorkoutFragment)
                        .add(R.id.subWorkout_fragment,subWorkoutFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private ArrayAdapter getAdapter() {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(getContext());
        List<String> list = mainWorkoutTable.getMainWorkoutNames();
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                R.layout.simple_list_item,list);
        return adapter;
    }
}
