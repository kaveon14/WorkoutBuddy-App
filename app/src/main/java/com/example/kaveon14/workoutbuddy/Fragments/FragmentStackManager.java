package com.example.kaveon14.workoutbuddy.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentStackManager {

    private int currFragId = 0;
    private FragmentManager fragmentManager;

    public FragmentStackManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragmentToStack(Fragment frag,int fragId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(currFragId==0) {
            fragmentTransaction.add(fragId,frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {

            fragmentTransaction.hide(fragmentManager.findFragmentById(currFragId));
            fragmentTransaction.add(fragId, frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        currFragId = fragId;
    }
}
