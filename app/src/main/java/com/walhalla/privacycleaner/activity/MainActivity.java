//package com.walhalla.privacycleaner.activity;
//
//import android.Manifest;
//import android.accounts.AccountManager;
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.text.TextUtils;
//import android.text.method.ScrollingMovementMethod;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.material.snackbar.Snackbar;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
//import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
//import com.google.api.client.util.ExponentialBackOff;
//import com.google.api.services.youtube.YouTubeScopes;
//
//import com.walhalla.privacycleaner.helper.Utils;
//import com.walhalla.privacycleaner.mvp.view.MainView;
////import com.google.api.services.youtube.model.Comment;
////import com.google.api.services.youtube.model.CommentListResponse;
////import com.google.api.services.youtube.model.CommentSnippet;
////import com.google.api.services.youtube.model.CommentThread;
////import com.google.api.services.youtube.model.CommentThreadListResponse;
//
//import java.util.Arrays;
//import java.util.List;
//
//
//import pub.devrel.easypermissions.AfterPermissionGranted;
//import pub.devrel.easypermissions.EasyPermissions;
//
//import static com.walhalla.privacycleaner.helper.Utils.REQUEST_AUTHORIZATION;
//
//public class MainActivity extends AppCompatActivity
//        implements EasyPermissions.PermissionCallbacks,
//        MainView {
//
//    private static final String TAG = "@@@";
//
//    private GoogleAccountCredential mCredential;
//    private TextView mOutputText;
//    private Button mCallApiButton;
//    ProgressDialog mProgress;
//
//    static final int REQUEST_ACCOUNT_PICKER = 1000;
//
//    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
//    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
//
//    private static final String BUTTON_TEXT = "Call YouTube Data API";
//    private static final String PREF_ACCOUNT_NAME = "acc_name_1";
//
//
//    private static final String[] SCOPES =
//            {
//                    YouTubeScopes.YOUTUBE,
//                    //YouTubeScopes.YOUTUBE_FORCE_SSL,//Comments
//                    YouTubeScopes.YOUTUBE_READONLY,//Read list
//                    YouTubeScopes.YOUTUBE_UPLOAD,
//                    YouTubeScopes.YOUTUBEPARTNER,
//                    YouTubeScopes.YOUTUBEPARTNER_CHANNEL_AUDIT
//            };
//
//    /**
//     * Create the main activity.
//     *
//     * @param savedInstanceState previously saved instance data.
//     */
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////
////        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//
//
////        try {
////            //GmailQuickstart.main();
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (GeneralSecurityException e) {
////            e.printStackTrace();
////        }
//
//        LinearLayout activityLayout = new LinearLayout(this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        activityLayout.setLayoutParams(lp);
//        activityLayout.setOrientation(LinearLayout.VERTICAL);
//        activityLayout.setPadding(16, 16, 16, 16);
//
//        ViewGroup.LayoutParams tlp = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        mCallApiButton = new Button(this);
//        mCallApiButton.setText(BUTTON_TEXT);
//        mCallApiButton.setOnClickListener(v -> {
//            mCallApiButton.setEnabled(false);
//            mOutputText.setText("");
//            getResultsFromApi(aaaa);
//            mCallApiButton.setEnabled(true);
//        });
//        activityLayout.addView(mCallApiButton);
//
//        mOutputText = new TextView(this);
//        mOutputText.setLayoutParams(tlp);
//        mOutputText.setPadding(16, 16, 16, 16);
//        mOutputText.setVerticalScrollBarEnabled(true);
//        mOutputText.setMovementMethod(new ScrollingMovementMethod());
//        mOutputText.setText(
//                "Click the \'" + BUTTON_TEXT + "\' button to test the API.");
//        activityLayout.addView(mOutputText);
//
//        mProgress = new ProgressDialog(this);
//        mProgress.setMessage("Calling YouTube Data API ...");
//
//        setContentView(activityLayout);
//
//        // Initialize credentials and service object.
//        mCredential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), Arrays.asList(SCOPES))
//                .setBackOff(new ExponentialBackOff());
//
//        //Credential credential = Auth.authorize(Arrays.asList(SCOPES), "commentthreads");
//
//    }
//
//
//
//
//    /**
//     * Attempts to set the account used with the API credentials. If an account
//     * name was previously saved it will use that one; otherwise an account
//     * picker dialog will be shown to the user. Note that the setting the
//     * account to use with the credentials object requires the app to have the
//     * GET_ACCOUNTS permission, which is requested here if it is not already
//     * present. The AfterPermissionGranted annotation indicates that this
//     * function will be rerun automatically whenever the GET_ACCOUNTS permission
//     * is granted.
//     */
//    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
//    private void chooseAccount() {
//        if (EasyPermissions.hasPermissions(
//                this, Manifest.permission.GET_ACCOUNTS)) {
//            String accountName = context.getSharedPreferences(Context.MODE_PRIVATE)
//                    .getString(PREF_ACCOUNT_NAME, null);
//            if (accountName != null) {
//                mCredential.setSelectedAccountName(accountName);
//                getResultsFromApi(aaaa);
//            } else {
//                // Start a dialog from which the user can choose an account
//                startActivityForResult(
//                        mCredential.newChooseAccountIntent(),
//                        REQUEST_ACCOUNT_PICKER);
//            }
//        } else {
//            // Request the GET_ACCOUNTS permission via a user dialog
//            EasyPermissions.requestPermissions(
//                    this,
//                    "This app needs to access your Google account (via Contacts).",
//                    REQUEST_PERMISSION_GET_ACCOUNTS,
//                    Manifest.permission.GET_ACCOUNTS);
//        }
//    }
//
//    /**
//     * Called when an activity launched here (specifically, AccountPicker
//     * and authorization) exits, giving you the requestCode you started it with,
//     * the resultCode it returned, and any additional data from it.
//     *
//     * @param requestCode code indicating which activity result is incoming.
//     * @param resultCode  code indicating the result of the incoming
//     *                    activity result.
//     * @param data        Intent (containing result data) returned by incoming
//     *                    activity result.
//     */
//    @Override
//    protected void onActivityResult(
//            int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case REQUEST_GOOGLE_PLAY_SERVICES:
//                if (resultCode != RESULT_OK) {
//                    mOutputText.setText(
//                            "This app requires Google Play Services. Please install " +
//                                    "Google Play Services on your device and relaunch this app.");
//                } else {
//                    getResultsFromApi(aaaa);
//                }
//                break;
//            case REQUEST_ACCOUNT_PICKER:
//                if (resultCode == RESULT_OK && data != null &&
//                        data.getExtras() != null) {
//                    String accountName =
//                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
//                    if (accountName != null) {
//                        SharedPreferences settings =
//                                context.getSharedPreferences(Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = settings.edit();
//                        editor.putString(PREF_ACCOUNT_NAME, accountName);
//                        editor.apply();
//                        mCredential.setSelectedAccountName(accountName);
//                        getResultsFromApi(aaaa);
//                    }
//                }
//                break;
//            case REQUEST_AUTHORIZATION:
//                if (resultCode == RESULT_OK) {
//                    getResultsFromApi(aaaa);
//                }
//                break;
//        }
//    }
//
//    /**
//     * Respond to requests for permissions at runtime for API 23 and above.
//     *
//     * @param requestCode  The request code passed in
//     *                     requestPermissions(android.app.Activity, String, int, String[])
//     * @param permissions  The requested permissions. Never null.
//     * @param grantResults The grant results for the corresponding permissions
//     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(
//                requestCode, permissions, grantResults, this);
//    }
//
//    /**
//     * RadarFragmentPresenter for when a permission is granted using the EasyPermissions
//     * library.
//     *
//     * @param requestCode The request code associated with the requested
//     *                    permission
//     * @param list        The requested permission list. Never null.
//     */
//    @Override
//    public void onPermissionsGranted(int requestCode, @NonNull List<String> list) {
//        // Do nothing.
//    }
//
//    /**
//     * RadarFragmentPresenter for when a permission is denied using the EasyPermissions
//     * library.
//     *
//     * @param requestCode The request code associated with the requested
//     *                    permission
//     * @param list        The requested permission list. Never null.
//     */
//    @Override
//    public void onPermissionsDenied(int requestCode, @NonNull List<String> list) {
//        // Do nothing.
//    }
//
//    /**
//     * Checks whether the device currently has a network connection.
//     *
//     * @return true if the device has a network connection, false otherwise.
//     */
//    private boolean isDeviceOnline() {
//        ConnectivityManager connMgr =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }
//
//
//    /**
//     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
//     * Play Services installation via a user dialog, if possible.
//     */
//    private void acquireGooglePlayServices() {
//        GoogleApiAvailability apiAvailability =
//                GoogleApiAvailability.getInstance();
//        final int connectionStatusCode =
//                apiAvailability.isGooglePlayServicesAvailable(this);
//        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
//            showGooglePlayServicesAvailabilityErrorDialog(this, connectionStatusCode);
//        }
//    }
//
//
//    /**
//     * Display an error dialog showing that Google Play Services is missing
//     * or out of date.
//     *
//     * @param connectionStatusCode code describing the presence (or lack of)
//     *                             Google Play Services on this device.
//     */
//    static void showGooglePlayServicesAvailabilityErrorDialog(Activity activity, final int connectionStatusCode) {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        Dialog dialog = apiAvailability.getErrorDialog(
//                activity, connectionStatusCode, REQUEST_GOOGLE_PLAY_SERVICES);
//        dialog.show();
//    }
//
//    /**
//     * Attempt to call the API, after verifying that all the preconditions are
//     * satisfied. The preconditions are: Google Play Services installed, an
//     * account was selected and the device currently has online access. If any
//     * of the preconditions are not satisfied, the app will prompt the user as
//     * appropriate.
//     */
//    //@Override
//    public void getResultsFromApi(View view) {
//        if (!Utils.isGooglePlayServicesAvailable(this)) {
//            acquireGooglePlayServices();
//        } else if (mCredential.getSelectedAccountName() == null) {
//            chooseAccount();
//        } else if (!isDeviceOnline()) {
//            errorResponse("No network connection available.");
//        } else {
//            //    new MakeRequestTask(this, "999", mCredential).execute();
//        }
//    }
//
//    @Override
//    public void showProgress() {
//        this.mProgress.show();
//    }
//
//    @Override
//    public void hideProgress() {
//        this.mProgress.hide();
//    }
//
//    @Override
//    public void handleError(Exception mLastError) {
//        if (mLastError != null) {
//            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
//                showGooglePlayServicesAvailabilityErrorDialog(this,
//                        ((GooglePlayServicesAvailabilityIOException) mLastError)
//                                .getConnectionStatusCode());
//            } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                startActivityForResult(
//                        ((UserRecoverableAuthIOException) mLastError).getIntent(),
//                        REQUEST_AUTHORIZATION);
//            } else {
//                showMessage(null, "The following error occurred:\n" + mLastError);
//                Log.d(TAG, mLastError + "");
//            }
//        } else {
//            showMessage(null, "Request Cancelled.");
//        }
//    }
//
//    @Override
//    public void showMessage(Object o, String s) {
//
//    }
//
//    public void showMessage(View view, String message) {
//        if (view == null) {
//            //        view = submit;
//        }
//        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
//        Log.d(TAG, "showMessage: " + message);
//    }
//
//    @Override
//    public void errorResponse(String m) {
//        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mProgress != null && mProgress.isShowing()) {
//            mProgress.cancel();
//        }
//    }
//
//
//    public void onPreExecute() {
//        mOutputText.setText("");
//        mProgress.show();
//    }
//
////    @Override
////    public void onPostExecute(String output) {
////
////    }
////
////    @Override
////    public void onCancelled(Exception mLastError) {
////
////    }
//
//
//    public void onPostExecute(List<String> output) {
//        mProgress.hide();
//        if (output == null || output.size() == 0) {
//            mOutputText.setText("No results returned.");
//        } else {
//            output.add(0, "Data retrieved using the YouTube Data API:");
//            mOutputText.setText(TextUtils.join("\n", output));
//        }
//    }
//
//    public void onCancelled(String s) {
//        mOutputText.setText(s);
//    }
//
//    public void hide() {
//        mProgress.hide();
//    }
//}
