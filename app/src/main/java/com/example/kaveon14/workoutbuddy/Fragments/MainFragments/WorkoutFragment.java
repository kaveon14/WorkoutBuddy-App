package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kaveon14.workoutbuddy.R;

public class WorkoutFragment extends Fragment {

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
        return root;
    }
}
