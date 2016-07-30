package com.mealshare.android.flow;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

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
        if (currUID == null) {
            currUID = "abc"; // TODO: Implement Facebook login
            settings.edit().putString(getActivity().getString(R.string.pref_curr_uid), currUID)
                    .apply();
            // TODO: Start onboarding
        }

        // Set default presenter
        setPresenter(new DashboardPresenter(currUID));
    }
}
