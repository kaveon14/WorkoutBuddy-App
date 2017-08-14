package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;

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
        TextView textView = (TextView) popupLayout.findViewById(R.id.bodystatsPopup_textView);
        textView.setText(message);
    }

    private void setYesPopupButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.bodyStats_yes_popupBtn);
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
        Button btn = (Button) popupLayout.findViewById(R.id.bodyStats_no_popupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
