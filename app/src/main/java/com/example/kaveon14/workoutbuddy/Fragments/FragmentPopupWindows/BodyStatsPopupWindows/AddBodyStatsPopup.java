package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;

public class AddBodyStatsPopup extends PopupWindowManager {

    private MainActivity mainActivity;

    public AddBodyStatsPopup(View root, Context context) {
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

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private void setTextView() {
        String message = "      Would you like to add new body workout data??";
        TextView textView = (TextView) popupLayout.findViewById(R.id.bodystatsPopup_textView);
        textView.setText(message);
    }

    private void setYesPopupButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.bodyStats_yes_popupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.showBlankBodyStatsFragment();
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
