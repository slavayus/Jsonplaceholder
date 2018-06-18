package com.job.jsonplaceholder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.jsonplaceholder.R;
import com.job.jsonplaceholder.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {
    private List<User> mData = new ArrayList<>();

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_users, parent, false);
        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class UsersHolder extends RecyclerView.ViewHolder {

        UsersHolder(View view) {
            super(view);
        }

        void bind() {
        }

    }

}
