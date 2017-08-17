package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutStatsPopupWindows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.WorkoutStatsTable;
import com.example.kaveon14.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class DeleteWorkoutStatsPopup extends PopupWindowManager {

    private int position;
    private RecyclerView recyclerView;
    private List<SubWorkout> subWorkoutList;

    public DeleteWorkoutStatsPopup(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.yes_no_popup_layout);
        setPopupViewId(R.id.yesNo_popupWindow);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setSubWorkoutList(List<SubWorkout> subWorkoutList) {
        this.subWorkoutList = subWorkoutList;
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setYesPopupButton();
        setNoPopupButton();
        setTextView();
    }

    private void setYesPopupButton() {
        Button btn = (Button) popupWindow.getContentView().findViewById(R.id.YES_POPUP_BTN);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWorkoutStats();
                deleteRowView();
                Toast.makeText(context,"Workout Stats Deleted!",
                        Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    private void setNoPopupButton() {
        Button btn = (Button) popupWindow.getContentView().findViewById(R.id.NO_POPUP_BTN);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void setTextView() {
        String message = "   Do you want to DELETE the clicked progress photo??";
        TextView textView = (TextView) popupLayout.findViewById(R.id.POPUP_TEXT_VIEW);
        textView.setText(message);
    }

    private void deleteRowView() {
        subWorkoutList.remove(position);
        recyclerView.getAdapter().notifyItemRemoved(position);
    }

    private void deleteWorkoutStats() {
        String[] date = new String[] {
                subWorkoutList.get(position).getDate()
        };
        WorkoutStatsTable table = new WorkoutStatsTable(context);
        table.deleteRow(date);
    }
}
