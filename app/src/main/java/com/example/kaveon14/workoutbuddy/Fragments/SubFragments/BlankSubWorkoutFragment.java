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
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.BlankSWPopupMenu;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;
//add menu to delete or view exercise
public class BlankSubWorkoutFragment extends Fragment {

    private WorkoutAdapter workoutAdapter;
    private List<Exercise> exerciseList;

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

    public static class WorkoutAdapter extends BaseAdapter  {

        private List<Exercise> exerciseList;
        private Context context;
        private TextView exerciseNameView;

        public WorkoutAdapter(Context context,List<Exercise> exercises) {
            this.context = context;
            this.exerciseList = exercises;
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
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.blank_workout_list_item, null);
            }
            setListItemView(rowView,exercise);
            return rowView;
        }

        private void setListItemView(View rowView,Exercise exercise) {
            setExerciseNameTextView(rowView,exercise);
            setExerciseRepsTextView(rowView,exercise);
            setExerciseSets(rowView,exercise);
        }

        private void setExerciseNameTextView(View rowView,Exercise exercise) {
            exerciseNameView = (TextView) rowView.findViewById(R.id.nameView);
            exerciseNameView.setText(exercise.getExerciseName());
        }

        private void setExerciseRepsTextView(View rowView,Exercise exercise) {
            TextView repsView = (TextView) rowView.findViewById(R.id.repsView);
            repsView.setText(exercise.getExerciseReps());
        }

        private void setExerciseSets(View rowView,Exercise exercise) {
            TextView setsView = (TextView) rowView.findViewById(R.id.setsView);
            setsView.setText(exercise.getExerciseSets());
        }

    }
}
