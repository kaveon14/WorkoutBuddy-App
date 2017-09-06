package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;
// TODO bug when getting time for chrono ex (1:00 is wrong)
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.BlankSWPopupMenu;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
//needs more robust features for time intervals
public class WorkoutFragment extends Fragment {

    private Exercise exercise;
    private WorkoutAdapter workoutAdapter;
    private int currentSet = 1;
    private int partialSetCount = 1;
    private List<String> sets;
    private static List<Exercise> exerciseList;

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
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setVisibility(View.INVISIBLE);
        }
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
                String maxTime = 5 > timeLimit.length() ? "0"+timeLimit : timeLimit;
                if(chronometer.getText().toString().equalsIgnoreCase(maxTime)) {
                    resetChronometer(chronometer);
                    setResetButton(root);
                    addSet();
                }
            }
        });
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
                //make sure workout is started
                try {
                    exerciseList = workoutAdapter.getExerciseList();
                    Map<String,String> map = workoutAdapter.getWorkoutData();
                    WorkoutExercise we = new WorkoutExercise(ExerciseFragment.getClickedExercise());
                    we.setWorkoutData(map);
                    BlankSWPopupMenu.workoutData.add(we);

                    Toast.makeText(getContext(),"Data Added",Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Toast.makeText(getContext(),"Workout Not Started!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hideChronoButton(View root) {
        Button btn = (Button) root.findViewById(R.id.button3);
        btn.setVisibility(View.INVISIBLE);
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
        String minutes = time.substring(0,2).trim();
        String seconds = time.substring(3,time.length()).trim();
        if(minutes.equalsIgnoreCase("00")) {
            return Integer.valueOf(seconds);
        } else {
            int mins = Integer.valueOf(minutes);
            int sec = (mins*60) + Integer.valueOf(seconds);
            return sec;
        }
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
        if(currentSet <=Integer.valueOf(exercise.getGoalSets())) {
            currentSet++;
            sets.add("Set "+ currentSet);
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
        int exerciseSets = Integer.valueOf(exercise.getGoalSets());
        if(currentSet <exerciseSets) {
            for (int x = currentSet; x < exerciseSets; x++) {
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

    private class WorkoutAdapter extends BaseAdapter {

        private Exercise exercise;
        private List<String> sets;
        private List<Exercise> exerciseList;
        private List<View> rowViews;
        private Map<String,String> workoutData;


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

        public Exercise getExerciseObject() {
            return new Exercise(exercise.getExerciseName(),null);
        }

        private String getData(View rowView) {
            EditText repsAndWeight_editTextView = (EditText) rowView.findViewById(R.id.repsNweightEditText);
            String repsAndWeight = repsAndWeight_editTextView.getText().toString();
            return repsAndWeight;
        }

        public Map<String,String> getWorkoutData() {
            if(workoutData == null) {
                workoutData = new Hashtable<>(10);
            }

            for(int x=rowViews.size()-1;x>0;x--) {
                View rowView = rowViews.get(x);
                if(rowView.isShown()) {
                    String set = getTextViewSet(rowView);
                    workoutData.put(set, getData(rowView));
                }
            }
            return workoutData;
        }

        public List<Exercise> getExerciseList() {
            for(int x=rowViews.size()-1;x>0;x--) {
                exerciseList.add(getExerciseObject());
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
            }
            String set = sets.get(position);
            setSetsTextView(rowView,set);
            return rowView;
        }

        private void setSetsTextView(View rowView,String set) {
            TextView setsView = (TextView) rowView.findViewById(R.id.setsTextView);
            setsView.setText(set);
        }

        private String getTextViewSet(View rowView) {
            TextView setsView = (TextView) rowView.findViewById(R.id.setsTextView);
            return setsView.getText().toString();
        }
    }
}