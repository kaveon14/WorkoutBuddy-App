package com.example.kaveon14.workoutbuddy;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kaveon14.workoutbuddy.dummy.DummyContent;


//content main == first page
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ExerciseFragment.OnListFragmentInteractionListener,
        WorkoutFragment.OnListFragmentInteractionListener {
//create class to handle all functions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override//possibly split up into seperate statements
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.lifting_stats) {//put all of this into functions
            System.out.println("lifting stats");
        } else if (id == R.id.body_stats) {
            System.out.println("body stats");
        } else if (id == R.id.workout_menu) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            WorkoutFragment frag = WorkoutFragment.newInstance(1);
            ft.add(R.id.workout_fragment,frag);//change support.v4 to app.Fragment
            ft.commit();
            //selectFrag();
            System.out.println("workout");
        } else if (id == R.id.exercise_menu) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ExerciseFragment frag = ExerciseFragment.newInstance(1);
            ft.add(R.id.exercise_fragment,frag);//change support.v4 to app.Fragment
            ft.commit();
            System.out.println("exercises");
        } else if (id == R.id.calenderBtn) {
            System.out.println("calender");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
    //zero put all this in new class and hide details
    //first when in fragment allow back press to previous page
    //second create a home button
    //third allow items in list to open new page with associated details(fragment of main activity)
    //fourth start database

}
