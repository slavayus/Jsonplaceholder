package com.job.jsonplaceholder.presenter;

import com.job.jsonplaceholder.model.UsersFragmentContractModel;

/**
 * Created by slavik on 6/18/18.
 */

public class UsersFragmentPresenter {

    private final UsersFragmentContractModel model;
    private UsersFragmentContractView view;

    public UsersFragmentPresenter(UsersFragmentContractModel model) {
        this.model = model;
    }

    public void attachView(UsersFragmentContractView view) {
        this.view = view;
    }

    public void viewIsReady() {
        downloadUsers();
    }

    private void downloadUsers() {
        model.downloadUsers(new UsersFragmentContractModel.OnDownloadUsers(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

    }
}
