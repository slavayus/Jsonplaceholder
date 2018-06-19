package com.job.jsonplaceholder.mvp.presenter;


import com.job.jsonplaceholder.pojo.Photo;
import com.job.jsonplaceholder.pojo.User;

import java.util.List;

public interface PhotosFragmentContractView {
    User gerUser();

    void showPhotos(List<Photo> photos);
}
