package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.kaveon14.workoutbuddy.R;

public class TestCalenderFragment extends Fragment {

    public TestCalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_test_calender, container, false);
        // Inflate the layout for this fragment
        CalendarView cv = (CalendarView) root.findViewById(R.id.calendarView);

        return root;
    }//for now just load workout on dates
}
