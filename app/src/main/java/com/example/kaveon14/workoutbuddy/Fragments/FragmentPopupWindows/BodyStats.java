package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.BodyStatsFragment;
import com.example.kaveon14.workoutbuddy.R;

public class BodyStats extends PopupWindowManager {

    public BodyStats(View root) {
        setRootView(root);
        setPopupLayout(R.layout.bodystats_popup_layout);
        setPopupViewId(R.id.bodyStats_popupWindow);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setTextView();
        setYesPopupButton();
        setNoPopupButton();
    }

    private void setTextView() {
        String message = "      Would you like to add new body data??";
        TextView textView = (TextView) popupLayout.findViewById(R.id.bodystatsPopup_textView);
        textView.setText(message);
    }

    private void setYesPopupButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.bodyStats_yes_popupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BodyStatsFragment.bodyStatsFragment.showBlankBodyStatsfragment();
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
