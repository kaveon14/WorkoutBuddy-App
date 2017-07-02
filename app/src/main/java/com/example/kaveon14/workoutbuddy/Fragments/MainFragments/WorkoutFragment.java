package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.R;
//fix some stuff refactor then push
public class WorkoutFragment extends Fragment {

    private String START_TIME_INTERVALS = "Start";
    private String RESET_TIME_INTERVALS = "Reset";

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        // content needs to be loaded dynamically based on amount of exercises,sets, and reps
        checkBox(root);
        return root;
    }

    private void checkBox(View root) {
        String m = "Enter Time in MM:SS";
        CheckBox checkBox = (CheckBox) root.findViewById(R.id.checkBox);

        System.out.println("dafuq: "+checkBox.isChecked());

        EditText ed = (EditText) root.findViewById(R.id.textView2);
        ed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(ed.getText().toString().equalsIgnoreCase(m)) {
                    ed.setText("");
                }
                return false;
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) {
                    checkBox.setText("Hide Time Intervals");
                    setStartButton(root);
                    ed.setVisibility(View.VISIBLE);
                } else  {
                    checkBox.setText("Show Time Intervals");
                    hideButton(root);
                    hideChrono(root);
                    ed.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setStartButton(View root) {
        Button btn = (Button) root.findViewById(R.id.button3);
        EditText editText = (EditText) root.findViewById(R.id.textView2);
        btn.setText(START_TIME_INTERVALS);
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
        Chronometer chrono = (Chronometer) root.findViewById(R.id.chronometer2);
        chrono.setFormat("%s");
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        int maxTime = getTime(timeLimit);
        setProgressBarMax(root,maxTime);
        chrono.setVisibility(View.VISIBLE);
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                int time = getTime(chrono.getText().toString());
                setProgressBarProgress(root,time);
                if(chronometer.getText().toString().equalsIgnoreCase(timeLimit)) {//this is called again need to reset chrono
                    System.out.println("vcalled: "+timeLimit);
                    chronometer.setText("00:00");
                    chronometer.stop();
                    chronometer.setVisibility(View.INVISIBLE);
                    setResetButton(root);
                }
            }
        });
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
        hideChrono(root);
        Button btn = (Button) root.findViewById(R.id.button3);
        EditText editText = (EditText) root.findViewById(R.id.textView2);
        btn.setText(RESET_TIME_INTERVALS);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.INVISIBLE);
                String text = "Enter Time in MM:SS";
                editText.setText(text);
                setProgressBarProgress(root,0);
                setStartButton(root);
            }
        });
    }

    private void hideButton(View root) {
        Button btn = (Button) root.findViewById(R.id.button3);
        btn.setVisibility(View.INVISIBLE);
    }


    private void hideChrono(View root) {
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

    private abstract class WorkoutAdapter extends BaseAdapter {//abstract until required methods implemented

    }
}
//button update text every second with time once time met set text to reset before have button set to start to get data from edit text
//have check box saying if use time only show new listView item every increment else show all of them
//make button etc. show or not show based off of check box or just disable the button so it does not look empty