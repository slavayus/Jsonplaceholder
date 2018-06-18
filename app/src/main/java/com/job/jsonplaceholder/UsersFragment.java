package com.job.jsonplaceholder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.jsonplaceholder.model.UsersFragmentModel;
import com.job.jsonplaceholder.presenter.UsersFragmentContractView;
import com.job.jsonplaceholder.presenter.UsersFragmentPresenter;

public class UsersFragment extends Fragment implements UsersFragmentContractView {
    private RecyclerView mRecyclerView;
    private UsersFragmentPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_users);

        mPresenter = new UsersFragmentPresenter(new UsersFragmentModel());
        mPresenter.attachView(this);
        mPresenter.viewIsReady();

        return view;
    }

    public static Fragment newInstance() {

        return new UsersFragment();
    }
}
