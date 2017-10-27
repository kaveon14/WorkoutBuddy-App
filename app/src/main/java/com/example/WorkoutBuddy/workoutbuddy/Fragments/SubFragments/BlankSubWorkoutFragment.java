package com.example.WorkoutBuddy.workoutbuddy.Fragments.SubFragments;
//add checkbox to list view if checked workoutData stored un-check workoutData not stores and load workoutData on click in workout fragment
// in popup menu have start a workout border_accent that brings up the checkboxes and change floating add extra border_accent to submit workoutData
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.WorkoutBuddy.workoutbuddy.Activities.MainActivity;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.Exercise;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.BlankSWPopupMenu;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.FragmentStackManager;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.WorkoutBuddy.workoutbuddy.R;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.ExerciseApi;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.WorkoutApi;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers.WorkoutRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//add menu to delete or view exercise
public class BlankSubWorkoutFragment extends Fragment {

    private WorkoutAdapter workoutAdapter;
    private List<Exercise> exerciseList;
    private SubWorkout clickedSubWorkout;
    private MainActivity mainActivity;
    private FragmentStackManager fragmentStackManager;

    public BlankSubWorkoutFragment() {
        // Required empty public constructor
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setClickedSubWorkout(SubWorkout clickedSubWorkout) {
        this.clickedSubWorkout = clickedSubWorkout;
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
        View root = inflater.inflate(R.layout.fragment_blank_workout, container, false);
        new RemoteAsyncTask(root).execute();
        setTextView(root);
        //setListView(root);
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
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });
        return fab;
    }

    private void showPopupMenu() {
        BlankSWPopupMenu popup = new BlankSWPopupMenu(getView(),getContext());
        popup.setExerciseList(exerciseList);
        popup.setAdapter(workoutAdapter);
        popup.setMainActivity(mainActivity);
        popup.showPopupWindow();
    }

    private void setTextView(View rootView) {
        TextView textView = (TextView) rootView.findViewById(R.id.workoutNameView);
        textView.setText(SubWorkoutFragment.getClickedSubWorkout().getSubWorkoutName());
    }

    private void setListView(View root) {
        ListView listView = (ListView) root.findViewById(R.id.blankWorkout_listView);
        if(workoutAdapter != null) {
            listView.setAdapter(workoutAdapter);
        } else {
           // listView.setAdapter(setWorkoutAdapter());
        }
        openWorkoutOnClick(listView);
        if(workoutAdapter.isEmpty()) {
            listView.setEmptyView(root.findViewById(R.id.blankSubWorkoutEmptyListItem));
        }
    }

    private void openWorkoutOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise clickedExercise = exerciseList.get(position);
                openWorkoutFragment(clickedExercise);
                ExerciseFragment.setClickedExercise(clickedExercise);
            }
        });
    }

    private void openWorkoutFragment(Exercise exercise) {
        WorkoutFragment workoutFragment =  new WorkoutFragment();
        workoutFragment.setExercise(exercise);
        fragmentStackManager.addFragmentToStack(workoutFragment,R.id.workout_fragment);
    }

    private WorkoutAdapter setWorkoutAdapter() {
        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(getContext());
        String tableName = subWorkoutTable.getCorrectTableName(clickedSubWorkout.getMainWorkoutName()
                ,clickedSubWorkout.getSubWorkoutName());
        exerciseList = subWorkoutTable.getSubWorkoutExercises(tableName);
        workoutAdapter = new WorkoutAdapter(getContext(),exerciseList);
        return workoutAdapter;
    }

    public static class WorkoutAdapter extends BaseAdapter  {

        private List<Exercise> exerciseList;
        private Context context;
        private TextView exerciseNameView;
        private List<View> rowViews;

        public WorkoutAdapter(Context context,List<Exercise> exercises) {
            this.context = context;
            this.exerciseList = exercises;
        }

        public int getCount() {
            return exerciseList.size();
        }

        public Exercise getItem(int position) {
            return exerciseList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View rowView, ViewGroup viewGroup) {
            Exercise exercise = exerciseList.get(position);
            if (rowView == null) {
                if(rowViews == null) {
                    rowViews = new ArrayList<>();
                }
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.blank_workout_list_item, null);
                rowViews.add(position,rowView);
            }
            setListItemView(rowView,exercise);
            return rowView;
        }

        private void setListItemView(View rowView,Exercise exercise) {
            setExerciseNameTextView(rowView,exercise);
            setExerciseRepsTextView(rowView,exercise);
            setExerciseSets(rowView,exercise);
        }

        private String setExerciseNameTextView(View rowView,Exercise exercise) {
            String text = exercise.getExerciseName();
            exerciseNameView = (TextView) rowView.findViewById(R.id.nameView);
            exerciseNameView.setText("Exercise:"+" "+text);
            return text;
        }

        private String setExerciseRepsTextView(View rowView,Exercise exercise) {
            String text = exercise.getGoalReps();
            TextView repsView = (TextView) rowView.findViewById(R.id.repsView);
            repsView.setText("Reps:"+" "+text);
            return text;
        }

        private String setExerciseSets(View rowView,Exercise exercise) {
            String text = exercise.getGoalSets();
            TextView setsView = (TextView) rowView.findViewById(R.id.setsView);
            setsView.setText("Sets:"+" "+text);
            return text;
        }
    }

    private class RemoteAsyncTask extends AsyncTask<Void, Void, String> {

        View root;

        public RemoteAsyncTask(View root) {
            this.root = root;
        }

        @Override
        protected String doInBackground(Void... params) {
            WorkoutRequestHandler requestHandler = new WorkoutRequestHandler();
            return requestHandler.sendGetSubWorkoutExercisesRequest(
                    SubWorkoutFragment.getClickedSubWorkout().getRowID());
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(!jsonObject.getBoolean(CoreAPI.JSON_ERROR)) {
                    workoutAdapter = new WorkoutAdapter(getContext(),getData(jsonObject));
                    setListView(root);
                } else {
                    //do toast
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private List<Exercise> getData(JSONObject jsonObject) throws JSONException {
            exerciseList = new ArrayList<>();
            JSONArray array = jsonObject.getJSONArray(CoreAPI.JSON_KEY);
            for(int x=0;x<array.length();x++) {
                String ex_name = ((JSONObject) array.get(x)).getString(ExerciseApi.JSON_EXERCISE_NAME);
                String reps = ((JSONObject) array.get(x)).getString(WorkoutApi.JSON_GOAL_REPS);
                String sets = ((JSONObject) array.get(x)).getString(WorkoutApi.JSON_GOAL_SETS);
                Exercise exercise = new Exercise(ex_name,null);
                exercise.setGoalReps(reps);
                exercise.setGoalSets(sets);
                exerciseList.add(exercise);
            }
            return exerciseList;
        }
    }
}
