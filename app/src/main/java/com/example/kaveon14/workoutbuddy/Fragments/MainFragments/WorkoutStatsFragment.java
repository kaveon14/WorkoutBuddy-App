package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.WorkoutStatsTable;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.FullWorkoutStatsFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// use floating action button to reset list
import static android.content.Context.SEARCH_SERVICE;
//possily create binary search tree to speedup matching searched item times
public class WorkoutStatsFragment extends Fragment {

    private WorkoutStatsAdapter workoutStatsAdapter;
    private List<SubWorkout> subWorkoutList;
    public static Map<String,List<String>> queriedData;
    private Menu menu;
    private ListView listView;


    public WorkoutStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout_stats, container, false);
        listView = (ListView) root.findViewById(R.id.workoutStats_listView);
        listView.setAdapter(setAdapter());
        setListViewOnClick(listView);
        setSearchViewOnClick();
        return root;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    private void setSearchViewOnClick() {
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                WorkoutStatsTable table = new WorkoutStatsTable(getContext());
                loadSearchedItems(table.searchTable(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setListViewOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showFullWorkoutStatsFragment(subWorkoutList.get(position).getWorkoutData());
            }
        });
    }

    private void showFullWorkoutStatsFragment(List<WorkoutExercise> workoutData) {
        FullWorkoutStatsFragment fw = new FullWorkoutStatsFragment();
        fw.setWorkoutData(workoutData);
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.fullWorkoutStats_fragment,fw)
                .addToBackStack(null)
                .commit();
    }


    private WorkoutStatsAdapter setAdapter(){
        WorkoutStatsTable table = new WorkoutStatsTable(getContext());
        subWorkoutList = table.getCompletedWorkouts();

        workoutStatsAdapter = new WorkoutStatsAdapter(subWorkoutList);
        return new WorkoutStatsAdapter(subWorkoutList);
    }

    public List<SubWorkout> loadSearchedItems(Map<String,List<String>> queriedData) {
        List<SubWorkout> list = new ArrayList<>();
        if(queriedData!=null) {
            List<String> mainWorkoutNames = queriedData.get(DataBaseContract.WorkoutData.COLUMN_MAINWORKOUT);
            List<String> subWorkoutNames = queriedData.get(DataBaseContract.WorkoutData.COLUMN_SUBWORKOUT);
            List<String> dates = queriedData.get(DataBaseContract.WorkoutData.COLUMN_DATE);

            for (int x = 0; x < mainWorkoutNames.size(); x++) {
                String mainWorkoutName = mainWorkoutNames.get(x);
                String subWorkoutName = subWorkoutNames.get(x);
                String date = dates.get(x);

                for (int i = 0; i < subWorkoutList.size(); i++) {
                    SubWorkout subWorkout = subWorkoutList.get(i);
                    String mainName = subWorkout.getMainWorkoutName();
                    String subName = subWorkout.getSubWorkoutName();
                    String dateSubWorkoutList = subWorkout.getDate();

                    if(mainWorkoutName.equals(mainName) && subWorkoutName.equals(subName)
                            && date.equals(dateSubWorkoutList)) {
                        list.add(subWorkout);
                    }
                }
            }
        }
        listView.setAdapter(new WorkoutStatsAdapter(list));
        return list;
    }

    private class WorkoutStatsAdapter extends BaseAdapter {

        private List<SubWorkout> subWorkoutList;

        public WorkoutStatsAdapter(List<SubWorkout> subWorkoutList) {
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
                rowView = inflater.inflate(R.layout.workoutstats_list_item,null);
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

        private void setDateView(View rowView,SubWorkout subWorkout) {//convert date
            TextView textView = (TextView)  rowView.findViewById(R.id.liftingStatsDate_textView);
            String text = "Date -> ";
            String date = getParsedDate(subWorkout.getDate());
            textView.setText(text + date);
        }

        private String getParsedDate(String date) {//length will always be the same
            String year = date.substring(0,date.length()-6);
            String month = date.substring(5,date.length()-3);
            String day = date.substring(date.length()-2);
            return new StringBuilder(month).append("/")
                    .append(day).append("/").append(year).toString();
        }

        private void setMainWorkoutTextView(View rowView,SubWorkout subWorkout) {
            TextView textView = (TextView) rowView.findViewById(R.id.mainWorkout_textView);
            String text = "MainWorkout -> ";
            textView.setText(text+subWorkout.getMainWorkoutName());
        }

        private void setSubWorkoutTextView(View rowView,SubWorkout subWorkout) {
            TextView textView = (TextView) rowView.findViewById(R.id.subWorkout_textView);
            String text = "SubWorkout -> ";
            textView.setText(text + subWorkout.getSubWorkoutName());
        }

        private void setTotalSetsView(View rowView,SubWorkout subWorkout) {
            TextView textView = (TextView) rowView.findViewById(R.id.sets_textView);
            String text = "Total Sets -> ";
            textView.setText(text + subWorkout.getTotalSets());
        }

        private void setTotalRepsView(View rowView,SubWorkout subWorkout) {
            TextView textView = (TextView) rowView.findViewById(R.id.reps_textView);
            String text = "Total Reps -> ";
            textView.setText(text + " " + subWorkout.getTotalReps());
        }

        private void setTotalWeightView(View rowView,SubWorkout subWorkout) {
            TextView textView = (TextView) rowView.findViewById(R.id.weight_textView);
            String text = "Total Weight -> ";
            textView.setText(text +subWorkout.getTotalWeight());
        }
    }
}
