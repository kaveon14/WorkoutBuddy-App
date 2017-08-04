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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaveon14.workoutbuddy.Activity.MainActivity;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.BodyTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows.DeleteBodyStatsPopup;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows.AddBodyStatsPopup;
import com.example.kaveon14.workoutbuddy.Fragments.SubFragments.BlankBodyStatsFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private BodyStatsAdapter bodyStatsAdapter;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_body_stats, container, false);
        setUpListView();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recylerView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new AAdapter(getContext(),bodyStats));

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

    private void setUpListView() {
        ListView listView = (ListView) root.findViewById(R.id.bodyStats_listView);
        listView.setAdapter(getAdapter());
        handleListViewClicks(listView);
        /*if(bodyStatsAdapter.isEmpty()) {deleted file by accident
            listView.setEmptyView(root.findViewById(R.id.bodyStatsEmptyListItem));
        }*/
    }

    private void handleListViewClicks(ListView listView) {
        updateRowView(listView);
        deleteRowView(listView);
    }

    private void updateRowView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setClickedBodyStatsItem(getBodyStats(position));
                BlankBodyStatsFragment blankBodyStatsFragment = showBlankBodyStatsfragment();
                blankBodyStatsFragment.isUpdatingRow(true) ;
            }
        });
    }

    private void updateListViewForNewBodyStats() {
        if (newBodyStats != null) {
            bodyStats.add(newBodyStats);
            bodyStatsAdapter.notifyDataSetChanged();
            bodyStatsAdapter.sortByDate();
        }
    }

    public BlankBodyStatsFragment showBlankBodyStatsfragment() {
        BlankBodyStatsFragment blankBodyStatsFragment = new BlankBodyStatsFragment();
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.blankBodyStats_fragment,blankBodyStatsFragment)
                .addToBackStack(null)
                .commit();
        return blankBodyStatsFragment;
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
        popup.setAdapter(bodyStatsAdapter);
        popup.setListView(listView);
        popup.setBodyStatsList(bodyStats);
        popup.setPosition(position);
        popup.showPopupWindow();
    }

    private BodyStatsAdapter getAdapter() {
         BodyTable bodyTable = new BodyTable(getContext());
         int amountOfStatsLogged = bodyTable.getColumn(COLUMN_DATE).size();
         bodyStats = new ArrayList<>();
         for(int x=0;x<amountOfStatsLogged;x++) {
             bodyStats.add(getBodyStats(x));
         }
         bodyStatsAdapter = new BodyStatsAdapter(getContext(),bodyStats);
         bodyStatsAdapter.sortByDate();
         return bodyStatsAdapter;
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

    public class BodyStatsAdapter extends BaseAdapter {

        private Context context;//delete
        private List<Body> bodyStatsList;

        public BodyStatsAdapter(Context context,List<Body> bodyStats) {
            bodyStatsList = bodyStats;
            this.context = context;
        }

        public int getCount() {return bodyStatsList.size();}

        public Body getItem(int i) {return bodyStatsList.get(i);}

        public long getItemId(int i) {return i;}

        public View getView(int position, View rowView, ViewGroup viewGroup) {
            Body bodyStats = bodyStatsList.get(position);
            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.simple_cardview,null);
            }
            //setListItemView(rowView,bodyStats);
            return rowView;
        }

        private void setListItemView(View rowView,Body body) {
            setDateTextView(rowView,body);
            setWeightTextView(rowView,body);
            setChestSizeTextView(rowView,body);
            setBackSizeTextView(rowView,body);
            setArmSizeTextView(rowView,body);
            setForearmSizeTextView(rowView,body);
            setWaistSizeTextView(rowView,body);
            setQuadSizeTextView(rowView,body);
            setCalfSizeTextView(rowView,body);
        }

        private void setDateTextView(View rowView,Body body) {
            TextView dateTextView = (TextView) rowView.findViewById(R.id.date_textView);
            String date = body.getStringDate();
            dateTextView.setText("Date: "+date);
        }

        private void setWeightTextView(View rowView,Body body) {
            TextView weightView = (TextView) rowView.findViewById(R.id.weight_textView);
            String weight = body.getWeight();
            weightView.setText("Weight: "+weight);
        }

        private void setChestSizeTextView(View rowView,Body body) {
            TextView chestSizeView = (TextView) rowView.findViewById(R.id.chestSize_textView);
            String chestSize = body.getChestSize();
            chestSizeView.setText("Chest: "+chestSize);
        }

        private void setBackSizeTextView(View rowView,Body body) {
            TextView backSizeView = (TextView) rowView.findViewById(R.id.backSize_textView);
            String backSize = body.getBackSize();
            backSizeView.setText("Back: "+backSize);
        }

        private void setArmSizeTextView(View rowView,Body body) {
            TextView armSizeView = (TextView) rowView.findViewById(R.id.armSize_textView);
            String armSize = body.getArmSize();
            armSizeView.setText("Arms: "+armSize);
        }

        private void setForearmSizeTextView(View rowView,Body body) {
            TextView forearmSizeView = (TextView) rowView.findViewById(R.id.forearmSize_textView);
            String forearmSize = body.getForearmSize();
            forearmSizeView.setText("Forearms: "+forearmSize);
        }

        private  void setWaistSizeTextView(View rowView,Body body) {
            TextView waistSizeView = (TextView) rowView.findViewById(R.id.waistSize_textView);
            String waistSize = body.getWaistSize();
            waistSizeView.setText("Waist: "+waistSize);
        }

        private void setQuadSizeTextView(View rowView,Body body) {
            TextView quadSizeView = (TextView) rowView.findViewById(R.id.quadSize_textView);
            String quadSize = body.getQuadSize();
            quadSizeView.setText("Quads: "+quadSize);
        }

        private void setCalfSizeTextView(View rowView,Body body) {
            TextView calfSizeView = (TextView) rowView.findViewById(R.id.calfSize_textView);
            String calfSize = body.getCalfSize();
            calfSizeView.setText("Calves: "+calfSize);
        }

        public void sortByDate() {

            Collections.sort(bodyStatsList, new Comparator<Body>() {
                @Override
                public int compare(Body object1, Body object2) {

                    return object2.getDateForSorting().compareTo(object1.getDateForSorting());
                }
            });
        }
    }

    public class AAdapter extends RecyclerView.Adapter<AAdapter.CustomViewHolder> {

        private List<Body> bodyStats;
        private Context context;

        public AAdapter(Context context,List<Body> bodyStats) {
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
            customViewHolder.dateTextView.setText(customViewHolder.dateTextView.getText()+" "+
                    body.getStringDate());
            customViewHolder.weightView.setText(customViewHolder.weightView.getText()+" "
                    +body.getWeight());
            customViewHolder.chestSizeView.setText(customViewHolder.chestSizeView.getText()+" "
                    +body.getChestSize());
            customViewHolder.backSizeView.setText(customViewHolder.backSizeView.getText()+" "
                    +body.getBackSize());
            customViewHolder.armSizeView.setText(customViewHolder.armSizeView.getText()+" "
                    +body.getArmSize());
            customViewHolder.forearmSizeView.setText(customViewHolder.forearmSizeView.getText()+" "
                    +body.getForearmSize());
            customViewHolder.waistSizeView.setText(customViewHolder.waistSizeView.getText()+" "
                    +body.getWaistSize());
            customViewHolder.quadSizeView.setText(customViewHolder.quadSizeView.getText()+" "
                    +body.getQuadSize());
            customViewHolder.calfSizeView.setText(customViewHolder.calfSizeView.getText()+" "
                    +body.getCalfSize());
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
                        BlankBodyStatsFragment blankBodyStatsFragment = showBlankBodyStatsfragment();
                        blankBodyStatsFragment.isUpdatingRow(true) ;
                    }
                });
            }
        }
    }
}
