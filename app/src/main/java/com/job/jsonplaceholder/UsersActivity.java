package com.job.jsonplaceholder;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UsersActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return UsersFragment.newInstance();
    }
}
