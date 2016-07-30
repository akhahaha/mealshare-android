package com.munch.android.flow;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.munch.android.R;
import com.munch.android.event.Event;
import com.munch.android.event.FacebookLoginEvent;
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
        Presenter nextPresenter;
        switch (event.getEventTag()) {
            case LoginFragment.EVENT_FB_LOGIN_SUCCESS:
                FacebookLoginEvent fbLoginEvent = (FacebookLoginEvent) event;
                String currUID = fbLoginEvent.getAccessToken().getUserId();
                // Save currUID to local preferences
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString(
                        getContext().getString(R.string.pref_curr_uid), currUID).apply();

                // TODO: Perform onboarding
                nextPresenter = new DashboardPresenter(getContext(), currUID);
                nextPresenter.setPrevPresenter(this);
                notifyListeners(nextPresenter);
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
