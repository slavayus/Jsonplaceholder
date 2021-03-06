package com.job.jsonplaceholder.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
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

public class PhotosFragment extends Fragment implements PhotosFragmentContractView, PhotosAdapter.Loader {
    private static final String USER = "USER";
    private User user;
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

        if (getActivity() != null) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar!=null) {
                supportActionBar.setTitle(user.getName());
            }
        }

        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view_photos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPhotosAdapter = new PhotosAdapter(this);
        mRecyclerView.setAdapter(mPhotosAdapter);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mPresenter = new PhotosFragmentPresenter(new PhotosFragmentModel(getContext()));
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

    @Override
    public List<Photo> getPhotos() {
        return mPhotosAdapter.gePhotos();
    }

    @Override
    public void notifyImageLoaded(int index) {
        mPhotosAdapter.notifyItemChanged(index);
    }

    @Override
    public void notifyImageUpdated(int index) {
        mPhotosAdapter.notifyItemChanged(index);
    }

    public static PhotosFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        PhotosFragment fragment = new PhotosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void load(Photo photo) {
        mPresenter.loadBitmap(photo);
    }
}
