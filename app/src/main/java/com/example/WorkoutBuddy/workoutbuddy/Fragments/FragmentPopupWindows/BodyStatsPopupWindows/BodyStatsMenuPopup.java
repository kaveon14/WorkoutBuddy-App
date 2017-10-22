package com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.Body;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.WorkoutBuddy.workoutbuddy.R;

import java.util.List;

public class BodyStatsMenuPopup extends PopupWindowManager {

    private List<Body> bodyList;
    private RecyclerView recyclerView;

    public BodyStatsMenuPopup(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.yes_no_popup_layout);
        setPopupViewId(R.id.subWorkout_popupWindow);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setTextView();
        setYesPopupButton();
        setNoPopupButton();
    }

    public void setBodyList(List<Body> bodyList) {
        this.bodyList = bodyList;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    private void setTextView() {
        String message = "Would you like to add new body workout data??";
        TextView textView = (TextView) popupLayout.findViewById(R.id.POPUP_TEXT_VIEW);
        textView.setText(message);
    }

    private void setYesPopupButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.YES_POPUP_BTN);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageBodyStatsPopup popup = new ManageBodyStatsPopup(getRootView(),context);
                popup.isUpdatingRow(false);
                popup.setBodyList(bodyList);
                popup.setRecyclerView(recyclerView);
                popup.showPopupWindow();
                popupWindow.dismiss();
            }
        });
    }

    private void setNoPopupButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.NO_POPUP_BTN);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
