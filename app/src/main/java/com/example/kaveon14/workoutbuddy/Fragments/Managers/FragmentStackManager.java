package com.example.kaveon14.workoutbuddy.Fragments.Managers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Stack;

public class FragmentStackManager {

    private int currFragId = 0;
    private int prevFragId = 0;
    private FragmentManager fragmentManager;
    private static Stack<Integer> test;

    public FragmentStackManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        if(test==null) {
            test = new Stack<>();
            test.add(0);
        }
    }

    public static void PopFragmentStack() {
        test.pop();
        if(test.isEmpty()) {
            test.add(0);
        }
    }

    public void addFragmentToStack(Fragment frag,int fragId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(test.peek());
        if(fragment != null) {
            fragmentTransaction.hide(fragment);
            fragmentTransaction.add(fragId, frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.add(fragId,frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        test.add(fragId);

       /* if(currFragId==fragId) {
            return;
        } else if(currFragId != 0) {
            prevFragId = currFragId;
            currFragId = fragId;
        } else {
            prevFragId = currFragId = fragId;
        }*/
    }
}
