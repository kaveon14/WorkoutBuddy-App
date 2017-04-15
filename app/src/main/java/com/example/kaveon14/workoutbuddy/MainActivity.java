package com.example.kaveon14.workoutbuddy;

import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.kaveon14.workoutbuddy.FragmentContent.WorkoutContent.WorkoutItem;
import com.example.kaveon14.workoutbuddy.FragmentContent.ExerciseContent.ExerciseItem;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static com.example.kaveon14.workoutbuddy.WorkoutNames.standardWorkouts;

//content main == first page
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ExerciseFragment.OnListFragmentInteractionListener,
        WorkoutFragment.OnListFragmentInteractionListener {
//create class to handle all functions

    public static final String TAG = MainActivity.class.getSimpleName();
    private ExerciseNames exerciseObject;
    public static  List<String> exerciseNames = new LinkedList<>();
    private FragmentTransaction fragmentTransaction;
    private ExerciseFragment exercise_frag = new ExerciseFragment();
    private WorkoutFragment workout_frag = new WorkoutFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        exerciseObject = new ExerciseNames(this,"ExerciseNames.txt");
        exerciseNames = exerciseObject.readFile();
        standardWorkouts();
        //for (String string : exerciseNames) {//not needed
          //  Log.d(TAG, string);
        //}
        setFloatingButton();
        setDrawerLayout(toolbar);
        setNavigationView();
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
        navigationMenuButton(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(ExerciseItem item) {

    }

    @Override
    public void onListFragmentInteraction(WorkoutItem item) {

    }



    //zero put all this in new class and hide details
    //first when in fragment allow back press to previous page
    //second create a home button
    //third allow items in list to open new page with associated details(fragment of main activity)
    //fourth start database

    //functions below are exclusively for the onCreate method
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);//email button delete? user for
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//possible home button
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

   //thw functions below are exclusively for the onNavigationItemSelected method
   private void navigationMenuButton(int id) {
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
        workout_frag = WorkoutFragment.newInstance(1);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(exercise_frag);
        fragmentTransaction.add(R.id.workout_fragment,workout_frag);
        fragmentTransaction.commit();
    }

    private void exerciseMenuButton(int id) {
        exercise_frag =  ExerciseFragment.newInstance(1);
       // fragmentTransaction.hide(workout_frag);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(workout_frag);
        fragmentTransaction.add(R.id.exercise_fragment,exercise_frag);//change support.v4 to app.Fragment
        fragmentTransaction.commit();
    }

    private void calenderButton() {
        System.out.println("calender");
    }

    public void testMap() {
        Map<String,String> exercieses = new Hashtable<>();

        exercieses.put("bench press","this is a bench press");
        exercieses.put("back squat","this is a back squat");
        exercieses.put("front squat","this is a front squat");
        System.out.println("test: "+exercieses.get("back squat"));//yes it works as expected

    }
}
