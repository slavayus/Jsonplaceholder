package com.job.jsonplaceholder.mvp.view;

import android.support.v4.app.Fragment;

public class UsersActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return UsersFragment.newInstance();
    }
}
