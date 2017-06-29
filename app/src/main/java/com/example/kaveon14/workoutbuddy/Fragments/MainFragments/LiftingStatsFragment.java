package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.R;

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

    private abstract class LiftingStatsAdapter extends BaseAdapter {
        //just abstract until workout object finished
      public int getCount() {
          return 0;
      }

      public long getItemId(int i) {
          return i;
      }

      public View getView(int position,View rowView,ViewGroup viewGroup) {


          return null;
      }







    }
}
