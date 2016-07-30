package com.munch.android.flow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.munch.android.event.Event;
import com.munch.android.view.MealCreationFragment;

/**
 * Meal creation presenter
 * Created by Alan on 7/30/2016.
 */
public class MealCreationPresenter extends Presenter {
    public MealCreationPresenter(Context context) {
        super(context);
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
        return MealCreationFragment.newInstance();
    }
}
