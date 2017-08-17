package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ProgressPhotoPopupWindows;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.example.kaveon14.workoutbuddy.Fragments.Managers.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.R;

public class ExpandedImagePopup extends PopupWindowManager {

    private Context context;
    private Bitmap image;

    public ExpandedImagePopup(Context context, View root) {
        this.context = context;
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.image_view);
        setPopupViewId(R.id.popupImageViewPopup);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setImageView();
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    private void setImageView() {
        ImageView imageView = (ImageView) popupLayout.findViewById(R.id.expandedImage);
        imageView.setImageBitmap(image);
    }
}
