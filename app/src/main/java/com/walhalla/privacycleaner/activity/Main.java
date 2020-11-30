package com.walhalla.privacycleaner.activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.gmail.model.Message;
import com.walhalla.privacycleaner.AdListener;
import com.walhalla.ui.DLog;
import com.walhalla.ui.Module_U;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import aa.model.GmailMessageViewModel;

import com.walhalla.privacycleaner.R;

import com.walhalla.privacycleaner.config.Config;
import com.walhalla.privacycleaner.fragment.BlankFragment;

import com.walhalla.privacycleaner.helper.InternetDetector;
import com.walhalla.privacycleaner.helper.Utils;
import com.walhalla.privacycleaner.mvp.controller.MainPresenter;

import com.walhalla.privacycleaner.mvp.view.MainView;
import com.walhalla.privacycleaner.repository.PrivacyCleaner;

import static com.walhalla.privacycleaner.helper.Utils.REQUEST_AUTHORIZATION;

public class Main extends QBaseActivity
        implements BlankFragment.Callback, MainView {

    private final int SELECT_PHOTO = 1;
    private AdView adView;
    private MainPresenter presenter;
    private static final String PREF_ACCOUNT_NAME = "account_name";
    private InternetDetector internetDetector;
    private Navigation aa;

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aa = new Navigation(this);

        MobileAds.initialize(this, initializationStatus -> {
            //getString(R.string.app_id)
        });

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        adView = findViewById(R.id.adView);
        List<String> testDevices = new ArrayList<>();
        testDevices.add(AdRequest.DEVICE_ID_EMULATOR);

        ///testDevices.add("F8D53F0CF38BF02FB206BD8DA5078BA2");
        //testDevices.add("3A51B5FD01FE03C5A115A8BE80A98C38");

        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDevices)
                .build();
        MobileAds.setRequestConfiguration(requestConfiguration);
        // Start loading the ad in the background.
        adView.loadAd(new AdRequest.Builder().build());
        adView.setAdListener(new AdListener(adView));

        internetDetector = new InternetDetector(getApplicationContext());
        presenter = new MainPresenter(this, this, internetDetector, getResources().getString(R.string.app_name));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(() -> Module_U.checkUpdate(getApplicationContext()));

        if (savedInstanceState == null) {
            aa.homeScreen();
        }
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


    private void getResultsFromApi() {
        final List<Fragment> aa = getSupportFragmentManager().getFragments();
        for (Fragment fragment : aa) {
            if (fragment instanceof PrivacyCleaner.Callback) {
                presenter.getResultsFromApi(this, (PrivacyCleaner.Callback<Set<Message>>) fragment);
            }
        }
    }

    @Override
    public void getResultsFromApi(@Nullable PrivacyCleaner.Callback<Set<Message>> mListener) {
        presenter.getResultsFromApi(this, mListener);
    }

    @Override
    public void showProgress() {
        DLog.d("showProgress: ");
    }

    @Override
    public void errorResponse(String m) {
        DLog.d("@" + m);
    }

    @Override
    public void hideProgress() {
        DLog.d("hideProgress: ");
    }

    //Пришло исключение от фрагмента...
    @Override
    public void handleError(Exception e, @NonNull BlankFragment fragment) {
        if (e != null) {
            DLog.handleException(e);
            if (e instanceof GooglePlayServicesAvailabilityIOException) {


                Main.this.showGooglePlayServicesAvailabilityErrorDialog(
                        ((GooglePlayServicesAvailabilityIOException) e)
                                .getConnectionStatusCode());
            } else if (e instanceof UserRecoverableAuthIOException) {
                startActivityForResult(((UserRecoverableAuthIOException) e).getIntent(),
                        REQUEST_AUTHORIZATION);
            } else if (e instanceof GoogleAuthIOException) {


                DLog.d("aa1" + e.getLocalizedMessage());
                DLog.d("aa2" + e.toString());
                DLog.d("aa3" + e.getMessage());

                //showMessage(null, "The following error occurred:\n" + e);
                GoogleAuthIOException e1 = (GoogleAuthIOException) e;

                DLog.d("aa1" + e1.getCause().getLocalizedMessage());
                DLog.d("aa2" + e1.getCause().getMessage());
                DLog.d("aa3" + e1.getCause().toString());

                fragment.setLabel("GE: " + e1.getCause().getMessage());
                presenter.getCredentials(this);
            } else {
                //onCancelled("The following error occurred:\n" + e.getMessage());
                showMessage("The following error occurred:\n" + e);
                DLog.d(e + "");
            }
        } else {
            //onCancelled("Request cancelled.");
            showMessage("Request Cancelled.");
            String eee = (e == null) ? "NuLL" : (e.getLocalizedMessage() + " " + e.getClass().getSimpleName());
            fragment.setLabel("Error: " + eee);
        }
        if (Config.EXCEPTION_MODE) {
            String eee = (e == null) ? "NuLL" : (e.getLocalizedMessage() + " " + e.getClass().getSimpleName());
            DLog.d("onFailure: " + eee);
        }
    }

    private void showMessage(String resource) {
        Toast.makeText(this, resource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resource) {
        DLog.d("showMessage: " + resource);
        Toast.makeText(this, resource, Toast.LENGTH_SHORT).show();
    }

    @Override
    // Method for Google Play Services Error Info
    public void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                Main.this, connectionStatusCode, Utils.REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    // Storing Mail ID using Shared Preferences
    @Override
    public void chooseAccount() {
        if (Utils.checkPermission(getApplicationContext(), Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE).getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                presenter.mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(presenter.mCredential.newChooseAccountIntent(), Utils.REQUEST_ACCOUNT_PICKER);
            }
        } else {
            ActivityCompat.requestPermissions(Main.this,
                    new String[]{Manifest.permission.GET_ACCOUNTS}, Utils.REQUEST_PERMISSION_GET_ACCOUNTS);
        }
    }

    @Override
    public void deleteGmailMessagesRequest(List<GmailMessageViewModel> delete_all_data) {
        presenter.deleteGmailMessages(delete_all_data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Utils.REQUEST_PERMISSION_GET_ACCOUNTS:
                chooseAccount();
                break;

            case SELECT_PHOTO:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Utils.REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    showMessage(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case Utils.REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        presenter.mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;

            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;

//            case SELECT_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    final Uri imageUri = data.getData();
//                    fileName = getPathFromURI(imageUri);
//                    edtAttachmentData.setText(fileName);
//                }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, "", null, "");
        assert cursor != null;
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

//    @Override
//    public void onPreExecute() {
//        showProgress();
//    }
//
//    @Override
//    public void onPostExecute(String output) {
//        hideProgress();
//        if (output == null || output.length() == 0) {
//            this.showMessage(null, "No results returned.");
//        } else {
//            this.showMessage(null, output);
//        }
//    }
//
//    @Override
//    public void onCancelled(Exception mLastError) {
//        handleError(mLastError);
//        DLog.d( "===== task canceled =====" + mLastError);
//    }
}