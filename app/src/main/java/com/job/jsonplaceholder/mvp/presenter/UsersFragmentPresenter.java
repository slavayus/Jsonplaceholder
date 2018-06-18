package com.job.jsonplaceholder.mvp.presenter;

import com.job.jsonplaceholder.mvp.model.UsersFragmentContractModel;
import com.job.jsonplaceholder.pojo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class UsersFragmentPresenter {

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
        model.downloadUsers(new UsersFragmentContractModel.OnDownloadUsers() {

            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    if (viewIsValid()) {
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

            }
        });
    }

    private boolean viewIsValid() {
        return view.get() != null;
    }
}
