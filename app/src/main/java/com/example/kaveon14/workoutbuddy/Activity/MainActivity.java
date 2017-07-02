package com.example.kaveon14.workoutbuddy.Activity;
// TODO start lifting stats stuff
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.BodyStatsFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.WorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankBodyStatsFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import com.roomorama.caldroid.CaldroidFragment;
import java.util.Calendar;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.CalenderFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int fragId;
    private CaldroidFragment caldroid_frag;
    public static MainActivity activity;
    private CustomExercisePopup customExercisePopup;
    private static Bitmap bitmap;
    private int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity =  this;
        setBaseContent();
        getPermissions();
        preloadExerciseData();


        Chronometer c = new Chronometer(getBaseContext());
        c.setBase(SystemClock.elapsedRealtime());
        c.start();
        c.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                System.out.println("time: "+chronometer.getText());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(customExercisePopup != null) {
            customExercisePopup.setImageViewWithGalleryImage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(customExercisePopup != null) {
            bitmap = customExercisePopup.getGalleryImage(requestCode, resultCode, data);
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        // TODO move this or think if better method
        boolean inActivity = true;
        if(getActiveFragment() == null || fragId == 0) {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            setFloatingActionButtonImage(fab,inActivity);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        } else {
            inActivity = false;
        }
    }

    private void setFloatingActionButtonImage(FloatingActionButton fab, boolean onActivity) {
        if(onActivity) {
            fab.setImageResource(R.drawable.ic_menu_camera);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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
                //nothing yet and not sure needed
                showRealWorkoutFragment();
                break;
            case R.id.workout_menu:
                showWorkoutFragment();
                break;
            case R.id.exercise_menu:
                showExerciseFragment();
                break;
            case R.id.calenderBtn:
                showCaldroidFragment();
                break;
            case R.id.nav_send:
                //nothing yet
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void preloadExerciseData() {
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.exercise_fragment,exerciseFragment)
                .hide(exerciseFragment)
                .commit();
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

    private void showRealWorkoutFragment() {
        WorkoutFragment wf = new WorkoutFragment();
        addFragmentToStack(getActiveFragment(),wf,R.id.workout_fragment);
    }

    private Fragment getActiveFragment() {
        return getSupportFragmentManager().findFragmentById(fragId);
    }

    private void showBodyStatsFragment() {
        BodyStatsFragment bodyStats_fragment = new BodyStatsFragment();
        addFragmentToStack(getActiveFragment(),bodyStats_fragment,R.id.bodyStats_fragment);
    }

    private void showExerciseFragment() {
        ExerciseFragment exercise_frag = new ExerciseFragment();
        addFragmentToStack(getActiveFragment(),exercise_frag,R.id.exercise_fragment);
    }

    public void showBlankExerciseFragment() {
        BlankExerciseFragment blankExercise_frag = new BlankExerciseFragment();
        addFragmentToStack(getActiveFragment(),blankExercise_frag
                , R.id.blankExercise_fragment);
    }

    private void showWorkoutFragment() {
        MainWorkoutFragment mainWorkout_frag = new MainWorkoutFragment();
        addFragmentToStack(getActiveFragment(),mainWorkout_frag,R.id.mainWorkout_fragment);
    }

    public void showBlankBodyStatsFragment() {
        BlankBodyStatsFragment blankBodyStats_frag = new BlankBodyStatsFragment();
        addFragmentToStack(getActiveFragment(),blankBodyStats_frag,R.id.blankBodyStats_fragment);
    }

    private void showCaldroidFragment() {
        fragId = R.id.calendar_fragment;
        caldroid_frag = new CaldroidFragment();
        setCaldroidFragContent(caldroid_frag);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(getActiveFragment() != null) {
            ft.hide(getActiveFragment());
        }
        ft.add(R.id.calendar_fragment,caldroid_frag);
        ft.add(R.id.calendar_fragment,new CalenderFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void addFragmentToStack(@Nullable Fragment fragToHide, Fragment fragToShow, int fragId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        this.fragId = fragId;
        if(fragToHide != null) {fragmentTransaction.hide(fragToHide);}
        if(caldroid_frag!=null && caldroid_frag.isVisible()) {fragmentTransaction.hide(caldroid_frag);}
        fragmentTransaction.add(fragId, fragToShow);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getActiveFragment();
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

    private void setCaldroidFragContent(CaldroidFragment caldroid_frag) {
        Calendar cal = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroid_frag.setArguments(args);
    }

    public void showAddExercisePopupWindow() {
        customExercisePopup = new CustomExercisePopup(getCurrentFocus());
        customExercisePopup.showPopupWindow();
    }

    private class CustomExercisePopup extends PopupWindowManager {

        public CustomExercisePopup(View root) {
            setRootView(root);
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
                    ExerciseFragment.addExerciseToList(customExercise);
                    popupWindow.dismiss();
                }
            });
        }

        private void openExternalImageGallery() {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            MainActivity.activity.startActivityForResult(pickIntent, RESULT_LOAD_IMAGE);
        }

        private void setImageViewWithGalleryImage() {
            ImageView imageView;
            if (bitmap != null) {
                if(popupWindow.isShowing()) {
                    imageView = (ImageView) popupWindow.getContentView().findViewById(R.id.addExercisePopup_imageView);
                    imageView.setImageResource(0);
                    imageView.setImageBitmap(bitmap);
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
            Bitmap exerciseImage = bitmap;

            Exercise exercise = new Exercise(exerciseName,exerciseDescription);
            exercise.setExerciseImage(exerciseImage);
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
// do a-chart engine later for now just figure out how to save workout data and body data