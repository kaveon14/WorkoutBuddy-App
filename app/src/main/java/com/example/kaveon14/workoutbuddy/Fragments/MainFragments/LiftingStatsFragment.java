package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.R;

import java.util.List;

public class LiftingStatsFragment extends Fragment {

    public LiftingStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lifting_stats, container, false);

        return root;
    }

    private class LiftingStatsAdapter extends BaseAdapter {//this

        //needs to show mainWorkout, subWorkout,date logged and total weight,reps,and sets
        // on click allow partial editing of an already completed workout
        public LiftingStatsAdapter() {
        }

        public int getCount() {
          return 0;
      }

        public long getItemId(int i) {
          return i;
      }

        public String getItem(int i) {
            return null;
        }

        public View getView(int position,View rowView,ViewGroup viewGroup) {

            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(null,null);//first one null until view created
            }
          return rowView;
        }
    }
}
