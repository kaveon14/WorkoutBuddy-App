package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.example.kaveon14.workoutbuddy.Activity.MainActivity;

public class PopupWindowManager {

    private int id;
    private View root;
    private int layout;
    private View popupLayout = null;
    private PopupWindow popupWindow = null;
    private Context context = MainActivity.activity.getBaseContext();


    public PopupWindowManager(View root, int layout, int id) {
        this.root = root;
        this.layout = layout;
        this.id = id;
    }

    public void displayPopupWindow() {
        if(popupLayout == null)
            setPopupLayout();
        if(popupWindow == null)
            createPopupWindow();
        showPopupWindow();
        dimBackground();
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
    }

    private void showPopupWindow() {
        int width =  LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        popupWindow.update(0,0,width,height);
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public View getPopupLayout() {
        return popupLayout;
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
