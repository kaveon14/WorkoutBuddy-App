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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.ExerciseImages;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
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
        EditText exTextBox = (EditText) view.findViewById(R.id.exDescriptionBox);
        exTextBox.setText(exerciseContent.getExerciseDescription());
        ImageView exImageView = (ImageView) view.findViewById(R.id.exerciseImageView);
        exImageView.setImageBitmap(exerciseContent.getImageBitmap(view));
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
                        "otherwise do nit==ot show the button",Toast.LENGTH_LONG).show();
            }
        });
    }

    private class ExerciseContent extends ExerciseImages {

        private final int getImageID() {
            int image_id;
            try {
                String string_image_id = EXERCISE_IMAGE_MAP.
                        get(ExerciseFragment.clickedExercise.getExerciseName());
                image_id = Integer.valueOf(string_image_id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                image_id = R.mipmap.ic_launcher;
            }
            return image_id;
        }

        public Bitmap getImageBitmap(View view) {
            return BitmapFactory.decodeResource(view.getResources(),getImageID());
        }

        private Exercise getClickedExercise() {
            return ExerciseFragment.clickedExercise;
        }

        public String getExerciseDescription() {
            DataBaseSQLiteHelper db = new DataBaseSQLiteHelper(getContext());
            SQLiteDatabase database = db.getReadableDatabase();
            Cursor cursor = database.query(DataBaseContract.ExerciseData.TABLE_NAME,
                    null,null,null,null,null,null);
            String exDescription = null;
            while(cursor.moveToNext() && cursor.getString(1) != getClickedExercise().getExerciseName()) {
                exDescription = cursor.getString(2);
            }
            return exDescription;
        }
    }
}