package com.mealshare.android.flow;

import android.support.v4.app.FragmentActivity;

/**
 * Mealshare flow controller
 * Created by Alan on 7/30/2016.
 */
public class MealshareFlow extends FlowController {
    public MealshareFlow(FragmentActivity activity, int resourceID) {
        super(activity, resourceID);

        // Set default presenter
        setPresenter(new DashboardPresenter());
    }
}
