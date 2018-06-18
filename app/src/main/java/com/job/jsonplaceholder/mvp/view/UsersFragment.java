package com.job.jsonplaceholder.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.jsonplaceholder.R;
import com.job.jsonplaceholder.mvp.model.UsersFragmentModel;
import com.job.jsonplaceholder.mvp.presenter.UsersFragmentContractView;
import com.job.jsonplaceholder.mvp.presenter.UsersFragmentPresenter;
import com.job.jsonplaceholder.mvp.view.adapter.UsersAdapter;
import com.job.jsonplaceholder.pojo.User;

import java.util.List;

public class UsersFragment extends Fragment implements UsersFragmentContractView {
    private RecyclerView mRecyclerView;
    private UsersFragmentPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_users);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter = new UsersFragmentPresenter(new UsersFragmentModel());
        mPresenter.attachView(this);
        mPresenter.viewIsReady();

        return view;
    }

    public static Fragment newInstance() {

        return new UsersFragment();
    }

    @Override
    public void showUsers(List<User> users) {
        mRecyclerView.setAdapter(new UsersAdapter(users));
    }
}
