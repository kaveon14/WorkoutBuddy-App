package com.example.kaveon14.workoutbuddy.Activity;
/* TODO- make it impossible to add subWorkout to the default workout
 TODO- and also make it impossible to add or delete exercises */
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.DefaultData.DefaultExerciseContent;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.BodyTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ProgressPhotosTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.WorkoutStatsTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentStackManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.BodyStatsFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ProgressPhotosFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.WorkoutStatsFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO data loaded in mainActivty is not the most recent yet
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ExerciseFragment exercise_frag;//make these variables in a pyramid
    private ProgressPhotosFragment progressPhoto_frag;
    private int fragId;
    public MainActivity mainActivity = this;
    private CustomExercisePopup customExercisePopup;
    private Bitmap exerciseImageBitmap;
    private int RESULT_LOAD_IMAGE = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private Menu menu;
    private boolean activityHidden = false;


    FragmentStackManager fragmentStackManager =
            new FragmentStackManager(getSupportFragmentManager());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContent();
        getPermissions();
        loadRecentStats();
        DefaultExerciseContent exerciseContent = new DefaultExerciseContent(getBaseContext());
        exerciseContent.getExerciseDescriptions();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(customExercisePopup != null) {
            customExercisePopup.setImageViewWithGalleryImage();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(customExercisePopup != null) {
            exerciseImageBitmap = customExercisePopup.getGalleryImage(requestCode, resultCode, data);
        }
        Bitmap cameraBitmap = getCameraImage(requestCode,resultCode,data);
        if(cameraBitmap != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
            String date = dateFormat.format(new Date());
            ProgressPhotosTable table = new ProgressPhotosTable(getBaseContext());
            table.addProgressPhoto(date,cameraBitmap);
            progressPhoto_frag.addPhotoToList(new ProgressPhoto(date,cameraBitmap));
        }

    }

    private Bitmap getCameraImage(int requestCode,int resultCode,Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            return (Bitmap) extras.get("data");
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(getSupportFragmentManager().getBackStackEntryCount()==1
                && activityHidden) {
            activityHidden = false;
            showMainActivityContent();
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.body_stats:
                showBodyStatsFragment();
                break;
            case R.id.lifting_stats:
                showWorkoutStatsFragment();
                break;
            case R.id.workout_menu:
                showWorkoutFragment();
                break;
            case R.id.exercise_menu:
                showExerciseFragment();
                break;
            case R.id.progress_photos:
                showProgressPhotoFragment();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }
        }
    }

    private Fragment getActiveFragment() {
        return getSupportFragmentManager().findFragmentById(fragId);
    }

    private void showWorkoutStatsFragment() {
        WorkoutStatsFragment workoutStatsFragment = new WorkoutStatsFragment();
        workoutStatsFragment.setMenu(menu);
        fragmentStackManager.addFragmentToStack(workoutStatsFragment,R.id.workoutStats_fragment);
        if(!activityHidden) {
            hideMainActivityContent();
            activityHidden = true;
        }
    }

    private void showBodyStatsFragment() {
        BodyStatsFragment bodyStats_fragment = new BodyStatsFragment();
        fragmentStackManager.addFragmentToStack(bodyStats_fragment,R.id.bodyStats_fragment);
        if(!activityHidden) {
            hideMainActivityContent();
            activityHidden = true;
        }
    }

    private void showExerciseFragment() {
        exercise_frag = new ExerciseFragment();
        exercise_frag.setMenu(menu);
        exercise_frag.setFragmentStackManager(fragmentStackManager);
        exercise_frag.setMainActivity(mainActivity);
        fragmentStackManager.addFragmentToStack(exercise_frag,R.id.exercise_fragment);
        if(!activityHidden) {
            hideMainActivityContent();
            activityHidden = true;
        }
    }

    public void showBlankExerciseFragment() {
        BlankExerciseFragment blankExercise_frag = new BlankExerciseFragment();
        fragmentStackManager.addFragmentToStack(blankExercise_frag,R.id.blankExercise_fragment);
    }

    private void showWorkoutFragment() {
        MainWorkoutFragment mainWorkout_frag = new MainWorkoutFragment();
        mainWorkout_frag.setMenu(menu);
        mainWorkout_frag.setMainActivity(mainActivity);
        mainWorkout_frag.setFragmentStackManager(fragmentStackManager);
        fragmentStackManager.addFragmentToStack(mainWorkout_frag,R.id.mainWorkout_fragment);
        if(!activityHidden) {
            hideMainActivityContent();
            activityHidden = true;
        }
    }

    private void showProgressPhotoFragment() {
        getCameraPermission();
        progressPhoto_frag = new ProgressPhotosFragment();
        progressPhoto_frag.setMainActivity(mainActivity);
        fragmentStackManager.addFragmentToStack(progressPhoto_frag,R.id.progressPhotos_fragment);
        if(!activityHidden) {
            hideMainActivityContent();
            activityHidden = true;
        }
    }

    public void addFragmentToStack(@Nullable Fragment fragToHide, Fragment fragToShow, int fragId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        this.fragId = fragId;
        if(fragToHide != null) {fragmentTransaction.hide(fragToHide);}
        fragmentTransaction.add(fragId, fragToShow);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getActiveFragment();
        if(!activityHidden) {
            hideMainActivityContent();
            activityHidden = true;
        }
    }

    private void showMainActivityContent() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.activityScrollView);
        scrollView.setVisibility(View.VISIBLE);
    }

    private void hideMainActivityContent() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.activityScrollView);
        scrollView.setVisibility(View.INVISIBLE);
    }

    private void loadRecentStats() {
        loadRecentBodyStats();
        loadRecentWorkoutStats();
      //  loadRecentProgressPhoto();
    }

    private void loadRecentWorkoutStats() {
        int INDEX = 0;
        WorkoutStatsTable table = new WorkoutStatsTable(getBaseContext());
        try {
            loadRecentWorkoutStatsPt1(table,INDEX);
            loadRecentWorkoutStatsPt2(table,INDEX);
        } catch (IndexOutOfBoundsException e) {
            // do nothing
        }
    }

    private void loadRecentProgressPhoto() {
        int INDEX = 0;
        ProgressPhotosTable table = new ProgressPhotosTable(getBaseContext());
        try {
            Bitmap photo = table.getImageData(INDEX + 1,
                    DataBaseContract.ProgressPhotos.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
            //ImageView imageView = (ImageView) findViewById(R.id.progressPhotoView);
            //imageView.setImageBitmap(photo);
        } catch(IndexOutOfBoundsException e) {
            //do nothing
        }
    }

    private void loadRecentBodyStats() {
        int INDEX = 0;
        BodyTable table = new BodyTable(getBaseContext());
        try {
            loadRecentBodyStatsPt1(table, INDEX);
            loadRecentBodyStatsPt2(table, INDEX);
            loadRecentBodyStatsPt3(table, INDEX);
        } catch (IndexOutOfBoundsException e) {
            //do nothing
        }
    }

    private void loadRecentBodyStatsPt1(BodyTable table,int INDEX) throws IndexOutOfBoundsException {
        String date = table.getColumn(DataBaseContract.BodyData.COLUMN_DATE,INDEX+1,
                DataBaseContract.BodyData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String weight = table.getColumn(DataBaseContract.BodyData.COLUMN_WEIGHT,INDEX+1,
                DataBaseContract.BodyData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String chest = table.getColumn(DataBaseContract.BodyData.COLUMN_CHEST_SIZE,INDEX+1,
                DataBaseContract.BodyData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);

        TextView textView = (TextView) findViewById(R.id.recentDateView);
        textView.setText(date);

        textView = (TextView) findViewById(R.id.recentWeightView);
        textView.setText(weight);

        textView = (TextView) findViewById(R.id.recentChestView);
        textView.setText(chest);
    }

    private void loadRecentBodyStatsPt2(BodyTable table,int INDEX) throws IndexOutOfBoundsException {
        String back = table.getColumn(DataBaseContract.BodyData.COLUMN_BACK_SIZE,INDEX+1,
                DataBaseContract.BodyData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String arm = table.getColumn(DataBaseContract.BodyData.COLUMN_ARM_SIZE,INDEX+1,
                DataBaseContract.BodyData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String forearm = table.getColumn(DataBaseContract.BodyData.COLUMN_FOREARM_SIZE,INDEX+1,
                DataBaseContract.BodyData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);

        TextView textView = (TextView) findViewById(R.id.recentBackView);
        textView.setText(back);

        textView = (TextView) findViewById(R.id.recentArmsView);
        textView.setText(arm);

        textView = (TextView) findViewById(R.id.recentForearmsView);
        textView.setText(forearm);
    }

    private void loadRecentBodyStatsPt3(BodyTable table,int INDEX) throws IndexOutOfBoundsException {
        String waist = table.getColumn(DataBaseContract.BodyData.COLUMN_WAIST_SIZE,INDEX+1,
                DataBaseContract.BodyData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String quad = table.getColumn(DataBaseContract.BodyData.COLUMN_QUAD_SIZE,INDEX+1,
                DataBaseContract.BodyData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String calves = table.getColumn(DataBaseContract.BodyData.COLUMN_CALF_SIZE,INDEX+1,
                DataBaseContract.BodyData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);

        TextView textView = (TextView) findViewById(R.id.recentWaistView);
        textView.setText(waist);

        textView = (TextView) findViewById(R.id.recentQuadsView);
        textView.setText(quad);

        textView = (TextView) findViewById(R.id.recentCalvesView);
        textView.setText(calves);
    }


    private void loadRecentWorkoutStatsPt1(WorkoutStatsTable table,int INDEX)
            throws IndexOutOfBoundsException {
        String date = table.getColumn(DataBaseContract.WorkoutData.COLUMN_DATE,INDEX+1,
                DataBaseContract.WorkoutData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String mainWorkout = table.getColumn(DataBaseContract.WorkoutData.COLUMN_MAINWORKOUT,INDEX+1,
                DataBaseContract.WorkoutData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String subWorkout = table.getColumn(DataBaseContract.WorkoutData.COLUMN_SUBWORKOUT,INDEX+1,
                DataBaseContract.WorkoutData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);

        TextView textView = (TextView) findViewById(R.id.recentWorkoutDateView);
        textView.setText(textView.getText().toString()+" "+date);

        textView = (TextView) findViewById(R.id.recentMainWorkoutView);
        textView.setText(textView.getText().toString()+" "+mainWorkout);

        textView = (TextView) findViewById(R.id.recentSubWorkoutView);
        textView.setText(textView.getText().toString()+" "+subWorkout);
    }

    private void loadRecentWorkoutStatsPt2(WorkoutStatsTable table,int INDEX)
            throws IndexOutOfBoundsException {
        String sets = table.getColumn(DataBaseContract.WorkoutData.COLUMN_TOTAL_SETS,INDEX+1,
                DataBaseContract.WorkoutData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String reps = table.getColumn(DataBaseContract.WorkoutData.COLUMN_TOTAL_REPS,INDEX+1,
                DataBaseContract.WorkoutData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);
        String weight = table.getColumn(DataBaseContract.WorkoutData.COLUMN_TOTAL_WEIGHT,INDEX+1,
                DataBaseContract.WorkoutData.COLUMN_DATE+" DESC LIMIT 1").get(INDEX);

        TextView textView = (TextView) findViewById(R.id.recentTotalSetsView);
        textView.setText(textView.getText().toString()+" "+sets);

        textView = (TextView) findViewById(R.id.recentTotalRepsView);
        textView.setText(textView.getText().toString()+" "+reps);

        textView = (TextView) findViewById(R.id.recentTotalWeightView);
        textView.setText(textView.getText().toString()+" "+weight);
    }


    private void setBaseContent() {
        setContentView(R.layout.activity_main);
        setDrawer(setToolbar());
        setNaviagtionView();
    }

    private Toolbar setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void setDrawer(Toolbar toolbar) {
        DrawerLayout mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setNaviagtionView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void showAddExercisePopupWindow() {
        customExercisePopup = new CustomExercisePopup(getCurrentFocus());
        customExercisePopup.showPopupWindow();
    }

    private class CustomExercisePopup extends PopupWindowManager {//must put this in
        // different class this

        public CustomExercisePopup(View root) {
            setRootView(root);
            setWindowManagerContext(getBaseContext());
            setPopupLayout(R.layout.addexercise_popup_layout);
            setPopupViewId(R.id.addExercise_PopupWindow);
        }

        public void showPopupWindow() {
            displayPopupWindow();
            setPopupImageView();
            addExerciseButton();
        }

        private void setPopupImageView() {
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
                    new ExerciseTable(getBaseContext())
                            .addAnExercise(customExercise);
                    Toast.makeText(getBaseContext()
                            ,"Custom Exercise Added",
                            Toast.LENGTH_SHORT).show();
                    exercise_frag.addExerciseToList(customExercise);
                    popupWindow.dismiss();
                }
            });
        }

        private void openExternalImageGallery() {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            startActivityForResult(pickIntent, RESULT_LOAD_IMAGE);
        }

        private void setImageViewWithGalleryImage() {
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

        private Bitmap getGalleryImage(int requestCode,int resultCode,Intent data) {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                return BitmapFactory.decodeFile(picturePath);
            }
            return null;
        }

        private Exercise getCustomExercise(View popupLayout) {
            String exerciseName = getExerciseName(popupLayout);
            String exerciseDescription = getExerciseDescription(popupLayout);
            Exercise exercise = new Exercise(exerciseName,exerciseDescription);
            if(exerciseImageBitmap != null) {
                exercise.setExerciseImage(exerciseImageBitmap);
            } else {
                exercise.setExerciseImage(BitmapFactory.decodeResource(getResources(),
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
}
