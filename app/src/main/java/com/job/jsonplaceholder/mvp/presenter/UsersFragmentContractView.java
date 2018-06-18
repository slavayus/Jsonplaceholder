package com.job.jsonplaceholder.mvp.presenter;

import com.job.jsonplaceholder.pojo.User;

import java.util.List;

public interface UsersFragmentContractView {
    void showUsers(List<User> users);
}
