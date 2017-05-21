package com.example.kaveon14.workoutbuddy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.kaveon14.workoutbuddy.Data.Workout;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutTable;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.WorkoutData.COLUMN_EXERCISE_SETS;

public class BlankWorkoutFragment extends Fragment {


    private Context context;

    public BlankWorkoutFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_blank_workout, container, false);
        setTextView(rootView);
        setListView(rootView);
        return rootView;
    }

    private void setTextView(View rootView) {
        TextView textView = (TextView) rootView.findViewById(R.id.workoutNameView);
        textView.setText(WorkoutFragment.workoutName);
    }

    private void setListView(View rootView) {
        ListView listView = (ListView) rootView.findViewById(R.id.blankWorkout_listView);
        listView.setAdapter(setWorkoutAdapter());
    }

    private WorkoutAdapter setWorkoutAdapter() {
        WorkoutTable workoutTable = new WorkoutTable(context);
        int amountOfWorkouts = workoutTable.getWorkoutNames().size();
        List<Workout> workouts = new ArrayList<>();
        for(int x=0;x<amountOfWorkouts;x++) {
            workouts.add(getWorkout(x));
        }
        return new  WorkoutAdapter(context,workouts);
    }

    private Workout getWorkout(int x) {
        WorkoutTable workoutTable = new WorkoutTable(context);
        String tableName = WorkoutFragment.workoutName+"_wk";
        List<String> exerciseNameList = workoutTable.getColumn(tableName,COLUMN_EXERCISE_NAMES);

        Map<String,String> setsMap = new Hashtable<>();
        setsMap.put(exerciseNameList.get(x),workoutTable.getColumn(tableName,COLUMN_EXERCISE_SETS).get(x));

        Map<String,String> repsMap = new Hashtable<>();
        repsMap.put(exerciseNameList.get(x),workoutTable.getColumn(tableName,COLUMN_EXERCISE_REPS).get(x));

        return new Workout(tableName.substring(0,tableName.length()-3), exerciseNameList,setsMap,repsMap);
    }

    private static class WorkoutAdapter extends BaseAdapter implements View.OnClickListener {

        private List<Workout> workoutList;
        private Context context;
        private TextView exerciseNameView;

        public WorkoutAdapter(Context context,List<Workout> workouts) {
            this.context = context;
            this.workoutList = workouts;
        }

        public int getCount() {
            return workoutList.size();
        }

        public Object getItem(int position) {
            return workoutList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View rowView, ViewGroup viewGroup) {
            Workout workout = workoutList.get(position);
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.blank_workout_list_item, null);
            }
            setListItemView(rowView,workout,position);
            return rowView;
        }

        @Override
        public void onClick(View view) {
            //TODO open up the clicked exercise description
            System.out.println("yes this works in blank workout fragment adapter");
        }

        private void setListItemView(View rowView,Workout workout,int position) {
            setExerciseNameTextView(rowView,workout,position);
            setExerciseRepsTextView(rowView,workout);
            setExerciseSets(rowView,workout);
        }

        private void setExerciseNameTextView(View rowView,Workout workout,int position) {
            exerciseNameView = (TextView) rowView.findViewById(R.id.nameView);
            List<String> exerciseList =   workout.getExerciseList();
            exerciseNameView.setText(exerciseList.get(position));
        }

        private void setExerciseRepsTextView(View rowView,Workout workout) {
            TextView repsView = (TextView) rowView.findViewById(R.id.repsView);
            String exerciseReps = workout.getExerciseReps().get(exerciseNameView.getText());
            repsView.setText(exerciseReps);
        }

        private void setExerciseSets(View rowView,Workout workout) {
            TextView setsView = (TextView) rowView.findViewById(R.id.setsView);
            String exerciseSets = workout.getExerciseSets().get(exerciseNameView.getText());
            setsView.setText(exerciseSets);
        }

    }
}
