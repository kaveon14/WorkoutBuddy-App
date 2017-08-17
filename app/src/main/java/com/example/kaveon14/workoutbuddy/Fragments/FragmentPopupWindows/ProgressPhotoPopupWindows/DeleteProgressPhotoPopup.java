package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ProgressPhotoPopupWindows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.kaveon14.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;

import java.util.List;

public class DeleteProgressPhotoPopup extends PopupWindowManager {

    private int position;
    private RecyclerView recyclerView;
    private List<ProgressPhoto> progressPhotoList;

    public DeleteProgressPhotoPopup(View root, Context context)  {
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

    public void setProgressPhotoList(List<ProgressPhoto> progressPhotoList) {
        this.progressPhotoList = progressPhotoList;
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
}
