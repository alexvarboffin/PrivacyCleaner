package com.walhalla.privacycleaner.mvp.controller;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.walhalla.boilerplate.domain.executor.impl.BackgroundExecutor;
import com.walhalla.boilerplate.threading.MainThreadImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import aa.model.GmailMessageViewModel;
import com.walhalla.privacycleaner.GmailInteractorImpl;
import com.walhalla.privacycleaner.LStorage;
import com.walhalla.privacycleaner.R;

import com.walhalla.privacycleaner.helper.InternetDetector;
import com.walhalla.privacycleaner.helper.Utils;
import com.walhalla.privacycleaner.mvp.view.MainView;
import com.walhalla.privacycleaner.repository.PrivacyCleaner;

public class MainPresenter {

    private final String appName;
    private final MainView mView;
    private final InternetDetector __a;

    private GmailInteractorImpl interactor;
    public GoogleAccountCredential mCredential;
    //    private static final String[] SCOPES = {
//
////Send message
////            GmailScopes.GMAIL_LABELS,
////            GmailScopes.GMAIL_COMPOSE,
////            GmailScopes.GMAIL_INSERT,
////            GmailScopes.GMAIL_MODIFY,
//
//            //Remove all
//            //@@@ GmailScopes.GMAIL_LABELS, //Manage mailbox labels.
//            //@@@GmailScopes.GMAIL_COMPOSE,//Manage drafts and send emails.
//            //GmailScopes.GMAIL_INSERT,
//            //@@@ GmailScopes.GMAIL_MODIFY, //View and modify but not delete your email.
//            //@@@ GmailScopes.GMAIL_READONLY, //View your email messages and settings.
//            GmailScopes.MAIL_GOOGLE_COM, //Read, compose, send, and permanently delete all your email from Gmail.
//            //GmailScopes.GMAIL_METADATA
//    };
    private static final String[] SCOPES = {

//Send message
//            GmailScopes.GMAIL_LABELS,
//            GmailScopes.GMAIL_COMPOSE,
//            GmailScopes.GMAIL_INSERT,
//            GmailScopes.GMAIL_MODIFY,

            //Remove all
            GmailScopes.GMAIL_LABELS, //Manage mailbox labels.
            GmailScopes.GMAIL_COMPOSE,//Manage drafts and send emails.
            GmailScopes.GMAIL_INSERT,
            GmailScopes.GMAIL_MODIFY, //View and modify but not delete your email.
            GmailScopes.GMAIL_READONLY, //View your email messages and settings.
            GmailScopes.MAIL_GOOGLE_COM, //Read, compose, send, and permanently delete all your email from Gmail.
            GmailScopes.GMAIL_METADATA,
    };

    public MainPresenter(Context context, MainView mainView, InternetDetector internetDetector, String appName) {
        getCredentials(context);
        this.appName = appName;
        this.mView = mainView;
        this.__a = internetDetector;
    }

    public void getCredentials(Context context) {
        mCredential = null;
        mCredential = GoogleAccountCredential
                .usingOAuth2(context.getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }

    /**
     * @param context
     * @param mListener - active action screen
     */
    public void getResultsFromApi(Context context, @Nullable PrivacyCleaner.Callback<Set<Message>> mListener) {
        Set<String> stringSet = LStorage.getInstance(context).whiteListLabels();

//        Account aaa = presenter.mCredential.getSelectedAccount();
//        DLog.d("Account --> " + aaa);
//        presenter.mCredential.
        if (!Utils.isGooglePlayServicesAvailable(context)) {
            acquireGooglePlayServices(context);
        } else if (mCredential.getSelectedAccountName() == null) {
            if (mView != null) {
                mView.chooseAccount();
            }
        } else if (!__a.checkMobileInternetConn()) {
            if (mView != null) {
                mView.showMessage(R.string.no_network_connection_available);
            }
//        } else if (!Utils.isNotEmpty(edtToAddress)) {
//            showMessage(view, "To address Required");
//        } else if (!Utils.isNotEmpty(edtSubject)) {
//            showMessage(view, "Subject Required");
//        } else if (!Utils.isNotEmpty(edtMessage)) {
//            showMessage(view, "GmailMessageViewModel Required");
        } else {
            interactor = new GmailInteractorImpl(
                    //this,
                    appName,
                    mCredential,
                    BackgroundExecutor.getInstance(),
                    MainThreadImpl.getInstance()
            );
            interactor.getAllMessageListByTag("", stringSet, mListener);
        }
    }

    // Method to Show Info, If Google Play Service is Not Available.
    private void acquireGooglePlayServices(Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            if (mView != null) {
                mView.showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            }
        }
    }

    public void deleteGmailMessages(List<GmailMessageViewModel> obj) {
        interactor.deleteGmailMessages(obj);
    }
}
