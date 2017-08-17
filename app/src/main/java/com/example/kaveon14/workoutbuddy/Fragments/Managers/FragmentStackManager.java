package com.example.kaveon14.workoutbuddy.Fragments.Managers;

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
        /*if(currFragId==0) {
            fragmentTransaction.add(fragId,frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if(currFragId==fragId) {
            fragmentTransaction.add(fragId, frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.hide(fragmentManager.findFragmentById(currFragId));
            fragmentTransaction.add(fragId, frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }*/




        Fragment fragment = fragmentManager.findFragmentById(currFragId);
        if(fragment != null && fragment.isVisible()) {
            fragmentTransaction.hide(fragment);
            fragmentTransaction.add(fragId, frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.add(fragId,frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }




        currFragId = fragId;
    }
}
