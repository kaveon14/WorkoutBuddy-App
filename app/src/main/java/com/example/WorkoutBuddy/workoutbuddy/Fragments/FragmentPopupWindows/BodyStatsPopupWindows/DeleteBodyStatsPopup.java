package com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.Body;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.BodyTable;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.WorkoutBuddy.workoutbuddy.R;
import java.util.List;

public class DeleteBodyStatsPopup extends PopupWindowManager {

    private int position;
    private RecyclerView recyclerView;
    private List<Body> bodyStats;

    public DeleteBodyStatsPopup(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.yes_no_popup_layout);
        setPopupViewId(R.id.yesNo_popupWindow);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBodyStatsList(List<Body> bodyStats) {
        this.bodyStats = bodyStats;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
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
                deleteBodyStatsRow();
                Toast.makeText(context,"Body Stats Data Deleted!",Toast.LENGTH_SHORT).show();
                deleteRowView();
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
        String message = "   Do you want to DELETE the clicked body stats'??";
        TextView textView = (TextView) popupLayout.findViewById(R.id.POPUP_TEXT_VIEW);
        textView.setText(message);
    }

    private void deleteRowView() {
        bodyStats.remove(position);
        recyclerView.getAdapter().notifyItemRemoved(position);
    }

    private void deleteBodyStatsRow() {
        String[] date = new String[] {
                bodyStats.get(position).getStringDate()
        };
        BodyTable bodyTable = new BodyTable(context);
        bodyTable.deleteRow(date);
    }
}
