package com.mealshare.android.flow;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import com.firebase.client.Firebase;
import com.mealshare.android.R;

/**
 * Mealshare flow controller
 * Created by Alan on 7/30/2016.
 */
public class MealshareFlow extends FlowController {
    private String currUID;

    public MealshareFlow(FragmentActivity activity, int resourceID) {
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
