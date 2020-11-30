package com.walhalla.privacycleaner.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.walhalla.privacycleaner.R;
import com.walhalla.privacycleaner.fragment.SettingsPreferenceFragment;

public class ActivitySetting extends AppCompatActivity
{

    private AppCompatDelegate mDelegate;
    private ActionBar actionBar;

    private View parent_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsPreferenceFragment())
                .commit();
    }


    /**
     * Binds a preference's summary to its value. More specifically, when the preference's value is changed.
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), "")
        );
    }

    /**
     * A preference value change listener that updates the preference's summary to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = (preference, value) -> {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in the preference's 'entries' list.
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            // Set the summary to reflect the new value.
            preference.setSummary(
                    index >= 0 ? listPreference.getEntries()[index] : null
            );

        } else {
            // For all other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    private void initToolbar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(R.string.action_settings);
        // for system bar in lollipop
//        ShareManager.systemBarLolipop(this);
//        ShareManager.setActionBarColor(this, actionBar);
//        actionBar.setSubtitle(Locale.getDefault().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Support for Activity : DO NOT CODE BELOW ----------------------------------------------------
     */

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

//    @Override
//    protected void onDestroy() {
//        getDelegate().onDestroy();
//        super.onDestroy();
//    }

//    public void invalidateOptionsMenu() {
//        getDelegate().invalidateOptionsMenu();
//    }
//
//    public AppCompatDelegate getDelegate() {
//        if (mDelegate == null) {
//            mDelegate = AppCompatDelegate.create(this, null);
//        }
//        return mDelegate;
//    }

    @Override
    protected void onResume() {
        //initToolbar();
        super.onResume();
//        ActivityRecreationHelper.onResume(this);
//        currentLocale.setText(Locale.getDefault().toString());
//        date.setText(DateProvider.provideSystemLocaleFormattedDate());

    }


    @Override
    protected void onDestroy() {
        //ActivityRecreationHelper.onDestroy(this);
        super.onDestroy();
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        newBase = LocaleChanger.configureBaseContext(newBase);
//        super.attachBaseContext(newBase);
//    }

//    private static void setNewLocale(AppCompatActivity context, @LocaleChanger.LocaleDef String language) {
//        LocaleChanger.setLocale(context, language);
//        ActivityRecreationHelper.recreate(context, true);
//
////        Intent intent = context.getIntent();
////        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
//
//
//        //Toast.makeText(getBaseContext(), lang.getEntries()[index], Toast.LENGTH_LONG).show();
////        try {
//////                            LocaleChanger.onConfigurationChanged(new Locale(newValue.toString()));
//////                            ActivityRecreationHelper.recreate(getActivity(), false);
////            //Reload activity
////            context.finish();
////            context.overridePendingTransition(0, 0);
////            context.startActivity(context.getIntent());
////            context.overridePendingTransition(0, 0);
////
////            //@@@ShareManager.restartApplication(getActivity());
////
////        } catch (Exception e) {
////            handleException(context, e);
////        }
//    }
}
