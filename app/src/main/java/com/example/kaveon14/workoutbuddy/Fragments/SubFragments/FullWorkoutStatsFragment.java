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
import com.example.kaveon14.workoutbuddy.DataBase.Data.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;
import java.util.Map;

public class FullWorkoutStatsFragment extends Fragment {

    private List<WorkoutExercise> workoutData;

    public FullWorkoutStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_full_workout_stats, container, false);
        ListView listView = (ListView) root.findViewById(R.id.fullWorkoutStatsListView);
        listView.setAdapter(setAdapter());
        return root;
    }

    private FullWorkoutStatsAdapter setAdapter() {
        return new FullWorkoutStatsAdapter(workoutData);
    }

    public void setWorkoutData(List<WorkoutExercise> workoutData) {
        this.workoutData = workoutData;
    }

    private class FullWorkoutStatsAdapter extends BaseAdapter {

        private List<WorkoutExercise> workoutData;
        private int[] setsTextViewIds = {
                R.id.set1_TextView,R.id.set2_TextView,R.id.set3_TextView,
                R.id.set4_TextView,R.id.set5_TextView,R.id.set6_TextView,
                R.id.set7_TextView,R.id.set8_TextView,R.id.set9_TextView,
                R.id.set10_TextView
        };

        public FullWorkoutStatsAdapter(List<WorkoutExercise> workoutData) {
            this.workoutData = workoutData;
        }

        public int getCount() {
            return workoutData != null ? workoutData.size() : 0;
        }

        public Exercise getItem(int i) {
            return workoutData.get(i);
        }

        public long getItemId(int i) {
            return i;
        }

        public View getView(int position,View rowView,ViewGroup viewGroup) {
            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.fullworkoutstats_list_item,null);
            }
            WorkoutExercise workout = workoutData.get(position);
            setListItemView(rowView,workout);
            return rowView;
        }

        private void setListItemView(View rowView,WorkoutExercise workout) {
            Map<String,String> data = workout.getWorkoutData();

            setExerciseNameView(rowView,workout.getExerciseName());
            for(int x=1;x<=data.size();x++) {
                set_SetsTextView(rowView,setsTextViewIds[x-1],x,workout);
            }
        }

        private void setExerciseNameView(View rowView,String exerciseName) {
            TextView textView = (TextView) rowView.findViewById(R.id.fwStats_exerciseNameTextView);
            textView.setText("Exercise:"+" "+exerciseName);
        }

        private void set_SetsTextView(View rowView, int id, int set, WorkoutExercise workout) {
            TextView textView = (TextView) rowView.findViewById(id);
            textView.setVisibility(View.VISIBLE);
            int reps = workout.getReps("Set " + set);
            String weight = workout.getWeight("Set " + set)[WorkoutExercise.WEIGHT];
            String uOfmeas = workout.getWeight("Set " + set)[WorkoutExercise.UNIT_OF_MEAS];
            StringBuilder builder = new StringBuilder();
            String setData = builder.append("Set ").append(set).append(": ")
                    .append(reps).append("/").append(weight).append(uOfmeas).toString();
            textView.setText(setData);
        }
    }
}
