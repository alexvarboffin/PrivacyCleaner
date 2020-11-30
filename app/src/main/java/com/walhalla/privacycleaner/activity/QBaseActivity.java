package com.walhalla.privacycleaner.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.walhalla.privacycleaner.R;
import com.walhalla.privacycleaner.fragment.SettingsPreferenceFragment;

import com.walhalla.ui.DLog;
import com.walhalla.ui.Module_U;
import com.walhalla.ui.observer.RateAppModule;

public abstract class QBaseActivity extends AppCompatActivity {

    private RateAppModule var0;
    private Toast toast;

    public QBaseActivity() {
    }

//    protected static void mess(Object o) {
//        Log.d(TAG, "mess: " + o.toString());
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var0 = new RateAppModule(this);
        getLifecycle().addObserver(var0);        //WhiteScree
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            case R.string.start_test_again:
//                return false;

            case R.id.action_about:
                Module_U.aboutDialog(this);
                return true;

            case R.id.action_privacy_policy:
                Module_U.openBrowser(this, getString(R.string.url_privacy_policy));
                return true;

            case R.id.action_rate_app:
                Module_U.rateUs(this);
                return true;

            case R.id.action_share_app:
                Module_U.shareThisApp(this);
                return true;

            case R.id.action_discover_more_app:
                Module_U.moreApp(this);
                return true;

//            case R.id.action_exit:
//                this.finish();
//                return true;

            case R.id.action_feedback:
                Module_U.feedback(this);
                return true;

            case R.id.action_settings:
                replaceFragment(new SettingsPreferenceFragment());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


        //action_how_to_use_app
        //action_support_developer

        //return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
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

//    public void replaceFragment(Fragment fragment) {
//        try {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.container, fragment);
//            transaction.addToBackStack(null)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit();
//        } catch (Exception e) {
//            DLog.handleException(e);
//        }
//    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (var0 != null) {
            var0.appReloadedHandler();
        }
        super.onSaveInstanceState(outState);
    }


    private boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {

        //Pressed back => return to home screen
        FragmentManager fm = getSupportFragmentManager();
        final int oldValue = getSupportFragmentManager().getBackStackEntryCount();
        if (oldValue > 0) {
            fm.popBackStack(fm.getBackStackEntryAt(0).getId(),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            //Правка кнопки назад....
//            for (int entry = 0; entry < fm.getBackStackEntryCount(); entry++) {
//                DLog.i("Found fragment: " + fm.getBackStackEntryAt(entry).getId());
//            }
//            if (getSupportActionBar() != null) {
//                final int newValue = getSupportFragmentManager().getBackStackEntryCount();
//                getSupportActionBar().setHomeButtonEnabled(newValue > 0);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(newValue > 0);
//                getSupportActionBar().setDisplayShowHomeEnabled(newValue > 0);
//                getSupportActionBar().setSubtitle("Value -> " + newValue);
//            }
        } else {//oldValue == 0
//                Dialog
//                new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Leaving this App?")
//                        .setMessage("Are you sure you want to close this application?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
            //super.onBackPressed();


            if (doubleBackToExitPressedOnce) {
                if (toast != null) {
                    toast.cancel();
                }
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            toast = Toast.makeText(this,
                    getString(R.string.press_again_to_exit).toUpperCase(), Toast.LENGTH_LONG);
            toast.show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 800);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
