package com.job.jsonplaceholder.mvp.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.job.jsonplaceholder.utils.URLHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UsersFragmentModel implements UsersFragmentContractModel {
    private static final String TAG = "UsersFragmentModel";
    private DownloadUsersHandler downloadUsersHandler = null;
    private static final int SUCCESS = 200;
    private static final int ERROR = 400;

    @Override
    public void downloadUsers(OnDownloadUsers onDownloadUsers) {
        downloadUsersHandler = new DownloadUsersHandler(onDownloadUsers);

        new Thread(() -> {
            String usersAsString = getUsersAsString();
            Log.d(TAG, "downloadUsers: getUsersAsString");
            if (usersAsString != null) {
                JSONArray usersAsJsonArray = getUsersAsJsonArray(usersAsString);
                Log.d(TAG, "downloadUsers: getUsersAsArray");
                if (usersAsJsonArray != null) {
                    Log.d(TAG, "downloadUsers: notify");
                    downloadUsersHandler.sendMessage(downloadUsersHandler.obtainMessage(SUCCESS, usersAsJsonArray));
                }
            } else {
                Log.d(TAG, "downloadUsers: error");
                downloadUsersHandler.sendEmptyMessage(ERROR);
            }
        }).start();

    }

    private String getUsersAsString() {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(URLHelper.USERS_URL);
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private JSONArray getUsersAsJsonArray(String usersAsString) {
        try {
            return new JSONArray(usersAsString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class DownloadUsersHandler extends Handler {
        private final OnDownloadUsers onDownloadUsers;

        DownloadUsersHandler(OnDownloadUsers onDownloadUsers) {
            this.onDownloadUsers = onDownloadUsers;
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: notified");
            switch (msg.what) {
                case SUCCESS: {
                    Log.d(TAG, "handleMessage: notified success");
                    onDownloadUsers.onSuccess((JSONArray) msg.obj);
                    break;
                }
                case ERROR: {
                    Log.d(TAG, "handleMessage: notified error");
                    onDownloadUsers.onError();
                }
            }
        }
    }
}

