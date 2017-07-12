package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;
//add checkbox to list view if checked data stored un-check data not stores and load data on click in workout fragment
// in popup menu have start a workout button that brings up the checkboxes and change floating add extra button to submit data
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Workout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.BlankSWPopupMenu;
import com.example.kaveon14.workoutbuddy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//add menu to delete or view exercise
public class BlankSubWorkoutFragment extends Fragment {

    private WorkoutAdapter workoutAdapter;
    private List<Exercise> exerciseList;
    public static Map<String,Workout> workoutMap;

    public BlankSubWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank_workout, container, false);
        setTextView(rootView);
        setListView(rootView);
        MainActivity.fragId = R.id.blankWorkout_fragment;
        setFloatingActionButton();
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            setFloatingActionButton();
        }
    }

    private FloatingActionButton setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });
        return fab;
    }

    private void showPopupMenu() {
        BlankSWPopupMenu popup = new BlankSWPopupMenu(getView());
        popup.setExerciseList(exerciseList);
        popup.setAdapter(workoutAdapter);
        popup.showPopupWindow();
    }

    private void setTextView(View rootView) {
        TextView textView = (TextView) rootView.findViewById(R.id.workoutNameView);
        textView.setText(SubWorkoutFragment.clickedSubWorkout.getSubWorkoutName());
    }

    private void setListView(View rootView) {
        ListView listView = (ListView) rootView.findViewById(R.id.blankWorkout_listView);
        if(workoutAdapter != null) {
            listView.setAdapter(workoutAdapter);
        } else {
            listView.setAdapter(setWorkoutAdapter());
        }
        openWorkoutOnClick(listView);
    }

    private void openWorkoutOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise clickedExercise = exerciseList.get(position);
                openWorkoutFragment(clickedExercise);
            }
        });
    }

    private void openWorkoutFragment(Exercise exercise) {
        WorkoutFragment workoutFragment =  new WorkoutFragment();
        workoutFragment.setExercise(exercise);
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.workout_fragment,workoutFragment)
                .addToBackStack(null)
                .commit();
    }

    private WorkoutAdapter setWorkoutAdapter() {
        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(getContext());
        String tableName = SubWorkoutFragment.clickedSubWorkout.getSubWorkoutName() + "_wk";
        exerciseList = subWorkoutTable.getSubWorkoutExercises(tableName);
        workoutAdapter = new WorkoutAdapter(getContext(),exerciseList);
        return workoutAdapter;
    }

    public static class WorkoutAdapter extends BaseAdapter  {//workout object needs tocome in to play

        private List<Exercise> exerciseList;
        private Context context;
        private TextView exerciseNameView;
        private List<View> rowViews;
        private List<Workout> workouts;

        public WorkoutAdapter(Context context,List<Exercise> exercises) {
            this.context = context;
            this.exerciseList = exercises;
            workouts = new ArrayList<>(10);
        }

        public int getCount() {
            return exerciseList.size();
        }

        public Object getItem(int position) {
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

        public void showCheckBoxes() {
            CheckBox checkBox;
            for(View rowView : rowViews) {
                checkBox = (CheckBox) rowView.findViewById(R.id.blankWorkoutCheckBox);
                checkBox.setVisibility(View.VISIBLE);
            }
        }

        public void onCheckedBox() {
            CheckBox checkBox;
            for (View rowView : rowViews) {
                checkBox = (CheckBox) rowView.findViewById(R.id.blankWorkoutCheckBox);
                if (checkBox.isChecked()) {
                    workouts.add(new Workout(WorkoutFragment.getWorkoutData()));
                }
            }
        }

        public void hideCheckBoxes() {
            CheckBox checkBox;
            for(View rowView : rowViews) {
                checkBox = (CheckBox) rowView.findViewById(R.id.blankWorkoutCheckBox);
                checkBox.setVisibility(View.INVISIBLE);
            }
        }

        private void setListItemView(View rowView,Exercise exercise) {
            setExerciseNameTextView(rowView,exercise);
            setExerciseRepsTextView(rowView,exercise);
            setExerciseSets(rowView,exercise);
        }

        private String setExerciseNameTextView(View rowView,Exercise exercise) {
            String text = exercise.getExerciseName();
            exerciseNameView = (TextView) rowView.findViewById(R.id.nameView);
            exerciseNameView.setText(text);
            return text;
        }

        private String setExerciseRepsTextView(View rowView,Exercise exercise) {
            String text = exercise.getExerciseReps();
            TextView repsView = (TextView) rowView.findViewById(R.id.repsView);
            repsView.setText(text);
            return text;
        }

        private String setExerciseSets(View rowView,Exercise exercise) {
            String text = exercise.getExerciseSets();
            TextView setsView = (TextView) rowView.findViewById(R.id.setsView);
            setsView.setText(text);
            return text;
        }
    }
}
