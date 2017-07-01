package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class LiftingStatsFragment extends Fragment {//change name to workout stats

    public LiftingStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lifting_stats, container, false);

        return root;
    }

    private void test(View root){

        MainWorkoutTable mt = new MainWorkoutTable(getContext());
        List<String> m = mt.getSubWorkoutNames("TEST WORKOUT");
        List<SubWorkout> subWorkouts = new ArrayList<>(15);//better to get specif subWorkouts with exercise and sht alredy done


        ExerciseTable et = new ExerciseTable(getContext());
        List<Exercise> ex = et.getCustomExercises();
        for(int x=0;x<ex.size();x++) {
            ex.get(x).setActualReps(x);
            ex.get(x).setActualReps(x);
        }

        for(int x=0;x<m.size();x++) {
            subWorkouts.add(new SubWorkout(m.get(x),ex));
        }
    }

    private class LiftingStatsAdapter extends BaseAdapter {

        List<Exercise> exerciseList;//possibly not need subWorkouts contain the exercises
        List<SubWorkout> subWorkoutList;

        public LiftingStatsAdapter(List<SubWorkout> subWorkoutList) {
            this.subWorkoutList = subWorkoutList;
        }

        public int getCount() {
          return 0;
      }

        public long getItemId(int i) {
          return i;
      }

        public String getItem(int i) {
            return null;
        }

        public View getView(int position,View rowView,ViewGroup viewGroup) {
            SubWorkout subWorkout = subWorkoutList.get(position);
            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.liftingstats_list_item,null);
            }
            setListItemView(rowView,subWorkout);
            return rowView;
        }

        private void setListItemView(View rowView,SubWorkout subWorkout) {
            setDateView();
            setMainWorkoutTextView(rowView,subWorkout);
            setSubWorkoutTextView(rowView,subWorkout);
            setTotalSetsView(rowView);
            setTotalRepsView(rowView);
            setTotalWeightView(rowView);
        }

        private void setDateView() {

        }

        private void setMainWorkoutTextView(View rowView,SubWorkout subWorkout) {
            TextView textView = (TextView) rowView.findViewById(R.id.mainWorkout_textView);
            String text = textView.getText().toString();
            textView.setText(text + " " + subWorkout.getMainWorkoutName());
        }

        private void setSubWorkoutTextView(View rowView,SubWorkout subWorkout) {
            TextView textView = (TextView) rowView.findViewById(R.id.subWorkout_textView);
            String text = textView.getText().toString();
            textView.setText(text + " " + subWorkout.getSubWorkoutName());
        }

        private void setTotalSetsView(View rowView) {
            int totalSets = 0;
            for(Exercise exercise : exerciseList) {
                totalSets += exercise.getActualSets();
            }
            TextView textView = (TextView) rowView.findViewById(R.id.sets_textView);
            String text = textView.getText().toString();
            textView.setText(text + " " + totalSets);
        }

        private void setTotalRepsView(View rowView) {
            int totalReps = 0;
            for(Exercise exercise : exerciseList) {
                totalReps += exercise.getActualReps();
            }
            TextView textView = (TextView) rowView.findViewById(R.id.sets_textView);
            String text = textView.getText().toString();
            textView.setText(text + " " + totalReps);
        }

        private void setTotalWeightView(View rowView) {
            int totalWeight = 0;
            for(Exercise exercise : exerciseList) {
                totalWeight += exercise.getActualWeight();
            }
            TextView textView = (TextView) rowView.findViewById(R.id.weight_textView);
            String text = textView.getText().toString();
            textView.setText(text + " " + totalWeight);
        }



    }
}
