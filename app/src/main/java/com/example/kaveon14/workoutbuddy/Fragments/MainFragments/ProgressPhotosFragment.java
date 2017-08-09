package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kaveon14.workoutbuddy.R;

public class ProgressPhotosFragment extends Fragment {


    public ProgressPhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_progress_photos, container, false);
        setRecycleView(root);
        return root;
    }

    private void setRecycleView(View root) {
        RecyclerView recyclerView =(RecyclerView) root.findViewById(R.id.photoRecycleView);

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

        public RecyclerAdapter() {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.progress_photo_cardview,null);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder,int i) {

        }


        class CustomViewHolder extends RecyclerView.ViewHolder {


            public CustomViewHolder(View rowView) {
                super(rowView);
            }

        }
    }

}
