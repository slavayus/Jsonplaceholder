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
import com.job.jsonplaceholder.mvp.model.PhotosFragmentModel;
import com.job.jsonplaceholder.mvp.presenter.PhotosFragmentContractView;
import com.job.jsonplaceholder.mvp.presenter.PhotosFragmentPresenter;
import com.job.jsonplaceholder.mvp.view.adapter.PhotosAdapter;
import com.job.jsonplaceholder.pojo.Photo;
import com.job.jsonplaceholder.pojo.User;

import java.util.List;

public class PhotosFragment extends Fragment implements PhotosFragmentContractView {
    private static final String USER = "USER";
    private User user;
    private RecyclerView mRecyclerView;
    private PhotosFragmentPresenter mPresenter;
    private PhotosAdapter mPhotosAdapter;

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

        mRecyclerView = view.findViewById(R.id.recycler_view_photos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPhotosAdapter = new PhotosAdapter();
        mRecyclerView.setAdapter(mPhotosAdapter);

        mPresenter = new PhotosFragmentPresenter(new PhotosFragmentModel());
        mPresenter.attachView(this);
        mPresenter.viewIsReady();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroyView();
    }

    @Override
    public User gerUser() {
        return user;
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        mPhotosAdapter.addPhotos(photos);
    }

    public static PhotosFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        PhotosFragment fragment = new PhotosFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
