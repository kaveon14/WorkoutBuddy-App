package com.example.kaveon14.workoutbuddy.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.kaveon14.workoutbuddy.Data.Exercise;
import com.example.kaveon14.workoutbuddy.Data.ExerciseImages;
import com.example.kaveon14.workoutbuddy.DataBase.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.DataBaseSQLiteHelper;
import com.example.kaveon14.workoutbuddy.R;

public class BlankExerciseFragment extends Fragment {

    private Context context;

    public BlankExerciseFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context) {
        this.context = context;
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
        return view;
    }

    private void setExerciseContent(View view) {
        ExerciseImages exerciseImages = new ExerciseImages(context);
        exerciseImages.setImageMap();
        ExerciseContent exerciseContent = new ExerciseContent();
        EditText exTextBox = (EditText) view.findViewById(R.id.exDescriptionBox);
        exTextBox.setText(exerciseContent.getExerciseDescription());
        ImageView exImageView = (ImageView) view.findViewById(R.id.exerciseImageView);
        exImageView.setImageBitmap(exerciseContent.getImageBitmap(view));

    }

    private class ExerciseContent extends ExerciseImages {


        public final int getImageID() {//possibly change it to an actual exercise and get the name
            String string_image_id = EXERCISE_IMAGE_MAP.
                    get(ExerciseFragment.clickedExerciseName.getExerciseName());
            //exercise name is not in map
            return Integer.valueOf(string_image_id);
        }

        public Bitmap getImageBitmap(View view) {
            return BitmapFactory.decodeResource(view.getResources(),getImageID());
        }

        public Exercise getClickedExercise() {
            return ExerciseFragment.clickedExerciseName;
        }

        public String getExerciseDescription() {
            DataBaseSQLiteHelper db = new DataBaseSQLiteHelper(context);
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