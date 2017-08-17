package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.MainWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.SubWorkoutMenuPopup;
import com.example.kaveon14.workoutbuddy.Fragments.Managers.FragmentStackManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class SubWorkoutFragment extends Fragment {

    private Menu menu;
    private ListView listView;
    private int subWorkoutCount;
    private MainActivity mainActivity;
    private List<String> subWorkoutNames;
    private MainWorkout clickedMainWorkout;
    private ArrayAdapter subWorkoutAdapter;
    private static SubWorkout clickedSubWorkout;
    private FragmentStackManager fragmentStackManager;

    public SubWorkoutFragment() {
        // Required empty public constructor
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static SubWorkout getClickedSubWorkout() {
        return SubWorkoutFragment.clickedSubWorkout;
    }

    public static void setClickedSubWorkout(SubWorkout clickedSubWorkout) {
        SubWorkoutFragment.clickedSubWorkout = clickedSubWorkout;
    }

    public void setClickedMainWorkout(MainWorkout clickedMainWorkout) {
        this.clickedMainWorkout = clickedMainWorkout;
    }

    public void setFragmentStackManager(FragmentStackManager fragmentStackManager) {
        this.fragmentStackManager = fragmentStackManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_subworkout, container, false);
        setListView(root);
        openWorkoutOnClick(listView);
        addExerciseToSubWorkout(listView);
        setFloatingActionButton();
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            setFloatingActionButton();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resetFloatingActionButton();
    }

    private void setListView(View root) {
        listView = (ListView) root.findViewById(R.id.subWokoutListView);
        if(getAdapter() != null) {
            listView.setAdapter(getAdapter());
            subWorkoutCount = listView.getCount();
        }
        if(subWorkoutAdapter.isEmpty()) {
            listView.setEmptyView(root.findViewById(R.id.subWorkoutEmptyListItem));
        }
    }

    private void resetFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        }
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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
                if(!clickedMainWorkout.getMainWorkoutName().equals("Default Workouts")) {
                    showSubWorkoutPopupMenu();
                } else {
                    Toast.makeText(getContext(),"Can not alter the Default SubWorkouts!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSubWorkoutPopupMenu() {
        SubWorkoutMenuPopup popup = new SubWorkoutMenuPopup(getView(),getContext());
        popup.setSubWorkoutAdapter(subWorkoutAdapter);
        popup.setSubWorkoutNames(subWorkoutNames);
        popup.setClickedMainWorkoutName(clickedMainWorkout.getMainWorkoutName());
        popup.setCurrentSubWorkoutCount(subWorkoutCount);
        popup.showPopupWindow();
    }

    private ArrayAdapter getAdapter() {
        MainWorkoutTable workoutTable = new MainWorkoutTable(getContext());
        subWorkoutNames = workoutTable.getSubWorkoutNames(clickedMainWorkout.getMainWorkoutName());
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
                setClickedSubWorkout(getSubWorkout(subWorkoutName));
                getClickedSubWorkout().setMainWorkoutName(clickedMainWorkout.getMainWorkoutName());
                showBlankWorkoutFragment();
            }
        });
    }

    private void addExerciseToSubWorkout(ListView listView) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!clickedMainWorkout.getMainWorkoutName().equals("Default Workouts")) {
                    String subWorkoutName = parent.getItemAtPosition(position).toString();
                    setClickedSubWorkout(getSubWorkout(subWorkoutName));
                    ExerciseFragment exerciseFragment = showExercisefragment();
                    exerciseFragment.addExerciseFromSubWorkout(true);
                } else {
                    Toast.makeText(getContext(),"Can not alter the Default SubWorkouts!",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private SubWorkout getSubWorkout(String subWorkoutName) {
        SubWorkout subWorkout = new SubWorkout(subWorkoutName);
        subWorkout.setMainWorkoutName(clickedMainWorkout.getMainWorkoutName());
        return subWorkout;
    }

    private void showBlankWorkoutFragment() {
        BlankSubWorkoutFragment blankSubWorkoutFragment = new BlankSubWorkoutFragment();
        blankSubWorkoutFragment.setClickedSubWorkout(getClickedSubWorkout());
        blankSubWorkoutFragment.setMainActivity(mainActivity);
        blankSubWorkoutFragment.setFragmentStackManager(fragmentStackManager);
        fragmentStackManager.addFragmentToStack(blankSubWorkoutFragment,
                R.id.blankWorkout_fragment);
    }

    private ExerciseFragment showExercisefragment() {
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        exerciseFragment.setMenu(menu);
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.exercise_fragment,exerciseFragment)
                .addToBackStack(null)
                .commit();
        return exerciseFragment;
    }
}//if sets entered over cap warn user that the sets have been changed to ten,the maximum
