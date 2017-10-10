package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;
//add checkbox to list view if checked workoutData stored un-check workoutData not stores and load workoutData on click in workout fragment
// in popup menu have start a workout border_accent that brings up the checkboxes and change floating add extra border_accent to submit workoutData
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.kaveon14.workoutbuddy.Activities.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.BlankSWPopupMenu;
import com.example.kaveon14.workoutbuddy.Fragments.Managers.FragmentStackManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;
//add menu to delete or view exercise
public class BlankSubWorkoutFragment extends Fragment {

    private WorkoutAdapter workoutAdapter;
    private List<Exercise> exerciseList;
    private SubWorkout clickedSubWorkout;
    private MainActivity mainActivity;
    private FragmentStackManager fragmentStackManager;

    public BlankSubWorkoutFragment() {
        // Required empty public constructor
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setClickedSubWorkout(SubWorkout clickedSubWorkout) {
        this.clickedSubWorkout = clickedSubWorkout;
    }

    public void setFragmentStackManager(FragmentStackManager fragmentStackManager) {
        this.fragmentStackManager = fragmentStackManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blank_workout, container, false);
        setTextView(root);
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

    @Override
    public void onDetach() {
        super.onDetach();
        resetFloatingActionButton();
    }

    private void resetFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        }
    }

    private FloatingActionButton setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });
        return fab;
    }

    private void showPopupMenu() {
        BlankSWPopupMenu popup = new BlankSWPopupMenu(getView(),getContext());
        popup.setExerciseList(exerciseList);
        popup.setAdapter(workoutAdapter);
        popup.setMainActivity(mainActivity);
        popup.showPopupWindow();
    }

    private void setTextView(View rootView) {
        TextView textView = (TextView) rootView.findViewById(R.id.workoutNameView);
        textView.setText(SubWorkoutFragment.getClickedSubWorkout().getSubWorkoutName());
    }

    private void setListView(View root) {
        ListView listView = (ListView) root.findViewById(R.id.blankWorkout_listView);
        if(workoutAdapter != null) {
            listView.setAdapter(workoutAdapter);
        } else {
            listView.setAdapter(setWorkoutAdapter());
        }
        openWorkoutOnClick(listView);
        if(workoutAdapter.isEmpty()) {
            listView.setEmptyView(root.findViewById(R.id.blankSubWorkoutEmptyListItem));
        }
    }

    private void openWorkoutOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise clickedExercise = exerciseList.get(position);
                openWorkoutFragment(clickedExercise);
                ExerciseFragment.setClickedExercise(clickedExercise);
            }
        });
    }

    private void openWorkoutFragment(Exercise exercise) {
        WorkoutFragment workoutFragment =  new WorkoutFragment();
        workoutFragment.setExercise(exercise);
        fragmentStackManager.addFragmentToStack(workoutFragment,R.id.workout_fragment);
    }

    private WorkoutAdapter setWorkoutAdapter() {
        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(getContext());
        String tableName = subWorkoutTable.getCorrectTableName(clickedSubWorkout.getMainWorkoutName()
                ,clickedSubWorkout.getSubWorkoutName());
        exerciseList = subWorkoutTable.getSubWorkoutExercises(tableName);
        workoutAdapter = new WorkoutAdapter(getContext(),exerciseList);
        return workoutAdapter;
    }

    public static class WorkoutAdapter extends BaseAdapter  {

        private List<Exercise> exerciseList;
        private Context context;
        private TextView exerciseNameView;
        private List<View> rowViews;

        public WorkoutAdapter(Context context,List<Exercise> exercises) {
            this.context = context;
            this.exerciseList = exercises;
        }

        public int getCount() {
            return exerciseList.size();
        }

        public Exercise getItem(int position) {
            return exerciseList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View rowView, ViewGroup viewGroup) {
            Exercise exercise = exerciseList.get(position);
            if (rowView == null) {
                if(rowViews == null) {
                    rowViews = new ArrayList<>();
                }
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.blank_workout_list_item, null);
                rowViews.add(position,rowView);
            }
            setListItemView(rowView,exercise);
            return rowView;
        }

        private void setListItemView(View rowView,Exercise exercise) {
            setExerciseNameTextView(rowView,exercise);
            setExerciseRepsTextView(rowView,exercise);
            setExerciseSets(rowView,exercise);
        }

        private String setExerciseNameTextView(View rowView,Exercise exercise) {
            String text = exercise.getExerciseName();
            exerciseNameView = (TextView) rowView.findViewById(R.id.nameView);
            exerciseNameView.setText("Exercise:"+" "+text);
            return text;
        }

        private String setExerciseRepsTextView(View rowView,Exercise exercise) {
            String text = exercise.getGoalReps();
            TextView repsView = (TextView) rowView.findViewById(R.id.repsView);
            repsView.setText("Reps:"+" "+text);
            return text;
        }

        private String setExerciseSets(View rowView,Exercise exercise) {
            String text = exercise.getGoalSets();
            TextView setsView = (TextView) rowView.findViewById(R.id.setsView);
            setsView.setText("Sets:"+" "+text);
            return text;
        }
    }
}
