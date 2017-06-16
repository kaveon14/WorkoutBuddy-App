package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.MainWorkoutTable;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.SubWorkoutFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;

public class MainWorkoutFragment extends Fragment {

    public static String clickedMainWorkout;
    private List<String> mainWorkoutNames;
    private ArrayAdapter adapter;

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
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            setFloatingActionButton(getView());
        }
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
                setUpAndShowPopupWindow(root);
            }
        });
    }

    private ListView setListView(View root) {
        ListView listView = (ListView) root.findViewById(R.id.mainWorkout_listView);
        listView.setAdapter(getAdapter());
        openWorkoutOnClick(listView);
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
                clickedMainWorkout = parent.getItemAtPosition(position).toString();
                showSubWorkoutfragment();
            }
        });
    }

    private void showSubWorkoutfragment() {
        SubWorkoutFragment subWorkoutFragment = new SubWorkoutFragment();
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.subWorkout_fragment,subWorkoutFragment)
                .addToBackStack(null)
                .commit();
    }

    private PopupWindow setUpAndShowPopupWindow(final View root) {
        int width =  LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        View popupLayout = getPopupLayout(root);
        final PopupWindow popupWindow = new PopupWindow(popupLayout,width,height);
        popupWindow.setFocusable(true);
        popupWindow.update(0,0,width,height);
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
        dimBackground(popupWindow);
        setupPopupWindowContent(popupLayout);

        popupButtonClicked(popupWindow,popupLayout);
        return popupWindow;
    }

    private View getPopupLayout(View root) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getBaseContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.mainworkout_popup_layout,(ViewGroup)
                root.findViewById(R.id.mainWorkout_popupWindow));
    }

    private void setupPopupWindowContent(View popupLayout) {
        EditText editText = (EditText) popupLayout.findViewById(R.id.mainWorkoutPopup_editText);
        editText.setBackgroundColor(Color.WHITE);

    }

    private void dimBackground(PopupWindow popupWindow) {
        View container = (View) popupWindow.getContentView().getParent();
        WindowManager wm = (WindowManager) getActivity().getBaseContext()
                .getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) container.getLayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        wm.updateViewLayout(container, layoutParams);
    }

    private void popupButtonClicked(PopupWindow popupWindow,View popupLayout) {
        Button btn = (Button) popupLayout.findViewById(R.id.mainWorkoutPopupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMainWorkoutOnClick(popupLayout);
                Toast.makeText(getContext(),"MainWorkout Successfully Created!",Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });
    }

    private void addNewMainWorkoutOnClick(View popupLayout) {
        String mainWorkoutName = getMainWorkoutName(popupLayout);
        addMainWorkoutToDatatable(mainWorkoutName);
        mainWorkoutNames.add(mainWorkoutName);
        adapter.notifyDataSetChanged();
    }

    private void addMainWorkoutToDatatable(String mainWorkoutName) {
        MainWorkoutTable mainWorkoutTable = new MainWorkoutTable(getContext());
        mainWorkoutTable.addMainWorkout(mainWorkoutName);
    }

    private String getMainWorkoutName(View popupLayout) {
        EditText mainWorkoutEditText = (EditText) popupLayout.findViewById(R.id.mainWorkoutPopup_editText);
        return mainWorkoutEditText.getText().toString();
    }
}
