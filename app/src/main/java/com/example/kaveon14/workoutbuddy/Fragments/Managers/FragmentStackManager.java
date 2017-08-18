package com.example.kaveon14.workoutbuddy.Fragments.Managers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Stack;

public class FragmentStackManager {

    private FragmentManager fragmentManager;
    private static Stack<Integer> fragIds;

    public FragmentStackManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        if(fragIds ==null) {
            fragIds = new Stack<>();
            fragIds.add(0);
        }
    }

    public static void PopFragmentStack() {
        fragIds.pop();
        if(fragIds.isEmpty()) {
            fragIds.add(0);
        }
    }

    public void addFragmentToStack(Fragment frag,int fragId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(fragIds.peek());
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
        fragIds.add(fragId);
    }
}
