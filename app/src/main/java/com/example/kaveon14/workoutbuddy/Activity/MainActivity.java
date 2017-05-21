package com.example.kaveon14.workoutbuddy.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.example.kaveon14.workoutbuddy.Fragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.WorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

import com.example.kaveon14.workoutbuddy.Fragments.CalenderFragment;
// TODO handle exercise content error
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // TODO create better looking list views
// TODO use check boxes to mass add exercises to a workout
// TODO create a body frag???? or just a blank "bodyFrag" ???
    public static int fragId;
    private CaldroidFragment caldroid_frag;

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
        // Inflate the menu; this adds items to the action bar if it is present.
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.body_stats) {

        } else if (id == R.id.lifting_stats) {

        } else if (id == R.id.workout_menu) {
            showWorkoutFragment();
        } else if (id == R.id.exercise_menu) {
            showExerciseFragment();
        } else if (id == R.id.calenderBtn) {
            showCaldroidFragment();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Fragment getActiveFragment() {
        return getSupportFragmentManager().findFragmentById(fragId);
    }

    private void showExerciseFragment() {
        ExerciseFragment exercise_frag = new ExerciseFragment();
        exercise_frag.setContext(this);
        addFragmentToStack(getActiveFragment(),exercise_frag,R.id.exercise_fragment);
        getActiveFragment();
    }

    private void showWorkoutFragment() {
        WorkoutFragment workout_frag = new WorkoutFragment();
        workout_frag.setContext(this);
        addFragmentToStack(getActiveFragment(),workout_frag,R.id.workout_fragment);
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
        if(caldroid_frag.isVisible()) {fragmentTransaction.hide(caldroid_frag);}
        fragmentTransaction.add(fragId, fragToShow);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getActiveFragment();
    }

    private void setBaseContent() {
        setContentView(R.layout.activity_main);
        setDrawer(setToolbar());
        setFloatingButton();
        setNaviagtionView();
    }

    private void setFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
