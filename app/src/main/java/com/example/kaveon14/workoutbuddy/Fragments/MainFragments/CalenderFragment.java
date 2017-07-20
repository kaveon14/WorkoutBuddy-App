package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Date;

public class CalenderFragment extends Fragment {

    private CaldroidFragment caldroidFragment;

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
                System.out.println("date: "+date);
            }
        };
        caldroidFragment.setCaldroidListener(listener);
    }

}
//date in calfrag = Mon Jul 17 00:00:00 CDT 2017