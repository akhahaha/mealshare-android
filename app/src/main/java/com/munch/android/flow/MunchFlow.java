package com.munch.android.flow;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import com.facebook.AccessToken;
import com.munch.android.R;
import com.munch.android.data.FacebookDAO;
import com.munch.android.data.UserQueryCallback;
import com.munch.android.model.User;

/**
 * Munch flow controller
 * Created by Alan on 7/30/2016.
 */
public class MunchFlow extends FlowController {
    private String currUID;

    public MunchFlow(FragmentActivity activity, int resourceID) {
        super(activity, resourceID);

        // Verify Facebook login
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            // Not logged in
            setPresenter(new LoginPresenter(getActivity()));
        } else {
            // Logged in
            FacebookDAO.getInstance().generateMunchUser(accessToken, new UserQueryCallback() {
                @Override
                public void onCompleted(User user) {
                    setPresenter(new DashboardPresenter(getActivity(), user.getId()));
                }

                @Override
                public void onFailed() {
                    setPresenter(new LoginPresenter(getActivity()));
                }
            });
        }
    }
}
