package com.job.jsonplaceholder.model;

import org.json.JSONArray;

public interface UsersFragmentContractModel {
    void downloadUsers(OnDownloadUsers onDownloadUsers);

    interface OnDownloadUsers {
        void onSuccess(JSONArray jObj);

        void onError();
    }
}
