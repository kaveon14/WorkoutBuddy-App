package com.example.WorkoutBuddy.workoutbuddy.Activities;
// TODO decouple even more try to keep database and business login as serpereate as possible,elimnate any mgic numbers
/*
-keep local sql database
-redo photos/store in an actual location and in database just store the file path(for local images)
-gonna need php
 */
// go local if no changes between remote  and local database
// TODO get ready to test the mysql database first run simple test to see if connected
// TODO for instant workout add it to one log for the day and add do exercise option to exercise with max sets(dont take date)
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.*;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.*;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows.CustomExercisePopup;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.FragmentStackManager;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.MainFragments.*;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.SubFragments.BlankExerciseFragment;
import com.example.WorkoutBuddy.workoutbuddy.R;
import java.text.*;
import java.util.Date;
// TODO make sure when a fragment with a recycler view is no longer empty to hide the text view
// TODO fix sql injection
// TODO add choice to choose kgs or lbs only store number in database and ask(out in setting withs convertors
// TODO decouple alot by using ids instead of exercise name
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Menu menu;
    private boolean activityHidden = false;
    private ExerciseFragment exercise_frag;
    public static final int RESULT_LOAD_IMAGE = 1;
    private CustomExercisePopup customExercisePopup;
    public static final int REQUEST_IMAGE_CAPTURE = 2;
    private ProgressPhotosFragment progressPhoto_frag;
    private FragmentStackManager fragmentStackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContent();
        getPermissions();
        loadRecentStats();
        fragmentStackManager =
                new FragmentStackManager(getSupportFragmentManager());
        setTileOnCLickListeners();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap customImageBitmap = getGalleryImage(requestCode, resultCode, data);
        if(customImageBitmap != null) {
            customExercisePopup.setExerciseImageBitmap(customImageBitmap);
            customExercisePopup.setImageViewWithGalleryImage();
        }
       // customImageBitmap = getCameraImage(requestCode,resultCode,data);



        if(customImageBitmap != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
            String date = dateFormat.format(new Date());
            ProgressPhotosTable table = new ProgressPhotosTable(getBaseContext());
            table.addProgressPhoto(date,customImageBitmap);
            progressPhoto_frag.addPhotoToList(new ProgressPhoto(date,customImageBitmap));
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
            String picturePath = cursor.getString(columnIndex);//its from here
            cursor.close();//store this not

            return BitmapFactory.decodeFile(picturePath);//return path
        }
        return null;
    }

    private Bitmap getCameraImage(int requestCode,int resultCode,Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {//store the file path in here
           // Bundle extras = data.getExtras();//no bitmap with that method must get file path
            return BitmapFactory.decodeFile(progressPhoto_frag.path);
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        FragmentStackManager.PopFragmentStack();
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

    private void setTileOnCLickListeners() {
        exerciseTileCLicked();
        workoutTileClicked();
        progressPhotosTileCLicked();
        workoutStatsTileClicked();
        bodyStatsTileClicked();
    }

    private void exerciseTileCLicked() {
        CardView cardView = (CardView) findViewById(R.id.exerciseTile);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExerciseFragment();
            }
        });
    }

    private void workoutTileClicked() {
        CardView cardView = (CardView) findViewById(R.id.workoutTile);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWorkoutFragment();
            }
        });
    }

    private void progressPhotosTileCLicked() {
        CardView cardView = (CardView) findViewById(R.id.progressPhotoTile);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressPhotoFragment();
            }
        });
    }

    private void workoutStatsTileClicked() {
        CardView cardView = (CardView) findViewById(R.id.workoutStatsTile);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWorkoutStatsFragment();
            }
        });
    }

    private void bodyStatsTileClicked() {
        CardView cardView = (CardView) findViewById(R.id.bodyStatsTile);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBodyStatsFragment();
            }
        });
    }

    private void showWorkoutStatsFragment() {
        WorkoutStatsFragment workoutStatsFragment = new WorkoutStatsFragment();
        workoutStatsFragment.setMenu(menu);
        workoutStatsFragment.setFragmentStackManager(fragmentStackManager);
        fragmentStackManager.addFragmentToStack(workoutStatsFragment,R.id.workoutStats_fragment);
        hideMainActivity();
    }

    private void showBodyStatsFragment() {
        BodyStatsFragment bodyStats_fragment = new BodyStatsFragment();
        fragmentStackManager.addFragmentToStack(bodyStats_fragment,R.id.bodyStats_fragment);
        hideMainActivity();
    }

    private void showExerciseFragment() {
        exercise_frag = new ExerciseFragment();
        exercise_frag.setMenu(menu);
        exercise_frag.setFragmentStackManager(fragmentStackManager);
        exercise_frag.setMainActivity(this);
        fragmentStackManager.addFragmentToStack(exercise_frag,R.id.exercise_fragment);
        hideMainActivity();
    }

    public void showBlankExerciseFragment() {
        BlankExerciseFragment blankExercise_frag = new BlankExerciseFragment();
        fragmentStackManager.addFragmentToStack(blankExercise_frag,R.id.blankExercise_fragment);
    }

    private void showWorkoutFragment() {
        MainWorkoutFragment mainWorkout_frag = new MainWorkoutFragment();
        mainWorkout_frag.setMenu(menu);
        mainWorkout_frag.setMainActivity(this);
        mainWorkout_frag.setFragmentStackManager(fragmentStackManager);
        fragmentStackManager.addFragmentToStack(mainWorkout_frag,R.id.mainWorkout_fragment);
        hideMainActivity();
    }

    private void showProgressPhotoFragment() {
        getCameraPermission();
        progressPhoto_frag = new ProgressPhotosFragment();
        progressPhoto_frag.setMainActivity(this);
        fragmentStackManager.addFragmentToStack(progressPhoto_frag, R.id.progressPhotos_fragment);
        hideMainActivity();
    }

    public void showAddExercisePopupWindow() {
        customExercisePopup = new CustomExercisePopup(getCurrentFocus(),getBaseContext());
        customExercisePopup.setExerciseFragment(exercise_frag);
        customExercisePopup.setMainActivity(this);
        customExercisePopup.showPopupWindow();
    }

    private void showMainActivityContent() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.activityScrollView);
        scrollView.setVisibility(View.VISIBLE);
        loadRecentStats();
    }

    private void hideMainActivity() {
        if (!activityHidden) {
            hideMainActivityContent();
            activityHidden = true;
        }
    }

    private void hideMainActivityContent() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.activityScrollView);
        scrollView.setVisibility(View.INVISIBLE);
    }

    private void loadRecentStats() {
        try {
            loadRecentBodyStats();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        try {
            loadRecentWorkoutStats();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void loadRecentWorkoutStats() throws IndexOutOfBoundsException {
        int INDEX = 0;
        WorkoutStatsTable table = new WorkoutStatsTable(getBaseContext());
        loadRecentWorkoutStatsPt1(table,INDEX);
        loadRecentWorkoutStatsPt2(table,INDEX);
    }

    private void loadRecentBodyStats() throws IndexOutOfBoundsException{
        int INDEX = 0;
        BodyTable table = new BodyTable(getBaseContext());
        loadRecentBodyStatsPt1(table, INDEX);
        loadRecentBodyStatsPt2(table, INDEX);
        loadRecentBodyStatsPt3(table, INDEX);
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
        textView.setText(getString(R.string.datePointer)+" "+TableManager.getParsedDate(date));

        textView = (TextView) findViewById(R.id.recentMainWorkoutView);
        textView.setText(getString(R.string.mainWorkoutPointer)+" "+mainWorkout);

        textView = (TextView) findViewById(R.id.recentSubWorkoutView);
        textView.setText(getString(R.string.subWorkoutPointer)+" "+subWorkout);
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
        textView.setText(getString(R.string.totalReps)+" "+sets);

        textView = (TextView) findViewById(R.id.recentTotalRepsView);
        textView.setText(getString(R.string.totalReps)+" "+reps);

        textView = (TextView) findViewById(R.id.recentTotalWeightView);
        textView.setText(getString(R.string.totalWeight)+" "+weight);
    }
}
