package com.example.kaveon14.workoutbuddy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;
import static com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.ExerciseTable;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;

public class ExerciseFragment extends Fragment {

    private Context context;
    protected static Exercise clickedExerciseName;
    public static List<Exercise> exerciseList;
    private ExerciseFragment exercise_frag = this;

    public ExerciseFragment() {

    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);//not sure on item listener
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exercise, container, false);
        setListView(root);
        return root;
    }

    private void setListView(View root) {
        ListView listView = (ListView) root.findViewById(R.id.exercise_listView);
        listView.setAdapter(setExerciseAdapter());
    }

    private ExerciseAdapter setExerciseAdapter() {
        int amountOfExercise = new ExerciseTable(context).getColumn(COLUMN_EXERCISES).size();
        List<Exercise> exercises = new ArrayList<>();
        for(int x=0;x<amountOfExercise;x++) {
            exercises.add(getExercise(x));
        }
        exerciseList = exercises;
        return new ExerciseAdapter(exercises);
    }

    private Exercise getExercise(int x) {
        List<String> exerciseNames = new ExerciseTable(context).getColumn(COLUMN_EXERCISES);
        return new Exercise(exerciseNames.get(x),null);
    }

    public void showBlankExerciseFragment() {
        BlankExerciseFragment bf = new BlankExerciseFragment();
        bf.setContext(context);
        getFragmentManager().beginTransaction()
                .hide(exercise_frag)
                .replace(R.id.blankExercise_fragment,bf)
                .addToBackStack(null)
                .commit();
    }

    private class ExerciseAdapter extends BaseAdapter {

        private List<Exercise> exerciseList;

        public ExerciseAdapter(List<Exercise> exercises) {
            exerciseList = exercises;
        }

        @Override
        public int getCount() {
            return exerciseList.size();
        }

        @Override
        public Exercise getItem(int i) {//may need work
            return exerciseList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.simple_list_item,viewGroup,false);
            }
            final Exercise exercise = getItem(i);//TODO need to implement a checkbox and a small view to
            setTextView(exercise,rowView);
            exerciseClicked(rowView,exercise);
            return rowView;
        }

        private void setTextView(Exercise exercise,View rowView) {
            TextView exercseName = (TextView) rowView.findViewById(R.id.exerciseList_textView);
            exercseName.setText(exercise.getExerciseName());
        }

        private void exerciseClicked(View rowView,Exercise exercise) {
            rowView.setOnClickListener(new View.OnClickListener() {//maybe do on long click too
                @Override//or maybe this is just for sending information
                public void onClick(View v) {
                    MainActivity.fragId = R.id.blankExercise_fragment;
                    ExerciseFragment.clickedExerciseName = exercise;
                    exercise_frag.showBlankExerciseFragment();
                }
            });
        }
    }
}



