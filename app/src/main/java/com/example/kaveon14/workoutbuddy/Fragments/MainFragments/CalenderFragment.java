package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;
//get better transition
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.WorkoutStatsTable;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.FullWorkoutStatsFragment;
import com.example.kaveon14.workoutbuddy.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
//for calendar events store name of main and sub workout and date with time
public class CalenderFragment extends Fragment {

    private CaldroidFragment caldroidFragment;
    private static List<SubWorkout> completedWorkoutList = null;
    private static Map<Date,SubWorkout> workoutMap = null;
    public CalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calender, container, false);
        TextView textView = (TextView) root.findViewById(R.id.calenderTextView);
        String temporaryMessage  = "Show MainWorkout -> SubWorkout Name for the Date";
        textView.setText(temporaryMessage);
        setFloatingActionButton();
        caldroidFragmentOnClick();
        if(completedWorkoutList == null) {
            completedWorkoutList = new WorkoutStatsTable(getContext())
                    .getCompletedWorkouts();
            workoutMap = getWorkoutDataMap();
        }
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            setFloatingActionButton();
        }
    }

    public void setCaldroidFragment(CaldroidFragment caldroidFragment) {
        this.caldroidFragment = caldroidFragment;
    }

    private FloatingActionButton setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setImageResource(R.drawable.ic_menu_manage);
            handleFloatingActionButtonEvents(fab);
        }
        return fab;
    }

    private void handleFloatingActionButtonEvents(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"do calender stuff",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void caldroidFragmentOnClick() {
        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                openFullWorkoutStatsFragment(date);
            }
        };
        caldroidFragment.setCaldroidListener(listener);
    }

    private void openFullWorkoutStatsFragment(Date date) {
        FullWorkoutStatsFragment fw = new FullWorkoutStatsFragment();
        fw.setWorkoutData(workoutMap.get(date).getWorkoutData());
        getFragmentManager().beginTransaction()
                .hide(this)
                .hide(caldroidFragment)
                .add(R.id.fullWorkoutStats_fragment,fw)
                .addToBackStack(null)
                .commit();

    }

    private Map<Date,SubWorkout> getWorkoutDataMap() {
        Map<Date,SubWorkout> workoutData = new Hashtable<>();
        Date date;
        String unParsedDate;
        String parsedDate;
        for(SubWorkout subWorkout :completedWorkoutList) {
            unParsedDate = subWorkout.getDate();
            parsedDate = getParsedDate(unParsedDate);
            date = new Date(parsedDate);
            workoutData.put(date,subWorkout);
        }
        return workoutData;
    }

    private String getParsedDate(String date) {//length will always be the same
        String year = date.substring(0,date.length()-6);
        String month = date.substring(5,date.length()-3);
        String day = date.substring(date.length()-2);
        return new StringBuilder(month).append("/")
                .append(day).append("/").append(year).toString();
    }
}
