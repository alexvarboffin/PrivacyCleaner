package com.walhalla.privacycleaner;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.walhalla.ui.DLog;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.walhalla.privacycleaner.fragment.SettingsPreferenceFragment;

public class LStorage {

    private static final String LABEL_SPAM = "SPAM";
    private static final String INBOX = "INBOX";

    private static LStorage instance = null;

    private SharedPreferences getVar0() {
        if (BuildConfig.DEBUG) {
            Map<String, ?> keys = var0.getAll();

            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                DLog.d(entry.getKey() + ": " +
                        entry.getValue().toString());
            }
        }
        return var0;
    }

    //private final Context mContext;
    private SharedPreferences var0;

    private LStorage(Context context) {
        var0 = PreferenceManager.getDefaultSharedPreferences(context);
        //context.getSharedPreferences("PREF_DATA_44", Context.MODE_PRIVATE);
        //mContext = context;
    }

    public synchronized static LStorage getInstance(Context context) {
        if (instance == null) {
            instance = new LStorage(context);
        }
        return instance;
    }

    public Object settingsValue(String propertyKey) {
        switch (propertyKey) {

//            case SettingsPreferenceFragment.KEY_APP_LOCATION:
//                return saveLocation();

            case SettingsPreferenceFragment.PREF_LABEL:
                return whiteListLabels();

            default:
                return var0.getString(propertyKey, "...");
        }
    }

    public Set<String> whiteListLabels() {
        //List<List<String>> newValue = new ArrayList<>();
        Set<String> defaultData = new HashSet<>();
        //

        //update
        defaultData.add(LABEL_SPAM);
        defaultData.add(INBOX);

//        if (raw != null) {
//            raw = raw.replace(" ", "").replace("[", "")
//                    .replace("]", "");
//            String[] one = raw.split(",");
//            newValue = new ArrayList<>(one.length);
//            //List<String> newValue = Arrays.asList(one);
//            for (String s : one) {
//                String[] mm = s.split("\\|");
//                //List<String> aaa = new ArrayList<>(mm.length);
//                List<String> aaa = Arrays.asList(mm);
//                newValue.add(aaa);
//            }
//        }
//        return newValue;
        return getVar0().getStringSet(SettingsPreferenceFragment.PREF_LABEL, defaultData);
    }

}
