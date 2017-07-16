package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Workout;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FullWorkoutFragment extends Fragment {//change name
    static int c = 1;

    private List<WorkoutExercise> workoutData;

    public FullWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_full_workout, container, false);
        ListView listView = (ListView) root.findViewById(R.id.fullWorkoutListView);
        listView.setAdapter(setAdapter());
        return root;
    }

    private FullWorkoutAdapter setAdapter() {

        return new FullWorkoutAdapter(workoutData);
    }

    public void setWorkoutData(List<WorkoutExercise> workoutData) {
        this.workoutData = workoutData;
    }

    private class FullWorkoutAdapter extends BaseAdapter {

        private List<WorkoutExercise> workoutData;
        private int[] setsTextViewIds = {
                R.id.setsTextView1,R.id.setsTextView2,R.id.setsTextView3,
                R.id.setsTextView4,R.id.setsTextView5,R.id.setsTextView6,
                R.id.setsTextView7,R.id.setsTextView8,R.id.setsTextView9,
                R.id.setsTextView10
        };

        public FullWorkoutAdapter(List<WorkoutExercise> workoutData) {
            this.workoutData = workoutData;
        }

        public int getCount() {
            return workoutData.size();
        }

        public Exercise getItem(int i) {
            return workoutData.get(i);
        }

        public long getItemId(int i) {
            return i;
        }

        public View getView(int position,View rowView,ViewGroup viewGroup) {
            //error here with multiple exercises of the same type


           /* Exercise exercise = null;//the sets is being gettin wrong from lkifting stats table
            if(position < exerciseList.size()) {
                exercise = exerciseList.get(position);//need to get all ex near with same name
            }*/



            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.fullworkout_list_item,null);
            }
            WorkoutExercise workout = workoutData.get(position);
            setListItemView(rowView,workout);
            /*List<Exercise> exercises = new ArrayList<>();
            if(exercise != null) {
                int index = getExerciseIndex(exerciseList, exercise);
                int base = getBaseIndex(exercise);
                for (int x = index - base; x < index; x++) {//error here beacuse of the sets
                    exercises.add(exerciseList.get(x));
                }
                setListItemView(rowView, exercises);
                exerciseList.removeAll(exercises);
            }*/




            return rowView;
        }

        private int getExerciseIndex(List<Exercise> exerciseList, Exercise exercise) {//ex list not needed
            int index = exerciseList.indexOf(exercise);
            int sets = exercise.getActualSets();
            return index+sets;
        }

        private int getBaseIndex(Exercise exercise) {
            return exercise.getActualSets();
        }

        private void setListItemView(View rowView,WorkoutExercise workout) {
            Map<String,String> data = workout.getWorkoutData();

            setExerciseNameView(rowView,workout.getExerciseName());
            for(int x=1;x<=data.size();x++) {
                set_SetsTextView(rowView,setsTextViewIds[x-1],x,workout);
            }

           // setExerciseNameView(rowView,exercises.get(0));
            /*int sets = exercises.get(0).getActualSets();
            if(sets != 1) {
                for (int x = 0; x < sets; x++) {
                    set_SetsTextView(rowView, setsTextViewIds[x], x, exercises.get(x));
                }
            } else {
                set_SetsTextView(rowView,setsTextViewIds[0],0,exercises.get(0));
            }*/
        }

        private void setExerciseNameView(View rowView,String exerciseName) {
            TextView textView = (TextView) rowView.findViewById(R.id.exerciseNametextView5);
            textView.setText("Exercise: "+exerciseName);
        }

        private void set_SetsTextView(View rowView, int id, int set, WorkoutExercise workout) {
            TextView textView = (TextView) rowView.findViewById(id);
            textView.setVisibility(View.VISIBLE);
            int reps = workout.getReps("Set "+set);
            String weight = workout.getWeight("Set "+set)[WorkoutExercise.WEIGHT];
            String uOfmeas = workout.getWeight("Set "+set)[WorkoutExercise.UNIT_OF_MEAS];
            StringBuilder builder = new StringBuilder();
            String setData = builder.append("Set ").append(set).append(": ")
                    .append(reps).append("/").append(weight).append(uOfmeas).toString();
            textView.setText(setData);
        }
    }

}
