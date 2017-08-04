package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.WorkoutStatsTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows.ExerciseToWorkoutPopup;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows.ExercisePopupMenu;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static android.content.Context.SEARCH_SERVICE;

public class ExerciseFragment extends Fragment {

    private static ArrayAdapter exerciseAdapter;
    public static Exercise clickedExercise;
    public static List<Exercise> exerciseList;
    public static List<Exercise> customExerciseList;
    private static List<String> exerciseNames;
    private boolean fromSubWorkout = false;
    private ListView listView;
    private Menu menu;
    private MainActivity mainActivity;

    public ExerciseFragment() {

    }

    public void addExerciseFromSubWorkout(boolean fromSubWorkout) {
        this.fromSubWorkout = fromSubWorkout;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exercise, container, false);
        setExerciseAdapter();
        setListView(root,exerciseAdapter);
        setFloatingActionButton();
        setSearchViewOnClick();
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
                showExercisePopupMenu();
            }
        });
    }

    private void showExercisePopupMenu() {
        ExercisePopupMenu popup = new ExercisePopupMenu(getView(),getContext());
        popup.setMainActivity(mainActivity);
        popup.setCustomExerciseList(customExerciseList);
        popup.setFromSubWorkout(fromSubWorkout);
        popup.showPopupWindow();
    }

    private void setListView(View root,ArrayAdapter adapter) {
        listView = (ListView) root.findViewById(R.id.exercise_listView);
        listView.setAdapter(adapter);
        setListViewOnClick(listView,root);
        listViewOnLongClick(listView,root);
    }

    private void setListViewOnClick(ListView listView, View root) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedExercise = exerciseList.get(position);
                showBlankExerciseFragment();
            }
        });
    }

    private void listViewOnLongClick(ListView listView,View root) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clickedExercise = exerciseList.get(position);
                ExerciseToWorkoutPopup popup = new ExerciseToWorkoutPopup(root,getContext()
                        ,fromSubWorkout);
                popup.showPopupWindow();
                return true;
            }
        });
    }

    public static void addExerciseToList(Exercise exercise) {
        exerciseList.add(exercise);
        customExerciseList.add(exercise);
        exerciseNames.add(exercise.getExerciseName());
        sortExerciseListByName();
        sortCustomExerciseListByName();
        sortExerciseNames();
        exerciseAdapter.notifyDataSetChanged();
    }

    public static void deleteExerciseFromList(Exercise exercise) {
        exerciseList.remove(exercise);
        exerciseNames.remove(exercise.getExerciseName());
        customExerciseList.remove(exercise);
        exerciseAdapter.notifyDataSetChanged();
    }

    public static List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public static List<Exercise> getCustomExerciseList() {
        return customExerciseList;
    }

    private void setExerciseAdapter() {
        setAllExerciseLists();
        exerciseAdapter = new ArrayAdapter(getContext(),R.layout.simple_list_item,exerciseNames);
    }

    public void setAllExerciseLists() {//not possible
        ExerciseTable exerciseTable = new ExerciseTable(getContext());
        if (exerciseList == null) {
            exerciseList = exerciseTable.getExercises();
            sortExerciseListByName();
        }
        if (customExerciseList == null) {
            customExerciseList = exerciseTable.getCustomExercises();
            sortCustomExerciseListByName();
        }
        if (exerciseNames == null) {
            exerciseNames = new ArrayList<>();
            for (int x = 0; x < exerciseList.size(); x++) {
                exerciseNames.add(exerciseList.get(x).getExerciseName());
            }
        }
    }

    private void showBlankExerciseFragment() {
        BlankExerciseFragment bf = new BlankExerciseFragment();
        getFragmentManager().beginTransaction()
                .hide(this)
                .replace(R.id.blankExercise_fragment,bf)
                .addToBackStack(null)
                .commit();
    }

    private static void sortExerciseNames() {
        Collections.sort(exerciseNames, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return  o1.compareToIgnoreCase(o2);
            }
        });
    }

    private static void sortExerciseListByName() {
        Collections.sort(exerciseList, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise exercise1, Exercise exercise2) {
                return exercise1.getExerciseName().compareToIgnoreCase(exercise2.getExerciseName());
            }
        });
    }

    private static void sortCustomExerciseListByName() {
        Collections.sort(customExerciseList, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise exercise1, Exercise exercise2) {
                return exercise1.getExerciseName().compareToIgnoreCase(exercise2.getExerciseName());
            }
        });
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    private void setSearchViewOnClick() {
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ExerciseTable table = new ExerciseTable(getContext());
                loadSearchedItems(table.searchTable(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private List<String> loadSearchedItems(Map<String,List<String>> queriedData) {
        List<String> list = new ArrayList<>();
        if (queriedData != null) {
            List<String> exerciseNames = queriedData.
                    get(DataBaseContract.ExerciseData.COLUMN_EXERCISES);
            for(int x=0;x<exerciseNames.size();x++) {
                String exerciseName = exerciseNames.get(x);
                for(int i=0;i<exerciseList.size();i++) {
                    String exerciseListName = exerciseList.get(i).getExerciseName();
                    if(exerciseName.equals(exerciseListName)) {
                        list.add(exerciseListName);
                    }
                }
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(getContext(),R.layout.simple_list_item,list);
        listView.setAdapter(adapter);
        return list;
    }
}
//no need to change the custom image name because it is tied and retrieved by an exercise object