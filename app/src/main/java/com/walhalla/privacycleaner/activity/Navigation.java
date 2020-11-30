package com.walhalla.privacycleaner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.walhalla.ui.DLog;

import com.walhalla.privacycleaner.R;
import com.walhalla.privacycleaner.fragment.BlankFragment;

public class Navigation {

    private AppCompatActivity activity;
    private static final String F_HOME = "home";

    public Navigation(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        try {
            String fragmentTag = fragment.getClass().getName();
            boolean fragmentPopped = fragmentManager
                    .popBackStackImmediate(fragmentTag, 0); //popBackStackImmediate - some times crashed

            if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) {

                FragmentTransaction ftx = fragmentManager.beginTransaction();
                ftx.addToBackStack(fragment.getClass().getSimpleName());
//                ftx.setCustomAnimations(R.anim.slide_in_right,
//                        R.anim.slide_out_left, R.anim.slide_in_left,
//                        R.anim.slide_out_right);
                ftx.replace(R.id.container, fragment);
                ftx.commit();
            }
        } catch (IllegalStateException e) {
            DLog.handleException(e);
        }
    }

    public void homeScreen() {
        Fragment fragment = BlankFragment.newInstance("1", "2");

        //Fragment fragment = BlockListFragment.newInstance("");
        activity.getSupportFragmentManager()

                .beginTransaction()
                //.addToBackStack(null)
                .add(R.id.container, fragment, fragment.getClass().getSimpleName())
                .commit();
//        replaceFragment(fragment);
    }
}