package com.example.kaveon14.workoutbuddy.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.BodyStatsFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankBodyStatsFragment;
import com.example.kaveon14.workoutbuddy.R;
import com.roomorama.caldroid.CaldroidFragment;
import java.util.Calendar;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.CalenderFragment;
// TODO allow add exercise to subworkout from subworkout fragment
// TODO allow deletion of exercise from workout,subworkout from mainworkout,and mainworkout
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int fragId;
    private CaldroidFragment caldroid_frag;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContent();
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

    private Fragment getActiveFragment() {
        return getSupportFragmentManager().findFragmentById(fragId);
    }

    private void showBlankBodyStatsFragment() {
        BlankBodyStatsFragment blankBodyStatsFragment = new BlankBodyStatsFragment();
        addFragmentToStack(getActiveFragment(),blankBodyStatsFragment,R.id.blankBodyStats_fragment);
    }

    private void showBodyStatsFragment() {
        BodyStatsFragment bodyStats_fragment = new BodyStatsFragment();
        addFragmentToStack(getActiveFragment(),bodyStats_fragment,R.id.bodyStats_fragment);
    }

    private void showExerciseFragment() {
        ExerciseFragment exercise_frag = new ExerciseFragment();
        addFragmentToStack(getActiveFragment(),exercise_frag,R.id.exercise_fragment);
        getActiveFragment();
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
        Calendar cal = Calendar.getInstance();//put this in different function
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroid_frag.setArguments(args);
    }
}
// do a-chart engine later for now just figure out how to save workout data and body data