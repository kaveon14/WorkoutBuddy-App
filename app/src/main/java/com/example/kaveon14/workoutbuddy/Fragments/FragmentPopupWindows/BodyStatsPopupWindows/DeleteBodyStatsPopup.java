package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.BodyTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.BodyStatsFragment;
import com.example.kaveon14.workoutbuddy.R;
import java.util.List;
import static com.example.kaveon14.workoutbuddy.DataBase.DatabaseManagment.DataBaseContract.BodyData.COLUMN_DATE;

public class DeleteBodyStatsPopup extends PopupWindowManager {

    private int position;
    private ListView listView;
    private List<Body> bodyStats;
    private BodyStatsFragment.BodyStatsAdapter adapter;

    public DeleteBodyStatsPopup(View root) {
        setRootView(root);
        setPopupLayout(R.layout.bodystats_popup_layout);
        setPopupViewId(R.id.bodyStats_popupWindow);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBodyStatsList(List<Body> bodyStats) {
        this.bodyStats = bodyStats;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setAdapter(BodyStatsFragment.BodyStatsAdapter adapter) {
        this.adapter = adapter;
    }

    public void showPopupWindow() {
        displayPopupWindow();
        setYesPopupButton();
        setNoPopupButton();
        setTextView();
        listView.setAdapter(adapter);
    }

    private void setYesPopupButton() {
        Button btn = (Button) popupWindow.getContentView().findViewById(R.id.bodyStats_yes_popupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBodyStatsRow();
                Toast.makeText(context,"Body Stats Data Deleted!",Toast.LENGTH_SHORT).show();
                deleteRowView();
                popupWindow.dismiss();
            }
        });
    }

    private void setNoPopupButton() {
        Button btn = (Button) popupWindow.getContentView().findViewById(R.id.bodyStats_no_popupBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void setTextView() {//take
        String message = "   Do you want to DELETE the clicked body stats'??";
        TextView textView = (TextView) popupLayout.findViewById(R.id.bodystatsPopup_textView);
        textView.setText(message);
    }

    private void deleteRowView() {
        bodyStats.remove(position);
        adapter.notifyDataSetChanged();
    }

    private void deleteBodyStatsRow() {
        List<String> bodyStats = new BodyTable(context).getColumn(COLUMN_DATE);
        String[] date = new String[] {
                bodyStats.get(position)
        };
        BodyTable bodyTable = new BodyTable(context);
        bodyTable.deleteRow(date);
    }
}
