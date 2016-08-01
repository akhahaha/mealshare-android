package com.munch.android.flow;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.munch.android.R;
import com.munch.android.data.FacebookDAO;
import com.munch.android.data.MunchDAO;
import com.munch.android.data.UserQueryCallback;
import com.munch.android.event.Event;
import com.munch.android.event.FacebookLoginEvent;
import com.munch.android.model.User;
import com.munch.android.view.LoginFragment;

/**
 * Login presenter
 * Created by Alan on 7/30/2016.
 */
public class LoginPresenter extends Presenter {
    private Fragment viewFragment;

    public LoginPresenter(Context context) {
        super(context);
        this.viewFragment = LoginFragment.newInstance();
    }

    @Override
    public boolean handleEvent(Event event) {
        Presenter currPresenter = this;
        switch (event.getEventTag()) {
            case LoginFragment.EVENT_FB_LOGIN_SUCCESS:
                FacebookLoginEvent fbLoginEvent = (FacebookLoginEvent) event;

                // Generate user
                FacebookDAO.getInstance().generateMunchUser(fbLoginEvent.getAccessToken(),
                        new UserQueryCallback() {
                    @Override
                    public void onCompleted(User user) {
                        // Save currUID to local preferences
                        PreferenceManager.getDefaultSharedPreferences(getContext()).edit()
                                .putString(getContext().getString(
                                        R.string.pref_curr_uid), user.getId())
                                .apply();

                        // TODO: Perform onboarding if necessary
                        MunchDAO.getInstance().upsertUser(user);

                        notifyListeners(new DashboardPresenter(getContext(), user.getId()));
                    }

                    @Override
                    public void onFailed() {

                    }
                });

                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean handleEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                pop();
                return true;
            default:
                return false;
        }
    }

    @Override
    public Fragment getViewFragment() {
        return viewFragment;
    }
}
