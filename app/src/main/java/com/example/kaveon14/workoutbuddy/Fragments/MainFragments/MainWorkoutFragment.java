package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;
// TODO allow deletion of mainworkouts like bodystats
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.MainWorkoutPopupMenu;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class MainWorkoutFragment extends Fragment {

    public static String clickedMainWorkoutName;
    private List<String> mainWorkoutNames;
    private ArrayAdapter adapter;

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
        setFloatingActionButton(root);
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            setFloatingActionButton(getView());
        }
    }

    private FloatingActionButton setFloatingActionButton(View root) {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setImageResource(R.drawable.ic_menu_slideshow);
            handleFloatingActionButtonEvents(fab,root);
        }
        return fab;
    }

    private void handleFloatingActionButtonEvents(FloatingActionButton fab,View root) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMainWorkoutPopupMenu();
            }
        });
    }

    private void showMainWorkoutPopupMenu() {
        MainWorkoutPopupMenu popup = new MainWorkoutPopupMenu(getView());
        popup.setMainWorkoutAdapter(adapter);
        popup.setMainWorkoutNames(mainWorkoutNames);
        popup.showPopupWindow();
    }

    private ListView setListView(View root) {
        ListView listView = (ListView) root.findViewById(R.id.mainWorkout_listView);
        listView.setAdapter(getAdapter());
        openWorkoutOnClick(listView);
        return listView;
    }

    private ArrayAdapter getAdapter() {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(getContext());
        mainWorkoutNames = mainWorkoutTable.getMainWorkoutNames();
        adapter = new ArrayAdapter<>(getContext(),
                R.layout.simple_list_item,mainWorkoutNames);
        return adapter;
    }

    private void openWorkoutOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedMainWorkoutName = parent.getItemAtPosition(position).toString();
                showSubWorkoutfragment();
            }
        });
    }

    private void showSubWorkoutfragment() {
        SubWorkoutFragment subWorkoutFragment = new SubWorkoutFragment();
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.subWorkout_fragment,subWorkoutFragment)
                .addToBackStack(null)
                .commit();
    }
}
