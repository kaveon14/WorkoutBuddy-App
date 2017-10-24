package com.example.WorkoutBuddy.workoutbuddy.Fragments.MainFragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.os.AsyncTask;
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
import android.widget.Toast;
import com.example.WorkoutBuddy.workoutbuddy.Activities.MainActivity;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.Exercise;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows.ExerciseToWorkoutPopup;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows.ExercisePopupMenu;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.FragmentStackManager;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.SubFragments.BlankExerciseFragment;
import com.example.WorkoutBuddy.workoutbuddy.R;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers.ExerciseRequestHandler;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import static android.content.Context.SEARCH_SERVICE;
import static com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ExerciseApi.JSON_DEFAULT_EXERCISE;
import static com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ExerciseApi.JSON_EXERCISE_DESCRIPTION;
import static com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ExerciseApi.JSON_EXERCISE_NAME;
import static com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI.JSON_KEY;

public class ExerciseFragment extends Fragment {//not do transitions and shit

    private Menu menu;
    private View root;
    private ListView listView;
    private MainActivity mainActivity;
    private List<String> exerciseNames;
    private List<Exercise> exerciseList;
    private boolean fromSubWorkout = false;
    private ArrayAdapter<String> exerciseAdapter;
    private static Exercise clickedExercise;
    private List<Exercise> customExerciseList;
    private FragmentStackManager fragmentStackManager;

    public ExerciseFragment() {

    }

    public void addExerciseFromSubWorkout(boolean fromSubWorkout) {
        this.fromSubWorkout = fromSubWorkout;
    }

    public static void setClickedExercise(Exercise clickedExercise) {
        ExerciseFragment.clickedExercise = clickedExercise;
    }

    public void setFragmentStackManager(FragmentStackManager fragmentStackManager) {
        this.fragmentStackManager = fragmentStackManager;
    }

    public static Exercise getClickedExercise() {
        return clickedExercise;
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
        root = inflater.inflate(R.layout.fragment_exercise, container, false);
        if(CoreAPI.getUserId() == 0) {
            new LocalAsyncTask().execute(new ArrayList<String>()); //may not be updated
        } else {
            new RemoteAsyncTask().execute();
        }
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
            fab.setVisibility(View.VISIBLE);
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
        popup.setExerciseFragment(this);
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
                setClickedExercise(exerciseList.get(position));
                showBlankExerciseFragment();
            }
        });
    }

    private void listViewOnLongClick(ListView listView,View root) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setClickedExercise(exerciseList.get(position));
                ExerciseToWorkoutPopup popup = new ExerciseToWorkoutPopup(root,getContext()
                        ,fromSubWorkout);
                popup.showPopupWindow();
                return true;
            }
        });
    }

    public void addExerciseToList(Exercise exercise) {
        exerciseList.add(exercise);
        customExerciseList.add(exercise);
        exerciseNames.add(exercise.getExerciseName());
        sortExerciseListByName();
        sortCustomExerciseListByName();
        sortExerciseNames();
        exerciseAdapter.notifyDataSetChanged();
    }

    public void deleteExerciseFromList(Exercise exercise) {
        exerciseList.remove(exercise);
        exerciseNames.remove(exercise.getExerciseName());
        customExerciseList.remove(exercise);
        exerciseAdapter.notifyDataSetChanged();
    }

    private void showBlankExerciseFragment() {
        BlankExerciseFragment bf = new BlankExerciseFragment();
        fragmentStackManager.addFragmentToStack(bf,R.id.blankExercise_fragment);
    }

    private void sortExerciseNames() {
        Collections.sort(exerciseNames, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return  o1.compareToIgnoreCase(o2);
            }
        });
    }

    private void sortExerciseListByName() {
        Collections.sort(exerciseList, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise exercise1, Exercise exercise2) {
                return exercise1.getExerciseName().compareToIgnoreCase(exercise2.getExerciseName());
            }
        });
    }

    private void sortCustomExerciseListByName() {
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

    private class LocalAsyncTask extends AsyncTask<List<String>,Void,List<String>> {

        private ExerciseTable table;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            table = new ExerciseTable(getContext());
        }

        @Override
        protected List<String> doInBackground(List<String>[] params) {
            exerciseList = table.getExercises();
            customExerciseList = table.getCustomExercises();
            for(int x=0;x<exerciseList.size();x++) {
                params[0].add(exerciseList.get(x).getExerciseName());
            }
            exerciseNames = params[0];
            return params[0];
        }

        @Override
        protected void onPostExecute(List<String> exerciseNames) {
            progressDialog.dismiss();
            exerciseAdapter =
                    new ArrayAdapter<String>(getContext(),R.layout.simple_list_item,exerciseNames);
            setListView(root,exerciseAdapter);
        }
    }

    private class RemoteAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            ExerciseRequestHandler requestHandler = new ExerciseRequestHandler();
            return requestHandler.sendGetAllExercisesRequest(CoreAPI.getUserId());
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(!jsonObject.getBoolean(CoreAPI.JSON_ERROR)) {
                    exerciseAdapter = new ArrayAdapter<String>(getContext()
                            ,R.layout.simple_list_item,getData(jsonObject));
                    setListView(root,exerciseAdapter);
                } else {
                    Toast.makeText(getContext(), jsonObject.getString(CoreAPI.JSON_ERROR_MESSAGE),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private List<String> getData(JSONObject jsonObject) throws JSONException {
            List<String> list = new ArrayList<>();
            exerciseList = new ArrayList<>();
            customExerciseList = new ArrayList<>();

            JSONArray array = jsonObject.getJSONArray(JSON_KEY);
            for(int x=0;x<array.length();x++) {//need to declare custom exercises down here
                String ex_name = ((JSONObject) array.get(x)).getString(JSON_EXERCISE_NAME);//error here got two json arrays
                String ex_descr = ((JSONObject) array.get(x)).getString(JSON_EXERCISE_DESCRIPTION);
                boolean custom = !((JSONObject) array.get(x)).getBoolean(JSON_DEFAULT_EXERCISE);
                list.add(ex_name);
                exerciseList.add(new Exercise(ex_name,ex_descr));
                if(custom) {
                    customExerciseList.add(new Exercise(ex_name,ex_descr));
                }
            }
            return list;
        }
    }
}
//no need to change the custom image name because it is tied and retrieved by an exercise object