package com.example.kaveon14.workoutbuddy.Fragments.MainFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.BodyTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows.DeleteBodyStatsPopup;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows.BodyStatsMenuPopup;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows.ManageBodyStatsPopup;
import com.example.kaveon14.workoutbuddy.R;
import java.util.ArrayList;
import java.util.List;

public class BodyStatsFragment extends Fragment {

    private static Body clickedBodyStatsItem;
    private View root;
    private List<Body> bodyStats;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    public BodyStatsFragment() {
        // Required empty public constructor
    }

    public static void setClickedBodyStatsItem(Body clickedBodyStatsItem) {
        BodyStatsFragment.clickedBodyStatsItem = clickedBodyStatsItem;
    }

    public static Body getClickedBodyStatsItem() {
        return clickedBodyStatsItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_body_stats, container, false);
        setFloatingActionButton();
        new MyAsyncTask().execute(new ArrayList<Body>());
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
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

    private void setRecycleView(View root,RecyclerAdapter recyclerAdapter) {
        recyclerView = (RecyclerView) root.findViewById(R.id.recylerView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemViewCacheSize(12);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        if(recyclerView.getAdapter().getItemCount()==0) {
            TextView textView = (TextView) root.findViewById(R.id.noBodyStats);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void handleFloatingActionButtonEvents(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BodyStatsMenuPopup popup = new BodyStatsMenuPopup(getView(),getContext());
                popup.showPopupWindow();
            }
        });
    }

    private void showDeleteBodyStatsPopup(RecyclerView recyclerView,int position) {
        DeleteBodyStatsPopup popup = new DeleteBodyStatsPopup(getView(),getContext());
        popup.setRecyclerView(recyclerView);
        popup.setBodyStatsList(bodyStats);
        popup.setPosition(position);
        popup.showPopupWindow();
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

        private List<Body> bodyStats;

        public RecyclerAdapter(List<Body> bodyStats) {
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
                editBodyStats(rowView);
                deleteBodyStats(rowView);
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

            private void editBodyStats(View rowView) {
                cardView = (CardView) rowView.findViewById(R.id.card_view);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = getLayoutPosition();
                        setClickedBodyStatsItem(bodyStats.get(i));
                        ManageBodyStatsPopup popup = new ManageBodyStatsPopup(root,getContext());
                        popup.setRecyclerView(recyclerView);
                        popup.setBodyList(bodyStats);
                        popup.setPosition(getLayoutPosition());
                        popup.isUpdatingRow(true);
                        popup.showPopupWindow();
                    }
                });
            }

            private void deleteBodyStats(View rowView) {
                cardView = (CardView) rowView.findViewById(R.id.card_view);
                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int i = getLayoutPosition();
                        v.performHapticFeedback(1);
                        showDeleteBodyStatsPopup(recyclerView,i);
                        return true;
                    }
                });
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<List<Body>,Void,List<Body>> {

        private BodyTable table;

        @Override
        protected void onPreExecute() {
            table = new BodyTable(getContext());
        }

        @Override
        protected List<Body> doInBackground(List<Body>[] params) {
            params[0] = table.getSortedBodyStats();
            bodyStats = params[0];
            return params[0];
        }

        @Override
        protected void onPostExecute(List<Body> bodyStats) {
            recyclerAdapter = new RecyclerAdapter(bodyStats);
            setRecycleView(root,recyclerAdapter);
        }
    }
}
