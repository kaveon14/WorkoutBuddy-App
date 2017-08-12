package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.ExerciseImages;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
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
        ExerciseImages exerciseImages = new ExerciseImages(getContext());
        exerciseImages.setImageMap();
        ExerciseContent exerciseContent = new ExerciseContent();
        TextView exTextBox = (TextView) view.findViewById(R.id.exDescriptionBox);

        String exerciseDcription = exerciseContent.getExerciseDescription();
        exTextBox.setText(exerciseContent.getClickedExercise().getExerciseDescription());
        if(exerciseDcription != null) {
            exTextBox.setText(exerciseDcription);
        } else {
            exTextBox.setText(R.string.addExerciseDescription);
        }
        ImageView exImageView = (ImageView) view.findViewById(R.id.exerciseImageView);
        Bitmap bitmap = exerciseContent.getClickedExercise().getExerciseImage();
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

    private class ExerciseContent extends ExerciseImages {//most likely nothing needed

        private final int getImageID() {
            String string_image_id = EXERCISE_IMAGE_MAP.
                    get(ExerciseFragment.getClickedExercise().getExerciseName());
            if(string_image_id != null) {
                return Integer.valueOf(string_image_id);
            } else {
                return R.mipmap.ic_launcher;
            }
        }

        private Exercise getClickedExercise() {
            return ExerciseFragment.getClickedExercise();
        }

        public String getExerciseDescription() {//not even needed
            DataBaseSQLiteHelper db = new DataBaseSQLiteHelper(getContext());
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.query(DataBaseContract.ExerciseData.TABLE_NAME,
                    null,null,null,null,null,null);
            String exDescription = null;
            Exercise exercise = getClickedExercise();
            while(cursor.moveToNext() && cursor
                    .getString(cursor.getColumnIndexOrThrow(DataBaseContract.ExerciseData.COLUMN_EXERCISES))
                    != exercise.getExerciseName()) {

                exDescription = cursor
                        .getString(cursor.getColumnIndexOrThrow(DataBaseContract.ExerciseData.COLUMN_EXERCISE_DESCRIPTION));
                String dafuq = exDescription;

            }
            return exDescription;
        }

        public Bitmap getExerciseImage() {
            ExerciseTable et = new ExerciseTable(getContext());
            return et.getExerciseImage(getClickedExercise());
        }

        public Bitmap getImageBitmap(View view) {
            return BitmapFactory.decodeResource(view.getResources(),getImageID());
        }
    }
}