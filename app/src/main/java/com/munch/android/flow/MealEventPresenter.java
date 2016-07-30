package com.munch.android.flow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.munch.android.event.Event;
import com.munch.android.view.MealEventFragment;

/**
 * Meal event presenter
 * Created by Alan on 7/30/2016.
 */
public class MealEventPresenter extends Presenter {
    private Fragment viewFragment;

    String currUID;
    Integer eventID;

    public MealEventPresenter(Context context, String currUID, Integer eventID) {
        super(context);
        this.currUID = currUID;
        this.eventID = eventID;
        this.viewFragment = MealEventFragment.newInstance(currUID, eventID);
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
