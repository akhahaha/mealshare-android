package com.munch.android.flow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.munch.android.event.Event;
import com.munch.android.event.MealSelectEvent;
import com.munch.android.view.DashboardFragment;

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
        Presenter nextPresenter;
        switch (event.getEventTag()) {
            case DashboardFragment.EVENT_CREATE_MEAL:
                nextPresenter = new MealCreationPresenter(getContext());
                nextPresenter.setPrevPresenter(this);
                notifyListeners(nextPresenter);
                return true;
            case DashboardFragment.EVENT_MEAL_SELECTED:
                nextPresenter = new MealEventPresenter(getContext(), currUID,
                        ((MealSelectEvent) event).getEventID());
                nextPresenter.setPrevPresenter(this);
                notifyListeners(nextPresenter);
                return true;
            default:
                return false;
        }
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
