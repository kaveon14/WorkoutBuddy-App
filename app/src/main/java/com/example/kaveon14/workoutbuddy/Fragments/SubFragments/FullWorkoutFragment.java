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
import com.example.kaveon14.workoutbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class FullWorkoutFragment extends Fragment {//change name
    static int c = 1;

    private List<Exercise> exerciseList;

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

        return new FullWorkoutAdapter(exerciseList);
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    private class FullWorkoutAdapter extends BaseAdapter {

        private List<Exercise> exerciseList;
        private int[] setsTextViewIds = {
                R.id.setsTextView1,R.id.setsTextView2,R.id.setsTextView3,
                R.id.setsTextView4,R.id.setsTextView5,R.id.setsTextView6,
                R.id.setsTextView7,R.id.setsTextView8,R.id.setsTextView9,
                R.id.setsTextView10
        };

        public FullWorkoutAdapter(List<Exercise> exerciseList) {
            this.exerciseList = exerciseList;
        }

        public int getCount() {
            return exerciseList.size();
        }

        public Exercise getItem(int i) {
            return exerciseList.get(i);
        }

        public long getItemId(int i) {
            return i;
        }

        public View getView(int position,View rowView,ViewGroup viewGroup) {
            //error here with multiple exercises of the same type


            Exercise exercise = null;//the sets is being gettin wrong from lkifting stats table
            if(position < exerciseList.size()) {
                exercise = exerciseList.get(position);//need to get all ex near with same name
            }



            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.fullworkout_list_item,null);
            }
            List<Exercise> exercises = new ArrayList<>();
            if(exercise != null) {
                int index = getExerciseIndex(exerciseList, exercise);
                int base = getBaseIndex(exercise);
                for (int x = index - base; x < index; x++) {//error here beacuse of the sets
                    exercises.add(exerciseList.get(x));
                }
                setListItemView(rowView, exercises);
                exerciseList.removeAll(exercises);
            }




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

        private void setListItemView(View rowView,List<Exercise> exercises) {

            setExerciseNameView(rowView,exercises.get(0));
            int sets = exercises.get(0).getActualSets();
            if(sets != 1) {
                for (int x = 0; x < sets; x++) {
                    set_SetsTextView(rowView, setsTextViewIds[x], x, exercises.get(x));
                }
            } else {
                set_SetsTextView(rowView,setsTextViewIds[0],0,exercises.get(0));
            }
        }

        private void setExerciseNameView(View rowView,Exercise exercise) {
            TextView textView = (TextView) rowView.findViewById(R.id.exerciseNametextView5);
            textView.setText("Exercise: "+exercise.getExerciseName());
        }

        private void set_SetsTextView(View rowView, int id, int increment, Exercise exercise) {
            TextView textView = (TextView) rowView.findViewById(id);
            textView.setVisibility(View.VISIBLE);
            int reps = exercise.getActualReps();
            increment++;
            String weight = exercise.getActualWeight();
            textView.setText("Set "+increment+": "+reps+"/"+weight);
        }
    }

}
