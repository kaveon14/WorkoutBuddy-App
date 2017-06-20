package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISE_DESCRIPTION;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ExercisePopupWindowHandler;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExerciseFragment extends Fragment {

    public static Exercise clickedExercise;
    public static List<Exercise> exerciseList;
    private boolean fromSubWorkout = false;

    public ExerciseFragment() {

    }

    public void addExerciseFromSubWorkout(boolean fromSubWorkout) {
        this.fromSubWorkout = fromSubWorkout;
    }

    public List<Exercise> getExerciseList() {
        if(exerciseList==null) {
            setExerciseList();
        }
        return exerciseList;
    }

    private void setExerciseList() {
        exerciseList = new ArrayList<>();
        int amountOfExercise = new ExerciseTable(getContext()).getColumn(COLUMN_EXERCISES).size();
        for(int x=0;x<amountOfExercise;x++) {
            exerciseList.add(getExercise(x));
        }
        sortExerciseListByName();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exercise, container, false);
        setListView(root);
        setFloatingActionButton();
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            setFloatingActionButton();
        }
    }

    private FloatingActionButton setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setImageResource(R.drawable.ic_menu_manage);
            handleFloatingActionButtonEvents(fab);
        }
        return fab;
    }

    private void handleFloatingActionButtonEvents(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExercisePopupWindowHandler windowHandler =
                        new ExercisePopupWindowHandler(getContext(),fromSubWorkout);
                windowHandler.setMainExercisePopupWindow(fab.getRootView());
            }
        });
    }

    private void setListView(View root) {
        ListView listView = (ListView) root.findViewById(R.id.exercise_listView);
        listView.setAdapter(setExerciseAdapter());
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ExercisePopupWindowHandler windowHandler
                        = new ExercisePopupWindowHandler(getContext(),fromSubWorkout);
                windowHandler.showExerciseToWorkoutPopupWindow(root,R.layout.exercise_popup_layout,
                        R.id.exercise_popupWindow);
                return true;
            }
        });
    }

    private void sortExerciseListByName() {
        Collections.sort(exerciseList, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise exercise1, Exercise exercis2) {
                return exercise1.getExerciseName().compareTo(exercis2.getExerciseName());
            }
        });
    }

    private ExerciseAdapter setExerciseAdapter() {
        return new ExerciseAdapter(getExerciseList());
    }

    private Exercise getExercise(int x) {
        ExerciseTable exerciseTable = new ExerciseTable(getContext());

        List<String> exerciseNames = exerciseTable.getColumn(COLUMN_EXERCISES);
        List<String> exerciseDescriptions =
                 exerciseTable.getColumn(COLUMN_EXERCISE_DESCRIPTION);
        Exercise exercise = new Exercise(exerciseNames.get(x),exerciseDescriptions.get(x));
        exercise.setExerciseImage(exerciseTable.getExerciseImage(exercise));
        return exercise;
    }

    private void showBlankExerciseFragment() {
        BlankExerciseFragment bf = new BlankExerciseFragment();
        getFragmentManager().beginTransaction()
                .hide(this)
                .replace(R.id.blankExercise_fragment,bf)
                .addToBackStack(null)
                .commit();
    }

    private class ExerciseAdapter extends BaseAdapter {

        private List<Exercise> exerciseList;

        public ExerciseAdapter(List<Exercise> exercises) {
            exerciseList = exercises;
        }

        @Override
        public int getCount() {
            return exerciseList.size();
        }

        @Override
        public Exercise getItem(int i) {//may need work
            return exerciseList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.simple_list_item,viewGroup,false);
            }
            final Exercise exercise = getItem(i);
            setTextView(exercise,rowView);
            exerciseClicked(rowView,exercise);
            exerciseLongClicked(rowView,exercise);
            return rowView;
        }

        private void setTextView(Exercise exercise,View rowView) {
            TextView exercseName = (TextView) rowView.findViewById(R.id.exerciseList_textView);
            exercseName.setText(exercise.getExerciseName());
        }

        private void exerciseClicked(View rowView,Exercise exercise) {
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExerciseFragment.clickedExercise = exercise;
                    showBlankExerciseFragment();
                }
            });
        }

        private void exerciseLongClicked(View rowView,Exercise exercise) {
            rowView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ExerciseFragment.clickedExercise = exercise;
                    return false;
                }
            });
        }
    }
}
//no need to change the custom image name because it is tied and retrieved by an exercise object