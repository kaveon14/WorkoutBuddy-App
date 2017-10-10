package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.Activities.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.kaveon14.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;

public class CustomExercisePopup extends PopupWindowManager {

    private Context context;
    private MainActivity mainActivity;
    private Bitmap exerciseImageBitmap;
    private ExerciseFragment exerciseFragment;

    public CustomExercisePopup(View root, Context context) {
        this.context = context;
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.addexercise_popup_layout);
        setPopupViewId(R.id.addExercise_PopupWindow);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setPopupImageView();
        addExerciseButton();
    }

    public void setExerciseFragment(ExerciseFragment exerciseFragment) {
        this.exerciseFragment = exerciseFragment;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setExerciseImageBitmap(Bitmap exerciseImageBitmap) {
        this.exerciseImageBitmap = exerciseImageBitmap;
    }

    public void setPopupImageView() {
        ImageView imageView = (ImageView) popupLayout.findViewById(R.id.addExercisePopup_imageView);
        imageView.setImageResource(R.drawable.ic_menu_gallery);
        float x = 0.5f;float y = 0.5f;
        imageView.setScaleX(x);
        imageView.setScaleY(y);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExternalImageGallery();
            }
        });
    }

    private void addExerciseButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.addExercisePopupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise customExercise = getCustomExercise(popupLayout);
                new ExerciseTable(context)
                        .addAnExercise(customExercise);
                Toast.makeText(context
                        ,"Custom Exercise Added",
                        Toast.LENGTH_SHORT).show();
                exerciseFragment.addExerciseToList(customExercise);
                popupWindow.dismiss();
            }
        });
    }

    private void openExternalImageGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        mainActivity.startActivityForResult(pickIntent, MainActivity.RESULT_LOAD_IMAGE);
    }

    public void setImageViewWithGalleryImage() {
        ImageView imageView;
        if (exerciseImageBitmap != null) {
            if(popupWindow.isShowing()) {
                imageView = (ImageView) popupWindow.getContentView().findViewById(R.id.addExercisePopup_imageView);
                imageView.setImageResource(0);
                imageView.setImageBitmap(exerciseImageBitmap);
                imageView.setScaleX(1.0f);
                imageView.setScaleY(1.0f);
            }
        }
    }

    private Exercise getCustomExercise(View popupLayout) {
        String exerciseName = getExerciseName(popupLayout);
        String exerciseDescription = getExerciseDescription(popupLayout);
        Exercise exercise = new Exercise(exerciseName,exerciseDescription);
        if(exerciseImageBitmap != null) {
            exercise.setExerciseImage(exerciseImageBitmap);
        } else {
            exercise.setExerciseImage(BitmapFactory.decodeResource(mainActivity.getResources(),
                    R.mipmap.no_image));
        }
        return exercise;
    }

    private String getExerciseName(View popupLayout) {
        EditText et = (EditText) popupLayout.findViewById(R.id.addExercisePopup_NameEditText);
        return et.getText().toString();
    }

    private String getExerciseDescription(View popupLayout) {
        EditText et = (EditText) popupLayout.findViewById(R.id.addExercisePopup_DescriptionEditText);
        return et.getText().toString();
    }
}