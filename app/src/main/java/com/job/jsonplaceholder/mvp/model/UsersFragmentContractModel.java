package com.job.jsonplaceholder.mvp.model;

import org.json.JSONArray;

public interface UsersFragmentContractModel {
    void downloadUsers(OnDownloadUsers onDownloadUsers);

    interface OnDownloadUsers {
        void onSuccess(JSONArray jsonArray);

        void onError();
    }
}
