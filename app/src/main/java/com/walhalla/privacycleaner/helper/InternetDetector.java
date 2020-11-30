package com.walhalla.privacycleaner.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetDetector {

    private Context f0;

    public InternetDetector(Context context) {
        this.f0 = context;
    }

    public boolean checkMobileInternetConn() {
        ConnectivityManager connectivity = (ConnectivityManager) f0
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo info1 = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo info2 = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (info1 != null && info2 != null) {
                if (info1.isConnected() || info2.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
}