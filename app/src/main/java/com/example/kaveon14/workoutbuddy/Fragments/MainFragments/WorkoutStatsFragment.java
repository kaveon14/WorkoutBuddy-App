package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.kaveon14.workoutbuddy.DataBase.Data.SubWorkout;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.Data.WorkoutExercise;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.WorkoutStatsTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutStatsPopupWindows.DeleteWorkoutStatsPopup;
import com.example.kaveon14.workoutbuddy.Fragments.Managers.FragmentStackManager;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.FullWorkoutStatsFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// use floating action button to reset list
import static android.content.Context.SEARCH_SERVICE;
//fix search bugs
public class WorkoutStatsFragment extends Fragment {

    private Menu menu;
    private View root;
    private RecyclerView recyclerView;
    private FragmentStackManager fragmentStackManager;
    private RecyclerAdapter recyclerAdapter;
    private List<SubWorkout> subWorkoutList;

    public WorkoutStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_workout_stats, container, false);
        new MyAsyncTask().execute(subWorkoutList);
        setSearchViewOnClick();
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab !=  null) {
            fab.setVisibility(View.INVISIBLE);
        }
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

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setFragmentStackManager(FragmentStackManager fragmentStackManager) {
        this.fragmentStackManager = fragmentStackManager;
    }

    private void setRecycleView(View root, RecyclerAdapter adapter) {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView = (RecyclerView) root.findViewById(R.id.workoutStatsRecycleView);
        recyclerView.setItemViewCacheSize(12);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if (subWorkoutList.size()==0) {
            TextView textView = (TextView) root.findViewById(R.id.noWorkoutStats);
            textView.setVisibility(View.VISIBLE);
        }

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

    private void showFullWorkoutStatsFragment(List<WorkoutExercise> workoutData) {
        FullWorkoutStatsFragment fw = new FullWorkoutStatsFragment();
        fw.setWorkoutData(workoutData);
        fragmentStackManager.addFragmentToStack(fw,R.id.fullWorkoutStats_fragment);
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
        recyclerView.setAdapter(new RecyclerAdapter(list));
        return list;
    }

    private void showDeleteWorkoutStatsPopup(int position) {
        DeleteWorkoutStatsPopup popup = new DeleteWorkoutStatsPopup(root,getContext());
        popup.setPosition(position);
        popup.setRecyclerView(recyclerView);
        popup.setSubWorkoutList(subWorkoutList);
        popup.showPopupWindow();
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

        private List<SubWorkout> subWorkoutList;

        public RecyclerAdapter(List<SubWorkout> subWorkoutList) {
            this.subWorkoutList = subWorkoutList;
        }

        @Override
        public int getItemCount() {
            return (null != subWorkoutList ? subWorkoutList.size() : 0);
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.workout_card_view,null);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder,int i) {
            SubWorkout subWorkout = subWorkoutList.get(i);
            customViewHolder.dateView.setText(customViewHolder.dateView.getText()+" "+
                    subWorkout.getDate());
            customViewHolder.mainWorkoutView.setText(customViewHolder.mainWorkoutView.getText() + " " +
                    subWorkout.getMainWorkoutName());
            customViewHolder.subWorkoutView.setText(customViewHolder.subWorkoutView.getText()+" "+
            subWorkout.getSubWorkoutName());
            customViewHolder.setsView.setText(customViewHolder.setsView.getText()+" "+
            subWorkout.getTotalSets());
            customViewHolder.repsView.setText(customViewHolder.repsView.getText()+" "+
            subWorkout.getTotalReps());
            customViewHolder.weightView.setText(customViewHolder.weightView.getText()+" "+
            subWorkout.getTotalWeight());
            customViewHolder.openFullWorkoutStats(customViewHolder.cardView,subWorkout.getWorkoutData());
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            protected CardView cardView;
            protected TextView dateView;
            protected TextView mainWorkoutView;
            protected TextView subWorkoutView;
            protected TextView setsView;
            protected TextView repsView;
            protected TextView weightView;

            public CustomViewHolder(View rowView) {
                super(rowView);
                cardView = (CardView) rowView.findViewById(R.id.workoutStatsCardView);
                dateView = (TextView) rowView.findViewById(R.id.liftingStatsDate_textView);
                mainWorkoutView = (TextView) rowView.findViewById(R.id.mainWorkout_textView);
                subWorkoutView = (TextView) rowView.findViewById(R.id.subWorkout_textView);
                setsView = (TextView) rowView.findViewById(R.id.sets_textView);
                repsView = (TextView) rowView.findViewById(R.id.reps_textView);
                weightView = (TextView) rowView.findViewById(R.id.weight_textView);
                deleteWorkoutStats(cardView);
            }

            public void openFullWorkoutStats(CardView cardView, List<WorkoutExercise> workoutData) {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFullWorkoutStatsFragment(workoutData);}
                });
            }

            public void deleteWorkoutStats(CardView cardView) {
                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showDeleteWorkoutStatsPopup(getLayoutPosition());
                        return true;
                    }
                });
            }


        }
    }

    private class MyAsyncTask extends AsyncTask<List<SubWorkout>,Void,List<SubWorkout>> {

        private WorkoutStatsTable table;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            table = new WorkoutStatsTable(getContext());
            progressDialog = new ProgressDialog(getContext(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected List<SubWorkout> doInBackground(List<SubWorkout>[] params) {
            params[0] = table.getCompletedWorkouts();
            subWorkoutList = params[0];
            return params[0];
        }

        @Override
        protected void onPostExecute(List<SubWorkout> subWorkoutList) {
            progressDialog.dismiss();
            recyclerAdapter = new RecyclerAdapter(subWorkoutList);
            setRecycleView(root,recyclerAdapter);
        }

    }
}
