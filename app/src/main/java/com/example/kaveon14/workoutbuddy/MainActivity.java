package com.example.kaveon14.workoutbuddy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaveon14.workoutbuddy.DataBase.DataBaseSQLiteHelper;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutTable;
import com.example.kaveon14.workoutbuddy.FragmentContent.BlankWorkoutContent.BlankWorkoutItem;
import com.example.kaveon14.workoutbuddy.FragmentContent.WorkoutContent.WorkoutItem;
import com.example.kaveon14.workoutbuddy.FragmentContent.ExerciseContent.ExerciseItem;
import com.example.kaveon14.workoutbuddy.FragmentRecyclers.MyExerciseRecyclerViewAdapter;
import com.example.kaveon14.workoutbuddy.FragmentTextHandling.ExerciseDescriptions;
import com.example.kaveon14.workoutbuddy.FragmentTextHandling.ExerciseNames;
import com.example.kaveon14.workoutbuddy.Fragments.BlankExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.BlankWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.TextViewHostFragment;
import com.example.kaveon14.workoutbuddy.Fragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.WorkoutFragment;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.example.kaveon14.workoutbuddy.FragmentTextHandling.WorkoutNames.standardWorkouts;
// TODO make this class smaller!!!!!!!!
// TODO CREATE DATABASE JUST FOR EXERCIES AND SORT ALPHABETICALLY
// TODO test fragments with replace mthod to shorten the functions lentgh
// TODO fix image errors
// TODO add cardio and abs exercises etc.
// TODO add reps and sets under workout exercises


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ExerciseFragment.OnListFragmentInteractionListener,
        WorkoutFragment.OnListFragmentInteractionListener,BlankExerciseFragment.OnFragmentInteractionListener,
        TextViewHostFragment.OnFragmentInteractionListener,BlankWorkoutFragment.OnListFragmentInteractionListener {


    public static  List<String> exerciseNames = new ArrayList<>();
    public android.support.v4.app.FragmentTransaction fragmentTransaction;
    public ExerciseFragment exercise_frag = new ExerciseFragment();
    private WorkoutFragment workout_frag = new WorkoutFragment();
    private BlankExerciseFragment blankExercise_frag = new BlankExerciseFragment();
    private CaldroidFragment caldroid_frag = new CaldroidFragment();
    private TextViewHostFragment calender_frag = new TextViewHostFragment();
    private BlankWorkoutFragment blankWorkout_frag = new BlankWorkoutFragment();
    public static ExerciseItem exerciseItem = null;
    FloatingActionButton floatingBtn = null;
    Context context = this;
    Bundle args = new Bundle();

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
        floatingBtn.hide();
        exercise_frag.setExerciseContext(this);
        testDataBase();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        floatingBtn.show();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else {
            super.onBackPressed();
            hideFloatingBtn(workout_frag);
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
        navigationMenuButtons(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(ExerciseItem item) {
        exerciseItem = item;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(exercise_frag);
        fragmentTransaction.add(R.id.blankExercise_fragment, blankExercise_frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(WorkoutItem item) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(workout_frag);
        fragmentTransaction.show(blankWorkout_frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(BlankWorkoutItem item) {

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
        floatingBtn = (FloatingActionButton) findViewById(R.id.home_button);//email button delete? user for
        floatingBtn.setImageResource(R.drawable.ic_menu_manage);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//possible home button
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }

    public void navigationMenuButtons(int id) {
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
               exerciseMenuButton();
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
    }

    private void workoutMenuButton() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(exercise_frag.isVisible()) {
            fragmentTransaction.hide(exercise_frag);
        } else if(blankExercise_frag.isVisible()) {
            fragmentTransaction.hide(blankExercise_frag);
        } else if(caldroid_frag.isVisible()) {
            fragmentTransaction.hide(caldroid_frag);
            fragmentTransaction.hide(calender_frag);
        }
        fragmentTransaction.show(workout_frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        floatingBtn.show();
    }

    private void exerciseMenuButton() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(workout_frag.isVisible()) {
            fragmentTransaction.hide(workout_frag);
        } else if(blankExercise_frag.isVisible()) {
            fragmentTransaction.hide(blankExercise_frag);
        } else if(caldroid_frag.isVisible()) {
            fragmentTransaction.hide(caldroid_frag);
            fragmentTransaction.hide(calender_frag);
        }
        fragmentTransaction.show(exercise_frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void calenderButton() {//needed for bottom of calender fragment
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(exercise_frag.isVisible()) {
            fragmentTransaction.hide(exercise_frag);
        } else if(workout_frag.isVisible()) {
            fragmentTransaction.hide(workout_frag);
        } else if(blankExercise_frag.isVisible()) {
            fragmentTransaction.hide(blankExercise_frag);
        }
        fragmentTransaction.show(caldroid_frag);
        fragmentTransaction.show(calender_frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        extra();
        caldroidFragment(caldroid_frag);
    }

    private void addFragments() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.exercise_fragment,exercise_frag);
        fragmentTransaction.hide(exercise_frag);

        fragmentTransaction.add(R.id.workout_fragment,workout_frag);
        fragmentTransaction.hide(workout_frag);

        fragmentTransaction.add(R.id.calendar_fragment, caldroid_frag);
        fragmentTransaction.hide(caldroid_frag);

        fragmentTransaction.add(R.id.blankWorkout_fragment,blankWorkout_frag);
        fragmentTransaction.hide(blankWorkout_frag);

        fragmentTransaction.commit();
        setUpCaldroidFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.calendar_fragment,calender_frag);
        fragmentTransaction.hide(calender_frag);
        fragmentTransaction.commit();
    }

    private void set_GUI_Elements(Toolbar toolbar) {
        setFloatingButton();
        setDrawerLayout(toolbar);
        setNavigationView();
    }

    private void setExerciseContent() {
        blankExercise_frag.setContext(this);//error possibly here
        standardWorkouts();
        ExerciseNames exerciseObject = new ExerciseNames(this,"ExerciseNames.txt");
        exerciseNames = exerciseObject.readFileSorted();
        ExerciseDescriptions ex = new ExerciseDescriptions(this);
        ex.setExerciseDescriptions();
    }

    private void setUpCaldroidFragment() {
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroid_frag.setArguments(args);
    }

    private void extra() {//change the name of this function
        Date date = new Date("5/4/17");//works
        ColorDrawable transparent = new ColorDrawable(getResources().getColor(R.color.caldroid_transparent));
        caldroid_frag.setBackgroundDrawableForDate(transparent,date);//this is the color
        caldroid_frag.refreshView();
    }

    public void caldroidFragment(final CaldroidFragment caldroidFragment) {//change the name of this function
        final CaldroidListener listener  = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(context, "nice this works",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {//dont add to main
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(caldroidFragment);
                fragmentTransaction.hide(calender_frag);
                fragmentTransaction.show(workout_frag);//open the workout that is listed
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onCaldroidViewCreated() {//dont add to main
            }

        };
        caldroidFragment.setCaldroidListener(listener);
    }

    private void hideFloatingBtn(WorkoutFragment workoutFragment) {
        if(workoutFragment.isVisible()) {
            floatingBtn.show();
        } else {
            floatingBtn.hide();
        }
    }

    public void testDataBase() {
        DataBaseSQLiteHelper db = new DataBaseSQLiteHelper(this);
        SQLiteDatabase database = db.getWritableDatabase();

        WorkoutTable wt = new WorkoutTable(this);
        wt.printWorkoutTable("Workout1");
        wt.printWorkoutTable("Workout2");
        wt.printWorkoutTable("Workout3");
        wt.printWorkoutTable("Workout4");
        wt.printWorkoutTable("Workout5");
    }


}
/** possibly add no new fragments for adding exercises to workouts
 *  instead just put an add button or option to add exercise to workout and
 *  and if just plain viewing exercises have option to ad exercise to workout
 */