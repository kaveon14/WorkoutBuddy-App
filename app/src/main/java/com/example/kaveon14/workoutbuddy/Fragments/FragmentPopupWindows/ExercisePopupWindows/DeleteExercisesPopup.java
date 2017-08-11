package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindows;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;

// ViewCustomExercisesPopup just change adapter not create popup class
public class DeleteExercisesPopup extends PopupWindowManager {//dont create popup just

    private ArrayAdapter customExerciseAdapter;
    private List<Exercise> customExerciseList;
    private ExerciseFragment exercise_frag;

    public DeleteExercisesPopup(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.deletesubworkout_popup_layout);
        setPopupViewId(R.id.deleteSubWorkoutPopup);
    }

    public void setExerciseFragment(ExerciseFragment exercise_frag) {
        this.exercise_frag = exercise_frag;
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setCustomExerciseListView();
    }

    public void setCustomExerciseList(List<Exercise> customExerciseList) {
        this.customExerciseList = customExerciseList;
    }

    private void setCustomExerciseListView() {
        List<String> names = new ArrayList<>();
        for(int x=0;x<customExerciseList.size();x++) {
            names.add(customExerciseList.get(x).getExerciseName());
        }
        customExerciseAdapter = new ArrayAdapter(context,R.layout.simple_list_item,names);
        ListView listView = (ListView) popupLayout.findViewById(R.id.deleteSubWorkoutPopup_listView);
        listView.setAdapter(customExerciseAdapter);
        listView.setBackgroundColor(Color.WHITE);
        listViewOnClick(listView);
        if(customExerciseAdapter.isEmpty()) {
            listView.setEmptyView(popupLayout.findViewById(R.id.emptyList));
        }
    }

    private void listViewOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetExerciseListViewColors(parent);
                parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                Exercise exercise = customExerciseList.get(position);
                setDeleteButton(exercise);
            }
        });
    }

    private void setDeleteButton(Exercise exercise) {//test
        Button btn = (Button) popupLayout.findViewById(R.id.button);
        btn.setText("Delete Exercise");
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
        customExerciseList.remove(exercise);
        customExerciseAdapter.notifyDataSetChanged();

        ExerciseTable exerciseTable = new ExerciseTable(context);
        exerciseTable.deleteExercise(exercise);

        exercise_frag.deleteExerciseFromList(exercise);
    }

    private void resetExerciseListViewColors(AdapterView<?> parent) {
        for(int x=0;x<parent.getCount();x++) {
            View view = parent.getChildAt(x);
            view.setBackgroundColor(Color.WHITE);
        }
    }
}
