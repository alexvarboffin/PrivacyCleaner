package com.walhalla.privacycleaner.repository;

/**
 *
 * Repository
 *
 */
public interface PrivacyCleaner {

    interface Callback<T> {

        void successResult(T data);

        void onFailure(Exception e);

        void onProgress(String finI);

        void mailboxEmpty(String response);
    }

    void init();
}
