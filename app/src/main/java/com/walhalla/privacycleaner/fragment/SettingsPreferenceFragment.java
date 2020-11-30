package com.walhalla.privacycleaner.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.walhalla.ui.DLog;
import com.walhalla.ui.Module_U;

import com.walhalla.privacycleaner.LStorage;
import com.walhalla.privacycleaner.R;

public class SettingsPreferenceFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    public static final String PREF_LABEL = "__pref_key_label";
    private static final String PREF_PRIVACY_POLICY = "pref_privacy_policy";
    private static final String PREF_SHARE_KEY = "pref_share_key";
    private static final String PREF_RATE_REVIEW_KEY = "pref_rate_review_key";
    private static final String PREF_DEVELOPER = "pref_developer_summary";

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_notification);

        //parent_view = findViewById(android.R.id.content);
        //sharedPref = new SharedPref(getApplicationContext());

        //bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_ringtone)));
        final Preference preference3 = findPreference("key_more");
        if (preference3 != null) {
            preference3.setOnPreferenceClickListener(preference -> {
                Module_U.moreApp(getContext());
                return false;
            });
        }

        final androidx.preference.MultiSelectListPreference keyLang = findPreference(PREF_LABEL);
        bindPreferenceSummaryToValue(keyLang); //Attach
//        String[] arr = getResources().getStringArray(R.array.label_values);
//        StringBuilder sb = new StringBuilder();
//        sb.append("(");
//        int size = arr.length;
//        for (int i = 0; i < size; i++) {
//            sb.append(arr[i]);
//            if (i < size - 1) {
//                sb.append(", ");
//            }
//        }
//        sb.append(")");
        if (keyLang != null) {
//keyLang.setValueIndex(0);
//                keyLang.setOnPreferenceChangeListener((preference, newValue) -> {
//
//                    int index = keyLang.findIndexOfValue(newValue.toString());
//
//                    if (index != -1) {
//                        //setNewLocale((AppCompatActivity) getActivity(), newValue.toString()); //LocaleChanger.RUSSIAN
//
//                        LocaleChanger.setLocale(getActivity(), new Locale(newValue.toString()));
//                        ActivityRecreationHelper.recreate(getActivity(), true);
//                    }
//                    return true;
//                });
        }
//        bindPreferenceSummaryToValue(pr0);
//        bindPreferenceSummaryToValue(pr1);
//        bindPreferenceSummaryToValue(pr2);
//        bindPreferenceSummaryToValue(pr3);
//        bindPreferenceSummaryToValue(pr4);
//        bindPreferenceSummaryToValue(pr5);
//        bindPreferenceSummaryToValue(pr6);
//        bindPreferenceSummaryToValue(pr7);

//        if (pr0 != null) {
//            pr0.setOnPreferenceChangeListener((preference, newValue) -> {
//                int val = 8888;
//                try {
//                    val = Integer.parseInt(newValue.toString().trim());
//                    if ((val > 1) && (val < 65535)) {
//
//                        pr0.setSummary("" +val);
//                        pr0.setText("" + val);
//                        return true;
//                    } else {
//                        // invalid you can show invalid message
//                        Toast.makeText(getContext(), "error text", Toast.LENGTH_LONG).show();
//                        return false;
//                    }
//                } catch (Exception e) {
//                    return false;
//                }
//            });
//        }


        //newValue
        Preference p1 = getPreferenceManager().findPreference(PREF_DEVELOPER);
        if (p1 != null) {
            p1.setSummary(getString(R.string.play_google_pub));
            p1.setOnPreferenceClickListener(this);
        }
        Preference p2 = getPreferenceManager().findPreference("key_pref_version");
        if (p2 != null && getActivity() != null) {
            p2.setSummary(DLog.getAppVersion(getActivity()));
        }
        Preference p3 = getPreferenceScreen().findPreference("key_pref_about");
        if (p3 != null) {
            p3.setSummary(R.string.app_name);
        }

        Preference preference = getPreferenceScreen().findPreference(PREF_RATE_REVIEW_KEY);
        if (preference != null) {
            preference.setOnPreferenceClickListener(this);
        }

        Preference preference10 = findPreference(PREF_SHARE_KEY);
        if (preference10 != null) {
            preference10.setOnPreferenceClickListener(this);
        }

        Preference nn = findPreference(PREF_PRIVACY_POLICY);
        if (nn != null) {
            nn.setOnPreferenceClickListener(this);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity a = (AppCompatActivity) getActivity();
        if (a != null && a.getSupportActionBar() != null) {
            a.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            a.getSupportActionBar().setDisplayShowHomeEnabled(true);
            a.getSupportActionBar().setHomeButtonEnabled(true);
            a.getSupportActionBar().setSubtitle(R.string.action_settings);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        //String stringValue = newValue.toString();
        if (preference instanceof EditTextPreference) {
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            editTextPreference.setText((String) newValue);
            editTextPreference.setSummary((String) newValue);
        } else if (preference instanceof MultiSelectListPreference) {
            MultiSelectListPreference aa = (MultiSelectListPreference) preference;
            aa.setSummary(newValue.toString());
        }
        return true;
    }


    private void bindPreferenceSummaryToValue(Preference preference) {
        if (preference != null) {
            preference.setOnPreferenceChangeListener(this/*listener*/);

            //Устанавливаем настройки из сохраненных в LocalStorage
            Object obj = LStorage.getInstance(getContext()).settingsValue(preference.getKey());
            //String obj = mPreferences.getString(preference.getKey(),"");//Crash if boolean
            DLog.d("@@@@@@@@@@" + obj.toString());
            this.onPreferenceChange(preference, obj);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) { // item.getTitle());

            case android.R.id.home: // Status bar home button
                //case R.id.action_back:
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
                break;

            case R.id.action_exit:
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(PREF_SHARE_KEY)) {
//            Intent shareIntent = new Intent("android.intent.action.SEND");
//            shareIntent.setType("text/plain");
//            shareIntent.putExtra("android.intent.extra.TEXT", getString(R.string.share_message)
//                    + "\n" + getString(R.string.market_rate_url));
//            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
            Module_U.shareThisApp(getContext());
        } else if (preference.getKey().equals(PREF_RATE_REVIEW_KEY)) {
            if (getActivity() != null) {
                Module_U.rateUs(getActivity());
            }
        } else if (PREF_PRIVACY_POLICY.equals(preference.getKey())) {
            if (getActivity() != null) {
                Module_U.openBrowser(getActivity(), getString(R.string.url_privacy_policy));
            }
        } else if (PREF_DEVELOPER.equals(preference.getKey())) {
            Module_U.moreApp(getActivity());
        }
        return true;
    }
}
