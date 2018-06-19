package com.job.jsonplaceholder.mvp.model;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.job.jsonplaceholder.pojo.Photo;
import com.job.jsonplaceholder.pojo.User;
import com.job.jsonplaceholder.utils.URLHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PhotosFragmentModel implements PhotosFragmentContractModel {
    private static final String TAG = "PhotosFragmentModel";
    private static final int SUCCESS = 200;
    private static final int ERROR = 400;

    @Override
    public void downloadUserPhotos(User user, OnDownloadPhotos onDownloadPhotos) {
        DownloadUserPhotosHandler downloadUserPhotosHandler = new DownloadUserPhotosHandler(onDownloadPhotos);

        new Thread(() -> {
            try {
                String userAlbumsAsString = downloadFromUri(URLHelper.ALBUMS_BY_USER_URL + user.getId());
                JSONArray albumsAsJsonArray = new JSONArray(userAlbumsAsString);
                List<Photo> photos = downloadPhotos(albumsAsJsonArray);
                downloadUserPhotosHandler.sendMessage(downloadUserPhotosHandler.obtainMessage(SUCCESS, photos));
            } catch (IOException | JSONException e) {
                Log.d(TAG, "downloadUsers: error" + e.getMessage());
                e.printStackTrace();
                downloadUserPhotosHandler.sendEmptyMessage(ERROR);
            }
        }).start();
    }

    private List<Photo> downloadPhotos(JSONArray albumsAsJsonArray) throws JSONException, IOException {
        List<Photo> photosList = new ArrayList<>();
        for (int i = 0; i < albumsAsJsonArray.length(); i++) {
            JSONObject album = albumsAsJsonArray.getJSONObject(i);
            int id = album.getInt("id");
            String photosByAlbumIdAsString = downloadFromUri(URLHelper.PHOTOS_BY_ALBUMS_ID_URL + id);
            JSONArray photosJsonArray = new JSONArray(photosByAlbumIdAsString);
            for (int j = 0; j < photosJsonArray.length(); j++) {
                JSONObject jsonPhoto = photosJsonArray.getJSONObject(j);
                photosList.add(new Photo(jsonPhoto.getString("title"), jsonPhoto.getString("url")));
            }
        }

        return photosList;
    }

    private String downloadFromUri(String uri) throws IOException {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            in.close();
            bufferedReader.close();
            return sb.toString();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private static class DownloadUserPhotosHandler extends Handler {
        private final OnDownloadPhotos onDownloadPhotos;

        DownloadUserPhotosHandler(OnDownloadPhotos onDownloadPhotos) {
            this.onDownloadPhotos = onDownloadPhotos;
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: notified");
            switch (msg.what) {
                case SUCCESS: {
                    Log.d(TAG, "handleMessage: notified success");
                    onDownloadPhotos.onSuccess(((List<Photo>) msg.obj));
                    break;
                }
                case ERROR: {
                    Log.d(TAG, "handleMessage: notified error");
                    onDownloadPhotos.onError();
                }
            }
        }
    }
}
