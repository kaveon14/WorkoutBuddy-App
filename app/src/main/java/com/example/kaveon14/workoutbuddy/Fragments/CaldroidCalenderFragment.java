package com.example.kaveon14.workoutbuddy.Fragments;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import java.util.Date;


// TODO setup like real fragment (copy calender fragment)
// TODO lots of shit to fix possibly just put caldroid frag
// TODO on top of another frag (best case) damn lol

public class CaldroidCalenderFragment /*extends android.support.v4.app.Fragment*/  {//this one will most likely be deleted or changed
//display current workout at bottom of the calender frag(based on day clicked)//massively

    private Context mContext = null;

    public  CaldroidCalenderFragment() {
    }

    public void setContext(Context context) {
        mContext = context;
    }//put in constructor
    //create all stuff here wrap into a few small functions

    public void ttt(CaldroidFragment caldroidFragment) {
        final CaldroidListener listener  = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(mContext, "nice this works",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {//dont add to main
                String text = "month: " + month + " year: " + year;
                Toast.makeText(mContext, text,
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(mContext,
                        "Long click " + "yee",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCaldroidViewCreated() {//dont add to main
                Toast.makeText(mContext,
                        "Caldroid view is created",
                        Toast.LENGTH_SHORT).show();
            }

        };

        caldroidFragment.setCaldroidListener(listener);
    }

    public void setFragments() {

    }
}
