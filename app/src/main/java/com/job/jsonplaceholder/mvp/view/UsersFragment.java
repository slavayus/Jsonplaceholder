package com.job.jsonplaceholder.mvp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
    private ProgressDialog dialog;

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

    @Override
    public void showProgressDialog() {
        dialog = new ProgressDialog(this.getContext());
        dialog.setMessage(getResources().getString(R.string.loading_users));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void showErrorLoadingUsersDialog() {
        if (getActivity() == null) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.loading_users_error_title)
                .setMessage(R.string.loading_users_error_message)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> getActivity().finishAffinity())
                .setCancelable(false)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
