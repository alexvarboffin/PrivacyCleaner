package com.walhalla.privacycleaner.repository;

import com.google.api.services.gmail.model.Message;
import aa.model.GmailMessageViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface GmailInteractor extends PrivacyCleaner {

    void getDataFromApi();

    void getAllMessageListByTag(String tag, Set<String> stringSet, Callback<Set<Message>> callback) throws IOException;

    //Delete Gmail Messages
    void deleteAllGmailMessages();

    void deleteOneGmailMessages(String id);

    void deleteGmailMessagesByIds(List<String> ids);
    void deleteGmailMessages(List<GmailMessageViewModel> messages);
}
