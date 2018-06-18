package com.job.jsonplaceholder.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.job.jsonplaceholder.R;
import com.job.jsonplaceholder.mvp.view.OnUserClickListener;
import com.job.jsonplaceholder.mvp.view.UsersFragment;
import com.job.jsonplaceholder.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {
    private final OnUserClickListener userClickListener;
    private List<User> mData = new ArrayList<>();

    public UsersAdapter(List<User> users, OnUserClickListener userClickListener) {
        this.userClickListener = userClickListener;
        mData.addAll(users);
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_users, parent, false);
        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class UsersHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private ConstraintLayout userLayout;

        UsersHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.user_name);
            userLayout = view.findViewById(R.id.user_layout);
        }

        void bind(User user) {
            userName.setText(user.getName());
            userLayout.setOnClickListener(v -> userClickListener.onClick(user));
        }

    }

}
