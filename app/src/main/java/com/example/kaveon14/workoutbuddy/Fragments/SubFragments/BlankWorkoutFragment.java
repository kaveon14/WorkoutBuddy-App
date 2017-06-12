package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_NAMES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_REPS;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.SubWorkoutData.COLUMN_EXERCISE_SETS;

public class BlankWorkoutFragment extends Fragment {

    public BlankWorkoutFragment() {
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
        return rootView;
    }

    private void setTextView(View rootView) {
        TextView textView = (TextView) rootView.findViewById(R.id.workoutNameView);
        textView.setText(SubWorkoutFragment.clickedWorkoutName);
    }

    private void setListView(View rootView) {
        ListView listView = (ListView) rootView.findViewById(R.id.blankWorkout_listView);
        listView.setAdapter(setWorkoutAdapter());
         viewExerciseOnClick(listView);
    }

    private void viewExerciseOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise clickedExercise = getSubWorkout(position).getExerciseList().get(position);
                showExercise(clickedExercise);
            }
        });
    }

    private void showExercise(Exercise clickedExercise) {
        ExerciseFragment.clickedExercise = clickedExercise;
        BlankExerciseFragment blankExerciseFragment = new BlankExerciseFragment();
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.blankExercise_fragment,blankExerciseFragment)
                .addToBackStack(null)
                .commit();
    }

    private WorkoutAdapter setWorkoutAdapter() {
        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(getContext());
        String tableName = SubWorkoutFragment.clickedWorkoutName + "_wk";
        int amountOfExercises = subWorkoutTable.getColumn(tableName,COLUMN_EXERCISE_NAMES).size();
        List<SubWorkout> workouts = new ArrayList<>();
        for(int x=0;x<amountOfExercises;x++) {
            workouts.add(getSubWorkout(x));
        }
        return new WorkoutAdapter(getContext(),workouts);
    }

    private SubWorkout getSubWorkout(int x) {
        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(getContext());
        String tableName = SubWorkoutFragment.clickedWorkoutName +"_wk";

        List<Exercise> exerciseList = new LinkedList<>();//need to create exercise first
        List<String> f = subWorkoutTable.getColumn(tableName,COLUMN_EXERCISE_NAMES);
        for(int z=0;z<f.size();z++) {
            exerciseList.add(new Exercise(f.get(z),null));

        }

        Map<String,String> setsMap = new Hashtable<>();
        setsMap.put(exerciseList.get(x).getExerciseName(),
                subWorkoutTable.getColumn(tableName,COLUMN_EXERCISE_SETS).get(x));

        Map<String,String> repsMap = new Hashtable<>();
        repsMap.put(exerciseList.get(x).getExerciseName(),
                subWorkoutTable.getColumn(tableName,COLUMN_EXERCISE_REPS).get(x));

        return new SubWorkout(tableName.substring(0,tableName.length()-3),
                exerciseList,setsMap,repsMap);
    }

    private static class WorkoutAdapter extends BaseAdapter  {

        private List<SubWorkout> workoutList;
        private Context context;
        private TextView exerciseNameView;

        public WorkoutAdapter(Context context,List<SubWorkout> workouts) {
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
            SubWorkout workout = workoutList.get(position);
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.blank_workout_list_item, null);
            }
            setListItemView(rowView,workout,position);
            return rowView;
        }

        private void setListItemView(View rowView, SubWorkout workout, int position) {
            setExerciseNameTextView(rowView,workout,position);
            setExerciseRepsTextView(rowView,workout);
            setExerciseSets(rowView,workout);
        }

        private void setExerciseNameTextView(View rowView, SubWorkout workout, int position) {
            exerciseNameView = (TextView) rowView.findViewById(R.id.nameView);
            List<Exercise> exerciseList =   workout.getExerciseList();
            exerciseNameView.setText(exerciseList.get(position).getExerciseName());
        }

        private void setExerciseRepsTextView(View rowView,SubWorkout workout) {
            TextView repsView = (TextView) rowView.findViewById(R.id.repsView);
            String exerciseReps = workout.getExerciseReps().get(exerciseNameView.getText());
            repsView.setText(exerciseReps);
        }

        private void setExerciseSets(View rowView,SubWorkout workout) {
            TextView setsView = (TextView) rowView.findViewById(R.id.setsView);
            String exerciseSets = workout.getExerciseSets().get(exerciseNameView.getText());
            setsView.setText(exerciseSets);
        }

    }
}
