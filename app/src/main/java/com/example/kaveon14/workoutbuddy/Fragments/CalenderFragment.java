package com.example.kaveon14.workoutbuddy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.R;

public class CalenderFragment extends Fragment {
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
        CalenderFragment calender_frag = this;
        View root = inflater.inflate(R.layout.fragment_calender, container, false);
        TextView textView = (TextView) root.findViewById(R.id.calenderTextView);
        textView.setText("Show Workout Name for the Date");

        return root;
    }

}
