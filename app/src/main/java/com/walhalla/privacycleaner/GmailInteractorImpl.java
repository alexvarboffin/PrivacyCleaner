package com.walhalla.privacycleaner;

import androidx.annotation.NonNull;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.BatchDeleteMessagesRequest;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.walhalla.boilerplate.domain.executor.Executor;
import com.walhalla.boilerplate.domain.executor.MainThread;

import com.walhalla.boilerplate.domain.interactors.base.AbstractInteractor;
import com.walhalla.ui.DLog;

import aa.model.GmailMessageViewModel;
import com.walhalla.privacycleaner.repository.GmailInteractor;
import com.walhalla.privacycleaner.repository.PrivacyCleaner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GmailInteractorImpl extends AbstractInteractor implements GmailInteractor {

    private static final String CURRENT_USER = "me"; //me [ alexvarboffin@gmail.com == me ]

    public GmailInteractorImpl(
            //MakeRequestTask.RadarFragmentPresenter activity,
            String appName,
            GoogleAccountCredential credential, Executor threadExecutor, MainThread mainThread) {
        super(threadExecutor, mainThread);
        //this.weakReference = new WeakReference<MakeRequestTask.RadarFragmentPresenter>(activity);
        //HttpTransport transport = AndroidHttp.newCompatibleTransport();
        HttpTransport transport = new NetHttpTransport();

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        this.mGmail = new com.google.api.services.gmail.Gmail.Builder(
                transport, jsonFactory, credential)
                .setApplicationName(appName)
                .build();
    }


    public GmailInteractorImpl(Gmail mGmail, Executor threadExecutor, MainThread mainThread) {
        super(threadExecutor, mainThread);
        this.mGmail = mGmail;
    }

    private final Gmail mGmail;
    //private final WeakReference<MainView> weakReference;
    private Exception mLastError = null;

//    public GmailHelper(//, MainView activity
//    ) {
//
//        //this.weakReference = new WeakReference<MainView>(activity);
//    }

    @Override
    public void getDataFromApi() {

    }

    @Override
    public void getAllMessageListByTag(String tag, Set<String> stringSet, Callback<Set<Message>> callback) {
        mThreadExecutor.submit(() -> {
            try {
//        Gmail.Users.History.List cc = mGmail.users().history().list(CURRENT_USER);
//        cc.setPrettyPrint(true);
//        Log.d(TAG, "getAllMessageListByTag: "+cc);

//mGmail.users().messages().attachments().get()
//                "UNREAD",
//                        "IMPORTANT",
//                        "CATEGORY_PERSONAL",
//                        "INBOX"
//
//                "CATEGORY_PERSONAL",
//                        "INBOX"
//
//                "UNREAD",
//                        "CATEGORY_UPDATES",
//                        "INBOX"
//
//                "SENT"
//
//                "UNREAD",
//                        "CATEGORY_SOCIAL",
//                        "INBOX"
//
//                "CATEGORY_PROMOTIONS",
//                        "UNREAD",
//                        "INBOX"
//
//                "UNREAD",
//                        "IMPORTANT",
//                        "CATEGORY_UPDATES",
//                        "INBOX"
//
//                'CATEGORY_UPDATES',
//                        'CATEGORY_PROMOTIONS',
//                        'CATEGORY_SOCIAL',
//                        'CATEGORY_FORUMS',
//                        'TRASH',
//                        'CHAT',
//
//        try {

                Set<Message> all_data_buffer = new HashSet<>();
                ListMessagesResponse response = null; //tmp

                for (String key : stringSet) {
                    String[] mm = key.split("\\|");
                    //List<String> aaa = new ArrayList<>(mm.length);
                    List<String> labelIds = Arrays.asList(mm);
                    Gmail.Users.Messages.List query = mGmail.users().messages().list(CURRENT_USER);
                    query.setMaxResults(999L);
//                    .setQ(tag)
//                    .setPageToken(null)
//                query.setIncludeSpamTrash(true);
                    if (!labelIds.isEmpty()) {
                        query.setLabelIds(labelIds); //Include messages from SPAM and TRASH in the results
                    }
                    //.setMaxResults(1L)
                    response = query.execute();

//DLog.d( "getAllMessageListByTag: " + response.toPrettyString());

//                    if (callback != null && response.getResultSizeEstimate() > 0) {
//                        mMainThread.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                callback.successResult(response.getMessages());
//                            }
//                        });
//                    }
                    while (response.getMessages() != null) {
                        final String finI = key + " - " + response.toString();
                        DLog.d(response.getMessages().size() + " | " + response.getNextPageToken());

                        //messages.addAll(response.getMessages());
                        final List<Message> list = response.getMessages();
                        if (list != null) {
                            all_data_buffer.addAll(list);
                        }
                        if (callback != null) {
                            mMainThread.post(() -> callback.onProgress(finI));
                        }
                        if (response.getNextPageToken() != null) {
                            String pageToken = response.getNextPageToken();
                            Gmail.Users.Messages.List raw0 = mGmail.users().messages()
                                    .list(CURRENT_USER);
                            //.setQ(tag)
                            raw0.setPageToken(pageToken);
                            //                query.setIncludeSpamTrash(true);
                            if (!labelIds.isEmpty()) {
                                raw0.setLabelIds(labelIds); //Include messages from SPAM and TRASH in the results
                            }
                            //.setMaxResults(1L)
                            response = raw0.execute();
                        } else {
                            break;
                        }
                    }
//                    if (callback != null) {
//                        mMainThread.post(() -> callback.successResult(all_data_buffer));
//                    }
//                    if (all_data_buffer.isEmpty()) {
//                        if (callback != null) {
//                            ListMessagesResponse finalResponse = response;
//                            mMainThread.post(() ->
//                                    {
//                                        callback.mailboxEmpty(finalResponse.toString()
//                                                //+ response.getResultSizeEstimate()
//                                        );
//                                    }
//                            );
//
//                        }
//                        return;
//                    }
                }

                if (callback != null) {
                    mMainThread.post(() -> callback.successResult(all_data_buffer));
                }
                if (all_data_buffer.isEmpty()) {
                    if (callback != null && response != null) {
                        ListMessagesResponse finalResponse = response;
                        mMainThread.post(() ->
                                {
                                    callback.mailboxEmpty(finalResponse.toString()
                                            //+ response.getResultSizeEstimate()
                                    );
                                }
                        );

                    }
                    return;
                }
                DLog.d("=====================================");

//        } catch (UserRecoverableAuthIOException e) {
//            DLog.d( "@@@: " + e.getClass().getSimpleName() + "@@@");
//            if (callback != null) {
//            //    callback.errorResult("Exception " + e.getLocalizedMessage());
//            }
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
            } catch (Exception e) {
                DLog.handleException(e);
                mMainThread.post(() -> {
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                });
            }
        });
    }

    @Override
    public void deleteAllGmailMessages() {

    }

    @Override
    public void deleteOneGmailMessages(String id) {

    }

    @Override
    public void deleteGmailMessagesByIds(List<String> ids) {
        mThreadExecutor.submit(() -> {
            BatchDeleteMessagesRequest request = new BatchDeleteMessagesRequest();
            request.setIds(ids);
            try {
                mGmail.users()
                        .messages().batchDelete(CURRENT_USER, request)
                        .execute();
            } catch (Exception e) {
                DLog.handleException(e);
            }
        });
    }

    /**
     * 1000 ids limit
     */
    @Override
    public void deleteGmailMessages(@NonNull List<GmailMessageViewModel> messages) {
//        List<String> ids = new ArrayList<>();
//        for (GmailMessageViewModel message : messages) {
//            ids.add(message.getId());
//        }
//        deleteGmailMessagesByIds(ids);


        mThreadExecutor.submit(() -> {

            List<String> ids = new ArrayList<>();
            for (int i = 0; i < messages.size(); i++) {
                ids.add(messages.get(i).id);

                if (i == 990) {
                    BatchDeleteMessagesRequest request = new BatchDeleteMessagesRequest();
                    request.setIds(ids);
                    try {
                        mGmail.users()
                                .messages().batchDelete(CURRENT_USER, request)
                                .execute();
                    } catch (Exception e) {
                        DLog.handleException(e);
                    }
                    ids = new ArrayList<>();
                }
            }

            if (!ids.isEmpty()) {
                BatchDeleteMessagesRequest request = new BatchDeleteMessagesRequest();
                request.setIds(ids);
                try {
                    mGmail.users()
                            .messages().batchDelete(CURRENT_USER, request)
                            .execute();
                } catch (Exception e) {
                    DLog.handleException(e);
                }
            }
        });
    }

    public void readMessages(String id, PrivacyCleaner.Callback<Message> callback) {
        mThreadExecutor.submit(() -> {
            try {
                final Message message = mGmail.users().messages().get(CURRENT_USER, id).execute();
                //System.out.println(message1.getPayload().getHeaders());
                //System.out.println(message1.toPrettyString());
                //mailExtractor(message.toPrettyString());
                if (callback != null) {
                    mMainThread.post(() -> callback.successResult(message));
                }
            } catch (Exception e) {
                mMainThread.post(() -> callback.onFailure(e));
            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {

    }
}
