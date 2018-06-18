package com.job.jsonplaceholder.model;

/**
 * Created by slavik on 6/18/18.
 */

public interface UsersFragmentContractModel {
    void downloadUsers(OnDownloadUsers onDownloadUsers);

    interface OnDownloadUsers {
        void onSuccess();

        void onError();
    }
}
