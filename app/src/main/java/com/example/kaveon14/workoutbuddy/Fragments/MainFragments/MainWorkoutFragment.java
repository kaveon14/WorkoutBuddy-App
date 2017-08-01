package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.DeleteMainWorkoutPopup;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.WorkoutPopupWindows.MainWorkoutPopupMenu;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.SEARCH_SERVICE;

public class MainWorkoutFragment extends Fragment {

    public static String clickedMainWorkoutName;//no longer needed
    private List<String> mainWorkoutNames;
    private ArrayAdapter adapter;
    private ListView listView;
    private Menu menu;

    public MainWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_workout, container, false);
        setListView(root);
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

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    private FloatingActionButton setFloatingActionButton(View root) {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
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
        MainWorkoutPopupMenu popup = new MainWorkoutPopupMenu(getView());
        popup.setMainWorkoutAdapter(adapter);
        popup.setMainWorkoutNames(mainWorkoutNames);
        popup.showPopupWindow();
    }

    private ListView setListView(View root) {
        listView = (ListView) root.findViewById(R.id.mainWorkout_listView);
        listView.setAdapter(getAdapter());
        openWorkoutOnClick(listView);
        deleteRowVew();
        return listView;
    }

    private ArrayAdapter getAdapter() {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(getContext());
        mainWorkoutNames = mainWorkoutTable.getMainWorkoutNames();
        adapter = new ArrayAdapter<>(getContext(),
                R.layout.simple_list_item,mainWorkoutNames);
        return adapter;
    }

    private void openWorkoutOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedMainWorkoutName = parent.getItemAtPosition(position).toString();
                showSubWorkoutfragment(clickedMainWorkoutName);
            }
        });
    }

    private void showSubWorkoutfragment(String clickedMainWorkoutName) {
        SubWorkoutFragment subWorkoutFragment = new SubWorkoutFragment();
        subWorkoutFragment.setClickedMainWorkout(clickedMainWorkoutName);
        subWorkoutFragment.setMenu(menu);
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.subWorkout_fragment,subWorkoutFragment)
                .addToBackStack(null)
                .commit();
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

    private List<String> loadSearchedItems(Map<String,List<String>> queriedData) {
        List<String> list = new ArrayList<>();
        if (queriedData != null) {
            List<String> mainWorkouts = queriedData
                    .get(DataBaseContract.MainWorkoutData.COLUMN_MAINWORKOUT);
           for(int x=0;x<mainWorkouts.size();x++) {
               String mainWorkotName = mainWorkouts.get(x);
               for(int i=0;i<mainWorkoutNames.size();i++) {
                   String mainWorkoutListName = mainWorkoutNames.get(i);
                   if(mainWorkotName.equals(mainWorkoutListName)) {
                       list.add(mainWorkoutListName);
                   }
               }

           }

        }
        ArrayAdapter adapter = new ArrayAdapter(getContext(),R.layout.simple_list_item,list);
        listView.setAdapter(adapter);
        return list;
    }

    private void deleteRowVew() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.performHapticFeedback(1);
                showDeleteMainWorkoutPopup(position);
                return true;
            }
        });
    }

    private void showDeleteMainWorkoutPopup(int position) {
        DeleteMainWorkoutPopup popup = new DeleteMainWorkoutPopup(getView());
        popup.setMainWorkoutAdapter(adapter);
        popup.setMainWorkoutNames(mainWorkoutNames);
        popup.setPosition(position);
        popup.showPopupWindow();
    }
}
