package com.mealshare.android.flow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.mealshare.android.event.Event;
import com.mealshare.android.view.DashboardFragment;

/**
 * Dashboard presenter
 * Created by Alan on 7/30/2016.
 */
public class DashboardPresenter extends Presenter {
    private Fragment viewFragment;

    private String currUID;

    public DashboardPresenter(Context context, String currUID) {
        super(context);
        this.currUID = currUID;
        this.viewFragment = DashboardFragment.newInstance(currUID);
    }

    @Override
    public boolean handleEvent(Event event) {
        return false;
    }

    @Override
    public boolean handleEvent(KeyEvent event) {
        return false;
    }

    @Override
    public Fragment getViewFragment() {
        return viewFragment;
    }
}
