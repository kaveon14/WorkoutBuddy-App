package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;
// TODO put in different package
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.SubWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.ExerciseFragment.clickedExercise;
import static com.example.kaveon14.workoutbuddy.Fragments.MainFragments.MainWorkoutFragment.clickedMainWorkout;

public class ExercisePopupWindowHandler {

    private Context context;
    private boolean fromSubWorkout;

    public ExercisePopupWindowHandler(Context context, boolean fromSubWorkout) {
        this.context = context;
        this.fromSubWorkout = fromSubWorkout;
    }

    protected void setMainExercisePopupWindow(View root) {
        PopupWindow pw = showPopupWindow(root,R.layout.popup_window,R.id.popupWin);
        addExerciseBtn(pw.getContentView(),pw);
    }

    private void addExerciseBtn(View popupLayout, PopupWindow popupWindow) {
        Button btn = (Button) popupLayout.findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.showAddExercisePopupWindow();
                popupWindow.dismiss();
            }
        });
    }

    protected void showExerciseToWorkoutPopupWindow(View root, int layout, int id) {
        View popupLayout = showPopupWindow(root,layout,id).getContentView();
        ListView popupListView = setPopupListView(popupLayout);
        handlePopupEvents(popupListView,popupLayout);
    }

    private ListView setPopupListView(View popupLayout) {
        ListView listView = (ListView) popupLayout.findViewById(R.id.exercisePopup_listView);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(getMainWorkoutAdapter());
        return listView;
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

    private void showPopupButton(View popupLayout) {
        Button btn = (Button) popupLayout.findViewById(R.id.exercisePopupBtn);
        btn.setVisibility(View.VISIBLE);
    }


    private void setAndShowPopupEditTextViews(View popupLayout) {
        EditText setsView = (EditText) popupLayout.findViewById(R.id.exercisePopup_setsTextView);
        setsView.setBackgroundColor(Color.WHITE);
        setsView.setVisibility(View.VISIBLE);


        // TODO make these on click listeners in different fuction
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

    private void popupButtonClicked(View root, Exercise exercise, String subWorkoutName) {
        Button btn = (Button) root.findViewById(R.id.exercisePopupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExerciseToSubWorkout(exercise,root,subWorkoutName);
                Toast.makeText(context,"Exercise Successfully Added!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addExerciseToSubWorkout(Exercise exercise, View root, String subWorkoutName) {//checked
        exercise.setExerciseReps(getExerciseReps(root));
        exercise.setExerciseSets(getExerciseSets(root));

        SubWorkoutTable subWorkoutTable = new SubWorkoutTable(context);
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

    private ArrayAdapter getMainWorkoutAdapter() {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(context);
        List<String> list = mainWorkoutTable.getMainWorkoutNames();
        ArrayAdapter adapter = new ArrayAdapter<>(context,
                R.layout.simple_list_item,list);
        return adapter;
    }

    private ArrayAdapter getSubWorkoutAdapter() {
        MainWorkoutTable workoutTable = new MainWorkoutTable(context);
        List<String> list = workoutTable.getSubWorkoutNames(clickedMainWorkout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.simple_list_item,list);
        return adapter;
    }

    private void resetSubWorkoutListViewColors(AdapterView<?> parent) {
        for(int x=0;x<parent.getCount();x++) {
            View view = parent.getChildAt(x);
            view.setBackgroundColor(Color.WHITE);
        }
    }

    private PopupWindow showPopupWindow(View root,int layout,int id) {
        int width =  LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        View popupLayout = getPopupLayout(root,layout,id);
        PopupWindow popupWindow = new PopupWindow(popupLayout,width,height);
        popupWindow.setFocusable(true);
        popupWindow.update(0,0,width,height);
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
        dimBackground(popupWindow);
        return popupWindow;
    }

    private View getPopupLayout(View root,int layout,int id) {
        LayoutInflater inflater = (LayoutInflater) MainActivity.activity.getBaseContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(layout,(ViewGroup)
                root.findViewById(id));
    }

    private void dimBackground(PopupWindow popupWindow) {
        View container = (View) popupWindow.getContentView().getParent();
        WindowManager wm = (WindowManager) MainActivity.activity.getBaseContext()
                .getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) container.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        wm.updateViewLayout(container, layoutParams);
    }
}