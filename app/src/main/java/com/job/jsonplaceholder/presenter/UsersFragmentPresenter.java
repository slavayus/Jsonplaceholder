package com.job.jsonplaceholder.presenter;

import com.job.jsonplaceholder.model.UsersFragmentContractModel;

import org.json.JSONArray;

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
            public void onSuccess(JSONArray jObj) {
                System.out.println(jObj);
            }

            @Override
            public void onError() {

            }
        });

    }
}
