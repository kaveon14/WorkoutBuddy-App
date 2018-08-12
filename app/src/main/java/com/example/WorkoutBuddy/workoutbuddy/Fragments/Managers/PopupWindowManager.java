package com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public abstract class PopupWindowManager  {

    private int id;
    private View root;
    protected int layout;
    protected View popupLayout = null;
    protected PopupWindow popupWindow = null;
    protected Context context;

    protected void displayPopupWindow() {
        if(popupLayout == null)
            setPopupLayout();
        if(popupWindow == null)
            createPopupWindow();
        showPopupWindow();
        dimBackground();
    }

    protected void setPopupLayout(int layout) {
        this.layout = layout;
    }

    protected void setPopupViewId(int id) {
        this.id = id;
    }

    protected void setRootView(View root) {
        this.root = root;
    }

    protected void setWindowManagerContext(Context context) {
        this.context = context;
    }

    private void setPopupLayout() {
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupLayout = inflater.inflate(layout,(ViewGroup) root.findViewById(id));
    }

    private void createPopupWindow() {
        int width =  LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        popupWindow = new PopupWindow(popupLayout,width,height);
        popupWindow.setFocusable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void showPopupWindow() {
        int width =  LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        popupWindow.update(0,0,width,height);
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
    }


    protected View getRootView() {
        return root;
    }

    private void dimBackground() {
        View container = (View) popupLayout.getParent();
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) container.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        wm.updateViewLayout(container, layoutParams);
    }
}
