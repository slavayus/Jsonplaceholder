package com.job.jsonplaceholder.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.job.jsonplaceholder.R;
import com.job.jsonplaceholder.pojo.User;

public class PhotosFragment extends Fragment {
    private static final String USER = "USER";
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromArguments();
    }

    private void readFromArguments() {
        if (getArguments() == null) {
            throw new IllegalArgumentException("There is no user in arguments");
        } else {
            user = getArguments().getParcelable(USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        TextView textView = view.findViewById(R.id.user_name);
        textView.setText(user.getName());

        return view;
    }

    public static PhotosFragment newInstance(User user) {

        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        PhotosFragment fragment = new PhotosFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
