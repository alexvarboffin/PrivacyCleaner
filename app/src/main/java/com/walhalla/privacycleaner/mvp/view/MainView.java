package com.walhalla.privacycleaner.mvp.view;


import androidx.annotation.Nullable;

import com.google.api.services.gmail.model.Message;

import java.util.List;
import java.util.Set;

import aa.model.GmailMessageViewModel;
import com.walhalla.privacycleaner.fragment.BlankFragment;
import com.walhalla.privacycleaner.repository.PrivacyCleaner;

public interface MainView {

    void showProgress();

    void errorResponse(String m);

    void hideProgress();

    void handleError(Exception mLastError, BlankFragment fragment);

    void showMessage(int s);

    void showGooglePlayServicesAvailabilityErrorDialog(int connectionStatusCode);

    void chooseAccount();

    void deleteGmailMessagesRequest(List<GmailMessageViewModel> delete_all_data);

    void getResultsFromApi(@Nullable PrivacyCleaner.Callback<Set<Message>> mListener);
}
