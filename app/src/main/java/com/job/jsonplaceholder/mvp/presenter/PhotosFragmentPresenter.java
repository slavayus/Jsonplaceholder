package com.job.jsonplaceholder.mvp.presenter;

import com.job.jsonplaceholder.mvp.model.PhotosFragmentContractModel;
import com.job.jsonplaceholder.pojo.Photo;

import java.lang.ref.WeakReference;
import java.util.List;

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
        model.downloadUserPhotos(view.get().gerUser(), new PhotosFragmentContractModel.OnDownloadPhotos(){

            @Override
            public void onSuccess(List<Photo> photos) {
                if (viewIsValid()) {
                    view.get().showPhotos(photos);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private boolean viewIsValid() {
        return view.get() != null;
    }

    public void destroyView() {
        view.clear();
    }
}
