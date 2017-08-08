package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;
//restore sorted values to increase sorting speeds
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.BodyTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows.DeleteBodyStatsPopup;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows.AddBodyStatsPopup;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows.EditBodyStatsPopup;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_DATE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_CALF_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_QUAD_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_WEIGHT;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_FOREARM_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_WAIST_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_ARM_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_BACK_SIZE;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_CHEST_SIZE;

public class BodyStatsFragment extends Fragment {

    private static Body clickedBodyStatsItem;
    private static Body newBodyStats;
    private View root;
    private List<Body> bodyStats;
    private MainActivity mainActivity;

    public BodyStatsFragment() {
        // Required empty public constructor
    }

    public static void setClickedBodyStatsItem(Body clickedBodyStatsItem) {
        BodyStatsFragment.clickedBodyStatsItem = clickedBodyStatsItem;
    }

    public static Body getClickedBodyStatsItem() {
        return clickedBodyStatsItem;
    }

    public static void setNewBodyStats(Body newBodyStats) {
        BodyStatsFragment.newBodyStats = newBodyStats;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BodyTable bodyTable = new BodyTable(getContext());
        int amountOfStatsLogged = bodyTable.getColumn(COLUMN_DATE).size();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_body_stats, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recylerView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemViewCacheSize(12);
        recyclerView.setHasFixedSize(true);

        BodyTable bodyTable = new BodyTable(getContext());
        int amountOfStatsLogged = bodyTable.getColumn(COLUMN_DATE).size();
        bodyStats = new ArrayList<>();
        for(int x=0;x<6;x++) {
            bodyStats.add(getBodyStats(x));
        }
        recyclerView.setAdapter(new RecyclerAdapter(getContext(),bodyStats));

        setFloatingActionButton();
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            updateListViewForNewBodyStats();
            setFloatingActionButton();
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

    private FloatingActionButton setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setImageResource(R.drawable.ic_menu_manage);
            handleFloatingActionButtonEvents(fab);
        }
        return fab;
    }

    private void handleFloatingActionButtonEvents(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBodyStatsPopup bt = new AddBodyStatsPopup(getView(),getContext());
                bt.setMainActivity(mainActivity);
                bt.showPopupWindow();
            }
        });
    }

    private void updateRowView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setClickedBodyStatsItem(getBodyStats(position));
                EditBodyStatsPopup popup = new EditBodyStatsPopup(root,getContext());
                popup.isUpdatingRow(true);
                popup.showPopupWindow();

            }
        });
    }

    private void updateListViewForNewBodyStats() {
        if (newBodyStats != null) {
            bodyStats.add(newBodyStats);
        }
    }

    private void deleteRowView(ListView listView) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.performHapticFeedback(1);
                showDeleteBodyStatsPopup(listView,position);
                return true;
            }
        });
    }

    private void showDeleteBodyStatsPopup(ListView listView,int position) {
        DeleteBodyStatsPopup popup = new DeleteBodyStatsPopup(getView(),getContext());
        popup.setListView(listView);
        popup.setBodyStatsList(bodyStats);
        popup.setPosition(position);
        popup.showPopupWindow();
    }

    private Body getBodyStats(int x) {
         BodyTable bodyTable = new BodyTable(getContext());

         List<String> dateList = bodyTable.getColumn(COLUMN_DATE);
         List<String> weightList = bodyTable.getColumn(COLUMN_WEIGHT);
         List<String> chestSizeList = bodyTable.getColumn(COLUMN_CHEST_SIZE);
         List<String> backSizeList = bodyTable.getColumn(COLUMN_BACK_SIZE);
         List<String> armSizeList = bodyTable.getColumn(COLUMN_ARM_SIZE);
         List<String> forearmSizeList = bodyTable.getColumn(COLUMN_FOREARM_SIZE);
         List<String> waistSizeList = bodyTable.getColumn(COLUMN_WAIST_SIZE);
         List<String> quadSizeList = bodyTable.getColumn(COLUMN_QUAD_SIZE);
         List<String> calfSizeList = bodyTable.getColumn(COLUMN_CALF_SIZE);

         return new Body().setDate(dateList.get(x)).setWeight(weightList.get(x))
                 .setChestSize(chestSizeList.get(x)).setBackSize(backSizeList.get(x))
                 .setArmSize(armSizeList.get(x)).setForearmSize(forearmSizeList.get(x))
                 .setWaistSize(waistSizeList.get(x)).setQuadSize(quadSizeList.get(x))
                 .setCalfSize(calfSizeList.get(x));
     }


    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

        private List<Body> bodyStats;
        private Context context;

        public RecyclerAdapter(Context context, List<Body> bodyStats) {
            this.context = context;
            this.bodyStats = bodyStats;
        }

        @Override
        public int getItemCount() {
            return (null != bodyStats ? bodyStats.size() : 0);
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.simple_cardview,null);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
            Body body = bodyStats.get(i);
            customViewHolder.dateTextView.setText(body.getStringDate());
            customViewHolder.weightView.setText(body.getWeight());
            customViewHolder.chestSizeView.setText(body.getChestSize());
            customViewHolder.backSizeView.setText(body.getBackSize());
            customViewHolder.armSizeView.setText(body.getArmSize());
            customViewHolder.forearmSizeView.setText(body.getForearmSize());
            customViewHolder.waistSizeView.setText(body.getWaistSize());
            customViewHolder.quadSizeView.setText(body.getQuadSize());
            customViewHolder.calfSizeView.setText(body.getCalfSize());
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            protected CardView cardView;
            protected TextView dateTextView;
            protected TextView weightView;
            protected TextView chestSizeView;
            protected  TextView backSizeView;
            protected TextView armSizeView;
            protected TextView forearmSizeView;
            protected TextView waistSizeView;
            protected  TextView quadSizeView;
            protected TextView calfSizeView;

            public CustomViewHolder(View rowView) {
                super(rowView);
                onItemClickListener(rowView);
                dateTextView = (TextView) rowView.findViewById(R.id.date_textView);
                weightView = (TextView) rowView.findViewById(R.id.weight_textView);
                chestSizeView = (TextView) rowView.findViewById(R.id.chestSize_textView);
                backSizeView = (TextView) rowView.findViewById(R.id.backSize_textView);
                armSizeView = (TextView) rowView.findViewById(R.id.armSize_textView);
                forearmSizeView = (TextView) rowView.findViewById(R.id.forearmSize_textView);
                waistSizeView = (TextView) rowView.findViewById(R.id.waistSize_textView);
                quadSizeView = (TextView) rowView.findViewById(R.id.quadSize_textView);
                calfSizeView = (TextView) rowView.findViewById(R.id.calfSize_textView);
            }

            private void onItemClickListener(View rowView) {
                cardView = (CardView) rowView.findViewById(R.id.card_view);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = getLayoutPosition();
                        setClickedBodyStatsItem(bodyStats.get(i));
                        EditBodyStatsPopup popup = new EditBodyStatsPopup(root,getContext());
                        popup.isUpdatingRow(true);
                        popup.showPopupWindow();
                    }
                });
            }
        }
    }
}
