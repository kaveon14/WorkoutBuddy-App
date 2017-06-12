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
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment.clickedMainWorkout;

public class SubWorkoutFragment extends Fragment {

    public static String clickedWorkoutName;

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
        SubWorkoutFragment subWorkoutFragment = this;
        openWorkoutOnClick(listView, subWorkoutFragment);
        return view;
    }

    private void openWorkoutOnClick(ListView listView,SubWorkoutFragment subWorkoutFragment) {
        BlankWorkoutFragment blankWorkoutFragment = new BlankWorkoutFragment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedWorkoutName = parent.getItemAtPosition(position).toString();
                getFragmentManager().beginTransaction()
                        .hide(subWorkoutFragment)
                        .add(R.id.blankWorkout_fragment,blankWorkoutFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private ArrayAdapter getAdapter() {
        MainWorkoutTable workoutTable = new MainWorkoutTable(getContext());
        List<String> list = workoutTable.getSubWorkoutNames(clickedMainWorkout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.simple_list_item,list);
        return adapter;
    }
}
