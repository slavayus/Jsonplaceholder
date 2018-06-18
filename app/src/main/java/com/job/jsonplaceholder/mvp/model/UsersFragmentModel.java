package com.job.jsonplaceholder.mvp.model;

import android.os.Handler;
import android.os.Message;

import com.job.jsonplaceholder.utils.URLHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class UsersFragmentModel implements UsersFragmentContractModel {
    private DownloadUsersHandler downloadUsersHandler = null;
    private static final int SUCCESS = 200;
    private static final int ERROR = 400;

    @Override
    public void downloadUsers(OnDownloadUsers onDownloadUsers) {
        downloadUsersHandler = new DownloadUsersHandler(onDownloadUsers);

        new Thread(() -> {
            String usersAsString = getUsersAsString();
            if (usersAsString != null) {
                JSONArray usersAsJsonArray = getUsersAsJsonArray(usersAsString);
                if (usersAsJsonArray != null) {
                    downloadUsersHandler.sendMessage(downloadUsersHandler.obtainMessage(SUCCESS, usersAsJsonArray));
                }
            } else {
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
        private final WeakReference<OnDownloadUsers> onDownloadUsers;

        DownloadUsersHandler(OnDownloadUsers onDownloadUsers) {
            this.onDownloadUsers = new WeakReference<>(onDownloadUsers);
        }

        @Override
        public void handleMessage(Message msg) {
            if (onDownloadUsers.get() != null) {
                switch (msg.what) {
                    case SUCCESS: {
                        onDownloadUsers.get().onSuccess((JSONArray) msg.obj);
                        break;
                    }
                    case ERROR: {
                        onDownloadUsers.get().onError();
                    }
                }
            }
        }
    }

}
