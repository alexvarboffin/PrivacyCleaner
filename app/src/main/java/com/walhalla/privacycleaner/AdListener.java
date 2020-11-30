package com.walhalla.privacycleaner;

import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdView;


import static android.accounts.AccountManager.ERROR_CODE_NETWORK_ERROR;
import static android.accounts.AccountManager.VISIBILITY_UNDEFINED;

public class AdListener extends com.google.android.gms.ads.AdListener {

    private static final String TAG = "@@@";
    private final AdView mAdView;

    public AdListener(AdView name) {
        super();
        this.mAdView = name;
    }

    @Override
    public void onAdClosed() {
        super.onAdClosed();
        Log.d(TAG, "onAdClosed: " + mAdView.getAdUnitId());
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        super.onAdFailedToLoad(errorCode);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, String.format("Ad %s failed to load with error %d.", mAdView.getAdUnitId(), errorCode));

            if (errorCode == ERROR_CODE_NETWORK_ERROR) {
                mAdView.resume();
//                if (mAdView.getVisibility() == View.VISIBLE) {
//                    mAdView.setVisibility(View.GONE);
//                }
            }
            //#PACKAGE_NAME_KEY_LEGACY_VISIBLE}, {@link #PACKAGE_NAME_KEY_LEGACY_NOT_VISIBLE
            else if (errorCode == VISIBILITY_UNDEFINED) {
                Log.i(TAG, "onAdFailedToLoad: " + mAdView.getContext().getPackageName());
                Log.i(TAG, "onAdFailedToLoad: VISIBILITY_UNDEFINED");
            }
        }
    }


    @Override
    public void onAdOpened() {
        super.onAdOpened();
        Log.d(TAG, "onAdOpened: " + mAdView.getAdUnitId());
    }


    @Override
    public void onAdLeftApplication() {
        super.onAdLeftApplication();
        Log.d(TAG, "onAdLeftApplication: " + mAdView.getAdUnitId());
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        Log.d(TAG, "onAdLoaded: " + mAdView.getAdUnitId());

        if (mAdView.getVisibility() == View.GONE) {
            mAdView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAdClicked() {
        super.onAdClicked();
        Log.d(TAG, "onAdClicked: " + mAdView.getAdUnitId());
    }

    @Override
    public void onAdImpression() {
        super.onAdImpression();
        Log.d(TAG, "onAdImpression: " + mAdView.getAdUnitId());
    }
}
