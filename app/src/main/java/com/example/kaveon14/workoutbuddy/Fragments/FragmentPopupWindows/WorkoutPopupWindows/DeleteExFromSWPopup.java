package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows;
//dont allow in default default workouts
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankSubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;

public class DeleteExFromSWPopup extends PopupWindowManager {

    private List<Exercise> exerciseList;
    private ArrayAdapter adapter;
    private  List<String> exerciseNames;
    private BlankSubWorkoutFragment.WorkoutAdapter workoutAdapter;

    public DeleteExFromSWPopup(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.view_exercise_popup);
        setPopupViewId(R.id.viewExercisesPopup);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setListView();
    }

    public void setWorkoutAdapter(BlankSubWorkoutFragment.WorkoutAdapter workoutAdapter) {
        this.workoutAdapter = workoutAdapter;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    private void setListView() {
        exerciseNames = new ArrayList<>(10);
        for(int x=0;x<exerciseList.size();x++) {
            exerciseNames.add(exerciseList.get(x).getExerciseName());
        }
        adapter = new ArrayAdapter(context,R.layout.simple_list_item,exerciseNames);
        ListView listView = (ListView) popupLayout.findViewById(R.id.exerciseListView_popup);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(adapter);
        listViewOnClick(listView);
    }

    private void listViewOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetExerciseListViewColors(parent);
                parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                Exercise exercise = exerciseList.get(position);
                setDeleteExerciseButton(exercise);
            }
        });
    }

    private void resetExerciseListViewColors(AdapterView<?> parent) {
        for(int x=0;x<parent.getCount();x++) {
            View view = parent.getChildAt(x);
            view.setBackgroundColor(Color.WHITE);
        }
    }

    private void setDeleteExerciseButton(Exercise exercise) {
        Button btn = (Button) popupLayout.findViewById(R.id.button2);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteExercise(exercise);
                Toast.makeText(context,"Exercise Successfully Deleted!"
                        ,Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    private void deleteExercise(Exercise exercise) {
        exerciseNames.remove(exercise.getExerciseName());
        adapter.notifyDataSetChanged();

        exerciseList.remove(exercise);
        workoutAdapter.notifyDataSetChanged();

        SubWorkoutTable subWorkoutTable  = new SubWorkoutTable(context);
        subWorkoutTable.deleteExerciseFromSubWorkout(exercise
                , SubWorkoutFragment.getClickedSubWorkout().getSubWorkoutName());
    }
}
