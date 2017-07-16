package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;
// TODO temporary switching focus to the act of doing a workout and storing the results
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.DataBase.Data.Exercise;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.LiftingStatsTable;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.FullWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;

import java.util.List;

public class LiftingStatsFragment extends Fragment {//change name

    private LiftingStatsAdapter liftingStatsAdapter;
    private List<SubWorkout> subWorkoutList;

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
        ListView listView = (ListView) root.findViewById(R.id.liftStats_listView);
        listView.setAdapter(setAdapter());//on click view full workout stats
        setListViewOnClick(listView);
        return root;
    }

    private void setListViewOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //need list view to show full workout
                /*
                Ex Name
                load sets dynamically (max 10)
                set 1 reps/ weight lbs or kgs
                set 2 same
                Ex Name
                * */
                //need exercise list
                showFullWorkoutFragment(subWorkoutList.get(position).getExerciseList());
            }
        });
    }

    private void showFullWorkoutFragment(List<Exercise> exerciseList) {
        FullWorkoutFragment fw = new FullWorkoutFragment();
        fw.setExerciseList(exerciseList);
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.fullWorkout_fragment,fw)
                .addToBackStack(null)
                .commit();
    }

    private LiftingStatsAdapter setAdapter(){
        LiftingStatsTable table = new LiftingStatsTable(getContext());
        subWorkoutList = table.getCompletedWorkouts();

        liftingStatsAdapter = new LiftingStatsAdapter(subWorkoutList);
        return new LiftingStatsAdapter(subWorkoutList);
    }

    private class LiftingStatsAdapter extends BaseAdapter {

        private List<SubWorkout> subWorkoutList;

        public LiftingStatsAdapter(List<SubWorkout> subWorkoutList) {
            this.subWorkoutList = subWorkoutList;
        }

        public int getCount() {
          return subWorkoutList.size();
      }

        public SubWorkout getItem(int i) {
            return subWorkoutList.get(i);
        }

        public long getItemId(int i) {
            return i;
        }

        public View getView(int position,View rowView,ViewGroup viewGroup) {
            SubWorkout subWorkout = subWorkoutList.get(position);//pass in a
            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.liftingstats_list_item,null);
            }
            setListItemView(rowView,subWorkout);
            return rowView;
        }

        private void setListItemView(View rowView,SubWorkout subWorkout) {
            setDateView(rowView,subWorkout);
            setMainWorkoutTextView(rowView,subWorkout);
            setSubWorkoutTextView(rowView,subWorkout);
            setTotalSetsView(rowView,subWorkout);
            setTotalRepsView(rowView,subWorkout);
            setTotalWeightView(rowView,subWorkout);
        }

        private void setDateView(View rowView,SubWorkout subWorkout) {
            TextView textView = (TextView)  rowView.findViewById(R.id.liftingStatsDate_textView);
            String text = "Date -> ";
            textView.setText(text + subWorkout.getDate());
        }

        private void setMainWorkoutTextView(View rowView,SubWorkout subWorkout) {//done
            TextView textView = (TextView) rowView.findViewById(R.id.mainWorkout_textView);
            String text = "MainWorkout -> ";
            textView.setText(text+subWorkout.getMainWorkoutName());
        }

        private void setSubWorkoutTextView(View rowView,SubWorkout subWorkout) {//done
            TextView textView = (TextView) rowView.findViewById(R.id.subWorkout_textView);
            String text = "SubWorkout -> ";
            textView.setText(text + subWorkout.getSubWorkoutName());
        }

        private void setTotalSetsView(View rowView,SubWorkout subWorkout) {//done
            TextView textView = (TextView) rowView.findViewById(R.id.sets_textView);
            String text = "Total Sets -> ";
            textView.setText(text + subWorkout.getTotalSets());
        }

        private void setTotalRepsView(View rowView,SubWorkout subWorkout) {//done
            System.out.println("please: "+subWorkout.getTotalReps());
            TextView textView = (TextView) rowView.findViewById(R.id.reps_textView);
            String text = "Total Reps -> ";
            textView.setText(text + " " + subWorkout.getTotalReps());
        }

        private void setTotalWeightView(View rowView,SubWorkout subWorkout) {//done
            TextView textView = (TextView) rowView.findViewById(R.id.weight_textView);
            String text = "Total Weight -> ";
            textView.setText(text +subWorkout.getTotalWeight());
        }
    }
}
