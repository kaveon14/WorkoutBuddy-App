package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.ProgressPhoto;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.ProgressPhotosTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.ProgressPhotoPopupWindows.ExpandedImagePopup;
import com.example.kaveon14.workoutbuddy.R;

import java.util.List;

import static com.example.kaveon14.workoutbuddy.Activity.MainActivity.REQUEST_IMAGE_CAPTURE;

public class ProgressPhotosFragment extends Fragment {


    private MainActivity mainActivity;
    private View root;
    private List<ProgressPhoto> progressPhotos;
    private ProgressPhotoAdapter progressPhotoAdapter;

    public ProgressPhotosFragment() {
        // Required empty public constructor
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_progress_photos, container, false);
        setRecycleView(root);
        setFloatingActionButton();
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        resetFloatingActionButton();
    }

    private void resetFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        }
    }

    private FloatingActionButton setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setImageResource(R.drawable.ic_menu_camera);
            handleFloatingActionButtonEvents(fab);
        }
        return fab;
    }

    private void handleFloatingActionButtonEvents(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openCamera();
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        mainActivity.startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }

    private void showExapndedImagePopup(Bitmap image) {
        ExpandedImagePopup popup = new ExpandedImagePopup(getContext(),root);
        popup.setImage(image);
        popup.showPopupWindow();
    }

    private void setRecycleView(View root) {
        ProgressPhotosTable table = new ProgressPhotosTable(getContext());
        progressPhotos = table.getProgressPhotos();
        progressPhotoAdapter = new ProgressPhotoAdapter(progressPhotos);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.photoRecycleView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemViewCacheSize(12);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(progressPhotoAdapter);
    }

    public void addPhotoToList(ProgressPhoto photo) {
        progressPhotos.add(0,photo);
        progressPhotoAdapter.notifyItemInserted(0);
    }

    public class ProgressPhotoAdapter extends RecyclerView.Adapter<ProgressPhotoAdapter.CustomViewHolder> {

        List<ProgressPhoto> progressPhotoList;

        public ProgressPhotoAdapter(List<ProgressPhoto> progressPhotoList) {
            this.progressPhotoList = progressPhotoList;
        }

        @Override
        public int getItemCount() {
            return (null != progressPhotoList ? progressPhotoList.size() : 0);
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.progress_photo_cardview,null);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder,int i) {
            ProgressPhoto progressPhoto = progressPhotoList.get(i);
            customViewHolder.dateView.setText(progressPhoto.getDate());
            customViewHolder.imageView.setImageBitmap(progressPhoto.getProgressPhoto());
        }


        class CustomViewHolder extends RecyclerView.ViewHolder {

            protected TextView dateView;
            protected ImageView imageView;

            public CustomViewHolder(View rowView) {
                super(rowView);
                dateView = (TextView) rowView.findViewById(R.id.progressPhotoDateView);
                imageView = (ImageView) rowView.findViewById(R.id.progressPhotoImageView);
                expandedImage(imageView);
            }

            private void expandedImage(ImageView imageView) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = getLayoutPosition();
                        Bitmap image = progressPhotoList.get(i).getProgressPhoto();
                        showExapndedImagePopup(image);
                    }
                });
            }
        }
    }

}
