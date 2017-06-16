package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.ExerciseData.COLUMN_EXERCISES;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment.clickedMainWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ExerciseTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankExerciseFragment;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;
//make all poopup window global
public class ExerciseFragment extends Fragment {

    public static Exercise clickedExercise;
    private static List<Exercise> exerciseList;
    private ExerciseFragment exercise_frag = this;
    private boolean fromSubWorkout = false;
    private PopupWindow popupWindow;

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_exercise, container, false);
        setListView(root);
        setFloatingActionButton();
        return root;
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
                Toast.makeText(getContext(),"Add exercise",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setListView(View root) {
        ListView listView = (ListView) root.findViewById(R.id.exercise_listView);
        listView.setAdapter(setExerciseAdapter());
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                exercise_frag.setUpAndShowPopupWindow(root);
                return true;
            }
        });
    }

    private ExerciseAdapter setExerciseAdapter() {
        return new ExerciseAdapter(getExerciseList());
    }

    private Exercise getExercise(int x) {
        List<String> exerciseNames = new ExerciseTable(getContext()).getColumn(COLUMN_EXERCISES);
        return new Exercise(exerciseNames.get(x),null);
    }

    private void showBlankExerciseFragment() {
        BlankExerciseFragment bf = new BlankExerciseFragment();
        getFragmentManager().beginTransaction()
                .hide(exercise_frag)
                .replace(R.id.blankExercise_fragment,bf)
                .addToBackStack(null)
                .commit();
    }

    private PopupWindow setUpAndShowPopupWindow(final View root) {
        int width =  LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        View popupLayout = getPopupLayout(root);
        popupWindow = new PopupWindow(popupLayout,width,height);
        popupWindow.setFocusable(true);
        popupWindow.update(0,0,width,height);
        popupWindow.showAtLocation(root,Gravity.CENTER,0,0);
        dimBackground();

        ListView popupListView = setPopupListView(popupLayout);
        handlePopupEvents(popupListView,popupLayout);
        return popupWindow;
    }

    private View getPopupLayout(View root) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getBaseContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.exercise_popup_layout,(ViewGroup)
                root.findViewById(R.id.exercise_popupWindow));
    }

    private void showPopupButton(View popupLayout) {
        Button btn = (Button) popupLayout.findViewById(R.id.exercisePopupBtn);
        btn.setVisibility(View.VISIBLE);
    }

    private ListView setPopupListView(View popupLayout) {
        ListView listView = (ListView) popupLayout.findViewById(R.id.exercisePopup_listView);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(getMainWorkoutAdapter());
        return listView;
    }

    private void setAndShowPopupEditTextViews(View popupLayout) {
        EditText setsView = (EditText) popupLayout.findViewById(R.id.exercisePopup_setsTextView);
        setsView.setBackgroundColor(Color.WHITE);
        setsView.setVisibility(View.VISIBLE);


        if(setsView.isFocused() && setsView.getText().toString().equalsIgnoreCase("Sets:")) {
            setsView.setText("");
        }

        EditText repsView = (EditText) popupLayout.findViewById(R.id.exercisePopup_repsTextView);
        repsView.setBackgroundColor(Color.WHITE);
        repsView.setVisibility(View.VISIBLE);

        if(repsView.isFocused() && repsView.getText().toString().equalsIgnoreCase("Reps:")) {
            repsView.setText("");
        }
    }

    private void dimBackground() {
        View container = (View) popupWindow.getContentView().getParent();
        WindowManager wm = (WindowManager) getActivity().getBaseContext()
                .getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) container.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        wm.updateViewLayout(container, layoutParams);
    }


    private ArrayAdapter getMainWorkoutAdapter() {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(getContext());
        List<String> list = mainWorkoutTable.getMainWorkoutNames();
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                R.layout.simple_list_item,list);
        return adapter;
    }

    private ArrayAdapter getSubWorkoutAdapter() {
        MainWorkoutTable workoutTable = new MainWorkoutTable(getContext());
        List<String> list = workoutTable.getSubWorkoutNames(clickedMainWorkout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.simple_list_item,list);
        return adapter;
    }

    private void handlePopupEvents(ListView listView, View root) {
        if(!fromSubWorkout) {
            mainWorkoutClicked(listView,root);
        } else {
            listView.setVisibility(View.INVISIBLE);
            showPartialSubWorkoutPopup(root);
        }
    }

    private void mainWorkoutClicked(ListView listView, View root) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedMainWorkout = parent.getItemAtPosition(position).toString();
                listView.setAdapter(getSubWorkoutAdapter());
                subWorkoutClicked(listView, root);
            }
        });
    }

    private void subWorkoutClicked(ListView listView, View root) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetSubWorkoutListViewColors(parent);
                parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                showFullSubWorkoutPopup(listView,root,position);
            }
        });
    }

    private void showFullSubWorkoutPopup(AdapterView<?> parent, View root, int position) {
        String subWorkoutName = parent.getItemAtPosition(position).toString();
        popupButtonClicked(root, clickedExercise, subWorkoutName);
        setAndShowPopupEditTextViews(root);
        showPopupButton(root);
    }

    private void showPartialSubWorkoutPopup(View root) {
        setAndShowPopupEditTextViews(root);
        showPopupButton(root);
        popupButtonClicked(root,clickedExercise,
                SubWorkoutFragment.clickedSubWorkout.getSubWorkoutName());
    }

    private void resetSubWorkoutListViewColors(AdapterView<?> parent) {
        for(int x=0;x<parent.getCount();x++) {
            View view = parent.getChildAt(x);
            view.setBackgroundColor(Color.WHITE);

        }
    }

    private void popupButtonClicked(View root, Exercise exercise, String subWorkoutName) {
        Button btn = (Button) root.findViewById(R.id.exercisePopupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExerciseToSubWorkout(exercise,root,subWorkoutName);
                Toast.makeText(getContext(),"Exercise Successfully Added!",
                        Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    private void addExerciseToSubWorkout(Exercise exercise, View root, String subWorkoutName) {
        exercise.setExerciseReps(getExerciseReps(root));
        exercise.setExerciseSets(getExerciseSets(root));

        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(getContext());
        subWorkoutTable.
                addExerciseToSubWorkout(clickedMainWorkout,subWorkoutName+"_wk",
                        exercise);
    }

    private String getExerciseSets(View popupLayout) {
        EditText setsView = (EditText) popupLayout.findViewById(R.id.exercisePopup_setsTextView);
        return setsView.getText().toString();
    }

    private String getExerciseReps(View popupLayout) {
        EditText repsView = (EditText) popupLayout.findViewById(R.id.exercisePopup_repsTextView);
        return repsView.getText().toString();
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
                    exercise_frag.showBlankExerciseFragment();
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