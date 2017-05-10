package com.example.kaveon14.workoutbuddy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.kaveon14.workoutbuddy.DataBase.DataBaseSQLiteHelper;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutTable;
import com.example.kaveon14.workoutbuddy.FragmentTextHandling.ExerciseNames;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;





/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Mock Context context;
    @Mock DataBaseSQLiteHelper dataBaseSQLiteHelper = new DataBaseSQLiteHelper(context);
    @Mock WorkoutTable workoutTable = new WorkoutTable(context);
    @Test
    public void setWorkoutTable() {
        workoutTable.printWorkoutTable("Workout1");
    }

}