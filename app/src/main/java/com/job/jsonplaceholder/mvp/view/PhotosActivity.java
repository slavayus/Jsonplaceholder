package com.job.jsonplaceholder.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.job.jsonplaceholder.pojo.User;

public class PhotosActivity extends SingleFragmentActivity {

    private static final String USER = "USER";

    public static Intent newInstance(Context context, User user) {
        Intent intent = new Intent(context, PhotosActivity.class);
        intent.putExtra(USER, user);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return PhotosFragment.newInstance(getIntent().getParcelableExtra(USER));
    }
}
