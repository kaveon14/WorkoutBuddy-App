package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

// ViewCustomExercisesPopup just change adapter not create popup class
public class DeleteExercisesPopup extends PopupWindowManager {//dont create popup just

    private ExerciseFragment.ExerciseAdapter customExerciseAdapter;
    private List<Exercise> customExerciseList;

    public DeleteExercisesPopup(View root) {
        setRootView(root);
        setPopupLayout(R.layout.deletesubworkout_popup_layout);
        setPopupViewId(R.id.deleteSubWorkoutPopup);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setCustomExerciseListView();
    }

    public void setCustomExerciseAdapter(ExerciseFragment.ExerciseAdapter customExerciseAdapter) {
        this.customExerciseAdapter = customExerciseAdapter;
    }

    public void setCustomExerciseList(List<Exercise> customExerciseList) {
        this.customExerciseList = customExerciseList;
    }

    private void setCustomExerciseListView() {
        ListView listView = (ListView) popupLayout.findViewById(R.id.deleteSubWorkoutPopup_listView);
        listView.setAdapter(customExerciseAdapter);
        listView.setBackgroundColor(Color.WHITE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetSubWorkoutListViewColors(parent);
                parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);

                Exercise exercise = customExerciseList.get(position);
                setDeleteButton(exercise);
            }
        });
    }

    private void setDeleteButton(Exercise exercise) {
        Button btn = (Button) popupLayout.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMainWorkout(exercise);
                Toast.makeText(context,"Exercise Successfully Deleted!"
                        ,Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    private void deleteMainWorkout(Exercise exercise) {
        customExerciseList.remove(exercise);
        customExerciseAdapter.notifyDataSetChanged();

        ExerciseTable exerciseTable = new ExerciseTable(context);
        exerciseTable.deleteExercise(exercise);
    }

    private void resetSubWorkoutListViewColors(AdapterView<?> parent) {
        for(int x=0;x<parent.getCount();x++) {
            View view = parent.getChildAt(x);
            view.setBackgroundColor(Color.WHITE);
        }
    }
}
