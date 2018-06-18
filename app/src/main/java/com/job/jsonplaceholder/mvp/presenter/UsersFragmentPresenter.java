package com.job.jsonplaceholder.mvp.presenter;

import android.util.Log;

import com.job.jsonplaceholder.mvp.model.UsersFragmentContractModel;
import com.job.jsonplaceholder.pojo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class UsersFragmentPresenter {

    private static final String TAG = "UsersFragmentPresenter";
    private final UsersFragmentContractModel model;
    private WeakReference<UsersFragmentContractView> view;

    public UsersFragmentPresenter(UsersFragmentContractModel model) {
        this.model = model;
    }

    public void attachView(UsersFragmentContractView view) {
        this.view = new WeakReference<>(view);
    }

    public void viewIsReady() {
        downloadUsers();
    }

    private void downloadUsers() {
        view.get().showProgressDialog();
        model.downloadUsers(new UsersFragmentContractModel.OnDownloadUsers() {

            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d(TAG, "onSuccess: get users");
                try {
                    if (viewIsValid()) {
                        Log.d(TAG, "onSuccess: parse users");
                        view.get().dismissProgressDialog();
                        view.get().showUsers(parseJSONArray(jsonArray));
                    }
                } catch (JSONException e) {
                    onError();
                }
            }

            private List<User> parseJSONArray(JSONArray jsonArray) throws JSONException {
                List<User> users = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    users.add(new User(jsonObject.getInt("id"), jsonObject.getString("name")));
                }
                return users;
            }

            @Override
            public void onError() {
                if (viewIsValid()) {
                    Log.d(TAG, "onError: loading users");
                    view.get().dismissProgressDialog();
                    view.get().showErrorLoadingUsersDialog();
                }
            }
        });
    }

    private boolean viewIsValid() {
        return view.get() != null;
    }
}
