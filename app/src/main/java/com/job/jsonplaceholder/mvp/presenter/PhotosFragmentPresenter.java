package com.job.jsonplaceholder.mvp.presenter;

import com.job.jsonplaceholder.mvp.model.PhotosFragmentContractModel;

import java.lang.ref.WeakReference;

public class PhotosFragmentPresenter {
    private final PhotosFragmentContractModel model;
    private WeakReference<PhotosFragmentContractView> view;

    public PhotosFragmentPresenter(PhotosFragmentContractModel model) {
        this.model = model;
    }

    public void attachView(PhotosFragmentContractView view) {
        this.view = new WeakReference<>(view);
    }

    public void viewIsReady() {
        downloadUserPhotos();
    }

    private void downloadUserPhotos() {
        model.downloadUserPhotos(view.get().gerUser());
    }

    private boolean viewIsValid() {
        return view.get() != null;
    }

    public void destroyView() {
        view.clear();
    }
}
