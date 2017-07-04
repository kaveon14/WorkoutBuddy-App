package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;
//needs more robust features for time intervals
public class WorkoutFragment extends Fragment {

    private Exercise exercise;
    private WorkoutAdapter workoutAdapter;
    private int setCount = 1;//needs more accurate name
    private int partialSetCount = 1;
    private List<String> sets;
    List<Exercise> exerciseList;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        checkBox(root);
        setStartButton(root);
        ListView listView = (ListView) root.findViewById(R.id.listView);
        listView.setAdapter(getAdapter());
        setSaveDataBtn(root);
        return root;
    }

    private void checkBox(View root) {
        CheckBox checkBox = (CheckBox) root.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) {
                    showContent(checkBox);
                } else  {
                    hideContent(checkBox);
                }
            }
        });
    }

    private void showContent(CheckBox checkBox) {
        View root = checkBox.getRootView();
        checkBox.setText("Hide Time Intervals");
        showEditText(root);
        ProgressBar timeProgressBar = (ProgressBar) root.findViewById(R.id.timeProgressBar);
        if(timeProgressBar.getProgress() == timeProgressBar.getMax()) {
            setResetButton(root);
        }else {
            setStartButton(root);
        }
        showPartialAdapter();
    }

    private void hideContent(CheckBox checkBox) {
        View root = checkBox.getRootView();
        checkBox.setText("Show Time Intervals");
        hideChronoButton(root);
        hideChronometer(root);
        hideProgressBar(root);
        hideEditText(root);
        showFullAdapter();
    }

    private void setStartButton(View root) {
        Button btn = (Button) root.findViewById(R.id.button3);
        EditText editText = (EditText) root.findViewById(R.id.textView2);
        btn.setText("Start");
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.INVISIBLE);
                startChronoTimeInterval(root,editText.getText().toString());
            }
        });
    }

    private void startChronoTimeInterval(View root, String timeLimit) {
        Chronometer chrono = setupChronometer(root,timeLimit);
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                int time = getTime(chrono.getText().toString());
                setProgressBarProgress(root,time);
                if(chronometer.getText().toString().equalsIgnoreCase(timeLimit)) {//this is called again need to reset chrono
                    resetChronometer(chronometer);
                    setResetButton(root);
                    addSet();
                }
            }
        });
    }

    private Chronometer setupChronometer(View root,String timeLimit) {
        Chronometer chrono = (Chronometer) root.findViewById(R.id.chronometer2);
        chrono.setFormat("%s");
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        int maxTime = getTime(timeLimit);
        setProgressBarMax(root,maxTime);
        chrono.setVisibility(View.VISIBLE);
        return chrono;
    }

    private void resetChronometer(Chronometer chronometer) {
        chronometer.setText("00:00");
        chronometer.stop();
        chronometer.setVisibility(View.INVISIBLE);
    }

    private int getTime(String time){
        time = time.replace(":"," ");
        String minutes = time.substring(0,2);
        String seconds = time.substring(3,5).trim();
        if(minutes.equalsIgnoreCase("00")) {
            return Integer.valueOf(seconds);
        } else {
            int mins = Integer.valueOf(minutes);
            int sec = (mins*60) + Integer.valueOf(seconds);
            return sec;
        }
    }

    private void setResetButton(View root) {
        hideChronometer(root);
        Button btn = (Button) root.findViewById(R.id.button3);
        btn.setText("Reset");
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.INVISIBLE);
                setProgressBarProgress(root,0);
                setStartButton(root);
            }
        });
    }

    private void setSaveDataBtn(View root) {
        if(exerciseList==null) {exerciseList = new ArrayList<>();}
        Button btn = (Button) root.findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutAdapter.getExerciseList();
            }
        });
    }

    private void hideChronoButton(View root) {
        Button btn = (Button) root.findViewById(R.id.button3);
        btn.setVisibility(View.INVISIBLE);
    }

    private void hideChronometer(View root) {
        Chronometer chrono = (Chronometer) root.findViewById(R.id.chronometer2);
        chrono.setVisibility(View.INVISIBLE);
    }

    private void setProgressBarMax(View root,int timeLimit) {
        ProgressBar timeProgressBar = (ProgressBar) root.findViewById(R.id.timeProgressBar);
        timeProgressBar.setMax(timeLimit);
    }

    private void setProgressBarProgress(View root,int progress) {
        ProgressBar timeProgressBar = (ProgressBar) root.findViewById(R.id.timeProgressBar);
        timeProgressBar.setVisibility(View.VISIBLE);
        timeProgressBar.setProgress(progress);
    }

    private void hideProgressBar(View root) {
        ProgressBar timeProgressBar = (ProgressBar) root.findViewById(R.id.timeProgressBar);
        timeProgressBar.setVisibility(View.INVISIBLE);
    }

    private void addSet() {
        if(setCount<=Integer.valueOf(exercise.getExerciseSets())) {
            setCount++;
            sets.add("Set "+setCount);
            partialSetCount = sets.size();
            workoutAdapter.notifyDataSetChanged();
        }
    }

    private WorkoutAdapter getAdapter() {
        sets = new ArrayList<>(5);
        sets.add("Set 1");
        exerciseList = new ArrayList<>();
        workoutAdapter = new WorkoutAdapter(exercise,sets,exerciseList);
        return workoutAdapter;
    }

    private void showFullAdapter() {
        int exerciseSets = Integer.valueOf(exercise.getExerciseSets());
        if(setCount<exerciseSets) {
            for (int x = setCount; x < exerciseSets; x++) {
                sets.add("Set " + (x + 1));
            }
            workoutAdapter.notifyDataSetChanged();
        }
    }

    private void showPartialAdapter() {
        for(int x = sets.size(); x> partialSetCount; x--) {
            sets.remove("Set "+x);
        }
        workoutAdapter.notifyDataSetChanged();
    }

    private void hideEditText(View root) {
        EditText editText = (EditText) root.findViewById(R.id.textView2);
        editText.setVisibility(View.INVISIBLE);
    }

    private void showEditText(View root) {
        EditText editText = (EditText) root.findViewById(R.id.textView2);
        editText.setVisibility(View.VISIBLE);
    }

    private void addWorkoutData() {
        //add nothing to table need to add to a data structure with ine final push that stores
        //everything at same time
    }

    private class WorkoutAdapter extends BaseAdapter {//abstract until required methods implemented

        private SubWorkout subWorkout;
        private Exercise exercise;
        private List<String> sets;
        //set#: reps#/weight#
        List<Exercise> exerciseList;
        List<View> rowViews;


        public WorkoutAdapter(Exercise exercise,List<String> sets,List<Exercise> exerciseList) {
            this.exercise = exercise;
            this.sets = sets;
            this.exerciseList = exerciseList;

        }

        public int getCount() {
            return sets.size();
        }

        public String getItem(int i) {
            return sets.get(i);
        }

        public long getItemId(int i) {
            return i;
        }

        private void test(View rowView) {
            for(int x=0;x<getCount();x++) {
                getExerciseObject(rowView);
            }
        }

        public Exercise getExerciseObject(View rowView) {
            int actualSets = getCount();
            EditText repsAndWeight_editTextView = (EditText) rowView.findViewById(R.id.repsNweightEditText);
            String repsAndWeight = repsAndWeight_editTextView.getText().toString();
            int index = repsAndWeight.indexOf("/");
            String reps = repsAndWeight.subSequence(0,index).toString();
            String weight = repsAndWeight.subSequence(index+1,repsAndWeight.length()).toString();

            Exercise ex = new Exercise(exercise.getExerciseName(),null);
            if(!reps.equalsIgnoreCase("reps")) {
                ex.setActualReps(Integer.valueOf(reps));
            }
            ex.setActualSets(actualSets);
            ex.setActualWeight(weight);
            System.out.println("wtf");
            return ex;
        }

        public List<Exercise> getExerciseList() {
            for(int x=rowViews.size()-1;x>0;x--) {
                exerciseList.add(getExerciseObject(rowViews.get(x)));
            }
            System.out.println("yes: "+exerciseList.size());
            //exerciseList.remove(0);
            for(int x=0;x<exerciseList.size();x++) {
                System.out.println("nice: "+exerciseList.get(x).getActualReps());
            }
            return exerciseList;
        }

        public View getView(int position, View rowView, ViewGroup viewGroup) {
            if(rowView == null) {
                if(rowViews==null) {
                    rowViews = new ArrayList<>();
                }
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.workout_list_item,null);
                rowViews.add(position,rowView);
                //getExerciseObject(rowView);
            }
            String set = sets.get(position);
            setSetsTextView(rowView,set);
            return rowView;
        }

        private void setSetsTextView(View rowView,String set) {
            TextView setsView = (TextView) rowView.findViewById(R.id.setsTextView);
            setsView.setText(set);
        }
    }
}
//button update text every second with time once time met set text to reset before have button set to start to get data from edit text
//have check box saying if use time only show new listView item every increment else show all of them
//make button etc. show or not show based off of check box or just disable the button so it does not look empty