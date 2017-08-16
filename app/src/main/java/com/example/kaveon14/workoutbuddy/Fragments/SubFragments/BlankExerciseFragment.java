package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;

public class BlankExerciseFragment extends Fragment {

    public BlankExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_exercise, container, false);
        setExerciseContent(view);
        setFloatingActionButton();//shown for now
        return view;
    }

    private void setExerciseContent(View view) {
        TextView exTextBox = (TextView) view.findViewById(R.id.exDescriptionBox);
        String exerciseDescription = ExerciseFragment.getClickedExercise().getExerciseDescription();

        if(exerciseDescription != null) {
            exTextBox.setText(exerciseDescription);
        } else {
            exTextBox.setText(R.string.addExerciseDescription);
        }

        ImageView exImageView = (ImageView) view.findViewById(R.id.exerciseImageView);
        Bitmap bitmap = ExerciseFragment.getClickedExercise().getExerciseImage();

        if(bitmap != null) {
            exImageView.setImageBitmap(bitmap);
        } else {
            exImageView.setImageResource(R.mipmap.no_image);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            setFloatingActionButton();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resetFloatingActionButton();
    }

    private void resetFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        }
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
                Toast.makeText(getContext(),"if custom exercise allow it too be editable " +
                        "otherwise do not show the border_accent",Toast.LENGTH_LONG).show();
            }
        });
    }
}