package com.munch.android.data;

import com.munch.android.model.User;

/**
 * User query callback interface
 * Created by Alan on 7/31/2016.
 */
public interface UserQueryCallback {
    void onCompleted(User user);

    void onFailed();
}
