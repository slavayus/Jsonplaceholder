package com.job.jsonplaceholder.mvp.model;


import com.job.jsonplaceholder.pojo.Photo;
import com.job.jsonplaceholder.pojo.User;

import java.util.List;

public interface PhotosFragmentContractModel {
    void downloadUserPhotos(User user, OnDownloadPhotos onDownloadPhotos);

    interface OnDownloadPhotos {
        void onSuccess(List<Photo> photos);

        void onError();

        void onComplete();
    }
}
