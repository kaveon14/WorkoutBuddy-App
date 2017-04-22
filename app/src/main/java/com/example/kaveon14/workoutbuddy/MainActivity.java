package com.example.kaveon14.workoutbuddy;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import static com.example.kaveon14.workoutbuddy.Fragments.BlankFragment.setContext;
import com.example.kaveon14.workoutbuddy.FragmentContent.WorkoutContent.WorkoutItem;
import com.example.kaveon14.workoutbuddy.FragmentContent.ExerciseContent.ExerciseItem;
import com.example.kaveon14.workoutbuddy.FragmentTextHandling.ExerciseDescriptions;
import com.example.kaveon14.workoutbuddy.FragmentTextHandling.ExerciseNames;
import com.example.kaveon14.workoutbuddy.Fragments.BlankFragment;
import com.example.kaveon14.workoutbuddy.Fragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.WorkoutFragment;
import java.util.LinkedList;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.FragmentTextHandling.WorkoutNames.standardWorkouts;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ExerciseFragment.OnListFragmentInteractionListener,
        WorkoutFragment.OnListFragmentInteractionListener,BlankFragment.OnFragmentInteractionListener {
    private ExerciseNames exerciseObject;
    public static  List<String> exerciseNames = new LinkedList<>();
    private FragmentTransaction fragmentTransaction;
    private ExerciseFragment exercise_frag = new ExerciseFragment();
    private WorkoutFragment workout_frag = new WorkoutFragment();
    private BlankFragment blank_frag = new BlankFragment();
    public static ExerciseItem exerciseItem = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        set_GUI_Elements(toolbar);
        setExerciseContent();
        addFragments();
        ExerciseImages ex = new ExerciseImages(this);
        ex.setImageMap();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else {
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        navigationMenuButton(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(ExerciseItem item) {
        exerciseItem = item;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(exercise_frag);
        fragmentTransaction.add(R.id.blank_fragment,blank_frag);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(WorkoutItem item) {
        System.out.println("wk: "+item.toString());
    }

    @Override
    public void onFragmentInteraction(Uri uri){}

    private void setDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setFloatingButton() {
        FloatingActionButton homeBtn = (FloatingActionButton) findViewById(R.id.home_button);//email button delete? user for
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//possible home button
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void navigationMenuButton(int id) {
        switch (id) {
            case R.id.lifting_stats:
                liftingStatsButton();
                break;
            case R.id.body_stats:
                bodyStatsButton();
                break;
            case R.id.workout_menu:
                workoutMenuButton();
                break;
            case R.id.exercise_menu:
                exerciseMenuButton(id);
                break;
            case R.id.calenderBtn:
                calenderButton();
                break;
        }
    }

    private void liftingStatsButton() {
        System.out.println("lifting stats");
    }

    private void bodyStatsButton() {
        System.out.println("body stats");
    }

    private void workoutMenuButton() {
        fragmentTransaction = getFragmentManager().beginTransaction();
        if(exercise_frag.isVisible()) {
            fragmentTransaction.hide(exercise_frag);
            fragmentTransaction.show(workout_frag);
        } else if(blank_frag.isVisible()) {
            fragmentTransaction.hide(blank_frag);
            fragmentTransaction.show(workout_frag);
        } else {
            fragmentTransaction.show(workout_frag);
        }
        fragmentTransaction.commit();
    }

    private void exerciseMenuButton(int id) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        if(workout_frag.isVisible()) {
            fragmentTransaction.hide(workout_frag);
            fragmentTransaction.show(exercise_frag);
        } else if(blank_frag.isVisible()) {
            fragmentTransaction.hide(blank_frag);
            fragmentTransaction.show(exercise_frag);
        } else {
            fragmentTransaction.show(exercise_frag);
        }
        fragmentTransaction.commit();
    }

    private boolean calenderButton() {
        fragmentTransaction = getFragmentManager().beginTransaction();
        if(exercise_frag.isVisible()) {
            fragmentTransaction.hide(exercise_frag);
            fragmentTransaction.show(workout_frag);
            fragmentTransaction.commit();
            return true;
        } else if(workout_frag.isVisible()) {
            fragmentTransaction.hide(workout_frag);
            fragmentTransaction.show(exercise_frag);
            fragmentTransaction.commit();
            return true;
        }
        return false;

    }

    private void addFragments() {
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.exercise_fragment,exercise_frag);
        fragmentTransaction.hide(exercise_frag);
        fragmentTransaction.add(R.id.workout_fragment,workout_frag);
        fragmentTransaction.hide(workout_frag);
        fragmentTransaction.commit();
    }

    private void set_GUI_Elements(Toolbar toolbar) {
        setFloatingButton();
        setDrawerLayout(toolbar);
        setNavigationView();
    }

    private void setExerciseContent() {
        setContext(this);
        standardWorkouts();
        exerciseObject = new ExerciseNames(this,"ExerciseNames.txt");
        exerciseNames = exerciseObject.readFile();
        ExerciseDescriptions ex = new ExerciseDescriptions(this);
        ex.setExerciseDescriptions();
    }

}
