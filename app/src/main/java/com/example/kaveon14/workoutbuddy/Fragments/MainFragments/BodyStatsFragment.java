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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.BodyTable;
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
// TODO create a blank list view adapter so screen is not all white

public class BodyStatsFragment extends Fragment {

    public static Body clickedBodyStatsItem;
    private List<Body> bodyStats;
    private BodyStatsAdapter bodyStatsAdapter;
    public static Body bodyObject;

    public BodyStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_body_stats, container, false);
        setUpListView(root);
        setFloatingActionButton();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && bodyObject != null) {
            bodyStats.add(bodyObject);
            bodyStatsAdapter.notifyDataSetChanged();
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
            public void onClick(View v) {//do stuff to allow new creation of main workout
                showBlankBodyStatsfragment();
            }
        });
    }

    private void setUpListView(View root) {
        ListView listView = (ListView) root.findViewById(R.id.bodyStats_listView);
        listView.setAdapter(getAdapter());
        handleListViewClicks(listView,root);
    }

    private void handleListViewClicks(ListView listView,View root) {
        updateRowView(listView);
        deleteRowView(listView,root);
    }

    private void updateRowView(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedBodyStatsItem = getBodyStats(position);
                BlankBodyStatsFragment blankBodyStatsFragment = showBlankBodyStatsfragment();
                blankBodyStatsFragment.isUpdatingRow(true) ;
            }
        });
    }

    private BlankBodyStatsFragment showBlankBodyStatsfragment() {
        BlankBodyStatsFragment blankBodyStatsFragment = new BlankBodyStatsFragment();
        getFragmentManager().beginTransaction()
                .hide(this)
                .add(R.id.blankBodyStats_fragment,blankBodyStatsFragment)
                .addToBackStack(null)
                .commit();
        return blankBodyStatsFragment;
    }

    private void deleteRowView(ListView listView,View root) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.performHapticFeedback(1);
                setUpAndShowPopupWindow(root,listView,position);
                return true;
            }
        });
    }

    private PopupWindow setUpAndShowPopupWindow(final View root,ListView listView,int position) {
        int width =  LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        View popupLayout = getPopupLayout(root);
        final PopupWindow popupWindow = new PopupWindow(popupLayout,width,height);
        popupWindow.setFocusable(true);
        popupWindow.update(0,0,width,height);
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
        dimBackground(popupWindow);
        setupPopupWindowContent(popupLayout);

        popupYESButtonClicked(popupWindow,listView,popupLayout,position);
        popupNOButtonClicked(popupLayout,popupWindow);
        return popupWindow;
    }

    private View getPopupLayout(View root) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getBaseContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.bodystats_popup_layout,(ViewGroup)
                root.findViewById(R.id.bodyStats_popupWindow));
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

    private void popupYESButtonClicked(PopupWindow popupWindow,ListView listView,View popupLayout,int position) {
        Button btn = (Button) popupLayout.findViewById(R.id.bodyStats_yes_popupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBodyStatsRow(position);
                listView.setAdapter(getAdapter());
                Toast.makeText(getContext(),"Body Stats Deleted!",Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });
    }

    private void popupNOButtonClicked(View popupLayout,PopupWindow popupWindow) {
        Button btn = (Button) popupLayout.findViewById(R.id.bodyStats_no_popupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void setupPopupWindowContent(View popupLayout) {
        TextView textView = (TextView) popupLayout.findViewById(R.id.bodystatsPopup_textView);
        textView.setBackgroundColor(Color.WHITE);

    }








    private void deleteBodyStatsRow(int position) {
        List<String> bodyStats = new BodyTable(getContext()).getColumn(COLUMN_DATE);
        String[] date = new String[] {
                bodyStats.get(position)
        };
        BodyTable bodyTable = new BodyTable(getContext());
        bodyTable.deleteRow(date);
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

    private class BodyStatsAdapter extends BaseAdapter {

        private Context context;
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
                rowView = inflater.inflate(R.layout.body_list_item,null);
            }
            setListItemView(rowView,bodyStats);
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
                    return object2.getDate().compareTo(object1.getDate());
                }
            });
        }
    }
}
