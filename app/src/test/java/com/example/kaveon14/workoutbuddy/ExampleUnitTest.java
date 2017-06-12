package com.example.kaveon14.workoutbuddy;

import android.content.Context;

import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseSQLiteHelper;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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
    @Mock
    SubWorkoutTable subWorkoutTable = new SubWorkoutTable(context);
    @Test
    public void setWorkoutTable() {
        subWorkoutTable.printSubWorkoutTable("Workout1");
    }

}