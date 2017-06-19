package com.example.kaveon14.workoutbuddy.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.BodyStatsFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import com.roomorama.caldroid.CaldroidFragment;

import java.lang.reflect.Field;
import java.util.Calendar;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.CalenderFragment;
// TODO allow deletion of ex from workout,subworkout from mainworkout,and mainworkout
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int fragId;
    private CaldroidFragment caldroid_frag;
    public static MainActivity activity;
    private Bitmap bitmap;
    private int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContent();
        permissionShit();
        activity =  this;
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        // TODO move this
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
                //nothing yet
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

    private void permissionShit() {
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

    private void showWorkoutFragment() {
        MainWorkoutFragment mainWorkout_frag = new MainWorkoutFragment();
        addFragmentToStack(getActiveFragment(),mainWorkout_frag,R.id.mainWorkout_fragment);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = getGalleryImage(requestCode,resultCode,data);
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

    @Override
    public void onStart() {
        super.onStart();
        setImageViewWithGalleryImage();
    }

    private void setImageViewWithGalleryImage() {
        ExercisePopupWindowHandler ex = new ExercisePopupWindowHandler();
        ImageView imageView;
        if (bitmap != null) {
            PopupWindow popupWindow = ex.showPopupWindow(getCurrentFocus());
            if(popupWindow.isShowing()) {
                imageView = (ImageView) popupWindow.getContentView().findViewById(R.id.addExercisePopup_imageView);
                imageView.setImageBitmap(bitmap);
                imageView.setScaleX(1.0f);
                imageView.setScaleY(1.0f);
            }
        }
    }

    public void showAddExercisePopupWindow() {
        ExercisePopupWindowHandler popupWindowHandler = new ExercisePopupWindowHandler();
        popupWindowHandler.showPopupWindow(getCurrentFocus());
    }

    public class ExercisePopupWindowHandler {

        public PopupWindow showPopupWindow(View root) {
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            View popupLayout = getPopupLayout(root);
            PopupWindow popupWindow = new PopupWindow(popupLayout, width, height);
            popupWindow.setFocusable(true);
            popupWindow.update(0, 0, width, height);
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            dimBackground(popupLayout);
            setPopupImageView(popupLayout);

            return popupWindow;
        }

        private View getPopupLayout(View root) {
            LayoutInflater inflater = (LayoutInflater) getBaseContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            return inflater.inflate(R.layout.addexercise_popup_layout, (ViewGroup)
                    root.findViewById(R.id.addExercise_PopupWindow));
        }

        private void dimBackground(View popupLayout) {
            View container = (View) popupLayout.getParent();
            WindowManager wm = (WindowManager) getBaseContext()
                    .getSystemService(Context.WINDOW_SERVICE);

            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) container.getLayoutParams();
            layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            layoutParams.dimAmount = 0.6f;
            wm.updateViewLayout(container, layoutParams);
        }

        private void setPopupImageView(View popupLayout) {
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

        private void openExternalImageGallery() {//
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            startActivityForResult(pickIntent, RESULT_LOAD_IMAGE);
        }
    }
}
// do a-chart engine later for now just figure out how to save workout data and body data