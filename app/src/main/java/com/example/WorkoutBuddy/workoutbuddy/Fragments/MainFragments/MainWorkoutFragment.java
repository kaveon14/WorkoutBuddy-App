package com.example.WorkoutBuddy.workoutbuddy.Fragments.MainFragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.WorkoutBuddy.workoutbuddy.Activities.MainActivity;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.Data.MainWorkout;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.WorkoutBuddy.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.MainWorkoutPopupMenu;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.Managers.FragmentStackManager;
import com.example.WorkoutBuddy.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.WorkoutBuddy.workoutbuddy.R;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.WorkoutApi;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers.WorkoutRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static android.content.Context.SEARCH_SERVICE;
import static com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI.JSON_KEY;

public class MainWorkoutFragment extends Fragment {

    private static MainWorkout clickedMainWorkout;
    private List<MainWorkout> mainWorkouts;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private View root;
    private MainActivity mainActivity;
    private FragmentStackManager fragmentStackManager;
    private Menu menu;

    public MainWorkoutFragment() {
        // Required empty public constructor
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static MainWorkout getClickedMainWorkout() {
        return clickedMainWorkout;
    }

    public static void setClickedMainWorkout(MainWorkout clickedMainWorkout) {
        MainWorkoutFragment.clickedMainWorkout = clickedMainWorkout;
    }

    public void setFragmentStackManager(FragmentStackManager fragmentStackManager) {
        this.fragmentStackManager = fragmentStackManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main_workout, container, false);
        if(CoreAPI.getUserId()==0) {
            new LocalAsyncTask().execute(mainWorkouts);
        } else {
            new RemoteAsyncTask().execute();
        }
        setFloatingActionButton(root);
        setSearchViewOnClick();
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            setFloatingActionButton(getView());
        }
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

    private FloatingActionButton setFloatingActionButton(View root) {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.ic_menu_slideshow);
            handleFloatingActionButtonEvents(fab,root);
        }
        return fab;
    }

    private void handleFloatingActionButtonEvents(FloatingActionButton fab,View root) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMainWorkoutPopupMenu();
            }
        });
    }

    private void showMainWorkoutPopupMenu() {
        MainWorkoutPopupMenu popup = new MainWorkoutPopupMenu(getView(),getContext());
        popup.setMainWorkoutList(mainWorkouts);
        popup.setRecyclerAdapter(recyclerAdapter);
        popup.showPopupWindow();
    }

    private RecyclerView setRecycleView(View root,RecyclerAdapter recyclerAdapter) {
        recyclerView = (RecyclerView) root.findViewById(R.id.mainWorkout_RecycleView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemViewCacheSize(12);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        return recyclerView;
    }

    private void showSubWorkoutfragment(MainWorkout clickedMainWorkout) {
        SubWorkoutFragment subWorkoutFragment = new SubWorkoutFragment();
        subWorkoutFragment.setClickedMainWorkout(clickedMainWorkout);
        subWorkoutFragment.setMenu(menu);
        subWorkoutFragment.setMainActivity(mainActivity);
        subWorkoutFragment.setFragmentStackManager(fragmentStackManager);
        fragmentStackManager.addFragmentToStack(subWorkoutFragment,
                R.id.subWorkout_fragment);
    }

    private void setSearchViewOnClick() {
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainWorkoutTable table = new MainWorkoutTable(getContext());
                loadSearchedItems(table.searchTable(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private List<MainWorkout> loadSearchedItems(Map<String,List<String>> queriedData) {
        List<MainWorkout> list = new ArrayList<>();
        if (queriedData != null) {
            List<String> mainWorkouts = queriedData
                    .get(DataBaseContract.MainWorkoutData.COLUMN_MAINWORKOUT);
           for(int x=0;x<mainWorkouts.size();x++) {
               String mainWorkoutName = mainWorkouts.get(x);
               for(int i=0;i<mainWorkouts.size();i++) {
                   String mainWorkoutListItem = mainWorkouts.get(i);
                   if(mainWorkoutName.equals(mainWorkoutListItem)) {
                       list.add(new MainWorkout(mainWorkoutName,null));
                   }
               }
           }

        }
        recyclerView.setAdapter(new RecyclerAdapter(list));
        return list;
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

        private List<MainWorkout> mainWorkoutList;

        public RecyclerAdapter(List<MainWorkout> mainWorkoutList) {
            this.mainWorkoutList = mainWorkoutList;
        }

        @Override
        public int getItemCount() {
            return (null != mainWorkoutList ? mainWorkoutList.size() : 0);
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.simple_list_item,null);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder,int i) {
            MainWorkout mainWorkout = mainWorkoutList.get(i);
            customViewHolder.nameView.setText(mainWorkout.getMainWorkoutName());
        }


        class CustomViewHolder extends RecyclerView.ViewHolder {

            protected TextView nameView;

            public CustomViewHolder(View rowView) {
                super(rowView);
                nameView = (TextView) rowView.findViewById(R.id.simpleTextView);
                openClickdMainWorkout(nameView  );
            }

            private void openClickdMainWorkout(TextView textView) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = getLayoutPosition();
                        setClickedMainWorkout(mainWorkoutList.get(i));
                        showSubWorkoutfragment(getClickedMainWorkout());
                    }
                });
            }
        }
    }

    private class LocalAsyncTask extends AsyncTask<List<MainWorkout>,Void,List<MainWorkout>> {

        private MainWorkoutTable table;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            table = new MainWorkoutTable(getContext());
            progressDialog = new ProgressDialog(getContext(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected List<MainWorkout> doInBackground(List<MainWorkout>[] params) {
            params[0] = table.getMainWorkouts();
            mainWorkouts = params[0];
            return params[0];
        }

        @Override
        protected void onPostExecute(List<MainWorkout> mainWorkouts) {
            progressDialog.dismiss();
            recyclerAdapter = new RecyclerAdapter(mainWorkouts);
            setRecycleView(root,recyclerAdapter);

        }

    }

    private class RemoteAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            WorkoutRequestHandler requestHandler = new WorkoutRequestHandler();
            return requestHandler.sendGetMainWorkoutsRequest(CoreAPI.getUserId());
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(!jsonObject.getBoolean(CoreAPI.JSON_ERROR)) {
                    recyclerAdapter = new RecyclerAdapter(getData(jsonObject));
                    setRecycleView(root,recyclerAdapter);
                } else {
                    //do toast
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }

        private List<MainWorkout> getData(JSONObject jsonObject) throws JSONException {
            mainWorkouts = new ArrayList<>();

            JSONArray array = jsonObject.getJSONArray(JSON_KEY);
            for(int x=0;x<array.length();x++) {
                String mainWorkout_name = ((JSONObject) array.get(x))
                        .getString(WorkoutApi.JSON_MAINWORKOUT_NAME);
                int rowId = ((JSONObject) array.get(x)).getInt(CoreAPI.JSON_ROW_ID);
                MainWorkout mainWorkout = new MainWorkout(mainWorkout_name,null);
                mainWorkout.setRowId(rowId);
                mainWorkouts.add(mainWorkout);
            }
            return mainWorkouts;
        }

    }
}
