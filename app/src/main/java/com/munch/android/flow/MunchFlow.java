package com.munch.android.flow;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import com.munch.android.R;

/**
 * Munch flow controller
 * Created by Alan on 7/30/2016.
 */
public class MunchFlow extends FlowController {
    private String currUID;

    public MunchFlow(FragmentActivity activity, int resourceID) {
        super(activity, resourceID);

        // Initialize user session and preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        currUID = settings.getString(getActivity().getString(R.string.pref_curr_uid), null);

        // Not logged in
        if (currUID == null) {
            // Start login and onboarding
            setPresenter(new LoginPresenter(getActivity()));
        } else {
            // Logged in
            setPresenter(new DashboardPresenter(getActivity(), currUID));
        }
    }
}
